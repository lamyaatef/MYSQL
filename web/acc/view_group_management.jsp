<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.acc.*"

         import="com.mobinil.sds.core.system.acc.model.*"
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
  Vector groups = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  %>
  <CENTER>
      <H2> Transaction Type Group List</H2>
    </CENTER>
    <form name='ACCform' id='ACCform' action='' method='post'>
      <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_GROUP_ID+"\""+
          " value=\""+"\">"); 
  
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>
<tr class=TableHeader>
      <td align='center'>Group ID</td>
      <td align='center'>Group Name</td>
      <td align='center'>Edit</td>
        <td align='center'>Delete</td>
      </tr>
      <%
      for (int i =0;i<groups.size();i++)
      {
    	  GroupModel groupModel = (GroupModel)groups.get(i);
    	  String groupId = groupModel.getGroupId();
    	  String groupName = groupModel.getGroupName();
    	  %>
    	  <tr class=<%=InterfaceKey.STYLE[i%2]%>>
      	<td><%=groupId%></td>
      	<td><%=groupName%></td>
      	 <td align='center'>
          <%
              out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_EDIT_TRANSACTIONTYPE_GROUP+"';"+
                  "document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_GROUP_ID+".value = '"+groupId+"';"+
                  "ACCform.submit();\">");
          %>
           </td>
          <td align='center'>
          <%
              out.print("<INPUT class=button type='button' value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_DELETE_TRANSACTIONTYPE_GROUP+"';"+
                  "document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_GROUP_ID+".value = '"+groupId+"';"+
                  "ACCform.submit();\">");
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
      out.print("<INPUT class=button type='button' value=\" Create New Transaction Type Group \" name=\"new\" id=\"new\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_CREATE_NEW_TRANSACTIONTYPE_GROUP+"';"+
                  "ACCform.submit();\">");
%>
       </center>
       </form>
</body>
</html>