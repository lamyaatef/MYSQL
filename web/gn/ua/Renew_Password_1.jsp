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
 * Renew_Password.jsp:
 * Ask for creating a new password .
 * 
 * 
 * redirect(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Redirect to the login page if the new password is valid.
 *  
 *
 * @version	1.01 Jan 2009
 * @author  Mahmoud Abdel aal
 * @see     
 *
 * SDS
 * MobiNil
 */ 
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
<%!
/**
 * redirect method: 
 * Redirect to the login page if the user email is valid and that 
 * he was sent a new password to his email. 
 *
 * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
 * @return  
 * @throws  ServletException, IOException
 * @see    
 *
 */ 


  public void redirect(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    if(dataHashMap != null)
    {
      if(! dataHashMap.isEmpty())
      {
        String strError = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);
        if(strError != null)
        {
                    System.out.println("strError1 "+strError);
          out.println("<script type=\"text/javascript\">alert('"+strError+"');</script>");
        }
        else
        {
          String strMessage = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
          System.out.println("strMessage1 "+strMessage);
          if(strMessage != null)
          {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('"+strMessage+"');");
            out.println("document.NewPassword."+InterfaceKey.HASHMAP_KEY_ACTION+".value=\""+UserAccountInterfaceKey.ACTION_REDIRECT_TO_LOGIN_PAGE+"\";");
            out.println("NewPassword.submit();");
            out.println("</script>");
          }
        }
      }
    }
  }
%>
<%
  String serverName = request.getServerName();
  int serverPort = request.getServerPort();
  String appName = request.getContextPath();
  HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  System.out.println("sdada "+userID);
%>









              
              





<html>
  <head>
    <TITLE>:::: SDS ::::</TITLE>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type=text/javascript></SCRIPT>
  </head>
  <body>
    <Center>
      <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="NewPassword" method="post"
        onsubmit="if(NonBlank(<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_NEW_PASSWORD);%>, true, 'text'))
         {
         if(NonBlank(<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_CONFIRM_PASSWORD);%>, true, 'text'))
         {
        return true;
         }   
        } 

        return false;">

        <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>"
        value="<%out.print(UserAccountInterfaceKey.ACTION_NEW_PASSWORD);%>">

        <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>" 
        value="<%out.print(userID);%>">
      
        <input type="hidden" name="<%out.print(UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_NAME);%>"
        value="<%out.print(serverName);%>">
        <input type="hidden" name="<%out.print(UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_PORT);%>"
        value="<%out.print(serverPort);%>">
        <input type="hidden" name="<%out.print(UserAccountInterfaceKey.CONTROL_HIDDEN_CONTEXT_PATH);%>"
        value="<%out.print(appName);%>">

        <%redirect(request, response, out);%>
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
                <td colspan=2><IMG src="<%out.print(appName);%>/resources/img/images/SDS_39.gif"></td>
                <td colspan=2><IMG src="<%out.print(appName);%>/resources/img/images/SDS_13.gif"></td>
              </tr>
              <tr>
                <td colspan=6><IMG src="<%out.print(appName);%>/resources/img/images/SDS_14.gif"></td>
              </tr>
              <tr style="background: url(<%out.print(appName);%>/resources/img/images/SDS_32.gif) no-repeat 0 0;"> 
                <td colspan=3><IMG src="<%out.print(appName);%>/resources/img/images/SDS_19.gif"></td>
                <td colspan=2><input type="password" name="<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_NEW_PASSWORD);%>" value=""></td>
                <td><IMG src="<%out.print(appName);%>/resources/img/images/SDS_17.gif"></td>
              </tr>
              <tr>
                <td colspan=6><IMG src="<%out.print(appName);%>/resources/img/images/SDS_18.gif"></td>
              </tr>
              <tr style="background: url(<%out.print(appName);%>/resources/img/images/SDS_32.gif) no-repeat 0 0;">
                <td colspan=3><IMG src="<%out.print(appName);%>/resources/img/images/SDS_19.gif"></td>
                <td colspan=2><input type="password" name="<%out.print(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_CONFIRM_PASSWORD);%>" value=""></td>
                <td><IMG src="<%out.print(appName);%>/resources/img/images/SDS_21.gif"></td>
              </tr>
              <tr>
                <td colspan=6><IMG src="<%out.print(appName);%>/resources/img/images/SDS_22.gif"></td>
              </tr>
              <tr>
                <td colspan=2><IMG src="<%out.print(appName);%>/resources/img/images/SDS_23.gif"></td>
                <td colspan=2 align="center" bgcolor="#E6E2DB"><a href="#" onclick="submit();"><IMG border=0 alt="Login" src="<%out.print(appName);%>/resources/img/images/SDS_34.gif"></a></td>
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
          <td><IMG src="<%out.print(appName);%>/resources/img/images/SDS_41.gif"></td>
        </TR>  
        <TR>
          <TD colspan=2><IMG src="<%out.print(appName);%>/resources/img/images/SDS_31.gif"></TD>
        <TR>      
       </TABLE>
      </form>
    </center>
  </body>
</html>