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
<meta http-equiv="Content-Type" content="Application/txt;charset=windows-1256">
<%
    String appName = request.getContextPath();
    int pageStartNum = 1 ; 
    int rowPerPage = 200;
%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    <SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>
    <link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />
    <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
    <script type="text/javascript" src="../resources/js/jquery_tablesorter_pack.js"></script>
    <link rel="stylesheet" href="../resources/css/themes/blue/style.css" type="text/css"/>
    <script type="text/javascript">

      function Toggle(item1)
      {
        divs=document.getElementsByTagName("DIV");
        for (i=0;i<divs.length;i++) 
        {
          
            divs[i].style.display="none";
          
        }
        obj=document.getElementById(item1);
        if (obj!=null)
        {
          visible = obj.style.display!="none";
          if (visible) {
            obj.style.display="none";
          } 
          else {
             obj.style.display="";
          }
        }
      }
    </script>

    

  </head>
  <body>
  <script language="javascript">
        $(document).ready(function(){$("#groupListTable").tablesorter(); });

  </script>
    <CENTER>
      <H2> Report Groups List</H2>
    </CENTER>
  

    <form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formReportList" id="formReportList" method="post">&nbsp;

    <%
     appName = request.getContextPath();
    
     String exportPath = appName+"/report/";
     
    
    out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.EXPORT_FILE_PATH+"\""+
            " value=\""+exportPath+"\">");

    
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
      out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.PARAM_LOAD_REPORT_ID+"\""+
                  " value=\""+"\">");                    
      out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.PARAM_GROUP_ID+"\""+
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

      out.println("function newGroup() {");
      out.println("document.formReportList."+InterfaceKey.HASHMAP_KEY_ACTION+
                    ".value=\""+ReportInterfaceKey.ACTION_NEW_GROUP+"\";");
      out.println("formReportList.submit();}");

      out.println("function loadGroup(groupId) {");
      out.println("document.formReportList."+InterfaceKey.HASHMAP_KEY_ACTION+
                    ".value=\""+ReportInterfaceKey.ACTION_LOAD_GROUP+"\";");
      out.println("document.formReportList."+ReportInterfaceKey.PARAM_GROUP_ID+
                    ".value=groupId;");        
      out.println("formReportList.submit();}");

            out.println("function assignReportToGroup(groupId) {");
      out.println("document.formReportList."+InterfaceKey.HASHMAP_KEY_ACTION+
                    ".value=\""+ReportInterfaceKey.ACTION_ASSIGN_REPORT_TO_GROUP+"\";");
      out.println("document.formReportList."+ReportInterfaceKey.PARAM_GROUP_ID+
                    ".value=groupId;");        
      out.println("formReportList.submit();}");
      

      out.println("function saveGroupStatus() {");
      out.println("document.formReportList."+InterfaceKey.HASHMAP_KEY_ACTION+".value=\""+ReportInterfaceKey.ACTION_SAVE_GROUP_STATUS+"\";");        
      out.println("formReportList.submit();}");
      out.println("</script>");


      HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      
      String strComingAction = (String)dataHashMap.get (InterfaceKey.HASHMAP_KEY_ACTION);
      boolean reportPrivilage = false;
    %>

<%
showReportList((HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT) , out, request);
%>
      
<%
if(strComingAction.equals(ReportInterfaceKey.ACTION_RETRIEVE_REPORT_LIST) || strComingAction.equals(ReportInterfaceKey.ACTION_UPDATE_CUSTOMIZED_REPORT))
    {
      reportPrivilage = true;
    }
    if(reportPrivilage)
    {
    %>
       <!-- <table  cellspacing="2" cellpadding="1" border="0" width="100%">
        <tr>
          <td align=middle>            <input class=button type="button" name="update" value="Update KPI Template" 
            onclick="document.formKPITemplateManagment.action.value='action_update_kpi_template'; formKPITemplateManagment.submit();" disabled>
            <INPUT class=button type=button onclick="newGroup();" value="New Group">
                       <INPUT class=button type=button onclick="newReport();" value="New Report">
            <INPUT class=button type=button onclick="saveGroupStatus();" value="Save Group Status">-->

          </td>
        </tr>
      </table>
 <%}%>     
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
  
  Vector vecGroupList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION) ;
  String strComingAction = (String)objDataHashMap.get (InterfaceKey.HASHMAP_KEY_ACTION);
  boolean reportPrivilage = false;
  if(strComingAction.equals(ReportInterfaceKey.ACTION_RETRIEVE_REPORT_LIST) || strComingAction.equals(ReportInterfaceKey.ACTION_UPDATE_CUSTOMIZED_REPORT))
  {
    reportPrivilage = true;
  }
  
  if (vecGroupList ==null)
  {
    return;
  }
  //Utility.logger.debug ( "number of Reports ------> " +  vecGroupList.size() ) ;
  int reportListSize = vecGroupList.size();
  out.println("<TABLE class=\"tablesorter\" id=\"groupListTable\">");
    out.println("<thead>");
  out.println("<TR>");
   if(!reportPrivilage)
    {
  out.println("<th><font size=2>Report Groups</font></a></th></TR></thead><tbody>");
  }
  else{
out.println("<th ><font size=2>Report Groups</font></a></th><th><font size=2>Status</font></a></th><th><font size=2>Edit</font></a></th></TR></thead><tbody>");
  }
  for (int i=0; i<reportListSize; i++)
  {
//    ReportBuilderWizardDTO reportWizard =(ReportBuilderWizardDTO) vecGroupList.elementAt (i);  

    GroupDTO groupDTO =  (GroupDTO) vecGroupList.elementAt (i);
    Integer groupId = groupDTO.getGroupId();    
    String groupName=groupDTO.getGroupName();
    String groupStatus = groupDTO.getGroupStatus().intValue()+"";
    
    String tdStyle =InterfaceKey.STYLE[i%2];


   
    out.println("<TR class=\""+InterfaceKey.STYLE[i%2]+"\">");
    out.println("<td width=\"100%\">");
    out.println("<a id=x"+groupId+" href=\"javascript:Toggle('"+groupId+"');\">");
//    out.print("<IMG height=16 hspace=0 src=\""+appName+"/resources/img/plus.gif\" width=16 border=0 >");
    out.print(groupName+"</a>");    
    

    out.println("</td>");
    if(reportPrivilage)
    {
    Vector groupStatusVec = (Vector) objDataHashMap.get(ReportInterfaceKey.HASHMAP_KEY_COLLECTION_GROUP_STATUS);
        out.println("<td>");
    out.println("<select name=\"groupstatus"+groupId+"\" id=\"groupstatus"+groupId+"\">");

          for(int k= 0; k<groupStatusVec.size(); k++)
          {
            GroupStatusDTO gdto = (GroupStatusDTO) groupStatusVec.get(k); 
            
            String optionselected = "";
            String groupStatusId = gdto.getId().intValue()+"";
            String groupStatusName = gdto.getStatus();
            if(groupStatus.equals(groupStatusId))
            {
                optionselected = "selected";
            }
            out.println("<option value="+groupStatusId+" "+optionselected+">"+groupStatusName+"</option>");

          }
          out.println("</select>");
                          out.println("</td>");
    out.println("<td>");
    out.println("<input class=button type=\"button\" name=\"edit\" value=\" Edit \" onclick=\"loadGroup("+groupId+")\"");
    out.println(">");
    out.println("</td>");
   }
    out.println("</tr>");
    

  }
      out.println("</tbody>");
  out.println("</TABLE>");
  if(reportPrivilage)
    {
  out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=0>");
  out.println("<TR><td align=middle>");
           out.println("<INPUT class=button type=button onclick=\"newGroup();\" value=\"New Group\">");
           out.println(" <INPUT class=button type=button onclick=\"saveGroupStatus();\" value=\"Save Group Status\">");
  out.println("</TR></td></table><br>");
  }
  Vector vecAllReports = (Vector) objDataHashMap.get(ReportInterfaceKey.HASHMAP_KEY_COLLECTION_ALL_REPORTS) ;
//  Vector vecReportList = (Vector) objDataHashMap.get(ReportInterfaceKey.HASHMAP_KEY_COLLECTION_GROUP_REPORTS) ;
  for (int i=0; i<reportListSize; i++)
  {
   GroupDTO groupDTO =  (GroupDTO) vecGroupList.elementAt (i);
    Integer groupId = groupDTO.getGroupId();    
    String groupName=groupDTO.getGroupName();

  out.println("<div id=\""+groupId+"\" style=\"display: none\">");
        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
        out.println("<TR class=TableHeader><td colspan=5 nowrap align=middle>"+groupName+" Report</td></tr>");
        out.println("<TR class=TableHeader>");
        out.println("<td width=\"30%\" nowrap align=middle>Report Name</td>");
        out.println("<td width=\"50%\" nowrap align=middle>Report Description</td>");
        
       if(reportPrivilage)
        {
         out.println("<td width=\"20%\" nowrap align=middle>Assigned</td></tr>");
        }
          else
        {    
    out.println("<TD noWrap align=middle width=\"20%\">Export To Excel</TD>");
    out.println("<TD noWrap align=middle width=\"20%\">Export To Text</TD>");
    out.println("<td width=\"10%\" nowrap align=middle>View</td>");
        }

        
if(!reportPrivilage)
        {
        vecAllReports =((GroupDTO) vecGroupList.elementAt (i)).getGroupReport();
        }
        
        for(int j = 0; j<vecAllReports.size(); j++)
        {
          ReportModel reportDTO = (ReportModel)vecAllReports.get(j);
          int reportID = Integer.parseInt(reportDTO.getReportId());
          String reportName = reportDTO.getReportName();
          String reportDesc = reportDTO.getReportDescription();
          out.println("<TR class=\""+InterfaceKey.STYLE[j%2]+"\">");
          out.println("<td>"+reportName+"</td>");
          if(reportDesc == null)
          {
            reportDesc = "";
          }
          out.println("<TD>"+reportDesc+"</TD>");
if(reportPrivilage)
    {
          out.println("<TD align=middle>");
          out.println("<input type=\"checkbox\" name="+ReportInterfaceKey.CONTROL_CHECKBOX_ASSIGN_REPORT_TO_GROUP+groupId+reportID+" ");
    Vector vecReportList = groupDTO.getGroupReport();
        for(int k = 0; k<vecReportList.size(); k++)
        {
            ReportModel groupReportDTO = (ReportModel)vecReportList.get(k);
            int intReportId = Integer.parseInt(groupReportDTO.getReportId());
              
                  if(intReportId == reportID)
                  {
                      out.print(" checked");
                  }
              
        }
          out.print(" ></TD>");

}
else
{
out.println("<TD align=middle>");

   // if (reportWizard.getReportParameters().size()!=0)
    
    //{
    
    out.println("<INPUT id=button2 class=button type=button value=\"Export To Excel\" name=button2 "
                +"onclick=\"exportReportWithParameter("+reportID+")\">");
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
                +"onclick=\"exportReportToText("+reportID+")\">");
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
    out.println("onclick=\"viewReportWithParameter("+reportID+")\">");
    //}
    
    out.println("</TD>");
}
          out.print("</TR>");
        }
        out.println("</TABLE>");
        if(reportPrivilage)
    {
    out.println("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\" width=\"747\" style=\"WIDTH: 747px; HEIGHT: 26px\">");
    out.println("<tr>");
    out.println("<td align=middle>");
    out.println("<P align=center>");
     out.println("&nbsp;<INPUT class=button onclick=\"assignReportToGroup("+groupId+");\" type=button value=\" Assign Reports to  "+groupName+"\" name=save></P></td>");
    out.println("</tr>");
    out.println("</table>");
    }
    out.println("</div>");
    
  }



  }
  catch(Exception e)
  {
  e.printStackTrace();
  }
}
%>