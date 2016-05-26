<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
                         import="com.mobinil.sds.core.system.sip.model.*"
                
                import = " com.mobinil.sds.web.interfaces.cam.*"
                import= "org.apache.poi.xssf.usermodel.*"
				import="org.apache.poi.ss.util.CellRangeAddress"
				import="org.apache.poi.ss.usermodel.*"
				import ="org.apache.poi.hssf.usermodel.HSSFWorkbook"
				import="org.apache.poi.hssf.util.HSSFColor"
				
				import="java.util.ArrayList"
				import ="java.util.Date"
				import ="java.util.Iterator"
				import ="java.util.LinkedHashMap"
				import ="java.util.Map"
				import ="java.util.HashMap"
				import ="java.io.File"
				import ="java.io.FileOutputStream"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	              	     	
		<%
		HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
		
		String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		String savedReportId = (String)request.getParameter(SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_ID);
		  String sipId=(String)request.getParameter(SIPInterfaceKey.INPUT_HIDDEN_SIP_ID);
		  Vector itemVec=(Vector) objDataHashMap.get(SIPInterfaceKey.VECTOR_SIP_ITEM_AMOUNT);
		  System.out.println("dsffffffffffffffffffffffffff");
		  String appName = request.getContextPath();

		  String path = request.getRealPath("/sip/download/");  
		  path = path+"\\";


		  Workbook wb;
			
	        wb = new HSSFWorkbook();
	        
		
		Sheet sheet2=  wb.createSheet("template");
		 
		PrintSetup printSetup = sheet2.getPrintSetup();
        printSetup.setLandscape(true);
        sheet2.setFitToPage(true);
        sheet2.setHorizontallyCenter(true);
        Row headerRow2 = null;
        Cell headerCell2;
        headerRow2=sheet2.createRow(0);
        headerCell2 = headerRow2.createCell(0);
        headerCell2.setCellValue("DCM ID");
        headerCell2 = headerRow2.createCell(1);
        headerCell2.setCellValue("Item Name");
        headerCell2 = headerRow2.createCell(2);
        headerCell2.setCellValue("Item Amount");
        headerCell2 = headerRow2.createCell(3);
        headerCell2.setCellValue("Status");
        
        for(int i=0;i<itemVec.size();i++)
        {
        	  String dcm_id="";
        	  String item_Name="";
        	  String item_amount="";
        	  String status="";
        	  
        	 sipReportItemModel sipReportModel=(sipReportItemModel) itemVec.get(i);
        	  dcm_id=sipReportModel.getDCM_ID();
        	  item_Name=sipReportModel.getSIP_REPORT_ITEM_NAME();
        	  item_amount=sipReportModel.getSIP_REPORT_ITEM_AMOUNT();
        	  status=sipReportModel.getINCLUDE();
        	 
        	 if(status.equals("0"))
        	 {
        		 
        		 
        		 status="Excluded" ;
        		 
        	 }
        	 if(status.equals("1"))
        	 {
        		 
        		 
        		 status="Included" ;
        		 
        	 }	
             headerRow2=sheet2.createRow(i+1);
             
             headerCell2 = headerRow2.createCell(0);
             headerCell2.setCellValue(dcm_id);
             headerCell2 = headerRow2.createCell(1);
             headerCell2.setCellValue(item_Name);
             headerCell2 = headerRow2.createCell(2);
             headerCell2.setCellValue(item_amount);
             headerCell2 = headerRow2.createCell(3);
             headerCell2.setCellValue(status);
        	 
        	
         }
        Date date = new Date();
        
        StringBuffer filePath = new StringBuffer("");    
        filePath.append(path);     
          filePath.append("template");
         // filePath.append(quartar);
          filePath.append("_");
          filePath.append((date.getYear ( )+1900));
          filePath.append("-");
          filePath.append((date.getMonth ( )+1));
          filePath.append("-");
          filePath.append(date.getHours ( ));
          filePath.append("-");
          filePath.append(date.getMinutes());
          filePath.append("-");
          filePath.append(date.getSeconds());
          filePath.append(".xls");
	    
	    File excelDistFile = new File(filePath.toString()); 

//	    if(wb instanceof XSSFWorkbook) file += "x";
	    FileOutputStream output = new FileOutputStream(excelDistFile);
	    wb.write(output);
	    output.close();
		String returnedFileName = "\\"+filePath.toString();
		String fileNameFromPath =returnedFileName.substring((returnedFileName.lastIndexOf(System.getProperty("file.separator"))+1),returnedFileName.length())  ;
		objDataHashMap.put(InterfaceKey.EXPORT_FILE_PATH,fileNameFromPath);
		objDataHashMap.put(InterfaceKey.MODULE_SUB_PATH,"/sip/download/");
		
	//	objDataHashMap.put(SIPInterfaceKey.CONTROL_INCOMING_ACITON, SIPInterfaceKey.ACTION_S_SIP);
		ArrayList temp_text = new ArrayList();
		temp_text.add("Press Save To Export File.");
		objDataHashMap.put(SIPInterfaceKey.VECTOR_MEMO_REPORT_ERROR_MSG_TOBEVIEWED, temp_text);
		session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT ,objDataHashMap);
 
	    out.println("<form action=\"/SDS/servlet/com.mobinil.sds.web.controller.UploadingFileServlet\" name=\"GenerateSheet\" method=\"post\">");
	    //out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value="+userID+">");
	    out.println("</form>");
	    out.println("<script type=\"text/javascript\">");
	    out.println("GenerateSheet.submit();");
	    out.println("</script>");  
		%>
		
</body>
</html>