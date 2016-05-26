package com.mobinil.sds.core.system.sop.requests.model;
import java.io.Serializable;
import java.sql.ResultSet;

public class WarehouseModel implements Serializable{
	
	String warehouseId;
	String warehouseName;
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	
	public static final String WAREHOUSE_ID = "WAREHOUSE_ID";
	public static final String WAREHOUSE_NAME = "WAREHOUSE_NAME";
	
	public WarehouseModel(){
		
	}
	
	public WarehouseModel(ResultSet res){
		try
		{
			warehouseId = res.getString(WAREHOUSE_ID);
			warehouseName = res.getString(WAREHOUSE_NAME);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
