package tel.panfilov.documentum.utils;

import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfModuleDescriptor;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.client.IDfSessionManagerEventListener;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.DfLogger;
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfList;
import com.documentum.fc.common.IDfLoginInfo;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public final class ClientXUtils {

    private ClientXUtils() {
        super();
    }

    public static IDfId getId(String objectId) {
        return new DfClientX().getId(objectId);
    }

    public static IDfClientX getClientX() throws DfException {
        return new DfClientX();
    }

    public static IDfClient getClient() throws DfException {
        return getClientX().getLocalClient();
    }

    public static IDfSessionManager getSessionManager() throws DfException {
        return getClient().newSessionManager();
    }

    public static boolean isObjectId(String value) {
        return CoreUtils.isNotEmpty(value) && DfId.isObjectId(value);
    }

    public static boolean isObjectId(IDfId value) {
        return value != null && !value.isNull() && value.isObjectId();
    }

    public static boolean isNotObjectId(String value) {
        return CoreUtils.isEmpty(value) || !DfId.isObjectId(value);
    }

    public static boolean isNotObjectId(IDfId value) {
        return value == null || value.isNull() || !value.isObjectId();
    }

    public static IDfLoginInfo getLoginInfo() throws DfException {
        return new DfClientX().getLoginInfo();
    }

    public static IDfSessionManager newSessionManager(IDfSession session)
        throws DfException {
        return newSessionManager(session, null);
    }

    public static IDfSessionManager newSessionManager(IDfSession session,
            IDfSessionManagerEventListener listener) throws DfException {
        IDfLoginInfo loginInfo = session.getLoginInfo();
        int timeout = session.getServerConfig().getInt(
                "max_login_ticket_timeout");
        loginInfo.setPassword(session.getLoginTicketEx(null, "docbase",
                timeout, false, session.getDocbaseName()));
        IDfSessionManager sessionManager = getSessionManager();
        sessionManager.setIdentity(session.getDocbaseName(), loginInfo);
        if (listener != null) {
            sessionManager.setListener(listener);
        }
        return sessionManager;
    }

    public static IDfSessionManager getSessionManager(String docbase,
            String userName, String password) throws DfException {
        return getSessionManager(docbase, userName, password, null, null);
    }

    public static IDfSessionManager getSessionManager(String docbase,
            String userName, String password,
            IDfSessionManagerEventListener listener) throws DfException {
        return getSessionManager(docbase, userName, password, null, listener);
    }

    public static IDfSessionManager getSessionManager(String docbase,
            String userName, String password, String domain) throws DfException {
        return getSessionManager(docbase, userName, password, domain, null);
    }

    public static IDfSessionManager getSessionManager(String docbase,
            String userName, String password, String domain,
            IDfSessionManagerEventListener listener) throws DfException {
        IDfSessionManager sessionManager = getSessionManager();
        sessionManager.setIdentity(docbase,
                getLoginInfo(userName, password, domain));
        if (listener != null) {
            sessionManager.setListener(listener);
        }
        return sessionManager;
    }

    public static IDfLoginInfo getLoginInfo(String user, String password)
        throws DfException {
        return getLoginInfo(user, password, null);
    }

    public static IDfLoginInfo getLoginInfo(String user, String password,
            String domain) throws DfException {
        IDfLoginInfo loginInfo = getLoginInfo();
        loginInfo.setUser(user);
        loginInfo.setPassword(password);
        loginInfo.setDomain(domain);
        return loginInfo;
    }

    public static IDfQuery getQuery(String dql) throws DfException {
        IDfQuery query = new DfClientX().getQuery();
        query.setDQL(dql);
        return query;
    }

    public static IDfList getList() throws DfException {
        return getClientX().getList();
    }

    public static String getDocbaseNameFromId(IDfId objectId)
        throws DfException {
        return getClient().getDocbaseNameFromId(objectId);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getService(Class<T> cls, String serviceName,
            IDfSessionManager sMgr) throws DfException {
        T service = (T) getClient().newService(serviceName, sMgr);
        DfLogger.debug(ClientXUtils.class, "Service obtained: " + service,
                null, null);
        return service;
    }

    public static <T> T getService(Class<T> cls, IDfSessionManager sMgr)
        throws DfException {
        return getService(cls, cls.getName(), sMgr);
    }

    public static <T> T getModule(Class<T> cls, String name, IDfSession session)
        throws DfException {
        return getModule(cls, name, session.getSessionManager(),
                session.getDocbaseName());
    }

    public static <T> T getModule(Class<T> cls, IDfSession session)
        throws DfException {
        return getModule(cls, cls.getName(), session);
    }

    public static <T> T getModule(Class<T> cls, IDfSessionManager sMgr,
            String docbase) throws DfException {
        return getModule(cls, cls.getName(), sMgr, docbase);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getModule(Class<T> cls, String name,
            IDfSessionManager sMgr, String docbase) throws DfException {
        T module = (T) getClient().newModule(docbase, name, sMgr);
        DfLogger.debug(ClientXUtils.class, "Module obtained: " + module, null,
                null);
        return module;
    }

    public static IDfModuleDescriptor getModuleDescriptor(String moduleName,
            IDfSession session) throws DfException {
        IDfModuleDescriptor descriptor;
        try {
            descriptor = session.getModuleRegistry().getModuleDescriptor(
                    moduleName);
        } catch (DfException e) {
            descriptor = getClient().getModuleRegistry().getServiceDescriptor(
                    moduleName);
        }
        return descriptor;
    }

}
