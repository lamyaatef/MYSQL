package com.mobinil.sds.core.system.dcm.group.model;

import java.util.Vector;
import java.sql.*;
import com.mobinil.sds.core.system.dcm.group.dao.*;

public class GroupModel 
{
  int groupID = 0;
  int groupStatusID = 0;
 
  String groupStatusName = "";
  String groupName = "";
  String groupFunctionStatusName = "";
  String groupDescription = "";
  
  Vector groupFunctionNames = new Vector();
  Vector groupFunctionIDs = new Vector();
  Vector groupFunctionTargets = new Vector();
  Vector groupPOSCodes = new Vector();
  Vector groupPOSNames = new Vector();
  Vector groupPOSIDs = new Vector();
  Vector groupFunctionStatusID = new Vector();  
  Vector groupFunctionTargetTypes = new Vector();

  

  public static final String GROUP_ID = "GROUP_ID";
  public static final String GROUP_STATUS_ID = "GROUP_STATUS_ID";
  public static final String GROUP_STATUS_NAME = "GROUP_STATUS_NAME";
  public static final String GROUP_NAME = "GROUP_NAME";
  public static final String FUNCTION_NAME = "FUNCTION_NAME";
  public static final String FUNCTION_ID = "FUNCTION_ID";
  public static final String DCM_NAME = "DCM_NAME";
  public static final String DCM_CODE = "DCM_CODE";
  public static final String DCM_ID = "POS_ID";
  public static final String FUNCTION_STATUS_NAME = "FUNCTION_STATUS_NAME";
  public static final String FUNCTION_STATUS_ID = "FUNCTION_STATUS_ID";
  public static final String POS_ID = "POS_ID";
  public static final String TARGET_VALUE = "TARGET_VALUE";
  public static final String GROUP_DESCRIPTION = "GROUP_DESCRIPTION";
  public static final String  TARGET_DURATION_TYPE = "TARGET_DURATION_TYPE";

  public void setGroupPOSIDs(Vector newgroupPOSIDs )
   {
	   groupPOSIDs = newgroupPOSIDs;
   }
 public void setGroupFunctionTargetTypes(Vector newgroupFunctionTargetTypes )
   {
	   groupFunctionTargetTypes = newgroupFunctionTargetTypes;
   }   
   public void setGroupID(int newGroupID )
   {
	   groupID = newGroupID ;
   }
    public void setGroupFunctionStatusID(Vector newGroupFunctionStatusID )
   {
	   groupFunctionStatusID = newGroupFunctionStatusID ;
   }
    public void setGroupFunctionStatusName(String newGroupFunctionStatusName )
   {
	   groupFunctionStatusName = newGroupFunctionStatusName ;
   }
   public void setGroupStatusID(int newGroupStatusID )
   {
  		groupStatusID =newGroupStatusID ;
   }
   public void setGroupStatusName(String newGroupStatusName)
   {
   		groupStatusName=newGroupStatusName;
   }
    public void setGroupDescription(String newGroupDescription)
   {
   		groupDescription=newGroupDescription;
   }
   public void setGroupName(String newGroupName )
   {
   		groupName =newGroupName ;
   }
   public void setGroupFunctionNames(Vector newGroupFunctionNames )
   {
   		groupFunctionNames = newGroupFunctionNames ;
   }
   public void setGroupFunctionIDs(Vector newGroupFunctionIDs )
   {
   		groupFunctionIDs =newGroupFunctionIDs ;
   }
   public void setGroupFunctionTargets(Vector newGroupFunctionTargets)
   {
   		groupFunctionTargets=newGroupFunctionTargets;
   }
   public void setGroupPOSCodes(Vector newGroupPOSCodes )
   {
		   groupPOSCodes =newGroupPOSCodes ;
   }
   public void setGroupPOSNames(Vector newGroupPOSNames )
   {
   		groupPOSNames=newGroupPOSNames ; 
   }
   public int getGroupID()
   {
	   return groupID ;
   }
   public Vector getGroupFunctionStatusID()
   {
	   return groupFunctionStatusID ;
   }
   public String getGroupFunctionStatusName()
   {
	   return groupFunctionStatusName ;
   }

   public int getGroupStatusID()
   {
	   return groupStatusID ;
   }
   public String getGroupStatusName()
   {
	   return groupStatusName;
   }
   public String getGroupDescription()
   {
	   return groupDescription;
   }
   public String getGroupName()
   {
	   return groupName ;
   }
   public Vector getGroupFunctionNames()
   {
	   return groupFunctionNames ;
   }
   public Vector getGroupFunctionIDs()
   {
	   return groupFunctionIDs ;
   }
   public Vector getGroupFunctionTargets()
   {
	   return groupFunctionTargets;
   }
   public Vector getGroupPOSCodes()
   {
	   return groupPOSCodes ;
   }
   public Vector getGroupPOSNames()
   {
	   return groupPOSNames ;
   }   
   public Vector getGroupPOSIDs()
   {
	   return groupPOSIDs ;
   }   
   
   public Vector getGroupFunctionTargetTypes()
   {
	   return groupFunctionTargetTypes ;
   }   
   
  public GroupModel(ResultSet rs , Connection con)throws Exception
  {
          setGroupID( rs.getInt(GroupModel.GROUP_ID));
          setGroupStatusID( rs.getInt(GroupModel.GROUP_STATUS_ID));
//          setGroupStatusName(rs.getString(GroupModel.GROUP_STATUS_NAME));
          setGroupName(rs.getString(GroupModel.GROUP_NAME));
          setGroupDescription(rs.getString(GroupModel.GROUP_DESCRIPTION));           

          setGroupFunctionNames(GroupDAO.getGroupFunctionNames(con , getGroupID())); 
          setGroupFunctionIDs(GroupDAO.getGroupFunctionIDs(con,getGroupID()));
          Vector functionTargets = new Vector();
          Vector functionTargetTypes = new Vector();
          for(int i = 0 ; i < getGroupFunctionIDs().size() ; i ++){
            functionTargets.add(GroupDAO.getGroupFunctionTargets(con , getGroupID(),Integer.parseInt((String)getGroupFunctionIDs().get(i))));
            functionTargetTypes.add(GroupDAO.getGroupFunctionTargetTypes(con , getGroupID(),Integer.parseInt((String)getGroupFunctionIDs().get(i)))+"");
            }
          setGroupFunctionTargets(functionTargets);
          setGroupFunctionTargetTypes(functionTargetTypes);
          setGroupPOSCodes(GroupDAO.getGroupPOSCodes(con , getGroupID()));            
          setGroupPOSNames(GroupDAO.getGroupPOSNames(con , getGroupID()));
          setGroupPOSIDs(GroupDAO.getGroupPOSIDs(con , getGroupID()));
  }
  public GroupModel()
  {
  }
}