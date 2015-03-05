package tel.panfilov.documentum.utils.wrappers;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

import tel.panfilov.documentum.utils.CoreUtils;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class BufferedReaderIterator implements Iterator<String>, Closeable {

    private BufferedReader _reader;

    private String _next;

    public BufferedReaderIterator(BufferedReader reader) {
        _reader = reader;
    }

    @Override
    public void close() throws IOException {
        CoreUtils.closeSilently(_reader);
        _reader = null;
    }

    @Override
    public boolean hasNext() {
        if (_next != null) {
            return true;
        }
        if (_reader == null) {
            return false;
        }
        try {
            _next = _reader.readLine();
            if (_next != null) {
                return true;
            }
            CoreUtils.closeSilently(this);
            return false;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new IllegalStateException("Empty iterator");
        }
        String current = _next;
        _next = null;
        return current;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(
                "This operation is not supported");
    }
}
