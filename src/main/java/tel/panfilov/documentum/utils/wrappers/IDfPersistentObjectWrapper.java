package tel.panfilov.documentum.utils.wrappers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.documentum.fc.client.DfTypedObject;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.client.IDfRelation;
import com.documentum.fc.client.IDfType;
import com.documentum.fc.client.IDfValidator;
import com.documentum.fc.client.impl.IPersistentObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfList;
import com.documentum.fc.common.IDfTime;
import com.documentum.fc.common.IDfValue;

import tel.panfilov.utils.ReflectionUtils;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class IDfPersistentObjectWrapper<T extends IDfPersistentObject> extends
        IDfTypedObjectWrapper<T> implements IDfPersistentObject {

    public IDfPersistentObjectWrapper(T wrappedObject) {
        super(wrappedObject);
    }

    @Override
    public IDfRelation addChildRelative(String relationTypeName, IDfId childId,
            String childLabel, boolean isPermanent, String description)
        throws DfException {
        return getWrappedObject().addChildRelative(relationTypeName, childId,
                childLabel, isPermanent, description);
    }

    @Override
    public IDfRelation addParentRelative(String relationTypeName,
            IDfId parentId, String childLabel, boolean isPermanent,
            String description) throws DfException {
        return getWrappedObject().addParentRelative(relationTypeName, parentId,
                childLabel, isPermanent, description);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean apiExec(String cmd, String args) throws DfException {
        return getWrappedObject().apiExec(cmd, args);
    }

    @Override
    @SuppressWarnings("deprecation")
    public String apiGet(String cmd, String args) throws DfException {
        return getWrappedObject().apiGet(cmd, args);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean apiSet(String cmd, String attr, String value)
        throws DfException {
        return getWrappedObject().apiSet(cmd, attr, value);
    }

    @Override
    public void destroy() throws DfException {
        getWrappedObject().destroy();
    }

    @Override
    public boolean fetch(String ignore) throws DfException {
        return getWrappedObject().fetch(ignore);
    }

    @Override
    public boolean fetchWithCaching(String currencyCheckValue,
            boolean usePersistentCache, boolean useSharedCache)
        throws DfException {
        return getWrappedObject().fetchWithCaching(currencyCheckValue,
                usePersistentCache, useSharedCache);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IDfList getAttrAssistance(String attrName) throws DfException {
        return getWrappedObject().getAttrAssistance(attrName);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IDfList getAttrAssistanceWithValues(String attrName,
            IDfList depAttrNameList, IDfList depAttrValueListList)
        throws DfException {
        return getWrappedObject().getAttrAssistanceWithValues(attrName,
                depAttrNameList, depAttrValueListList);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IDfList getAttrAsstDependencies(String attrName) throws DfException {
        return getWrappedObject().getAttrAsstDependencies(attrName);
    }

    @Override
    public IDfCollection getChildRelatives(String relationTypeName)
        throws DfException {
        return getWrappedObject().getChildRelatives(relationTypeName);
    }

    @Override
    public IDfCollection getParentRelatives(String relationTypeName)
        throws DfException {
        return getWrappedObject().getParentRelatives(relationTypeName);
    }

    @Override
    public int getPartition() throws DfException {
        return getWrappedObject().getPartition();
    }

    @Override
    public IDfType getType() throws DfException {
        return getWrappedObject().getType();
    }

    @Override
    public IDfValidator getValidator() throws DfException {
        return getWrappedObject().getValidator();
    }

    @Override
    public int getVStamp() throws DfException {
        return getWrappedObject().getVStamp();
    }

    @Override
    @SuppressWarnings("deprecation")
    public String getWidgetType(int environment, String attrName)
        throws DfException {
        return getWrappedObject().getWidgetType(environment, attrName);
    }

    @Override
    public boolean isDeleted() throws DfException {
        return getWrappedObject().isDeleted();
    }

    @Override
    public boolean isDirty() throws DfException {
        return getWrappedObject().isDirty();
    }

    @Override
    public boolean isInstanceOf(String typeName) throws DfException {
        return getWrappedObject().isInstanceOf(typeName);
    }

    @Override
    public boolean isNew() throws DfException {
        return getWrappedObject().isNew();
    }

    @Override
    public boolean isReplica() throws DfException {
        return getWrappedObject().isReplica();
    }

    @Override
    public void lock() throws DfException {
        getWrappedObject().lock();
    }

    @Override
    public void lockEx(boolean validateStamp) throws DfException {
        getWrappedObject().lockEx(validateStamp);
    }

    @Override
    public void registerEvent(String message, String event, int priority,
            boolean sendMail) throws DfException {
        getWrappedObject().registerEvent(message, event, priority, sendMail);
    }

    @Override
    public void removeChildRelative(String relationTypeName, IDfId childId,
            String childLabel) throws DfException {
        getWrappedObject().removeChildRelative(relationTypeName, childId,
                childLabel);
    }

    @Override
    public void removeParentRelative(String relationTypeName, IDfId parentId,
            String childLabel) throws DfException {
        getWrappedObject().removeParentRelative(relationTypeName, parentId,
                childLabel);
    }

    @Override
    public void revert() throws DfException {
        getWrappedObject().revert();
    }

    @Override
    public void save() throws DfException {
        getWrappedObject().save();
    }

    @Override
    public void setPartition(int partition) throws DfException {
        getWrappedObject().setPartition(partition);
    }

    @Override
    public void signoff(String user, String osPassword, String reason)
        throws DfException {
        getWrappedObject().signoff(user, osPassword, reason);
    }

    @Override
    public void unRegisterEvent(String event) throws DfException {
        getWrappedObject().unRegisterEvent(event);
    }

    @Override
    public void unRegisterEventEx(String event, String userName)
        throws DfException {
        getWrappedObject().unRegisterEventEx(event, userName);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void validateAllRules(int stopAfterNumOfErrors) throws DfException {
        getWrappedObject().validateAllRules(stopAfterNumOfErrors);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void validateAttrRules(String attrName, int stopAfterNumOfErrors)
        throws DfException {
        getWrappedObject().validateAttrRules(attrName, stopAfterNumOfErrors);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void validateAttrRulesWithValue(String attrName, String value,
            int stopAfterNumOfErrors) throws DfException {
        getWrappedObject().validateAttrRulesWithValue(attrName, value,
                stopAfterNumOfErrors);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void validateAttrRulesWithValues(String attrName, IDfList valueList,
            int stopAfterNumOfErrors) throws DfException {
        getWrappedObject().validateAttrRulesWithValues(attrName, valueList,
                stopAfterNumOfErrors);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void validateObjRules(int stopAfterNumOfErrors) throws DfException {
        getWrappedObject().validateObjRules(stopAfterNumOfErrors);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void validateObjRulesWithValues(IDfList attrNameList,
            IDfList valueListList, int stopAfterNumOfErrors) throws DfException {
        getWrappedObject().validateObjRulesWithValues(attrNameList,
                valueListList, stopAfterNumOfErrors);
    }

    @Override
    public void removeAll(String attr) throws DfException {
        if (isInternalAttribute(attr)) {
            removeAllInternal(attr);
        } else {
            super.removeAll(attr);
        }
    }

    @Override
    public final void setValue(String attributeName, IDfValue value)
        throws DfException {
        if (value == null) {
            throw new NullPointerException("value");
        }
        if (value.getDataType() == IDfValue.DF_TIME) {
            setTime(attributeName, value.asTime());
        } else {
            setString(attributeName, value.toString());
        }
    }

    @Override
    public final void appendValue(String attributeName, IDfValue value)
        throws DfException {
        if (value == null) {
            throw new NullPointerException("value");
        }
        if (value.getDataType() == IDfValue.DF_TIME) {
            appendTime(attributeName, value.asTime());
        } else {
            appendString(attributeName, value.toString());
        }
    }

    @Override
    public void setString(String attr, String value) throws DfException {
        if (isInternalAttribute(attr)) {
            setStringInternal(attr, value);
        } else {
            super.setString(attr, value);
        }
    }

    @Override
    public void appendString(String attr, String value) throws DfException {
        if (isInternalAttribute(attr)) {
            appendStringInternal(attr, value);
        } else {
            super.appendString(attr, value);
        }
    }

    @Override
    public void setTime(String attr, IDfTime value) throws DfException {
        if (isInternalAttribute(attr)) {
            setTimeInternal(attr, value);
        } else {
            super.setTime(attr, value);
        }
    }

    @Override
    public void appendTime(String attr, IDfTime value) throws DfException {
        if (isInternalAttribute(attr)) {
            appendTimeInternal(attr, value);
        } else {
            super.appendTime(attr, value);
        }
    }

    @Override
    public void setBoolean(String attr, boolean value) throws DfException {
        if (isInternalAttribute(attr)) {
            setBooleanInternal(attr, value);
        } else {
            super.setBoolean(attr, value);
        }
    }

    @Override
    public void appendBoolean(String attr, boolean value) throws DfException {
        if (isInternalAttribute(attr)) {
            appendBooleanInternal(attr, value);
        } else {
            super.appendBoolean(attr, value);
        }
    }

    @Override
    public void setDouble(String attr, double value) throws DfException {
        if (isInternalAttribute(attr)) {
            setDoubleInternal(attr, value);
        } else {
            super.setDouble(attr, value);
        }
    }

    @Override
    public void appendDouble(String attr, double value) throws DfException {
        if (isInternalAttribute(attr)) {
            appendDoubleInternal(attr, value);
        } else {
            super.appendDouble(attr, value);
        }
    }

    @Override
    public void setId(String attr, IDfId value) throws DfException {
        if (isInternalAttribute(attr)) {
            setIdInternal(attr, value);
        } else {
            super.setId(attr, value);
        }
    }

    @Override
    public void appendId(String attr, IDfId value) throws DfException {
        if (isInternalAttribute(attr)) {
            appendIdInternal(attr, value);
        } else {
            super.appendId(attr, value);
        }
    }

    @Override
    public void setInt(String attr, int value) throws DfException {
        if (isInternalAttribute(attr)) {
            setIntInternal(attr, value);
        } else {
            super.setInt(attr, value);
        }
    }

    @Override
    public void appendInt(String attr, int value) throws DfException {
        if (isInternalAttribute(attr)) {
            appendIntInternal(attr, value);
        } else {
            super.appendInt(attr, value);
        }
    }

    protected final void setStringInternal(String attr, String value)
        throws DfException {
        try {
            Method method = ReflectionUtils.getMethod(DfTypedObject.class,
                    "setStringInternal", String.class, String.class);
            method.invoke(getImp(), attr, value);
        } catch (IllegalAccessException ex) {
            throw new DfException(ex);
        } catch (InvocationTargetException ex) {
            throw new DfException(ex);
        }
    }

    protected final void appendStringInternal(String attr, String value)
        throws DfException {
        try {
            Method method = ReflectionUtils.getMethod(DfTypedObject.class,
                    "appendStringInternal", String.class, String.class);
            method.invoke(getImp(), attr, value);
        } catch (IllegalAccessException ex) {
            throw new DfException(ex);
        } catch (InvocationTargetException ex) {
            throw new DfException(ex);
        }
    }

    protected final void setTimeInternal(String attr, IDfTime value)
        throws DfException {
        try {
            Method method = ReflectionUtils.getMethod(DfTypedObject.class,
                    "setTimeInternal", String.class, IDfTime.class);
            method.invoke(getImp(), attr, value);
        } catch (IllegalAccessException ex) {
            throw new DfException(ex);
        } catch (InvocationTargetException ex) {
            throw new DfException(ex);
        }
    }

    protected final void appendTimeInternal(String attr, IDfTime value)
        throws DfException {
        try {
            Method method = ReflectionUtils.getMethod(DfTypedObject.class,
                    "appendTimeInternal", String.class, IDfTime.class);
            method.invoke(getImp(), attr, value);
        } catch (IllegalAccessException ex) {
            throw new DfException(ex);
        } catch (InvocationTargetException ex) {
            throw new DfException(ex);
        }
    }

    protected final void setIdInternal(String attr, IDfId value)
        throws DfException {
        try {
            Method method = ReflectionUtils.getMethod(DfTypedObject.class,
                    "setIdInternal", String.class, IDfId.class);
            method.invoke(getImp(), attr, value);
        } catch (IllegalAccessException ex) {
            throw new DfException(ex);
        } catch (InvocationTargetException ex) {
            throw new DfException(ex);
        }
    }

    protected final void appendIdInternal(String attr, IDfId value)
        throws DfException {
        try {
            Method method = ReflectionUtils.getMethod(DfTypedObject.class,
                    "appendIdInternal", String.class, IDfId.class);
            method.invoke(getImp(), attr, value);
        } catch (IllegalAccessException ex) {
            throw new DfException(ex);
        } catch (InvocationTargetException ex) {
            throw new DfException(ex);
        }
    }

    protected final void setIntInternal(String attr, int value)
        throws DfException {
        try {
            Method method = ReflectionUtils.getMethod(DfTypedObject.class,
                    "setIntInternal", String.class, int.class);
            method.invoke(getImp(), attr, value);
        } catch (IllegalAccessException ex) {
            throw new DfException(ex);
        } catch (InvocationTargetException ex) {
            throw new DfException(ex);
        }
    }

    protected final void appendIntInternal(String attr, int value)
        throws DfException {
        try {
            Method method = ReflectionUtils.getMethod(DfTypedObject.class,
                    "appendIntInternal", String.class, int.class);
            method.invoke(getImp(), attr, value);
        } catch (IllegalAccessException ex) {
            throw new DfException(ex);
        } catch (InvocationTargetException ex) {
            throw new DfException(ex);
        }
    }

    protected final void setDoubleInternal(String attr, double value)
        throws DfException {
        try {
            Method method = ReflectionUtils.getMethod(DfTypedObject.class,
                    "setDoubleInternal", String.class, double.class);
            method.invoke(getImp(), attr, value);
        } catch (IllegalAccessException ex) {
            throw new DfException(ex);
        } catch (InvocationTargetException ex) {
            throw new DfException(ex);
        }
    }

    protected final void appendDoubleInternal(String attr, double value)
        throws DfException {
        try {
            Method method = ReflectionUtils.getMethod(DfTypedObject.class,
                    "appendDoubleInternal", String.class, double.class);
            method.invoke(getImp(), attr, value);
        } catch (IllegalAccessException ex) {
            throw new DfException(ex);
        } catch (InvocationTargetException ex) {
            throw new DfException(ex);
        }
    }

    protected final void setBooleanInternal(String attr, boolean value)
        throws DfException {
        try {
            Method method = ReflectionUtils.getMethod(DfTypedObject.class,
                    "setBooleanInternal", String.class, boolean.class);
            method.invoke(getImp(), attr, value);
        } catch (IllegalAccessException ex) {
            throw new DfException(ex);
        } catch (InvocationTargetException ex) {
            throw new DfException(ex);
        }
    }

    protected final void appendBooleanInternal(String attr, boolean value)
        throws DfException {
        try {
            Method method = ReflectionUtils.getMethod(DfTypedObject.class,
                    "appendBooleanInternal", String.class, boolean.class);
            method.invoke(getImp(), attr, value);
        } catch (IllegalAccessException ex) {
            throw new DfException(ex);
        } catch (InvocationTargetException ex) {
            throw new DfException(ex);
        }
    }

    protected final void removeAllInternal(String attr) throws DfException {
        try {
            Method method = ReflectionUtils.getMethod(DfTypedObject.class,
                    "removeAllInternal", String.class);
            method.invoke(getImp(), attr);
        } catch (IllegalAccessException ex) {
            throw new DfException(ex);
        } catch (InvocationTargetException ex) {
            throw new DfException(ex);
        }
    }

    private boolean isInternalAttribute(String attributeName) {
        return attributeName.startsWith("i_") || attributeName.startsWith("r_");
    }

    private Object getImp() {
        return ((IPersistentObject) getWrappedObject()).getProxyHandler()
                .____getImp____();
    }

}
