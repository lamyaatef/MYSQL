<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
%>
<HTML>
  <HEAD>
    <TITLE>:::: SDS ::::</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=windows-1256">
  </HEAD>
          <%showRoles(request, response, out, session);%>
</HTML>

<%!
  public void showRoles(HttpServletRequest request, HttpServletResponse response, JspWriter out, HttpSession session)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    out.println("<FRAMESET cols=200,*>"); //cols=200,*
    out.println("<FRAME name=lhs src=\""+appName+"/gn/ua/Choose_Role.jsp\" frameBorder=No noResize>");
    out.println("<FRAME name=rhs src=\"\" frameBorder=No noResize>");
    out.println("</FRAMESET>");
    
   // out.println("<jsp:forward page=\""+appName+"/gn/ua/Choose_Role.jsp\"> ");
   // out.println("</jsp:forward>");
  }
%>
