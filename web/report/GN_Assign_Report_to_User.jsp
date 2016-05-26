<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.web.interfaces.gn.reports.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.gn.reports.dto.*"
         import="com.mobinil.sds.core.system.gn.reports.model.*"
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
      <H2>User Managment</H2>
    </Center>    
    <script type="text/javascript">
      function newSaveReports(personID) 
      {

      document.UserManagment.<%out.print(UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID);%>.value=personID;
      document.UserManagment.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value="<%out.print(ReportInterfaceKey.ACTION_UPDATE_USER_REPORTS);%>";
      UserManagment.submit();
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
    <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="UserManagment" method="post">

      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" 
      value="<%out.print(ReportInterfaceKey.ACTION_UPDATE_USER_REPORTS);%>">

      <input type="hidden" name="<%out.print(ReportInterfaceKey.PARAM_GROUP_ID);%>" 
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
    showReports(request, response, out);
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
        /*out.println("<TD class=TableTextNote align=middle>");
        out.println("<input class=button type=\"button\" name=\""+UserInterfaceKey.CONTROL_BUTTON_NAME_EDIT_USER_PREFIX+personID+"\" value=\"   Edit   \"");
        out.println("onclick=\"document.UserManagment."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+UserInterfaceKey.ACTION_EDIT_USER+"';");
        out.println("document.UserManagment."+UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID+".value='"+personID+"';");
        out.println("UserManagment.submit();\">");
        out.println("</TD>");
        */
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

  public void showReports(HttpServletRequest request, HttpServletResponse response, JspWriter out)
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
//        out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"user_"+personID+"\" method=\"post\">");
//        out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\"");
//        out.print(" value=\""+ReportInterfaceKey.ACTION_UPDATE_USER_REPORTS+"\">");
//        out.println("<input type=\"hidden\" name=\""+UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID+"\" value=\""+personID+"\">");
//        out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.INPUT_HIDDEN_REPORT_ID+"\">");
        Vector userRole = ((UserDTO)dataVector.elementAt(i)).getUserRoles();
        //HashMap additionalDataHashMap = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        //Vector vecRoles = (Vector)additionalDataHashMap.get(UserInterfaceKey.HASHMAP_KEY_ROLES_LIST);
        Vector vecGroupList = (Vector) dataHashMap.get (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION) ;
        Vector vecPersonReportList = (Vector) dataHashMap.get (ReportInterfaceKey.HASHMAP_KEY_COLLECTION_PERSON_REPORT) ;
        for(int k = 0; k<vecPersonReportList.size(); k++)
        {
              PersonReportModel personReportModel = (PersonReportModel)vecPersonReportList.get(k);
              String personReportId = personReportModel.getPersonReportId();
              String personId = personReportModel.getPersonId();
              String reportId = personReportModel.getReportId();
              String groupId = personReportModel.getGroupId();
        }
          int reportListSize = vecGroupList.size();
        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
        out.println("<TR class=TableHeader>");
        out.println("<td nowrap align=center colspan=3><font size=2>Assign "+personName+" to Report Groups</font></a></TD></TR>");
        for(int j = 0; j<vecGroupList.size(); j++)
        {
          GroupDTO groupDTO =  (GroupDTO) vecGroupList.elementAt (j);
          Integer groupId = groupDTO.getGroupId();    
          String groupName=groupDTO.getGroupName();
          String tdStyle =InterfaceKey.STYLE[i%2];

          


        out.println("<TR class=TableTextNote>");
        out.println("<td class="+InterfaceKey.STYLE[j%2]+">");
        
        out.print(groupName);    
//        out.println("<div id=\""+personID+groupId+"\" style=\"display: none\">");
//        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
//        out.println("<TR class=TableHeader><td colspan=3 nowrap align=middle>"+personName+" Reports</td></tr>");
//        out.println("<TR class=TableHeader>");
//        out.println("<td width=\"30%\" nowrap align=middle>Report Name</td>");
//        out.println("<td width=\"50%\" nowrap align=middle>Report Description</td>");
//        out.println("<td width=\"20%\" nowrap align=middle>Assigned</td></tr>");
// 
//        Vector vecReportModels = groupDTO.getGroupReport();
//
//        for (int x=0;x<vecReportModels.size();x++)
//        {
//          ReportModel rm = (ReportModel)vecReportModels.elementAt(x);
//          int reportID = Integer.parseInt(rm.getReportId());
//          String reportName = rm.getReportName();
//          String reportDesc = rm.getReportDescription();
//          out.println("<TR class=\""+InterfaceKey.STYLE[j%2]+"\">");
//          /*out.println("<TD class=TableTextNote><a href=\"javascript:"+
//          "openWindowfull('"+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"+
//          InterfaceKey.HASHMAP_KEY_ACTION+"="+UserInterfaceKey.ACTION_SHOW_USER_ROLE_PRIVILEGES+"&"+
//          UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID+"="+personID+"&"+
//          ReportInterfaceKey.INPUT_HIDDEN_REPORT_ID+"="+reportID+"&');\">"+reportName+"</a></TD>");
//          */
//          out.println("<td>"+reportName+"</td>");
//          if(reportDesc == null)
//          {
//            reportDesc = "";
//          }
//          out.println("<TD>"+reportDesc+"</TD>");
//          out.println("<TD align=middle>");
//          out.println("<input type=\"checkbox\" name="+UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX+groupId+reportID+
//                      " id="+UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX+groupId+reportID+" onclick=\"document.getElementById('update"+personID+groupId+"').disabled = false;\"");
//
//        for(int k = 0; k<vecPersonReportList.size(); k++)
//        {
//              PersonReportModel personReportModel = (PersonReportModel)vecPersonReportList.get(k);
//              String XpersonReportId = personReportModel.getPersonReportId();
//              String XpersonId = personReportModel.getPersonId();
//              int intPersonId = Integer.parseInt(XpersonId);
//              String XreportId = personReportModel.getReportId();
//              String XgroupId = personReportModel.getGroupId();
//              int intGroupId = Integer.parseInt(XgroupId);
//              int intReportId = Integer.parseInt(XreportId);
//              if(intPersonId == personID)
//              {
//                  if(intReportId == reportID && intGroupId == groupId.intValue())
//                  {
//                      out.print(" checked");
//                  }
//              }
//        }
//          out.print(" ></TD></TR>");
//
//          
//          
//          
//         }
//         out.println("<tr>");
//         out.println("<td colspan=3 align=center>");
//         out.println("<input class=button type=\"button\" name=\"update"+personID+groupId+"\" id=\"update"+personID+groupId+"\" onClick=newSaveReports("+groupId+","+personID+") value=\"Update "+personName+" Reports\" disabled>");
//         out.println("</td>");
//         out.println("</tr>");
//         out.println("</TABLE>");
//         out.println("</div></td>");
         out.println("</td><td width=\"5%\" align=center class="+InterfaceKey.STYLE[j%2]+">");


         out.println("<input type=\"checkbox\" name="+UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX+personID+groupId+ 
         " onclick=\"document.getElementById('update"+personID+"').disabled = false;\"");
          for(int k = 0; k<vecPersonReportList.size(); k++)
          {

          PersonReportModel personReportModel = (PersonReportModel)vecPersonReportList.get(k);

         int nGroupId =  Integer.parseInt(personReportModel.getGroupId());
         int nPersonId =  Integer.parseInt(personReportModel.getPersonId());
         if (groupId.intValue()==nGroupId &&nPersonId ==  personID)
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
        out.println("<input class=button type=\"button\" onClick=newSaveReports("+personID+") name=\"update"+personID+"\" value=\"Update "+personName+" Reports\" disabled>");
        out.println("</td>");
        out.println("</tr>");
//        out.println("</table></form></div></a>");
        out.println("</table></div></a>");

      }
    }
    out.println("<script>var obj = document.getElementById('listofusers');obj.style.display='';</script>");    
  }
%>