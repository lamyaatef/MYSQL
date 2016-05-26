package com.mobinil.sds.core.utilities;

import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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


public class QualityTeamSIPReport 
{
	private static final String[] titles={
		"Revenue Target","Gross Adds STF Level 1","Q2-2009 NI","Line Quality"};
		
		

	 private static final String[] columns={
		"D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
		
		 
		 
	 };
	 private static final String[] titles2={
		"PosCode","PosENm","Field Rep Name","JulyRevenue","JulyLines","AugustRevenue",
		"AugustLines","SeptemberRevenue","SeptLines","Total ARPU  Q3-09","TotalLines Q3-09",
		"Total Q2-2009","% of Q3-2009 from Q2-2009","July","August","September","April","May",
		"June","Q2-2009 NI","Total Q3-2009","April","May","June","Total Dormant","% of Dormant"
		 
		 
	 };


	public static String mainExportQualityTeamSIPReport(String path,String quarter,
	      HashMap<String,LinkedHashMap<String,ArrayList<String> >> qualityLinkedHashMapes) throws Exception 
	{
		 Workbook wb;
	
	       wb = (Workbook) new HSSFWorkbook();
	       
		
		Sheet sheet2=  wb.createSheet("Quality Team SIP Report Details");

		
		
		SIPReport( wb, sheet2,
		      qualityLinkedHashMapes.get ( "fieldRepName"),
		      qualityLinkedHashMapes.get ( "revenueREport"),
		      qualityLinkedHashMapes.get ( "GrossAdds"),
		      qualityLinkedHashMapes.get ( "Dormant"),
		      qualityLinkedHashMapes.get ( "notIntial"));

		for(int i=0;i<40;i++)
		{
			//sheet.autoSizeColumn((short)i); //adjust width of the first column
			sheet2.autoSizeColumn((short)i); //adjust width of the second column
		}
		////////////////////////////////////////////////////////////////////////////////
	    
		
		    
		// Write the output to a file
		Date date = new Date();
		
		StringBuffer filePath = new StringBuffer("");    
		filePath.append(path);
		  filePath.append("QualityTeam_");
		  filePath.append(quarter);
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
		
		FileOutputStream out = new FileOutputStream(excelDistFile);
		wb.write(out);
		out.close();

		return filePath.toString();
		
	}
	private static void SIPReport(Workbook wb,Sheet sheet2,
			LinkedHashMap<String,ArrayList<String> >  fieldRepName,
			LinkedHashMap<String,ArrayList<String> >  revenueREport,
			LinkedHashMap<String,ArrayList<String> >  GrossAdds,
			LinkedHashMap<String,ArrayList<String> >  Dormant,LinkedHashMap<String,ArrayList<String> >  Q3HashMap
			)
	{
		PrintSetup printSetup = sheet2.getPrintSetup();
        printSetup.setLandscape(true);
        sheet2.setFitToPage(true);
        sheet2.setHorizontallyCenter(true);
        Map<String, CellStyle> styles = createStyles(wb);

       Row headerRow2= sheet2.createRow(1);
       Cell headerCell2;
/**********************Merger and setting Borders********************/
        headerCell2 = headerRow2.createCell(3);
        headerCell2.setCellValue("Revenue Target");
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
        headerCell2.setCellStyle(styles.get("header"));
        
        headerCell2 = headerRow2.createCell(13);
        headerCell2.setCellValue("Gross Adds STF Level 1");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(14);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(15);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(16);
        headerCell2.setCellValue("Q2-2009 NI");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(17);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(18);
        headerCell2.setCellStyle(styles.get("header"));
    
        headerCell2 = headerRow2.createCell(19);
        headerCell2.setCellValue("");
        headerCell2.setCellStyle(styles.get("header"));  
        headerCell2 = headerRow2.createCell(20);
        headerCell2.setCellValue("");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(21);
        headerCell2.setCellValue("Dormant Q2-2009");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(22);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(23);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(24);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(25);
        headerCell2.setCellStyle(styles.get("header"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$D$2:$M$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$N$2:$P$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$Q$2:$S$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$V$2:$Z$2"));
        
 /////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////
        headerRow2 = sheet2.createRow(2);
        // Cell headerCell2;
          for (int i = 0; i < titles2.length; i++) {
              headerCell2 = headerRow2.createCell(i);
              headerCell2.setCellValue(titles2[i]);
              headerCell2.setCellStyle(styles.get("header"));
          }
          
//          LinkedHashMap<String,ArrayList<String> >  fieldRepName= new  LinkedHashMap<String,ArrayList<String>>();
//  		
//  	    ArrayList<String>  fieldRepNameList=new ArrayList<String>();
//    	  fieldRepNameList.add("16646.001");
//    	  fieldRepNameList.add("Ibrahim el Shirbini");
//  	    
//  	fieldRepName.put("Shad Phone",fieldRepNameList);
//  	    
//  	fieldRepNameList=new ArrayList<String>();
//  			fieldRepNameList.add("16646.001");
//  			fieldRepNameList.add("Ibrahim el Shirbini");
//  	    
//  	  fieldRepName.put("Kaml Kamal",fieldRepNameList);
//	    LinkedHashMap<String,ArrayList<String> >  revenueREport= new  LinkedHashMap<String,ArrayList<String>>();
//		
//	    ArrayList<String> revenueList=new ArrayList<String>();
//	    revenueList.add("1");
//	    revenueList.add("2");
//	    revenueList.add("1");
//	    revenueList.add("2");
//	    revenueList.add("1");
//	    revenueList.add("2");
//	    revenueList.add("1");
//	    
//	    //me.add("3");
//	
//	    revenueREport.put("Shad Phone",revenueList);
//	    revenueList=new ArrayList<String>();
//	    revenueList.add("4");
//	    revenueList.add("5");
//	    revenueList.add("1");
//	    revenueList.add("2");
//	    revenueList.add("1");
//	    revenueList.add("2");
//	    revenueList.add("1");
//	   
//	  //  me.add("2");
//	    revenueREport.put("Kaml Kamal",revenueList);
//	   
//	    	
//	   // 	System.out.println("The hash map  is"+revenueREport.get("ALPHA"));
//LinkedHashMap<String,ArrayList<String> >  GrossAdds= new  LinkedHashMap<String,ArrayList<String>>();
//		
//	    ArrayList<String>  grossAddList=new ArrayList<String>();
//	    grossAddList.add("1");
//	    grossAddList.add("5");
//	    grossAddList.add("3");
//	    GrossAdds.put("Shad Phone",grossAddList);
//	    
//	    grossAddList=new ArrayList<String>();
//	    grossAddList.add("4");
//	    grossAddList.add("10");
//	    grossAddList.add("7");
//	    GrossAdds.put("Kaml Kamal",grossAddList);
/////////////////////////////////////////////////////////////////
//	    
//	    LinkedHashMap<String,ArrayList<String> >  Dormant= new  LinkedHashMap<String,ArrayList<String>>();
//	 			
//	 		    ArrayList<String>  dormantList=new ArrayList<String>();
//	 		    dormantList.add("10");
//	 		   dormantList.add("4");
//	 		  dormantList.add("10");
//	 		 dormantList.add("7");
//	 		   Dormant.put("Shad Phone",dormantList);
//	 		    
//	 		   dormantList=new ArrayList<String>();
//	 		   dormantList.add("14");
//	 		  dormantList.add("1");
//	 		 dormantList.add("5");
//	 		dormantList.add("3");
//	 		   //dormantList.add("3");
//	 		   Dormant.put("Kaml Kamal",dormantList);
//
//
//	 		
////////////////////////////////////////////////////////////
//	 		   LinkedHashMap<String,ArrayList<String> >  Q3HashMap= new  LinkedHashMap<String,ArrayList<String>>();
//	 			
//	 		    ArrayList<String>  Q3List=new ArrayList<String>();
//	 		    
//	 		   Q3List.add("14");
//	 		   Q3List.add("20");
//	 		  Q3List.add("10");
//		 		 Q3List.add("7");
//	 		  Q3HashMap.put("Shad Phone",Q3List);
//	 		    
//	 		  Q3List=new ArrayList<String>();
//		 
//	 		 Q3List.add("10");
//	 		 Q3List.add("7");
//	 		 Q3List.add("14");
//	 		   Q3List.add("20");
//	 		
//	 		Q3HashMap.put("Kaml Kamal",Q3List);
	    Iterator it =revenueREport.keySet().iterator();

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
			    ArrayList   alphakeyvaluse3=   fieldRepName.get(alphakey);
			    revenueTargetCell=revenueTargetRow.createCell(0);
			    revenueTargetCell.setCellValue((String) alphakeyvaluse3.get(0));
	        	revenueTargetCell.setCellStyle(styles.get("header"));

	        	 revenueTargetCell=revenueTargetRow.createCell(2);
				    revenueTargetCell.setCellValue((String) alphakeyvaluse3.get(1));
		        	revenueTargetCell.setCellStyle(styles.get("header"));

			    
			    
			    revenueTargetCell=revenueTargetRow.createCell(1);
			    revenueTargetCell.setCellValue(alphakey);
	        	revenueTargetCell.setCellStyle(styles.get("header"));

			    int t=h+1;
			    ScratchCell=revenueTargetRow.createCell(9);
			    ScratchCell.setCellFormula("D"+t+"+F"+t+"+H"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(10);
			    ScratchCell.setCellFormula("E"+t+"+G"+t+"+I"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(12);
			    ScratchCell.setCellFormula("(J"+t+"-L"+t+")/L"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(20);
			    ScratchCell.setCellFormula("N"+t+"+O"+t+"+P"+t+"+T"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(25);
			    ScratchCell.setCellFormula("Y"+t+"/U"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    ArrayList   alphakeyvaluse1=  GrossAdds.get(alphakey);
			    ArrayList   alphakeyvaluse=   revenueREport.get(alphakey);
			   
			    ArrayList   alphakeyvaluse2=   Dormant.get(alphakey);

			   /// ArrayList   alphakeyvaluse3=   GrossAdds2.get(alphakey);
			    ArrayList   alphakeyvaluse4=   Q3HashMap.get(alphakey);

		        for (int i = 0; i < alphakeyvaluse.size(); i++) 
			    {
		        	if(i+3==9)
		        	{
		        		  revenueTargetCell=revenueTargetRow.createCell(11);
					    	double finalValue=Double.parseDouble((String)  alphakeyvaluse.get(i));
				        	revenueTargetCell.setCellValue(finalValue);				        
				        	revenueTargetCell.setCellStyle(styles.get("cell"));
		        		
		        	}
		        	else
		        	{
			        revenueTargetCell=revenueTargetRow.createCell(i+3);
			    	double finalValue=Double.parseDouble((String)  alphakeyvaluse.get(i));
		        	revenueTargetCell.setCellValue(finalValue);				        
		        	revenueTargetCell.setCellStyle(styles.get("cell"));
		        	}
			    }
		        for (int i = 0; i < alphakeyvaluse1.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+13);
		         	double finalValue=Double.parseDouble((String)  alphakeyvaluse1.get(i));
		        	revenueTargetCell.setCellValue(finalValue);
		        	//revenueTargetCell.setCellValue((String)  alphakeyvaluse1.get(i));

		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
		        for (int i = 0; i < alphakeyvaluse2.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+21);

		         	double finalValue=Double.parseDouble((String)  alphakeyvaluse2.get(i));
		        	revenueTargetCell.setCellValue(finalValue);		
		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
		        for (int i = 0; i < alphakeyvaluse4.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+16);
		         	double finalValue=Double.parseDouble((String)  alphakeyvaluse4.get(i));
		        	revenueTargetCell.setCellValue(finalValue);
		        	revenueTargetCell.setCellStyle(styles.get("cell"));

		        	//revenueTargetCell.setCellValue((String)  alphakeyvaluse3.get(i));
			        
			    }

			    h++;
	    }
	
       
	    revenueTargetRow = sheet2.createRow(h);
        revenueTargetCell=revenueTargetRow.createCell(2);
    	revenueTargetCell.setCellStyle(styles.get("header"));

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

