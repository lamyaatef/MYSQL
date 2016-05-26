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
         %>
<%
        String appName = request.getContextPath();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String formName = "payment_edit_form";
        String pageHeader="Edit SCM Payment Status";
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        Vector allScm = (Vector) dataHashMap.get(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT);
        PaymentScmStatusModel item = (PaymentScmStatusModel)allScm.get(0);
        Vector everyDcmStatus=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS);
        EveryDcmStatusModel everyDcmstatus=(EveryDcmStatusModel)everyDcmStatus.get(0);
        Vector statusReason=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON);
        Vector allStatusCases=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES);
        
        
%>

<script language="JavaScript">
function submitEdit(scmId)
{
	
if(document.getElementById("<%=PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID%>").value == 2)
{
alert("you can only change from Eligible to Suspended or the opposite");
return;
}
document.<%=formName%>.<%=PaymentInterfaceKey.CONTROL_HIDDEN_SCM_PAYMENT_ID%>.value = scmId;
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_DO_EDIT_SCM_PAYMENT_STATUS_DISTRIBUTION%>";
document.<%=formName%>.submit();
}
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    </head>
    <body>
        <center><h2> <%=pageHeader%></h2></center>

        <%
         
        %>
        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
            <input type="hidden" name="<%=PaymentInterfaceKey.CONTROL_HIDDEN_SCM_PAYMENT_ID%>" value="">
            

              <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableHeader >
              <td colspan="2" align="center">Edit</td>
              </TR>
          
             <TR>
             <td name="" id="">SCM Name</td>
             <td><%=item.getDcmName()%></td>
             </tr>
             <tr>
             <td name="" id="">SCM Code</td>
             <td><%=item.getDcmCode()%></td>
             </tr>
             <tr>
             <td>SCM Status</td>
             <td>
             <select name="<%=PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID%>" id="<%=PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID%>">
            <%
             String status_selected="";
             for(int k=0;k<allStatusCases.size();k++){
             PaymentStatusCasesModel c=(PaymentStatusCasesModel) allStatusCases.get(k);
             
             if(c.getCamStatusForPayment().equals("NOT_ELIGIBLE"))
        		 continue;
             if(c.getCamStatusForPayment().equalsIgnoreCase(everyDcmstatus.getCamStatusForPayment()))
             status_selected="selected";
             else
             status_selected="";             
              %>
              
             <option <%=status_selected%> value="<%=c.getCamStatusForPaymentId()%>"><%=c.getCamStatusForPayment()%></option>
             <%}%>
             </select>             
             </td>             
             </TR>
             <tr>
             <td>Reason</td>
                        
             <td>
             <select name="<%=PaymentInterfaceKey.CONTROL_SCM_PAYMENT_STATUS_REASON_ID%>" id="<%=PaymentInterfaceKey.CONTROL_SCM_PAYMENT_STATUS_REASON_ID%>">
             <%            
             for(int j=0;j<statusReason.size();j++){
             PaymentStatusReasonModel reason=(PaymentStatusReasonModel) statusReason.get(j);
             //System.out.println("status reason: "+statusReason.get(j));
             if(reason.getReason().equalsIgnoreCase(everyDcmstatus.getReason()))
             status_selected="selected";
             else
             status_selected="";
             %>
             <option <%=status_selected%> value="<%=reason.getReasonId()%>"><%=reason.getReason()%></option>
             <%}%>
             </select>
             
             </td>
             </tr>         
            </TABLE>

<table  cellspacing="2" cellpadding="1" border="0" width="100%">
                <tr>
                    <td align=center>
                        <input class=button type="button" name="new" value="Update"
                               onclick="submitEdit('<%=item.getScmId()%>');">
                    </td>                         
                </tr>
            </table>

        </form>
    </body>
</html>
