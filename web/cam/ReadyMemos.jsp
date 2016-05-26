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
String formName = "memo_mgt_form";
String pageHeader="Ready Memos";
HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
String action=  (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
%>
<script language="JavaScript">

function submitViewMemo(memoId,memoName,stateName)
{

document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_NAME%>.value = memoName;
document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME%>.value = stateName;
alert(document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value);
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_VIEW_MEMO%>";
document.<%=formName%>.submit();
}

function submitIssue(memoId)
{

document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_ISSUE_READY_MEMO%>";
alert(document.getElementById('<%=InterfaceKey.HASHMAP_KEY_ACTION%>').value);
document.<%=formName%>.submit();
}


function submitDelete(memoId)
{

document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_DELETE_READY_MEMO%>";
document.<%=formName%>.submit();
}


function submitGetReport(memoId,memoName)
{
	document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_ID%>.value = memoId;
	document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_MEMO_NAME%>.value = memoName;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_GET_MEMO_REPORT%>";
	document.<%=formName%>.submit();
}


</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">

<title>Insert title here</title>
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
            


<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
             
             <tr class="TableHeader">
             <td>ID</td>
             <td>Name</td>
             <td>Description</td>
             <td>Creation Date</td>
             <td>Channel</td>
             <td>Payment Type</td>
             <td>Type</td>
             <td>Delete</td>
             <td>Action</td> 
             
             </tr>
             <%
             Vector memos=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_ALL_MEMOS);
             for(int i=0;i<memos.size();i++)
             {
             MemoModel memo=(MemoModel)memos.get(i);
             
             %>
             <tr >
             <td>
             <%=memo.getId()%>
             </td>
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
             
             <td><input class="button" type="button" name="delete" value="delete" onclick="javascript:submitDelete(<%=memo.getId()%>)"></td>
             <td><input class="button" type="button" name="issue" value="Issue" onclick="javascript:submitIssue(<%=memo.getId()%>)"></td>
                         
             
             </tr>
             <%}%> 
            </table>
   </form>

</body>
</html>