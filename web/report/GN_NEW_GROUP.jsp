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
         import ="com.mobinil.sds.core.system.gn.dataview.dto.*"
         
 %>

 <%
 String appName = request.getContextPath();

 %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">    
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    <SCRIPT language=JavaScript>
function loadValues(control,value)
  {
    document.formGroupDetails.control.value=value;
  }
    
  function checkbeforSubmit()
  {
    if(NonBlank(document.formGroupDetails.<%out.print(ReportInterfaceKey.CONTROL_GROUP_NAME);%>, true, 'Group Name'))
    {
      if(NonBlank(document.formGroupDetails.<%out.print(ReportInterfaceKey.CONTROL_GROUP_STATUS);%>, true, 'Group Status'))
      {
        document.formGroupDetails.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(ReportInterfaceKey.ACTION_SAVE_GROUP);%>';
        formGroupDetails.submit();
      }
    } 
    return false;
  }
</SCRIPT>
  </head>
  <body>
        <CENTER>
<H2>  New&nbsp;Group </H2>
</CENTER>
 <form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formGroupDetails" id="formGroupDetails" method="post" >

<%

HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

 String strComingAction = (String)objDataHashMap.get (InterfaceKey.HASHMAP_KEY_ACTION);
 boolean reportPrivilage = false;
 String groupName="";
 String groupDesc="";
 String groupStatus="";
 int statucCount=2;
if(strComingAction.compareTo(ReportInterfaceKey.ACTION_LOAD_GROUP)==0)
    {
    
      reportPrivilage = true;
      statucCount=3;
      
    }
Vector vecStatusList = (Vector) objDataHashMap.get(ReportInterfaceKey.HASHMAP_KEY_COLLECTION_GROUP_STATUS) ;

System.out.println(vecStatusList.size());

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

out.println("<TABLE style=\"WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px\" cellSpacing=2 cellPadding=1 width=\"746\" border=1>");
out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Group&nbsp;Name *</TD>");

out.println("<TD class=TableTextNote colSpan=4><INPUT style=\"WIDTH: 452px; HEIGHT: 22px\" size=66"
              + " value=\"" +"\""  
              + " name=\""+ReportInterfaceKey.CONTROL_GROUP_NAME+"\">");


out.println("</TD></TR>");
out.println("<TR>");
out.println("<TD class=TableTextNote width=\"20%\" noWrap>Group&nbsp;Description </TD>");
out.println("<TD class=TableTextNote colSpan=4><TEXTAREA id=textarea1 style=\"WIDTH: 451px; HEIGHT: 84px\" name=\""+ReportInterfaceKey.CONTROL_GROUP_DESC+"\" rows=4 cols=47>"+"</TEXTAREA></TD></TR>");

out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Group&nbsp;Status *</TD>");
out.println("<TD class=TableTextNote colSpan=4><SELECT "); 
out.println("name=\""+ReportInterfaceKey.CONTROL_GROUP_STATUS+"\">");

for (int i=0; i<statucCount; i++)
{
GroupStatusDTO status = (GroupStatusDTO)vecStatusList.elementAt (i);
out.println("<OPTION value="+status.getId());
if (!reportPrivilage &&i==0){
out.println(" selected");
}
out.println(">"+ status.getStatus()+"</OPTION>");
} 
out.println("</SELECT></TD></TR>");
out.println("<TR class=TableTextNote><TD colspan=5 class=TableTextNote><font size=1>* indicates mandatory fields.&nbsp;</font></TD></TR></TABLE></TABLE>");
if(reportPrivilage)
{
out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.PARAM_GROUP_ID+"\""+
                  " value=\""+"\">");

        GroupDTO vecGroupDetails = (GroupDTO) objDataHashMap.get(ReportInterfaceKey.HASHMAP_KEY_COLLECTION_GROUP_DETAILS) ;

        groupName = vecGroupDetails.getGroupName();
        String groupId = vecGroupDetails.getGroupId()+"";
        groupDesc = vecGroupDetails.getGroupDesc();
        groupStatus = vecGroupDetails.getGroupStatus()+"";
        if (groupDesc==null){groupDesc="";}
        out.println("<script>");
        out.println("document.formGroupDetails."+ReportInterfaceKey.CONTROL_GROUP_NAME+".value='"+groupName+"';");
        out.println("document.formGroupDetails."+ReportInterfaceKey.CONTROL_GROUP_DESC+".value='"+groupDesc+"';");
        out.println("document.formGroupDetails."+ReportInterfaceKey.CONTROL_GROUP_STATUS+".value='"+groupStatus+"';");        
        out.println("document.formGroupDetails."+ReportInterfaceKey.PARAM_GROUP_ID+".value='"+groupId+"';");        
        out.println("</script>");
        
//        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
//        out.println("<TR class=TableHeader><td colspan=3 nowrap align=middle>"+groupName+" Reports</td></tr>");
//        out.println("<TR class=TableHeader>");
//        out.println("<td width=\"30%\" nowrap align=middle>Report Name</td>");
//        out.println("<td width=\"50%\" nowrap align=middle>Report Description</td>");
//        out.println("<td width=\"20%\" nowrap align=middle>Assigned</td></tr>");
//
//        for(int j = 0; j<vecAllReports.size(); j++)
//        {
//          ReportModel reportDTO = (ReportModel)vecAllReports.get(j);
//          int reportID = Integer.parseInt(reportDTO.getReportId());
//          String reportName = reportDTO.getReportName();
//          String reportDesc = reportDTO.getReportDescription();
//          out.println("<TR class=\""+InterfaceKey.STYLE[j%2]+"\">");
//          out.println("<td>"+reportName+"</td>");
//          if(reportDesc == null)
//          {
//            reportDesc = "";
//          }
//          out.println("<TD>"+reportDesc+"</TD>");
//          out.println("<TD align=middle>");
//          out.println("<input type=\"checkbox\" name="+ReportInterfaceKey.CONTROL_CHECKBOX_ASSIGN_REPORT_TO_GROUP+reportID+
//                      " vlaue="+reportID+"\"");
//
//        for(int k = 0; k<vecReportList.size(); k++)
//        {
//            ReportModel groupReportDTO = (ReportModel)vecReportList.get(k);
//            int intReportId = Integer.parseInt(groupReportDTO.getReportId());
//              
//                  if(intReportId == reportID)
//                  {
//                      out.print(" checked");
//                  }
//              
//        }
//          out.print(" ></TD></TR>");
//        }
//        out.println("</TABLE>");
}

%>

<table  cellspacing="2" cellpadding="1" border="0" width="747" style="WIDTH: 747px; HEIGHT: 26px">
<tr>
<td align=middle>
      <P align=center>

 &nbsp;<INPUT class=button onclick="checkbeforSubmit();" type=button value=" Save " name=save></P></td>
</tr>
</table>
</form>

  </body>
</html>




