/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.model;

import com.mobinil.sds.core.system.Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed Adel
 */
public class DistributerStaticDataModel extends Model{

private  int dataId;
private  String dataName;
private String dataValue;

    /**
     * @return the dataId
     */
    public  int getDataId() {
        return dataId;
    }

    /**
     * @param aDataId the dataId to set
     */
    public  void setDataId(int aDataId) {
        dataId = aDataId;
    }

    /**
     * @return the dataName
     */
    public String getDataName() {
        return dataName;
    }

    /**
     * @param aDataName the dataName to set
     */
    public void setDataName(String aDataName) {
        dataName = aDataName;
    }

    /**
     * @return the dataValue
     */
    public  String getDataValue() {
        return dataValue;
    }

    /**
     * @param aDataValue the dataValue to set
     */
    public void setDataValue(String aDataValue) {
        dataValue = aDataValue;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setDataId(res.getInt("DATA_ID"));
            this.setDataName(res.getString("DATA_NAME"));
            if(res.getString("DATA_VALUE")!=null)
            {
            this.setDataValue(res.getString("DATA_VALUE"));
            }else
            {
            this.setDataValue("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DistributerStaticDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
