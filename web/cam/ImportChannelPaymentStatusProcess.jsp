<%@ page contentType="text/html;charset=windows-1252"
              import ="com.mobinil.sds.core.utilities.*"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"  
              import="com.mobinil.sds.core.system.sa.importdata.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"                 
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.core.system.aacm.dao.AuthAgentDAO"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.web.interfaces.ac.*"
              import="com.mobinil.sds.web.interfaces.aacm.*"
              import ="com.mobinil.sds.core.system.aacm.model.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"              
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
String baseDirectory =request.getRealPath("/aacm/upload/");
Vector dcmDoesNotExist = new Vector();
String tableId = (String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_TABLES) ;
System.out.println("The table id isssssssssss " + tableId);
String operation =(String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_OPERATION);
System.out.println("The operation issssssssssss " + operation);
String strUserID =(String)request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
//String year=(String)request.getParameter(AACMInterfaceKey.CONTROL_SELECT_IMPORT_AMS_YEAR);
//System.out.println("The year isssssssssssssss " + year);

//String month=(String)request.getParameter(AACMInterfaceKey.CONTROL_SELECT_IMPORT_AMS_MONTH);
//System.out.println("The month isssssssssssssssss " + month);
//boolean check = AuthAgentDAO.amsUploadCheck(year,month);
//System.out.println("The check issssssssssssssssss " + check);

	  
%>


<%@page import="com.mobinil.sds.web.interfaces.cam.PaymentInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.cam.payment.dao.PaymentScmStatusDao"%><form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="Import" method="post">

<% out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");
%>
    <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="<%=PaymentInterfaceKey.ACTION_IMPORT_CHANNEL_PAYMENT_STATUS %>">
</form>

<%




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

//String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);


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

java.util.Date currentDate = new java.util.Date();
PaymentScmStatusDao.importChannelPaymentState();

//int currentYear = currentDate.getYear()+1900;
//int currentMonth = currentDate.getMonth()+1;
//int currentDay = currentDate.getDate();

//String strCurrentDate = currentYear+"/"+currentMonth+"/"+currentDay;
//ErrorInfo contractError = null;
/*Vector amsData = AuthAgentDAO.getTmpAmsData();

  for(int i = 0;i<amsData.size();i++)
  {
    AMSImportModel amsImportModel = (AMSImportModel)amsData.get(i);
    String tmRowNum = amsImportModel.getRowNum();
    String tmAuthAgentName = amsImportModel.getAuthAgentName();
    String tmBscsCode = amsImportModel.getBscsCode();
    
   
    AuthAgentDAO.insertAMSData(tmAuthAgentName,tmBscsCode,strUserID,year,month);
    
  }*/ 
  
 Long fileId = AuthAgentDAO.updateAmsDataWithStatusDcmExists();
  //AuthAgentDAO.insertAmsImportFile(fileId,strUserID,month,year);
  dcmDoesNotExist = AuthAgentDAO.selectAuthorizedAgentNotExist(fileId);
  
  
if (report.size()==0 && reportImport.size()==0)
{
  printToStream("<h3>",out);
  printToStream("Operation Completed Successfully: " + fileNameOnClient,out);
  printToStream("</h3>",out);

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


/*if(dcmDoesNotExist.size()!=0)
{
	System.out.println("The vector size issssssssssssss" + dcmDoesNotExist.size());
	printToStream("<center>",out);
	printToStream("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>",out);
	printToStream("<TR class=TableHeader>",out);
	  printToStream("<td size =20% nowrap align=center>DCM's Not Exist</td>",out);
	  printToStream("</tr>",out);
	  for(int m = 0;m<dcmDoesNotExist.size();m++)
	  {
		  String authAgentName = (String)dcmDoesNotExist.get(m);
		  printToStream("<tr>",out);
		    printToStream("<TD class=TableTextNote align=center >",out);
		    printToStream(authAgentName,out);
		    printToStream("</td>",out);
		    
		    printToStream("</tr>",out);
	  }
	  printToStream("</TABLE>",out);
	  printToStream("</center>",out);
}*/
PaymentScmStatusDao.deletePaymentStatusImportData();

%>




