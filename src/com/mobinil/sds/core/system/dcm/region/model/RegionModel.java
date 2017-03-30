package com.mobinil.sds.core.system.dcm.region.model;
import com.mobinil.sds.core.system.Model;
import com.mobinil.sds.core.system.scm.dao.GenericDAO;
import java.sql.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegionModel extends Model implements Serializable
{
  String regionId;
  String regionName;
  String regionStatusTypeId;
  String regionStatusTypeName;
  String parentRegionId;
  String regionLevelTypeId;
  String regionLevelTypeName;
  private int id;
  private String name;
  private String parentId;
  
  public static final String REGION_ID = "REGION_ID";
  public static final String REGION_NAME = "REGION_NAME";
  public static final String REGION_STATUS_TYPE_ID = "REGION_STATUS_TYPE_ID";
  public static final String REGION_STATUS_TYPE_NAME = "REGION_STATUS_TYPE_NAME";
  public static final String PARENT_REGION_ID = "PARENT_REGION_ID";
  public static final String REGION_LEVEL_TYPE_ID = "REGION_LEVEL_TYPE_ID";
  public static final String REGION_LEVEL_TYPE_NAME = "REGION_LEVEL_TYPE_NAME";
  
  public RegionModel()
  {
  }

  public int getId()
  {
    return id;
  }
  public void setId(int id)
  {
    this.id = id;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public String getParentId()
  {
    return parentId;
  }
  public void setParentId(String parentId)
  {
    this.parentId = parentId;
  }

  public RegionModel(ResultSet res)
  {
    try
    {
      regionId = res.getString(REGION_ID);
      regionName = res.getString(REGION_NAME);
      regionStatusTypeId = res.getString(REGION_STATUS_TYPE_ID);
      regionStatusTypeName = res.getString(REGION_STATUS_TYPE_NAME);
      parentRegionId = res.getString(PARENT_REGION_ID);
      regionLevelTypeId = res.getString(REGION_LEVEL_TYPE_ID);
      regionLevelTypeName = res.getString(REGION_LEVEL_TYPE_NAME);  
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } 
  }

  public String getRegionId()
  {
  return regionId;
  }
  public void setRegionId(String newRegionId)
  {
  regionId= newRegionId;
  }	
  
  public String getRegionName()
  {
  return regionName;
  }
  public void setRegionName(String newRegionName)
  {
  regionName= newRegionName;
  }	
  
  public String getRegionStatusTypeId()
  {
  return regionStatusTypeId;
  }
  public void setRegionStatusTypeId(String newRegionStatusTypeId)
  {
  regionStatusTypeId= newRegionStatusTypeId;
  }	
  
  public String getRegionStatusTypeName()
  {
  return regionStatusTypeName;
  }
  public void setRegionStatusTypeName(String newRegionStatusTypeName)
  {
  regionStatusTypeName= newRegionStatusTypeName;
  }	
  
  public String getParentRegionId()
  {
  return parentRegionId;
  }
  public void setParentRegionId(String newParentRegionId)
  {
  parentRegionId= newParentRegionId;
  }	
  
  public String getRegionLevelTypeId()
  {
  return regionLevelTypeId;
  }
  public void setRegionLevelTypeId(String newRegionLevelTypeId)
  {
  regionLevelTypeId= newRegionLevelTypeId;
  }	
  
  public String getRegionLevelTypeName()
  {
  return regionLevelTypeName;
  }
  public void setRegionLevelTypeName(String newRegionLevelTypeName)
  {
  regionLevelTypeName= newRegionLevelTypeName;
  }
  public void fillForRepManagementSearch(ResultSet res){
        try {
            this.setRegionId(res.getString("REGION_ID"));
            this.setRegionName(res.getString("REGION_NAME"));
            this.setRegionLevelTypeId(res.getString("region_level_type_id"));
            this.setRegionLevelTypeName(res.getString("region_level_type_name"));
        } catch (SQLException ex) {
            Logger.getLogger(RegionModel.class.getName()).log(Level.SEVERE, null, ex);
        }

  }
  
  
  public void fillForRepManagementSearchMin(ResultSet res){
        try {
            this.setRegionId(res.getString("REGION_ID"));
            this.setRegionName(res.getString("REGION_NAME"));
           // this.setRegionLevelTypeId(res.getString("region_level_type_id"));
           // this.setRegionLevelTypeName(res.getString("region_level_type_name"));
        } catch (SQLException ex) {
            Logger.getLogger(RegionModel.class.getName()).log(Level.SEVERE, null, ex);
        }

  }
  
  public void fillForSubRegion(ResultSet res){
        try {
            this.setRegionLevelTypeId(res.getString("region_level_type_id"));
            this.setParentRegionId(res.getString("parent_region_id"));
            this.setRegionName(res.getString("region_name"));
            this.setRegionId(res.getString("region_id"));
        } catch (SQLException ex) {
            Logger.getLogger(RegionModel.class.getName()).log(Level.SEVERE, null, ex);
        }

  }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            if(GenericDAO.checkColumnName("REGION_ID", res)){
            Integer id =res.getInt("REGION_ID");
                this.setRegionId(id.toString());
            }
            if(GenericDAO.checkColumnName("REGION_NAME", res))
            this.setRegionName(res.getString("REGION_NAME"));
            if(GenericDAO.checkColumnName("REGION_LEVEL_TYPE_NAME", res))
            this.setRegionLevelTypeName(res.getString("REGION_LEVEL_TYPE_NAME"));
              if(GenericDAO.checkColumnName("PARENT_REGION_ID", res)){
             Integer parent =res.getInt("PARENT_REGION_ID");
                  this.setParentRegionId(parent.toString());
              }
                   if(GenericDAO.checkColumnName("REGION_LEVEL_TYPE_ID", res)){
                   Integer level =res.getInt("REGION_LEVEL_TYPE_ID");
                       this.setRegionLevelTypeId(level.toString());
                   }
        } catch (SQLException ex) {
            Logger.getLogger(RegionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}