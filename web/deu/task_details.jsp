<%
/**
 *This page shows all the tasks currently stored in the database. 
 *It has buttons for editing a certain task and a button for adding a
 *new task.
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
         import="com.mobinil.sds.core.system.deu.task.dao.*"
         import="com.mobinil.sds.core.system.deu.task.model.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="java.util.Date, java.text.SimpleDateFormat"
         import="java.text.*"
         import="java.util.Locale.*"
         
%>
<HTML><HEAD>                        
<TITLE>::::DEU ADMIN::::</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1256">
<LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
<SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>



<META content="Microsoft FrontPage 5.0" name=GENERATOR></HEAD>
<BODY leftMargin=0 topMargin=0>
    <Center>
      <H2>Task Settings</H2>
    </Center>
<FORM id=frmTaskDetails name=frmTaskDetails action='<%out.print(request.getContextPath());%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet' method=post>
<input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" >
<input type="hidden" name="<%out.print(DEUKeyInterface.HIDDEN_TASK_ID);%>" >
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=0 cellPadding=1
            width="100%" bgColor=#ffffff border=1>
              <TBODY>
              <TR>
                <TD vAlign=top colSpan=2>
                <TABLE class=main cellSpacing=0 cellPadding=0 width="100%" align=center
border=0>
  <TBODY>
  <TR>
    <TD >
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#ffffff
        border=0>
                    <TBODY>
                    <TR>
                      <TD class=TableTextColumn vAlign=top align=middle >
                        <TABLE class=main style="BORDER-COLLAPSE: collapse"
                        cellSpacing=0 cellPadding=2 width="100%" align=center
                        border=1>
                          <TBODY>
                          <TR>
                            <TD class=TableHeader vAlign=top
                              align=middle >Task Name</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >Frequency</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >Start On</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >End On</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >Occurrences</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >Task Status</TD>
   <!--                         <TD class=TableTextColumnEven vAlign=top
                              align=middle >Run Hour</TD>-->
                        <!-- <TD class=TableTextColumnEven vAlign=top
                              align=middle >Description</TD>-->
                            <TD class=TableHeader vAlign=top
                              align=middle >Next Run Date</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >Edit</TD>
                             <TD class=TableHeader vAlign=top
                              align=middle >Run Now</TD> 
                            </TR>


<%
  showTasks(request, response, out);
%>
</TBODY></TABLE></TD></TR>
                                     <TR><TD>
            <TABLE cellSpacing=0 cellPadding=1 width="100%" bgColor=#ffffff
            border=0>
              <TBODY>
              <TR class=TableHeader>
                <TD class=TableTextColumnOdd vAlign=top noWrap align=middle colSpan=2>
                <INPUT class=button id=AddTask type=button value=" Add Task  " name=AddTask onclick="document.frmTaskDetails.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(DEUKeyInterface.ACTION_NEW_TASK);%>';frmTaskDetails.submit();">
                </TD></TR></TBODY></TABLE>
                    </TD></TR></TBODY></TABLE>
</TD></TR></TBODY></TABLE>
<CENTER>
<p><A onclick="window.print();" href="#"><IMG src="<%out.print(request.getContextPath());%>/resources/img/printicon.gif" border=0 height="31"></A>
</p>
</CENTER>
                    </TBODY></TABLE></FORM></BODY></HTML>

<%!
/**
 * generates the HTML code for showing the tasks encapsulated in the dataHashMap attribute of the request
 * 
 * @param	request	HttpServletRequest for this page
 * @param	response	HttpServletResponse object for this page
 * @param	out	JspWriter Object for this page
 */ 
public void showTasks(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(!dataHashMap.isEmpty()){
      Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      for(int i = 0; i<dataVector.size(); i++){
        String taskID 			= ((TaskModel)dataVector.elementAt(i)).getTaskID();
        String name 			= ((TaskModel)dataVector.elementAt(i)).getName();
        String fileName 		= ((TaskModel)dataVector.elementAt(i)).getOutputFile();
        String frequency 		= ((TaskModel)dataVector.elementAt(i)).getFrequency();
        String startDate 		= ((TaskModel)dataVector.elementAt(i)).getStartDate();
        String endDate 			= ((TaskModel)dataVector.elementAt(i)).getEndDate();
        String maxOccurrences 	= ((TaskModel)dataVector.elementAt(i)).getMaxOccurrences();
        String occurrences 		= ((TaskModel)dataVector.elementAt(i)).getOccurrences();
        String taskStatus 		= ((TaskModel)dataVector.elementAt(i)).getStatus();
        String runHour 			= ((TaskModel)dataVector.elementAt(i)).getRunHour(); 
        String description 		= ((TaskModel)dataVector.elementAt(i)).getDescription();
        String nextRunDate 		= ((TaskModel)dataVector.elementAt(i)).getNextRunDate();
        String bgcolor			= DEUKeyInterface.STYLE[1];

      if (taskStatus.compareTo("Active")!=0)
      {
      nextRunDate ="N/A";
      }
        if(endDate!=null && nextRunDate!= null)
        {
			String format = "dd-MM-yyyy";
			java.util.Date tmp_endDate		= null;
			java.util.Date tmp_nextRunDate 	= null;
      
			SimpleDateFormat formatter 		= new SimpleDateFormat(format, java.util.Locale.ENGLISH);


            
		
			ParsePosition pos 	= new ParsePosition(0);
			ParsePosition pos1 	= new ParsePosition(0);				

			tmp_endDate 	= formatter.parse(endDate,pos);
			tmp_nextRunDate = formatter.parse(nextRunDate,pos1);

  /* 
      Utility.logger.debug(endDate);
      Utility.logger.debug(tmp_endDate);
      Utility.logger.debug(nextRunDate);
      Utility.logger.debug(tmp_nextRunDate);
      Utility.logger.debug(taskStatus);
      
      
      Utility.logger.debug(tmp_nextRunDate.before(tmp_endDate));
      Utility.logger.debug(tmp_nextRunDate.after(tmp_endDate));
*/

      
			if(tmp_nextRunDate!=null&& tmp_nextRunDate.after(tmp_endDate)) 
      nextRunDate ="N/A";

        }

      if(endDate==null)
        {
          if(maxOccurrences==null) 	endDate = "No End Date.";
          else 						endDate = "After "+maxOccurrences+" Occurrence(s).";
        }


        String color="";
        
        if(taskStatus.equals("Active")) 
        color=""; 
        else
        color="_inactive"; 

        bgcolor=DEUKeyInterface.STYLE[i%2];
         
        out.print("<TR>\r\n");
        out.print("<TD class="+bgcolor+" vAlign=top align=left>"+name+"</TD>");
        out.print("<TD class="+bgcolor+" vAlign=top align=left>"+frequency+"</TD>");
        out.print("<TD class="+bgcolor+" vAlign=top align=left>"+startDate+"</TD>");
        out.print("<TD class="+bgcolor+" vAlign=top align=left>"+endDate+"</TD>");
        out.print("<TD class="+bgcolor+" vAlign=top align=left>"+occurrences+"</TD>");
        out.print("<TD class="+bgcolor+color+" vAlign=top align=left>"+taskStatus+"</TD>");
        /*
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+name+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+frequency+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+startDate+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+endDate+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+occurrences+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+taskStatus+"</TD>");
        */
 //       out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=center><B>"+runHour+"</B></TD>");
        if(description==null)
          description="No available description.";
  //      out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=center><B>"+description+"</B></TD>");
        if(nextRunDate==null)
          nextRunDate="Not Specified.";
        out.print("<TD class="+bgcolor+" vAlign=top align=left>"+nextRunDate+"</TD>\r\n");
        out.print("<TD class="+bgcolor+" vAlign=top align=left>"+
        "<TABLE cellSpacing=0 cellPadding=1 width='100%' bgColor=#ffffff border=0>"+
        "<TBODY><TR><TD class="+bgcolor+" vAlign=top noWrap align=center>"+
        "<INPUT class=button type=button id=EditTask"+i+" value='  Edit  ' name='EditTask"+i+
        "' onclick=\"document.frmTaskDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+DEUKeyInterface.ACTION_EDIT_TASK+"'; "
        +"document.frmTaskDetails."+DEUKeyInterface.HIDDEN_TASK_ID+".value='"+taskID+"'; "
        +"frmTaskDetails.submit();\" ></TD></TR></TBODY></TABLE></TD>");

        if (color.length()==0)
        {
        out.print("<TD class="+bgcolor+" vAlign=top align=center><TABLE cellSpacing=0 cellPadding=1 width='100%' bgColor=#ffffff border=0><TBODY><TR><TD class="+bgcolor+" vAlign=top noWrap align=center>"
        +"<INPUT class=button type=button id=RunTask"+i+"type=button onclick=\"if(confirm('Are you sure you want to run this task now?')) {document.frmTaskDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+DEUKeyInterface.ACTION_RUN_TASK+"'; "
        +"document.frmTaskDetails."+DEUKeyInterface.HIDDEN_TASK_ID+".value='"+taskID+"'; frmTaskDetails.submit();}\" value='  Run Now  ' name=RunTask"+i+"></TD></TR></TBODY></TABLE>\r\n");
        }
        else
        {
                out.print("<TD class="+bgcolor+" vAlign=top align=center><TABLE cellSpacing=0 cellPadding=1 width='100%' bgColor=#ffffff border=0><TBODY><TR><TD class="+bgcolor+" vAlign=top noWrap align=center>"
        +"<INPUT class=button type=button id=RunTask"+i+"type=button onclick=\"if(confirm('This Task Is Inactive. Are you sure you want to run this task now?')) {document.frmTaskDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+DEUKeyInterface.ACTION_RUN_TASK+"'; "
        +"document.frmTaskDetails."+DEUKeyInterface.HIDDEN_TASK_ID+".value='"+taskID+"'; frmTaskDetails.submit();}\" value='  Run Now  ' name=RunTask"+i+"></TD></TR></TBODY></TABLE>\r\n");
        }

        out.print("</TR>");
      }
    }
  }
%>