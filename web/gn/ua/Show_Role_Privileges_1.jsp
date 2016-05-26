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
         import="com.mobinil.sds.core.system.sa.privileges.model.*"
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

      String appName = request.getContextPath();
%>
<html>
  <head>
    <TITLE>:::: SDS ::::</TITLE>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/leftmenu.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/utilities.js" type=text/javascript></SCRIPT>
  </head>
  <body onload="form_OnLoad()">
    <script type="text/javascript">
      function toggle(item1)
      {
        obj=document.getElementById(item1);
        if (obj!=null)
        {
          visible = obj.style.display!="none";
          if (visible) {
            obj.style.display="none";
          } 
          else {
             obj.style.display="";
          }
        }
      }
    </script>
    <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="UserPrivileges" method="post" target="lhs">
    <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" value="">
    <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>" value="">
    <input type="hidden" name="<%out.print(RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID);%>" value=0>
    <input type="hidden" name="<%out.print(UserAccountInterfaceKey.CONTROL_HIDDEN_PRIVILEGE_ACTION_NAME);%>" value="">
    <left>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=0 cellPadding=0 width=100% border=0>
      <TBODY>
      <TR class=TableHeader>
        <TD vAlign=top width=163>
          <IMG height=16 alt="" src="<%out.print(appName);%>/resources/img/index_10.gif" width=163>
        </TD>
      </TR>
      <TR class=TableHeader>
        <TD vAlign=top width=163>&nbsp;</TD>
      </TR>
      <TR>
        <TD vAlign=top width=163>
        <%showPrivileges(request, response, out, session);%>
          </TD>
        </TR>
    </TBODY>
    </TABLE>
    </left>

    </form>
  </body>
</html>

<%!
  /**
   * showPrivileges method: 
   * Display the active privileges of active modules grouped by modules.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out, HttpSession session
   * @return  
   * @throws  ServletException, IOException
   * @see    
   *
   */ 

  public void showPrivileges(HttpServletRequest request, HttpServletResponse response, JspWriter out, HttpSession session)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    String userID = (String)session.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
    String roleID = (String)session.getAttribute(RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID);
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
      out.println("document.UserPrivileges.target=\"_top\";");
      out.println("document.UserPrivileges."+InterfaceKey.HASHMAP_KEY_ACTION+".value=\""+UserAccountInterfaceKey.ACTION_SHOW_LOGIN_PAGE+"\";");
      out.println("UserPrivileges.submit();");
      out.println("</script>");
    }
    else
    {
      out.println("<script type=\"text/javascript\">"+
      "document.UserPrivileges."+InterfaceKey.HASHMAP_KEY_USER_ID+".value="+userID+";"+
      "document.UserPrivileges."+RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID+".value="+roleID+";"+
      "</script>");
      if(dataHashMap != null)
      {
        if(! dataHashMap.isEmpty())
        {
          RoleDTO newRoleDTO = (RoleDTO)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
          session.setAttribute(RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID, roleID);
          Vector rolePrivilegeVector = (Vector)newRoleDTO.getRolePrivileges();
          Vector roleModules = (Vector)newRoleDTO.getRoleModules();
          for(int i=0; i<roleModules.size(); i++)
          {
            int roleModuleID = ((Integer)roleModules.elementAt(i)).intValue();
            Vector moduleVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
            for(int j = 0; j<moduleVector.size(); j++)
            {
              int moduleID = ((ModuleDTO)moduleVector.elementAt(j)).getModuleModel().getModuleID();
              String moduleName = ((ModuleDTO)moduleVector.elementAt(j)).getModuleModel().getModuleName();
              if(roleModuleID == moduleID)
              {
                out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=0 cellPadding=0 width=\"100%\" border=0>");
                out.println("<TBODY>");
                out.println("<TR class=TableOrdinaryText>");
                out.println("<TD class=TableOrdinaryText vAlign=top width=\"100%\"><a href=\"javascript:toggle('"+moduleID+"');\">"+moduleName+"</a></TD>");
                out.println("</TR>");
                out.println("<TR>");
                out.println("<TD vAlign=top width=\"100%\">");
                out.println("<div style=\"DISPLAY: none\" id="+moduleID+" name="+moduleName+">");
                Vector modulePrivilege = ((ModuleDTO)moduleVector.elementAt(j)).getModulePrivileges();
                for(int k = 0; k<modulePrivilege.size(); k++)
                {
                  int privilegeID = ((PrivilegeModel)modulePrivilege.elementAt(k)).getPrivilegeID();
                  String privilegeName = ((PrivilegeModel)modulePrivilege.elementAt(k)).getPrivilegeName();
                  String privilegeAction = ((PrivilegeModel)modulePrivilege.elementAt(k)).getPrivilegeActionName();
                  String privilegeTarget = ((PrivilegeModel)modulePrivilege.elementAt(k)).getPrivilegeTarget();
                  for(int l= 0; l<rolePrivilegeVector.size(); l++)
                  {
                    int usedPrivilegeID = ((PrivilegeModel)rolePrivilegeVector.elementAt(l)).getPrivilegeID();
                    if(privilegeID == usedPrivilegeID)
                    {
                      out.println("<TABLE cellSpacing=0 cellPadding=0 width=\"100%\" bgColor=#f1f1ed ");
                      out.println("border=0>");
                      out.println("<TBODY>");
                      out.println("<TR>");
                      out.println("<TD vAlign=top width=\"100%\">");
                      out.println("<DIV class=option onmouseover=\"this.style.background='#DEE7EF'\" ");
                      out.println("onmouseout=\"this.style.background='#f5f5f5'\">");
                      out.println("<A href=\"javascript:"+
                      "document.UserPrivileges."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+privilegeAction+"';"+
                      "document.UserPrivileges."+UserAccountInterfaceKey.CONTROL_HIDDEN_PRIVILEGE_ACTION_NAME+".value='"+privilegeAction+"';"+
                      "document.UserPrivileges.target='"+privilegeTarget+"';"+
                      "UserPrivileges.submit();\">");
                      out.println("<SPAN style=\"FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698\" onclick=\"Highlight(this);\">");
                      out.println(privilegeName+"</SPAN></A></DIV></TD></TR></TBODY></TABLE>");
                    }
                  }
                }
                out.println("</div></TD>");
                out.println("</TR>");
                out.println("</TBODY>");
                out.println("</TABLE>");
              }
            }
          }
        }
        out.println("<TR id=section1>");
        out.println("<TD vAlign=top bgColor=#f6f6f6 colSpan=7>&nbsp;&nbsp;");
        out.println("<IMG height=70 alt=\"\" src=\""+appName+"/resources/img/index_37.jpg\" width=69>");
        out.println("<TR class=TableTextNote>");
        out.println("<TD class=TableTextNote width=\"100%\">");
        out.println("<DIV class=option onmouseover=\"this.style.background='#DEE7EF'\" ");
        out.println("onmouseout=\"this.style.background='#f5f5f5'\">");
        out.println("<a href=\"javascript:"+
        "document.UserPrivileges."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+UserAccountInterfaceKey.ACTION_SHOW_USER_MAIN+"';"+
        "document.UserPrivileges."+InterfaceKey.HASHMAP_KEY_USER_ID+".value="+userID+";"+
        "document.UserPrivileges.target='_top';"+
        "UserPrivileges.submit();\">");
        out.println("<SPAN style=\"FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698\" onclick=\"Highlight(this);\">");
        out.println("Change current role</SPAN></a></DIV></TD></TR>");
        out.println("<TR class=TableTextNote>");
        out.println("<TD class=TableTextNote width=\"100%\">");
        out.println("<DIV class=option onmouseover=\"this.style.background='#DEE7EF'\" ");
        out.println("onmouseout=\"this.style.background='#f5f5f5'\">");
        out.println("<a href=\"javascript:"+
        "document.UserPrivileges."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+UserAccountInterfaceKey.ACTION_LOGOUT+"';"+
        "document.UserPrivileges.target='_top';"+
        "UserPrivileges.submit();\">");
        out.println("<SPAN style=\"FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698\" onclick=\"Highlight(this);\">");
        out.println("Log Out</SPAN></a></TD></TR>");
        out.println("</TD>");
        out.println("</TR>");
        out.println("<script>function form_OnLoad(){}</script>");
      }
      else
      {
        /*
         * Check if the datahashmap is null which indecates that this is 
         * the first for this page to be called from its parent frame.
         * It then resubmit the data on itself once more.
         */
        out.println("<script>");
        out.println("function form_OnLoad()");
        out.println("{");
        out.println("document.UserPrivileges."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+UserAccountInterfaceKey.ACTION_SHOW_ROLE_PRIVILEGES+"';");
        out.println("document.UserPrivileges."+InterfaceKey.HASHMAP_KEY_USER_ID+".value="+userID+";");
        out.println("document.UserPrivileges."+RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID+".value="+roleID+";");
        out.println("UserPrivileges.submit();");
        out.println("}");
        out.println("</script>");
      }
    }
  }
%>
