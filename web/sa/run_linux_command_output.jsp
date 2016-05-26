<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
%>
<html>
<head>

 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
  <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
</head>
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  String strLinuxCommandResult = (String)objDataHashMap.get(AdministrationInterfaceKey.LINUX_COMMAND_RESULT);
  out.println(strLinuxCommandResult);
%>
</body>
</html>
