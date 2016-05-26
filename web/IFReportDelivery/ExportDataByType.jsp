<%@ page import="com.mobinil.sds.core.system.ifReportDelivery.excelImport.*"
                import="java.util.*"
                import="com.mobinil.sds.web.interfaces.*"
                
%>

<%
  String appName = request.getContextPath();
  HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
%>

<%@page import="com.mobinil.sds.web.interfaces.IFReportDelivery.IFReportDeliveryInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.ifReportDelivery.dao.IFReportDeliveyDao"%><html>
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
      
      String jobId=(String)dataHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_JOB_ID);
      String templatePath = request.getRealPath("/IFReportDelivery/template/");
      
      String action=(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
      
      if(action.equals(IFReportDeliveryInterfaceKey.ACTION_EXPORT_DIST_ERRORS)){
    	  System.out.println("export dist errors");
    	  IFReportDeliveyDao.exportDataByType(templatePath+"/data by type.xls",templatePath+"/Dist Errors",jobId,"5");
    	  out.println("<form action=\""+appName+"/IFReportDelivery/template/"+"Dist Errors"+".xls\" name=\"GenerateSheet\" method=\"post\">");
    	  
      }else if(action.equals(IFReportDeliveryInterfaceKey.ACTION_EXPORT_INFO_FORT_ERRORS)){
    	  IFReportDeliveyDao.exportDataByType(templatePath+"/data by type.xls",templatePath+"/Info Fort Errors",jobId,"6");
    	  System.out.println("export info fort errors");
    	  out.println("<form action=\""+appName+"/IFReportDelivery/template/"+"Info Fort Errors"+".xls\" name=\"GenerateSheet\" method=\"post\">");
      }else if(action.equals(IFReportDeliveryInterfaceKey.ACTION_EXPORT_NOT_FOUND)){
    	  IFReportDeliveyDao.exportDataByType(templatePath+"/data by type.xls",templatePath+"/Not Found",jobId,"3");
    	  System.out.println("export  not found ");
    	  out.println("<form action=\""+appName+"/IFReportDelivery/template/"+"Not Found"+".xls\" name=\"GenerateSheet\" method=\"post\">");
      }else if(action.equals(IFReportDeliveryInterfaceKey.ACTION_EXPORT_DUPLICATES)){
    	  IFReportDeliveyDao.exportDuplicates(templatePath+"/data by type.xls",templatePath+"/Duplicates",jobId);
    	  System.out.println("export  duplicates ");
    	  out.println("<form action=\""+appName+"/IFReportDelivery/template/"+"Duplicates"+".xls\" name=\"GenerateSheet\" method=\"post\">");
      }else if(action.equals(IFReportDeliveryInterfaceKey.ACTION_EXPORT_AUTO_MATCHED)){
    	  IFReportDeliveyDao.exportDataByType(templatePath+"/data by type.xls",templatePath+"/Matched",jobId,"2");
    	  System.out.println("export  duplicates ");
    	  out.println("<form action=\""+appName+"/IFReportDelivery/template/"+"Matched"+".xls\" name=\"GenerateSheet\" method=\"post\">");
      }
      
       
        
        
      
        
        
       // String dcmName=IFReportDeliveyDao.exportMobinilToInfoReport(templatePath+"/Mobinil To Info fort template.xls",templatePath,Integer.parseInt(batchId));
        
        
        
        
        //java.io.File f = new java.io.File("def.xls");
        // out.println(f.exists());
       
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