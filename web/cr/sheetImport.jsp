<%@ 
page contentType="text/html;charset=windows-1256"
              import ="com.mobinil.sds.core.utilities.Utility"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"            
              import="com.mobinil.sds.core.system.cr.excelImport.*"
              import="com.mobinil.sds.web.interfaces.*"
              import ="java.io.*"              
              %>
    

<%!
    public void printToStream(String s, JspWriter out, PrintWriter pw)throws Exception
    {
      out.println(s);
      if (pw!=null)
      {
        pw.println(s);
        pw.flush();        
      }
    }
    
    public void printSheets(Sheet[] sheets , JspWriter out, PrintWriter pw, HttpServletRequest request) throws Exception
    {                
      String appName = request.getContextPath();
      for (int i = 0 ; i < sheets.length; i++)
      {
       // out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
        Sheet sheetRep = sheets[i];
        Contract[] contracts = sheetRep.getAllContractsSorted();
        
        String sheetId = sheetRep.getRowNum()+"";
        printToStream("<tr>",out,pw);
        printToStream("<TD class=TableTextNote align=left size=\"20%\">",out,pw);
        if (contracts.length>0)
        {
          printToStream("<A id=\"x"+sheetId +"\" href=\"javascript:Toggle(\'"+sheetId+"\');\">"+"<img src=\'"+appName+"/resources/img/plus.gif\'>"+"</A>",out,pw);           
          printToStream( "&nbsp; &nbsp;"+ (String)sheetRep.getName(),out,pw) ;   
        }
        else          
        printToStream( "&nbsp;&nbsp;&nbsp; &nbsp; &nbsp;"+ (String)sheetRep.getName(),out,pw) ;
       // out.println("<a class=darkgreyonlightgrey>");              
       printToStream("</td>",out,pw);
       printToStream("  <TD class=TableTextNote align=left size=\"20%\">",out,pw);
      printToStream(sheetRep.getRowNum()+"",out,pw);
       printToStream("</td>",out,pw);
      printToStream("  <TD class=TableTextNote align=left size=\"10%\">",out,pw);
       printToStream(sheetRep.getContractsCount()+"",out,pw);
       printToStream("</td>",out,pw);
 
       printToStream("  <TD class=TableTextNote align=left size=\"20%\">",out,pw);
       printToStream(sheetRep.getStatusName(),out,pw);        
       printToStream("</td>",out,pw);                
    //  out.println(sheetRep.getStatusName());
    //  out.println("</a>"); 
    //  out.println("</td>");
    
        printToStream("  <TD class=TableTextNote align=left size=\"40%\">",out,pw);
        Enumeration sheetWarning = sheetRep.warnings.elements();
        while (sheetWarning.hasMoreElements())
        {
          printToStream((String)sheetWarning.nextElement(),out,pw);
          printToStream("<br>",out,pw);
        }
        printToStream("</td>",out,pw);
   //   out.println("</table>");        
        if (contracts.length>0)
        {
        printToStream("<tr>",out,pw);
        printToStream("<td colspan=5>",out,pw);
        
        printToStream("<div style=\"display:none\" id="+sheetId + " name="+sheetId+">",out,pw);
        printToStream("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",out,pw);
        printToStream("<TR class=TableHeader>",out,pw);
        printToStream("<td nowrap align=left>Contract ID</td>",out,pw);
        printToStream("<td nowrap align=left>Row Num</td>",out,pw);
        printToStream("<td nowrap align=left>Status</td>",out,pw);
        printToStream("<td nowrap align=left>Warning</td>",out,pw);          
        printToStream("</tr>",out,pw);              
  ///contracts of this sheet printing 
        
        
        for (int index = 0; index <contracts.length; index++)       
        {
          Contract contractRep = contracts[index];  
          printToStream(" <TR class=TableTextNote>",out,pw); 
          printToStream("  <TD class=TableTextNote align=left>",out,pw);
          printToStream( (String)contractRep.getName(),out,pw);
          printToStream("</td>",out,pw);
          printToStream("  <TD class=TableTextNote align=left>",out,pw);
          printToStream(contractRep.getRowNum()+"",out,pw);        
          printToStream("</td>",out,pw);    
          printToStream("  <TD class=TableTextNote align=left>",out,pw);
          printToStream(contractRep.getStatusName(),out,pw);
          printToStream("</td>",out,pw);
          printToStream("  <TD class=TableTextNote align=left>",out,pw);
          if (contractRep.getWarningText()!=null)
          {
            printToStream(contractRep.getWarningText(),out,pw);
          }
          printToStream("</td>",out,pw);
          printToStream("</tr>",out,pw);
        }
        printToStream("</table>",out,pw);
        printToStream("</div>",out,pw);
        printToStream("</td>",out,pw);
        printToStream("</tr>",out,pw);
        }
        
        //out.println("<BR>");
        
      }
}
    %>
      
<%
    String appName = request.getContextPath();

//boolean isMultipart = FileUpload.isMultipartContent(request);

DiskFileUpload upload = new DiskFileUpload();
List items = upload.parseRequest(request);
String fileUniqueName ="";
//File parentDirectory = new File("test.txt").getAbsolutePath();

Utility.logger.debug("real path " + request.getRealPath("/cr/"));

String baseDirectory =request.getRealPath("/cr/upload/");

Utility.logger.debug("path = " +baseDirectory);

String htmlSaveDirectory = baseDirectory;
String fileNameOnClient="";

Iterator itr = items.iterator();

while(itr.hasNext()) {
	FileItem item = (FileItem) itr.next();
        
	// check if the current item is a form field or an uploaded file
	if(item.isFormField()) {
            
	// get the name of the field
	String fieldName = item.getFieldName();
	
	// if it is name, we can set it in request to thank the user
	if(fieldName.equals("name"))
		request.setAttribute("msg", "Thank You: " + item.getString());
		
	} else {

		// the item must be an uploaded file save it to disk. Note that there
		// seems to be a bug in item.getName() as it returns the full path on
		// the client's machine for the uploaded file name, instead of the file
		// name only. To overcome that, I have used a workaround using
		// fullFile.getName().
	//	File fullFile  = new File(item.getName()); 

  try{
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
String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);


ExcelImport importEngine = new ExcelImport(userID);
Vector errorVector = new Vector();
Vector statusVector = new Vector();
ImportReport report = new ImportReport();


long stime= System.currentTimeMillis();
System.out.println("stime" + stime);

boolean importStatus = importEngine.ImportFile(baseDirectory+fileUniqueName , errorVector, report);
long etime =  System.currentTimeMillis();

System.out.println("time took = "+ (etime-stime));


//importEngine = null;



if (!importStatus)
{
out.println("Error File can not be imported");
}
else 
{
  java.io.File fileHtml =null;
  java.io.FileOutputStream outFile = null;
  java.io.PrintWriter pw = null;
  
/*
  //if (errorVector.size()==1 && statusVector.size()==0 )
  {
  
  }
  //else
  //{
  Write to an html file in the cr/upload directory 
*/
   fileHtml = new java.io.File(htmlSaveDirectory+fileUniqueName+".html");
   fileHtml.createNewFile();
   outFile = new java.io.FileOutputStream(fileHtml);
   pw = new PrintWriter(outFile);     
   pw.flush();
   
   //}

        
printToStream("<LINK REL=STYLESHEET TYPE=\"text/css\" HREF=\""+appName+"/resources/css/Template1.css\">",out,pw);
printToStream("<script type=\"text/javascript\">",out,pw);
printToStream("function Toggle(item) {",out,pw);
printToStream("obj=document.getElementById(item);",out,pw);
printToStream("if (obj!=null) {",out,pw);
printToStream("visible=(obj.style.display!=\"none\")",out,pw);
printToStream("key=document.getElementById(\"x\" + item);",out,pw);     
printToStream("if (visible) {",out,pw);
printToStream("obj.style.display=\"none\";",out,pw);
printToStream("key.innerHTML=\"<img src=\'"+appName+"/resources/img/plus.gif\'>\";",out,pw);
printToStream("} else {",out,pw);
printToStream("obj.style.display=\"block\";",out,pw);
printToStream("key.innerHTML=\"<img src='"+appName+"/resources/img/minus.gif\'>\";",out,pw);      
printToStream("}}}",out,pw);
printToStream("</script>",out,pw);  

  
  
  printToStream("<h3>",out,pw);
  printToStream("Import Results of File : " + fileNameOnClient,out,pw);
  printToStream("</h3>",out,pw);

  printToStream("<h4>",out,pw);
  printToStream("Summary of Imported Items",out,pw);
  printToStream("</h4>",out,pw);


/*
<H2>
Imported Items
</H2>
*/
printToStream("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",out,pw);
        printToStream("<TR class=TableHeader>",out,pw);
        printToStream("<td size =20% nowrap align=left>Sheet ID</td>",out,pw);
        printToStream("<td size= 10% nowrap align=left>Row Number</td>",out,pw);
        printToStream("<td size =10% nowrap align=left>Contracts Count</td>",out,pw);
        printToStream("<td size= 10% nowrap align=left>Status</td>",out,pw);        
        printToStream("<td size= 50% nowrap align=left>Warning</td>",out,pw); 
        printToStream("</tr>",out,pw);
         
printSheets(report.getAllSheetOfType(Sheet.IMPORTED),out,pw, request);
printSheets(report.getAllSheetOfType(Sheet.REJECTED),out,pw, request);
printSheets(report.getAllSheetOfType(Sheet.PARTIAL_IMPORTED),out,pw, request);
printToStream("</TABLE>",out,pw);


if (errorVector.size()>0)
{
printToStream("<H4>Summary of Import Errors </H4>",out,pw);


printToStream("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",out,pw);
printToStream("<TR class=TableHeader>",out,pw);
printToStream("<td nowrap align=left>Error Type</td>",out,pw);
printToStream("<td nowrap align=left>Error Row Number</td>",out,pw);
printToStream("<td nowrap align=left>Error Details</td>",out,pw);
printToStream("</tr>",out,pw);

  for (int i = 0;i <errorVector.size();i++)
  {
    ErrorInfo errorInfo = (ErrorInfo)errorVector.get(i);
    
    printToStream(" <TR class=TableTextNote>",out,pw);
    printToStream("  <TD class=TableTextNote align=left>",out,pw);
    printToStream(errorInfo.getErrorName(),out,pw);
    printToStream("</td>",out,pw);
    printToStream("  <TD class=TableTextNote align=left>",out,pw);
    printToStream(errorInfo.getRowNum()+"",out,pw);
    printToStream("</td>",out,pw);  
    printToStream("  <TD class=TableTextNote align=left>",out,pw);
    printToStream(errorInfo.getErrorDetail(),out,pw);
    printToStream("</td>",out,pw);  

    printToStream("</tr>",out,pw);
        
  }

  
}

 
    try
    {
    if (pw!=null)
    {
      //Utility.logger.debug("saved");
      //Utility.logger.debug("save " +fileUniqueName );
      importEngine.saveHtmlLog(baseDirectory+fileUniqueName+".html",fileUniqueName+".html");
    //  importEngine.close();
      pw.flush();
      pw.close();
      outFile.close();
      
    }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }


}

%>