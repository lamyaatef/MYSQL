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
String pageHeader="";
HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String userID =(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
String action=(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
Vector closedPayments=null;
Vector payment=null;
String paymentId="-1";
Vector paymentTypes=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES);

if(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_FROZEN_CLOSED_PAYMENTS)||action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_FROZEN_CLOSED_PAYMENTS)||action.equalsIgnoreCase(MemoInterfaceKey.ACTION_FREEEZE_PAYMENT)){
	pageHeader="Frozen Status Of Closed Payments";
	closedPayments=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_CLOSED_PAYMENTS);
	System.out.println("payments size: "+closedPayments);
}else if(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_FROZEN_PAYMENT_CONTENTS)||action.equalsIgnoreCase(MemoInterfaceKey.ACTION_FREEEZE_DCM)){
	pageHeader="Frozen Status Of Payment Contents";
	payment=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_PAYMENT_CONTENTS);
	paymentId=(String)dataHashMap.get(PaymentInterfaceKey.CONTROL_PAYMENT_ID);
}

	                      
%>
<script language="JavaScript">
function submitSearch()
{
	document.getElementById('<%=InterfaceKey.HASHMAP_KEY_ACTION%>').value='<%=MemoInterfaceKey.ACTION_SEARCH_FROZEN_CLOSED_PAYMENTS%>';
	document.<%=formName%>.submit();
}
function submitViewPayment(paymentId,paymentName)
{
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_ID%>.value = paymentId;
	document.<%=formName%>.<%=PaymentInterfaceKey.CONTROL_HIDDEN_PAYMENT_NAME%>.value = paymentName;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_VIEW_FROZEN_PAYMENT_CONTENTS%>";
	document.<%=formName%>.submit();
}
function drawCalender(argOrder,argValue)
{
    document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(memo_mgt_form."+argOrder+",'mm/dd/yyyy','Choose date')\">");
    document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
}
function submitExportAsExcel(paymentId) {
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_ID%>.value = paymentId;
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_FROZEN_STATUS_ID%>.value = 1;
	
	document.getElementById('<%=InterfaceKey.HASHMAP_KEY_ACTION%>').value='<%=MemoInterfaceKey.ACTION_EXPORT_DCM_PAYMENT_MEMBERS_EXCEL%>';
	document.<%=formName%>.submit();
}
function submitFreezePayment(paymentId,statusId){
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_ID%>.value = paymentId;
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_FROZEN_STATUS_ID%>.value = statusId;
	document.getElementById('<%=InterfaceKey.HASHMAP_KEY_ACTION%>').value='<%=MemoInterfaceKey.ACTION_FREEEZE_PAYMENT%>';
	document.<%=formName%>.submit();
}
function submitFreezeDCM(pId,pName,dId,sId){
	
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_ID%>.value = pId;
	
	document.<%=formName%>.<%=PaymentInterfaceKey.CONTROL_HIDDEN_PAYMENT_NAME%>.value = pName;
	
	document.<%=formName%>.<%=PaymentInterfaceKey.CONTROL_HIDDEN_SCM_PAYMENT_ID%>.value = dId;
	
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_FROZEN_STATUS_ID%>.value = sId;
	
	document.getElementById('<%=InterfaceKey.HASHMAP_KEY_ACTION%>').value='<%=MemoInterfaceKey.ACTION_FREEEZE_DCM%>';
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

        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
            <input type="hidden" name="<%=PaymentInterfaceKey.HIDDEN_PAYMENT_ID%>" value="<%=paymentId%>">
            <input type="hidden" name="<%=PaymentInterfaceKey.HIDDEN_PAYMENT_FROZEN_STATUS_ID%>" value="-1">
            <input type="hidden" name="<%=PaymentInterfaceKey.CONTROL_HIDDEN_SCM_PAYMENT_ID%>" value="-1">            
            <input type="hidden" name="<%=PaymentInterfaceKey.CONTROL_HIDDEN_PAYMENT_NAME%>" value="-1">
            <%if(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_FROZEN_CLOSED_PAYMENTS)||action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_FROZEN_CLOSED_PAYMENTS)||action.equalsIgnoreCase(MemoInterfaceKey.ACTION_FREEEZE_PAYMENT)){ %>
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            
              <tr class="TableHeader">
              <td colspan="4" align="center"><b>Search</b></td>
              </tr>
              <tr>
              <td>Payment Name</td>
              <td ><input type="text" name="<%=PaymentInterfaceKey.CONTROL_PAYMENT_NAME%>" id="<%=PaymentInterfaceKey.CONTROL_PAYMENT_NAME%>"></td>
              <td >Status</td>
              <td>
              <select name="<%=PaymentInterfaceKey.CONTROL_SELECT_STATUS %>" id="<%=PaymentInterfaceKey.CONTROL_SELECT_STATUS %>">
              <option value=""></option>
              <option value="FROZEN">Frozen</option>
              <option value="NOT FROZEN">Not Frozen</option>
              </select>
              </td>            
              </tr>
              
              <tr>
              <td >Payment Type</td>
              <td>
              <select name="<%=PaymentInterfaceKey.CONTROL_SELECT_TYPE%>" id="<%=PaymentInterfaceKey.CONTROL_SELECT_TYPE%>">
           	<option value=""></option>
            <%
             for(int k=0;k<paymentTypes.size();k++){
            	 PaymentTypeModel c=(PaymentTypeModel) paymentTypes.get(k);
            %>
             <option value="<%=c.getPaymentTypeId() %>"><%=c.getPaymentTypeName() %></option>
             <%}%>
             </select>
              </td>
              <td colspan="2">
              </tr>
              
              <tr>
              <td>Creation From Date</td>
              <td>
              	<script>
                  drawCalender('<%=MemoInterfaceKey.CONTROL_INSERTED_START_DATE%>','Start_Date');
               	</script>
             </td>
              <td>Creation To Date</td>
              <td>
              	<script>
                  drawCalender('<%=MemoInterfaceKey.CONTROL_INSERTED_END_DATE%>','End_Date');
               	</script>
              </td>
              </tr>
              
              
              <tr>
              <td>Add to Accounting Module From Date</td>
              <td>
              	<script>
                  drawCalender('<%=MemoInterfaceKey.CONTROL_ADDING_TO_MODULE_START_DATE%>','Start_Date');
               	</script>
             </td>
              <td>Add to Accounting Module To Date</td>
              <td>
              	<script>
                  drawCalender('<%=MemoInterfaceKey.CONTROL_ADDING_TO_MODULE__END_DATE%>','End_Date');
               	</script>
              </td>
              </tr>
              
              
              
              <tr>              
              <td>DCM Name</td>
              <td><input type="text" name="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME%>" id="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME%>"></td>
              <td>DCM Code</td>
              <td><input type="text" name="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>" id="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>"></td>
              </tr>                
              </table>
              <br>
              <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="0">
              <tr>
              <td align="center" colspan="5">
            <input class="button" type="button" name="search" value="Search"
                               onclick="javascript:submitSearch()">             
                           
            </td>
            </tr>
              </table>
              <%if(closedPayments.size()!=0){ %>
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
              <tr class="TableHeader">
              <td>Payment Name</td>
              <td>Commission Value</td>
              <td>Start Date</td>
              <td>End Date</td>
              <td>Freeze Status</td>
              <td>Freeze Action</td>
              </tr>
              
              <%
              for(int i=0;i<closedPayments.size();i++)
              {
              MemoMembersModel member=(MemoMembersModel)closedPayments.get(i);
              
              %>
              <tr>
              <td name="<%=PaymentInterfaceKey.CONTROL_PAYMENT_ID%>" id="<%=PaymentInterfaceKey.CONTROL_PAYMENT_ID%>">
             <a href="javaScript:submitViewPayment('<%=member.getPaymentId()%>','<%=member.getPaymetName() %>')" ><%=member.getPaymetName()%></a>
             </td>
              <td><%=member.getScmCommissionValue()%></td>
              <td><%=member.getStartDate()%></td>
              <td><%=member.getEndDate()%></td>
              <td><%=member.getFrozenStatus() %></td>
              <%if(member.getFrozenStatus()!=null && member.getFrozenStatus().compareToIgnoreCase("NOT Frozen")==0){ %>
              <td>
              <input class="button" type="button" name="Freeze" value="Freeze"
                               onclick="javascript:submitFreezePayment('<%=member.getPaymentId() %>','0')">
              </td>
                                  
              <%}else{ %>
              <td>
              <input class="button" type="button" name="Un Freeze" value="Un Freeze"
                               onclick="javascript:submitFreezePayment('<%=member.getPaymentId() %>','1')">
              </td>
              
              <%} %>
              </tr>
              <%}%>
              
              </table>
              <%} %>
              <%} %>
              <%
              if(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_FROZEN_PAYMENT_CONTENTS)||action.equalsIgnoreCase(MemoInterfaceKey.ACTION_FREEEZE_DCM)){
            	  String paymentName=(String)dataHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAYMENT_NAME);
            	  
            	  if(payment!=null||payment.size()!=0){
            		  System.out.println("payments sisze: "+payment.size());
            		  
              %>
              <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
              <tr class="TableHeader">
              <td colspan="4" align="center"><%=paymentName%></td>
              </tr>
              <tr class="TableHeader">
              <td>DCM NAME</td>           
              <td>Commission</td>
              <td>Freeze Status</td>
              <td>Freeze Action</td>
              
              </tr>
              
              <%
              for(int i=0;i<payment.size();i++){
            	  MemoMembersModel member=(MemoMembersModel)payment.get(i);
            	  
              %>
              <tr>
              <td><%=member.getDcmName() %></td>
              <td><%=member.getScmCommissionValue()%></td>
              <td><%=member.getFrozenStatus() %></td>
              <%if(member.getFrozenStatus()==null || member.getFrozenStatus().compareToIgnoreCase("NOT Frozen")==0){ %>
              <td>
              <input class="button" type="button" name="Freeze" value="Freeze"
                               onclick="javascript:submitFreezeDCM('<%=member.getPaymentId() %>','<%=member.getPaymetName() %>','<%=member.getScmId() %>','0')">
              </td>
                                  
              <%}else{ %>
              <td>
              <input class="button" type="button" name="Un Freeze" value="Un Freeze"
                               onclick="javascript:submitFreezeDCM('<%=member.getPaymentId() %>','<%=member.getPaymetName() %>','<%=member.getScmId() %>','1')">
              </td>
              
              <%} %>
              </tr>
              <%} %>
              
              </TABLE>
              
              <table width="100%">
              <tr>
              <td align="center" colspan="4">
            <input class="button" type="button" name="export" value="Export As Excel"
                               onclick="javascript:submitExportAsExcel('<%=paymentId%>')">
             
                           
            </td>
              </tr>
              
              </table>
              
            	  
              <%}}
              %>
              </form>
    </body>
</html>