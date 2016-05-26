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

String memoTypeId=(String)dataHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID);
String memoTypeName=(String)dataHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_TYPE_NAME);

String paymentTypeId=(String)dataHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID);
String paymentTypeName=(String)dataHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME); 
                     
                     
%>
<script language="JavaScript">
function submitView(paymentTypeId,paymentTypeName)
{
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID%>.value = paymentTypeId;
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME%>.value = paymentTypeName;	
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_VIEW_MEMO_EXCEL_ATTRIBUTES%>";
	document.<%=formName%>.submit();
}
function alertNoData()
{
	alert("There is no Excel Attributes for this Payment Type");
}
function submitViewEdit(payementExcelId)
{
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_EXCEL_MANAGEMENT_ID%>.value = payementExcelId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_VIEW_UPDATE_MEMO_EXCEL_ATTRIBUTES%>";
	document.<%=formName%>.submit();
}
function submitEdit(payementExcelId)
{
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_EXCEL_MANAGEMENT_ID%>.value = payementExcelId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_UPDATE_MEMO_EXCEL_ATTRIBUTES%>";
	document.<%=formName%>.submit();
}
function submitInsert(paymentTypeId)
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
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_INSERT_MEMO_EXCEL_ATTRIBUTES%>";
	document.<%=formName%>.submit();
}
function submitViewInsert(paymentTypeId,paymentTypeName)
{
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID%>.value = paymentTypeId;
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME%>.value = paymentTypeName;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_VIEW_INSERT_MEMO_EXCEL_ATTRIBUTES%>";
	document.<%=formName%>.submit();
}
function alertNotUnique()
{
	alert("The Sheet Number you entered is already exist");
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
            <input type="hidden" name="<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID %>" value="-1">
            <input type="hidden" name="<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME %>" value="-1">
            <input type="hidden" name="<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_EXCEL_MANAGEMENT_ID %>" value="-1">
            
            <%if(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_MEMO_EXCEL_MANAGEMENT)){ %>
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableHeader>
              <td>Payment Type Name</td>
             <td>View Excel Attributes</td> 
              </TR>
              
              <%
             Vector paymentTypes=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES);
             if(paymentTypes!=null||paymentTypes.size()!=0){
            	
             for(int i=0;i<paymentTypes.size();i++)
             {
            	 PaymentTypeModel paymentType=(PaymentTypeModel)paymentTypes.get(i);
             %>
             <tr >
             <td ><%=paymentType.getPaymentTypeName() %> </td>
             <td><input class="button" type="button" name="View" value="View" onclick="javascript:submitView('<%=paymentType.getPaymentTypeId()%>','<%=paymentType.getPaymentTypeName() %>')"></td>
             
             </tr>
             <%}}%>
                 
             </TABLE>
             <%} %>
             
             
             <%if(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_MEMO_EXCEL_ATTRIBUTES)){
            	 
                 String msg=(String)dataHashMap.get(MemoInterfaceKey.EXCEL_SHEET_MESSAGE);
                 
            	 %>
            <%if(msg!=null && msg.compareTo("NOT_UNIQUE")==0){ %>
            <script>
            alertNotUnique();
            </script>
            	 
            	 <%} %>
             
              <%
              Vector PaymentTypeExcelModels=(Vector)dataHashMap.get(PaymentInterfaceKey.PAYMENT_TYPE_EXCEL_ATTRIBUTE);
              //System.out.println("excel attributes models size: "+PaymentTypeExcelModels.size());
             if( PaymentTypeExcelModels.size()!=0&&PaymentTypeExcelModels!=null){
            	 System.out.println("hellllloooooo");
            	
             %>
              <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableHeader>
                <td colspan="4" align="center" ><%=paymentTypeName %> </td> 
              </TR>
              <tr class=TableHeader>
              <td>Excel Sheet Number</td>
              <td>Excel Sheet Name</td>
              <td>Sheet SQL Statement</td> 
              <td>Edit</td>  
              </tr>
              
              <% for(int i=0;i<PaymentTypeExcelModels.size();i++){
             	 
                  PaymentTypeExcelModel model=(PaymentTypeExcelModel)PaymentTypeExcelModels.get(i);
                  %>
              
             <tr >
             
             <td><%=model.getExcelSheetNumber() %></td>
                       
             <td><%=model.getExcelSheetName() %></td>
             
             <td><%=model.getExcelSheetSqlStatement() %>
             </td>
             <td><input class="button" type="button" name="Edit" value="Edit" onclick="javascript:submitViewEdit('<%=model.getPaymentExcelManagementId() %>')"></td>
             </tr>
             <%} %>
            
             </TABLE>
             <br>
             
             <%} %>
             <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=0>
             <tr>
             <td colspan="3" align="center"><input class="button" type="button" name="Insert" value="Insert New Sheet Attributes For this Payment Type" onclick="javascript:submitViewInsert('<%=paymentTypeId%>','<%=paymentTypeName %>')"></td>
             </tr>
             </table>
             <%} %>
             </form>
    </body>
</html>