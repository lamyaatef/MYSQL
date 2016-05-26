<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import=" com.mobinil.sds.web.interfaces.cam.*"
         import=" com.mobinil.sds.core.system.cam.accrual.model.*"
         import ="com.mobinil.sds.core.system.cam.accrual.dao.*"
         import ="com.mobinil.sds.web.interfaces.InterfaceKey"
         import ="com.mobinil.sds.core.system.cam.memo.model.*"
         
         %>
<%
        String appName = request.getContextPath();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String formName = "memo_screen_mgt_form";
        String pageHeader="Memo Settings";
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String action=  (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
        
        Vector memoSettings=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_PAYMENT_TYPE_GENERATION_SETTINGS);
                      
%>
<script language="JavaScript">


function submitUpdateFlag()
{
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_SET_PAYMENT_MANAGEMENT_MANUAL%>";
document.<%=formName%>.submit();
}
function submitUpdateDate()
{
	
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_SET_PAYMENT_MANAGEMENT_AUTO%>";
document.<%=formName%>.submit();

}

function toggleWeekly()
{
	
document.getElementById("1").style.display="";
document.getElementById("2").style.display='none';
document.getElementById("3").style.display='none';

}

function toggleMonthly()
{

document.getElementById("1").style.display='none';
document.getElementById("2").style.display="";
document.getElementById("3").style.display='none';
}
function toggleQuarterly()
{
	
document.getElementById("1").style.display='none';
document.getElementById("2").style.display='none';
document.getElementById("3").style.display="";
}
function toggleNone()
{
	document.getElementById("1").style.display='none';
	document.getElementById("2").style.display='none';
	document.getElementById("3").style.display='none';
}

function toggleChange()
{
	
	if(document.getElementById('<%=MemoInterfaceKey.SELECTED_MEMO_GENERATION_PERIOD%>').value == "1")
		toggleWeekly();
	else if(document.getElementById('<%=MemoInterfaceKey.SELECTED_MEMO_GENERATION_PERIOD%>').value == "2")
		toggleMonthly();
	else if(document.getElementById('<%=MemoInterfaceKey.SELECTED_MEMO_GENERATION_PERIOD%>').value == "3")
		toggleQuarterly();
	else
		toggleNone();
}
//function drawCalender(argOrder,argValue)
//{
//document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(payment_screen_mgt_form."+argOrder+",'dd-mm-yyyy','Choose date')\">");
//document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
//}

function drawCalender(argOrder,argValue)
    {
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(memo_screen_mgt_form."+argOrder+",'mm/dd/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
 }
function submitEdit(memoSettingsId)
{
	document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_SETTINGS_ID%>.value=memoSettingsId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_VIEW_EDIT_MEMO_SETTINGS%>";
	document.<%=formName%>.submit();
}
function submitUpdate(memoSettingsId)
{
	document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_SETTINGS_ID%>.value=memoSettingsId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_UPDATE_MEMO_SETTINGS%>";
	document.<%=formName%>.submit();
}
</script>

<%@page import="java.text.DateFormatSymbols"%><html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/calendar.js"></SCRIPT>
        </head>
    <body>
        <center><h2> <%=pageHeader%></h2></center>

        <%
        
        %>
        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_SETTINGS_ID%>" value="-1">
            
           
            <%if(action.equals(MemoInterfaceKey.ACTION_VIEW_MEMO_SETTINGS)||action.equals(MemoInterfaceKey.ACTION_UPDATE_MEMO_SETTINGS)){ %>
         <table style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
         <tr class="tableheader">
         <td>Payment Type</td>         
         <td>Generation By Period</td>
         <td>Edit</td>
       </tr>
       
       <%if(memoSettings!=null && memoSettings.size()!=0){
    	   for(int i=0;i<memoSettings.size();i++){
    		   MemoPaymentTypeSettingsModel model=(MemoPaymentTypeSettingsModel)memoSettings.get(i);
       %>
       <tr>
       <td><%=model.getPaymentType() %></td>
       <td><%=model.getGenerationPeriod() %></td>
       <td><input class="button" type="button" value="Edit" onclick="javascript:submitEdit('<%=model.getMemoSeetingsId() %>')"></td>
       </tr>
       <%} }%>
         </table>
         <%} %>
        
        
        
         
         <%if(action.equals(MemoInterfaceKey.ACTION_VIEW_EDIT_MEMO_SETTINGS)){
        	 MemoPaymentTypeSettingsModel	model=(MemoPaymentTypeSettingsModel)dataHashMap.get(MemoInterfaceKey.PAYMENT_TYPE_GENERATION_SETTINGS);
        	 
        	 Vector memoGenerationPeriods=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_GENERATION_PERIODS);
         %>
         <table style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
         <tr class="tableheader">
         <td>Payment Type</td>         
         <td>Generation By Period</td>
         <td>Tracking Flag</td>
        
       </tr>
       
       <%if(model!=null){    	   
       %>
       <tr>
       <td><%=model.getPaymentType() %></td>
       <td>
       <select name="<%=MemoInterfaceKey.SELECTED_MEMO_GENERATION_PERIOD%>" id="<%=MemoInterfaceKey.SELECTED_MEMO_GENERATION_PERIOD%>" onchange="javaScript:toggleChange();">
       <option value=""></option>
         <%
           if(memoGenerationPeriods!=null||memoGenerationPeriods.size()!=0){
        	   String status_selected="";
            for(int i=0;i<memoGenerationPeriods.size();i++){
            	MemoGenerationPeriodsModel period=(MemoGenerationPeriodsModel) memoGenerationPeriods.get(i);
            if(period.getMemoGenerationPeriod().compareTo(model.getGenerationPeriod())==0 )
            	status_selected="selected";
            else
            	status_selected="";
             %>
           <option <%=status_selected%> value="<%=period.getMemoGenerationPeriodId() %>"><%=period.getMemoGenerationPeriod() %></option>
            <%}}%>
           </select>
       </td> 
         <td>
         <select name="<%=MemoInterfaceKey.SELECTED_MEMO_TRACKING_FLAG%>" id="<%=MemoInterfaceKey.SELECTED_MEMO_TRACKING_FLAG%>" >
         <%if(model.getTrackingFlag()==1){ %>
         <option selected="selected" value="1">Tracked</option>
         <%}else{ %>
         <option value="1">Tracked</option>
         <%} %>
         
         <%if(model.getTrackingFlag()==0){ %>
         <option selected="selected" value="0">Not Tracked</option>
         <%}else{ %>
         <option value="0">Not Tracked</option>
         <%} %>
       
       </select>
         </td>
       
       </tr>
       
       <% }%>       
         </table>
         
         
         <br><br>
         <div id="1" style="display:none">
         <table style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
       <tr class="tableheader">
       <td>Week Number</td>
       <td>Day Of Week</td>
       </tr>
       
       
       <%for(int i=1;i<=52;i++){ %>
       <tr>
       <td>Week No. <%=i%></td>
       <td>
       <select name="weekNo+<%=i%>" id="weekNo+<%=i%>">
       
       <%if(model.getGenerationPeriod().compareTo("WEEKLY")==0){ %>
       <%int [][] weeks=model.getWeeks(); %>
       <%if(weeks[i-1][0]==-1){ %>
       <option selected="selected" value=""></option>
       <%}else{ %>
       <option  value=""></option>
       <%} %>
       
       <%if(weeks[i-1][0]==1){ %>
       <option selected="selected" value="1">Sunday</option>
       <%}else{ %>
       <option value="">Sunday</option>
       <%} %>
       
       <%if(weeks[i-1][0]==2){ %>
       <option selected="selected"  value="2">Monday</option>
       <%}else{ %>
       <option  value="2">Monday</option>
       <%} %>
        
        <%if(weeks[i-1][0]==3){ %>
       <option  selected="selected" value="3">Tuesday</option>
       <%}else{ %>
       <option  value="3">Tuesday</option>
       <%} %>
        
        <%if(weeks[i-1][0]==4){ %>
       <option  selected="selected" value="4">Wednesday</option>
       <%}else{ %>
       <option  value="4">Wednesday</option>
       <%} %>
       
       <%if(weeks[i-1][0]==5){ %>
       <option selected="selected"  value="5">Thrusday</option>
       <%}else{ %>
       <option  value="5">Thrusday</option>
       <%} %>
        
        
       <%}else{ %>
       <option value=""></option>
       <option  value="1">Sunday</option>
        <option  value="2">Monday</option>
        <option  value="3">Tuesday</option>
        <option  value="4">Wednesday</option>
        <option  value="5">Thrusday</option>
       <%} %>
        
       </select>
       </td>
       </tr>
       <%} %>
       </table>
         </div>
         
       
       <div id="2" style="display:none">
       <table style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
       <%   DateFormatSymbols symbols = new DateFormatSymbols(); 
       String[] monthNames = symbols.getMonths(); 
       %>
       <tr class="tableheader">
       <td>Month</td>
       <td>Day in Month</td>       
       </tr>
       
       <%for(int i=1;i<12;i++){ %>
       <tr>
       <td><%=monthNames[i] %></td>
       <td>
       <select name="monthNo+<%=i %>" id="monthNo+<%=i %>">
       <%if(model.getGenerationPeriod().compareTo("MONTHLY")==0){ %>
       <%int[][] months=model.getMonths(); %>
       <%if(months[i][0]==-1){ %>
       <option selected="selected"  value=""></option>
       <%}else{ %>
       <option value=""></option>
       <%} %>
       
       <%for(int j=1;j<=28;j++){ %>
       <%if(months[i][0]==j){ %>
       <option selected="selected"  value="<%=j %>"><%=j %></option>
       <%}else{ %>
        <option  value="<%=j %>"><%=j %></option> 
       <%} %>
              
        <%} %>
       
       <%}else{ %>      
       <option value=""></option>
       <%for(int j=1;j<=28;j++){ %>
        <option  value="<%=j %>"><%=j %></option>
       <%} %>
       
       <%} %>       
       </select> 
       </td>
       </tr>
       <%} %>  
       
       
       <tr>
       <td><%=monthNames[0] %></td>
       <td>
       <select name="monthNo+<%=0 %>" id="monthNo+<%=0 %>">
       <%if(model.getGenerationPeriod().compareTo("MONTHLY")==0){ %>
       <%int[][] months=model.getMonths(); %>
       <%if(months[0][0]==-1){ %>
       <option selected="selected"  value=""></option>
       <%}else{ %>
       <option value=""></option>
       <%} %>
       
       <%for(int j=1;j<=28;j++){ %>
       <%if(months[0][0]==j){ %>
       <option selected="selected"  value="<%=j %>"><%=j %></option>
       <%}else{ %>
        <option  value="<%=j %>"><%=j %></option> 
       <%} %>
              
        <%} %>
       
       <%}else{ %>      
       <option value=""></option>
       <%for(int j=1;j<=28;j++){ %>
        <option  value="<%=j %>"><%=j %></option>
       <%} %>
       
       <%} %>       
       </select> 
       </td>
       </tr>     
       </table>
       </div>
       
              
       <div id="3" style="display:none">
       <table style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
       <tr class="tableheader">
       <td>Quarter</td>
       <td>Month In Quarter</td>
       <td>Day In Month</td>       
       </tr>
       <%if(model.getGenerationPeriod().compareTo("QUARTERLY")==0){ %>
       <%int [][] qrtrs=model.getQuarters(); %>
       <tr>
       <td >Quarter One</td>
       <td>
       <%if(qrtrs[0][0]!=0){ %>
       <select name="qrtrMonth1" id="qrtrMonth1">
       <%
       String status_selected="";
       for(int i=1;i<=3;i++){
    	   if(qrtrs[0][0]==i+3)
    		   status_selected="selected";
    	   else 
    		 status_selected="";
    	   %>
       <option <%=status_selected %>  value="<%=i+3 %>"><%=monthNames[i+2] %></option>
       <%} %>
       </select>
       <%}else{ %>
       <select name="qrtrMonth1" id="qrtrMonth1">
       <%
         for(int i=1;i<=3;i++){
    	   
    	   %>
       <option value="<%=i+3 %>"><%=monthNames[i+2] %></option>
       <%} %>
       </select>
       <%} %>
       
       </td>
       
       <td>
       <%if(qrtrs[0][1]!=0){ %>
       <select name="qrtrDay1" id="qrtrDay1">
       <%
       String status_selected="";
       for(int i=1;i<=28;i++){
    	   if(qrtrs[0][1]==i)
    		   status_selected="selected";
    	   else 
    		 status_selected="";
    	   %>
       <option <%=status_selected %>  value="<%=i %>"><%=i %></option>
       <%} %>
       </select>
       <%}else{ %>
       <select name="qrtrDay1" id="qrtrDay1">
       <%
         for(int i=1;i<=28;i++){
    	   
    	   %>
       <option  value="<%=i %>"><%=i %></option>
       <%} %>
       </select>
       <%} %>
       
       </td>
       </tr>
       
       <tr>
       <td >Quarter Two</td>
       <td>
       <%if(qrtrs[1][0]!=0){ %>
       <select name="qrtrMonth2" id="qrtrMonth2">
       <%
       String status_selected="";
       for(int i=1;i<=3;i++){
    	   if(qrtrs[1][0]==i+6)
    		   status_selected="selected";
    	   else 
    		 status_selected="";
    	   %>
       <option <%=status_selected %>  value="<%=i+6 %>"><%=monthNames[i+5] %></option>
       <%} %>
       </select>
       <%}else{ %>
       <select name="qrtrMonth2" id="qrtrMonth2">
       <%
         for(int i=1;i<=3;i++){
    	   
    	   %>
       <option  value="<%=i+6 %>"><%=monthNames[i+5] %></option>
       <%} %>
       </select>
       <%} %>
       
       </td>
       
       <td>
       <%if(qrtrs[1][1]!=0){ %>
       <select name="qrtrDay2" id="qrtrDay2">
       <%
       String status_selected="";
       for(int i=1;i<=28;i++){
    	   if(qrtrs[1][1]==i)
    		   status_selected="selected";
    	   else 
    		 status_selected="";
    	   %>
       <option <%=status_selected %>  value="<%=i %>"><%=i %></option>
       <%} %>
       </select>
       <%}else{ %>
       <select name="qrtrDay2" id="qrtrDay2">
       <%
         for(int i=1;i<=28;i++){
    	   
    	   %>
       <option  value="<%=i %>"><%=i %></option>
       <%} %>
       </select>
       <%} %>
       
       </td>
       </tr>
       
       <tr>
       <td >Quarter Three</td>
       <td>
       <%if(qrtrs[2][0]!=0){ %>
       <select name="qrtrMonth3" id="qrtrMonth3">
       <%
       String status_selected="";
       for(int i=1;i<=3;i++){
    	   if(qrtrs[2][0]==i+9)
    		   status_selected="selected";
    	   else 
    		 status_selected="";
    	   %>
       <option <%=status_selected %>  value="<%=i+9 %>"><%=monthNames[i+8] %></option>
       <%} %>
       </select>
       <%}else{ %>
       <select name="qrtrMonth3" id="qrtrMonth3">
       <%
         for(int i=1;i<=3;i++){
    	   
    	   %>
      <option value="<%=i+9 %>"><%=monthNames[i+8] %></option>
       <%} %>
       </select>
       <%} %>
       
       </td>
       
       <td>
       <%if(qrtrs[2][1]!=0){ %>
       <select name="qrtrDay3" id="qrtrDay3">
       <%
       String status_selected="";
       for(int i=1;i<=28;i++){
    	   if(qrtrs[2][1]==i)
    		   status_selected="selected";
    	   else 
    		 status_selected="";
    	   %>
       <option <%=status_selected %>  value="<%=i %>"><%=i %></option>
       <%} %>
       </select>
       <%}else{ %>
       <select name="qrtrDay3" id="qrtrDay3">
       <%
         for(int i=1;i<=28;i++){
    	   
    	   %>
       <option  value="<%=i %>"><%=i %></option>
       <%} %>
       </select>
       <%} %>
       
       </td>
       </tr>
       
       <tr>
       <td >Quarter Four</td>
       <td>
       <%if(qrtrs[3][0]!=0){ %>
       <select name="qrtrMonth4" id="qrtrMonth4">
       <%
       String status_selected="";
       for(int i=1;i<=3;i++){
    	   if(qrtrs[3][0]==i)
    		   status_selected="selected";
    	   else 
    		 status_selected="";
    	   %>
       <option <%=status_selected %>  value="<%=i %>"><%=monthNames[i-1]%></option>
       <%} %>
       </select>
       <%}else{ %>
       <select name="qrtrMonth4" id="qrtrMonth4">
       <%
         for(int i=1;i<=3;i++){
    	   
    	   %>
       <option value="<%=i %>"><%=monthNames[i-1]%></option>
       <%} %>
       </select>
       <%} %>
       
       </td>
       
       <td>
       <%if(qrtrs[3][1]!=0){ %>
       <select name="qrtrDay4" id="qrtrDay4">
       <%
       String status_selected="";
       for(int i=1;i<=28;i++){
    	   if(qrtrs[3][1]==i)
    		   status_selected="selected";
    	   else 
    		 status_selected="";
    	   %>
       <option <%=status_selected %>  value="<%=i %>"><%=i %></option>
       <%} %>
       </select>
       <%}else{ %>
       <select name="qrtrDay4" id="qrtrDay4">
       <%
         for(int i=1;i<=28;i++){
    	   
    	   %>
       <option  value="<%=i %>"><%=i %></option>
       <%} %>
       </select>
       <%} %>
       
       </td>
       </tr>
       <%}else{ %>
       <tr>
       <td >Quarter One</td>
       <td>       
       <select name="qrtrMonth1" id="qrtrMonth1">
       <% for(int i=1;i<=3;i++){   	   
    	   %>
       <option value="<%=i+3 %>"><%=monthNames[i+2]%></option>
       <%} %>
       </select>     
       </td>       
       <td>       
       <select name="qrtrDay1" id="qrtrDay1">
       <%
      for(int i=1;i<=28;i++){ %>
       <option  value="<%=i %>"><%=i %></option>
       <%} %>
       </select>      
       </td>
       </tr>
       
       <tr>
       <td >Quarter Two</td>
       <td>       
       <select name="qrtrMonth2" id="qrtrMonth2">
       <% for(int i=1;i<=3;i++){   	   
    	   %>
       <option value="<%=i+6 %>"><%=monthNames[i+5] %></option>
       <%} %>
       </select>     
       </td>       
       <td>       
       <select name="qrtrDay2" id="qrtrDay2">
       <%
      for(int i=1;i<=28;i++){ %>
       <option  value="<%=i %>"><%=i %></option>
       <%} %>
       </select>      
       </td>
       </tr>
       
       <tr>
       <td >Quarter Three</td>
       <td>       
       <select name="qrtrMonth3" id="qrtrMonth3">
       <% for(int i=1;i<=3;i++){   	   
    	   %>
       <option value="<%=i+9 %>"><%=monthNames[i+8] %></option>
       <%} %>
       </select>     
       </td>       
       <td>       
       <select name="qrtrDay3" id="qrtrDay3">
       <%
      for(int i=1;i<=28;i++){ %>
       <option  value="<%=i %>"><%=i %></option>
       <%} %>
       </select>      
       </td>
       </tr>
       
       <tr>
       <td >Quarter Four</td>
       <td>       
       <select name="qrtrMonth4" id="qrtrMonth4">
       <%
       for(int i=1;i<=3;i++){   	   
    	   %>
       <option value="<%=i %>"><%=monthNames[i-1] %></option>
       <%} %>
       </select>     
       </td>       
       <td>       
       <select name="qrtrDay4" id="qrtrDay4">
       <%
      for(int i=1;i<=28;i++){ %>
       <option  value="<%=i %>"><%=i %></option>
       <%} %>
       </select>      
       </td>
       </tr>
       <%} %>
       
       
       </table>
       </div>  
       
         <br> 
       <table style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="0">
       <tr >
       <td colspan="2" align="center" ><input class="button" type="button" value="Update" onclick="javascript:submitUpdate('<%=model.getMemoSeetingsId() %>')"></td>
       </tr>
       </table>
       
       <%if(model.getGenerationPeriod().compareTo("WEEKLY")==0){ %>
         <script>
         toggleWeekly();
         </script>
         <%}else if(model.getGenerationPeriod().compareTo("MONTHLY")==0){ %>
         <script>
         toggleMonthly();
         </script>
         <%}else if(model.getGenerationPeriod().compareTo("QUARTERLY")==0){ %>
         <script>
         toggleQuarterly();
         </script>
           <%} %>
         <%} %>
         
            </form>
    </body>
</html>