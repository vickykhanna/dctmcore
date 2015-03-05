package tel.panfilov.documentum.benchmark.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfDocbaseConstants;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfList;

import tel.panfilov.documentum.utils.query.QueryHelper;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class ObjectBulkFetch extends ObjectFetch {

    protected IDfCollection _collection;

    public ObjectBulkFetch(String docbase, String userName, String password) {
        super(docbase, userName, password);
    }

    @Override
    public void doOp() {
        try {
            if (_collection == null) {
                renewCollection(_session);
            }
            if (!_collection.next()) {
                if (_collection.getState() != IDfCollection.DF_CLOSED_STATE) {
                    _collection.close();
                }
                renewCollection(_session);
                if (!_collection.next()) {
                    throw new RuntimeException("no objects");
                }
            }
            _collection.getTypedObject();
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void doRelease() {
        try {
            if (_collection != null
                    && _collection.getState() != IDfCollection.DF_CLOSED_STATE) {
                _collection.close();
            }
            super.doRelease();
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected void renewCollection(IDfSession session) throws DfException,
        IOException {
        List<String> objects = new ArrayList<String>(2000);
        String objectId = null;
        while ((objectId = _objects.readLine()) != null) {
            objects.add(objectId);
            if (objects.size() >= 2000) {
                break;
            }
        }
        if (objects.size() == 0) {
            throw new IOException("empty buffer");
        }
        String query = QueryHelper.createInClause(
                DfDocbaseConstants.R_OBJECT_ID, objects);
        _collection = session.apply(null, "DQL_MATCH", new DfList(new String[] {
            "QUERY_TYPE", "QUERY_PREDICATE" }), new DfList(new String[] {"S",
            "S" }), new DfList(new String[] {"dm_document", query }));
    }

}
