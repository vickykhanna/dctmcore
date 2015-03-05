package tel.panfilov.documentum.utils.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfTypedObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfLogger;
import com.documentum.fc.common.DfUtil;
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfTime;
import com.documentum.fc.common.IDfValue;

import tel.panfilov.documentum.utils.ClientXUtils;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class QueryHelper {

    private static final QueryHelper INSTANCE = new QueryHelper();

    protected QueryHelper() {
        super();
    }

    private static QueryHelper getInstance() {
        return INSTANCE;
    }

    private static String getDql(CharSequence query) {
        if (query == null) {
            return null;
        } else if (query instanceof String) {
            return (String) query;
        } else {
            return query.toString();
        }
    }

    public static void selectQuery(IDfSession session, CharSequence query,
            IRowHandler rowHandler) throws DfException {
        getInstance().selectQueryInternal(session, query, null, rowHandler,
                IDfQuery.DF_EXEC_QUERY, -1, -1);
    }

    public static void selectQuery(IDfSession session, CharSequence query,
            IMetadataHandler metadataHandler, IRowHandler rowHandler)
        throws DfException {
        getInstance().selectQueryInternal(session, query, metadataHandler,
                rowHandler, IDfQuery.DF_EXEC_QUERY, -1, -1);
    }

    public static void selectQuery(IDfSession session, CharSequence query,
            IRowHandler rowHandler, int start, int count) throws DfException {
        getInstance().selectQueryInternal(session, query, null, rowHandler,
                IDfQuery.DF_EXEC_QUERY, start, count);
    }

    public static void selectQuery(IDfSession session, CharSequence query,
            IMetadataHandler metadataHandler, IRowHandler rowHandler,
            int start, int count) throws DfException {
        getInstance().selectQueryInternal(session, query, metadataHandler,
                rowHandler, IDfQuery.DF_EXEC_QUERY, start, count);
    }

    public static void selectQuery(IDfSession session, CharSequence query,
            IRowHandler rowHandler, int queryType) throws DfException {
        getInstance().selectQueryInternal(session, query, null, rowHandler,
                queryType, -1, -1);
    }

    public static void selectQuery(IDfSession session, CharSequence query,
            IMetadataHandler metadataHandler, IRowHandler rowHandler,
            int queryType) throws DfException {
        getInstance().selectQueryInternal(session, query, metadataHandler,
                rowHandler, queryType, -1, -1);
    }

    public static void selectQuery(IDfSession session, CharSequence query,
            IRowHandler rowHandler, int queryType, int start, int count)
        throws DfException {
        getInstance().selectQueryInternal(session, query, null, rowHandler,
                queryType, start, count);
    }

    public static void selectQuery(IDfSession session, CharSequence query,
            IMetadataHandler metadataHandler, IRowHandler rowHandler,
            int queryType, int start, int count) throws DfException {
        getInstance().selectQueryInternal(session, query, metadataHandler,
                rowHandler, queryType, start, count);
    }

    public static void processCollection(IDfCollection coll, IRowHandler handler)
        throws DfException {
        getInstance().processCollectionInternal(coll, handler, -1, -1);
    }

    public static void processCollection(IDfCollection coll,
            IRowHandler handler, int start, int count) throws DfException {
        getInstance().processCollectionInternal(coll, handler, start, count);
    }

    public static void executeQuery(IDfSession session, CharSequence query)
        throws DfException {
        getInstance().executeQuery(session, query, false);
    }

    public static void executeQuerySilently(IDfSession session,
            CharSequence query) throws DfException {
        DfLogger.warn(QueryHelper.class, "Query executing silently!!!", null,
                null);
        getInstance().executeQuery(session, query, true);
    }

    public static IDfId getSingleValueFromQueryAsId(IDfSession session,
            CharSequence query, String attrName) throws DfException {
        IDfValue value = getInstance().getSingleValueFromQueryInternal(session,
                query, attrName);
        if (value == null) {
            return null;
        } else {
            return value.asId();
        }
    }

    public static String getSingleValueFromQueryAsString(IDfSession session,
            CharSequence query, String attrName) throws DfException {
        IDfValue value = getInstance().getSingleValueFromQueryInternal(session,
                query, attrName);
        if (value == null) {
            return null;
        } else {
            return value.asString();
        }
    }

    public static String getSingleValueFromQueryAsString(IDfSession session,
            CharSequence query, String attrName, String defaultValue)
        throws DfException {
        String value = getSingleValueFromQueryAsString(session, query, attrName);
        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }

    public static Integer getSingleValueFromQueryAsInteger(IDfSession session,
            CharSequence query, String attrName) throws DfException {
        IDfValue value = getInstance().getSingleValueFromQueryInternal(session,
                query, attrName);
        if (value == null) {
            return null;
        } else {
            return value.asInteger();
        }
    }

    public static Integer getSingleValueFromQueryAsInteger(IDfSession session,
            CharSequence query, String attrName, Integer defaultValue)
        throws DfException {
        Integer value = getSingleValueFromQueryAsInteger(session, query,
                attrName);
        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }

    public static Double getSingleValueFromQueryAsDouble(IDfSession session,
            CharSequence query, String attrName) throws DfException {
        IDfValue value = getInstance().getSingleValueFromQueryInternal(session,
                query, attrName);
        if (value == null) {
            return null;
        } else {
            return value.asDouble();
        }
    }

    public static Double getSingleValueFromQueryAsDouble(IDfSession session,
            CharSequence query, String attrName, Double defaultValue)
        throws DfException {
        Double value = getSingleValueFromQueryAsDouble(session, query, attrName);
        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }

    public static Boolean getSingleValueFromQueryAsBoolean(IDfSession session,
            CharSequence query, String attrName) throws DfException {
        IDfValue value = getInstance().getSingleValueFromQueryInternal(session,
                query, attrName);
        if (value == null) {
            return null;
        } else {
            return value.asBoolean();
        }
    }

    public static Boolean getSingleValueFromQueryAsBoolean(IDfSession session,
            CharSequence query, String attrName, Boolean defaultValue)
        throws DfException {
        Boolean value = getSingleValueFromQueryAsBoolean(session, query,
                attrName);
        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }

    public static IDfTime getSingleValueFromQueryAsTime(IDfSession session,
            CharSequence query, String attrName) throws DfException {
        IDfValue value = getInstance().getSingleValueFromQueryInternal(session,
                query, attrName);
        if (value == null) {
            return null;
        } else {
            return value.asTime();
        }
    }

    public static IDfTime getSingleValueFromQueryAsTime(IDfSession session,
            CharSequence query, String attrName, IDfTime defaultValue)
        throws DfException {
        IDfTime value = getSingleValueFromQueryAsTime(session, query, attrName);
        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }

    public static IDfValue getSingleValueFromQuery(IDfSession session,
            CharSequence query, String attrName) throws DfException {
        return getInstance().getSingleValueFromQueryInternal(session, query,
                attrName);
    }

    public static List<IDfId> readQueryAsIdList(IDfSession session,
            CharSequence query, final String attrName) throws DfException {
        return readQueryAsIdList(session, query, attrName, false);
    }

    public static List<IDfId> readQueryAsIdList(IDfSession session,
            CharSequence query, final String attrName, final boolean skipBlank)
        throws DfException {
        List<IDfId> result = new ArrayList<IDfId>();
        for (IDfValue ivalue : getInstance().readQueryAsValueListInternal(
                session, query, attrName)) {
            IDfId value = ivalue.asId();
            if (skipBlank && value.isNull()) {
                continue;
            }
            result.add(value);
        }
        return result;
    }

    public static List<String> readQueryAsStringList(IDfSession session,
            CharSequence query, String attrName) throws DfException {
        return readQueryAsStringList(session, query, attrName, false);
    }

    public static List<String> readQueryAsStringList(IDfSession session,
            CharSequence query, final String attrName, final boolean skipBlank)
        throws DfException {
        List<String> result = new ArrayList<String>();
        for (IDfValue ivalue : getInstance().readQueryAsValueListInternal(
                session, query, attrName)) {
            String value = ivalue.asString();
            if (skipBlank && StringUtils.isBlank(value)) {
                continue;
            }
            result.add(value);
        }
        return result;
    }

    public static Set<String> readQueryAsStringSet(IDfSession session,
            CharSequence query, String attrName, boolean skipBlank)
        throws DfException {
        return new LinkedHashSet<String>(readQueryAsStringList(session, query,
                attrName, skipBlank));
    }

    public static Set<String> readQueryAsStringSet(IDfSession session,
            CharSequence query, String attrName) throws DfException {
        return new LinkedHashSet<String>(readQueryAsStringList(session, query,
                attrName));
    }

    public static List<Integer> readQueryAsIntegerList(IDfSession session,
            CharSequence query, final String attrName) throws DfException {
        List<Integer> result = new ArrayList<Integer>();
        for (IDfValue ivalue : getInstance().readQueryAsValueListInternal(
                session, query, attrName)) {
            Integer value = ivalue.asInteger();
            result.add(value);
        }
        return result;
    }

    public static Set<Integer> readQueryAsIntegerSet(IDfSession session,
            CharSequence query, String attrName) throws DfException {
        return new LinkedHashSet<Integer>(readQueryAsIntegerList(session,
                query, attrName));
    }

    public static List<Double> readQueryAsDoubleList(IDfSession session,
            CharSequence query, final String attrName) throws DfException {
        List<Double> result = new ArrayList<Double>();
        for (IDfValue ivalue : getInstance().readQueryAsValueListInternal(
                session, query, attrName)) {
            Double value = ivalue.asDouble();
            result.add(value);
        }
        return result;
    }

    public static Set<Double> readQueryAsDoubleSet(IDfSession session,
            CharSequence query, String attrName) throws DfException {
        return new LinkedHashSet<Double>(readQueryAsDoubleList(session, query,
                attrName));
    }

    public static List<Boolean> readQueryAsBooleanList(IDfSession session,
            CharSequence query, final String attrName) throws DfException {
        List<Boolean> result = new ArrayList<Boolean>();
        for (IDfValue ivalue : getInstance().readQueryAsValueListInternal(
                session, query, attrName)) {
            Boolean value = ivalue.asBoolean();
            result.add(value);
        }
        return result;
    }

    public static Set<Boolean> readQueryAsBooleanSet(IDfSession session,
            CharSequence query, String attrName) throws DfException {
        return new LinkedHashSet<Boolean>(readQueryAsBooleanList(session,
                query, attrName));
    }

    public static List<IDfTime> readQueryAsTimeList(IDfSession session,
            CharSequence query, final String attrName) throws DfException {
        List<IDfTime> result = new ArrayList<IDfTime>();
        for (IDfValue ivalue : getInstance().readQueryAsValueListInternal(
                session, query, attrName)) {
            IDfTime value = ivalue.asTime();
            result.add(value);
        }
        return result;
    }

    public static Set<IDfTime> readQueryAsTimeSet(IDfSession session,
            CharSequence query, String attrName) throws DfException {
        return new LinkedHashSet<IDfTime>(readQueryAsTimeList(session, query,
                attrName));
    }

    public static List<IDfValue> readQueryAsValueList(IDfSession session,
            CharSequence query, final String attrName) throws DfException {
        return getInstance().readQueryAsValueListInternal(session, query,
                attrName);
    }

    public static Map<String, IDfTypedObject> readQueryAsMap(
            IDfSession session, CharSequence query, final String attrName)
        throws DfException {
        return getInstance().readQueryAsMapInternal(session, query, attrName);
    }

    public static String createInClause(String attrName,
            Collection<String> values) {
        return getInstance().createInClauseInternal(attrName, values, 250);
    }

    public static String createInClause(String attrName,
            Collection<String> values, int maxItems) {
        return getInstance().createInClauseInternal(attrName, values, maxItems);
    }

    public static void close(RowIterator coll) {
        try {
            if (coll != null
                    && coll.getState() != IDfCollection.DF_CLOSED_STATE) {
                coll.closeInternal();
            }
        } catch (DfException ex) {
            DfLogger.error(QueryHelper.class, "Exception in close()", null, ex);
        }
    }

    public static void close(IDfCollection coll) {
        try {
            if (coll != null
                    && coll.getState() != IDfCollection.DF_CLOSED_STATE) {
                coll.close();
            }
        } catch (DfException ex) {
            DfLogger.error(QueryHelper.class, "Exception in close()", null, ex);
        }
    }

    public static int getMaxResultCount(IDfSession session) throws DfException {
        return session.getClientConfig().getInt("dfc.search.max_results");
    }

    final void selectQueryInternal(IDfSession session, CharSequence query,
            IMetadataHandler metadataHandler, IRowHandler rowHandler,
            int queryType, int start, int count) throws DfException {
        if (session == null) {
            throw new NullPointerException("IDfSession is null");
        }
        if (query == null) {
            throw new NullPointerException("Query is null");
        }
        if (rowHandler == null) {
            throw new NullPointerException("Handler is null");
        }
        if (!(queryType == IDfQuery.DF_READ_QUERY
                || queryType == IDfQuery.DF_QUERY
                || queryType == IDfQuery.DF_CACHE_QUERY
                || queryType == IDfQuery.DF_EXECREAD_QUERY || queryType == IDfQuery.DF_EXEC_QUERY)) {
            throw new IllegalArgumentException("Wrong query type argument");
        }
        int end = -1;
        if (start >= 0 && count >= 0) {
            end = start + count;
        }
        try {
            IDfQuery dfQuery = getQuery(getDql(query));
            RowIterator iterator = null;
            try {
                iterator = new RowIterator(dfQuery.execute(session, queryType));
                int position = 0;
                if (metadataHandler != null) {
                    metadataHandler.handleMetadata(iterator);
                }
                while (iterator.nextInternal() && (end < 0 || position < end)) {
                    if (position >= start) {
                        rowHandler.handleRow(iterator, position);
                    }
                    position++;
                }
            } finally {
                close(iterator);
            }
        } catch (DfException ex) {
            DfLogger.error(QueryHelper.class,
                    "Exception in selectQueryInternal()", null, ex);
            throw ex;
        } catch (Exception ex) {
            DfLogger.error(QueryHelper.class,
                    "Exception in selectQueryInternal()", null, ex);
            throw new DfException("Exception in selectQueryInternal()", ex);
        }
    }

    final void processCollectionInternal(IDfCollection coll,
            IRowHandler handler, int start, int count) throws DfException {
        if (coll == null) {
            throw new NullPointerException("Collection is null");
        }
        if (handler == null) {
            throw new NullPointerException("Handler is null");
        }
        int end = -1;
        if (start >= 0 && count >= 0) {
            end = start + count;
        }
        try {
            RowIterator iterator = null;
            try {
                iterator = new RowIterator(coll);
                int position = 0;
                while (iterator.nextInternal() && (end < 0 || position < end)) {
                    if (position >= start) {
                        handler.handleRow(iterator, position);
                    }
                    position++;
                }
            } finally {
                close(iterator);
            }
        } catch (DfException ex) {
            DfLogger.error(QueryHelper.class,
                    "Exception in processCollection()", null, ex);
            throw ex;
        } catch (Exception ex) {
            DfLogger.error(QueryHelper.class,
                    "Exception in processCollection()", null, ex);
            throw new DfException("Exception in processCollection()", ex);
        }
    }

    final void executeQuery(IDfSession session, CharSequence query,
            boolean silent) throws DfException {
        if (session == null) {
            throw new NullPointerException("IDfSession is null");
        }
        if (query == null) {
            throw new NullPointerException("Query is null");
        }
        try {
            IDfQuery dfQuery = getQuery(getDql(query));
            IDfCollection coll = null;
            try {
                coll = dfQuery.execute(session, IDfQuery.DF_EXEC_QUERY);
            } finally {
                close(coll);
            }
        } catch (DfException ex) {
            if (!silent) {
                DfLogger.error(QueryHelper.class,
                        "Exception in executeQuery()", null, ex);
            }
            throw ex;
        } catch (Exception ex) {
            if (!silent) {
                DfLogger.error(QueryHelper.class,
                        "Exception in executeQuery()", null, ex);
            }
            throw new DfException("Exception in executeQuery()", ex);
        }
    }

    final IDfValue getSingleValueFromQueryInternal(IDfSession session,
            CharSequence query, String attrName) throws DfException {
        if (session == null) {
            throw new NullPointerException("IDfSession is null");
        }
        if (query == null) {
            throw new NullPointerException("Query is null");
        }
        try {
            IDfQuery dfQuery = getQuery(getDql(query));
            IDfCollection coll = null;
            try {
                coll = dfQuery.execute(session, IDfQuery.DF_READ_QUERY);
                if (coll.next()) {
                    if (attrName == null) {
                        if (coll.getAttrCount() <= 0) {
                            throw new IllegalArgumentException(
                                    "arrtName argument is blank and collection does not have any attributes");
                        }
                        return coll.getValue(coll.getAttr(0).getName());
                    } else {
                        return coll.getValue(attrName);
                    }
                } else {
                    return null;
                }
            } finally {
                close(coll);
            }
        } catch (DfException ex) {
            DfLogger.error(QueryHelper.class,
                    "Exception in getSingleValueFromQueryInternal()", null, ex);
            throw ex;
        } catch (Exception ex) {
            DfLogger.error(QueryHelper.class,
                    "Exception in getSingleValueFromQueryInternal()", null, ex);
            throw new DfException(
                    "Exception in getSingleValueFromQueryInternal()", ex);
        }
    }

    final List<IDfValue> readQueryAsValueListInternal(IDfSession session,
            CharSequence query, final String attrName) throws DfException {
        final List<IDfValue> result = new ArrayList<IDfValue>();
        selectQuery(session, query, new IRowHandler() {

            private String _fieldName = attrName;

            @Override
            public void handleRow(IDfCollection row, int position)
                throws DfException {
                if (StringUtils.isBlank(_fieldName)) {
                    if (row.getAttrCount() <= 0) {
                        throw new IllegalArgumentException(
                                "Field argument is blank and collection does not have any attributes");
                    } else {
                        _fieldName = row.getAttr(0).getName();
                    }
                }
                IDfValue value = row.getValue(_fieldName);
                result.add(value);
            }

        });
        return result;
    }

    final Map<String, IDfTypedObject> readQueryAsMapInternal(
            IDfSession session, CharSequence query, final String attrName)
        throws DfException {
        final Map<String, IDfTypedObject> result = new HashMap<String, IDfTypedObject>();
        selectQueryInternal(session, query, null, new IRowHandler() {

            @Override
            public void handleRow(IDfCollection row, int position)
                throws DfException {
                result.put(row.getString(attrName), row.getTypedObject());
            }

        }, IDfQuery.DF_EXEC_QUERY, -1, -1);
        return result;
    }

    final String createInClauseInternal(String attrName,
            Collection<String> values, int maxItems) {
        if (StringUtils.isBlank(attrName)) {
            throw new IllegalArgumentException("attrname is blank");
        }
        if (values == null || values.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("( ").append(attrName).append(" IN (");
        Collection<String> resultValues = new HashSet<String>(values);
        int iterations = 0;
        for (Iterator<String> iter = resultValues.iterator(); iter.hasNext();) {
            stringBuilder.append("'")
                    .append(DfUtil.escapeQuotedString(iter.next())).append("'");
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

    protected IDfQuery getQuery(CharSequence query) throws DfException {
        return ClientXUtils.getQuery(getDql(query));
    }

}
