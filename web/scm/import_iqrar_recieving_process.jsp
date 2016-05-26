<%-- 
    Document   : import_iqrar_recieving_process
    Created on : Oct 25, 2010, 10:46:14 AM
    Author     : AHMED SAFWAT
--%>


<%@
page contentType="text/html;charset=windows-1252"
     import="com.mobinil.sds.core.utilities.Utility"
     import="org.apache.commons.fileupload.*"
     import="java.util.*"
     import="com.mobinil.sds.web.interfaces.*"
     import="com.mobinil.sds.web.interfaces.scm.*"
     import="com.mobinil.sds.core.system.sa.importdata.*"
     import="com.mobinil.sds.core.system.sa.importdata.model.*"
     import="java.io.*"
     import="java.sql.Connection"
     import="com.mobinil.sds.core.system.scm.dao.*"
     import="com.mobinil.sds.core.system.scm.model.*"
     import="java.lang.String.*"
     %>
<%
            String appName = request.getContextPath();

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    </head>
    <body>
        <%
                    DiskFileUpload upload = new DiskFileUpload();
                    List items = upload.parseRequest(request);
                    String fileUniqueName = "";
                    String baseDirectory = request.getRealPath("/scm/upload/");

                    HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

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
                    Connection conn = Utility.getConnection();

                     IqrarRecievingDAO.truncateIqrarRecievingTempTable(conn);

                    DataImportEngine importEngine = new DataImportEngine();

                    DataImportReport importReport = importEngine.ImportFile(baseDirectory + fileUniqueName,
                            "INSERT", SCMInterfaceKey.QUERY_IQRAR_RECIEVING_TABLE);

                    Vector report = importReport.getReport();




                    Vector allPOSIqrarRecieving = IqrarRecievingDAO.getIqrarRecievingPOSImported(conn);

                    int processed = allPOSIqrarRecieving.size();

                    Vector invalidPOS = new Vector();
                    Vector invalidIqrarRecieving = new Vector();
                    Vector invalidNoSTKOwn=new Vector();


                    for (int i = 0; i < allPOSIqrarRecieving.size(); i++) {
                        
                        IqrarRecievingModel iqrarRecievingModel = (IqrarRecievingModel) allPOSIqrarRecieving.get(i);
                        long dcmId = IqrarRecievingDAO.checkPOS(conn, iqrarRecievingModel);
                        if (dcmId < 0) {
                            invalidPOS.add(iqrarRecievingModel);
                            processed--;
                            continue;
                        }

                        int checkFlag=IqrarRecievingDAO.checkIfPOSOwnsSTKOrNot(conn, dcmId);
                        if(checkFlag<=0){
                            invalidNoSTKOwn.add(iqrarRecievingModel);
                            processed--;
                            continue;
                        }

                         Date iqrarRecievingDate= IqrarRecievingDAO.checkPOSIqrarRecieved(conn, dcmId);
                        if (iqrarRecievingDate!=null) {
                            iqrarRecievingModel.setIqrarRecievedDate(iqrarRecievingDate);
                            invalidIqrarRecieving.add(iqrarRecievingModel);
                            processed--;
                            continue;
                        }
                         IqrarRecievingDAO.updatePosIqrarRecievedVerification(conn, dcmId);
                    }

                    IqrarRecievingDAO.truncateIqrarRecievingTempTable(conn);
        %>
        <br>
        <p><B>Report of the imported POS iqrar recieving</B></p>
        <br>
        <hr>
        <br>
        <ul>
            <li>Can't import (Invalid data):<%=report.size()%></li>
            <div align="center" >



                <%
                            int reportVectorSize = 0;
                            if (report != null && report.size() != 0) {
                                reportVectorSize = report.size();
                %>

                <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border="1">
                    <tr><td class=TableHeader nowrap align=center >ROW_NUM</td><td  class=TableHeader nowrap align=center>CELL NAME</td><td class=TableHeader nowrap align=center>ERROR MESSAGE</td></tr>
                    <%
                                }
                                for (int i = 0; i < reportVectorSize; i++) {
                                    ErrorInfo errorInfo = (ErrorInfo) report.get(i);
                    %>


                    <tr><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=errorInfo.getRowNum() + 1%></td><td align="center"  style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=errorInfo.getCellName()%></td><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=errorInfo.getErrorMsg()%></td></tr>
                    <%
                                }
                    %>
                </table>
            </div>
             <li>Invalid POS:<%=invalidPOS.size()%></li>
            <div align="center" >


                <%
                            int invalidPOSCodeVectorSize = 0;
                            if (invalidPOS != null && invalidPOS.size() != 0) {
                                invalidPOSCodeVectorSize = invalidPOS.size();
                %>
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border="1">
                    <tr><td class=TableHeader nowrap align=center >POS_CODE</td></tr>

                    <%
                                }
                                for (int i = 0; i < invalidPOSCodeVectorSize; i++) {
                                    IqrarRecievingModel iqrarRecievingModel = (IqrarRecievingModel) invalidPOS.get(i);
                    %>


                    <tr><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=iqrarRecievingModel.getPosCode() %></td></tr>
                    <%
                                }
                    %>
                </table>
            </div>
             <li>Invalid POS, POS doesn't own STK :<%=invalidNoSTKOwn.size()%></li>
            <div align="center" >


                <%
                            int invalidNoSTKOwnVectorSize = 0;
                            if (invalidNoSTKOwn != null && invalidNoSTKOwn.size() != 0) {
                                invalidNoSTKOwnVectorSize = invalidNoSTKOwn.size();
                %>
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border="1">
                    <tr><td class=TableHeader nowrap align=center >POS_CODE</td></tr>

                    <%
                                }
                                for (int i = 0; i < invalidNoSTKOwnVectorSize; i++) {
                                    IqrarRecievingModel iqrarRecievingModel = (IqrarRecievingModel) invalidNoSTKOwn.get(i);
                    %>


                    <tr><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=iqrarRecievingModel.getPosCode() %></td></tr>
                    <%
                                }
                    %>
                </table>
            </div>
            <li>Invalid iqrar recieving, iqrar already recieved:<%=invalidIqrarRecieving.size()%></li>
            <div align="center" >


                <%
                            int invalidIqrarRecievingVectorSize = 0;
                            if (invalidIqrarRecieving != null && invalidIqrarRecieving.size() != 0) {
                                invalidIqrarRecievingVectorSize = invalidIqrarRecieving.size();
                %>
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border="1">
                    <tr><td class=TableHeader nowrap align=center >POS_CODE</td><td  class=TableHeader nowrap align=center>Iqrar Recieved Date</td></tr>
                    <%
                                }

                                for (int i = 0; i < invalidIqrarRecievingVectorSize; i++) {
                                    IqrarRecievingModel iqrarRecievingModel = (IqrarRecievingModel) invalidIqrarRecieving.get(i);
                                    Date iqrarRecievedDate=iqrarRecievingModel.getIqrarRecievedDate();
                    %>


                    <tr><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=iqrarRecievingModel.getPosCode() %></td><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=iqrarRecievedDate.getDate()%>/<%=iqrarRecievedDate.getMonth()+1 %>/<%=iqrarRecievedDate.getYear()+1900 %></td></tr>
                    <%
                                }
                    %>
                </table>
            </div>
 
            <li><B>Processed successfully:&nbsp;<%=processed%></B></li>
        </ul>
    </body>
</html>
