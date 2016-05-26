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


String fileNameOnClient="";




/*if (operationName.compareTo("INSERT")==0)
{
out.println("Total Number Of Records Updated = "+ numOfRecordsUpdated);
}*/

          
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


/*if (report.size()==0)
{
   printToStream("<h3>",out);
  printToStream("Operation Completed Successfully: " + fileNameOnClient,out);
  printToStream("</h3>",out);

}*/

printToStream("<h3>",out);
  printToStream("Operation Completed Successfully: " + fileNameOnClient,out);
  printToStream("</h3>",out);

/*else
{
  
  printToStream("<h3>",out);
  printToStream("Errors Report : " + fileNameOnClient,out);
  printToStream("</h3>",out);

  

  printToStream("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",out);
  printToStream("<TR class=TableHeader>",out);
  printToStream("<td size =20% nowrap align=left>Row Num</td>",out);
  printToStream("<td size= 10% nowrap align=left>Cell Name</td>",out);
  printToStream("<td size =10% nowrap align=left>Error Message</td>",out); 
  printToStream("</tr>",out);

  for (int i = 0 ; i < report.size();i++)
  {
    ErrorInfo error = (ErrorInfo)report.get(i);

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

}*/

%>