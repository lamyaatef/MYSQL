/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.model;

import com.mobinil.sds.core.system.Model;
import com.mobinil.sds.core.system.scm.dao.GenericDAO;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed Adel
 */
public class POSModel extends Model{

    private int POS_ID;
    private String POS_Code;
    private String POS_NAME;
    private String STK_Dial;
    private int Iqrar_Status;
    private int Cbill_Status;
    private String POS_Status;
    private String payment_Status;
    private int STK_Status;

 /**
     * @return the POS_ID
     */
    public int getPOS_ID() {
        return POS_ID;
    }

    /**
     * @param POS_ID the POS_ID to set
     */
    public void setPOS_ID(int POS_ID) {
        this.POS_ID = POS_ID;
    }

    /**
     * @return the POS_Code
     */
    public String getPOS_Code() {
        return POS_Code;
    }

    /**
     * @param POS_Code the POS_Code to set
     */
    public void setPOS_Code(String POS_Code) {
        this.POS_Code = POS_Code;
    }

    /**
     * @return the POS_NAME
     */
    public String getPOS_NAME() {
        return POS_NAME;
    }

    /**
     * @param POS_NAME the POS_NAME to set
     */
    public void setPOS_NAME(String POS_NAME) {
        this.POS_NAME = POS_NAME;
    }

    /**
     * @return the STK_Dial
     */
    public String getSTK_Dial() {
        return STK_Dial;
    }

    /**
     * @param STK_Dial the STK_Dial to set
     */
    public void setSTK_Dial(String STK_Dial) {
        this.STK_Dial = STK_Dial;
    }

    /**
     * @return the Iqrar_Status
     */
    public int getIqrar_Status() {
        return Iqrar_Status;
    }

    /**
     * @param Iqrar_Status the Iqrar_Status to set
     */
    public void setIqrar_Status(int Iqrar_Status) {
        this.Iqrar_Status = Iqrar_Status;
    }

    /**
     * @return the Cbill_Status
     */
    public int getCbill_Status() {
        return Cbill_Status;
    }

    /**
     * @param Cbill_Status the Cbill_Status to set
     */
    public void setCbill_Status(int Cbill_Status) {
        this.Cbill_Status = Cbill_Status;
    }
      /**
     * @param POS_Status the POS_Status to set
     */
    public void setPOS_Status(String POS_Status) {
        this.POS_Status = POS_Status;
    }



    /**
     * @param payment_Status the payment_Status to set
     */
    public void setPayment_Status(String payment_Status) {
        this.payment_Status = payment_Status;
    }


    /**
     * @return the POS_Status
     */
    public String getPOS_Status() {
        return POS_Status;
    }



    /**
     * @return the payment_Status
     */
    public String getPayment_Status() {
        return payment_Status;
    }

    /**
     * @return the STK_STATUS
     */
    public int getSTK_Status() {
        return STK_Status;
    }

    /**
     * @param STK_STATUS the STK_STATUS to set
     */
    public void setSTK_Status(int STK_Status) {
        this.STK_Status = STK_Status;
    }





    @Override
    public void fillInstance(ResultSet res) {
        try {
           if( GenericDAO.checkColumnName("DCM_CODE", res))
            this.setPOS_Code(res.getString("DCM_CODE"));
           if( GenericDAO.checkColumnName("DCM_NAME", res))
            this.setPOS_NAME(res.getString("DCM_NAME"));
           if( GenericDAO.checkColumnName("STK_NUMBER", res))
            this.setSTK_Dial(res.getString("STK_NUMBER"));
           if( GenericDAO.checkColumnName("CBILL_STATUS", res))
            this.setCbill_Status(res.getInt("CBILL_STATUS"));
           if( GenericDAO.checkColumnName("IQRAR_STATUS", res))
            this.setIqrar_Status(res.getInt("IQRAR_STATUS"));
           if( GenericDAO.checkColumnName("DCM_ID", res))
            this.setPOS_ID(res.getInt("DCM_ID"));
           if( GenericDAO.checkColumnName("PAYMENT_STATUS", res))
            this.setPayment_Status(res.getString("PAYMENT_STATUS"));
           if( GenericDAO.checkColumnName("DCM_STATUS", res))
            this.setPOS_Status(res.getString("DCM_STATUS"));
           if( GenericDAO.checkColumnName("STK_STATUS_ID", res))
            this.setSTK_Status(res.getInt("STK_STATUS_ID"));

        } catch (SQLException ex) {
            Logger.getLogger(POSModel.class.getName()).log(Level.SEVERE, null, ex);
        }




    }

  

  



   
}
