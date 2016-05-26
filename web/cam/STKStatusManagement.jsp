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
String pageHeader="STK Status Management";


HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
String action=  (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
                     
%>
<script language="JavaScript">
function submitSearch( )
{

if (document.getElementById("<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>").value == "" &&
document.getElementById("<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME%>").value == "" && document.getElementById("<%=PaymentInterfaceKey.CONTROL_STK_NUMBER%>").value == "" )
{
alert("there is no criteria");
return ;
}
else 
{
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_SEARCH_STK_STATUS_MANAGEMENT%>";
document.<%=formName%>.submit();
}
}
function submitUpdate(scmStatusId)
{
	document.<%=formName%>.<%=PaymentInterfaceKey.CONTROL_HIDDEN_SCM_PAYMENT_ID%>.value = scmStatusId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_UPDATE_STK_STATUS_MANAGEMENT%>";
	document.<%=formName%>.submit();
}
function DCMNotExist()
{
	alert("There Is No DCM BY These Criteria");
}
function DCMSTKNotExist()
{
	alert("This DCM Has No STK Number In The Accounting Module"); 
}
function submitAdd()
{
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_INSERT_NEW_DCM_STK_STATUS%>";
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
            <input type="hidden" name="<%=PaymentInterfaceKey.CONTROL_HIDDEN_SCM_PAYMENT_ID%>" value="-1">
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <TR class="TableHeader">
            <td colspan="4" align="center">Search</td>
            </TR> 
            <tr>
            <td>DCM Name</td>
            <td><input type="text" name="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME%>" id="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME%>"></td>
            <td>DCM Code</td>
            <td><input type="text" name="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>" id="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>"></td>
            </tr>
            <tr>
            <td>STK Number</td>
            <td><input type="text" name="<%=PaymentInterfaceKey.CONTROL_STK_NUMBER%>" id="<%=PaymentInterfaceKey.CONTROL_STK_NUMBER%>"></td>
            </tr>
             <tr>
             <td colspan="4" align="center"><input class="button" type="button" value="  Search  " onclick="javascript:submitSearch()"></td>
             </tr>
             
             
            </table>
            <br><br>
            <%
            if (action.equals(PaymentInterfaceKey.ACTION_SEARCH_STK_STATUS_MANAGEMENT) )
            {
            	//the results from search
            	//the status - reason part
            	Vector dcmStatusModel=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_DCM_DETAILS_STATUS);
            	//System.out.println("in jsp dcm model size= "+dcmStatusModel.size());
            	Vector stkStatusCases=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_STK_STATUS_CASES);
       	       	
            	
            %>
            
            
            <%if (dcmStatusModel.size()!=0 ){
            	PaymentImportModel dcmModel=(PaymentImportModel)dcmStatusModel.get(0);
            	 %>
            	 <%if(dcmModel.getStkNumberStatus()!=null){ %>
            	 <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <TR class="TableHeader">
            <td colspan="8" align="center">DCM Status</td>
            </TR>
            <TR class="TableHeader">
            <td>DCM Name</td>
            <td>DCM Code</td>
            <td>Status</td>
            <td>Reason</td>
            <td>STK Number</td>
            <td>STK Number Status</td>
             <td colspan="2" align="center">Comment</td>
            </tr>
            <tr>
            <td><%=dcmModel.getScmName()%></td>
            <td><%=dcmModel.getScmCode()%></td>
            <td><%=dcmModel.getScmStatus()%></td>
            <td><%=dcmModel.getReason()%></td>
            <td><%=dcmModel.getStkNumber()%></td>
            
            <td>
            <select name="<%=PaymentInterfaceKey.CONTROL_SELECT_STK_STATUS %>" id="<%=PaymentInterfaceKey.CONTROL_SELECT_STK_STATUS %>">
            <%String status_selected=""; 
            for(int i=0;i<stkStatusCases.size();i++){
            	STKStatusModel model=(STKStatusModel)stkStatusCases.get(i);
            	if(model.getStkStatus().compareTo(dcmModel.getStkNumberStatus().toString())==0)
            			status_selected="selected";
            	else 
            		status_selected="";
            	%>
            	<option <%=status_selected %> value="<%=model.getStkId() %>"><%=model.getStkStatus() %></option>
            	<%} %>
            </select>           
            </td>
             <td colspan="2"><input type="text" name="<%=PaymentInterfaceKey.CONTROL_STK_STATUS_COMMENT%>" id="<%=PaymentInterfaceKey.CONTROL_STK_STATUS_COMMENT%>" value="<%=dcmModel.getStkStatusComment() %>"> </td>
            </tr>
            
            
            
            <tr>
             <td colspan="8" align="center"><input class="button" type="button" value="  Update  " onclick="javascript:submitUpdate('<%=dcmModel.getId() %>')"></td>
             </tr>
            
            </TABLE>
            <%}else{ %>
            <script>
            DCMSTKNotExist();
            </script> 
                           
            <%} %>
            <%}else{ %>
            <%//there is no dcm by these criteria %>
            <script>
            DCMNotExist();
            </script>
            <%} %>
            <br>
            <%
            }%>
       </form>
    </body>
</html>