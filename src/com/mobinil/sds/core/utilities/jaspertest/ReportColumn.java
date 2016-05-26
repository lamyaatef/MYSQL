/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.utilities.jaspertest;

/**
 *
 * @author amr
 */
public class ReportColumn {
private String header_name, DB_field_name, DB_field_dataType;

    public ReportColumn(String header_name, String DB_field_name, String DB_field_dataType) {
        this.header_name = header_name;
        this.DB_field_name = DB_field_name;
        this.DB_field_dataType = DB_field_dataType;
    }

    public String getDB_field_dataType() {
        return DB_field_dataType;
    }

    public void setDB_field_dataType(String DB_field_dataType) {
        this.DB_field_dataType = DB_field_dataType;
    }

    public String getDB_field_name() {
        return DB_field_name;
    }

    public void setDB_field_name(String DB_field_name) {
        this.DB_field_name = DB_field_name;
    }

    public String getHeader_name() {
        return header_name;
    }

    public void setHeader_name(String header_name) {
        this.header_name = header_name;
    }


}
