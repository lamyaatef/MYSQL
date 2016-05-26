package com.mobinil.sds.core.system.sip.dto;

public class sipExportFileDTO
{
   private String excel_fields_count,excel_sheets_count,file_sheet_name,report_type,file_type_name,field1,field2,field3,sheet_number,file_sheet_header;

   public String getExcel_sheets_count()
   {
      return excel_sheets_count;
   }

   public void setExcel_sheets_count(String excelSheetsCount)
   {
      excel_sheets_count = excelSheetsCount;
   }

   public String getFile_sheet_name()
   {
      return file_sheet_name;
   }

   public void setFile_sheet_name(String fileSheetName)
   {
      file_sheet_name = fileSheetName;
   }

   public String getReport_type()
   {
      return report_type;
   }

   public void setReport_type(String reportType)
   {
      report_type = reportType;
   }

   public String getFile_type_name()
   {
      return file_type_name;
   }

   public void setFile_type_name(String fileTypeName)
   {
      file_type_name = fileTypeName;
   }

   public String getField1()
   {
      return field1;
   }

   public void setField1(String field1)
   {
      this.field1 = field1;
   }

   public String getField2()
   {
      return field2;
   }

   public void setField2(String field2)
   {
      this.field2 = field2;
   }

   public String getField3()
   {
      return field3;
   }

   public void setField3(String field3)
   {
      this.field3 = field3;
   }

   public String getSheet_number()
   {
      return sheet_number;
   }

   public void setSheet_number(String sheetNumber)
   {
      sheet_number = sheetNumber;
   }

   public void setExcel_fields_count(String excel_fields_count)
   {
      this.excel_fields_count = excel_fields_count;
   }

   public String getExcel_fields_count()
   {
      return excel_fields_count;
   }

   public void setFile_sheet_header(String file_sheet_header)
   {
      this.file_sheet_header = file_sheet_header;
   }

   public String getFile_sheet_header()
   {
      return file_sheet_header;
   }
}
