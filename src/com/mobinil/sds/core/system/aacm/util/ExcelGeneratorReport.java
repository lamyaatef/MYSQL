/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.aacm.util;
import com.mobinil.sds.core.system.aacm.dao.RevenueFileDao;
import com.mobinil.sds.core.system.aacm.model.AccmRevUpload;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
/**
 *
 * @author mabdelaal
 */
public class ExcelGeneratorReport {
    private String folderPath;
    String tmepFolderPath;
    
    private long file_id;
    private RevenueFileDao revDAO;
    public final String systemSeparator = System.getProperty("file.separator");
    ExcelGenerator excelGen = null;

    public ExcelGeneratorReport(String folderPath, long file_id, RevenueFileDao revDAO) {
        this.folderPath = folderPath;        
        this.file_id = file_id;
        this.revDAO =revDAO;
    }

    public ExcelGeneratorReport() {
    }
    
    private String generateSuccessReport() {        
    String fileUniquePath = getFilePathPerType();    
    Vector<AccmRevUpload> successRowsVec =revDAO.getFileRecords(file_id);
    fileUniquePath = generateExcelFile(successRowsVec);
    return fileUniquePath;
    }
    
    private String generateExcelFile(Vector<AccmRevUpload> rowsVec){
        String filePath = getFilePathPerType();
    excelGen = new ExcelGenerator(filePath);        
    excelGen.createSheetAndHeaders();
    for (AccmRevUpload accmRevUpload : rowsVec) {            
            excelGen.exportRevist(accmRevUpload);
        }    
        try {
            excelGen.writeExcelFile();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return filePath;
    } 
    
    public String getGeneratedReport(){    
    return generateSuccessReport();
    }
    
   
    
    private boolean checkFolderPathLastCharIsFileSeparator(){
    String filePath = getFolderPath();
    String lastChar = filePath.substring(filePath.length()-1, filePath.length());
    return lastChar.equals(systemSeparator);
    }
    
    private String getFilePathPerType()
    {    
    String filePath = checkFolderPathLastCharIsFileSeparator()? getFolderPath() : getFolderPath() + systemSeparator;            
    StringBuilder fileFullPath = new StringBuilder(filePath);
    fileFullPath.append(file_id);
    fileFullPath.append("_");
    fileFullPath.append(System.currentTimeMillis());
    fileFullPath.append(".xlsx");    
    return fileFullPath.toString();
    }
    
    
    

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

   
    public long getFile_id() {
        return file_id;
    }

    public void setFile_id(long file_id) {
        this.file_id = file_id;
    }
    
}

    
    
