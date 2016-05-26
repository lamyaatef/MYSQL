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
					
					
					
					
					var temppp ='<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?';
									 	
				 	temppp+='action=';
				 			 	
				 	temppp+=document.getElementById('sip_action').value+'&';	
				 						
				 	temppp+='previous_action='+document.getElementById('previous_action').value+'&';
				 	temppp+='year='+document.getElementById('year').value+'&';
				 	temppp+='quartar='+document.getElementById('quartar').value+'&';
				 					 	
					temppp+='sip_report_name='+document.getElementById('sip_report_name').value+'';
					
				
								
				document.getElementById('FldForm').action=temppp;						
				document.getElementById('FldForm').submit();			
								
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
			
		
						
		document.getElementById('FldForm').action=temppp;						
		document.getElementById('FldForm').submit();	
      }
      
      </script>
   </head>



<body onkeypress = "if(event.keyCode==13){FldForm.submit();}">    
<form name="FldForm" id="FldForm"  method="post" enctype="multipart/form-data">
<input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID %>" id="<%=InterfaceKey.HASHMAP_KEY_USER_ID %>" value="<%=userId %>" />
<center>
      <h2>Upload List Of POS Per Field Rep</h2>
      </center>
<input type="hidden" name="sip_action" id="sip_action" value="<%=SIPInterfaceKey.ACTION_HIDDEN_PARAM_NAME_UPLOAD_DATA %>">
<input type="hidden" name="<%= SIPInterfaceKey.ACTION_HIDDEN_PARAM_NAME_PREVIOUS_ACTION%>" id="<%= SIPInterfaceKey.ACTION_HIDDEN_PARAM_NAME_PREVIOUS_ACTION %>" value="<%=SIPInterfaceKey.ACTION_UPLOAD_POS_FIELD_REP %>">
<input type="hidden" name="<%= SIPInterfaceKey.HIDDEN_PARAM_NAME_SIP_REPORT %>" id="<%= SIPInterfaceKey.HIDDEN_PARAM_NAME_SIP_REPORT%>" value="<%= SIPInterfaceKey.HIDDEN_PARAM_SIP_REPORT_EMP%>">

<input type="hidden" name="quartar" id="quartar" value="0">
<input type="hidden" name="year" id="year" value="0">

    <center>
    <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1>
    
    
    <tr>
      <TD class=TableHeader>File</TD>
              <td>    <input type="file" name="distUploadFile" id="distUploadFile">
              </td>    
    </tr>    
    <tr>      
              <td colspan=2 align="center">    <input class="button" type="button" name="Submit" value="Submit your file" onclick="javascript:load_image('distUploadFile','FldForm','FldForm');">
              <input class="button" type="button" name="download" value="Download Template" onclick="javascript:downloadTemp();">
              </td>
    
    </tr>
    
  </table>
    </center>
</form>
   </body>
</html>