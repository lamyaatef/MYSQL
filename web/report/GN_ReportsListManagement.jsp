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
         import="com.mobinil.sds.core.system.gn.reports.model.*"
%>

<%
    int pageStartNum = 1 ; 
    int rowPerPage = 200;
        String appName = request.getContextPath();
%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">

      <link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
      <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
      <script type="text/javascript" src="../resources/js/jquery_tablesorter_pack.js"></script>
      <link rel="stylesheet" href="../resources/css/themes/blue/style.css" type="text/css"/>
    
  </head>
  <body>
  <script language=JavaScript>
function checkEnter()
{
	//alert (event.keyCode);
    if (event.keyCode==13) 
    {
    	document.formReportList.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(ReportInterfaceKey.ACTION_SHOW_REPORT);%>';
        document.formReportList.submit();
    }
	  
	   // alert (event.keyCode);
	    return event.keyCode ;
	    
	 
}
</script>
  <script>
  function setSearchValues(reportName)
  {
    document.getElementById("<%=ReportInterfaceKey.INPUT_SEARCH_TEST_REPORT_NAME%>").value = reportName;
    
  }
  </script>
   <script language="javascript">
        $(document).ready(function(){$("#reportListTable").tablesorter(); });
  </script>
    <CENTER>
      <H2> Reports List</H2>
    </CENTER>
  

    <form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formReportList" id="formReportList" method="post">&nbsp;

    <%
	
    String exportPath = request.getRealPath("/report/");
    HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    Vector vecReportList = new Vector();
    if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_COLLECTION))
    {
    	vecReportList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION) ;
    }
    String reportName = "";
    if(objDataHashMap.containsKey(ReportInterfaceKey.INPUT_SEARCH_TEST_REPORT_NAME))
    {
    	reportName = (String)objDataHashMap.get(ReportInterfaceKey.INPUT_SEARCH_TEST_REPORT_NAME);
    	if(reportName == null)
    	{
    		reportName = "";
    	}
    }
    out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.EXPORT_FILE_PATH+"\""+
            " value=\""+exportPath+"\">");

    
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
      out.println("function exportReportToText(reportId) {");
      out.println("document.formReportList."+InterfaceKey.HASHMAP_KEY_ACTION+
                    ".value=\""+ReportInterfaceKey.ACTION_SHOW_REPORT_PARAM_TEXT+"\";");
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

      out.println("function saveReportStatus() {");
      out.println("document.formReportList."+InterfaceKey.HASHMAP_KEY_ACTION+".value=\""+ReportInterfaceKey.ACTION_SAVE_REPORT_STATUS+"\";");        
      out.println("formReportList.submit();}");
      out.println("</script>");


      HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      
      String strComingAction = (String)dataHashMap.get (InterfaceKey.HASHMAP_KEY_ACTION);
      boolean reportPrivilage = false;
    %>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
      <td align=middle>Report Name</td>
        <td align=middle><input onkeypress = "checkEnter(event);" type='text' name='<%=ReportInterfaceKey.INPUT_SEARCH_TEST_REPORT_NAME%>' id='<%=ReportInterfaceKey.INPUT_SEARCH_TEST_REPORT_NAME%>' value ="<%=reportName%>"></td>
        
         </tr>
      <tr>
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchReport\" id=\"searchReport\" onclick=\"document.formReportList."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+ReportInterfaceKey.ACTION_SHOW_REPORT+"';"+
                  "formReportList.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('');\">");          
        %>
        </td>
      </tr>
    </TABLE>
    <%
      if (vecReportList.size()!=0)
      {%>
    <table class="tablesorter" id="reportListTable">
    
    <thead>
  <tr>
    <th>Report  Name </th>
    <th>Description </th>
    <th>Export To Excel</th>
    <th>Export To Text </th>
    <th>View </th>
    <th> Edit</th>    
    <th>Status</th> 
  </tr>
  </thead>
  <%
    }%>
<tbody>


<%
showReportList( (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT) , out, request);
%>
</tbody>
      </table>

        <table  cellspacing="2" cellpadding="1" border="0" width="100%">
        <tr>
          <td align=middle><!--            <input class=button type="button" name="update" value="Update KPI Template" 
            onclick="document.formKPITemplateManagment.action.value='action_update_kpi_template'; formKPITemplateManagment.submit();" disabled>-->
            <INPUT class=button type=button onclick="newReport();" value="New Report">
            <%
            if(vecReportList.size()!=0)
            {%>
            <INPUT class=button type=button onclick="saveReportStatus();" value="Save Status">
            <%
            }%>
          </td>
        </tr>
      </table>
     
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
  
  Vector vecReportList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION) ;
  String strComingAction = (String)objDataHashMap.get (InterfaceKey.HASHMAP_KEY_ACTION);
  
  
  if (vecReportList ==null)
  {
    return;
  }
  //Utility.logger.debug ( "number of Reports ------> " +  vecReportList.size() ) ;
  for (int i=0; i<vecReportList.size(); i++)
  {
//    ReportBuilderWizardDTO reportWizard =(ReportBuilderWizardDTO) vecReportList.elementAt (i);  

    ReportDTO reportDTO =  (ReportDTO) vecReportList.elementAt (i);
    
      
    out.println("<TR class=\""+InterfaceKey.STYLE[i%2]+"\">");
    //out.println("<TD class=TableTextNote noWrap>"+reportDTO.getReportID()+"</TD>");    
    out.println("<TD>" +    reportDTO.getReportName() +"</TD>");
    out.println("<TD>" +    reportDTO.getReportDescription() +"</TD>");
    
    out.println("<TD align=middle>");

   // if (reportWizard.getReportParameters().size()!=0)
    
    //{
    
    out.println("<INPUT id=button2 class=button type=button value=\"Export To Excel\" name=button2 "
                +"onclick=\"exportReportWithParameter("+reportDTO.getReportID()+")\">");
    /*}
    else
    {
     out.println("<INPUT id=button2 class=button type=button value=\"Export To Excel\" name=button2 "
                +"onclick=\"openWindowfull('"+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"+
                InterfaceKey.HASHMAP_KEY_ACTION+"="+ReportInterfaceKey.ACTION_EXPORT_REPORT+"&"+
                ReportInterfaceKey.PARAM_PREVIEW_REPORT_ID+"="+reportDTO.getReportID()+"&"+
                ReportInterfaceKey.PARAM_PREVIEW_REPORT_ROWS_PER_PAGE+"="+rowPerPageForExcel+"&"+
                ReportInterfaceKey.PARAM_PREVIEW_REPORT_PAGE_NUM+"="+pageStartNum+"&"+
                ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAMS_NUM+"=0');\">");
    }*/
    out.println("</TD>");
    
    out.println("</TD>");

    out.println("<TD calign=middle>");
    out.println("<INPUT id=button3 class=button type=button value=\"Export To Text\" name=button3 "
                +"onclick=\"exportReportToText("+reportDTO.getReportID()+")\">");
    out.println("</TD>"); 

    out.println("<TD align=middle>");

    //if (reportWizard.getReportParameters().size()==0)
    //{
    //  out.println("<input class=button type=\"button\" name=\"edit_kpi_template1\" value=\" View \"");
    //  out.println("onclick=\"viewReport("+reportDTO.getReportID()+")\">");
    //}
    //else
    //{
    
     out.println("<input class=button type=\"button\" name=\"edit_kpi_template1\" value=\" View \"");
    out.println("onclick=\"viewReportWithParameter("+reportDTO.getReportID()+")\">");
    //}
    
    out.println("</TD>");
    

    out.println("<TD align=middle>"
                +"<INPUT class=button onclick=\"loadReport("+reportDTO.getReportID()+")\""
                +"type=button value=\" Edit \" name=edit_kpi_template1></TD>");


          Vector reportStatusVec = (Vector) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
          out.println("<TD align=middle>");

          out.println("<select name=\"reportstatus"+reportDTO.getReportID()+"\" id=\"reportstatus"+reportDTO.getReportID()+"\">");
          int intcurrentreportstatusid = reportDTO.getReportStatusID();
          String currentreportstatusid = ""+intcurrentreportstatusid;
          for(int k= 0; k<reportStatusVec.size(); k++)
          {
            ReportStatusModel reportStatusModel = (ReportStatusModel) reportStatusVec.get(k);            

            String reportStatusId = reportStatusModel.getReportStatusId();
            String reportStatusName = reportStatusModel.getReportStatusName();
            String reportStatusDesc = reportStatusModel.getReportStatusDescription();
            String optionselected = "";
            if(currentreportstatusid.equals(reportStatusId))
            {
                optionselected = "selected";
            }
            out.println("<option value="+reportStatusId+" "+optionselected+">"+reportStatusName+"</option>");
          }
          out.println("</select>");
    
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