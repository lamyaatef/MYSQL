/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.commission.postarget.report;

import com.mobinil.sds.core.system.commission.postarget.dao.POSTargetDao;
import com.mobinil.sds.core.system.commission.postarget.model.POSTargetModel;
import com.mobinil.sds.core.system.sa.importdata.ErrorInfo;
import com.mobinil.sds.web.interfaces.commission.POSTargetInterface;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mabdelaal
 */
public class ExcelGeneratorReport {
    private String folderPath;
    String tmepFolderPath;
    private Vector<ErrorInfo> errorReportVec;
    private long file_id;
    private POSTargetDao posDAO;
    public final String systemSeparator = System.getProperty("file.separator");
    ExcelGenerator excelGen = null;

    public ExcelGeneratorReport(String folderPath, Vector<ErrorInfo> errorReportVec, long file_id, POSTargetDao posDAO) {
        this.folderPath = folderPath;
        this.errorReportVec = errorReportVec;
        this.file_id = file_id;
        this.posDAO = posDAO;
    }

    public ExcelGeneratorReport() {
    }
    
    
    
    private String generateFailureReport(){
        String fileUniquePath = getFilePathPerType(false);
        Vector<POSTargetModel> failedRowsVec = posDAO.getFailedCode();        
        fileUniquePath = generateExcelFile(failedRowsVec, false);
        return fileUniquePath;
    }
    private String generateSuccessReport() {        
    String fileUniquePath = getFilePathPerType(true);    
    Vector<POSTargetModel> successRowsVec = posDAO.getSuccessCode(file_id);
    fileUniquePath = generateExcelFile(successRowsVec, true);
    return fileUniquePath;
    }
    
    private String generateExcelFile(Vector<POSTargetModel> rowsVec, boolean fileType){
        String filePath = getFilePathPerType(fileType);
    excelGen = new ExcelGenerator(filePath, fileType);        
    excelGen.createSheetAndHeaders();
    for (POSTargetModel pOSTargetModel : rowsVec) {            
            excelGen.exportPOSist(pOSTargetModel);
        }
    if (!fileType && errorReportVec!=null && !errorReportVec.isEmpty())
    {
        for (ErrorInfo errorInfo : errorReportVec) {
            excelGen.exportErrorInfoist(errorInfo);
        }
    }
        try {
            excelGen.writeExcelFile();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return filePath;
    } 
    
    public String[] getGeneratedReports(){
    String filesPaths[] = new String[2];
    filesPaths[0] = checkFileExists(true) ? getFilePathPerType(true) : null;
    filesPaths[1] = checkFileExists(false) ? getFilePathPerType(false) : null;
    return filesPaths;
    }
    
    private boolean checkFileExists(boolean fileReportType){
     String filePath = getFilePathPerType(fileReportType);
     File file = new File(filePath);
     return file.exists();
    }
    
    private boolean checkFolderPathLastCharIsFileSeparator(){
    String filePath = getFolderPath();
    String lastChar = filePath.substring(filePath.length()-1, filePath.length());
    return lastChar.equals(systemSeparator);
    }
    
    private String getFilePathPerType(boolean fileReportType)
    {    
    String filePath = checkFolderPathLastCharIsFileSeparator()? getFolderPath() : getFolderPath() + systemSeparator;            
    StringBuilder fileFullPath = new StringBuilder(filePath);
    fileFullPath.append(file_id);
    fileFullPath.append("_");
    fileFullPath.append(fileReportType ? POSTargetInterface.SUCCESS_FILE_NAME : POSTargetInterface.FAILD_FILE_NAME);
    fileFullPath.append(".xlsx");    
    return fileFullPath.toString();
    }
    public String [] generateReports(){
    String  [] filePaths = new String  [2];
    filePaths[0] =  generateSuccessReport();
    filePaths[1] =  generateFailureReport();
      return filePaths;  
    } 
    
    

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public Vector<ErrorInfo> getErrorReportVec() {
        return errorReportVec;
    }

    public void setErrorReportVec(Vector<ErrorInfo> errorReportVec) {
        this.errorReportVec = errorReportVec;
    }

    public long getFile_id() {
        return file_id;
    }

    public void setFile_id(long file_id) {
        this.file_id = file_id;
    }

    public POSTargetDao getPosDAO() {
        return posDAO;
    }

    public void setPosDAO(POSTargetDao posDAO) {
        this.posDAO = posDAO;
    }
    
    
}
