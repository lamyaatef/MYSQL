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
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/login-04.css">
    <!--[if IE 7]><link rel="stylesheet" href="<%out.print(appName);%>/resources/css/login-04-ie7.css" type="text/css" /><![endif]-->
<!--[if IE 8]><link rel="stylesheet" href="<%out.print(appName);%>/resources/css/login-04-ie8.css" type="text/css" /><![endif]-->
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    <TITLE>:::: SDS ::::</TITLE>
  </head>
  <body onkeypress = "if(event.keyCode==13){checkbeforSubmit();}">
    
      

<img src="<%out.print(appName);%>/resources/img/images/logo.jpg" class="logo-page" alt="" />

<div id="page-login">

<div class="form-login">

<div class="form-login-space">

<h1><span>Welcome To SDS</span></h1>
      
      <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="UserLogin" method="post">

       <!--<input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>"
        value="<%out.print(UserAccountInterfaceKey.ACTION_LOGIN);%>"> -->
       <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>"
        value="<%out.print(UserAccountInterfaceKey.ACTION_LOGIN);%>">

        <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>" value="">

         <input type="hidden" name="<%out.print(UserAccountInterfaceKey.CONTROL_HIDDEN_NAME_ACTIVATION_PAGE);%>"
        value="<%out.print("0");%>">

        <%redirect(request, response, out, session);%>
        
                <label>User Name:</label>
              <input type="text" name="<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_EMAIL);%>" value="">
                
              
          
              
                <label>Password:</label>
                <input type="password" name="<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_PASSWORD);%>" value="">                
              
                 <button onclick="checkbeforSubmit();" type="submit" id="submit">Login</button>


      </form>

                
                
<p><a href="#" onclick="document.UserLogin.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(UserAccountInterfaceKey.ACTION_FORGOT_PASSWORD);%>';UserLogin.submit();">Forgot your password?</a></p>

</div>

</div>

</div>

<!-- Page End -->


<div class="clear"></div>


<!-- Footer -->

<div id="footer-login">

<p>&copy; Egyptian Company for Mobile Services (ECMS), all rights reserved. SDS developed by <a href="http://www.sandcti.com/" target="_blank">SAND S.A.E.</a></p>

</div>

<!-- Footer End -->

        
  </body>
</html>