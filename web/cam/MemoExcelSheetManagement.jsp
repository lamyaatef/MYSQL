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
String formName = "payment_mgt_form";
String pageHeader="Memo Excel Management";

HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
String action=  (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);

String paymentTypeId=(String)dataHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID);
String paymentTypeName=(String)dataHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME);
Vector searchResults = (Vector) dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
                     
%>
<script language="JavaScript">
function submitView(paymentTypeId)
{
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID%>.value = paymentTypeId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_VIEW_MEMO_EXCEL_ATTRIBUTES%>";
	document.<%=formName%>.submit();
}
function alertNoData()
{
	alert("There is no Excel Attributes for this Payment Type");
}
function submitUpdate(payementExcelSheetId,paymentTypeId,paymentTypeName,oldSheetNumber)
{
	var x=document.<%=formName%>.<%=PaymentInterfaceKey.CONTROL_EXCEL_SHEET_NUMBER%>.value;
	var anum=/(^\d+$)|(^\d+\.\d+$)/;
	if (anum.test(x))
	testresult=true;
	else{
	alert("Please input a valid number!");
	return;
	}
	
	
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_EXCEL_MANAGEMENT_ID%>.value = payementExcelSheetId;
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID%>.value = paymentTypeId;
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME%>.value = paymentTypeName;
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_OLD_EXCEL_SHEET_NUMBER%>.value = oldSheetNumber;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_UPDATE_MEMO_EXCEL_ATTRIBUTES%>";
	document.<%=formName%>.submit();
}
function submitInsert(paymentTypeId,paymentTypeName)
{
	var x=document.<%=formName%>.<%=PaymentInterfaceKey.CONTROL_NEW_EXCEL_SHEET_NUMBER%>.value;
	var anum=/(^\d+$)|(^\d+\.\d+$)/;
	if (anum.test(x))
	testresult=true;
	else{
	alert("Please input a valid number!");
	return;
	}
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID%>.value = paymentTypeId;
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME%>.value = paymentTypeName;	
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_INSERT_MEMO_EXCEL_ATTRIBUTES%>";
	document.<%=formName%>.submit();
}
function submitViewInsert(paymentTypeId)
{
	
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID%>.value = paymentTypeId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_VIEW_INSERT_MEMO_EXCEL_ATTRIBUTES%>";
	document.<%=formName%>.submit();
}
function alertNotUnique()
{
	alert("The Sheet Number you entered is already exist");
}
function toggleChange()
{
	if(document.getElementById("templateType").value == "specific")
		{
		document.getElementById("1").style.display='';
		document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_SQL_TEMPLATE_ID %>.value="-1";
		}
	else if(document.getElementById("templateType").value == "temp")
		{
		document.getElementById("1").style.display='none';
		document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_SQL_TEMPLATE_ID %>.value="1";
		}
	else{
		document.getElementById("1").style.display='none';
		document.<%=formName%>.<%=MemoInterfaceKey.HIDDEN_SQL_TEMPLATE_ID %>.value="1";
	}
		
}
</script>

<%@page import="com.mobinil.sds.core.system.payment.model.PaymentModel"%>
 
<%@page import="com.mobinil.sds.web.interfaces.cam.PaymentInterfaceKey"%>
 

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
            <input type="hidden" name="<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID %>" value="-1">
            <input type="hidden" name="<%=PaymentInterfaceKey.HIDDEN_OLD_EXCEL_SHEET_NUMBER %>" value="-1">
            <input type="hidden" name="<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME %>" value="-1">
            <input type="hidden" name="<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_EXCEL_MANAGEMENT_ID %>" value="-1">
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_SQL_TEMPLATE_ID %>" value="-1">
            
             
             <%if(action.equals(MemoInterfaceKey.ACTION_VIEW_UPDATE_MEMO_EXCEL_ATTRIBUTES)){
            	 PaymentTypeExcelModel model=(PaymentTypeExcelModel)dataHashMap.get(PaymentInterfaceKey.PAYMENT_TYPE_EXCEL_ATTRIBUTE);
            	 String msg=(String)dataHashMap.get(PaymentInterfaceKey.EXCEL_SHEET_MESSAGE);
            	 String read="";
            	 if(model.getTempId()!=-1)
                	 read="readonly";
                	 else
                		 read="";
            	 %>
            	 
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            <TR class=TableHeader>
            <td colspan="2" align="center">Edit</td>
            </TR>
             <tr >
             <td width="15%">Excel Sheet Number</td>
             <td><input type="text" value="<%=model.getExcelSheetNumber() %>" name="<%=PaymentInterfaceKey.CONTROL_EXCEL_SHEET_NUMBER%>" id="<%=PaymentInterfaceKey.CONTROL_EXCEL_SHEET_NUMBER%>">
             </td>
             </tr>
             <tr>
             <td>Excel Sheet Name</td>             
             <td><input type="text" value="<%=model.getExcelSheetName() %>" name="<%=PaymentInterfaceKey.CONTROL_EXCEL_SHEET_NAME%>" id="<%=PaymentInterfaceKey.CONTROL_EXCEL_SHEET_NAME%>">
             </td>
             </tr>
             <tr>
             <td>Payment Name</td>  
             <td>
	             <select name="<%=PaymentInterfaceKey.CONTROL_SELECT_PAYMENT %>">
	             <option></option>
	             <% 	
	             	if(searchResults !=null)
	             		for(int i=0;i<searchResults.size();i++){
	             			PaymentModel payment_model = (PaymentModel)searchResults.get(i);
	             %>
	             <option value="<%=payment_model.getPaymentID() %>"><%=payment_model.getPaymentName() %></option>
	             <%} %>
	             </select>
             </td>
             </tr>
             <tr>
             <td>Sheet SQL Statement</td>
             <td><textarea <%=read%> rows="20" cols="100" name="<%=PaymentInterfaceKey.CONTROL_EXCEL_SHEET_SQL_STATEMENT %>" id="<%=PaymentInterfaceKey.CONTROL_EXCEL_SHEET_SQL_STATEMENT %>" ><%=model.getExcelSheetSqlStatement() %></textarea>
             </td>
             </tr>
             <tr>
             <td colspan="2" align="center">
	             <input class="button" type="button" name="Update" value="update" onclick="javascript:submitUpdate('<%=model.getPaymentExcelManagementId() %>','<%=model.getPaymentTypeId() %>','<%=model.getPaymentType() %>','<%=model.getExcelSheetNumber() %>')">
	             <input class="button" type="button" name="test_query" value="Test Query" onclick="javascript:submitTest()">
             </td>
             </tr>
             </TABLE>
             <%} %> 
              
             
             
             <%if(action.equals(MemoInterfaceKey.ACTION_VIEW_INSERT_MEMO_EXCEL_ATTRIBUTES)){ 
            	 
            %>
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            <TR class=TableHeader>
            <td colspan="2" align="center"> New</td>
            </TR>
             <tr >
             <td>Excel Sheet Number</td>
             <td><input type="text"  name="<%=PaymentInterfaceKey.CONTROL_NEW_EXCEL_SHEET_NUMBER%>" id="<%=PaymentInterfaceKey.CONTROL_NEW_EXCEL_SHEET_NUMBER%>">
             </td>
             </tr>
             <tr>
             <td>Excel Sheet Name</td>             
             <td><input type="text"  name="<%=PaymentInterfaceKey.CONTROL_NEW_EXCEL_SHEET_NAME%>" id="<%=PaymentInterfaceKey.CONTROL_NEW_EXCEL_SHEET_NAME%>">
             </td>
             </tr>
             
             <tr>
             <td>SQL Type</td>
             <td>
             <select name="templateType" id="" onchange="toggleChange()">
             <option value=""></option>
             <option value="specific">Specific</option>
             <option value="temp">Memo Excel Template</option>
             </select>
             </td>
             </tr>
             <tr id="1" style="display:none">
             <td>Sheet SQL Statement</td>
             <td><textarea rows="20" cols="20" name="<%=PaymentInterfaceKey.CONTROL_NEW_EXCEL_SHEET_SQL_STATEMENT %>" id="<%=PaymentInterfaceKey.CONTROL_NEW_EXCEL_SHEET_SQL_STATEMENT %>" ></textarea>
             </td>
             </tr>
             <tr>
             <td colspan="2" align="center"><input class="button" type="button" name="Insert" value="Insert" onclick="javascript:submitInsert('<%=paymentTypeId%>','<%=paymentTypeName %>')"></td>
             </tr>
             </TABLE>
             <%} %>

             </form>
    </body>
</html>