package com.mobinil.sds.core.system.sip.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SIPConfigModel {
	  private Integer configId;
	  private String configName;
	  public static final String SIP_CONFIG_ID = "CONFIG_ID";
	  public static final String SIP_CONFIG_NAME = "CONFIG_NAME";

	  public SIPConfigModel()
	  {
	  }
	  
	  public SIPConfigModel(ResultSet res) throws SQLException
	  {
		  configId = new Integer (res.getInt(SIP_CONFIG_ID));
		  configName = res.getString(SIP_CONFIG_NAME);
	  }


	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}



}
