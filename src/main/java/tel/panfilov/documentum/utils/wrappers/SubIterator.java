package tel.panfilov.documentum.utils.wrappers;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tel.panfilov.documentum.utils.CoreUtils;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class SubIterator<T> implements Iterator<List<T>>, Closeable {

    private Iterator<T> _iterator;

    private final int _limit;

    public SubIterator(Iterator<T> collection, int limit) {
        _iterator = collection;
        _limit = limit;
    }

    public SubIterator(Iterable<T> collection, int limit) {
        this(collection.iterator(), limit);
    }

    @Override
    public void close() throws IOException {
        if (_iterator instanceof Closeable) {
            CoreUtils.closeSilently((Closeable) _iterator);
        }
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
    public List<T> next() {
        List<T> result = new ArrayList<T>();
        for (int i = 0; i < _limit && hasNext(); i++) {
            result.add(_iterator.next());
        }
        return result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(
                "This operation is not supported");
    }

}
