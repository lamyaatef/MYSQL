<%-- 
    Document   : assign_many_pos_to_group_process
    Created on : Nov 1, 2010, 1:14:12 PM
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
            String formName="excelBack";
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            int groupId = (Integer) dataHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID);
            String groupName = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <script>
            function viewAssign(groupId,groupName){
            document.<%=formName%>.<%=SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID%>.value=groupId;
            document.<%=formName%>.<%=SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME%>.value=groupName;
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_ASSIGN_UNASSIGN_POS_TO_GROUP%>";
            document.<%=formName%>.submit();
            }
        </script>
    </head>
    <body>
        <%
                    DiskFileUpload upload = new DiskFileUpload();
                    List items = upload.parseRequest(request);
                    String fileUniqueName = "";
                    String baseDirectory = request.getRealPath("/scm/upload/");


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

                    POSGroupsDAO.truncatePOSGroupTempTable(conn);

                    DataImportEngine importEngine = new DataImportEngine();

                    DataImportReport importReport = importEngine.ImportFile(baseDirectory + fileUniqueName,
                            "INSERT", SCMInterfaceKey.QUERY_POS_GROUP_TABLE);

                    Vector report = importReport.getReport();




                    Vector posForGroup = POSGroupsDAO.getPOSImported(conn);

                    int processed = posForGroup.size();

                    Vector invalidPOS = new Vector(5);
                    Vector invalidPOSAlreadyExist=new Vector();



                    for (int i = 0; i < posForGroup.size(); i++) {
                        POSModel posModel = (POSModel) posForGroup.get(i);
                        int dcmId = POSGroupsDAO.checkPOS(conn, posModel.getPOS_Code());
                        if (dcmId < 0) {
                            invalidPOS.add(posModel);
                            processed--;
                            continue;
                        }

                        if(POSGroupsDAO.checkPOSAlreadyExistInTheGroup(conn, groupId,dcmId)){
                            invalidPOSAlreadyExist.add(posModel);
                            processed--;
                            continue;

                        }


                       POSGroupsDAO.assingPOSToGroup(conn, groupId, dcmId);
                    }

                    POSGroupsDAO.truncatePOSGroupTempTable(conn);
        %>
        <br>
        <p><B>Report of the Imported POS to Group :<%=groupName%></B></p>
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
                                    POSModel posModel = (POSModel) invalidPOS.get(i);
                    %>


                    <tr><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posModel.getPOS_Code()%></td></tr>
                    <%
                                }
                    %>
                </table>
            </div>
             <li>Invalid POS, Already Exist In the Group:<%=invalidPOSAlreadyExist.size()%></li>
            <div align="center" >


                <%
                            int invalidPOSAlreadyExistVectorSize = 0;
                            if (invalidPOSAlreadyExist != null && invalidPOSAlreadyExist.size() != 0) {
                                invalidPOSAlreadyExistVectorSize = invalidPOSAlreadyExist.size();
                %>
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border="1">
                    <tr><td class=TableHeader nowrap align=center >POS_CODE</td></tr>

                    <%
                                }
                                for (int i = 0; i < invalidPOSAlreadyExistVectorSize; i++) {
                                    POSModel posModel = (POSModel) invalidPOSAlreadyExist.get(i);
                    %>


                    <tr><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posModel.getPOS_Code()%></td></tr>
                    <%
                                }
                    %>
                </table>
            </div>
              <li><B>Processed Successfully:&nbsp;<%=processed%></B></li>
        </ul>
         <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
                 <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
                <input type="hidden" name="<%=SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID%>" value="-1">
                <input type="hidden" name="<%=SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME%>" value="-1">
              <center><input type="button" class="button" value="Back" onclick="viewAssign(<%=groupId%>,'<%=groupName%>');" name="Submit"></center>
         </form>
    </body>
</html>

