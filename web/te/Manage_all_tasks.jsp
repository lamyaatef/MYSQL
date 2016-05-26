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
        $(document).ready(function(){$("#taskListTable").tablesorter(); });

  </script>
  <script>  
     
      function loadTask(taskId) {
      document.formMngTasks.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(TaskInterfaceKey.ACTION_NEW_EDIT_TASK);%>';
      document.formMngTasks.<%out.print(TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID);%>.value=taskId;        
      formMngTasks.submit();
      }

      function uploadTask(taskId) {
      document.formMngTasks.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(TaskInterfaceKey.ACTION_UPLOAD_FILE);%>';
      document.formMngTasks.<%out.print(TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID);%>.value=taskId;        
      formMngTasks.submit();
      }

      function deleteTask() {
      document.formMngTasks.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(TaskInterfaceKey.ACTION_DELETE_TASK);%>';
      formMngTasks.submit();
      }      
      
      function newTask() {
      document.formMngTasks.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(TaskInterfaceKey.ACTION_NEW_EDIT_TASK);%>';
      formMngTasks.submit();
      }       
    </script>
    <CENTER>
      <H2> Task List</H2>
    </CENTER>
  

    <form  action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formMngTasks" id="formMngTasks" method="post">&nbsp;

    <%
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+"\">");
      out.println("<input type=\"hidden\" name=\""+TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID+"\" value=\"\">");                                

      HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);      
      String strError = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);

        if(strError != null)
        {
        out.println("<script language=\"javascript\">");
        out.println("alert('"+strError+"');");
        out.println("</script>");
        }

    %>

<%
showTaskList((HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT) , out, request);
%>      
<div align=center><input type=button value="Add Task" onClick="newTask();" > &nbsp;&nbsp;
<input type=button value="Delete Selected Tasks" onClick="deleteTask();" > </div>
 
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
   
out.println("<th ><font size=2>Task Name</font></a></th><th><font size=2>Task Descreiption</font></a></th><th><font size=2>Edit</font></a></th><th><font size=2>Upload</font></a></th><th><font size=2>Delete</font></a></th></TR></thead><tbody>");

  for (int i=0; i<taskListSize; i++)
  {
  
    TaskDTO taskDTO =  (TaskDTO) vecTaskList.elementAt (i);    
    int taskId = taskDTO.getTaskId();
    String taskName=taskDTO.getTaskName();
    String taskDesc = taskDTO.getTaskDescription();
    if (taskDesc==null)taskDesc="";
   
    out.println("<TR class=\""+InterfaceKey.STYLE[i%2]+"\">");
    out.println("<td width=\"37.5%\">"+taskName+"</td>");   
    out.println("<td width=\"37.5%\" align=center>"+taskDesc+"</td>");
    
    out.println("<td width=\"10%\" align=center><input type=button value=\"Edit\" onclick=\"loadTask("+taskId+");\"></td>");
    out.println("<td width=\"10%\" align=center><input type=button value=\"Upload\" onclick=\"uploadTask("+taskId+");\"></td>");
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