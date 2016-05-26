<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
%>
<%
/**
 * User_New_Edit.jsp:
 * Add or edit a user.
 * 
 * showUser(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Display the form in which a user will be edited or added.
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
        showUser(request, response, out);
        %>
  </body>
</html>

<%!
  /**
   * showUser method: 
   * Display the form in which a user will be edited or added.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
   * @return  
   * @throws  ServletException, IOException
   * @see    
   *
   */ 

  public void showUser(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String serverName = request.getServerName();
    int serverPort = request.getServerPort();
    String appName = request.getContextPath();

    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(dataHashMap != null)
    {
      String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
      String header = "New User";
      int personID = 0;
      String personName = "";
      String personEmail = "";
      String personAddress = "";
      int userStatusID = 0;
      String strLockID="";
      int userLockId=0;
      Integer maxLockTimes =(Integer) dataHashMap.get(UserInterfaceKey.HASHMAP_KEY_USER_MAX_LOCK);
      UserModel newUserModel = (UserModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      if(newUserModel != null)
      {
        personID = newUserModel.getPersonModel().getPersonID();
        personName = newUserModel.getPersonModel().getPersonFullName();
        personEmail = newUserModel.getPersonModel().getPersonEMail();
        personAddress = newUserModel.getPersonModel().getPersonAddress();
        userStatusID = newUserModel.getUserStatusID();
        strLockID = newUserModel.getM_isLocked();
        userLockId = new Integer(strLockID).intValue();
        header = personName;
      }
      out.println("<Center>");
      out.println("<H2>"+header+"</H2>");
      out.println("</Center>");
      out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"UserManagment\" method=\"post\">");
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" "+
                     "value=\""+UserInterfaceKey.ACTION_UPDATE_USER+"\">");
                     
      out.println("<input type=\"hidden\" name=\""+UserInterfaceKey.HASHMAP_KEY_USER_MAX_LOCK+"\" "+
                     "value=\""+maxLockTimes+"\">");

      out.println("<input type=\"hidden\" name=\""+UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_NAME+"\" "+
                     "value=\""+serverName+"\">");
      out.println("<input type=\"hidden\" name=\""+UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_PORT+"\" "+
                     "value=\""+serverPort+"\">");
      out.println("<input type=\"hidden\" name=\""+UserAccountInterfaceKey.CONTROL_HIDDEN_CONTEXT_PATH+"\" "+
                     "value=\""+appName+"\">");
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" "+
                     "value="+userID+">");

      out.println("<input type=\"hidden\" name=\""+UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID+"\" value=\""+personID+"\">");
      out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width=\"20%\">User Full Name *</TD><TD class=TableTextNote>");
      out.println("<input size=\"50%\" type=\"text\" name=\""+UserInterfaceKey.CONTROL_TEXT_NAME_USER_NAME+"\"");
      if(newUserModel != null)
      {
        out.print(" value=\""+personName+"\"");
      }
      out.print(">");
      out.println("</TD></TR>");
      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width=\"20%\">E-Mail *</TD><TD class=TableTextNote>");
      out.println("<input size=\"50%\" type=\"text\" name=\""+UserInterfaceKey.CONTROL_TEXT_NAME_USER_EMAIL+"\"");
      if(newUserModel != null)
      {
        out.print(" value=\""+personEmail+"\"");
      }
      out.print(">");
      out.println("</TD></TR>");
      if(newUserModel != null)
      {
        out.println("<TR class=TableTextNote>");
        out.println("<TD class=TableTextNote width=\"20%\">User Status</TD>");
        out.println("<TD class=TableTextNote>");
        out.println("<SELECT name="+UserInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+personID+
                    " onchange=\"document.UserManagment.update.disabled = false;\">");
        Vector additionalDataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        for(int j = 0; j<additionalDataVector.size(); j++)
        {
          int statusID = ((UserStatusModel)additionalDataVector.elementAt(j)).getUserStatusID();
          String statusName = ((UserStatusModel)additionalDataVector.elementAt(j)).getUserStatusName();
          out.println("<option value=\""+statusID+"\""); 
          if(userStatusID == statusID)
          {
            out.print("selected");
          }
          out.print(">"+statusName+"</option>");
        }
        out.println("</SELECT>");
        out.println("</TD></TR>");


        out.println("<TR class=TableTextNote>");
        out.println("<TD class=TableTextNote width=\"20%\">User Lock</TD>");
        out.println("<TD class=TableTextNote>");
        out.println("<SELECT name="+UserInterfaceKey.CONTROL_SELSCT_LOCK_PREFIX+personID+
                    " onchange=\"document.UserManagment.update.disabled = false;\">");

        Vector vecLocks = (Vector)dataHashMap.get(UserInterfaceKey.HASHMAP_KEY_USER_LOCK_LIST);

         for(int j = 0; j<vecLocks.size(); j++)
        {
          int lockId = ((UserLockModel)vecLocks.elementAt(j)).getUserLockID();
          String lockName = ((UserLockModel)vecLocks.elementAt(j)).getUserLockName();
           out.println("<option value=\""+lockId+"\""); 
          if(userLockId < maxLockTimes.intValue() && lockName.compareTo("NOT LOCKED")==0)
          {
            out.print("selected");
          }
          out.print(">"+lockName+"</option>");
          

        }
        
        out.println("</SELECT>");
        out.println("</TD></TR>");
//        for(int j = 0; j<additionalDataVector.size(); j++)
//        {
//          int statusID = ((UserStatusModel)additionalDataVector.elementAt(j)).getUserStatusID();
//          String statusName = ((UserStatusModel)additionalDataVector.elementAt(j)).getUserStatusName();
//          out.println("<option value=\""+statusID+"\""); 
//          if(userStatusID == statusID)
//          {
//            out.print("selected");
//          }
//          out.print(">"+statusName+"</option>");
//        }
        
        
      }
      out.println("<TR class=TableTextNote><TD colspan=2 class=TableTextNote><font size=1>* indicates mandatory fields.</font></TD></TR></TABLE>");
      out.println("</TABLE>");
    
      out.println("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\" width=\"100%\">");
      out.println("<tr>");
      out.println("<td align=center>");
      out.println("<input class=button type=\"submit\" name=\"save\"");
      if(newUserModel != null)
      {
        out.print(" value=\" Update \"");
      }
      else
      {
        out.print(" value=\"   Add   \"");
      }
      out.print(" onclick=\"if(NonBlank("+UserInterfaceKey.CONTROL_TEXT_NAME_USER_NAME+", true, 'User Name')){if(NonBlank("+UserInterfaceKey.CONTROL_TEXT_NAME_USER_EMAIL+", true, 'E-Mail')){if(emailInValid("+UserInterfaceKey.CONTROL_TEXT_NAME_USER_EMAIL+".value)){document.UserManagment."+InterfaceKey.HASHMAP_KEY_ACTION+".value='");
      if(newUserModel != null)
      {
        out.print(UserInterfaceKey.ACTION_UPDATE_USER);
      }
      else
      {
        out.print(UserInterfaceKey.ACTION_ADD_USER);
      }
      out.print("';UserManagment.submit();}}} return false;\">");
      out.println("</td>");
      out.println("</tr>");
      out.println("</table></form>");
    }
  }
%>
