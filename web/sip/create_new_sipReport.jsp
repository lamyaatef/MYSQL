<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.web.interfaces.commission.*"
         import="com.mobinil.sds.web.interfaces.sip.*"       
         import="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.dcm.region.model.*"
         import="com.mobinil.sds.core.system.dcm.user.model.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.model.*"
         import="com.mobinil.sds.core.system.gn.dataview.dto.*"         
         import="com.mobinil.sds.core.system.commission.model.*"
%>
<%@page import="com.mobinil.sds.core.system.sip.model.*"%>
<%@page import="com.mobinil.sds.core.system.sip.model.sipReportChannelModel"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link href="../resources/css/Template1.css" rel="stylesheet" type="text/css" />
<SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>

<script language="javascript">


function drawCalender(argOrder,argValue)
{
    document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(COMform."+argOrder+",'dd/mm/yyyy','Choose date')\">");
    document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
}




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
document.COMform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_NAME%>.value="";
document.COMform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_ID%>.value="";
document.COMform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_STATUS%>.value="";
document.COMform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_CHANNEL%>.value="";    
document.COMform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_CATEGORY%>.value="";

document.COMform.<%=SIPInterfaceKey.CONTROL_SELECT_SIP_LABEL%>.value="";
document.COMform.<%=SIPInterfaceKey.CONTROL_SELECT_SIP_QUARTER%>.value="";

document.COMform.<%=SIPInterfaceKey.INPUT_TEXT_SIP_YEAR%>.value="";
document.COMform.<%=SIPInterfaceKey.INPUT_TEXT_LINE_COMMISSION_IDS%>.value="ex: 2364,16365,18954";
document.COMform.<%=SIPInterfaceKey.INPUT_TEXT_NI_COMMISSION_IDS%>.value="ex: 6544,5664,9987";
document.COMform.<%=SIPInterfaceKey.INPUT_TEXT_SOP_IDS%>.value="ex: 3564,5895,4589";
}

    
function checkForSubmit()
{   
  //if(eval("document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_BASE%>.value") == 0)
 // {
 	if(eval("document.COMform.<%=SIPInterfaceKey.INPUT_TEXT_SIP_REPORT_NAME%>.value") == "")
      {
        alert("Sip Report Name must not be empty.");
        return;
      }
      if(eval("document.COMform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_CHANNEL%>.value") == "")
      {
        alert("Sip Report Channel must not be empty.");
        return;
      }
      if(eval("document.COMform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_CATEGORY%>.value") == "")
      {
        alert("Sip Report Category must not be empty.");
        return;
      }
      if(eval("document.COMform.<%=SIPInterfaceKey.CONTROL_SELECT_SIP_LABEL%>.value") == "")
      {
        alert("Sip Report Label must not be empty.");
        return;
      }
      
      if(eval("document.COMform.<%=SIPInterfaceKey.CONTROL_SELECT_SIP_QUARTER%>.value") == "")
      {
        alert("Sip Report Quartar must not be empty.");
        return;
      }
      if(eval("document.COMform.<%=SIPInterfaceKey.INPUT_TEXT_SIP_YEAR%>.value") == "")
      {
        alert("Sip Report year must not be empty.");
        return;
      }
     
            
      
  //}
     COMform.submit()
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
    function disable(value)
    {
      if(value != 0)
      {
        document.COMform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_CATEGORY%>.disabled =false;
        document.COMform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_TYPE%>.disabled =false;
        document.COMform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DATA_VIEW%>.disabled =false;
        document.COMform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DATA_VIEW_TYPE%>[0].disabled=false;        
        document.COMform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DATA_VIEW_TYPE%>[1].disabled=false;               

      }
      else
      {
        document.COMform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_CATEGORY%>.disabled =true;
        document.COMform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_TYPE%>.disabled =true;
        document.COMform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DATA_VIEW%>.disabled =true;
        document.COMform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DATA_VIEW_TYPE%>[0].disabled=true;        
        document.COMform.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DATA_VIEW_TYPE%>[1].disabled=true;               
      }
    }

</script>
</head>
<body>
<center><H2>New SIP Report</H2></center>
<%

   HashMap labelHashMap = new HashMap();
   HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String strReportID = (String)objDataHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ID);
  SIPModel reportModel = (SIPModel)objDataHashMap.get(SIPInterfaceKey.SIP_REPORT_EDITTED_MODEL);
  System.out.println("The report id   issssssssssss   in create jsp isss  "+ strReportID);
  
  Vector sipReportChannelVec = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  Vector sipReportTypeCategories = (Vector)objDataHashMap.get(SIPInterfaceKey.VECTOR_SIP_TYPE_CATEGORY); 
  System.out.println("The SIP Report Channel  Vector   size   is   "+sipReportChannelVec);
 
	Vector sipQuarters = (Vector) objDataHashMap.get(SIPInterfaceKey.VECTOR_SIP_QUARTER);
	Vector sipYears = (Vector) objDataHashMap.get(SIPInterfaceKey.VECTOR_SIP_YEAR);
	Vector sipLabels = (Vector) objDataHashMap.get(SIPInterfaceKey.VECTOR_SIP_LABEL);	
 
	
  	String sipReportName = "";  	
  	String sipReportLCID   = "";
  	String sipReportNICom  = "";                                    
    String sipReportSOPID = "";
    String sipReportChannel  = "";
    String sipReportCategory = "";
    String sipReportLabel  = "";
	String sipReportQuarter  = "";
    String sipReportYear   = "";
    String sipReportDesc = "";
    System.out.println("strReportID is   "+strReportID);    
    strReportID = strReportID==null?"":strReportID;
    System.out.println("strReportID is   "+strReportID);
  if (reportModel!=null)
  {
     
     
     sipReportName = reportModel.getsipName()==null?"":reportModel.getsipName();     
     sipReportLCID   = reportModel.getSipReportLCID()==null?"":reportModel.getSipReportLCID();     
   	 sipReportNICom  = reportModel.getSipReportNICom()==null?"":reportModel.getSipReportNICom();   	
     sipReportSOPID = reportModel.getSipReportSOPID()==null?"":reportModel.getSipReportSOPID();    
     sipReportChannel  = reportModel.getsipChannelId()==0?"":reportModel.getsipChannelId()+"";    
     sipReportCategory = reportModel.getsipTypeCtageoryID()==0?"":reportModel.getsipTypeCtageoryID()+"";    
     sipReportLabel  = reportModel.getSipReportLabel()==null?"":reportModel.getSipReportLabel();    
 	 sipReportQuarter  = reportModel.getSipReportQuarter()==null?"":reportModel.getSipReportQuarter(); 	
     sipReportYear   = reportModel.getSipYearID()==0?"":reportModel.getSipYearID()+"";    
     sipReportDesc = reportModel.getsipDescription()==null?"":reportModel.getsipDescription();
    
  }
  
  
%>

<form name="COMform" action="" method="post">
<%
  
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");    
  
  out.println("<input type=\"hidden\" name=\""+SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ID+"\""+
          " value=\""+strReportID+"\">"); 
                                    
%>

<script>
    function changeTypeCategory(selectName) 
    {
      var typeID = eval("document.COMform."+selectName+".value");
      removeAllOptions('<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_CATEGORY%>');
      addOption('','','<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_CATEGORY%>'); 
      <%
      for(int i = 0 ; i< sipReportTypeCategories.size() ; i++)
      {
        GenericModel commissionTypeCategoryModel = (GenericModel)sipReportTypeCategories.get(i);
        String categoryID = commissionTypeCategoryModel.get_primary_key_value().trim();
        String categoryName = commissionTypeCategoryModel.get_field_2_value().trim();
        String categoryType = commissionTypeCategoryModel.get_field_3_value().trim();
        %>
        
        if(typeID == '<%=categoryType%>')
        {
          addOption('<%=categoryID%>','<%=categoryName%>','<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_CATEGORY%>');          
        }
        
      <%}%>
    }

</script>

<table style="BORDER-COLLAPSE: collapse" width="756" border="1" align="center" cellpadding="0" cellspacing="1" >
<tr>
  <td height="14" colspan="6" class="TableHeader">SIP Report Details</td>
</tr>
  
  <tr>
		<td class="TableTextNote" align="left">SIP Report Name</td>
		<td class="TableTextNote" align="left"><input name="<%=SIPInterfaceKey.INPUT_TEXT_SIP_REPORT_NAME%>" type="text" value="<%=sipReportName %>" class="style2" id="c_hsv" style=" width : 200px;"/> </td>
		</tr>	
  <tr>
		<td class="TableTextNote" align="left">Line Commission IDs</td>
		<td class="TableTextNote" align="left"><input name="<%=SIPInterfaceKey.INPUT_TEXT_LINE_COMMISSION_IDS%>" type="text" onkeypress="onlyNumbersIDS();" value="<%=sipReportLCID %>" class="style2" id="c_hsv" style=" width : 200px;"/> </td>
		</tr>	
			<tr>
	<td class="TableTextNote" align="left">NI Commission IDs</td>
		<td class="TableTextNote" align="left"><input name="<%=SIPInterfaceKey.INPUT_TEXT_NI_COMMISSION_IDS%>" type="text"  onkeypress="onlyNumbersIDS();"  value="<%= sipReportNICom%>" class="style2" id="c_hsv" style=" width : 200px;"/></td>
		</tr>
	
			
			
   <tr>
   <td class="TableTextNote" align="left">SOP Report IDs</td>
		<td class="TableTextNote" align="left"> <input name="<%=SIPInterfaceKey.INPUT_TEXT_SOP_IDS%>" type="text" onkeypress="onlyNumbersIDS();"  value="<%=sipReportSOPID %>" class="style2" id="c_hsv" style=" width : 200px;"/></td>
</tr>
  <tr>
  <td height="27" class="TableTextNote">SIP Report Channel </td>
  <td class=TableTextNote>
          <select name='<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_CHANNEL%>' id='<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_CHANNEL%>' value="<%=sipReportChannel %>" style=" width : 100px;">
          <option value=''></option>
              <%
              for(int j=0;j<sipReportChannelVec.size();j++)
              {
                SIPChannelModel sipReportChannelModel = (SIPChannelModel) sipReportChannelVec.get(j);
                String sipReportChannelIdX = sipReportChannelModel.getChannelId()+"";
                String sipReportChannelNameX = sipReportChannelModel.getChannelName();
                
                %>
                <option value='<%=sipReportChannelIdX%>'
                <%
                if (sipReportChannelIdX.compareTo(sipReportChannel)==0)
                {
                   out.print("selected=\"selected\" ");
                }
                %>
                ><%=sipReportChannelNameX%></option>
                <%
              }
              %>
              
            </select>
        </td>
  </tr>
  <tr>
    <td height="28" class="TableTextNote">SIP Report  Category </td>
    <td class="TableTextNote"><select name="<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_CATEGORY%>" id="<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_CATEGORY%>" value="<%=sipReportCategory %>" style=" width : 100px;">
    <option></option>
    <%
    
      for(int i = 0 ; i < sipReportTypeCategories.size() ; i++)
      {
        GenericModel commissionTypeCategoryModel = (GenericModel)sipReportTypeCategories.get(i);
        
        String selected="";
        if (commissionTypeCategoryModel.get_primary_key_value().equals(
              sipReportCategory))
           selected = "selected";
        
        out.println("<option value='"+commissionTypeCategoryModel.get_primary_key_value()+"' "+selected+">"+commissionTypeCategoryModel.get_field_2_value());
                out.println("</option>");
      }
    %>    
    </select></td>
  </tr>
  
  <tr>
		<td class="TableTextNote" align="left">Label</td>
		<td class="TableTextNote" align="left">
		<select
			name="<%=SIPInterfaceKey.CONTROL_SELECT_SIP_LABEL%>" value="<%=sipReportLabel %>" style=" width : 100px;">	
			<option value=''></option>		
			<%
				for (int i = 0; i < sipLabels.size(); i++) {
					GenericModel sipLabelModel = (GenericModel) sipLabels
							.get(i);
					
					labelHashMap.put(sipLabelModel.get_primary_key_value(),sipLabelModel.get_field_2_value());
					String selected = "";
						if (sipLabelModel.get_primary_key_value().equals(
						      sipReportLabel))
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
		</tr>
	
   
   
   
   
   <tr>
   		<td class="TableTextNote" align="left" > Report Quarter
		</td>
		<td class="TableTextNote" align="left" >
		<select name="<%=SIPInterfaceKey.CONTROL_SELECT_SIP_QUARTER%>" value="<%=sipReportQuarter%>" style=" width : 100px;">			
			<option value=''></option>
			<%
				for (int i = 0; i < sipQuarters.size(); i++) {
					GenericModel sipQuarterModel = (GenericModel) sipQuarters
							.get(i);
					String selected = "";
						if (sipQuarterModel.get_primary_key_value().equals(
						      sipReportQuarter))
							selected = "selected";
						out.println("<option value='"
								+ sipQuarterModel.get_primary_key_value()
								+ "' " + selected + "> "
								+ sipQuarterModel.get_field_2_value());
						out.println(" </option> ");
					
				}
			%>

		</select></td>
		
	</tr>
  
   
   	<tr>
	<td class="TableTextNote" align="left">Year</td>
		<td class="TableTextNote" align="left">
		<select name="<%=SIPInterfaceKey.INPUT_TEXT_SIP_YEAR%>" value="<%=sipReportYear %>" style=" width : 100px;">			
			<option value=''></option>
			<%
				for (int i = 0; i < sipYears.size(); i++) {
					GenericModel sipYearModel = (GenericModel) sipYears
							.get(i);
					String selected = "";
						if (sipYearModel.get_primary_key_value().equals(
						      sipReportYear))
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
		</tr>
		

<tr>
    <td class="TableTextNote">SIP Report Description </td>
    <td class="TableTextNote"><textarea name="<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DESCRIPTION%>" id="<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DESCRIPTION%>"  value="<%=sipReportDesc %>" style="width:60%;height=80px"></textarea></td>
  </tr>
   
  <tr>
    </form>
<%
out.println("<td colspan=\"6\" align=\"center\"><input name=\"Submit\" type=\"button\" class=\"button\" value=\"Save\""+
            " onclick=\"document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
           SIPInterfaceKey.ACTION_SAVE_NEW_SIP_REPORT+"';checkForSubmit();\" /></td>");
%>

  </tr>
</table>
</body>
</html>

