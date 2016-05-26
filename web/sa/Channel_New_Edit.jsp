<%@page import="com.mobinil.sds.core.system.request.model.ChannelModel"%>
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
        showChannel(request, response, out);
        %>
  </body>
</html>
<javascript> <SCRIPT language=JavaScript>
    
    function caDownload (flag ) {
        
        if ( NonBlank(document.getElementById('<% out.print(AdministrationInterfaceKey.CONTROL_TEXT_NAME_CHANNEL_NAME );%>'), true, 'Channel Name'))
       
    {
        var elem = document.getElementById('<% out.print(InterfaceKey.HASHMAP_KEY_ACTION ); %>');
    
        if (!flag ) {        

                elem.value = '<%out.print(AdministrationInterfaceKey.ACTION_UPDATE_CHANNEL);%>' ;
            }

        else
            {
                elem.value = '<%out.print(AdministrationInterfaceKey.ACTION_ADD_CHANNEL);%>' ;
            }


         document.getElementById("ChannelManagement").submit();
     
 
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

  public void showChannel(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String serverName = request.getServerName();
    int serverPort = request.getServerPort();
    String appName = request.getContextPath();
    
    
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(dataHashMap != null)
    {
      String channelID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
      System.out.println("channel id : "+channelID);
      String header = "New Channel";
      String channelName="";
      
      ChannelModel newChannelModel = (ChannelModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      if(newChannelModel != null)
      {
        channelName = newChannelModel.getChannelName();
        header = channelName;
      }
      out.println("<Center>");
      out.println("<H2>"+header+"</H2>");
      out.println("</Center>");
      out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"ChannelManagement\" id=\"ChannelManagement\" method=\"post\">       ");
      
      out.println("<input type=\"hidden\" id=\"action\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" >");
      
      //out.println("<input type=\"hidden\" id=\"action\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" " + "value=\""+AdministrationInterfaceKey.ACTION_ADD_CHANNEL+"\">");
      
/*      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" "+
                     "value=\""+AdministrationInterfaceKey.ACTION_UPDATE_CHANNEL+"\">");
      */
      
     
      
      out.println("<input type=\"hidden\" name=\""+UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_NAME+"\" "+
                     "value=\""+serverName+"\">");
      out.println("<input type=\"hidden\" name=\""+UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_PORT+"\" "+
                     "value=\""+serverPort+"\">");
      out.println("<input type=\"hidden\" name=\""+UserAccountInterfaceKey.CONTROL_HIDDEN_CONTEXT_PATH+"\" "+
                     "value=\""+appName+"\">");
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" "+
                     "value="+channelID+">");

      out.println("<input type=\"hidden\" name=\""+UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID+"\" value=\""+channelID+"\">");
      out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width=\"20%\">Channel Name *</TD><TD class=TableTextNote>");
      out.println("<input size=\"50%\" type=\"text\" id=\"channel_name\" name=\""+AdministrationInterfaceKey.CONTROL_TEXT_NAME_CHANNEL_NAME+"\"");
      if(newChannelModel != null)
      {
        out.print(" value=\""+channelName+"\"");
      }
      out.print(">");
      out.println("</TD></TR>");
      
      out.println("<TR class=TableTextNote><TD colspan=2 class=TableTextNote><font size=1>* indicates mandatory fields.</font></TD></TR></TABLE>");
      out.println("</TABLE>");
    
      out.println("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\" width=\"100%\">");
      out.println("<tr>");
      out.println("<td align=center>");
      
      
      
      
      if(newChannelModel != null)
      {
          out.println("<input class=button type=\"button\"  name=\"save\" onclick=\"caDownload(false)\"");
        out.print(" value=\" Update \">");
      }
      else
      {
          out.println("<input class=button type=\"button\"  name=\"save\" onclick=\"caDownload(true)\"");
        out.print(" value=\"   Add   \">");
      }
      

      
      out.println("</td>");
      out.println("</tr>");
      out.println("</table></form>");
    }
  }
%>
