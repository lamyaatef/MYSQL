<%@ page contentType="text/html;charset=windows-1252"%>
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
        <SCRIPT language=JavaScript src="../resources/js/utilities.js" type="text/javascript"></SCRIPT>
        <link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />
        <SCRIPT language=JavaScript src="../resources/js/calendar.js" type="text/javascript"></SCRIPT>
        <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
        <script type="text/javascript" src="../resources/js/jquery_tablesorter_pack.js"></script>
        <link rel="stylesheet" href="../resources/css/themes/blue/style.css" type="text/css"/>
        <script type="text/javascript">

            function timedRefresh(timeoutPeriod) {
                setTimeout("location.reload(true);",timeoutPeriod);
            }
     
     
     
        </script>



    </head>
    <body onload="timedRefresh(30000);">
        <script language="javascript">
            $(document).ready(function(){$("#taskListTable").tablesorter(); });

        </script>
    <CENTER>
        <H2> Task List</H2>
    </CENTER>


    <form  action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formTaskList" id="formTaskList" method="post">&nbsp;

        <%
            out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_ACTION + "\""
                    + " value=\"" + "\">");

            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String taskType = (String) dataHashMap.get(TaskInterfaceKey.CONTROL_TASK_TYPE);
        %>

        <%
            showTaskList((HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT), out, request, taskType);
        %>      

    </form>
</body>
</html>

<%!
    public void showTaskList(HashMap objDataHashMap, JspWriter out, HttpServletRequest request, String taskType) {
        String appName = request.getContextPath();

        try {
            if (objDataHashMap == null) {

                return;
            }

            Vector vecTaskList = (Vector) objDataHashMap.get(TaskInterfaceKey.VEC_TASKS);

            if (vecTaskList == null) {
                return;
            }
            int taskListSize = vecTaskList.size();
            out.println("<TABLE class=\"tablesorter\" id=\"taskListTable\">");
            out.println("<thead>");
            out.println("<TR>");

            out.println("<th ><font size=2>Task Name</font></a></th>");
            out.println("<th ><font size=2>StartTime</font></a></th><th><font size=2>EndTime</font></a></th>");
            out.println("<th ><font size=2>UpdatedRows</font></a></th>");


            out.println("</TR></thead><tbody>");

            for (int i = 0; i < taskListSize; i++) {

                AuthAgentArpuUpdateTaskDTO taskDTO = (AuthAgentArpuUpdateTaskDTO) vecTaskList.elementAt(i);
                String taskName = taskDTO.getTaskName();

                out.println("<TR class=\"" + InterfaceKey.STYLE[i % 2] + "\">");
                out.println("<td width=\"50%\">" + taskName + "</td>");
                String startTime = "Not Finish yet";
                String endTime = "Not Finish yet";
                String updatedRows = "0";
                startTime = taskDTO.getStartTime().toString();

                if (taskDTO.getEndTime() != null) {
                    endTime = taskDTO.getEndTime().toString();
                }
                updatedRows = taskDTO.getUpdatedRows();

                out.println("<td width=\"50%\">" + startTime + "</td>");
                out.println("<td width=\"50%\" >" + endTime + "</td>");
                out.println("<td width=\"50%\" align=center>" + updatedRows + "</td>");



                out.println("</tr>");


            }
            out.println("</tbody>");
            out.println("</table>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>