package tel.panfilov.documentum.benchmark.impl;

import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class SessionII extends Session {

    public SessionII(String docbase, String userName, String password) {
        super(docbase, userName, password);
    }

    @Override
    public void doOp() {
        IDfSession session = null;
        try {
            session = _sessionManager.newSession(_docbase);
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (session != null) {
                _sessionManager.release(session);
            }
        }
    }

}
