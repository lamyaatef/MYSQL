<%@page import="com.mobinil.sds.core.system.dataMigration.model.PaymentLevelModel"%>
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
        showPaymentLevel(request, response, out);
        %>
  </body>
</html>

   <javascript> <SCRIPT language=JavaScript>
    
    function caDownload (flag ) {
        
        if ( NonBlank(document.getElementById('<% out.print(AdministrationInterfaceKey.CONTROL_TEXT_NAME_PAYMENT_LEVEL_NAME );%>'), true, 'Payment Level Name') )
       
        {
            var elem = document.getElementById('<% out.print(InterfaceKey.HASHMAP_KEY_ACTION ); %>');

            if (!flag ) {        

                    elem.value = '<%out.print(AdministrationInterfaceKey.ACTION_UPDATE_PAYMENT_LEVEL);%>' ;
                }

            else
                {
                    elem.value = '<%out.print(AdministrationInterfaceKey.ACTION_ADD_PAYMENT_LEVEL);%>' ;
                }


             document.getElementById("Payment_Level_Management").submit();

 
        }

   }
</script>
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

  public void showPaymentLevel(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String serverName = request.getServerName();
    int serverPort = request.getServerPort();
    String appName = request.getContextPath();

    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(dataHashMap != null)
    {
      String paymentLevelID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
      String header = "New Payment Level";
      String paymentLevelName = "";
      PaymentLevelModel newPaymentLevelModel = (PaymentLevelModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      if(newPaymentLevelModel != null)
      {
        paymentLevelName = newPaymentLevelModel.getPaymentLevelName();
        header = paymentLevelName;
      }
      out.println("<Center>");
      out.println("<H2>"+header+"</H2>");
      out.println("</Center>");
      out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" id=\"Payment_Level_Management\" name=\"Payment_Level_Management\" method=\"post\">");
      out.println("<input type=\"hidden\" id = \"action\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" >");
      

      out.println("<input type=\"hidden\" name=\""+UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_NAME+"\" "+
                     "value=\""+serverName+"\">");
      out.println("<input type=\"hidden\" name=\""+UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_PORT+"\" "+
                     "value=\""+serverPort+"\">");
      out.println("<input type=\"hidden\" name=\""+UserAccountInterfaceKey.CONTROL_HIDDEN_CONTEXT_PATH+"\" "+
                     "value=\""+appName+"\">");
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" "+
                     "value="+paymentLevelID+">");

      out.println("<input type=\"hidden\" name=\""+UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID+"\" value=\""+paymentLevelID+"\">");
      out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width=\"20%\">Payment Level Name *</TD><TD class=TableTextNote>");
      out.println("<input size=\"50%\" type=\"text\" id=\""+AdministrationInterfaceKey.CONTROL_TEXT_NAME_PAYMENT_LEVEL_NAME+"\" name=\""+AdministrationInterfaceKey.CONTROL_TEXT_NAME_PAYMENT_LEVEL_NAME+"\"");
      if(newPaymentLevelModel != null)
      {
        out.print(" value=\""+paymentLevelName+"\"");
      }
      out.print(">");
      out.println("</TD></TR>");
      out.println("<TR class=TableTextNote><TD colspan=2 class=TableTextNote><font size=1>* indicates mandatory fields.</font></TD></TR></TABLE>");
      out.println("</TABLE>");
    
      out.println("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\" width=\"100%\">");
      out.println("<tr>");
      out.println("<td align=center>");
       if(newPaymentLevelModel != null)
      {
          out.println("<input class=button type=\"button\" name=\"save\" onclick=\"caDownload(false)\" ");
        out.print(" value=\" Update \"");
      }
      else
      {
          out.println("<input class=button type=\"button\" name=\"save\" onclick=\"caDownload(true)\" ");
        out.print(" value=\"   Add   \"");
      }
      out.println("</td>");
      out.println("</tr>");
      out.println("</table></form>");
    }
  }
%>
