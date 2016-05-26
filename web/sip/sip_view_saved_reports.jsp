<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import ="javax.servlet.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sip.*"
         import="com.mobinil.sds.core.system.sip.model.*"
         import="com.mobinil.sds.web.interfaces.commission.*"      
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.model.*"
         import="com.mobinil.sds.core.system.gn.dataview.dto.*"         
         import="com.mobinil.sds.core.system.commission.model.*"  
         import="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"

%>

<%
    String appName = request.getContextPath();
    String userId=(String)  request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
    
    
   
%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL="STYLESHEET" TYPE="text/css" HREF="../resources/css/Template2.css">
    <SCRIPT language="JavaScript" src="../resources/js/utilities.js" type="text/javascript"></SCRIPT>
    <link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />
    <SCRIPT language="JavaScript" src="../resources/js/calendar.js" type="text/javascript"></SCRIPT>
    <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
    <script type="text/javascript" src="../resources/js/jquery_tablesorter_pack.js"></script>
    <link rel="stylesheet" href="../resources/css/themes/blue/style.css" type="text/css"/>


    

  </head>
  <body>
  <script language="javascript">
        $(document).ready(function(){$("#taskListTable").tablesorter(); });

  </script>
  <script>  
     



  
  function loadField(id)
  {
	
	  document.formChannel.<%out.print(SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_ID);%>.value=id;
  }

 

    function loadSavedReport(savedReportId,savedReportTypeId) {
      document.formChannel.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(SIPInterfaceKey.ACTION_SIP_EDIT_SAVED_REPORT);%>';
      document.formChannel.<%out.print(SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_ID);%>.value=savedReportId;
      document.formChannel.<%out.print(SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_TYPE_ID);%>.value=savedReportTypeId;        
      formChannel.submit();
      }

      function deleteSavedReport(savedReportId) {
          document.formChannel.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(SIPInterfaceKey.ACTION_SIP_DELETE_SAVED_REPORT);%>';
          document.formChannel.<%out.print(SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_ID);%>.value=savedReportId;        
          formChannel.submit();
          }    
      function newChannel() {
      document.formChannel.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(SIPInterfaceKey.ACTION_SIP_NEW_SAVED_REPORT);%>';
      formChannel.submit();
      }       
    </script>
    <CENTER>
      <H2> Saved Report List</H2>
    </CENTER>
    
    <script>
  function checkForSubmit()
  {	
	  formChannel.submit();
  }
  </script>


<script>
    function clearValues()
    {
    	 document.formChannel.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_NAME%>.value="";
    	 document.formChannel.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_ID%>.value="";
    	 document.formChannel.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_STATUS%>.value="";	
    	 document.formChannel.<%=SIPInterfaceKey.CONTROL_SELECT_SIP_QUARTER%>.value="";
    	 document.formChannel.<%=SIPInterfaceKey.INPUT_TEXT_SIP_YEAR%>.value="";
    	 document.formChannel.<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_TYPE%>.value="";
    	 
         
    }
    

</script>

    <form  action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formChannel" id="formChannel" method="post">&nbsp;


    <%
    HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);  
    
    String  savedReportId = (String )dataHashMap.get( SIPInterfaceKey.SAVED_SIP_REPORT_ID);  
    
    

       
       
  String DeleetErrorMessage= (String)  dataHashMap.get(SIPInterfaceKey.SIP_REPORT_ERROR_MESSAGE); 
  System.out.println("The  delete error message is  "+DeleetErrorMessage);
    
  
	if (DeleetErrorMessage !=null)
	{
	out.println("<script> alert('"+DeleetErrorMessage+"');</script>");
	

	}


	
	
	if (savedReportId!=null)
	{
	out.println("<script>alert('Sip Report Saved Id is "+savedReportId+"');</script>");
	}

    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+"\">");
     out.println("<input type=\"hidden\" name=\""+SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_ID+"\""+
    	
             " value=\""+"\">");                                 
     out.println("<input type=\"hidden\" name=\""+SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_TYPE_ID+"\""+
             " value=\""+"\">");                                 
        
      
    //  Vector dataViews = (Vector) dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
     
  //  Vector sipQuarters = (Vector) dataHashMap.get(SIPInterfaceKey.VECTOR_SIP_QUARTER);
      
    //  Vector yearVector = (Vector)   dataHashMap.get(SIPInterfaceKey.VECTOR_SIP_REPORT_YEAR);
   
   //   Vector savedReportStatus = (Vector)   dataHashMap.get(SIPInterfaceKey.VECTOR_SIP_REPORT_TYPE);
      
      
      
       Vector  sipQuarter =    (Vector) dataHashMap.get(SIPInterfaceKey.VECTOR_SIP_QUARTER );
       Vector sipYear  =   (Vector)  dataHashMap.get(SIPInterfaceKey.VECTOR_SIP_YEAR );
       Vector sipReportType=   (Vector)   dataHashMap.get(SIPInterfaceKey.VECTOR_SIP_REPORT_TYPE);
       Vector sipReportStatus  =   (Vector)   dataHashMap.get(SIPInterfaceKey.VECTOR_SIP_REPORT_STATUS );
       String sipreportName="";
       String  sipReportId="";
       String sipreportType="";
       String  sipyear="";
   
   
   Vector savedReportList = (Vector)     dataHashMap.get(SIPInterfaceKey.SAVED_REPORT_VECTOR);
      
     // System.out.println("the  saved  reports vector  size  issss"+savedReportList.size());
 
      
      %>

<%
  //showchannelList((HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT) , out, request);
%>      





<table style="BORDER-COLLAPSE: collapse" width="100%" border="1"
	align="center" cellpadding="0" cellspacing="1">
	<tr>
		<td colspan=6 class="TableHeader">Search</td>
	</tr>
	<tr>
	
		
		
			</tr>
	<tr>
	<td class="TableTextNote" align="center">Report Quarter</td>
		<td class="TableTextNote" align="left"><select
			name="<%=SIPInterfaceKey.CONTROL_SELECT_SIP_QUARTER%>" style=" width : 100px;">			
			<option value=''></option>
			<%
				
			for (int i = 0; i < sipQuarter.size(); i++) {
					GenericModel sipQuarterModel = (GenericModel) sipQuarter.get(i);
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
		<td class="TableTextNote" align="center">Report ID</td>
		<td class="TableTextNote" align="left" ><input type="text" onkeypress="onlyNumbers()" style=" width : 200px;"
			name="<%=SIPInterfaceKey.CONTROL_TEXT_SIP_ID%>"
			value="<%=sipReportId%>" /></td>
		
			</tr>
			
			<tr>
	<td class="TableTextNote" align="center">Report Year</td>
	
		<td class="TableTextNote" align="left">
		<select
			name="<%=SIPInterfaceKey.INPUT_TEXT_SIP_YEAR %>"	value="<%=sipyear %>" style=" width : 100px;">	
			<option value=''></option>		
			<%
				for (int i = 0; i < sipYear.size(); i++) {
					GenericModel sipYearModel = (GenericModel) sipYear
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
		<td class="TableTextNote" align="center">Report Name</td>
		<td class="TableTextNote" align="left"><input type="text" style=" width : 200px;"
			name="<%=SIPInterfaceKey.CONTROL_TEXT_SIP_NAME%>"
			value="<%=sipreportName%>" /></td>
			</tr>
			<tr>
	
	
	
	
	

		
		
		
		
		
		<td class="TableTextNote" align="center">Report Type</td>
	
		<td class="TableTextNote" align="left">
		<select
			name="<%=SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_TYPE %>"	value="<%=sipreportType%>" style=" width : 100px;">	
			<option value=''></option>		
			<%
				for (int i = 0; i < sipReportType.size(); i++) {
					GenericModel sipReportModel = (GenericModel) sipReportType
							.get(i);
					
					
					String selected = "";
						if (sipReportModel.get_primary_key_value().equals(
								sipReportModel))
							selected = "selected";
						out.println("<option value='"
								+ sipReportModel.get_primary_key_value()
								+ "' " + selected + "> "
								+ sipReportModel.get_field_2_value());
						out.println(" </option> ");
					
				}
			%>

		</select>
		</td>
		
		
		
	
	
		
		<td class="TableTextNote" align="center">Report Status</td>
		<td class="TableTextNote" align="left" >
		<select
			name="<%=SIPInterfaceKey.CONTROL_TEXT_SIP_STATUS%>" style=" width : 100px;">
			<option value=''></option>			
			<%
				for (int i = 0; i < sipReportStatus.size(); i++) {
					GenericModel sipStatusModel = (GenericModel) sipReportStatus
							.get(i);
					String selected = "";

					if (sipStatusModel.get_field_2_value()
							.equals("Draft")  ||  sipStatusModel.get_field_2_value()
							.equals("Preparing")  ) {
					
						if (sipStatusModel.get_primary_key_value().equals(
						      sipStatusModel))
							selected = "selected";
						out.println("<option value='"
								+ sipStatusModel.get_primary_key_value()
								+ "' " + selected + "> "
								+ sipStatusModel.get_field_2_value());
						out.println(" </option> ");
					}
				}
			%>

		</select>
		</td>
	
		
	</tr>


	<tr>
		<td colspan="6" align="center">
		<%
			out
					.println("<input name=\"Button\" type=\"button\" class=\"button\" value=\"Search\" onclick=\"document.formChannel."
							+ InterfaceKey.HASHMAP_KEY_ACTION
							+ ".value='"
							+ SIPInterfaceKey.ACTION_SEARCH_SAVED_REPORT
							+ "'; checkForSubmit();\"/>");
		%> <input name="Submit2" type="button" class="button"
			value="Clear Values" onclick="clearValues();" />
		</div>
		</td>
	</tr>
</table>
<p>&nbsp;</p>


























<% if(savedReportList!=null  &&  savedReportList.size()>0)  
{
%>
	
				 <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 

    <tr class=TableHeader>
    		<td align='center'>Report  ID</td>
    		<td align='center'>Report  Name</td>
     		<td align='center'>Report Type Name</td>
     		<td align='center'>Report Status</td>     		
     		<td align='center'>Report Year</td>
     		     		
     		
     		     		<td align='center'>Delete</td>
     		     		<td align='center'>Factor</td>
     		     		<td align='center'>View</td>
     		     		<td align='center'>Export Excel</td>
     		
    </tr>
		
		<tr class="TableTextNote" align="center"> </td>
		
			<%
			int reportId=0;	
			String reportName="";
			String reportTypeName="";
			String reportStatus="";
			
			String reportYear="";
			for (int i = 0; i < savedReportList.size(); i++) 
				{
					savedSipReportModel  sipReportlist = (savedSipReportModel) savedReportList
							.get(i);
					reportId=sipReportlist.getsipReportID();
					reportName=sipReportlist.getsipReportName();
				//	sipReportlist.getsipReportDataViewTypeName();
					reportTypeName=sipReportlist.getsipReportTypeName();
					reportStatus=	sipReportlist.getsipReportStatusTypeName();					
					reportYear=	sipReportlist.getSipYearName();
					
					 %>
					 <tr class=<%=InterfaceKey.STYLE[i%2]%>>
					 		<td><%=reportId%></td>
					 		<td><%=reportName%></td>  		
			  			    <td><%=reportTypeName%></td>			  			
					  		<td><%=reportStatus.compareTo("Draft")==0?"Ready":reportStatus%></td>		  				
					  		
					  		<td><%=reportYear%></td>
					  		
					  		<TD width="20%" noWrap align=middle>
        <%
        out.println("<INPUT class=button type=button value= \"Delete\"  name =\"Delete \"    onclick=\"formChannel."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SIPInterfaceKey.ACTION_SIP_DELETE_SAVED_REPORT+"';"+
        "loadField('"+sipReportlist.getsipReportID()+"');formChannel.submit();\">"); 
        
        
        %>
        </TD>
        <TD width="20%" noWrap align=middle>
        <%
        out.println("<INPUT class=button type=button value= \"Factors\"  name =\"Factors \"    onclick=\"formChannel."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SIPInterfaceKey.ACTION_FACTOR_EDIT+"';"+
        "loadField('"+sipReportlist.getsipReportID()+"');formChannel.submit();\">"); 
        
        
        %>
        </TD>



 <TD width="20%" noWrap align=middle>
        <%
        out.println("<INPUT class=button type=button value= \"View\"  name =\"View \"    onclick=\"formChannel."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SIPInterfaceKey.ACTION_EXPORT_SAVED_REPORT+"';"+
        "loadField('"+sipReportlist.getsipReportID()+"');formChannel.submit();\">"); 
        
        
        %>
        </TD>

<TD width="20%" noWrap align=middle>
        <%
        out.println("<INPUT class=button type=button value= \"Export Excel\"  name =\"Export excel \"    onclick=\"formChannel."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SIPInterfaceKey.ACTION_EXPORT_SAVED_REPORT_EXCEL+"';"+
        "loadField('"+sipReportlist.getsipReportID()+"');formChannel.submit();\">"); 
        
        
        %>
        </TD>




<% 		
				}
			%>




	
			</tr>


</table>





<% }
	

%>







<br>
<br>
<div align="center"><input type="button" value="Add Report" onClick="newChannel();" ></div>
 
    </form>
   </body>
</html>


<%-- 
<%!
 
public void showchannelList(HashMap objDataHashMap , JspWriter out, HttpServletRequest request)
{
    String appName = request.getContextPath();

try{
  if (objDataHashMap == null)
  {
  
   return;
   }
  
  Vector vecChannelList = (Vector) objDataHashMap.get(SIPInterfaceKey.SAVED_REPORT_VECTOR) ;
  
  if (vecChannelList ==null)
  {
    return;
  }
  int taskListSize = vecChannelList.size();
  out.println("<TABLE class=\"tablesorter\" id=\"taskListTable\">");
    out.println("<thead>");
  out.println("<TR>");
   
out.println("<th ><font size=2>Report Id</font></a></th><th ><font size=2>Report Name</font></a></th><th ><font size=2>Report Type</font></a></th><th><font size=2>Edit</font></a></th><th><font size=2>Delete</font></a></th></thead><tbody>");

  for (int i=0; i<taskListSize; i++)
  {
  
    SIPSavedReportModel savedReportModel=  (SIPSavedReportModel) vecChannelList.elementAt (i);    
    int savedReportId = savedReportModel.getSavedReportId().intValue();
    String savedReportName=savedReportModel.getSavedReportName();
    String savedReportType=savedReportModel.getSavedReportType();
	Integer savedReportTypeID = savedReportModel.getSavedReportTypeID(); 
    
    
   
    out.println("<TR class=\""+InterfaceKey.STYLE[i%2]+"\">");
    out.println("<td width=\"15%\">"+savedReportId+"</td>");   

    out.println("<td width=\"90%\">"+savedReportName+"</td>");   
    out.println("<td width=\"90%\">"+savedReportType+"</td>");   

    
    out.println("<td width=\"10%\" align=center><input type=button value=\"Edit\" onclick=\"loadSavedReport("+savedReportId+","+savedReportTypeID+");\"></td>");
    out.println("<td width=\"10%\" align=center><input type=button value=\"Delete\" onclick=\"deleteSavedReport("+savedReportId+");\"></td>");


    out.println("</tr>");
    

  }
     
 
  
  %>

  

    <%!
  out.println("</tbody>");
    out.println("</table>");

  }
  catch(Exception e)
  {
  e.printStackTrace();
  }
}

  --%> 
