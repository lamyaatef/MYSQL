  <%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
     	 import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"
         import = "com.mobinil.sds.core.system.dm.file.model.*"
       	 import="com.mobinil.sds.core.system.authenticationResult.model.*"
%>
<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>     
    </head>
<body>
<% 
HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector vecUserLabel = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String strMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+strMsg+"');</script>");
  }
  %>
  <CENTER>
      <H2> User Label List</H2>
    </CENTER>
    <form  name='AUTHform' id='AUTHform' action='' method='post'>
     <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+AuthResInterfaceKey.INPUT_HIDDEN_USER_ID+"\""+
          " value=\""+"\">"); 

  
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
<tr class=TableHeader>
      <td align='center'>User ID</td>
      <td align='center'>User Name</td>
      <td align='center'>User Mail</td>
        <td align='center'>Delete</td>
     
      </tr>
      <%
      for (int i =0;i<vecUserLabel.size();i++)
      {
    	  AuthResUserLabelModel authResUserLabelModel = (AuthResUserLabelModel)vecUserLabel.get(i);
    	  String userId = authResUserLabelModel.getUserId();
    	  String userName = authResUserLabelModel.getUserId();
    	  String userMail = "";//authResUserLabelModel.getUserMail();
    	  
    	  %>
    	  <tr class=<%=InterfaceKey.STYLE[i%2]%>>
      	<td><%=userId%></td>
      	<%
          out.println("<TD><a href=\"#\" onclick=\"document.AUTHform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AuthResInterfaceKey.ACTION_VIEW_LABEL_LIST+"';"+
        		  "document.AUTHform."+AuthResInterfaceKey.INPUT_HIDDEN_USER_ID+".value = '"+userId+"';"+
                      "AUTHform.submit();\">"+userName+"</a></TD>");
          %>
      	<td><%=userMail%></td>
      	 
          
          <td align='center'>
          <%
              out.print("<INPUT class=button type='button' value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.AUTHform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AuthResInterfaceKey.ACTION_DELETE_USER+"';"+
            		  "document.AUTHform."+AuthResInterfaceKey.INPUT_HIDDEN_USER_ID+".value = '"+userId+"';"+
                  "AUTHform.submit();\">");
          %>
           </td>
           
      	</tr>
    	  <%
      }
      %>
      
       </TABLE>
       </center>
       <br><br>
       <center>
       <%
      out.print("<INPUT class=button type='button' value=\" Add New User  \" name=\"new\" id=\"new\" onclick=\"document.AUTHform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AuthResInterfaceKey.ACTION_ADD_NEW_USER+"';"+
                  "AUTHform.submit();\">");
%>
       </center>
       </form>
</body>
</html>