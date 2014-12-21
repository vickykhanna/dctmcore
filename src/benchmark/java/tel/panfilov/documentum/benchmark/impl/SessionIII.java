package tel.panfilov.documentum.benchmark.impl;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class SessionIII extends SessionII {

	public SessionIII(String docbase, String userName, String password) {
		super(docbase, userName, password);
	}

	@Override
	public void doOp() {
		super.doOp();
		_sessionManager.flushSessions();
	}

}
