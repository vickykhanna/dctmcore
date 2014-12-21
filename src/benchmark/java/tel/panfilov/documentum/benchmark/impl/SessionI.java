package tel.panfilov.documentum.benchmark.impl;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class SessionI extends Session {

    public SessionI(String docbase, String userName, String password) {
        super(docbase, userName, password);
    }

    @Override
    public void doOp() {
        super.doOp();
        _sessionManager.flushSessions();
    }

}
