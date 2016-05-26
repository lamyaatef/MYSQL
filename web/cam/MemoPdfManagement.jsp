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
String pageHeader="Memo PDF Management";

HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
String action=  (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);

String paymentTypeId=(String)dataHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID);
String paymentTypeName=(String)dataHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME); 
                     
%>
<script language="JavaScript">
function submitView(paymentTypeId,paymentTypeName)
{
	
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID%>.value = paymentTypeId;
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME%>.value = paymentTypeName;	
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_VIEW_MEMO_PDF_ATTRIBUTES%>";
	document.<%=formName%>.submit();
}
function alertNoData()
{
	alert("There is no Excel Attributes for this Payment Type");
}
function submitViewEdit(payementExcelId)
{
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_EXCEL_MANAGEMENT_ID%>.value = payementExcelId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_VIEW_UPDATE_PAYMENT_EXCEL_ATTRIBUTES%>";
	document.<%=formName%>.submit();
}
function submitEdit(payementExcelId)
{
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_EXCEL_MANAGEMENT_ID%>.value = payementExcelId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_UPDATE_MEMO_PDF_ATTRIBUTES%>";
	document.<%=formName%>.submit();
}
function submitInsert(paymentTypeId)
{
	
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID%>.value = paymentTypeId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_INSERT_MEMO_PDF_ATTRIBUTES%>";
	document.<%=formName%>.submit();
}
function submitViewInsert(paymentTypeId,paymentTypeName)
{
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID%>.value = paymentTypeId;
	document.<%=formName%>.<%=PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME%>.value = paymentTypeName;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=MemoInterfaceKey.ACTION_VIEW_INSERT_MEMO_PDF_ATTRIBUTES%>";
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
            <input type="hidden" name="<%=MemoInterfaceKey.HIDDEN_SQL_TEMPLATE_ID %>" value="-1">
            
            <%if(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_MEMO_PDF_MANAGEMENT)){ %>
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
             
             
             <%if(action.equalsIgnoreCase(MemoInterfaceKey.ACTION_VIEW_MEMO_PDF_ATTRIBUTES)){
           	 %>
            <%
              Vector PaymentTypePdfModels=(Vector)dataHashMap.get(PaymentInterfaceKey.PAYMENT_TYPE_PDF_ATTRIBUTE);
              //System.out.println("excel attributes models size: "+PaymentTypeExcelModels.size());
              
             if( PaymentTypePdfModels.size()!=0&&PaymentTypePdfModels!=null){
            	 
            	 PaymentTypePDFModel model=(PaymentTypePDFModel)PaymentTypePdfModels.get(0);
            	 String read="";
            	 if(model.getTempId()!=-1)
                	 read="readonly";
                	 else
                		 read="";
             %>
              <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableHeader>
                <td colspan="2" align="center" ><%=paymentTypeName %> </td> 
              </TR>
              <tr class=TableHeader>
              <td>Queries Params</td>
              <td>PDF SQL Statement</td> 
                
              </tr>
              
             <tr >            
             <td><input type="text" value="<%=model.getQueriesParams() %>" name="<%=PaymentInterfaceKey.CONTROL_QUERIES_PARAMS%>" id="<%=PaymentInterfaceKey.CONTROL_QUERIES_PARAMS%>">
             <td><textarea <%=read%> rows="20" cols="20"  name="<%=PaymentInterfaceKey.CONTROL_PDF_SQL_STATEMENT%>" id="<%=PaymentInterfaceKey.CONTROL_PDF_SQL_STATEMENT%>" ><%=model.getPdfSql() %></textarea></td>
             </tr>
             
            <tr>
            <td colspan="2" align="center"><input class="button" type="button" name="Update" value="Update" onclick="javascript:submitEdit('<%=model.getPaymentPdfManagmentId() %>')"></td>
            </tr>
            
             </TABLE>
             <br>
             
             <%}else{ %>
             <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=0>
             <tr>
             <td colspan="3" align="center"><input class="button" type="button" name="Insert" value="Insert Memo PDF Attributes For this Payment Type" onclick="javascript:submitViewInsert('<%=paymentTypeId%>','<%=paymentTypeName %>')"></td>
             </tr>
             </table>
             <%} %>
             
             <%} %>
             
             
              <%if(action.equals(MemoInterfaceKey.ACTION_VIEW_INSERT_MEMO_PDF_ATTRIBUTES)){ 
            	 
            %>
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            <TR class=TableHeader>
            <td colspan="2" align="center"> New</td>
            </TR>
             <tr>
             <td>Queries Params</td>             
             <td><input type="text"  name="<%=PaymentInterfaceKey.CONTROL_NEW_QUERIES_PARAMS%>" id="<%=PaymentInterfaceKey.CONTROL_NEW_QUERIES_PARAMS%>">
             </td>
             </tr>
             <tr>
             <td>SQL Type</td>
             <td>
             <select name="templateType" id="" onchange="toggleChange()">
             <option value=""></option>
             <option value="specific">Specific</option>
             <option value="temp">Memo PDF Template</option>
             </select>
             </td>
             </tr>
             <tr id="1" style="display:none">
             <td>PDF SQL Statement</td>
             <td><textarea rows="20" cols="20" name="<%=PaymentInterfaceKey.CONTROL_NEW_PDF_SQL_STATEMENT %>" id="<%=PaymentInterfaceKey.CONTROL_NEW_PDF_SQL_STATEMENT %>" ></textarea>
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