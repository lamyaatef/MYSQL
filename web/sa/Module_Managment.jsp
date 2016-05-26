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
%>
<%
/**
 * Module_Managment.jsp:
 * Display the system modules with their current statuses.
 * 
 * showModules(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Display the system modules with their current statuses.
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
  </head>
  <body>
    <Center>
      <H2>Module Managment</H2>
    </Center>
    <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="ModuleManagment" method="post">

      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" 
      value="<%out.print(AdministrationInterfaceKey.ACTION_UPDATE_MODULES_STATUS);%>">

      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>" 
      value="<%out.print(userID);%>">

      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td nowrap align=center>Name</td>
          <td nowrap align=center>Description</td>
          <td nowrap align=center>Status</td>
        </tr>	
        <%
        showModules(request, response, out);
        %>
      </TABLE>
      <table  cellspacing="2" cellpadding="1" border="0" width="100%">
      <tr>
        <td align=center>
          <input class=button type="submit" name="update" value="Update Modules" disabled>
        </td>
      </tr>
      </table>
    </form>
  </body>
</html>

<%!
  /**
   * showModules method: 
   * Display the system modules with their current statuses.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
   * @return  
   * @throws  ServletException, IOException
   * @see    
   *
   */ 

  public void showModules(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(! dataHashMap.isEmpty()){
      Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      for(int i = 0; i<dataVector.size(); i++){
        int moduleID = ((ModuleDTO)dataVector.elementAt(i)).getModuleModel().getModuleID();
        String moduleName = ((ModuleDTO)dataVector.elementAt(i)).getModuleModel().getModuleName();
        String moduleDesc = ((ModuleDTO)dataVector.elementAt(i)).getModuleModel().getModuleDescription();
        int moduleStatusID = ((ModuleDTO)dataVector.elementAt(i)).getModuleModel().getModuleStatusID();

        out.println("<TR class=TableTextNote>");
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+">"+moduleName+"</TD>");
        if(moduleDesc == null)
        {
          moduleDesc ="No available description.";
        }
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+">"+moduleDesc+"</TD>");
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+" align=middle>");
        
        out.println("<SELECT name="+AdministrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+moduleID+
                    " onchange=\"document.ModuleManagment.update.disabled = false;\"");
        if (moduleName.equals("Administration"))
        {
          out.print(" disabled");
        }
        out.print(">");
        Vector additionalDataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        for(int j = 0; j<additionalDataVector.size(); j++)
        {
          int statusID = ((ModuleStatusModel)additionalDataVector.elementAt(j)).getModuleStatusID();
          String statusName = ((ModuleStatusModel)additionalDataVector.elementAt(j)).getModuleStatusName();
          out.println("<option value=\""+statusID+"\""); 
          if(moduleStatusID == statusID)
          {
            out.print("selected");
          }
          out.print(">"+statusName+"</option>");
        }
        out.println("</SELECT>");
        out.println("</TD>");
        out.println("</TR>");
      }
    }
  }
%>