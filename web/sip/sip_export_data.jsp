
<%@page import="com.mobinil.sds.web.interfaces.cam.MemoInterfaceKey"%> 
<%@ page        import="java.util.*"
				import="java.sql.*"
                import="com.mobinil.sds.web.interfaces.*"
                import="com.mobinil.sds.core.utilities.*"
                import="com.mobinil.sds.web.interfaces.sip.*"
                import="java.io.*"
                import="com.mobinil.sds.core.system.sip.dao.*"
                import="com.mobinil.sds.core.utilities.POSReport"
                import="com.mobinil.sds.core.utilities.DistributorsSIPReport"
                import="com.mobinil.sds.core.utilities.QualityTeamSIPReport"
                import="com.mobinil.sds.core.utilities.SuperdealersReport"
                import = " com.mobinil.sds.web.interfaces.cam.*"
                import = " com.mobinil.sds.core.system.sip.exportEngine.*"
                
%>

<%
  String appName = request.getContextPath();
//  System.out.println("appname issssssssss "+appName);
  HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String userID = (String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
  String reportTypeId = (String)request.getParameter(SIPInterfaceKey.CONTROL_HIDDEN_REPORT_TYPE_ID);
  String reportId = (String)request.getParameter(SIPInterfaceKey.INPUT_HIDDEN_SIP_ID);
  
  String yearId = (String)request.getParameter(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_YEAR_ID);
  String quartarId = (String)request.getParameter(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_QUARTAR_ID);
  String labelId = (String)request.getParameter(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_LABEL_ID);
  
  System.out.println("SIP Export Data");
  System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
  System.out.println("reportTypeId is "+reportTypeId);
  System.out.println("reportId is "+reportId);

  String templatePath = request.getRealPath("/sip/download/");  
  String fileSeparator = System.getProperty("file.separator");
  templatePath = templatePath+fileSeparator;
  
  String returnedFileName="";
  if(Integer.parseInt(reportTypeId)==3)
	//	System.out.println("000000000000000000000");
  {
    
	 		
	 		
	  returnedFileName=DistributorsSIPReport.mainExportDistributorsSIPReport(templatePath,"Q2",exportEngine.getDistributorsExcelReport(reportId)); //distributor=new DistributorsSIPReport();
	  
	  
	  
  }
  else if(Integer.parseInt(reportTypeId)==4)
  		{//System.out.println("111111111111111111111");
  			
  			returnedFileName=QualityTeamSIPReport.mainExportQualityTeamSIPReport(templatePath,"Q2",exportEngine.getQualityExcelReport(reportId,labelId,quartarId,yearId));
  		}
  				
  else if(Integer.parseInt(reportTypeId)==1)
  {

		returnedFileName=SuperdealersReport.mainExportSuperdealersReport(templatePath,"Q2",exportEngine.getSDExcelReport(reportId,labelId,quartarId,yearId));
  }	
  else if(Integer.parseInt(reportTypeId)==2)
  {
	  returnedFileName=POSReport.mainExportPOSReport(templatePath,"Q2");
  }
		
  else if(Integer.parseInt(reportTypeId)==100)
  {  
     returnedFileName=DistributorsSIPReport.mainExportDistributorsSIPReportCSV(templatePath,reportId,userID,"Q2"); //distributor=new DistributorsSIPReport();
  }
  
  

		System.out.println(returnedFileName); 
		returnedFileName = fileSeparator+returnedFileName;
		String fileNameFromPath =returnedFileName.substring((returnedFileName.lastIndexOf(System.getProperty("file.separator"))+1),returnedFileName.length())  ;
		dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH,fileNameFromPath);
		dataHashMap.put(InterfaceKey.MODULE_SUB_PATH,"/sip/download/");
		
		dataHashMap.put(SIPInterfaceKey.CONTROL_INCOMING_ACITON, SIPInterfaceKey.ACTION_SEARCH_SIP);
		
		ArrayList temp_text = new ArrayList();
		temp_text.add("Press Save To Export File.");
		dataHashMap.put(SIPInterfaceKey.VECTOR_MEMO_REPORT_ERROR_MSG_TOBEVIEWED, temp_text);
		
		session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT ,dataHashMap);
		
 // response.addHeader("Content-Disposition", "attachment; filename="+fileNameFromPath);
 //String url = request.getContextPath()+"/cam/ExportFileDispatcher.jsp";
  
  out.println("<form action=\"/SDS/servlet/com.mobinil.sds.web.controller.UploadingFileServlet\" name=\"GenerateSheet\" method=\"post\">");
  //out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value="+userID+">");
  out.println("</form>");
  out.println("<script type=\"text/javascript\">");
  out.println("GenerateSheet.submit();");
  out.println("</script>");  
 %>