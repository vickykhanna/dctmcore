package tel.panfilov.documentum.utils.wrappers;

import com.documentum.fc.client.IDfActivity;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfPackage;
import com.documentum.fc.client.IDfWorkflowAttachment;
import com.documentum.fc.client.IDfWorkitem;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfList;
import com.documentum.fc.common.IDfTime;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class IDfWorkitemWrapper<T extends IDfWorkitem> extends
        IDfPersistentObjectWrapper<T> implements IDfWorkitem {

    public IDfWorkitemWrapper(T wrappedObject) {
        super(wrappedObject);
    }

    public void acquire() throws DfException {
        getWrappedObject().acquire();
    }

    public IDfId addAttachment(String componentType, IDfId componentId)
        throws DfException {
        return getWrappedObject().addAttachment(componentType, componentId);
    }

    public IDfId addPackage(String packageName, String packageType,
            IDfList componentIds) throws DfException {
        return getWrappedObject().addPackage(packageName, packageType,
                componentIds);
    }

    public IDfId addPackage(String packageName, String packageType,
            IDfList componentIds, IDfList componentNames) throws DfException {
        return getWrappedObject().addPackage(packageName, packageType,
                componentIds, componentNames);
    }

    public IDfId addPackageEx(String packageName, String packageType,
            IDfList componentIds, IDfList componentNames, int skillLevel)
        throws DfException {
        return getWrappedObject().addPackageEx(packageName, packageType,
                componentIds, componentNames, skillLevel);
    }

    public IDfId addPackageEx(String packageName, String packageType,
            IDfList componentIds, int skillLevel) throws DfException {
        return getWrappedObject().addPackageEx(packageName, packageType,
                componentIds, skillLevel);
    }

    public void complete() throws DfException {
        getWrappedObject().complete();
    }

    public void completeEx(int returnValue, String execOSError,
            IDfId execResultId) throws DfException {
        getWrappedObject().completeEx(returnValue, execOSError, execResultId);
    }

    public void completeEx2(int returnValue, String execOSError,
            IDfId execResultId, int userTime, double userCost)
        throws DfException {
        getWrappedObject().completeEx2(returnValue, execOSError, execResultId,
                userTime, userCost);
    }

    public void delegateTask(String user) throws DfException {
        getWrappedObject().delegateTask(user);
    }

    public IDfId getActDefId() throws DfException {
        return getWrappedObject().getActDefId();
    }

    public IDfActivity getActivity() throws DfException {
        return getWrappedObject().getActivity();
    }

    public int getActSeqno() throws DfException {
        return getWrappedObject().getActSeqno();
    }

    public IDfCollection getAllPackages(String additionalAttributes)
        throws DfException {
        return getWrappedObject().getAllPackages(additionalAttributes);
    }

    public IDfWorkflowAttachment getAttachment(IDfId attachmentId)
        throws DfException {
        return getWrappedObject().getAttachment(attachmentId);
    }

    public IDfCollection getAttachments() throws DfException {
        return getWrappedObject().getAttachments();
    }

    public IDfId getAutoMethodId() throws DfException {
        return getWrappedObject().getAutoMethodId();
    }

    public IDfTime getCreationDate() throws DfException {
        return getWrappedObject().getCreationDate();
    }

    public IDfTime getDueDate() throws DfException {
        return getWrappedObject().getDueDate();
    }

    public String getExecOsError() throws DfException {
        return getWrappedObject().getExecOsError();
    }

    public IDfId getExecResultId() throws DfException {
        return getWrappedObject().getExecResultId();
    }

    public int getExecRetriedCount() throws DfException {
        return getWrappedObject().getExecRetriedCount();
    }

    public String getExtendedPerformer(int valueIndex) throws DfException {
        return getWrappedObject().getExtendedPerformer(valueIndex);
    }

    public int getExtendedPerformerCount() throws DfException {
        return getWrappedObject().getExtendedPerformerCount();
    }

    public IDfList getForwardActivities() throws DfException {
        return getWrappedObject().getForwardActivities();
    }

    public IDfTime getLaunchTimeout() throws DfException {
        return getWrappedObject().getLaunchTimeout();
    }

    public IDfCollection getMissingOutputPackages() throws DfException {
        return getWrappedObject().getMissingOutputPackages();
    }

    public IDfList getNextActivityNames() throws DfException {
        return getWrappedObject().getNextActivityNames();
    }

    public String getOutputPort(int valueIndex) throws DfException {
        return getWrappedObject().getOutputPort(valueIndex);
    }

    public int getOutputPortCount() throws DfException {
        return getWrappedObject().getOutputPortCount();
    }

    public IDfPackage getPackage(IDfId packageId) throws DfException {
        return getWrappedObject().getPackage(packageId);
    }

    public IDfCollection getPackages(String additionalAttributes)
        throws DfException {
        return getWrappedObject().getPackages(additionalAttributes);
    }

    public String getPerformerName() throws DfException {
        return getWrappedObject().getPerformerName();
    }

    public IDfList getPreviousActivityNames() throws DfException {
        return getWrappedObject().getPreviousActivityNames();
    }

    public int getPriority() throws DfException {
        return getWrappedObject().getPriority();
    }

    public IDfId getQueueItemId() throws DfException {
        return getWrappedObject().getQueueItemId();
    }

    public IDfList getRejectActivities() throws DfException {
        return getWrappedObject().getRejectActivities();
    }

    public int getReturnValue() throws DfException {
        return getWrappedObject().getReturnValue();
    }

    public int getRuntimeState() throws DfException {
        return getWrappedObject().getRuntimeState();
    }

    public int getSkillLevel() throws DfException {
        return getWrappedObject().getSkillLevel();
    }

    public IDfId getTargetTaskId() throws DfException {
        return getWrappedObject().getTargetTaskId();
    }

    public double getUserCost() throws DfException {
        return getWrappedObject().getUserCost();
    }

    public int getUserTime() throws DfException {
        return getWrappedObject().getUserTime();
    }

    public IDfId getWorkflowId() throws DfException {
        return getWrappedObject().getWorkflowId();
    }

    public boolean isDelegatable() throws DfException {
        return getWrappedObject().isDelegatable();
    }

    public boolean isExecLaunch() throws DfException {
        return getWrappedObject().isExecLaunch();
    }

    public boolean isExecTimeOut() throws DfException {
        return getWrappedObject().isExecTimeOut();
    }

    public boolean isManualExecution() throws DfException {
        return getWrappedObject().isManualExecution();
    }

    public boolean isManualTransition() throws DfException {
        return getWrappedObject().isManualTransition();
    }

    public boolean isRepeatable() throws DfException {
        return getWrappedObject().isRepeatable();
    }

    public boolean isSignOffRequired() throws DfException {
        return getWrappedObject().isSignOffRequired();
    }

    public void pause() throws DfException {
        getWrappedObject().pause();
    }

    public IDfId queue(String user, String eventType, int priority,
            boolean sendMail, IDfTime dueDate, String message)
        throws DfException {
        return getWrappedObject().queue(user, eventType, priority, sendMail,
                dueDate, message);
    }

    public void removeAttachment(IDfId attachmentId) throws DfException {
        getWrappedObject().removeAttachment(attachmentId);
    }

    public void removePackage(String packageName) throws DfException {
        getWrappedObject().removePackage(packageName);
    }

    public void repeat(IDfList list) throws DfException {
        getWrappedObject().repeat(list);
    }

    public void resume() throws DfException {
        getWrappedObject().resume();
    }

    public void setOutput(IDfList list) throws DfException {
        getWrappedObject().setOutput(list);
    }

    public void setOutputByActivities(IDfList actList) throws DfException {
        getWrappedObject().setOutputByActivities(actList);
    }

    public void setPackageSkillLevel(String packageName, int skillLevel)
        throws DfException {
        getWrappedObject().setPackageSkillLevel(packageName, skillLevel);
    }

    public void setPerformers(String actName, IDfList userGroupList)
        throws DfException {
        getWrappedObject().setPerformers(actName, userGroupList);
    }

    public void setPriority(int newPriority) throws DfException {
        getWrappedObject().setPriority(newPriority);
    }

}
