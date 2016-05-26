/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.commission.postarget.report;

import com.mobinil.mcss.dl.excel.*;
import com.mobinil.mcss.dl.model.DCMListDetail;
import com.mobinil.sds.core.system.commission.postarget.model.POSTargetModel;
import com.mobinil.sds.core.system.sa.importdata.ErrorInfo;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public ExcelGenerator(String fullFileAndPath,boolean isSuccess) {
        this.isSuccess = isSuccess;
        this.path = fullFileAndPath;
        System.out.println("fullFileAndPath is "+fullFileAndPath);
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
        Cell titelCellCode = row.createCell(0);
        Cell titelCellID = row.createCell(1);
        Cell statusCellID = row.createCell(2);
        titelCellCode.setCellValue("DCM Code");
        titelCellID.setCellValue("Target");
        statusCellID.setCellValue("Result");
    }

    

    public void exportPOSist(POSTargetModel posTargetModel)  {        
        row = sheet.createRow(sheet.getLastRowNum() + 1);
        cell = row.createCell(row.getLastCellNum() + 1);
        createCell(workbook, cell, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, posTargetModel.getPos_code());
        cell = row.createCell(row.getLastCellNum());
        createCell(workbook, cell, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, String.valueOf(posTargetModel.getTarget()));
        cell = row.createCell(row.getLastCellNum());
        createCell(workbook, cell, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, (isSuccess? "Found" : posTargetModel.getErrorMSG()!=null ? posTargetModel.getErrorMSG() : "Not Found"));
    }
    
    private void createHeaderOfInvalidInsertedRows(){
    
    row = sheet.createRow(sheet.getLastRowNum() + 1);
    cell = row.createCell(row.getLastCellNum() + 1);
    createCell(workbook, cell, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, "-----------Invalid Insertion-----------");
    row = sheet.createRow(sheet.getLastRowNum() + 1);
    cell = row.createCell(row.getLastCellNum() + 1);
    createCell(workbook, cell, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, "Row Number");
    cell = row.createCell(row.getLastCellNum() );
    createCell(workbook, cell, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, "Cell Name");
    cell = row.createCell(row.getLastCellNum() );
    createCell(workbook, cell, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, "Error Message");
    isHeaderWritten = true;
    }
    public void exportErrorInfoist(ErrorInfo errorInfo)  {
        if (!isHeaderWritten)createHeaderOfInvalidInsertedRows();
        row = sheet.createRow(sheet.getLastRowNum() + 1);
        cell = row.createCell(row.getLastCellNum() + 1);
        createCell(workbook, cell, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, String.valueOf(errorInfo.getRowNum()+1));
        cell = row.createCell(row.getLastCellNum());
        createCell(workbook, cell, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, errorInfo.getCellName());
        cell = row.createCell(row.getLastCellNum());
        createCell(workbook, cell, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, errorInfo.getErrorMsg());
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
