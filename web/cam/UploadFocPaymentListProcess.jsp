<%-- 
    Document   : UploadFocPaymentListProcess
    Created on : Dec 24, 2012, 1:58:17 PM
    Author     : sand
--%>
<%@page import="com.mobinil.sds.core.system.cam.excelImport.ExcelImport"%>
<%@page import="com.mobinil.sds.core.system.sa.importdata.ErrorInfo"%>
<%@page import="com.mobinil.sds.core.system.sa.importdata.DataImportEngine"%>
<%@page import="com.mobinil.sds.core.system.sa.importdata.model.DataImportReport"%>
<%@page import="com.mobinil.sds.web.interfaces.sa.AdministrationInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.cam.payment.dao.PaymentScmStatusDao"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.io.File"%>
<%@page import="com.mobinil.sds.web.interfaces.InterfaceKey"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.mobinil.sds.core.utilities.Utility"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.fileupload.DiskFileUpload"%>
<%@ 
page contentType="text/html;charset=windows-1252"
     import ="com.mobinil.sds.core.utilities.Utility"
     import="org.apache.commons.fileupload.*"
     import="java.util.*"                          
     import="com.mobinil.sds.web.interfaces.*"
     import="com.mobinil.sds.web.interfaces.sa.*"
     import="com.mobinil.sds.core.system.sa.importdata.*"
     import="com.mobinil.sds.core.system.sa.importdata.model.*"

     import ="java.io.*"              
     %>


<%!
    public void printToStream(String s, JspWriter out) throws Exception {
        out.println(s);
    }
%>

<%
    String appName = request.getContextPath();
    DiskFileUpload upload = new DiskFileUpload();
    List items = upload.parseRequest(request);
    String fileUniqueName = "";
    String baseDirectory = request.getRealPath("/cam/uploadPaymentList/");
    
    String tableId = "74";
    
    String fileNameOnClient = "";

    Iterator itr = items.iterator();

    while (itr.hasNext()) {
        FileItem item = (FileItem) itr.next();

        if (item.isFormField()) {
            String fieldName = item.getFieldName();

            if (fieldName.equals("name")) {
                request.setAttribute("msg", "Thank You: " + item.getString());
            }

        } else {
            File fullFile = new File(item.getName());

            try {
                fileNameOnClient = item.getName();
                Utility.logger.debug("fileNameOnClient" + fileNameOnClient);
                fileUniqueName = System.currentTimeMillis() + ".xls";
                File savedFile = new File(baseDirectory + fileUniqueName);
                Utility.logger.debug("file " + savedFile);
                item.write(savedFile);
            } catch (Exception e) {
                out.println("Error File can not be imported");
                e.printStackTrace();
                return;
            }
        }
    }


    HashMap dataHashMap = null;
    dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String userID = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

    java.sql.Connection con = Utility.getConnection();
    Vector errorVector = new Vector();
    Vector statusVector = new Vector();
    
    PaymentScmStatusDao.deleteTmpScmCode(con);
    
    DataImportEngine importEngine = new DataImportEngine();

    DataImportReport importReport = importEngine.ImportFile(baseDirectory + fileUniqueName, AdministrationInterfaceKey.DATA_IMPORT_INSERT, tableId);
    Vector report = importReport.getReport();
    String operationName = importReport.getOperation();
    int numOfRecordsUpdated = importReport.getNumOfRecordsUpdated();
    
    
    
    
    
    
    
    Utility.closeConnection(con);
    out.println("Operation Was Completed");
    {

        if (operationName.compareTo("UPDATE") == 0) {
            out.println("Total Number Of Records Updated = " + numOfRecordsUpdated);
        }


        printToStream("<LINK REL=STYLESHEET TYPE=\"text/css\" HREF=\"" + appName + "/resources/css/Template1.css\">", out);
        printToStream("<script type=\"text/javascript\">", out);
        printToStream("function Toggle(item) {", out);
        printToStream("obj=document.getElementById(item);", out);
        printToStream("if (obj!=null) {", out);
        printToStream("visible=(obj.style.display!=\"none\")", out);
        printToStream("key=document.getElementById(\"x\" + item);", out);
        printToStream("if (visible) {", out);
        printToStream("obj.style.display=\"none\";", out);
        printToStream("key.innerHTML=\"<img src=\'" + appName + "/resources/img/plus.gif\'>\";", out);
        printToStream("} else {", out);
        printToStream("obj.style.display=\"block\";", out);
        printToStream("key.innerHTML=\"<img src='" + appName + "/resources/img/minus.gif\'>\";", out);
        printToStream("}}}", out);
        printToStream("</script>", out);








        if (report.size() == 0) {
            printToStream("<h3>", out);
            printToStream("Operation Completed Successfully: " + fileNameOnClient, out);
            printToStream("</h3>", out);

        } else {

            printToStream("<h3>", out);
            printToStream("Errors Report : " + fileNameOnClient, out);
            printToStream("</h3>", out);



            printToStream("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>", out);
            printToStream("<TR class=TableHeader>", out);
            printToStream("<td size =20% nowrap align=left>Row Num</td>", out);
            printToStream("<td size= 10% nowrap align=left>Cell Name</td>", out);
            printToStream("<td size =10% nowrap align=left>Error Message</td>", out);
            printToStream("</tr>", out);

            for (int i = 0; i < report.size(); i++) {
                ErrorInfo error = (ErrorInfo) report.get(i);
                printToStream("<tr>", out);
                printToStream("<TD class=TableTextNote align=left size=\"20%\">", out);
                if (error.getRowNum() > -1) {
                    printToStream((error.getRowNum() + 1) + "", out);
                }
                printToStream("</td>", out);
                printToStream("<TD class=TableTextNote align=left size=\"20%\">", out);
                printToStream(error.getCellName(), out);
                printToStream("</td>", out);
                printToStream("<TD class=TableTextNote align=left size=\"60%\">", out);
                printToStream(error.getErrorMsg(), out);
                printToStream("</td>", out);

                printToStream("</tr>", out);

            }


            printToStream("</TABLE>", out);

        }

    }

    String templatePath = request.getRealPath("/cam/template/");

    ExcelImport gen = new ExcelImport(userID);
    gen.exportFocPaymentList(templatePath + "/Dcm Details List.xls", templatePath + "/foc_payment_list.xls");
    
    System.out.print(appName);
    out.println("<form action=\"" + appName + "/cam/template/foc_payment_list.xls\" name=\"GenerateList\" method=\"post\">");
    out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_USER_ID + "\" value=" + userID + ">");

    out.println("</form>");
    out.println("<script type=\"text/javascript\">");
    out.println("GenerateList.submit();");
    out.println("</script>");


    %>
