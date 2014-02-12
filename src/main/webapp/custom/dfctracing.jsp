<%@ page import="com.documentum.fc.common.DfPreferences" %>
<%@ page import="com.documentum.fc.tracing.impl.Tracing" %>
<%@ page import="java.io.BufferedInputStream" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.OutputStreamWriter" %>
<%@ page import="java.io.PrintWriter" %>
<%
    DfPreferences preferences = DfPreferences.getInstance();
    boolean isDownload = request.getParameterMap().containsKey("download");
    if (!isDownload) {
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    } else {
        File file = new File(Tracing.getTraceFileName());
        if (file.exists()) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + '"');
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(file));
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) > -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            fileInputStream.close();
            outputStream.flush();
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return;
    }
%>
<html>
<head>
    <title>DFC Tracing</title>
</head>
<body>

<%
    boolean submitted = "submit".equals(request.getParameter("submit"));
    boolean tracingEnable;
    int tracingStackDepth;
    String tracingMode;
    boolean tracingVerbose;
    String tracingFileCreationMode;
    String tracingTimingStyle;
    String tracingThreadNameFilter;
    String tracingUserNameFilter;
    String tracingMethodNameFilter;
    boolean tracingIncludeRPC;
    boolean tracingIncludeRPCCount;
    boolean tracingIncludeSessionId;
    ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
    if (submitted) {
        tracingEnable = "true".equals(request.getParameter(DfPreferences.DFC_TRACING_ENABLE));
        tracingStackDepth = Integer.valueOf(request.getParameter(DfPreferences.DFC_TRACING_MAX_STACK_DEPTH));
        tracingMode = request.getParameter(DfPreferences.DFC_TRACING_MODE);
        tracingVerbose = "true".equals(request.getParameter(DfPreferences.DFC_TRACING_VERBOSE));
        tracingFileCreationMode = request.getParameter(DfPreferences.DFC_TRACING_FILE_CREATION_MODE);
        tracingTimingStyle = request.getParameter(DfPreferences.DFC_TRACING_TIMING_STYLE);
        tracingThreadNameFilter = request.getParameter(DfPreferences.DFC_TRACING_THREAD_NAME_FILTER);
        tracingUserNameFilter = request.getParameter(DfPreferences.DFC_TRACING_USER_NAME_FILTER);
        tracingMethodNameFilter = request.getParameter(DfPreferences.DFC_TRACING_METHOD_NAME_FILTER);
        tracingIncludeRPC = "true".equals(request.getParameter(DfPreferences.DFC_TRACING_INCLUDE_RPCS));
        tracingIncludeRPCCount = "true".equals(request.getParameter(DfPreferences.DFC_TRACING_INCLUDE_RPC_COUNT));
        tracingIncludeSessionId = "true".equals(request.getParameter(DfPreferences.DFC_TRACING_INCLUDE_SESSION_ID));
        if (tracingThreadNameFilter != null && tracingThreadNameFilter.trim().length() == 0) {
            tracingThreadNameFilter = null;
        }
        if (tracingUserNameFilter != null && tracingUserNameFilter.trim().length() == 0) {
            tracingUserNameFilter = null;
        }
        if (tracingMethodNameFilter != null && tracingMethodNameFilter.trim().length() == 0) {
            tracingMethodNameFilter = null;
        }
        try {
            preferences.setInt(DfPreferences.DFC_TRACING_MAX_STACK_DEPTH, tracingStackDepth);
            preferences.setString(DfPreferences.DFC_TRACING_MODE, tracingMode);
            preferences.setBoolean(DfPreferences.DFC_TRACING_VERBOSE, tracingVerbose);
            preferences.setString(DfPreferences.DFC_TRACING_FILE_CREATION_MODE, tracingFileCreationMode);
            preferences.setString(DfPreferences.DFC_TRACING_TIMING_STYLE, tracingTimingStyle);
            preferences.truncate(DfPreferences.DFC_TRACING_THREAD_NAME_FILTER, 0);
            if (tracingThreadNameFilter != null) {
                for (String str : tracingThreadNameFilter.split(", ")) {
                    preferences.appendString(DfPreferences.DFC_TRACING_THREAD_NAME_FILTER, str);
                }
            }
            preferences.truncate(DfPreferences.DFC_TRACING_USER_NAME_FILTER, 0);
            if (tracingUserNameFilter != null) {
                for (String str : tracingUserNameFilter.split(", ")) {
                    preferences.appendString(DfPreferences.DFC_TRACING_USER_NAME_FILTER, str);
                }
            }
            preferences.truncate(DfPreferences.DFC_TRACING_METHOD_NAME_FILTER, 0);
            if (tracingMethodNameFilter != null) {
                for (String str : tracingMethodNameFilter.split(", ")) {
                    preferences.appendString(DfPreferences.DFC_TRACING_METHOD_NAME_FILTER, str);
                }
            }
            preferences.setBoolean(DfPreferences.DFC_TRACING_INCLUDE_RPCS, tracingIncludeRPC);
            preferences.setBoolean(DfPreferences.DFC_TRACING_INCLUDE_RPC_COUNT, tracingIncludeRPCCount);
            preferences.setBoolean(DfPreferences.DFC_TRACING_INCLUDE_SESSION_ID, tracingIncludeSessionId);
            preferences.setTracingEnabled(tracingEnable);
        } catch (Exception ex) {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(errorStream));
            ex.printStackTrace(printWriter);
            printWriter.flush();
            printWriter.close();
        }
    } else {
        tracingEnable = preferences.isTracingEnabled();
        tracingStackDepth = preferences.getTracingStackDepth();
        tracingMode = preferences.getString(DfPreferences.DFC_TRACING_MODE);
        tracingVerbose = preferences.getBoolean(DfPreferences.DFC_TRACING_VERBOSE);
        tracingFileCreationMode = preferences.getString(DfPreferences.DFC_TRACING_FILE_CREATION_MODE);
        tracingTimingStyle = preferences.getString(DfPreferences.DFC_TRACING_TIMING_STYLE);
        tracingThreadNameFilter = preferences.getAllRepeatingStrings(DfPreferences.DFC_TRACING_THREAD_NAME_FILTER, ", ");
        tracingUserNameFilter = preferences.getAllRepeatingStrings(DfPreferences.DFC_TRACING_USER_NAME_FILTER, ", ");
        tracingMethodNameFilter = preferences.getAllRepeatingStrings(DfPreferences.DFC_TRACING_METHOD_NAME_FILTER, ", ");
        tracingIncludeRPC = preferences.getBoolean(DfPreferences.DFC_TRACING_INCLUDE_RPCS);
        tracingIncludeRPCCount = preferences.getBoolean(DfPreferences.DFC_TRACING_INCLUDE_RPC_COUNT);
        tracingIncludeSessionId = preferences.getBoolean(DfPreferences.DFC_TRACING_INCLUDE_SESSION_ID);
    }
%>

<%
    if (errorStream.size() > 0) {
%>
<pre>
    <%= new String(errorStream.toByteArray())%>
</pre>
<%
    }
%>

<form action="<%=request.getRequestURI()%>" method="post">
    <table>
        <% if ("standard".equals(tracingFileCreationMode)) {
            String fileName = Tracing.getTraceFileName();
        %>
        <tr>
            <td>File:</td>
            <% if (fileName == null) { %>
            <td></td>
            <% } else { %>
            <td><a href="<%=request.getRequestURI()%>?download"><%=fileName%>
            </a></td>
            <% } %>
        </tr>
        <%
            }
        %>
        <tr>
            <td><%=DfPreferences.DFC_TRACING_ENABLE%>:</td>
            <td><input name="<%=DfPreferences.DFC_TRACING_ENABLE%>" type="checkbox"
                       value="true" <%=tracingEnable ? "checked" : ""%>></td>
        </tr>
        <tr>
            <td><%=DfPreferences.DFC_TRACING_MAX_STACK_DEPTH%>:</td>
            <td><input name="<%=DfPreferences.DFC_TRACING_MAX_STACK_DEPTH%>" type="text" maxlength="3" size="3"
                       value="<%=tracingStackDepth%>"></td>
        </tr>
        <tr>
            <td><%=DfPreferences.DFC_TRACING_MODE%>:</td>
            <td><select name="<%=DfPreferences.DFC_TRACING_MODE%>">
                <option value="standard" <%="standard".equals(tracingMode) ? "selected" : ""%>>standard</option>
                <option value="compact" <%="compact".equals(tracingMode) ? "selected" : ""%>>compact</option>
            </select></td>
        </tr>
        <tr>
            <td><%=DfPreferences.DFC_TRACING_VERBOSE%>:</td>
            <td><input name="<%=DfPreferences.DFC_TRACING_VERBOSE%>" type="checkbox"
                       value="true" <%=tracingVerbose ? "checked" : ""%>></td>
        </tr>
        <tr>
            <td><%=DfPreferences.DFC_TRACING_FILE_CREATION_MODE%>:</td>
            <td><select name="<%=DfPreferences.DFC_TRACING_FILE_CREATION_MODE%>">
                <option value="standard" <%="standard".equals(tracingFileCreationMode) ? "selected" : ""%>>standard
                </option>
                <option value="thread" <%="thread".equals(tracingFileCreationMode) ? "selected" : ""%>>thread</option>
                <option value="user" <%="user".equals(tracingFileCreationMode) ? "selected" : ""%>>user</option>
            </select>
            </td>
        </tr>
        <tr>
            <td><%=DfPreferences.DFC_TRACING_TIMING_STYLE%>:</td>
            <td><select name="<%=DfPreferences.DFC_TRACING_TIMING_STYLE%>">
                <option value="no_timing" <%="no_timing".equals(tracingTimingStyle) ? "selected" : ""%>>no_timing
                </option>
                <option value="nanoseconds" <%="nanoseconds".equals(tracingTimingStyle) ? "selected" : ""%>>
                    nanoseconds
                </option>
                <option value="milliseconds" <%="milliseconds".equals(tracingTimingStyle) ? "selected" : ""%>>
                    milliseconds
                </option>
                <option value="milliseconds_from_start" <%="milliseconds_from_start".equals(tracingTimingStyle) ? "selected" : ""%>>
                    milliseconds_from_start
                </option>
                <option value="date" <%="date".equals(tracingTimingStyle) ? "selected" : ""%>>date</option>
                <option value="seconds" <%="seconds".equals(tracingTimingStyle) ? "selected" : ""%>>seconds</option>
            </select></td>
        </tr>
        <tr>
            <td><%=DfPreferences.DFC_TRACING_THREAD_NAME_FILTER%>:</td>
            <td><input name="<%=DfPreferences.DFC_TRACING_THREAD_NAME_FILTER%>" type="text" maxlength="500" size="50"
                       value="<%=tracingThreadNameFilter == null ? "" : tracingThreadNameFilter%>"></td>
        </tr>
        <tr>
            <td><%=DfPreferences.DFC_TRACING_USER_NAME_FILTER%>:</td>
            <td><input name="<%=DfPreferences.DFC_TRACING_USER_NAME_FILTER%>" type="text" maxlength="500" size="50"
                       value="<%=tracingUserNameFilter == null ? "" : tracingUserNameFilter%>"></td>
        </tr>
        <tr>
            <td><%=DfPreferences.DFC_TRACING_METHOD_NAME_FILTER%>:</td>
            <td><input name="<%=DfPreferences.DFC_TRACING_METHOD_NAME_FILTER%>" type="text" maxlength="500" size="50"
                       value="<%=tracingMethodNameFilter == null ? "" : tracingMethodNameFilter%>"></td>
        </tr>
        <tr>
            <td><%=DfPreferences.DFC_TRACING_INCLUDE_RPCS%>:</td>
            <td><input name="<%=DfPreferences.DFC_TRACING_INCLUDE_RPCS%>" type="checkbox"
                       value="true" <%=tracingIncludeRPC ? "checked" : ""%>></td>
        </tr>
        <tr>
            <td><%=DfPreferences.DFC_TRACING_INCLUDE_RPC_COUNT%>:</td>
            <td><input name="<%=DfPreferences.DFC_TRACING_INCLUDE_RPC_COUNT%>" type="checkbox"
                       value="true" <%=tracingIncludeRPCCount ? "checked" : ""%>></td>
        </tr>
        <tr>
            <td><%=DfPreferences.DFC_TRACING_INCLUDE_SESSION_ID%>:</td>
            <td><input name="<%=DfPreferences.DFC_TRACING_INCLUDE_SESSION_ID%>" type="checkbox"
                       value="true" <%=tracingIncludeSessionId ? "checked" : ""%>></td>
        </tr>
        <tr>
            <td colspan="2" align="left"><input type="submit" name="submit" value="submit"></td>
        </tr>
    </table>
</form>

</body>
</html>
