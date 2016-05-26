/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.aacm.util;


import com.mobinil.sds.core.system.aacm.model.AccmRevFile;
import com.mobinil.sds.core.system.aacm.model.AccmRevUpload;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
/**
 *
 * @author mabdelaal
 */
public class ExcelGenerator {

    String path = null;
    Workbook workbook = null;
    FileOutputStream fileOut = null;
    Cell cell = null;
    Row row = null;
    Sheet sheet = null;  
    private boolean isSuccess;
    private boolean isHeaderWritten = false;
    
//    private String isSuccess;
    

    public ExcelGenerator() {
    }

    public ExcelGenerator(String fullFileAndPath) {        
        this.path = fullFileAndPath;        
        try {
            fileOut = new FileOutputStream(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        

        workbook = new XSSFWorkbook();
    }

    public void createSheetAndHeaders() {
        sheet = workbook.createSheet("Sheet 1");
        Row row = sheet.createRow(0);
        Cell custCodeCode = row.createCell(0);
        Cell accountMgrCell = row.createCell(1);
        Cell sumOfInvCell = row.createCell(2);
        Cell sumOfAciveLineCell = row.createCell(3);
        custCodeCode.setCellValue("Customer Code");
        accountMgrCell.setCellValue("Account Manager");
        sumOfInvCell.setCellValue("Sum of invoice amount");
        sumOfAciveLineCell.setCellValue("Sum of active lines");
    }

    

    public void exportRevist(AccmRevUpload accmRevUpload)  {        
        row = sheet.createRow(sheet.getLastRowNum() + 1);
        cell = row.createCell(row.getLastCellNum() + 1);
        createCell(workbook, cell, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, accmRevUpload.getCustomerCode()  );
        cell = row.createCell(row.getLastCellNum());
        createCell(workbook, cell, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, accmRevUpload.getAccountMgr());
        cell = row.createCell(row.getLastCellNum());
        createCell(workbook, cell, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, String.valueOf(accmRevUpload.getSumOfInvoice()));
        cell = row.createCell(row.getLastCellNum());
        createCell(workbook, cell, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, String.valueOf(accmRevUpload.getSumOfActive()));
    }

    public void writeExcelFile() throws IOException {
        workbook.write(fileOut);
        fileOut.close();       

    }

    private static void createCell(Workbook wb, Cell cell, short halign, short valign, String value) {
        cell.setCellValue(value);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cell.setCellStyle(cellStyle);
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isIsHeaderWritten() {
        return isHeaderWritten;
    }

    public void setIsHeaderWritten(boolean isHeaderWritten) {
        this.isHeaderWritten = isHeaderWritten;
    }
}
