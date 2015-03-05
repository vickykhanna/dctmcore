package tel.panfilov.documentum.benchmark.impl;

import java.io.IOException;

import com.documentum.fc.client.IDfEnumeration;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class ObjectBulkFetchII extends ObjectBulkFetch {

    private String _attrs;

    private IDfEnumeration _enumeration;

    public ObjectBulkFetchII(String docbase, String userName, String password) {
        super(docbase, userName, password);
    }

    @Override
    public void doOp() {
        try {
            if (_enumeration == null) {
                renewCollection(_session);
            }
            if (!_enumeration.hasMoreElements()) {
                renewCollection(_session);
            }
            _enumeration.nextElement();
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void renewCollection(IDfSession session) throws DfException,
        IOException {
        _enumeration = _session.getObjectsByQuery(getProjection() + " WHERE "
                + getRestriction() + getOrderBy(), "dm_document");
    }

}
