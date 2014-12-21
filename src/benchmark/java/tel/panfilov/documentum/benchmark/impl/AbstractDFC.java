package tel.panfilov.documentum.benchmark.impl;

import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import com.documentum.com.DfClientX;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.client.impl.connection.docbase.ClientInfo;
import com.documentum.fc.client.impl.docbase.DocbaseSpec;
import com.documentum.fc.client.impl.docbase.IDocbaseSpec;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfLoginInfo;
import com.documentum.fc.common.IDfLoginInfo;

import tel.panfilov.documentum.benchmark.IBenchmark;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public abstract class AbstractDFC implements IBenchmark {

	public static final String DATE_FORMAT = "yyyy/MM/dd";

	protected final String _docbase;
	protected final String _userName;
	protected final String _password;
	protected final IDfClient _dfClient;

	public AbstractDFC(String docbase, String userName, String password) {
		try {
			_docbase = docbase;
			_userName = userName;
			_password = password;
			_dfClient = new DfClientX().getLocalClient();
		} catch (DfException ex) {
			throw new RuntimeException(ex);
		}
	}

	protected final IDfLoginInfo makeDfLoginInfo() {
		return new DfLoginInfo(_userName, _password);
	}

	protected final IDocbaseSpec makeDocbaseSpec() {
		return new DocbaseSpec(_docbase);
	}

	protected final IDfSessionManager makeSessionManager(IDfLoginInfo loginInfo) throws DfException {
		IDfSessionManager sessionManager = _dfClient.newSessionManager();
		sessionManager.setIdentity(_docbase, loginInfo);
		return sessionManager;
	}

	protected final IDfSessionManager makeSessionManager() throws DfException {
		return makeSessionManager(makeDfLoginInfo());
	}

	protected final ClientInfo makeClientInfo() {
		return new ClientInfo(Locale.getDefault(), TimeZone.getDefault(), DATE_FORMAT, new ArrayList<String>(),
				new ArrayList<String>(), new ArrayList<String>());
	}

}
