<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.core.system.sa.modules.model.*"
         import="com.mobinil.sds.core.system.sa.modules.dto.*"
         import="com.mobinil.sds.core.system.sa.privileges.model.*"
%>
<%
/**
 * Privilege_Managment.jsp:
 * Display the system privileges ordered by modules with their current statuses.
 * 
 * showPrivileges(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Display the system privileges ordered by modules with their current statuses.
 *
 * @version	1.01 March 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

    String appName = request.getContextPath();
    HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template2.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/tree.js" type=text/javascript></SCRIPT>
  </head>
  <body>
    <Center>
      <H2>Privilege Managment</H2>
    </Center>
    <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="PreviligeManagment" method="post">

      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" 
       value="<%out.print(AdministrationInterfaceKey.ACTION_UPDATE_PRIVILEGE_STATUS);%>">

      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>" 
      value="<%out.print(userID);%>">

      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <%
        showPrivileges(request, response, out);
        %>
      </TABLE>
      <table  cellspacing="2" cellpadding="1" border="0" width="100%">
      <tr>
        <td align=center>
          <input class=button type="submit" name="update" value="Update Privileges" disabled>
        </td>
      </tr>
      </table>
    </form>
  </body>
</html>

<%!
  /**
   * showPrivileges method: 
   * Display the system privileges ordered by modules with their current statuses.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
   * @return  
   * @throws  ServletException, IOException
   * @see    
   *
   */ 

  public void showPrivileges(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(! dataHashMap.isEmpty())
    {
      out.println("<TR class=TableHeader>");
      out.println("<td nowrap align=center colspan=3><font size=2>Module Name</font></a></TD></TR>");
      Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      for(int i = 0; i<dataVector.size(); i++)
      {
        int moduleID = ((ModuleDTO)dataVector.elementAt(i)).getModuleModel().getModuleID();
        String moduleName = ((ModuleDTO)dataVector.elementAt(i)).getModuleModel().getModuleName();
        out.println("<TR class=TableTextNote>");
        out.println("<td class="+InterfaceKey.STYLE[i%2]+">");
        out.println("<a id=x"+moduleID+" href=\"javascript:Toggle('"+moduleID+"');\">");
        out.print("<IMG height=16 hspace=0 src=\""+appName+"/resources/img/plus.gif\" width=16 border=0 >");
        out.print("</a>");
        out.print(moduleName+"</TD></TR>");
        out.println("<TR class=TableHeader>");
        out.println("<TD colspan=3 class=TableTextNote>");
        out.println("<div style=\"DISPLAY: none\" id="+moduleID+" name="+moduleName+">");
        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
        out.println("<TR class=TableHeader>");
        out.println("<td width=\"30%\" nowrap align=middle>Privilege Name</td>");
        out.println("<td width=\"50%\" nowrap align=middle>Privilege Description</td>");
        out.println("<td width=\"20%\" nowrap align=middle>Privilege Status</td></tr>");
        Vector modulePrivilege = ((ModuleDTO)dataVector.elementAt(i)).getModulePrivileges();
        for(int j = 0; j<modulePrivilege.size(); j++)
        {
          int privilegeID = ((PrivilegeModel)modulePrivilege.elementAt(j)).getPrivilegeID();
          String privilegeName = ((PrivilegeModel)modulePrivilege.elementAt(j)).getPrivilegeName();
          String privilegeDesc = ((PrivilegeModel)modulePrivilege.elementAt(j)).getPrivilegeDescription();
          int privilegeStatusID = ((PrivilegeModel)modulePrivilege.elementAt(j)).getPreviligeStatusID();
          String privilegeStatusName = ((PrivilegeModel)modulePrivilege.elementAt(j)).getPrirvilegeStatusName();
          out.println("<TR>");
          out.println("<TD class="+InterfaceKey.STYLE[j%2]+">"+privilegeName+"</TD>");
          out.println("<TD class="+InterfaceKey.STYLE[j%2]+">"+(privilegeDesc!=null ? privilegeDesc:"") +"</TD>");
          out.println("<TD class="+InterfaceKey.STYLE[j%2]+" align=middle>");
          out.println("<SELECT name="+AdministrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+privilegeID+
                      " onchange=\"document.PreviligeManagment.update.disabled = false;\"");
          if (moduleName.equals("Administration"))
          {
            out.print(" disabled");
          }
          out.print(">");
          Vector additionalDataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
          for(int k = 0; k<additionalDataVector.size(); k++)
          {
            int statusID = ((PrivilegeStatusModel)additionalDataVector.elementAt(k)).getPrivilegeStatusID();
            String statusName = ((PrivilegeStatusModel)additionalDataVector.elementAt(k)).getPrivilegeStatusName();
            out.println("<option value=\""+statusID+"\""); 
            if(privilegeStatusID == statusID)
            {
              out.print("selected");
            }
            out.print(">"+statusName+"</option>");
          }
          out.println("</SELECT></TD></TR>");
        }
        out.println("</TABLE></td></tr></div>");
      }
    }
  }
%>