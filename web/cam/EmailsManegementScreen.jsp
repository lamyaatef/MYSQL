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
         %>
<%
        String appName = request.getContextPath();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String formName = "payment_mgt_form";
        String pageHeader="Email Notification";
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        Vector emailList=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_DCM_EMAIL_LIST);
        
        
%>
<script language="JavaScript">
function submitSearch()
{
if(document.getElementById("<%=PaymentInterfaceKey.CONTROL_SEARCH_SCM_NAME_NOTIFICATION%>").value == "" &&
document.getElementById("<%=PaymentInterfaceKey.CONTROL_SEARCH_SCM_EMAIL_NOTIFICATION%>").value == "")
{
alert("you have to insert search criteria");
return;
}
else
{

document.getElementById("<%=PaymentInterfaceKey.CONTROL_SEARCH_SCM_NAME_NOTIFICATION%>").value ;
document.getElementById("<%=PaymentInterfaceKey.CONTROL_SEARCH_SCM_EMAIL_NOTIFICATION%>").value ;
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_SEARCH_EMAILS_NOTIFICATION%>";
document.<%=formName%>.submit();
}
}

function submitNew()
{
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_NEW_EMAILS_NOTIFICATION%>";
document.<%=formName%>.submit();
}

function submitUpdateEmail(employeeName)
{
document.<%=formName%>.<%=PaymentInterfaceKey.CONTROL_HIDDEN_EMPLOYEE_NAME%>.value = employee_name;
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_UPDATE_EMAILS_NOTIFICATION%>";
document.<%=formName%>.submit();
}
function submitDelete(employeeName)
{
document.<%=formName%>.<%=PaymentInterfaceKey.CONTROL_HIDDEN_EMPLOYEE_NAME%>.value = employeeName;
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_DELETE_EMAIL_NOTIFICATION%>";
document.<%=formName%>.submit();
}
</script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    </head>
    <body>
        <center><h2> <%=pageHeader%></h2></center>

        <%
         
        %>
        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=PaymentInterfaceKey.CONTROL_HIDDEN_EMPLOYEE_NAME%>" value="">

            
             <%
               if(emailList!=null||emailList.size()!=0){
             %>
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
             <TR class=TableHeader>
              <td>Employee Name</td>
              <td>Employee Email</td>
              <td>Edit</td>
              <td>Delete</td>
              </tr>
              <%for(int i=0;i<emailList.size();i++){
              PaymentDcmEmailsModel scmEmails=(PaymentDcmEmailsModel)emailList.get(i);
              %>
              <tr>
              <td><%=scmEmails.getScmName()%></td>
              <td><%=scmEmails.getScmEmail()%></td>
              <td><input class=button type="button" name="Edit" value="Edit"
                               onclick="submitUpdateEmail('<%=scmEmails.getScmName()%>');"></td>
              <td><input class=button type="button" name="Delete" value="Delete"
                               onclick="submitDelete('<%=scmEmails.getScmName()%>');"></td>
              
              </tr>
              <%}%>
             </table>
             <%}%>
             
             <table  cellspacing="2" cellpadding="1" border="0" width="100%">
                <tr>             <br><br>
                    <td align=center>
                        <input class=button type="button" name="new" value="New Email Notification"
                               onclick="submitNew();">
                                &nbsp;
                        
                    </td>
                    
                     
                </tr>
            </table>

        </form>
    </body>
</html>