<%@page import="com.mobinil.mcss.commissionLabel.LabelImportModelThree"%>
<%@page import="com.mobinil.mcss.commissionLabel.CommissionLabelDAO"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import ="java.io.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.web.interfaces.commission.*"  
         import="com.mobinil.sds.web.interfaces.payment.*"
         import="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.dcm.region.model.*"
         import="com.mobinil.sds.core.system.dcm.user.model.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.model.*"
         import="com.mobinil.sds.core.system.gn.dataview.dto.*"         
         import="com.mobinil.sds.core.system.commission.model.*"
         import="com.mobinil.sds.core.system.commission.dao.*"
         import="com.mobinil.sds.core.system.sa.importdata.model.*"
         import="com.mobinil.sds.core.system.payment.model.*"
         import="com.mobinil.sds.core.system.commission.factor.model.*"
         import="com.mobinil.sds.core.system.sa.importdata.*"
         import="com.mobinil.sds.core.system.sa.importdata.model.*"
         import="org.apache.commons.fileupload.*"

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
    String baseDirectory = request.getRealPath("/sop/upload/");

    String tableId = (String) request.getParameter(AdministrationInterfaceKey.QUERY_STRING_TABLES);
    System.out.println("The table id issssssssssssss " + tableId);
    String operation = (String) request.getParameter(AdministrationInterfaceKey.QUERY_STRING_OPERATION);
    System.out.println("The operation isssssssssssssssss " + operation);
    String strUserID = (String) request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
    String labelId = (String) request.getParameter(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
    System.out.println("The label id issssssssssssssssssssss " + labelId);
    String fileNameOnClient = "";

    Iterator itr = items.iterator();

    while (itr.hasNext()) {
        FileItem item = (FileItem) itr.next();

        //      System.out.println("Item:" + item);

        if (item.isFormField()) {
            // get the name of the field
            String fieldName = item.getFieldName();

            // if it is name, we can set it in request to thank the user
            if (fieldName.equals("name")) {
                request.setAttribute("msg", "Thank You: " + item.getString());
            }

        } else {
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

    Vector errorVector = new Vector();
    Vector statusVector = new Vector();

//Utility.logger.debug("inside upload_commission_data_process.jsp");

    java.sql.Connection con = Utility.getConnection();

    Vector vecCommissionLabelDetails = null;
    Vector vecCommissionLabelDetailsThree = null;
    LabelModel labelModel = CommissionLabelDAO.selectLabelData(con, labelId);


    System.out.println("Label Model Data 3:" + labelModel.getThreeValues() + " 2:" + labelModel.getTwoValues());

    if (labelModel.getTwoValues() == 1 && labelModel.getThreeValues() == 0) {
        System.out.println("Delete From 2 Values");
        CommissionDAO.deleteTmpCommissionLabel(con);
    } else {
        System.out.println("Delete From 3 Values");

        CommissionLabelDAO.deleteTmpCommissionLabelThree(con);

    }

    DataImportEngine importEngine = new DataImportEngine();

    DataImportReport importReport = importEngine.ImportFileWithRowNumber(baseDirectory + fileUniqueName, operation, tableId);
    Vector reportImport = importReport.getReport();



    Vector report = new Vector();

    String operationName = importReport.getOperation();
    int numOfRecordsUpdated = importReport.getNumOfRecordsUpdated();

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

        ErrorInfo contractError = null;

        if (labelModel.getTwoValues() == 1 && labelModel.getThreeValues() == 0) {
            vecCommissionLabelDetails = CommissionDAO.getTmpCommissionLabel(con);
        } else {
            vecCommissionLabelDetailsThree = CommissionLabelDAO.getTmpCommissionLabelThree(con);
        }

        java.sql.Statement stat = con.createStatement();

        if (vecCommissionLabelDetails != null) {
            Utility.logger.debug("The Vector  size  for 2 lables is " + vecCommissionLabelDetails.size());

            for (int i = 0; i < vecCommissionLabelDetails.size(); i++) {
                LabelImportModel labelImportModel = (LabelImportModel) vecCommissionLabelDetails.get(i);
                String tmRowNum = labelImportModel.getRowNum();
                String tmDcmCode = labelImportModel.getDcmCode();
                //Utility.logger.debug("The Dcm Code issssssssssssssssssssss " + tmDcmCode);
                String tmAmount = labelImportModel.getAmount();
                //Utility.logger.debug("The amount isssssssssssssssssss " + tmAmount);
                /////////////////////////////////////////////////////////////////////by Mohamed Youssef
                CommissionDAO.insertLabelDetailsFromExcel(labelId, tmDcmCode, tmAmount, stat);

            }
            stat.close();
            CommissionDAO.deleteTmpCommissionLabel(con);
        }
        if (vecCommissionLabelDetailsThree != null) {
            Utility.logger.debug("The Vector  size  for 3 lables is " + vecCommissionLabelDetailsThree.size());

            for (int i = 0; i < vecCommissionLabelDetailsThree.size(); i++) {
                LabelImportModelThree labelImportModel = (LabelImportModelThree) vecCommissionLabelDetailsThree.get(i);
                String tmRowNum = labelImportModel.getRowNum();
                String tmDcmCode = labelImportModel.getDcmCode();
                //Utility.logger.debug("The Dcm Code issssssssssssssssssssss " + tmDcmCode);
                String tmAmount = labelImportModel.getAmount();
                String tmCategory = labelImportModel.getCategory();
                //Utility.logger.debug("The amount isssssssssssssssssss " + tmAmount);
                /////////////////////////////////////////////////////////////////////by Mohamed Youssef

                CommissionLabelDAO.insertLabelDetailsThreeFromExcel(labelId, tmDcmCode, tmAmount, tmCategory, stat);

            }
            stat.close();
            CommissionLabelDAO.deleteTmpCommissionLabelThree(con);

        }


        Utility.closeConnection(con);


        if (report.size() == 0 && reportImport.size() == 0) {
            printToStream("<h3>", out);
            printToStream("Operation Completed Successfully: " + fileNameOnClient, out);
            printToStream("</h3>", out);
%>
<center>
    <%
                out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-2);\">");
            } else {

                printToStream("<br><br><h4>Error Report</h4>", out);
                printToStream("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>", out);
                printToStream("<TR class=TableHeader>", out);
                printToStream("<td size =20% nowrap align=left>Row Num</td>", out);
                printToStream("<td size= 10% nowrap align=left>Cell Value</td>", out);
                printToStream("<td size =10% nowrap align=left>Error Message</td>", out);
                printToStream("</tr>", out);

                for (int i = 0; i < report.size(); i++) {
                    ErrorInfo error = (ErrorInfo) report.get(i);
                    Utility.logger.debug(error.getCellName());
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
                for (int k = 0; k < reportImport.size(); k++) {
                    ErrorInfo error = (ErrorInfo) reportImport.get(k);
                    Utility.logger.debug(error.getCellName());
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
    %>