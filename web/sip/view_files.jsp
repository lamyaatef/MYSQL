  <%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sip.*"
         import = "com.mobinil.sds.core.system.sip.model.*"
       	import="com.mobinil.sds.core.system.dcm.genericModel.*"
       
%>

<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <script type="text/javascript">
      function loadField(id,id2)
      {
		
          document.fileForm.<%=SIPInterfaceKey.INPUT_HIDDEN_FILE_ID%>.value=id;
          document.fileForm.<%=SIPInterfaceKey.INPUT_HIDDEN_FILE_TYPE_ID%>.value=id2;
      }
      function exportfile(typeId)
      {
    	  document.getElementById('<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>').value='<%out.print(SIPInterfaceKey.ACTION_EXPORT_FILE);%>';
          
          document.getElementById('<%=SIPInterfaceKey.CONTROL_HIDDEN_FILE_TYPE_ID%>').value=typeId;

      }
      function setSearchValues(year,quarter,type)
      {
        document.getElementById("<%=SIPInterfaceKey.INPUT_SEARCH_YEAR%>").value = year;
        document.getElementById("<%=SIPInterfaceKey.INPUT_SEARCH_QUARTER%>").value = quarter;
        document.getElementById("<%=SIPInterfaceKey.INPUT_SEARCH_FILE_TYPE%>").value = type;
        

      }
      </script>
    </head>
  <body>
  <form  name='fileForm' id='fileForm' action='' method='post'>  
<%
 
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);


    //Vector files  = (Vector) objDataHashMap.get(SIPInterfaceKey.VECTOR_FILES);
	Vector sipQuarters = (Vector) objDataHashMap.get(SIPInterfaceKey.VECTOR_SIP_QUARTER);
	Vector result=(Vector) objDataHashMap.get(SIPInterfaceKey.VECTOR_SIP_SEARCH_RESULT);
	Vector sipYear = (Vector) objDataHashMap.get(SIPInterfaceKey.VECTOR_SIP_YEAR);
	Vector sipType = (Vector) objDataHashMap.get(SIPInterfaceKey.VECTOR_SIP_TYPE);
	String strUserID = (String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
	
	///System.out.println("YEAR ISSSSSSSSSSSS "+sipYear.size());
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
 " value=\""+"\">");
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
        " value=\""+strUserID+"\">");
  
out.println("<input type=\"hidden\" name=\""+SIPInterfaceKey.INPUT_HIDDEN_FILE_ID+"\" value=\""+"\">");
out.println("<input type=\"hidden\" name=\""+SIPInterfaceKey.INPUT_HIDDEN_FILE_TYPE_ID+"\" value=\""+"\">");

out.println("<input type=\"hidden\" name=\""+SIPInterfaceKey.CONTROL_HIDDEN_FILE_TYPE_ID+"\" value=\""+"\">");
  
  
  
  
%>  

<center>
    <h1>Uploaded File Details</h1>
</center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote >
      <td style=" width : 95px;">Year</td>
      <td>
			   <select name="<%=SIPInterfaceKey.INPUT_SEARCH_YEAR%>" id="<%=SIPInterfaceKey.INPUT_SEARCH_YEAR%>">
				<option value=''>-</option>
					<%
					  for (int l=0; l<sipYear.size(); l++)
					  {
							GenericModel sipYearModel = (GenericModel) sipYear.get(l);
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
					</TR>
								<tr>
	<td style=" width : 95px;">Quarter</td>
	
		<td class="TableTextNote" align="left">
		<select
			name="<%=SIPInterfaceKey.INPUT_SEARCH_QUARTER %>"	 style=" width : 100px;">	
			<option value=''>-</option>		
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

		</select>
		</td>
		</tr>
		
								<tr>
	<td style=" width : 95px;">File Type</td>
	
		<td class="TableTextNote" align="left">
		<select
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
	<td style=" width : 95px;">User Id</td>
	
		<td class="TableTextNote" align="left">
		<input type="text" style=" width : 200px;"
			name="<%=SIPInterfaceKey.HASHMAP_KEY_SEARCH_USER_ID%>"
			value="" />
		</td>
		</tr>
      <tr>
        <td align='center' colspan=6>
				      <% 
				out.print("<INPUT class=button type=button value=\" Search \" name=\"Search\" id=\"Search\" onclick=\"document.fileForm."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SIPInterfaceKey.ACTION_SEARCH_FILE+"';"+
				"form.submit();\">");
				%>
        </td>
      </tr>
    </table> 

    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <TR class=TableHeader>
         <td width="10%" nowrap align=middle>File Id</td>
         <td width="20%" nowrap align=middle>Year</td>
         <td width="20%" nowrap align=middle>Quartar/Month</td>
         <td width="20%" nowrap align=middle>Time</td>
         <td width="40%" nowrap align=middle>File Type</td>
         <td width="20%" nowrap align=middle>User ID</td>         
         <td width="20%" nowrap align=middle>Export</td>
         <td width="20%" nowrap align=middle>Invalid File</td>
         <td width="20%" nowrap align=middle>Delete</td>          
      </TR>
      <%
      
        String FileId="";
    	String Year="";
    	String Month="";
    	int typeId ;
    	String fileType="";
  		String userId="";
  		String time="";
    	for (int i=0; i< result.size(); i++)
        {
        	fileModel model = (fileModel) result.get(i);            
        	FileId =  model.getFILE_ID();
        	Year=model.getYEAR()==null?"":model.getYEAR();
        	time = model.getUPLOADED_DATE();
        	Month=model.getMONTH();
        	System.out.println("Month issssss "+Month);
        	typeId = model.getREPORT_TYPE_ID();
        	userId=model.getUSER_ID();
    		
    		for (int k = 0; k < sipType.size(); k++) {
				GenericModel sipTypeModel = (GenericModel) sipType
						.get(k);
				
				
				
					if (sipTypeModel.get_primary_key_value().equals(
					      String.valueOf(typeId)))
					   fileType=sipTypeModel.get_field_2_value();
				
					
				
			}
        	
      %>
      <TR class=TableTextNote>
        <td width="20%" nowrap align=middle><%=FileId%></td>
        <td width="40%" nowrap align=middle><%=Year%></td>
        <td width="40%" nowrap align=middle><%=Month%></td>
        <td width="40%" nowrap align=middle><%=time%></td>        
        <td width="40%" nowrap align=middle><%=fileType%></td>       
                <td width="20" nowrap align=middle><%=userId%></td>        
         
        <%  out.println("<td width=\"20%\" nowrap align=middle><input class=button type=button value=\"Export\" onclick=\"exportfile('1');document.getElementById('"+SIPInterfaceKey.INPUT_HIDDEN_FILE_ID+"').value='"+FileId+"';fileForm.submit();\"></td>");
        
        out.println("<td width=\"20%\" nowrap align=middle><input class=button type=button value=\"Export\" onclick=\"exportfile('0');document.getElementById('"+SIPInterfaceKey.INPUT_HIDDEN_FILE_ID+"').value='"+FileId+"';fileForm.submit();\"></td>");
     %>
     
        <TD width="10%" noWrap align=middle>
        <%
        
        
       
 
       
        
        
      out.println("<INPUT class=button type=button    value= \"Delete\"  name =\"Delete \"onclick=\"document.fileForm."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+SIPInterfaceKey.ACTION_SIP_FILE_DELETE+"';"+
"loadField('"+FileId+"','"+typeId+"');fileForm.submit();\">"); 
        
        %>
        </TD>
        
      
      
      
  
      
      
      
      
      
      
      
  
       
      <%
      }
      %>
    </table>

   </form>  
  </body>
</html>