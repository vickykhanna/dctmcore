package tel.panfilov.documentum.utils.bulk;

import java.util.Arrays;
import java.util.Iterator;

import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfDocbaseConstants;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class BulkQueryIterator extends AbstractBulkIterator<String> {

    public BulkQueryIterator(IDfSession session, String objectType,
            String query, ISkipChecker skipChecker) {
        super(session, objectType, query, skipChecker);
    }

    @Override
    protected Iterator<String> getQueries(String param) {
        String projection = getProjection();
        StringBuilder query = new StringBuilder(projection.length()
                + param.length());
        query.append("SELECT ");
        query.append(projection);
        query.append(" FROM ").append(getObjectType());
        if (projection.contains(DfDocbaseConstants.I_CHRONICLE_ID)) {
            query.append("(ALL)");
        }
        query.append(" WHERE r_object_id IN (");
        query.append(param);
        query.append(")");
        query.append(" ORDER BY ").append(DfDocbaseConstants.R_OBJECT_ID);
        if (projection.contains("i_position")) {
            query.append(", i_position DESC");
        }
        return Arrays.asList(query.toString()).iterator();
    }

}
