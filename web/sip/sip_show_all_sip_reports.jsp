<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sip.*"

         import="com.mobinil.sds.core.system.sip.model.*"
         
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
<H2> View SIP Report Types </H2>
</CENTER>
<%
	HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	
	String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	
	System.out.println("The user id isssssss " + strUserID);
	
	
	
		
		Vector vecSchemaModules = (Vector)objDataHashMap.get(SIPInterfaceKey.VECTOR_SCHEMA_SIP);	
		System.out.println("Vector  in  jsp  isss"+vecSchemaModules);
	
	if(vecSchemaModules!=null)
	System.out.println("Vector size isssssssssssss " + vecSchemaModules.size());
%>
<Form name="SIPform" action="" method="POST">
 <% 
 out.println("<input type=\"hidden\" name=\""+SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ID+"\""+
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
    		<td align='center'>Report Type  ID</td>
     		<td align='center'>Report Type Name</td>
     		     		<td align='center'>Edit</td>
     		
     		     		<td align='center'>Delete</td>
     		
    </tr>
<%
 //}
 for(int i=0;i<vecSchemaModules.size();i++)
 {
	 
	// SIPReportModel sipReportModel = (SIPReportModel) vecSchemaModules.get(i);

	 
	 SIPReportTypeModel sipReportModel=(SIPReportTypeModel) vecSchemaModules.get(i);
	 String sipReportId=sipReportModel.getSipReportId();
	 String sipReportName=sipReportModel.getSipReportName();
	 if(sipReportName==null)
	 {sipReportName="";}
 

	
 %>
 <tr class=<%=InterfaceKey.STYLE[i%2]%>>
 		<td><%=sipReportId%></td>
  		<td><%=sipReportName%></td>
   		
   		
    	        <td>
            <%
              out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.SIPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SIPInterfaceKey.ACTION_UPDATE_SIP_REPORT+"';"+
                  "document.SIPform."+SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ID+".value = '"+sipReportId+"';"+
                  "SIPform.submit();\">");
          %>
           </td>

       <td>
            <%
              out.print("<INPUT class=button type='button' value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.SIPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SIPInterfaceKey.ACTION_DELETE_SIP_REPORT+"';"+
                  "document.SIPform."+SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ID+".value = '"+sipReportId+"';"+
                  "SIPform.submit();\">");
          %>
           </td>    
 </tr>
 <%} %>

</TABLE>
<center>
 <% 
 out.print("<INPUT class=button type='button' value=\" Add Report Type \" name=\"new\" id=\"new\" onclick=\"document.SIPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SIPInterfaceKey.ACTION_CREATE_REPORT_TYPE+"';"+
 "SIPform.submit();\">"); %>
</center>

</Form>
</body>
</html>