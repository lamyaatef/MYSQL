package com.mobinil.sds.core.utilities;

import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.mobinil.sds.core.system.sip.dao.SipDAO;
import com.mobinil.sds.core.system.sip.dto.sipDistHistoryCSVDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;
import java.io.File;
import java.io.FileOutputStream;


public class DistributorsSIPReport 
{
	private static final String[] titles2={
		"Revenue Target","Gross Adds CIF","Dormant","Line Quality","POS Split","Gross Adds (Act)"}
		
		
	;
	 private static final String[] titles = {
         "July",	"August", "September", "Q3", "July", "August", "September", "Q2 2009 NI", "Total Gross Ads",
         "April", "May", "June","Total Dormant","CIF Gross Adds-Q2 Dormant","July","August","September","Q3","July",
         "August","September","Total Gross Adds"
 };
	 private static final String[] columns={
		"C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
		"AA","AB","AC","AD","AE","AF","AG","AH","AI","AJ" 
		 
		 
	 };

	 private static final String[] columns2={
		"C","D","E","F","G","H","I","J","K","L","M","N","O"
		 
		 
	 };
	 private static final String[] titles3 = {"Exectives","DCM Name","Scratch","E-Topup"
        ,"Scratch", "E-Topup","Scratch","E-Topup", "Total Scratch", "Total E-Topup","Total Scratch","%",
         "", "", "","","","","","","","","ExPos","Non ExPos","ExPos","Non ExPos","ExPos","Non ExPos","ExPos","Non ExPos","Total POS"
         ,"%","","","",""
 };
	 private static final String[] titles4={
		 "Exectives","DCM Name","Scratch","E-Topup","Total Revenue","% of Etopup",
		 "Total Gross Adds","Total Dormant","% of Dormant","CIF Gross Adds - Q2 Dormant",
		 "Total Gross Adds","ExPos","Non ExPos","Total POS","%"
		 
		 
		 
		 
	 };
	 public static  String mainExportDistributorsSIPReportCSV(String path,String report_id, String userId, String quartar) throws Exception 
	    {
	    
	     String sqlToGetReportCategory = "select sip_report_type_category_id  from sip_Report_Detail where sip_report_detail.SIP_REPORT_DETAIL_ID = " +report_id;
	     
	     String reportCategory = DBUtil.executeQuerySingleValueString(sqlToGetReportCategory, "sip_report_type_category_id");
	     
	     
	     System.out.println("reportCategory="+ reportCategory);
	     
	     Vector<sipDistHistoryCSVDTO> vecCSV = new Vector<sipDistHistoryCSVDTO>();

	  //  if (reportCategory.compareTo("3")==0)
	    {
	        
	        
	   
	   
	   String sqlToExportDist = "SELECT QUERY FROM SIP_REPORT_QUERY WHERE QUERY_TYPE_ID = 7 and REPORT_TYPE_CATEGORY_ID  ="+ reportCategory;
	   System.out.println("@@@@@@@@@@@@@@@@@22");
	   System.out.println(sqlToExportDist);
	   
       String query = DBUtil.executeQuerySingleValueString(sqlToExportDist, "QUERY");
       
	    String sql =  "";//DBUtil.executeQuerySingleValueString(query, "query");
	  //  System.out.println("#####################");
	  //  System.out.println(query);
	  //  System.out.println("#####################");
	    sql = query.replaceAll("\\?", report_id);
	   // System.out.println(sql);	    
	  //  System.out.println("#####################");
	    SipDAO.fillDistVector ( report_id  , sql ,vecCSV );
	    
	    }
	    
	    String empId = "";
        String ChannelName = "";
        String modeName = "";
        String lineName = "";
        String kpiName = "";
        String kpiValue = "";
        String transactionDate = null;
        
        Date date = new Date();
        
        StringBuffer filePath = new StringBuffer("");    
        filePath.append(path);                  
          filePath.append("Distributors_CSV");
          filePath.append(quartar);
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
          filePath.append(".CSV");
          
File excelDistCSVFile = new File(filePath.toString());  
FileOutputStream out = new FileOutputStream(excelDistCSVFile);
        
        
	    for (int k = 0; k < vecCSV.size(); k++) 
        {
	       sipDistHistoryCSVDTO sddto = (sipDistHistoryCSVDTO) vecCSV.elementAt(k);
	       
            if (k == 0) {
                String temp = "Channel Name,Emp Id,Mode of Sales,Line of Business,KPI Metric,KPI Metric Value,Transaction Date \n";
                out.write ( temp.getBytes ( ) );
                out.flush ( );
            }
             empId = "";
             ChannelName = "";
             modeName = "";
             lineName = "";
             kpiName = "";
             kpiValue = "";
             transactionDate = null;
             
            if (sddto.getEmp_id ( ) != null) empId = sddto.getEmp_id();          
            if (sddto.getSip_report_channel_name ( ) != null) ChannelName = sddto.getSip_report_channel_name ( );
            if (sddto.getMode_name ( )!= null) modeName = sddto.getMode_name();
            if (sddto.getLine_name ( ) != null) lineName = sddto.getLine_name();
            if (sddto.getKpi_name ( ) != null) kpiName= sddto.getKpi_name();
            if (sddto.getKpi_value ( ) != null) kpiValue = sddto.getKpi_value();
            if (sddto.getTransaction_date ( ) != null) transactionDate = sddto.getTransaction_date();
            String temp = ChannelName+ "," 
                    + empId + ","                    
                    +modeName+ ","
                    +lineName+ ","
                    +kpiName+ ","
                    +kpiValue+ ","
                    +transactionDate
                    + "\r\n";          
            out.write ( temp.getBytes ( ) );
            out.flush ( );
        }
	    
	out.close();

	return filePath.toString();
	    }
	public static  String mainExportDistributorsSIPReport(String path,String quartar,HashMap<String,LinkedHashMap<String,ArrayList<String> >> disttLinkedHashMapes) throws Exception 
	{
		 Workbook wb;
		 
	         wb =  new HSSFWorkbook();
	        
	         
	          
//	          

	          
		Sheet sheet =  wb.createSheet("Dist SIP Report Details");
		Sheet sheet2=  wb.createSheet("Dist SIP Report");

		SIPReportDetails( wb, sheet,
		      disttLinkedHashMapes.get ( "exectives"),
              disttLinkedHashMapes.get ( "revenueREport"),
              disttLinkedHashMapes.get ( "GrossAdds"),
              disttLinkedHashMapes.get ( "Dormant"),                  
              disttLinkedHashMapes.get ( "GrossAdds2"),
              disttLinkedHashMapes.get ( "GrossAddActHashMap")
		      );
		SIPReport( wb, sheet2,
		      disttLinkedHashMapes.get ( "exectives"),
              disttLinkedHashMapes.get ( "revenueREportNonDetail"),
              disttLinkedHashMapes.get ( "GrossAddsNonDetail"),
              disttLinkedHashMapes.get ( "DormantNonDetail"),                  
              disttLinkedHashMapes.get ( "GrossAdds2NonDetail"),
              disttLinkedHashMapes.get ( "ExPOSNonDetail")
                
		);

	    //Sheet sheet5 = wb.getSheetAt(0);
		for(int i=0;i<40;i++)
		{
			sheet.autoSizeColumn((short)i); //adjust width of the first column
			sheet2.autoSizeColumn((short)i); //adjust width of the second column
		}
	    sheet.createFreezePane( 2, 0 );
	    sheet2.createFreezePane( 2, 2 );
	
////////////////////////////////////////////////////////////////////////////////


    
// Write the output to a file
//Date date = new Date();
//String file = "Distributors_Q3_"+date.getHours()+"-"+date.getMinutes()+"-"+date.getSeconds()+".xls";
//StringBuffer filePath = new StringBuffer("");    
//  filePath.append(new File("").getAbsolutePath());
//  filePath.append(System.getProperty("file.separator"));
//  filePath.append("SDS");
//  filePath.append(System.getProperty("file.separator"));
//  filePath.append("download");
//  filePath.append(System.getProperty("file.separator"));
//  filePath.append(file);
Date date = new Date();
        
        StringBuffer filePath = new StringBuffer("");    
        filePath.append(path);                  
          filePath.append("Distributors_");
          filePath.append(quartar);
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

//if(wb instanceof XSSFWorkbook) file += "x";
FileOutputStream out = new FileOutputStream(excelDistFile);
wb.write(out);
out.close();

return filePath.toString();
	}
	private static void SIPReport(Workbook wb,Sheet sheet2,
	      LinkedHashMap<String,ArrayList<String> >  exectives,
          LinkedHashMap<String,ArrayList<String> >  revenueREport,
          LinkedHashMap<String,ArrayList<String> >  GrossAdds,
          LinkedHashMap<String,ArrayList<String> >  Dormant,          
          LinkedHashMap<String,ArrayList<String> >  GrossAdds2,
          LinkedHashMap<String,ArrayList<String> >  Q3HashMap
	)
	{
		PrintSetup printSetup = sheet2.getPrintSetup();
        printSetup.setLandscape(true);
        sheet2.setFitToPage(true);
        sheet2.setHorizontallyCenter(true);
        Map<String, CellStyle> styles = createStyles(wb);

        
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$C$1:$F$1"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$H$1:$I$1"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$L$1:$O$1"));
      //  sheet2.addMergedRegion(CellRangeAddress.valueOf("$I$3:$L$3"));
       // sheet2.addMergedRegion(CellRangeAddress.valueOf("$W$3:$X$3"));
       // sheet2.addMergedRegion(CellRangeAddress.valueOf("$Y$3:$Z$3"));
        //sheet2.addMergedRegion(CellRangeAddress.valueOf("$AA$3:$AB$3"));
        //sheet2.addMergedRegion(CellRangeAddress.valueOf("$AC$3:$AF$3"));
         
        //headerRow.setHeightInPoints(40);
     //   Cell headerCell2;
      
         Row headerRow2 = sheet2.createRow(0);
        //headerRow.setHeightInPoints(40);
        Cell headerCell2;
        headerCell2 = headerRow2.createCell(2);
        headerCell2.setCellValue("Revenue Target");
        headerCell2.setCellStyle(styles.get("header"));
        
        
        
        headerCell2 = headerRow2.createCell(6);
        headerCell2.setCellValue("Gross Adds (CIF)");
        headerCell2.setCellStyle(styles.get("header"));
        
        headerCell2 = headerRow2.createCell(7);
        headerCell2.setCellValue("Dormant");
        headerCell2.setCellStyle(styles.get("header"));
        
        headerCell2 = headerRow2.createCell(9);
        headerCell2.setCellValue("Line Quality");
        headerCell2.setCellStyle(styles.get("header"));
        
        headerCell2 = headerRow2.createCell(10);
        headerCell2.setCellValue("Gross Adds (Act)");
        headerCell2.setCellStyle(styles.get("header"));
        
        headerCell2 = headerRow2.createCell(11);
        headerCell2.setCellValue("Q2");
        headerCell2.setCellStyle(styles.get("header"));    
        headerRow2 = sheet2.createRow(1);
      // Cell headerCell2;
        for (int i = 0; i < titles4.length; i++) {
            headerCell2 = headerRow2.createCell(i);
            headerCell2.setCellValue(titles4[i]);
            headerCell2.setCellStyle(styles.get("header"));
        }
        
        
	   
	    Iterator it =revenueREport.keySet().iterator();

	    int h=2;
	    
        Cell revenueTargetCell;
        Cell ScratchCell;
        Row revenueTargetRow = null;

	    while(it.hasNext())
	    {

			    String alphakey= (String)	it.next();
			    ArrayList   alphakeyvaluse5=   exectives.get(alphakey);

			    System.out.println("The key isssssssssssssssssssssss"+alphakey);
		        revenueTargetRow = sheet2.createRow(h);
		        
		        
		        revenueTargetCell=revenueTargetRow.createCell(0);
			    revenueTargetCell.setCellValue((String) alphakeyvaluse5.get(0) );
	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        	
			    revenueTargetCell=revenueTargetRow.createCell(1);
			    revenueTargetCell.setCellValue(alphakey);
	        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    int t=h+1;
			    ScratchCell=revenueTargetRow.createCell(4);
			    ScratchCell.setCellFormula("C"+t+"+D"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(5);
			    ScratchCell.setCellFormula("D"+t+"/E"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(8);
			    ScratchCell.setCellFormula("H"+t+"/G"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(9);
			    ScratchCell.setCellFormula("G"+t+"-H"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(13);
			    ScratchCell.setCellFormula("M"+t+"+L"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(14);
			    ScratchCell.setCellFormula("L"+t+"/N"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    ArrayList   alphakeyvaluse1=  GrossAdds.get(alphakey);
			    ArrayList   alphakeyvaluse=   revenueREport.get(alphakey);
			   
			    ArrayList   alphakeyvaluse2=   Dormant.get(alphakey);

			    ArrayList   alphakeyvaluse3=   GrossAdds2.get(alphakey);
			    ArrayList   alphakeyvaluse4=   Q3HashMap.get(alphakey);

		        for (int i = 0; i < alphakeyvaluse.size(); i++) 
			    {
			        revenueTargetCell=revenueTargetRow.createCell(i+2);
			    	double finalValue=Double.parseDouble((String)  alphakeyvaluse.get(i));
		        	revenueTargetCell.setCellValue(finalValue);				        
		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
		        for (int i = 0; i < alphakeyvaluse1.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+6);
		         	double finalValue=Double.parseDouble((String)  alphakeyvaluse1.get(i));
		        	revenueTargetCell.setCellValue(finalValue);
		        	//revenueTargetCell.setCellValue((String)  alphakeyvaluse1.get(i));

		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
		        
		        for (int i = 0; i < alphakeyvaluse2.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+7);

		         	double finalValue=Double.parseDouble((String)  alphakeyvaluse2.get(i));
		        	revenueTargetCell.setCellValue(finalValue);		
		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
		        
		        for (int i = 0; i < alphakeyvaluse3.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+10);
		         	double finalValue=Double.parseDouble((String)  alphakeyvaluse3.get(i));
		        	revenueTargetCell.setCellValue(finalValue);
		        	revenueTargetCell.setCellStyle(styles.get("cell"));

		        	//revenueTargetCell.setCellValue((String)  alphakeyvaluse3.get(i));
			        
			    }
		        
		        for (int i = 0; i < alphakeyvaluse4.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+11);
		         	double finalValue=Double.parseDouble((String)  alphakeyvaluse4.get(i));
		        	revenueTargetCell.setCellValue(finalValue);
		        	revenueTargetCell.setCellStyle(styles.get("cell"));

		        	//revenueTargetCell.setCellValue((String)  alphakeyvaluse3.get(i));
			        
			    }
			    h++;
	    }
	
       

		
		
	}
	
	public static void SIPReportDetails(Workbook wb,Sheet sheet/*,LinkedHashMap<String,ArrayList<String> >*/,
	      LinkedHashMap<String,ArrayList<String> >  exectives,
          LinkedHashMap<String,ArrayList<String> >  revenueREport,
          LinkedHashMap<String,ArrayList<String> >  GrossAdds,
          LinkedHashMap<String,ArrayList<String> >  Dormant,
          LinkedHashMap<String,ArrayList<String> >  POS,
          LinkedHashMap<String,ArrayList<String> >  GrossAdds2      
	)
	{
		PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        Map<String, CellStyle> styles = createStyles(wb);
      /*  sheet.addMergedRegion(CellRangeAddress.valueOf("$C$2:$L$2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$M$2:$F$2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$R$2:$H$2"));
        //sheet.addMergedRegion(CellRangeAddress.valueOf("$$2:$L$2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$W$2:$AF$2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$AG$2:$J$2"));
        */
       sheet.addMergedRegion(CellRangeAddress.valueOf("$C$3:$D$3"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$E$3:$F$3"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$G$3:$H$3"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$I$3:$L$3"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$W$3:$X$3"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$Y$3:$Z$3"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$AA$3:$AB$3"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$AC$3:$AF$3"));
         
        //headerRow.setHeightInPoints(40);
     //   Cell headerCell2;
      
         Row headerRow2 = sheet.createRow(1);
        //headerRow.setHeightInPoints(40);
        Cell headerCell2;
        /********************************Merging and Setting Borders***************/
        headerCell2 = headerRow2.createCell(2);
        headerCell2.setCellValue("Revenue Target");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(3);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(4);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(5);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(6);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(7);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(8);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(9);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(10);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(11);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(12);
        headerCell2.setCellValue("Gross Adds CIF");
        headerCell2.setCellStyle(styles.get("header"));
        
        headerCell2 = headerRow2.createCell(13);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(14);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(15);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(16);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(17);
        headerCell2.setCellValue("Dormant");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(18);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(19);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(20);
        headerCell2.setCellStyle(styles.get("header"));

        headerCell2 = headerRow2.createCell(21);
        headerCell2.setCellValue("Line Quality");
        headerCell2.setCellStyle(styles.get("header"));
        
        headerCell2 = headerRow2.createCell(22);
        headerCell2.setCellValue("POS Split");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(23);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(24);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(25);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(26);
        headerCell2.setCellStyle(styles.get("header"));
        
        headerCell2 = headerRow2.createCell(27);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(28);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(29);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(30);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(31);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(32);
        headerCell2.setCellValue("Gross Adds (Act)");
        headerCell2.setCellStyle(styles.get("header"));    
        headerCell2 = headerRow2.createCell(33);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(34);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(35);
        headerCell2.setCellStyle(styles.get("header"));
       
        headerRow2 = sheet.createRow(3);
        for (int i = 0; i < titles3.length; i++) {
            headerCell2 = headerRow2.createCell(i);
            headerCell2.setCellValue(titles3[i]);
            headerCell2.setCellStyle(styles.get("header"));
        }
       
        sheet.addMergedRegion(CellRangeAddress.valueOf("$C$2:$L$2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$M$2:$Q$2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$R$2:$U$2"));
        //sheet.addMergedRegion(CellRangeAddress.valueOf("$$2:$L$2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$W$2:$AF$2"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$AG$2:$AJ$2"));
       /////////////////////////////////////////////////////////////////////////////////////
       /////////////////////////////////////////////////////////////////////////////////////
        Row headerRow = sheet.createRow(2);
        //headerRow.setHeightInPoints(40);
        Cell headerCell = null;
        int i=2;
        int u=0;
        headerRow2 = sheet.createRow(2);
       while(i < 100 && u<titles.length) {
    	   if(i==2 || i==3 )
    	   {
           
            if(i==3){u++;}
            else
            {
            	 headerCell = headerRow2.createCell(i);
            	 headerCell.setCellValue(titles[u]);
            	 headerCell.setCellStyle(styles.get("header"));
            	 System.out.println(i+" "+u);
            }
    	   }
    	   
    	
    	   
    	   else if(i==4 || i==5 )
    	   {
          
            if(i==5){u++;}
            else
            {
            	  headerCell = headerRow2.createCell(i);
            	  headerCell.setCellValue(titles[u]);
            	  headerCell.setCellStyle(styles.get("header"));
            	  System.out.println(i+" "+u);
            }
    	   }
    	   else   if(i==6 || i==7 )
    	   {
           
            if(i==7){u++;}
            else
            {
            	 headerCell = headerRow2.createCell(i);
            	 headerCell.setCellValue(titles[u]);
            	 headerCell.setCellStyle(styles.get("header"));
            	 System.out.println(i+" "+u);
            }
    	   }
    	   else  if(i==8 || i==9 || i==10 ||  i==11 )
    	   {
          
            if(i==11){u++;}
            else if(i==8)
            {
            	headerCell = headerRow2.createCell(i);
            	headerCell.setCellValue(titles[u]);
            	headerCell.setCellStyle(styles.get("header"));
            	System.out.println(i+" "+u);
            }
    	   }
    	   else if(i==22 || i==23 )
    	   {
          
            if(i==23){u++;}
            else
            {
            	headerCell = headerRow2.createCell(i);
            	headerCell.setCellValue(titles[u]);
            	headerCell.setCellStyle(styles.get("header"));
            	System.out.println(i+" "+u);
            }
    	   }
    	   
    	   else if(i==24 || i==25 )
    	   {
            if(i==25){u++;}
            else
            {
                headerCell = headerRow2.createCell(i);
                headerCell.setCellValue(titles[u]);
                headerCell.setCellStyle(styles.get("header"));
         	   System.out.println(i+" "+u);
            }
    	   }
    	   
    	   else if(i==26 || i==27 )
    	   {
            if(i==27){u++;}
            else
            {
                headerCell = headerRow2.createCell(i);
                headerCell.setCellValue(titles[u]);
                headerCell.setCellStyle(styles.get("header"));
         	   System.out.println(i+" "+u);
            }
    	   }
    	   else if(i==28 || i==29 || i==30 || i==31 )
    	   {
            if(i==31){u++;}
            else if(i==28)
            {
                headerCell = headerRow2.createCell(i);
                headerCell.setCellValue(titles[u]);
                headerCell.setCellStyle(styles.get("header"));
         	   System.out.println(i+" "+u);
            }
    	   }
    	   else
    	   {
    		   headerCell = headerRow2.createCell(i);
               headerCell.setCellValue(titles[u]);
               headerCell.setCellStyle(styles.get("header"));
               u++;
        	   System.out.println(i+" "+u);

    		   
    	   }
    	   i++;
    	 }
        
      /////////////////////////////////
        //Row row=sheet.createRow(1);
        //Cell titleCell = row.createCell(2);
        //titleCell.setCellValue("Revenue Target");
        //titleCell.setCellStyle(styles.get("header"));
        //sheet.addMergedRegion(CellRangeAddress.valueOf("$C$2:$L$2"));
        //////////////////////////////
    //    Row headerRow1 = sheet.createRow(1);
        //headerRow.setHeightInPoints(40);
        Cell headerCell1;
	   
	    Iterator it =revenueREport.keySet().iterator();

	    int h=4;
	    
        Cell revenueTargetCell;
        Cell ScratchCell;
        Row revenueTargetRow = null;

	    while(it.hasNext())
	    {
			    String alphakey= (String)	it.next();
			    ArrayList   alphakeyvaluse5=  exectives.get(alphakey);

			    System.out.println("The key isssssssssssssssssssssss"+alphakey);
		        revenueTargetRow = sheet.createRow(h);
		        
		        
		        revenueTargetCell=revenueTargetRow.createCell(0);
			    revenueTargetCell.setCellValue((String)  alphakeyvaluse5.get(0));
	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        	
			    revenueTargetCell=revenueTargetRow.createCell(1);
			    revenueTargetCell.setCellValue(alphakey);
	        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    int t=h+1;
			    ScratchCell=revenueTargetRow.createCell(8);
			    ScratchCell.setCellFormula("C"+t+"+E"+t+"+G"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(9);
			    ScratchCell.setCellFormula("D"+t+"+F"+t+"+H"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));

			    
			    ScratchCell=revenueTargetRow.createCell(10);
			    ScratchCell.setCellFormula("I"+t+"+J"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));

			    
			    ScratchCell=revenueTargetRow.createCell(11);
			    ScratchCell.setCellFormula("J"+t+"/K"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));

			    
			    ScratchCell=revenueTargetRow.createCell(16);
			    ScratchCell.setCellFormula("M"+t+"+N"+t+"+O"+t+"+P"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(20);
			    ScratchCell.setCellFormula("R"+t+"+S"+t+"+T"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));

			    
			    ScratchCell=revenueTargetRow.createCell(21);
			    ScratchCell.setCellFormula("Q"+t+"-U"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));

			    ScratchCell=revenueTargetRow.createCell(29);
			    ScratchCell.setCellFormula("X"+t+"+Z"+t+"+AB"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(30);
			    ScratchCell.setCellFormula("AC"+t+"+AD"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    
			    ScratchCell=revenueTargetRow.createCell(31);
			    ScratchCell.setCellFormula("AC"+t+"/AE"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(35);
			    ScratchCell.setCellFormula("AG"+t+"+AH"+t+"+AI"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ArrayList   alphakeyvaluse1=  GrossAdds.get(alphakey);
			    ArrayList   alphakeyvaluse=   revenueREport.get(alphakey);
			   
			    ArrayList   alphakeyvaluse2=   Dormant.get(alphakey);

			    ArrayList   alphakeyvaluse3=   POS.get(alphakey);
			    ArrayList   alphakeyvaluse4=   GrossAdds2.get(alphakey);

		        for ( i = 0; i < alphakeyvaluse.size(); i++) 
			    {
			        revenueTargetCell=revenueTargetRow.createCell(i+2);
			    	double finalValue=Double.parseDouble((String)  alphakeyvaluse.get(i));
		        	revenueTargetCell.setCellValue(finalValue);				        
		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
		        for ( i = 0; i < alphakeyvaluse1.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+12);
		         	double finalValue=Double.parseDouble((String)  alphakeyvaluse1.get(i));
		        	revenueTargetCell.setCellValue(finalValue);
		        	//revenueTargetCell.setCellValue((String)  alphakeyvaluse1.get(i));

		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
		        
		        for ( i = 0; i < alphakeyvaluse2.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+17);

		         	double finalValue=Double.parseDouble((String)  alphakeyvaluse2.get(i));
		        	revenueTargetCell.setCellValue(finalValue);		
		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
		        
		        for ( i = 0; i < alphakeyvaluse3.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+22);
		         	double finalValue=Double.parseDouble((String)  alphakeyvaluse3.get(i));
		        	revenueTargetCell.setCellValue(finalValue);
		        	revenueTargetCell.setCellStyle(styles.get("cell"));

		        	//revenueTargetCell.setCellValue((String)  alphakeyvaluse3.get(i));
			        
			    }
		        System.out.print("Size is "+alphakeyvaluse4.size());
		        for ( i = 0; i < alphakeyvaluse4.size(); i++) 
			    {
			        System.out.print("Size is "+i);

		        	revenueTargetCell=revenueTargetRow.createCell(i+32);
		        	double finalValue=Double.parseDouble((String)  alphakeyvaluse4.get(i));
		        	revenueTargetCell.setCellValue(finalValue);
		        	revenueTargetCell.setCellStyle(styles.get("cell"));
			        
			    }
			    h++;
	    }
	
         revenueTargetRow = sheet.createRow(h);
         revenueTargetCell=revenueTargetRow.createCell(1);

        	revenueTargetCell.setCellValue("Total");
        	revenueTargetCell.setCellStyle(styles.get("header"));

        	for( i=0;i<columns.length;i++)
        	{ 
        		revenueTargetCell=revenueTargetRow.createCell(i+2);
        		revenueTargetCell.setCellFormula("SUM("+columns[i]+"5:"+columns[i]+h+")");
	        	revenueTargetCell.setCellStyle(styles.get("header"));

        		
        		
        	}
		
		
	}
	  private static Map<String, CellStyle> createStyles(Workbook wb){
	        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
	        CellStyle style;
	        Font titleFont = wb.createFont();
	        //titleFont.setFontHeightInPoints((short)18);
	        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        titleFont.setFontHeightInPoints((short)10);
	        titleFont.setFontName("Times New Roman");
	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        style.setFont(titleFont);
	        styles.put("title", style);

	        Font monthFont = wb.createFont();
	        //monthFont.setFontHeightInPoints((short)11);
	        monthFont.setColor(IndexedColors.BLACK.getIndex());
	        monthFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        monthFont.setFontHeightInPoints((short)10);
	        monthFont.setFontName("Times New Roman");
	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        style.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        style.setBorderBottom(CellStyle.BORDER_THIN);
	        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderLeft(CellStyle.BORDER_THIN);
	        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        
	        style.setBorderRight(CellStyle.BORDER_THIN);
	        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        
	        style.setBorderTop(CellStyle.BORDER_THIN);
	        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        
	
	        style.setFont(monthFont);
	        style.setWrapText(true);
	        styles.put("header", style);

	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        style.setWrapText(true);
	        style.setBorderRight(CellStyle.BORDER_THIN);
	        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderLeft(CellStyle.BORDER_THIN);
	        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderTop(CellStyle.BORDER_THIN);
	        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderBottom(CellStyle.BORDER_THIN);
	        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        styles.put("cell", style);

	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
	        styles.put("formula", style);

	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
	        styles.put("formula_2", style);

	        return styles;
	    }
	
	
}
