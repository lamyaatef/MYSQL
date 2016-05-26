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
        String pageHeader="SQL Templates Backup Management";
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String userID =(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String action=(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
        
        String realPath = request.getRealPath("/cam/template/");
        
        
        
%>
<script language="javaScript">

function submitAdd()
{
	
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_ADD_PYAMENT_DCM_ELIGIBLE%>";
document.<%=formName%>.submit();
}
function submitCreate() {
	
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_CREATE_SQL_TABLE_BKP_FILE%>";
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
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_APPLIACTION_CONTEXT%>" value="<%= realPath%>">
            
            
            
            
            
            
            <TABLE align="center"  style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="50%" border="1">
            <tr class="TableHeader">
    		<td>Table Name</td>
    		<td>
    		<select name="<%=MemoInterfaceKey.SQL_TABLE_ID%>" id="<%=MemoInterfaceKey.SQL_TABLE_ID%>">
    		<option value="1">CAM_PAYMENT_EXCEL_MANAGEMENT</option>
    		<option value="2">CAM_MEMO_EXCEL_MANAGEMENT</option>
    		<option value="3">CAM_MEMO_PDF_MANAGEMENT</option>
    		</select>
    		</td>
  			</tr>
			
			<tr><td colspan="2" align="center"><input class="button" type="button" value="Generate File" onclick="submitCreate()"></td></tr>
	        </TABLE>
            
                        

            
                     
       </form>
    </body>
</html>