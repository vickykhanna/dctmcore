package tel.panfilov.documentum.benchmark.impl;

import com.documentum.fc.client.IDfPersistentObject;

import tel.panfilov.documentum.utils.CoreUtils;
import tel.panfilov.documentum.utils.bulk.BulkCollectionIterator;
import tel.panfilov.documentum.utils.wrappers.BufferedReaderIterator;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class ObjectBulkFetchIII extends ObjectFetch {

    BulkCollectionIterator _iterator;

    public ObjectBulkFetchIII(String docbase, String userName, String password) {
        super(docbase, userName, password);
    }

    @Override
    public void doOp() {
        if (_iterator == null) {
            _iterator = new BulkCollectionIterator(_session, "dm_document",
                    new BufferedReaderIterator(_objects), null);
        }
        if (_iterator.hasNext()) {
            IDfPersistentObject object = _iterator.next();
            return;
        }
        throw new RuntimeException("no objects");
    }

    @Override
    public void doRelease() {
        super.doRelease();
        CoreUtils.closeSilently(_iterator);
    }

}
