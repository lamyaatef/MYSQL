<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import=" com.mobinil.sds.web.interfaces.cam.*"
         import=" com.mobinil.sds.core.system.cam.accrual.model.*"
         import ="com.mobinil.sds.core.system.cam.accrual.dao.*"
         import ="com.mobinil.sds.web.interfaces.InterfaceKey"
         import ="com.mobinil.sds.core.system.cam.payment.model.*"
         import="com.mobinil.sds.core.system.cam.memo.model.*"        
         
         %>
<%
        String appName = request.getContextPath();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String formName = "memo_mgt_form";
        String pageHeader="SQL Templates Management";
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String userID =(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String action=(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
        
        Vector templates=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_SQL_TEMPLATES);
        //t dcmId=1;
        
        
        
        
%>
<script language="javaScript">

function submitAdd()
{
	
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_ADD_PYAMENT_DCM_ELIGIBLE%>";
document.<%=formName%>.submit();
}
function submitEdit(templateId) {
	
	document.getElementById("<%=MemoInterfaceKey.HIDDEN_SQL_TEMPLATE_ID%>").value=templateId;

	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_VIEW_EDIT_SQL_TEMPLATE_MANAGEMENT%>";
	document.<%=formName%>.submit();
	
	
}


</script>



<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/calendar.js"></SCRIPT>
        </head>
    <body>
        <center><h2> <%=pageHeader%></h2></center>
        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_SQL_TEMPLATE_ID %>" value="0">
            
            
            
            
            
            <TABLE align="center"  style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <tr class=TableHeader>
    		<td>Template Name</td>
    		<td>Template SQL</td>
    		<td>Edit</td>
  			</tr>
			
			<%for(int i=0;i<templates.size();i++){
				SqlTemplateModel template=(SqlTemplateModel)templates.get(i);
				%>
				<tr>
				<td><%=template.getTemplateName() %></td>
				<td><%=template.getTemplateSql() %></td>
				<td><input class="button" type="button" value="Edit" onclick="submitEdit('<%=template.getTemplateId() %>')"> </td>
				</tr>
			<%}%>
			
	        </TABLE>
            
                        

            
                     
       </form>
    </body>
</html>