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
    java.util.Date dateNow = new java.util.Date();
        int imonth = dateNow.getMonth() + 1;
        int iyear = dateNow.getYear() + 1900;
        String strdate = (new StringBuffer("[")).append(dateNow.getDate()).append("-").append(imonth).append("-").append(iyear).append("]-").append(dateNow.getHours()).append(".").append(dateNow.getMinutes()).append(".").append(dateNow.getSeconds()).append("_").toString();
    //String appName = request.getContextPath();
 
Blob blob = POSDAO.getDistHistoryExcel((String)request.getAttribute(SCMInterfaceKey.DIST_REQUEST_ID));
    InputStream is = blob.getBinaryStream();
    BufferedOutputStream output = null;
String filePath = request.getRealPath("/scm/upload")+Slach+strdate+"file.xls";

    try
      {

        output = new BufferedOutputStream(new FileOutputStream(filePath));
        int data = -1;
        while ((data = is.read()) != -1)
          {
            output.write(data);
          }
      }
    finally
      {
        output.flush();
        output.close();
        is.close();
      }
dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, strdate+"file.xls");
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
            document.GenerateSheet.action=document.GenerateSheet.action+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+'<%=SCMInterfaceKey.VIEW_STK_DIST_REQUEST_EXCEL%>';
            document.GenerateSheet.submit();

        }
    </script>
    <script>
        document.GenerateSheet.submit();

    </script>
</html>
