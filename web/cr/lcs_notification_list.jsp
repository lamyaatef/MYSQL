<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.cr.*"
         import="com.mobinil.sds.core.system.cr.notificationList.dto.*"     

%>

<%
    String appName = request.getContextPath();
    String userId=(String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    <SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>
    <link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />
    <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
    <script type="text/javascript" src="../resources/js/jquery_tablesorter_pack.js"></script>
    <link rel="stylesheet" href="../resources/css/themes/blue/style.css" type="text/css"/>


    

  </head>
  <body>
  <script language="javascript">
        $(document).ready(function(){$("#emailListTable").tablesorter(); });

  </script>
  <script>  
     
      function loadEmail(taskId) {
      document.formMngList.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(ContractRegistrationInterfaceKey.ACTION_NEW_EDIT_EMAIL);%>';
      document.formMngList.<%out.print(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_EMAIL_ID);%>.value=taskId;        
      formMngList.submit();
      }

      function deleteEamil() {
      document.formMngList.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(ContractRegistrationInterfaceKey.ACTION_DELETE_EMAIL);%>';
      formMngList.submit();
      }      
      
      function newEmail() {
      document.formMngList.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(ContractRegistrationInterfaceKey.ACTION_NEW_EDIT_EMAIL);%>';
      formMngList.submit();
      }       
    </script>
    <CENTER>
      <H2> Notification List</H2>
    </CENTER>
  

    <form  action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formMngList" id="formMngList" method="post">&nbsp;

    <%
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+"\">");
      out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_EMAIL_ID+"\" value=\"\">");                                

      HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);      
     

    %>

<%
showEmailList((HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT) , out, request);
%>      
<div align=center><input type=button value="Add Eamil" onClick="newEmail();" > &nbsp;&nbsp;
<input type=button value="Delete Selected Emails" onClick="deleteEamil();" > </div>
 
    </form>
   </body>
</html>

<%!
public void showEmailList(HashMap objDataHashMap , JspWriter out, HttpServletRequest request)
{
    String appName = request.getContextPath();

try{
  if (objDataHashMap == null)
  {
  
   return;
   }
  
  Vector vecEmailList = (Vector) objDataHashMap.get(ContractRegistrationInterfaceKey.VEC_EMAILS) ;
  
  if (vecEmailList ==null)
  {
    return;
  }
  int emailListSize = vecEmailList.size();
  out.println("<TABLE class=\"tablesorter\" id=\"emailListTable\">");
    out.println("<thead>");
  out.println("<TR>");
   
out.println("<th ><font size=2>Name</font></a></th><th><font size=2>Edit</font></a></th><th><font size=2>Delete</font></a></th></TR></thead><tbody>");

  for (int i=0; i<emailListSize; i++)
  {
  
	  notificatioListDTO nldto =  (notificatioListDTO) vecEmailList.elementAt (i);    
    int id = nldto.getId().intValue();
    String email=nldto.getEmail();    
    
   
    out.println("<TR class=\""+InterfaceKey.STYLE[i%2]+"\">");
    out.println("<td width=\"37.5%\">"+email+"</td>");
    out.println("<td width=\"10%\" align=center><input type=button value=\"Edit\" onclick=\"loadEmail("+id+");\"></td>");    
    out.println("<td width=\"5%\" align=center><input type=checkbox name=taskCheck"+id+"></td>");

    out.println("</tr>");
    

  }
      out.println("</tbody>");
    out.println("</table>");

  }
  catch(Exception e)
  {
  e.printStackTrace();
  }
}
%>