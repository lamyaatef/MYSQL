<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.gn.reports.*"
         import="com.mobinil.sds.core.system.gn.reports.dto.*"
         import ="com.mobinil.sds.core.system.gn.dataview.dto.*"
         
 %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">    
  </head>
  <body>
        <CENTER>
<H2>  Update&nbsp;Report </H2>
</CENTER>
 <form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formReportDetails" id="formReportDetails" method="post">

<%

HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

ReportBuilderWizardDTO repDto = (ReportBuilderWizardDTO) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_DTO_OBJECT) ;

Utility.logger.debug(" rep dto = " + repDto);

int reportDataViewId = repDto.getReport().getReportDataViewID();
int reportId = repDto.getReport().getReportID();
String reportDataViewName = repDto.getDetailedDataView().getDataViewName();
String reportName = repDto.getReport().getReportName();
String reportDescription = repDto.getReport().getReportDescription();

Vector vecScopesList = repDto.getAvailableDataViews();

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
      out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.PARAM_LOAD_REPORT_DETAILS_ID+"\""+
                  " value=\""+"\">");                    

out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.PARAM_UPDATE_REPORT_ID+"\""+
                  " value=\""+"\">");
      out.println("<script>");
      out.println("function loadReportDetails(reportId) {");

      out.println("document.formReportDetails."+InterfaceKey.HASHMAP_KEY_ACTION+
                   ".value=\""+ReportInterfaceKey.ACTION_LOAD_REPORT_DETAILS+"\";");
      out.println("document.formReportDetails."+ReportInterfaceKey.PARAM_LOAD_REPORT_DETAILS_ID+
                    ".value=reportId;");        
      out.println("formReportDetails.submit();");
      out.println("}");
      out.println("</script>");

      out.println("<script>");
      out.println("function saveReport() {");

      out.println("document.formReportDetails."+InterfaceKey.HASHMAP_KEY_ACTION+
                   ".value=\""+ReportInterfaceKey.ACTION_UPDATE_REPORT+"\";");

      out.println("document.formReportDetails."+ReportInterfaceKey.PARAM_UPDATE_REPORT_ID+
                    ".value="+reportId+";");        
      out.println("formReportDetails.submit();");
      out.println("}");
      out.println("</script>");

out.println("<TABLE style=\"WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px\" cellSpacing=2 cellPadding=1 width=\"746\" border=1>");
out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Report&nbsp;Name *</TD>");

out.println("<TD class=TableTextNote colSpan=4><INPUT style=\"WIDTH: 452px; HEIGHT: 22px\" size=66"
              + " value=\""+reportName +"\""  
              + " name="+ReportInterfaceKey.PARAM_UPDATE_REPORT_NAME+">");


out.println("</TD></TR>");
out.println("<TR>");
out.println("<TD class=TableTextNote width=\"20%\" noWrap>   Related&nbsp;Report DataView *</TD>");
out.println("<TD class=TableTextNote colSpan=4><SELECT style=\"WIDTH: 751px\""); 
out.println("name="+ReportInterfaceKey.PARAM_UPDATE_REPORT_DATAVIEW_ID+">");
for (int i=0; i<vecScopesList.size(); i++)
{
BriefDataViewDTO briefDVDto = (BriefDataViewDTO)vecScopesList.elementAt (i);
if (reportDataViewId==briefDVDto.getDataViewID())
{
out.println("<OPTION value="+briefDVDto.getDataViewID()+" selected>"+ briefDVDto.getDataViewName()+"</OPTION>");
}
else
{
  out.println("<OPTION value="+briefDVDto.getDataViewID()+" >"+ briefDVDto.getDataViewName()+"</OPTION>");
}
}
//out.println("<OPTION value="+reportDataViewId+" selected>"+ reportDataViewName+"</OPTION>"); 

out.println("</SELECT></TD></TR>");
out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Report&nbsp;Description *</TD>");
out.println("<TD class=TableTextNote colSpan=4><TEXTAREA id="+ReportInterfaceKey.PARAM_UPDATE_REPORT_DESC +" style=\"WIDTH: 451px; HEIGHT: 84px\" name="+ReportInterfaceKey.PARAM_UPDATE_REPORT_DESC+" rows=4 cols=47>"+reportDescription+"</TEXTAREA></TD></TR>");
out.println("<TR class=TableTextNote><TD colspan=5 class=TableTextNote><font size=1>* indicates mandatory fields.&nbsp;</font></TD></TR></TABLE>");
out.println("<TR class=TableHeader>");
out.println("<TD noWrap colSpan=3><STRONG><FONT size=4>Report Displayed"); 
out.println("Fields</FONT></STRONG></TD></TR>");
//out.println("<TR ><td nowrap rowspan=5 align=right>");
//out.println("<select  size=\"10\" multiple name=\"data_view_fields\">");
//out.println("<option value=\"\">-- AVAILABLE FIELDS --</option>");
//for (int i = 0 ; i < dataViewFieldsVector.size();i++)
//{
//FieldModel fieldModel =(FieldModel) dataViewFieldsVector.elementAt(i);
//int fieldId  = fieldModel.getFieldID();
//String fieldName = fieldModel.getFieldName();
//if (reportSelectedFieldsTable.get(fieldId+"")==null)
//{
//  out.println("<option value=\""+fieldId+"\">"+fieldName+"</option>");
//}
//
//}
//out.println("</select>");
//out.println("</td>");
//out.println("</TR>");
//
//
//String disableButton ;
//if (reportIsOverRided)
//{
//disableButton ="disabled";
//}
//else
//{
//disableButton ="";
//}
//
//
//out.println("<TR>");
//out.println("<td width=\"5%\"><input value=\"     >     \" class=\"button\" type=\"button\" name=\"addToList\" "
//            +disableButton +" onclick=\"xaddToList(data_view_fields"+", "+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST+");\""
//            +"  style=\"WIDTH: 60px; HEIGHT: 20px\"></td>");
//
//out.println("<td nowrap rowspan=4>");
//out.println("<select  size=\"10\" multiple name=\""+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST +"\">");
//out.println("<option value=\"\">-- SELECTED FIELDS --</option>");
//for (int i = 0 ; i < reportSelectedFieldsVector.size();i++)
//{
//FieldModel fieldModel =(FieldModel) reportSelectedFieldsVector.elementAt(i);
//int fieldId  = fieldModel.getFieldID();
//String fieldName = fieldModel.getFieldName();
//out.println("<option value=\""+fieldId+"\">"+fieldName+"</option>");
//}
//out.println("</select>");
//out.println("</td>");
//out.println("</TR>");
//
//out.println("<TR >");
//out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"addAll\" value=\"    >>    \" "
//            +disableButton+" onclick=\"xaddAll(data_view_fields"+", "+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST+");\""
//            + " style=\"WIDTH: 60px; HEIGHT: 20px\" size=19></td>");
//
//
//out.println("</TR>");
//
//out.println("<TR >");
//out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeFromList\" value=\"     <      \" "
//+disableButton+" onclick=\"xremoveFromList(data_view_fields"+", "+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST+");\""
//+" style=\"WIDTH: 61px; HEIGHT: 20px\" size=19></td>");
//out.println("</TR>");
//out.println("<TR >");
//out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeAll\" value=\"    <<    \" "
//+disableButton+" onclick=\"xremoveAll(data_view_fields"+", "+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST+");\"></TD>");
//out.println("</TR>");
out.println("</TABLE>");
%>

<table  cellspacing="2" cellpadding="1" border="0" width="747" style="WIDTH: 747px; HEIGHT: 26px">
<tr>
<td align=middle>
      <P align=center>
<input class=button type="button" name="save"
 value=" Customize " onclick="loadReportDetails(<%out.print(reportId);%>);">&nbsp;<INPUT class=button onclick="saveReport();" type=button value=" Save " name=save></P></td>
</tr>
</table>
</form>

  </body>
</html>




