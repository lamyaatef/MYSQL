<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"

         import="com.mobinil.sds.core.system.sop.requests.model.*"

%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
<script>
    function checkBeforeSubmit()
    {
      
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strChannelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector requestNotificationList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);          
%>   
    <CENTER>
      <H2> Request Notification List </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
          " value=\""+strChannelId+"\">"); 

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_REQUEST_NOTIFICATION_ID+"\""+
                  " value=\""+"\">");                 
%> 
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <TD>Person Name</TD>
          <TD>E-mail</TD>
          <TD align='center'>Edit</TD>
          <TD align='center'>Delete</TD>
        </tr>
        <%
        for(int i=0;i<requestNotificationList.size();i++)
        {
        RequestNotificationModel requestNotificationModel = (RequestNotificationModel)requestNotificationList.get(i);
        String requestNotificationId = requestNotificationModel.getRequestNotificationId();
        String personFullName = requestNotificationModel.getPersonFullName();
        String personEmail = requestNotificationModel.getPersonEmail();
        %>
        <TR class=TableTextNote>
          <TD><%=personFullName%></TD>
          <TD><%=personEmail%></TD>
          <TD align='center'>
          <%
            out.print("<INPUT class=button type=button value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_EDIT_REQUEST_NOTIFICATION+"';"+
                      "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_REQUEST_NOTIFICATION_ID+".value = '"+requestNotificationId+"';"+
                      "SOPform.submit();\">");

          %>
          </TD>
          <TD align='center'>
          <%
            out.print("<INPUT class=button type=button value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_DELETE_REQUEST_NOTIFICATION+"';"+
                      "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_REQUEST_NOTIFICATION_ID+".value = '"+requestNotificationId+"';"+
                      "SOPform.submit();\">");

          %>
          </TD>
        </tr>
        <%
        }
        %>
      </table>  

    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Add New Request Notifiation \" name=\"addnewrequest\" id=\"addnewrequest\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_CREATE_REQUEST_NOTIFICATION+"';"+
                  "SOPform.submit();\">");
      %>
      </center>
       
</form>
</body>
</html>
