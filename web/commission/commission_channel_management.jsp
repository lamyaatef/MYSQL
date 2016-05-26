<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.web.interfaces.commission.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.commission.model.*"
         
         
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
    HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template2.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/utilities.js" type=text/javascript></SCRIPT>

    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/tree.js" type=text/javascript></SCRIPT>
  </head>
  <body>
    <Center>
      <H2>Assign Channel To User</H2>
    </Center>    
    <script type="text/javascript">
      function newSavechannels(personID) 
      {

      document.channelManagment.<%out.print(UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID);%>.value=personID;
      document.channelManagment.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value="<%out.print(CommissionInterfaceKey.ACTION_COMMISSION_ASSIGN_CHANNEL_TO_USER);%>";
      channelManagment.submit();
      }
      
      function toggle1(item1)
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
    <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="channelManagment" method="post">

      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" 
      value="<%out.print(CommissionInterfaceKey.ACTION_COMMISSION_ASSIGN_CHANNEL_TO_USER);%>">

      <input type="hidden" name="<%out.print(CommissionInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);%>" 
      value="">

      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>" 
      value="<%out.print(userID);%>">

      <input type="hidden" name="<%out.print(UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID);%>" 
      value="">
      

<!--      <input type="hidden" name="<%out.print(UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID);%>" value=0>-->
      <div name="listofusers" id="listofusers" style="display:none">
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td width="50%" nowrap align=center>User Full Name</td>
          <td width="50%" nowrap align=center>E-Mail</td>
          <!--td width="10%" nowrap align=center>Edit</td-->
        </tr>
        
        <%
        showUsers(request, response, out);
        %>
        
      </TABLE>
      </div>

    <%
    showChannels(request, response, out);
    %>
    </form>
    
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
   * showUsers method: 
   * Display the system Users.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
   * @return  
   * @throws  ServletException, IOException
   * @see    
   *
   */ 

  public void showUsers(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(! dataHashMap.isEmpty()){
      Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      for(int i = 0; i<dataVector.size(); i++){
        int personID = ((UserDTO)dataVector.elementAt(i)).getUserModel().getPersonModel().getPersonID();
        String personName = ((UserDTO)dataVector.elementAt(i)).getUserModel().getPersonModel().getPersonFullName();
        String personEMail = ((UserDTO)dataVector.elementAt(i)).getUserModel().getPersonModel().getPersonEMail();
        int userStatusID = ((UserDTO)dataVector.elementAt(i)).getUserModel().getUserStatusID();
        out.println("<TR class=\""+InterfaceKey.STYLE[i%2]+"\">");
        out.println("<TD><a href=\"#divname"+personID+"\" onclick=\"javascript:toggle1('"+personID+"');\">"+personName+"</a></TD>");
        out.println("<TD>"+personEMail+"</TD>");        
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

  public void showChannels(HttpServletRequest request, HttpServletResponse response, JspWriter out)
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

        Vector userRole = ((UserDTO)dataVector.elementAt(i)).getUserRoles();
        
        Vector vecChannelList = (Vector) dataHashMap.get (CommissionInterfaceKey.CHANNEL_VECTOR) ;
        Vector personChannelList = (Vector) dataHashMap.get (CommissionInterfaceKey.USER_CHANNEL_VECTOR) ;
        
          int reportListSize = vecChannelList.size();
        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
        out.println("<TR class=TableHeader>");
        out.println("<td nowrap align=center colspan=3><font size=2>Assign "+personName+" to Channels</font></a></TD></TR>");
        //System.out.println("User name "+personName);
        for(int j = 0; j<vecChannelList.size(); j++)
        {
          ChannelModel cm =  (ChannelModel) vecChannelList.elementAt (j);
          int channelId = cm.getChannelId().intValue();    
          String channelName=cm.getChannelName();
          //System.out.println("channel id  "+channelId + " User name "+ personName);

          


        out.println("<TR class=TableTextNote>");
        out.println("<td class="+InterfaceKey.STYLE[j%2]+">");
        
        out.print(channelName);    

         out.println("</td><td width=\"5%\" align=center class="+InterfaceKey.STYLE[j%2]+">");


         out.println("<input type=\"checkbox\" name="+UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX+personID+channelId+ 
         " onclick=\"document.getElementById('update"+personID+"').disabled = false;\"");
         
         
         
         for (int t=0;t<personChannelList.size();t++)
         {
        
        	int userId = ((ChannelModel)personChannelList.elementAt(t)).getChannelId().intValue();	
        	String channelValue =((ChannelModel)personChannelList.elementAt(t)).getChannelName();
        	
            int nChannelId =  Integer.parseInt(channelValue);
            int nPersonId =  userId;            
            if (channelId==nChannelId && nPersonId ==  personID)
            {
              out.print(" checked");
              break;
                                             
            }
           
         }
          
                    out.println("></td>"); 
         out.println("</tr>");
        }
         out.println("</TABLE>");
       

        out.println("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\" width=\"100%\">");
        out.println("<tr>");
        out.println("<td align=center>");
        out.println("<input class=button type=\"button\" onClick=newSavechannels("+personID+") name=\"update"+personID+"\" value=\"Update "+personName+" Channels\" disabled>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table></div></a>");

      }
    }
    out.println("<script>var obj = document.getElementById('listofusers');obj.style.display='';</script>");    
  }
%>