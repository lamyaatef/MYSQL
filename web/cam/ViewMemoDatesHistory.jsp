<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="com.mobinil.sds.web.interfaces.cam.*"
         import=" com.mobinil.sds.core.system.cam.accrual.model.*"
         import ="com.mobinil.sds.core.system.cam.accrual.dao.*"
         import ="com.mobinil.sds.web.interfaces.InterfaceKey"
         import ="com.mobinil.sds.core.system.cam.payment.model.*"
         import="com.mobinil.sds.core.system.cam.memo.model.*"  
         
         
         %>
<%
        String appName = request.getContextPath();
		String realPath = request.getRealPath("/cam/camMemosFiles/");
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String formName = "memo_mgt_form";
        String page_header="Edit Memo History";
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String action=  (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
        int memoId=0;
        int assignedMemoPeriodId=0;
        String periodId=(String)dataHashMap.get(MemoInterfaceKey.MEMO_PERIOD_ID);
        Vector delayReasons=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_MEMO_REASONS);
        System.out.println("delayreasons size: "+delayReasons.size());
        Vector memoDelayReasons=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_EVERY_MEMO_REASONS);
        
       
        
%>
<script language="JavaScript">



function submitViewMemo(memoId,memoName,stateName)
{

document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_NAME%>.value = memoName;
document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME%>.value = stateName;

document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_VIEW_MEMO%>";
document.<%=formName%>.submit();
}



function drawCalender(argOrder,argValue)
{
	document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(memo_mgt_form."+argOrder+",'mm/dd/yyyy','Choose date')\">");
    document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
}
function submitUpdate(memoId,periodId,assignedPeriodId)
{
	var x=document.<%=formName%>.<%=MemoInterfaceKey.CONTROL_COMMISSION_CALC_DELAY%>.value;
	var anum=/(^\d+$)|(^\d+\.\d+$)/;
	if (anum.test(x))
	testresult=true;
	else{
	alert("Please input a valid number in Commission Calculation Delay!");
	return;
	}

	var x=document.<%=formName%>.<%=MemoInterfaceKey.CONTROL_PAYMENT_DELAY%>.value;
	var anum=/(^\d+$)|(^\d+\.\d+$)/;
	if (anum.test(x))
	testresult=true;
	else{
	alert("Please input a valid number in Payment Delay!");
	return;
	} 
	 
	document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_PERIOD_ID%>.value = periodId;
	document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
	document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_ASSIGNED_PERIOD_ID%>.value = assignedPeriodId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_UPDATE_MEMO_HISTORY%>";
	document.<%=formName%>.submit();
}
</script>

<%@page import="java.text.DateFormatSymbols"%><html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL="STYLESHEET" TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        <SCRIPT language="JavaScript" src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/calendar.js"></SCRIPT>
        </head>
    <body>
        <center><h2> <%=page_header%></h2></center>

        
        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID%>" value="-1">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>" value="-1">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_NAME%>" value="-1">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME%>" value="-1">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_PAYMENT_METHOD%>" value="-1">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_PERIOD_ID%>" value="-1">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_ASSIGNED_PERIOD_ID%>" value="-1">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_APPLIACTION_CONTEXT%>" value="<%= realPath%>">
            
             
            <%
            	Vector memos=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_ALL_MEMOS);
            	
            	
            %>
            <%if(memos!=null||memos.size()!=0){ 
            	MemoModel memo=(MemoModel)memos.get(0);
            	memoId=memo.getId();
            	
            	
           %>
           <br>
            	<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            	
              <tr class="TableHeader">
              
              <td align="center" colspan="3"><%=memo.getName() %></td>
              </tr>
              <tr>
              
              <td>Send For Validation Date</td>
              <%if(memo.getSendForValidationDate()!=null){ %>
              <td><%=memo.getSendForValidationDate() %></td>
              <%}else{ %>
              <td></td>
              <%} %>
              <td>
              	<script>
                  drawCalender('<%=MemoInterfaceKey.CONTROL_SEND_FOR_VALIDATION_DATE%>','Send_For_Validation_Date');
               	</script>
             </td>
              </tr>
              
              <tr>
              <td>Approval Date</td>
             <%if(memo.getApprovalDate()!=null){ %>
              
               <td><%=memo.getApprovalDate() %></td>
               <%}else{ %>
               <td></td>
               <%} %>
              <td>
              	<script>
                  drawCalender('<%=MemoInterfaceKey.CONTROL_APPROVAL_DATE%>','Approval_Date');
               	</script>
             </td>
              </tr>
              
              <tr>
              <td>Sales Manager Approval Date</td>
              <%if(memo.getSalesManagerApprovalDate()!=null){ %>
               <td><%=memo.getSalesManagerApprovalDate() %></td>
               <%}else{ %>
               <td></td>
               <%} %>
              <td>
              	<script>
                  drawCalender('<%=MemoInterfaceKey.CONTROL_SALES_MANAGER_APPROVAL_DATE%>','Sales_Manager_Approval_Date');
               	</script>
             </td>
              </tr>
              
              
              <tr>
              <td>Sales Back Office Approval Date</td>
              <%if(memo.getSalesBackOfficeApprovalDate()!=null){ %>
               <td><%=memo.getSalesBackOfficeApprovalDate() %></td>
               <%}else{ %>
               <td></td>
               <%} %>
               <td>
              	<script>
                  drawCalender('<%=MemoInterfaceKey.CONTROL_SALES_BACK_OFFICE_APPROVAL_DATE%>','Sales_Back_Office_Approval_Date');
               	</script>
             </td>
              </tr>
              
              
              <tr>
              <td>Sales Directive Approval Date</td>
               <%if(memo.getSalesDirectiveApprovalDate()!=null){ %>
                <td><%=memo.getSalesDirectiveApprovalDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                <td>
              	<script>
                  drawCalender('<%=MemoInterfaceKey.CONTROL_SALES_DIRECTIVE_APPROVAL_DATE%>','Sales_Directive_Approval_Date');
               	</script>
             </td>
              </tr>
              
              
              <tr>
              <td>Finance Receive Date</td>
              <%if(memo.getFinanceReceiveDate()!=null){ %>
                <td><%=memo.getFinanceReceiveDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
              <td>
              	<script>
                  drawCalender('<%=MemoInterfaceKey.CONTROL_FINANCE_RECEIVE_DATE%>','Finance_Receive_Date');
               	</script>
             </td>
              </tr>
              
              
              <tr>
              <td>Payment Date</td>
              <%if(memo.getPaymentDate()!=null){ %>
                <td><%=memo.getPaymentDate() %></td>
                <%}else{ %>
                <td>nn</td>
                <%} %>
              <td>
              	<script>
                  drawCalender('<%=MemoInterfaceKey.CONTROL_PAYMENT_DATE%>','Payment_Date');
               	</script>
             </td>
              </tr>
              
              <tr>
              <td>Ready Commission Calculation Date</td>
              <%if(memo.getReadyCommClacDate()!=null){ %>
                <td><%=memo.getReadyCommClacDate() %></td>
                <%}else{ %>
                <td>nn</td>
                <%} %>
              <td>
              	<script>
                  drawCalender('<%=MemoInterfaceKey.CONTROL_COMMISSION_CALC_DATE%>','Commission_Calc_Date');
               	</script>
             </td>
              </tr>
              
              <tr>
              <td>Calculation Targeted Date</td>
              <%if(memo.getCalcTargetedDate()!=null){ %>
                <td><%=memo.getCalcTargetedDate() %></td>
                <%}else{ %>
                <td>nn</td>
                <%} %>
              <td>
              	<script>
                  drawCalender('<%=MemoInterfaceKey.CONTROL_CALC_TARGETED_DATE%>','Calc_Targeted_Date');
               	</script>
             </td>
              </tr>
              
              <tr>
              <td>Payment Targeted Date</td>
              <%if(memo.getPaymentTargetedDate()!=null){ %>
                <td><%=memo.getPaymentTargetedDate() %></td>
                <%}else{ %>
                <td>nn</td>
                <%} %>
              <td>
              	<script>
                  drawCalender('<%=MemoInterfaceKey.CONTROL_PAYMENT_TARGETED_DATE%>','Payment_Targeted_Date');
               	</script>
             </td>
              </tr>
              
              <tr>
              <td>Finished on</td>
              <%if(memo.getFinishedOn()!=null){ %>
                <td><%=memo.getFinishedOn()%></td>
                <%}else{ %>
                <td></td>
                <%} %>
              <td>
              	<script>
                  drawCalender('<%=MemoInterfaceKey.CONTROL_FINISHED_ON_DATE%>','Finished_On_Date');
               	</script>
             </td>
              </tr>
              
              <tr>
              <td>Commission Calculation Delay</td>
              <%if(memo.getCommCalcDelay()!=0){ %>
                <td><%=memo.getCommCalcDelay() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
              <td>
              	<input type="text" name="<%=MemoInterfaceKey.CONTROL_COMMISSION_CALC_DELAY %>" id="<%=MemoInterfaceKey.CONTROL_COMMISSION_CALC_DELAY %>">
             </td>
              </tr>
              
              <tr>
              <td>Payment Delay</td>
              <%if(memo.getPaymentDelay()!=0){ %>
                <td><%=memo.getPaymentDelay() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
              <td>
              	<input type="text" name="<%=MemoInterfaceKey.CONTROL_PAYMENT_DELAY %>" id="<%=MemoInterfaceKey.CONTROL_PAYMENT_DELAY %>">
             </td>
              </tr>
              
              <%if(Integer.parseInt(periodId)==1){
            	  
            	  int [] data=new int[2];
            	  data=(int[])dataHashMap.get(MemoInterfaceKey.MEMO_SETTINGS_ARRAY_DATA);
            	  assignedMemoPeriodId=data[0];
            	  %>
            	 <tr>
            	 <td>Period</td><td>Weekly</td>
            	 <td></td>
            	</tr>  
            	<tr>
            	<td>Week No.</td>
            	
            	<td >
            	<select name="weekNo" id="weekNo">
            	<%
            	String status_selected="";
            	for(int i=1;i<=52;i++){
            		if(data[0]==i)
            			status_selected="selected";
            		else
            			status_selected="";
            	%>
            	<option <%=status_selected %> value="<%=i %>"><%=i %></option>
            	<%} %>
            	</select>
            	</td>
            	<td></td>
            	</tr>
            	<tr>
            	<td>Day In Week</td>
            	<%if(data[1]==1){ %>
            	<td colspan="2">Sunday</td>
            	<%}else if(data[1]==2){ %>
            	<td colspan="2">Monday</td>
            	<%}else if(data[1]==3){ %>
            	<td colspan="2">Tuesday</td>
            	<%}else if(data[1]==4){ %>
            	<td colspan="2">Wednesday</td>
            	<%}else if(data[1]==5){ %>
            	<td colspan="2">Thrusday</td>
            	<%} %>
            	
            	</tr>
            	
            	  
              <%}else if(Integer.parseInt(periodId)==2){
            	  int [] data=new int[2];
            	  data=(int[])dataHashMap.get(MemoInterfaceKey.MEMO_SETTINGS_ARRAY_DATA);
            	  assignedMemoPeriodId=data[0];
            	%>
            	  <tr>
            	 <td>Period</td><td >Monthly</td>
            	 <td></td>
            	</tr>  
            	<tr>
            	<td>Month No.</td>
            	
            	<td >
            	<select name="monthNo" id="monthNo">
            	<%
            	String status_selected="";
            	DateFormatSymbols symbols = new DateFormatSymbols(); 
                String[] monthNames = symbols.getMonths(); 
            	for(int i=1;i<=12;i++){
            		if(data[0]==i)
            			status_selected="selected";
            		else
            			status_selected="";
            	%>
            	<option <%=status_selected %> value="<%=i %>"><%=monthNames[i-1] %></option>
            	<%} %>
            	</select>
            	</td>
            	<td></td>
            	</tr>
            	<tr>
            	<td>Day In Month</td>
            	<td ><%=data[1] %></td>
            	<td></td>
            	
	           	</tr>
              <%}else if(Integer.parseInt(periodId)==3){
            	  String [] qrtrData=new String[2];
            	  qrtrData=(String[])dataHashMap.get(MemoInterfaceKey.MEMO_SETTINGS_ARRAY_DATA);
            	  assignedMemoPeriodId=Integer.parseInt(qrtrData[0]);
            	%>  
            	<tr>
            	 <td>Period</td><td >Quarterly</td>
            	 <td></td>
            	</tr>  
            	<tr>
            	<td>Quarter No.</td>
            	<td >
            	<select name="qrtrNo" id="qrtrhNo">
            	<%
            	String status_selected="";
            	for(int i=1;i<=4;i++){
            		if(Integer.parseInt(qrtrData[0])==i)
            			status_selected="selected";
            		else
            			status_selected="";
            	%>
            	<option <%=status_selected %> value="<%=i %>"><%=i%></option>
            	<%} %>
            	
            	</select>
            	</td >
            	<td></td>
            	</tr>
            	<tr>
            	<td>Month In Quarter</td>
            	
            	<%
            	 DateFormatSymbols symbols = new DateFormatSymbols(); 
                String[] monthNames = symbols.getMonths(); 
               	%>        	
            	<td ><%=monthNames[Integer.parseInt(qrtrData[1])-1]%> </td>
            	<td></td>            	
            	
            	</tr>
            	<tr>
            	<td>Day Of Month</td>
            	<td ><%=qrtrData[2] %> </td>
            	<td></td>
            	</tr>
            	
             <% }
              %>
              <tr>
              <td>
              Delay Reasons
              </td>
              
              <%if(memoDelayReasons!=null||memoDelayReasons.size()!=0){
            	
              %>
              <td>
              <%for(int i=0;i<memoDelayReasons.size();i++){  
          		MemoReasonModel memoReason=(MemoReasonModel)memoDelayReasons.get(i); %>
              <%=memoReason.getReasonName()+","+"\n" %>
              <%} %>
              </td>
              <% }else{%>
              <td></td>
              <%} %>
              
              <td>
              <select name="<%=MemoInterfaceKey.CONTROL_SELECT_MEMO_REASONS %>" id="<%=MemoInterfaceKey.CONTROL_SELECT_MEMO_REASONS %>" multiple="multiple">
              <%if(delayReasons!=null&&delayReasons.size()!=0){
            	  for(int i=0;i<delayReasons.size();i++){
            		  MemoReasonModel memoReason=(MemoReasonModel)delayReasons.get(i);
            	  %>
              <option value="<%=memoReason.getReasonId() %>"><%=memoReason.getReasonName()%></option>
              <%}} %>
               </select>
              </td>
              </tr>
              
            	</table>
            <%}%>
            <table cellspacing="2" cellpadding="1" border="0" width="100%">
            <tr>
                    <td align="center" colspan="3">
                        <input class=button type="button" name="Update" value="Update"
                               onclick="javascript:submitUpdate('<%=memoId %>','<%=periodId %>',<%=assignedMemoPeriodId %>)">
                    </td>
               
                </tr>  
            </table>
        

             </form>
    </body>
</html>