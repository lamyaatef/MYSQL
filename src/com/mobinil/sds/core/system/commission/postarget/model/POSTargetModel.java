/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.commission.postarget.model;

import com.mobinil.sds.core.system.Model;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mabdelaal
 */
public class POSTargetModel extends Model {

    private String pos_code;
    private int target;
    private long rowNum;
    private String errorMSG;

    /**
     * Get the value of rowNum
     *
     * @return the value of rowNum
     */
    public long getRowNum() {
        return rowNum;
    }

    /**
     * Set the value of rowNum
     *
     * @param rowNum new value of rowNum
     */
    public void setRowNum(long rowNum) {
        this.rowNum = rowNum;
    }

    

    /**
     * Get the value of target
     *
     * @return the value of target
     */
    public int getTarget() {
        return target;
    }

    public POSTargetModel(String pos_code, int target) {
        this.pos_code = pos_code;
        this.target = target;
    }

    public POSTargetModel() {
    }

    @Override
    public String toString() {
        return "POSTargetModel{" + "pos_code=" + pos_code + ", target=" + target + '}';
    }

    /**
     * Set the value of target
     *
     * @param target new value of target
     */
    public void setTarget(int target) {
        this.target = target;
    }

    /**
     * Get the value of pos_code
     *
     * @return the value of pos_code
     */
    public String getPos_code() {
        return pos_code;
    }

    /**
     * Set the value of pos_code
     *
     * @param pos_code new value of pos_code
     */
    public void setPos_code(String pos_code) {
        this.pos_code = pos_code;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
           setPos_code(res.getString("POS_CODE"));
           setTarget(res.getInt("TARGET"));
             try {
                setRowNum(res.getLong("row_num"));
            } catch (Exception e) {
            }
            } catch (SQLException e) {
            
        }
    }

    public String getErrorMSG() {
        return errorMSG;
    }

    public void setErrorMSG(String errorMSG) {
        this.errorMSG = errorMSG;
    }

}
