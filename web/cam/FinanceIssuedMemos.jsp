<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
//String real_path = request.getRealPath("/cam/camMemosFiles/");
String serverName = request.getServerName();
int serverPort = request.getServerPort();
String form_name = "memo_mgt_form";
String page_header="Finance Isuued Memos";
HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
String action=  (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
%>
<html>
<head>
<script language="JavaScript">

function submitViewMemo(memo_id,memo_name,state_name)
{
alert("submitViewMemo()");
document.<%=form_name%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memo_id;
document.<%=form_name%>.<%=MemoInterfaceKey.HIDDEN_MEMO_NAME%>.value = memo_name;
document.<%=form_name%>.<%=MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME%>.value = state_name;
alert(document.<%=form_name%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value);
document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_VIEW_MEMO%>";
document.<%=form_name%>.submit();
}

function submitGetReport(memo_id,memo_name)
{
	document.<%=form_name%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memo_id;
	document.<%=form_name%>.<%=MemoInterfaceKey.HIDDEN_MEMO_NAME%>.value = memo_name;
	document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_GET_MEMO_REPORT%>";
	document.<%=form_name%>.submit();
}


</script>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>
<form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=form_name%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID%>" value="-1">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>" value="-1">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_NAME%>" value="-1">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME%>" value="-1">
<center><h2> <%=page_header%></h2></center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
             <tr class="TableHeader">
             <td>Name</td>
             <td>Description</td>
             <td>Creation Date</td>
             <td>Channel</td>
             <td>Payment Type</td>
             <td>Type</td>
             <td>Action</td> 
             
             </tr>
             <%
             Vector memos=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_ALL_MEMOS);
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
             
             <td><input class="button" type="button" name="view_memo_report" value="view memo" onclick="javascript:submitGetReport(<%=memo.getId()%>)"></td>
             
             </tr>
             <%}%> 
            </table>
        </form>

</body>
</html>