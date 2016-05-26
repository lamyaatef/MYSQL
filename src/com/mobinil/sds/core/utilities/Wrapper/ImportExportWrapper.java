package com.mobinil.sds.core.utilities.Wrapper;

import java.awt.Color;
import java.util.Vector;
import java.util.HashMap;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.*;
import jxl.write.*; 


public class ImportExportWrapper
{ 
  static HashMap dataHashMap;
    int column_number=0;
 /* public ExportMetaData getMetaData(HashMap paramHashMap)
  {
      ExportMetaData export_meta_data=new ExportMetaData();
      String str_column_number=(String) paramHashMap.get(InterfaceKey.COLUMN_NUMBER);
      column_number=Integer.parseInt(str_column_number);

      for(int i=1;i<=column_number;i++)
      {
          String filter_value=(String) paramHashMap.get(InterfaceKey.CONTROL_TEXT+i);
          String column_type=(String) paramHashMap.get(InterfaceKey.COLUMN_TYPE+i);
          String column_name=(String) paramHashMap.get(InterfaceKey.COLUMN_NAME+i);
          String column_alias=(String) paramHashMap.get(InterfaceKey.COLUMN_ALIAS+i);
          String filter_column=(String) paramHashMap.get(InterfaceKey.COLUMN_FILTER+i);

          if(filter_value.equals(""))
              filter_value="-1";

          export_meta_data.AddFilterValues(filter_value);
          export_meta_data.AddColumnAlias(column_alias);
          export_meta_data.AddColumnName(column_name);
          export_meta_data.AddColumnType(column_type);
          export_meta_data.AddColumnFilter(filter_column);

      }
      return export_meta_data;
  }*/
/*
  public void exportExcelFile(Connection con,HashMap paramHashMap,String sql,int startCol,int startRow)
  {
        ExportMetaData export_field = this.getMetaData(paramHashMap);

        String table_name=(String)paramHashMap.get(InterfaceKey.TABLE_NAME);
        String file_name=(String)paramHashMap.get(InterfaceKey.FILE_NAME);
        String sheet_name=(String)paramHashMap.get(InterfaceKey.SHEET_NAME);
        
        ExportDBData exportDBData = new ExportDBData(sql,column_number);
        MatrixData matrix_data = exportDBData.getMatrixData(con, table_name, export_field);

        String templatePath = ServiceOutageDAO.getUploadPath(con);
        exportExcelFile(templatePath, file_name, sheet_name, matrix_data, startCol, startRow);
  }
  public void exportPdfFile(Connection con,HashMap paramHashMap,String sql)
  {
        ExportMetaData export_field = this.getMetaData(paramHashMap);

        String table_name=(String)paramHashMap.get(InterfaceKey.TABLE_NAME);
        String file_name=(String)paramHashMap.get(InterfaceKey.FILE_NAME);
        String title_name="";
        
        ExportDBData exportDBData = new ExportDBData(sql,column_number);
        MatrixData matrix_data = exportDBData.getMatrixData(con, table_name, export_field);
        int columns=matrix_data.getColNumber();
        int rows=matrix_data.getData()[0].size();
        String templatePath = ServiceOutageDAO.getUploadPath(con);
        exportPdfFile(templatePath, file_name, title_name,matrix_data,columns,rows);
  }
  int data_size;
  public void exportPdfFile(String file_path,String file_name,String title_name,MatrixData matrix_data,int columns,int rows)
  {
        file_path+= "\\" + file_name+".pdf";
        Vector data_vector=null;
        String data="";
        try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file_path));
            document.open();
            //////////////////////////Start Headers/////////////////////////////////////
            Table t = new Table(columns,rows);
            
            for(int j=0;j<matrix_data.getColNumber();j++)
            {
                data_vector=matrix_data.getData()[j];

                t.setBorderColor(new Color(220, 255, 100));

                t.setPadding(3);

                t.setSpacing(0);
                
                t.setBorderWidth(1);
                
                Cell c = new Cell(data_vector.get(0).toString());
                c.setBackgroundColor(Color.yellow);
                c.setHeader(true);

                t.addCell(c);

            }
            t.endHeaders();
            ////////////////////////End Headers///////////////////////////////////////

            
            //////////////////////////Start Data Rows/////////////////////////////////////////
             for(int i=1;i<rows;i++)
             {
                for(int j=0;j<columns;j++)
                {
                    data_vector=matrix_data.getData()[j];
                   if(data_vector.get(i)==null)
                       data="";
                   else
                       data=data_vector.get(i).toString();
                    Cell c=new Cell(data);
                    t.addCell(c);
                }

             }
            //////////////////////////End Data Rows/////////////////////////////////////////

            document.add(t);
            document.close();

        } catch (Exception ex) {
            Logger.getLogger(ImportExportWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  public static void exportExcelFile(String file_path,String file_name ,String sheet_name, MatrixData matrixData, int startCol, int startRow)
  {
    try
    {
         file_path+= "\\" + file_name+".xls";
         System.out.println("filePath: "+file_path);
         Vector  data_vector;
         WritableWorkbook workbook = Workbook.createWorkbook(new File(file_path));
         String data="";
         WritableSheet sheet = workbook.createSheet(sheet_name, 0); 

         for(int i=0;i<matrixData.getColNumber();i++)
         {
            data_vector=matrixData.getData()[i];
            for(int j=0;j<data_vector.size();j++)
            {
               if(data_vector.get(j)==null)
                   data="";
               else
                   data=data_vector.get(j).toString();
               
               Label label = new Label(i+startCol,j+startRow ,data);
               sheet.addCell(label); 
            }
         }
         workbook.write();
         workbook.close();
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
  }
*/
  public static HashMap importExcelFile(String file_name, int startCol, int startRow, int width, int height)
  {
    dataHashMap=new HashMap();
    Vector vector_data;
    try
    {
       Workbook workbook = Workbook.getWorkbook(new File(file_name));  
       Sheet sheet=workbook.getSheet(0);
       int sheet_length=sheet.getRows();
       int sheet_width=sheet.getColumns();
    
       System.out.println("start row ="+ startRow);
       System.out.println("sheet width ="+ sheet_width);
    
       for(int i=startRow+1;i<sheet_length;i++)
       {
           System.out.println("start col = "+ startCol );
           System.out.println("i =" + i );
          jxl.Cell column_name = sheet.getCell(startCol,i);
          System.out.println("column_name ="+ column_name.getContents());
          vector_data=new Vector();
          for(int j=startCol;j<sheet_width;j++)
          {
            //jxl.Cell cell=sheet.getCell(i,j);
       //     System.out.println("j = " + j +"   i ="+i);
            jxl.Cell cell=sheet.getCell(j,i);
            vector_data.addElement(cell.getContents());
      //      System.out.println("cell content ="+ cell.getContents());
            
          }   
          System.out.println("vector data size =" + vector_data.size());
          
          dataHashMap.put(column_name.getContents(),vector_data);
          System.out.println("data hashmap size "+ dataHashMap.size());
       }

    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return dataHashMap;
  }

}