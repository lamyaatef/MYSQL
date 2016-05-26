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
        String pageHeader="Memo History";
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String action=  (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
        Vector memoTypes=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_MEMO_TYPES);        
        Vector memoStates=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_MEMO_STATES);        
        Vector paymentTypes=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES);
        Vector channels=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_DCM_CHANNELS);
        Vector subChannel=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS);
        String statusId=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_SELECT_STATE_ID);
        String paymentTypeId=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID);
        String specificChannelId=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID);
        if(specificChannelId==null||specificChannelId.equals(""))
        	specificChannelId="-1";
        String collectionChannelId=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_SELECT_COLLECTION_CHANNEL_ID);
        if(collectionChannelId==null||collectionChannelId.equals(""))
        	collectionChannelId="-1";
        
%>
<script language="JavaScript">

function submitSearch()
{	

//var ff=document.getElementById('<%=MemoInterfaceKey.CONTROL_SELECT_COLLECTION_CHANNEL_ID%>').value;
//alert("collection"+ff);
//var f=document.getElementById('<%=MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID%>').value;
//alert("specific"+f);
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_SEARCH_MEMO_HISTORY%>";
document.<%=formName%>.submit();

}

function submitViewMemo(memo_id,memo_name,state_name)
{

document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memo_id;
document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_NAME%>.value = memo_name;
document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME%>.value = state_name;

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
//alert("remember it is for Specific memos only");
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
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID%>" value="-1">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>" value="-1">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_NAME%>" value="-1">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME%>" value="-1">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_PAYMENT_METHOD%>" value="-1">
           <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_APPLIACTION_CONTEXT%>" value="<%= realPath%>">
            
            
                        
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableHeader>
              <td colspan="4" align="center"><b>Search</b></td>
              </TR>
              
              <tr>
              <td>Memo Name</td><td><input type="text" name="<%=MemoInterfaceKey.CONTROL_MEMO_NAME%>" id="<%=MemoInterfaceKey.CONTROL_MEMO_NAME%>"></td>
              
              <td>Status</td>
              <td>
              <select name="<%=MemoInterfaceKey.CONTROL_SELECT_STATE_ID%>" id="<%=MemoInterfaceKey.CONTROL_SELECT_STATE_ID%>">
              <option value=""></option>
              <%
              if(memoStates!=null||memoStates.size()!=0){
              for(int i=0;i<memoStates.size();i++){
              MemoStateModel state=(MemoStateModel) memoStates.get(i);
              if(state.getState().equals("DELETED"))
            	  continue;
              %>
              <option  value="<%=state.getId()%>"><%=state.getState()%></option>
              <%}}%>
              </select>
              </td>
              </tr>
              
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
              <option value=""></option>
              <%if(memoTypes!=null||memoTypes.size()!=0)
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
             
             <table  cellspacing="2" cellpadding="1" border="0" width="100%">
                <tr>
                    <td align=center>
                        <input class=button type="button" name="search" value="Search"
                               onclick="javascript:submitSearch()">
                    </td>
               
                </tr>
            </table>
            <%if(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_MEMO_HISTORY)){
            	Vector memos=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_ALL_MEMOS);  
            	System.out.println("memos size: "+memos.size());
            	
            %>
            <%if(memos!=null||memos.size()!=0){ 
           
           %>
           <br>
            	<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            	
              <tr class="TableHeader">
              <td>Memo Name</td>
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
              <td>Assigned For period</td>
              <td>Delay Reason</td>
              <td>Edit</td>
              </tr>
            	<% for(int i=0;i<memos.size();i++)
                {
            		MemoModel memo=(MemoModel)memos.get(i); %>
              
              
              <tr>
              <td><%=memo.getName() %></td>
              
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
                <%if(Integer.valueOf(memo.getPeriodId())!=null&&memo.getPeriodId()==1){ %>
                <td>Weekly</td>
                <%}else if(Integer.valueOf(memo.getPeriodId())!=null&&memo.getPeriodId()==2){ %>
                <td>Monthly</td>
                <%}else if(Integer.valueOf(memo.getPeriodId())!=null&&memo.getPeriodId()==3){ %>
                <td>Quarterly</td>
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
                
                <td><input class=button type="button" name="Edit" value="Edit"
                               onclick="javascript:submitEdit('<%=memo.getId() %>')"></td>
                </tr>
                
                           	
                <%} %>
                
              
            	</table>
            <%}%>
            
            <%} %>

             </form>
    </body>
</html>