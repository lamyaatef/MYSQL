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
        String pageHeader="Memo Management";
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
        Vector  paymentMethods=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD);
        String specificChannelId=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID);
        if(specificChannelId==null||specificChannelId.equals(""))
        	specificChannelId="-1";
        String collectionChannelId=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_SELECT_COLLECTION_CHANNEL_ID);
        if(collectionChannelId==null||collectionChannelId.equals(""))
        	collectionChannelId="-1";
        
        
        String msg=(String)dataHashMap.get(MemoInterfaceKey.CONTROL_ACCRUAL_MESSAGE);
    	
%>
<script language="JavaScript">

function submitSearch()
{	

//var ff=document.getElementById('<%=MemoInterfaceKey.CONTROL_SELECT_COLLECTION_CHANNEL_ID%>').value;
//alert("collection"+ff);
//var f=document.getElementById('<%=MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID%>').value;
//alert("specific"+f);
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_SEARCH_SPECIFIC_COLLECTION_MEMO%>";
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

function submitIssue(memoId)
{

document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_ISSUE_MEMO%>";
document.<%=formName%>.submit();
}

function submitFinanceIssue(memoId)
{

document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_FINANCE_ISSUE_MEMO%>";
document.<%=formName%>.submit();
}
function submitDelete(memoId)
{

document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_DELETE_MEMO%>";
document.<%=formName%>.submit();
}
function toggleSpecific()
{

document.getElementById("1").style.display='';
document.getElementById("2").style.display='';
document.getElementById("3").style.display='none';
document.getElementById("4").style.display='none';
//document.getElementById("3").style.display='none';
document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID%>.value= "2";

}

function toggleCollection()
{
 //document.getElementById("3").style.display='';
 document.getElementById("1").style.display='none';
 document.getElementById("2").style.display='none';
 document.getElementById("3").style.display='';
 document.getElementById("4").style.display='';
 document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID%>.value= "1";
}
function toggleNone()
{
 //document.getElementById("3").style.display='';
 document.getElementById("1").style.display='none';
 document.getElementById("2").style.display='none';
 document.getElementById("3").style.display='none';
 document.getElementById("4").style.display='none';
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



function submitGetReport(memoId)
{
	document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
	
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_GET_MEMO_REPORT%>";
	document.<%=formName%>.submit();
}
function submitGetReportExcel(memoId,methodId)
{
	document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
	document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_PAYMENT_METHOD%>.value = methodId;
	
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_EXPORT_MEMO_TO_EXCEL%>";
	document.<%=formName%>.submit();
}
function drawCalender(argOrder,argValue)
{
    document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(memo_mgt_form."+argOrder+",'mm/dd/yyyy','Choose date')\">");
    document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
}
function showAccrualMessage(accrualMsg)
{
	alert(accrualMsg);
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
            
            <%if(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_MEMO)||action.equalsIgnoreCase(MemoInterfaceKey.ACTION_DELETE_MEMO)
            ||action.equalsIgnoreCase(MemoInterfaceKey.ACTION_ISSUE_MEMO)||
            action.equals(MemoInterfaceKey.ACTION_GET_MEMO_REPORT)||action.equals(MemoInterfaceKey.ACTION_FINANCE_ISSUE_MEMO) ){%>
            
            <%
            if(msg!=null)
        	{
        		if(msg.equals("DONE")){%>
        		<script>
        		showAccrualMessage("The Issuing operation DONE \n The Accrual Value was Sufficient to the DCM Commisssions");
        		</script>
        			
        		<%}else if(msg.equals("NOT DONE")){%>
        	    <script>
        		showAccrualMessage("The Issuing operation NOT DONE \n The Accrual Value was NOT Sufficient to the DCM Commisssions");
        		</script>
        		<%}
        	}
            %>
                        
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
              
              
              <td id="3" style="display:none">Payment Method</td>
              <td id="4" style="display:none">
              <select name="<%=MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD_ID%>" id="<%=MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD_ID%>">
              <option value=""></option>
              <%if(memoTypes!=null||memoTypes.size()!=0)
               {          
              for(int i=0;i<paymentMethods.size();i++){
            	  PaymentTypeMethodModel type=(PaymentTypeMethodModel) paymentMethods.get(i);
              
              %>
              <option  value="<%=type.getPaymentTypeMethodId()%>"><%=type.getPaymentTypeMethodName()%></option>
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
            <%}%>




            


            <%
            //if the action is search collection memo
            if(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_COLLECTION_MEMO)){
            	Vector memos=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_ALL_MEMOS);
            %>
             <%//the search part %>
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
              
              <td id="3" style="display:none">Payment Method</td>
              <td id="4" style="display:none">
              <select name="<%=MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD_ID%>" id="<%=MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD_ID%>">
              <option value=""></option>
              <%if(memoTypes!=null||memoTypes.size()!=0)
               {          
              for(int i=0;i<paymentMethods.size();i++){
            	  PaymentTypeMethodModel type=(PaymentTypeMethodModel) paymentMethods.get(i);
              
              %>
              <option  value="<%=type.getPaymentTypeMethodId()%>"><%=type.getPaymentTypeMethodName()%></option>
              <%}}%>
              </select>
              </td>
              
              </tr>       
              
                                                      
             </TABLE>
             <br><br>
             <table  cellspacing="2" cellpadding="1" border="0" width="100%">
                <tr>
                    <td align=center>
                        <input class=button type="button" name="search" value="Search"
                               onclick="javascript:submitSearch()">
                    </td>
                    
                     
                </tr>
            </table>



             <%
             //the results part
             if(memos.size()!=0&&memos!=null){
             %>
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            <TR class=TableHeader>
            <td colspan="12" align="center"><b>Search Results</b></td>
            </TR> 
            <tr class=TableHeader>
             <td>Name</td>
             <td>Description</td>
             <td>Creation Date</td>
             <td>Channel</td>
             <td>Type</td>
             <td>State</td>
             <td>Update</td>
             <%if(!statusId.equals("4")){ %>
             <td>Delete</td>
             <td>Action</td>
             <%} else{%>
             <td></td>
             <td></td>
             <%} %>
             <td>Export excel</td>
             <td>Export PDF Report</td> 
             <td>Get EToPop Excel</td>
              
             </tr>
             <%
             
             
             for(int i=0;i<memos.size();i++)
             {
             MemoModel memo=(MemoModel)memos.get(i);
             
             %>
             <tr>
             <td name="<%=MemoInterfaceKey.CONTROL_MEMO_ID%>" id="<%=MemoInterfaceKey.CONTROL_MEMO_ID%>">
             <a href="javaScript:submitViewMemo('<%=memo.getId()%>','<%=memo.getName()%>','<%=memo.getState()%>')" ><%=memo.getName()%></a>
             </td>
             <%if(memo.getDesc()!=null){ %>
             <td ><%=memo.getDesc()%></td>
             <%}else { %>
             <td></td>
             <%}%>
             <%if(memo.getStartDate()!=null){ %>
             <td><%=memo.getStartDate()%></td>
             <%}else { %>
             <td></td>
             <%}%>
             
             <%if(memo.getChannel()!=null){ %>
             <td><%=memo.getChannel()%></td>
             <%}else { %>
             <td></td>
             <%} %>
             
             <%if(memo.getType()!=null){ %>
             <td><%=memo.getType()%></td>
             <%}else { %>
             <td></td>
             <%} %>
             
             <%if(memo.getState()!=null){ %>
             <td><%=memo.getState()%></td>
             <%}else { %>
             <td></td>
             <%} %>
             <%if(memo.getState().equalsIgnoreCase("READY")){%>
             <td><input class=button type="button" name="delete" value="delete" onclick="javascript:submitDelete(<%=memo.getId()%>)"></td>
             <td><input class=button type="button" name="issue" value="Issue" onclick="javascript:submitIssue(<%=memo.getId()%>)"></td>
             <%}else if(memo.getState().equalsIgnoreCase("ISSUED")){%>
             <td><input class=button type="button" name="delete" value="delete" onclick="javascript:submitDelete(<%=memo.getId()%>)"></td>
             <td><input class=button type="button" name="finance_issue" value="Finance Issue" onclick="javascript:submitFinanceIssue(<%=memo.getId()%>)"></td>             
             <%}else{%>
             <td></td>
             <td></td>
             <%}%>
             <td><input class=button type="button" name="view_memo_report" value="Export excel" onclick="javascript:submitGetReportExcel('<%=memo.getId()%>','0')"></td>
             <td><input class=button type="button" name="view_memo_report" value="View memo report" onclick="javascript:submitGetReport('<%=memo.getId()%>')"></td>
              <%if(memo.getPaymentMethod()!=null&&memo.getPaymentMethod().compareTo("FOC")==0){
           	 %>
           	 <td><input class=button type="button" name="view_memo_report" value="export ETOPOP Memo" onclick="javascript:submitGetReportExcel('<%=memo.getId()%>','1')"></td>
           	 <%}else{ %>
           	 <td></td>
           	 <%} %>
             </tr>
             <%}%> 
            </table>                    
            
            <%}}%>






            
            <%
            //if the action is return from search specific memo
            if(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_SEARCH_SPECIFIC_MEMO)){
            	Vector memos=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_ALL_MEMOS);
            %>

            <%//the search part %>
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
              <option value="Collection" >Collection</option>
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
              
              <td id="3" style="display:none">Payment Method</td>
              <td id="4" style="display:none">
              <select name="<%=MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD_ID%>" id="<%=MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD_ID%>">
              <option value=""></option>
              <%if(memoTypes!=null||memoTypes.size()!=0)
               {          
              for(int i=0;i<paymentMethods.size();i++){
            	  PaymentTypeMethodModel type=(PaymentTypeMethodModel) paymentMethods.get(i);
              
              %>
              <option  value="<%=type.getPaymentTypeMethodId()%>"><%=type.getPaymentTypeMethodName()%></option>
              <%}}%>
              </select>
              </td>
              
              </tr>       
                                                      
             </TABLE>
             <br><br>
             <table  cellspacing="2" cellpadding="1" border="0" width="100%">
                <tr>
                    <td align=center>
                        <input class=button type="button" name="search" value="Search"
                               onclick="javascript:submitSearch()">
                    </td>
                    
                     
                </tr>
            </table>
            

             <%//the results part
             if(memos.size()!=0&&memos!=null){
             %>
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
             <TR class=TableHeader>
             <td colspan="12" align="center"><b>Search Results</b></td>
             </TR> 
             <tr class=TableHeader>
             <td>Name</td>
             <td>Description</td>
             <td>Creation Date</td>
             <td>Channel</td>
             <td>Payment Type</td>
             <td>Type</td>
             <td>State</td>
             <%if(!statusId.equals("4")){ %>
             <td>Delete</td>
             <td>Action</td>
             <%}else{ %>
             <td></td>
             <td></td>
             <%} %>
             <td>Export excel</td>
             <td>Export PDF Report</td> 
             <td>Get EToPop Excel</td>
             
             </tr>
             <%
             
             
             for(int i=0;i<memos.size();i++)
             {
             MemoModel memo=(MemoModel)memos.get(i);
             %>
             <tr >
             <td name="<%=MemoInterfaceKey.CONTROL_MEMO_ID%>" id="<%=MemoInterfaceKey.CONTROL_MEMO_ID%>">
             <a href="javaScript:submitViewMemo('<%=memo.getId()%>','<%=memo.getName()%>','<%=memo.getState()%>')" ><%=memo.getName()%></a>
             </td>
             <%if(memo.getDesc()!=null){ %>
             <td ><%=memo.getDesc()%></td>
             <%}else { %>
             <td></td>
             <%}%>
             <%if(memo.getStartDate()!=null){ %>
             <td><%=memo.getStartDate()%></td>
             <%}else { %>
             <td></td>
             <%}%>
             
             <%if(memo.getChannel()!=null){ %>
             <td><%=memo.getChannel()%></td>
             <%}else { %>
             <td></td>
             <%} %>
             
             <%if(memo.getPaymentType()!=null){ %>
             <td><%=memo.getPaymentType()%></td>
             <%}else { %>
             <td></td>
             <%} %>
             
             <%if(memo.getType()!=null){ %>
             <td><%=memo.getType()%></td>
             <%}else { %>
             <td></td>
             <%} %>
             
             <%if(memo.getState()!=null){ %>
             <td><%=memo.getState()%></td>
             <%}else { %>
             <td></td>
             <%} %>
                        
             <%if(memo.getState().equalsIgnoreCase("READY")){%>
              <td><input class=button type="button" name="delete" value="delete" onclick="javascript:submitDelete(<%=memo.getId()%>)"></td>
             <td><input class=button type="button" name="issue" value="Issue" onclick="javascript:submitIssue(<%=memo.getId()%>)"></td>
             <%}else if(memo.getState().equalsIgnoreCase("ISSUED")){%>
             <td><input class=button type="button" name="delete" value="delete" onclick="javascript:submitDelete(<%=memo.getId()%>)"></td>
             <td><input class=button type="button" name="finance_issue" value="Finance Issue" onclick="javascript:submitFinanceIssue(<%=memo.getId()%>)"></td>
             <%}else{%>
             <td></td>
             <td></td>
             <%}%>
             
             <td><input class=button type="button" name="view_memo_report" value="Export excel" onclick="javascript:submitGetReportExcel('<%=memo.getId()%>','0')"></td>
             <td><input class=button type="button" name="view_memo_report" value="View memo report" onclick="javascript:submitGetReport('<%=memo.getId()%>')"></td>
             <%if(memo.getPaymentMethod()!=null&&memo.getPaymentMethod().compareTo("FOC")==0){
           	 %>
           	 <td><input class=button type="button" name="view_memo_report" value="export ETOPOP Memo" onclick="javascript:submitGetReportExcel('<%=memo.getId()%>','1')"></td>
           	 <%}else{ %>
           	 <td></td>
           	 <%} %>
             </tr>
             <%}%> 
            </table>
                        
            <%}}%>





            
            
             </form>
    </body>
</html>






