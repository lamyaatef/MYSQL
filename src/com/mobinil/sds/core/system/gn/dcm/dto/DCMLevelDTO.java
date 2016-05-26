package com.mobinil.sds.core.system.gn.dcm.dto;
import java.io.*;

public class DCMLevelDTO implements Serializable
{
  private int LevelID;
  private String LevelName;
  private String LevelDescription;

  public DCMLevelDTO()
  {
  }

  public int getLevelID()
  {
    return LevelID;
  }

  public void setLevelID(int newLevelID)
  {
    LevelID = newLevelID;
  }

  public String getLevelName()
  {
    return LevelName;
  }

  public void setLevelName(String newLevelName)
  {
    LevelName = newLevelName;
  }

  public String getLevelDescription()
  {
    return LevelDescription;
  }

  public void setLevelDescription(String newLevelDescription)
  {
    LevelDescription = newLevelDescription;
  }
}