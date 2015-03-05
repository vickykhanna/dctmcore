package tel.panfilov.documentum.utils.query;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public final class ReservedWords {

    public static final Set<String> RESERVED = new HashSet<String>();

    static {
        RESERVED.add("ABORT");
        RESERVED.add("ALTER");
        RESERVED.add("ASSEMBLIES");
        RESERVED.add("ACL");
        RESERVED.add("AND");
        RESERVED.add("ASSEMBLY");
        RESERVED.add("ADD");
        RESERVED.add("ANY");
        RESERVED.add("ASSISTANCE");
        RESERVED.add("ADD_FTINDEX");
        RESERVED.add("APPEND");
        RESERVED.add("ATTR");
        RESERVED.add("ADDRESS");
        RESERVED.add("APPLICATION");
        RESERVED.add("AUTO");
        RESERVED.add("ALL");
        RESERVED.add("AS");
        RESERVED.add("AVG");
        RESERVED.add("ALLOW");
        RESERVED.add("ASC");
        RESERVED.add("BAG");
        RESERVED.add("BOOL");
        RESERVED.add("BUSINESS");
        RESERVED.add("BEGIN");
        RESERVED.add("BOOLEAN");
        RESERVED.add("BY");
        RESERVED.add("BETWEEN");
        RESERVED.add("BROWSE");
        RESERVED.add("CABINET");
        RESERVED.add("COMMENT");
        RESERVED.add("CONTAINS");
        RESERVED.add("CACHING");
        RESERVED.add("COMMIT");
        RESERVED.add("CONTENT_FORMAT");
        RESERVED.add("CHANGE");
        RESERVED.add("COMPLETE");
        RESERVED.add("CONTENT_ID");
        RESERVED.add("CHARACTER");
        RESERVED.add("COMPONENTS");
        RESERVED.add("COUNT");
        RESERVED.add("CHARACTERS");
        RESERVED.add("COMPOSITE");
        RESERVED.add("CREATE");
        RESERVED.add("CHAR");
        RESERVED.add("COMPUTED");
        RESERVED.add("CURRENT");
        RESERVED.add("CHECK");
        RESERVED.add("CONTAIN_ID");
        RESERVED.add("DATE");
        RESERVED.add("DELETED");
        RESERVED.add("DISTINCT");
        RESERVED.add("DATEADD");
        RESERVED.add("DEPENDENCY");
        RESERVED.add("DM_SESSION_DD_LOCALE");
        RESERVED.add("DATEDIFF");
        RESERVED.add("DEPTH");
        RESERVED.add("DOCBASIC");
        RESERVED.add("DATEFLOOR");
        RESERVED.add("DESC");
        RESERVED.add("DOCUMENT");
        RESERVED.add("DATETOSTRING");
        RESERVED.add("DESCEND");
        RESERVED.add("DOUBLE");
        RESERVED.add("DAY");
        RESERVED.add("DISABLE");
        RESERVED.add("DROP");
        RESERVED.add("DEFAULT");
        RESERVED.add("DISALLOW");
        RESERVED.add("DROP_FTINDEX");
        RESERVED.add("DELETE");
        RESERVED.add("DISPLAY");
        RESERVED.add("ELSE");
        RESERVED.add("ENFORCE");
        RESERVED.add("EXEC");
        RESERVED.add("ELSEIF");
        RESERVED.add("ESCAPE");
        RESERVED.add("EXECUTE");
        RESERVED.add("ENABLE");
        RESERVED.add("ESTIMATE");
        RESERVED.add("EXISTS");
        RESERVED.add("FALSE");
        RESERVED.add("FOR");
        RESERVED.add("FT_OPTIMIZER");
        RESERVED.add("FIRST");
        RESERVED.add("FOREIGN");
        RESERVED.add("FULLTEXT");
        RESERVED.add("FLOAT");
        RESERVED.add("FROM");
        RESERVED.add("FUNCTION");
        RESERVED.add("FOLDER");
        RESERVED.add("FTINDEX");
        RESERVED.add("GRANT");
        RESERVED.add("GROUP");
        RESERVED.add("HAVING");
        RESERVED.add("HITS");
        RESERVED.add("ID");
        RESERVED.add("INT");
        RESERVED.add("IS");
        RESERVED.add("IF");
        RESERVED.add("INTEGER");
        RESERVED.add("ISCURRENT");
        RESERVED.add("IN");
        RESERVED.add("INTERNAL");
        RESERVED.add("ISPUBLIC");
        RESERVED.add("INSERT");
        RESERVED.add("INTO");
        RESERVED.add("ISREPLICA");
        RESERVED.add("JOIN");
        RESERVED.add("KEY");
        RESERVED.add("LANGUAGE");
        RESERVED.add("LIGHTWEIGHT");
        RESERVED.add("LITE");
        RESERVED.add("LAST");
        RESERVED.add("LIKE");
        RESERVED.add("LOWER");
        RESERVED.add("LATEST");
        RESERVED.add("LINK");
        RESERVED.add("LEFT");
        RESERVED.add("LIST");
        RESERVED.add("MAPPING");
        RESERVED.add("MEMBERS");
        RESERVED.add("MONTH");
        RESERVED.add("MATERIALIZE");
        RESERVED.add("MFILE_URL");
        RESERVED.add("MOVE");
        RESERVED.add("MATERIALIZATION");
        RESERVED.add("MHITS");
        RESERVED.add("MSCORE");
        RESERVED.add("MAX");
        RESERVED.add("MIN");
        RESERVED.add("MCONTENTID");
        RESERVED.add("MODIFY");
        RESERVED.add("NODE");
        RESERVED.add("NOTE");
        RESERVED.add("NULLID");
        RESERVED.add("NODESORT");
        RESERVED.add("NOW");
        RESERVED.add("NULLINT");
        RESERVED.add("NONE");
        RESERVED.add("NULL");
        RESERVED.add("NULLSTRING");
        RESERVED.add("NOT");
        RESERVED.add("NULLDATE");
        RESERVED.add("OF");
        RESERVED.add("ON");
        RESERVED.add("ORDER");
        RESERVED.add("OBJECT");
        RESERVED.add("ONLY");
        RESERVED.add("OUTER");
        RESERVED.add("OBJECTS");
        RESERVED.add("OR");
        RESERVED.add("OWNER");
        RESERVED.add("PAGE_NO");
        RESERVED.add("PERMIT");
        RESERVED.add("PRIVATE");
        RESERVED.add("PARENT");
        RESERVED.add("POLICY");
        RESERVED.add("PRIVILEGES");
        RESERVED.add("PARTITION");
        RESERVED.add("POSITION");
        RESERVED.add("PROPERTY");
        RESERVED.add("PATH");
        RESERVED.add("PRIMARY");
        RESERVED.add("PUBLIC");
    }

    private ReservedWords() {
        super();
    }

    public static boolean isReserved(String attrName) {
        if (StringUtils.isBlank(attrName)) {
            throw new IllegalArgumentException("attrname is blank");
        }
        return RESERVED.contains(attrName.toUpperCase());
    }

    public static String getProjection(Set<String> attrs) {
        if (attrs == null || attrs.isEmpty()) {
            throw new IllegalArgumentException("empty attributes");
        }
        StringBuilder result = new StringBuilder(attrs.size() * 15);
        for (Iterator<String> iter = attrs.iterator(); iter.hasNext();) {
            String attrName = iter.next();
            if (isReserved(attrName)) {
                result.append('\"').append(attrName).append('\"');
            } else {
                result.append(attrName);
            }
            if (iter.hasNext()) {
                result.append(',');
            }
        }
        return result.toString();
    }

}
