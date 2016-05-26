package com.mobinil.sds.core.system.cam.memo.model;

public class ChannelModel 
{

    int channelId;
    String channelName;
    public ChannelModel()
    {
      
    }

    public ChannelModel(int channelId, String channelName) {
        this.channelId = channelId;
        this.channelName = channelName;
    }

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
 
}