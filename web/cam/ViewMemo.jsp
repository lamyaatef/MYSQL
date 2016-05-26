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
        String pageHeader="Memo List";
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String userID =(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String action=(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
        String memoId=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_MEMO_ID);
        
        String memoName=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_MEMO_NAME);
        
        Vector memoMembers=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_MEMO_MEMBERS);
        
        String stateName=(String)dataHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME);
        
        Vector paymentTypes=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES);
        boolean setZeroForNegative=true;
        
        for(int i=0;i<memoMembers.size();i++)
        {
        MemoMembersModel member=(MemoMembersModel)memoMembers.get(i);
        
        if(member.getScmCommissionValue()<0){
      	  setZeroForNegative=true;
        }
        }
        
%>
<script language="javaScript">
function submitDeleteMember(dcmId,paymentId)
{
document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_DCM_ID%>.value = dcmId;
document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_PAYMENT_ID%>.value = paymentId;
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_REMOVE_MEMBERS_OF_MEMO%>";
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
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_DCM_ID%>" value="-1">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_PAYMENT_ID%>" value="-1">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>" value="<%=memoId%>">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME%>" value="<%=stateName%>">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_NAME%>" value="<%=memoName%>">
            
            
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <tr class="TableHeader">
            <td colspan="4" align="center">Search</td>
            </tr>
            <tr>
            <td>DCM NAME</td>
            <td><input type="text" name="<%=MemoInterfaceKey.CONTROL_MEMO_DCM_NAME%>" id="<%=MemoInterfaceKey.CONTROL_MEMO_DCM_NAME%>">  </td>
            
            <td>DCM Code</td>
            <td><input type="text" name="<%=MemoInterfaceKey.CONTROL_MEMO_DCM_CODE %>" id="<%=MemoInterfaceKey.CONTROL_MEMO_DCM_CODE %>" ></td>
            </tr>
            <tr>
            <td>Payment Type</td>
            <td>
              <select name="<%=MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID%>" id="<%=MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID%>">
              <option value=""></option>
              <%if(paymentTypes!=null||paymentTypes.size()!=0)
               {          
              for(int i=0;i<paymentTypes.size();i++){
              PaymentTypeModel type=(PaymentTypeModel) paymentTypes.get(i);
              
              %>
              <option  value="<%=type.getPaymentTypeId()%>"><%=type.getPaymentTypeName()%></option>
              <%}}%>
              </select>
              </td>
              </tr>  
            </TABLE>
            
            <br>
            <table  cellspacing="2" cellpadding="1" border="0" width="100%">
                <tr>
                    <td align="center">
                        <input class="button" type="button" name="search" value="Search"
                               onclick="javascript:submitSearch()">
                    </td>
                    
                     
                </tr>
            </table>
            
            <%if(setZeroForNegative==true){ %>
            <table  cellspacing="2" cellpadding="1" border="0" width="100%">
                <tr>
                    <td align="center">
                        <input class="button" type="button" name="Set Zero Commission For Negative Values" value="Set Zero Commission For Negative Values"
                               onclick="javascript:submitSetZero()">
                    </td>
                    
                     
                </tr>
            </table>
            <%} %>
            
            
            
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
              <TR class="TableHeader">
              <%if(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_MEMO)||action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SET_NEGATIVE_TO_ZERO_IN_MEMO)){
              %>
              <td colspan="4" align="center" name="<%=MemoInterfaceKey.CONTROL_MEMO_NAME%>"><b><%=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_MEMO_NAME)%></b></td>
              <%}else
              {
              %>
              <td colspan="4" align="center" name="<%=MemoInterfaceKey.CONTROL_VIEW_MEMO_NAME%>" ><b><%=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_VIEW_MEMO_NAME)%></b></td>
              <%}%>
              </TR>
              <tr class="TableHeader">
              <td>DCM Name</td>
              <td>Commission Value</td>
              <%//<td>tddelete DCM From Memotd</td> %>
              </tr>
              
              <%
              for(int i=0;i<memoMembers.size();i++)
              {
              MemoMembersModel member=(MemoMembersModel)memoMembers.get(i);
              setZeroForNegative=true;
              if(member.getScmCommissionValue()<0){
            	  setZeroForNegative=true;
              }
              
              %>
              <tr>
              <td><%=member.getDcmName()%></td>
              <td><%=member.getScmCommissionValue()%></td>
              <%
              //if(state_name.equalsIgnoreCase("READY")){
              %>
              <%//<td> %>
              <%//<input class="button" type="button" name="Delete Member" value="Delete Member"
              //onclick=/"javascript:submitDeleteMember('<%=member.getScm_id()/%/>','<%=member.getPayment_id()%/>')"> %>
              <%//</td> %>
              <%//}else{%>
              <%//<td></td> %>
              <%//}%>
                          
              
              </tr>
              <%}%>
              
              </table>
            
                     
       </form>
    </body>
</html>