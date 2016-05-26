<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="com.mobinil.sds.web.interfaces.cam.*"
         import=" com.mobinil.sds.core.system.cam.accrual.model.*"
         import ="com.mobinil.sds.core.system.cam.accrual.dao.*"
         import ="com.mobinil.sds.web.interfaces.InterfaceKey"
         import ="com.mobinil.sds.core.system.cam.payment.model.*"
         import="com.mobinil.sds.core.system.cam.memo.model.*"  
         
         
         %>
<%
        String appName = request.getContextPath();
		String realPath = request.getRealPath("/dm/uploadedZip/");
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String formName = "memo_mgt_form";
        String pageHeader="General Revenue Report";
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String action=  (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
        
%>
<script language="JavaScript">

function submitGenerate()
{	

document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=DMInterfaceKey.GENERATE_REVENUE_REPORT%>";
document.<%=formName%>.submit();

}


function drawCalender(argOrder,argValue)
{
    document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(memo_mgt_form."+argOrder+",'mm/dd/yyyy','Choose date')\">");
    document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
}
</script>

<%@page import="com.mobinil.sds.web.interfaces.dm.DMInterfaceKey"%><html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL="STYLESHEET" TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        <SCRIPT language="JavaScript" src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/calendar.js"></SCRIPT>
        </head>
    <body>
        <center><h2> <%=pageHeader%></h2></center>

        
        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
            
            
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableHeader>
              <td colspan="4" align="center"><b>Generate</b></td>
              </TR>
              
              <tr>
              <td>
              Start Date
              </td>
              <td>
              	<script>
                  drawCalender('<%=DMInterfaceKey.GENERAL_REVENUE_REPORT_START_DATE%>','Start_Date');
               	</script>
              </td>
              <td>
              End Date
              </td>
              <td>
              	<script>
                  drawCalender('<%=DMInterfaceKey.GENERAL_REVENUE_REPORT_END_DATE%>','End_Date');
               	</script>
              </td>
              </tr>
              
              <tr>
              <td align=center colspan="4">
                        <input class=button type="button" name="Generate" value="Generate"
                               onclick="javascript:submitGenerate()">
                    </td>
              </tr>
              </TABLE>
            

             </form>
    </body>
</html>