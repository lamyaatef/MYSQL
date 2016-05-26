<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.te.*"
         import="com.mobinil.sds.core.system.te.dto.*"     

%>

<%
    String appName = request.getContextPath();
String path = request.getRealPath("/te/upload/");
path = path+"\\";
    String userId=(String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
    String action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet";
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

<script type="text/javascript">

      function timedRefresh(timeoutPeriod) {
	setTimeout("location.reload(true);",timeoutPeriod);
}
     
     
     
    </script>
    

  </head>
  <body>
  <script language="javascript">
        $(document).ready(function(){$("#taskListTable").tablesorter(); });

  </script>
  <script>  
     
      function exportData(taskId) {
      document.formCmpltTasks.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(TaskInterfaceKey.ACTION_EXPORT_COMPLETED_TASKS);%>';
      document.formCmpltTasks.<%out.print(TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID);%>.value=taskId;        
      formCmpltTasks.submit();
      }

      function deleteTask() {
      document.formCmpltTasks.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(TaskInterfaceKey.ACTION_DELETE_COMPLETED_TASKS);%>';
      formCmpltTasks.submit();
      }      
    </script>
    
    <CENTER>
      <H2> Completed Tasks List</H2>
    </CENTER>
  

    <form  action="<% out.print(action); %>" name="formCmpltTasks" id="formCmpltTasks" method="post">&nbsp;

    <%
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+userId+"\">");
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION+"\""+
                  " value=\""+path+"\">");            
      out.println("<input type=\"hidden\" name=\""+TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID+"\" value=\"\">");                                

      HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);            

    %>

<%
showTaskList((HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT) , out, request);
%>      
<div align=center><input type=button value="Delete Selected Tasks" onClick="deleteTask();" > </div>
 
    </form>
   </body>
</html>

<%!
public void showTaskList(HashMap objDataHashMap , JspWriter out, HttpServletRequest request)
{
    String appName = request.getContextPath();

try{
  if (objDataHashMap == null)
  {
  
   return;
   }
  
  Vector vecTaskList = (Vector) objDataHashMap.get(TaskInterfaceKey.VEC_TASKS) ;
  
  if (vecTaskList ==null)
  {
    return;
  }
  int taskListSize = vecTaskList.size();
  out.println("<TABLE class=\"tablesorter\" id=\"taskListTable\">");
    out.println("<thead>");
  out.println("<TR>");
   
out.println("<th ><font size=2>Task Name</font></a></th><th><font size=2>Export</font></a></th><th><font size=2>Delete</font></a></th></TR></thead><tbody>");

  for (int i=0; i<taskListSize; i++)
  {
  
    TaskDTO taskDTO =  (TaskDTO) vecTaskList.elementAt (i);    
    int taskId = taskDTO.getTaskId();
    String taskName=taskDTO.getTaskName();
    String taskDesc = taskDTO.getTaskDescription();
    if (taskDesc==null)taskDesc="";
   
    out.println("<TR class=\""+InterfaceKey.STYLE[i%2]+"\">");
    out.println("<td width=\"75%\">"+taskName+"</td>");       

    out.println("<td width=\"20%\" align=center><input type=button value=\"Export Updated Data\" onclick=\"exportData("+taskId+");\"></td>");
    out.println("<td width=\"5%\" align=center><input type=checkbox name=taskCheck"+taskId+"></td>");

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
