package tel.panfilov.documentum.benchmark.impl;

import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfLoginInfo;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class DarkHorse extends SessionII {

    public DarkHorse(String docbase, String userName, String password) {
        super(docbase, userName, password);
    }

    @Override
    public void doOp() {
        IDfSession session = null;
        IDfSessionManager sessionManager = null;
        try {
            IDfLoginInfo loginInfo = makeDfLoginInfo();
            loginInfo.setForceAuthentication(false);
            sessionManager = makeSessionManager(loginInfo);
            session = sessionManager.getSession(_docbase);
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (session != null) {
                sessionManager.release(session);
                sessionManager.flushSessions();
            }
        }
    }

}
