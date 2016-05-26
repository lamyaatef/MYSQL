<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.gn.reports.*"
         import="com.mobinil.sds.core.system.gn.reports.dto.*" 
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
</head>
<body>
<%
HashMap hmData = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
out.println("<form name=\"reportForm\" action=\"\" method=\"post\">");

  String strJobId = (String)hmData.get(InterfaceKey.HASHMAP_KEY_JOB_ID);
    
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_JOB_ID+"\""+
              " value=\""+strJobId+"\">"); 

 String reportStatus = (String)hmData.get(InterfaceKey.HASHMAP_KEY_MESSAGE); 

if(reportStatus.compareTo(ReportInterfaceKey.REPORT_LOADING)==0)
{
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
              " value=\""+ReportInterfaceKey.ACTION_EXPORT_REPORT_EXCEL+"\">");

  out.println("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0\" width=\"300\" height=\"100\">");
         out.println("<param name=\"movie\" value=\"../resources/img/loading.swf\">");
         out.println("<param name=\"quality\" value=\"high\">");
         out.println("<embed src=\"../resources/img/loading.swf\" quality=\"high\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\" width=\"300\" height=\"100\"></embed>");
   out.println("</object>");
  out.println("<script>");
  out.println("setTimeout(\"reportForm.submit();\", 5000);");
  out.println("</script>");
}
else if(reportStatus.compareTo(ReportInterfaceKey.REPORT_FINISHED)==0)
{  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
              " value=\""+ReportInterfaceKey.ACTION_EXPORT_REPORT_EXCEL_FINISHED+"\">");

  out.println("<script>");
  out.println("reportForm.submit();");
  out.println("</script>");
}
out.println("</form>");
%>
</body>
</html>
