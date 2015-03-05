package tel.panfilov.documentum.utils.wrappers;

import org.w3c.dom.Element;

import com.documentum.bpm.IDfWorkitemEx;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfList;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class IDfWorkitemExWrapper<T extends IDfWorkitemEx> extends
        IDfWorkitemWrapper<T> implements IDfWorkitemEx {

    public IDfWorkitemExWrapper(T wrappedObject) {
        super(wrappedObject);
    }

    @Override
    public IDfList getAllPackageObjectsWithBinding() throws DfException {
        return getWrappedObject().getAllPackageObjectsWithBinding();
    }

    @Override
    public IDfList getPackageObjectsWithBinding() throws DfException {
        return getWrappedObject().getPackageObjectsWithBinding();
    }

    @Override
    public Object getPrimitiveVariableValue(String variableName)
        throws DfException {
        return getWrappedObject().getPrimitiveVariableValue(variableName);
    }

    @Override
    public Object getStructuredDataTypeAttrValue(String variableName,
            String attrName) throws DfException {
        return getWrappedObject().getStructuredDataTypeAttrValue(variableName,
                attrName);
    }

    @Override
    public Object[] getStructuredDataTypeAttrValues(String variableName,
            String attrName) throws DfException {
        return getWrappedObject().getStructuredDataTypeAttrValues(variableName,
                attrName);
    }

    @Override
    public Element getStructuredDataTypeVariableValue(String variableName)
        throws DfException {
        return getWrappedObject().getStructuredDataTypeVariableValue(
                variableName);
    }

    @Override
    public void setPrimitiveObjectValue(String variableName, Object value)
        throws DfException {
        getWrappedObject().setPrimitiveObjectValue(variableName, value);
    }

    @Override
    public void setStructuredDataTypeAttrValue(String variableName,
            String attrName, Object newValue) throws DfException {
        getWrappedObject().setStructuredDataTypeAttrValue(variableName,
                attrName, newValue);
    }

    @Override
    public void setStructuredDataTypeAttrValues(String variableName,
            String attrName, Object[] newValues) throws DfException {
        getWrappedObject().setStructuredDataTypeAttrValues(variableName,
                attrName, newValues);
    }

    @Override
    public void setStructuredDataTypeVariableValue(String variableName,
            Element element) throws DfException {
        getWrappedObject().setStructuredDataTypeVariableValue(variableName,
                element);
    }

}
