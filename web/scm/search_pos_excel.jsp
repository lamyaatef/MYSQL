<%--
    Document   : search_pos_excel
    Created on : 19/12/2010, 15:43:14
    Author     : Ahmed Adel
--%>

<%@page contentType="text/html" pageEncoding="windows-1256"%>

<%@ page      import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"
              import="com.mobinil.sds.core.system.scm.model.*"
              import="com.mobinil.sds.core.system.scm.dao.*"
              %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
       <%
                            HashMap dataHashMap = new HashMap(100);
                            dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
                            String Slach=System.getProperty("file.separator");
                            String baseDirectory = request.getRealPath(Slach+"scm"+Slach+"upload"+Slach);
                            String excelLink=(String)dataHashMap.get(SCMInterfaceKey.SEARCH_EXCEL_SHEET_LINK);
                            dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, excelLink);
                            dataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "scm"+Slach+"upload"+Slach);
                            session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, dataHashMap);
        %>
       <div name="Excel" id="Excel" align="center">
    <%out.println("<form action=\"com.mobinil.sds.web.controller.UploadingFileServlet\" name=\"GenerateSheet\" id=\"GenerateSheet\" method=\"post\">");
    %>
    <script>
        document.GenerateSheet.submit();
    </script>
    <% out.println("</form>");%>
        </div>
    </body>
</html>
