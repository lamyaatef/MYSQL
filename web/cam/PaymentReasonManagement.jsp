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
//String real_path = request.getRealPath("/cam/camMemosFiles/");
String serverName = request.getServerName();
int serverPort = request.getServerPort();
String formName = "memo_mgt_form";
String pageHeader="Payment Reason Management";

HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
String action=  (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
Vector reasons =(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON);
                     
%>
<script language="JavaScript">

function submitViewEdit(reasonId)
{
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_REASON_ID%>.value = reasonId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_VIEW_UPDATE_PAYMENT_REASON%>";
	document.<%=formName%>.submit();
	
}
function submitEdit(reasonId)
{
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_REASON_ID%>.value = reasonId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_UPDATE_PAYMENT_REASON%>";
	document.<%=formName%>.submit();

}
function submitDelete(reasonId){
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_REASON_ID%>.value = reasonId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_DELETE_PAYMENT_REASON%>";
	document.<%=formName%>.submit();
}
function submitViewAdd()
{
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_VIEW_ADD_PAYMENT_REASON%>";
	document.<%=formName%>.submit();
}
function submitAdd()
{
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_ADD_PAYMENT_REASON%>";
	document.<%=formName%>.submit();
}
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL="STYLESHEET" TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        <SCRIPT language="JavaScript" src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/calendar.js"></SCRIPT>
    </head>
    <body>
        <center><h2> <%=pageHeader%></h2></center>

        <%
         
        %>
        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
            <input type="hidden" name="<%=PaymentInterfaceKey.HIDDEN_PAYMENT_REASON_ID %>" value="-1">
            <%if(action.equals(PaymentInterfaceKey.ACTION_VIEW_PAYMENT_REASON)||action.equals(PaymentInterfaceKey.ACTION_UPDATE_PAYMENT_REASON)||action.equals(PaymentInterfaceKey.ACTION_DELETE_PAYMENT_REASON)||action.equals(PaymentInterfaceKey.ACTION_ADD_PAYMENT_REASON)) {%>
            <%if(reasons!=null||reasons.size()!=0){%>
           <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <TR class="TableHeader">
            <td colspan="4" align="center">Payment Reasons</td>
            </TR> 
            <tr class="TableHeader">
            <td>reason</td>
            <td>Description</td>
            <td>Edit</td>
            <td>Delete</td>
            </tr>
            <%
            for(int i=0;i<reasons.size();i++){
            	PaymentStatusReasonModel reason=(PaymentStatusReasonModel)reasons.get(i);
           
            %>
            <tr>
            
            <td><%=reason.getReason()%></td>
            
            <td><%=reason.getReasonDesc()%></td>
            <td ><input class="button" type="button" value="Edit" onclick="javascript:submitViewEdit('<%=reason.getReasonId() %>')"></td>
            <td ><input class="button" type="button" value="Delete" onclick="javascript:submitDelete('<%=reason.getReasonId() %>')"></td>
            </tr>
            <%}%>
            <tr>
            <td colspan="4" align="center"><input class="button" type="button" value="Add New Reason" onclick="javascript:submitViewAdd()"></td>
            </tr>
            </table>
            <%} %>
            <%}else if(action.equals(PaymentInterfaceKey.ACTION_VIEW_ADD_PAYMENT_REASON)){ %>
            
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <TR class="TableHeader">
            <td colspan="2">Add Reason</td>
            </TR>
            <tr>
            <td>Reason</td>
            <td><input name="<%=PaymentInterfaceKey.CONTROL_PAYMENT_REASON_NAME %>" id="<%=PaymentInterfaceKey.CONTROL_PAYMENT_REASON_NAME%>"  > </td>
            </TR>
            <tr>
            <td>Description</td>
            <td><input name="<%=PaymentInterfaceKey.CONTROL_PAYMENT_REASON_DESC %>" id="<%=PaymentInterfaceKey.CONTROL_PAYMENT_REASON_DESC %>"> </td>
            </tr>
            <tr>
            <td colspan="2" align="center"><input class="button" type="button" value="Add" onclick="javascript:submitAdd()"></td>
            </tr>
            </TABLE>
            <%}else if(action.equals(PaymentInterfaceKey.ACTION_VIEW_UPDATE_PAYMENT_REASON)){
            	Vector reasonVec=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON);
            	PaymentStatusReasonModel reason=(PaymentStatusReasonModel)reasonVec.get(0);
            	%>
            	<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <TR class="TableHeader">
            <td colspan="2">Edit Reason</td>
            </TR>
            <tr>
            <td>Reason</td>
            <td><input name="<%=PaymentInterfaceKey.CONTROL_PAYMENT_REASON_NAME %>" id="<%=PaymentInterfaceKey.CONTROL_PAYMENT_REASON_NAME%>"  value="<%=reason.getReason() %>" > </td>
            </TR>
            <tr>
            <td>Description</td>
            <td><input name="<%=PaymentInterfaceKey.CONTROL_PAYMENT_REASON_DESC %>" id="<%=PaymentInterfaceKey.CONTROL_PAYMENT_REASON_DESC %>"  value="<%=reason.getReasonDesc() %>" > </td>
            </tr>
            <tr>
            <td colspan="2" align="center"><input class="button" type="button" value="Update" onclick="javascript:submitEdit('<%=reason.getReasonId() %>')"></td>
            </tr>
            </TABLE>
            
            <%} %>

             </form>
    </body>
</html>