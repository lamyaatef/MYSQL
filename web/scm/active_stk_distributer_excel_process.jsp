<%@page import="org.apache.poi.hssf.usermodel.HSSFRow"%>
<%@
page contentType="text/html;charset=windows-1252"
	import="com.mobinil.sds.core.utilities.Utility"
	import="org.apache.commons.fileupload.*" import="java.util.*"
	import="com.mobinil.sds.web.interfaces.*"
	import="com.mobinil.sds.web.interfaces.scm.*"
	import="com.mobinil.sds.core.system.sa.importdata.*"
	import="com.mobinil.sds.core.system.sa.importdata.model.*"
        import="com.mobinil.sds.core.system.scm.model.*"
	import="com.mobinil.sds.core.system.scm.dao.*" 
        import="java.io.*"
        import="java.sql.*"
        %>
<%!public void printToStream(String s, JspWriter out) throws Exception
	{
		out.println(s);
	}%>

<%
	String appName = request.getContextPath();
                    DiskFileUpload upload = new DiskFileUpload();
                    List items = upload.parseRequest(request);
                    String fileUniqueName = "";
                    String Slach = System.getProperty("file.separator");
                    String baseDirectory = request.getRealPath(Slach + "scm" + Slach + "upload" + Slach)+Slach;
                    String ActivestkforDistributer = appName + "/servlet/com.mobinil.sds.web.controller.WebControllerServlet?";
                    String DCM_Code = (String) request.getParameter(SCMInterfaceKey.DISTRIBUTER_CONTROL_SELECT);
                    HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
                    String fileNameOnClient = "";
                    String Message = (String) dataHashMap.get(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE);
                    if (Message.equals("")) {
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


                        Connection con;
                        Boolean isContinue = false;
                        String fileName = "";
                        try {
                            con = Utility.getConnection();
                            STKDAO.deleteInvalidRows(con,false);
                        DataImportEngine importEngine = new DataImportEngine();

                        DataImportReport importReport = importEngine.ImportFileWithRowNumber(baseDirectory + fileUniqueName,
                                SCMInterfaceKey.INSERT_OPERATION, "56");
                                
                            STKDAO.updateAllTempTable(DCM_Code, con);
                            STKDAO.beforeImportTOActive(DCM_Code, con);
                            Vector<CaseModel> STKInvalid = STKDAO.getValidInvalidRows(con, false);
                            Vector<CaseModel> STKvalid = STKDAO.getValidInvalidRows(con, true);
                            PoiWriteExcelFile excelWriter = new PoiWriteExcelFile();
                            HashMap<Integer, HSSFRow> validRows = excelWriter.buildPreActivateExcel(STKInvalid, STKvalid, baseDirectory + fileUniqueName,
                                    baseDirectory);
                            fileName = excelWriter.getOldPath();
                            request.getSession(false).putValue(SCMInterfaceKey.HASHMAP_VALID_ROWS, validRows);
                            request.getSession(false).putValue(SCMInterfaceKey.EXCEL_FILE_PATH , fileName);
                             isContinue =  validRows!=null && !validRows.isEmpty()? true : false;
                             if (!isContinue)
                                 {
                             STKDAO.deleteInvalidRows(con,true);
                             }

                            Utility.closeConnection(con);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

        {

		
                


                               
                                %>
                               <form name="activestks" id="activestks"  method="post" action="<%=ActivestkforDistributer%>">
                                   <input type="hidden"  name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="<%=SCMInterfaceKey.ACTIVE_DOWNLOAD_PREACTIVTE_EXCEL %>">
                                   <input type="hidden"  name="<%=SCMInterfaceKey.BASE_DIR %>" value="<%=baseDirectory%>">
                                   <input type="hidden"  name="<%=SCMInterfaceKey.OLD_FILE_PATH %>" value="<%=baseDirectory + fileName %>">
                                   <input type="hidden" name="<%=SCMInterfaceKey.POS_CODE%>" value="<%=DCM_Code%>">
                                   <input type="hidden" name="<%=SCMInterfaceKey.IS_CONTINUE%>" value="<%=isContinue%>">
                                   <script>
                                       document.activestks.submit();
                                   </script>
                        </form>
<%

			

		}


	}
        else
           { out.print("<h3>");
            out.print("This Distributer Code doesn't exist");
            out.print("</h3>");
           %>
         <input type="button" name="Back" value="Back" onclick="history.go(-1);" align="middle">

       <% }



%>
