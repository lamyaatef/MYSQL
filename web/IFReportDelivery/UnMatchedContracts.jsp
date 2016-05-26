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
        String pageHeader="Job Details";
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String userID =(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String action=(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
        Vector infoFortData=(Vector)dataHashMap.get(IFReportDeliveryInterfaceKey.VECTOR_INFO_FORT_REPORTS);
        Vector dataRecordTypes=(Vector)dataHashMap.get(IFReportDeliveryInterfaceKey.VECTOR_DATA_RECORD_TYPES);
        String jobId=(String)dataHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_JOB_ID);
        String view="";
                
%>
<script language="javaScript">
function nextPage()
{
  
  document.getElementById('<%=IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER%>').value = parseInt(document.getElementById('<%=IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER%>').value)+1;  
  document.getElementById('<%=InterfaceKey.HASHMAP_KEY_ACTION%>').value='<%=IFReportDeliveryInterfaceKey.ACTION_VIEW_JOB_DETAILS%>';
  document.getElementById('<%=IFReportDeliveryInterfaceKey.First_Time_Search%>').value='<%=IFReportDeliveryInterfaceKey.First_Time_Search_FALSE%>'; 
  document.<%=formName%>.submit();
}

function prevPage()
{
  document.getElementById('<%=IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER%>').value -= 1;
  document.getElementById('<%=InterfaceKey.HASHMAP_KEY_ACTION%>').value='<%=IFReportDeliveryInterfaceKey.ACTION_VIEW_JOB_DETAILS%>';    
  document.<%=formName%>.<%=IFReportDeliveryInterfaceKey.First_Time_Search%>.value='<%=IFReportDeliveryInterfaceKey.First_Time_Search_FALSE%>'; 
  document.<%=formName%>.submit();
}
function submitSearch()
{
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=IFReportDeliveryInterfaceKey.ACTION_SEARCH_EXPORT_MOBINIL_REPORT_TO_INFO %>";
document.<%=formName%>.submit();
}

function drawCalender(argOrder,argValue)
{
    document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(if_mgt_form."+argOrder+",'mm/dd/yyyy','Choose date')\">");
    document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
}
function submitExport(batchId)
{
	document.<%=formName%>.<%=IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_BATCH_ID%>.value = batchId;
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=IFReportDeliveryInterfaceKey.ACTION_EXPORT_MOBINIL_REPORT_TO_INFO %>";
	document.<%=formName%>.submit();
}
function submitExportPage()
{
	
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=IFReportDeliveryInterfaceKey.ACTION_EXPORT_JOB_DETAILS_PAGE %>";
	document.<%=formName%>.submit();
}
function submitUpdate()
{
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=IFReportDeliveryInterfaceKey.ACTION_UPDATE_JOB_DETAILS %>";
	document.<%=formName%>.submit();
}
function submitView(jobId)
{
	
	document.<%=formName%>.<%=IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID%>.value = jobId;
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=IFReportDeliveryInterfaceKey.ACTION_VIEW_JOB_DETAILS %>";
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
            <input type="hidden" name="<%=IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_BATCH_ID%>" value="-1">
            <input type="hidden" name="<%=IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID%>" value="<%=jobId %>">
            
            <input type="hidden" name="<%out.print(IFReportDeliveryInterfaceKey.First_Time_Search);%>" value="true">
            
		      <input type="hidden" name="<%out.print(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER);%>" value="<%
      if(dataHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER) ==null)
        out.print("1");
      else 
        out.print(Integer.parseInt(dataHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString()));
      %>"> 
            
            
            

            
            <%if(infoFortData!=null && infoFortData.size()!=0){
            	int jobStatusId=Integer.parseInt((String)dataHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_SELECT_JOB_STATUS));
            	if(jobStatusId!=1)
                	view="disabled";
            	else
            		view="";
            	%>
            	
            
            <table border="0" width="100%">
            <tr >
            <td align="center" colspan="5">
      <%
        if(Integer.parseInt(dataHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString())>1)
          {
      %>
              <a href='javascript:prevPage();'>Previous</a>
      <%
          }
      %>
    
      page (<%=dataHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString()%>/<%=dataHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_TOTAL_PAGES).toString()%>)
    
      <%
        if(Integer.parseInt(dataHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString())!=
            Integer.parseInt(dataHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_TOTAL_PAGES).toString())&& Integer.parseInt(dataHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString())>=1)
          {
      %>
            <a href='javascript:nextPage();'>Next</a>
      <%
          }
      %>
    </td>
    </tr>
    </table>
            
            
            
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <tr class="TableHeader">
            <td colspan="14" align="center">Job Details</td>
            </tr>
            <tr class="TableHeader">
            <td>Batch Id</td>                       
            <td>DCM Name</td>
            <td>Dial</td>              
            <td>POS Code</td>   
            <td>Sheet Serial</td>      
            <td>SIM Serial</td>
            
            <td>Info Fort Report 1st Name</td>
            <td>Info Fort Report 2nd Name</td>
            <td>Info Fort Report 3rd Name</td>
            <td>Info Fort Report 4th Name</td>
            <td>Distributer Report 1st Name</td>
            <td>Distributer Report 2nd Name</td>
            <td>Distributer Report 3rd Name</td>
            <td>Record Status</td>
            </tr>
            <%for(int i=0;i<infoFortData.size();i++){
            	InfoFortDataModel dm=(InfoFortDataModel)infoFortData.get(i);
            	//System.out.println("sim serial: "+dm.getSimSerial());
            %>
            <tr>
            <td><%=dm.getBatchId() %></td>
            <td><%=dm.getDcmName() %></td>
            <td><%=dm.getDial() %></td>
            <td><%=dm.getPosCode() %></td>
            <td><%=dm.getSheetSerial() %></td>
            <td><%=dm.getSimSerial() %></td>
            
            <td><%=dm.getInfoFort1stName() %></td>
            <td><%=dm.getInfoFort2ndName() %></td>   
            <td><%=dm.getInfoFort3rdName() %></td>
            <td><%=dm.getInfoFort4thName() %></td>
            <td><%=dm.getContract1stName() %></td>
            <td><%=dm.getContract2ndName() %></td>
            <td><%=dm.getContractlstName() %></td>
            
            <td>
            
            <select  <%=view %> name="<%=IFReportDeliveryInterfaceKey.CONTROL_SELECT_RECORD_TYPE %>+<%=dm.getSimSerial() %>" id="<%=IFReportDeliveryInterfaceKey.CONTROL_SELECT_RECORD_TYPE %>+<%=dm.getSimSerial() %>" >
            <option value="-1"></option>            
            <%for(int j=0;j<dataRecordTypes.size();j++){
            	DataRecordTypeModel tm=(DataRecordTypeModel)dataRecordTypes.get(j);
            	String typeSelected="";
            	
            	if(dm.getRecordTypeId()==tm.getTypeId()){
            		typeSelected="selected";
            		
            	}
            	else
            		typeSelected="";
            %>
            <option <%=typeSelected%> value="<%=tm.getTypeId() %>"><%=tm.getTypeName() %></option>
            <%} %>
            
            </select>
            
            </td> 
            
                    
            </tr> 
             
            <%} %>
            
            
            <tr>
            <td colspan="14" align="center">
            <input <%=view %>  class="button" type="button" name="Update" value="Update" onclick="javascript:submitUpdate()">
            
            </td>
            </tr>
            
            </TABLE>
            
            
            
            <table border="0" width="100%">
            <tr></tr>
            <tr  >
            <td align="center" colspan="5" >
     			 <%
    	    if(Integer.parseInt(dataHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString())>1)
        	  { %>
              <a href='javascript:prevPage();'>Previous</a>
      			<% }%>
    	     page (<%=dataHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString()%>/<%=dataHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_TOTAL_PAGES).toString()%>)
    		     <%if(Integer.parseInt(dataHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString())!=
            Integer.parseInt(dataHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_TOTAL_PAGES).toString())&& Integer.parseInt(dataHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString())>=1)
         		 {%>
            <a href='javascript:nextPage();'>Next</a>
      			<% }%>
    		</td>
            </tr>
            </table>
            
            
            <table border="0" width="100%">
            <tr>
            <td colspan="14" align="center">
            <input class="button" type="button" name="Export As Excel" value="Export As Excel" onclick="javascript:submitExportPage()">
            </td>
            </tr>
            </table>
            
            
            
            
            <%} else{%>
            <b>there are no errors</b>
            <%} %>
            
            
            
            
                     
       </form>
    </body>
</html>