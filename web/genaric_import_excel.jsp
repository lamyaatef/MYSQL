<%-- 
    Document   : genaric_import_excel
    Created on : 13/12/2010, 15:20:32
    Author     : Ahmed Adel
--%>

<%@ page contentType="text/html;charset=windows-1252"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"
              import="com.mobinil.sds.core.system.scm.model.*"
              import="com.mobinil.sds.core.system.sa.importdata.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
               import="com.mobinil.sds.core.utilities.Utility"
              %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <%
    String appName = request.getContextPath();
    String action=appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
    +InterfaceKey.HASHMAP_KEY_ACTION+"="
    +"genaric_excel_sheet"+"&"+"table_id="+"55";

    String Slach=System.getProperty("file.separator");
            String baseDirectory = request.getRealPath(Slach+"scm"+Slach+"upload"+Slach);
            String tableId = (String) request.getParameter("table_id");
            
            
            
%>
    <body>
        <form name="myform" action="<%=action%>" method="post" enctype="multipart/form-data">
            <input type="text" name="table_id">
            <input type="file" name="myfile">
            <input type="button" onclick="submit();">
        </form>
    </body>

<%
if (tableId!=null&&tableId!=""){
            DiskFileUpload upload = new DiskFileUpload();
            List items = upload.parseRequest(request);
            String fileUniqueName = "";
            String fileNameOnClient = "";
            Iterator itr = items.iterator();
            if(itr!=null){
             while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                // check if the current item is a form field or an uploaded file
                if (item.isFormField()) {
                    // get the name of the field
                    String fieldName = item.getFieldName();

                    // if it is name, we can set it in request to thank the user
                    if (fieldName.equals("name")) {
                        request.setAttribute("msg",
                                "Thank You: " + item.getString());
                    }
                    } else {
           
            try {
                        fileNameOnClient = item.getName();
                        Utility.logger.debug("fileNameOnClient"
                                + fileNameOnClient);
                        fileUniqueName = System.currentTimeMillis() + ".xls";
                        File savedFile = new File(baseDirectory
                                + fileUniqueName);
                        Utility.logger.debug("file " + savedFile);
                        item.write(savedFile);
                    } catch (Exception e) {
                        out.println("Error File can not be imported");
                        e.printStackTrace();
                        return;
                    }
            }
     DataImportEngine importEngine = new DataImportEngine();
                if(fileUniqueName!=""){
            DataImportReport importReport = importEngine.ImportFile(baseDirectory + fileUniqueName,
                    "INSERT", tableId);
            Vector report = importReport.getReport();
            }
                }
             }
            }
%>
<script>
function submit()
{
    document.myform.action = document.myform.action+'&'+'table_id='+document.myform.table_id.value
  document.myform.submit();

}
</script>



</html>
