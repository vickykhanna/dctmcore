package tel.panfilov.documentum.utils.workflow;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.documentum.fc.client.IDfActivity;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfProcess;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.client.IDfWorkflow;
import com.documentum.fc.client.IDfWorkitem;
import com.documentum.fc.common.DfDocbaseConstants;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfList;

import tel.panfilov.documentum.utils.ClientXUtils;
import tel.panfilov.documentum.utils.CoreUtils;
import tel.panfilov.documentum.utils.query.IRowHandler;
import tel.panfilov.documentum.utils.query.QueryHelper;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public final class WorkItemUtils {

    public static final String RETRIEVE_WORKITEM_QUERY = "dmi_workitem where r_object_id=''{0}''";

    private WorkItemUtils() {
        super();
    }

    public static IDfWorkitem getWorkItem(IDfSession session, String workItemId)
        throws DfException {
        if (ClientXUtils.isNotObjectId(workItemId)) {
            return null;
        }
        return (IDfWorkitem) session.getObjectByQualification(MessageFormat
                .format(RETRIEVE_WORKITEM_QUERY, workItemId));
    }

    public static boolean isWorkitemActive(IDfWorkitem workitem)
        throws DfException {
        if (workitem == null) {
            return false;
        }

        IDfSession session = workitem.getSession();
        IDfWorkflow workflow = (IDfWorkflow) session.getObject(workitem
                .getWorkflowId());

        if (workflow.getRuntimeState() != IDfWorkflow.DF_WF_STATE_RUNNING) {
            return false;
        }

        if (workitem.getRuntimeState() == IDfWorkitem.DF_WI_STATE_FINISHED) {
            return false;
        }
        return true;
    }

    public static String getProcessName(IDfWorkitem workitem)
        throws DfException {
        if (workitem == null) {
            return null;
        }

        IDfSession session = workitem.getSession();
        IDfWorkflow workflow = (IDfWorkflow) session.getObject(workitem
                .getWorkflowId());

        if (workflow == null) {
            return null;
        }

        IDfSysObject process = (IDfSysObject) session.getObject(workflow
                .getProcessId());

        if (process == null) {
            return null;
        }

        return process.getObjectName();
    }

    public static boolean canHaltTask(int runtimeState) {
        return runtimeState == IDfWorkitem.DF_WI_STATE_DORMANT
                || runtimeState == IDfWorkitem.DF_WI_STATE_ACQUIRED;
    }

    public static boolean canResumeTask(int runtimeState) {
        return runtimeState == IDfWorkitem.DF_WI_STATE_PAUSED;
    }

    public static void haltTask(IDfSession session, String objectId)
        throws DfException {
        IDfWorkitem witem = getWorkItem(session, objectId);

        if (witem == null) {
            return;
        }

        witem.fetch(null);
        witem.pause();
    }

    public static void resumeTask(IDfSession session, String objectId)
        throws DfException {
        IDfWorkitem witem = getWorkItem(session, objectId);

        if (witem == null) {
            return;
        }

        witem.fetch(null);
        witem.resume();
    }

    public static IDfList convertActivitiesToPortNames(IDfWorkitem workitem,
            IDfList activities) throws DfException {
        IDfList result = ClientXUtils.getList();
        IDfSession session = workitem.getSession();
        IDfWorkflow workflow = (IDfWorkflow) session.getObject(workitem
                .getWorkflowId());
        IDfProcess process = (IDfProcess) session.getObject(workflow
                .getProcessId());
        int actPosition = process
                .findId("r_act_def_id", workitem.getActDefId());
        String actName = process.getActivityName(actPosition);

        if (CoreUtils.isEmpty(actName)) {
            return result;
        }

        Set<String> activityNames = new HashSet<String>();
        for (int i = 0, n = activities.getCount(); i < n; i++) {
            IDfActivity activity = (IDfActivity) activities.get(i);
            if (activity == null) {
                continue;
            }
            String activityName = activity.getObjectName();
            if (CoreUtils.isEmpty(activityName)) {
                continue;
            }
            activityNames.add(activityName);
        }

        for (int i = 0, n = process.getProcessLinkCount(); i < n; i++) {
            String srcActivity = process.getLinkSrcActivity(i);
            String dstActivity = process.getLinkDestActivity(i);
            String srcPort = process.getLinkSrcPort(i);

            if (!actName.equals(srcActivity)) {
                continue;
            }

            if (!activityNames.contains(dstActivity)) {
                continue;
            }

            result.appendString(srcPort);
        }

        return result;
    }

    public static List<IDfWorkitem> getNewWorkItems(final IDfWorkitem workitem)
        throws DfException {
        List<IDfWorkitem> workitems = getNewWorkItemsInternal(workitem);
        if (workitems == null || workitems.isEmpty()) {
            return workitems;
        }

        final IDfSession session = workitem.getSession();
        if (!session.isTransactionActive()) {
            return workitems;
        }
        List<String> ids = new ArrayList<String>();
        for (IDfWorkitem w : workitems) {
            ids.add(w.getObjectId().getId());
        }
        IDfSessionManager sessionManager = ClientXUtils
                .newSessionManager(session);
        IDfSession newSession = null;
        try {
            newSession = sessionManager.getSession(session.getDocbaseName());
            ids.removeAll(QueryHelper.readQueryAsStringSet(
                    newSession,
                    "SELECT r_object_id FROM dmi_workitem WHERE "
                            + CoreUtils.createInClause(
                                    DfDocbaseConstants.R_OBJECT_ID, ids),
                    DfDocbaseConstants.R_OBJECT_ID));
        } finally {
            if (newSession != null) {
                sessionManager.release(newSession);
            }
            sessionManager.clearIdentities();
        }
        for (Iterator<IDfWorkitem> workitemIterator = workitems.iterator(); workitemIterator
                .hasNext();) {
            IDfWorkitem w = workitemIterator.next();
            if (!ids.contains(w.getObjectId().getId())) {
                workitemIterator.remove();
            }
        }
        return workitems;
    }

    private static List<IDfWorkitem> getNewWorkItemsInternal(
            final IDfWorkitem workitem) throws DfException {
        final List<IDfWorkitem> workitems = new ArrayList<IDfWorkitem>();
        if (workitem == null) {
            return workitems;
        }
        List<String> nextActivityIds = new ArrayList<String>();
        IDfList nextActivities = workitem.getForwardActivities();
        for (int i = 0, n = nextActivities.getCount(); i < n; i++) {
            nextActivityIds.add(((IDfActivity) nextActivities.get(i))
                    .getObjectId().getId());
        }
        nextActivities = workitem.getRejectActivities();
        for (int i = 0, n = nextActivities.getCount(); i < n; i++) {
            nextActivityIds.add(((IDfActivity) nextActivities.get(i))
                    .getObjectId().getId());
        }

        if (nextActivityIds.isEmpty()) {
            return workitems;
        }

        final IDfSession session = workitem.getSession();
        QueryHelper.selectQuery(
                session,
                "SELECT r_object_id FROM dmi_workitem WHERE r_workflow_id='"
                        + workitem.getWorkflowId().getId()
                        + "' AND r_act_seqno > "
                        + workitem.getActSeqno()
                        + " AND "
                        + CoreUtils.createInClause("r_act_def_id",
                                nextActivityIds), new IRowHandler() {
                    @Override
                    public void handleRow(IDfCollection row, int position)
                        throws DfException {
                        workitems.add((IDfWorkitem) session.getObject(row
                                .getId("r_object_id")));
                    }
                });
        return workitems;
    }

    public static List<IDfWorkitem> getWorkItems(final IDfSession session,
            IDfId workflowId, int actSeqNo) throws DfException {
        final List<IDfWorkitem> workitems = new ArrayList<IDfWorkitem>();
        QueryHelper.selectQuery(session,
                "SELECT r_object_id FROM dmi_workitem WHERE r_workflow_id='"
                        + workflowId.getId() + "' AND r_act_seqno=" + actSeqNo,
                new IRowHandler() {
                    @Override
                    public void handleRow(IDfCollection row, int position)
                        throws DfException {
                        workitems.add((IDfWorkitem) session.getObject(row
                                .getId("r_object_id")));
                    }
                });
        return workitems;
    }

    public static List<IDfWorkitem> getConnectedWorkItems(IDfWorkitem workitem,
            boolean manualOnly) throws DfException {
        final List<IDfWorkitem> workitems = new ArrayList<IDfWorkitem>();
        if (workitem == null) {
            return workitems;
        }
        IDfSession session = workitem.getObjectSession();
        IDfWorkflow workflow = (IDfWorkflow) session.getObject(workitem
                .getWorkflowId());
        IDfProcess process = (IDfProcess) session.getObject(workflow
                .getProcessId());
        IDfList previousActivityNames = workitem.getPreviousActivityNames();
        for (int i = 0, n = previousActivityNames.getCount(); i < n; i++) {
            String activityName = previousActivityNames.getString(i);
            if (CoreUtils.isEmpty(activityName)) {
                continue;
            }
            IDfId activityId = process.getActivityIdByName(activityName);
            if (ClientXUtils.isNotObjectId(activityId)) {
                continue;
            }
            StringBuilder queryBuilder = new StringBuilder(
                    "dmi_workitem WHERE r_workflow_id='");
            queryBuilder.append(workflow.getObjectId().getId()).append("'");
            queryBuilder.append(" AND r_act_def_id='")
                    .append(activityId.getId()).append("'");
            queryBuilder.append(" AND r_runtime_state <> 2");
            if (manualOnly) {
                queryBuilder.append(" AND r_auto_method_id='")
                        .append(DfId.DF_NULLID_STR).append("'");
            }
            IDfWorkitem connectedWorkitem = (IDfWorkitem) session
                    .getObjectByQualification(queryBuilder.toString());
            if (connectedWorkitem != null) {
                workitems.add(connectedWorkitem);
            }
        }
        return workitems;
    }

}
