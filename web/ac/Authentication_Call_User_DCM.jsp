<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.web.interfaces.cr.*"
         import="com.mobinil.sds.web.interfaces.ac.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
%>
<%
/**
 * Authentication_Call_User_DCM.jsp:
 * Managae Users DCMs access in the authentication call moduel.
 * 
 * showUsers(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Display the system Users.
 *
 * showDCMs(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Build hidden divs, each holding DCMs of one user.
 * A user DCMs div will be visible if the user was selected.
 *
 * @version	1.01 May 2004
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
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/utilities.js" type=text/javascript></SCRIPT>
  </head>
  <body>
      <Center>
        <H2>Authentication Call Administration</H2>
      </Center>
        
      <left>
       <h3>User DCM Managment</h3>
      </left>
    <script type="text/javascript">
      function toggle(item1)
      {
        divs=document.getElementsByTagName("DIV");
        for (i=0;i<divs.length;i++) 
        {
          if(divs[i].id != item1)
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
    <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="UserManagment" method="post">

      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>" value="<%out.print(userID);%>">

      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td width="20%" nowrap align=center>User Full Name</td>
          <td width="50%" nowrap align=center>E-Mail</td>
          <td width="20%" nowrap align=center>Status</td>
        </tr>	
        <%
        showUsers(request, response, out);
        %>
      </TABLE>
    </form>
    <%
    showDCMs(request, response, out);
    %>
  </body>
</html>
<%
  /**
   * Display any error messages returning from the server.
   */ 

  dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
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
  throws ServletException, IOException{
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(! dataHashMap.isEmpty()){
      Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      for(int i = 0; i<dataVector.size(); i++){
        int personID = ((UserDTO)dataVector.elementAt(i)).getUserModel().getPersonModel().getPersonID();
        String personName = ((UserDTO)dataVector.elementAt(i)).getUserModel().getPersonModel().getPersonFullName();
        String personEMail = ((UserDTO)dataVector.elementAt(i)).getUserModel().getPersonModel().getPersonEMail();
        int userStatusID = ((UserDTO)dataVector.elementAt(i)).getUserModel().getUserStatusID();
        out.println("<TR class=TableTextNote>");
        out.println("<TD class=TableTextNote><a href=\"#divname"+personID+"\" onclick=\"javascript:toggle('"+personID+"');\">"+personName+"</a></TD>");
        out.println("<TD class=TableTextNote>"+personEMail+"</TD>");
        out.println("<TD class=TableTextNote align=middle>");
        HashMap additionalDataHashMap = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        Vector vecStatus = (Vector)additionalDataHashMap.get(UserInterfaceKey.HASHMAP_KEY_USER_STATUSES_LIST);
        for(int j = 0; j<vecStatus.size(); j++)
        {
          int statusID = ((UserStatusModel)vecStatus.elementAt(j)).getUserStatusID();
          String statusName = ((UserStatusModel)vecStatus.elementAt(j)).getUserStatusName();
          if(userStatusID == statusID)
          {
          out.print(statusName);
          }
        }
        out.println("</TD>");
        out.println("</TR>");
      }
    }
  }

  /**
   * showDCMs method: 
   * Build hidden divs, each holding DCMs of one user.
   * A user DCMs div will be visible if the user was selected.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
   * @return  
   * @throws  ServletException, IOException
   * @see    
   *
   */ 

  public void showDCMs(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(! dataHashMap.isEmpty())
    {
      String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
      Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      for(int i = 0; i<dataVector.size(); i++)
      {
        int personID = ((UserDTO)dataVector.elementAt(i)).getUserModel().getPersonModel().getPersonID();
        String personName = ((UserDTO)dataVector.elementAt(i)).getUserModel().getPersonModel().getPersonFullName();
        out.println("<a name=\"divname"+personID+"\" id=\"divname"+personID+"\">");
        out.println("<div style=\"DISPLAY: none\" id="+personID+" name="+personName+">");
        out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"user_"+personID+"\" method=\"post\">");
        out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\"");
        out.print(" value=\""+AuthCallInterfaceKey.ACTION_UPDATE_USER_DCM_AUTHENTICATION_CALL+"\">");
        out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value=\""+userID+"\">");
        out.println("<input type=\"hidden\" name=\""+UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID+"\" value=\""+personID+"\">");
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_DCM_ID+"\">");
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_DCM_NAME+"\">");
        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
        out.println("<TR class=TableHeader><td colspan=3 nowrap align=middle>"+personName+" DCMs</td></tr>");
        out.println("<TR class=TableHeader>");
        out.println("<td width=\"40%\" nowrap align=middle>DCM Name</td>");
        out.println("<td width=\"40%\" nowrap align=middle>DCM Code</td>");
        out.println("<td width=\"20%\" nowrap align=middle>Assigned</td></tr>");
        Vector userDCM = ((UserDTO)dataVector.elementAt(i)).getUserDCMs();
        HashMap additionalDataHashMap = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        DCMDto newDCMDto = (DCMDto)additionalDataHashMap.get(UserInterfaceKey.HASHMAP_KEY_DCMS_LIST);
        for(int j = 0; j<newDCMDto.getDcmModelsSize(); j++)
        {
          
          int DCMID = newDCMDto.getDcm(j).getDcmId();
          String DCMName = newDCMDto.getDcm(j).getDcmName();
          String DCMCode = newDCMDto.getDcm(j).getDcmCode();
          if(DCMCode == null)
          {
            DCMCode = "";
          }
          out.println("<TR>");
          out.println("<TD class=TableTextNote>"+DCMName+"</TD>");
          out.println("<TD class=TableTextNote align=middle>"+DCMCode+"</TD>");
          out.println("<TD class=TableTextNote align=middle>");
          out.println("<input type=\"checkbox\" name="+UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX+DCMID+
                      " vlaue="+DCMID+" onclick=\"document.user_"+personID+".update.disabled = false;\"");
          for(int l = 0; l<userDCM.size(); l++)
          {
            int nUserDCMID = ((UserDCMModel)userDCM.elementAt(l)).getDCMID();
            if(DCMID == nUserDCMID)
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
        out.println("<input class=button type=\"submit\" name=\"update\" value=\"Update "+personName+" DCMs\" disabled>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table></form></div>");
        out.println("</a>");
      }
    }
  }

%>