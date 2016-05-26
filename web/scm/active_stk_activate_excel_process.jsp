<%@page import="com.mobinil.sds.core.utilities.DBUtil"%>
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

<%
	String appName = request.getContextPath();
                    DiskFileUpload upload = new DiskFileUpload();
                    List items = upload.parseRequest(request);
                    String fileUniqueName = "";
                    String Slach = System.getProperty("file.separator");
                    String baseDirectory = request.getRealPath(Slach + "scm" + Slach + "upload" + Slach)+Slach;
                    HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
                    String fileNameOnClient = "";
                    String Message = (String) dataHashMap.get(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE);
                    
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
                            String defPath = (String) request.getSession(false).getValue(SCMInterfaceKey.EXCEL_FILE_PATH);
                            HashMap<Integer, HSSFRow> validRows = (HashMap<Integer, HSSFRow>) request.getSession(false).getValue(SCMInterfaceKey.HASHMAP_VALID_ROWS);
                            PoiWriteExcelFile excelWriter = new PoiWriteExcelFile();
                            HashMap<String, HSSFRow> validRowsToActivate = excelWriter.getRowsToActivate(validRows, defPath);
                            HashMap<String, String> dcmByMobilNumber = STKDAO.getDCMByMobileNumber(con);
                            Statement st = con.createStatement();
                            String dcm_id =null;
                            for (String validMobileNumber : validRowsToActivate.keySet()) {
                               dcm_id = dcmByMobilNumber.get(validMobileNumber);
                                
                                if (dcm_id != null && dcm_id.compareTo("") != 0) {
                                    int rowCount = DistributerSTKDataDAO.activeForValidRows(st, dcm_id, validMobileNumber);
                                    if (rowCount == 0) {
                                      validRowsToActivate.remove(validMobileNumber);
                                    }
                                }
                              
                            }
                              DBUtil.close(st);
                            DistributerSTKDataDAO.doAfterActivate(con);

                            excelWriter.buildTemplateActivateExcel(baseDirectory, validRowsToActivate);
                            String templatePathIs = excelWriter.getTemplatePath();
                            dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, templatePathIs.substring(templatePathIs.lastIndexOf(Slach)+1, templatePathIs.length()));
                            dataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "scm" + Slach + "upload" + Slach);
                            session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, dataHashMap);
                            String path=baseDirectory+(String)dataHashMap.get(InterfaceKey.EXPORT_FILE_PATH);
                            DistributerSTKDataDAO.insertRequest(con, dcm_id,path);
                            Utility.closeConnection(con);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                                %>

                                <div align="center">
    <form action="com.mobinil.sds.web.controller.UploadingFileServlet" name="GenerateSheet" id="GenerateSheet" method="post">
    </form>
</div>
                                <script>
        document.GenerateSheet.submit();
    </script>
                             



	