<%@ 
page contentType="text/html;charset=windows-1252"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                         
              import="java.sql.Connection"   
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.web.interfaces.cr.*"
              import="com.mobinil.sds.web.interfaces.dcm.*"
              import="com.mobinil.sds.core.system.sa.importdata.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import="com.mobinil.sds.core.system.sa.users.dao.*"
              import="com.mobinil.sds.core.system.sa.users.model.*"
              import="com.mobinil.sds.core.system.sa.users.dto.*"
              import ="java.io.*"      

              import ="com.mobinil.sds.core.system.gn.dcm.dto.*"
              import ="com.mobinil.sds.core.system.gn.dcm.dao.*"
              import ="com.mobinil.sds.core.system.gn.dcm.model.*"

              import ="com.mobinil.sds.core.system.dcm.service.model.*"
              import ="com.mobinil.sds.core.system.dcm.service.dao.*"
              import ="com.mobinil.sds.core.utilities.Utility" 
              %>
    <%!
    public void printToStream(String s, JspWriter out ,PrintWriter pw)throws Exception
    {
      out.println(s); 
      if (pw!=null)
      {
        pw.println(s);
        pw.flush();        
      }
    }
  %>
      
<%
String appName = request.getContextPath();
DiskFileUpload upload = new DiskFileUpload();
List items = upload.parseRequest(request);
String fileUniqueName ="";
String baseDirectory =request.getRealPath("/dcm/upload/");

String tableId = (String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_TABLES) ;
String operation =(String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_OPERATION);
String strUserID =(String)request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
String serviceId = (String)request.getParameter(DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID);

String fileNameOnClient="";

Iterator itr = items.iterator();

while(itr.hasNext()) {
	FileItem item = (FileItem) itr.next();          
	// check if the current item is a form field or an uploaded file
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
		// the item must be an uploaded file save it to disk. Note that there
		// seems to be a bug in item.getName() as it returns the full path on
		// the client's machine for the uploaded file name, instead of the file
		// name only. To overcome that, I have used a workaround using
		// fullFile.getName().
	//	File fullFile  = new File(item.getName()); 

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

//ExcelImport importEngine = new ExcelImport(userID);
Vector errorVector = new Vector();
Vector statusVector = new Vector();


DataImportEngine importEngine = new DataImportEngine();

DataImportReport importReport =importEngine.ImportFileWithRowNumber(baseDirectory+fileUniqueName , operation, tableId); 

Vector reportImport = importReport.getReport();

Vector report = new Vector();

String operationName = importReport.getOperation();
int numOfRecordsUpdated = importReport.getNumOfRecordsUpdated();

//importEngine = null;

out.println("Operation Was Completed");
{

if (operationName.compareTo("UPDATE")==0)
{
out.println("Total Number Of Records Updated = "+ numOfRecordsUpdated);
}

  java.io.File fileHtml =null;
  java.io.FileOutputStream outFile = null;
  java.io.PrintWriter pw = null;

  fileHtml = new java.io.File(baseDirectory+fileUniqueName+".html");
  fileHtml.createNewFile();
  outFile = new java.io.FileOutputStream(fileHtml);
  pw = new PrintWriter(outFile);     
  pw.flush();

  
printToStream("<LINK REL=STYLESHEET TYPE=\"text/css\" HREF=\""+appName+"/resources/css/Template1.css\">",out,pw);
printToStream("<script type=\"text/javascript\">",out,pw);
  printToStream("function Toggle(item) {",out,pw);
  printToStream("obj=document.getElementById(item);",out,pw);
  printToStream("if (obj!=null) {",out,pw);
  printToStream("visible=(obj.style.display!=\"none\")",out,pw);
  printToStream("key=document.getElementById(\"x\" + item);",out,pw);     
  printToStream("if (visible) {",out,pw);
  printToStream("obj.style.display=\"none\";",out,pw);
  printToStream("key.innerHTML=\"<img src=\'"+appName+"/resources/img/plus.gif\' border='0'>\";",out,pw);
  printToStream("} else {",out,pw);
  printToStream("obj.style.display=\"block\";",out,pw);
  printToStream("key.innerHTML=\"<img src='"+appName+"/resources/img/minus.gif\' border='0'>\";",out,pw);      
  printToStream("}}}",out,pw);
printToStream("</script>",out,pw);  

  ErrorInfo sheetError = null;
  Connection con = Utility.getConnection();
  Vector vecTmpPosServiceEligibility = ServiceDAO.getTmpServiceEligibility(con);
  for(int i = 0;i<vecTmpPosServiceEligibility.size();i++)
  {
    PosServiceImportModel posServiceImportModel = (PosServiceImportModel)vecTmpPosServiceEligibility.get(i);
    String tmRowNum = posServiceImportModel.getRowNum();
    String posCode = posServiceImportModel.getPosCode();

    boolean posExists = ServiceDAO.checkPosCodeExists(con,posCode);
    if(posExists)
    {
      boolean posServicEligibilityExists = ServiceDAO.checkPosServiceEligibilityExists(con,posCode,serviceId);
      if(!posServicEligibilityExists)
      {
        ServiceDAO.insertPosEligiableForService(con,posCode,serviceId);
      }
      else
      {
        sheetError = new ErrorInfo();
        sheetError.setCellName(posCode);
        sheetError.setErrorMsg("POS code already assigned to this service.");
        sheetError.setRowNum(Integer.parseInt(tmRowNum));
        report.add(sheetError);
      }
    }
    else
    {
      sheetError = new ErrorInfo();
      sheetError.setCellName(posCode);
      sheetError.setErrorMsg("POS code does not exists in the system.");
      sheetError.setRowNum(Integer.parseInt(tmRowNum));
      report.add(sheetError);
    }
  }  
  ServiceDAO.deleteTmpServiceEligibility(con);
  Utility.closeConnection(con); 
  
/////////////////////////////    
  printToStream("<center><h3>",out,pw);
  printToStream("Report : " + fileNameOnClient,out,pw);
  printToStream("</h3></center>",out,pw);

if (report.size()>0 || reportImport.size()>0)
{
  printToStream("<br><br><h4>Error Report</h4>",out,pw);
  printToStream("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",out,pw);
  printToStream("<TR class=TableHeader>",out,pw);
  printToStream("<td size =20% nowrap align=left>Row Num</td>",out,pw);
  printToStream("<td size= 10% nowrap align=left>Cell Value</td>",out,pw);
  printToStream("<td size =10% nowrap align=left>Error Message</td>",out,pw); 
  printToStream("</tr>",out,pw);

  for (int i = 0 ; i < reportImport.size();i++)
  {
    ErrorInfo error = (ErrorInfo)reportImport.get(i);
    Utility.logger.debug(error.getCellName());
    printToStream("<tr>",out,pw);
    printToStream("<TD class=TableTextNote align=left size=\"20%\">",out,pw);
    if (error.getRowNum()>-1)
    printToStream((error.getRowNum()+1)+"",out,pw);

    printToStream("</td>",out,pw);
    printToStream("<TD class=TableTextNote align=left size=\"20%\">",out,pw);
    printToStream(error.getCellName(),out,pw);
    printToStream("</td>",out,pw);
    printToStream("<TD class=TableTextNote align=left size=\"60%\">",out,pw);
    printToStream(error.getErrorMsg(),out,pw);
    printToStream("</td>",out,pw);
  
    printToStream("</tr>",out,pw);
         
  }
  for (int i = 0 ; i < report.size();i++)
  {
    ErrorInfo error = (ErrorInfo)report.get(i);
    Utility.logger.debug(error.getCellName());
    printToStream("<tr>",out,pw);
    printToStream("<TD class=TableTextNote align=left size=\"20%\">",out,pw);
    if (error.getRowNum()>-1)
    printToStream((error.getRowNum()+1)+"",out,pw);

    printToStream("</td>",out,pw);
    printToStream("<TD class=TableTextNote align=left size=\"20%\">",out,pw);
    printToStream(error.getCellName(),out,pw);
    printToStream("</td>",out,pw);
    printToStream("<TD class=TableTextNote align=left size=\"60%\">",out,pw);
    printToStream(error.getErrorMsg(),out,pw);
    printToStream("</td>",out,pw);
  
    printToStream("</tr>",out,pw);
         
  }


  printToStream("</TABLE>",out,pw);

}

}
%>
