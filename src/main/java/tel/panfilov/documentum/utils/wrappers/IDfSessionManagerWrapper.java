package tel.panfilov.documentum.utils.wrappers;

import com.documentum.fc.client.DfAuthenticationException;
import com.documentum.fc.client.DfIdentityException;
import com.documentum.fc.client.DfPrincipalException;
import com.documentum.fc.client.DfServiceException;
import com.documentum.fc.client.IDfScopeManager;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.client.IDfSessionManagerConfig;
import com.documentum.fc.client.IDfSessionManagerEventListener;
import com.documentum.fc.client.IDfSessionManagerStatistics;
import com.documentum.fc.common.IDfLoginInfo;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class IDfSessionManagerWrapper implements IDfSessionManager {

    private final IDfSessionManager _wrappedManager;

    public IDfSessionManagerWrapper(IDfSessionManager wrappedManager) {
        _wrappedManager = wrappedManager;
    }

    @Override
    public void setPrincipalName(String s) {
        _wrappedManager.setPrincipalName(s);
    }

    @Override
    public String getPrincipalName() {
        return _wrappedManager.getPrincipalName();
    }

    @Override
    public void setLocale(String s) {
        _wrappedManager.setLocale(s);
    }

    @Override
    public String getLocale() {
        return _wrappedManager.getLocale();
    }

    @Override
    public void setIdentity(String docbase, IDfLoginInfo logininfo)
        throws DfServiceException {
        _wrappedManager.setIdentity(docbase, logininfo);
    }

    @Override
    public IDfLoginInfo getIdentity(String docbase) {
        return _wrappedManager.getIdentity(docbase);
    }

    @Override
    public boolean hasIdentity(String docbase) {
        return _wrappedManager.hasIdentity(docbase);
    }

    @Override
    public void clearIdentity(String docbase) {
        _wrappedManager.clearIdentity(docbase);
    }

    @Override
    public void clearIdentities() {
        _wrappedManager.clearIdentities();
    }

    @Override
    public void flushSessions() {
        _wrappedManager.flushSessions();
    }

    @Override
    public void authenticate(String s) throws DfIdentityException,
        DfAuthenticationException, DfPrincipalException, DfServiceException {
        _wrappedManager.authenticate(s);
    }

    @Override
    public IDfSession getSession(String docbase) throws DfIdentityException,
        DfAuthenticationException, DfPrincipalException, DfServiceException {
        return _wrappedManager.getSession(docbase);
    }

    @Override
    public IDfSession newSession(String docbase) throws DfIdentityException,
        DfAuthenticationException, DfPrincipalException, DfServiceException {
        return _wrappedManager.newSession(docbase);
    }

    @Override
    public void release(IDfSession session) {
        _wrappedManager.release(session);
    }

    @Override
    public void beginTransaction() throws DfServiceException {
        _wrappedManager.beginTransaction();
    }

    @Override
    public void commitTransaction() throws DfServiceException {
        _wrappedManager.commitTransaction();
    }

    @Override
    public void abortTransaction() throws DfServiceException {
        _wrappedManager.abortTransaction();
    }

    @Override
    public boolean isTransactionActive() {
        return _wrappedManager.isTransactionActive();
    }

    @Override
    public void setTransactionRollbackOnly() {
        _wrappedManager.setTransactionRollbackOnly();
    }

    @Override
    public boolean getTransactionRollbackOnly() {
        return _wrappedManager.getTransactionRollbackOnly();
    }

    @Override
    public void beginClientControl() {
        _wrappedManager.beginClientControl();
    }

    @Override
    public void endClientControl() throws DfServiceException {
        _wrappedManager.endClientControl();
    }

    @Override
    public IDfSessionManagerStatistics getStatistics() {
        return _wrappedManager.getStatistics();
    }

    @Override
    public IDfSessionManagerConfig getConfig() {
        return _wrappedManager.getConfig();
    }

    @Override
    public IDfSessionManagerEventListener setListener(
            IDfSessionManagerEventListener idfsessionmanagereventlistener) {
        return _wrappedManager.setListener(idfsessionmanagereventlistener);
    }

    @Override
    public IDfScopeManager getScopeManager() {
        return _wrappedManager.getScopeManager();
    }

}
