/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.level_relation_management.model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author SAND
 */
public class GenLevelZero {
    private int  levelZeroId;
    private String levelZeroName;
    
    public GenLevelZero(ResultSet rs) {
        try {
            this.levelZeroId=rs.getInt("DCM_ID");
            this.levelZeroName=rs.getString("DCM_NAME");
        } catch (SQLException ex) {
            Logger.getLogger(GenWarehouse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getLevelZeroId() {
        return levelZeroId;
    }

    public void setLevelZeroId(int levelZeroId) {
        this.levelZeroId = levelZeroId;
    }

    public String getLevelZeroName() {
        return levelZeroName;
    }

    public void setLevelZeroName(String levelZeroName) {
        this.levelZeroName = levelZeroName;
    }
    
    
}
