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
  <body onload="timedRefresh(10000);">
  <script language="javascript">
        $(document).ready(function(){$("#taskListTable").tablesorter(); });

  </script>
    <CENTER>
      <H2>Tasks List</H2>
    </CENTER>
  

    <form  action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formTaskList" id="formTaskList" method="post">&nbsp;

    <%
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

      HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);      
      String taskType=(String)dataHashMap.get(TaskInterfaceKey.CONTROL_TASK_TYPE);
      System.out.println("in jsp task type is : "+taskType);
    %>

<%
showTaskList((HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT) , out, request,taskType);
%>      
 
    </form>
   </body>
</html>

<%!
public void showTaskList(HashMap objDataHashMap , JspWriter out, HttpServletRequest request,String taskType)
{
    String appName = request.getContextPath();

try{
  if (objDataHashMap == null)
  {
  
   return;
   }
  
  Vector vecTaskList = (Vector) objDataHashMap.get(TaskInterfaceKey.VEC_TASKS) ;

  System.out.println("vecTaskList ISSS : "+vecTaskList);
  if (vecTaskList ==null)
  {
    return;
  }
  int taskListSize = vecTaskList.size();
  System.out.println("tasks SIZE : "+taskListSize+"  task type : "+taskType);
  out.println("<TABLE class=\"tablesorter\" id=\"taskListTable\">");
  out.println("<thead>");
  out.println("<TR>");
   
out.println("<th ><font size=2>Task Name</font></a></th><th><font size=2>Status</font></a></th>");

//if(taskType.equals("5") || taskType.equals("8") || taskType.equals("7") || taskType.equals("1")){
out.println("<th ><font size=2>StartTime</font></a></th><th><font size=2>EndTime</font></a></th>");
out.println("<th ><font size=2>TotalTime</font></a></th>");
out.println("<th ><font size=2>UpdatedRows</font></a></th>");
//}

out.println("</TR></thead><tbody>");

    String startTime="Not Finish yet";
    String endTime="Not Finish yet";
    String totalTime="Not Finish yet";
    Integer updatedRows=0;

  for (int i=0; i<taskListSize; i++)
  {
  
    TaskDTO taskDTO =  (TaskDTO) vecTaskList.elementAt (i);    
    int taskId = taskDTO.getTaskId();
    String taskName = taskDTO.getTaskName();
    String taskStatus = taskDTO.getTaskStatusTypeName();
  //  System.out.println("name status type Id in JSP : "+taskName+"   "+taskStatus+"    "+taskType+"    "+taskDTO.getTaskCurrentStatusId());
    out.println("<TR class=\""+InterfaceKey.STYLE[i%2]+"\">");
    out.println("<td width=\"50%\">"+taskName+"</td>");   
    out.println("<td width=\"50%\" align=center>"+taskStatus+"</td>");            
    //out.println("<td width=\"50%\" align=center>"+"in progress"+"</td>");            
    
    if(taskType.equals("5") || taskType.equals("7")){
        System.out.println("IF task type : "+taskType);
        Vector <NomadTaskDTO> nomad =(Vector<NomadTaskDTO>)objDataHashMap.get(TaskInterfaceKey.CONTROL_NOMAD_TASK_TIME_DATA);
         System.out.println("NOMAD OBJECT : "+nomad);
      // NomadTaskDTO nomad =(NomadTaskDTO)objDataHashMap.get(TaskInterfaceKey.CONTROL_NOMAD_TASK_TIME_DATA);
        startTime="Not Finish yet";
        endTime="Not Finish yet";
        totalTime="Not Finish yet";
        updatedRows=0;
       //String updatedRows="Not Finish yet";
     //  System.out.println("STATUS jsp taskDTO.getTaskCurrentStatusId "+taskDTO.getTaskCurrentStatusId());
           if(taskDTO.getTaskCurrentStatusId()==3){
               startTime=nomad.get(i).getStartTime().toString();
               endTime=nomad.get(i).getEndTime().toString();
               totalTime=nomad.get(i).getTotalTime()+" Sec";
               updatedRows=new Integer(nomad.get(i).getUpdatedRows());
           }
       out.println("<td width=\"50%\">"+startTime+"</td>");
       out.println("<td width=\"50%\" align=center>"+endTime+"</td>");
       out.println("<td width=\"50%\" align=center>"+totalTime+"</td>");
       out.println("<td width=\"50%\" align=center>"+updatedRows.toString()+"</td>");
    }
    else if(taskType.equals("6")){
        Vector <NTRATaskDTO> ntraTask =(Vector <NTRATaskDTO>)objDataHashMap.get(TaskInterfaceKey.CONTROL_NTRA_TASK_TIME_DATA);
         startTime="Not Finish yet";
         endTime="Not Finish yet";
         totalTime="Not Finish yet";
         updatedRows=0;
     
        if(taskDTO.getTaskCurrentStatusId()==3){
          for(NTRATaskDTO ntraTaskDTO :ntraTask) 
          {
          if (taskId==ntraTaskDTO.getTaskId())
                       {
                startTime=ntraTaskDTO.getStartTime().toString();
                endTime=ntraTaskDTO.getEndTime().toString();
                totalTime=ntraTaskDTO.getTotalTime()+" Sec";
                updatedRows=Integer.parseInt(ntraTaskDTO.getUpdatedRows());
            }
          }  
        }
    
    }
    else{
        System.out.println("ELSE task type : "+taskType);
        out.println("<td width=\"50%\">"+startTime+"</td>");
        out.println("<td width=\"50%\" align=center>"+endTime+"</td>");
        out.println("<td width=\"50%\" align=center>"+totalTime+"</td>");
        out.println("<td width=\"50%\" align=center>"+updatedRows.toString()+"</td>");
    }
    }



    out.println("</tr>");
    

  
      out.println("</tbody>");
    out.println("</table>");

  }
  catch(Exception e)
  {
  e.printStackTrace();
  }
}
%>