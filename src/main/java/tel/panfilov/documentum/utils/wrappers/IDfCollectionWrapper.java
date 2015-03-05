package tel.panfilov.documentum.utils.wrappers;

import java.io.ByteArrayInputStream;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfTypedObject;
import com.documentum.fc.common.DfException;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class IDfCollectionWrapper<T extends IDfCollection> extends
        IDfTypedObjectWrapper<T> implements IDfCollection {

    public IDfCollectionWrapper(T wrappedObject) {
        super(wrappedObject);
    }

    @Override
    public void close() throws DfException {
        getWrappedObject().close();
    }

    @Override
    public boolean next() throws DfException {
        return getWrappedObject().next();
    }

    @Override
    public IDfTypedObject getTypedObject() throws DfException {
        return getWrappedObject().getTypedObject();
    }

    @Override
    public int getState() {
        return getWrappedObject().getState();
    }

    @Override
    public int getStateEx() {
        return getWrappedObject().getStateEx();
    }

    @Override
    public ByteArrayInputStream getBytesBuffer(String cmd, String buf,
            String buflen, int length) throws DfException {
        return getWrappedObject().getBytesBuffer(cmd, buf, buflen, length);
    }

}
