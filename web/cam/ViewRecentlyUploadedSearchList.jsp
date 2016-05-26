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
//String real_path = request.getRealPath("/cam/camMemosFiles/");
String serverName = request.getServerName();
int serverPort = request.getServerPort();
String formName = "memo_mgt_form";
String pageHeader="DCM Details Search List";

HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
String action=  (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
Vector dcmStatusModel=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_DCM_DETAILS_STATUS);
                     
%>
<script language="JavaScript">
function submitSearch( )
{
	
if (document.getElementById("<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>").value == "" &&
document.getElementById("<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME%>").value == ""&&
document.getElementById("<%=PaymentInterfaceKey.CONTROL_STK_NUMBER%>").value == "")
{
alert("there is no criteria");
return ;
}
else 
{
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_SEARCH_DCM_DETAILS%>";
document.<%=formName%>.submit();
}
}

function submitSearchGen( )
{

if (document.getElementById("<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>").value == "" &&
document.getElementById("<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME%>").value == ""&&
document.getElementById("<%=PaymentInterfaceKey.CONTROL_STK_NUMBER%>").value == "")
{
alert("there is no criteria");
return ;
}
else 
{
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_SEARCH_GEN_DCM_DETAILS%>";
document.<%=formName%>.submit();
}
}
function DCMStatusNotExist()
{
	alert("This DCM Has No Status For Payment In The Accounting Module"); 
}
function DCMNotExist()
{
	alert("There Is No DCM BY These Criteria");
}
function submitAdd()
{
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_INSERT_NEW_DCM_PAYMENT_STATUS%>";
	document.<%=formName%>.submit();
}
function submitExportExcel(dcmCode)
{

	document.<%=formName%>.<%=PaymentInterfaceKey.CONTROL_HIDDEN_DCM_CODE%>.value=dcmCode;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_EXPORT_DCM_DETAILS_EXCEL%>";
	document.<%=formName%>.submit();
	
}

function submitExportAllExcel()
{

	
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_EXPORT_ALL_DCM_DETAILS_EXCEL%>";
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

        <%
         
        %>
        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
            <input type="hidden" name="<%=PaymentInterfaceKey.CONTROL_HIDDEN_DCM_CODE%>" value="-1">
            
            <br>
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="0">
            <tr>
            <td colspan="3" align="center"><input class="button" type="button" value="Export All As Excel" onclick="javascript:submitExportAllExcel()"></td>
            </tr>
            </TABLE>
       </form>
    </body>
</html>