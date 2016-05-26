<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.sa.roles.model.*"
         import="com.mobinil.sds.core.system.sa.roles.dto.*"
         import="com.mobinil.sds.core.system.sa.modules.dto.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.sa.privileges.model.*"
%>
<%
/**
 * Choose_Role.jsp:
 * Display the user active roles after a successful login.
 * 
 * showRoles(HttpServletRequest request, HttpServletResponse response, JspWriter out, HttpSession session): 
 * Display the user active roles.
 *
 * @version	1.01 March 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

      String appName = request.getContextPath();
      String userID = (String)session.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
%>
<html>
  <head>
    <TITLE>:::: SDS ::::</TITLE>
    <%@include file ="SDSMetaOrange.jsp" %>
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/style.css">
    
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/utilities.js" type=text/javascript></SCRIPT>
  </head>
  <body  onload="form_OnLoad()">
      
<div class="container">

    <div class="row">
        <!-- Left Side cut-->

        
       <%-- 
<jsp:include page ="SDSLeftSideOrange.jsp" >
<jsp:param name="current" value="welcome" />
</jsp:include>
--%>

<!-- Left Side End cut-->
        
        


    <div class="col-md-2" style="background:#666666; display:inline-table; height: 100%;">

<!-- Main Menu -->

<div id="main-menu">

<img src="../resources/images/logo.jpg" class="logo-img" alt="">
          <%showRoles(request, response, out, session);%>

         

</div>
          
    </div>
        
 <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="ChooseRole" method="post" target=lhs>
    <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" 
    value="<%out.print(UserAccountInterfaceKey.ACTION_SHOW_ROLE_PRIVILEGES);%>">
    <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>" value=0>
    <input type="hidden" name="<%out.print(RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID);%>" value=0>
     
        </form>   

 <%--   
<div class="col-md-10">
    

<div id="header">
<p>
    <%
    out.println("<a href=\"javascript:"+
          "document.ChooseRole."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+UserAccountInterfaceKey.ACTION_CHANGE_PASSWORD+"';"+
          "document.ChooseRole."+InterfaceKey.HASHMAP_KEY_USER_ID+".value="+userID+";"+
          "document.ChooseRole.target='rhs';"+
          "ChooseRole.submit();\">");
    
    %>
    
    change password
<%
    out.println("</a>");
%>

    
    <%
          out.println("<a href=\"javascript:"+
          "document.ChooseRole."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+UserAccountInterfaceKey.ACTION_LOGOUT+"';"+
          "document.ChooseRole.target='_top';"+
          "ChooseRole.submit();\">");

          %>
          &nbsp; - &nbsp; logout
<%
    out.println("</a>");
%>

</p>

</div>
<form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="ChooseRole" method="post" target=lhs>
    <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" 
    value="<%out.print(UserAccountInterfaceKey.ACTION_SHOW_ROLE_PRIVILEGES);%>">
    <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>" value=0>
    <input type="hidden" name="<%out.print(RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID);%>" value=0>
     
        </form>

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
--%>
</div> <%--lamya here --%>    

</div>
<%@include file ="SDSScriptsOrange.jsp" %>
  </body>

</html>


<%!
/**
 * showRoles method: 
 * Display the user active roles.
 *
 * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out, HttpSession session
 * @return  
 * @throws  ServletException, IOException
 * @see    
 *
 */ 
  public void showRoles(HttpServletRequest request, HttpServletResponse response, JspWriter out, HttpSession session)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    String userID = (String)session.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    /*
     * Check if the user is no longer loged in 
     * by checking for the user id in the session.
     * If user is no longer loge in he is redirected to the login page.
     */
    if(userID == null )
    {
      out.println("<script type=\"text/javascript\">");
      if(dataHashMap != null)
      {
        if(! dataHashMap.isEmpty())
        {
          String strError = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);
          if(strError != null)
          {
            out.println("alert('"+strError+"');");
          }
        }
      }
      out.println("function form_OnLoad(){}");
      out.println("document.ChooseRole.target=\"_top\";");
      out.println("document.ChooseRole."+InterfaceKey.HASHMAP_KEY_ACTION+".value=\""+UserAccountInterfaceKey.ACTION_SHOW_LOGIN_PAGE+"\";");
      out.println("ChooseRole.submit();");
      out.println("</script>");
    }
    else
    {
      if(dataHashMap != null)
      {
        if(! dataHashMap.isEmpty())
        {
          UserDTO newUserDTO = (UserDTO)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
          Vector roleVector = (Vector)newUserDTO.getUserRoles();
          out.println("<ul id=\"main-nav\">");
          for(int i = 0; i<roleVector.size(); i++)
          {
            int roleID = ((RoleModel)roleVector.elementAt(i)).getRoleID();
            String roleName = ((RoleModel)roleVector.elementAt(i)).getRoleName();
            /*out.println("<TABLE cellSpacing=0 cellPadding=0 width=\"100%\" bgColor=#f1f1ed border=0>");
            out.println("<TBODY>");
            out.println("<TR>");
            out.println("<TD vAlign=top>");
            out.println("<DIV class=option onmouseover=\"this.style.background='#DEE7EF'\" ");
            out.println("onmouseout=\"this.style.background='#f5f5f5'\">");*/
            //lamya
            
            //end lamya
            
            
            
            
            
            out.println("<li>");//class=\"nav-top-item no-submenu current\
            
            
            
            out.println("<a class=\"nav-top-item no-submenu \"   href=\"javascript:"+
            "document.ChooseRole."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+UserAccountInterfaceKey.ACTION_SHOW_ROLE_PRIVILEGES_MAIN+"';"+
            "document.ChooseRole."+InterfaceKey.HASHMAP_KEY_USER_ID+".value="+userID+";"+
            "document.ChooseRole."+RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID+".value="+roleID+";"+
            "document.ChooseRole.target='_top';"+
            "ChooseRole.submit();\">");
            out.println(roleName+"</a></li>"); 
            /*out.println("<SPAN style=\"FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698\" onclick=\"Highlight(this);\">");
            out.println(roleName+"</SPAN></a></DIV></TD></TR></TBODY></TABLE>");*/
            
            
          }
          //lamya
          out.println("</ul>");
          //end lamya
         /* out.println("<TR id=section1>");
          out.println("<TD vAlign=top bgColor=#f6f6f6 colSpan=7>&nbsp;&nbsp;");
          out.println("<IMG height=70 alt=\"\" src=\""+appName+"/resources/img/index_37.jpg\" width=69>");
          out.println("<TR class=TableTextNote>");
          out.println("<TD class=TableTextNote width=\"100%\">");
          out.println("<DIV class=option onmouseover=\"this.style.background='#DEE7EF'\" ");
          out.println("onmouseout=\"this.style.background='#f5f5f5'\">");*/
          /*out.println("<a href=\"javascript:"+
          "document.ChooseRole."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+UserAccountInterfaceKey.ACTION_CHANGE_PASSWORD+"';"+
          "document.ChooseRole."+InterfaceKey.HASHMAP_KEY_USER_ID+".value="+userID+";"+
          "document.ChooseRole.target='rhs';"+
          "ChooseRole.submit();\">");
          out.println("<SPAN style=\"FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698\" onclick=\"Highlight(this);\">");
          out.println("Change Password</SPAN></a></TD></TR>");
          out.println("<p>Change Password</p>");*/
          
          /*out.println("<TR class=TableTextNote>");
          out.println("<TD class=TableTextNote width=\"100%\">");
          out.println("<DIV class=option onmouseover=\"this.style.background='#DEE7EF'\" ");
          out.println("onmouseout=\"this.style.background='#f5f5f5'\">");*/
          //out.println("<div class=\"col-md-10\">");
          //out.println("<div id=\"header\">");
          /*out.println("<a href=\"javascript:"+
          "document.ChooseRole."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+UserAccountInterfaceKey.ACTION_LOGOUT+"';"+
          "document.ChooseRole.target='_top';"+
          "ChooseRole.submit();\">");
          out.println("<SPAN style=\"FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698\" onclick=\"Highlight(this);\">");
          out.println("Log Out</SPAN></a></TD></TR>");
          out.println("<p>Log Out</p>");*/
         
          out.println("<script>function form_OnLoad(){}</script>");
        }
      }
      else
      {
        /*
         * Check if the datahashmap is null which indecates that this is 
         * the first time for this page to be called from its parent frame.
         * It then resubmit the data on itself once more.
         */
        out.println("<script>");
        out.println("function form_OnLoad()");
        out.println("{");
        out.println("document.ChooseRole."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+UserAccountInterfaceKey.ACTION_SHOW_USER_ROLES+"';");
        out.println("document.ChooseRole."+InterfaceKey.HASHMAP_KEY_USER_ID+".value="+userID+";");
        out.println("ChooseRole.submit();");
        out.println("}");
        out.println("</script>");
      }
    }
  }

%>