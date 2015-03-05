package tel.panfilov.documentum.utils;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import com.documentum.fc.common.DfLogger;
import com.documentum.fc.common.DfUtil;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public final class CoreUtils {

    public static final String EMPTY_STRING = "";

    private CoreUtils() {
        super();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean asBoolean(String booleanStr) {
        return "T".equalsIgnoreCase(booleanStr)
                || "TRUE".equalsIgnoreCase(booleanStr)
                || "1".equalsIgnoreCase(booleanStr)
                || "1.0".equalsIgnoreCase(booleanStr);
    }

    public static boolean inList(String str, String... values) {
        if (isEmpty(str)) {
            return false;
        }
        if (values == null) {
            return false;
        }
        for (String val : values) {
            if (str.equals(val)) {
                return true;
            }
        }
        return false;
    }

    public static boolean inListIgnoreCase(String str, String... values) {
        if (isEmpty(str)) {
            return false;
        }
        if (values == null) {
            return false;
        }
        for (String val : values) {
            if (str.equalsIgnoreCase(val)) {
                return true;
            }
        }
        return false;
    }

    public static String nullToBlank(String str) {
        if (str != null) {
            return str;
        }
        return EMPTY_STRING;
    }

    public static String createInClause(String attrName,
            Collection<String> values) {
        return createInClause(attrName, values, 250);
    }

    public static String createInClause(String attrName,
            Collection<String> values, int maxItems) {
        return createInClause(attrName, values, true, maxItems);
    }

    public static String createInClause(String attrName, Collection<?> values,
            boolean quote, int maxItems) {
        if (isEmpty(attrName)) {
            throw new IllegalArgumentException("attrname is blank");
        }
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("values are empty");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("( ").append(attrName).append(" IN (");
        Collection<?> resultValues = new HashSet<Object>(values);
        int iterations = 0;
        for (Iterator<?> iter = resultValues.iterator(); iter.hasNext();) {
            Object value = iter.next();
            if (value == null) {
                continue;
            }
            if (quote) {
                stringBuilder.append("'");
            }
            stringBuilder.append(DfUtil.escapeQuotedString(String
                    .valueOf(value)));
            if (quote) {
                stringBuilder.append("'");
            }
            if (iter.hasNext()) {
                if (iterations > 0 && 0 == iterations % maxItems) {
                    stringBuilder.append(") OR ").append(attrName)
                            .append(" IN (");
                } else {
                    stringBuilder.append(", ");
                }
            }
            iterations++;
        }
        stringBuilder.append("))");
        return stringBuilder.toString();
    }

    public static void closeSilently(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ex) {
            DfLogger.error(CoreUtils.class, ex.getMessage(), null, ex);
        }
    }

}
