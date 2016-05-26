/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.dcm.region.dto;

import com.mobinil.sds.core.system.Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Ahmed Adel
 */
public class Area_Level_Type_OriginalDto extends Model{
    private int id;
    private String name;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setId(res.getInt("TYPE_ID"));
            this.setName(res.getString("NAME"));
        } catch (SQLException ex) {
            Logger.getLogger(RegionLevelDto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
