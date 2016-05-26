/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.request.model;

/**
 *
 * @author sand
 */
public class CalidusZipFileModel {
    private String fileName;
    private String fileStatus;
    private String fileCreationDate;

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the fileStatus
     */
    public String getFileStatus() {
        return fileStatus;
    }

    /**
     * @param fileStatus the fileStatus to set
     */
    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    /**
     * @return the fileCreationDate
     */
    public String getFileCreationDate() {
        return fileCreationDate;
    }

    /**
     * @param fileCreationDate the fileCreationDate to set
     */
    public void setFileCreationDate(String fileCreationDate) {
        this.fileCreationDate = fileCreationDate;
    }
    
}
