<%@ page contentType="text/html;charset=windows-1256"%>
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
         import="com.mobinil.sds.core.system.gn.querybuilder.model.*"
         import ="com.mobinil.sds.core.system.gn.reports.dto.ReportOrderByDTO"                 
 %>


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
<%      String appName = request.getContextPath();%> 
<HTML>
<HEAD>
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/combobox.js"></SCRIPT>
<TITLE></TITLE>
</HEAD>
<BODY valign="top">
<CENTER>
<H2> Customize Report&nbsp; </H2>

<form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formReportDetails" id="formReportDetails" method="post">

<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=0>

<%

out.println("<script>");
out.println("function saveNewReport(selectList, orderList, orderTypeList) {");

out.println("if (selectList.options.length<=1) {");
out.println("alert('There Must be at least one field in the selected fields');return false;}");


out.println("for(i=1;i<selectList.options.length;i++){");
out.println("selectList[i].selected=true;}");
out.println("for(i=1;i<orderList.options.length;i++){");
out.println("orderList[i].selected=true;}");
out.println("for(i=1;i<orderTypeList.options.length;i++){");
out.println("orderTypeList[i].selected=true;}");
out.println("document.formReportDetails."+InterfaceKey.HASHMAP_KEY_ACTION+
             ".value=\""+ReportInterfaceKey.ACTION_UPDATE_CUSTOMIZED_REPORT+"\";");
out.println("formReportDetails.submit();");
out.println("}");
out.println("</script>");





HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
ReportBuilderWizardDTO repDto = (ReportBuilderWizardDTO) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_DTO_OBJECT) ;

if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
{
  String strMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
  out.println("<script>alert('"+strMsg+"');</script>");
}

boolean reportIsOverRided =  repDto.getDetailedDataView().getDataViewOverrideSQL() == null ? false : true ;

Utility.logger.debug( " reportIsOverRided " + reportIsOverRided );

int reportDataViewId = repDto.getReport().getReportDataViewID();
int reportId = repDto.getReport().getReportID();
String reportDataViewName = repDto.getDetailedDataView().getDataViewName();
String reportName = repDto.getReport().getReportName();
String reportDescription = repDto.getReport().getReportDescription();

Vector reportSelectedFieldsVector = repDto.getReport().getReportSelectedFields();
Vector dataViewFieldsVector = repDto.getDetailedDataView().getDataViewFields();
Vector reportOrderFieldVector = repDto.getReport().getReportSelectedOrderBy();

//Utility.logger.debug(reportOrderFieldVector);

if (dataViewFieldsVector ==null)
dataViewFieldsVector = new Vector();

if (reportOrderFieldVector == null)
{
Utility.logger.debug("reportOrderFieldVector is null");
  reportOrderFieldVector = new Vector();
}


Hashtable reportSelectedFieldsTable = new Hashtable();
for (int i = 0  ; i < reportSelectedFieldsVector.size();i++)
{
 FieldModel fieldModel =(FieldModel) reportSelectedFieldsVector.elementAt(i);
 int fieldId  = fieldModel.getFieldID();
 //Utility.logger.debug("field id "+ fieldId);
 reportSelectedFieldsTable.put(fieldId+"","");
}

Hashtable reportOrderByFieldsTable = new Hashtable();
for (int i = 0 ; i < reportOrderFieldVector.size();i++)
{
ReportOrderByDTO reportOrderByDto = (ReportOrderByDTO)  reportOrderFieldVector.elementAt(i);
int fieldId = reportOrderByDto.getOrderByFieldID();
String fieldName = reportOrderByDto.getOrderByFieldName();
//Utility.logger.debug("order "+ fieldId);
reportOrderByFieldsTable.put(fieldId+"","");
}



//Utility.logger.debug(repDto.getDetailedDataView().getDataViewName());
//Utility.logger.debug("data view fields vector " + dataViewFieldsVector);

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ID+"\""+
                  " value=\""+reportId+"\">");


out.println("<TR class=TableHeader>");
out.println("<TD noWrap colSpan=3><STRONG><FONT size=4>Report Displayed"); 
out.println("Fields</FONT></STRONG></TD></TR>");
out.println("<TR ><td nowrap rowspan=5 align=right>");
out.println("<select  size=\"10\" multiple name=\"data_view_fields\">");
out.println("<option value=\"\">-- AVAILABLE FIELDS --</option>");
for (int i = 0 ; i < dataViewFieldsVector.size();i++)
{
FieldModel fieldModel =(FieldModel) dataViewFieldsVector.elementAt(i);
int fieldId  = fieldModel.getFieldID();
String fieldName = fieldModel.getFieldName();
if (reportSelectedFieldsTable.get(fieldId+"")==null)
{
  out.println("<option value=\""+fieldId+"\">"+fieldName+"</option>");
}

}
out.println("</select>");
out.println("</td>");
out.println("</TR>");


String disableButton ;
if (reportIsOverRided)
{
disableButton ="disabled";
}
else
{
disableButton ="";
}


out.println("<TR>");
out.println("<td width=\"5%\"><input value=\"     >     \" class=\"button\" type=\"button\" name=\"addToList\" "
            +disableButton +" onclick=\"xaddToList(data_view_fields"+", "+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST+");\""
            +"  style=\"WIDTH: 60px; HEIGHT: 20px\"></td>");

out.println("<td nowrap rowspan=4>");
out.println("<select  size=\"10\" multiple name=\""+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST +"\">");
out.println("<option value=\"\">-- SELECTED FIELDS --</option>");
for (int i = 0 ; i < reportSelectedFieldsVector.size();i++)
{
FieldModel fieldModel =(FieldModel) reportSelectedFieldsVector.elementAt(i);
int fieldId  = fieldModel.getFieldID();
String fieldName = fieldModel.getFieldName();
out.println("<option value=\""+fieldId+"\">"+fieldName+"</option>");
}
out.println("</select>");
out.println("</td>");
out.println("</TR>");

out.println("<TR >");
out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"addAll\" value=\"    >>    \" "
            +disableButton+" onclick=\"xaddAll(data_view_fields"+", "+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST+");\""
            + " style=\"WIDTH: 60px; HEIGHT: 20px\" size=19></td>");


out.println("</TR>");

out.println("<TR >");
out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeFromList\" value=\"     <      \" "
+disableButton+" onclick=\"xremoveFromList(data_view_fields"+", "+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST+");\""
+" style=\"WIDTH: 61px; HEIGHT: 20px\" size=19></td>");
out.println("</TR>");
out.println("<TR >");
out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeAll\" value=\"    <<    \" "
+disableButton+" onclick=\"xremoveAll(data_view_fields"+", "+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST+");\"></TD>");

out.println("</TR>");

out.println("<TR >");
out.println("  <TD noWrap colSpan=3></TD></TR>");
out.println("<TR class=TableHeader>");
out.println("<td nowrap colspan=3><font size=4><B>Report&nbsp;Sorted By&nbsp;Fields</B></font></td></TR>");
out.println("<TR ><td nowrap rowspan=7 align=right>");
out.println("<select  size=\"10\" multiple name=\"data_view_fields_order_by_section\">");
out.println("<option value=\"\" >-- AVAILABLE FIELDS --</option>");
for (int i = 0 ; i < dataViewFieldsVector.size();i++)
{
FieldModel fieldModel =(FieldModel) dataViewFieldsVector.elementAt(i);
int fieldId  = fieldModel.getFieldID();
String fieldName = fieldModel.getFieldName();
Utility.logger.debug("checkng for field " + fieldId);

if (reportOrderByFieldsTable.get(fieldId+"")==null)
{
out.println("<option value=\""+fieldId+"\">"+fieldName+"</option>");
}
}
out.println("</select></td>");
out.println("</TR><TR>");

out.println("<td width=\"5%\"><input value=\"     >(Asc)     \" class=\"button\" type=\"button\" name=\"addToList\" "
            +disableButton+" onclick=\"xaddToOrderList(data_view_fields_order_by_section"+", "+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ID_LIST+","+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ORDER_TYPE_LIST+",'"+ReportInterfaceKey.ORDER_TYPE_ASCENDING+"' );\""
            +"  style=\"WIDTH: 60px; HEIGHT: 20px\"></td>");


out.println("<td nowrap rowspan=7>");

out.println("<select  size=\"10\" multiple name=\""+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ID_LIST+"\">");
out.println("<option value=\"\" >-- SELECTED FIELDS --</option>");
for (int i = 0 ; i < reportOrderFieldVector.size();i++)
{
ReportOrderByDTO reportOrderByDto = (ReportOrderByDTO)  reportOrderFieldVector.elementAt(i);
int fieldId = reportOrderByDto.getOrderByFieldID();
String fieldName = reportOrderByDto.getOrderByFieldName();
String fieldOrderByType = reportOrderByDto.getOrderByType();
out.println("<option value=\""+fieldId+"\">"+fieldName + " ("+fieldOrderByType+")"+"</option>");
}
out.println("</select>");
out.println("</td>");
out.println("</TR>");

out.println("<TR >");
out.println("<td width=\"5%\"><input value=\"     >(Desc)     \" class=\"button\" type=\"button\" name=\"addToList\" "
            +disableButton+" onclick=\"xaddToOrderList(data_view_fields_order_by_section"+", "+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ID_LIST+","+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ORDER_TYPE_LIST+",'"+ReportInterfaceKey.ORDER_TYPE_DESCENDING+"' );\""
            +"  style=\"WIDTH: 60px; HEIGHT: 20px\"></td>");
out.println("</TR>");            

out.println("<TR >");
out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"addAll\" value=\"  >>(Asc)\" onclick=\"xaddAllOrderList(data_view_fields_order_by_section"+", "+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ID_LIST+","+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ORDER_TYPE_LIST+",'"+ReportInterfaceKey.ORDER_TYPE_ASCENDING+"' );\""
               +disableButton +"  style=\"WIDTH: 60px; HEIGHT: 20px\"></td>");
out.println("</TR>");

out.println("<TR >");
out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"addAll\" value=\"  >>(Desc)\" onclick=\"xaddAllOrderList(data_view_fields_order_by_section"+", "+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ID_LIST+","+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ORDER_TYPE_LIST+",'"+ReportInterfaceKey.ORDER_TYPE_DESCENDING+"' );\""
               +disableButton+"  style=\"WIDTH: 60px; HEIGHT: 20px\"></td>");
out.println("</TR>");


out.println("<TR >");
out.println("        <td width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeFromList\" value=\"     <      \"       onclick=\"xremoveFromListOrderList(data_view_fields_order_by_section"+", "+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ID_LIST+","+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ORDER_TYPE_LIST+");\""+disableButton+ " style=\"WIDTH: 61px; HEIGHT: 20px\" size=18></td>");
out.println("</TR>");
out.println("<TR >");
out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeAll\" value=\"    <<    \" " 
 +disableButton+" onclick=\"xremoveAllOrderList(data_view_fields_order_by_section"+", "+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ID_LIST+","+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ORDER_TYPE_LIST +");\"></TD>");

out.println("</TR>");
out.println("<TR >");

//out.println("<tr><td>");

out.println("<select size =\"10\" multiple name =\""+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ORDER_TYPE_LIST+"\" style=\"display:none\">");
out.println("<option value=\"\">-- SELECTED FIELDS --</option>");
for (int i = 0 ; i < reportOrderFieldVector.size();i++)
{
ReportOrderByDTO reportOrderByDto = (ReportOrderByDTO)  reportOrderFieldVector.elementAt(i);

String fieldOrderType = reportOrderByDto.getOrderByType();
out.println("<option value=\""+fieldOrderType+"\">"+fieldOrderType+"</option>");
}
out.println("</select>");
//out.println("</td>);

out.println("</tr>");


out.println("<td colspan=5 width=\"5%\">");
out.println("<P align=center><input class=\"button\" type=\"button\" name=\"save\" value=\"    Save    \"");

out.println("onclick=\"saveNewReport("+ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST  +","+ ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ID_LIST  +","+ ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ORDER_TYPE_LIST  +");\"></P></td>" );
out.println("</TR>");
%>
        
        
        



        
      
         
        
</TABLE>

</form>
</CENTER>
</BODY>
</HTML>
