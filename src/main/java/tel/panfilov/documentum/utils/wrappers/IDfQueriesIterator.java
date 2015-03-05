package tel.panfilov.documentum.utils.wrappers;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfTypedObject;

import tel.panfilov.documentum.utils.CoreUtils;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class IDfQueriesIterator implements Iterator<IDfTypedObject>, Closeable {

    private final IDfSession _session;

    private Iterator<String> _queries;

    private IDfQueryIterator _iterator;

    public IDfQueriesIterator(IDfSession session, Iterator<String> queries) {
        _session = session;
        _queries = queries;
    }

    @Override
    public void close() throws IOException {
        CoreUtils.closeSilently(_iterator);
        _iterator = null;
        if (_queries instanceof Closeable) {
            CoreUtils.closeSilently((Closeable) _queries);
        }
        _queries = null;
    }

    @Override
    public boolean hasNext() {
        if (_iterator != null && _iterator.hasNext()) {
            return true;
        }
        if (_iterator != null) {
            CoreUtils.closeSilently(_iterator);
            _iterator = null;
        }
        while (_queries != null && _queries.hasNext()) {
            _iterator = new IDfQueryIterator(_session, _queries.next());
            if (_iterator.hasNext()) {
                return true;
            }
            CoreUtils.closeSilently(_iterator);
            _iterator = null;
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
