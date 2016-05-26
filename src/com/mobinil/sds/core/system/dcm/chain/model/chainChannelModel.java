package com.mobinil.sds.core.system.dcm.chain.model;
import java.sql.*;
import java.io.*;

public class chainChannelModel implements Serializable
{ 
  String channelId;
  String channelName;

  public static final String CHANNEL_ID = "CHANNEL_ID";
  public static final String CHANNEL_NAME = "CHANNEL_NAME";
  
  public chainChannelModel()
  {
  }

  public chainChannelModel(ResultSet res)
  {
    try
    {
      channelId = res.getString(CHANNEL_ID);
      channelName = res.getString(CHANNEL_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  /////////////////////////////////////////////////
  public String getChannelId()
  {
    return channelId;
  }
  public void setChannelId(String newChannelId)
  {
    channelId = newChannelId;
  }
  /////////////////////////////////////////////////
  public String getChannelName()
  {
    return channelName;
  }
  public void setChannelName(String newChannelName)
  {
    channelName = newChannelName;
  }
}