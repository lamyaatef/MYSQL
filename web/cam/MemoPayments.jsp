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
        String pageHeader="Memo Payments";
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String userID =(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String action=(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
        String memoId=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_MEMO_ID);
        
        String memoName=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_MEMO_NAME);
        
        Vector payments=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_MEMO_PAYMENTS);
        
        
        
        
%>
<script language="javaScript">
function submitDeleteMember(memoId,paymentId)
{
document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID %>.value = memoId;
document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_PAYMENT_ID%>.value = paymentId;
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_DELETE_MEMO_PAYMENT_NAMES_TYPES%>";
document.<%=formName%>.submit();
}
function submitSearch()
{
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_SEARCH_IN_MEMO%>";
document.<%=formName%>.submit();
}

function submitSetZero()
{
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_SET_NEGATIVE_TO_ZERO_IN_MEMO%>";
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
            
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_PAYMENT_ID%>" value="-1">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>" value="<%=memoId%>">
            
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_NAME%>" value="<%=memoName%>">
            
            
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <tr class="TableHeader">
            <td colspan="4" align="center"><%=memoName %></td>
            </tr>
            <tr class="TableHeader">
            <td>Payment NAME</td>
            <td>Payment Type</td>
            <td>Delete</td>
            </tr>
            <%for(int i=0;i<payments.size();i++){
            	MemoMembersModel payment=(MemoMembersModel)payments.get(i);
            %>
            <tr>
            <td><%= payment.getPaymetName() %></td>
            <td><%= payment.getPaymentTypename() %></td>
            <td><input class="button" type="button"  value="Delete" onclick="submitDeleteMember('<%=memoId %>','<%=payment.getPaymentId() %>')"></td> 
            
            </tr>
            <%} %>
            
            </TABLE>

            
                     
       </form>
    </body>
</html>