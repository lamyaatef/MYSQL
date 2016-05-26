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
        String pageHeader="Memo Monitoring";
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String action=  (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);       
        Vector channels=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_DCM_CHANNELS);
        Vector subChannel=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS);
        
        String channelId=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID) ;
        String subChannelId=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SUB_CHANNEL_ID) ;
        String fromDate=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_START_DATE) ;
        String toDate=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_END_DATE) ;
        
        
        
%>
<script language="JavaScript">

function submitSearch()
{	
if(document.getElementById("<%=MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID%>").value == "" && document.getElementById("<%=MemoInterfaceKey.CONTROL_SELECT_SUB_CHANNEL_ID%>").value == ""
		&& document.getElementById("<%=MemoInterfaceKey.CONTROL_INSERTED_START_DATE%>").value == "Start_Date" &&
		document.getElementById("<%=MemoInterfaceKey.CONTROL_INSERTED_END_DATE%>").value == "End_Date")
	{
	   alert("you have to select search criteria");
	   return;
	}
//var ff=document.getElementById('<%=MemoInterfaceKey.CONTROL_SELECT_COLLECTION_CHANNEL_ID%>').value;
//alert("collection"+ff);
//var f=document.getElementById('<%=MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID%>').value;
//alert("specific"+f);
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_SEARCH_MEMO_MONITOR%>";
document.<%=formName%>.submit();

}

function submitViewMemo(memoId,memoName,stateName)
{

document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_NAME%>.value = memoName;
document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME%>.value = stateName;

document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_VIEW_MEMO%>";
document.<%=formName%>.submit();
}

function toggleSpecific()
{

document.getElementById("1").style.display='';
document.getElementById("2").style.display='';
//document.getElementById("3").style.display='none';
document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID%>.value= "2";

}

function toggleCollection()
{
 //document.getElementById("3").style.display='';
 document.getElementById("1").style.display='none';
 document.getElementById("2").style.display='none';
document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID%>.value= "1";
}
function toggleNone()
{
 //document.getElementById("3").style.display='';
 document.getElementById("1").style.display='none';
 document.getElementById("2").style.display='none';
document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID%>.value= "-1";
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
function drawCalender(argOrder,argValue)
{
	document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(memo_mgt_form."+argOrder+",'mm/dd/yyyy','Choose date')\">");
    document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
}
function submitEdit(memoId)
{
	document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_VIEW_UPDATE_MEMO_HISTORY%>";
	document.<%=formName%>.submit();
}
function submitExportAsExcel()
{
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_EXPORT_MEMO_MONITOR_TO_EXCEL%>";
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
        <center><h2> <%=pageHeader%></h2></center>

        
        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_CHANNEL_ID%>" value="<%=channelId%>">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_SUB_CHANNEL_ID%>" value="<%=subChannelId%>">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_FROM_DATE%>" value="<%=fromDate%>">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_TO_DATE%>" value="<%=toDate%>">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_APPLIACTION_CONTEXT%>" value="<%= realPath%>">
            
            
                        
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableHeader>
              <td colspan="4" align="center"><b>Search</b></td>
              </TR>
              
              
              
              <tr>
              <td >Channel</td>
              <td >
              <select name="<%=MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID%>" id="<%=MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID%>">
              <option value=""></option>
              <%
              if(channels!=null||channels.size()!=0){
              for(int i=0;i<channels.size();i++){
              ChannelModel channel=(ChannelModel)channels.get(i);
              
              %>
              <option  value="<%=channel.getChannelId()%>"><%=channel.getChannelName()%></option>
              <%}}%> 
              </select>  
              </td>
              <td>Sub Channel</td>
              <td>
              <select name="<%=MemoInterfaceKey.CONTROL_SELECT_SUB_CHANNEL_ID%>" id="<%=MemoInterfaceKey.CONTROL_SELECT_SUB_CHANNEL_ID%>">
              <option value=""></option>
              <%
              if(subChannel!=null||subChannel.size()!=0){
              for(int i=0;i<subChannel.size();i++){
              SubChannelModel subchannel=(SubChannelModel)subChannel.get(i);
              
              %>
              <option  value="<%=subchannel.getSubChannelId()%>"><%=subchannel.getCriteriaName()%></option>
              <%}}%> 
              </select>
              </td>
              
              </tr>
              <tr>
              <td>From Date</td>
              <td>
              	<script>
                  drawCalender('<%=MemoInterfaceKey.CONTROL_INSERTED_START_DATE%>','Start_Date');
               	</script>
             </td>
              <td>To Date</td>
              <td>
              	<script>
                  drawCalender('<%=MemoInterfaceKey.CONTROL_INSERTED_END_DATE%>','End_Date');
               	</script>
              </td>
              </tr>                    
             </TABLE>
             
             <table  cellspacing="2" cellpadding="1" border="0" width="100%">
                <tr>
                    <td align=center>
                        <input class=button type="button" name="search" value="Search"
                               onclick="javascript:submitSearch()">
                    </td>
               
                </tr>
            </table>
            
            
            
                        
            
            <%if(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_MEMO_MONITOR)){
            	Vector qMemos=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_ALL_QUARTERLY_MEMOS); 
            	Vector mMemos=(Vector) dataHashMap.get(MemoInterfaceKey.VECTOR_ALL_MONTHLY_MEMOS);
            	Vector wMemos=(Vector) dataHashMap.get(MemoInterfaceKey.VECTOR_ALL_WEEKLY_MEMOS);
            %>
            <%if(qMemos!=null||qMemos.size()!=0){ 
           
           %>
           <br>
            	<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            	<tr class="TableHeader">
            	<td colspan="18" align="center">Quarterly Memos</td>
            	</tr>
            	
              <tr class="TableHeader">
              <td>Memo Name</td>
              <td>State</td>
              <td>Send For Validation Date</td>              
              <td>Approval Date</td>
              <td>Sales Manager Approval Date</td>
              <td>Sales Back Office Approval Date</td>
              <td>Sales Directive Approval Date</td>
              <td>Finance Receive Date</td>
              <td>Payment Date</td>
              <td>Ready Commission Calculation Date</td>
              <td>Calculation Targeted Date</td>
              <td>Payment Targeted Date</td>
              <td>Finished On</td>
              <td>Commission Calculation Delay</td>
              <td>Payment Delay</td>
              <td>Delay Reason</td>
              <td>Quarter Number</td>
              <td>Date in Quarter</td>
              
              </tr>
            	<% for(int i=0;i<qMemos.size();i++)
                {
                	MemoModel memo=(MemoModel)qMemos.get(i); %>
              
              
              <tr>
              <td><%=memo.getName() %></td>
              <td><%=memo.getState() %></td>
              
              <%if(memo.getSendForValidationDate()!=null){ %>
              <td><%=memo.getSendForValidationDate() %></td>
              <%}else{ %>
              <td></td>
              <%} %>
              
              <%if(memo.getApprovalDate()!=null){ %>
              
               <td><%=memo.getApprovalDate() %></td>
               <%}else{ %>
               <td></td>
               <%} %>
               
               <%if(memo.getSalesManagerApprovalDate()!=null){ %>
               <td><%=memo.getSalesManagerApprovalDate() %></td>
               <%}else{ %>
               <td></td>
               <%} %>
               
               <%if(memo.getSalesBackOfficeApprovalDate()!=null){ %>
               <td><%=memo.getSalesBackOfficeApprovalDate() %></td>
               <%}else{ %>
               <td></td>
               <%} %>
               
               <%if(memo.getSalesDirectiveApprovalDate()!=null){ %>
                <td><%=memo.getSalesDirectiveApprovalDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                
               <%if(memo.getFinanceReceiveDate()!=null){ %>
                <td><%=memo.getFinanceReceiveDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <%if(memo.getPaymentDate()!=null){ %>
                <td><%=memo.getPaymentDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                <%if(memo.getReadyCommClacDate()!=null){ %>
                <td><%=memo.getReadyCommClacDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <%if(memo.getCalcTargetedDate()!=null){ %>
                <td><%=memo.getCalcTargetedDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <%if(memo.getPaymentTargetedDate()!=null){ %>
                <td><%=memo.getPaymentTargetedDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <%if(memo.getFinishedOn()!=null){ %>
                <td><%=memo.getFinishedOn() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <%if(memo.getCommCalcDelay()!=0){ %>
                <td><%=memo.getCommCalcDelay() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <%if(memo.getPaymentDelay()!=0){ %>
                <td><%=memo.getPaymentDelay() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <%Vector memoReasons=memo.getDelayReason();
                if(memoReasons!=null&&memoReasons.size()!=0){
                	
                %>
                <td>
                <%for(int j=0;j<memoReasons.size();j++){
            		MemoReasonModel reason=(MemoReasonModel)memoReasons.get(j); %>
                <%=reason.getReasonName() %>
                <%=",\n" %>
                <%} %>
                </td>
                <%}else{ %>
                <td></td>
                <%} %>
                <%if(memo.getQuarterId()==1){ %>
                <td>Quarter One</td>
                <%}else if(memo.getQuarterId()==2){ %>
                <td>Quarter Two</td>
                <%}else if(memo.getQuarterId()==3){ %>
                <td>Quarter Three</td>
                <%}else if(memo.getQuarterId()==4){ %>
                <td>Quarter Four</td>
                <%} %>
                <%DateFormatSymbols symbols = new DateFormatSymbols(); 
                String[] monthNames = symbols.getMonths(); 
                String qdate="";
                if(memo.getMonthInQuarter()>0)
                qdate=memo.getDayInMonthInQuarter()+"-"+monthNames[memo.getMonthInQuarter()-1];
                else
                	qdate=memo.getDayInMonthInQuarter()+"-"+monthNames[memo.getMonthInQuarter()];
                %>
                <td><%=qdate %></td>
                
                </tr>
                
                           	
                <%} %>
                
              
            	</table>
            <%}%>
            
            
            
            <%if(mMemos!=null||mMemos.size()!=0){ 
           
           %>
           <br>
            	<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            	<tr class="TableHeader">
            	<td colspan="18" align="center">Monthly Memos</td>
            	</tr>
            	
              <tr class="TableHeader">
              <td>Memo Name</td>
              <td>State</td>
              <td>Send For Validation Date</td>              
              <td>Approval Date</td>
              <td>Sales Manager Approval Date</td>
              <td>Sales Back Office Approval Date</td>
              <td>Sales Directive Approval Date</td>
              <td>Finance Receive Date</td>
              <td>Payment Date</td>
              <td>Ready Commission Calculation Date</td>
              <td>Calculation Targeted Date</td>
              <td>Payment Targeted Date</td>
              <td>Finished On</td>
              <td>Commission Calculation Delay</td>
              <td>Payment Delay</td>
              <td>Delay Reason</td>
              <td>Month</td>
              <td>Date in Month</td>
              
              </tr>
            	<% for(int i=0;i<mMemos.size();i++)
                {
                	MemoModel memo=(MemoModel)mMemos.get(i); %>
              
              
              <tr>
              <td><%=memo.getName() %></td>
              <td><%=memo.getState() %></td>
              <%if(memo.getSendForValidationDate()!=null){ %>
              <td><%=memo.getSendForValidationDate() %></td>
              <%}else{ %>
              <td></td>
              <%} %>
              <%if(memo.getApprovalDate()!=null){ %>
              
               <td><%=memo.getApprovalDate() %></td>
               <%}else{ %>
               <td></td>
               <%} %>
               <%if(memo.getSalesManagerApprovalDate()!=null){ %>
               <td><%=memo.getSalesManagerApprovalDate() %></td>
               <%}else{ %>
               <td></td>
               <%} %>
               <%if(memo.getSalesBackOfficeApprovalDate()!=null){ %>
               <td><%=memo.getSalesBackOfficeApprovalDate() %></td>
               <%}else{ %>
               <td></td>
               <%} %>
               <%if(memo.getSalesDirectiveApprovalDate()!=null){ %>
                <td><%=memo.getSalesDirectiveApprovalDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
               <%if(memo.getFinanceReceiveDate()!=null){ %>
                <td><%=memo.getFinanceReceiveDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                <%if(memo.getPaymentDate()!=null){ %>
                <td><%=memo.getPaymentDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                <%if(memo.getReadyCommClacDate()!=null){ %>
                <td><%=memo.getReadyCommClacDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <%if(memo.getCalcTargetedDate()!=null){ %>
                <td><%=memo.getCalcTargetedDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <%if(memo.getPaymentTargetedDate()!=null){ %>
                <td><%=memo.getPaymentTargetedDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <%if(memo.getFinishedOn()!=null){ %>
                <td><%=memo.getFinishedOn() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <%if(memo.getCommCalcDelay()!=0){ %>
                <td><%=memo.getCommCalcDelay() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <%if(memo.getPaymentDelay()!=0){ %>
                <td><%=memo.getPaymentDelay() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                <%Vector memoReasons=memo.getDelayReason();
                if(memoReasons!=null&&memoReasons.size()!=0){
                	
                %>
                <td>
                <%for(int j=0;j<memoReasons.size();j++){
            		MemoReasonModel reason=(MemoReasonModel)memoReasons.get(j); %>
                <%=reason.getReasonName() %>
                <%=",\n" %>
                <%} %>
                </td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <%DateFormatSymbols symbols = new DateFormatSymbols(); 
                String[] monthNames = symbols.getMonths(); 
                System.out.println("month is: "+monthNames[memo.getMonthId()-1]);
                System.out.println("day in month is: "+memo.getDayInMonth());
                 %>
                <td><%=monthNames[memo.getMonthId()-1] %></td>
                <td><%=memo.getDayInMonth() %></td>
                
                </tr>
                
                           	
                <%} %>
                
              
            	</table>
            <%}%>
            
            
            
            <%if(wMemos!=null||wMemos.size()!=0){ 
           
           %>
           <br>
            	<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            	<tr class="TableHeader">
            	<td colspan="18" align="center">Weekly Memos</td>
            	</tr>
            	
              <tr class="TableHeader">
              <td>Memo Name</td>
              <td>State</td>
              <td>Send For Validation Date</td>              
              <td>Approval Date</td>
              <td>Sales Manager Approval Date</td>
              <td>Sales Back Office Approval Date</td>
              <td>Sales Directive Approval Date</td>
              <td>Finance Receive Date</td>
              <td>Payment Date</td>
              <td>Ready Commission Calculation Date</td>
              <td>Calculation Targeted Date</td>
              <td>Payment Targeted Date</td>
              <td>Finished On</td>
              <td>Commission Calculation Delay</td>
              <td>Payment Delay</td>
              <td>Delay Reason</td>
              <td>Week Number</td>
              <td>Day In Week</td>
              
              </tr>
            	<% for(int i=0;i<wMemos.size();i++)
                {
                	MemoModel memo=(MemoModel)wMemos.get(i); %>
              
              
              <tr>
              <td><%=memo.getName() %></td>
              <td><%=memo.getState() %></td>
              <%if(memo.getSendForValidationDate()!=null){ %>
              <td><%=memo.getSendForValidationDate() %></td>
              <%}else{ %>
              <td></td>
              <%} %>
              <%if(memo.getApprovalDate()!=null){ %>
              
               <td><%=memo.getApprovalDate() %></td>
               <%}else{ %>
               <td></td>
               <%} %>
               <%if(memo.getSalesManagerApprovalDate()!=null){ %>
               <td><%=memo.getSalesManagerApprovalDate() %></td>
               <%}else{ %>
               <td></td>
               <%} %>
               <%if(memo.getSalesBackOfficeApprovalDate()!=null){ %>
               <td><%=memo.getSalesBackOfficeApprovalDate() %></td>
               <%}else{ %>
               <td></td>
               <%} %>
               <%if(memo.getSalesDirectiveApprovalDate()!=null){ %>
                <td><%=memo.getSalesDirectiveApprovalDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
               <%if(memo.getFinanceReceiveDate()!=null){ %>
                <td><%=memo.getFinanceReceiveDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                <%if(memo.getPaymentDate()!=null){ %>
                <td><%=memo.getPaymentDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                <%if(memo.getReadyCommClacDate()!=null){ %>
                <td><%=memo.getReadyCommClacDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <%if(memo.getCalcTargetedDate()!=null){ %>
                <td><%=memo.getCalcTargetedDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <%if(memo.getPaymentTargetedDate()!=null){ %>
                <td><%=memo.getPaymentTargetedDate() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <%if(memo.getFinishedOn()!=null){ %>
                <td><%=memo.getFinishedOn() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <%if(memo.getCommCalcDelay()!=0){ %>
                <td><%=memo.getCommCalcDelay() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <%if(memo.getPaymentDelay()!=0){ %>
                <td><%=memo.getPaymentDelay() %></td>
                <%}else{ %>
                <td></td>
                <%} %>
                <%Vector memoReasons=memo.getDelayReason();
                if(memoReasons!=null&&memoReasons.size()!=0){
                	
                %>
                <td>
                <%for(int j=0;j<memoReasons.size();j++){
            		MemoReasonModel reason=(MemoReasonModel)memoReasons.get(j); %>
                <%=reason.getReasonName() %>
                <%=",\n" %>
                <%} %>
                </td>
                <%}else{ %>
                <td></td>
                <%} %>
                
                <td><%=memo.getWeekId() %></td>
                <%if(memo.getDayInWeek()==1){ %>
            	<td >Sunday</td>
            	<%}else if(memo.getDayInWeek()==2){ %>
            	<td>Monday</td>
            	<%}else if(memo.getDayInWeek()==3){ %>
            	<td>Tuesday</td>
            	<%}else if(memo.getDayInWeek()==4){ %>
            	<td >Wednesday</td>
            	<%}else if(memo.getDayInWeek()==5){ %>
            	<td>Thrusday</td>
            	<%} %>
                
                
                </tr>
                
                           	
                <%} %>
                
              
            	</table>
            <%}%>
            
            <br>
            <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=0>
            <tr>
            <td align="center" colspan="11">
            <input class="button" type="button" name="export" value="Export As Excel"
                               onclick="javascript:submitExportAsExcel()">
             
                           
            </td>
            </tr>
            </table>
            
            
            
            
            
            <%} %>

             </form>
    </body>
</html>