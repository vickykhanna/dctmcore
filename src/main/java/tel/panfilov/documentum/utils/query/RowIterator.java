package tel.panfilov.documentum.utils.query;

import java.io.ByteArrayInputStream;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfTypedObject;
import com.documentum.fc.common.DfException;

import tel.panfilov.documentum.utils.wrappers.IDfTypedObjectWrapper;

final class RowIterator extends IDfTypedObjectWrapper<IDfCollection> implements
        IDfCollection {

    RowIterator(IDfCollection coll) {
        super(coll);
    }

    public boolean next() throws DfException {
        throw new UnsupportedOperationException(
                "This operation is not supported");
    }

    protected boolean nextInternal() throws DfException {
        return getWrappedObject().next();
    }

    public void close() throws DfException {
        throw new UnsupportedOperationException(
                "This operation is not supported");
    }

    protected void closeInternal() throws DfException {
        getWrappedObject().close();
    }

    public IDfTypedObject getTypedObject() throws DfException {
        return getWrappedObject().getTypedObject();
    }

    public int getState() {
        return getWrappedObject().getState();
    }

    public int getStateEx() {
        return getWrappedObject().getStateEx();
    }

    public ByteArrayInputStream getBytesBuffer(String str1, String str2,
            String str3, int count) throws DfException {
        return getWrappedObject().getBytesBuffer(str1, str2, str3, count);
    }

}
