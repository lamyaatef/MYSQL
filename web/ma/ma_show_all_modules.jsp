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
    
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<CENTER>
<H2> View all Modules </H2>
</CENTER>
<%
	HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	
	String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	
	System.out.println("The user id isssssss " + strUserID);
	
	
	
		
		Vector vecSchemaModules = (Vector)objDataHashMap.get(MAInterfaceKey.VECTOR_SCHEMA_MODULE);	
		System.out.println("Vector  in  jsp  isss"+vecSchemaModules);
	
	if(vecSchemaModules!=null)
	System.out.println("Vector size isssssssssssss " + vecSchemaModules.size());
%>
<Form name="MAform" action="" method="POST">
 <% 
 out.println("<input type=\"hidden\" name=\""+MAInterfaceKey.INPUT_HIDDEN_MODULE_ID+"\""+
                  " value=\""+"\">"); 
                   out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
                                
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 

	%>
  <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
<%
   // if(vecSchemaProdcut.size()!=0)
    //{
%>
    <tr class=TableHeader>
    		<td align='center'>Module ID</td>
     		<td align='center'>Module Name</td>
      		<td align='center'>Module Description</td>
      		<td align='center'>Module Status</td>
            <td align='center'>Edit</td>
             <td align='center'>Delete</td>
      </tr>
<%
 //}
 for(int i=0;i<vecSchemaModules.size();i++)
 {
	   System.out.println("the counter in the   jsp"+i);
	 
	 ModuleModel moduleModel = (ModuleModel)vecSchemaModules.get(i);
 		String moduleId=moduleModel.getModuleId();
 		System.out.println("Module   idddddddddd    is    in  jsp"+moduleId);
 		String moduleName=moduleModel.getModuleName();
 		
 		System.out.println("Module   NAME    is    in  jsp"+moduleName);
 		String moduleDescription=moduleModel.getModuleDesc();
 		String moduleStatusName=moduleModel.getModuleStatusName();
 	if(moduleName==null)
 	{moduleName="";}
 	if(moduleDescription==null)
 	{moduleDescription="";}
	
 %>
 <tr class=<%=InterfaceKey.STYLE[i%2]%>>
 		<td><%=moduleId%></td>
  		<td><%=moduleName%></td>
   		<td><%=moduleDescription%></td>
   		   		<td><%=moduleStatusName%></td>
   		
    	        <td>
            <%
              out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.MAform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+MAInterfaceKey.ACTION_UPDATE_MODULE+"';"+
                  "document.MAform."+MAInterfaceKey.INPUT_HIDDEN_MODULE_ID+".value = '"+moduleId+"';"+
                  "MAform.submit();\">");
          %>
           </td>

       <td>
            <%
              out.print("<INPUT class=button type='button' value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.MAform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+MAInterfaceKey.ACTION_DELETE_MODULE+"';"+
                  "document.MAform."+MAInterfaceKey.INPUT_HIDDEN_MODULE_ID+".value = '"+moduleId+"';"+
                  "MAform.submit();\">");
          %>
           </td>    
 </tr>
 <%} %>

</TABLE>
<center>
 <% 
 out.print("<INPUT class=button type='button' value=\" Create New Module \" name=\"new\" id=\"new\" onclick=\"document.MAform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+MAInterfaceKey.ACTION_CREATE_NEW_MODULE+"';"+
 "MAform.submit();\">"); %>
</center>

</Form>
</body>
</html>