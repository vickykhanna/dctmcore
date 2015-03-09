package tel.panfilov.documentum.utils.bulk;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfDocbaseConstants;
import com.documentum.fc.common.DfException;

import tel.panfilov.documentum.utils.CoreUtils;
import tel.panfilov.documentum.utils.query.QueryHelper;
import tel.panfilov.documentum.utils.wrappers.SubIterator;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class BulkCollectionIterator extends
        AbstractBulkIterator<Iterator<String>> {

    public static int MAX_OBJECTS_IN_QUERY = Integer.getInteger(
            "fetch.bulk.size", 100);

    public static final int MAX_OBJECTS_IN_CLAUSE = 250;

    public BulkCollectionIterator(IDfSession session, String objectType,
            Iterator<String> objectIds, ISkipChecker skipChecker) {
        super(session, objectType, objectIds, skipChecker);
    }

    public BulkCollectionIterator(IDfSession session, String objectType,
            Collection<String> objectIds, ISkipChecker skipChecker) {
        this(session, objectType, objectIds.iterator(), skipChecker);
    }

    @Override
    protected Iterator<String> getQueries(final Iterator<String> objectIds) {
        return new QueryIterator(objectIds);
    }

    protected String buildQuery(List<String> ids) {
        try {
            Set<String> attributes = getAttributes();
            return "SELECT "
                    + getProjection(attributes)
                    + " FROM "
                    + getObjectType()
                    + getTypeModifier(attributes)
                    + " WHERE "
                    + QueryHelper.createInClause(
                            DfDocbaseConstants.R_OBJECT_ID, ids,
                            MAX_OBJECTS_IN_CLAUSE) + " "
                    + getOrderBy(attributes);
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        }
    }

    private class QueryIterator implements Iterator<String>, Closeable {

        SubIterator<String> _iterator;

        public QueryIterator(Iterator<String> objectIds) {
            _iterator = new SubIterator<String>(objectIds, MAX_OBJECTS_IN_QUERY);
        }

        @Override
        public void close() throws IOException {
            CoreUtils.closeSilently(_iterator);
            _iterator = null;
        }

        @Override
        public boolean hasNext() {
            if (_iterator != null && _iterator.hasNext()) {
                return true;
            }
            CoreUtils.closeSilently(this);
            return false;
        }

        @Override
        public String next() {
            if (hasNext()) {
                return buildQuery(_iterator.next());
            }
            throw new IllegalStateException("Empty iterator");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "This operation is not supported");
        }

    }

}
