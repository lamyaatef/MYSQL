<%@ page import="com.mobinil.sds.core.utilities.Utility"
	import="javax.servlet.*" import="javax.servlet.http.*"
	import="java.io.PrintWriter" import="java.io.IOException"
	import="java.util.*" import="javax.servlet.jsp.*"
	import="com.mobinil.sds.web.interfaces.*"
	import="com.mobinil.sds.web.interfaces.sip.*"
	import="com.mobinil.sds.core.system.dcm.genericModel.*"
	import="com.mobinil.sds.core.system.sip.model.*"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

<link href="../resources/css/Template2.css" rel="stylesheet"
	type="text/css" />
<SCRIPT language=JavaScript src="../resources/js/calendar.js"
	type=text/javascript></SCRIPT>
<script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
<script type="text/javascript"
	src="../resources/js/jquery_tablesorter_pack.js"></script>
<SCRIPT language=JavaScript src="../resources/js/tree.js"
	type=text/javascript></SCRIPT>
<link rel="stylesheet" href="../resources/css/themes/blue/style.css"
	type="text/css" />
	<LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
	<style type="text/css">
<!--
.style2 {
	font-size: 14px;
	font-style: italic;
	color: #999999;
}
.style6 {
	font-size: 14px;
	color: #000000;
}
-->
</style>
	
<script language="javascript">

function onlyNumbers(){
	
	if (event.keyCode < 48 || event.keyCode > 58) {		
		event.returnValue = false;
		
	}
	}
function onlyNumbersIDS(){
	
	if (event.keyCode < 48 || event.keyCode > 58) {
		if(event.keyCode !=44){
		event.returnValue = false;
		}
	}
	}

function drawCalender(argOrder,argValue)
    {
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(SIPform."+argOrder+",'dd/mm/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
    }

     function removeAllOptions(selectname)
		{
		var elSel = document.getElementById(selectname);
		if (elSel.length == 0) 
			{
		  	return;
		  	}
		elSel.remove(0);
		removeAllOptions(selectname)  
		}
		      
		function addOption(optvalue,opttext,selectname)
		{

      
			var elSel = document.getElementById(selectname);
			var elOptNew = new Option();
		
			elOptNew.text =  opttext;
			elOptNew.value =  optvalue;
			
			elSel.options[elSel.length] = elOptNew;		
		}
    function clearValues()
    {
    document.SIPform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_NAME%>.value="";       
    document.SIPform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_CHANNEL%>.value="";    
    document.SIPform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_CATEGORY%>.value="";

    document.SIPform.<%=SIPInterfaceKey.CONTROL_SELECT_SIP_LABEL%>.value="";
    document.SIPform.<%=SIPInterfaceKey.CONTROL_SELECT_SIP_QUARTER%>.value="";
    
    document.SIPform.<%=SIPInterfaceKey.INPUT_TEXT_SIP_YEAR%>.value="";
    document.SIPform.<%=SIPInterfaceKey.INPUT_TEXT_LINE_COMMISSION_IDS%>.value="ex: 2364,16365,18954";
    document.SIPform.<%=SIPInterfaceKey.INPUT_TEXT_NI_COMMISSION_IDS%>.value="ex: 6544,5664,9987";
    document.SIPform.<%=SIPInterfaceKey.INPUT_TEXT_SOP_IDS%>.value="ex: 3564,5895,4589";
    
    
            
    }
    
</script>
<script>
function checkForSubmit()
{	
	if (document.SIPform.<%=SIPInterfaceKey.INPUT_TEXT_LINE_COMMISSION_IDS%>.value=='ex: 2364,16365,18954')
	{
		document.SIPform.<%=SIPInterfaceKey.INPUT_TEXT_LINE_COMMISSION_IDS%>.value='';
	} 
	if (document.SIPform.<%=SIPInterfaceKey.INPUT_TEXT_NI_COMMISSION_IDS%>.value=='ex: 6544,5664,9987')
	{
		document.SIPform.<%=SIPInterfaceKey.INPUT_TEXT_NI_COMMISSION_IDS%>.value='';
	} 
	if (document.SIPform.<%=SIPInterfaceKey.INPUT_TEXT_SOP_IDS%>.value=='ex: 3564,5895,4589')
	{
		document.SIPform.<%=SIPInterfaceKey.INPUT_TEXT_SOP_IDS%>.value='';
	} 
	SIPform.submit()
}
</script>
</head>

<body>

<%
	HashMap objDataHashMap = (HashMap) request
			.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
HashMap labelHashMap = new HashMap();
	String appName = request.getContextPath();
	String strUserID = (String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
	   
	   /*(String) objDataHashMap
			.get(InterfaceKey.HASHMAP_KEY_USER_ID);*/
	Vector sipQuarters = (Vector) objDataHashMap
	.get(SIPInterfaceKey.VECTOR_SIP_QUARTER);
	
	Vector sipLabels = (Vector) objDataHashMap
	.get(SIPInterfaceKey.VECTOR_SIP_LABEL);
	
	Vector sipTypeCategories = (Vector) objDataHashMap
			.get(SIPInterfaceKey.VECTOR_SIP_TYPE_CATEGORY);
	
	Vector sipChannelVec = (Vector) objDataHashMap
			.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
	Vector yearVector = (Vector)objDataHashMap.get(SIPInterfaceKey.VECTOR_SIP_REPORT_YEAR);
	Vector SearchResults = null;
	if (objDataHashMap.containsKey(SIPInterfaceKey.VECTOR_SIP_SEARCH_RESULT))
	{
	SearchResults = (Vector) objDataHashMap
			.get(SIPInterfaceKey.VECTOR_SIP_SEARCH_RESULT);
	}
	String incomingAction = (String) objDataHashMap
			.get(InterfaceKey.HASHMAP_KEY_ACTION);

	String sipAction = (String) objDataHashMap
			.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_ACTION);
	String hiddenSIPStatus = (String) objDataHashMap
			.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_STATUS);


	String sipReportId = "";
	String sipReportName = "";
	String sipReportSearchStatus = "0";
	String sipReportType = "";
	String sipReportChannel = "";
	String sipReportTypeCategory = "";
	
	String sipReportQuarter  = "";						            
    String sipReportLabel  = "";
    String sipReportYear   = "";
    String sipReportLCID   = "ex: 2364,16365,18954";                                   
    String sipReportNICom  = "ex: 6544,5664,9987";                                    
    String sipReportSOPID = "ex: 3564,5895,4589"; 
	
	
	 
    
     
	
	
	/*String sipReportStartDateFrom = "*";
	String sipReportStartDateTo = "*";
	String sipReportEndDateFrom = "*";
	String sipReportEndDateTo = "*";*/
	String sipReportSavedId=null;

	sipReportSavedId = (String) objDataHashMap
	.get(SIPInterfaceKey.SAVED_SIP_REPORT_ID);

	if (objDataHashMap.containsKey(SIPInterfaceKey.SIP_REPORT_ERROR_MESSAGE))
	{
	   %>
	   <script>alert('<%=objDataHashMap.get(SIPInterfaceKey.SIP_REPORT_ERROR_MESSAGE)%>');</script>
	   <%
	   
	}
	
	if (objDataHashMap
			.containsKey(SIPInterfaceKey.CONTROL_TEXT_SIP_NAME)) {
	   sipReportName = (String) objDataHashMap
				.get(SIPInterfaceKey.CONTROL_TEXT_SIP_NAME);
	   sipReportId = (String) objDataHashMap
		.get(SIPInterfaceKey.CONTROL_TEXT_SIP_ID);
	   sipReportSearchStatus = (String) objDataHashMap
				.get(SIPInterfaceKey.CONTROL_TEXT_SIP_STATUS);
	   sipReportChannel = (String) objDataHashMap
				.get(SIPInterfaceKey.CONTROL_TEXT_SIP_CHANNEL);
	   /*sipReportType = (String) objDataHashMap
				.get(SIPInterfaceKey.CONTROL_TEXT_SIP_TYPE);*/
	   sipReportTypeCategory = (String) objDataHashMap
				.get(SIPInterfaceKey.CONTROL_TEXT_SIP_CATEGORY);
	   sipReportQuarter  = (String)objDataHashMap.get(SIPInterfaceKey.CONTROL_SELECT_SIP_QUARTER);						            
	     sipReportLabel  = (String)objDataHashMap.get(SIPInterfaceKey.CONTROL_SELECT_SIP_LABEL);
	     sipReportYear          = (String)objDataHashMap.get(SIPInterfaceKey.INPUT_TEXT_SIP_YEAR);
	     sipReportLCID          = (String)objDataHashMap.get(SIPInterfaceKey.INPUT_TEXT_LINE_COMMISSION_IDS);                                   
	     sipReportNICom          = (String)objDataHashMap.get(SIPInterfaceKey.INPUT_TEXT_NI_COMMISSION_IDS);                                    
	     sipReportSOPID          = (String)objDataHashMap.get(SIPInterfaceKey.INPUT_TEXT_SOP_IDS);
	   /*sipReportStartDateFrom = (String) objDataHashMap
				.get(SIPInterfaceKey.CONTROL_TEXT_SIP_START_DATE_FROM);
	   sipReportStartDateTo = (String) objDataHashMap
				.get(SIPInterfaceKey.CONTROL_TEXT_SIP_START_DATE_TO);
	   sipReportEndDateFrom = (String) objDataHashMap
				.get(SIPInterfaceKey.CONTROL_TEXT_SIP_END_DATE_FROM);
	   sipReportEndDateTo = (String) objDataHashMap
				.get(SIPInterfaceKey.CONTROL_TEXT_SIP_END_DATE_TO);*/
	
			
	}
%>

<center>
<H2>SIP Report Search</H2>
</center>






<%

//System.out.println("&&&&&&&&&&&&&&&*************");
//System.out.println("savedCommissionId = "+ savedCommissionId);
	if (sipReportSavedId!=null)
	{
	out.println("<script>alert('SIP Report Saved Id is "+sipReportSavedId+"');</script>");
	}
	%>
	
<form name="SIPform" action="" method="post">
<%
	out.println("<input type=\"hidden\" name=\""
			+ InterfaceKey.HASHMAP_KEY_ACTION + "\"" + " value=\""
			+ "\">");

	out.println("<input type=\"hidden\" name=\""
			+ InterfaceKey.HASHMAP_KEY_USER_ID + "\"" + " value=\""
			+ strUserID + "\">");

	out.println("<input type=\"hidden\" name=\""
			+ SIPInterfaceKey.INPUT_HIDDEN_SIP_STATUS
			+ "\"" + " value=\"" + hiddenSIPStatus + "\">");
	out.println("<input type=\"hidden\" name=\""
			+ SIPInterfaceKey.INPUT_HIDDEN_SIP_ACTION
			+ "\"" + " value=\"" + sipAction + "\">");
	
	out.println("<input type=\"hidden\" name=\""
			+ SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_CHANNEL_ID
			+ "\"" + " value=\"" + "" + "\">");
	
	out.println("<input type=\"hidden\" name=\""
			+ SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_QUARTAR_ID
			+ "\"" + " value=\"" + "" + "\">");
	
	out.println("<input type=\"hidden\" name=\""
			+ SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_YEAR_ID
			+ "\"" + " value=\"" + "" + "\">");
	
	out.println("<input type=\"hidden\" name=\""
			+ SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_LABEL_ID
			+ "\"" + " value=\"" + "" + "\">");

	out.println("<input type=\"hidden\" name=\""
			+ SIPInterfaceKey.INPUT_HIDDEN_SIP_ID
			+ "\" value=\"\">");
	out.println("<input type=\"hidden\" name=\""
			+ SIPInterfaceKey.INPUT_HIDDEN_SIP_CHANGE_STATUS
			+ "\" value=\"\">");
	out.println("<input type=\"hidden\" name=\""
			+ SIPInterfaceKey.CONTROL_HIDDEN_REPORT_TYPE_ID
			+ "\" value=\"\">");
%> 
<table style="BORDER-COLLAPSE: collapse" width="100%" border="1"
	align="center" cellpadding="0" cellspacing="1">
	<tr>
		<td colspan=6 class="TableHeader">Search</td>
	</tr>
	<tr>
	
		
		
			</tr>
	<tr>
	<td class="TableTextNote" align="center">Report Querter</td>
		<td class="TableTextNote" align="left"><select
			name="<%=SIPInterfaceKey.CONTROL_SELECT_SIP_QUARTER%>" style=" width : 100px;">			
			<option value=''></option>
			<%
				for (int i = 0; i < sipQuarters.size(); i++) {
					GenericModel sipQuarterModel = (GenericModel) sipQuarters
							.get(i);
					String selected = "";
						if (sipQuarterModel.get_primary_key_value().equals(
						      sipQuarterModel))
							selected = "selected";
						out.println("<option value='"
								+ sipQuarterModel.get_primary_key_value()
								+ "' " + selected + "> "
								+ sipQuarterModel.get_field_2_value());
						out.println(" </option> ");
					
				}
			%>

		</select></td>
		<td class="TableTextNote" align="center">Report Name&nbsp;</td>
		<td class="TableTextNote" align="left" ><input type="text" style=" width : 200px;"
			name="<%=SIPInterfaceKey.CONTROL_TEXT_SIP_NAME%>"
			value="<%=sipReportName%>" /></td>
		
			</tr>
			
			<tr>
	<td class="TableTextNote" align="center">Report Year</td>
	
		<td class="TableTextNote" align="left">
		<select
			name="<%=SIPInterfaceKey.INPUT_TEXT_SIP_YEAR %>"	value="<%=sipReportYear %>" style=" width : 100px;">	
			<option value=''></option>		
			<%
				for (int i = 0; i < yearVector.size(); i++) {
					GenericModel sipYearModel = (GenericModel) yearVector
							.get(i);
					
					
					String selected = "";
						if (sipYearModel.get_primary_key_value().equals(
						      sipYearModel))
							selected = "selected";
						out.println("<option value='"
								+ sipYearModel.get_primary_key_value()
								+ "' " + selected + "> "
								+ sipYearModel.get_field_2_value());
						out.println(" </option> ");
					
				}
			%>

		</select>
		</td>
		<td class="TableTextNote" align="center">Line Report IDs</td>
		<td class="TableTextNote" align="left"><input name="<%=SIPInterfaceKey.INPUT_TEXT_LINE_COMMISSION_IDS%>" type="text" onkeypress="onlyNumbersIDS();" onblur="if(this.value ==''){this.value='ex: 2364,16365,18954';this.className = 'style2';this.s='';}" onfocus="if(this.s!='t'){this.value='';this.className = 'style6';this.s='t';}" name="q" autocomplete="Off" value="<%=sipReportLCID %>" class="style2" id="c_hsv" style=" width : 200px;"/></td>
			</tr>
			<tr>
	<td class="TableTextNote" align="center">Report Channel</td>
		<td class="TableTextNote" align="left"><select
			name='<%=SIPInterfaceKey.CONTROL_TEXT_SIP_CHANNEL%>'
			id='<%=SIPInterfaceKey.CONTROL_TEXT_SIP_CHANNEL%>' style=" width : 100px;">
			<option value=''></option>
			<%
				for (int j = 0; j < sipChannelVec.size(); j++) {
					SIPChannelModel sipChannelModel = (SIPChannelModel) sipChannelVec
							.get(j);
					String sipChannelIdX = sipChannelModel
							.getChannelId()+"";
					String sipChannelNameX = sipChannelModel
							.getChannelName();
			%>
			<option value='<%=sipChannelIdX%>'><%=sipChannelNameX%></option>
			<%
				}
			%>
		</select></td>
		<td class="TableTextNote" align="center">SOP Report IDs</td>
		<td class="TableTextNote" align="left"><input name="<%=SIPInterfaceKey.INPUT_TEXT_SOP_IDS%>" type="text" onkeypress="onlyNumbersIDS();" onblur="if(this.value ==''){this.value='ex: 3564,5895,4589';this.className = 'style2';this.s='';}" onfocus="if(this.s!='t'){this.value='';this.className = 'style6';this.s='t';}" name="q" autocomplete="Off" value="<%=sipReportSOPID %>" class="style2" id="c_hsv" style=" width : 200px;"/> </td>
			</tr>
			<tr>
		<td class="TableTextNote" align="center">Label</td>
		<td class="TableTextNote" align="left">
		<select
			name="<%=SIPInterfaceKey.CONTROL_SELECT_SIP_LABEL%>" style=" width : 100px;">	
			<option value=''></option>		
			<%
				for (int i = 0; i < sipLabels.size(); i++) {
					GenericModel sipLabelModel = (GenericModel) sipLabels
							.get(i);
					
					labelHashMap.put(sipLabelModel.get_primary_key_value(),sipLabelModel.get_field_2_value());
					String selected = "";
						if (sipLabelModel.get_primary_key_value().equals(
						      sipLabelModel))
							selected = "selected";
						out.println("<option value='"
								+ sipLabelModel.get_primary_key_value()
								+ "' " + selected + "> "
								+ sipLabelModel.get_field_2_value());
						out.println(" </option> ");
					
				}
			%>

		</select>
		</td>
		
		<td class="TableTextNote" align="center">NI Report IDs</td>
		<td class="TableTextNote" align="left"><input name="<%=SIPInterfaceKey.INPUT_TEXT_NI_COMMISSION_IDS%>" type="text"  onkeypress="onlyNumbersIDS();" onblur="if(this.value ==''){this.value='ex: 6544,5664,9987';this.className = 'style2';this.s='';}" onfocus="if(this.s!='t'){this.value='';this.className = 'style6';this.s='t';}" name="q" autocomplete="Off" value="<%= sipReportNICom%>" class="style2" id="c_hsv" style=" width : 200px;"/></td>
		
		
	</tr>
	<tr>
		
		<td class="TableTextNote" align="center">Report Category</td>
		<td class="TableTextNote" align="left" >
		<select
			name="<%=SIPInterfaceKey.CONTROL_TEXT_SIP_CATEGORY%>" style=" width : 100px;">
			<option value=''></option>			
			<%
			System.out.println("sipTypeCategories isssss "+sipTypeCategories);
				for (int i = 0; i < sipTypeCategories.size(); i++) {
					GenericModel sipCatModel = (GenericModel) sipTypeCategories
							.get(i);
					String selected = "";
						if (sipCatModel.get_primary_key_value().equals(
						      sipCatModel))
							selected = "selected";
						out.println("<option value='"
								+ sipCatModel.get_primary_key_value()
								+ "' " + selected + "> "
								+ sipCatModel.get_field_2_value());
						out.println(" </option> ");
					
				}
			%>

		</select>
		</td>
		<td class="TableTextNote" align="center" >&nbsp;</td>
		<td class="TableTextNote" align="left">&nbsp;
		</td>
		
	</tr>
	<tr>
	<td class="TableTextNote" align="center">&nbsp;</td>
	<td class="TableTextNote" align="left" >&nbsp;
	</td>
		
		<td class="TableTextNote" align="center" colspan=2></td>
		
			</tr>
	

	<tr>
		<td colspan="6" align="center">
		<%
			out
					.println("<input name=\"Button\" type=\"button\" class=\"button\" value=\"Search\" onclick=\"document.SIPform."
							+ InterfaceKey.HASHMAP_KEY_ACTION
							+ ".value='"
							+ SIPInterfaceKey.ACTION_SIP_SEARCH_SIP
							+ "'; checkForSubmit();\"/>");
		%> <input name="Submit2" type="button" class="button"
			value="Clear Values" onclick="clearValues();" />
		</div>
		</td>
	</tr>
</table>
<p>&nbsp;</p>
<%
	if (SearchResults != null && SearchResults.size() != 0) {

		
%> 

<table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
<%
   for (int i = 0; i < SearchResults.size(); i++) {
 			SIPModel SIPModel = (SIPModel) SearchResults
 					.get(i);
 			if (!SIPModel.getsipStatusTypeName().equals(
 					"Deleted")) { 				
 				int preChannelId = 0;
 				if (i != 0)
 					preChannelId = ((SIPModel) SearchResults
 							.get(i - 1)).getsipChannelId();
 				if (SIPModel.getsipChannelId() != preChannelId) {
 					int channelId = SIPModel.getsipChannelId();
 					String channelName = SIPModel.getsipChannelName();
 					if (i != 0)
 						out.println("</table></div></td></tr>");
%>
<TR class=TableTextNote>
	<td class="<% out.print(InterfaceKey.STYLE[i%2]); %>">
	<a style="text-decoration:none;" id=x<% out.print(channelId); %> href="javascript:Toggle('<% out.print(channelId); %>');"> <IMG
		height=16 hspace=0 src="<% out.print(appName); %>/resources/img/plus.gif" width=16
		border=0> </a>
		<%out.print(" "+channelName);%>
	</td>
	
</TR>
<TR>
	<td class="<% out.print(InterfaceKey.STYLE[i%2]); %>">

<div style="DISPLAY: none" id="<% out.print(channelId); %>" name=<%out.print(" "+channelName);%>>
<table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1 class="<% out.print(InterfaceKey.STYLE[i%2]); %>" id="CommissionTable">

	<tr class=TableHeader>
		<td nowrap align=middle>Name</td>
		<td nowrap align=middle>Category</td>
		<td nowrap align=middle>Creation Date</td>
		<!-- td nowrap align=middle>Start Date</td>
		<td nowrap align=middle>End Date</td -->		
		<td nowrap align=middle>Status</td>
		<td nowrap align=middle>Channel</td>
		<td nowrap align=middle>Year</td>
		<td nowrap align=middle>Quarter</td>
		<td nowrap align=middle>Label</td>
		<td nowrap align=middle>LC IDs</td>
		<td nowrap align=middle>NI IDs</td>
		<td nowrap align=middle>SOP IDs</td>
		<%
			if (!sipAction
									.equals(SIPInterfaceKey.INPUT_HIDDEN_ALL_SIP)) {
		%>
		<td nowrap align=middle>Delete</td>
		<td nowrap align=middle><%=sipAction%></td>
		<%
			}
							
								if (hiddenSIPStatus.equals("2")||hiddenSIPStatus.equals("3")) {
		%>

		<!-- td nowrap align=middle>Factors</td -->
		<td nowrap align=middle>Edit</td>
		<td nowrap align=middle>Excel</td>
		<td nowrap align=middle>CSV</td>
		<%
			}
		%>
		
		<!-- td nowrap align=middle>View</td -->
		
	</tr>

	<%
		}
					//out.println("<tbody>");

					////////////////////

					out.println("<tr>");
					

					out
							.println("<td class="+InterfaceKey.STYLE[i%2]+">"+
									SIPModel.getsipName()
									+ "</td>");

					out.println("<td  class="+InterfaceKey.STYLE[i%2]+">"
							+ SIPModel
									.getsipTypeCategoryName()
							+ "</td>");
					out.println("<td  nowrap class="+InterfaceKey.STYLE[i%2]+">"
							+ SIPModel.getsipCreationDate()
							+ "</td>");
					//out.println("<td  nowrap class="+InterfaceKey.STYLE[i%2]+">"
						//	+ SIPModel.getsipStartDate()
							//+ "</td>");
					//out.println("<td  nowrap class="+InterfaceKey.STYLE[i%2]+">"
						//	+ SIPModel.getsipEndDate()
							//+ "</td>");
					
					out.println("<td  class="+InterfaceKey.STYLE[i%2]+">"
							+ SIPModel.getsipStatusTypeName()
							+ "");
					out.println("</td>");
					out.println("<td  class="+InterfaceKey.STYLE[i%2]+">"
							+ SIPModel.getsipChannelName() 
							+ "");
					out.println("</td>");
					
					
					
					
					
					
					out.println("<td  class="+InterfaceKey.STYLE[i%2]+">"
							+ SIPModel.getSipReportYear()
							+ "");
					out.println("</td>");
					
					out.println("<td  class="+InterfaceKey.STYLE[i%2]+">"
							+ "Q"+SIPModel.getSipReportQuarter());
					out.println("</td>");
					
					out.println("<td  class="+InterfaceKey.STYLE[i%2]+">"
							+  labelHashMap.get(SIPModel.getSipReportLabel())
							+ "");
					out.println("</td>");
					
					out.println("<td  class="+InterfaceKey.STYLE[i%2]+">"
							+ SIPModel.getSipReportLCID()
							+ "");
					out.println("</td>");
					
					out.println("<td  class="+InterfaceKey.STYLE[i%2]+">"
							+ SIPModel.getSipReportNICom()
							+ "");
					out.println("</td>");
					
					out.println("<td  class="+InterfaceKey.STYLE[i%2]+">"
							+ SIPModel.getSipReportSOPID()
							+ "");
					out.println("</td>");
					
					
					
					
					if(i==0){
					System.out.println("hiddenSIPStatus issssssssssssssssssss "+hiddenSIPStatus);
					System.out.println("sipAction issssssssssssssssssss "+sipAction);
					}
					if (!sipAction
							.equals(SIPInterfaceKey.INPUT_HIDDEN_ALL_SIP)) {
						if (hiddenSIPStatus.equals(String.valueOf(SIPInterfaceKey.SIP_REPORT_STATUS_READY))&&hiddenSIPStatus.equals(String.valueOf(SIPModel
										.getsipStatusTypeID()))) {
							Utility.logger.debug(hiddenSIPStatus);
							out
									.println("<td  align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\"Delete\" onclick=\""
											+ "document.SIPform."
											+ SIPInterfaceKey.INPUT_HIDDEN_SIP_ID
											+ ".value='"
											+ SIPModel
													.getsipID()
											+ "';"
											+ "document.SIPform."
											+ InterfaceKey.HASHMAP_KEY_ACTION
											+ ".value='"
											+ SIPInterfaceKey.ACTION_SIP_CHANGE_STATUS
											+ "';"
											+ "document.SIPform."
											+ SIPInterfaceKey.INPUT_HIDDEN_SIP_CHANGE_STATUS
											+ ".value='"
											+ SIPInterfaceKey.SIP_REPORT_STATUS_DELETED
											+ "';"
											+ "checkForSubmit();\"/></td>");
							out
									.println("<td  align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\""
											+ sipAction
											+ "\" onclick=\""
											+ "document.SIPform."
											+ SIPInterfaceKey.INPUT_HIDDEN_SIP_ID
											+ ".value='"
											+ SIPModel
													.getsipID()
											+ "';"
											+ "document.SIPform."
											+ InterfaceKey.HASHMAP_KEY_ACTION
											+ ".value='"
											+ SIPInterfaceKey.ACTION_SIP_CHANGE_STATUS
											+ "';"
											+ "document.SIPform."
											+ SIPInterfaceKey.INPUT_HIDDEN_SIP_CHANGE_STATUS
											+ ".value='"
											+ SIPInterfaceKey.SIP_REPORT_STATUS_FINAL
											+ "';"
											+ "document.SIPform."
											+ SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_QUARTAR_ID
											+ ".value='"
											+ SIPModel.getSipReportQuarter()
											+ "';"
											+ "document.SIPform."
											+ SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_YEAR_ID
											+ ".value='"
											+ SIPModel.getSipReportYear()
											+ "';"
											+ "document.SIPform."
											+ SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_CHANNEL_ID
											+ ".value='"
											+ SIPModel.getsipChannelId()
											+ "';"
											+ "checkForSubmit();\"/></td>");

							System.out.println("hiddenCommissionStatus= "
									+ hiddenSIPStatus);
							
							if (hiddenSIPStatus.equals("3")) {
							   
							   out
								.println("<td  align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\"Excel\" onclick=\""
										+ "document.SIPform."
										+ SIPInterfaceKey.INPUT_HIDDEN_SIP_ID
										+ ".value='"
										+ SIPModel
												.getsipID()
										+ "';"
										+ "document.SIPform."
										+ InterfaceKey.HASHMAP_KEY_ACTION
										+ ".value='"
										+ SIPInterfaceKey.ACTION_EXPORT_DATA
										+ "';"
										+ "document.SIPform."
										+ SIPInterfaceKey.CONTROL_HIDDEN_REPORT_TYPE_ID
										+ ".value='"
										+ SIPModel.getsipTypeCtageoryID()
										+ "';"
										+ "SIPform.submit();\"/></td>");
							}
							if (!hiddenSIPStatus.equals("1")) {
								/*if (hiddenSIPStatus.equals("2")) {
									out
											.println("<td  align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\"Factors\" onclick=\""
													+ "document.SIPform."
													+ SIPInterfaceKey.INPUT_HIDDEN_SIP_ID
													+ ".value='"
													+ SIPModel
															.getsipID()
													+ "';"
													+ "document.SIPform."
													+ InterfaceKey.HASHMAP_KEY_ACTION
													+ ".value='"
													+ SIPInterfaceKey.ACTION_SIP_FACTORS
													+ "';"
													+ "SIPform.submit();\"/></td>");
								}*/
								if (hiddenSIPStatus.equals("2")) {
									out
											.println("<td  align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\"Edit\" onclick=\""
													+ "document.SIPform."
													+ SIPInterfaceKey.INPUT_HIDDEN_SIP_ID
													+ ".value='"
													+ SIPModel
															.getsipID()
													+ "';"
													+ "document.SIPform."
													+ InterfaceKey.HASHMAP_KEY_ACTION
													+ ".value='"
													+ SIPInterfaceKey.ACTION_CREATE_NEW_SIP_REPORT
													+ "';"
													+ "SIPform.submit();\"/></td>");
								}
								out
										.println("<td  align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\"Excel\" onclick=\""
												+ "document.SIPform."
												+ SIPInterfaceKey.INPUT_HIDDEN_SIP_ID
												+ ".value='"
												+ SIPModel
														.getsipID()
												+ "';"
												+ "document.SIPform."
												+ InterfaceKey.HASHMAP_KEY_ACTION
												+ ".value='"
												+ SIPInterfaceKey.ACTION_EXPORT_DATA
												+ "';"
												+ "document.SIPform."
												+ SIPInterfaceKey.CONTROL_HIDDEN_REPORT_TYPE_ID
												+ ".value='"
												+ SIPModel.getsipTypeCtageoryID()
												+ "';"
												+ "document.SIPform."
												+ SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_YEAR_ID
												+ ".value='"
												+ SIPModel.getSipYearID()
												+ "';"
												+ "document.SIPform."
												+ SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_QUARTAR_ID
												+ ".value='"
												+ SIPModel.getSipReportQuarter()
												+ "';"												
												+ "document.SIPform."
												+ SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_LABEL_ID
												+ ".value='"
												+ SIPModel.getSipReportLabel()
												+ "';"	
												+ "SIPform.submit();\"/></td>");
								
								
								out
								.println("<td  align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\"CSV\" onclick=\""
										+ "document.SIPform."
										+ SIPInterfaceKey.INPUT_HIDDEN_SIP_ID
										+ ".value='"
										+ SIPModel
												.getsipID()
										+ "';"
										+ "document.SIPform."
										+ InterfaceKey.HASHMAP_KEY_ACTION
										+ ".value='"
										+ SIPInterfaceKey.ACTION_EXPORT_DATA
										+ "';"
										+ "document.SIPform."
										+ SIPInterfaceKey.CONTROL_HIDDEN_REPORT_TYPE_ID
										+ ".value='100';"
										+ "SIPform.submit();\"/></td>");
								/*out
										.println("<td  align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\"View\" onclick=\""
												+ "document.SIPform."
												+ SIPInterfaceKey.INPUT_HIDDEN_SIP_ID
												+ ".value='"
												+ SIPModel
														.getsipID()
												+ "';"
												+ "document.SIPform."
												+ InterfaceKey.HASHMAP_KEY_ACTION
												+ ".value='"
												+ SIPInterfaceKey.ACTION_VIEW_SIP
												+ "';"
												+ "SIPform.submit();\"/></td>");*/
							}

						} else {
							out.println("<td  align=\"center\"></td>");
							out.println("<td  align=\"center\"></td>");

							if (hiddenSIPStatus.equals("2")||hiddenSIPStatus.equals("3"))
								out.println("<td  align=\"center\"></td>");
							
							
							if (hiddenSIPStatus.equals("3")&&hiddenSIPStatus.equals(String.valueOf(SIPModel
									.getsipStatusTypeID()))) {
							  
							   out
								.println("<td  align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\"Excel\" onclick=\""
										+ "document.SIPform."
										+ SIPInterfaceKey.INPUT_HIDDEN_SIP_ID
										+ ".value='"
										+ SIPModel
												.getsipID()
										+ "';"
										+ "document.SIPform."
										+ InterfaceKey.HASHMAP_KEY_ACTION
										+ ".value='"
										+ SIPInterfaceKey.ACTION_EXPORT_DATA
										+ "';"
										+ "document.SIPform."
										+ SIPInterfaceKey.CONTROL_HIDDEN_REPORT_TYPE_ID
										+ ".value='"
										+ SIPModel.getsipTypeCtageoryID()
										+ "';"
										+ "SIPform.submit();\"/></td>");
							   
							   out
								.println("<td  align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\"CSV\" onclick=\""
										+ "document.SIPform."
										+ SIPInterfaceKey.INPUT_HIDDEN_SIP_ID
										+ ".value='"
										+ SIPModel
												.getsipID()
										+ "';"
										+ "document.SIPform."
										+ InterfaceKey.HASHMAP_KEY_ACTION
										+ ".value='"
										+ SIPInterfaceKey.ACTION_EXPORT_DATA
										+ "';"
										+ "document.SIPform."
										+ SIPInterfaceKey.CONTROL_HIDDEN_REPORT_TYPE_ID
										+ ".value='100';"
										+ "SIPform.submit();\"/></td>");
							}
							else
							   out.println("<td  align=\"center\"></td>");
						}
						
					}
					out.println("</tr>");
					//////////////////////////////////////////   
				}

	 			if (i==(SearchResults.size()-1))
				{
	 				out.println("</table></div></td></tr>");
				}
			}
			//out.println("</tbody>");
			
	%>

</table>

	<%
		}
	%>
	
	<p>&nbsp;</p>
	<table style="BORDER-COLLAPSE: collapse" width="100%" border="0"
		align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="1" align='center'>
			<%
				out
						.println("<input type='button' class='button' name='newReport' value='Create New SIP Report' onclick=\"document.SIPform."
								+ InterfaceKey.HASHMAP_KEY_ACTION
								+ ".value='"
								+ SIPInterfaceKey.ACTION_CREATE_NEW_SIP_REPORT 
								+ "';" + "document.SIPform."
								+ SIPInterfaceKey.INPUT_HIDDEN_SIP_ID
								+ ".value='"								  
								+ "'; SIPform.submit();\">");
			%>
			</td>
		</tr>
	</table>
</form>
<p>&nbsp;</p>
</body>
</html>
