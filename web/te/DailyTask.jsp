<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.te.TaskInterfaceKey"
         import="com.mobinil.sds.core.system.te.dto.*"         
 %>

 <%
  System.out.println("here in the head of jsp");
 String appName = request.getContextPath();
  System.out.println("here in the head of jsp app "+appName);
     String userId=(String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
	  System.out.println("here in the head of jsp user id "+userId);

 %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">    
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
		<SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/calendar.js" type=text/javascript></SCRIPT>
    <SCRIPT language=JavaScript>   
    function textCounter(field,cntfield,maxlimit) {
    	if (field.value.length > maxlimit) // if too long...trim it!
    	field.value = field.value.substring(0, maxlimit);
    	// otherwise, update 'characters left' counter
    	else
    	cntfield.value = maxlimit - field.value.length;
    	}
    function defaultValues() 
    {
    document.getElementById('start_hour').selectedIndex =0;
    document.getElementById('end_hour').selectedIndex =0;
    }
function loadValues(control,value)
  {
    document.formTaskDaily.control.value=value;
  }    
function checkbeforSubmit()
{		
  if(NonBlank(document.formTaskDaily.<%out.print(TaskInterfaceKey.CONTROL_TEXT_TASK_NAME);%>, true, 'Task Name')
  && NonBlank(document.formTaskDaily.<%out.print(TaskInterfaceKey.CONTROL_INPUT_DAY_STAER_FROM_DATE);%>, true, 'day start') && 
  NonBlank(document.formTaskDaily.message1, true, 'SQL statement'))
  {  
	  var startHour = document.formTaskDaily.start_hour.value;
	  var endHour = document.formTaskDaily.end_hour.value;

	  if (parseInt(startHour) >=  parseInt(endHour))
	  {
	  	alert('Please check tast start and end hour.');
	  }
	  else
	  {
		document.formTaskDaily.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(TaskInterfaceKey.ACTION_CHECK_DB_BEFORE_SUBMIT);%>'
		formTaskDaily.submit();
	  }
  } 
return false;
}
  
</SCRIPT>
  </head>
  <body>
        <CENTER>
<H2>  New&nbsp;Daily&nbsp;Task </H2>
</CENTER>
 <form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formTaskDaily" id="formTaskDaily" method="post" >

<%
boolean flag = false;
System.out.println("here in the head of java code ");
HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
DailyDTO taskInfo = (DailyDTO) objDataHashMap.get(TaskInterfaceKey.Daily_DTO_FLY_VALUES) ;
System.out.println("step 1 jsp " +objDataHashMap );
String message[] = (String [])objDataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);
System.out.println("step 2 jsp "+ message);
if (message!= null){
	System.out.println("calss dto is " +taskInfo.getTaskName() );
	
if (message[4]!= null)
	{
	
	System.out.println("here in done ");	
	out.println("<script>");
	out.println("document.formTaskDaily.action.value='"+TaskInterfaceKey.ACTION_ADD_NEW_DAILY_CONTRACT+"'");
	out.println("document.formTaskDaily."+InterfaceKey.HASHMAP_KEY_USER_ID+".value='"+userId+"';");
	flag = true;
	
	out.println("</script>");
	message = null;
	
	}
else{
		
			System.out.println("here in not done ");
		
		out.println("<script>");
		
		String erroeMessage = "";
		for(int i=0;i<=message.length-1;i++)
		{
			if (message[i]!=null)
			{
			if (message[i].compareTo("")!=0)
			{
			System.out.print("message length is "+message[i].length());
			erroeMessage +=message[i] +  "\\n";
			System.out.print(erroeMessage);
			}

			}
		}
		out.println("alert ('Please fix these values \\n"+erroeMessage+" ');");

		
		out.println("</script>");
		message = null;
		
	}
}
System.out.println("step 4 jsp "+ message);
  
 if (message==null){
	 System.out.println("here in null ");

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value="+userId+"\">");                  

out.println("<TABLE style=\"WIDTH: 100%; BORDER-COLLAPSE: collapse; HEIGHT: 91px\" cellSpacing=2 cellPadding=1 width=\"746\" border=1>");
out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Task&nbsp;Name *</TD>");


out.println("<TD class=TableTextNote colSpan=4><INPUT style=\"WIDTH: 452px; HEIGHT: 22px\" size=66"
			  + " value=\"" +"\"" 
              + " name=\""+TaskInterfaceKey.CONTROL_TEXT_TASK_NAME+"\">");


out.println("</TD></TR>");


out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Task&nbsp;Start&nbsp;Day *</TD><TD>");


out.println("<input name=\""+TaskInterfaceKey.CONTROL_INPUT_DAY_STAER_FROM_DATE+"\" type=\"text\"><input type=\"button\" onClick=\"showCalendar(formTaskDaily."+TaskInterfaceKey.CONTROL_INPUT_DAY_STAER_FROM_DATE+",'dd/mm/yyyy','Choose date')\" name=\"Submit2\" value=\"...\">");


out.println("</TD></TR>");

out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Task&nbsp;Start&nbsp;and&nbsp;End&nbsp;Hour*</TD>");


out.println("<TD class=TableTextNote colSpan=4>");
out.println("From &nbsp;&nbsp;<select name=\"start_hour\">");
for(int i=0;i<24;i++)
{
	if (i<10)
		out.println("<option value="+i+">0"+i+"</option>");
	else 
		out.println("<option value="+i+">"+i+"</option>");
}
out.println("</select>");

out.println("To &nbsp;&nbsp;<select name=\"end_hour\">");
for(int i=0;i<24;i++)
{
	if (i<10)		
		out.println("<option value="+i+">0"+i+"</option>");
	else 
		out.println("<option value="+i+">"+i+"</option>");
}
out.println("</select>");

out.println("</TD></TR>");
out.println("<TR>");
out.println("<TD class=TableTextNote width=\"20%\" noWrap>Task&nbsp;Sql </TD>");
out.println("<TD class=TableTextNote colSpan=4><textarea name=message1 style=\"WIDTH: 451px; HEIGHT: 84px\" maxlength=4000 wrap=physical cols=50  rows=8 "+
		" onKeyDown=\"textCounter(document.getElementById('message1'),document.getElementById('remLen1'),4000)\""+
			" onKeyUp=\"textCounter(document.getElementById('message1'),document.getElementById('remLen1'),4000)\"></textarea>&nbsp; <input readonly type=text name=remLen1 size=4 maxlength=4 value=4000 /></TD></TR>");

out.println("<TR>");
out.println("<TD class=TableTextNote width=\"20%\" noWrap>Task&nbsp;Description </TD>");
out.println("<TD class=TableTextNote colSpan=4><TEXTAREA id=textarea1 style=\"WIDTH: 451px; HEIGHT: 84px\" name=\""+TaskInterfaceKey.CONTROL_TEXT_TASK_DESCRIPTION+"\" rows=4 cols=47>"+"</TEXTAREA></TD></TR>");

out.println("<TR class=TableTextNote><TD colspan=5 class=TableTextNote><font size=1>* indicates mandatory fields.&nbsp;</font></TD></TR>");
out.println("<TR class=TableTextNote><TD colspan=5 class=TableTextNote><font size=1>00 indicates to 12:00 Am</font></TD></TR></TABLE></TABLE>");

out.println("<input type=\"hidden\" name=\""+TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID+"\""+
                  " value=\""+"\">");
out.println("<table  cellspacing=2 cellpadding=1 border=0 width=747 style=WIDTH: 747px; HEIGHT: 26px>");
out.println("<tr>");
out.println("<td align=middle>");
      out.println("<P align=center>");
if (taskInfo!=null){
		System.out.println("calss task name is  " +taskInfo );
	out.println("<script>");	
	out.println("document.formTaskDaily."+TaskInterfaceKey.CONTROL_TEXT_TASK_NAME+".value='"+taskInfo.getTaskName()+"';");
	out.println("document.formTaskDaily."+TaskInterfaceKey.CONTROL_INPUT_DAY_STAER_FROM_DATE+".value='"+taskInfo.getDayStart()+"';");
	out.println("document.formTaskDaily.start_hour.value='"+taskInfo.getHourStart()+"';");
	out.println("document.formTaskDaily.end_hour.value='"+taskInfo.getHourEnd()+"';");
	out.println("document.formTaskDaily.message1.value='"+taskInfo.getSQL()+"';");
	out.println("document.formTaskDaily."+TaskInterfaceKey.CONTROL_TEXT_TASK_DESCRIPTION+".value='"+taskInfo.getTaskDesc()+"';");
	if (flag)
	{
		out.println("formTaskDaily.submit();");
	}
	out.println("</script>");
	}
out.println(" &nbsp;<INPUT class=button onClick=\"checkbeforSubmit();\" type=button value=\" Save \" name=save></P></td>");
out.println("</tr>");
out.println("</table>");
 }
%>
</form>

  </body>
</html>




