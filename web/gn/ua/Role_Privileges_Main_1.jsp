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
<%
/**
 * Show_Role_Privileges.jsp:
 * Display the active privileges of active modules grouped by modules
 * assigned to the current active role of a user.
 * 
 * showPrivileges(HttpServletRequest request, HttpServletResponse response, JspWriter out, HttpSession session): 
 * Display the active privileges of active modules grouped by modules.
 *
 * @version	1.01 March 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 
    
    System.out.println("show privileges jsp");

     //String appName = request.getContextPath();
      //String userID = (String)session.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
%>
<HTML>
  <HEAD>
    <TITLE>:::: SDS ::::</TITLE>
    <%@include file ="SDSMetaOrange.jsp" %>
  <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/style.css">
    
  </HEAD>
  <%--
  <div class="col-md-10">
<img src="../resources/images/logo.jpg" class="logo-img" alt="">
    <% showRoles(request, response, out, session);%>
<!-- Header cut-->

<div id="header">
<p>
    <%
     
    out.println("<a href=\"javascript:"+
          "document.UserPrivileges."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+UserAccountInterfaceKey.ACTION_CHANGE_PASSWORD+"';"+
          "document.UserPrivileges."+InterfaceKey.HASHMAP_KEY_USER_ID+".value="+userID+";"+
          "document.UserPrivileges.target='rhs';"+
          "UserPrivileges.submit();\">");
    
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

    
</div>

<!-- Pages End -->

<div class="clear"></div>

<!-- Footer cut-->

<%@include file ="SDSFooterOrange.jsp" %>
<!-- Footer End cut-->

</div>
  
  
  --%>
  
          
          
</HTML>

<%!
  public void showRoles(HttpServletRequest request, HttpServletResponse response, JspWriter out, HttpSession session)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    String userID = (String)session.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
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
    
   out.println("<FRAMESET width=100%,>");//cols=200,*> width=100%,>
   
   out.println("<div class=\"col-md-2\">");



    out.println("<div id=\"main-menu\">");
    out.println("<img src=\"../resources/images/logo.jpg\" class=\"logo-img\" alt=\"\">");
    
   
   
    out.println("<FRAME name=lhs src=\""+appName+"/gn/ua/Show_Role_Privileges.jsp\" frameBorder=No noResize>");
   out.println ("</div></div>");
   
   out.println("<div class=\"col-md-10\">");


out.println("<div id=\"header\">");
out.println("<p>");
    
    out.println("<a href=\"javascript:"+
          "document.UserPrivileges."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+UserAccountInterfaceKey.ACTION_CHANGE_PASSWORD+"';"+
          "document.UserPrivileges."+InterfaceKey.HASHMAP_KEY_USER_ID+".value="+userID+";"+
          "document.UserPrivileges.target='rhs';"+
          "UserPrivileges.submit();\">");
    
    
    
    out.println("change password");

    
    
            out.println("</a>");
          out.println("<a href=\"javascript:"+
          "document.UserPrivileges."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+UserAccountInterfaceKey.ACTION_LOGOUT+"';"+
          "document.UserPrivileges.target='_top';"+
          "UserPrivileges.submit();\">");

          
          out.println("&nbsp; - &nbsp; logout");

    out.println("</a>");
    out.println("</p>");


out.println("</div>");



out.println("<div class=\"clear\">");
out.println("</div>");


out.println("<div class=\"header-title\">");
out.println("<h1>Welcome</h1>");
out.println("</div>");

out.println("<div class=\"clear\">");
out.println("</div>");



out.println("<div class=\"pages\">");
 
    out.println("<FRAME name=rhs src=\"\" frameBorder=No noResize>");
        
       
out.println("</div>");



out.println("<div class=\"clear\">");
out.println("</div>");
%>

<%@include file ="SDSFooterOrange.jsp" %>

<%!
out.println("</div>");
        
   out.println("</FRAMESET>");
  }
%> 
