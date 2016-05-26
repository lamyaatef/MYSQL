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
        String formName = "payment_screen_mgt_form";
        String pageHeader="Admin Payment Screen Management";
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        Vector flag = (Vector) dataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_MANUAL_FLAG);                
        Vector dates = (Vector) dataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_AUTO_DATES);
        String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String manualOrAuto=(String)dataHashMap.get(PaymentInterfaceKey.CONTROL_WORKING_MODE);
                      
%>
<script language="JavaScript">
function viewWorkingMode()
{
	alert("The system is currently working in " + '<%=manualOrAuto%>'+" mode");

}

function submitUpdateFlag()
{
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_SET_PAYMENT_MANAGEMENT_MANUAL%>";
document.<%=formName%>.submit();
}
function submitUpdateDate()
{
	
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_SET_PAYMENT_MANAGEMENT_AUTO%>";
document.<%=formName%>.submit();

}
function toggleManual()
{

document.getElementById("1").style.display='';
document.getElementById("2").style.display='';
document.getElementById("3").style.display="none"
document.getElementById("4").style.display="none"
document.getElementById("5").style.display="none"
document.<%=formName%>.<%=PaymentInterfaceKey.CONTROL_MANUAL_OR_AUTO%>.value="MANUAL";

}

function toggleAutomatic()
{
document.getElementById("3").style.display='';
document.getElementById("4").style.display='';
document.getElementById("5").style.display='';
document.getElementById("1").style.display="none"
 document.getElementById("2").style.display="none"
 document.<%=formName%>.<%=PaymentInterfaceKey.CONTROL_MANUAL_OR_AUTO%>.value="AUTO";
}
//function drawCalender(argOrder,argValue)
//{
//document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(payment_screen_mgt_form."+argOrder+",'dd-mm-yyyy','Choose date')\">");
//document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
//}

function drawCalender(argOrder,argValue)
    {
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(payment_screen_mgt_form."+argOrder+",'mm/dd/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
 }
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/calendar.js"></SCRIPT>
        </head>
    <body>
        <center><h2> <%=pageHeader%></h2></center>

        <%
        
        %>
        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
            <input type="hidden" name="<%=PaymentInterfaceKey.CONTROL_MANUAL_OR_AUTO%>" value="AUTO">
            
            <script>
            viewWorkingMode();
            </script>
            
        <table style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="0">
         <tr>
              <td colspan="4" align="center">
              <%if(manualOrAuto.equals("MANUAL")){%>
              <input type="radio" name="ScreenManagement" checked="checked" value="Manual" onClick="javaScript:toggleManual();">Manual
               <%}else{ %>
              <input type="radio" name="ScreenManagement" value="Manual" onClick="javaScript:toggleManual();">Manual
              <%} %>
              </td>
        </tr>
        	
        <TR id="1"  style="display:none">
        
        <TD colspan="4" align="center">
        <br> 
        <select name="<%=PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_SCREEN_MANAGEMENT_FLAG%>" id="<%=PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_SCREEN_MANAGEMENT_FLAG%>">
             <%
             String status_selected=null;
             PaymentScreenManagementModel item=(PaymentScreenManagementModel) flag.get(0);
             
             if(item.getFlag().equalsIgnoreCase("Enabled"))
             status_selected="selected";
             else
             status_selected="";
             %>
             <option <%=status_selected%> >Enabled</option>
             <%
             if(item.getFlag().equalsIgnoreCase("Disabled"))
             status_selected="selected";
             else
             status_selected="";
             %>
             <option <%=status_selected%> >Disabled</option>
             </select>
        </TD>
        </TR>
        <TR id="2" style="display:none">  
                   
             <TD colspan="4" align="center"><BR><input class="button" type="button" value="Update" onclick="javascript:submitUpdateFlag()">
             </TD>
       </TR>
       <tr>
              <td colspan="4" align="center">
              <br>
              <%if(manualOrAuto.equals("AUTO")){%>
              <input type="radio" name="ScreenManagement" checked="checked" value="Automatic" onClick="javaScript:toggleAutomatic();">Automatic
              
              <%}else{ %>
              <input type="radio" name="ScreenManagement" value="Automatic" onClick="javaScript:toggleAutomatic();">Automatic
              <%} %>
              </td>
        </tr>
         
        <%PaymentScreenManagementModel item2=(PaymentScreenManagementModel) dates.get(0);
              int start=item2.getStartDate();
              
              
              int end=item2.getEndDate();
              
              %>
              <tr id="3" style="display:none">
              <td >              
              <b>Start Date:</b></td>
              <td>
              
              <input type="text" disabled="true" value="<%=item2.getStartDate()%>" name="PaymentInterfaceKey.CONTROL_CURRENT_START_DATE" id="PaymentInterfaceKey.CONTROL_CURRENT_START_DATE"></td>
              <td >
              
              <b>End Date:</b></td>
              <td >
              
              <input type="text" disabled="true" value="<%=item2.getEndDate()%>" name="PaymentInterfaceKey.CONTROL_CURRENT_END_DATE" id="PaymentInterfaceKey.CONTROL_CURRENT_END_DATE"></td>
              </tr>
              
              <tr id="4" style="display:none">
              <td><b>Choose Start Date</b></td>
              <td>
              <select name="<%=PaymentInterfaceKey.CONTROL_INSERTED_START_DATE%>" id="<%=PaymentInterfaceKey.CONTROL_INSERTED_START_DATE%>">
             <% for(int k=1;k<=28;k++){ %>
             <option value="<%=k%>"><%=k%></option>
             <%}%>
             </select>
              
              
              </td>
              
              <td><b>Choose End Date</b></td>
              <td>
              <select name="<%=PaymentInterfaceKey.CONTROL_INSERTED_END_DATE%>" id="<%=PaymentInterfaceKey.CONTROL_INSERTED_END_DATE%>">
             <% for(int k=1;k<=28;k++){ %>
             <option value="<%=k%>"><%=k%></option>
             <%}%>
             </select>
              
              </td>
              
              </tr>
              <TR id="5" style="display:none">
             <TD colspan="4" align="center"><BR><BR><input class="button" type="button" value="Update" onclick="javascript:submitUpdateDate();">
             </TD>
             </TR>
              
         
         </table>
         <%if(manualOrAuto.equals("MANUAL")){%>
            <script >
              toggleManual();
              </script>
              <%} %>
              <%if(manualOrAuto.equals("AUTO")){%>
              <script >
              toggleAutomatic();
              </script>
              <%} %>
            </form>
    </body>
</html>