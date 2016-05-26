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
 * @author samy
 */
public class WarehouseToLevelZero {
    private int id;
    private int warehouse;
    private int levelZero;
    private int flag;

    public WarehouseToLevelZero() {
    }

    public WarehouseToLevelZero(ResultSet rs){
        try {
            this.id = rs.getInt("id");
            this.warehouse = rs.getInt("warehouse_id");
            this.levelZero = rs.getInt("level_zero_id");
            this.flag=rs.getInt("flag");
        } catch (SQLException ex) {
            Logger.getLogger(WarehouseToLevelZero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(int warehouse) {
        this.warehouse = warehouse;
    }

    public int getLevelZero() {
        return levelZero;
    }

    public void setLevelZero(int levelZero) {
        this.levelZero = levelZero;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
    
    
    
}
