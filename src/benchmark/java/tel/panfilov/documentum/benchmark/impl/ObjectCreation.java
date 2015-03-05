package tel.panfilov.documentum.benchmark.impl;

import com.documentum.fc.client.IDfDocument;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.common.DfException;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class ObjectCreation extends AbstractDFC {

    protected IDfSession _session;

    public ObjectCreation(String docbase, String userName, String password) {
        super(docbase, userName, password);
    }

    @Override
    public void doSetup() {
        try {
            IDfSessionManager sessionManager = makeSessionManager();
            _session = sessionManager.newSession(_docbase);
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void doOp() {
        try {
            IDfDocument document = (IDfDocument) _session
                    .newObject("dm_document");
            document.link("/Temp");
            document.save();
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void doRelease() {
        if (_session != null) {
            _session.getSessionManager().release(_session);
        }
    }

}
