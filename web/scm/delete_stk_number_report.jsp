<%-- 
    Document   : delete_stk_number_report
    Created on : May 24, 2011, 12:59:24 PM
    Author     : mabdelaal
--%>

<%@ page contentType="text/html;charset=windows-1252"
              import="java.util.*"              
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.scm.model.*"
              %>

    <%
            String appName = request.getContextPath(); 
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
            String tableError = dataHashMap.get(SCMInterfaceKey.HTML_TABLE_ERROR_STKS) !=null ? (String)dataHashMap.get(SCMInterfaceKey.HTML_TABLE_ERROR_STKS) : "" ;
            String excelFile = dataHashMap.get(SCMInterfaceKey.EXCEL_ERROR_STKS) !=null ? (String)dataHashMap.get(SCMInterfaceKey.EXCEL_ERROR_STKS) : "" ;
            
            
            if (excelFile.compareTo("") != 0) {
                String fileName = excelFile.substring(excelFile.lastIndexOf(System.getProperty("file.separator")+1,excelFile.length()));
                System.out.println("fileName iss "+fileName);
                    dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, fileName);
                    dataHashMap.put(InterfaceKey.MODULE_SUB_PATH, SCMInterfaceKey.CONSTANT_SCM_UPLOAD_DIRECTORY);
                    session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, dataHashMap);
                }
            
    %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <title>Delete STK Number Report</title>

</head>
    <body>
    <center>
        <br><br><br><br><br><h1>Delete STK Number Report</h1> <br><br><br><br>
        <%= tableError.compareTo("")!=0 ? tableError : "<h3>Delete complete.</h3>" %>
        <br>
        <br>
        <br>
        <%if (excelFile.compareTo("") != 0) {%>
        <form action="com.mobinil.sds.web.controller.UploadingFileServlet" name="GenerateSheet" id="GenerateSheet" method="post">
            <center>                
                <input id="bckButton" name="bckButton" class='button' type='button' value='Back' onclick="back();"/>
            </center>
        </form>
    </center></body>
    <script>
        function back ()
        {
            document.GenerateSheet.action="com.mobinil.sds.web.controller.WebControllerServlet?";
            document.GenerateSheet.action=document.GenerateSheet.action+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+'<%=SCMInterfaceKey.ACTION_DELETE_STKS%>';
            document.GenerateSheet.submit();
            
        }
    </script>
    <script>
        document.GenerateSheet.submit();       
        
    </script>
        <%} else {%>
        </center></body>
        <%} %>
    
</html>
