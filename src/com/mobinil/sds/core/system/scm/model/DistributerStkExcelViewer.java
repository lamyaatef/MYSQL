/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.model;

import com.mobinil.sds.core.system.Model;
import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gado
 */
public class DistributerStkExcelViewer extends Model{
private String fileId;
    private String dcm_Id;
    private Date date_Activation;
    private Blob file;

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setFileId(res.getString("FILE_ID"));
             this.setDcm_Id(res.getString("DCM_ID"));
             this.setDate_Activation(res.getDate("DATE_ACTIVATION"));
              this.setFile(res.getBlob("FILE_DIR") );
        } catch (SQLException ex) {
            Logger.getLogger(DistributerStkExcelViewer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @return the fileId
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * @param fileId the fileId to set
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * @return the dcm_Code
     */
    public String getDcm_Id() {
        return dcm_Id;
    }

    /**
     * @param dcm_Code the dcm_Code to set
     */
    public void setDcm_Id(String dcm_Id) {
        this.dcm_Id = dcm_Id;
    }

    /**
     * @return the date_Activation
     */
    public Date getDate_Activation() {
        return date_Activation;
    }

    /**
     * @param date_Activation the date_Activation to set
     */
    public void setDate_Activation(Date date_Activation) {
        this.date_Activation = date_Activation;
    }

    /**
     * @return the file
     */
    public Blob getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(Blob file) {
        this.file = file;
    }

}
