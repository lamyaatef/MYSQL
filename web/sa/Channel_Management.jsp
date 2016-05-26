<%@page import="com.mobinil.sds.core.system.request.model.ChannelModel"%>
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
      <H2>Channel Management</H2>
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
    <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="ChannelManagement" method="post">

        
        
        
      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" 
      value="<%out.print(AdministrationInterfaceKey.ACTION_ADD_CHANNEL);%>">

      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>" 
      value="<%out.print("anyname");%>">

      <input type="hidden" name="<%out.print(AdministrationInterfaceKey.CONTROL_HIDDEN_NAME_CHANNEL_ID);%>" value=0>
        
        

      <div name="listofusers" id="listofusers" >
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td width="20%" nowrap align=center>Channel ID</td>
          <td width="40%" nowrap align=center>Channel Name</td>
          <td width="10%" nowrap align=center>Edit</td>
        </tr>	
        <%
       showChannels(request, response, out);
        %>
      </TABLE>
      
      
      <table  cellspacing="2" cellpadding="1" border="0" width="100%">
        <tr>
          <td align=center>
            <input class=button type="button" name="new" value=" New Channel " 
            onclick="document.ChannelManagement.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(AdministrationInterfaceKey.ACTION_NEW_CHANNEL);%>';ChannelManagement.submit();">
            
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

  public void showChannels(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(! dataHashMap.isEmpty()){
      
      Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
       System.out.println("inside datahashmap if dataVector.size() : "+dataVector.size()); 
      //Integer maxLockTimes =(Integer) dataHashMap.get(UserInterfaceKey.HASHMAP_KEY_USER_MAX_LOCK);
      for(int i = 0; i<dataVector.size(); i++){
         System.out.println("Channels SIZE : "+dataVector.size());
        ChannelModel channelModel = (ChannelModel) dataVector.get(i);
        int channelId = channelModel.getChannelId();
        String channelName = channelModel.getChannelName();
        out.println("<TR class=TableTextNote>");
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+"><a href=\"#divname"+channelId+"\" onclick=\"javascript:toggle('"+channelId+"');\">"+channelId+"</a></TD>");
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+">"+channelName+"</TD>");
        
        
        
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+" align=middle>");
        out.println("<input class=button type=\"button\" name=\""+AdministrationInterfaceKey.CONTROL_BUTTON_NAME_EDIT_CHANNEL_PREFIX+channelId+"\" value=\"   Edit   \"");
        out.println("onclick=\"document.ChannelManagement."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+AdministrationInterfaceKey.ACTION_EDIT_CHANNEL+"';");
        out.println("document.ChannelManagement."+AdministrationInterfaceKey.CONTROL_HIDDEN_NAME_CHANNEL_ID+".value='"+channelId+"';");
        out.println("ChannelManagement.submit();\">");
        out.println("</TD>");
        out.println("</TR>");
      }
    }
  }


%>