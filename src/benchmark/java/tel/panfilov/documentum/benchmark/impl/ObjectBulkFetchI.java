package tel.panfilov.documentum.benchmark.impl;

import java.io.IOException;

import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class ObjectBulkFetchI extends ObjectBulkFetch {

    public ObjectBulkFetchI(String docbase, String userName, String password) {
        super(docbase, userName, password);
    }

    @Override
    protected void renewCollection(IDfSession session) throws DfException,
        IOException {
        _collection = new DfQuery(getProjection() + " WHERE "
                + getRestriction() + getOrderBy()).execute(_session,
                IDfQuery.DF_EXEC_QUERY);
    }

}
