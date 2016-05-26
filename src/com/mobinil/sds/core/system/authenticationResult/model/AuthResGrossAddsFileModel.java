/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.authenticationResult.model;

import com.mobinil.sds.core.system.Model;
import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author mabdelaal
 */
public class AuthResGrossAddsFileModel extends  Model{

    private String file_id , record_count, month_number,year_number, user_id, type_id;
    private Date creation_date;
    @Override
    public void fillInstance(ResultSet res) {
        try {
            setFile_id(res.getString("FILE_ID"));
            setRecord_count(res.getString("RECORD_COUNT"));
            setMonth_number(res.getString("MONTH_NUMBER"));
            setYear_number(res.getString("YEAR_NUMBER"));
            setUser_id(res.getString("USER_ID"));
            setType_id(res.getString("TYPE_ID"));
            setCreation_date(res.getDate("CREATION_DATE"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the file_id
     */
    public String getFile_id() {
        return file_id;
    }

    /**
     * @param file_id the file_id to set
     */
    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    /**
     * @return the record_count
     */
    public String getRecord_count() {
        return record_count;
    }

    /**
     * @param record_count the record_count to set
     */
    public void setRecord_count(String record_count) {
        this.record_count = record_count;
    }

    /**
     * @return the month_number
     */
    public String getMonth_number() {
        return month_number;
    }

    /**
     * @param month_number the month_number to set
     */
    public void setMonth_number(String month_number) {
        this.month_number = month_number;
    }

    /**
     * @return the year_number
     */
    public String getYear_number() {
        return year_number;
    }

    /**
     * @param year_number the year_number to set
     */
    public void setYear_number(String year_number) {
        this.year_number = year_number;
    }

    /**
     * @return the user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the type_id
     */
    public String getType_id() {
        return type_id;
    }

    /**
     * @param type_id the type_id to set
     */
    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    /**
     * @return the creation_date
     */
    public Date getCreation_date() {
        return creation_date;
    }

    /**
     * @param creation_date the creation_date to set
     */
    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }
    
    
}
