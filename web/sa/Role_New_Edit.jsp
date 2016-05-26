<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.core.system.sa.roles.model.*"
         import="com.mobinil.sds.core.system.sa.roles.dto.*"
         import="com.mobinil.sds.core.system.sa.modules.dto.*"
         import="com.mobinil.sds.core.system.sa.privileges.model.*"
%>
<%
/**
 * Role_New_Edit.jsp:
 * Add or edit a role.
 * 
 * showRole(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Display the form in which a role will be edited or added.
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
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type=text/javascript></SCRIPT>
  </head>
  
  <body>
        <%
        showRole(request, response, out);
        %>
  </body>
</html>

<%!
  /**
   * showRole method: 
   * Display the form in which a role will be edited or added.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
   * @return  
   * @throws  ServletException, IOException
   * @see    
   *
   */ 

  public void showRole(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(dataHashMap !=null)
    {
      String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
      String header = "New Role";
      int roleID = 0;
      String roleName = "";
      String roleDesc = "";
      int roleStatusID = 0;
      RoleModel newRoleModel = (RoleModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      if(newRoleModel != null)
      {
        roleID = newRoleModel.getRoleID();
        roleName = newRoleModel.getRoleName();
        roleDesc = newRoleModel.getRoleDescription();
        roleStatusID = newRoleModel.getRoleStatusID();
        header = newRoleModel.getRoleName();
      }
      out.println("<Center>");
      out.println("<H2>"+header+"</H2>");
      out.println("</Center>");
      out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"RoleManagment\" method=\"post\">");
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" ");
      out.println("value=\""+RoleInterfaceKey.ACTION_UPDATE_ROLE+"\">");

      out.println("<input type=\"hidden\" name="+InterfaceKey.HASHMAP_KEY_USER_ID+" "); 
      out.println("value="+userID+">");

      out.println("<input type=\"hidden\" name=\""+RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID+"\" value=\""+roleID+"\">");
      out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width=\"20%\">Role Name *</TD><TD class=TableTextNote>");
      out.println("<input size=\"50%\" type=\"text\" name=\""+RoleInterfaceKey.CONTROL_TEXT_NAME_ROLE_NAME+"\"");
      if(newRoleModel != null)
      {
        out.print(" value=\""+roleName+"\"");
      }
      out.print(">");
      out.println("</TD></TR><TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width=\"20%\">Role Description</TD><TD class=TableTextNote>");
      out.println("<input size=\"100%\" type=\"text\" name=\""+RoleInterfaceKey.CONTROL_TEXT_NAME_ROLE_DESCRIPTION+"\"");
      if(newRoleModel != null)
      {
        if(roleDesc == null)
        {
          roleDesc="";
        }
        out.print(" value=\""+roleDesc+"\"");
      }
      out.print(">");
      if(newRoleModel != null)
      {
        out.println("</TD></TR><TR class=TableTextNote>");
        out.println("<TD class=TableTextNote width=\"20%\">Role Status</TD>");
        out.println("<TD class=TableTextNote>");
        out.println("<SELECT name="+RoleInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+roleID+
                    " onchange=\"document.RoleManagment.update.disabled = false;\">");
        Vector additionalDataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        for(int j = 0; j<additionalDataVector.size(); j++)
        {
          int statusID = ((RoleStatusModel)additionalDataVector.elementAt(j)).getRoleStatusID();
          String statusName = ((RoleStatusModel)additionalDataVector.elementAt(j)).getRoleStatusName();
          out.println("<option value=\""+statusID+"\""); 
          if(roleStatusID == statusID)
          {
            out.print("selected");
          }
          out.print(">"+statusName+"</option>");
        }
        out.println("</SELECT>");
      }
      out.println("<TR class=TableTextNote><TD colspan=2 class=TableTextNote><font size=1>* indicates mandatory fields.</font></TD></TR></TABLE>");
      out.println("</TD></TR></TABLE>");
      out.println("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\" width=\"100%\">");
      out.println("<tr>");
      out.println("<td align=center>");
      out.println("<input class=button type=\"submit\" name=\"save\"");
      if(newRoleModel != null)
      {
        out.print(" value=\" Update \"");
      }
      else
      {
        out.print(" value=\"   Add   \"");
      }
      out.print(" onclick=\"if(NonBlank("+RoleInterfaceKey.CONTROL_TEXT_NAME_ROLE_NAME+", true, 'Role Name')){document.RoleManagment."+InterfaceKey.HASHMAP_KEY_ACTION+".value='");
      if(newRoleModel != null)
      {
        out.print(RoleInterfaceKey.ACTION_UPDATE_ROLE);
      }
      else
      {
        out.print(RoleInterfaceKey.ACTION_ADD_ROLE);
      }
      out.print("';RoleManagment.submit();} return false;\">");
      out.println("</td>");
      out.println("</tr>");
      out.println("</table></form>");
    }
  }
%>
