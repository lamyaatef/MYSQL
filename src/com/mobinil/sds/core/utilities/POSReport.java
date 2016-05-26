package com.mobinil.sds.core.utilities;



import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.FileOutputStream;

public class POSReport 
{
	private static final String[] titles={
		"Revenue Target","Gross Adds STF Level 1","Q2-2009 NI","Line Quality"};
		
	private static final String[] titles2={
		"RepName","RegionalSupervisorName","SalesRegionName","ExPos July","Non ExPosJuly",
		"ExPosAugust","Non ExPos August","ExPos September","Non ExPos Septemeber","ExPos",
		"Non ExPos","Total POS","% of ExPOS"
		
	};
private static final String[] titles3={"RepName",
	"RegionalSupervisorName","PosCode","PosENm","SalesRegionName","English Address",
	"DistrictName","GovernrateName","July Revnue","JulyTotalLines","August Revnue","AugTotalLines"
	,"September Revnue","SeptTotalLines","Total ARPU Lines for Q3-09","Total Q3-2009 Revnue","July STF",
	"August STF","September STF","April","May","June","Q2-2009 NI","Total STF level2 GA Q3-2009","April Dormant",
	"May Dormant","June Dormant","Total Dormant","% of Dormant","STF Gross Adds - Q2-2009 Dormant","Status"
	
	
};
private static final String[] titles4={
"PosCode","PosENm","SalesRegionName","English Address","DistrictName","GovernrateName",
"July Revnue","JulyTotalLines","August Revnue","AugTotalLines","September Revnue","SeptTotalLines",
"Total ARPU Commissioned Lines for Q3-09","Total Q3-2009 Revnue","July STF","August STF","September STF",
"April","May","June","Q2-2009 NI","Total STF level2 GA Q3-2009","April Dormant","May Dormant","June Dormant",
"Total Dormant","% of Dormant","STF Gross Adds - Q2-2009 Dormant","Status"

	
};
private static final String[] titles5={
	"PosCode","PosENm","SalesRegionName","English Address","DistrictName","RepName","RegionalSupervisorName","GovernrateName",
	"July Revnue","JulyTotalLines","August Revnue","AugTotalLines","September Revnue","SeptTotalLines",
	"Total ARPU Commissioned Lines for Q3-09","Total Q3-2009 Revnue","July STF","August STF","September STF",
	"April","May","June","Q2-2009 NI","Total STF level2 GA Q3-2009","April Dormant","May Dormant","June Dormant",
	"Total Dormant","% of Dormant","STF Gross Adds - Q2-2009 Dormant","Status"

		
	};
	 private static final String[] columns={
		"D","E","F","G","H","I","J","K","L","M"
		
		 
		 
	 };



	public static  String mainExportPOSReport(String path,String quartar) throws Exception 
	{
		 Workbook wb;
	
	        wb = (Workbook) new HSSFWorkbook();
	        
		
		Sheet sheet2=  wb.createSheet("POS Split");
		Sheet sheet=wb.createSheet("POS SIP Reports Summary per Rep");
		Sheet sheet3=wb.createSheet("POS SIP Reports Summary per Sup");
		Sheet sheet4=wb.createSheet("POS SIP Reports Details");
		SIPReport( wb, sheet2);
		POSSummary(wb,sheet);
		POSSummarySup(wb,sheet3);
		POSDetails(wb,sheet4);
		for(int i=0;i<40;i++)
		{
			sheet.autoSizeColumn((short)i); //adjust width of the first column
			sheet2.autoSizeColumn((short)i); //adjust width of the second column
			sheet3.autoSizeColumn((short) i);
			sheet4.autoSizeColumn((short) i);

		}
		    sheet.createFreezePane(1, 3);
		    sheet4.createFreezePane(1, 8);

		////////////////////////////////////////////////////////////////////////////////
	    
		
		    
		 // Write the output to a file
//		    Date date = new Date();
//		    String file = "POS_Report_Q3_"+date.getHours()+"-"+date.getMinutes()+"-"+date.getSeconds()+".xls";
//		    StringBuffer filePath = new StringBuffer("");    
//		      filePath.append(new File("").getAbsolutePath());
//		      filePath.append(System.getProperty("file.separator"));
//		      filePath.append("SDS");
//		      filePath.append(System.getProperty("file.separator"));
//		      filePath.append("download");
//		      filePath.append(System.getProperty("file.separator"));
//		      filePath.append(file);

		    Date date = new Date();
	        
	        StringBuffer filePath = new StringBuffer("");    
	        filePath.append(path);     
	          filePath.append("POS_Report_");
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

//		    if(wb instanceof XSSFWorkbook) file += "x";
		    FileOutputStream out = new FileOutputStream(excelDistFile);
		    wb.write(out);
		    out.close();

		    return filePath.toString();
		
	}
	private static void POSDetails(Workbook wb,Sheet sheet2)
	{
		PrintSetup printSetup = sheet2.getPrintSetup();
        printSetup.setLandscape(true);
        sheet2.setFitToPage(true);
        sheet2.setHorizontallyCenter(true);
        Map<String, CellStyle> styles = createStyles(wb);
/*************************Merging and Setting Borders*******************/
        Row headerRow2 = null;
        Cell headerCell2;
        headerRow2=sheet2.createRow(5);
        headerCell2 = headerRow2.createCell(16);
        headerCell2.setCellValue("Gross Adds STF Level 2");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(17);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(18);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(19);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(20);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(21);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(22);
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
        headerRow2=sheet2.createRow(6);

        headerCell2 = headerRow2.createCell(8);
        headerCell2.setCellValue("Revenue Target");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(9);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(10);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(11);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(12);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(13);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(14);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(15);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(16);
        headerCell2.setCellValue("STF Gross Adds level 2 Q3-09");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(17);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(18);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(19);
        headerCell2.setCellValue("STF level 2 NI Q2-09");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(20);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(21);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(22);
        headerCell2.setCellValue("Total Gross Adds");
        headerCell2.setCellStyle(styles.get("header"));
        
        headerCell2 = headerRow2.createCell(23);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(24);
        headerCell2.setCellValue("Dormant");
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
        headerCell2.setCellValue("Line Quality");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(30);
        headerCell2.setCellStyle(styles.get("header"));
        
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$Q$6:$AD$6"));

        sheet2.addMergedRegion(CellRangeAddress.valueOf("$I$7:$P$7"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$Q$7:$S$7"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$T$7:$V$7"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$W$7:$X$7"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$Y$7:$AC$7"));
        
        headerRow2 = sheet2.createRow(7);
        // Cell headerCell2;
          for (int i = 0; i < titles5.length; i++) {
              headerCell2 = headerRow2.createCell(i);
              headerCell2.setCellValue(titles5[i]);
              headerCell2.setCellStyle(styles.get("header"));
          }
   //////////////////////////////////////////////////////////////////////////////////
	    LinkedHashMap<String,ArrayList<String> >  RevenueTarget= new  LinkedHashMap<String,ArrayList<String>>();
	    LinkedHashMap<String,ArrayList<String> >  RegionalSupervisorName= new  LinkedHashMap<String,ArrayList<String>>();
	    LinkedHashMap<String,ArrayList<String> >  Dormant= new  LinkedHashMap<String,ArrayList<String>>();
	    LinkedHashMap<String,ArrayList<String> >  Status= new  LinkedHashMap<String,ArrayList<String>>();

	    ArrayList<String> revenueList=new ArrayList<String>();
	    revenueList.add("1");
	    revenueList.add("2");
	    revenueList.add("1");
	    revenueList.add("2");
	    revenueList.add("1");
	    revenueList.add("2");
	    revenueList.add("4");
	    revenueList.add("5");
	    revenueList.add("1");
	    revenueList.add("2");
	    revenueList.add("1");
	    revenueList.add("2");
	    revenueList.add("5");

	    //me.add("3");
	
	    RevenueTarget.put("0001.001",revenueList);
	    revenueList=new ArrayList<String>();
	    revenueList.add("4");
	    revenueList.add("5");
	    revenueList.add("1");
	    revenueList.add("2");
	    revenueList.add("1");
	    revenueList.add("2");
	    revenueList.add("1");
	    revenueList.add("2");
	    revenueList.add("1");
	    revenueList.add("2");
	    revenueList.add("1");
	    revenueList.add("2");
	    revenueList.add("5");

	  //  me.add("2");
	    RevenueTarget.put("0001.002",revenueList);
	   //////////////////////////////////////////////
	    
	    ArrayList<String> regionalNameList=new ArrayList<String>();
	    regionalNameList.add("Pyramids Telecom");
	    regionalNameList.add("Greater Cairo");
	    regionalNameList.add("Shop No 1 Masaken El Amleen Hay El nour");
	    regionalNameList.add("Qasr El Nile");

	    regionalNameList.add("Mohammed Anter");
	    regionalNameList.add("Moustafa Mohamed El Nabawy");
	    regionalNameList.add("Cairo");


	    RegionalSupervisorName.put("0001.001",regionalNameList);

	    regionalNameList=new ArrayList<String>();
	    regionalNameList.add("Pyramids Telecom");
	    regionalNameList.add("Alexandria");
	   
	    regionalNameList.add("Shop No 1 Masaken El Amleen Hay El nour");
	    regionalNameList.add("Sid_Gaber");
	    regionalNameList.add("Ibrahim Ahmed El Khawanky");
	    regionalNameList.add("Ayman Ali Nemat-Allah");
	    regionalNameList.add("Alexandria");


	    RegionalSupervisorName.put("0001.002",regionalNameList);
	    ////////////////////////////////////////////////////////
	    ArrayList<String> dormantList=new ArrayList<String>();
	    
	    dormantList.add("12");
	    dormantList.add("5");
	    dormantList.add("9");
	    Dormant.put("0001.001",dormantList);

	    dormantList=new ArrayList<String>();
	    
	    dormantList.add("9");
	    dormantList.add("12");
	    dormantList.add("4");
	    Dormant.put("0001.002",dormantList);
	    ////////////////////////////////////////////////////////
	    ArrayList<String> statusList=new ArrayList<String>();
	    
	    statusList.add("Non ExPos");
	  
	    Status.put("0001.001",statusList);

	    statusList=new ArrayList<String>();
	    
	    statusList.add("Non ExPos");
	
	    Status.put("0001.002",statusList);
	    
	    Iterator it =RevenueTarget.keySet().iterator();

	    int h=8;
	    
        Cell revenueTargetCell;
        Cell ScratchCell;
        Row revenueTargetRow = null;
int g=0;
int u=0;
	    while(it.hasNext())
	    {
			    String alphakey= (String)	it.next();
			    
			    System.out.println("The key isssssssssssssssssssssss"+alphakey);
		      //  revenueTargetRow = sheet2.createRow(h);
		        revenueTargetRow = sheet2.createRow(h);

			    ArrayList   alphakeyvaluse1=   RegionalSupervisorName.get(alphakey);
			    ArrayList   alphakeyvaluse2=   Dormant.get(alphakey);
			    ArrayList   alphakeyvaluse=   RevenueTarget.get(alphakey);
			    ArrayList   alphakeyvaluse3=   Status.get(alphakey);

				g=h+1;
				revenueTargetCell=revenueTargetRow.createCell(0);
				revenueTargetCell.setCellValue(alphakey);
				revenueTargetCell.setCellStyle(styles.get("cell"));
	        	revenueTargetCell=revenueTargetRow.createCell(14);
	        	revenueTargetCell.setCellFormula("N"+g+"+J"+g+"+L"+g);
	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        
	        	revenueTargetCell=revenueTargetRow.createCell(15);
	        	revenueTargetCell.setCellFormula("M"+g+"+I"+g+"+K"+g);
	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        	
	        	revenueTargetCell=revenueTargetRow.createCell(23);
	        	revenueTargetCell.setCellFormula("Q"+g+"+R"+g+"+S"+g+"+W"+g);
	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        	
	        	revenueTargetCell=revenueTargetRow.createCell(24);
	        	revenueTargetCell.setCellFormula("O"+g+"+P"+g+"+Q"+g+"+U"+g);
	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        	
	        	revenueTargetCell=revenueTargetRow.createCell(27);
	        	revenueTargetCell.setCellFormula("Y"+g+"+Z"+g+"+AA"+g);
	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        	
	        	revenueTargetCell=revenueTargetRow.createCell(28);
	        	revenueTargetCell.setCellFormula("AB"+g+"/X"+g);
	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        	
	        	revenueTargetCell=revenueTargetRow.createCell(29);
	        	revenueTargetCell.setCellFormula("X"+g+"-AB"+g);
	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        	
	        	revenueTargetCell=revenueTargetRow.createCell(30);
	        	revenueTargetCell.setCellValue((String)  alphakeyvaluse3.get(0));
	        	revenueTargetCell.setCellStyle(styles.get("cell"));

			 //   revenueTargetCell=revenueTargetRow.createCell(2);
	        ///	revenueTargetCell.setCellValue(alphakey);

	        	//revenueTargetCell.setCellStyle(styles.get("cell"));
	        	int t=8;
			       for (int i = 0; i < alphakeyvaluse.size(); i++) 
				    {
			    	   if(i==6 )
			    	   {
				        	
			    		   	t=t+2;
			    	   }
			    	   
			        	revenueTargetCell=revenueTargetRow.createCell(t);
			        	double finalValue=Double.parseDouble((String)  alphakeyvaluse.get(i));
			        	revenueTargetCell.setCellValue(finalValue);
			        	revenueTargetCell.setCellStyle(styles.get("cell"));
			        	t++;
			    	
				    }
		    
			    for (int i = 0; i < alphakeyvaluse1.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+1);
		         	//double finalValue=Double.parseDouble((String)  alphakeyvaluse1.get(i));
		        	revenueTargetCell.setCellValue((String)  alphakeyvaluse1.get(i));
		        	//revenueTargetCell.setCellValue((String)  alphakeyvaluse1.get(i));

		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
			    
			    for (int i = 0; i < alphakeyvaluse2.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+24);
		         	double finalValue=Double.parseDouble((String)  alphakeyvaluse2.get(i));
		        	revenueTargetCell.setCellValue(finalValue);
		        	//revenueTargetCell.setCellValue((String)  alphakeyvaluse1.get(i));

		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
			    
			    h++;
	    }
		
	}
	private static void POSSummarySup(Workbook wb,Sheet sheet2)
	{
		PrintSetup printSetup = sheet2.getPrintSetup();
        printSetup.setLandscape(true);
        sheet2.setFitToPage(true);
        sheet2.setHorizontallyCenter(true);
        Map<String, CellStyle> styles = createStyles(wb);

        Row headerRow2 = null;
        Cell headerCell2;
        /**************************Meriging and Setting Borders*******************/
        headerRow2=sheet2.createRow(0);
        headerCell2 = headerRow2.createCell(14);
        headerCell2.setCellValue("Gross Adds STF Level 2");
        headerCell2.setCellStyle(styles.get("header"));
        
        headerRow2=sheet2.createRow(1);

        headerCell2 = headerRow2.createCell(6);
        headerCell2.setCellValue("Revenue Target");
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
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(13);
        headerCell2.setCellStyle(styles.get("header"));
      
        headerCell2 = headerRow2.createCell(14);
        headerCell2.setCellValue("STF Gross Adds level 2 Q3-09");
        headerCell2.setCellStyle(styles.get("header"));
        
     
        headerCell2 = headerRow2.createCell(15);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(16);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(17);
        headerCell2.setCellValue("STF level 2 NI Q2-09");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(18);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(19);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(20);
        headerCell2.setCellValue("Total Gross Adds");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(21);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(22);
        headerCell2.setCellValue("Dormant");
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

        headerCell2.setCellValue("Line Quality");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(28);
        headerCell2.setCellStyle(styles.get("header"));

        sheet2.addMergedRegion(CellRangeAddress.valueOf("$O$1:$AB$1"));

        sheet2.addMergedRegion(CellRangeAddress.valueOf("$G$2:$N$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$O$2:$Q$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$R$2:$T$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$U$2:$V$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$W$2:$AA$2"));
        
        headerRow2 = sheet2.createRow(2);
        // Cell headerCell2;
          for (int i = 0; i < titles4.length; i++) {
              headerCell2 = headerRow2.createCell(i);
              headerCell2.setCellValue(titles4[i]);
              headerCell2.setCellStyle(styles.get("header"));
          }
   /************************************************************************************/
	    LinkedHashMap<String,ArrayList<String> >  RevenueTarget= new  LinkedHashMap<String,ArrayList<String>>();
	    LinkedHashMap<String,ArrayList<String> >  RegionalSupervisorName= new  LinkedHashMap<String,ArrayList<String>>();
	    LinkedHashMap<String,ArrayList<String> >  Dormant= new  LinkedHashMap<String,ArrayList<String>>();
	    LinkedHashMap<String,ArrayList<String> >  Status= new  LinkedHashMap<String,ArrayList<String>>();

	    ArrayList<String> revenueList=new ArrayList<String>();
	    revenueList.add("1");
	    revenueList.add("2");
	    revenueList.add("1");
	    revenueList.add("2");
	    revenueList.add("1");
	    revenueList.add("2");
	    revenueList.add("4");
	    revenueList.add("5");
	    revenueList.add("1");
	    revenueList.add("2");
	    revenueList.add("1");
	    revenueList.add("2");

	    //me.add("3");
	
	    RevenueTarget.put("10258.001",revenueList);
	    revenueList=new ArrayList<String>();
	    revenueList.add("4");
	    revenueList.add("5");
	    revenueList.add("1");
	    revenueList.add("2");
	    revenueList.add("1");
	    revenueList.add("2");
	    revenueList.add("1");
	    revenueList.add("2");
	    revenueList.add("1");
	    revenueList.add("2");
	    revenueList.add("1");
	    revenueList.add("2");
	  //  me.add("2");
	    RevenueTarget.put("10260.001",revenueList);
	   //////////////////////////////////////////////
	    
	    ArrayList<String> regionalNameList=new ArrayList<String>();
	    regionalNameList.add("Abdel Rahman Abd Allah Mohamed");
	    regionalNameList.add("Ahmed Shawky Rady");
	    regionalNameList.add("Mohammed Ayman");
	    regionalNameList.add("Canal North & South Sinai");
	    regionalNameList.add("Shop No 1 Masaken El Amleen Hay El nour");


	    RegionalSupervisorName.put("10258.001",regionalNameList);

	    regionalNameList=new ArrayList<String>();
	    regionalNameList.add("Abdel Rahman Abd Allah Mohamed");
	    regionalNameList.add("Ahmed Shawky Rady");
	    regionalNameList.add("Mohammed Ayman");
	    regionalNameList.add("Canal North & South Sinai");
	    regionalNameList.add("Shop No 1 Masaken El Amleen Hay El nour");
	

	    RegionalSupervisorName.put("10260.001",regionalNameList);
	    ////////////////////////////////////////////////////////
	    ArrayList<String> dormantList=new ArrayList<String>();
	    
	    dormantList.add("12");
	    dormantList.add("5");
	    dormantList.add("9");
	    Dormant.put("10258.001",dormantList);

	    dormantList=new ArrayList<String>();
	    
	    dormantList.add("9");
	    dormantList.add("12");
	    dormantList.add("4");
	    Dormant.put("10260.001",dormantList);
	    ////////////////////////////////////////////////////////
	    ArrayList<String> statusList=new ArrayList<String>();
	    
	    statusList.add("Non ExPos");
	  
	    Status.put("10258.001",statusList);

	    statusList=new ArrayList<String>();
	    
	    statusList.add("Non ExPos");
	
	    Status.put("10260.001",statusList);
	    
	    Iterator it =RevenueTarget.keySet().iterator();

	    int h=3;
	    
        Cell revenueTargetCell;
        Cell ScratchCell;
        Row revenueTargetRow = null;
int g=0;
int u=0;
	    while(it.hasNext())
	    {
			    String alphakey= (String)	it.next();
			    
			    System.out.println("The key isssssssssssssssssssssss"+alphakey);
		      //  revenueTargetRow = sheet2.createRow(h);
		        revenueTargetRow = sheet2.createRow(h);

			    ArrayList   alphakeyvaluse1=   RegionalSupervisorName.get(alphakey);
			    ArrayList   alphakeyvaluse2=   Dormant.get(alphakey);
			    ArrayList   alphakeyvaluse=   RevenueTarget.get(alphakey);
			    ArrayList   alphakeyvaluse3=   Status.get(alphakey);

				g=h+1;
				revenueTargetCell=revenueTargetRow.createCell(0);
				revenueTargetCell.setCellValue(alphakey);
				revenueTargetCell.setCellStyle(styles.get("cell"));
	        	revenueTargetCell=revenueTargetRow.createCell(12);
	        	revenueTargetCell.setCellFormula("H"+g+"+J"+g+"+L"+g);
	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        	
	        	revenueTargetCell=revenueTargetRow.createCell(13);
	        	revenueTargetCell.setCellFormula("G"+g+"+I"+g+"+K"+g);
	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        	
	        	revenueTargetCell=revenueTargetRow.createCell(20);
	        	revenueTargetCell.setCellFormula("R"+g+"+S"+g+"+T"+g);
	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        	
	        	revenueTargetCell=revenueTargetRow.createCell(21);
	        	revenueTargetCell.setCellFormula("O"+g+"+P"+g+"+Q"+g+"+U"+g);
	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        	
	        	revenueTargetCell=revenueTargetRow.createCell(25);
	        	revenueTargetCell.setCellFormula("W"+g+"+X"+g+"+Y"+g);
	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        	
	        	revenueTargetCell=revenueTargetRow.createCell(26);
	        	revenueTargetCell.setCellFormula("Z"+g+"/V"+g);
	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        	
	        	revenueTargetCell=revenueTargetRow.createCell(27);
	        	revenueTargetCell.setCellFormula("V"+g+"-Z"+g);
	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        	
	        	revenueTargetCell=revenueTargetRow.createCell(28);
	        	revenueTargetCell.setCellValue((String)  alphakeyvaluse3.get(0));
	        	revenueTargetCell.setCellStyle(styles.get("cell"));

			 //   revenueTargetCell=revenueTargetRow.createCell(2);
	        ///	revenueTargetCell.setCellValue(alphakey);

	        	//revenueTargetCell.setCellStyle(styles.get("cell"));
	        	int t=6;
			       for (int i = 0; i < alphakeyvaluse.size(); i++) 
				    {
			    	   if(i==6 )
			    	   {
				        	
			    		   	t=t+2;
			    	   }
			    	   
			        	revenueTargetCell=revenueTargetRow.createCell(t);
			        	double finalValue=Double.parseDouble((String)  alphakeyvaluse.get(i));
			        	revenueTargetCell.setCellValue(finalValue);
			        	revenueTargetCell.setCellStyle(styles.get("cell"));
			        	t++;
			    	
				    }
		    
			    for (int i = 0; i < alphakeyvaluse1.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+1);
		         	//double finalValue=Double.parseDouble((String)  alphakeyvaluse1.get(i));
		        	revenueTargetCell.setCellValue((String)  alphakeyvaluse1.get(i));
		        	//revenueTargetCell.setCellValue((String)  alphakeyvaluse1.get(i));

		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
			    
			    for (int i = 0; i < alphakeyvaluse2.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+22);
		         	double finalValue=Double.parseDouble((String)  alphakeyvaluse2.get(i));
		        	revenueTargetCell.setCellValue(finalValue);
		        	//revenueTargetCell.setCellValue((String)  alphakeyvaluse1.get(i));

		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
			    
			    h++;
	    }
	
    
		
		
	}

	private static void POSSummary(Workbook wb,Sheet sheet2)
	{
		PrintSetup printSetup = sheet2.getPrintSetup();
        printSetup.setLandscape(true);
        sheet2.setFitToPage(true);
        sheet2.setHorizontallyCenter(true);
        Map<String, CellStyle> styles = createStyles(wb);

        Row headerRow2 = null;
        Cell headerCell2;
        /*************************Merging and Setting Borders**********************/
        headerRow2=sheet2.createRow(0);
        headerCell2 = headerRow2.createCell(16);
        headerCell2.setCellValue("Gross Adds STF Level 2");
        headerCell2.setCellStyle(styles.get("header"));
        headerRow2=sheet2.createRow(1);

        headerCell2 = headerRow2.createCell(8);
        headerCell2.setCellValue("Revenue Target");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(9);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(10);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(11);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(12);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(13);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(14);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(15);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(16);
        headerCell2.setCellValue("STF Gross Adds level 2 Q3-09");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(17);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(18);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(19);
        headerCell2.setCellValue("STF level 2 NI Q2-09");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(20);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(21);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(22);
        headerCell2.setCellValue("Total Gross Adds");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(23);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(24);
        headerCell2.setCellValue("Dormant");
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
        headerCell2.setCellValue("Line Quality");
        headerCell2.setCellStyle(styles.get("header"));

        sheet2.addMergedRegion(CellRangeAddress.valueOf("$Q$1:$AD$1"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$I$2:$P$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$Q$2:$S$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$T$2:$V$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$W$2:$X$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$Y$2:$AC$2"));
        headerRow2 = sheet2.createRow(2);
        // Cell headerCell2;
          for (int i = 0; i < titles3.length; i++) {
              headerCell2 = headerRow2.createCell(i);
              headerCell2.setCellValue(titles3[i]);
              headerCell2.setCellStyle(styles.get("header"));
          }
    /******************************************************************************/
        //sheet2.addMergedRegion(CellRangeAddress.valueOf("$W$2:$X$2"));
	    LinkedHashMap<String,ArrayList<String> >  POSSplit= new  LinkedHashMap<String,ArrayList<String>>();
	    LinkedHashMap<String,ArrayList<String> >  RegionalSupervisorName= new  LinkedHashMap<String,ArrayList<String>>();
	    LinkedHashMap<String,ArrayList<String> >  SalesRegionName= new  LinkedHashMap<String,ArrayList<String>>();
	    LinkedHashMap<String,ArrayList<String> >  Status= new  LinkedHashMap<String,ArrayList<String>>();

	    ArrayList<String> posList=new ArrayList<String>();
	    posList.add("1");
	    posList.add("2");
	    posList.add("1");
	    posList.add("2");
	    posList.add("1");
	    posList.add("2");
	    posList.add("4");
	    posList.add("5");
	    posList.add("1");
	    posList.add("2");
	    posList.add("1");
	    posList.add("2");
	    
	    posList.add("10");
	    posList.add("5");
	    posList.add("15");
	    posList.add("3");
	    posList.add("8");
	    posList.add("2");
	    posList.add("1");
	    posList.add("13");
	    posList.add("15");
	    posList.add("3");
	   // posList.add("7");

	    //me.add("3");
	
	    POSSplit.put("10258.001",posList);
	    posList=new ArrayList<String>();
	    posList.add("4");
	    posList.add("5");
	    posList.add("1");
	    posList.add("2");
	    posList.add("1");
	    posList.add("2");
	    posList.add("4");
	    posList.add("5");
	    posList.add("1");
	    posList.add("2");
	    posList.add("1");
	    posList.add("2");
	    posList.add("10");
	    posList.add("5");
	    posList.add("15");
	    posList.add("3");
	    posList.add("8");
	    posList.add("2");
	    posList.add("1");
	    posList.add("13");
	    posList.add("15");
	    posList.add("3");
	    //posList.add("7");
	  //  me.add("2");
	    POSSplit.put("10260.001",posList);
	   //////////////////////////////////////////////
	    
	    ArrayList<String> regionalNameList=new ArrayList<String>();
	    regionalNameList.add("Abdel Rahman Abd Allah Mohamed");
	    regionalNameList.add("Ahmed Shawky Rady");
	    regionalNameList.add("Mohammed Ayman");
	    regionalNameList.add("Canal North & South Sinai");
	    regionalNameList.add("Shop No 1 Masaken El Amleen Hay El nour");
	    regionalNameList.add("Sharm El Sheikh");
	    regionalNameList.add("South Sinai");

	    RegionalSupervisorName.put("10258.001",regionalNameList);

	    regionalNameList=new ArrayList<String>();
	    regionalNameList.add("Abdel Rahman Abd Allah Mohamed");
	    regionalNameList.add("Ahmed Shawky Rady");
	    regionalNameList.add("Mohammed Ayman");
	    regionalNameList.add("Canal North & South Sinai");
	    regionalNameList.add("Shop No 1 Masaken El Amleen Hay El nour");
	    regionalNameList.add("Sharm El Sheikh");
	    regionalNameList.add("South Sinai");

	    RegionalSupervisorName.put("10260.001",regionalNameList);
	    ////////////////////////////////////////////////////////
	    ArrayList<String>  statusList=new ArrayList<String>();
	    statusList.add("ExPos");
	
	    Status.put("10258.001",statusList);
	    
	    statusList=new ArrayList<String>();
	    statusList.add("ExPos");

	    Status.put("10260.001",statusList);
///////////////////////////////////////////////////////////////
	  
	    Iterator it =POSSplit.keySet().iterator();

	    int h=3;
	    
        Cell revenueTargetCell;
        Cell ScratchCell;
        Row revenueTargetRow = null;
int t=0;
	    while(it.hasNext())
	    {
			    String alphakey= (String)	it.next();
			    
			    System.out.println("The key isssssssssssssssssssssss"+alphakey);
		      //  revenueTargetRow = sheet2.createRow(h);
		        revenueTargetRow = sheet2.createRow(h);

			    ArrayList   alphakeyvaluse1=   RegionalSupervisorName.get(alphakey);
			    ArrayList   alphakeyvaluse2=   SalesRegionName.get(alphakey);
			    ArrayList   alphakeyvaluse3=   Status.get(alphakey);

		
		
			    ArrayList   alphakeyvaluse=   POSSplit.get(alphakey);
			   
			   
			    revenueTargetCell=revenueTargetRow.createCell(2);
	        	revenueTargetCell.setCellValue(alphakey);

	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        	 t=0;
			       for (int i = 0; i < alphakeyvaluse1.size(); i++) 
				    {
			    	   if(i==2)
			    	   {
				        	
			    		   	t++;
			    	   }
			        	revenueTargetCell=revenueTargetRow.createCell(t);
			        	revenueTargetCell.setCellValue((String)  alphakeyvaluse1.get(i));
			        	revenueTargetCell.setCellStyle(styles.get("cell"));
			        	t++;
			    	
				    }
		        for (int i = 0; i < alphakeyvaluse.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+8);
		         	double finalValue=Double.parseDouble((String)  alphakeyvaluse.get(i));
		        	revenueTargetCell.setCellValue(finalValue);
		        	//revenueTargetCell.setCellValue((String)  alphakeyvaluse1.get(i));

		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
		        for (int i = 0; i < alphakeyvaluse3.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+30);
		         //	double finalValue=Double.parseDouble((String)  alphakeyvaluse3.get(i));
		        	revenueTargetCell.setCellValue((String)  alphakeyvaluse3.get(i));
		        	//revenueTargetCell.setCellValue((String)  alphakeyvaluse1.get(i));

		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }

			    h++;
	    }
	
    
		
		
	}

	private static void SIPReport(Workbook wb,Sheet sheet2)
	{
		PrintSetup printSetup = sheet2.getPrintSetup();
        printSetup.setLandscape(true);
        sheet2.setFitToPage(true);
        sheet2.setHorizontallyCenter(true);
        Map<String, CellStyle> styles = createStyles(wb);
        Row headerRow2 = null;
        /***************************Merging and Setting Borders*********************/
        Cell headerCell2;
        headerRow2=sheet2.createRow(1);
        headerCell2 = headerRow2.createCell(3);
        headerCell2.setCellValue("July");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(4);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(5);
        headerCell2.setCellValue("August");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(6);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(7);
        headerCell2.setCellValue("September");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(8);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(9);
        headerCell2.setCellValue("Q3-2009");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(10);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(11);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(12);
        headerCell2.setCellStyle(styles.get("header"));
        headerRow2 = sheet2.createRow(2);
        // Cell headerCell2;
          for (int i = 0; i < titles2.length; i++) {
              headerCell2 = headerRow2.createCell(i);
              headerCell2.setCellValue(titles2[i]);
              headerCell2.setCellStyle(styles.get("header"));
          }
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$D$2:$E$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$F$2:$G$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$H$2:$I$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$J$2:$M$2"));
/**************************************************************************/
	    LinkedHashMap<String,ArrayList<String> >  POSSplit= new  LinkedHashMap<String,ArrayList<String>>();
	    LinkedHashMap<String,ArrayList<String> >  RegionalSupervisorName= new  LinkedHashMap<String,ArrayList<String>>();
	    LinkedHashMap<String,ArrayList<String> >  SalesRegionName= new  LinkedHashMap<String,ArrayList<String>>();

	    ArrayList<String> posList=new ArrayList<String>();
	    posList.add("1");
	    posList.add("2");
	    posList.add("1");
	    posList.add("2");
	    posList.add("1");
	    posList.add("2");
	    
	    //me.add("3");
	
	    POSSplit.put("Abdel Rahman Abd Allah Mohamed",posList);
	    posList=new ArrayList<String>();
	    posList.add("4");
	    posList.add("5");
	    posList.add("1");
	    posList.add("2");
	    posList.add("1");
	    posList.add("2");
	   
	  //  me.add("2");
	    POSSplit.put("Ahmed Abdel Mawgoud Mostafa",posList);
	   //////////////////////////////////////////////
	    
	    ArrayList<String> regionalNameList=new ArrayList<String>();
	    regionalNameList.add("Ahmed Shawky Rady");
	    
	    RegionalSupervisorName.put("Abdel Rahman Abd Allah Mohamed",regionalNameList);

	    regionalNameList=new ArrayList<String>();
	    regionalNameList.add("John Zaher Botros");
	    
	    RegionalSupervisorName.put("Ahmed Abdel Mawgoud Mostafa",regionalNameList);
	    ////////////////////////////////////////////////////////
	    
	    ArrayList<String> salesRegionList=new ArrayList<String>();
	    salesRegionList.add("Canal North");
	    
	    SalesRegionName.put("Abdel Rahman Abd Allah Mohamed",salesRegionList);

	    salesRegionList=new ArrayList<String>();
	    salesRegionList.add("Upper Egypt");
	    
	    SalesRegionName.put("Ahmed Abdel Mawgoud Mostafa",salesRegionList);
	    ////////////////////////////////////////////////////////
	    Iterator it =POSSplit.keySet().iterator();

	    int h=3;
	    
        Cell revenueTargetCell;
        Cell ScratchCell;
        Row revenueTargetRow = null;

	    while(it.hasNext())
	    {
			    String alphakey= (String)	it.next();
			    
			    System.out.println("The key isssssssssssssssssssssss"+alphakey);
		      //  revenueTargetRow = sheet2.createRow(h);
		        revenueTargetRow = sheet2.createRow(h);

			    ArrayList   alphakeyvaluse1=   RegionalSupervisorName.get(alphakey);
			    ArrayList   alphakeyvaluse2=   SalesRegionName.get(alphakey);

		        revenueTargetCell=revenueTargetRow.createCell(0);
		        revenueTargetCell.setCellValue(alphakey);
	        	revenueTargetCell.setCellStyle(styles.get("header"));

		        revenueTargetCell=revenueTargetRow.createCell(1);
		        revenueTargetCell.setCellValue((String)  alphakeyvaluse1.get(0));
	        	revenueTargetCell.setCellStyle(styles.get("header"));

	        	   revenueTargetCell=revenueTargetRow.createCell(2);
			        revenueTargetCell.setCellValue((String)  alphakeyvaluse2.get(0));
		        	revenueTargetCell.setCellStyle(styles.get("header"));
			    int t=h+1;
			    ScratchCell=revenueTargetRow.createCell(9);
			    ScratchCell.setCellFormula("D"+t+"+F"+t+"+H"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(10);
			    ScratchCell.setCellFormula("E"+t+"+G"+t+"+I"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(11);
			    ScratchCell.setCellFormula("K"+t+"+J"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(12);
			    ScratchCell.setCellFormula("J"+t+"/L"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			  /*  
			    ScratchCell=revenueTargetRow.createCell(25);
			    ScratchCell.setCellFormula("Y"+t+"/U"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));*/
			    ArrayList   alphakeyvaluse=   POSSplit.get(alphakey);
			   
			   

		     
		        for (int i = 0; i < alphakeyvaluse.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+3);
		         	double finalValue=Double.parseDouble((String)  alphakeyvaluse.get(i));
		        	revenueTargetCell.setCellValue(finalValue);
		        	//revenueTargetCell.setCellValue((String)  alphakeyvaluse1.get(i));

		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
	

			    h++;
	    }
	
       
	    revenueTargetRow = sheet2.createRow(h);
        revenueTargetCell=revenueTargetRow.createCell(2);

       	revenueTargetCell.setCellValue("Total");
       	for(int i=0;i<columns.length;i++)
       	{ 
       		revenueTargetCell=revenueTargetRow.createCell(i+3);
       		revenueTargetCell.setCellFormula("SUM("+columns[i]+"4:"+columns[i]+h+")");
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