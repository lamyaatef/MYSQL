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
Vector sipType = (Vector) objDataHashMap.get(SIPInterfaceKey.VECTOR_SIP_TYPE);
 %>

<html>
   <head>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <TITLE>Data Import</TITLE>      
      <script type="text/javascript">
      function hideAllDivs(){
    	    
    	  document.getElementById('quartarDivName').style.display="none";
    	  document.getElementById('quartarDivSelect').style.display="none";

    	  document.getElementById('monthDivName').style.display="none";
    	  document.getElementById('monthDivSelect').style.display="none";

    	  document.getElementById('yearDivName').style.display="none";
    	  document.getElementById('yearDivSelect').style.display="none";

    	  
    	  
          }

      function showAllDivs(){
  	    
    	  document.getElementById('quartarDivName').style.display="block";
    	  document.getElementById('quartarDivSelect').style.display="block";

    	  document.getElementById('monthDivName').style.display="block";
    	  document.getElementById('monthDivSelect').style.display="block";

    	  document.getElementById('yearDivName').style.display="block";
    	  document.getElementById('yearDivSelect').style.display="block";

    	  
    	  
          }
      function showAllDivsUnlessOne(divName){
    	    
    	  document.getElementById('quartarDivName').style.display="block";
    	  document.getElementById('quartarDivSelect').style.display="block";

    	  document.getElementById('monthDivName').style.display="block";
    	  document.getElementById('monthDivSelect').style.display="block";

    	  document.getElementById('yearDivName').style.display="block";
    	  document.getElementById('yearDivSelect').style.display="block";

    	  document.getElementById(divName+'Name').style.display="none";
    	  document.getElementById(divName+'Select').style.display="none";
    	  
          }
      
      function onlyNumbers(){
    	  if (event.keyCode < 48 || event.keyCode > 58) {
    			event.returnValue = false;
    		}
    		}
		function changeFileTypeFun(fileType){
			
			var fileTypeValue = fileType.value;
			alert (fileTypeValue);
			document.getElementById('<%=SIPInterfaceKey.HIDDEN_PARAM_NAME_SIP_REPORT%>').value = fileTypeValue;
			if (fileTypeValue==6||fileTypeValue==3)
			{
				showAllDivsUnlessOne('quartarDiv');
			}
			else if(fileTypeValue==7)
			{
				hideAllDivs();
			}
			else
			{
				showAllDivsUnlessOne('monthDiv');
			}
			
			resetCotrols();
			 
			}

		function resetCotrols()
		{
			document.getElementById('quartar').selectedIndex = 0;
			document.getElementById('month').selectedIndex = 0;
			document.getElementById('year').selectedIndex = 0;			
			document.getElementById('distUploadFile').value='';
			}
      function downloadTemp()
      {
    	  if (document.getElementById('<%=SIPInterfaceKey.INPUT_SEARCH_FILE_TYPE%>').value=='')
			{
				alert('Please select upload file type.');
				
			}
			else
			{
    	  var temppp ='<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?';
		 	
		 	temppp+='action=';		 			 	
		 	temppp+='download_excel_template&';				 	
			temppp+='sip_report_name='+document.getElementById('sip_report_name').value+'';
			document.getElementById('DistForm').action=temppp;						
			document.getElementById('DistForm').submit();	
			}
						
		
      }

      function submitFun (){
    	  var temppp ='<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?';
		 	
		 	temppp+='action=';
		 			 	
		 	temppp+=document.getElementById('sip_action').value+'&';	
		 					 						
		 	temppp+='previous_action='+document.getElementById('previous_action').value+'&';
		 	temppp+='year='+document.getElementById('year').value+'&';
		 	temppp+='month='+document.getElementById('month').value+'&';
		 	temppp+='quartar='+document.getElementById('quartar').value+'&';
		 					 	
			temppp+='sip_report_name='+document.getElementById('sip_report_name').value+'';
			
		
						
		document.getElementById('DistForm').action=temppp;						
		document.getElementById('DistForm').submit();
          }
      function load_image(fileName,formName,uploadformName)

		{
		
			var imgpath = document.getElementById(fileName).value;
			
			if (document.getElementById('<%=SIPInterfaceKey.INPUT_SEARCH_FILE_TYPE%>').value=='')
			{
				alert('Please select upload file type.');
				
			}
			else
			{

			if(imgpath != "")
			
			{
			
				// code to get File Extension..
				
				var arr1 = new Array;
				
				arr1 = imgpath.split("\\");
				
				var len = arr1.length;
				
				var img1 = arr1[len-1];
				
				
				var filext = img1.substring(img1.lastIndexOf(".")+1);
								
				// Checking Extension
				
				if(filext == "xls" || 	filext == "xlsx" )
				{		
					var fileTypeValue = document.getElementById('<%=SIPInterfaceKey.HIDDEN_PARAM_NAME_SIP_REPORT%>').value ;
					
					

				if(fileTypeValue==7)
				{
					submitFun();
				}
				else
				{

					if ((fileTypeValue==6||fileTypeValue==3)&&document.getElementById('month').value=='')
					{
						
						alert('Please choose month.');
						
					}
					else
					{
					
					if (fileTypeValue!=6&&fileTypeValue!=3&&document.getElementById('quartar').value=='')
					{
						alert('Please choose quartar.');
						
					}
					else
					{
						if (fileTypeValue!=7&&document.getElementById('year').value=='')
						{
							alert('Please choose year.');
						}
						else
						{

							submitFun();
								
								
							}
					}

				}
				}	
							
				}
				else
				{
				
				
					alert("Invalid Excel File Format Selected.");
					
				}
				
				
				
			}
			else alert("please select excel file.");

			}				

		}
      
      </script>
   </head>



<body onload="hideAllDivs();" onkeypress = "if(event.keyCode==13){DistForm.submit();}">    
<form name="DistForm" id="DistForm"  method="post" enctype="multipart/form-data">
<input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID %>" id="<%=InterfaceKey.HASHMAP_KEY_USER_ID %>" value="<%=userId %>" />
<center>
      <h2>Upload List Of Distributors Per Executives</h2>
      </center>
<input type="hidden" name="sip_action" id="sip_action" value="<%=SIPInterfaceKey.ACTION_HIDDEN_PARAM_NAME_UPLOAD_DATA %>">
<input type="hidden" name="<%= SIPInterfaceKey.ACTION_HIDDEN_PARAM_NAME_PREVIOUS_ACTION%>" id="<%= SIPInterfaceKey.ACTION_HIDDEN_PARAM_NAME_PREVIOUS_ACTION %>" value="<%=SIPInterfaceKey.ACTION_UPLOAD_DISTRIBUTORS_PER_EXECUTIVES %>">
<input type="hidden" name="<%= SIPInterfaceKey.HIDDEN_PARAM_NAME_SIP_REPORT %>" id="<%= SIPInterfaceKey.HIDDEN_PARAM_NAME_SIP_REPORT%>" value="">
    <center>
    <table style="BORDER-COLLAPSE: collapse" cellSpacing=0 cellPadding=0 width="50%" border=1>
     <tr>
	<TD class=TableHeader>Upload File Type</TD>
	
		<td class="TableTextNote" align="left">
		<select style="width:200px" onchange="changeFileTypeFun(this);"
			name="<%=SIPInterfaceKey.INPUT_SEARCH_FILE_TYPE %>"	>	
			<option value=''>-</option>		
			<%
				for (int i = 0; i < sipType.size(); i++) {
					GenericModel sipTypeModel = (GenericModel) sipType
							.get(i);
					
					
					String selected = "";
						if (sipTypeModel.get_primary_key_value().equals(
								sipTypeModel))
							selected = "selected";
						out.println("<option value='"
								+ sipTypeModel.get_primary_key_value()
								+ "' " + selected + "> "
								+ sipTypeModel.get_field_2_value());
						out.println(" </option> ");
					
				}
			%>

		</select>
		</td>
		</tr>
    <tr>
              <TD class=TableHeader><div id="quartarDivName">Quarter</div></TD>
    		  <td><div id="quartarDivSelect">
    				<select name="quartar" style=" width : 200px;">
    							<option value=""></option>
    							<option value="1">Q1</option>
    							<option value="2">Q2</option>
    		    				<option value="3">Q3</option>
    		    				<option value="4">Q4</option>     							
    				
    				</select>
    				</div>
    		 </td>
    </tr>
    <tr>
    <TD class=TableHeader> <div id="monthDivName">Month</div></TD>
    		  <td>
    				
    				<div id="monthDivSelect">
    				<select name="month" style=" width : 200px;">
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
    		    				      							
    				
    				</select></div>
    		 </td>
    </tr>
    <tr>
    
              <TD class=TableHeader><div id="yearDivName">Year</div></TD>
              <td><div id="yearDivSelect"><select
			name="year" style=" width : 200px;">	
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

		</select></div></td>
    		
    			
    </tr>
   
    <tr>
      <TD class=TableHeader>File</TD>
              <td>    <input type="file" name="distUploadFile" id="distUploadFile">
              </td>    
    </tr>    
    <tr>      
              <td colspan=2 align="center">    <input class="button" type="button" name="Submit" value="Submit your file" onclick="javascript:load_image('distUploadFile','DistForm','DistForm');">
               <input class="button" type="button" name="download" value="Download Template" onclick="javascript:downloadTemp();">
              </td>
    
    </tr>
    
  </table>
    </center>
</form>
   </body>
</html>