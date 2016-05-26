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
<html>
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
      
      String dcmCode=(String)dataHashMap.get(PaymentInterfaceKey.CONTROL_EDIT_TEXT_SCM_CODE);
      
       
        ExcelImport gen = new ExcelImport(userID);
        String templatePath = request.getRealPath("/cam/template/");
        
        String action=(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
        
        if(action.compareTo(MemoInterfaceKey.ACTION_EXPORT_DCM_DETAILS_EXCEL)==0){
        	gen.exportDCMDetailsExcel(templatePath+"/Dcm Details.xls",templatePath+"/generated.xls",dcmCode);
        	
        }else if(action.compareTo(MemoInterfaceKey.ACTION_EXPORT_GEN_DCM_DETAILS_EXCEL)==0){
        	gen.exportGenDCMDetailsExcel(templatePath+"/Gen Dcm Details.xls",templatePath+"/generated.xls",dcmCode);
        	
        }else if(action.compareTo(MemoInterfaceKey.ACTION_EXPORT_ALL_DCM_DETAILS_EXCEL)==0){
        	gen.exportDCMDetailsListExcel(templatePath+"/Dcm Details List.xls",templatePath+"/generated.xls");
        }
        
        
        //java.io.File f = new java.io.File("def.xls");
        // out.println(f.exists());
        out.println("<form action=\""+appName+"/cam/template/generated.xls\" name=\"GenerateSheet\" method=\"post\">");
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