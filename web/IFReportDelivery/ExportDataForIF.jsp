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
        String pageHeader="Mobinil Report To Info Fort";
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String userID =(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String action=(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
        Vector batchTypes=(Vector)dataHashMap.get(IFReportDeliveryInterfaceKey.VECTOR_BATCH_TYPES);
                
%>
<script language="javaScript">
function submitSearch()
{
	if(document.<%=formName%>.<%=IFReportDeliveryInterfaceKey.CONTROL_BATCH_ID%>.value == '' && document.<%=formName%>.<%=IFReportDeliveryInterfaceKey.CONTROL_BATCH_DATE%>.value == 'Batch_Date' && 
			document.<%=formName%>.<%=IFReportDeliveryInterfaceKey.CONTROL_BATCH_TYPE %>.value == '' &&
			document.<%=formName%>.<%=IFReportDeliveryInterfaceKey.CONTROL_DCM_NAME %>.value == '')
		{
		   alert("you have to select search criteria");
		   return;
		}
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

            
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <tr class="TableHeader">
            <td colspan="4" align="center">Search</td>
            </tr>
            <tr>
            <td>Batch ID</td>
            <td><input type="text" name="<%=IFReportDeliveryInterfaceKey.CONTROL_BATCH_ID%>" id="<%=IFReportDeliveryInterfaceKey.CONTROL_BATCH_ID%>">  </td>
            
            <td>Batch Date</td>
            <td>
            <script>
                  drawCalender('<%=IFReportDeliveryInterfaceKey.CONTROL_BATCH_DATE%>','Batch_Date');
            </script>
            </td>
            
            </tr>
            <tr>            
            <td>Batch Type </td>
            <td>
            <select name="<%=IFReportDeliveryInterfaceKey.CONTROL_BATCH_TYPE %>" id="<%=IFReportDeliveryInterfaceKey.CONTROL_BATCH_TYPE %>" >
            <option value=""></option>
            <%for(int i=0;i<batchTypes.size();i++){
            	BatchTypeModel bType =(BatchTypeModel)batchTypes.get(i);
            	%>
            <option value="<%=bType.getBatchTypeId() %>"><%=bType.getBatchTypeName() %></option>
            <%} %>
            </select>
            </td>
            <td>DCM Name</td>
            <td><input type="text" name="<%=IFReportDeliveryInterfaceKey.CONTROL_DCM_NAME %>" id="<%=IFReportDeliveryInterfaceKey.CONTROL_DCM_NAME %>" ></td>
              </tr>  
            </TABLE>
            
            <br>
            <table  cellspacing="2" cellpadding="1" border="0" width="100%">
                <tr>
                    <td align="center">
                        <input class="button" type="button" name="search" value="Search"
                               onclick="javascript:submitSearch()">
                    </td>
                    
                     
                </tr>
            </table>
            
            <%if(action.compareTo(IFReportDeliveryInterfaceKey.ACTION_SEARCH_EXPORT_MOBINIL_REPORT_TO_INFO)==0){
            	Vector batches=(Vector)dataHashMap.get(IFReportDeliveryInterfaceKey.VECTOR_SEARCH_BATCHES);
            %>
            <%if(batches!=null&& batches.size()!=0){ %>
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="100%" border="1">
            <tr class="TableHeader">
            <td>Batch Id</td>
            <td>Batch Date</td>
            <td>Batch Type </td>
            <td>DCM Name</td>
            <td>DCM Code</td>     
            <td>Export</td>       
            </tr>
            <%for(int i=0;i<batches.size();i++){ 
            	BatchModel bm=(BatchModel) batches.get(i);
            %>
            <tr>
            <td><%=bm.getBatchId() %></td>
            <td><%=bm.getBatchDate() %></td>
            <td><%=bm.getBatchType() %></td>
            <td><%=bm.getDcmName() %></td>
            <td><%=bm.getDcmCode()%></td>
            <td ><input class="button" type="button" name="Export" value="Export" onclick="javascript:submitExport(<%=bm.getBatchId() %>)"></td>
            
            </tr>
            <%} %>
            </TABLE>
            <%} %>
            <%} %>
            
            
            
            
                     
       </form>
    </body>
</html>