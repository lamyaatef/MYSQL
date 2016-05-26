<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.ma.MAInterfaceKey"
         import="com.mobinil.sds.core.system.ma.model.*"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>     
     <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/yav-style.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav.js"></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav-config.js"></SCRIPT>
	  <SCRIPT language=JavaScript src="../resources/js/validation.js"></SCRIPT>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">

function setSearchValues(reports)
{
  document.getElementById("<%=MAInterfaceKey.INPUT_SEARCH_MODULE_NAME%>").value = reports;
  

}

</script>
</head>
<body>
<CENTER>
<H2> View all Privilages </H2>
</CENTER>

    <%
	HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	
	String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	
	System.out.println("The user id isssssss " + strUserID);
	
	
	
		Vector vecListOfModules   = (Vector) objDataHashMap.get(MAInterfaceKey.VECTOR_SCHEMA_MODULE_2);
		Vector vecSchemaPrivilage = (Vector)objDataHashMap.get(MAInterfaceKey.VECTOR_SCHEMA_MODULE);	
		System.out.println("Vector  in  jsp  isss"+vecSchemaPrivilage);
	
	if(vecSchemaPrivilage!=null)
	System.out.println("Vector size isssssssssssss " + vecSchemaPrivilage.size());
%>

<Form name="MAform" action="" method="POST">
 <% 
 out.println("<input type=\"hidden\" name=\""+MAInterfaceKey.INPUT_HIDDEN_PRIVILAGE_ID+"\""+
                  " value=\""+"\">"); 
                   out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
                                
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 

	%>

<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote >
      <td style=" width : 95px;">Module Name</td>
      <td>
			   <select name="<%=MAInterfaceKey.INPUT_SEARCH_MODULE_NAME%>" id="<%=MAInterfaceKey.INPUT_SEARCH_MODULE_NAME%>">
				<option value="-">-</option>
					<%
					  for (int l=0; l<vecListOfModules.size(); l++)
					  {
					    ModuleModel moduleModel = (ModuleModel)vecListOfModules.get(l);
					     String strModuleId = moduleModel.getModuleId();
					    String strModuleName = moduleModel.getModuleName();
					    String chainCitySelected = "";
				    	System.out.println("Selected!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! "+MAInterfaceKey.INPUT_SEARCH_MODULE_NAME+" "+strModuleId);

					    if(strModuleId==MAInterfaceKey.INPUT_SEARCH_MODULE_NAME)
					    {
					    	System.out.println("Not Selected!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					   //   if(strModuleId==MAInterfaceKey.INPUT_SEARCH_MODULE_NAME)
					 //     { 
					 //       chainCitySelected = "selected";
					        
					      //  chainCityName = strchainCityName;
					   //   }
					    }
					%>
						
					
					<option value="<%=strModuleId%>"   ><%=strModuleName%></option>
					
					<%
					  }
					%>
					</select>
					</td>
					</TR>
      <tr>
        <td align='center' colspan=6>
				      <% 
				out.print("<INPUT class=button type=button value=\" Search \" name=\"Search\" id=\"Search\" onclick=\"document.MAform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+MAInterfaceKey.ACTION_SHOW_ALL_PRIVILAGES+"';"+
				"MAform.submit();\">");
				%>
        </td>
      </tr>
    </table> 

            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
<%
    if(vecSchemaPrivilage.size()!=0)
    {
%>
    <tr class=TableHeader>
    		<td align='center'>Privilage ID</td>
     		<td align='center'>Privilage Name</td>
      		<td align='center'>Module Name</td>
      		<td align='center'>Privilage Status Name</td>
      		<td align='center'>Privilage Action Name</td>
      		<td align='center'>Order Value</td>
            <td align='center'>Edit</td>
             <td align='center'>Delete</td>
      </tr>
<%
 }
 for(int i=0;i<vecSchemaPrivilage.size();i++)
 {
	   System.out.println("the counter in the   jsp"+i);
	 
	 PrivilageModel privilageModel = (PrivilageModel)vecSchemaPrivilage.get(i);
 		String privilageId=privilageModel.getPrivilageId();
 		//System.out.println("Module   idddddddddd    is    in  jsp"+privilageId);
 		String privilageName=privilageModel.getPrivilageName();
 		String privilageDescription=privilageModel.getPrivilageDesc();
 		String moduleName=privilageModel.getModuleName();
 		String privilageStatusName=privilageModel.getPrivilageStatusName();
 		String privilageActionName=privilageModel.getPrivilageActionName();
 		String orderValue=privilageModel.getOrderValue();
 		String privilageTarget=privilageModel.getPrivilageTarget();
 	if(privilageDescription==null)
 	{privilageDescription="";}
	if(privilageTarget==null)
	{privilageTarget="";}
 %>
 <tr class=<%=InterfaceKey.STYLE[i%2]%>>
 		<td><%=privilageId%></td>
  		<td><%=privilageName%></td>
   		<td><%=moduleName%></td>
   		<td><%=privilageStatusName%></td>
   		<td><%=privilageActionName %></td>
   		<td><%=orderValue %></td>
    	        <td>
            <%
              out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.MAform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+MAInterfaceKey.ACTION_UPDATE_PRIVILAGE+"';"+
                  "document.MAform."+MAInterfaceKey.INPUT_HIDDEN_PRIVILAGE_ID+".value = '"+privilageId+"';"+
                  "MAform.submit();\">");
          %>
           </td>

       <td>
            <%
              out.print("<INPUT class=button type='button' value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.MAform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+MAInterfaceKey.ACTION_DELETE_PRIVILAGE+"';"+
                  "document.MAform."+MAInterfaceKey.INPUT_HIDDEN_PRIVILAGE_ID+".value = '"+privilageId+"';"+
                  "MAform.submit();\">");
          %>
           </td>    
 </tr>
 <%} %>

</TABLE>
<center>
<br>
 <% 
 out.print("<INPUT class=button type='button' value=\" Create New Privilage \" name=\"new\" id=\"new\" onclick=\"document.MAform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+MAInterfaceKey.ACTION_CREATE_PRIVILAGE+"';"+
 "MAform.submit();\">"); %>
</center>
<% 
    String searchReport= "";
  

  
  
%>
<%

	

searchReport = (String)objDataHashMap.get(MAInterfaceKey.INPUT_SEARCH_MODULE_NAME);
    if(searchReport!=null && !searchReport.equals(""))
    {
      out.println("<script>setSearchValues('"+searchReport+"');</script>");
    }
  
%>
</Form>
</body>
</html>