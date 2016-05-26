



<%@page import="com.mobinil.sds.core.system.scm.dao.STKDAO"%>
<%@page import="com.mobinil.sds.core.system.scm.dao.PoiWriteExcelFile"%>

<%@ page contentType="text/html;charset=windows-1252"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"
              import="com.mobinil.sds.core.system.scm.model.*"
              %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>STK_POS_STATUS</title>
        <link href="../resources/css/Template1.css" rel="stylesheet" type="text/css" />
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type="text/javascript"></SCRIPT>
      <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
      <script type="text/javascript" src="../resources/js/jquery_tablesorter_pack.js"></script>
      <link rel="stylesheet" href="../resources/css/themes/blue/style.css" type="text/css"/>
      <script type="text/javascript" src="../resources/js/paging.js"></script>
 </head>
  <body>
    <script>
        function drawCalender(argOrder,argValue)
    {
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(SCMform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
    }
    </script>
      <%
 HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String  strUserID = request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
String generateDateFrom=(String)dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_GENERATE_DCM_TEMPLATE_FROM);
String generateDateTo=(String)dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_GENERATE_DCM_TEMPLATE_TO);
String appName = request.getContextPath();
String action=appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?";
String POSCode=(String)dataHashMap.get(SCMInterfaceKey.POS_CODE);
if(generateDateFrom==null){
    generateDateFrom="\"\"";}
if(generateDateTo==null){
   generateDateTo="\"\"";}
      %>
     <CENTER>
        <br>
        <br>
        <h2>Generate Distributer Template</h2>
        <br>
        <br>
    </CENTER>
   
  
<form name='SCMform' id='SCMform' action='<%=action%>' method='post'>
    <%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+SCMInterfaceKey.ACTIVE_STK_DISTRIBUTERS_AND_EXPORT_EXCEL+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");


%>

<input type="hidden" name="<%=SCMInterfaceKey.POS_CODE %>" value="<%=POSCode%>">
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>

    <tr class=TableTextNote>
        <td align=middle>Creation Date</td>
        <td align=middle>From</td>
        <td align=middle><Script>drawCalender('<%=SCMInterfaceKey.CONTROL_TEXT_GENERATE_DCM_TEMPLATE_FROM%>','<%=generateDateFrom%>',"*");</Script></td>
        <td align=middle>To</td>
        <td align=middle><Script>drawCalender('<%=SCMInterfaceKey.CONTROL_TEXT_GENERATE_DCM_TEMPLATE_TO%>','<%=generateDateTo%>',"*");</Script></td>
      </tr>
</TABLE>

      <center>
    <input type=button class=button value="Export to Excel"
        onclick="validateDate();">
        </center>
</form>
    </body>
    <script>
        function validateDate(){
           var fromDate=  document.SCMform.<%=SCMInterfaceKey.CONTROL_TEXT_GENERATE_DCM_TEMPLATE_FROM%>.value;
           var toDate=document.SCMform.<%=SCMInterfaceKey.CONTROL_TEXT_GENERATE_DCM_TEMPLATE_TO%>.value;

           if(fromDate==""){

               alert("From date can not be null");
               return;
           }
           if(toDate==""){
               alert("To date can not be null");
               return;
           }
           document.SCMform.submit();
       }
    </script>
</html>
