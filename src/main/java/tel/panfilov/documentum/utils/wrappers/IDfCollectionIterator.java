package tel.panfilov.documentum.utils.wrappers;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfTypedObject;
import com.documentum.fc.common.DfException;

import tel.panfilov.documentum.utils.CoreUtils;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public final class IDfCollectionIterator extends
        IDfTypedObjectWrapper<IDfTypedObject> implements Closeable,
        Iterator<IDfTypedObject> {

    private IDfTypedObject _next;

    public IDfCollectionIterator(IDfCollection wrappedObject) {
        super(wrappedObject);
    }

    private IDfCollection getCollection() {
        return (IDfCollection) getWrappedObject();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(
                "This operation is not supported");
    }

    private IDfTypedObject getTypedObject() throws DfException {
        return getCollection().getTypedObject();
    }

    public boolean hasNext() {
        try {
            if (_next != null) {
                return true;
            }
            if (isClosed()) {
                return false;
            }
            if (doNext()) {
                _next = getTypedObject();
                return true;
            } else {
                CoreUtils.closeSilently(this);
                return false;
            }
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public IDfTypedObject next() {
        IDfTypedObject current = null;
        if (hasNext()) {
            current = _next;
        }
        _next = null;
        if (current == null) {
            switch (doGetState()) {
            case IDfCollection.DF_INITIAL_STATE:
            case IDfCollection.DF_READY_STATE:
                throw new IllegalStateException("next() not called yet");
            case IDfCollection.DF_CLOSED_STATE:
                throw new IllegalStateException("collection closed");
            case IDfCollection.DF_NO_MORE_ROWS_STATE:
                throw new IllegalStateException("beyond end of collection");
            default:
                throw new IllegalStateException("Unknown state, state: "
                        + doGetState());
            }
        }
        return current;
    }

    @Override
    public void close() throws IOException {
        if (isClosed()) {
            return;
        }
        try {
            doClose();
        } catch (DfException ex) {
            throw new IOException(ex);
        }
    }

    private boolean isClosed() {
        return doGetState() == IDfCollection.DF_CLOSED_STATE;
    }

    private int doGetState() {
        return getCollection().getState();
    }

    private boolean doNext() throws DfException {
        return getCollection().next();
    }

    private void doClose() throws DfException {
        getCollection().close();
    }

}
