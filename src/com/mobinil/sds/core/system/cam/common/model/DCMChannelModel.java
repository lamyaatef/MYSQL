package com.mobinil.sds.core.system.cam.common.model;

public class DCMChannelModel 
{
private int channel_id;
private String channel_name;
  public DCMChannelModel()
  {
  }
  
	public DCMChannelModel(int channel_id, String channel_name) {
		super();
		this.channel_id = channel_id;
		this.channel_name = channel_name;
	}
	public int getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
	}
	public String getChannel_name() {
		return channel_name;
	}
	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}
}