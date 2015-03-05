package tel.panfilov.documentum.utils.wrappers;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfTypedObject;
import com.documentum.fc.common.DfException;

import tel.panfilov.documentum.utils.CoreUtils;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public final class IDfQueryIterator implements Iterator<IDfTypedObject>,
        Closeable {

    private IDfCollectionIterator _iterator;

    public IDfQueryIterator(IDfSession session, String query) {
        try {
            _iterator = new IDfCollectionIterator(new DfQuery(query).execute(
                    session, IDfQuery.DF_EXEC_QUERY));
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void close() throws IOException {
        CoreUtils.closeSilently(_iterator);
        _iterator = null;
    }

    @Override
    public boolean hasNext() {
        if (_iterator != null && _iterator.hasNext()) {
            return true;
        }
        CoreUtils.closeSilently(this);
        return false;
    }

    @Override
    public IDfTypedObject next() {
        if (_iterator == null) {
            throw new IllegalStateException("Null iterator");
        }
        if (!hasNext()) {
            throw new IllegalStateException("Empty iterator");
        }
        return _iterator.next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(
                "This operation is not supported");
    }

}
