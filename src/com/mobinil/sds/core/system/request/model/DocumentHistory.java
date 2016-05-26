/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.request.model;

import com.mobinil.sds.core.system.Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed Adel
 */
public class DocumentHistory extends Model{
private int docNum;
private String docName;
private Date updateIn;

    /**
     * @return the docNum
     */
    public int getDocNum() {
        return docNum;
    }

    /**
     * @param docNum the docNum to set
     */
    public void setDocNum(int docNum) {
        this.docNum = docNum;
    }

    /**
     * @return the docName
     */
    public String getDocName() {
        return docName;
    }

    /**
     * @param docName the docName to set
     */
    public void setDocName(String docName) {
        this.docName = docName;
    }
    /**
     * @return the updateIn
     */
    public Date getUpdateIn() {
        return updateIn;
    }

    /**
     * @param updateIn the updateIn to set
     */
    public void setUpdateIn(Date updateIn) {
        this.updateIn = updateIn;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setDocName(res.getString("PROPOSED_DOCUMENT_NAME"));
            this.setDocNum(res.getInt("POS_DOC_NUM"));
            this.setUpdateIn(res.getDate("UPDATE_IN"));
        } catch (SQLException ex) {
            Logger.getLogger(DocumentHistory.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



}
