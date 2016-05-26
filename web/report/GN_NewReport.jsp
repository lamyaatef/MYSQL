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
         import ="com.mobinil.sds.core.system.gn.dataview.dto.*"
         
 %>
 <%
     String appName = request.getContextPath();
 %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">  
        <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/combobox.js"></SCRIPT>
    <script>
function xremoveFromListOrderList(source, destination,orderTypeDestination) 
{


  for(i=0;i<destination.options.length;i++) 
  {
    if(destination.options[i].selected == true && destination.options[i].text!='-- SELECTED FIELDS --') 
    {
      var no   = new Option();
      no.value = destination.options[i].value;
      var text = destination.options[i].text;
      no.text  =   text.substring(0,text.lastIndexOf("("));       
      source.options[source.options.length] = no;		
    }
  }//end for
	for(i=destination.options.length-1;i>=0;i--) 
  {
		if(destination.options[i].selected == true && destination.options[i].text!='-- SELECTED FIELDS --') 
    {
			destination.options[i]=null;	
      orderTypeDestination.options[i]=null;
		}
	}//end for
}
</script>

<script>
function xremoveAllOrderList(source, destination,orderTypeDestination) 
{
	for(i=1;i<destination.options.length;i++) 
  {
		destination.options[i].selected = true
	}//end for
	xremoveFromListOrderList(source, destination ,orderTypeDestination );
}
</script>
 
 <script>
function xaddAllOrderList(source, destination,orderTypeDestination,orderType) 
{
	for(i=1;i<source.options.length;i++) 
  {
		source.options[i].selected = true
	}//end for
	xaddToOrderList(source, destination, orderTypeDestination,orderType );
}
</script>

<script>
function xaddToOrderList(source, destination, orderTypeDestination, orderType) 
{
	 for(i=0;i<source.options.length;i++) 
  {
    if(source.options[i].selected == true && source.options[i].text!='-- AVAILABLE FIELDS --') 
    {
      var no   = new Option();
      no.value = source.options[i].value;
      no.text  = source.options[i].text + ' ('+orderType+')';
      destination.options[destination.options.length] = no;
      var orderTypeNode = new Option();
      orderTypeNode.value= orderType;
      orderTypeNode.text= orderType;
      orderTypeDestination.options[orderTypeDestination.options.length]=orderTypeNode;
    }
  }
	for(i=source.options.length-1;i>=0;i--) 
  {
		if(source.options[i].selected == true && source.options[i].text!='-- AVAILABLE FIELDS --') 
    {
			source.options[i]=null;		
		}
  }
	
}
</script>
  </head>
  <body>
        <CENTER>
<H2>  New&nbsp;Report </H2>
</CENTER>
 <form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formReportDetails" id="formReportDetails" method="post">

<%


HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

Vector vecScopesList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION) ;
Vector vecGroupsList = (Vector) objDataHashMap.get (ReportInterfaceKey.HASHMAP_KEY_COLLECTION_GROUP_REPORTS) ;

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
out.println("<script>");
out.println("function saveNewReport(selectList) {");

out.println("if (selectList.options.length<=1) {");
out.println("alert('There Must be at least one field in the selected fields');return false;}");


out.println("for(i=1;i<selectList.options.length;i++){");
out.println("selectList[i].selected=true;}");
//out.println("for(i=1;i<orderList.options.length;i++){");
//out.println("orderList[i].selected=true;}");
//out.println("for(i=1;i<orderTypeList.options.length;i++){");
//out.println("orderTypeList[i].selected=true;}");
out.println("document.formReportDetails."+InterfaceKey.HASHMAP_KEY_ACTION+
             ".value=\""+ReportInterfaceKey.ACTION_SAVE_REPORT+"\";");
out.println("formReportDetails.submit();");
out.println("}");
out.println("</script>");


//out.println("<script>");
//out.println("function saveNewReport() {");
//
//out.println("document.formReportDetails."+InterfaceKey.HASHMAP_KEY_ACTION+
//             ".value=\""+ReportInterfaceKey.ACTION_SAVE_REPORT+"\";");
//out.println("formReportDetails.submit();");
//out.println("}");
//out.println("</script>");

out.println("<TABLE style=\"WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px\" cellSpacing=2 cellPadding=1 width=\"746\" border=1>");
out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Report&nbsp;Name *</TD>");

out.println("<TD class=TableTextNote colSpan=4><INPUT style=\"WIDTH: 452px; HEIGHT: 22px\" size=66"
              + " value=\"" +"\""  
              + " name=\""+ReportInterfaceKey.PARAM_SAVE_REPORT_NAME+"\">");


out.println("</TD></TR>");
out.println("<TR>");
out.println("<TD class=TableTextNote width=\"20%\" noWrap>   Related&nbsp;Report DataView *</TD>");
out.println("<TD class=TableTextNote colSpan=4><SELECT "); 
out.println("name=\""+ReportInterfaceKey.PARAM_SAVE_REPORT_DATAVIEW_ID+"\">");

for (int i=0; i<vecScopesList.size(); i++)
{
BriefDataViewDTO briefDVDto = (BriefDataViewDTO)vecScopesList.elementAt (i);
out.println("<OPTION value="+briefDVDto.getDataViewID()+" selected>"+ briefDVDto.getDataViewName()+"</OPTION>");
} 
out.println("</SELECT></TD></TR>");
out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Report&nbsp;Description *</TD>");
out.println("<TD class=TableTextNote colSpan=4><TEXTAREA id=textarea1 style=\"WIDTH: 451px; HEIGHT: 84px\" name=\""+ReportInterfaceKey.PARAM_SAVE_REPORT_DESC+"\" rows=4 cols=47>"+"</TEXTAREA></TD></TR>");

out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Report Groups *</TD>");
out.println("<TD class=TableTextNote colSpan=4>");
out.println("<table>");
out.println("<TR ><td  nowrap rowspan=5 align=left>");
out.println("<select  size=\"10\" multiple name=\"groups_fields\">");
out.println("<option value=\"\">-- AVAILABLE FIELDS --</option>");
for (int i = 0 ; i < vecGroupsList.size();i++)
{
GroupDTO gdto =(GroupDTO) vecGroupsList.elementAt(i);

int groupId  = gdto.getGroupId().intValue();
String groupName = gdto.getGroupName();

out.println("<option value=\""+groupId+"\">"+groupName+"</option>");


}
out.println("</select>");
out.println("</td>");
out.println("</TR>");

out.println("<TR>");
out.println("<td align=left width=\"5%\"><input value=\"     >     \" class=\"button\" type=\"button\" name=\"addToList\" "+
            " onclick=\"xaddToList(groups_fields"+", "+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST+");\""
            +"  style=\"WIDTH: 60px; HEIGHT: 20px\"></td>");

out.println("<td nowrap rowspan=4>");
out.println("<select  size=\"10\" multiple name=\""+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST +"\">");
out.println("<option value=\"\">-- SELECTED FIELDS --</option>");
out.println("</select>");
out.println("</td>");
out.println("</TR>");

out.println("<TR >");
out.println("<td align=left width=\"5%\"><input class=\"button\" type=\"button\" name=\"addAll\" value=\"    >>    \" "+
            "onclick=\"xaddAll(groups_fields"+", "+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST+");\""
            + " style=\"WIDTH: 60px; HEIGHT: 20px\" size=19></td>");


out.println("</TR>");

out.println("<TR >");
out.println("<td align=left width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeFromList\" value=\"     <      \" "+
" onclick=\"xremoveFromList(groups_fields"+", "+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST+");\""
+" style=\"WIDTH: 61px; HEIGHT: 20px\" size=19></td>");
out.println("</TR>");
out.println("<TR >");
out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeAll\" value=\"    <<    \" "+
" onclick=\"xremoveAll(groups_fields"+", "+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST+");\"></TD>");

out.println("</TR>");
out.println("</table>");
out.println("</TD></TR>");


out.println("<TR class=TableTextNote><TD colspan=5 class=TableTextNote><font size=1>* indicates mandatory fields.&nbsp;</font></TD></TR>");
out.println("</TABLE></TABLE>");




%>

<table  cellspacing="2" cellpadding="1" border="0" width="747" style="WIDTH: 747px; HEIGHT: 26px">
<tr>
<td align=middle>
<!--      <P align=center>

 &nbsp;<INPUT class=button onclick="saveNewReport();" type=button value=" Save " name=save></P></td>-->
<%
out.println("<P align=center><input class=\"button\" type=\"button\" name=\"save\" value=\"    Save    \"");

out.println("onclick=\"saveNewReport("+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST+");\"></P></td>" );
%>
 
</tr>
</table>
</form>

  </body>
</html>




