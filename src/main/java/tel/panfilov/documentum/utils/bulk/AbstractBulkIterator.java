package tel.panfilov.documentum.utils.bulk;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfType;
import com.documentum.fc.client.IDfTypedObject;
import com.documentum.fc.client.impl.objectmanager.PersistentObjectManager;
import com.documentum.fc.client.impl.session.ISession;
import com.documentum.fc.common.DfDocbaseConstants;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfAttr;

import tel.panfilov.documentum.utils.CoreUtils;
import tel.panfilov.documentum.utils.query.ReservedWords;
import tel.panfilov.documentum.utils.wrappers.IDfFakeCollection;
import tel.panfilov.documentum.utils.wrappers.IDfQueriesIterator;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public abstract class AbstractBulkIterator<T> implements
        Iterator<IDfPersistentObject>, Closeable {

    private final IDfSession _session;

    private final String _objectType;

    private String _projection;

    private final ISkipChecker _skipChecker;

    private IDfQueriesIterator _iterator;

    private IDfTypedObject _next;

    public AbstractBulkIterator(IDfSession session, String objectType, T param,
            ISkipChecker skipChecker) {
        _session = session;
        _objectType = objectType;
        _skipChecker = skipChecker;
        _iterator = new IDfQueriesIterator(_session, getQueries(param));
    }

    @Override
    public boolean hasNext() {
        try {
            if (_next != null) {
                return true;
            }
            while (_iterator != null && _iterator.hasNext()) {
                IDfTypedObject object = _iterator.next();
                if (skip(object)) {
                    continue;
                }
                _next = object;
                return true;
            }
            CoreUtils.closeSilently(this);
            return false;
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public IDfPersistentObject next() {
        try {
            IDfTypedObject current = null;
            if (hasNext()) {
                current = _next;
            }
            _next = null;
            if (current == null) {
                throw new IllegalStateException("Empty iterator");
            }
            PersistentObjectManager objectManager = ((ISession) _session)
                    .getObjectManager();
            return objectManager
                    .getObjectFromQueryRow(
                            new IDfFakeCollection<IDfTypedObject>(current),
                            _objectType);
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(
                "This operation is not supported");
    }

    @Override
    public void close() throws IOException {
        CoreUtils.closeSilently(_iterator);
        _iterator = null;
    }

    protected abstract Iterator<String> getQueries(T param);

    protected final boolean skip(IDfTypedObject object) throws DfException {
        if (_skipChecker == null) {
            return false;
        }
        if (!object.hasAttr(DfDocbaseConstants.R_OBJECT_ID)) {
            return false;
        }
        String objectId = object.getString(DfDocbaseConstants.R_OBJECT_ID);
        return _skipChecker.skip(objectId);
    }

    protected final IDfSession getSession() {
        return _session;
    }

    protected final String getObjectType() {
        return _objectType;
    }

    protected final String getProjection() {
        if (StringUtils.isNotBlank(_projection)) {
            return _projection;
        }
        try {
            Set<String> attributes;
            IDfType type = _session.getType(_objectType);
            if (type == null) {
                attributes = Collections.emptySet();
            } else {
                boolean hasRepeatings = false;
                attributes = new LinkedHashSet<String>();
                for (int i = 0, n = type.getTypeAttrCount(); i < n; i++) {
                    IDfAttr attr = type.getTypeAttr(i);
                    attributes.add(attr.getName());
                    if (attr.isRepeating()) {
                        hasRepeatings = true;
                    }
                }
                attributes.add(DfDocbaseConstants.R_OBJECT_ID);
                attributes.add(DfDocbaseConstants.I_VSTAMP);
                if (hasRepeatings) {
                    attributes.add("i_position");
                }
            }
            _projection = ReservedWords.getProjection(attributes);
            return _projection;
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        }

    }

}
