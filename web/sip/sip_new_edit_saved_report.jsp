<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sip.*"
         import="com.mobinil.sds.core.system.sip.model.*"         
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.model.*"
         import="com.mobinil.sds.core.system.gn.dataview.dto.*"         
         import="com.mobinil.sds.core.system.commission.model.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
 %>

 <%
    String appName = request.getContextPath();
     String userId=(String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);

 %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL="STYLESHEET" TYPE="text/css" HREF="../resources/css/Template1.css">    
        <SCRIPT language="JavaScript" src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    <SCRIPT language="JavaScript">
function loadValues(control,value)
  {
    document.formChannelDetails.control.value=value;
  }    
        function checkbeforSubmit()
        {
          if(NonBlank(document.formChannelDetails.<%out.print(SIPInterfaceKey.CONTROL_TEXT_SAVED_REPORT_NAME);%>, true, 'Report Name'))
          {  
          document.formChannelDetails.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(SIPInterfaceKey.ACTION_SAVE_NEW_SIP_REPORT_1);%>'
          formChannelDetails.submit();
          } 
        return false;
        }
  
</SCRIPT>
  </head>
  <body>
        <CENTER>
<H2>  New&nbsp;Report </H2>
</CENTER>
 <form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formChannelDetails" id="formChannelDetails" method="post" >

<%

HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);


  
 String savedReportName="";
 String savedReportType="";
 
 String sipReportYear = (String)objDataHashMap.get(SIPInterfaceKey.INPUT_TEXT_SIP_YEAR);
 
 Vector yearVector = (Vector)objDataHashMap.get(SIPInterfaceKey.VECTOR_SIP_REPORT_YEAR);
 
 Vector sipQuarters = (Vector) objDataHashMap.get(SIPInterfaceKey.VECTOR_SIP_QUARTER);
 
 Vector dataViews = (Vector) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);  



Vector configInfo = (Vector) objDataHashMap.get(SIPInterfaceKey.SAVED_REPORT_MODEL) ;

Vector   sipReportTypeVector  = (Vector) objDataHashMap.get(SIPInterfaceKey.VECTOR_SCHEMA_SIP);


out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
out.println("<input type=\"hidden\" name=\""+InterfaceKey. HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+userId+" \">");                  

out.println("<TABLE style=\"WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px\" cellSpacing=2 cellPadding=1 width=\"746\" border=1>");
out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Report&nbsp;Name *</TD>");

out.println("<TD class=TableTextNote colSpan=4><INPUT style=\"WIDTH: 452px; HEIGHT: 22px\" size=66"
              + " value=\"" +"\""  
              + " name=\""+SIPInterfaceKey.CONTROL_TEXT_SAVED_REPORT_NAME+"\">");



 


 %>
 
 
 
 
 
 
 
 
 
 <tr>
	<td class="TableTextNote" align="center" > Report Type *
</td>
<td class="TableTextNote" align="left" >

<select name="<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_TYPE%>" style=" width : 100px;">			
	<option value=''></option>
	<%
		
	System.out.println("sipReportTypeVector  size  isssssss"+sipReportTypeVector.size());
	for (int j = 0; j < sipReportTypeVector.size(); j++) {
		SIPReportTypeModel  sipReportTypeModel =(SIPReportTypeModel) sipReportTypeVector
		.get(j);
		//GenericModel sipReportTypeModel = (GenericModel) sipReportTypeVector
		//			.get(j);
			String selected = "";
			
				
				out.println("<option value='"
						+ sipReportTypeModel.getSipReportId()
						+ "' " + selected + "> "
						+ sipReportTypeModel.getSipReportName());
				out.println(" </option> ");
			
		}
	%>

</select></td>
 
 
 
 
 
 </tr>
 
 
 

<!-- <tr>
	<td class="TableTextNote" align="center" > Report Quarter
</td>
<td class="TableTextNote" align="left" >

<select name="SIPInterfaceKey.CONTROL_SELECT_SIP_QUARTER" style=" width : 100px;">			
	<option value=''></option>
	
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
	
	

</select></td></tr>
-->
<tr>
 
	<td class="TableTextNote" align="center">Report Year</td>
	
		<td class="TableTextNote" align="left">
		<select
			name="<%=SIPInterfaceKey.INPUT_TEXT_SIP_YEAR %>"	value="<%=sipReportYear %>" style=" width : 100px;">	
			<option value=''></option>		
			<%
				for (int i = 0; i < yearVector.size(); i++)
				{
					GenericModel sipYearModel = (GenericModel) yearVector.get(i);
							
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


</tr>
<tr>
    
              <TD class=TAbleTextNote>Month</TD>
    		  <td>
    				
    				
    				<select name="<%=SIPInterfaceKey.INPUT_TEXT_SIP_MONTH%>" style=" width : 100px;">
    							<option value=""></option>
    							<option value="1">JAN</option>    							
    							<option value="2">FEP</option>
    		    				<option value="3">MAR</option>
    		    				<option value="4">APR</option>
    		    				<option value="5">MAY</option>
								<option value="6">JUN</option>
								<option value="7">JUL</option>
								<option value="8">AUG</option>
								<option value="6">SEP</option>
								<option value="10">OCT</option>
								<option value="11">NOV</option>
								<option value="12">DEC</option>
    		    				      							
    				
    				</select>
    		 </td>
    </tr>

  <tr>
    <td height="26" class="TableTextNote">SIP Report Data View</td>
    <td class="TableTextNote"><select name="<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DATA_VIEW%>" id="<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DATA_VIEW%>">
    <option value="0"></option>
    <%
         BriefDataViewDTO dtoBriefDataView                        = null;
    if(dataViews != null)
    {
        
        for (int i = 0; i < dataViews.size(); i++) 
        {
            dtoBriefDataView = (BriefDataViewDTO)  dataViews.elementAt(i);
            if (dtoBriefDataView.getOverRideSQL() !=null)
            {
          System.out.println(" not going to display "+ dtoBriefDataView.getDataViewName());
          System.out.println(" the over ride sql is  "+ dtoBriefDataView.getOverRideSQL());
            }
            else
            {            
              out.println(" <option desc='"+dtoBriefDataView.getDataViewDescription()+"' value="+dtoBriefDataView.getDataViewID()+">"+dtoBriefDataView.getDataViewName()+"</option> ");
            }
        
        }
    } 
    %>
    </select></td>
  </tr>
  <tr>
  <td class=TableTextNote> Data View Type</td>
  <td class=TAbleTextNote>Cross Tab<input type="radio" value="<%=SIPInterfaceKey.DATA_VIEW_TYPE_A%>" name="<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DATA_VIEW_TYPE%>">   Normal<input type="radio" value="<%=SIPInterfaceKey.DATA_VIEW_TYPE_B%>" name="<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DATA_VIEW_TYPE%>"></td>
  </tr>


<% 

out.println("</TABLE>");

out.println("<input type=\"hidden\" name=\""+SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_ID+"\""+
                  " value=\""+"\">");
if (configInfo!=null)
{
	SIPSavedReportModel cm = (SIPSavedReportModel)configInfo.elementAt(0);
        savedReportName = cm.getSavedReportName();
        String savedReportId = cm.getSavedReportId().intValue()+"";
          savedReportType=cm.getSavedReportType();
          Integer savedReportTypeId =cm.getSavedReportTypeID(); 

        
        out.println("<script>");
        out.println("document.formChannelDetails."+SIPInterfaceKey.CONTROL_TEXT_SAVED_REPORT_NAME+".value='"+savedReportName+"';");        
        out.println("document.formChannelDetails."+InterfaceKey.HASHMAP_KEY_USER_ID+".value='"+userId+"';");        
        out.println("document.formChannelDetails."+SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_ID+".value='"+savedReportId+"';");  
        out.println("document.formChannelDetails."+SIPInterfaceKey.CONTROL_SELECT_SAVED_REPORT_TYPE+".value='"+savedReportTypeId+"';");        

        out.println("</script>");
}       



%>

<table  cellspacing="2" cellpadding="1" border="0" width="747" style="WIDTH: 747px; HEIGHT: 26px">
<tr>
<td align="middle">
      <P align="center">

 &nbsp;<INPUT class="button" onclick="checkbeforSubmit();" type="button" value=" Save " name="save"></P></td>
</tr>
</table>
</form>

  </body>
</html>




