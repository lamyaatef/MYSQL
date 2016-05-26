
<%@ page contentType="text/html;charset=windows-1252"
              import ="com.mobinil.sds.core.utilities.Utility"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                   
              import="com.mobinil.sds.web.interfaces.*"
         
              import="com.mobinil.sds.web.interfaces.sip.*"              
              import ="java.io.*"              
              import="com.mobinil.sds.core.system.dcm.genericModel.*"
              %>
              
 <%
 String appName = request.getContextPath();
 String userId=(String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
 HashMap objDataHashMap = (HashMap) request
	.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

Vector yearVector = (Vector)objDataHashMap.get(SIPInterfaceKey.VECTOR_SIP_REPORT_YEAR);
 %>

<html>
   <head>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <TITLE>Data Import</TITLE>      
      <script type="text/javascript">
      function onlyNumbers(){
    	  if (event.keyCode < 48 || event.keyCode > 58) {
    			event.returnValue = false;
    		}
    		}
      function load_image(fileName,formName,uploadformName)

		{
		
			var imgpath = document.getElementById(fileName).value;
			
			if(imgpath != "")
			
			{
			
				// code to get File Extension..
				
				var arr1 = new Array;
				
				arr1 = imgpath.split("\\");
				
				var len = arr1.length;
				
				var img1 = arr1[len-1];
				
				
				var filext = img1.substring(img1.lastIndexOf(".")+1);
								
				// Checking Extension
				
				if(filext == "xls")
				{		
					if (document.getElementById('quartar').value=='')
					{
						alert('Please choose quartar.');
						
					}
					else
					{
						if (document.getElementById('year').value=='')
						{
							alert('Please enter year.');
						}
						else
						{
					var temppp ='<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?';
									 	
				 	temppp+='action=';
				 			 	
				 	temppp+=document.getElementById('sip_action').value+'&';	
				 						
				 	temppp+='previous_action='+document.getElementById('previous_action').value+'&';
				 	temppp+='year='+document.getElementById('year').value+'&';
				 	temppp+='quartar='+document.getElementById('quartar').value+'&';
				 					 	
					temppp+='sip_report_name='+document.getElementById('sip_report_name').value+'';
					
				
								
				document.getElementById('SDForm').action=temppp;						
				document.getElementById('SDForm').submit();			
						}}		
				}
				else
				{
					if(filext == "xlsx")
					{
						alert("Please convert xlsx to xls.");
					}
					else
					{
					alert("Invalid Excel File Format Selected.");
					}
				}
				
				
				
			}
			else alert("please select excel file.");				

		}
      function downloadTemp()
      {
    	  var temppp ='<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?';
		 	
		 	temppp+='action=';		 			 	
		 	temppp+='download_excel_template&';				 	
			temppp+='sip_report_name='+document.getElementById('sip_report_name').value+'';
			
		
						
		document.getElementById('SDForm').action=temppp;						
		document.getElementById('SDForm').submit();	
      }
      
      </script>
   </head>



<body onkeypress = "if(event.keyCode==13){SDForm.submit();}">    
<form name="SDForm" id="SDForm"  method="post" enctype="multipart/form-data">
<input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID %>" id="<%=InterfaceKey.HASHMAP_KEY_USER_ID %>" value="<%=userId %>" />
<center>
      <h2>Upload List Of Field Reports Per Supervisor</h2>
      </center>
<input type="hidden" name="sip_action" id="sip_action" value="<%=SIPInterfaceKey.ACTION_HIDDEN_PARAM_NAME_UPLOAD_DATA %>">
<input type="hidden" name="<%= SIPInterfaceKey.ACTION_HIDDEN_PARAM_NAME_PREVIOUS_ACTION%>" id="<%= SIPInterfaceKey.ACTION_HIDDEN_PARAM_NAME_PREVIOUS_ACTION %>" value="<%=SIPInterfaceKey.ACTION_UPLOAD_FIELD_REP_SUPERVISOR %>">
<input type="hidden" name="<%= SIPInterfaceKey.HIDDEN_PARAM_NAME_SIP_REPORT %>" id="<%= SIPInterfaceKey.HIDDEN_PARAM_NAME_SIP_REPORT%>" value="<%= SIPInterfaceKey.HIDDEN_PARAM_SIP_REPORT_SUPERVISOR%>">
    <center>
    <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1>
    <tr>
    
              <TD class=TableHeader>Quarter</TD>
    		  <td>
    				<select name="quartar" style=" width : 140px;">
    							<option value=""></option>
    							<option value="1">Q1</option>
    							<option value="2">Q2</option>
    		    				<option value="3">Q3</option>
    		    				<option value="4">Q4</option>      							
    				
    				</select>
    		 </td>
    </tr>
    <tr>
    
              <TD class=TableHeader>Year</TD>
              <td><select
			name="year" style=" width : 140px;">	
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

		</select></td>
    		
    			
    </tr>
    <tr>
      <TD class=TableHeader>File</TD>
              <td>    <input type="file" name="distUploadFile" id="distUploadFile">
              </td>    
    </tr>    
    <tr>      
              <td colspan=2 align="center">    <input class="button" type="button" name="Submit" value="Submit your file" onclick="javascript:load_image('distUploadFile','SDForm','SDForm');">
              <input class="button" type="button" name="download" value="Download Template" onclick="javascript:downloadTemp();">
              </td>
    
    </tr>
    
  </table>
    </center>
</form>
   </body>
</html>