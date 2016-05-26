
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="java.io.*"
         import="javax.servlet.jsp.*"
        import="com.mobinil.sds.web.interfaces.*"
        import = " com.mobinil.sds.core.system.*"
        import = "org.apache.poi.hssf.usermodel.HSSFCell"
       import = "org.apache.poi.hssf.usermodel.HSSFCellStyle"
import=" org.apache.poi.hssf.usermodel.HSSFRow"
import ="org.apache.poi.hssf.usermodel.HSSFSheet"
import=" org.apache.poi.hssf.usermodel.HSSFWorkbook"
import = "com.mobinil.sds.core.system.dataMigration.model.RevenueReportModel"
import=" org.apache.poi.hssf.util.CellReference"
import=" org.apache.poi.poifs.filesystem.POIFSFileSystem"
         
        
       
       
%>
<%
 String appName = request.getContextPath();
  HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  HashMap revenueReport = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  %>
  <html>  
 <head>
    <meta http-equiv="Content-Type" content="APPLICATION/EXCL; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    <TITLE>:::: SDS ::::</TITLE>
  </head>
  <body>
<%
//String baseDirectory =request.getContextPath();
String baseDirectory = new java.io.File("").getAbsolutePath()+"\\tmp.xls";
FileOutputStream fileOut = new FileOutputStream(baseDirectory);
String categoryName = "";
System.out.println("The path issssssssssss " + baseDirectory);
   HSSFWorkbook wb = new HSSFWorkbook();
   //Set keys = revenueReport.keySet();
	Iterator i = revenueReport.keySet().iterator();
	System.out.println("hash map  issssssssss"+revenueReport);
	 while(i.hasNext())
       {
		 
       //Map.Entry me = (Map.Entry)i.next();
       categoryName = (String)(i.next());
       System.out.println("Category Name issssssss " + categoryName);	  
       HSSFSheet sheet1 = wb.createSheet(categoryName);
       //i = revenueReport.entrySet().iterator();

       //while( i. hasNext() ){

       //System.out.println( iterator.next() );
     Vector revenueData=  (Vector) revenueReport.get(categoryName);
     
       
       //Vector revenueData =(Vector)i.next();
       System.out.println("the vector size issssssss " + revenueData.size());
       for(int j=0;j<revenueData.size();j++)
       {
       	HSSFRow row = sheet1.createRow((short)j);
       	RevenueReportModel revenueReportModel = (RevenueReportModel)revenueData.get(j);
   		int count = 0;
       	
   		HSSFCell cell = row.createCell((short)count);
       	String productName  = revenueReportModel.getProductName();
       	cell.setCellValue( productName);
       	count++;
       	
       	cell = row.createCell((short)count);
       	String uniquePositive  = revenueReportModel.getUniquePositive();
       	cell.setCellValue( uniquePositive);
       	count++;
       	
       	
       	String rpu1 = revenueReportModel.getRpuU1();
       	HSSFCell cell2 = row.createCell((short)count);
       	cell2.setCellValue( rpu1);
       	count++;
       	
       	String countOfPositiveU1 = revenueReportModel.getCountOfPositiveU1();
       	HSSFCell cell3 = row.createCell((short)count);
       	cell3.setCellValue( countOfPositiveU1);
       	count++;
       	
       	String countOfNegativeU1 = revenueReportModel.getCountOfNegativeU1();
       	HSSFCell cell4 = row.createCell((short)count);
       	cell4.setCellValue( countOfNegativeU1);
       	count++;
       	
       	String rpu2 = revenueReportModel.getRpuU2();
       	HSSFCell cell5 = row.createCell((short)count);
       	cell5.setCellValue( rpu2);
       	count++;
       	String countOfPositiveU2 = revenueReportModel.getCountOfPositiveU2();
       	HSSFCell cell6 = row.createCell((short)count);
       	cell6.setCellValue( countOfPositiveU2);
       	count++;
       	String countOfNegativeU2 = revenueReportModel.getCountOfNegativeU2();
       	HSSFCell cell7 = row.createCell((short)count);
       	cell7.setCellValue( countOfNegativeU2);
       	count++;
       	
       	String rpu3 = revenueReportModel.getRpuU3();
       	HSSFCell cell8 = row.createCell((short)count);
       	cell8.setCellValue( rpu3);
       	count++;
       	String countOfPositiveU3 = revenueReportModel.getCountOfPositiveU3();
       	HSSFCell cell9 = row.createCell((short)count);
       	cell9.setCellValue( countOfPositiveU3);
       	count++;
       	String countOfNegativeU3 = revenueReportModel.getCountOfNegativeU3();
       	HSSFCell cell10 = row.createCell((short)count);
       	cell10.setCellValue( countOfNegativeU3);
       	count++;
       	
       	String rpu4 = revenueReportModel.getRpuU4();
       	HSSFCell cell11 = row.createCell((short)count);
       	cell11.setCellValue( rpu4);
       	count++;
       	String countOfPositiveU4 = revenueReportModel.getCountOfPositiveU4();
       	HSSFCell cell12 = row.createCell((short)count);
       	cell12.setCellValue( countOfPositiveU4);
       	count++;
       	String countOfNegativeU4 = revenueReportModel.getCountOfNegativeU4();
       	HSSFCell cell13 = row.createCell((short)count);
       	cell13.setCellValue( countOfNegativeU4);
       	count++;
       	
       	count++;
       	String uniqePositive = revenueReportModel.getUniquePositive();
       	HSSFCell cell14 = row.createCell((short)count);
       	cell14.setCellValue( uniqePositive);
       	
       	//System.out.println("countOfPositiveU1 isssssssssssss " + countOfPositiveU1);
       }
       }
       //}
	 
	  
     
	 
     wb.write(fileOut);
     
     fileOut.close();
    
  
     
     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value="+userID+">");

        

        out.println("</form>");
       
     
    
%>
</body>
</html>