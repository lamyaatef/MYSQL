<%
/**
 *Initially, this page shows all the sector logs currently stored in the database. 
 * It has a search form which reloads the page on submission with the sector 
 * logs matching the searched criteria. (an empty search, means show all logs)
 *
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
         import="com.mobinil.sds.core.system.deu.sectorlog.dao.*"
         import="com.mobinil.sds.core.system.deu.sectorlog.model.*"
         import="com.mobinil.sds.core.system.deu.task.dao.*"
         import="com.mobinil.sds.core.system.deu.task.model.*"
         import="com.mobinil.sds.core.system.deu.file.dao.*"
         import="com.mobinil.sds.core.system.deu.file.model.*"
         import="com.mobinil.sds.core.system.deu.source.dao.*"
         import="com.mobinil.sds.core.system.deu.source.model.*"
         import="com.mobinil.sds.core.system.deu.connection.dao.*"
         import="com.mobinil.sds.core.system.deu.connection.model.*"
         import="com.mobinil.sds.core.system.deu.sectorlogstatus.model.*"
         import="com.mobinil.sds.web.interfaces.*"
%>
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
  Vector taskVector=null, fileVector=null, sourceVector=null, connectionVector=null, statusVector=null;
  HashMap additional=null;
  if(dataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION))
  {
  additional = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  connectionVector = (Vector)additional.get(DEUKeyInterface.VECTOR_CONNECTIONS);
  sourceVector = (Vector)additional.get(DEUKeyInterface.VECTOR_SOURCES);
  fileVector = (Vector)additional.get(DEUKeyInterface.VECTOR_FILES);
  taskVector = (Vector)additional.get(DEUKeyInterface.VECTOR_TASKS);
  statusVector = (Vector)additional.get(DEUKeyInterface.VECTOR_SECTOR_STATUS);
  }
%>
<BODY leftMargin=0 topMargin=0>
    <Center>
      <H2>Sector Logs</H2>
    </Center>
<DIV>
   <FORM id=frmSearchTaskLogs name=frmSearchTaskLogs action='<%out.print(request.getContextPath());%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet' method=post>
   <script language="JavaScript" src="../resources/js/GCappearance.js" type="text/javascript"></script>
   <script language="JavaScript" src="../resources/js/GurtCalendar.js" type="text/javascript"></script>
  <link REL="STYLESHEET" TYPE="text/css" HREF="../resources/css/calendar.css">

   <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" VALUE="<%out.print(DEUKeyInterface.ACTION_SEARCH_SECTOR_LOGS);%>">
   <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#ffffff
        border=0 style="border-collapse: collapse" bordercolor="#111111"><TBODY>
        <TR>
          <TD>
              <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#ffffff
        border=0 style="border-collapse: collapse" bordercolor="#111111"><TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#ffffff 
            border=0 style="border-collapse: collapse" bordercolor="#111111">
              <TBODY>
              <TR class=TableHeader>
                <TD align=left colspan=2>Search Sector Logs</TD>
              </TR>
              <TR>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left>
                Sector Log ID:</TD>
                <TD class=TableTextColumnodd vAlign=top noWrap >
                <INPUT
                  class=input2 ONKEYPRESS="if ((event.keyCode < 48) ||(event.keyCode > 57)) event.returnValue = false;" maxLength=9
                  size=15 name="<%=DEUKeyInterface.CONTROL_TEXT_SECTOR_LOG_ID%>"></TD></TR>

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
                <SELECT
                  name="<%=DEUKeyInterface.CONTROL_SELECT_INITIAL_RELATION%>">
               <!--  <OPTION value="<">Before</OPTION> -->
                <OPTION value="=">Equals</OPTION>
                <option value=">" selected>After</option>
                </SELECT>
                <script><!--		
var GC_SET_0 = {
	'appearance': GC_APPEARANCE,
	'dateFormat' : 'd-m-Y'
}
new gCalendar(GC_SET_0,'');
//-->
                  </script>
                  </TD></TR>
              <TR>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                >End Time:</TD>
                <TD class=TableTextColumnodd vAlign=top noWrap>
                <SELECT name="<%=DEUKeyInterface.CONTROL_SELECT_FINAL_RELATION%>"> 
              <!--   <OPTION value="<" selected>Before</OPTION> -->
                <OPTION value="=">Equals</OPTION>
                <option value=">">After</option>
                </SELECT>
                  <script><!--		
var GC_SET_0 = {
	'appearance': GC_APPEARANCE,
	'dateFormat' : 'd-m-Y'
}
new gCalendar(GC_SET_0,'');
//-->
                  </script>
                  </TD></TR>
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
                <TD class=TableTextColumnodd vAlign=top noWrap align=left>
                Source Name:</TD>
                <TD class=TableTextColumnodd vAlign=top noWrap>
                <SELECT
                  name="<%=DEUKeyInterface.CONTROL_SELECT_SOURCE%>"> 
                <option value="" selected>--------------------</option>
                <%
                  if(sourceVector!=null)
                  {
                    for ( int i=0 ; i <sourceVector.size();i++)
                    {
                      SourceModel source =(SourceModel)(sourceVector.get(i));
                      out.print("<OPTION>");
                      out.print(source.getSourceName());
                      out.print("</option>\n");
                    }
                  }
               %>
                </SELECT></TD>
              </tr>
              <tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
               >Connection Name:</TD>
                <TD class=TableTextColumnodd vAlign=top noWrap>
                <SELECT
                  name="<%=DEUKeyInterface.CONTROL_SELECT_CONNECTION%>"> 
                <option value="" selected>--------------------</option>
                 <%
              if(connectionVector!=null)
              {
                for ( int i=0 ; i <connectionVector.size();i++)
                {
                  ConnectionModel connection =(ConnectionModel)(connectionVector.get(i));
                  out.print("<OPTION>");
                  out.print(connection.getConnectionName());
                  out.print("</option>\n");
                }
              }
            %>
                </SELECT></TD>
              </tr>
              <tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
               >Row Count:</TD>
                <TD class=TableTextColumnodd vAlign=top noWrap>
                <SELECT   name="<%=DEUKeyInterface.CONTROL_SELECT_ROW_COUNT%>">
                
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
                  SectorLogStatusModel status =(SectorLogStatusModel)(statusVector.get(i));
                  out.print("<OPTION value='"+status.getStatusText()+"'>");
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
                
                <INPUT class=button id=submit1 type=submit value="  Search Sector Logs  " name=searchLogs>
                </TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
                </FORM>

</DIV>
<DIV>
<FORM id=frmTaskLogsResults name=frmTaskLogsResults action='<%out.print(request.getContextPath());%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet' method=post>
<input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" >      
 <input type="hidden" name="<%out.print(DEUKeyInterface.HIDDEN_LOG_ID);%>" > 
 <br>
 <br>
 
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=0 cellPadding=1 width="100%" bgColor=#ffffff border=1>
              <TBODY>
<!--
              <TR class=TableHeader>
                <TD vAlign=top align=left width="100%"><B>All Sectors Logs</B></TD>
              </TR>
              -->
              <TR>
                <TD vAlign=top>
                  <TABLE class=main style="BORDER-COLLAPSE: collapse" cellSpacing=0 cellPadding=2 width="100%" align=center border=1>
                    <TBODY>
                    <TR>
                    <TD class=TableHeader vAlign=top align=middle >
                     Task Log ID</TD>
                    
                    <TD class=TableHeader vAlign=top align=middle >
                    Start Time</TD>
                    <TD class=TableHeader vAlign=top align=middle >
                    Task Name</TD>
                    <TD class=TableHeader vAlign=top align=middle >
                    Output File Name</TD>
                    <TD class=TableHeader vAlign=top align=middle >
                    Source Name</TD>
                    <TD class=TableHeader vAlign=top align=middle >
                    Sector Order</TD>
                    <TD class=TableHeader vAlign=top align=middle >
                    End Time</TD>
                    <TD class=TableHeader vAlign=top align=middle >
                    Row Count</TD>
                    <TD class=TableHeader vAlign=top align=middle >
                    Status</TD>
                    <TD class=TableHeader vAlign=top align=middle >
                    Error Message</TD>
                    </TR>

                      <%
                        showLogs(request, response, out);
                      %>
                      </TBODY></TABLE>
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
 * generates the HTML code for showing the sector logs encapsulated in the dataHashMap attribute of the request
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
        String error= ((SectorLogModel)dataVector.elementAt(i)).getError();
        if(error==null)error="";
        String fileName = ((SectorLogModel)dataVector.elementAt(i)).getFileName();
        String rowCount = ((SectorLogModel)dataVector.elementAt(i)).getRowCount();
        if(rowCount==null)rowCount="";
        String sourceName = ((SectorLogModel)dataVector.elementAt(i)).getSourceName();
        String status = ((SectorLogModel)dataVector.elementAt(i)).getStatus();
        String connectionName = ((SectorLogModel)dataVector.elementAt(i)).getConnectionName();
        String taskName = ((SectorLogModel)dataVector.elementAt(i)).getTaskName();
        String logID = ((SectorLogModel)dataVector.elementAt(i)).getLogID();
        String sectorOrder = ((SectorLogModel)dataVector.elementAt(i)).getSectorOrder();
        String timeInitial = ((SectorLogModel)dataVector.elementAt(i)).getTimeInitial();
        String timeFinal= ((SectorLogModel)dataVector.elementAt(i)).getTimeFinal();
        String taskWorkLogId= ((SectorLogModel)dataVector.elementAt(i)).getTaskWorkLogId();
        if(timeFinal==null)timeFinal="";

        //timeInitial= timeInitial.replace('/','-');
        //timeFinal= timeFinal.replace('/','-');
        
        out.print("<TR><TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=center>"+taskWorkLogId+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=center>"+timeInitial+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=center>"+taskName+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=center>"+fileName+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=center>"+sourceName+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=center>"+sectorOrder+"</TD>");
        //out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=center>"+connectionName+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=center>"+timeFinal+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=center>"+rowCount+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=center>"+status+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=center>"+error+"</TD>");
        out.print("</TR>\n");
      }
    }
  }
%>