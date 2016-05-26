<%@ page import="com.mobinil.sds.core.utilities.Utility"
	import="javax.servlet.*" import="javax.servlet.http.*"
	import="java.io.PrintWriter" import="java.io.IOException"
	import="java.util.*" import="javax.servlet.jsp.*"
	import="com.mobinil.sds.web.interfaces.*"
	import="com.mobinil.sds.web.interfaces.commission.*"
	import="com.mobinil.sds.core.system.dcm.genericModel.*"
	import="com.mobinil.sds.core.system.commission.model.*"%>

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
<script language="javascript">



function drawCalender(argOrder,argValue)
    {
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(COMform."+argOrder+",'dd/mm/yyyy','Choose date')\">");
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
    document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_NAME%>.value="";
    document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_ID%>.value="";
    document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_STATUS%>.value="0";
    document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL%>.value="";
    document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_TYPE%>.value="0";
    document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY%>.value="0";
    document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_START_DATE_FROM%>.value="*";
    document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_START_DATE_TO%>.value="*";    
    document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_END_DATE_FROM%>.value="*";    
    document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_END_DATE_TO%>.value="*";        
    }
    
</script>
<script>
function checkForSubmit()
{	
	COMform.submit()
}
</script>
</head>

<body>

<%
	HashMap objDataHashMap = (HashMap) request
			.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	String appName = request.getContextPath();
	String strUserID = (String) objDataHashMap
			.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	Vector commissionTypes = (Vector) objDataHashMap
			.get(CommissionInterfaceKey.VECTOR_COMMISSION_TYPE);
	Vector commissionTypeCategories = (Vector) objDataHashMap
			.get(CommissionInterfaceKey.VECTOR_COMMISSION_TYPE_CATEGORY);
	Vector commissionStatus = (Vector) objDataHashMap
			.get(CommissionInterfaceKey.VECTOR_COMMISSION_STATUS);
	Vector comChannelVec = (Vector) objDataHashMap
			.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
	Vector SearchResults = null;
	if (objDataHashMap
			.containsKey(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT))
		;
	SearchResults = (Vector) objDataHashMap
			.get(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT);

	String incomingAction = (String) objDataHashMap
			.get(InterfaceKey.HASHMAP_KEY_ACTION);

	String commissionAction = (String) objDataHashMap
			.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ACTION);
	String hiddenCommissionStatus = (String) objDataHashMap
			.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_STATUS);


	String commissionId = "";
	String commissionName = "";
	String commissionSearchStatus = "0";
	String commissionType = "0";
	String commissionChannel = "";
	String commissionTypeCategory = "0";
	String commissionStartDateFrom = "*";
	String commissionStartDateTo = "*";
	String commissionEndDateFrom = "*";
	String commissionEndDateTo = "*";
	String savedCommissionId=null;

	savedCommissionId = (String) objDataHashMap
	.get(CommissionInterfaceKey.SAVED_COMMISSION_ID);

	
	if (objDataHashMap
			.containsKey(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_NAME)) {
		commissionName = (String) objDataHashMap
				.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_NAME);
		commissionId = (String) objDataHashMap
		.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_ID);
		commissionSearchStatus = (String) objDataHashMap
				.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_STATUS);
		commissionChannel = (String) objDataHashMap
				.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL);
		commissionType = (String) objDataHashMap
				.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_TYPE);
		commissionTypeCategory = (String) objDataHashMap
				.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY);
		commissionStartDateFrom = (String) objDataHashMap
				.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_START_DATE_FROM);
		commissionStartDateTo = (String) objDataHashMap
				.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_START_DATE_TO);
		commissionEndDateFrom = (String) objDataHashMap
				.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_END_DATE_FROM);
		commissionEndDateTo = (String) objDataHashMap
				.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_END_DATE_TO);
	
			
	}
%>

<center>
<H2>Commission Search</H2>
</center>

<%

//System.out.println("&&&&&&&&&&&&&&&*************");
//System.out.println("savedCommissionId = "+ savedCommissionId);
	if (savedCommissionId!=null)
	{
	out.println("<script>alert('Commission Saved Id is "+savedCommissionId+"');</script>");
	}
	%>
	
<form name="COMform" action="" method="post">
<%
	out.println("<input type=\"hidden\" name=\""
			+ InterfaceKey.HASHMAP_KEY_ACTION + "\"" + " value=\""
			+ "\">");

	out.println("<input type=\"hidden\" name=\""
			+ InterfaceKey.HASHMAP_KEY_USER_ID + "\"" + " value=\""
			+ strUserID + "\">");

	out.println("<input type=\"hidden\" name=\""
			+ CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_STATUS
			+ "\"" + " value=\"" + hiddenCommissionStatus + "\">");
	out.println("<input type=\"hidden\" name=\""
			+ CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ACTION
			+ "\"" + " value=\"" + commissionAction + "\">");
%> <script>
    function changeTypeCategory(selectName) 
    {
      var typeID = eval("document.COMform."+selectName+".value");
      removeAllOptions('<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY%>');
      addOption('0','','<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY%>'); 
      <%for (int i = 0; i < commissionTypeCategories.size(); i++) {
				GenericModel commissionTypeCategoryModel = (GenericModel) commissionTypeCategories
						.get(i);
				String categoryID = commissionTypeCategoryModel
						.get_primary_key_value().trim();
				String categoryName = commissionTypeCategoryModel
						.get_field_2_value().trim();
				String categoryType = commissionTypeCategoryModel
						.get_field_3_value().trim();%>
        
        if(typeID == '<%=categoryType%>')
        {
          addOption('<%=categoryID%>','<%=categoryName%>','<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY%>');          
        }
        
      <%}%>
    }

</script>

<table style="BORDER-COLLAPSE: collapse" width="100%" border="1"
	align="center" cellpadding="0" cellspacing="1">
	<tr>
		<td colspan=6 class="TableHeader">Search</td>
	</tr>
	<tr>
	<td class="TableTextNote" align="center">Commission ID</td>
		<td class="TableTextNote" align="center"><input type="text"
			name="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_ID%>"
			value="<%=commissionId%>" /></td>
		<td class="TableTextNote" align="center">Commission Name</td>
		<td class="TableTextNote" align="center"><input type="text"
			name="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_NAME%>"
			value="<%=commissionName%>" /></td>
			</tr>
			<tr>
		<td class="TableTextNote" align="center">Commission Status</td>
		<td class="TableTextNote" align="center"><select
			name="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_STATUS%>">
			<option value='0'></option>
			<%
				for (int i = 0; i < commissionStatus.size(); i++) {
					GenericModel commissionStatusModel = (GenericModel) commissionStatus
							.get(i);
					String selected = "";

					if (!commissionStatusModel.get_field_2_value()
							.equals("Deleted")) {
						Utility.logger.debug(commissionStatusModel
								.get_field_2_value());
						if (commissionStatusModel.get_primary_key_value().equals(
								commissionSearchStatus))
							selected = "selected";
						out.println("<option value='"
								+ commissionStatusModel.get_primary_key_value()
								+ "' " + selected + "> "
								+ commissionStatusModel.get_field_2_value());
						out.println(" </option> ");
					}
				}
			%>

		</select></td>
		
		<td class="TableTextNote" align="center">Commission Channel</td>
		<td class=TableTextNote><select
			name='<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL%>'
			id='<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL%>'>
			<option value=''></option>
			<%
				for (int j = 0; j < comChannelVec.size(); j++) {
					CommissionChannelModel commissionChannelModel = (CommissionChannelModel) comChannelVec
							.get(j);
					String commissionChannelIdX = commissionChannelModel
							.getCommissionChannelId();
					String commissionChannelNameX = commissionChannelModel
							.getCommissionChannelName();
			%>
			<option value='<%=commissionChannelIdX%>'><%=commissionChannelNameX%></option>
			<%
				}
			%>
		</select></td>
	</tr>
	<tr>
		<td height="28" class="TableTextNote" align="center">Commission
		Type</td>
		<td class="TableTextNote" align="center"><select
			name="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_TYPE%>"
			onchange="changeTypeCategory(this.name);">
			<option value='0'></option>
			<%
				for (int i = 0; i < commissionTypes.size(); i++) {
					GenericModel commissionTypeModel = (GenericModel) commissionTypes
							.get(i);
					String selected = "";
					if (commissionTypeModel.get_primary_key_value().equals(
							commissionType))
						selected = "selected";

					out.println("<option value='"
							+ commissionTypeModel.get_primary_key_value() + "' "
							+ selected + "> "
							+ commissionTypeModel.get_field_2_value());
					out.println(" </option> ");
				}
			%>

		</select></td>
		<td class="TableTextNote" align="center">Commission Category</td>
		<td class="TableTextNote" align="center"><select
			name="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY%>">
			<option value='0'></option>
			<%
				for (int i = 0; i < commissionTypeCategories.size(); i++) {
					GenericModel commissionTypeCategoryModel = (GenericModel) commissionTypeCategories
							.get(i);
					String selected = "";
					if (commissionTypeCategoryModel.get_primary_key_value().equals(
							commissionTypeCategory))
						selected = "selected";

					out.println("<option value='"
							+ commissionTypeCategoryModel.get_primary_key_value()
							+ "'>"
							+ commissionTypeCategoryModel.get_field_2_value());
					out.println("</option>");
				}
			%>
		</select></td>
	</tr>
	
		
	

	<tr>
		<td colspan="6" align="center">
		<%
			out
					.println("<input name=\"Button\" type=\"button\" class=\"button\" value=\"Search\" onclick=\"document.COMform."
							+ InterfaceKey.HASHMAP_KEY_ACTION
							+ ".value='"
							+ CommissionInterfaceKey.ACTION_COMMISSION_SEARCH_COMMISSION
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

		out.println("<input type=\"hidden\" name=\""
				+ CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID
				+ "\" value=\"\">");
%> 
<table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
<%
 	for (int i = 0; i < SearchResults.size(); i++) {
 			CommissionModel commissionModel = (CommissionModel) SearchResults
 					.get(i);
 			if (!commissionModel.getCommissionStatusTypeName().equals(
 					"Deleted")) { 				
 				int preChannelId = 0;
 				if (i != 0)
 					preChannelId = ((CommissionModel) SearchResults
 							.get(i - 1)).getCommissionChannelId();
 				if (commissionModel.getCommissionChannelId() != preChannelId) {
 					int channelId = commissionModel.getCommissionChannelId();
 					String channelName = commissionModel.getCommissionChannelName();
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
		<td nowrap align=middle>Start Date</td>
		<td nowrap align=middle>End Date</td>
		<td nowrap align=middle>Status</td>
		<td nowrap align=middle>Channel</td>
		<%
			if (!commissionAction
									.equals(CommissionInterfaceKey.INPUT_HIDDEN_ALL_COMMISSION)) {
		%>
		<td nowrap align=middle>Delete</td>
		<td nowrap align=middle><%=commissionAction%></td>
		<%
			}
							if (hiddenCommissionStatus.equals("2")
									|| hiddenCommissionStatus.equals("3")) {
								if (hiddenCommissionStatus.equals("2")) {
		%>

		<td nowrap align=middle>Factors</td>
		<%
			}
		%>
		<td nowrap align=middle>Export</td>
		<td nowrap align=middle>View</td>
		<%
			}
		%>
	</tr>

	<%
		}
					//out.println("<tbody>");

					////////////////////

					out.println("<tr>");
					

					out
							.println("<td class="+InterfaceKey.STYLE[i%2]+">"+
									commissionModel.getCommissionName()
									+ "</td>");

					out.println("<td  class="+InterfaceKey.STYLE[i%2]+">"
							+ commissionModel
									.getCommissionTypeCategoryName()
							+ "</td>");
					out.println("<td  nowrap class="+InterfaceKey.STYLE[i%2]+">"
							+ commissionModel.getCommissionCreationDate()
							+ "</td>");
					out.println("<td  nowrap class="+InterfaceKey.STYLE[i%2]+">"
							+ commissionModel.getCommissionStartDate()
							+ "</td>");
					out.println("<td  nowrap class="+InterfaceKey.STYLE[i%2]+">"
							+ commissionModel.getCommissionEndDate()
							+ "</td>");
					out.println("<td  class="+InterfaceKey.STYLE[i%2]+">"
							+ commissionModel.getCommissionStatusTypeName()
							+ "");
					out.println("</td>");
					out.println("<td  class="+InterfaceKey.STYLE[i%2]+">"
							+ commissionModel.getCommissionChannelName()
							+ "");
					out.println("</td>");
					if (!commissionAction
							.equals(CommissionInterfaceKey.INPUT_HIDDEN_ALL_COMMISSION)) {
						if (hiddenCommissionStatus.equals(String
								.valueOf(commissionModel
										.getCommissionStatusTypeID()))) {
							Utility.logger.debug(hiddenCommissionStatus);
							out
									.println("<td  align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\"Delete\" onclick=\""
											+ "document.COMform."
											+ CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID
											+ ".value='"
											+ commissionModel
													.getCommissionID()
											+ "';"
											+ "document.COMform."
											+ InterfaceKey.HASHMAP_KEY_ACTION
											+ ".value='"
											+ CommissionInterfaceKey.ACTION_DELETE_COMMISSION
											+ "';"
											+ "COMform.submit();\"/></td>");
							out
									.println("<td  align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\""
											+ commissionAction
											+ "\" onclick=\""
											+ "document.COMform."
											+ CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID
											+ ".value='"
											+ commissionModel
													.getCommissionID()
											+ "';"
											+ "document.COMform."
											+ InterfaceKey.HASHMAP_KEY_ACTION
											+ ".value='"
											+ CommissionInterfaceKey.ACTION_UPDATE_COMMISSION_STATUS
											+ "';"
											+ "COMform.submit();\"/></td>");

							System.out.println("hiddenCommissionStatus= "
									+ hiddenCommissionStatus);
							if (!hiddenCommissionStatus.equals("1")) {
								if (hiddenCommissionStatus.equals("2")) {
									out
											.println("<td  align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\"Factors\" onclick=\""
													+ "document.COMform."
													+ CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID
													+ ".value='"
													+ commissionModel
															.getCommissionID()
													+ "';"
													+ "document.COMform."
													+ InterfaceKey.HASHMAP_KEY_ACTION
													+ ".value='"
													+ CommissionInterfaceKey.ACTION_COMMISSION_FACTORS
													+ "';"
													+ "COMform.submit();\"/></td>");
								}
								out
										.println("<td  align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\"Export\" onclick=\""
												+ "document.COMform."
												+ CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID
												+ ".value='"
												+ commissionModel
														.getCommissionID()
												+ "';"
												+ "document.COMform."
												+ InterfaceKey.HASHMAP_KEY_ACTION
												+ ".value='"
												+ CommissionInterfaceKey.ACTION_EXPORT_COMMISSION_TO_EXCEL
												+ "';"
												+ "COMform.submit();\"/></td>");
								out
										.println("<td  align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\"View\" onclick=\""
												+ "document.COMform."
												+ CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID
												+ ".value='"
												+ commissionModel
														.getCommissionID()
												+ "';"
												+ "document.COMform."
												+ InterfaceKey.HASHMAP_KEY_ACTION
												+ ".value='"
												+ CommissionInterfaceKey.ACTION_VIEW_COMMISSION
												+ "';"
												+ "COMform.submit();\"/></td>");
							}

						} else {
							out.println("<td  align=\"center\"></td>");
							out.println("<td  align=\"center\"></td>");

							if (hiddenCommissionStatus.equals("2"))
								out.println("<td  align=\"center\"></td>");
							out
									.println("<td  align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\"Export\" onclick=\""
											+ "document.COMform."
											+ CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID
											+ ".value='"
											+ commissionModel
													.getCommissionID()
											+ "';"
											+ "document.COMform."
											+ InterfaceKey.HASHMAP_KEY_ACTION
											+ ".value='"
											+ CommissionInterfaceKey.ACTION_EXPORT_COMMISSION_TO_EXCEL
											+ "';"
											+ "COMform.submit();\"/></td>");
							out
									.println("<td  align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\"View\" onclick=\""
											+ "document.COMform."
											+ CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID
											+ ".value='"
											+ commissionModel
													.getCommissionID()
											+ "';"
											+ "document.COMform."
											+ InterfaceKey.HASHMAP_KEY_ACTION
											+ ".value='"
											+ CommissionInterfaceKey.ACTION_VIEW_COMMISSION
											+ "';"
											+ "COMform.submit();\"/></td>");
							/*
							 
							out.println("<td  align=\"center\"></td>");
							 */
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
						.println("<input type='button' class='button' name='newcommission' value='Create New Commission' onclick=\"document.COMform."
								+ InterfaceKey.HASHMAP_KEY_ACTION
								+ ".value='"
								+ CommissionInterfaceKey.ACTION_COMMISSION_CREATE_NEW_COMMISSION
								+ "';" + "COMform.submit();\">");
			%>
			</td>
		</tr>
	</table>
</form>
<p>&nbsp;</p>
</body>
</html>
