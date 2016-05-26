package com.mobinil.mcss.spring.util;
import java.io.File;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;

import java.io.IOException;

import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.Row;

class XlstoCSV 

{

   static void xls(File inputFile, File outputFile) 

{

    // For storing data into CSV files
    StringBuffer data = new StringBuffer();
    try 
    {
    FileOutputStream fos = new FileOutputStream(outputFile);

    // Get the workbook object for XLS file
    HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(inputFile));
    // Get first sheet from the workbook
    HSSFSheet sheet = workbook.getSheetAt(0);
    Cell cell;
    Row row;

    // Iterate through each rows from first sheet
    Iterator<Row> rowIterator = sheet.iterator();
    
    int i = 0 ; 
    int columnIndex = 0 ; 
    while (rowIterator.hasNext()) 
    {
        row = rowIterator.next();
        if (i>0)
        {                             
            // For each row, iterate th"sora_order" "sora_order" "sora_order" ``rough each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            columnIndex = 0; 
            while (cellIterator.hasNext()) 
            {
                        if (columnIndex == 0)
                  {
                      data.append("{\"id\":");
                  }
                  else if (columnIndex == 1)
                  {
                      data.append(",\"sora_order\":");

                  }
                  else if(columnIndex == 2)
                  {
                      data.append(",\"sora_name\":");
                  }
                  else if (columnIndex ==3 )
                  {
                      data.append(",\"tarteb_elnzol\":");

                  }
                  else if (columnIndex == 4)
                  {   data.append(",\"ayat_number\":");

                  }
                  else if (columnIndex == 5)
                  {
                      data.append(",\"words_number\":");
                  }
                  else if(columnIndex == 6)
                  {
                      data.append(",\"characters_number\":");
                  }
                  else if (columnIndex == 7 )
                  {
                      data.append(",\"mkan_elnzol\":");
                  }


                      columnIndex ++;

                          cell = cellIterator.next();

                          switch (cell.getCellType()) 
                          {
                          case Cell.CELL_TYPE_BOOLEAN:
                                  data.append(cell.getBooleanCellValue() );
                                  break;

                          case Cell.CELL_TYPE_NUMERIC:

                                  data.append((int)cell.getNumericCellValue() );
                                  break;

                          case Cell.CELL_TYPE_STRING:
                                  data.append( cell.getStringCellValue() );
                                  break;

                          case Cell.CELL_TYPE_BLANK:
                                  data.append("" );
                                  break;

                          default:
                                  data.append(cell );
                          }


                  if (columnIndex == 8 )
                  {
                      data.append("},");
                  }
            }
        }
        i++;
        
    }

    fos.write(data.toString().getBytes());
    fos.close();
    }
    catch (FileNotFoundException e) 
    {
            e.printStackTrace();
    }
    catch (IOException e) 
    {
            e.printStackTrace();
    }
    }

    public static void main(String[] args) 
    {
            File inputFile = new File("/home/sand/Downloads/Book1.xls"); //Book1.xls .... 1000Q.xls
            File outputFile = new File("/home/sand/Downloads/tst.csv");
            xls(inputFile, outputFile);
    }
}