<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import=" com.mobinil.sds.web.interfaces.cam.*"
         import=" com.mobinil.sds.core.system.cam.accrual.model.*"
         import ="com.mobinil.sds.core.system.cam.accrual.dao.*"
         import ="com.mobinil.sds.web.interfaces.InterfaceKey"
         import ="com.mobinil.sds.core.system.cam.payment.model.*"
         import="com.mobinil.sds.core.system.cam.memo.model.*"   
         import=" com.mobinil.sds.core.system.ifReportDelivery.model.*"     
         
         %>
<%
        String appName = request.getContextPath();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String formName = "if_mgt_form";
        String pageHeader="Created Jobs";
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String userID =(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String action=(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
        Vector jobs=(Vector)dataHashMap.get(IFReportDeliveryInterfaceKey.VECTOR_JOBS);
        System.out.println("jobs size: "+jobs.size());
        Vector jobStatuses=(Vector)dataHashMap.get(IFReportDeliveryInterfaceKey.VECTOR_JOB_STATUSES);
        String view="";
%>
<script language="javaScript">
function submitSearch(){
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=IFReportDeliveryInterfaceKey.ACTION_SEARCH_JOBS %>";
	document.<%=formName%>.submit();
}
function submitView(jobId)
{
	
	document.<%=formName%>.<%=IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID%>.value = jobId;
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=IFReportDeliveryInterfaceKey.ACTION_VIEW_JOB_DETAILS %>";
document.<%=formName%>.submit();
}
function submitClose(jobId)
{
	
	document.<%=formName%>.<%=IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID%>.value = jobId;
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=IFReportDeliveryInterfaceKey.ACTION_CLOSE_JOB_DETAILS %>";
document.<%=formName%>.submit();
}
function submitDelete(jobId)
{
	
	document.<%=formName%>.<%=IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID%>.value = jobId;
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=IFReportDeliveryInterfaceKey.ACTION_DELETE_JOB_DETAILS %>";
document.<%=formName%>.submit();
}
function drawCalender(argOrder,argValue)
{
    document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(if_mgt_form."+argOrder+",'mm/dd/yyyy','Choose date')\">");
    document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
}
function submitExportNotFound(jobId)
{
	document.<%=formName%>.<%=IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID%>.value = jobId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=IFReportDeliveryInterfaceKey.ACTION_EXPORT_NOT_FOUND %>";
	document.<%=formName%>.submit();
}

function submitExportDistErrors(jobId)
{
	document.<%=formName%>.<%=IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID%>.value = jobId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=IFReportDeliveryInterfaceKey.ACTION_EXPORT_DIST_ERRORS %>";
	document.<%=formName%>.submit();
}

function submitExportInfoFortErrors(jobId)
{
	document.<%=formName%>.<%=IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID%>.value = jobId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=IFReportDeliveryInterfaceKey.ACTION_EXPORT_INFO_FORT_ERRORS %>";
	document.<%=formName%>.submit();
}
function submitExportDuplicates(jobId)
{
	document.<%=formName%>.<%=IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID%>.value = jobId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=IFReportDeliveryInterfaceKey.ACTION_EXPORT_DUPLICATES %>";
	document.<%=formName%>.submit();
}
function submitExportAutoMatched(jobId)
{
	document.<%=formName%>.<%=IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID%>.value = jobId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=IFReportDeliveryInterfaceKey.ACTION_EXPORT_AUTO_MATCHED %>";
	document.<%=formName%>.submit();
}
</script>

<%@page import="com.mobinil.sds.web.interfaces.IFReportDelivery.IFReportDeliveryInterfaceKey"%>
<%@page import="com.mobinil.sds.web.interfaces.IFReportDelivery.IFReportDeliveryInterfaceKey"%><html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/calendar.js"></SCRIPT>
        </head>
    <body>
        <center><h2> <%=pageHeader%></h2></center>
        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
            <input type="hidden" name="<%=IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID%>" value="-1">
            
            <input type="hidden" name="<%out.print(IFReportDeliveryInterfaceKey.First_Time_Search);%>" value="true">
            
		      <input type="hidden" name="<%out.print(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER);%>" value="1">
            
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <tr class="TableHeader">
            <td colspan="8" align="center">Search</td>
            </tr>
            <tr>
            <td>Job Creation Date</td>
            <td>
            <script type="text/javascript">
            drawCalender('<%=IFReportDeliveryInterfaceKey.CONTROL_JOB_CREATION_DATE%>','Job_Creation_Date');
    		</script>
            </td>
            <td>Report Creation Date</td>
            <td>
            <script type="text/javascript">
            drawCalender('<%=IFReportDeliveryInterfaceKey.CONTROL_REPORT_CREATION_DATE%>','Report_Creation_Date');
    		</script>
    		</td>
    		<td>Job Status</td>
    		<td>
    		<select name="<%=IFReportDeliveryInterfaceKey.CONTROL_SELECT_JOB_STATUS %>" id="<%=IFReportDeliveryInterfaceKey.CONTROL_SELECT_JOB_STATUS %>" >
    		<option></option>            
            <%for(int j=0;j<jobStatuses.size();j++){
            	JobStatusModel js=(JobStatusModel)jobStatuses.get(j);
               
            %>
            <option  value="<%=js.getJobStatusId() %>"><%=js.getJobStatusName() %></option>
            <%} %>
            
            </select>
    		</td>
            </tr>
            <tr>
            <td colspan="6" align="center"><input class="button" type="button" name="Search" value="Search" onclick="javascript:submitSearch()"></td>
            </tr>
            </TABLE>
            
            <br>
            <br>

            <%if(jobs.size()!=0 && jobs!=null){ %>
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <tr class="TableHeader">
            <td colspan="13" align="center">Jobs</td>
            </tr>
            <tr class="TableHeader">
            <td>Job ID</td>                       
            <td>Job Creation Date</td>
            <td>Job Status</td>              
            <td>Report Creation Date</td> 
            <td>Number Of Not Found Records</td>
            <td>Number Of Dist Errors</td>
            <td>Number Of Info Fort Errors</td> 
            <td>Number Of Duplicates</td>
            <td>Number Of Distincts</td>
            <td>Number Of Auto Matched</td>
              
            <td>View</td>   
            <td>Close</td>
            <td>Delete</td>   
            </tr>
            <%for(int i=0;i<jobs.size();i++){
            	IFReportJobModel jm=(IFReportJobModel)jobs.get(i);
            %>
            <tr>
            <td><%=jm.getJobId() %></td>
            <td><%=jm.getJobDate() %></td>
            <td><%=jm.getJobStatus() %></td>
            <td><%=jm.getReportCreationDate() %></td>  
            
            
            <td align="center">
            <%=jm.getNumberOfNotFound() %>
            <%if(jm.getNumberOfNotFound()==0)         	
            	view="disabled";
            else
            	view="";
            	%>
            
            <input <%=view %> class="button" type="button" name="Export As Excel" value="Export As Excel" onclick="javascript:submitExportNotFound(<%=jm.getJobId() %>)">
            </td>
            
            
            <td align="center">
            <%=jm.getNumberOfDistErrors() %>
            <%if(jm.getNumberOfDistErrors()==0)         	
            	view="disabled";
            else
            	view="";
            	%>
            <input <%=view %> class="button" type="button" name="Export As Excel" value="Export As Excel" onclick="javascript:submitExportDistErrors(<%=jm.getJobId() %>)">
            </td>
            
            
            <td align="center">
            <%=jm.getNumberOfInfoFortErros() %>
            <%if(jm.getNumberOfInfoFortErros()==0)         	
            	view="disabled";
            else
            	view="";
            	%>
            <input <%=view %> class="button" type="button" name="Export As Excel" value="Export As Excel" onclick="javascript:submitExportInfoFortErrors(<%=jm.getJobId() %>)">
            </td>
            
            
            <td align="center">
            <%=jm.getNumberOfDuplicates() %>
            <%if(jm.getNumberOfDuplicates()==0)         	
            	view="disabled";
            else
            	view="";
            	%>
            <input <%=view %> class="button" type="button" name="Export As Excel" value="Export As Excel" onclick="javascript:submitExportDuplicates(<%=jm.getJobId() %>)">
            </td>
            
            
            <td align="center">            
            <%=jm.getNumberOfDistincts() %>
            </td>
            
            <td align="center">
            <%=jm.getNumberofAutoMatched()%>
            <%if(jm.getNumberofAutoMatched()==0)         	
            	view="disabled";
            else
            	view="";
            	%>
            <input <%=view %> class="button" type="button" name="Export As Excel" value="Export As Excel" onclick="javascript:submitExportAutoMatched(<%=jm.getJobId() %>)">
            </td>
            
                      
             
            <td><input class="button" type="button" name="View" value="View" onclick="javascript:submitView(<%=jm.getJobId() %>)"></td>         
            <%if(jm.getJobStatusId()==2||jm.getJobStatusId()==3)
            	view="disabled";
            else
            	view="";
            	%>
            <td><input <%=view %> class="button" type="button" name="Close" value="Close" onclick="javascript:submitClose(<%=jm.getJobId() %>)"></td>
            <%if(jm.getJobStatusId()==3)
            	view="disabled";
            else
            	view="";
            	%>
            <td><input <%=view %> class="button" type="button" name="Delete" value="Delete" onclick="javascript:submitDelete(<%=jm.getJobId() %>)"></td>
            </tr>  
            <%} %>
            </TABLE>
            <%} %>
            
                     
       </form>
    </body>
</html>