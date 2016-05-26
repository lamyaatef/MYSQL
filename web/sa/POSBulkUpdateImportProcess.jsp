<%@page import="com.mobinil.sds.core.system.sa.unupdatedcode.model.UnupdatedCode"%>
<%@page import="com.mobinil.sds.core.system.request.dao.RequestDao"%>
<%@ 
page contentType="text/html;charset=windows-1252"
              import ="com.mobinil.sds.core.utilities.Utility"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                          
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.core.system.sa.importdata.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import="java.util.*"
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
    System.out.println("app name is : "+appName);
    HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
      
      
      %>
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template2.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/utilities.js" type=text/javascript></SCRIPT>
  </head>
        <body>
<%
//String appName = request.getContextPath();
DiskFileUpload upload = new DiskFileUpload();
List items = upload.parseRequest(request);
String fileUniqueName ="";
String baseDirectory =request.getRealPath("/sa/upload/");

String tableId = (String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_TABLES) ;

String operation =(String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_OPERATION);

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


//HashMap dataHashMap = null;
//dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

//String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);


//ExcelImport importEngine = new ExcelImport(userID);
Vector errorVector = new Vector();
Vector statusVector = new Vector();




DataImportEngine importEngine = new DataImportEngine();
DataImportReport importReport =importEngine.ImportFile(baseDirectory+fileUniqueName , operation, tableId); 
Vector report = importReport.getReport();
String operationName = importReport.getOperation();
int numOfRecordsInserted = importReport.getNumOfRecordsInserted();
String featureName = RequestDao.bulkUpdatePOSDetailFeature(tableId);
Vector unupdatedCodes = RequestDao.getListedButUnupdatedDCMCodes(tableId,featureName);
UnupdatedCode aCode = new UnupdatedCode();
if (featureName.compareToIgnoreCase("IS_MOBICASH , MOBICASH_NUMBER")==0)
    featureName = "Orange Money";

out.println("<CENTER>");
out.println("  <h2>List of Unupdated POS Codes</h2>");
out.println("</CENTER>");

if (!unupdatedCodes.isEmpty())
{
    
        //unupdatedCodes.get(i);
        printToStream("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",out);
        printToStream("<TR class=TableHeader>",out);
        printToStream("<td size =10% align=center>POS Code</td>",out);
        printToStream("<td size= 30% align=center>Feature Value</td>",out);
        printToStream("<td size =10% align=center>Error Message</td>",out); 
        printToStream("</tr>",out);
        System.out.println("unupdatedCodes.size() "+unupdatedCodes.size());
        for(int i=0; i<unupdatedCodes.size(); i++)
        {
           
           aCode = (UnupdatedCode)unupdatedCodes.get(i);
           
                  
          //ErrorInfo error = (ErrorInfo)report.get(i);

          printToStream("<tr>",out);
          printToStream("<TD class=TableTextNote align=left size=\"10%\">",out);
           printToStream(aCode.getCode(),out);
          //if (error.getRowNum()>-1)
          printToStream("",out);
          printToStream("</td>",out);
          printToStream("<TD class=TableTextNote align=left size=\"30%\">",out);
          printToStream(aCode.getFeature(),out);
          printToStream("</td>",out);
          printToStream("<TD class=TableTextNote align=left size=\"10%\">",out);
          printToStream("POS Code Does Not Exist in Database",out);
          printToStream("</td>",out);

          printToStream("</tr>",out);

        }


        printToStream("</TABLE>",out);

}
/*if (operationName.compareTo("INSERT")==0)
{
out.println("Total Number Of Records Updated = "+ numOfRecordsInserted);
}*/

          
/*printToStream("<LINK REL=STYLESHEET TYPE=\"text/css\" HREF=\""+appName+"/resources/css/Template1.css\">",out);
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
printToStream("</script>",out);  */


printToStream("<h3>",out);
out.println("Import Operation For Feature '"+featureName+"' Completed and "+numOfRecordsInserted+" Records Were Inserted");
printToStream("</h3>",out);
out.println("<br>");


if (report.size()==0)
{
   printToStream("<h3>",out);
  printToStream("Import Operation Completed Successfully for File Name: " + fileNameOnClient,out);
  printToStream("</h3>",out);

}
else
{
  
  printToStream("<h3>",out);
  printToStream("Error Importing File Name : " + fileNameOnClient,out);
  printToStream("</h3>",out);

  

  printToStream("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",out);
  printToStream("<TR class=TableHeader>",out);
  printToStream("<td size =10% nowrap align=left>Row Num</td>",out);
  printToStream("<td size= 20% nowrap align=left>Cell Name</td>",out);
  printToStream("<td size =30% nowrap align=left>Error Message</td>",out); 
  printToStream("</tr>",out);

  for (int i = 0 ; i < report.size();i++)
  {
    ErrorInfo error = (ErrorInfo)report.get(i);

    printToStream("<tr>",out);
    printToStream("<TD class=TableTextNote align=left size=\"10%\">",out);
    if (error.getRowNum()>-1)
    printToStream((error.getRowNum()+1)+"",out);
    printToStream("</td>",out);
    printToStream("<TD class=TableTextNote align=left size=\"20%\">",out);
    printToStream(error.getCellName(),out);
    printToStream("</td>",out);
    printToStream("<TD class=TableTextNote align=left size=\"30%\">",out);
    printToStream(error.getErrorMsg(),out);
    printToStream("</td>",out);
  
    printToStream("</tr>",out);
         
  }


  printToStream("</TABLE>",out);

}

%>
        </body>

</html>