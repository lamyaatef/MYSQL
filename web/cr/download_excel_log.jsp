<%-- 
    Document   : download_barcode_excel
    Created on : 14/12/2010, 17:28:38
    Author     : Ahmed Adel
--%>


<%@page import="java.sql.Connection"%>
<%@page import="com.mobinil.sds.core.utilities.Utility"%>
<%@page import="com.mobinil.sds.core.system.cr.importlog.dao.ImportLogDAO"%>
<%@page import="com.mobinil.sds.web.interfaces.cr.ContractRegistrationInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.cr.importlog.model.ImportLogModel"%>
<%@page import="java.sql.Blob"%>

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
       Connection con = Utility.getConnection();
         HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String Slach = System.getProperty("file.separator");
    String logId = (String) dataHashMap.get(ContractRegistrationInterfaceKey.DOWNLOAD_EXCEL_LOG_ID);
    ImportLogModel excelModel=ImportLogDAO.SearchImportLogByID(con,logId);
    String fileName = "log_"+excelModel.getFileName()+".html";
Blob blob = excelModel.getM_blbExcelImportLogfile();
    InputStream is = blob.getBinaryStream();
    BufferedOutputStream output = null;
String filePath = request.getRealPath("/cr/upload")+Slach+fileName;

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
    Utility.closeConnection(con);
dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, fileName);
dataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "cr"+Slach+"upload"+Slach);
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
            history.go(-1);
            
            
        }
    </script>
    <script>
        document.GenerateSheet.submit();       

    </script>
</html>
