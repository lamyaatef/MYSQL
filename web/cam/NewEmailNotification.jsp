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
        String formName = "Email_notification_mgt_form";
        
        String pageHeader=null;
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        if(dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION).toString().equals(PaymentInterfaceKey.ACTION_UPDATE_EMAILS_NOTIFICATION))
        {
        	pageHeader="Edit Email";
        }else if(dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION).toString().equals(PaymentInterfaceKey.ACTION_NEW_EMAILS_NOTIFICATION) ||
                dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION).toString().equals(PaymentInterfaceKey.ACTION_INSERT_NEW_EMAIL_NOTIFICATION))
        {
        	pageHeader="New Email Notification";	
        }
        String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        Vector emailList=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_DCM_EMAIL_LIST);
        String exist=(String)dataHashMap.get(PaymentInterfaceKey.CONTROL_EXISTING_EMAIL_NOTIFICATION);    

        
        
        
        
        
%>

<script language="javascript">
function exist()
{
alert("it is already exist");

}
function submitSearch()
{
if(document.getElementById("<%=PaymentInterfaceKey.CONTROL_SEARCH_SCM_NAME_NOTIFICATION%>").value == "" )
{
alert("you have to insert search criteria");
return;
}
else
{

document.getElementById("<%=PaymentInterfaceKey.CONTROL_SEARCH_SCM_NAME_NOTIFICATION%>").value ;
document.getElementById("<%=PaymentInterfaceKey.CONTROL_SEARCH_SCM_EMAIL_NOTIFICATION%>").value ;
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_SEARCH_EMAILS_NOTIFICATION_FOR_EDIT%>";
document.<%=formName%>.submit();
}
}


function submitInsert()
{
if(document.getElementById("<%=PaymentInterfaceKey.CONTROL_NEW_SCM_EMAIL_NOTIFICATION%>").value == "" &&
document.getElementById("<%=PaymentInterfaceKey.CONTROL_NEW_EMAIL_NOTIFICATION%>").value == "")
{
alert("there is no data");

return;
}
    //alert(document.getElementById("<%=PaymentInterfaceKey.CONTROL_NEW_EMAIL_NOTIFICATION%>").value);
    var str=document.getElementById("<%=PaymentInterfaceKey.CONTROL_NEW_EMAIL_NOTIFICATION%>").value;
    var at="@";
		var dot=".";
		var lat=str.indexOf(at);
		var lstr=str.length;
		var ldot=str.indexOf(dot);
    

	    // check if '@' is at the first position or  at last position or absent in given email 
		if (str.indexOf(at)==-1 || str.indexOf(at)==0 ||
                str.indexOf(at)==lstr){
		   alert("Invalid E-mail ID ");
		   return ;
		}

        // check if '.' is at the first position or at last position or absent in given email
		if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 ||
                str.indexOf(dot)==lstr){
		    alert("Invalid E-mail ID ");
		    return ;
		}

        // check if '@' is used more than one times in given email
		if (str.indexOf(at,(lat+1))!=-1){
		    alert("Invalid E-mail ID ");
		    return ;
		 }
   

         // check for the position of '.'
		 if (str.substring(lat-1,lat)==dot || 
                 str.substring(lat+1,lat+2)==dot){
		    alert("Invalid E-mail ID ");
		    return ;
		 }

         // check if '.' is present after two characters from location of '@'
		 if (str.indexOf(dot,(lat+2))==-1){
		    alert("Invalid E-mail ID ");
		    return ;
		 }		

		 // check for blank spaces in given email
		 if (str.indexOf(" ")!=-1){
		    alert("Invalid E-mail ID " );
		    return ;
		 }
document.getElementById("<%=PaymentInterfaceKey.CONTROL_NEW_SCM_EMAIL_NOTIFICATION%>").value ;
document.getElementById("<%=PaymentInterfaceKey.CONTROL_NEW_EMAIL_NOTIFICATION%>").value ;
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_INSERT_NEW_EMAIL_NOTIFICATION%>";
document.<%=formName%>.submit();
}
function submitUpdate(employeeName)
{
document.getElementById("<%=PaymentInterfaceKey.CONTROL_HIDDEN_EMPLOYEE_NAME%>").value = employeeName ;
var str=document.getElementById("<%=PaymentInterfaceKey.CONTROL_HIDDEN_SCM_EMAIL%>").value;
var at="@";
	var dot=".";
	var lat=str.indexOf(at);
	var lstr=str.length;
	var ldot=str.indexOf(dot);


    // check if '@' is at the first position or  at last position or absent in given email 
	if (str.indexOf(at)==-1 || str.indexOf(at)==0 ||
            str.indexOf(at)==lstr){
	   alert("Invalid E-mail ID ");
	   return ;
	}

    // check if '.' is at the first position or at last position or absent in given email
	if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 ||
            str.indexOf(dot)==lstr){
	    alert("Invalid E-mail ID ");
	    return ;
	}

    // check if '@' is used more than one times in given email
	if (str.indexOf(at,(lat+1))!=-1){
	    alert("Invalid E-mail ID ");
	    return ;
	 }


     // check for the position of '.'
	 if (str.substring(lat-1,lat)==dot || 
             str.substring(lat+1,lat+2)==dot){
	    alert("Invalid E-mail ID ");
	    return ;
	 }

     // check if '.' is present after two characters from location of '@'
	 if (str.indexOf(dot,(lat+2))==-1){
	    alert("Invalid E-mail ID ");
	    return ;
	 }		

	 // check for blank spaces in given email
	 if (str.indexOf(" ")!=-1){
	    alert("Invalid E-mail ID " );
	    return ;
	 }
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_DO_UPDATE_EMAILS_NOTIFICATION%>";
document.<%=formName%>.submit();

}

function emailCheck(str)
{
		var at="@";
		var dot=".";
		var lat=str.indexOf(at);
		var lstr=str.length;
		var ldot=str.indexOf(dot);
    

	    // check if '@' is at the first position or  at last position or absent in given email 
		if (str.indexOf(at)==-1 || str.indexOf(at)==0 ||
                str.indexOf(at)==lstr){
		   alert("Invalid E-mail ID");
		   return ;
		}

        // check if '.' is at the first position or at last position or absent in given email
		if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 ||
                str.indexOf(dot)==lstr){
		    alert("Invalid E-mail ID");
		    return ;
		}

        // check if '@' is used more than one times in given email
		if (str.indexOf(at,(lat+1))!=-1){
		    alert("Invalid E-mail ID");
		    return ;
		 }
   

         // check for the position of '.'
		 if (str.substring(lat-1,lat)==dot || 
                 str.substring(lat+1,lat+2)==dot){
		    alert("Invalid E-mail ID");
		    return ;
		 }

         // check if '.' is present after two characters from location of '@'
		 if (str.indexOf(dot,(lat+2))==-1){
		    alert("Invalid E-mail ID");
		    return ;
		 }		

		 // check for blank spaces in given email
		 if (str.indexOf(" ")!=-1){
		    alert("Invalid E-mail ID");
		    return ;
		 }

 		 return true;					
	
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
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
            <input type="hidden" name="<%=PaymentInterfaceKey.CONTROL_HIDDEN_EMPLOYEE_NAME%>" value="">
            <%
            //from the original page to search for update  
            %>
            
            <%
            if(dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION).toString().equals(PaymentInterfaceKey.ACTION_UPDATE_EMAILS_NOTIFICATION)){
            %>          
            
              <%if(emailList!=null||emailList.size()!=0){%>
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            <%for(int i=0;i<emailList.size();i++){
              PaymentDcmEmailsModel scmEmails=(PaymentDcmEmailsModel)emailList.get(i);
              
              %>
             <TR >
              <td>Employee Name</td>              
              <td><input type="text" name="<%=PaymentInterfaceKey.CONTROL_HIDDEN_SCM_NAME_EMAIL%>" id="<%=PaymentInterfaceKey.CONTROL_HIDDEN_SCM_NAME_EMAIL%>" value="<%=scmEmails.getScmName()%>">
              </td>
              </tr>
              <tr>
              <td>Employee Email</td>
              <td><input type="text" name="<%=PaymentInterfaceKey.CONTROL_HIDDEN_SCM_EMAIL%>" id="<%=PaymentInterfaceKey.CONTROL_HIDDEN_SCM_EMAIL%>" value="<%=scmEmails.getScmEmail()%>">
              </td>
              </tr>
               
              <TR>             
             <TD colspan="2" align="center"><BR><input class="button" type="button" value="Update" onclick="javascript:submitUpdate('<%=scmEmails.getScmName()%>');">
             </TD>
             </TR>
              
              <%}%>
             </table>
             
            <%}}%>

            <%// for insert new scm email
            %>

            
            <%
            if(dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION).toString().equals(PaymentInterfaceKey.ACTION_NEW_EMAILS_NOTIFICATION) ||
            dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION).toString().equals(PaymentInterfaceKey.ACTION_INSERT_NEW_EMAIL_NOTIFICATION))
             {
            %>
            <%        //it is already exist name
            if(exist!=null&&exist.length()!=0)
            {%>
            <script>
            exist();
            </script>
            <%}%>
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
             <TR class=TableHeader>
              <td colspan="2" align="center">New</td>
              </tr>
              <tr>
              <td>Employee Name</td>              
              <td ><input type="text" name="<%=PaymentInterfaceKey.CONTROL_NEW_SCM_EMAIL_NOTIFICATION%>" id="<%=PaymentInterfaceKey.CONTROL_NEW_SCM_EMAIL_NOTIFICATION%>"></td>
              </tr>
              <tr>
              <td>Employee Email</td>
              <td ><input type="text" name="<%=PaymentInterfaceKey.CONTROL_NEW_EMAIL_NOTIFICATION%>" id="<%=PaymentInterfaceKey.CONTROL_NEW_EMAIL_NOTIFICATION%>"></td>
              </tr>
              <TR>             
             <TD colspan="2" align="center"><BR><input class="button" type="button" value="Add New" onclick="javascript:submitInsert();">
             </TD>
             </TR>
             </table>
             <%}%>


            
             
            

        </form>
    </body>
</html>