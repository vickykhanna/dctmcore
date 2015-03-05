package tel.panfilov.documentum.utils.workflow;

import java.text.MessageFormat;

import com.documentum.fc.client.IDfGroup;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfUser;
import com.documentum.fc.client.IDfWorkflow;
import com.documentum.fc.common.DfException;

import tel.panfilov.documentum.utils.ClientXUtils;
import tel.panfilov.documentum.utils.CoreUtils;
import tel.panfilov.documentum.utils.session.SessionUtils;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public final class WorkflowUtils {

    public static final String RETRIEVE_WORKFLOW_QUERY = "dm_workflow where r_object_id=''{0}''";

    private WorkflowUtils() {
        super();
    }

    public static IDfWorkflow getWorkflow(IDfSession session, String workflowId)
        throws DfException {
        if (ClientXUtils.isNotObjectId(workflowId)) {
            return null;
        }
        return (IDfWorkflow) session.getObjectByQualification(MessageFormat
                .format(RETRIEVE_WORKFLOW_QUERY, workflowId));
    }

    public static boolean canDeleteWorkflow(IDfSession session,
            String workflowId) throws DfException {
        return canDeleteWorkflow(session, getWorkflow(session, workflowId));
    }

    public static boolean canDeleteWorkflow(IDfSession session,
            IDfWorkflow workflow) throws DfException {
        if (workflow == null) {
            return false;
        }

        if (workflow.getRuntimeState() != IDfWorkflow.DF_ACT_STATE_DORMANT
                && workflow.getRuntimeState() != IDfWorkflow.DF_WF_STATE_TERMINATED) {
            return false;
        }
        return canManageWorkflow(session, workflow);
    }

    public static boolean canResumeWorkflow(IDfSession session,
            String workflowId) throws DfException {
        return canResumeWorkflow(session, getWorkflow(session, workflowId));
    }

    public static boolean canResumeWorkflow(IDfSession session,
            IDfWorkflow workflow) throws DfException {
        if (workflow == null) {
            return false;
        }

        if (workflow.getRuntimeState() != IDfWorkflow.DF_WF_STATE_HALTED) {
            return false;
        }
        return canManageWorkflow(session, workflow);
    }

    public static boolean canHaltWorkflow(IDfSession session, String workflowId)
        throws DfException {
        return canHaltWorkflow(session, getWorkflow(session, workflowId));
    }

    public static boolean canHaltWorkflow(IDfSession session,
            IDfWorkflow workflow) throws DfException {
        if (workflow == null) {
            return false;
        }

        if (workflow.getRuntimeState() != IDfWorkflow.DF_WF_STATE_RUNNING) {
            return false;
        }
        return canManageWorkflow(session, workflow);
    }

    public static boolean canAbortWorkflow(IDfSession session, String workflowId)
        throws DfException {
        return canAbortWorkflow(session, getWorkflow(session, workflowId));
    }

    public static boolean canAbortWorkflow(IDfSession session,
            IDfWorkflow workflow) throws DfException {
        if (workflow == null) {
            return false;
        }

        if (workflow.getRuntimeState() != IDfWorkflow.DF_WF_STATE_RUNNING
                && workflow.getRuntimeState() != IDfWorkflow.DF_WF_STATE_HALTED) {
            return false;
        }
        return canManageWorkflow(session, workflow);
    }

    public static boolean canManageWorkflow(IDfSession session,
            IDfWorkflow workflow) throws DfException {

        if (SessionUtils.isSuperUser(session)
                || SessionUtils.isSysAdmin(session)) {
            return true;
        }

        String supervisor = workflow.getSupervisorName();

        if (CoreUtils.isEmpty(supervisor)) {
            return false;
        }

        IDfUser supervisorUser = session.getUser(supervisor);

        if (supervisorUser == null) {
            return false;
        }

        if (supervisorUser.isGroup()) {
            IDfGroup supervisorGroup = session.getGroup(supervisor);
            return supervisorGroup.isUserInGroup(session.getLoginUserName());
        } else {
            return supervisor.equals(session.getLoginUserName());
        }
    }

    public static void halt(IDfSession session, String objectId)
        throws DfException {
        IDfWorkflow workflow = getWorkflow(session, objectId);

        if (workflow == null) {
            return;
        }

        workflow.fetch(null);
        workflow.haltAll();
    }

    public static void resume(IDfSession session, String objectId)
        throws DfException {
        IDfWorkflow workflow = getWorkflow(session, objectId);
        if (workflow == null) {
            return;
        }
        workflow.fetch(null);
        workflow.resumeAll();
    }

    public static void abort(IDfSession session, String objectId)
        throws DfException {
        IDfWorkflow workflow = getWorkflow(session, objectId);
        if (workflow == null) {
            return;
        }
        workflow.fetch(null);
        workflow.abort();
    }

    public static void delete(IDfSession session, String objectId)
        throws DfException {
        IDfWorkflow workflow = getWorkflow(session, objectId);
        if (workflow == null) {
            return;
        }
        workflow.fetch(null);
        workflow.destroy();
    }

}
