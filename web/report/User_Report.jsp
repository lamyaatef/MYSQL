<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.web.interfaces.cr.*"
         import="com.mobinil.sds.web.interfaces.gn.reports.*"         
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
%>
<%
/**
 * User_Report.jsp:
 * Managae Users Reports access in the contract registeration moduel.
 * 
 * showUsers(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Display the system Users.
 *
 * showReportss(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Build hidden divs, each holding Reports of one user.
 * A user Reports div will be visible if the user was selected.
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
        <H2>Contract Registeration Administration</H2>
      </Center>
      <left>
       <h3>User Report Managment</h3>
      </left>
    </Center>
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
    <Center>
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
    showReports(request, response, out);
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
        out.println("<TD class=TableTextNote><a href=\"javascript:toggle('"+personID+"');\">"+personName+"</a></TD>");
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
   * showReports method: 
   * Build hidden divs, each holding Reports of one user.
   * A user Reports div will be visible if the user was selected.
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
      String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
      Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      for(int i = 0; i<dataVector.size(); i++)
      {
        int personID = ((UserDTO)dataVector.elementAt(i)).getUserModel().getPersonModel().getPersonID();
        String personName = ((UserDTO)dataVector.elementAt(i)).getUserModel().getPersonModel().getPersonFullName();
        out.println("<div style=\"DISPLAY: none\" id="+personID+" name="+personName+">");
        out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"user_"+personID+"\" method=\"post\">");
        out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\"");
        out.print(" value=\""+ReportInterfaceKey.ACTION_UPDATE_USER_REPORT+"\">");
        out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value=\""+userID+"\">");
        out.println("<input type=\"hidden\" name=\""+UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID+"\" value=\""+personID+"\">");
        out.println("<input type=\"hidden\" name=\""+UserInterfaceKey.CONTROL_HIDDEN_Report_ID+"\">");
        out.println("<input type=\"hidden\" name=\""+UserInterfaceKey.CONTROL_TEXT_Report_NAME+"\">");
        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
        out.println("<TR class=TableHeader><td colspan=3 nowrap align=middle>"+personName+" Reports</td></tr>");
        out.println("<TR class=TableHeader>");
        out.println("<td width=\"40%\" nowrap align=middle>Report Name</td>");
        
        out.println("<td width=\"20%\" nowrap align=middle>Assigned</td></tr>");

        //Vector userReport = ((UserDTO)dataVector.elementAt(i)).getUserReports();
        
        HashMap additionalDataHashMap = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

        Vector  reportModelVector = (Vector)additionalDataHashMap.get(UserInterfaceKey.HASHMAP_KEY_REPORT_LIST);

        Hashtable userReportListTable = (Hashtable)additionalDataHashMap.get(UserInterfaceKey.HASHMAP_KEY_REPORT_USERLIST);
        
        for(int j = 0; j<reportModelVector.size(); j++)
        {
           com.mobinil.sds.core.system.gn.reports.dto.ReportDTO reportModel = ( com.mobinil.sds.core.system.gn.reports.dto.ReportDTO) reportModelVector.elementAt(j);
          
          int ReportID = reportModel.getReportID();
          String ReportName = reportModel.getReportName();

        
          out.println("<TR>");
          out.println("<TD class=TableTextNote>"+ReportName+"</TD>");        
          out.println("<TD class=TableTextNote align=middle>");
          out.println("<input type=\"checkbox\" name="+UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX+ReportID+
                      " vlaue="+ReportID+" onclick=\"document.user_"+personID+".update.disabled = false;\"");

          if (userReportListTable.get(personID+"")!=null)
          {
            Object reportIdObjInHashtable = ((Hashtable)userReportListTable.get(personID+"")).get(ReportID+"");
            if (reportIdObjInHashtable !=null)
            {
            out.print(" checked");
            }
          }
/*
      
          for(int l = 0; l<userReport.size(); l++)
          {
            int nUserReportID = ((UserReportModel)userReport.elementAt(l)).getReportID();
            if(ReportID == nUserReportID)
            {
              out.print(" checked");
            }
          }
          */
          out.print("></TD></TR>");
        }


        
        out.println("</TABLE>");
        out.println("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\" width=\"100%\">");
        out.println("<tr>");
        out.println("<td align=center>");
        out.println("<input class=button type=\"submit\" name=\"update\" value=\"Update "+personName+" Reports\" disabled>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table></form></div>");
      }
    }
  }

%>