/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dl.excel;

import com.mobinil.mcss.dl.model.DCMListDetail;
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
        Cell titelCellCode = row.createCell(0);
        Cell titelCellID = row.createCell(1);
        titelCellCode.setCellValue("DCM Code");
        titelCellID.setCellValue("DCM ID");
    }

    

    public void exportDCMList(DCMListDetail dcmListDetail)  {        
        row = sheet.createRow(sheet.getLastRowNum() + 1);
        cell = row.createCell(row.getLastCellNum() + 1);
        createCell(workbook, cell, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, dcmListDetail.getDcmCode());
        cell = row.createCell(row.getLastCellNum());
        createCell(workbook, cell, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, String.valueOf(dcmListDetail.getDcmID()==null ? "" : dcmListDetail.getDcmID()));
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
}
