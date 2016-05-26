<%@ 
page contentType="text/html;charset=windows-1252"
     import="com.mobinil.sds.core.utilities.Utility"
     import="org.apache.commons.fileupload.*" import="java.util.*"
     import="com.mobinil.sds.web.interfaces.*"
     import="com.mobinil.sds.web.interfaces.scm.*"
     import="com.mobinil.sds.core.system.sa.importdata.*"
     import="com.mobinil.sds.core.system.sa.importdata.model.*"
     import="com.mobinil.sds.core.system.scm.dao.*" import="java.io.*"%>
<%!public void printToStream(String s, JspWriter out) throws Exception {
        out.println(s);
    }%>

<%
            String appName = request.getContextPath();
            DiskFileUpload upload = new DiskFileUpload();
            List items = upload.parseRequest(request);
            String fileUniqueName = "";
            String Slach=System.getProperty("file.separator");
            String baseDirectory = request.getRealPath(Slach+"scm"+Slach+"upload"+Slach);
            String tableId = (String) request.getParameter(SCMInterfaceKey.IMPORT_TABLE);
            String operation = (String) request.getParameter(SCMInterfaceKey.QUERY_STRING_OPERATION);
            String stockId = (String) request.getParameter(SCMInterfaceKey.CONTROL_SELECT_STOCK_TYPE);
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String strUserID = request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
            String fileNameOnClient = "";

            Iterator itr = items.iterator();

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
                    // the item must be an uploaded file save it to disk. Note that there
                    // seems to be a bug in item.getName() as it returns the full path on
                    // the client's machine for the uploaded file name, instead of the file
                    // name only. To overcome that, I have used a workaround using
                    // fullFile.getName().
                    //	File fullFile  = new File(item.getName());

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
            }

            ;
            Vector errorVector = new Vector();
            Vector statusVector = new Vector();

            DataImportEngine importEngine = new DataImportEngine();

            DataImportReport importReport = importEngine.ImportFileWithRowNumber(baseDirectory + fileUniqueName,
                    "INSERT", tableId);

            Vector report = importReport.getReport();
            int numOfRecordsUpdated = importReport.getNumOfRecordsUpdated();

            // if numOfRecordsUpdated> 0
            // bussines process check
            // get errors add them to the report
            // then conduct final insert
            // truncate

            ArrayList<Integer> duplicateRows = new ArrayList<Integer>();
            ArrayList<Integer> usedrows = new ArrayList<Integer>();
            ArrayList<Integer> nonExist = new ArrayList<Integer>();
            int importedRows = 0;
            int rowerrors = report.size();
            int deletedrowsno = 0;
            if (operation.compareTo("INSERT") == 0) {
                STKDAO convert = new STKDAO();
                duplicateRows = convert.beforeInsertSTK();
                importedRows = convert.InsertSTK(strUserID,stockId);

            } else if (operation.compareTo("DELETE") == 0) {
                STKDAO convert = new STKDAO();
                deletedrowsno = convert.deleteSTK(strUserID,stockId);
                usedrows = convert.getUsedSTKNonDelete();
                nonExist=convert.afterdelete();

            }

            {

                printToStream("<LINK REL=STYLESHEET TYPE=\"text/css\" HREF=\""
                        + appName + "/resources/css/Template1.css\">", out);
                printToStream("<script type=\"text/javascript\">", out);
                printToStream("function Toggle(item) {", out);
                printToStream("obj=document.getElementById(item);", out);
                printToStream("if (obj!=null) {", out);
                printToStream("visible=(obj.style.display!=\"none\")", out);
                printToStream("key=document.getElementById(\"x\" + item);", out);
                printToStream("if (visible) {", out);
                printToStream("obj.style.display=\"none\";", out);
                printToStream("key.innerHTML=\"<img src=\'" + appName
                        + "/resources/img/plus.gif\'>\";", out);
                printToStream("} else {", out);
                printToStream("obj.style.display=\"block\";", out);
                printToStream("key.innerHTML=\"<img src='" + appName
                        + "/resources/img/minus.gif\'>\";", out);
                printToStream("}}}", out);
                printToStream("</script>", out);

                if (report.size() == 0 && duplicateRows.isEmpty()
                        && operation.compareTo("INSERT") == 0) {
                    printToStream("<h3>", out);
                    printToStream("Operation completed successfully "
                            + importedRows + " STKs were imported", out);
                    printToStream("</h3>", out);

                } else if (report.size() == 0 && usedrows.isEmpty()
                        &&  nonExist.isEmpty() && operation.compareTo("DELETE") == 0) {
                    printToStream("<h3>", out);
                    out.println(deletedrowsno + " STKs were deleted");
                    printToStream("</h3>", out);

                } else if (operation.compareTo("INSERT") == 0) {
                    printToStream("<h3>", out);
                    printToStream("Errors Report", out);
                    printToStream("</h3>", out);

                    if (report.size() != 0) {
                        printToStream(
                                "<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",
                                out);
                        printToStream("<TR class=TableHeader>", out);
                        printToStream(
                                "<td size =20% nowrap align=left>Row Num</td>",
                                out);
                        printToStream(
                                "<td size= 10% nowrap align=left>Cell Name</td>",
                                out);
                        printToStream(
                                "<td size =10% nowrap align=left>Error Message</td>",
                                out);
                        printToStream("</tr>", out);

                        for (int i = 0; i < report.size(); i++) {
                            ErrorInfo error = (ErrorInfo) report.get(i);

                            printToStream("<tr>", out);
                            printToStream(
                                    "<TD class=TableTextNote align=left size=\"20%\">",
                                    out);
                            if (error.getRowNum() > -1) {
                                printToStream((error.getRowNum()+1) + "", out);
                            }
                            printToStream("</td>", out);
                            printToStream(
                                    "<TD class=TableTextNote align=left size=\"20%\">",
                                    out);
                            printToStream(error.getCellName(), out);
                            printToStream("</td>", out);
                            printToStream(
                                    "<TD class=TableTextNote align=left size=\"60%\">",
                                    out);
                            printToStream(error.getErrorMsg(), out);
                            printToStream("</td>", out);

                            printToStream("</tr>", out);

                        }

                        printToStream("</TABLE>", out);
                    }
                    printToStream("<h3>", out);
                    printToStream(importedRows + duplicateRows.size() + rowerrors + " STKs You Try To Import", out);
                    printToStream("</h3>", out);

                    printToStream("<h3>", out);
                    out.println(importedRows + " STKs were imported");
                    printToStream("</h3>", out);

                    printToStream("<h3>", out);
                    if (duplicateRows.isEmpty()) {
                        out.println("0 STKs were already existed");
                    } else {
                        out.println(duplicateRows.size()
                                + " STKs were already existed");
                    }

                    String excelLink = PoiWriteExcelFile.ExportExcelInsert(duplicateRows, baseDirectory);
                    printToStream("</h3>", out);

                    printToStream("<h3>", out);
                    out.println(rowerrors + " STKs errors not imported");
                    printToStream("</h3>", out);



                    dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, excelLink);
                    dataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "scm"+Slach+"upload"+Slach);



                    session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, dataHashMap);
%>
<div name="Excel" id="Excel" align="center">
    <%out.println("<form action=\"com.mobinil.sds.web.controller.UploadingFileServlet\" name=\"GenerateSheet\" method=\"post\">");
    %>
    <input class="button"  type="submit" name="Submit" value="Download Excel Sheet" id="download" onclick="Sheet();"/>
    <%
                                                             out.println("</form>");%>
</div>
<%			} else if (operation.compareTo("DELETE") == 0) {
                            printToStream("<h3>", out);
                            printToStream("Errors Report", out);
                            printToStream("</h3>", out);

                            if (report.size() != 0) {
                                printToStream(
                                        "<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",
                                        out);
                                printToStream("<TR class=TableHeader>", out);
                                printToStream(
                                        "<td size =20% nowrap align=left>Row Num</td>",
                                        out);
                                printToStream(
                                        "<td size= 10% nowrap align=left>Cell Name</td>",
                                        out);
                                printToStream(
                                        "<td size =10% nowrap align=left>Error Message</td>",
                                        out);
                                printToStream("</tr>", out);

                                for (int i = 0; i < report.size(); i++) {
                                    ErrorInfo error = (ErrorInfo) report.get(i);

                                    printToStream("<tr>", out);
                                    printToStream(
                                            "<TD class=TableTextNote align=left size=\"20%\">",
                                            out);
                                    if (error.getRowNum() > -1) {
                                        printToStream(error.getRowNum() + "", out);
                                    }
                                    printToStream("</td>", out);
                                    printToStream(
                                            "<TD class=TableTextNote align=left size=\"20%\">",
                                            out);
                                    printToStream(error.getCellName(), out);
                                    printToStream("</td>", out);
                                    printToStream(
                                            "<TD class=TableTextNote align=left size=\"60%\">",
                                            out);
                                    printToStream(error.getErrorMsg(), out);
                                    printToStream("</td>", out);

                                    printToStream("</tr>", out);

                                }

                                printToStream("</TABLE>", out);
                            }
                            printToStream("<h3>", out);
                            printToStream(deletedrowsno + usedrows.size() + rowerrors + nonExist.size() + "STKs You Try To Import", out);
                            printToStream("</h3>", out);

                            printToStream("<h3>", out);
                            out.println(deletedrowsno + " STKs were deleted");
                            printToStream("</h3>", out);

                            printToStream("<h3>", out);
                            out.println(nonExist.size() + " STKs were non exist");
                            printToStream("</h3>", out);

                            printToStream("<h3>", out);
                            if (usedrows.isEmpty()) {
                                out.println("0 STKs were in use");
                            } else {
                                out.println(usedrows.size()
                                        + " STKs were in use");
                            }

                            String excelLink = PoiWriteExcelFile.ExportExcelDelete(usedrows,nonExist, baseDirectory);
                            printToStream("</h3>", out);

                            printToStream("<h3>", out);
                            out.println(rowerrors + " STKs errors not imported");
                            printToStream("</h3>", out);



                            dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, excelLink);
                            dataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "scm"+Slach+"upload"+Slach);



                            session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, dataHashMap);
%>
<div name="Excel" id="Excel" align="center">
    <%out.println("<form action=\"com.mobinil.sds.web.controller.UploadingFileServlet\" name=\"GenerateSheet\" id=\"GenerateSheet\" method=\"post\">");
    %>
    <input class="button"  type="submit" name="Submit" id="download" value="Download Excel Sheet" onclick="Sheet();"/>
    <%
                                                             out.println("</form>");%>
</div>
<% }
              
}
	%>
<script>
    function Sheet()
    {
        document.GenerateSheet.Submit.disabled=true;
        document.GenerateSheet.submit();


    }

</script>
