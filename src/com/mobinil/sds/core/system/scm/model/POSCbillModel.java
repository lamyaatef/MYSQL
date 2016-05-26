/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.model;

import com.mobinil.sds.core.system.Model;
import com.mobinil.sds.core.system.scm.dao.GenericDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AHMED SAFWAT
 */
public class POSCbillModel extends Model {

    private String POSCode;
    private String STKNo;
    private String CaseId;

    public String getPOSCode(){
        return this.POSCode;
    }
    public void setPOSCode(String POSCode){
        this.POSCode=POSCode;
    }
    public String getSTKNo(){
        return this.STKNo;
    }
    public void setSTKNo(String STKNo){
        this.STKNo=STKNo;
    }
    public String getCaseId(){
        return this.CaseId;
    }
    public void setCaseId(String CaseId){
        this.CaseId=CaseId;
    }

        
    public void fillInstance(ResultSet res) {
        try {

           if( GenericDAO.checkColumnName("CASE_ID", res))
            this.setCaseId(res.getString("CASE_ID"));

           if( GenericDAO.checkColumnName("POS_CODE", res))
            this.setPOSCode(res.getString("POS_CODE"));

           if( GenericDAO.checkColumnName("STK_NO", res))
            this.setSTKNo(res.getString("STK_NO"));

        } catch (SQLException ex) {
            Logger.getLogger(POSCbillModel.class.getName()).log(Level.SEVERE, null, ex);
        }




    }
}