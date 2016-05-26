<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.dcm.region.model.*"
         import="com.mobinil.sds.core.system.dcm.user.model.*"
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
      if(dataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
      {
        String strMsg = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
        if(strMsg.compareTo("")!=0)
        {
          out.println("<script>alert('"+strMsg+"');</script>");        }
      }
      String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
      String userLevelTypeId = (String)dataHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_LEVEL_TYPE_ID);
      Vector regions = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
      
      String header = "New User";
      int personID = 0;
      String personName = "";
      String personEmail = "";
      String personAddress = "";
      int userStatusID = 0;
      UserModel newUserModel = (UserModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      if(newUserModel != null)
      {
        personID = newUserModel.getPersonModel().getPersonID();
        personName = newUserModel.getPersonModel().getPersonFullName();
        personEmail = newUserModel.getPersonModel().getPersonEMail();
        personAddress = newUserModel.getPersonModel().getPersonAddress();
        userStatusID = newUserModel.getUserStatusID();
        header = personName;
      }

      String userDetailId = "";
      String userId = "";
      String userFullName = "";
      String userAddress = "";
      String userEmail = "";
      String userMobile = "";
      String regionId = "";
      String regionName = "";
      if(dataHashMap.containsKey(DCMInterfaceKey.DCM_HASHMAP_KEY_COLLECTION))
      {
        DCMUserDetailModel dcmUserDetailModel = (DCMUserDetailModel)dataHashMap.get(DCMInterfaceKey.DCM_HASHMAP_KEY_COLLECTION);
        if(dcmUserDetailModel != null)
        {
          userDetailId = dcmUserDetailModel.getUserDetailId();
          userId = dcmUserDetailModel.getUserId();
          userFullName = dcmUserDetailModel.getUserFullName();
          userAddress = dcmUserDetailModel.getUserAddress();
          if(userAddress == null)userAddress = "";
          userEmail = dcmUserDetailModel.getUserEmail();
          userMobile = dcmUserDetailModel.getUserMobile();
          if(userMobile == null)userMobile = "";
          regionId = dcmUserDetailModel.getRegionId();
          regionName = dcmUserDetailModel.getRegionName();
        }
      }
      out.println("<Center>");
      out.println("<H2>"+header+"</H2>");
      out.println("</Center>");
      out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"UserManagment\" method=\"post\">");
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" "+
                     "value=\""+UserInterfaceKey.ACTION_UPDATE_USER+"\">");

      out.println("<input type=\"hidden\" name=\""+UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_NAME+"\" "+
                     "value=\""+serverName+"\">");
      out.println("<input type=\"hidden\" name=\""+UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_PORT+"\" "+
                     "value=\""+serverPort+"\">");
      out.println("<input type=\"hidden\" name=\""+UserAccountInterfaceKey.CONTROL_HIDDEN_CONTEXT_PATH+"\" "+
                     "value=\""+appName+"\">");
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" "+
                     "value="+userID+">");
      out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_LEVEL_TYPE_ID+"\" "+
                     "value="+userLevelTypeId+">");

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
      }

      //Used for DCM User
      out.println("<TR class=TableTextNote>");
      out.println("<td>User Address</td>");
      out.println("<td><input size=\"50%\" type=\"text\" name=\""+DCMInterfaceKey.INPUT_TEXT_DCM_USER_ADDRESS+"\" id=\""+DCMInterfaceKey.INPUT_TEXT_DCM_USER_ADDRESS+"\" value=\""+userAddress+"\"></td>");
      out.println("</TR>");

      out.println("<TR class=TableTextNote>");
      out.println("<td>User Mobile</td>");
      out.println("<td><input size=\"50%\" type=\"text\" name=\""+DCMInterfaceKey.INPUT_TEXT_DCM_USER_MOBILE+"\" id=\""+DCMInterfaceKey.INPUT_TEXT_DCM_USER_MOBILE+"\" value=\""+userMobile+"\"></td>");
      out.println("</TR>");

      out.println("<TR class=TableTextNote>");
      out.println("<td>Region</td>");
      out.println("<td><select name=\""+DCMInterfaceKey.INPUT_SELECT_REGION_ID+"\" id=\""+DCMInterfaceKey.INPUT_SELECT_REGION_ID+"\">");
      out.println("<option value=''></option>");
      for(int r=0;r<regions.size();r++)
      {
        RegionModel regionModel = (RegionModel)regions.get(r);
        String strRegionId = regionModel.getRegionId();
        String strRegionName = regionModel.getRegionName();
        String regionSelected = "";
        if(strRegionId.compareTo(regionId)==0)regionSelected = "selected";
        out.println("<option value='"+strRegionId+"' "+regionSelected+">"+strRegionName+"</option>");
      }
      out.println("</select>");
      out.println("</td></TR>");
      
      /////
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
      out.print(" onclick=\"if(NonBlank("+UserInterfaceKey.CONTROL_TEXT_NAME_USER_NAME+", true, 'User Name')){if(NonBlank("+UserInterfaceKey.CONTROL_TEXT_NAME_USER_EMAIL+", true, 'E-Mail')){if(emailInValid("+UserInterfaceKey.CONTROL_TEXT_NAME_USER_EMAIL+".value)){if(NonBlank("+DCMInterfaceKey.INPUT_SELECT_REGION_ID+", true, 'Region')){document.UserManagment."+InterfaceKey.HASHMAP_KEY_ACTION+".value='");
      if(newUserModel != null)
      {
        //out.print(UserInterfaceKey.ACTION_UPDATE_USER);
        out.print(DCMInterfaceKey.ACTION_DCM_UPDATE_DCM_USER);
      }
      else
      {
        //out.print(UserInterfaceKey.ACTION_ADD_USER);
        out.print(DCMInterfaceKey.ACTION_DCM_ADD_NEW_DCM_USER);
      }
      out.print("';UserManagment.submit();}}}} return false;\">");
      out.println("</td>");
      out.println("</tr>");
      out.println("</table></form>");
    }
  }
%>
