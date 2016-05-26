<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.web.interfaces.cr.*"
         import="com.mobinil.sds.web.interfaces.sop.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
%>
<%
/**
 * User_DCM.jsp:
 * Managae Users DCMs access in the contract registeration moduel.
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
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template2.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/utilities.js" type=text/javascript></SCRIPT>
  </head>
  <body>
      <Center>
        <H2> Payment and Commission Management</H2>
      </Center>
      <left>
       
      
      </left>
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
    <Center>
    <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="UserManagment" method="post">
  
      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>" value="<%out.print(userID);%>">
     
     
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td width="20%" nowrap align=center>User Full Name</td>
          <td width="50%" nowrap align=center>E-Mail</td>
          <td width="20%" nowrap align=center></td>
            
        </tr>	
        <%
        showUsers(request, response, out);
        %>
      </TABLE>
     
      <br>
      <center>
      <% 
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\"");
      out.print(" value=\""+SOPInterfaceKey.ACTION_USER_DELETE_CONTROL+"\">");
      out.println("<input class=button type=\"submit\" name=\"save\" value=\"save\">");	
       
      %>
                
                
                
                
                </center>
                
    </form>
  
   
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
      Vector userPerVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
      
      for(int i = 0; i<dataVector.size(); i++){
        int personID = ((UserDTO)dataVector.elementAt(i)).getUserModel().getPersonModel().getPersonID();
        String personName = ((UserDTO)dataVector.elementAt(i)).getUserModel().getPersonModel().getPersonFullName();
        String personEMail = ((UserDTO)dataVector.elementAt(i)).getUserModel().getPersonModel().getPersonEMail();
        int userStatusID = ((UserDTO)dataVector.elementAt(i)).getUserModel().getUserStatusID();
        out.println("<TR class=TableTextNote>");
        out.println("<TD class=TableTextNote>"+personName+"</TD>");
        out.println("<TD class=TableTextNote>"+personEMail+"</TD>");
        out.println("<TD class=TableTextNote align=middle>");
       /* HashMap additionalDataHashMap = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        Vector vecStatus = (Vector)additionalDataHashMap.get(UserInterfaceKey.HASHMAP_KEY_USER_STATUSES_LIST);
        for(int j = 0; j<vecStatus.size(); j++)
        {
          int statusID = ((UserStatusModel)vecStatus.elementAt(j)).getUserStatusID();
          String statusName = ((UserStatusModel)vecStatus.elementAt(j)).getUserStatusName();
          if(userStatusID == statusID)
          {
          out.print(statusName);
          }
        }*/
        if(userPerVector.contains(personID))
          out.println("<input type=checkbox name=check:"+personID+" id=check:"+personID+" value="+personID+" checked>");
        else
          out.println("<input type=checkbox name=check:"+personID+" id=check:"+personID+" value="+personID+" >");	
        
        out.println("</TD>");
        out.println("</TR>");
      }
    }
  }

 

%>