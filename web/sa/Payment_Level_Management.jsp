<%@page import="com.mobinil.sds.core.system.dataMigration.model.PaymentLevelModel"%>
<%@ page import="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.sa.roles.dto.*"
         import="com.mobinil.sds.core.system.sa.roles.model.*"
%>
<%
/**
 * User_Managment.jsp:
 * Display the system Users and each user roles.
 * 
 * showUsers(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Display the system Users.
 *
 * showRoles(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Build hidden divs, each holding roles of one user.
 * A user roles div will be visible if the user was selected.
 *
 * @version	1.01 March 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

    String appName = request.getContextPath();
    //HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_COLLECTION);
    //String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template2.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/utilities.js" type=text/javascript></SCRIPT>
  </head>
  <body>
    <Center>
      <H2>Payment Level Management</H2>
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
    <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="Payment_Level_Management" method="post">


         
      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" 
      value="<%out.print(AdministrationInterfaceKey.ACTION_ADD_PAYMENT_LEVEL);%>">

      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>" 
      value="<%out.print("anyname");%>">

      <input type="hidden" name="<%out.print(AdministrationInterfaceKey.CONTROL_HIDDEN_NAME_PAYMENT_LEVEL_ID);%>" value=0>
        
        
        
        
        
        
      <div name="listofusers" id="listofusers" >
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td width="20%" nowrap align=center>Payment Level ID</td>
          <td width="40%" nowrap align=center>Payment Level Name</td>
          <td width="10%" nowrap align=center>Edit</td>
        </tr>	
        <%
       showPaymentLevels(request, response, out);
        %>
      </TABLE>
      
      
      <table  cellspacing="2" cellpadding="1" border="0" width="100%">
        <tr>
          <td align=center>
            <input class=button type="button" name="new" value=" New Payment Level " 
            onclick="document.Payment_Level_Management.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(AdministrationInterfaceKey.ACTION_NEW_PAYMENT_LEVEL);%>';Payment_Level_Management.submit();">
            
          </td>
        </tr>
      </table>   
      </div>
    </form>
    <%
   //showRoles(request, response, out);
    %>
  </body>
</html>
<%
  /**
   * Display any error messages returning from the server.
   */ 

  /*String strError = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);
  if(strError != null)
  {
    out.println("<script type=\"text/javascript\">alert('"+strError+"');</script>");
  }*/
%>

<%!
  /**
   * showUsers method: 
   * Display the system Users.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
   * @return  
   * @throws  ServletException, IOException
   * @see    
   *
   */ 

  public void showPaymentLevels(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(! dataHashMap.isEmpty()){
      
      Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
       System.out.println("inside datahashmap if dataVector.size() : "+dataVector.size()); 
      //Integer maxLockTimes =(Integer) dataHashMap.get(UserInterfaceKey.HASHMAP_KEY_USER_MAX_LOCK);
      for(int i = 0; i<dataVector.size(); i++){
          System.out.println("Payment Levels SIZE : "+dataVector.size());
    
        PaymentLevelModel payLevelModel = (PaymentLevelModel) dataVector.get(i);
        System.out.println("pay model : "+payLevelModel+" i : "+i);
        String payLevelId = payLevelModel.getPaymentLevelId();
        String payLevelName = payLevelModel.getPaymentLevelName();
        System.out.println("id name : "+payLevelId+" "+payLevelName);
        out.println("<TR class=TableTextNote>");
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+"><a href=\"#divname"+payLevelId+"\" onclick=\"javascript:toggle('"+payLevelId+"');\">"+payLevelId+"</a></TD>");
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+">"+payLevelName+"</TD>");
        
        
        
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+" align=middle>");
        out.println("<input class=button type=\"button\" name=\""+AdministrationInterfaceKey.CONTROL_BUTTON_NAME_EDIT_PAYMENT_LEVEL_PREFIX+payLevelId+"\" value=\"   Edit   \"");
        out.println("onclick=\"document.Payment_Level_Management."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+AdministrationInterfaceKey.ACTION_EDIT_PAYMENT_LEVEL+"';");
        out.println("document.Payment_Level_Management."+AdministrationInterfaceKey.CONTROL_HIDDEN_NAME_PAYMENT_LEVEL_ID+".value='"+payLevelId+"';");
        out.println("Payment_Level_Management.submit();\">");
        out.println("</TD>");
        out.println("</TR>");
      }
    }
  }

  /**
   * showRoles method: 
   * Build hidden divs, each holding roles of one user.
   * A user roles div will be visible if the user was selected.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
   * @return  
   * @throws  ServletException, IOException
   * @see    
   *
   */ 

 /* public void showRoles(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(! dataHashMap.isEmpty())
    {
      Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      for(int i = 0; i<dataVector.size(); i++)
      {
        int personID = ((UserDTO)dataVector.elementAt(i)).getUserModel().getPersonModel().getPersonID();
        String personName = ((UserDTO)dataVector.elementAt(i)).getUserModel().getPersonModel().getPersonFullName();
        out.println("<a name=\"divname"+personID+"\" id=\"divname"+personID+"\">");
        out.println("<div style=\"DISPLAY: none\" id="+personID+" name="+personName+">");
        out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"user_"+personID+"\" method=\"post\">");
        out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\"");
        out.print(" value=\""+UserInterfaceKey.ACTION_UPDATE_USER_ROLES+"\">");
        out.println("<input type=\"hidden\" name=\""+UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID+"\" value=\""+personID+"\">");
        out.println("<input type=\"hidden\" name=\""+RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID+"\">");
        out.println("<input type=\"hidden\" name=\""+RoleInterfaceKey.CONTROL_TEXT_NAME_ROLE_NAME+"\">");
        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
        out.println("<TR class=TableHeader><td colspan=3 nowrap align=middle>"+personName+" Roles</td></tr>");
        out.println("<TR class=TableHeader>");
        out.println("<td width=\"30%\" nowrap align=middle>Role Name</td>");
        out.println("<td width=\"50%\" nowrap align=middle>Role Description</td>");
        out.println("<td width=\"20%\" nowrap align=middle>Assigned</td></tr>");
        Vector userRole = ((UserDTO)dataVector.elementAt(i)).getUserRoles();
        HashMap additionalDataHashMap = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        Vector vecRoles = (Vector)additionalDataHashMap.get(UserInterfaceKey.HASHMAP_KEY_ROLES_LIST);
        for(int j = 0; j<vecRoles.size(); j++)
        {
          int roleID = ((RoleDTO)vecRoles.elementAt(j)).getRoleModel().getRoleID();
          String roleName = ((RoleDTO)vecRoles.elementAt(j)).getRoleModel().getRoleName();
          String roleDesc = ((RoleDTO)vecRoles.elementAt(j)).getRoleModel().getRoleDescription();
          out.println("<TR>");
          out.println("<TD class="+InterfaceKey.STYLE[j%2]+"><a href=\"javascript:"+
          "openWindowfull('"+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"+
          InterfaceKey.HASHMAP_KEY_ACTION+"="+UserInterfaceKey.ACTION_SHOW_USER_ROLE_PRIVILEGES+"&"+
          UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID+"="+personID+"&"+
          RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID+"="+roleID+"&"+
          RoleInterfaceKey.CONTROL_TEXT_NAME_ROLE_NAME+"="+roleName+"');\">"+roleName+"</a></TD>");
          if(roleDesc == null)
          {
            roleDesc = "";
          }
          out.println("<TD class="+InterfaceKey.STYLE[j%2]+">"+roleDesc+"</TD>");
          out.println("<TD class="+InterfaceKey.STYLE[j%2]+" align=middle>");
          out.println("<input type=\"checkbox\" name="+UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX+roleID+
                      " vlaue="+roleID+" onclick=\"document.user_"+personID+".update.disabled = false;\"");
          for(int l = 0; l<userRole.size(); l++)
          {
            int nUserRoleID = ((UserRoleModel)userRole.elementAt(l)).getRoleID();
            if(roleID == nUserRoleID)
            {
              out.print(" checked");
            }
          }
          out.print("></TD></TR>");
        }
        out.println("</TABLE>");
        out.println("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\" width=\"100%\">");
        out.println("<tr>");
        out.println("<td align=center>");
        out.println("<input class=button type=\"submit\" name=\"update\" value=\"Update "+personName+" Roles\" disabled>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table></form></div>");
        out.println("</a>");
      }
    }
    out.println("<script>var obj = document.getElementById('listofusers');obj.style.display='';</script>");        
  }
*/
%>