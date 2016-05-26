<%-- 
    Document   : pos_data_entry_alert
    Created on : Oct 24, 2010, 12:47:44 PM
    Author     : Salma
--%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*"
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey"
         import="com.mobinil.sds.core.system.request.model.SupervisorModel"
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/yav-style.css">
        <title>POS Pre-Request Data Entry</title>
    </head>
<%

HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

String alert = (String) objDataHashMap.get(SCMInterfaceKey.REP_KIT_Alert);
String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

%>
<body onload="divAppearance()">
        
 <form id="formRepKit" name="formRepKit" action="" method=post>
 <input type="hidden" name=<%=InterfaceKey.HASHMAP_KEY_ACTION%> value="<%= SCMInterfaceKey.ACTION_VIEW_POS_DATA_MANAGEMENT %>">
 <input type=hidden name=<%=InterfaceKey.HASHMAP_KEY_USER_ID%> value=<%=strUserID %> >
 <input type="hidden" name=<%=SCMInterfaceKey.REP_KIT_Alert%> value="<%= alert %>">
 <BR><BR>
     <center><%= alert %></center>
     <br>
 <div name="alertDiv" id="alertDiv" style="visibility:hidden">
     <%
       if(alert=="POS Data Entered Successfully .." )
        {
              %>
              <center>
                <input class=button  name="backButton" id="backButton" value="Back" type=button onclick="formRepKit.submit();" />
             </center>
              <%
        }
       else  if(alert=="POS Data Updated Successfully ..")
          {
          %>
            <center>
               <input class=button  name="backButton" id="backButton" value="Back" type=button onclick="formRepKit.submit();" />
            </center>
          <%
           }
       else
        {
              %>
             <center>
                <input class=button  name="backButton" id="backButton" value="Back" type=button onclick="history.go(-1);return false;" />
             </center>
              <%
        }
     %>
    
 </div>
 </form>
 
    </body>
</html>
<script>
function divAppearance()
{
	if(eval("document.formRepKit.<%=SCMInterfaceKey.REP_KIT_Alert%>.value") != "")
	{
		 document.getElementById("alertDiv").style.visibility = 'visible';
	}
	
}








</script>