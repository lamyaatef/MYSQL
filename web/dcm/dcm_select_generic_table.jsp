<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"

%>
<html>
<head>

 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
  <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
</head>
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
 String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  
%>

<body>
<form name="genericTableSelectForm" action="" method="post">
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                " value=\""+strUserID+"\">");           
%>
<TABLE align="center" style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1>
  <tr class=TableHeader>
    <td width="354"><div align="right">TableName </div></td>
    <td width="367"><input type="text" name="<%=DCMInterfaceKey.CONTROL_TEXT_GENERIC_TABLE_NAME%>" /></td>
  </tr>
  <tr>
    <td colspan="2"><div align="center">
     <%out.println("<input name=\"Submit\" type=\"button\" class=\"button\" value=\"Next\" onclick=\"genericTableSelectForm."+InterfaceKey.HASHMAP_KEY_ACTION+
     ".value='"+DCMInterfaceKey.ACTION_SELECT_GENERIC_TABLE+"'; genericTableSelectForm.submit();\">");%>
    </div></td>
  </tr>
</table>
</form>
<p>&nbsp;</p>
</body>
</html>
