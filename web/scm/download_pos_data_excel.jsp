<%-- 
    Document   : download_barcode_excel
    Created on : 14/12/2010, 17:28:38
    Author     : Ahmed Adel
--%>


<%@page import="java.sql.Blob"%>
<%@page import="com.mobinil.sds.core.system.scm.dto.STKDistRequestViewerDTO"%>


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
            <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
        <title>JSP Page</title>
    </head>
    <body>
       <%
         HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String Slach = System.getProperty("file.separator");
    String fileName = (String) dataHashMap.get(SCMInterfaceKey.SEARCH_EXCEL_SHEET_LINK);
String filePath = request.getRealPath("/scm/upload")+Slach+fileName;

    
dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, fileName);
dataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "scm"+Slach+"upload"+Slach);
session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, dataHashMap);

        %>

<form action="com.mobinil.sds.web.controller.UploadingFileServlet" name="GenerateSheet" id="GenerateSheet" method="post">
    <center>
        <br><br><br><br><br><br><br><br><br><br><br><br><br>
    <input id="bckButton" name="bckButton" class='button' type='button' value='Back' onclick="back();"/>
    </center>
    </form>
    </body>
    <script>
        function back ()
        {
            document.GenerateSheet.action="com.mobinil.sds.web.controller.WebControllerServlet?";
            document.GenerateSheet.action=document.GenerateSheet.action+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+'<%=SCMInterfaceKey.ACTION_VIEW_POS_DATA_MANAGEMENT%>';
                                                                        
            document.GenerateSheet.submit();
            
        }
    </script>
    <script>
        document.GenerateSheet.submit();
        

    </script>
</html>
