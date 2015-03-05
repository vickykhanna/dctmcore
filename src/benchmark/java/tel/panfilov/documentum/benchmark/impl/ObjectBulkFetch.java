package tel.panfilov.documentum.benchmark.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfType;
import com.documentum.fc.client.impl.objectmanager.PersistentObjectManager;
import com.documentum.fc.client.impl.session.ISession;
import com.documentum.fc.common.DfDocbaseConstants;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfList;
import com.documentum.fc.common.IDfAttr;

import tel.panfilov.documentum.utils.query.QueryHelper;
import tel.panfilov.documentum.utils.query.ReservedWords;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class ObjectBulkFetch extends ObjectFetch {

    protected IDfCollection _collection;

    private String _attrs;

    public ObjectBulkFetch(String docbase, String userName, String password) {
        super(docbase, userName, password);
    }

    @Override
    public void doSetup() {
        try {
            super.doSetup();
            Set<String> attrs = new HashSet<String>();
            attrs.add(DfDocbaseConstants.R_OBJECT_ID);
            attrs.add(DfDocbaseConstants.I_VSTAMP);
            boolean hasRepeatings = false;
            IDfType type = _session.getType("dm_document");
            for (int i = 0, n = type.getTypeAttrCount(); i < n; i++) {
                IDfAttr attr = type.getTypeAttr(i);
                attrs.add(attr.getName());
                if (attr.isRepeating()) {
                    hasRepeatings = true;
                }
            }
            if (hasRepeatings) {
                attrs.add("i_position");
            }
            _attrs = ReservedWords.getProjection(attrs);
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void doOp() {
        try {
            if (_collection == null) {
                renewCollection(_session);
            }
            if (!_collection.next()) {
                if (_collection.getState() != IDfCollection.DF_CLOSED_STATE) {
                    _collection.close();
                }
                renewCollection(_session);
                if (!_collection.next()) {
                    throw new RuntimeException("no objects");
                }
            }
            PersistentObjectManager objectManager = ((ISession) _session)
                    .getObjectManager();
            IDfPersistentObject object = objectManager.getObjectFromQueryRow(
                    _collection, null);
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void doRelease() {
        try {
            if (_collection != null
                    && _collection.getState() != IDfCollection.DF_CLOSED_STATE) {
                _collection.close();
            }
            super.doRelease();
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected void renewCollection(IDfSession session) throws DfException,
        IOException {
        _collection = session
                .apply(null, "DQL_MATCH", new DfList(new String[] {
                    "QUERY_TYPE", "QUERY_PREDICATE" }), new DfList(
                        new String[] {"S", "S" }), new DfList(new String[] {
                    "dm_document", getRestriction() }));
    }

    protected final String getProjection() {
        return "SELECT " + _attrs + " FROM dm_document ";
    }

    protected final String getRestriction() throws IOException {
        List<String> objects = new ArrayList<String>(MAX_OBJECTS_IN_QUERY);
        String objectId = null;
        while ((objectId = _objects.readLine()) != null) {
            objects.add(objectId);
            if (objects.size() >= MAX_OBJECTS_IN_QUERY) {
                break;
            }
        }
        if (objects.size() == 0) {
            throw new IOException("empty buffer");
        }
        return QueryHelper.createInClause(DfDocbaseConstants.R_OBJECT_ID,
                objects, MAX_OBJECTS_IN_CLAUSE);
    }

    protected final String getOrderBy() {
        StringBuilder query = new StringBuilder();
        query.append(" ORDER BY ").append(DfDocbaseConstants.R_OBJECT_ID);
        if (_attrs.contains("i_position")) {
            query.append(", i_position DESC");
        }
        return query.toString();
    }

}
