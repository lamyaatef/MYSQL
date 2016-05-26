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
        String pageHeader="New Memo";
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String action=  (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
        String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        Vector memoTypes=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_MEMO_TYPES);
        Vector memoStates=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_MEMO_STATES);
        Vector paymentTypes=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES);
        Vector paymentMethods=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD);
        Vector channels=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_DCM_CHANNELS);
        String exist=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_EXISTING_MEMO);
        
        
        
                      
%>
<script language="JavaScript">
function exist()
{
alert("it is already exist");

}
function toggleSpecific()
{
	

document.getElementById("new").disabled=false;
document.getElementById("1").style.display='';
document.getElementById("2").style.display='';
document.getElementById("3").style.display='none';
document.getElementById("4").style.display='none';
//document.getElementById("3").style.display='none';
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_CREATE_SPECIFIC_MEMO%>";

}

function toggleCollection()
{
// document.getElementById("3").style.display='';
 document.getElementById("1").style.display='none';
 document.getElementById("new").disabled=false;
 document.getElementById("2").style.display='none';
 document.getElementById("3").style.display='';
 document.getElementById("4").style.display='';
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_CREATE_COLLECTION_MEMO%>";
}
function toggleNone()
{
 //document.getElementById("3").style.display='';
 document.getElementById("1").style.display='none';
 document.getElementById("2").style.display='none';
 document.getElementById("3").style.display='none';
 document.getElementById("4").style.display='none';
 document.getElementById("new").disabled=true;

}
function toggleChange()
{
	if(document.getElementById("MemoType").value == "Specific")
		toggleSpecific();
	else if(document.getElementById("MemoType").value == "Collection")
		toggleCollection();
	else
		toggleNone();
		
}

function submitNew()
{
	document.<%=formName%>.submit();	
   
}
function submitViewClosedPayments()
{
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_VIEW_CLOSED_PAYMENTS%>";
document.<%=formName%>.submit();
}
function checkPaymentAvailability()
{
	alert("Sorry! can Not create the memo\n there is no Payments in the Accounting Module by these Characteristics"); 
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

         <%if(action.equals(MemoInterfaceKey.ACTION_CREATE_SPECIFIC_MEMO)||action.equals(MemoInterfaceKey.ACTION_CREATE_COLLECTION_MEMO))
            {
        	 
        	 String payments=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_CHECK_MEMO_CREATION);
            
            	if(Integer.parseInt(payments)==0){
            %>
            	<script>
            	checkPaymentAvailability();
            	</script>
            	
            <%}}%>
        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
            <%
            if(exist!=null&&exist.length()!=0)
            {%>
            <script>
            exist();
            </script>
            <%}%>
            
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableHeader>
              <td colspan="4" align="center"><b>New Memo</b></td>
              </TR>
              <tr>
              <td>Description</td><td colspan="2"><input type="text" name="<%=MemoInterfaceKey.CONTROL_MEMO_DESCRIPTION%>" id="<%=MemoInterfaceKey.CONTROL_MEMO_DESCRIPTION%>"></td>
              <td></td>
              </tr>
              <tr>
              <td >Channel</td>
              <td >
              <select name="<%=MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID%>" id="<%=MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID%>">
              <%
              for(int i=0;i<channels.size();i++){
              ChannelModel channel=(ChannelModel)channels.get(i);
              
              %>
              <option  value="<%=channel.getChannelId()%>"><%=channel.getChannelName()%></option>
              <%}%>  
              </select> 
              </td>
              <td></td>
              <td></td>
              </tr>
              <tr>
              
              <td>Memo Type</td>
              <td >
              <select name="MemoType" id="MemoType" onchange="javaScript:toggleChange();">
              <option value=" "></option>
              <option value="Specific" >Specific</option>
              <option value="Collection" >Collective</option>
              </select>
              </td>
              
              <td id="1" style="display:none">Payment Type</td>
              <td id="2" style="display:none">
              <select name="<%=MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID%>" id="<%=MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID%>">
              <%if(paymentTypes!=null||paymentTypes.size()!=0)
               {          
              for(int i=0;i<paymentTypes.size();i++){
              PaymentTypeModel type=(PaymentTypeModel) paymentTypes.get(i);
              
              %>
              <option  value="<%=type.getPaymentTypeId()%>"><%=type.getPaymentTypeName()%></option>
              <%}}%>
              </select>
              </td>
              
              <td id="3" style="display:none">Payment Method</td>
              <td id="4" style="display:none">
              <select name="<%=MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD_ID%>" id="<%=MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD_ID%>">
              <%if(paymentMethods!=null||paymentMethods.size()!=0)
               {          
              for(int i=0;i<paymentMethods.size();i++){
              PaymentTypeMethodModel method=(PaymentTypeMethodModel) paymentMethods.get(i);
              
              %>
              <option  value="<%=method.getPaymentTypeMethodId() %>"><%=method.getPaymentTypeMethodName() %></option>
              <%}}%>
              </select>
              </td>
              
              </tr> 
                
             </TABLE>  

             <br><br>
             <table  cellspacing="2" cellpadding="1" border="0" width="100%">
                <tr>
                    <td align=center>
                        <input class=button disabled="disabled" type="button" name="new" id="new" value="Create"
                               onclick="javascript:submitNew()">
                    </td>
                     
                </tr>
                
            </table>
           
             </form>
    </body>
</html>