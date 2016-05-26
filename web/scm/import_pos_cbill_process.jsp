<%-- 
    Document   : import_pos_cbill_process
    Created on : Oct 20, 2010, 9:00:11 AM
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
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
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

                    POSCbillDAO.truncatePOSCbillTempTable(conn);

                    DataImportEngine importEngine = new DataImportEngine();

                    DataImportReport importReport = importEngine.ImportFile(baseDirectory + fileUniqueName,
                            "INSERT", SCMInterfaceKey.QUERY_POS_CBILL_TABLE);

                    Vector report = importReport.getReport();




                    Vector allCbill = POSCbillDAO.getPOSImportedCBill(conn);

                    int processed = allCbill.size();
                    Vector invalidStk = new Vector(5);
                    Vector invalidPOS = new Vector(5);
                    Vector invalidPOSOwnStk = new Vector(5);
                    Vector emptyCaseId = new Vector(5);
                    Vector susbendedSTK = new Vector(5);

                    for (int i = 0; i < allCbill.size(); i++) {
                        POSCbillModel posCbillModel = (POSCbillModel) allCbill.get(i);
                        if (posCbillModel.getCaseId() == null || posCbillModel.getCaseId().trim() == "") {
                            emptyCaseId.add(posCbillModel);
                            processed--;
                            continue;
                        }


                        long dcmId = POSCbillDAO.checkPOS(conn, posCbillModel);
                        if (dcmId < 0) {
                            invalidPOS.add(posCbillModel);
                            processed--;
                            continue;
                        }

                        long stkId = POSCbillDAO.checkSTK(conn, posCbillModel);
                        if (stkId < 0) {
                            invalidStk.add(posCbillModel);
                            processed--;
                            continue;
                        }

                        STKOwnerModel stkOwnerModel = POSCbillDAO.checkPOSOwnSTK(conn, stkId, dcmId);
                        if (stkOwnerModel==null) {
                            invalidPOSOwnStk.add(posCbillModel);
                            processed--;
                            continue;
                        }else{
                            if(stkOwnerModel.getStkStatusId()==7){
                                susbendedSTK.add(posCbillModel);
                                processed--;
                                continue;
                            }
                        }
                            int integerSTK=(int)stkId;
                        POSCbillDAO.updatePOSVerification(conn, dcmId, stkId);
                        POSCbillDAO.importcbillCase(posCbillModel.getCaseId(),Integer.toString(integerSTK));
                    }

                    POSCbillDAO.truncatePOSCbillTempTable(conn);
        %>
        <br>
        <p><B>Report of the Imported IVR activation file</B></p>
        <br>
        <hr>
        <br>
        <ul>
            <li>Can't Import (Invalid Data):<%=report.size()%></li>
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
            <li>No Case ID:<%=emptyCaseId.size()%></li>
            <div align="center" >


                <%
                            int emptyCaseIdVectorSize = 0;
                            if (emptyCaseId != null && emptyCaseId.size() != 0) {
                                emptyCaseIdVectorSize = emptyCaseId.size();
                %>
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border="1">
                    <tr><td class=TableHeader nowrap align=center >POS_CODE</td><td  class=TableHeader nowrap align=center>STK_NO</td><td class=TableHeader nowrap align=center>CASE_ID</td></tr>

                    <%
                                }
                                for (int i = 0; i < emptyCaseIdVectorSize; i++) {
                                    POSCbillModel posCbillModel = (POSCbillModel) emptyCaseId.get(i);
                    %>


                    <tr><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posCbillModel.getPOSCode()%></td><td align="center"  style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posCbillModel.getSTKNo()%></td><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posCbillModel.getCaseId() == null ? "" : posCbillModel.getCaseId()%></td></tr>
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
                    <tr><td class=TableHeader nowrap align=center >POS_CODE</td><td  class=TableHeader nowrap align=center>STK_NO</td><td class=TableHeader nowrap align=center>CASE_ID</td></tr>

                    <%
                                }
                                for (int i = 0; i < invalidPOSCodeVectorSize; i++) {
                                    POSCbillModel posCbillModel = (POSCbillModel) invalidPOS.get(i);
                    %>


                    <tr><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posCbillModel.getPOSCode()%></td><td align="center"  style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posCbillModel.getSTKNo()%></td><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posCbillModel.getCaseId() == null ? "" : posCbillModel.getCaseId()%></td></tr>
                    <%
                                }
                    %>
                </table>
            </div>
            <li>Invalid STK:<%=invalidStk.size()%></li>
            <div align="center" >


                <%
                            int invalidSTKNoVectorSize = 0;
                            if (invalidStk != null && invalidStk.size() != 0) {
                                invalidSTKNoVectorSize = invalidStk.size();
                %>
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border="1">
                    <tr><td class=TableHeader nowrap align=center >POS_CODE</td><td  class=TableHeader nowrap align=center>STK_NO</td><td class=TableHeader nowrap align=center>CASE_ID</td></tr>
                    <%
                                }

                                for (int i = 0; i < invalidSTKNoVectorSize; i++) {
                                    POSCbillModel posCbillModel = (POSCbillModel) invalidStk.get(i);
                    %>


                    <tr><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posCbillModel.getPOSCode()%></td><td align="center"  style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posCbillModel.getSTKNo()%></td><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posCbillModel.getCaseId() == null ? "" : posCbillModel.getCaseId()%></td></tr>
                    <%
                                }
                    %>
                </table>
            </div>
            <li>Invalid POS-STK Ownership:<%=invalidPOSOwnStk.size()%></li>
            <div align="center" >

                <%
                            int invalidPOSOwnSTKVectorSize = 0;
                            if (invalidPOSOwnStk != null && invalidPOSOwnStk.size() != 0) {
                                invalidPOSOwnSTKVectorSize = invalidPOSOwnStk.size();
                %>
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border="1">
                    <tr><td class=TableHeader nowrap align=center >POS_CODE</td><td  class=TableHeader nowrap align=center>STK_NO</td><td class=TableHeader nowrap align=center>CASE_ID</td></tr>
                    <%
                                }
                                for (int i = 0; i < invalidPOSOwnSTKVectorSize; i++) {
                                    POSCbillModel posCbillModel = (POSCbillModel) invalidPOSOwnStk.get(i);
                    %>


                    <tr><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posCbillModel.getPOSCode()%></td><td align="center"  style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posCbillModel.getSTKNo()%></td><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posCbillModel.getCaseId() == null ? "" : posCbillModel.getCaseId()%></td></tr>
                    <%
                                }
                    %>
                </table>
            </div>
            <li>Susbended STK :<%=susbendedSTK.size()%></li>
            <div align="center" >

                <%
                            int susbendedSTKVectorSize = 0;
                            if (susbendedSTK != null && susbendedSTK.size() != 0) {
                                susbendedSTKVectorSize = susbendedSTK.size();
                %>
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border="1">
                    <tr><td class=TableHeader nowrap align=center >POS_CODE</td><td  class=TableHeader nowrap align=center>STK_NO</td><td class=TableHeader nowrap align=center>CASE_ID</td></tr>
                    <%
                                }
                                for (int i = 0; i < susbendedSTKVectorSize; i++) {
                                    POSCbillModel posCbillModel = (POSCbillModel) susbendedSTK.get(i);
                    %>


                    <tr><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posCbillModel.getPOSCode()%></td><td align="center"  style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posCbillModel.getSTKNo()%></td><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posCbillModel.getCaseId() == null ? "" : posCbillModel.getCaseId()%></td></tr>
                    <%
                                }
                    %>
                </table>
            </div>

            <li><B>Processed Successfully:&nbsp;<%=processed%></B></li>
        </ul>
    </body>
</html>
