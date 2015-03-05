package tel.panfilov.documentum.utils.bulk;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfDocbaseConstants;

import tel.panfilov.documentum.utils.query.QueryHelper;
import tel.panfilov.documentum.utils.wrappers.SubIterator;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class BulkCollectionIterator extends
        AbstractBulkIterator<Iterator<String>> {

    public static final int MAX_OBJECTS_IN_QUERY = 1000;

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
        return new Iterator<String>() {

            Iterator<List<String>> _iterator = new SubIterator<String>(
                    objectIds, MAX_OBJECTS_IN_QUERY);

            @Override
            public boolean hasNext() {
                return _iterator.hasNext();
            }

            @Override
            public String next() {
                return buildQuery(_iterator.next());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException(
                        "This operation is not supported");
            }

        };
    }

    protected String buildQuery(List<String> ids) {
        String projection = getProjection();
        StringBuilder query = new StringBuilder(projection.length()
                + ids.size() * 17);
        query.append("SELECT ");
        query.append(projection);
        query.append(" FROM ").append(getObjectType());
        if (projection.contains(DfDocbaseConstants.I_CHRONICLE_ID)) {
            query.append("(ALL)");
        }
        query.append(" WHERE ");
        query.append(QueryHelper.createInClause(DfDocbaseConstants.R_OBJECT_ID,
                ids, MAX_OBJECTS_IN_CLAUSE));
        return query.toString();
    }

}
