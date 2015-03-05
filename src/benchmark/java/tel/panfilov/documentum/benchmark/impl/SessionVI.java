package tel.panfilov.documentum.benchmark.impl;

import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.common.DfException;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class SessionVI extends SessionII {

    public SessionVI(String docbase, String userName, String password) {
        super(docbase, userName, password);
    }

    @Override
    public void doOp() {
        IDfSession session = null;
        IDfSessionManager sessionManager = null;
        try {
            sessionManager = makeSessionManager();
            session = sessionManager.newSession(_docbase);
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (session != null) {
                sessionManager.release(session);
            }
        }
    }

}
