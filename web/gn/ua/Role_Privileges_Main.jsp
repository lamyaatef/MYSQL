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
    <% showRoles(request, response, out, session); %>
  </HEAD>
       <!-- Header cut-->

<div id="header">
<p>
    <%
    /*out.println("<a href=\"javascript:"+
          "document.UserPrivileges."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+UserAccountInterfaceKey.ACTION_CHANGE_PASSWORD+"';"+
          "document.UserPrivileges."+InterfaceKey.HASHMAP_KEY_USER_ID+".value="+userID+";"+
          "document.UserPrivileges.target='rhs';"+
          "UserPrivileges.submit();\">");*/
    
    %>
    
    change password
<%
    out.println("</a>");
%>

    
    <%
          out.println("<a href=\"javascript:"+
          "document.UserPrivileges."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+UserAccountInterfaceKey.ACTION_LOGOUT+"';"+
          "document.UserPrivileges.target='_top';"+
          "UserPrivileges.submit();\">");

          %>
          &nbsp; - &nbsp; logout
<%
    out.println("</a>");
%>

</p>

</div>

<!-- Header End cut-->

<div class="clear"></div>

<!-- Header Title cut-->

<div class="header-title">
<h1>Welcome</h1>
</div>

<!-- Header Title End cut-->

<div class="clear"></div>

<!-- Pages -->

<div id="pages">
<p>Welcome</p>
</div>

<!-- Pages End -->

<div class="clear"></div>

<!-- Footer cut-->

<%@include file ="SDSFooterOrange.jsp" %>
<!-- Footer End cut-->

</div>
</div>    
</HTML>

<%!
  public void showRoles(HttpServletRequest request, HttpServletResponse response, JspWriter out, HttpSession session)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(dataHashMap != null)
    {
      if(! dataHashMap.isEmpty())
      {
        String strRoleID = (String)dataHashMap.get(RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID);
        session.setAttribute(RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID, strRoleID);
      }
    }
    
    
   out.println("<FRAMESET cols=200,*>");//cols=200,*> width=100%,>
   out.println("<FRAME name=lhs src=\""+appName+"/gn/ua/Show_Role_Privileges.jsp\" frameBorder=No noResize>");
   out.println("<FRAME name=rhs src=\"\" frameBorder=No noResize>");
   out.println("</FRAMESET>");
  // out.println("<jsp:forward page=\""+appName+"/gn/ua/Show_Role_Privileges.jsp\"></jsp:forward>");

  }
%>
