package com.mobinil.sds.core.system.spreport;
import java.sql.*;
import com.mobinil.sds.core.utilities.*;
/*
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
*/
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;




public class RevenueReportEngine {

	public static void createHeaderMergedCell(Sheet sheet, String cellText, Row row,int rowIndex, int colIndex, CellStyle style )
	{


		Cell cell = row.createCell( colIndex );
		cell.setCellStyle(style);
		cell.setCellValue(cellText);



		sheet.addMergedRegion(new CellRangeAddress(
	            rowIndex, //first row (0-based)
	            rowIndex, //last row  (0-based)
	            colIndex, //first column (0-based)
	            (colIndex +2)  //last column  (0-based)
	    ));


		//sheet.addMergedRegion(new Region( rowIndex, colIndex,  rowIndex,   (colIndex +2) ));
	}

	public static void createHeader ( String cellText, Row row, int colIndex, CellStyle style )
	{
		Cell cell = row.createCell( colIndex );
		cell.setCellStyle(style);
		cell.setCellValue(cellText);
	}

	public static void createHeader ( float cellValue, Row row, int colIndex, CellStyle style )
	{
		Cell cell = row.createCell( colIndex );
		cell.setCellStyle(style);

		cell.setCellValue(cellValue);


	}
	public static void printFooter(Row row ,  float[] sumValArray)
	{
		//row.createCell(0).setCellValue("Total");
		if (sumValArray ==null)
			return ;

		createHeader("Total",row,0,grayHeadLineBlackFontStyle);

		for (int i=0 ; i <sumValArray.length;i++)
		{
			float value = sumValArray[i];
			createHeader(value,row,i+1,grayHeadLineBlackFontStyleRightAligned);
		}

	}

	public static Font firstCellFont = null;
	public static CellStyle grayHeadLineStyle=null;
	public static Font smallBlackCellFont = null;
	public static CellStyle activationOrangeStyle  = null;
	public static CellStyle grayHeadLineBlackFontStyle = null;
	public static CellStyle  grayHeadLineBlackFontStyleRightAligned=null;
	public static CellStyle  simpleStyle=null;

	public static void prepareStyle(Workbook wb)
	{
		DataFormat format = wb.createDataFormat();

		firstCellFont = wb.createFont();
		firstCellFont.setFontHeightInPoints((short)11);
		firstCellFont.setColor(HSSFColor.WHITE.index);



		grayHeadLineStyle = wb.createCellStyle();

		grayHeadLineStyle.setAlignment( (short)2 );
		grayHeadLineStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		grayHeadLineStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		grayHeadLineStyle.setFont(firstCellFont);


		smallBlackCellFont = wb.createFont();
		smallBlackCellFont.setFontHeightInPoints((short)8);
		//smallBlackCellFont.setColor(HSSFColor.WHITE.index);


		grayHeadLineBlackFontStyle = wb.createCellStyle();
		grayHeadLineBlackFontStyle.setAlignment( (short)2 );
		grayHeadLineBlackFontStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		grayHeadLineBlackFontStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		grayHeadLineBlackFontStyle.setFont(smallBlackCellFont);


		grayHeadLineBlackFontStyleRightAligned = wb.createCellStyle();
		grayHeadLineBlackFontStyleRightAligned.setAlignment( (short)3 );
		grayHeadLineBlackFontStyleRightAligned.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		grayHeadLineBlackFontStyleRightAligned.setFillPattern(CellStyle.SOLID_FOREGROUND);
		grayHeadLineBlackFontStyleRightAligned.setFont(smallBlackCellFont);

		grayHeadLineBlackFontStyleRightAligned.setDataFormat(format.getFormat("###,###,##0"));
		//.setDataFormat(format.getFormat("##,####0.0000")


		//firstCellFont.setcolo
		 activationOrangeStyle = wb.createCellStyle();
        activationOrangeStyle.setAlignment(CellStyle.ALIGN_CENTER);
        activationOrangeStyle.setFont(firstCellFont);
        activationOrangeStyle.setFillForegroundColor(HSSFColor.ORANGE.index);
        activationOrangeStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        simpleStyle = wb.createCellStyle();
        simpleStyle.setDataFormat(format.getFormat("###,###,##0"));



	}



	public static void writeSheet(String sheetName, String sql,Workbook wb, Connection con)throws Exception
	{
	    //org.apache.poi.hssf.usermodel.HSSFDataFormat.

		System.out.println(sheetName);
		Sheet s = wb.createSheet(sheetName);
		// declare a row object reference



		s.setDefaultColumnWidth( 20);


		//Connection con = Utility.getConnection();
		Statement stat = con.createStatement();


		ResultSet rs = stat.executeQuery(sql);
		String currentGroup = null;

		int beginIndex  =0 ;

		float sumValArray [] = null;
		while (rs.next())
		{

			String activationMonth = rs.getString("Activation_Month");

			if (currentGroup ==null || currentGroup.compareTo(activationMonth)!=0)
			{
				if (sumValArray == null)
				{
					//dont do anything
				}
				else
				{
					//print footer
					Row row = s.createRow(beginIndex);
					printFooter(row,sumValArray);

                                            // Medhat Amin
					//total arpu equation
					int t=beginIndex+1;
					Cell cell =row.createCell(21);
					 cell.setCellFormula("B"+t+"+E"+t+"+H"+t+"+K"+t);
					cell.setCellStyle(grayHeadLineBlackFontStyle);

					cell =row.createCell(22);
					cell.setCellFormula("C"+t+"+D"+t+"+F"+t+"+G"+t+"+I"+t+"+J"+t+"+L"+t+"+M"+t);
					cell.setCellStyle(grayHeadLineBlackFontStyle);

					cell =row.createCell(23);
				    cell.setCellFormula("V"+t+"/W"+t);
					cell.setCellStyle(grayHeadLineBlackFontStyle);
					beginIndex++;
				}

				//init array
				sumValArray = new float[18];

				currentGroup = activationMonth;
				System.out.println("new group "+ activationMonth);

				if (beginIndex !=0)
					beginIndex++;


				Row row = s.createRow(beginIndex);
				int colIndex = 0 ;

				createHeader(currentGroup,  row,  colIndex,  activationOrangeStyle );
				colIndex ++;

				createHeaderMergedCell(s,"Usage 1 (1st month of activation)",  row, beginIndex, colIndex,  grayHeadLineStyle );
				colIndex += 3;
				createHeaderMergedCell(s,"Usage 2 (2nd month of activation)",  row, beginIndex, colIndex,  grayHeadLineStyle );
				colIndex += 3;
				createHeaderMergedCell(s,"Usage 3 (3rd month of activation)",  row, beginIndex, colIndex,  grayHeadLineStyle );
				colIndex += 3;
				createHeaderMergedCell(s,"Usage 4 (4th month of activation)",  row, beginIndex, colIndex,  grayHeadLineStyle );
                                //Ahmed Adel set 6 loops for six monthes
                                colIndex += 3;
                                createHeaderMergedCell(s,"Usage 5 (5th month of activation)",  row, beginIndex, colIndex,  grayHeadLineStyle );
                                colIndex += 3;
                                createHeaderMergedCell(s,"Usage 6 (6th month of activation)",  row, beginIndex, colIndex,  grayHeadLineStyle );

                                colIndex = 0 ;
				beginIndex++;

				 row = s.createRow(beginIndex);


				 createHeader("Product",  row,  colIndex,  grayHeadLineBlackFontStyle );
				colIndex++;

				String[] headers = {"RPU", "Count of +ve ARPU Lines","Count of -ve ARPU Lines"};

				//Ahmed Adel set 6 loops for six monthes
				for(int j=0; j < 6; j++)
				{
					for (int i=  0 ; i <headers.length; i++)
					{

						createHeader(headers[i],  row,colIndex,  grayHeadLineBlackFontStyle );
						colIndex++;
					}
				}

				colIndex++;


				createHeader("Unique Positive Count",  row,  colIndex,  grayHeadLineBlackFontStyle );
				colIndex++;
				int j=0;






				String[] headers2={"Total ARPU","Total Lines","Average"};
				while(j<3)
				{
                                    //Ahmed Adel increase from 13 to 19
					if(colIndex!=19)
					{
						createHeader(headers2[j],  row,colIndex,  grayHeadLineBlackFontStyle );
					j++;
					}
					//else

					colIndex++;

				}

				beginIndex++;
			}

			Row row = s.createRow(beginIndex);
			row.createCell(0).setCellValue(rs.getString(3));
			float value ;
			//ahmed  adel increment loop
			for (int i=4 ; i <22;i++)
			{
				value = rs.getFloat(i);
				Cell cell =row.createCell( (i - 3 ));
			//	cell.
				//	format.getFormat("#,##0.0000"
				cell.setCellValue(value);
				cell.setCellStyle(simpleStyle);
				sumValArray[i-4] += value;
			}



//hagry added this column for the unique number of positive arpu
                        //ahmed adel
			value = rs.getFloat(16);

			Cell cell =row.createCell( 20);
			cell.setCellValue(value);
			cell.setCellStyle(simpleStyle);

			System.out.println("value of unique = "+ value);
			System.out.println("the query was :"+ sql);


			int t=beginIndex+1;
			//this is total ARPU cell
			cell =row.createCell( 21);
			cell.setCellFormula("B"+t+"+E"+t+"+H"+t+"+K"+t);

			cell =row.createCell( 22);
			cell.setCellFormula("C"+t+"+D"+t+"+F"+t+"+G"+t+"+I"+t+"+J"+t+"+L"+t+"+M"+t);

			cell =row.createCell(23);
			cell.setCellFormula("V"+t+"/W"+t);


			beginIndex++;
		}
		int t=beginIndex+1;

		Row row = s.createRow(beginIndex);


		//this is total ARPU cell
		Cell cell =row.createCell( 21);
		cell.setCellFormula("B"+t+"+E"+t+"+H"+t+"+K"+t);
		cell.setCellStyle(grayHeadLineBlackFontStyle);

		cell =row.createCell( 22);
		cell.setCellFormula("C"+t+"+D"+t+"+F"+t+"+G"+t+"+I"+t+"+J"+t+"+L"+t+"+M"+t);
		cell.setCellStyle(grayHeadLineBlackFontStyle);

		cell =row.createCell(23);
		cell.setCellFormula("V"+t+"/W"+t);
		cell.setCellStyle(grayHeadLineBlackFontStyle);
		printFooter(row,sumValArray);

		stat.close();
	//	Utility.closeConnection(con);
	}

	public static String setQueryParameter(String query, String query_parameter)
	{

		String searchFor="?";
		 int len = searchFor.length();
		 //ArrayList par_indexes =new ArrayList();
		 int start = 0;
		 start = query.indexOf(searchFor);
		  while(start != -1)
		  {
			  start = query.indexOf(searchFor);
			  if(start != -1)
			  { //query = replaceCharAt(query, start,parameters_value_type_pairs[i]);
				  query = replaceCharAt(query, start,query_parameter);
				  break;
			  }
		  }

		return query;
	}

	public static String replaceCharAt(String s, int pos, String c) {
		//int pos_int = pos.intValue();

		String temp  =s.substring(0,pos) + c+" " + s.substring(pos+1);
		   return  temp;
		}

	public static void exportFile(String filePath, String sdate, String edate)
	{

		try{

				FileOutputStream out = new FileOutputStream(filePath);
				// create a new workbook
				Workbook wb = new HSSFWorkbook();
				// create a new sheet
				prepareStyle(wb);



				Connection con= Utility.getConnection();

				Statement stat = con.createStatement();

				ResultSet res = stat.executeQuery("select count(*) from arpu_category_type");
				int count = 0 ;
				if (res.next())
				{
					count = res.getInt(1);
				}
				res.close();

				String [] sheetNames = new String[count];
				String [] sheetSql = new String[count];

				res = stat.executeQuery("select * from  arpu_category_type order by sheet_no");;
				int j=0;
				while (res.next())
				{
					sheetNames[j]=res.getString(2);
					String sql =res.getString(3);


					sql = setQueryParameter(sql,sdate);
					sql = setQueryParameter(sql,edate);

					sheetSql[j]= sql;
					System.out.println(sql);
					j++;
				}
				stat.close();



				for (int i = 0 ; i <sheetNames.length;i++)
					writeSheet(sheetNames[i],sheetSql[i],wb,con);

				Utility.closeConnection(con);
				wb.write(out);
				out.close();


			}
		catch(Exception e)
		{
		e.printStackTrace();
		}
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	//	String sheetNames [] = {"Alo-Dist", "Primo-Dist","Alo-Franchise"};
	//	String sql[] = {"SELECT   to_char(arpu_data.ACTIVATION_DATE,'Mon-yyyy') Activation_Month   ,channel_name,  gen_product.product_name,COUNT (arpu_data.imsi) AS count_of_lines,SUM (DECODE (SIGN (arpu_data.u1), +1, 1, 0)) AS count_of_positive_u1,SUM (DECODE (SIGN (arpu_data.u1), -1, 1, 0)) AS count_of_negative_u1,SUM (DECODE (arpu_data.u1, 0, 1, 0)) AS count_of_zero_u1,SUM (arpu_data.u1) AS rpu_u1,SUM (DECODE (SIGN (arpu_data.u2), +1, 1, 0)) AS count_of_positive_u2,SUM (DECODE (SIGN (arpu_data.u2), -1, 1, 0)) AS count_of_negative_u2,SUM (DECODE (arpu_data.u2, 0, 1, 0)) AS count_of_zero_u2,SUM (arpu_data.u2) AS rpu_u2,SUM (DECODE (SIGN (arpu_data.u3), +1, 1, 0)) AS count_of_positive_u3,SUM (DECODE (SIGN (arpu_data.u3), -1, 1, 0)) AS count_of_negative_u3,SUM (DECODE (arpu_data.u3, 0, 1, 0)) AS count_of_zero_u3,SUM (arpu_data.u3) AS rpu_u3,SUM (DECODE (SIGN (arpu_data.u4), +1, 1, 0)) AS count_of_positive_u4,         SUM (DECODE (SIGN (arpu_data.u4), -1, 1, 0)) AS count_of_negative_u4,  SUM (DECODE (arpu_data.u4, 0, 1, 0)) AS count_of_zero_u4,    SUM (arpu_data.u4) AS rpu_u4 FROM arpu_data, vw_gen_dcm, gen_product, arpu_category_type WHERE vw_gen_dcm.dcm_id = arpu_data.lcs_dcm_id AND arpu_data.lcs_contract_type_id = gen_product.product_id GROUP BY vw_gen_dcm.channel_name,gen_product.product_name, to_char(arpu_data.ACTIVATION_DATE,'Mon-yyyy')","SELECT   to_char(arpu_data.ACTIVATION_DATE,'Mon-yyyy') Activation_Month   ,channel_name,  gen_product.product_name,COUNT (arpu_data.imsi) AS count_of_lines,SUM (DECODE (SIGN (arpu_data.u1), +1, 1, 0)) AS count_of_positive_u1,SUM (DECODE (SIGN (arpu_data.u1), -1, 1, 0)) AS count_of_negative_u1,SUM (DECODE (arpu_data.u1, 0, 1, 0)) AS count_of_zero_u1,SUM (arpu_data.u1) AS rpu_u1,SUM (DECODE (SIGN (arpu_data.u2), +1, 1, 0)) AS count_of_positive_u2,SUM (DECODE (SIGN (arpu_data.u2), -1, 1, 0)) AS count_of_negative_u2,SUM (DECODE (arpu_data.u2, 0, 1, 0)) AS count_of_zero_u2,SUM (arpu_data.u2) AS rpu_u2,SUM (DECODE (SIGN (arpu_data.u3), +1, 1, 0)) AS count_of_positive_u3,SUM (DECODE (SIGN (arpu_data.u3), -1, 1, 0)) AS count_of_negative_u3,SUM (DECODE (arpu_data.u3, 0, 1, 0)) AS count_of_zero_u3,SUM (arpu_data.u3) AS rpu_u3,SUM (DECODE (SIGN (arpu_data.u4), +1, 1, 0)) AS count_of_positive_u4,         SUM (DECODE (SIGN (arpu_data.u4), -1, 1, 0)) AS count_of_negative_u4,  SUM (DECODE (arpu_data.u4, 0, 1, 0)) AS count_of_zero_u4,    SUM (arpu_data.u4) AS rpu_u4 FROM arpu_data, vw_gen_dcm, gen_product, arpu_category_type WHERE vw_gen_dcm.dcm_id = arpu_data.lcs_dcm_id AND arpu_data.lcs_contract_type_id = gen_product.product_id GROUP BY vw_gen_dcm.channel_name,gen_product.product_name, to_char(arpu_data.ACTIVATION_DATE,'Mon-yyyy')","SELECT   to_char(arpu_data.ACTIVATION_DATE,'Mon-yyyy') Activation_Month   ,channel_name,  gen_product.product_name,COUNT (arpu_data.imsi) AS count_of_lines,SUM (DECODE (SIGN (arpu_data.u1), +1, 1, 0)) AS count_of_positive_u1,SUM (DECODE (SIGN (arpu_data.u1), -1, 1, 0)) AS count_of_negative_u1,SUM (DECODE (arpu_data.u1, 0, 1, 0)) AS count_of_zero_u1,SUM (arpu_data.u1) AS rpu_u1,SUM (DECODE (SIGN (arpu_data.u2), +1, 1, 0)) AS count_of_positive_u2,SUM (DECODE (SIGN (arpu_data.u2), -1, 1, 0)) AS count_of_negative_u2,SUM (DECODE (arpu_data.u2, 0, 1, 0)) AS count_of_zero_u2,SUM (arpu_data.u2) AS rpu_u2,SUM (DECODE (SIGN (arpu_data.u3), +1, 1, 0)) AS count_of_positive_u3,SUM (DECODE (SIGN (arpu_data.u3), -1, 1, 0)) AS count_of_negative_u3,SUM (DECODE (arpu_data.u3, 0, 1, 0)) AS count_of_zero_u3,SUM (arpu_data.u3) AS rpu_u3,SUM (DECODE (SIGN (arpu_data.u4), +1, 1, 0)) AS count_of_positive_u4,         SUM (DECODE (SIGN (arpu_data.u4), -1, 1, 0)) AS count_of_negative_u4,  SUM (DECODE (arpu_data.u4, 0, 1, 0)) AS count_of_zero_u4,    SUM (arpu_data.u4) AS rpu_u4 FROM arpu_data, vw_gen_dcm, gen_product, arpu_category_type WHERE vw_gen_dcm.dcm_id = arpu_data.lcs_dcm_id AND arpu_data.lcs_contract_type_id = gen_product.product_id GROUP BY vw_gen_dcm.channel_name,gen_product.product_name, to_char(arpu_data.ACTIVATION_DATE,'Mon-yyyy')"};

		String sdate = "'1/1/2007'";
		String edate ="'1/12/2008'";
		long stime = System.currentTimeMillis();

		exportFile("c:\\adh_data\\workbook.xlsx",sdate,edate);
		long etime = System.currentTimeMillis();
		System.out.println("done in " + (etime - stime));
}
}
