package tel.panfilov.documentum.utils.wrappers;

import java.util.Enumeration;

import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.client.IDfTypedObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfAttr;
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfTime;
import com.documentum.fc.common.IDfValue;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class IDfTypedObjectWrapper<T extends IDfTypedObject> implements
        IDfTypedObject {

    private final T _wrappedObject;

    public IDfTypedObjectWrapper(T wrappedObject) {
        if (wrappedObject == null) {
            throw new NullPointerException("object is null");
        }
        _wrappedObject = wrappedObject;
    }

    protected final T getWrappedObject() {
        return _wrappedObject;
    }

    @Override
    public void appendBoolean(String attr, boolean value) throws DfException {
        _wrappedObject.appendBoolean(attr, value);
    }

    @Override
    public void appendDouble(String attr, double value) throws DfException {
        _wrappedObject.appendDouble(attr, value);
    }

    @Override
    public void appendId(String attr, IDfId value) throws DfException {
        _wrappedObject.appendId(attr, value);
    }

    @Override
    public void appendInt(String attr, int value) throws DfException {
        _wrappedObject.appendInt(attr, value);
    }

    @Override
    public void appendString(String attr, String value) throws DfException {
        _wrappedObject.appendString(attr, value);
    }

    @Override
    public void appendTime(String attr, IDfTime value) throws DfException {
        _wrappedObject.appendTime(attr, value);
    }

    @Override
    public void appendValue(String attr, IDfValue value) throws DfException {
        _wrappedObject.appendValue(attr, value);
    }

    @Override
    public String dump() throws DfException {
        return _wrappedObject.dump();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Enumeration enumAttrs() throws DfException {
        return _wrappedObject.enumAttrs();
    }

    @Override
    public int findAttrIndex(String attr) throws DfException {
        return _wrappedObject.findAttrIndex(attr);
    }

    @Override
    public int findBoolean(String attr, boolean value) throws DfException {
        return _wrappedObject.findBoolean(attr, value);
    }

    @Override
    public int findDouble(String attr, double value) throws DfException {
        return _wrappedObject.findDouble(attr, value);
    }

    @Override
    public int findId(String attr, IDfId value) throws DfException {
        return _wrappedObject.findId(attr, value);
    }

    @Override
    public int findInt(String attr, int value) throws DfException {
        return _wrappedObject.findInt(attr, value);
    }

    @Override
    public int findString(String attr, String value) throws DfException {
        return _wrappedObject.findString(attr, value);
    }

    @Override
    public int findTime(String attr, IDfTime value) throws DfException {
        return _wrappedObject.findTime(attr, value);
    }

    @Override
    public int findValue(String attr, IDfValue value) throws DfException {
        return _wrappedObject.findValue(attr, value);
    }

    @Override
    public String getAllRepeatingStrings(String attr, String separator)
        throws DfException {
        return _wrappedObject.getAllRepeatingStrings(attr, separator);
    }

    @Override
    public IDfAttr getAttr(int index) throws DfException {
        return _wrappedObject.getAttr(index);
    }

    @Override
    public int getAttrCount() throws DfException {
        return _wrappedObject.getAttrCount();
    }

    @Override
    public int getAttrDataType(String attr) throws DfException {
        return _wrappedObject.getAttrDataType(attr);
    }

    @Override
    public boolean getBoolean(String attr) throws DfException {
        return _wrappedObject.getBoolean(attr);
    }

    @Override
    public double getDouble(String attr) throws DfException {
        return _wrappedObject.getDouble(attr);
    }

    @Override
    public IDfId getId(String attr) throws DfException {
        return _wrappedObject.getId(attr);
    }

    @Override
    public int getInt(String attr) throws DfException {
        return _wrappedObject.getInt(attr);
    }

    @Override
    public long getLong(String attr) throws DfException {
        return _wrappedObject.getLong(attr);
    }

    @Override
    public IDfId getObjectId() throws DfException {
        return _wrappedObject.getObjectId();
    }

    @Override
    public IDfSession getObjectSession() {
        return _wrappedObject.getObjectSession();
    }

    @Override
    public IDfSession getOriginalSession() {
        return _wrappedObject.getOriginalSession();
    }

    @Override
    public boolean getRepeatingBoolean(String attr, int index)
        throws DfException {
        return _wrappedObject.getRepeatingBoolean(attr, index);
    }

    @Override
    public double getRepeatingDouble(String attr, int index) throws DfException {
        return _wrappedObject.getRepeatingDouble(attr, index);
    }

    @Override
    public IDfId getRepeatingId(String attr, int index) throws DfException {
        return _wrappedObject.getRepeatingId(attr, index);
    }

    @Override
    public int getRepeatingInt(String attr, int index) throws DfException {
        return _wrappedObject.getRepeatingInt(attr, index);
    }

    @Override
    public long getRepeatingLong(String attr, int index) throws DfException {
        return _wrappedObject.getRepeatingLong(attr, index);
    }

    @Override
    public String getRepeatingString(String attr, int index) throws DfException {
        return _wrappedObject.getRepeatingString(attr, index);
    }

    @Override
    public IDfTime getRepeatingTime(String attr, int index) throws DfException {
        return _wrappedObject.getRepeatingTime(attr, index);
    }

    @Override
    public IDfValue getRepeatingValue(String attr, int index)
        throws DfException {
        return _wrappedObject.getRepeatingValue(attr, index);
    }

    @Override
    public IDfSession getSession() {
        return _wrappedObject.getSession();
    }

    @Override
    public IDfSessionManager getSessionManager() {
        return _wrappedObject.getSessionManager();
    }

    @Override
    public String getString(String attr) throws DfException {
        return _wrappedObject.getString(attr);
    }

    @Override
    public IDfTime getTime(String attr) throws DfException {
        return _wrappedObject.getTime(attr);
    }

    @Override
    public IDfValue getValue(String attr) throws DfException {
        return _wrappedObject.getValue(attr);
    }

    @Override
    public IDfValue getValueAt(int index) throws DfException {
        return _wrappedObject.getValueAt(index);
    }

    @Override
    public int getValueCount(String attr) throws DfException {
        return _wrappedObject.getValueCount(attr);
    }

    @Override
    public boolean hasAttr(String attr) throws DfException {
        return _wrappedObject.hasAttr(attr);
    }

    @Override
    public void insertBoolean(String attr, int index, boolean value)
        throws DfException {
        _wrappedObject.insertBoolean(attr, index, value);
    }

    @Override
    public void insertDouble(String attr, int index, double value)
        throws DfException {
        _wrappedObject.insertDouble(attr, index, value);
    }

    @Override
    public void insertId(String attr, int index, IDfId value)
        throws DfException {
        _wrappedObject.insertId(attr, index, value);
    }

    @Override
    public void insertInt(String attr, int index, int value) throws DfException {
        _wrappedObject.insertInt(attr, index, value);
    }

    @Override
    public void insertString(String attr, int index, String value)
        throws DfException {
        _wrappedObject.insertString(attr, index, value);
    }

    @Override
    public void insertTime(String attr, int index, IDfTime value)
        throws DfException {
        _wrappedObject.insertTime(attr, index, value);
    }

    @Override
    public void insertValue(String attr, int index, IDfValue value)
        throws DfException {
        _wrappedObject.insertValue(attr, index, value);
    }

    @Override
    public boolean isAttrRepeating(String attr) throws DfException {
        return _wrappedObject.isAttrRepeating(attr);
    }

    @Override
    public boolean isNull(String attr) throws DfException {
        return _wrappedObject.isNull(attr);
    }

    @Override
    public void remove(String attr, int index) throws DfException {
        _wrappedObject.remove(attr, index);
    }

    @Override
    public void removeAll(String attr) throws DfException {
        _wrappedObject.removeAll(attr);
    }

    @Override
    public void setBoolean(String attr, boolean value) throws DfException {
        _wrappedObject.setBoolean(attr, value);
    }

    @Override
    public void setDouble(String attr, double value) throws DfException {
        _wrappedObject.setDouble(attr, value);
    }

    @Override
    public void setId(String attr, IDfId value) throws DfException {
        _wrappedObject.setId(attr, value);
    }

    @Override
    public void setInt(String attr, int value) throws DfException {
        _wrappedObject.setInt(attr, value);
    }

    @Override
    public void setNull(String attr) throws DfException {
        _wrappedObject.setNull(attr);
    }

    @Override
    public void setRepeatingBoolean(String attr, int index, boolean value)
        throws DfException {
        _wrappedObject.setRepeatingBoolean(attr, index, value);
    }

    @Override
    public void setRepeatingDouble(String attr, int index, double value)
        throws DfException {
        _wrappedObject.setRepeatingDouble(attr, index, value);
    }

    @Override
    public void setRepeatingId(String attr, int index, IDfId value)
        throws DfException {
        _wrappedObject.setRepeatingId(attr, index, value);
    }

    @Override
    public void setRepeatingInt(String attr, int index, int value)
        throws DfException {
        _wrappedObject.setRepeatingInt(attr, index, value);
    }

    @Override
    public void setRepeatingString(String attr, int index, String value)
        throws DfException {
        _wrappedObject.setRepeatingString(attr, index, value);
    }

    @Override
    public void setRepeatingTime(String attr, int index, IDfTime iDfTime)
        throws DfException {
        _wrappedObject.setRepeatingTime(attr, index, iDfTime);
    }

    @Override
    public void setRepeatingValue(String attr, int index, IDfValue value)
        throws DfException {
        _wrappedObject.setRepeatingValue(attr, index, value);
    }

    @Override
    public void setSessionManager(IDfSessionManager sessionManager)
        throws DfException {
        _wrappedObject.setSessionManager(sessionManager);
    }

    @Override
    public void setString(String attr, String value) throws DfException {
        _wrappedObject.setString(attr, value);
    }

    @Override
    public void setTime(String attr, IDfTime value) throws DfException {
        _wrappedObject.setTime(attr, value);
    }

    @Override
    public void setValue(String attr, IDfValue value) throws DfException {
        _wrappedObject.setValue(attr, value);
    }

    @Override
    public void truncate(String attr, int index) throws DfException {
        _wrappedObject.truncate(attr, index);
    }

}
