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
       String dcmName=(String)dataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE);
       String dcmCode=(String)dataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME);
       String statusId=(String)dataHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID);
       String pageNum=(String)dataHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER);
       String channelId=(String)dataHashMap.get(PaymentInterfaceKey.CONTROL_CHANNEL_ID);
       ExcelImport gen = new ExcelImport(userID);
       String templatePath = request.getRealPath("/cam/template/");
       
       gen.exportPaymentExcel(templatePath+"/DCM'S Payment Status.xls",templatePath+"/generated.xls",dcmCode,dcmName,statusId,Integer.parseInt(pageNum),Integer.parseInt(channelId)) ;
       //if(Integer.parseInt(channel_id)==-1)
      // {
    ///	   gen.exportPaymentExcel(templatePath+"/Template.xls",templatePath+"/generated.xls",dcm_code,dcm_name,status_id,Integer.parseInt(pageNum),Integer.parseInt(channel_id)) ;   
      // }else if(Integer.parseInt(channel_id)==1)
      // {
    //	   gen.exportPaymentExcel(templatePath+"/Template.xls",templatePath+"/generated.xls",dcm_code,dcm_name,status_id,Integer.parseInt(pageNum),Integer.parseInt(channel_id)) ;
     ///  }else if(Integer.parseInt(channel_id)==2)
      // {
    	//   gen.exportPaymentExcel(templatePath+"/Template.xls",templatePath+"/generated.xls",dcm_code,dcm_name,status_id,Integer.parseInt(pageNum),Integer.parseInt(channel_id)) ;
      // }else if(Integer.parseInt(channel_id)==3)
      // {
   // 	   gen.exportPaymentExcel(templatePath+"/Template.xls",templatePath+"/generated.xls",dcm_code,dcm_name,status_id,Integer.parseInt(pageNum),Integer.parseInt(channel_id)) ;
     //  }
        
        
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