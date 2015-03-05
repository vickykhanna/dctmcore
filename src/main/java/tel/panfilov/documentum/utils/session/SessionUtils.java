package tel.panfilov.documentum.utils.session;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Set;

import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.client.impl.bof.security.RoleRequest;
import com.documentum.fc.client.impl.bof.security.RoleRequestManager;
import com.documentum.fc.common.DfException;

import tel.panfilov.documentum.utils.CoreUtils;
import tel.panfilov.documentum.utils.query.QueryHelper;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public final class SessionUtils {

    public static final String SUPERUSERS_DYNAMIC_ROLE = "dm_superusers_dynamic";

    public static final String SUPERUSERS_ROLE = "dm_superusers";

    public static final String SYSADMIN_ROLE = "dm_sysadmin";

    public static final String R_INSTALL_OWNER_ATTR = "r_install_owner";

    public static final String DM_WORLD = "dm_world";

    private static final String USER_IN_NON_DYNAMIC_GROUP_QUERY = "select gr1.i_nondyn_supergroups_names as gr "
            + " from dm_group gr1, dm_group gr2 "
            + " where gr1.r_object_id=gr2.r_object_id "
            + " and gr2.users_names = ''{0}'' "
            + " and gr1.i_nondyn_supergroups_names = ''{1}'' "
            + " enable(row_based) ";

    private static final String GROUP_IN_NON_DYNAMIC_GROUP_QUERY = "select gr1.i_nondyn_supergroups_names as gr "
            + " from dm_group gr1, dm_group gr2 "
            + " where gr1.r_object_id=gr2.r_object_id "
            + " and gr2.groups_names = ''{0}'' "
            + " and gr1.i_nondyn_supergroups_names = ''{1}'' "
            + " enable(row_based) ";

    private SessionUtils() {
        super();
    }

    public static boolean beginTransactionIfNotActive(IDfSession session)
        throws DfException {
        boolean transactionActive = session.isTransactionActive();
        if (!transactionActive) {
            session.beginTrans();
        }
        return !transactionActive;
    }

    public static boolean beginTransactionIfNotActive(
            IDfSessionManager sessionManager) throws DfException {
        boolean transactionActive = sessionManager.isTransactionActive();
        if (!transactionActive) {
            sessionManager.beginTransaction();
        }
        return !transactionActive;
    }

    public static boolean isSysAdmin(IDfSession session) throws DfException {
        return isSysAdmin(session, null);
    }

    public static boolean isSysAdmin(IDfSession session, String userName)
        throws DfException {
        return isSuperUser(session, userName)
                || session.getUser(userName).isSystemAdmin()
                || isInGroup(session, SYSADMIN_ROLE, userName);
    }

    public static boolean isSuperUser(IDfSession session) throws DfException {
        return isSuperUser(session, null);
    }

    public static boolean isSuperUser(IDfSession session, String userName)
        throws DfException {
        return session.getUser(userName).isSuperUser() || userName == null
                && isDynamicGroupEnabled(session, SUPERUSERS_DYNAMIC_ROLE)
                || isDocbaseOwner(session, userName)
                || isInstallationOwner(session, userName)
                || isInGroup(session, SUPERUSERS_ROLE, userName)
                || userName == null
                && isRoleRequested(session, SUPERUSERS_DYNAMIC_ROLE);
    }

    public static boolean isRoleRequested(IDfSession session, String roleName)
        throws DfException {
        Collection<RoleRequest> requests = RoleRequestManager.getInstance()
                .getRoleRequests();
        if (requests == null || requests.isEmpty()) {
            return false;
        }
        String thisDocbaseName = session.getDocbaseName();
        for (RoleRequest request : requests) {
            String requestDocbaseName = request.getRoleSpec().getDocbaseName();
            if (requestDocbaseName != null
                    && !requestDocbaseName.equals(thisDocbaseName)) {
                continue;
            }
            if (request.getRoleSpec().getRoleName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInGroup(IDfSession session, String groupName,
            String userName) throws DfException {
        return isInNonDynamicGroup(session, groupName, userName)
                || userName == null
                && isAnyDynamicGroupInGroup(session, groupName);
    }

    public static boolean isAnyDynamicGroupInGroup(IDfSession session,
            String groupName) throws DfException {
        if (CoreUtils.isEmpty(groupName)) {
            throw new IllegalArgumentException("Group Name is empty");
        }
        for (int i = 0, n = session.getDynamicGroupCount(); i < n; i++) {
            Set<String> groups = QueryHelper.readQueryAsStringSet(session,
                    MessageFormat.format(GROUP_IN_NON_DYNAMIC_GROUP_QUERY,
                            session.getDynamicGroup(i), groupName), "gr");
            if (groups != null && groups.contains(groupName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInNonDynamicGroup(IDfSession session,
            String groupName, String user) throws DfException {
        if (CoreUtils.isEmpty(groupName)) {
            throw new IllegalArgumentException("Group Name is empty");
        }

        String userName = user;
        if (userName == null) {
            userName = session.getLoginUserName();
        }

        Set<String> nonDynamicGroups = QueryHelper.readQueryAsStringSet(
                session, MessageFormat.format(USER_IN_NON_DYNAMIC_GROUP_QUERY,
                        userName, groupName), "gr");
        Set<String> nonDynamicWorldGroups = QueryHelper.readQueryAsStringSet(
                session, MessageFormat.format(GROUP_IN_NON_DYNAMIC_GROUP_QUERY,
                        DM_WORLD, groupName), "gr");
        return nonDynamicGroups != null && nonDynamicGroups.contains(groupName)
                || nonDynamicWorldGroups != null
                && nonDynamicWorldGroups.contains(groupName);
    }

    public static boolean isInstallationOwner(IDfSession session,
            String userName) throws DfException {
        if (userName == null) {
            return session.getServerConfig().getString(R_INSTALL_OWNER_ATTR)
                    .equals(session.getLoginUserName());
        }
        return session.getServerConfig().getString(R_INSTALL_OWNER_ATTR)
                .equals(userName);
    }

    public static boolean isDocbaseOwner(IDfSession session, String userName)
        throws DfException {
        if (userName == null) {
            return session.getDocbaseOwnerName().equals(
                    session.getLoginUserName());
        }
        return session.getDocbaseOwnerName().equals(userName);
    }

    public static boolean isDynamicGroupEnabled(IDfSession session,
            String groupName) throws DfException {
        if (CoreUtils.isEmpty(groupName)) {
            throw new IllegalArgumentException("Group Name is empty");
        }
        for (int i = 0, n = session.getDynamicGroupCount(); i < n; i++) {
            if (session.getDynamicGroup(i).equals(groupName)) {
                return true;
            }
        }
        return false;
    }

}
