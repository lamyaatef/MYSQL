<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.gn.ua.*"
%>
<%
/**
 * Change_Password.jsp:
 * Change the user password.
 * 
 * 
 * redirect(HttpServletRequest request, HttpServletResponse response, JspWriter out, HttpSession session): 
 * Redirect to the login page if the user id is no longer found in the session. 
 * Or redirect to the user main if the password was changed successfuly.
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
    <Center>
      <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="UserLogin" method="post">
      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>"
      value="<%out.print(UserAccountInterfaceKey.ACTION_SAVE_NEW_PASSWORD);%>">
      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>"
      value="<%out.print(session.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID));%>">
      <%redirect(request, response, out, session);%>
      <H2>Change Password</H2>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 border=1>
          <TR class=TableTextNote>
            <TD class=TableTextNote>Old Password</TD><TD class=TableTextNote>
              <input type="password" name="<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_OLD_USER_PASSWORD);%>">
            </TD>
          </TR>
          <TR class=TableTextNote>
            <TD class=TableTextNote>New Password</TD><TD class=TableTextNote>
              <input type="password" name="<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_NEW_USER_PASSWORD);%>">
            </TD>
          </TR>
          <TR class=TableTextNote>
            <TD class=TableTextNote>Confirm Password</TD><TD class=TableTextNote>
              <input type="password" name="<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_PASSWORD);%>">
            </TD>
          </TR>
        </TABLE>
        <table  cellspacing="2" cellpadding="1" border="0" width="100%">
          <tr>
            <td align=center>
              <input class="button" type="submit" name="save" value="Change Password"
                 onclick="if(notEqual(<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_OLD_USER_PASSWORD);%>,
                            <%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_NEW_USER_PASSWORD);%>,
                            'Old Password','New Password'))
                          {
                            if(equal(<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_NEW_USER_PASSWORD);%>,
                                    <%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_PASSWORD);%>,
                                    'New Password','Confirm Password'))
                            {
                              if(validPassword(<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_OLD_USER_PASSWORD);%>, true, 'Old Password'))
                              {
                                if(validPassword(<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_NEW_USER_PASSWORD);%>, true, 'New Password'))
                                {
                                  if(validPassword(<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_PASSWORD);%>, true, 'Confirm Password'))
                                  {
                                    document.UserLogin.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(UserAccountInterfaceKey.ACTION_SAVE_NEW_PASSWORD);%>';
                                    UserLogin.submit();
                                  }
                                }
                              }
                            }
                          } return false;">
            </td>
          </tr>
        </table>
      </form>
    </Center>
  </body>
</html>

<%!
  /**
   * redirect method:
   * Redirect to the login page if the user id is no longer found in the session. 
   * Or redirect to the user main if the password was changed successfuly.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out, HttpSession session
   * @return  
   * @throws  ServletException, IOException
   * @see    
   * 
  */
  public void redirect(HttpServletRequest request, HttpServletResponse response, JspWriter out, HttpSession session)
  throws ServletException, IOException
  {
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
      out.println("document.UserLogin.target=\"_top\";");
      out.println("document.UserLogin."+InterfaceKey.HASHMAP_KEY_ACTION+".value=\""+UserAccountInterfaceKey.ACTION_SHOW_LOGIN_PAGE+"\";");
      out.println("UserLogin.submit();");
      out.println("</script>");
    }
    else
    {
      if(dataHashMap != null)
      {
        if(! dataHashMap.isEmpty())
        {
          String strError = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);
          if(strError != null)
          {
            out.println("<script type=\"text/javascript\">alert('"+strError+"');</script>");
          }
          else
          {
            String strMessage = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
            if(strMessage != null)
            {
              out.println("<script type=\"text/javascript\">alert('"+strMessage+"');");
              out.println("document.UserLogin."+InterfaceKey.HASHMAP_KEY_ACTION+".value=\""+UserAccountInterfaceKey.ACTION_SHOW_USER_MAIN+"\";");
              out.println("document.UserLogin.target='_top';");
              out.println("UserLogin.submit();");
              out.println("</script>");
            }
          }
        }
      }
    }
  }
%>
