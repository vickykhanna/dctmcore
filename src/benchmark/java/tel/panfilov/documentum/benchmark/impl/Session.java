package tel.panfilov.documentum.benchmark.impl;

import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.common.DfException;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class Session extends AbstractDFC {

    protected IDfSessionManager _sessionManager;

    public Session(String docbase, String userName, String password) {
        super(docbase, userName, password);
    }

    @Override
    public void doSetup() {
        try {
            _sessionManager = makeSessionManager();
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void doOp() {
        IDfSession session = null;
        try {
            session = _sessionManager.getSession(_docbase);
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (session != null) {
                _sessionManager.release(session);
            }
        }
    }

    @Override
    public void doRelease() {
        _sessionManager.clearIdentities();
    }

}
