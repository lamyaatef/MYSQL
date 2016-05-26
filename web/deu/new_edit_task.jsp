<%
/**
 *This page is responsible for adding or updating a task.
 * After adding the connection the user is redirected to task_details.jsp
 * 
 * @author Michael Gameel
 */ 
 %>
 
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.deu.*"
         import="com.mobinil.sds.core.system.deu.task.model.*"
         import="com.mobinil.sds.core.system.deu.frequency.model.*"
         import="com.mobinil.sds.core.system.deu.taskstatus.model.*"
         import="com.mobinil.sds.core.system.deu.file.model.*"
         import="com.mobinil.sds.core.system.deu.weekday.model.*"
         import="com.mobinil.sds.web.interfaces.*"
%>
<html>
<head>
<title>::::DEU ADMIN::::</title>
<link REL="STYLESHEET" TYPE="text/css" HREF="../resources/css/Template2.css">
<script language="JavaScript" src="../resources/js/utilities.js" type="text/javascript"></script>
<script language="JavaScript" src="../resources/js/gen_validatorv2.js" type="text/javascript"></script>


</head>

<body leftMargin="0" topMargin="0">
    <Center>
      <H2>Task Details</H2>
    </Center>
<%
  HashMap dataHashMap = new HashMap(100);
  dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  HashMap additional=null;
  Vector frequencyVector=null, fileVector=null, statusVector=null, weekdayVector=null;
  additional = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  frequencyVector = (Vector)additional.get(DEUKeyInterface.VECTOR_TASK_FREQUENCY) ;
  fileVector = (Vector)additional.get(DEUKeyInterface.VECTOR_TASK_FILE);
  statusVector = (Vector)additional.get(DEUKeyInterface.VECTOR_TASK_STATUS);
  weekdayVector = (Vector)additional.get(DEUKeyInterface.VECTOR_TASK_WEEKDAY);
  if(dataHashMap !=null)
  {
   String text="Add";
   String action=DEUKeyInterface.ACTION_ADD_TASK;
   String taskID = (String)dataHashMap.get(DEUKeyInterface.HIDDEN_TASK_ID);
   String name = "";
   String outputFile = "1";
   String frequency = "1";
   String dailyRate= "";
   String weeklyRate= "";
   String monthlyRate= "";
   String monthDayMonthly= "";
   String monthDayYearly= "";
   String endOption= "1";
   String weekDay= "1";
   String month= "1";
   String startDate = "";
   String endDate = "";
   String maxOccurrences = "";
   String status = "1";
   String runHour = "";
      String runMin = "";
   String description = "";
   
   TaskModel newTaskModel = (TaskModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
   if(newTaskModel != null)
   {
    text="Update";
    action=DEUKeyInterface.ACTION_UPDATE_TASK;
    taskID = newTaskModel.getTaskID();
    name = newTaskModel.getName() ;
    outputFile = newTaskModel.getOutputFile();
    frequency = newTaskModel.getFrequency();
    if(frequency.equalsIgnoreCase("Daily"))
    {
      dailyRate=newTaskModel.getRate() ;
      frequency="1";
    }
    else if(frequency.equalsIgnoreCase("Work Days"))
    {
      frequency="2";
    }
    else if(frequency.equalsIgnoreCase("Weekly"))
    {
      weekDay= newTaskModel.getWeekDay();
      weeklyRate=newTaskModel.getRate();
      frequency="3";
    }
    else if(frequency.equalsIgnoreCase("Monthly"))
    {
      monthDayMonthly=newTaskModel.getMonthDay();
      monthlyRate=newTaskModel.getRate();
      frequency="4";
    }
    else if(frequency.equalsIgnoreCase("Yearly"))
    {
      monthDayYearly=newTaskModel.getMonthDay();
      month= newTaskModel.getMonth();
      frequency="5";
    }
    if((dailyRate==null)||(dailyRate.equals("null")))
          dailyRate="";
    if((weeklyRate==null)||(weeklyRate.equals("null")))
      weeklyRate="";
    if((monthlyRate==null)||(monthlyRate.equals("null")))
      monthlyRate="";
    if((monthDayMonthly==null)||(monthDayMonthly.equals("null")))
      monthDayMonthly="";
    if((monthDayYearly==null)||(monthDayYearly.equals("null")))
      monthDayYearly="";
    endOption= newTaskModel.getEndOption();
    if((weekDay==null)||(weekDay.equals("null")))
      weekDay="";
    if((month==null)||(month.equals("null")))
      month="";
    startDate = newTaskModel.getStartDate() ;

    Utility.logger.debug(startDate);
    
    if(endOption.equalsIgnoreCase("3"))
      endDate = newTaskModel.getEndDate();
    else if(endOption.equalsIgnoreCase("2"))
      maxOccurrences = newTaskModel.getMaxOccurrences();
    if((endDate==null)||(endDate.equals("null")))
      endDate="";
    if((maxOccurrences==null)||(maxOccurrences.equals("null")))
      maxOccurrences="";
    status = newTaskModel.getStatus();
    runHour = newTaskModel.getRunHour();
    runMin = newTaskModel.getRunMin();
    description = newTaskModel.getDescription() ;
    if((description==null)||(description.equals("null")))
      description="";
  }
%>
<script>
function toggle(div_id)
{
document.getElementById('1').style.display="none";
document.getElementById('3').style.display="none";
document.getElementById('4').style.display="none";
document.getElementById('5').style.display="none";
obj=document.getElementById(div_id);
if(obj!=null)
obj.style.display="";
}
</script>
<form id="frmAddTask" name="frmAddTask" action='<%out.print(request.getContextPath());%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet' method=post>
  <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>">
  <%if((fileVector!=null)&&(fileVector.size()!=0)){
%>
  <input type="hidden" name="<%out.print(DEUKeyInterface.HIDDEN_TASK_ID);%>" value="<%=taskID%>">
  <script language="JavaScript" src="../resources/js/GCappearance.js" type="text/javascript">
  </script>
  <script language="JavaScript" src="../resources/js/GurtCalendar.js" type="text/javascript">
  </script>
  <link REL="STYLESHEET" TYPE="text/css" HREF="../resources/css/calendar.css">
  <table class="main" cellSpacing="0" cellPadding="2" width="100%" align="center" 1border="0">
    <tr>
      <td>
      <table cellSpacing="0" cellPadding="0" width="100%" bgColor="#ffffff" border="0">
        <tr>
          <td>
          <table cellSpacing="0" cellPadding="1" width="100%" bgColor="#ffffff" border="0" height="220">
            <tr class="TableHeader">
              <td colspan="2" align="left" height="16"><%=text%> Task:</td>
            </tr>
            <tr>
              <td class="TableTextColumnodd" vAlign="top" noWrap align="left" width="1%" height="21">
              Task Name:</td>
              <td class="TableTextColumnodd" vAlign="top" noWrap height="21">
              <input class="input2" type="text" maxlength="30" size="45" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_NAME);%>" value="<%=name%>" title="Enter Task Name"></td>
            </tr>
            <tr>
              <td class="TableTextColumnodd" vAlign="top" noWrap align="left" width="1%" height="18">
              Output File Name:</td>
              <td class="TableTextColumnodd" vAlign="top" noWrap height="18">
              <select class="input2" name="<%out.print(DEUKeyInterface.CONTROL_SELECT_TASK_FILE);%>" >
               <%
                if(fileVector!=null)
                {
                  for ( int i=0 ; i <fileVector.size();i++)
                  {
                    FileModel file =(FileModel)(fileVector.get(i));
                    if(file.getName().equalsIgnoreCase(outputFile))
                      out.print("<OPTION Selected value='"+file.getFileID()+"'>");
                    else 
                      out.print("<OPTION value='"+file.getFileID()+"'>");
                    out.print(file.getName());
                    out.print("</OPTION>\n");
                  }
                }
              %>
              </select></td>
            </tr>
            <tr>
              <td class="TableTextColumnodd" vAlign="top" noWrap align="left" width="1%" height="21">
              Dumping Task Frequency:</td>
              <td class="TableTextColumnodd" vAlign="top" noWrap height="21">
              <select onchange="toggle(document.frmAddTask.frequency.value);" name="<%out.print(DEUKeyInterface.CONTROL_SELECT_TASK_FREQUENCY);%>">
               <%
                if(frequencyVector!=null)
                {
                  for ( int i=0 ; i <frequencyVector.size();i++)
                  {
                  FrequencyModel freq =(FrequencyModel)(frequencyVector.get(i));
                    if(freq.getFrequencyID().equalsIgnoreCase(frequency))
                      out.print("<OPTION Selected value='"+freq.getFrequencyID()+"'>");
                    else 
                      out.print("<OPTION value='"+freq.getFrequencyID()+"'>");
                    out.print(freq.getFrequencyType());
                    out.print("</option>\n");
                  }
                }
              %>
              </select>
              <div id="1" style="DISPLAY: none" name="Daily">
                <table cellSpacing="0" cellPadding="1" width="100%" bgColor="#ffffff" border="0">
                  <tr>
                    <td class="TableTextColumnodd" vAlign="top" noWrap>Every
                    <input class="input2" size="4" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_DAILY_RATE);%>" value="<%=dailyRate%>" ONKEYPRESS="if ((event.keyCode &lt; 48) ||
                      (event.keyCode &gt; 57)) event.returnValue = false;"> day(s)</td>
                  </tr>
                </table>
              </div>
              <div id="3" style="DISPLAY: none" name="Weekly">
                <table cellSpacing="0" cellPadding="1" width="100%" bgColor="#ffffff" border="0">
                  <tr>
                    <td class="TableTextColumnodd" vAlign="top" noWrap>Every
                    <input class="input2" size="4" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_WEEKLY_RATE);%>" value="<%=weeklyRate%>" ONKEYPRESS="if ((event.keyCode &lt; 48) ||
            (event.keyCode &gt; 57)) event.returnValue = false;"> week(s) on
                    <select name="<%out.print(DEUKeyInterface.CONTROL_SELECT_TASK_WEEKDAY);%>">
                     <%
                        if(weekdayVector!=null)
                        {
                          for ( int i=0 ; i <weekdayVector.size();i++)
                          {
                          WeekDayModel day =(WeekDayModel)(weekdayVector.get(i));
                            if(day.getDayID().equalsIgnoreCase(weekDay))
                              out.print("<OPTION Selected value='"+day.getDayID()+"'>");
                            else 
                              out.print("<OPTION value='"+day.getDayID()+"'>");
                            out.print(day.getDayName());
                            out.print("</option>\n");
                          }
                        }
                      %>
                    </select> </td>
                  </tr>
                </table>
              </div>
              <div id="4" style="DISPLAY: none" name="Monthly">
                <table cellSpacing="0" cellPadding="1" width="100%" bgColor="#ffffff" border="0">
                  <tr>
                    <td class="TableTextColumnodd" vAlign="top" noWrap>Day
                    <input class="input2" maxlength="2" size="9" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_MONTHLY_MONTHDAY);%>" value="<%=monthDayMonthly%>" ONKEYPRESS="if ((event.keyCode &lt; 48) ||
            (event.keyCode &gt; 57)) event.returnValue = false;" onchange="if ((this.value>31)||((this.value<1)&&(this.value!=''))){alert('Invalid day!');return false;}"> every
                    <input class="input2" size="4" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_MONTHLY_RATE);%>" value="<%=monthlyRate%>" ONKEYPRESS="if ((event.keyCode &lt; 48) ||
            (event.keyCode &gt; 57)) event.returnValue = false;"> month(s)</td>
                  </tr>
                </table>
              </div>
              <div id="5" style="DISPLAY: none" name="Yearly">
                <table cellSpacing="0" cellPadding="1" width="100%" bgColor="#ffffff" border="0">
                  <tr>
                    <td class="TableTextColumnodd" vAlign="top" noWrap>Every
                    <select onchange="document.frmAddTask.<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_YEARLY_MONTHDAY);%>.value='';" name="<%out.print(DEUKeyInterface.CONTROL_SELECT_TASK_MONTH);%>">
                    <%
                       String [] months={"January","February","March","April","May",
                                  "June","July","August","September","October",
                                  "November","December"};
                        for ( int i=0 ; i <12;i++)
                        {
                          if(((i+1)+"").equalsIgnoreCase(month))
                            out.print("<OPTION Selected value='"+(i+1)+"'>");
                          else 
                            out.print("<OPTION value='"+(i+1)+"'>");
                          out.print(months[i]);
                          out.print("</option>\n");
                        }
                    %>
                    </select>
                    <input class="input2" size="4" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_YEARLY_MONTHDAY);%>" value="<%=monthDayYearly%>" ONKEYPRESS="if ((event.keyCode &lt; 48) ||
            (event.keyCode &gt; 57)) event.returnValue = false;" maxlength="2" ONCHANGE="return checkDate(this.value,document.frmAddTask.<%out.print(DEUKeyInterface.CONTROL_SELECT_TASK_MONTH);%>.value);"> </td>
                  </tr>
                </table>
              </div>
              </td>
            </tr>
            <script>
              toggle('<%=frequency%>');
            </script>
            <tr>
              <td class="TableTextColumnodd" vAlign="top" noWrap align="left" width="1%" height="16">
              Start on: </td>
              <td class="TableTextColumnodd" vAlign="top" noWrap height="16">
              
              <script>
<!--		
var GC_SET_0 = {
	'appearance': GC_APPEARANCE,
	'dateFormat' : 'd/m/Y'
}
new gCalendar(GC_SET_0,'<%=startDate%>');
//-->
                </script>
                

                
              </td>
            </tr>
            <tr>
              <td class="TableTextColumnodd" vAlign="top" noWrap align="left" width="1%" height="16">
              End on: </td>
              <td class="TableTextColumnodd" vAlign="top" noWrap height="16">
              <table cellSpacing="0" cellPadding="1" width="100%" bgColor="#ffffff" border="0">
                <tr>
                  <td colspan="2" class="TableTextColumnodd" vAlign="top" noWrap>
                  <input type="radio" value="1" <%if("1".equalsIgnoreCase(endOption)) out.print("checked"); %> name="<%out.print(
                  DEUKeyInterface.CONTROL_RADIO_TASK_ENDOPTION);%>" >No 
                  End Date</td>
                </tr>
                <tr>
                  <td colspan="2" class="TableTextColumnodd" vAlign="top" noWrap>
                  <input type="radio" value="2" <%if("2".equalsIgnoreCase(endOption)) out.print("checked"); %> name="<%out.print(DEUKeyInterface.CONTROL_RADIO_TASK_ENDOPTION);%>">End 
                  after
                  <input class="input" size="4" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_MAXOCCURRENCES);%>" value="<%=maxOccurrences%>" ONKEYPRESS="if ((event.keyCode &lt; 48) ||
            (event.keyCode &gt; 57)) event.returnValue = false;"> occurrence(s)</td>
                </tr>
                <tr>
                  <td class="TableTextColumnodd" vAlign="top" noWrap align="left" width="1%" height="16">
                  <input type="radio" value="3" <%if("3".equalsIgnoreCase(endOption)) out.print("checked"); %> name="<%out.print(DEUKeyInterface.CONTROL_RADIO_TASK_ENDOPTION);%>">End 
                  on </td>
                  <td colspan="2" class="TableTextColumnodd" vAlign="top" noWrap>
                  <script><!--		
var GC_SET_0 = {
	'appearance': GC_APPEARANCE,
	'dateFormat' : 'd/m/Y'
}
new gCalendar(GC_SET_0,'<%=endDate%>');
//-->
                  </script>
                  </td>
                </tr>
              </table>
              </td>
            </tr>
            <tr>
              <td class="TableTextColumnodd" vAlign="top" noWrap align="left" width="1%" height="21">
              Task Run Hour:</td>
              <td class="TableTextColumnodd" vAlign="top" noWrap height="21">
              <select name="<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_RUNHOUR);%>" >
              <%
              	String xWord = "";
				for(int i=0;i<10;i++) 
				{
					if(runHour.equals(i+"")) xWord =" selected "; else xWord ="";
					out.println("<option value='0"+i+"' "+xWord+">0"+i+"</option>");
				}
				for(int i=10;i<24;i++) 
				{
					if(runHour.equals(i+"")) xWord =" selected "; else xWord ="";
					out.println("<option value='"+i+"' "+xWord+">"+i+"</option>");
				}				
              %>
              </select>
			</td>
            </tr>
            <tr>
              <td class="TableTextColumnodd" vAlign="top" noWrap align="left" width="1%" height="21">
              Task Run Minute:</td>
              <td class="TableTextColumnodd" vAlign="top" noWrap height="21">
              <select name="<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_RUNMIN);%>">
              <%
              	xWord = "";
				for(int i=0;i<10;i++) 
				{
					if(runMin.equals(i+"")) xWord =" selected "; else xWord ="";
					out.println("<option value='0"+i+"' "+xWord+">0"+i+"</option>");
				}
				for(int i=10;i<60;i++) 
				{
					if(runMin.equals(i+"")) xWord =" selected "; else xWord ="";
					out.println("<option value='"+i+"' "+xWord+">"+i+"</option>");
				}				
              %>
              </select>              
			</td>
            </tr>
            <tr>
              <td class="TableTextColumnodd" vAlign="top" noWrap align="left" width="1%" height="21">
              Dumping Task Status:</td>
              <td class="TableTextColumnodd" vAlign="top" noWrap height="21">
              <select name="<%out.print(DEUKeyInterface.CONTROL_SELECT_TASK_STATUS);%>">
              <%
                if(statusVector!=null)
                {
                  for ( int i=0 ; i <statusVector.size();i++)
                  {
                  TaskStatusModel taskstatus =(TaskStatusModel)(statusVector.get(i));
                    if(taskstatus.getTaskStatusType().equalsIgnoreCase(status))
                      out.print("<OPTION Selected value='"+taskstatus.getTaskStatusID()+"'>");
                    else 
                      out.print("<OPTION value='"+taskstatus.getTaskStatusID()+"'>");
                    out.print(taskstatus.getTaskStatusType());
                    out.print("</option>\n");
                  }
                }
              %>
              </select></td>
            </tr>
            <tr>
              <td class="TableTextColumnodd" vAlign="top" noWrap align="left" width="1%" height="52">
              Task Description (Optional):</td>
              <td class="TableTextColumnodd" vAlign="top" noWrap height="52">
              <textarea ID="desc" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_DESCRIPTION);%>" title="Enter text to describe Task for later use" rows="3" cols="61"><%=description%></textarea></td>
            </tr>
          </table>
          <table cellSpacing="0" cellPadding="1" width="100%" bgColor="#ffffff" border="0">
            <tr class="TableHeader">
              <td class="TableTextColumnodd" vAlign="top" noWrap align="middle" colSpan="2">
              <input class="button" type="submit" value="<%=text%> Task" title="<%=text%> Task" name="AddTask" onclick="document.frmAddTask.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%=action%>';">
              <%
                 if(text.equalsIgnoreCase("Update"))
                {
                  out.print("<input class=button type=submit value='Delete' title='Delete Task' name='deleteTask' onclick=\" if(confirm('Are you sure you want to delete this task?')) {document.frmAddTask."+InterfaceKey.HASHMAP_KEY_ACTION+".value='deu_delete_task';return true;}else {return false;} \">");
                }
                %>
<!--
                <input type=button class=button value='Cancel' name='Cancel' onclick='history.go(-1);'>
-->
              </td>
            </tr>
          </table>
          </td>
        </tr>
      </table>
      </td>
    </tr>
  </table>
</form>
<script language="JavaScript" type="text/javascript">
function checkDigit(input) 
{
	var inp = input
	var aStr1 = new String(inp);
	for(i=0; i < aStr1.length; i++)
	{
		var curStr = aStr1.substr(i,1)
		if(!isDigit(curStr)) return false;
	}
	return true;
}

function isDigit(num) 
{
	if ( num.length > 1 ){return false;}
	var string="1234567890";
	if (string.indexOf(num)!=-1){return true;}
	return false;
}

function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this
}
 function checkDate(dayVal,monthVal)
 {
  var days = DaysArray(12);
  if((parseInt(dayVal)>(days[parseInt(monthVal)])))
  {
    alert('Invalid day for the selected month!');
    return false;
  }
  else
    return true;
 }
 
<!-- these functions are to check the date -->
function myCheckdate(objName) 
{
	var datefield = objName;
	if (chkdate(objName) == false) 
	{
		return false;
	}
	else 
	{
		return true;
	}
}
function chkdate(objName) 
{
	var strDatestyle = "US"; //United States date style
	//var strDatestyle = "EU";  //European date style
	var strDate;
	var strDateArray;
	var strDay;
	var strMonth;
	var strYear;
	var intday;
	var intMonth;
	var intYear;
	var booFound = false;
	var datefield = objName;
	var strSeparatorArray = new Array("-"," ","/",".");
	var intElementNr;
	var err = 0;
	var strMonthArray = new Array(12);
	strMonthArray[0] = "Jan";
	strMonthArray[1] = "Feb";
	strMonthArray[2] = "Mar";
	strMonthArray[3] = "Apr";
	strMonthArray[4] = "May";
	strMonthArray[5] = "Jun";
	strMonthArray[6] = "Jul";
	strMonthArray[7] = "Aug";
	strMonthArray[8] = "Sep";
	strMonthArray[9] = "Oct";
	strMonthArray[10] = "Nov";
	strMonthArray[11] = "Dec";
	strDate = datefield.value;
	if (strDate.length < 1) { return true; }
	for (intElementNr = 0; intElementNr < strSeparatorArray.length; intElementNr++) 
	{
		if (strDate.indexOf(strSeparatorArray[intElementNr]) != -1) 
		{
			strDateArray = strDate.split(strSeparatorArray[intElementNr]);
			if (strDateArray.length != 3) 
			{
				err = 1;
				return false;
			}
			else 
			{
				strDay = strDateArray[0];
				strMonth = strDateArray[1];
				strYear = strDateArray[2];
			}
			booFound = true;
		}
	}
	if (booFound == false) 
	{
		if (strDate.length>5) 
		{
		strDay = strDate.substr(0, 2);
		strMonth = strDate.substr(2, 2);
		strYear = strDate.substr(4);
		}
	}
	if (strYear.length == 2) 
	{
		strYear = '20' + strYear;
	}
	// US style
	if (strDatestyle == "US") 
	{
		strTemp = strDay;
		strDay = strMonth;
		strMonth = strTemp;
	}
	intday = parseInt(strDay, 10);
	if (isNaN(intday)) 
	{
		err = 2;
		return false;
	}
	intMonth = parseInt(strMonth, 10);
	if (isNaN(intMonth)) 
	{
		for (i = 0;i<12;i++) 
		{
			if (strMonth.toUpperCase() == strMonthArray[i].toUpperCase()) 
			{
				intMonth = i+1;
				strMonth = strMonthArray[i];
				i = 12;
			}
		}
		if (isNaN(intMonth)) 
		{
			err = 3;
			return false;
		}
	}
	intYear = parseInt(strYear, 10);
	if (isNaN(intYear)) 
	{
		err = 4;
		return false;
	}
	if (intMonth>12 || intMonth<1) 
	{
		err = 5;
		return false;
	}
	if ((intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8 || intMonth == 10 || intMonth == 12) && (intday > 31 || intday < 1)) 
	{
		err = 6;
		return false;
	}
	if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) && (intday > 30 || intday < 1)) 
	{
		err = 7;
		return false;
	}
	if (intMonth == 2) 
	{
		if (intday < 1) 
		{
			err = 8;
			return false;
		}
		if (LeapYear(intYear) == true) 
		{
			if (intday > 29) 
			{
				err = 9;
				return false;
			}
		}
		else 
		{
			if (intday > 28) 
			{
				err = 10;
				return false;
			}
		}
	}
	if (strDatestyle == "US") 
	{
		datefield.value = strMonthArray[intMonth-1] + " " + intday+" " + strYear;
	}
	else 
	{
		datefield.value = intday + " " + strMonthArray[intMonth-1] + " " + strYear;
	}
	return true;
}

function LeapYear(intYear) 
{
	if (intYear % 100 == 0) 
	{
		if (intYear % 400 == 0) { return true; }
	}
	else 
	{
		if ((intYear % 4) == 0) { return true; }
	}
	return false;
}
<!-- end of these functions to check the date -->
function doDateCheck(from, to) 
{
	if (Date.parse(from.value) <= Date.parse(to.value)) 
	{
		return true;
	}
	else 
	{
		if (from.value == "" || to.value == "")  return true;
		else { return false; }
	}
	return false;
}



function DoCustomValidation()
{
	var frm = document.forms["frmAddTask"];
	if(frm.endOption[1].checked)
	{
		if(frm.<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_MAXOCCURRENCES);%>.value=='')
		{
			alert('Please enter maximum occurrences');
			return false;
		}
	}
	if(frm.endOption[2].checked)
	{
		if(frm.date1.value=='')
		{
			alert('Please enter end date');
			return false;
		}
		
		else if(doDateCheck(frm.date0,frm.date1))
		{
			return true;
		}
		else
		{
			alert("End Date must occur after the Start Date."); 
			return false; 
		}
		return false;
	}
			
	if(frm.frequency.value=="1")
	{
		if(frm.dailyRate.value=="")
		{
			alert ('Please enter daily reccurrence frequency');
			return false;
		}
		if(frm.dailyRate.value=="0")
		{
			alert ('Invalid daily recurrence frequency');
			return false;
		}
	}
	else if(frm.frequency.value=="3")
	{
		if(frm.weeklyRate.value=='')
		{
			alert ('Please enter weekly reccurrence frequency');
			return false;
		}
	}
	else if(frm.frequency.value=="4")
	{
		if(frm.monthlyMonthDay.value=='')
		{
			alert ('Please enter day of month');
			return false;
		}
		if(frm.monthlyRate.value=='')
		{
			alert ('Please enter monthly reccurrence frequency');
			return false;
		}
		if(frm.monthlyRate.value=='0')
		{
			alert ('Invalid monthly reccurrence frequency');
			return false;
		}
	}
	else if(frm.frequency.value=="5")
	{
		if(frm.yearlyMonthDay.value=='')
		{
			alert ('Please enter day of month');
			return false;
		}
	}
	
	if (!checkDigit(frm.<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_MAXOCCURRENCES);%>.value))
	{
		alert('Invalid maximum occurrences value');
		return false;
	}
	if ((!checkDigit(frm.<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_RUNHOUR);%>.value))||(frm.<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_RUNHOUR);%>.value>59)||(frm.<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_RUNHOUR);%>.value<0))
	{
		alert('Invalid Run Hour');
		return false;
	}
	if ((!checkDigit(frm.<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_RUNMIN);%>.value))||(frm.<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_RUNMIN);%>.value>59)||(frm.<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_RUNMIN);%>.value<0))
	{
		alert('Invalid Run Min');
		return false;
	}
	return true;
}


 var frmvalidator = new Validator("frmAddTask");
 frmvalidator.addValidation("<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_NAME);%>","req","Please enter Task Name");
 frmvalidator.addValidation("date0","req","Please enter Start Date");
  frmvalidator.addValidation("<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_RUNHOUR);%>","req","Please Enter Task Run Hour");
  frmvalidator.addValidation("<%out.print(DEUKeyInterface.CONTROL_TEXT_TASK_RUNMIN);%>","req","Please Enter Task Run Min");
 frmvalidator.setAddnlValidationFunction("DoCustomValidation");
</script>
       <%}
else
{
  action=DEUKeyInterface.ACTION_NEW_FILE;
%><SCRIPT>
document.frmAddTask.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%=action%>';
alert('A File must be added first!');
document.frmAddTask.submit();
</SCRIPT>
<%}%> 
<%
}
%>
</body>

</html>