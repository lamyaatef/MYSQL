/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.request.model;

import com.mobinil.sds.core.system.Model;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed Adel
 */
public class ManagerHistory extends Model{
private String POS_Manager_Id;
    private String POS_Manager_Name;
    private Date POS_Manager_Birthdate;
    private String POS_Manager_Id_Type_Id;
    private String POS_Manager_Id_Number;
    private Timestamp Update_In;

    /**
     * @return the POS_Manager_Id
     */
    public String getPOS_Manager_Id() {
        return POS_Manager_Id;
    }

    /**
     * @param POS_Manager_Id the POS_Manager_Id to set
     */
    public void setPOS_Manager_Id(String POS_Manager_Id) {
        this.POS_Manager_Id = POS_Manager_Id;
    }

    /**
     * @return the POS_Manager_Name
     */
    public String getPOS_Manager_Name() {
        return POS_Manager_Name;
    }

    /**
     * @param POS_Manager_Name the POS_Manager_Name to set
     */
    public void setPOS_Manager_Name(String POS_Manager_Name) {
        this.POS_Manager_Name = POS_Manager_Name;
    }

    /**
     * @return the POS_Manager_Birthdate
     */
    public Date getPOS_Manager_Birthdate() {
        return POS_Manager_Birthdate;
    }

    /**
     * @param POS_Manager_Birthdate the POS_Manager_Birthdate to set
     */
    public void setPOS_Manager_Birthdate(Date POS_Manager_Birthdate) {
        this.POS_Manager_Birthdate = POS_Manager_Birthdate;
    }

    /**
     * @return the POS_Manager_Id_Type_Id
     */
    public String getPOS_Manager_Id_Type_Id() {
        return POS_Manager_Id_Type_Id;
    }

    /**
     * @param POS_Manager_Id_Type_Id the POS_Manager_Id_Type_Id to set
     */
    public void setPOS_Manager_Id_Type_Id(String POS_Manager_Id_Type_Id) {
        this.POS_Manager_Id_Type_Id = POS_Manager_Id_Type_Id;
    }

    /**
     * @return the POS_Manager_Id_Number
     */
    public String getPOS_Manager_Id_Number() {
        return POS_Manager_Id_Number;
    }

    /**
     * @param POS_Manager_Id_Number the POS_Manager_Id_Number to set
     */
    public void setPOS_Manager_Id_Number(String POS_Manager_Id_Number) {
        this.POS_Manager_Id_Number = POS_Manager_Id_Number;
    }

    /**
     * @return the Update_In
     */
    public Timestamp getUpdate_In() {
        return Update_In;
    }

    /**
     * @param Update_In the Update_In to set
     */
    public void setUpdate_In(Timestamp Update_In) {
        this.Update_In = Update_In;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setPOS_Manager_Name(res.getString("POS_MANAGER_NAME"));
            this.setPOS_Manager_Id_Number(res.getString("POS_MANAGER_ID_NUMBER"));
            this.setPOS_Manager_Birthdate(res.getDate("POS_MANAGER_BIRTHDATE"));
            this.setPOS_Manager_Id_Type_Id(res.getString("POS_MANAGER_ID_TYPE_ID"));
            this.setUpdate_In(res.getTimestamp("UPDATE_IN"));
        } catch (SQLException ex) {
            Logger.getLogger(ManagerHistory.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
