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
    public void printToStream(String s, JspWriter out)throws Exception
    {
      out.println(s);    
    }
  %>

  <%
String appName = request.getContextPath();
DiskFileUpload upload = new DiskFileUpload();
List items = upload.parseRequest(request);
String fileUniqueName ="";
String baseDirectory =request.getRealPath("/sop/upload/");

String tableId = (String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_TABLES) ;

String operation =(String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_OPERATION);

String strUserID =(String)request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
String labelId =(String)request.getParameter(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);

String fileNameOnClient="";

Iterator itr = items.iterator();

while(itr.hasNext()) {
	FileItem item = (FileItem) itr.next();

if(item.isFormField()) 
  {            
	// get the name of the field
	String fieldName = item.getFieldName();  
	
	// if it is name, we can set it in request to thank the user
	if(fieldName.equals("name"))
		request.setAttribute("msg", "Thank You: " + item.getString());
		
	} 
   else 
  {
  try
  {
    fileNameOnClient = item.getName();
    Utility.logger.debug("fileNameOnClient" + fileNameOnClient ) ;
    fileUniqueName = System.currentTimeMillis()+".xls";
		File savedFile = new File(baseDirectory+fileUniqueName);		
    Utility.logger.debug("file " + savedFile);
		item.write(savedFile);
    }
    catch (Exception e)
    {
    out.println("Error File can not be imported");
    e.printStackTrace();
    return ;
    }
	}
}

HashMap dataHashMap = null;
dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

Vector errorVector = new Vector();
Vector statusVector = new Vector();


DataImportEngine importEngine = new DataImportEngine();

DataImportReport importReport =importEngine.ImportFileWithRowNumber(baseDirectory+fileUniqueName , operation, tableId);
Vector reportImport = importReport.getReport();

Vector report = new Vector();

String operationName = importReport.getOperation();
int numOfRecordsUpdated = importReport.getNumOfRecordsUpdated();

out.println("Operation Was Completed");
{

if (operationName.compareTo("UPDATE")==0)
{
out.println("Total Number Of Records Updated = "+ numOfRecordsUpdated);
}

          
printToStream("<LINK REL=STYLESHEET TYPE=\"text/css\" HREF=\""+appName+"/resources/css/Template1.css\">",out);
printToStream("<script type=\"text/javascript\">",out);
  printToStream("function Toggle(item) {",out);
  printToStream("obj=document.getElementById(item);",out);
  printToStream("if (obj!=null) {",out);
  printToStream("visible=(obj.style.display!=\"none\")",out);
  printToStream("key=document.getElementById(\"x\" + item);",out);     
  printToStream("if (visible) {",out);
  printToStream("obj.style.display=\"none\";",out);
  printToStream("key.innerHTML=\"<img src=\'"+appName+"/resources/img/plus.gif\'>\";",out);
  printToStream("} else {",out);
  printToStream("obj.style.display=\"block\";",out);
  printToStream("key.innerHTML=\"<img src='"+appName+"/resources/img/minus.gif\'>\";",out);      
  printToStream("}}}",out);
printToStream("</script>",out);  

ErrorInfo contractError = null;
java.sql.Connection con = Utility.getConnection();
LabelModel labelModel = CommissionDAO.selectLabelData(con, labelId);

if (labelModel.getTwoValues() == 1 && labelModel.getThreeValues() == 0)
{
CommissionDAO.deleteLableDcms(labelId,con);


CommissionDAO.deleteTmpCommissionLabel(con);
CommissionDAO.deleteTmpDcm(con);
}
else if (labelModel.getTwoValues() == 0 && labelModel.getThreeValues() == 1){
    CommissionDAO.deleteLableDcmsThree(labelId, con);
    CommissionDAO.deleteTmpCommissionLabelThree(con);
    CommissionDAO.deleteTmpDcm(con);
}
Utility.closeConnection(con);

 if (report.size()==0 && reportImport.size()==0)
{
  printToStream("<h3>",out);
  printToStream("Operation Completed Successfully: " + fileNameOnClient,out);
  printToStream("</h3>",out);
%>
<center>
<%
  out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-2);\">");
}
else
{
  
  printToStream("<br><br><h4>Error Report</h4>",out);
  printToStream("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",out);
  printToStream("<TR class=TableHeader>",out);
  printToStream("<td size =20% nowrap align=left>Row Num</td>",out);
  printToStream("<td size= 10% nowrap align=left>Cell Value</td>",out);
  printToStream("<td size =10% nowrap align=left>Error Message</td>",out); 
  printToStream("</tr>",out);

  for (int i = 0 ; i < report.size();i++)
  {
    ErrorInfo error = (ErrorInfo)report.get(i);
    Utility.logger.debug(error.getCellName());
    printToStream("<tr>",out);
    printToStream("<TD class=TableTextNote align=left size=\"20%\">",out);
    if (error.getRowNum()>-1)
    printToStream((error.getRowNum()+1)+"",out);

    printToStream("</td>",out);
    printToStream("<TD class=TableTextNote align=left size=\"20%\">",out);
    printToStream(error.getCellName(),out);
    printToStream("</td>",out);
    printToStream("<TD class=TableTextNote align=left size=\"60%\">",out);
    printToStream(error.getErrorMsg(),out);
    printToStream("</td>",out);
  
    printToStream("</tr>",out);
         
  }
  for (int k = 0 ; k < reportImport.size();k++)
  {
    ErrorInfo error = (ErrorInfo)reportImport.get(k);
    Utility.logger.debug(error.getCellName());
    printToStream("<tr>",out);
    printToStream("<TD class=TableTextNote align=left size=\"20%\">",out);
    if (error.getRowNum()>-1)
    printToStream((error.getRowNum()+1)+"",out);

    printToStream("</td>",out);
    printToStream("<TD class=TableTextNote align=left size=\"20%\">",out);
    printToStream(error.getCellName(),out);
    printToStream("</td>",out);
    printToStream("<TD class=TableTextNote align=left size=\"60%\">",out);
    printToStream(error.getErrorMsg(),out);
    printToStream("</td>",out);
  
    printToStream("</tr>",out);
         
  }
  printToStream("</TABLE>",out);
  
}


}
  %>