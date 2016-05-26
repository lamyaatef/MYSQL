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


public class SuperdealersReport 
{
	private static final String[] titles2={
		"Scode","SDName","SD Acc.Mgr","Total Dormants","Total Gross Adds","% of Dormant","CIF Gross Adds- Q2 Dormant",
	"Expos","Non Expos","Total POS","% of Ex Pos"	
	
	}
		
		
	;
	 private static final String[] titles = {
      "DCM Name","Scratch","E-Topup","Total Revenue","% of Etopup","Total Gross Adds"
 };
	 private static final String[] columns={
		"B","C","D","E","F"
		
		 
		 
	 };

	 private static final String[] columns2={
			"D","E","F","G","H","I","J","K"
			
			 
			 
		 };
	 private static final String[] columns3={
			"D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X"
			
			 
			 
		 };
	 private static final String[] titles3 = {"Scode","SDName","SD Acc. Mgr","July","August",
		 "September","NI Q2 09","Total Gross Adds","April","May","June","Total Dormant","% of Dormant","CIF Gross Adds - Dormant"
 ,"ExPos","Non ExPos","ExPos","Non ExPos","ExPos","Non ExPos","ExPos","Non ExPos","Total POS","% of ExPos"
	 };
	public static String mainExportSuperdealersReport(String path,String quartar,HashMap<String,LinkedHashMap<String,ArrayList<String> >> sdLinkedHashMapes) throws Exception 
	{
		 Workbook wb;
	
	        wb = (Workbook) new HSSFWorkbook();
	        
		
		Sheet sheet2=  wb.createSheet("Dist SIP Report");
		Sheet sheet1=  wb.createSheet("Super Dealers SIP Reports");
		Sheet sheet3=  wb.createSheet("SIP Report Details");
		
		firstSuperdealersReport( wb, sheet1,
		      sdLinkedHashMapes.get ( "firstSDdetails" ),
		      sdLinkedHashMapes.get ( "firstGrossAdds" ),
		      sdLinkedHashMapes.get ( "firstDormant" ),
		      sdLinkedHashMapes.get ( "firstPOS" )
		);
		
		SIPReportDetails(wb,sheet3,
              sdLinkedHashMapes.get ( "secondSDdetails" ),
              sdLinkedHashMapes.get ( "secondGrossAdds" ),
              sdLinkedHashMapes.get ( "secondDormant" ),
              sdLinkedHashMapes.get ( "secondPOS" ));
		
		
		
		SIPReport( wb, sheet2,
		      sdLinkedHashMapes.get ( "thirdrevenueREport" ),
              sdLinkedHashMapes.get ( "thirdGrossAdds" )    
		);
		
		for(int i=0;i<40;i++)
		{
			sheet1.autoSizeColumn((short)i); //adjust width of the first column
			sheet2.autoSizeColumn((short)i); //adjust width of the second column
			sheet3.autoSizeColumn((short)i); //adjust width of the third column

		}
		sheet2.createFreezePane(1, 3);
		    
		////////////////////////////////////////////////////////////////////////////////
	    
		
		    
//		Date date = new Date();
//		String file = "Superdealers_Q3_"+date.getHours()+"-"+date.getMinutes()+"-"+date.getSeconds()+".xls";
//		StringBuffer filePath = new StringBuffer("");    
//		  filePath.append(new File("").getAbsolutePath());
//		  filePath.append(System.getProperty("file.separator"));
//		  filePath.append("SDS");
//		  filePath.append(System.getProperty("file.separator"));
//		  filePath.append("download");
//		  filePath.append(System.getProperty("file.separator"));
//		  filePath.append(file);
		
		Date date = new Date();
        
        StringBuffer filePath = new StringBuffer("");    
        filePath.append(path);          
          filePath.append("Superdealers_");
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

		
		FileOutputStream out = new FileOutputStream(excelDistFile);
		wb.write(out);
		out.close();

		return filePath.toString();
		
	}
	private static void SIPReportDetails(Workbook wb,Sheet sheet2,
	      LinkedHashMap<String,ArrayList<String> >  SDdetails,
          LinkedHashMap<String,ArrayList<String> >  Dormant,
          LinkedHashMap<String,ArrayList<String> >  GrossAdds,
          LinkedHashMap<String,ArrayList<String> >  POS      
	)
	{
		PrintSetup printSetup = sheet2.getPrintSetup();
        printSetup.setLandscape(true);
        sheet2.setFitToPage(true);
        sheet2.setHorizontallyCenter(true);
        Map<String, CellStyle> styles = createStyles(wb);
        Row headerRow2 = sheet2.createRow(2);
        Cell headerCell2;
        for (int i = 0; i < titles3.length; i++) {
            headerCell2 = headerRow2.createCell(i);
            headerCell2.setCellValue(titles3[i]);
            headerCell2.setCellStyle(styles.get("header"));
        }
     
         headerRow2 = sheet2.createRow(0);
        //headerRow2= sheet2.createRow(2);

        headerCell2 = headerRow2.createCell(14);
        headerCell2.setCellValue("POS Split");
        headerCell2.setCellStyle(styles.get("header"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$O$1:$X$1"));

        headerRow2= sheet2.createRow(1);
        
       

        
        headerCell2 = headerRow2.createCell(3);
        headerCell2.setCellValue("Gross Adds (CIF)");
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
        headerCell2.setCellValue("Dormant");
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
        headerCell2.setCellValue("Line Quality");
        headerCell2.setCellStyle(styles.get("header"));
        
        headerCell2 = headerRow2.createCell(14);
        headerCell2.setCellValue("July");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(15);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(16);
        headerCell2.setCellValue("August");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(17);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(18);
        headerCell2.setCellValue("September");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(19);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(20);
        headerCell2.setCellValue("Q3");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(21);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(22);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(23);
        headerCell2.setCellStyle(styles.get("header"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$D$2:$H$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$I$2:$M$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$O$2:$P$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$Q$2:$R$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$S$2:$T$2"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$U$2:$X$2"));





	 		

	    Iterator it =Dormant.keySet().iterator();

	    int h=3;
	    
        Cell revenueTargetCell;
        Cell ScratchCell;
        Row revenueTargetRow = null;

	    while(it.hasNext())
	    {
			    String alphakey= (String)	it.next();
			    ArrayList alphakeyvalue3= SDdetails.get(alphakey);

			    System.out.println("The key isssssssssssssssssssssss"+alphakey);
		      //  revenueTargetRow = sheet2.createRow(h);
		        revenueTargetRow = sheet2.createRow(h);

		        revenueTargetCell=revenueTargetRow.createCell(0);
			    revenueTargetCell.setCellValue((String)alphakeyvalue3.get(0));
	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        	
	        	 revenueTargetCell=revenueTargetRow.createCell(2);
				    revenueTargetCell.setCellValue((String) alphakeyvalue3.get(1) );
		        	revenueTargetCell.setCellStyle(styles.get("cell"));
		        	
			    revenueTargetCell=revenueTargetRow.createCell(1);
			    revenueTargetCell.setCellValue(alphakey);
	        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    int t=h+1;
			    ScratchCell=revenueTargetRow.createCell(7);
			    ScratchCell.setCellFormula("D"+t+"+E"+t+"+F"+t+"+G"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(11);
			    ScratchCell.setCellFormula("I"+t+"+J"+t+"+K"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(12);
			    ScratchCell.setCellFormula("L"+t+"/H"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(13);
			    ScratchCell.setCellFormula("H"+t+"-L"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(20);
			    ScratchCell.setCellFormula("O"+t+"+Q"+t+"+S"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(21);
			    ScratchCell.setCellFormula("P"+t+"+R"+t+"+T"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(22);
			    ScratchCell.setCellFormula("U"+t+"+V"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(23);
			    ScratchCell.setCellFormula("U"+t+"/W"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    ArrayList   alphakeyvaluse1=  Dormant.get(alphakey);
			    ArrayList   alphakeyvaluse=   GrossAdds.get(alphakey);
			    ArrayList alphakeyvalue2= POS.get(alphakey);
			   

			   /// ArrayList   alphakeyvaluse3=   GrossAdds2.get(alphakey);

		        for (int i = 0; i < alphakeyvaluse.size(); i++) 
			    {
		       
		        	
		        
			        revenueTargetCell=revenueTargetRow.createCell(i+3);
			    	double finalValue=Double.parseDouble((String)  alphakeyvaluse.get(i));
		        	revenueTargetCell.setCellValue(finalValue);				        
		        	revenueTargetCell.setCellStyle(styles.get("cell"));
		        	
			    }
		        for (int i = 0; i < alphakeyvaluse1.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+8);
		         	double finalValue=Double.parseDouble((String)  alphakeyvaluse1.get(i));
		        	revenueTargetCell.setCellValue(finalValue);
		        	//revenueTargetCell.setCellValue((String)  alphakeyvaluse1.get(i));

		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
		       for (int i = 0; i < alphakeyvalue2.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+14);
		         	double finalValue=Double.parseDouble((String)  alphakeyvalue2.get(i));
		        	revenueTargetCell.setCellValue(finalValue);
		        	//revenueTargetCell.setCellValue((String)  alphakeyvaluse1.get(i));

		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
		     /*   for (int i = 0; i < alphakeyvalue2.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+7);

		         	double finalValue=Double.parseDouble((String)  alphakeyvalue2.get(i));
		        	revenueTargetCell.setCellValue(finalValue);		
		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
		*/
			    h++;
	    }
	
       
	    revenueTargetRow = sheet2.createRow(h);
        revenueTargetCell=revenueTargetRow.createCell(2);

       	revenueTargetCell.setCellValue("Total");
    	revenueTargetCell.setCellStyle(styles.get("header"));

       	for(int i=0;i<columns3.length;i++)
       	{ 
       		revenueTargetCell=revenueTargetRow.createCell(i+3);
       		revenueTargetCell.setCellFormula("SUM("+columns3[i]+"4:"+columns3[i]+h+")");
	        	revenueTargetCell.setCellStyle(styles.get("header"));

       		
       		
       	}
		
		
	}
	/////////////////////////////////////////////////////////////////
	private static void firstSuperdealersReport(Workbook wb,Sheet sheet2,
	      LinkedHashMap<String,ArrayList<String> >  SDdetails,
	      LinkedHashMap<String,ArrayList<String> >  Dormant,
	      LinkedHashMap<String,ArrayList<String> >  GrossAdds,
	      LinkedHashMap<String,ArrayList<String> >  POS
	)
	{
		PrintSetup printSetup = sheet2.getPrintSetup();
        printSetup.setLandscape(true);
        sheet2.setFitToPage(true);
        sheet2.setHorizontallyCenter(true);
        Map<String, CellStyle> styles = createStyles(wb);
        Row headerRow2 = sheet2.createRow(3);
        Cell headerCell2;
        for (int i = 0; i < titles2.length; i++) {
            headerCell2 = headerRow2.createCell(i);
            headerCell2.setCellValue(titles2[i]);
            headerCell2.setCellStyle(styles.get("header"));
        }
        headerRow2= sheet2.createRow(2);
        
        headerCell2 = headerRow2.createCell(3);
        headerCell2.setCellValue("Gross Adds (CIF)");
        headerCell2.setCellStyle(styles.get("header"));
        
        headerCell2 = headerRow2.createCell(4);
        headerCell2.setCellValue("Dormant Q2");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(5);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(6);
        headerCell2.setCellValue("Line Quality");
        headerCell2.setCellStyle(styles.get("header"));
        
        headerCell2 = headerRow2.createCell(7);
        headerCell2.setCellValue("POS Split");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(8);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(9);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(10);
        headerCell2.setCellStyle(styles.get("header"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$E$3:$F$3"));

        sheet2.addMergedRegion(CellRangeAddress.valueOf("$H$3:$K$3"));







	 		

	    Iterator it =Dormant.keySet().iterator();

	    int h=4;
	    
        Cell revenueTargetCell;
        Cell ScratchCell;
        Row revenueTargetRow = null;

	    while(it.hasNext())
	    {
			    String alphakey= (String)	it.next();
			    
			    System.out.println("The key isssssssssssssssssssssss"+alphakey);
		      //  revenueTargetRow = sheet2.createRow(h);
		        revenueTargetRow = sheet2.createRow(h);
			    ArrayList alphakeyvalue3= SDdetails.get(alphakey);
			    
			    revenueTargetCell=revenueTargetRow.createCell(0);
			    revenueTargetCell.setCellValue((String) alphakeyvalue3.get(0));
	        	revenueTargetCell.setCellStyle(styles.get("cell"));
	        	
	        	 revenueTargetCell=revenueTargetRow.createCell(2);
				 revenueTargetCell.setCellValue((String) alphakeyvalue3.get(1));
				 revenueTargetCell.setCellStyle(styles.get("cell"));
		        	
			    revenueTargetCell=revenueTargetRow.createCell(1);
			    revenueTargetCell.setCellValue(alphakey);
	        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    int t=h+1;
			    ScratchCell=revenueTargetRow.createCell(5);
			    ScratchCell.setCellFormula("E"+t+"/D"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(6);
			    ScratchCell.setCellFormula("D"+t+"-E"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(9);
			    ScratchCell.setCellFormula("H"+t+"+I"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(10);
			    ScratchCell.setCellFormula("H"+t+"/J"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    ArrayList   alphakeyvaluse1=  GrossAdds.get(alphakey);
			    ArrayList   alphakeyvaluse=   Dormant.get(alphakey);
			    ArrayList alphakeyvalue2= POS.get(alphakey);
			   

			   /// ArrayList   alphakeyvaluse3=   GrossAdds2.get(alphakey);

		        for (int i = 0; i < alphakeyvaluse.size(); i++) 
			    {
		       
		        	
		        
			        revenueTargetCell=revenueTargetRow.createCell(i+4);
			    	double finalValue=Double.parseDouble((String)  alphakeyvaluse.get(i));
		        	revenueTargetCell.setCellValue(finalValue);				        
		        	revenueTargetCell.setCellStyle(styles.get("cell"));
		        	
			    }
		        for (int i = 0; i < alphakeyvaluse1.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+3);
		         	double finalValue=Double.parseDouble((String)  alphakeyvaluse1.get(i));
		        	revenueTargetCell.setCellValue(finalValue);
		        	//revenueTargetCell.setCellValue((String)  alphakeyvaluse1.get(i));

		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
		        for (int i = 0; i < alphakeyvalue2.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+7);

		         	double finalValue=Double.parseDouble((String)  alphakeyvalue2.get(i));
		        	revenueTargetCell.setCellValue(finalValue);		
		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
		
			    h++;
	    }
	
       
	    revenueTargetRow = sheet2.createRow(h);
        revenueTargetCell=revenueTargetRow.createCell(2);

       	revenueTargetCell.setCellValue("Total");
    	revenueTargetCell.setCellStyle(styles.get("header"));

       	for(int i=0;i<columns2.length;i++)
       	{ 
       		revenueTargetCell=revenueTargetRow.createCell(i+3);
       		revenueTargetCell.setCellFormula("SUM("+columns2[i]+"5:"+columns2[i]+h+")");
	        	revenueTargetCell.setCellStyle(styles.get("header"));

       		
       		
       	}
		
		
	}
	private static void SIPReport(Workbook wb,Sheet sheet2,
	      LinkedHashMap<String,ArrayList<String> >  revenueREport,
          LinkedHashMap<String,ArrayList<String> >  GrossAdds      
	)
	{
		PrintSetup printSetup = sheet2.getPrintSetup();
        printSetup.setLandscape(true);
        sheet2.setFitToPage(true);
        sheet2.setHorizontallyCenter(true);
        Map<String, CellStyle> styles = createStyles(wb);

        

        Row headerRow2 = sheet2.createRow(2);
        Cell headerCell2;
        for (int i = 0; i < titles.length; i++) {
            headerCell2 = headerRow2.createCell(i);
            headerCell2.setCellValue(titles[i]);
            headerCell2.setCellStyle(styles.get("header"));
        }
        headerRow2= sheet2.createRow(1);
        
        headerCell2 = headerRow2.createCell(1);
        headerCell2.setCellValue("Revenue Target");
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(2);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(3);
        headerCell2.setCellStyle(styles.get("header"));
        headerCell2 = headerRow2.createCell(4);
        headerCell2.setCellStyle(styles.get("header"));
        
        headerCell2 = headerRow2.createCell(5);
        headerCell2.setCellValue("Gross Adds (Act)");
        headerCell2.setCellStyle(styles.get("header"));
        sheet2.addMergedRegion(CellRangeAddress.valueOf("$B$2:$E$2"));

	    



	 		

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

			    revenueTargetCell=revenueTargetRow.createCell(0);
			    revenueTargetCell.setCellValue(alphakey);
	        //	revenueTargetCell.setCellStyle(styles.get("cell"));

			    int t=h+1;
			    ScratchCell=revenueTargetRow.createCell(3);
			    ScratchCell.setCellFormula("B"+t+"+C"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    
			    ScratchCell=revenueTargetRow.createCell(4);
			    ScratchCell.setCellFormula("C"+t+"/D"+t);
			    ScratchCell.setCellStyle(styles.get("cell"));
			    ArrayList   alphakeyvaluse1=  GrossAdds.get(alphakey);
			    ArrayList   alphakeyvaluse=   revenueREport.get(alphakey);
			   

			   /// ArrayList   alphakeyvaluse3=   GrossAdds2.get(alphakey);

		        for (int i = 0; i < alphakeyvaluse.size(); i++) 
			    {
		       
		        	
		        
			        revenueTargetCell=revenueTargetRow.createCell(i+1);
			    	double finalValue=Double.parseDouble((String)  alphakeyvaluse.get(i));
		        	revenueTargetCell.setCellValue(finalValue);				        
		        	revenueTargetCell.setCellStyle(styles.get("cell"));
		        	
			    }
		        for (int i = 0; i < alphakeyvaluse1.size(); i++) 
			    {
		        	revenueTargetCell=revenueTargetRow.createCell(i+5);
		         	double finalValue=Double.parseDouble((String)  alphakeyvaluse1.get(i));
		        	revenueTargetCell.setCellValue(finalValue);
		        	//revenueTargetCell.setCellValue((String)  alphakeyvaluse1.get(i));

		        	revenueTargetCell.setCellStyle(styles.get("cell"));

			    }
		        /*
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
*/
			    h++;
	    }
	
       
	    revenueTargetRow = sheet2.createRow(h);
        revenueTargetCell=revenueTargetRow.createCell(0);

       	revenueTargetCell.setCellValue("Total");
    	revenueTargetCell.setCellStyle(styles.get("header"));

       	for(int i=0;i<columns.length;i++)
       	{ 
       		revenueTargetCell=revenueTargetRow.createCell(i+1);
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
