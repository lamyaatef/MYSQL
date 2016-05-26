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
public class GenWarehouse {
    private int  warehouseId;
    private String warehouseName;

    public GenWarehouse(ResultSet rs) {
        try {
            this.warehouseId=rs.getInt("WAREHOUSE_ID");
            this.warehouseName=rs.getString("WAREHOUSE_NAME");
        } catch (SQLException ex) {
            Logger.getLogger(GenWarehouse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public GenWarehouse() {
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    
    
    
    
}
