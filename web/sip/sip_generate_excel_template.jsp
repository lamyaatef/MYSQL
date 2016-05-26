<%@ page 
                import="java.util.*"
                import="java.io.*"
                import="com.mobinil.sds.web.interfaces.*"
                import="com.mobinil.sds.web.interfaces.sip.*"
%>

<%
  String appName = request.getContextPath();
  HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    <TITLE>:::: SDS ::::</TITLE>
  </head>
  <body>
    <center>
    <br><br>
        
          <input type="button" class="button" value=" Back " onclick="history.go(-1);">
        
      <%   
       
        
        
      String templatePath = request.getRealPath("/sip/template/");
      String reportFlag =  request.getParameter(SIPInterfaceKey.HIDDEN_PARAM_NAME_SIP_REPORT);
      int type=Integer.parseInt (reportFlag);
      String excelFileName="";
      if (type==SIPInterfaceKey.HIDDEN_PARAM_SIP_REPORT_DCM)
      {
         excelFileName = "dist_exe_template.xls";
      
      }
   
   if (type==SIPInterfaceKey.HIDDEN_PARAM_SIP_REPORT_POS)
      {
      excelFileName = "pos_field_template.xls";
      
      }
   
   if (type==SIPInterfaceKey.HIDDEN_PARAM_SIP_REPORT_REGION)
      {
      excelFileName = "sales_super_template.xls";
      
      }
   
   if (type==SIPInterfaceKey.HIDDEN_PARAM_SIP_REPORT_SD)
      {
      excelFileName = "ex_sd_template.xls";
      
      }
   if (type==SIPInterfaceKey.HIDDEN_PARAM_SIP_REPORT_SUPERVISOR)
      {
      excelFileName = "field_super_template.xls";
      
      }
   if (type==SIPInterfaceKey.HIDDEN_PARAM_SIP_REPORT_EXPOS)
   {
   excelFileName = "dcm_expos_template.xls";
   
   }
   if (type==SIPInterfaceKey.HIDDEN_PARAM_SIP_REPORT_EMP)
   {
   excelFileName = "emp_user_template.xls";
   
   }
   else 
	   if (type==11) //dist (expost , non expos)
	   {
	   excelFileName = "dcm_expos_template.xls";
	   
	   }   
   //excelFileName = "\\"+excelFileName;
   //HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
   dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH,excelFileName);
	dataHashMap.put(InterfaceKey.MODULE_SUB_PATH,"/sip/template/");
	session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT ,dataHashMap);
	
   //String excelFilePath = appName+"/sip/template/"+excelFileName;
   
   
   out.println("<form action=\"/SDS/servlet/com.mobinil.sds.web.controller.UploadingFileServlet\" name=\"GenerateSheet\" method=\"post\">");
   out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value="+userID+">");
   out.println("</form>");
   out.println("<script type=\"text/javascript\">");
   out.println("GenerateSheet.submit();");
   out.println("</script>");
   
   
   
   
   
      
    
     
  
      %>
       
    </center>
  </body>
</html>