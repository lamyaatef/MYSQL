<%@ page import ="javax.servlet.*"
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
%>
<%
/**
 * User_Login.jsp:
 * User login by email and password.
 *
 *
 * redirect(HttpServletRequest request, HttpServletResponse response, JspWriter out, HttpSession session):
 * Redirect to the user main page if his login is valid.
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
<SCRIPT language=JavaScript>
<!--
//set message:
msg = "Welcome To Sales Distribution System";

timeID = 10;
stcnt = 16;
wmsg = new Array(33);
        wmsg[0]=msg;
        blnk = "                                                               ";
        for (i=1; i<32; i++)
        {
                b = blnk.substring(0,i);
                wmsg[i]="";
                for (j=0; j<msg.length; j++) wmsg[i]=wmsg[i]+msg.charAt(j)+b;
        }

function wiper()
{
        if (stcnt > -1) str = wmsg[stcnt]; else str = wmsg[0];
        if (stcnt-- < -40) stcnt=31;
        status = str;
        clearTimeout(timeID);
        timeID = setTimeout("wiper()",100);
}

wiper()
// -->
</SCRIPT>

<SCRIPT language=javascript>
<!--
if (screen.width <= 800) {
alert("Warning:you are using a large resolution(800x600) Site is best viewed with 1024x768");
}
//-->
</SCRIPT>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {
    if(NonBlank(document.UserLogin.<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_EMAIL);%>, true, 'E-Mail'))
    {
      if(validPassword(document.UserLogin.<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_PASSWORD);%>, true, 'Password',20))
      {
        if(emailInValid(document.UserLogin.<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_EMAIL);%>.value))
        {
          document.UserLogin.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(UserAccountInterfaceKey.ACTION_LOGIN);%>';
          document.UserLogin.submit();
        }
      }
    }
    return false;
  }
</SCRIPT>
<%!
/**
 * redirect method:
 * Redirect to the user main page if his login is valid.
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
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(dataHashMap != null)
    {
      if(! dataHashMap.isEmpty())
      {
        String strError = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);
        if(strError != null)
        {
        out.println("<script language=\"javascript\">alert('"+strError+"');");
        if(strError.compareTo("Current Password Does not Meet Security Rules")==0){
            System.out.println("fhgaldfjkagdflklkajdf "+strError);
            String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            out.println("document.UserLogin."+InterfaceKey.HASHMAP_KEY_ACTION+".value=\""+UserAccountInterfaceKey.ACTION_GO_TO_RENEW_PASSWORD+"\";");
            out.println("document.UserLogin."+InterfaceKey.HASHMAP_KEY_USER_ID+".value="+userID+";");
            out.println("document.UserLogin.submit();");


          }
                    out.println("</script>");
        }
        else
        {
          String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
          if(userID != null)
          {
            session.setAttribute(InterfaceKey.HASHMAP_KEY_USER_ID, userID);
            out.println("<script type=\"text/javascript\">");
            out.println("document.UserLogin."+InterfaceKey.HASHMAP_KEY_ACTION+".value=\""+UserAccountInterfaceKey.ACTION_SHOW_USER_MAIN+"\";");
            out.println("document.UserLogin."+InterfaceKey.HASHMAP_KEY_USER_ID+".value="+userID+";");
            out.println("document.UserLogin.submit();");
            out.println("</script>");
          }
        }
      }
    }
  }
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    <TITLE>:::: SDS ::::</TITLE>
  </head>
  <body onkeypress = "if(event.keyCode==13){checkbeforSubmit();}">
    <center>
      <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="UserLogin" method="post">

       <!--<input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>"
        value="<%out.print(UserAccountInterfaceKey.ACTION_LOGIN);%>"> -->
       <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>"
        value="<%out.print(UserAccountInterfaceKey.ACTION_LOGIN);%>">

        <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>" value="">

         <input type="hidden" name="<%out.print(UserAccountInterfaceKey.CONTROL_HIDDEN_NAME_ACTIVATION_PAGE);%>"
        value="<%out.print("0");%>">

        <%redirect(request, response, out, session);%>
        <TABLE cellSpacing=0 cellPadding=0 align=center border=0>
        <TBODY>
        <TR>
          <TD colspan=2><IMG src="<%out.print(appName);%>/resources/img/images/SDS_02.gif"></TD>
        <TR>
        <TR>
          <TD>
            <TABLE border=0 cellSpacing=0 cellPadding=0>
              <tr>
                <td><IMG src="<%out.print(appName);%>/resources/img/images/SDS_04.gif"></td>
                <td><IMG src="<%out.print(appName);%>/resources/img/images/SDS_05.gif"></td>
                <td><IMG src="<%out.print(appName);%>/resources/img/images/SDS_06.gif"></td>
                <td><IMG src="<%out.print(appName);%>/resources/img/images/SDS_07.gif"></td>
                <td><IMG src="<%out.print(appName);%>/resources/img/images/SDS_08.gif"></td>
                <td><IMG src="<%out.print(appName);%>/resources/img/images/SDS_09.gif"></td>
              </tr>
              <tr>
                <td colspan=2><IMG src="<%out.print(appName);%>/resources/img/images/SDS_11.gif"></td>
                <td colspan=2><IMG src="<%out.print(appName);%>/resources/img/images/SDS_12.gif"></td>
                <td colspan=2><IMG src="<%out.print(appName);%>/resources/img/images/SDS_13.gif"></td>
              </tr>
              <tr>
                <td colspan=6><IMG src="<%out.print(appName);%>/resources/img/images/SDS_14.gif"></td>
              </tr>
              <tr style="background: url(<%out.print(appName);%>/resources/img/images/SDS_32.gif) no-repeat 0 0;">
                <td colspan=3><IMG src="<%out.print(appName);%>/resources/img/images/SDS_15.gif"></td>
                <td colspan=2><input type="text" name="<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_EMAIL);%>" value=""></td>
                <td><IMG src="<%out.print(appName);%>/resources/img/images/SDS_17.gif"></td>
              </tr>
              <tr>
                <td colspan=6><IMG src="<%out.print(appName);%>/resources/img/images/SDS_18.gif"></td>
              </tr>
              <tr style="background: url(<%out.print(appName);%>/resources/img/images/SDS_32.gif) no-repeat 0 0;">
                <td colspan=3><IMG src="<%out.print(appName);%>/resources/img/images/SDS_19.gif"></td>
                <td colspan=2><input type="password" name="<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_PASSWORD);%>" value=""></td>
                <td><IMG src="<%out.print(appName);%>/resources/img/images/SDS_21.gif"></td>
              </tr>
              <tr>
                <td colspan=6><IMG src="<%out.print(appName);%>/resources/img/images/SDS_22.gif"></td>
              </tr>
              <tr>
                <td colspan=2><IMG src="<%out.print(appName);%>/resources/img/images/SDS_23.gif"></td>
                <td><a href="#" onclick="checkbeforSubmit();"><IMG border=0 alt="Login" src="<%out.print(appName);%>/resources/img/images/SDS_24.gif"></a></td>
                <td><a href="#" onclick="document.UserLogin.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(UserAccountInterfaceKey.ACTION_FORGOT_PASSWORD);%>';UserLogin.submit();"><IMG border=0 alt="Forget Password" src="<%out.print(appName);%>/resources/img/images/SDS_25.gif"></a></td>
                <td colspan=2><IMG src="<%out.print(appName);%>/resources/img/images/SDS_26.gif"></td>
              </tr>
              <tr>
                <td colspan=2><IMG src="<%out.print(appName);%>/resources/img/images/SDS_27.gif"></td>
                <td><IMG src="<%out.print(appName);%>/resources/img/images/SDS_28.gif"></td>
                <td><IMG src="<%out.print(appName);%>/resources/img/images/SDS_29.gif"></td>

                <td colspan=2><IMG src="<%out.print(appName);%>/resources/img/images/SDS_30.gif"></td>
              </tr>
            </table>
          </TD>
          <td><IMG src="<%out.print(appName);%>/resources/img/images/SDS_10.gif"></td>
        </TR>
        <TR>
          <TD colspan=2><IMG src="<%out.print(appName);%>/resources/img/images/SDS_31.gif"></TD>
        <TR>
       </TABLE>
      </form>
    </center>
  </body>
</html>