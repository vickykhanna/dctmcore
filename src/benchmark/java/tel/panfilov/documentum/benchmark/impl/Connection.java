package tel.panfilov.documentum.benchmark.impl;

import com.documentum.fc.client.impl.connection.docbase.ClientInfo;
import com.documentum.fc.client.impl.connection.docbase.DocbaseConnectionFactory;
import com.documentum.fc.client.impl.connection.docbase.IDocbaseConnection;
import com.documentum.fc.client.impl.docbase.IDocbaseSpec;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfLoginInfo;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class Connection extends AbstractDFC {

	protected DocbaseConnectionFactory _connectionFactory;
	protected IDocbaseSpec _docbaseSpec;
	protected IDfLoginInfo _loginInfo;
	protected ClientInfo _clientInfo;

	public Connection(String docbase, String userName, String password) {
		super(docbase, userName, password);
	}

	@Override
	public void doSetup() {
		_connectionFactory = new DocbaseConnectionFactory();
		_docbaseSpec = makeDocbaseSpec();
		_loginInfo = makeDfLoginInfo();
		_clientInfo = makeClientInfo();
	}

	@Override
	public void doOp() {
		IDocbaseConnection connection = null;
		try {
			connection = _connectionFactory.newDocbaseConnection(_docbaseSpec, _loginInfo, _clientInfo);
		} catch (DfException ex) {
			throw new RuntimeException(ex);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Override
	public void doRelease() {

	}

}
