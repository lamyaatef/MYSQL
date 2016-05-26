/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import jxl.*;
import jxl.read.biff.BiffException;

/**
 *
 * @author Shady Akl
 */
public class ExcelReader {

    public static void contentReading(InputStream fileInputStream) {
        WorkbookSettings ws = null;
        Workbook workbook = null;
        Sheet s = null;
        Cell rowData[] = null;
        int rowCount = '0';
        int columnCount = '0';
        DateCell dc = null;
        int totalSheet = 0;

        try {
            ws = new WorkbookSettings();
            ws.setLocale(new Locale("en", "EN"));
            workbook = Workbook.getWorkbook(fileInputStream, ws);

            totalSheet = workbook.getNumberOfSheets();
            if (totalSheet > 0) {
                System.out.println("Total Sheet Found:" + totalSheet);
                for (int j = 0; j < totalSheet; j++) {
                    System.out.println("Sheet Name:" + workbook.getSheet(j).getName());
                }
            }

            //Getting Default Sheet i.e. 0
            s = workbook.getSheet(0);

            //Reading Individual Cell
            getHeadingFromXlsFile(s);

            //Total Total No Of Rows in Sheet, will return you no of rows that are occupied with some data
            System.out.println("Total Rows inside Sheet:" + s.getRows());
            rowCount = s.getRows();

            //Total Total No Of Columns in Sheet
            System.out.println("Total Column inside Sheet:" + s.getColumns());
            columnCount = s.getColumns();
            System.out.println("columnCount : " + columnCount);

            //Reading Individual Row Content
            for (int i = 0; i < rowCount; i++) {
                //Get Individual Row
                rowData = s.getRow(i);
                if (rowData.length != 0) {
                    for (int j = 0; j < columnCount; j++) {
                        switch (j) {
                            case 0:
                                System.out.println("Credit Advice ID:" + rowData[j].getContents());
                                break;

                            case 1:
                                System.out.println("Credut Advice Serial:" + rowData[j].getContents());
                                break;

                            case 2:
                                System.out.println("Credut Advice Status:" + rowData[j].getContents());
                                break;

                            case 3:
                                System.out.println("Credut Advice Date:" + rowData[j].getContents());
                                break;

                            default:
                                break;

                        }
                    }
                }
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    public static void getHeadingFromXlsFile(Sheet sheet) {
        int columnCount = sheet.getColumns();
        for (int i = 0; i < columnCount; i++) {
            System.out.println(sheet.getCell(i, 0).getContents());
        }
    }
}
