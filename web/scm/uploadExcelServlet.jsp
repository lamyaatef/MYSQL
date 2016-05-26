<%-- 
    Document   : uploadExcelServlet
    Created on : Oct 31, 2010, 11:50:36 AM
    Author     : Salma
--%>
<%@
page contentType="text/html;charset=windows-1252"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.web.interfaces.cr.*"
              import="com.mobinil.sds.core.system.sa.importdata.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import="com.mobinil.sds.core.system.sa.users.dao.*"
              import="com.mobinil.sds.core.system.sa.users.model.*"
              import="com.mobinil.sds.core.system.sa.users.dto.*"
              import ="java.io.*"
              import ="com.mobinil.sds.core.system.gn.dcm.dto.*"
              import ="com.mobinil.sds.core.system.gn.dcm.dao.*"
              import ="com.mobinil.sds.core.system.gn.dcm.model.*"
              import ="java.sql.*"
              import ="com.mobinil.sds.core.utilities.*"
              import ="com.mobinil.sds.core.system.cr.batch.dto.*"
              import ="com.mobinil.sds.core.system.cr.batch.dao.*"
              import ="com.mobinil.sds.core.system.cr.sheet.dto.*"
              import ="com.mobinil.sds.core.system.cr.sheet.model.*"
              import ="com.mobinil.sds.core.system.cr.contract.dto.*"
              import ="com.mobinil.sds.core.system.cr.contract.model.*"
              import ="com.mobinil.sds.core.system.cr.contract.dao.*"
              import ="com.mobinil.sds.core.system.cr.worker.*"
              import ="com.mobinil.sds.core.system.request.dao.*"
              import="com.mobinil.sds.core.system.request.model.*"
              import="com.mobinil.sds.core.system.request.utility.*"
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
  String zipFilePath = "";

  boolean pdfCreated = false;
java.io.File fileHtml =null;
java.io.FileOutputStream outFile = null;
java.io.PrintWriter pw = null;

String fileNameOnClient="";

Vector report = new Vector();
Vector reportImport = new Vector();
String jobId = (String)request.getParameter(InterfaceKey.HASHMAP_KEY_JOB_ID) ;
if(jobId != null)
{
  HashMap workerHashMap = WorkerDataManagement.getWorkerData(jobId);
  if(workerHashMap == null)
  {
      out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"ImportArchivingForm\" method=\"post\">");

      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_JOB_ID+"\""+
                    " value=\""+jobId+"\">");

      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                    " value=\""+SCMInterfaceKey.ACTION_MANY_POS_IQRAR_SAVE+"\">");

      out.println("</form>");

      out.println("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0\" width=\"300\" height=\"100\">");
             out.println("<param name=\"movie\" value=\"../resources/img/loading.swf\">");
             out.println("<param name=\"quality\" value=\"high\">");
             out.println("<embed src=\"../resources/img/loading.swf\" quality=\"high\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\" width=\"300\" height=\"100\"></embed>");
       out.println("</object>");
      out.println("<script>");
      out.println("setTimeout(\"ImportArchivingForm.submit();\", 60000);");
      out.println("</script>");
  }
  else
  {
    report = (Vector)workerHashMap.get(ContractRegistrationInterfaceKey.OBJ_REPORT_IMPORT_ARCHIVING);
    reportImport = (Vector)workerHashMap.get(ContractRegistrationInterfaceKey.OBJ_SYS_REPORT_IMPORT_ARCHIVING);

    WorkerDataManagement.removeWorkerData(jobId);

    out.println("Operation Was Completed");

    printToStream("<center><h3>",out,pw);
    printToStream("Report : " + fileNameOnClient,out,pw);
    printToStream("</h3></center>",out,pw);

  }
}
else{
DiskFileUpload upload = new DiskFileUpload();
List items = upload.parseRequest(request);
String fileUniqueName ="";
String baseDirectory =request.getRealPath("/scm/upload/");
System.out.println("@@@@@@@@@@@@@@@@@@ " + baseDirectory);

String tableId = (String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_TABLES) ;
String operation =(String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_OPERATION);
String strUserID =(String)request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
/*String batchId = (String)request.getParameter(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);*/

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
		File savedFile = new File(baseDirectory+File.separator+fileUniqueName);
                System.out.println("fileUniqueName issssssssss " + fileUniqueName);
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

System.out.print(baseDirectory+File.separator+fileUniqueName  + "#################" + operation +"##################" + tableId);
DataImportReport importReport =importEngine.ImportFile(baseDirectory+File.separator+fileUniqueName , operation, tableId);

reportImport = importReport.getReport();

//String operationName = importReport.getOperation();
//int numOfRecordsUpdated = importReport.getNumOfRecordsUpdated();

//importEngine = null;

//fileHtml = new java.io.File(baseDirectory+File.separator+fileUniqueName+".html");
//fileHtml.createNewFile();
//outFile = new java.io.FileOutputStream(fileHtml);
//pw = new PrintWriter(outFile);
//pw.flush();


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

  Connection con = Utility.getConnection();

  Vector<String> posVec = RequestDao.getPOSPdfList(con);

  Zip.deleteFilePdf(request.getRealPath("downloadItems")) ;
  
  for(int i =0 ; i<posVec.size();i++)
   {
    int posId = RequestDao.checkPosIsFound(con , posVec.get(i));
    
                    if(posId == 0 )
                    {
                        System.out.println("1111111111111111111111111111111");
                        ErrorInfo sheetError = new ErrorInfo();
                        sheetError.setCellName(posVec.get(i));
                        sheetError.setErrorMsg("Sorry , Error in POS Code ...");
                        sheetError.setRowNum(i);
                        report.add(sheetError);
                    }
                    else if(RequestDao.checkPosHasStk(con , posId) == 0 )
                    {
                        System.out.println("2222222222222222222222222222");
                        ErrorInfo sheetError = new ErrorInfo();
                        sheetError.setCellName(posVec.get(i));
                        sheetError.setErrorMsg("Sorry , No STK Assigned to POS Code ...");
                        sheetError.setRowNum(i);
                        report.add(sheetError);
                      
                    }
                    /*else if( RequestDao.checkPosOwnerStatus(con , posId)== 2)
                    {
                        System.out.println("333333333333333333333333333333");
                        ErrorInfo sheetError = new ErrorInfo();
                        sheetError.setCellName(posVec.get(i));
                        sheetError.setErrorMsg("Sorry , POS Iqrar Received Before ...");
                         sheetError.setRowNum(i);
                        report.add(sheetError);
                    }*/
                    else
                    {
                      System.out.println("4444444444444444444444444444444");
                       PosIqrarModel posModel = RequestDao.getPosIqrarData(con , posVec.get(i));
                       String filePathDownload = PDFIqrarPrinting.createPDFIqrar(request.getRealPath("downloadItems") , request.getRealPath("resources") , posModel);
                       RequestDao.updateStkIqrarReceiving(con ,posId+"" ) ;
                       pdfCreated = true;

                    }
  }

  if(pdfCreated == true)
  {
    zipFilePath  = Zip.zipPdfFile(request.getRealPath("downloadItems") , request.getRealPath("zip"));
  }

  

}

if (report.size()>0 || reportImport.size()>0)
{
  printToStream("<br><br><h4>Error Report</h4>",out,pw);
  printToStream("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",out,pw);
  printToStream("<TR class=TableHeader>",out,pw);
  //printToStream("<td size =20% nowrap align=left>Row Num</td>",out,pw);
  printToStream("<td size= 10% nowrap align=left>Cell Value</td>",out,pw);
  printToStream("<td size =10% nowrap align=left>Error Message</td>",out,pw);
  printToStream("</tr>",out,pw);

  for (int i = 0 ; i < reportImport.size();i++)
  {
    ErrorInfo error = (ErrorInfo)reportImport.get(i);
    Utility.logger.debug(error.getCellName());
    printToStream("<tr>",out,pw);
   // printToStream("<TD class=TableTextNote align=left size=\"20%\">",out,pw);
   // if (error.getRowNum()>-1)
   // printToStream((error.getRowNum()+1)+"",out,pw);
   // printToStream("</td>",out,pw);
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
   // printToStream("<TD class=TableTextNote align=left size=\"20%\">",out,pw);
   // if (error.getRowNum()>-1)
   //printToStream((error.getRowNum()+1)+"",out,pw);

  // printToStream("</td>",out,pw);
    printToStream("<TD class=TableTextNote align=left size=\"20%\">",out,pw);
    printToStream(error.getCellName(),out,pw);
    printToStream("</td>",out,pw);
    printToStream("<TD class=TableTextNote align=left size=\"60%\">",out,pw);
    printToStream(error.getErrorMsg(),out,pw);
    printToStream("</td>",out,pw);

    printToStream("</tr>",out,pw);

  }


  printToStream("</TABLE>",out,pw);
   if(pdfCreated == true)
      {
      System.out.println("5555555555");
      printToStream("<center><br><br><h3>All Valid Iqrars Created ...</h3> ", out, pw);

       HashMap objDataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
       objDataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, zipFilePath);
       objDataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "zip//");

       session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, objDataHashMap);
       out.println("<form action=\"com.mobinil.sds.web.controller.UploadingFileServlet\" name=\"GenerateSheet\" method=\"post\">");


       out.println("<input class=\"button\"  type=\"submit\" id=download name=\"download\" value=\"Download Iqrars\" onclick=\"Download();\">");

     out.println("</form>");



      }

}
else
    {
      printToStream("<center><br><br><h3>All Iqrars Created ...</h3> ", out, pw);

       HashMap objDataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
       objDataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, zipFilePath);
       objDataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "zip//");

       session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, objDataHashMap);
       out.println("<form action=\"com.mobinil.sds.web.controller.UploadingFileServlet\" name=\"GenerateSheet\" method=\"post\">");


       out.println("<input class=\"button\"  type=\"submit\" id=download name=\"download\" value=\"Download Iqrars\" onclick=\"Download();\">");

     out.println("</form>");


    }



%>

<script>
    function Download()
{

    document.getElementById("download").disabled=true;
    document.GenerateSheet.submit();

}
</script>