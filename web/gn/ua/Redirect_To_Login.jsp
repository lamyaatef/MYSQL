<%@ page contentType="text/html;charset=windows-1252"
                import="com.mobinil.sds.web.interfaces.*"
                import="com.mobinil.sds.web.interfaces.gn.ua.*"
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
</head>
<body>
<%
  String appName = request.getContextPath();
%>
<form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="UserLogin" method="post" target="_top">
      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>"
      value="<%out.print(UserAccountInterfaceKey.ACTION_SHOW_LOGIN_PAGE);%>">
</form>
<script>
  alert('You are logged out from the system.');
  UserLogin.submit();
</script>
</body>
</html>
