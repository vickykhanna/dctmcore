package tel.panfilov.documentum.benchmark.impl;

import com.documentum.fc.client.impl.connection.docbase.IDocbaseConnection;
import com.documentum.fc.common.DfException;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class Authenticate extends Connection {

	protected IDocbaseConnection _connection;

	public Authenticate(String docbase, String userName, String password) {
		super(docbase, userName, password);
	}

	@Override
	public void doSetup() {
		try {
			super.doSetup();
			_connection = _connectionFactory.newDocbaseConnection(_docbaseSpec, _loginInfo, _clientInfo);
		} catch (DfException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void doOp() {
		try {
			_connection.authenticate(_loginInfo, _clientInfo, true, false, true);
		} catch (DfException ex) {
			throw new RuntimeException(ex);
		}
	}

}
