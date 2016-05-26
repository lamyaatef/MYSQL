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
%>

<%
    int pageStartNum = 1 ; 
    int rowPerPage = 200;
%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    <SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>
  </head>
  <body>
    <CENTER>
      <H2> Reports List</H2>
    </CENTER>
  

    <form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formReportList" id="formReportList" method="post">&nbsp;

    <%
    
      
    
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
      out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.PARAM_LOAD_REPORT_ID+"\""+
                  " value=\""+"\">");                    

      out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.PARAM_PREVIEW_REPORT_ID+"\""+
                  " value=\""+"\">");                    
      out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.PARAM_PREVIEW_REPORT_ROWS_PER_PAGE+"\""+
                  " value=\""+"\">");                    
      out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.PARAM_PREVIEW_REPORT_PAGE_NUM+"\""+
                  " value=\""+"\">");                    

      out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAMS_NUM+"\""+
                  " value=\""+"\">");                    

out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.PARAM_LOAD_REPORT_DETAILS_ID+"\""+
                  " value=\""+"\">");                    


out.println("<script>");      
      out.println("function exportReportWithParameter(reportId) {");
      out.println("document.formReportList."+InterfaceKey.HASHMAP_KEY_ACTION+
                    ".value=\""+ReportInterfaceKey.ACTION_SHOW_REPORT_PARAM_EXCEL+"\";");
      out.println("document.formReportList."+ReportInterfaceKey.PARAM_LOAD_REPORT_DETAILS_ID+
                    ".value=reportId;");  
      out.println("document.formReportList."+ReportInterfaceKey.PARAM_PREVIEW_REPORT_ROWS_PER_PAGE+
                    ".value="+rowPerPage+";");  
      out.println("document.formReportList."+ReportInterfaceKey.PARAM_PREVIEW_REPORT_PAGE_NUM+
                    ".value="+pageStartNum+";");       
      out.println("document.formReportList."+ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAMS_NUM+
                    ".value="+"0"+";");   
                    
      out.println("formReportList.submit();}");
      out.println("</script>");

    
    out.println("<script>");      
      out.println("function viewReportWithParameter(reportId) {");
      out.println("document.formReportList."+InterfaceKey.HASHMAP_KEY_ACTION+
                    ".value=\""+ReportInterfaceKey.ACTION_SHOW_REPORT_PARAM+"\";");
      out.println("document.formReportList."+ReportInterfaceKey.PARAM_LOAD_REPORT_DETAILS_ID+
                    ".value=reportId;");  
      out.println("document.formReportList."+ReportInterfaceKey.PARAM_PREVIEW_REPORT_ROWS_PER_PAGE+
                    ".value="+rowPerPage+";");  
      out.println("document.formReportList."+ReportInterfaceKey.PARAM_PREVIEW_REPORT_PAGE_NUM+
                    ".value="+pageStartNum+";");       
      out.println("document.formReportList."+ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAMS_NUM+
                    ".value="+"0"+";");   
                    
      out.println("formReportList.submit();}");
      out.println("</script>");

      out.println("<script>");      
      out.println("function viewReport(reportId) {");
      out.println("document.formReportList."+InterfaceKey.HASHMAP_KEY_ACTION+
                    ".value=\""+ReportInterfaceKey.ACTION_PREVIEW_REPORT+"\";");
      out.println("document.formReportList."+ReportInterfaceKey.PARAM_PREVIEW_REPORT_ID+
                    ".value=reportId;");  
      out.println("document.formReportList."+ReportInterfaceKey.PARAM_PREVIEW_REPORT_ROWS_PER_PAGE+
                    ".value="+rowPerPage+";");  
      out.println("document.formReportList."+ReportInterfaceKey.PARAM_PREVIEW_REPORT_PAGE_NUM+
                    ".value="+pageStartNum+";");       
      out.println("document.formReportList."+ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAMS_NUM+
                    ".value="+"0"+";");   

                    
      out.println("formReportList.submit();}");
      out.println("</script>");

                  
      out.println("<script>");      
      out.println("function loadReport(reportId) {");
      out.println("document.formReportList."+InterfaceKey.HASHMAP_KEY_ACTION+
                    ".value=\""+ReportInterfaceKey.ACTION_LOAD_REPORT+"\";");
      out.println("document.formReportList."+ReportInterfaceKey.PARAM_LOAD_REPORT_ID+
                    ".value=reportId;");        
      out.println("formReportList.submit();}");
      out.println("</script>");


      out.println("<script>");      
      out.println("function newReport() {");
      out.println("document.formReportList."+InterfaceKey.HASHMAP_KEY_ACTION+
                    ".value=\""+ReportInterfaceKey.ACTION_INITIALIZE_WIZARD+"\";");
      out.println("formReportList.submit();}");
      out.println("</script>");
    %>
    
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
    <TR class=TableHeader>
    <td width="20%" nowrap align=middle>  Report  Name</td>
    <td width="40%" nowrap align=middle>  Description</td>
    <TD noWrap align=middle width="20%">Export To Excel</TD>    
    <td width="10%" nowrap align=middle>View</td>
    </TR>	


<%
showReportList( (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT) , out, request);
%>
      </TABLE>

      
    </form>
   </body>
</html>

<%!
public void showReportList(HashMap objDataHashMap , JspWriter out, HttpServletRequest request)
{
    String appName = request.getContextPath();
    int pageStartNum = 1 ; 
    int rowPerPageForExcel = 65000;
try{
  if (objDataHashMap == null)
  {
  
   return;
   }


  String userID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
      
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value="+userID+">");

      
  Vector vecReportList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION) ;

  if (vecReportList ==null)
  {
    return;
  }
  //Utility.logger.debug ( "number of Reports ------> " +  vecReportList.size() ) ;
  for (int i=0; i<vecReportList.size(); i++)
  {
    ReportBuilderWizardDTO reportWizard =(ReportBuilderWizardDTO) vecReportList.elementAt (i);  
    ReportDTO reportDTO = reportWizard.getReport(); 
    
      
    out.println("<TR class=TableTextNote>");
    //out.println("<TD class=TableTextNote noWrap>"+reportDTO.getReportID()+"</TD>");    
    out.println("<TD class=TableTextNote>" +    reportDTO.getReportName() +"</TD>");
    out.println("<TD class=TableTextNote>" +    reportDTO.getReportDescription() +"</TD>");
    
    out.println("<TD class=TableTextNote align=middle>");

    if (reportWizard.getReportParameters().size()!=0)
    
    {
    
    out.println("<INPUT id=button2 class=button type=button value=\"Export To Excel\" name=button2 "
                +"onclick=\"exportReportWithParameter("+reportDTO.getReportID()+")\">");
    }
    else
    {
     out.println("<INPUT id=button2 class=button type=button value=\"Export To Excel\" name=button2 "
                +"onclick=\"openWindowfull('"+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"+
                InterfaceKey.HASHMAP_KEY_ACTION+"="+ReportInterfaceKey.ACTION_EXPORT_REPORT_EXCEL+"&"+
                ReportInterfaceKey.PARAM_PREVIEW_REPORT_ID+"="+reportDTO.getReportID()+"&"+
                ReportInterfaceKey.PARAM_PREVIEW_REPORT_ROWS_PER_PAGE+"="+rowPerPageForExcel+"&"+
                ReportInterfaceKey.PARAM_PREVIEW_REPORT_PAGE_NUM+"="+pageStartNum+"&"+
                ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAMS_NUM+"=0');\">");
    }
    out.println("</TD>");

                    
                    

    out.println("<TD class=TableTextNote align=middle>");

    if (reportWizard.getReportParameters().size()==0)
    {
      out.println("<input class=button type=\"button\" name=\"edit_kpi_template1\" value=\" View \"");
      out.println("onclick=\"viewReport("+reportDTO.getReportID()+")\">");
    }
    else
    {
    
     out.println("<input class=button type=\"button\" name=\"edit_kpi_template1\" value=\" View \"");
    out.println("onclick=\"viewReportWithParameter("+reportDTO.getReportID()+")\">");
    }
    
    out.println("</TD>");
    
    out.println("</TR>");
  }
  }
  catch(Exception e)
  {
  e.printStackTrace();
  }
}
%>