<%@ page import="javax.servlet.*" import="javax.servlet.http.*"
	import="java.io.PrintWriter" import="java.io.IOException"
	import="java.util.*" import="javax.servlet.jsp.*"
	import="com.mobinil.sds.web.interfaces.*"
	import="com.mobinil.sds.web.interfaces.sa.*"
	import="com.mobinil.sds.core.system.sa.roles.model.*"
	import="com.mobinil.sds.core.system.sa.roles.dto.*"
	import="com.mobinil.sds.core.system.sa.modules.dto.*"
	import="com.mobinil.sds.core.system.sa.privileges.model.*"%>

<%
/**
 * Role_Managment.jsp:
 * Display the system Roles and each role privileges.
 * 
 * showRoles(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Display the system Roles.
 *
 * showPrivileges(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Build hidden divs, each holding privileges of one role.
 * A role privileges div will be visible if the role was selected.
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
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<LINK REL=STYLESHEET TYPE="text/css"
	HREF="<%out.print(appName);%>/resources/css/Template2.css">
</head>
<body>
<Center>
<H2>Role Managment</H2>
</Center>
<script type="text/javascript">

      function toggle(item1)
      {
        divs=document.getElementsByTagName("DIV");
        for (i=0;i<divs.length;i++) 
        {
          if(divs[i].id != item1 && divs[i].id != 'listofusers')
          {
            divs[i].style.display="none";
          }
        }
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
<form
	action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet"
	name="RoleManagment" method="post"><input type="hidden"
	name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>"
	value="<%=RoleInterfaceKey.ACTION_UPDATE_ROLES_STATUS%>"> <input
	type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>"
	value="<%=userID%>"> <input type="hidden"
	name="<%=RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID%>" value=0>
<div id="listofusers" style="display: none">
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1
	width="100%" border=1>
	<TR class=TableHeader>		
		<td width=\ "20%\" nowrap align=center>Name</td>
		<td width=\ "50%\" nowrap align=center>Description</td>
		<td width=\ "20%\" nowrap align=center>Status</td>
		<td width=\ "10%\" nowrap align=center>Edit</td>
	</tr>
	<%
        showRoles(request, response, out);
        %>
</TABLE>
<table cellspacing="2" cellpadding="1" border="0" width="100%">
	<tr>
		<td align=center><input class=button type="button" name="new"
			value="New Role"
			onclick="document.RoleManagment.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value='<%=RoleInterfaceKey.ACTION_NEW_ROLE%>';RoleManagment.submit();">
		<input class=button type="button" name="update" value="Update Roles"
			onclick="document.RoleManagment.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value='<%=RoleInterfaceKey.ACTION_UPDATE_ROLES_STATUS%>';RoleManagment.submit();"
			disabled></td>
	</tr>
</table>
</div>
</form>
<%
    showPrivileges(request, response, out);
    %>
</body>
</html>

<%
  /**
   * Display any error messages returning from the server.
   */ 
  String strError = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);
  if(strError != null)
  {
    out.println("<script type=\"text/javascript\">alert('"+strError+"');</script>");
  }
%>
<%!
  /**
   * showRoles method: 
   * Display the system Roles.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
   * @return  
   * @throws  ServletException, IOException
   * @see    
   *
   */ 

  public void showRoles(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException{
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    if(! dataHashMap.isEmpty()){
      Vector<RoleDTO> dataVector = (Vector<RoleDTO>)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      int i=0;
      RoleModel model;
      HashMap additionalDataHashMap = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
      Vector<RoleStatusModel> vecStatus = (Vector<RoleStatusModel>)additionalDataHashMap.get(RoleInterfaceKey.HASHMAP_KEY_ROLE_STATUSES_LIST);
      for(RoleDTO role: dataVector)
      {
    	model = role.getRoleModel();  
        int roleID = model.getRoleID();
        String roleName =  model.getRoleName();
        String roleDesc =  model.getRoleDescription();
        int roleStatusID = model.getRoleStatusID();
        
        if(roleDesc == null)
        {
          roleDesc ="No available description.";
        }
        
        out.println("<TR class=TableTextNote>");
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+"><a href=\"#divname"+roleID+"\" onclick=\"javascript:toggle('"+roleID+"');\">"+roleName+"</a></TD>");
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+">"+roleDesc+"</TD>");        
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+" align=middle>");
        
        out.println("<SELECT name="+RoleInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+roleID+
                    " onchange=\"document.RoleManagment.update.disabled = false;\">");
        
        for(RoleStatusModel statusModel : vecStatus)
        {
          int statusID = statusModel.getRoleStatusID();
          out.println("<option value=\""+statusID+"\""); 
          if(roleStatusID == statusID)
          {
            out.print("selected");
          }
          out.print(">"+statusModel.getRoleStatusName()+"</option>");
        }
        out.println("</SELECT></TD>");
        out.println("<TD class=TableTextNote align=middle>");
        out.println("<input class=button type=\"button\" name=\""+
                    RoleInterfaceKey.CONTROL_BUTTON_NAME_EDIT_ROLE_PREFIX+roleID+
                    "\" value=\"   Edit   \"");
        out.println("onclick=\""+
        "document.RoleManagment."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+RoleInterfaceKey.ACTION_EDIT_ROLE+"';"+
        "document.RoleManagment."+RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID+".value='"+roleID+"';"+
        "RoleManagment.submit();\">");
        out.println("</TD></TR>");
        i++;
      }
    }
  }

  /**
   * showPrivileges method: 
   * Build hidden divs, each holding privileges of one role.
   * A role privileges div will be visible if the role was selected.
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
      Vector<RoleDTO> dataVector = (Vector<RoleDTO>)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      for(RoleDTO role : dataVector)
      {
        int roleID = role.getRoleModel().getRoleID();
        String roleName = role.getRoleModel().getRoleName();
        out.println("<a name=\"divname"+roleID+"\" id=\"divname"+roleID+"\">");
        out.println("<div style=\"DISPLAY: none\" id="+roleID+" name="+roleName+">");
        out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"role_"+roleID+"\" method=\"post\">");
        out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\"");
        out.print(" value=\""+RoleInterfaceKey.ACTION_UPDATE_ROLE_PRIVILEGES+"\">");
        out.println("<input type=\"hidden\" name=\""+RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID+"\" value=\""+roleID+"\">");
        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
        out.println("<TR class=TableHeader><td colspan=3 nowrap align=middle>"+roleName+" Privileges</td></tr>");
        out.println("<TR class=TableHeader>");
        out.println("<td width=\"40%\" nowrap align=middle>Privilege Name</td>");
        out.println("<td width=\"40%\" nowrap align=middle>Module Name</td>");
        out.println("<td width=\"20%\" nowrap align=middle>Assigned</td></tr>");
        Vector<RolePrivilegeModel> rolePrivilege = role.getRolePrivileges();
        HashMap additionalDataHashMap = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        Vector<ModuleDTO> vecModules = (Vector<ModuleDTO>)additionalDataHashMap.get(RoleInterfaceKey.HASHMAP_KEY_MODULES_LIST);
        int index=0;
    	int j=0;
        for(ModuleDTO moduleDTO : vecModules)
        {
        	j++; 
        	
          int moduleID = moduleDTO.getModuleModel().getModuleID();
          String moduleName = moduleDTO.getModuleModel().getModuleName();          
          Vector<PrivilegeModel> vecModulePrivilege = moduleDTO.getModulePrivileges();
          for(PrivilegeModel privilegeModule: vecModulePrivilege)
          {
          index++;
            int privilegeID = privilegeModule.getPrivilegeID();
            String privilegeName = privilegeModule.getPrivilegeName();
            out.println("<TR>");
            out.println("<TD class="+InterfaceKey.STYLE[index%2]+">"+privilegeName+"</TD>");
            out.println("<TD class="+InterfaceKey.STYLE[index%2]+">"+moduleName+"</TD>");
            out.println("<TD class="+InterfaceKey.STYLE[index%2]+" align=middle>");
            out.println("<input type=\"checkbox\" name="+RoleInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX+privilegeID+
                        " vlaue="+privilegeID+" onclick=\"document.role_"+roleID+".update.disabled = false;\"");
            boolean found=false;
            for(int l = 0; l<rolePrivilege.size()&& !found; l++)
            {
              int nRolePrivilegeID = rolePrivilege.elementAt(l).getPrivilegeID();
              if(privilegeID == nRolePrivilegeID)
              {
                out.print(" checked");
                found=true;
              }
            }
    
            out.print("></TD></TR>");
          }
        }
    
         
        out.println("</TABLE>");
        out.println("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\" width=\"100%\">");
        out.println("<tr><td align=center>");
        out.println("<input class=button type=\"submit\" name=\"update\" value=\"Update "+roleName+" Privileges\" disabled>");
        out.println("</td></tr>");
        out.println("</table></form></div></a>");
      }

    }
    out.println("<script>var obj = document.getElementById('listofusers');obj.style.display='';</script>");    
  }

%>