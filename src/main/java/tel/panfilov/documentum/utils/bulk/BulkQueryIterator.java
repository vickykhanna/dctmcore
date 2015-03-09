package tel.panfilov.documentum.utils.bulk;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;

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
        try {
            Set<String> attributes = getAttributes();
            String projection = getProjection(attributes);
            return Arrays.asList(
                    "SELECT " + projection + " FROM " + getObjectType()
                            + getTypeModifier(attributes)
                            + " WHERE r_object_id IN (" + param + ") "
                            + getOrderBy(attributes)).iterator();
        } catch (DfException ex) {
            throw new RuntimeException(ex);
        }
    }

}
