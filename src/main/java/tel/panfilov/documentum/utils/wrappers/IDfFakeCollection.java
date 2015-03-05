package tel.panfilov.documentum.utils.wrappers;

import java.io.ByteArrayInputStream;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfTypedObject;
import com.documentum.fc.common.DfException;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class IDfFakeCollection<T extends IDfTypedObject> extends
        IDfTypedObjectWrapper<T> implements IDfCollection {

    public IDfFakeCollection(T wrappedObject) {
        super(wrappedObject);
    }

    @Override
    public boolean next() throws DfException {
        throw new UnsupportedOperationException(
                "This operation is not supported");
    }

    @Override
    public IDfTypedObject getTypedObject() throws DfException {
        return getWrappedObject();
    }

    @Override
    public void close() throws DfException {
        throw new UnsupportedOperationException(
                "This operation is not supported");
    }

    @Override
    public int getState() {
        throw new UnsupportedOperationException(
                "This operation is not supported");
    }

    @Override
    public int getStateEx() {
        throw new UnsupportedOperationException(
                "This operation is not supported");
    }

    @Override
    public ByteArrayInputStream getBytesBuffer(String s, String s1, String s2,
            int i) throws DfException {
        throw new UnsupportedOperationException(
                "This operation is not supported");
    }

}
