package com.mobinil.sds.core.system.scm.dao;

import com.mobinil.sds.core.system.authenticationResult.dao.AuthResDAO;
import com.mobinil.sds.core.system.commission.model.RatedFileError;
import com.mobinil.sds.core.system.monthListFile.model.MonthListFileModel;
import com.mobinil.sds.core.system.paymentHistory.model.PaymentHistoryFileModel;
import com.mobinil.sds.core.system.regionReport.model.RegionPOSReportModel;
import com.mobinil.sds.core.system.sa.crosstabLists.model.CrosstabListsModel;
import com.mobinil.sds.core.system.scm.model.CaseModel;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.mobinil.sds.core.system.sa.importdata.ErrorInfo;
import com.mobinil.sds.core.system.scm.model.BarCodeCaseModel;
import com.mobinil.sds.core.system.scm.model.POSModel;
import com.mobinil.sds.core.system.scm.model.POSSearchExcelModel;
import com.mobinil.sds.core.system.scm.model.POSStatusCase;
import com.mobinil.sds.core.system.scm.model.RepExcelModel;
import com.mobinil.sds.core.system.scm.model.STKStatusCase;
import com.mobinil.sds.core.system.scm.model.SupervisorExcelModel;
import com.mobinil.sds.core.system.scm.model.TeamleaderExcelModel;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFontFormatting;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiWriteExcelFile
{

    private static Font wbFont;
    private static String Slach=System.getProperty("file.separator");
    public final static int mobileNumber = 9;
    public  static String defPath  = "";

    @SuppressWarnings(
	{ "deprecation", "null" }
	)
	public static String ExportExcelInsert(ArrayList<Integer> duplicateRows,
			String fileDir)
	{
		try
		{

			FileOutputStream fileOut = new FileOutputStream(fileDir
					+Slach+"stk_insert_report.xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("STK Worksheet");

			ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();

			ArrayList<HSSFCell> cellA = new ArrayList<HSSFCell>();
			ArrayList<HSSFCell> cellB = new ArrayList<HSSFCell>();
			ArrayList<HSSFCell> cellC = new ArrayList<HSSFCell>();

			for (int i = 0; i < duplicateRows.size() + 1; i++)
			{

				rows.add(worksheet.createRow((short) i));
				cellA.add(rows.get(i).createCell((short) 0));
				cellB.add(rows.get(i).createCell((short) 1));
				cellC.add(rows.get(i).createCell((short) 2));
			}


			cellA.get(0).setCellValue("Row number");
			cellB.get(0).setCellValue("cell name");
			cellC.get(0).setCellValue("Message");

			
			if (!duplicateRows.isEmpty())
			{
				for (int i = 1; i <=duplicateRows.size(); i++)
				{
					cellA.get(i).setCellValue(duplicateRows.get(i-1));
					cellB.get(i).setCellValue("");
					cellC.get(i).setCellValue("This STK already existed");

				}
			}

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			return "stk_insert_report.xls";
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return "";
		} catch (IOException e)
		{
			e.printStackTrace();
			return "";
		}

	}
	@SuppressWarnings(
			{ "deprecation", "null" }
			)
		public static String ExportExcel(Vector <CaseModel> resultcase,
					String fileDir)
			{
				try
				{

					FileOutputStream fileOut = new FileOutputStream(fileDir
							+Slach+ "pos_stk_report.xls");
					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet worksheet = workbook.createSheet("STK Worksheet");

					ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();

					ArrayList<HSSFCell> cellA = new ArrayList<HSSFCell>();
					ArrayList<HSSFCell> cellB = new ArrayList<HSSFCell>();
					ArrayList<HSSFCell> cellC = new ArrayList<HSSFCell>();

					for (int i = 0; i < resultcase.size() + 1; i++)
					{

						rows.add(worksheet.createRow((short) i));
						cellA.add(rows.get(i).createCell((short) 0));
						cellB.add(rows.get(i).createCell((short) 1));
						cellC.add(rows.get(i).createCell((short) 2));
					}


					cellA.get(0).setCellValue("Row number");
					cellB.get(0).setCellValue("cell name");
					cellC.get(0).setCellValue("Message");

					
					if (!resultcase.isEmpty())
					{
						for (int i = 1; i <=resultcase.size(); i++)
						{
							CaseModel current = (CaseModel) resultcase.get(i-1);
							cellA.get(i).setCellValue(current.getrowNumber());
							cellB.get(i).setCellValue("");
							String errormsg = "";
                                                        String Msgflag="";

							
							if(current.getposCase()!=0)
							{
								int posstatus =current.getposCase();
								switch (posstatus)
								{
										case 1 :
											errormsg="This Pos Already has STK";
									
										break;
										
										case 2 :
											errormsg="This POS was Deleted";
										break;	
										
										case 3 :
											errormsg="This POS is not exist";
										break;

                                                                                case 4 :
											errormsg="This POSCODE is not POS Level";

										break;

								}
							}
                                                        if(errormsg!="")
                                                             {
                                                           Msgflag=" & ";
                                                             }

							if(current.getstkCase()!=0)
							{
								int stkstatus =current.getstkCase();
								switch (stkstatus)
								{
										case 1 :
											errormsg=errormsg+Msgflag+"This STK Already used by another POS";
									
										break;
										
										case 2 :
											errormsg=errormsg+Msgflag+"This STK was Deleted";
										break;	
										
										case 3 :
											errormsg=errormsg+Msgflag+"This STK is not exist";

                                                                                case 10 :
											errormsg=errormsg+Msgflag+"This STK doesn't belong to POS stock.";
										break;	
								}
							}
                                                         cellC.get(i).setCellValue(errormsg);
							
						}
					}

					workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					return "pos_stk_report.xls";
				} catch (FileNotFoundException e)
				{
					e.printStackTrace();
					return "";
				} catch (IOException e)
				{
					e.printStackTrace();
					return "";
				}

			}

                        public static String ExportExcel(HashMap<String,String> Map,String fileDir,String Distribname)
                        {
                            try {
                                     FileOutputStream fileOut = new FileOutputStream(fileDir+Slach+Distribname+"_stk_report.xls");

					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet worksheet = workbook.createSheet("STK Worksheet");

					ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();

					ArrayList<HSSFCell> cellA = new ArrayList<HSSFCell>();
					ArrayList<HSSFCell> cellB = new ArrayList<HSSFCell>();
					ArrayList<HSSFCell> cellC = new ArrayList<HSSFCell>();
					

					for (int i = 0; i < Map.size() + 1; i++)
					{

						rows.add(worksheet.createRow((short) i));
						cellA.add(rows.get(i).createCell((short) 0));
						cellB.add(rows.get(i).createCell((short) 1));
						cellC.add(rows.get(i).createCell((short) 2));
						
					}


					cellA.get(0).setCellValue("Row number");
					cellB.get(0).setCellValue("STK Dial");
					cellC.get(0).setCellValue("Serial Number");

                                        String [] ArrdialAndSerial = null;
                                        for (int i = 1; i <=Map.size(); i++)
						{
                                                        Integer k= i;
							String Key=k.toString();
                                                        ArrdialAndSerial = Map.get(Key).split("\\_");
							cellA.get(i).setCellValue(i);
							cellB.get(i).setCellValue(ArrdialAndSerial[0]);
							cellC.get(i).setCellValue(ArrdialAndSerial[1]);
                                                }
						
                                        workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					return Distribname+"_stk_report.xls";
                                                        
					
                        
                                                
                            } catch (FileNotFoundException e)
				{
					e.printStackTrace();
					return "";
				} catch (IOException e)
				{
					e.printStackTrace();
					return "";
				}
                        
                     }

public static String ExportExcelDelete(ArrayList<Integer> usedrows,ArrayList<Integer> nonExist,
			String fileDir)
	{
		try
		{

			FileOutputStream fileOut = new FileOutputStream(fileDir
					+Slach+ "stk_Delete_report.xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("STK Worksheet");

			ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();

			ArrayList<HSSFCell> cellA = new ArrayList<HSSFCell>();
			ArrayList<HSSFCell> cellB = new ArrayList<HSSFCell>();
			

			for (int i = 0; i < usedrows.size() + nonExist.size() + 1; i++)
			{

				rows.add(worksheet.createRow((short) i));
				cellA.add(rows.get(i).createCell((short) 0));
				cellB.add(rows.get(i).createCell((short) 1));
				
			}


			cellA.get(0).setCellValue("Row number");
			cellB.get(0).setCellValue("Message");


			int stopCell=1;


			if (!usedrows.isEmpty())
			{
				for (int i = 1; i <=usedrows.size(); i++)
				{
					cellA.get(i).setCellValue(usedrows.get(i-1));
					cellB.get(i).setCellValue("is used");
					stopCell++;

				}
			}
                        if (!nonExist.isEmpty())
			{
				for (int i = stopCell; i <=nonExist.size(); i++)
				{
					cellA.get(i).setCellValue(nonExist.get(i-1));
					cellB.get(i).setCellValue("is nonExist");
					stopCell++;

				}
			}

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			return "stk_Delete_report.xls";
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return "";
		} catch (IOException e)
		{
			e.printStackTrace();
			return "";
		}

}


public static String ExportExcelDialNumber(String fileDir)
{
        try {
            
            FileOutputStream fileOut = new FileOutputStream(fileDir + Slach + "Active_STK_report.xls");
            Vector<POSModel> POSs=STKDAO.getNonActiveValidPOS();
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("STK Worksheet");

			ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();

			ArrayList<HSSFCell> cellA = new ArrayList<HSSFCell>();



			for (int i = 0; i < POSs.size()+ 1; i++)
			{

				rows.add(worksheet.createRow((short) i));
				cellA.add(rows.get(i).createCell((short) 0));


			}
                        cellA.get(0).setCellValue("STK Dial Number");

                        if (!POSs.isEmpty())
			{
				for (int i = 1; i <=POSs.size(); i++)
				{
					cellA.get(i).setCellValue(POSs.get(i-1).getSTK_Dial());
                                }
			}
            try {
                workbook.write(fileOut);
                fileOut.flush();
                fileOut.close();
                return "Active_STK_report.xls";
            } catch (IOException ex) {
                Logger.getLogger(PoiWriteExcelFile.class.getName()).log(Level.SEVERE, null, ex);
                return "";
            }


                         } catch (FileNotFoundException ex) {
            Logger.getLogger(PoiWriteExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }


}
public static String ExportExcelPOSChanges (Vector <POSStatusCase> refusedPOSs,
					String changeStatusType,String fileDir)
			{
				try
				{

					FileOutputStream fileOut = new FileOutputStream(fileDir
							+Slach+ "pos_changes_report.xls");
					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet worksheet = workbook.createSheet("POS Worksheet");

					ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();

					ArrayList<HSSFCell> cellA = new ArrayList<HSSFCell>();
					ArrayList<HSSFCell> cellB = new ArrayList<HSSFCell>();


					for (int i = 0; i <refusedPOSs.size() + 1; i++)
					{
                                           
                                              rows.add(worksheet.createRow((short) i));
						cellA.add(rows.get(i).createCell((short) 0));
						cellB.add(rows.get(i).createCell((short) 1));
                                             
                                          


                                       }


					cellA.get(0).setCellValue("Row number");
					cellB.get(0).setCellValue("Message");



					if (!refusedPOSs.isEmpty())
					{
						for (int i = 1; i <=refusedPOSs.size(); i++)
						{

							POSStatusCase current = (POSStatusCase) refusedPOSs.get(i-1);
							

							if(changeStatusType.equals("POS")&&current.getCaseExist()==1)
                                                        {
                                                            cellA.get(i).setCellValue(current.getPOSCode());
                                                            cellB.get(i).setCellValue("This POS is Not Exist");
                                                        }else{
                                                                if(changeStatusType.equals("Payment")&&current.getCaseExist()==1)
                                                                {
                                                                   cellA.get(i).setCellValue(current.getPOSCode());
                                                                   cellB.get(i).setCellValue("This POS is Not Exist");
                                                                }else if(changeStatusType.equals("Payment")&&current.getCaseExist()==2)
                                                                 {
                                                                    cellA.get(i).setCellValue(current.getPOSCode());
                                                                    cellB.get(i).setCellValue("This POS is Not Have Payment");
                                                                }else if(changeStatusType.equals("Payment")&&current.getCaseExist()==3)
                                                                 {
                                                                    cellA.get(i).setCellValue(current.getPOSCode());
                                                                    cellB.get(i).setCellValue("This POS is Not Active");
                                                                }

                                                             }


                                                        }

						}


					workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					return "pos_changes_report.xls";
				} catch (FileNotFoundException e)
				{
					e.printStackTrace();
					return "";
				} catch (IOException e)
				{
					e.printStackTrace();
					return "";
				}

			}

        public static String ExportExcelSTKChanges(Vector<STKStatusCase> STKs,String fileDir)
        {
        try
				{

					FileOutputStream fileOut = new FileOutputStream(fileDir
							+Slach+ "change_stk_report.xls");
					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet worksheet = workbook.createSheet("STK Worksheet");

					ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();

					ArrayList<HSSFCell> cellA = new ArrayList<HSSFCell>();
					ArrayList<HSSFCell> cellB = new ArrayList<HSSFCell>();


					for (int i = 0; i < STKs.size() + 1; i++)
					{

						rows.add(worksheet.createRow((short) i));
						cellA.add(rows.get(i).createCell((short) 0));
						cellB.add(rows.get(i).createCell((short) 1));

					}


					cellA.get(0).setCellValue("STK_Dial");
					cellB.get(0).setCellValue("Message");



					if (!STKs.isEmpty())
					{
						for (int i = 1; i <=STKs.size(); i++)
						{
							STKStatusCase current = (STKStatusCase) STKs.get(i-1);
							cellA.get(i).setCellValue(current.getSTKDial());
							cellB.get(i).setCellValue("This STK is not Exist");

                                                }


                                       }
					

					workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					return "change_stk_report.xls";
                                } catch (FileNotFoundException e)
				{
					e.printStackTrace();
					return "";
				} catch (IOException e)
				{
					e.printStackTrace();
					return "";
				}



        }
        public static String exportPOSGroupPOSs(Vector<POSModel> posGroupPOSs,String directionFile){
        try
				{

					FileOutputStream fileOut = new FileOutputStream(directionFile
							+Slach+ "pos_group_report.xls");
					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet worksheet = workbook.createSheet("POS Worksheet");

					ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();

					ArrayList<HSSFCell> cellA = new ArrayList<HSSFCell>();
					ArrayList<HSSFCell> cellB = new ArrayList<HSSFCell>();


					for (int i = 0; i < posGroupPOSs.size() + 1; i++)
					{

						rows.add(worksheet.createRow((short) i));
						cellA.add(rows.get(i).createCell((short) 0));
						cellB.add(rows.get(i).createCell((short) 1));

					}


					cellA.get(0).setCellValue("POS_CODE");
					cellB.get(0).setCellValue("POS_NAME");



					if (!posGroupPOSs.isEmpty())
					{
						for (int i = 1; i <=posGroupPOSs.size(); i++)
						{
							POSModel current = (POSModel) posGroupPOSs.get(i-1);
							cellA.get(i).setCellValue(current.getPOS_Code());
							cellB.get(i).setCellValue(current.getPOS_NAME());

                                                }


                                       }


					workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					return "pos_group_report.xls";
                                } catch (FileNotFoundException e)
				{
					e.printStackTrace();
					return "";
				} catch (IOException e)
				{
					e.printStackTrace();
					return "";
				}
        }

        public static String exportRefusedPOSBarCodeExcel(Vector<BarCodeCaseModel> POSsBarCodes,String directionFile)
        {

        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(directionFile + Slach + "pos_barcode_report.xls");

					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet worksheet = workbook.createSheet("POS Worksheet");

					ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();

					ArrayList<HSSFCell> cellA = new ArrayList<HSSFCell>();
					ArrayList<HSSFCell> cellB = new ArrayList<HSSFCell>();


					for (int i = 0; i < POSsBarCodes.size() + 1; i++)
					{

						rows.add(worksheet.createRow((short) i));
						cellA.add(rows.get(i).createCell((short) 0));
						cellB.add(rows.get(i).createCell((short) 1));

					}


					cellA.get(0).setCellValue("POS_CODE");
					cellB.get(0).setCellValue("Reason");



					if (!POSsBarCodes.isEmpty())
					{
						for (int i = 1; i <=POSsBarCodes.size(); i++)
						{
							BarCodeCaseModel current = (BarCodeCaseModel) POSsBarCodes.get(i-1);
							cellA.get(i).setCellValue(current.getPOS_Code());
                                                        if(current.getStatus()==1)
							cellB.get(i).setCellValue("This Pos doesn't exist");
                                                        else if(current.getStatus()==2)
                                                        cellB.get(i).setCellValue("The Quantity less than zero");
                                                }


                                       }


					workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					return "pos_barcode_report.xls";
                                } catch (FileNotFoundException e)
				{
					e.printStackTrace();
					return "";
				} catch (IOException e)
				{
					e.printStackTrace();
					return "";
				}

        }
        
        
  public static String exportExcelSheetForPOSSearch(Vector<POSSearchExcelModel> POSSearchResults,String directionFile)
  {
      java.util.Date dateNow = new java.util.Date();
        int imonth = dateNow.getMonth() + 1;
        int iyear = dateNow.getYear() + 1900;
        String strdate = (new StringBuffer("[")).append(dateNow.getDate()).append("-").append(imonth).append("-").append(iyear).append("]-").append(dateNow.getHours()).append(".").append(dateNow.getMinutes()).append(".").append(dateNow.getSeconds()).append("_").toString();
        String fileName = strdate+"pos_search_report.xls";
       FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(directionFile + Slach + fileName);

					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet worksheet = workbook.createSheet("POS Worksheet");

					ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();
                                        ArrayList<ArrayList<HSSFCell>> cells=new ArrayList<ArrayList<HSSFCell>>();
					//42
                                        for(int i=1; i<=45;i++){
                                        ArrayList<HSSFCell> cell = new ArrayList<HSSFCell>();
                                            cells.add(cell);
                                        }

                                       

                                        for (int i=0;i<=POSSearchResults.size();i++){

                                            rows.add(worksheet.createRow((short) i));
                                             //42
                                            for(int cellno=0;cellno<45;cellno++){
                                                
                                                cells.get(cellno).add(rows.get(i).createCell((short) cellno));
                                            }

                                        }


                                      int header=0;
                                      cells.get(header).get(0).setCellValue("Entery By");
                                      header++;
                                      cells.get(header).get(0).setCellValue("POS Id");
                                      header++;
                                      cells.get(header).get(0).setCellValue("POS Code");
                                      header++;
                                      cells.get(header).get(0).setCellValue("POS English Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("POS Arabic Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Sales Region Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Governrate Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("City Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("District Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Area Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Regional Supervisor Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Rep Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("English Address");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Arabic Address");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Phone");
                                      header++;
                                      cells.get(header).get(0).setCellValue("DemoLine");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Owner Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Supervisor Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Teamleader Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Salesrep Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Owner BirthDate");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Owner PhoneNumber");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Owner IDNo");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Owner ID Type");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Manager Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Manager BirthDate");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Manager PhoneNumber");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Manager ID No");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Manager ID Type");
                                      header++;
                                      cells.get(header).get(0).setCellValue("POS Mail");
                                      header++;
                                      cells.get(header).get(0).setCellValue("POS Document Type");
                                      header++;
                                      cells.get(header).get(0).setCellValue("POS Document Number");
                                      header++;
                                      cells.get(header).get(0).setCellValue("POS Status");
                                      header++;
                                      cells.get(header).get(0).setCellValue("STK Dial Number");
                                      header++;
                                      cells.get(header).get(0).setCellValue("STK Delivery Date");
                                      header++;
                                      cells.get(header).get(0).setCellValue("STKVrfcat_VantifCaseIdNo");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Iqrar Delivery Date");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Iqrar Recevied Date");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Entry Date");
                                      header++;
                                      cells.get(header).get(0).setCellValue("POS Rate");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Channel Code");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Level Code");
                                      header ++;
                                      cells.get(header).get(0).setCellValue("Payment Status");
                                      header ++;
                                      cells.get(header).get(0).setCellValue("Survey ID");
            try {
                Connection con = Utility.getConnection();


                             for(int i=1;i<=POSSearchResults.size();i++)
                             {
                                 POSSearchExcelModel ss = POSSearchResults.get(i-1);
                                int j=0;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getEnteryBy());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getPosId());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getPosCode());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getPosEnglishName());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getPosArabicName());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getRegionId());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getGovernrateId());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getCityId());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getDistrictId());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getAreaId());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getPosSupervisor());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getPosRebName());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getEnglishaddress());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getArabicAddress());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getPosPhoneNumber());
                                j++;
                                if(POSSearchResults.get(i-1).getPosDemoLine()!=null)
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getPosDemoLine());
                                else
                                cells.get(j).get(i).setCellValue("");
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getOwnerName());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getSupervisorId());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getTeamleaderId());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getSalesrepId());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getOwnerBirthDate().toString());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getOwnerPhoneNmber());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getOwnerIDNo());
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getOwnerIDTypeNumber());
                                j++;
                                if(POSSearchResults.get(i-1).getManagerName()!=null)
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getManagerName());
                                else
                                cells.get(j).get(i).setCellValue("");
                                j++;
                                if(POSSearchResults.get(i-1).getManagerBirthDate()!=null)
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getManagerBirthDate().toString());
                                 else
                                cells.get(j).get(i).setCellValue("");
                                j++;
                                if(POSSearchResults.get(i-1).getManagerPhoneNumber()!=null)
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getManagerPhoneNumber());
                                else
                                cells.get(j).get(i).setCellValue("");
                                j++;
                                if(POSSearchResults.get(i-1).getManagerIDTypeNumber()!=null)
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getManagerIDTypeNumber());
                                else
                                cells.get(j).get(i).setCellValue("");
                                j++;
                                if(POSSearchResults.get(i-1).getManagerIDNumber()!=null)
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getManagerIDNumber());
                                else
                                cells.get(j).get(i).setCellValue("");
                                j++;
                                if(POSSearchResults.get(i-1).getPosEmail()!=null)
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getPosEmail());
                                else
                                cells.get(j).get(i).setCellValue("");
                                j++;
                                if(POSSearchResults.get(i-1).getPosDocumentType()!=null)
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getPosDocumentType());
                                else
                                cells.get(j).get(i).setCellValue("");
                                j++;
                                if(POSSearchResults.get(i-1).getPosDocumentNumber()!=null)
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getPosDocumentNumber());
                                else
                                cells.get(j).get(i).setCellValue("");
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getPosStatus());
                                j++;
                                if(POSSearchResults.get(i-1).getStkNumber()!=null)
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getStkNumber());
                                else
                                cells.get(j).get(i).setCellValue("");
                                j++;

                                if(POSSearchResults.get(i-1).getStkDeliveryDate()!=null)
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getStkDeliveryDate().toString());
                                else
                                cells.get(j).get(i).setCellValue("");
                                j++;
                                if(POSSearchResults.get(i-1).getCbillCase()!=null)
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getCbillCase());
                                else
                                cells.get(j).get(i).setCellValue("");
                                j++;
                                if(POSSearchResults.get(i-1).getIqrarDeliveryDate()!=null)
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getIqrarDeliveryDate().toString());
                                else
                                cells.get(j).get(i).setCellValue("");
                                j++;
                                if(POSSearchResults.get(i-1).getIqrarReceviedDate()!=null)
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getIqrarReceviedDate().toString());
                                else
                                cells.get(j).get(i).setCellValue("");
                                j++;
                                 if(POSSearchResults.get(i-1).getEntryDate()!=null)
                                    cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getEntryDate().toString());
                                 else
                                    cells.get(j).get(i).setCellValue("");
                                j++;
                                cells.get(j).get(i).setCellValue(POSDAO.getPaymentLevelName(con,POSSearchResults.get(i-1).getPosPayment()));
                                j++;
                                cells.get(j).get(i).setCellValue(POSDAO.getChannelName(con,POSSearchResults.get(i-1).getPoschannel()));
                                j++;
                                cells.get(j).get(i).setCellValue(POSDAO.getPosLevelName(con,POSSearchResults.get(i-1).getPosLevel()));
                                j++;
                                cells.get(j).get(i).setCellValue(POSDAO.getPaymentStatusName(con,Integer.toString(POSSearchResults.get(i-1).getPosPaymentstatus() )));
                                j++;
                                cells.get(j).get(i).setCellValue(POSSearchResults.get(i-1).getSurveyId());
                             }
              con.close();
 } catch (SQLException ex) {
                Logger.getLogger(PoiWriteExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
                                        workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					return fileName;

                                } catch (FileNotFoundException e)
				{
					e.printStackTrace();
					return "";
				} catch (IOException e)
				{
					e.printStackTrace();
					return "";
				}


  }      
        

  public static String exportExcelSheetForHistory(Vector<PaymentHistoryFileModel> HistoryResults,String directionFile)
  {
      java.util.Date dateNow = new java.util.Date();
        int imonth = dateNow.getMonth() + 1;
        int iyear = dateNow.getYear() + 1900;
        String strdate = (new StringBuffer("[")).append(dateNow.getDate()).append("-").append(imonth).append("-").append(iyear).append("]-").append(dateNow.getHours()).append(".").append(dateNow.getMinutes()).append(".").append(dateNow.getSeconds()).append("_").toString();
        String fileName = strdate+"history_file_report.xls";
       FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(directionFile + Slach + fileName);

					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet worksheet = workbook.createSheet("POS Worksheet");

					ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();
                                        ArrayList<ArrayList<HSSFCell>> cells=new ArrayList<ArrayList<HSSFCell>>();
					//42 //45 //was 9
                                        for(int i=1; i<=16;i++){
                                        ArrayList<HSSFCell> cell = new ArrayList<HSSFCell>();
                                            cells.add(cell);
                                        }

                                       

                                        for (int i=0;i<=HistoryResults.size();i++){

                                            rows.add(worksheet.createRow(i));
                                             //42 //9
                                            for(int cellno=0;cellno<16;cellno++){
                                                
                                                cells.get(cellno).add(rows.get(i).createCell((short) cellno));
                                            }

                                        }


                                      int header=0;
                                      cells.get(header).get(0).setCellValue("File Id");
                                      header++;
                                      cells.get(header).get(0).setCellValue("User Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("POS Code");
                                      header++;
                                      cells.get(header).get(0).setCellValue("File Timestamp");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Month");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Year");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Channel Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Payment Level Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Status");
                                      header++;
                                      cells.get(header).get(0).setCellValue("SalesRegion");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Governerate");
                                      header++;
                                      cells.get(header).get(0).setCellValue("ImDistrict");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Area");
                                      header++;
                                      cells.get(header).get(0).setCellValue("SupervisorName");
                                      header++;
                                      cells.get(header).get(0).setCellValue("FRepName");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Teamleader");
                                      
                                      /*SalesRegion	Governerate	ImDistrict	Area	SupervisorName	FRepName Teamleader*/
                                      
            try {
                Connection con = Utility.getConnection();


                             for(int i=1;i<=HistoryResults.size();i++)
                             {
                                 PaymentHistoryFileModel ss = HistoryResults.get(i-1);
                                int j=0;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getHISTORY_FILE_ID());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getUSERNAME());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getDCM_CODE());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getFILE_TIMESTAMP());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getFILE_MONTH());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getFILE_YEAR());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getDCM_CHANNEL_NAME());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getDCM_PAYMENT_LEVEL());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getFILE_STATUS());
                                /*SalesRegion	Governerate	ImDistrict	Area	SupervisorName	FRepName Teamleader*/
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getRegionName());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getGovernName());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getDistrictName());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getAreaName());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getSupervisor());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getSalesrep());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getTeamleader());
                             }
              con.close();
 } catch (SQLException ex) {
                Logger.getLogger(PoiWriteExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
                                        workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					return fileName;

                                } catch (FileNotFoundException e)
				{
					e.printStackTrace();
					return "";
				} catch (IOException e)
				{
					e.printStackTrace();
					return "";
				}


  }
  
  
  public static String exportExcelSheetForMonthList(Vector<MonthListFileModel> HistoryResults,String directionFile)
  {
      java.util.Date dateNow = new java.util.Date();
        int imonth = dateNow.getMonth() + 1;
        int iyear = dateNow.getYear() + 1900;
        String strdate = (new StringBuffer("[")).append(dateNow.getDate()).append("-").append(imonth).append("-").append(iyear).append("]-").append(dateNow.getHours()).append(".").append(dateNow.getMinutes()).append(".").append(dateNow.getSeconds()).append("_").toString();
        String fileName = strdate+"month_list_file_report.xls";
       FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(directionFile + Slach + fileName);

					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet worksheet = workbook.createSheet("POS Worksheet");

					ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();
                                        ArrayList<ArrayList<HSSFCell>> cells=new ArrayList<ArrayList<HSSFCell>>();
					//42 //45 //was 9
                                        for(int i=1; i<=17;i++){
                                        ArrayList<HSSFCell> cell = new ArrayList<HSSFCell>();
                                            cells.add(cell);
                                        }

                                       

                                        for (int i=0;i<=HistoryResults.size();i++){

                                            rows.add(worksheet.createRow(i));
                                             //42 //9
                                            for(int cellno=0;cellno<17;cellno++){
                                                
                                                cells.get(cellno).add(rows.get(i).createCell((short) cellno));
                                            }

                                        }


                                      int header=0;
                                      cells.get(header).get(0).setCellValue("File Id");
                                      header++;
                                      cells.get(header).get(0).setCellValue("List Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("User Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("POS Code");
                                      header++;
                                      cells.get(header).get(0).setCellValue("File Timestamp");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Month");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Year");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Channel Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Payment Level Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Status");
                                      header++;
                                      cells.get(header).get(0).setCellValue("SalesRegion");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Governerate");
                                      header++;
                                      cells.get(header).get(0).setCellValue("ImDistrict");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Area");
                                      header++;
                                      cells.get(header).get(0).setCellValue("SupervisorName");
                                      header++;
                                      cells.get(header).get(0).setCellValue("FRepName");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Teamleader");
                                      
                                      /*SalesRegion	Governerate	ImDistrict	Area	SupervisorName	FRepName Teamleader*/
                                      
            try {
                Connection con = Utility.getConnection();


                             for(int i=1;i<=HistoryResults.size();i++)
                             {
                                 MonthListFileModel ss = HistoryResults.get(i-1);
                                int j=0;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getHISTORY_FILE_ID());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getLIST_NAME());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getUSERNAME());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getDCM_CODE());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getFILE_TIMESTAMP());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getFILE_MONTH());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getFILE_YEAR());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getDCM_CHANNEL_NAME());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getDCM_PAYMENT_LEVEL());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getFILE_STATUS());
                                /*SalesRegion	Governerate	ImDistrict	Area	SupervisorName	FRepName Teamleader*/
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getRegionName());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getGovernName());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getDistrictName());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getAreaName());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getSupervisor());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getSalesrep());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getTeamleader());
                             }
              con.close();
 } catch (SQLException ex) {
                Logger.getLogger(PoiWriteExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
                                        workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					return fileName;

                                } catch (FileNotFoundException e)
				{
					e.printStackTrace();
					return "";
				} catch (IOException e)
				{
					e.printStackTrace();
					return "";
				}


  }
  
  public static String exportExcelSheetForCrosstabLists(Vector<CrosstabListsModel> HistoryResults,String directionFile)
  {
      java.util.Date dateNow = new java.util.Date();
        int imonth = dateNow.getMonth() + 1;
        int iyear = dateNow.getYear() + 1900;
        String strdate = (new StringBuffer("[")).append(dateNow.getDate()).append("-").append(imonth).append("-").append(iyear).append("]-").append(dateNow.getHours()).append(".").append(dateNow.getMinutes()).append(".").append(dateNow.getSeconds()).append("_").toString();
        String fileName = strdate+"crosstab_file_report.xls";
       FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(directionFile + Slach + fileName);

					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet worksheet = workbook.createSheet("POS Worksheet");

					ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();
                                        ArrayList<ArrayList<HSSFCell>> cells=new ArrayList<ArrayList<HSSFCell>>();
					//42 //45 //was 9
                                        for(int i=1; i<=13;i++){
                                        ArrayList<HSSFCell> cell = new ArrayList<HSSFCell>();
                                            cells.add(cell);
                                        }

                                       

                                        for (int i=0;i<=HistoryResults.size();i++){

                                            rows.add(worksheet.createRow(i));
                                             //42 //9
                                            for(int cellno=0;cellno<13;cellno++){
                                                
                                                cells.get(cellno).add(rows.get(i).createCell((short) cellno));
                                            }

                                        }


                                      int header=0;
                                      cells.get(header).get(0).setCellValue("POS Code");
                                      header++;
                                      cells.get(header).get(0).setCellValue("POS Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("List Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Month");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Year");
                                      header++;
                                      cells.get(header).get(0).setCellValue("List Area Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("List Region Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("List Govern Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("List City Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("List District Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Supervisor Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Salesrep Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Teamleader Name");
                                      
                                      /*SalesRegion	Governerate	ImDistrict	Area	SupervisorName	FRepName Teamleader*/
                                      
            try {
                Connection con = Utility.getConnection();


                             for(int i=1;i<=HistoryResults.size();i++)
                             {
                                 CrosstabListsModel ss = HistoryResults.get(i-1);
                                int j=0;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getDcmCode());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getDcmName());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getListName());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getMonth());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getYear());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getAreaName());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getRegionName());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getGovernName());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getCityName());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getDistrictName());
                                /*SalesRegion	Governerate	ImDistrict	Area	SupervisorName	FRepName Teamleader*/
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getSupervisorName());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getSalesrepName());
                                j++;
                                cells.get(j).get(i).setCellValue(HistoryResults.get(i-1).getTeamleaderName());
                                
                             }
              con.close();
 } catch (SQLException ex) {
                Logger.getLogger(PoiWriteExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
                                        workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					return fileName;

                                } catch (FileNotFoundException e)
				{
					e.printStackTrace();
					return "";
				} catch (IOException e)
				{
					e.printStackTrace();
					return "";
				}


  }
  
  
  
  public static String exportExcelSheetForRegionPOSData(Vector<RegionPOSReportModel> RegionResults,String directionFile)
  {
      System.out.println("inside exportExcelSheetForRegionPOSData");
      java.util.Date dateNow = new java.util.Date();
        int imonth = dateNow.getMonth() + 1;
        int iyear = dateNow.getYear() + 1900;
        String strdate = (new StringBuffer("[")).append(dateNow.getDate()).append("-").append(imonth).append("-").append(iyear).append("]-").append(dateNow.getHours()).append(".").append(dateNow.getMinutes()).append(".").append(dateNow.getSeconds()).append("_").toString();
        String fileName = strdate+"region_pos_file_report.xls";
       FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(directionFile + Slach + fileName);

					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet worksheet = workbook.createSheet("POS Worksheet");

					ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();
                                        ArrayList<ArrayList<HSSFCell>> cells=new ArrayList<ArrayList<HSSFCell>>();
					//42 //45
                                        for(int i=1; i<=46;i++){
                                        ArrayList<HSSFCell> cell = new ArrayList<HSSFCell>();
                                            cells.add(cell);
                                        }

                                       

                                        for (int i=0;i<=RegionResults.size();i++){

                                            rows.add(worksheet.createRow(i));
                                             //42
                                            for(int cellno=0;cellno<46;cellno++){
                                                
                                                cells.get(cellno).add(rows.get(i).createCell((short) cellno));
                                            }

                                        }


                                      int header=0;
                                      cells.get(header).get(0).setCellValue("ChannelCode");
                                      header++;
                                      cells.get(header).get(0).setCellValue("PosCode");
                                      header++;
                                      cells.get(header).get(0).setCellValue("PosENm");
                                      header++;
                                      cells.get(header).get(0).setCellValue("ArabicName");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Owner");
                                      header++;
                                      cells.get(header).get(0).setCellValue("IDNumber");
                                      header++;
                                      cells.get(header).get(0).setCellValue("IDType");
                                      header++;
                                      cells.get(header).get(0).setCellValue("SalesRegion");
                                      header++;
                                      cells.get(header).get(0).setCellValue("City");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Governorate");
                                      header++;
                                      cells.get(header).get(0).setCellValue("DistrictID");
                                      header++;
                                      cells.get(header).get(0).setCellValue("District");
                                      header++;
                                      cells.get(header).get(0).setCellValue("AreaCode");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Area");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Address");
                                      header++;
                                      cells.get(header).get(0).setCellValue("DocNumber");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Documents");
                                      header++;
                                      cells.get(header).get(0).setCellValue("entryDt");
                                      header++;
                                      cells.get(header).get(0).setCellValue("PosStatus");
                                      header++;
                                      cells.get(header).get(0).setCellValue("OwnerPhone");
                                      header++;
                                      cells.get(header).get(0).setCellValue("LevelCode");
                                      header++;
                                      cells.get(header).get(0).setCellValue("RegionalName");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Teamleader");
                                      header++;
                                      cells.get(header).get(0).setCellValue("RepName");
                                      header++;
                                      cells.get(header).get(0).setCellValue("StkDialNo");
                                      header++;
                                      cells.get(header).get(0).setCellValue("StkStatus");
                                      header++;
                                      cells.get(header).get(0).setCellValue("StkActivationDate");
                                      header++;
                                      cells.get(header).get(0).setCellValue("PayStatus");
                                      header++;
                                      cells.get(header).get(0).setCellValue("PayLevelName");
                                      header++;
                                      cells.get(header).get(0).setCellValue("ArabicAddress");
                                      header++;
                                      cells.get(header).get(0).setCellValue("IqrarReceived");
                                      header++;
                                      cells.get(header).get(0).setCellValue("VerifyOK");
                                      header++;
                                    
                                      cells.get(header).get(0).setCellValue("DocumentLocation");
                                      header++;
                                      cells.get(header).get(0).setCellValue("SurveyID");
                                      header++;
                                      cells.get(header).get(0).setCellValue("POS_OWNER_PHONE_NUMBER");
                                      header++;
                                      cells.get(header).get(0).setCellValue("branch");
                                      header++;
                                      cells.get(header).get(0).setCellValue("L1");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Ex");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Sign");
                                      header++;
                                      cells.get(header).get(0).setCellValue("QC");
                                      
            try {
                Connection con = Utility.getConnection();


                             for(int i=1;i<=RegionResults.size();i++)
                             {
                                 RegionPOSReportModel ss = RegionResults.get(i-1);
                                int j=0;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getChannelCode());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getPosCode());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getPosENName());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getPosARName());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getOwnerName());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getIdNumber());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getIdType());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getRegion());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getCity());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getGovernorate());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getDistrictCodeId());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getDisctrict());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getAreaCode());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getArea());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getAddress());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getDocumentNumber());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getDocuments());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getEntryDate());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getPosStatus());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getPosOwnerPhoneNumber());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getPosLevel());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getRegionSupervisor());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getRegionTeamleader());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getSalesRep());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getStkDialNumber());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getStkStatus());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getStkActivationDate());
                                j++;
                                /*cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getIqrarReceivedDate());
                                j++;*/
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getPaymentStatus());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getPaymentLevelName());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getArAddress());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getIqrarReceived());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getVerifyOk());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getDocumentLocation());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getSurveyId());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getPosOwnerPhoneNumber());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getBranch());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getL1());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getEx());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getSign());
                                j++;
                                cells.get(j).get(i).setCellValue(RegionResults.get(i-1).getQC());
                                
                             }
              con.close();
 } catch (SQLException ex) {
                Logger.getLogger(PoiWriteExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
                                        workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					return fileName;

                                } catch (FileNotFoundException e)
				{
					e.printStackTrace();
					return "";
				} catch (IOException e)
				{
					e.printStackTrace();
					return "";
				}


  }
  
  public static String exportExcelSheetForAllRepsData(Vector<RepExcelModel> RepResults,String directionFile)
  {
      System.out.println("exportExcelSheetForAllRepsData");
      java.util.Date dateNow = new java.util.Date();
        int imonth = dateNow.getMonth() + 1;
        int iyear = dateNow.getYear() + 1900;
        String strdate = (new StringBuffer("[")).append(dateNow.getDate()).append("-").append(imonth).append("-").append(iyear).append("]-").append(dateNow.getHours()).append(".").append(dateNow.getMinutes()).append(".").append(dateNow.getSeconds()).append("_").toString();
        String fileName = strdate+"all_reps_file_report.xls";
       FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(directionFile + Slach + fileName);

					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet worksheet = workbook.createSheet("POS Worksheet");

					ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();
                                        ArrayList<ArrayList<HSSFCell>> cells=new ArrayList<ArrayList<HSSFCell>>();
					//42 //45
                                        for(int i=1; i<=12;i++){
                                        ArrayList<HSSFCell> cell = new ArrayList<HSSFCell>();
                                            cells.add(cell);
                                        }

                                       

                                        for (int i=0;i<=RepResults.size();i++){

                                            rows.add(worksheet.createRow(i));
                                             //42
                                            for(int cellno=0;cellno<12;cellno++){
                                                
                                                cells.get(cellno).add(rows.get(i).createCell((short) cellno));
                                            }

                                        }


                                      int header=0;
                                      cells.get(header).get(0).setCellValue("SaleRep Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("SaleRep Email");
                                      header++;
                                      cells.get(header).get(0).setCellValue("SaleRep Mobile");
                                      header++;
                                      cells.get(header).get(0).setCellValue("SaleRep Address");
                                      header++;
                                      cells.get(header).get(0).setCellValue("TeamLeader Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("TeamLeader Email");
                                      header++;
                                      cells.get(header).get(0).setCellValue("TeamLeader Mobile");
                                      header++;
                                      cells.get(header).get(0).setCellValue("TeamLeader Address");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Supervisor Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Supervisor Email");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Supervisor Mobile");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Supervisor Address");
                                      
                                      
            try {
                Connection con = Utility.getConnection();


                             for(int i=1;i<=RepResults.size();i++)
                             {
                                 RepExcelModel ss = RepResults.get(i-1);
                                int j=0;
                                cells.get(j).get(i).setCellValue(RepResults.get(i-1).getRepName().compareTo("null")==0 ? "" : RepResults.get(i-1).getRepName());
                                j++;
                                cells.get(j).get(i).setCellValue(RepResults.get(i-1).getRepEmail().compareTo("null")==0 ? "" : RepResults.get(i-1).getRepEmail());
                                j++;
                                cells.get(j).get(i).setCellValue(RepResults.get(i-1).getRepMobile().compareTo("null")==0 ? "" : RepResults.get(i-1).getRepMobile());
                                j++;
                                cells.get(j).get(i).setCellValue(RepResults.get(i-1).getRepAddress().compareTo("null")==0 ? "" : RepResults.get(i-1).getRepAddress());
                                j++;
                                cells.get(j).get(i).setCellValue(RepResults.get(i-1).getTeamleaderName().compareTo("null")==0 ? "" : RepResults.get(i-1).getTeamleaderName());
                                j++;
                                cells.get(j).get(i).setCellValue(RepResults.get(i-1).getTeamleaderEmail().compareTo("null")==0 ? "" : RepResults.get(i-1).getTeamleaderEmail());
                                j++;
                                cells.get(j).get(i).setCellValue(RepResults.get(i-1).getTeamleaderMobile().compareTo("null")==0 ? "" : RepResults.get(i-1).getTeamleaderMobile());
                                j++;
                                cells.get(j).get(i).setCellValue(RepResults.get(i-1).getTeamleaderAddress().compareTo("null")==0 ? "" : RepResults.get(i-1).getTeamleaderAddress());
                                j++;
                                cells.get(j).get(i).setCellValue(RepResults.get(i-1).getSupervisorName().compareTo("null")==0 ? "" : RepResults.get(i-1).getSupervisorName());
                                j++;
                                cells.get(j).get(i).setCellValue(RepResults.get(i-1).getSupervisorEmail().compareTo("null")==0 ? "" : RepResults.get(i-1).getSupervisorEmail());
                                j++;
                                cells.get(j).get(i).setCellValue(RepResults.get(i-1).getSupervisorMobile().compareTo("null")==0 ? "" : RepResults.get(i-1).getSupervisorMobile());
                                j++;
                                cells.get(j).get(i).setCellValue(RepResults.get(i-1).getSupervisorAddress().compareTo("null")==0 ? "" : RepResults.get(i-1).getSupervisorAddress());
                                
                             }
              con.close();
 } catch (SQLException ex) {
                Logger.getLogger(PoiWriteExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
                                        workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					return fileName;

                                } catch (FileNotFoundException e)
				{
					e.printStackTrace();
					return "";
				} catch (IOException e)
				{
					e.printStackTrace();
					return "";
				}


  }
  
  public static String exportExcelSheetForAllSupervisorsData(Vector<SupervisorExcelModel> SupervisorResults,String directionFile)
  {
      System.out.println("exportExcelSheetForAllSupervisorsData");
      java.util.Date dateNow = new java.util.Date();
        int imonth = dateNow.getMonth() + 1;
        int iyear = dateNow.getYear() + 1900;
        String strdate = (new StringBuffer("[")).append(dateNow.getDate()).append("-").append(imonth).append("-").append(iyear).append("]-").append(dateNow.getHours()).append(".").append(dateNow.getMinutes()).append(".").append(dateNow.getSeconds()).append("_").toString();
        String fileName = strdate+"all_supervisors_file_report.xls";
       FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(directionFile + Slach + fileName);

					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet worksheet = workbook.createSheet("POS Worksheet");

					ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();
                                        ArrayList<ArrayList<HSSFCell>> cells=new ArrayList<ArrayList<HSSFCell>>();
					//42 //45
                                        for(int i=1; i<=4;i++){
                                        ArrayList<HSSFCell> cell = new ArrayList<HSSFCell>();
                                            cells.add(cell);
                                        }

                                       

                                        for (int i=0;i<=SupervisorResults.size();i++){

                                            rows.add(worksheet.createRow(i));
                                             //42
                                            for(int cellno=0;cellno<4;cellno++){
                                                
                                                cells.get(cellno).add(rows.get(i).createCell((short) cellno));
                                            }

                                        }


                                      int header=0;
                                      cells.get(header).get(0).setCellValue("Supervisor Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Supervisor Email");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Supervisor Mobile");
                                      header++;
                                      cells.get(header).get(0).setCellValue("Supervisor Address");
                                      
                                      
            try {
                Connection con = Utility.getConnection();


                             for(int i=1;i<=SupervisorResults.size();i++)
                             {
                                 SupervisorExcelModel ss = SupervisorResults.get(i-1);
                                int j=0;
                                cells.get(j).get(i).setCellValue(SupervisorResults.get(i-1).getSupervisorName().compareTo("null")==0 ? "" : SupervisorResults.get(i-1).getSupervisorName());
                                j++;
                                cells.get(j).get(i).setCellValue(SupervisorResults.get(i-1).getSupervisorEmail().compareTo("null")==0 ? "" : SupervisorResults.get(i-1).getSupervisorEmail());
                                j++;
                                cells.get(j).get(i).setCellValue(SupervisorResults.get(i-1).getSupervisorMobile().compareTo("null")==0 ? "" : SupervisorResults.get(i-1).getSupervisorMobile());
                                j++;
                                cells.get(j).get(i).setCellValue(SupervisorResults.get(i-1).getSupervisorAddress().compareTo("null")==0 ? "" : SupervisorResults.get(i-1).getSupervisorAddress());
                                
                             }
              con.close();
 } catch (SQLException ex) {
                Logger.getLogger(PoiWriteExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
                                        workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					return fileName;

                                } catch (FileNotFoundException e)
				{
					e.printStackTrace();
					return "";
				} catch (IOException e)
				{
					e.printStackTrace();
					return "";
				}


  }
  
  public static String exportExcelSheetForAllTeamleadersData(Vector<TeamleaderExcelModel> TeamleaderResults,String directionFile)
  {
      System.out.println("exportExcelSheetForAllTeamleadersData");
      java.util.Date dateNow = new java.util.Date();
        int imonth = dateNow.getMonth() + 1;
        int iyear = dateNow.getYear() + 1900;
        String strdate = (new StringBuffer("[")).append(dateNow.getDate()).append("-").append(imonth).append("-").append(iyear).append("]-").append(dateNow.getHours()).append(".").append(dateNow.getMinutes()).append(".").append(dateNow.getSeconds()).append("_").toString();
        String fileName = strdate+"all_teamleaders_file_report.xls";
       FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(directionFile + Slach + fileName);

					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet worksheet = workbook.createSheet("POS Worksheet");

					ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();
                                        ArrayList<ArrayList<HSSFCell>> cells=new ArrayList<ArrayList<HSSFCell>>();
					//42 //45
                                        for(int i=1; i<=4;i++){
                                        ArrayList<HSSFCell> cell = new ArrayList<HSSFCell>();
                                            cells.add(cell);
                                        }

                                       

                                        for (int i=0;i<=TeamleaderResults.size();i++){

                                            rows.add(worksheet.createRow(i));
                                             //42
                                            for(int cellno=0;cellno<4;cellno++){
                                                
                                                cells.get(cellno).add(rows.get(i).createCell((short) cellno));
                                            }

                                        }


                                      int header=0;
                                      cells.get(header).get(0).setCellValue("TeamLeader Name");
                                      header++;
                                      cells.get(header).get(0).setCellValue("TeamLeader Email");
                                      header++;
                                      cells.get(header).get(0).setCellValue("TeamLeader Mobile");
                                      header++;
                                      cells.get(header).get(0).setCellValue("TeamLeader Address");
                                      
                                      
            try {
                Connection con = Utility.getConnection();


                             for(int i=1;i<=TeamleaderResults.size();i++)
                             {
                                 TeamleaderExcelModel ss = TeamleaderResults.get(i-1);
                                int j=0;
                                cells.get(j).get(i).setCellValue(TeamleaderResults.get(i-1).getTeamleaderName().compareTo("null")==0 ? "" : TeamleaderResults.get(i-1).getTeamleaderName());
                                j++;
                                cells.get(j).get(i).setCellValue(TeamleaderResults.get(i-1).getTeamleaderEmail().compareTo("null")==0 ? "" : TeamleaderResults.get(i-1).getTeamleaderEmail());
                                j++;
                                cells.get(j).get(i).setCellValue(TeamleaderResults.get(i-1).getTeamleaderMobile().compareTo("null")==0 ? "" : TeamleaderResults.get(i-1).getTeamleaderMobile());
                                j++;
                                cells.get(j).get(i).setCellValue(TeamleaderResults.get(i-1).getTeamleaderAddress().compareTo("null")==0 ? "" : TeamleaderResults.get(i-1).getTeamleaderAddress());
                                
                             }
              con.close();
 } catch (SQLException ex) {
                Logger.getLogger(PoiWriteExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
                                        workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					return fileName;

                                } catch (FileNotFoundException e)
				{
					e.printStackTrace();
					return "";
				} catch (IOException e)
				{
					e.printStackTrace();
					return "";
				}


  }
  
  
  
public static String  getExcelForRatedActivation(Vector <RatedFileError> SIMNumber,String fileDir)
{
  try
				{

					FileOutputStream fileOut = new FileOutputStream(fileDir
							+Slach+ "rated_activation_report.xls");
					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet worksheet = workbook.createSheet("rated Worksheet");

					ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();

					ArrayList<HSSFCell> cellA = new ArrayList<HSSFCell>();
					ArrayList<HSSFCell> cellB = new ArrayList<HSSFCell>();
                                        ArrayList<HSSFCell> cellC = new ArrayList<HSSFCell>();

					for (int i = 0; i < SIMNumber.size() + 1; i++)
					{

						rows.add(worksheet.createRow((short) i));
						cellA.add(rows.get(i).createCell((short) 0));
                                                cellB.add(rows.get(i).createCell((short) 1));
                                                cellC.add(rows.get(i).createCell((short) 2));

					}

                                        cellA.get(0).setCellValue("Line Number");
					cellB.get(0).setCellValue("SIM_Serial_Number");
                                        cellC.get(0).setCellValue("Reason");



					if (!SIMNumber.isEmpty())
					{
						for (int i = 1; i <=SIMNumber.size(); i++)
						{
                                                        cellA.get(i).setCellValue(Integer.toString(SIMNumber.get(i-1).getLineNumber()));
                                                        cellB.get(i).setCellValue(SIMNumber.get(i-1).getSIMNumber());
                                                        if(SIMNumber.get(i-1).getSIMNumber()==null)
                                                        cellC.get(i).setCellValue("The SIM Number is not contain numbers only");
                                                        if(SIMNumber.get(i-1).getFirstRatedsCall()==null)
                                                        cellC.get(i).setCellValue("The FirstRatedsCall is not vaild ");
                                                }


                                       }


					workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					return "rated_activation_report.xls";
                                } catch (FileNotFoundException e)
				{
					e.printStackTrace();
					return "";
				} catch (IOException e)
				{
					e.printStackTrace();
					return "";
				}

}

public static String ExportExcelPOSPaymentLevelChanges (Vector <POSStatusCase> refusedPOSs,
					String changeStatusType,String fileDir)
			{
				try
				{

					FileOutputStream fileOut = new FileOutputStream(fileDir
							+Slach+ "pos_changes_report.xls");
					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet worksheet = workbook.createSheet("POS Worksheet");

					ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();

					ArrayList<HSSFCell> cellA = new ArrayList<HSSFCell>();
					ArrayList<HSSFCell> cellB = new ArrayList<HSSFCell>();


					for (int i = 0; i <refusedPOSs.size() + 1; i++)
					{

                                              rows.add(worksheet.createRow((short) i));
						cellA.add(rows.get(i).createCell((short) 0));
						cellB.add(rows.get(i).createCell((short) 1));




                                       }


					cellA.get(0).setCellValue("Row number");
					cellB.get(0).setCellValue("Message");



					if (!refusedPOSs.isEmpty())
					{
						for (int i = 1; i <=refusedPOSs.size(); i++)
						{

							POSStatusCase current = (POSStatusCase) refusedPOSs.get(i-1);


							if(changeStatusType.equals("POS")&&current.getCaseExist()==1)
                                                        {
                                                            cellA.get(i).setCellValue(current.getPOSCode());
                                                            cellB.get(i).setCellValue("This POS is Not Exist");
                                                        }else{
                                                                if(changeStatusType.equals("Payment")&&current.getCaseExist()==1)
                                                                {
                                                                   cellA.get(i).setCellValue(current.getPOSCode());
                                                                   cellB.get(i).setCellValue("This POS is Not Exist");
                                                                }

                                                             }


                                                        }

						}


					workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					return "pos_changes_report.xls";
				} catch (FileNotFoundException e)
				{
					e.printStackTrace();
					return "";
				} catch (IOException e)
				{
					e.printStackTrace();
					return "";
				}

			}


    public static void main(String args []) {

         Connection con;
        try {
            con = Utility.getConnection();
            
            buildSimInfoExcelFile(con, "34", "D:\\testPDF\\");

//            Vector<CaseModel> STKInvalid = STKDAO.getValidInvalidRows(con, false);
//            Vector<CaseModel> STKvalid = STKDAO.getValidInvalidRows(con, false);
//            HashMap<Integer, HSSFRow> validRows = buildPreActivateExcel(STKInvalid, STKvalid, "C:\\Documents and Settings\\mabdelaal\\My Documents\\SCM\\scm upload.xls",
//                    "C:\\Documents and Settings\\mabdelaal\\My Documents\\SCM\\");
//
//            HashMap<String, HSSFRow> validRowsToActivate = getRowsToActivate(validRows, defPath);
//            HashMap<String, String> dcmByMobilNumber = STKDAO.getDCMByMobileNumber(con);
//            Statement st = con.createStatement();
//            for (String validMobileNumber : validRowsToActivate.keySet()) {
//                String dcm_id = dcmByMobilNumber.get(validMobileNumber);
//                if (dcm_id != null && dcm_id.compareTo("") != 0) {
//                    int rowCount = DistributerSTKDataDAO.activeForValidRows(st, dcm_id, validMobileNumber);
//                    if (rowCount == 0) {
//                        validRowsToActivate.remove(validMobileNumber);
//                    }
//                }
//                DBUtil.close(st);
//            }
////            DistributerSTKDataDAO.doAfterActivate(con);
//            buildTemplateActivateExcel("C:\\Documents and Settings\\mabdelaal\\My Documents\\SCM\\", validRowsToActivate);




            Utility.closeConnection(con);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

       
    }

    public static  HashMap<String,HSSFRow> getRowsToActivate( HashMap<Integer,HSSFRow> validRows,String pathOfPreActivateExcel){
        HashMap<String, HSSFRow> rowsToActivate = new HashMap<String, HSSFRow>();
    if (!validRows.isEmpty()) {

            HashMap<Integer, HSSFRow> currentRowsUploadedByUser = refactorOldExcelFile(pathOfPreActivateExcel);
            if (!currentRowsUploadedByUser.isEmpty()) {
                for (Integer rowNum : currentRowsUploadedByUser.keySet()) {
                    HSSFRow newRow = currentRowsUploadedByUser.get(rowNum);
                    HSSFCell cell = newRow.getCell(mobileNumber);
                    if (cell != null) {
                        String newMobileNumber = cell.getStringCellValue();

                        if (searchOnOldRowsForValidation(validRows, newMobileNumber)) {
                            rowsToActivate.put(newMobileNumber, newRow);
                        }
                    }
                }
            }
        }
        return rowsToActivate;

    }
    private static boolean searchOnOldRowsForValidation(HashMap<Integer, HSSFRow> validRows, String newMobileNumber) {
        boolean retrunBoolean = false;
        for (Integer rowNum : validRows.keySet()) {
            HSSFRow oldRow = validRows.get(rowNum);
            if (oldRow!=null){
            HSSFCell cell = oldRow.getCell(mobileNumber);
            if (cell.getStringCellValue().compareTo(newMobileNumber) == 0) {
                retrunBoolean = true;
            }
            }
            if (retrunBoolean) break;
        }
        return retrunBoolean;
    }
    public static String buildSimInfoExcelFile(Connection con , String fileId ,String filePath){
            
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Result");
        workbook.setActiveSheet(0);
        wbFont= workbook.createFont();
        wbFont.setFontHeightInPoints((short) 10);
        wbFont.setFontName("Arial");
        wbFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        
        addHeaderToSimInfoSheet(sheet);
        
        addSimInfoResultRows(con, sheet, fileId);
        
        
        return  writeExcelFile(workbook, filePath, "SIMInfoResult.xlsx");
    }
    
    private static void addSimInfoResultRows(Connection con, XSSFSheet sheet, String fileId){
    Statement st = null;
        ResultSet rs = null;
        try {
             st = con.createStatement();
             rs = AuthResDAO.getSimInfoResult(st, fileId);             
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next())
            {
            int rowNumber = sheet.getLastRowNum()+1;    
            XSSFRow row = sheet.createRow(rowNumber);
            row.createCell(0).setCellValue(rs.getString("SIM_SERIAL"));
            row.createCell(1).setCellValue(rs.getString("XYZ_REQ_POS_CODE"));
            row.createCell(2).setCellValue(rs.getDate("XYZ_REQ_CREATION_TIME")==null ? "" : formatter.format(rs.getDate("XYZ_REQ_CREATION_TIME")));
            row.createCell(3).setCellValue(rs.getString("XYZ_STATUS_DESCRIPTION"));
            row.createCell(4).setCellValue(rs.getString("NTRA_POS_CODE"));
            row.createCell(5).setCellValue(rs.getDate("NTRA_TIME_STAMP")==null ? "" : formatter.format(rs.getDate("NTRA_TIME_STAMP")));
            row.createCell(6).setCellValue(rs.getString("NTRA_STATUS_DESCRIPTION"));
            row.createCell(7).setCellValue(rs.getString("SFR_POS_CODE"));
            row.createCell(8).setCellValue(rs.getString("SFR_SECOND_POS_CODE"));
            row.createCell(9).setCellValue(rs.getDate("SFR_COMMISSION_DATE")==null ? "" : formatter.format(rs.getDate("SFR_COMMISSION_DATE")));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
        DBUtil.close(st);
        DBUtil.close(rs);
        }
    
    }
    
    private static String writeExcelFile(XSSFWorkbook workbook , String filePath, String fileName ){
     FileOutputStream fos = null;
     String returnFileName= "";
        try {
            java.util.Date dateNow = new java.util.Date();
            int imonth = dateNow.getMonth() + 1;
            int iyear = dateNow.getYear() + 1900;
            String strdate = (new StringBuffer("[")).append(dateNow.getDate()).append("-").append(imonth).append("-").append(iyear).append("]-").append(dateNow.getHours()).append(".").append(dateNow.getMinutes()).append(".").append(dateNow.getSeconds()).append("_").toString();
            defPath = filePath + strdate + fileName;
            returnFileName = strdate + fileName;
            fos = new FileOutputStream(new File(defPath));
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return returnFileName;
        }
    
    }
    public static HashMap<Integer,HSSFRow> buildPreActivateExcel(Vector<CaseModel> STKInvalid,Vector<CaseModel> STKvalid,String oldPath,String newPath){
        HashMap<Integer,HSSFRow> oldFileRows = refactorOldExcelFile(oldPath);
        HashMap<Integer,HSSFRow>  validRows = new HashMap<Integer,HSSFRow>();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet validSheet = workbook.createSheet("Valid Numbers");
        HSSFSheet invalidSheet = workbook.createSheet("Invalid Numbers");
        workbook.setActiveSheet(0);
        wbFont= workbook.createFont();
        wbFont.setFontHeightInPoints((short)10);
        wbFont.setFontName("Arial");
        wbFont.setBoldweight(Font.BOLDWEIGHT_BOLD);


        int countOfCols = addHeaderToSheets(validSheet, invalidSheet);

        validRows = addInvalidValidRowsToExcel(oldFileRows, validSheet, invalidSheet, STKInvalid, STKvalid, countOfCols);

//        wbFont.setItalic(false);
        FileOutputStream fos = null;

        try {
            java.util.Date dateNow = new java.util.Date();
        int imonth = dateNow.getMonth() + 1;
        int iyear = dateNow.getYear() + 1900;
        String strdate = (new StringBuffer("[")).append(dateNow.getDate()).append("-").append(imonth).append("-").append(iyear).append("]-").append(dateNow.getHours()).append(".").append(dateNow.getMinutes()).append(".").append(dateNow.getSeconds()).append("_").toString();
        defPath = newPath+strdate+"DistStkPreActivate.xls";
            fos = new FileOutputStream(new File(defPath));
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
return validRows;
        }

    }

    public String getOldPath(){
    return defPath;
    }
    public String getTemplatePath(){
    return defPath;
    }
    public static void buildTemplateActivateExcel(String path,HashMap<String, HSSFRow> activatedRows){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet Sheet = workbook.createSheet("Sheet1");

        workbook.setActiveSheet(0);
        wbFont= workbook.createFont();
        wbFont.setFontHeightInPoints((short)11);
        wbFont.setFontName("Calibri");        
        addHeaderToSheet(Sheet);
addTemplateRows(Sheet, activatedRows);
//        wbFont.setItalic(false);
        FileOutputStream fos = null;

        try {
            java.util.Date dateNow = new java.util.Date();
        int imonth = dateNow.getMonth() + 1;
        int iyear = dateNow.getYear() + 1900;
        String strdate = (new StringBuffer("[")).append(dateNow.getDate()).append("-").append(imonth).append("-").append(iyear).append("]-").append(dateNow.getHours()).append(".").append(dateNow.getMinutes()).append(".").append(dateNow.getSeconds()).append("_").toString();
        defPath = path+strdate+"DistStkActivatedNo.xls";
            fos = new FileOutputStream(new File(defPath));
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

    }
    public static void addTemplateRows(HSSFSheet sheet, HashMap<String, HSSFRow> activatedRows) {
        if (activatedRows != null && !activatedRows.isEmpty()) {
            String pLogin= "", pMobile= "", userNamePre = "MR", userName= "", shortName= "",
                    catCode= "", exCode= "", contPerson= "", address= "", city= "",
                    stat= "", ssn= "", country= "", loginId= "", pass= "", mobNumber= "",
                    pin= "", geoCode= "", roleCode= "", servs= "", comProfile= "", transPro= "", outCode= "", subOutCode= "",
                    grade= "", allow = "";
            HSSFRow row = null;
            HSSFCell cell = null;

            for (String temMobileNumber : activatedRows.keySet()) {
                boolean isPOS = false;
                int indx = 0;
                pLogin = activatedRows.get(temMobileNumber).getCell(indx++).getStringCellValue();
                pMobile = activatedRows.get(temMobileNumber).getCell(indx++).getStringCellValue();
                userName = activatedRows.get(temMobileNumber).getCell(indx++).getStringCellValue();
                catCode = activatedRows.get(temMobileNumber).getCell(indx++).getStringCellValue();
                exCode = activatedRows.get(temMobileNumber).getCell(indx++).getStringCellValue();
                address = activatedRows.get(temMobileNumber).getCell(indx++).getStringCellValue();
                city = activatedRows.get(temMobileNumber).getCell(indx++).getStringCellValue();
                loginId = activatedRows.get(temMobileNumber).getCell(indx++).getStringCellValue();
                pass = activatedRows.get(temMobileNumber).getCell(indx++).getStringCellValue();
                mobNumber = activatedRows.get(temMobileNumber).getCell(indx++).getStringCellValue();
                pin = activatedRows.get(temMobileNumber).getCell(indx++).getStringCellValue();

                if (catCode!=null&&catCode.compareTo("")!=0 && catCode.trim().toLowerCase().compareTo("pos")==0)
                {isPOS = true;
                }else isPOS = false;

                catCode = isPOS ? "POS" : "DA";
                geoCode = isPOS ? "SUB1" : "AR1";
                roleCode = catCode;
                servs = "RC";
                comProfile = isPOS ? "10" : "15";
                transPro = isPOS ? "11" : "8";
                grade = isPOS ? "PSA1" : "DTA1";
              row = sheet.createRow(sheet.getLastRowNum()+1);
              cell = row.createCell(0);
              cell.setCellValue(pLogin);
              row.createCell(row.getLastCellNum()).setCellValue(pMobile);
              row.createCell(row.getLastCellNum()).setCellValue(userNamePre);
              row.createCell(row.getLastCellNum()).setCellValue(userName);
              row.createCell(row.getLastCellNum()).setCellValue(shortName);
              row.createCell(row.getLastCellNum()).setCellValue(catCode);
              row.createCell(row.getLastCellNum()).setCellValue(exCode);
              row.createCell(row.getLastCellNum()).setCellValue(contPerson);
              row.createCell(row.getLastCellNum()).setCellValue(address);
              row.createCell(row.getLastCellNum()).setCellValue(city);
              row.createCell(row.getLastCellNum()).setCellValue(stat);
              row.createCell(row.getLastCellNum()).setCellValue(ssn);
              row.createCell(row.getLastCellNum()).setCellValue(country);
              row.createCell(row.getLastCellNum()).setCellValue(loginId);
              row.createCell(row.getLastCellNum()).setCellValue(pass);
              row.createCell(row.getLastCellNum()).setCellValue(mobNumber);
              row.createCell(row.getLastCellNum()).setCellValue(pin);
              row.createCell(row.getLastCellNum()).setCellValue(geoCode);
              row.createCell(row.getLastCellNum()).setCellValue(roleCode);
              row.createCell(row.getLastCellNum()).setCellValue(servs);
              row.createCell(row.getLastCellNum()).setCellValue(comProfile);
              row.createCell(row.getLastCellNum()).setCellValue(transPro);
              row.createCell(row.getLastCellNum()).setCellValue(outCode);
              row.createCell(row.getLastCellNum()).setCellValue(subOutCode);
              row.createCell(row.getLastCellNum()).setCellValue(grade);
              row.createCell(row.getLastCellNum()).setCellValue(allow);


            }
            if (row!=null){
            int lastCellCountForAutosize = row.getLastCellNum();
            for (int i = 0; i < lastCellCountForAutosize; i++) {
                row.getSheet().autoSizeColumn(i);

            }
            }
        }
    }
    private static int addHeaderToSheet(HSSFSheet Sheet) {
        HSSFRow sheetHeader = Sheet.createRow(0);
        sheetHeader.setHeightInPoints(15);

        String[] headers = new String[]{"Parent login ID**", "Parent mobile number**", "UserNamePrefix", "User name*", "ShortName", "Category code*", "External code", "ContactPerson", "Address1", "City", "State", "SSN", "Country", "Login ID1", "Password", "Mobile number*", "PIN1", "Geography code", "Group role code", "Services*", "Commission profile*", "Transfer profile*", "Outlet code", "Sub-outlet code", "Grade*", "Allow low balance alert"};
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cellA = sheetHeader.createCell(i);
            addCellStyleAndValue(cellA, headers[i]);
            sheetHeader.getSheet().autoSizeColumn(i);
        }
        return headers.length;
    }
    private static int addHeaderToSimInfoSheet(XSSFSheet Sheet) {
        XSSFRow sheetHeader = Sheet.createRow(0);
        sheetHeader.setHeightInPoints(15);
        
        String[] headers = new String[]{"      Sim_serial     "," xyz_req_pos_code","xyz_req_creation_time","xyz_status_description","ntra_pos_code","ntra_time_stamp","ntra_status_description","sfr_pos_code","sfr_second_pos_code","sfr_commission_date"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cellA = sheetHeader.createCell(i);
            addCellStyleAndValue(cellA, headers[i]);
            sheetHeader.getSheet().autoSizeColumn(i);
        }
        return headers.length;
    }

    private static int addHeaderToSheets(HSSFSheet validSheet, HSSFSheet invalidSheet) {
        HSSFRow validHeader = validSheet.createRow(0);
        validHeader.setHeightInPoints(27);
        HSSFRow invalidHeader = invalidSheet.createRow(0);
        invalidHeader.setHeightInPoints(27);
        String[] headers = new String[]{"Parent login ID**", "Parent mobile number**", "User name*", "Category code*", "External code", "Address1", "City", "Login ID*", "Password", "Mobile number*", "PIN"};
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cellA = validHeader.createCell(i);
            addCellStyleAndValue(cellA, headers[i]);
            validHeader.getSheet().autoSizeColumn(i);
        }
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cellA = invalidHeader.createCell(i);
            addCellStyleAndValue(cellA, headers[i]);
            invalidHeader.getSheet().autoSizeColumn(i);
        }
        return headers.length;
    }
    private static void addCellStyleAndValue(HSSFCell cell,String value){
        HSSFCellStyle style = cell.getCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER) ;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER) ;
        
        style.setFont(wbFont);
        style.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
        
        
    cell.setCellValue(new HSSFRichTextString(value));
    cell.setCellStyle(style);

    }
    private static void addCellStyleAndValue(XSSFCell cell,String value){
        XSSFCellStyle style = cell.getCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER) ;
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER) ;
        
        style.setFont(wbFont);
        style.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
        
        
    cell.setCellValue(new XSSFRichTextString(value));
    cell.setCellStyle(style);

    }

    private static HashMap<Integer,HSSFRow> refactorOldExcelFile(String oldPath)
    {
      InputStream myxls;
      HashMap<Integer,HSSFRow> result= new HashMap<Integer,HSSFRow>();
        try {
            myxls = new FileInputStream(oldPath);
            HSSFWorkbook wb     = new HSSFWorkbook(myxls);
            HSSFSheet sheet = wb.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
//            System.out.println("lastRowNum iss "+lastRowNum);
            for (int i = 0; i <= lastRowNum; i++) {
                result.put(i, sheet.getRow(i));

            }

            myxls.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
      
      


        return result;
    }

    private static HashMap<Integer,HSSFRow> addInvalidValidRowsToExcel(HashMap<Integer,HSSFRow> oldFileRows,HSSFSheet validSheet,HSSFSheet invalidSheet,Vector<CaseModel> STKInvalid,Vector<CaseModel> STKvalid,int countOfCols)
    {
        HashMap<Integer,HSSFRow> validRows = new HashMap<Integer,HSSFRow>();
         for (CaseModel caseModel : STKvalid) {
             int colNumber = validSheet.getLastRowNum()+1;
             HSSFRow validRow = validSheet.createRow(colNumber);
             HSSFRow oldValidRow = oldFileRows.get(caseModel.getrowNumber());
             mergTwoCells(oldValidRow, validRow, countOfCols);
             validRow.getSheet().autoSizeColumn(colNumber);
             validRows.put(caseModel.getrowNumber(), oldFileRows.get(caseModel.getrowNumber()));
        }
         for (CaseModel caseModel : STKInvalid) {
             int colNumber = invalidSheet.getLastRowNum()+1;
             HSSFRow invalidRow = invalidSheet.createRow(invalidSheet.getLastRowNum()+1);
             HSSFRow oldinvalidRow = oldFileRows.get(caseModel.getrowNumber());
             mergTwoCells(oldinvalidRow, invalidRow, countOfCols);
             invalidRow.getSheet().autoSizeColumn(colNumber);
        }
        return validRows;

    }

    private static void mergTwoCells(HSSFRow old,HSSFRow newRow, int countOfCols){        
        for (int i = 0; i < countOfCols; i++) {
            try {
            HSSFCell oldcel = old.getCell(i) ;
            String oldValue = oldcel.getStringCellValue();
            HSSFCell cel = newRow.createCell(i) ;
            HSSFCellStyle st = cel.getCellStyle();
            st.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            st.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            st.setBorderRight(HSSFCellStyle.BORDER_THIN);
            st.setBorderTop(HSSFCellStyle.BORDER_THIN);            
            cel.setCellStyle(st);
            cel.setCellValue(oldValue);
            } catch (Exception e) {
            }
            
        }
    }


    public static String mobinilReport(Vector <String> resultcase,
					String fileDir)
			{
				try
				{

			/*		FileOutputStream fileOut = new FileOutputStream(fileDir
							+Slach+ "mobinilinfo.xlsx");

                                        System.out.print(fileDir);
					Workbook workbook = new XSSFWorkbook();
					Sheet worksheet = workbook.createSheet("mobinil Worksheet");

					ArrayList<Row> rows = new ArrayList<Row>();

					ArrayList<Cell> cellA = new ArrayList<Cell>();

                                        System.out.println("resultCase Size = "+ resultcase.size());


					for (int i = 0; i < resultcase.size() + 1; i++)
					{
                                                rows.add(worksheet.createRow((short) i));

                                                if ((short) i != i )
                                                    System.out.println("alert i="+ i +"  short i ="+ ((short )i ));

						cellA.add(rows.get(i).createCell((short) 0));

					}


					cellA.get(0).setCellValue("Sim number");


					if (!resultcase.isEmpty())
					{
						for (int i = 1; i <=resultcase.size(); i++)
						{

							cellA.get(i).setCellValue(resultcase.get(i-1));


						}
					}

					workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					return "mobinilinfo.xlsx";*/

           FileWriter writer = new FileWriter(fileDir+Slach+ "mobinilinfo.csv");

	    writer.append("Sim Number");
	    writer.append('\n');
	    
            if (!resultcase.isEmpty())
       {
	for (int i = 1; i <=resultcase.size(); i++)
	{

	writer.append(resultcase.get(i-1));
        writer.append('\n');


	}
	}

	    //generate whatever data you want

	    writer.flush();
	    writer.close();
				return "mobinilinfo.csv";
                                
				} catch (FileNotFoundException e)
				{
					e.printStackTrace();
					return "";
				} catch (IOException e)
				{
					e.printStackTrace();
					return "";
				}

			}


}

