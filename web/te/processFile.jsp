<%@ 
page contentType="text/html;charset=windows-1252"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                          
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.te.*"
              import ="java.io.*"      
              import ="com.mobinil.sds.core.system.te.engine.*"
              import ="java.sql.*"
              import ="com.mobinil.sds.core.utilities.Utility"

              import ="com.mobinil.sds.core.system.te.dao.*"
              import ="com.mobinil.sds.core.system.te.dto.*"
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
String baseDirectory =request.getRealPath("/te/upload/");

String strUserID =(String)request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
String strTaskID =(String)request.getParameter(TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID);

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
    fileUniqueName = System.currentTimeMillis()+".txt";
    System.out.println("fileNameOnClient" + fileNameOnClient ) ;
		File savedFile = new File(baseDirectory+fileUniqueName);		
    Utility.logger.debug("file " + savedFile);
    System.out.println("file " + savedFile);
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
Vector errorSims = null;
Vector simsVec = null;


Object o = importEngine.getFileHashMap(baseDirectory+fileUniqueName);

if (o instanceof HashMap)
{
HashMap fileData = (HashMap)o;
simsVec = (Vector)fileData.get("validData");
errorSims = (Vector)fileData.get("invalidData");
}
{



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



  Connection con = Utility.getConnection();

  printToStream("<h2><center>",out,pw);
  printToStream("Insert SIMs",out,pw);
  printToStream("</center></h2>",out,pw);
  printToStream("<br><br>",out,pw);

  if(simsVec.size()<=50000)
  {
  TaskDAO.insertSimForTask(con,strTaskID,simsVec);


  printToStream("<table style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",out,pw);
  printToStream("<tr class=TableHeader>",out,pw);
  printToStream("<td>SIM</td>",out,pw);
  printToStream("</tr>",out,pw);


  for(int i=0;i<simsVec.size();i++)
  {
    String simNo = (String)simsVec.get(i);
    printToStream("<tr class=TableTextNote>",out,pw);
    printToStream("<td>"+simNo+"</td>",out,pw);
    printToStream("</tr>",out,pw);
  }


  printToStream("</table>",out,pw);

  
  

  
  Utility.closeConnection(con);

/////////////////////////////    
  //printToStream("<center><h3>",out,pw);
  //printToStream("Report : " + fileNameOnClient,out,pw);
  //printToStream("</h3></center>",out,pw);

if (errorSims.size()!=0)
{
  printToStream("<br><br><h4>Error Report</h4>",out,pw);
  printToStream("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",out,pw);
  printToStream("<TR class=TableHeader>",out,pw);
  printToStream("<td size =20% nowrap align=left>Sim Num</td>",out,pw);
  printToStream("<td size =10% nowrap align=left>Error Message</td>",out,pw); 
  printToStream("</tr>",out,pw);

  for (int i = 0 ; i < errorSims.size();i++)
  {
    String error = (String)errorSims.get(i);
    Utility.logger.debug(error+" is invalid to insert.");
    printToStream("<tr>",out,pw);
    printToStream("<TD class=TableTextNote align=left size=\"20%\">",out,pw);
    printToStream(error,out,pw);
    printToStream("</td>",out,pw);
    
    printToStream("<TD class=TableTextNote align=left size=\"20%\">",out,pw);
    printToStream("Invalid sim number length.",out,pw);
    printToStream("</td>",out,pw);

  
    printToStream("</tr>",out,pw);
         
  }
  
  


  printToStream("</TABLE>",out,pw);

}
}
  else
  {
    printToStream("<h3>Cannot Import Text File More Than 50000 Rows.</h3>",out,pw);
  }  
}
%>
