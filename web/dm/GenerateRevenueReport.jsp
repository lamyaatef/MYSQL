<%@ page import="com.mobinil.sds.core.system.cam.excelImport.*"
                import="java.util.*"
                import="com.mobinil.sds.web.interfaces.*"
                import=" com.mobinil.sds.web.interfaces.cam.*"
%>

<%
  String appName = request.getContextPath();
  HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
%>

<%@page import="com.mobinil.sds.core.system.spreport.RevenueReportEngine"%>
<%@page import="com.mobinil.sds.web.interfaces.dm.DMInterfaceKey"%><html>
  <head>
    <meta http-equiv="Content-Type" content="APPLICATION/EXCL; charset=windows-1256">
    <LINK REL="STYLESHEET" TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language="JavaScript" src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    <TITLE>:::: SDS ::::</TITLE>
  </head>
  <body>
    <center>
      <%   
       
      //String payment_id=(String)dataHashMap.get(PaymentInterfaceKey.CONTROL_PAYMENT_ID);
      //String fromFrozenPayment=(String)dataHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_FROZEN_STATUS_ID);
      
           
       
       // ExcelImport gen = new ExcelImport(userID);
        String templatePath = request.getRealPath("/dm/uploadedZip/generated.xls");
        
        String action=(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
        String sDate=(String)dataHashMap.get(DMInterfaceKey.GENERAL_REVENUE_REPORT_START_DATE);
        String eDate=(String)dataHashMap.get(DMInterfaceKey.GENERAL_REVENUE_REPORT_END_DATE);
        
        
        RevenueReportEngine re=new RevenueReportEngine();
        re.exportFile(templatePath,sDate,eDate );
        
        //java.io.File f = new java.io.File("def.xls");
        // out.println(f.exists());
        out.println("<form action=\""+appName+"/dm/uploadedZip/generated.xls\" name=\"GenerateSheet\" method=\"post\">");
        out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value="+userID+">");

        /*String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");
*/
        out.println("</form>");
        out.println("<script type=\"text/javascript\">");
        out.println("GenerateSheet.submit();");
        out.println("</script>");
        //response.sendRedirect(""+appName+"/cr/Template/generated.xls");
  
      %>
    </center>
  </body>
</html>