package com.mobinil.sds.core.system.sop.requests.model;
import java.io.Serializable;
import java.sql.ResultSet;

public class WarehouseChannelModel implements Serializable{
	
	String channelId;
	String channelName;
	String warehouseId;
	String warehouseName;
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
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
	
	public static final String CHANNEL_ID = "CHANNEL_ID";
	public static final String WAREHOUSE_ID = "WAREHOUSE_ID";
	public static final String CHANNEL_NAME = "CHANNEL_NAME";
	public static final String WAREHOUSE_NAME = "WAREHOUSE_NAME";
	
	public WarehouseChannelModel(){
		
	}
	
	public WarehouseChannelModel(ResultSet res){
		try
		{
			channelId = res.getString(CHANNEL_ID);
			channelName = res.getString(CHANNEL_NAME);
			warehouseId = res.getString(WAREHOUSE_ID);
			warehouseName = res.getString(WAREHOUSE_NAME);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
