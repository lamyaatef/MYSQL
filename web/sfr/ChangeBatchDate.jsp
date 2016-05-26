<%@ page import="javax.servlet.*" 
	import="javax.servlet.http.*"
	import="java.io.PrintWriter" 
	import="java.io.IOException"
	import="java.util.*" 
	import=" com.mobinil.sds.web.interfaces.cam.*"
	import=" com.mobinil.sds.core.system.cam.accrual.model.*"
	import="com.mobinil.sds.core.system.cam.accrual.dao.*"
	import="com.mobinil.sds.web.interfaces.InterfaceKey"
	import="com.mobinil.sds.core.system.cam.payment.model.*"
	import="com.mobinil.sds.core.system.cam.memo.model.*"
	%>
<%
String appName = request.getContextPath();
String serverName = request.getServerName();
int serverPort = request.getServerPort();
String formName = "memo_mgt_form";
String pageHeader="Change Batch Date";
HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String userID =(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
String action=(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
String closedIdList=(String)dataHashMap.get(SFRInterfaceKey.CONTROL_HIDDEN_MSG);                      
%>
<script language="JavaScript">
function IsNumeric(sText)
{
   var ValidChars = "0123456789";
   var IsNumber=true;
   var Char;

 
   for (i = 0; i < sText.length && IsNumber == true; i++) 
      { 
      Char = sText.charAt(i); 
      if (ValidChars.indexOf(Char) == -1) 
         {
         IsNumber = false;
         }
      }
   return IsNumber;
   
   }
function submitUpdate()
{
	if (IsNumeric(document.getElementById('<%=SFRInterfaceKey.CONTROL_FROM_BATCH_ID%>').value)== false ||IsNumeric(document.getElementById('<%=SFRInterfaceKey.CONTROL_TO_BATCH_ID%>').value)== false) {
		alert("characters is not allowed");
	}else if(document.getElementById('<%=SFRInterfaceKey.GENERATE_BATCH_DATE%>').value=='date'){
		alert("select DATE");
	}else{
	document.getElementById('<%=InterfaceKey.HASHMAP_KEY_ACTION%>').value='<%=SFRInterfaceKey.ACTION_CHANGE_BATCH_DATE%>';
	document.<%=formName%>.submit();
	}
}
function drawCalender(argOrder,argValue)
{
    document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(memo_mgt_form."+argOrder+",'mm/dd/yyyy','Choose date')\">");
    document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
}

function alertFailed()
{
	alert("this batch Id is not exist");
}
</script>

<%@page import="com.mobinil.sds.web.interfaces.cr.ContractRegistrationInterfaceKey"%>
<%@page import="com.mobinil.sds.web.interfaces.sfr.SFRInterfaceKey"%><html>
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
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID %>">
            <%if(action.compareTo(SFRInterfaceKey.ACTION_CHANGE_BATCH_DATE)==0){
            	  if(String.valueOf(dataHashMap.get(SFRInterfaceKey.ACTION_CHANGE_BATCH_DATE_FAILED)).compareTo("failed")==0){  
              %>
              <script type="text/javascript">
              alertFailed();
              </script>
              <%} %>      
             
              <%} %>
            
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            
              <tr class="TableHeader">
              <td colspan="6" align="center"><b>Change</b></td>
              </tr>
              <tr>
              <td>From Batch ID</td>
              <td><input type="text" name="<%=SFRInterfaceKey.CONTROL_FROM_BATCH_ID%>" id="<%=SFRInterfaceKey.CONTROL_FROM_BATCH_ID%>"></td>
              <td>To Batch ID</td>
              <td><input type="text" name="<%=SFRInterfaceKey.CONTROL_TO_BATCH_ID%>" id="<%=SFRInterfaceKey.CONTROL_TO_BATCH_ID%>"></td>
              <td>Date</td>
              <td>
              	<script>
                  drawCalender('<%=SFRInterfaceKey.GENERATE_BATCH_DATE%>','date');
               	</script>
              </td>
              </tr>
              <tr>
              <td align="center" colspan="4">
            <input class="button" type="button" name="Update" value="Update"
                               onclick="javascript:submitUpdate()">             
                           
            </td>
            </tr>
                 
              </table>
              
              <br><center><table border = 0>
              <%
              if(closedIdList != null && !closedIdList.equals(""))
              {
                 for(int i= 0 ; i < closedIdList.split("-").length  ; i++)
                 {
                	%>
                	 <tr><td>Batch Id <%= closedIdList.split("-")[i] %> is closed</td></tr>
                	<%  
                 }
              }
              %>
              </table></center></br>
              </form>
    </body>
</html>