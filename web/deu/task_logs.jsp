<%
/**
 *Initially, this page shows all the task logs currently stored in 
 *the database. It has a search form which reloads the page on submission 
 *with the task logs matching the searched criteria. 
 *(an empty search, means show all logs)
 * 
 * @author Michael Gameel
 */ 
 %>
 
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.deu.*"
         import="com.mobinil.sds.core.system.deu.tasklog.dao.*"
         import="com.mobinil.sds.core.system.deu.tasklog.model.*"
         import="com.mobinil.sds.core.system.deu.task.dao.*"
         import="com.mobinil.sds.core.system.deu.task.model.*"
         import="com.mobinil.sds.core.system.deu.file.dao.*"
         import="com.mobinil.sds.core.system.deu.file.model.*"
         import="com.mobinil.sds.core.system.deu.source.dao.*"
         import="com.mobinil.sds.core.system.deu.source.model.*"
         import="com.mobinil.sds.core.system.deu.connection.dao.*"
         import="com.mobinil.sds.core.system.deu.connection.model.*"         
         import="com.mobinil.sds.core.system.deu.tasklogstatus.model.*"
         import="com.mobinil.sds.web.interfaces.*"
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset//EN">
<HTML>
<HEAD>
<TITLE>:: DEU ADMIN ::</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1256">
<LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
<SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>
</HEAD>
<%
      HashMap dataHashMap = new HashMap(100);
      dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      Vector taskVector=null, fileVector=null, statusVector=null;
      HashMap additional=null;
      if(dataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION))
      {
      additional = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
      fileVector = (Vector)additional.get(DEUKeyInterface.VECTOR_FILES);
      taskVector = (Vector)additional.get(DEUKeyInterface.VECTOR_TASKS);
      statusVector = (Vector)additional.get(DEUKeyInterface.VECTOR_SECTOR_STATUS);
      }
  %>
<BODY leftMargin=0 topMargin=0>
    <Center>
      <H2>Task Logs</H2>
    </Center>
<DIV>                                                         
   <FORM id=frmSearchTaskLogs name=frmSearchTaskLogs action='<%out.print(request.getContextPath());%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet' method=post>
   <script language="JavaScript" src="../resources/js/GCappearance.js" type="text/javascript"></script>
   <script language="JavaScript" src="../resources/js/GurtCalendar.js" type="text/javascript"></script>
  <link REL="STYLESHEET" TYPE="text/css" HREF="../resources/css/calendar.css">
  <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" VALUE="<%out.print(DEUKeyInterface.ACTION_SEARCH_TASK_LOGS);%>">
  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#ffffff
        border=0 style="border-collapse: collapse" bordercolor="#111111"><TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#ffffff border=0 style="border-collapse: collapse" bordercolor="#111111">
              <TBODY>
              <TR class=TableHeader>
                <TD align=left >Search Task Logs</TD>
                <TD align=left>&nbsp;</TD></TR>
              <TR>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left>
                Task Log ID:</TD>
                <TD class=TableTextColumnodd vAlign=top noWrap >
                <INPUT
                  class=input2 ONKEYPRESS="if ((event.keyCode < 48) ||(event.keyCode > 57)) event.returnValue = false;" maxLength=9
                  size=15 name="<%=DEUKeyInterface.CONTROL_TEXT_TASK_LOG_ID%>"></TD></TR>
              <TR>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left>
                Start Time:</TD>
                <TD class=TableTextColumnodd vAlign=top noWrap >
                <SELECT name="<%=DEUKeyInterface.CONTROL_SELECT_INITIAL_RELATION%>">
               <!--   <OPTION value="<" > Before</OPTION>  -->
                <OPTION value="="> Equals</OPTION>
                <option value=">" selected>After</option>
                </SELECT>
                
                
                <script>
                
                  </script></TD></TR>
              <TR>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                >End Time:</TD>
                <TD class=TableTextColumnodd vAlign=top noWrap>
                <SELECT name="<%=DEUKeyInterface.CONTROL_SELECT_FINAL_RELATION%>"> 
              <!--   <OPTION value="<" selected>Before</OPTION> --> 
                <OPTION value="=">Equals</OPTION>
                <option value=">">After</option>
                </SELECT><script>
                
                  </script></TD></TR>
              <tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                >Task Name:</TD>
                <TD class=TableTextColumnodd vAlign=top noWrap >
                <SELECT
                  name="<%=DEUKeyInterface.CONTROL_SELECT_TASK%>"> 
                <option selected value="">--------------------</option>
                <%
                  if(taskVector!=null)
                  {
                    for ( int i=0 ; i <taskVector.size();i++)
                    {
                      TaskModel task =(TaskModel)(taskVector.get(i));
                      out.print("<OPTION>");
                      out.print(task.getName());
                      out.print("</option>\n");
                    }
                  }
                %>
                </SELECT></TD>
              </tr>
              <tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
               >Output File Name:</TD>
                <TD class=TableTextColumnodd vAlign=top noWrap>
                <SELECT name="<%=DEUKeyInterface.CONTROL_SELECT_FILE%>"> 
                <option value="" selected>--------------------</option>
                 <%
                  if(fileVector!=null)
                  {
                    for ( int i=0 ; i <fileVector.size();i++)
                    {
                      FileModel file =(FileModel)(fileVector.get(i));
                      out.print("<OPTION>");
                      out.print(file.getName());
                      out.print("</option>\n");
                    }
                  }
                %>
                </SELECT></TD>
              </tr>
              <tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                >Sector Count:</TD>
                <TD class=TableTextColumnodd vAlign=top noWrap>
                <SELECT
                  name="<%=DEUKeyInterface.CONTROL_SELECT_SECTOR_COUNT%>">
                 <!--   <OPTION value="<">Less Than</OPTION> -->
                    <OPTION value="=">Equals</OPTION>
                <option value=">" selected>Greater Than</option>
                </SELECT><INPUT
                  class=input2 ONKEYPRESS="if ((event.keyCode < 48) ||
            (event.keyCode > 57)) event.returnValue = false;" maxLength=9
                  size=15 name="<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_COUNT%>"></TD>
              </tr>
              <tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
               >Row Count:</TD>
                <TD class=TableTextColumnodd vAlign=top noWrap>
                <SELECT
                  name="<%=DEUKeyInterface.CONTROL_SELECT_ROW_COUNT%>"> 
                  <!--  <OPTION value="<">Less Than</OPTION> -->
                    <OPTION value="=">Equals</OPTION>
                <option value=">" selected>Greater Than</option>
                </SELECT><INPUT
                  class=input2 ONKEYPRESS="if ((event.keyCode < 48) ||
            (event.keyCode > 57)) event.returnValue = false;" maxLength=9
                  size=15 name="<%=DEUKeyInterface.CONTROL_TEXT_ROW_COUNT%>"></TD>
              </tr>
              <tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                >Status:</TD>
                <TD class=TableTextColumnodd vAlign=top noWrap >
                <SELECT
                  name="<%=DEUKeyInterface.CONTROL_SELECT_STATUS%>"> 
                <option selected value="">--------------------</option>
              <%
              if(statusVector!=null)
              {
                for ( int i=0 ; i <statusVector.size();i++)
                {
                  TaskLogStatusModel status =(TaskLogStatusModel)(statusVector.get(i));
                  out.print("<OPTION value='"+status.getStatusID()+"'>");
                  out.print(status.getStatusText());
                  out.print("</option>\n");
                }
              }
            %>
                </SELECT></TD>
              </tr>
              </TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=1 width="100%" bgColor=#ffffff
            border=0>
              <TBODY>
              <TR class=TableHeader>
                <TD class=TableTextColumnodd vAlign=top noWrap align=middle colSpan=2>
                
                <INPUT class=button id=submit1 type=submit value="  Search Task Logs  " name=searchLogs> 
                </TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
                </FORM>

</DIV>
<DIV>
<FORM id=frmTaskLogsResults name=frmTaskLogsResults action='<%out.print(request.getContextPath());%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet' method=post>
<input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" >      
<input type="hidden" name="<%out.print(DEUKeyInterface.HIDDEN_TASK_LOG_ID);%>" > 
<br>
<br>
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=0 cellPadding=1 width="100%" bgColor=#ffffff border=1>
              <TBODY>
              <TR>
                <TD vAlign=top>
                  <TABLE class=main style="BORDER-COLLAPSE: collapse" cellSpacing=0 cellPadding=2 width="100%" align=center border=1>
                    <TBODY>
                    <TR>
                      <TD class=TableHeader vAlign=top align=middle width="147">Task Log ID</TD>
                      <TD class=TableHeader vAlign=top align=middle width="82">Start Time</TD>
                      <TD class=TableHeader vAlign=top align=middle width="68">Output File Name</TD>
                      <TD class=TableHeader vAlign=top align=middle width="141">Task Name</TD>
                      <TD class=TableHeader vAlign=top align=middle width="75">End Time</TD>
                      <TD class=TableHeader vAlign=top align=middle width="75">Sector Count</TD>
                      <TD class=TableHeader vAlign=top align=middle width="70">Row Count</TD>
                      <TD class=TableHeader vAlign=top align=middle width="86">Status</TD>
                      <TD class=TableHeader vAlign=top align=middle width="86">Error Message</TD>
                      <TD class=TableHeader vAlign=top align=middle width="86">View Sector Logs</TD>
                      </TR>
                    <%
    showLogs(request, response, out);
    %>                         </TBODY></TABLE>
              <TR>
                <TD vAlign=top>
<CENTER>
<p><A onclick="window.print();" href="#"><IMG src="<%out.print(request.getContextPath());%>/resources/img/printicon.gif" border=0 width="64" height="31"></A>
</p>
</CENTER>
                    </TBODY></TABLE>
                    </FORM>
                    </DIV>
                    </BODY>
</HTML>
<%!
/**
 * generates the HTML code for showing the task logs encapsulated in the dataHashMap attribute of the request
 * 
 * @param	request	HttpServletRequest for this page
 * @param	response	HttpServletResponse object for this page
 * @param	out	JspWriter Object for this page
 */ 
public void showLogs(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(! dataHashMap.isEmpty()){
      Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      for(int i = 0; i<dataVector.size(); i++){
        String error= ((TaskLogModel)dataVector.elementAt(i)).getError();
        if(error==null)error="";
        String fileName = ((TaskLogModel)dataVector.elementAt(i)).getFileName();
        String rowCount = ((TaskLogModel)dataVector.elementAt(i)).getRowCount();
        if(rowCount==null)rowCount="";
        String sectorCount = ((TaskLogModel)dataVector.elementAt(i)).getSectorCount();
        if(sectorCount==null)sectorCount="";
        String status = ((TaskLogModel)dataVector.elementAt(i)).getStatus();
        String statusID = ((TaskLogModel)dataVector.elementAt(i)).getStatusID();
        String taskName = ((TaskLogModel)dataVector.elementAt(i)).getTaskName();
        String logID = ((TaskLogModel)dataVector.elementAt(i)).getLogID(); 
        String timeInitial = ((TaskLogModel)dataVector.elementAt(i)).getTimeInitial();
        //timeInitial=timeInitial.replace('/','-');
        String timeFinal= ((TaskLogModel)dataVector.elementAt(i)).getTimeFinal();
        if(timeFinal==null)timeFinal="";
        //timeFinal = timeFinal.replace('/','-');
        out.print("<TR><TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+logID+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+timeInitial+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+fileName+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+taskName+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+timeFinal+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+sectorCount+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+rowCount+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+statusID+":"+status+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+error+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+
        "<TABLE cellSpacing=0 cellPadding=1 width='100%' bgColor=#ffffff border=0>"+
        "<TBODY><TR><TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top noWrap align=left>"+
        "<INPUT class=button type=button id=ViewSectorLogs"+i+" value='  View Sector Logs  ' name='ViewSectorLogs"+i+
        "' onclick=\"document.frmTaskLogsResults."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+DEUKeyInterface.ACTION_SHOW_SECTOR_LOGS_BY_TASK+"'; "
        +"document.frmTaskLogsResults."+DEUKeyInterface.HIDDEN_TASK_LOG_ID+".value='"+logID+"'; "
        +"frmTaskLogsResults.submit();\" ></TD></TR></TBODY></TABLE></TD>");
        out.print("</TR>\n");
      }
    }
  }
%>