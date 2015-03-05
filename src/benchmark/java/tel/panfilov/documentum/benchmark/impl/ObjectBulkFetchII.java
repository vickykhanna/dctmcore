package tel.panfilov.documentum.benchmark.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfType;
import com.documentum.fc.common.DfDocbaseConstants;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfAttr;

import tel.panfilov.documentum.utils.query.QueryHelper;
import tel.panfilov.documentum.utils.query.ReservedWords;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class ObjectBulkFetchII extends ObjectBulkFetch {

    private String _attrs;

    public ObjectBulkFetchII(String docbase, String userName, String password) {
        super(docbase, userName, password);
    }

    @Override
    public void doSetup() {
        try {
            super.doSetup();
            Set<String> attrs = new HashSet<String>();
            attrs.add(DfDocbaseConstants.R_OBJECT_ID);
            attrs.add(DfDocbaseConstants.I_VSTAMP);
            IDfType type = _session.getType("dm_document");
            for (int i = 0, n = type.getTypeAttrCount(); i < n; i++) {
                IDfAttr attr = type.getTypeAttr(i);
                if (!attr.isRepeating()) {
                    attrs.add(attr.getName());
                }
            }
            _attrs = ReservedWords.getProjection(attrs);
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
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
        _collection = new DfQuery("SELECT "
                + _attrs
                + " FROM dm_document WHERE "
                + QueryHelper.createInClause(DfDocbaseConstants.R_OBJECT_ID,
                        objects)).execute(_session, IDfQuery.READ_QUERY);
    }

}
