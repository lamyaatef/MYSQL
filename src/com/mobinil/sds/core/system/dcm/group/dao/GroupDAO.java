package com.mobinil.sds.core.system.dcm.group.dao;

import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.system.dcm.genericModel.DAO.*;
import com.mobinil.sds.core.system.dcm.genericModel.GenericModel;
import com.mobinil.sds.core.system.dcm.group.model.*;
import com.mobinil.sds.core.utilities.*;

public class GroupDAO 
{
  public static Vector getGroupsByFilter(Connection con , String groupName , int functionID , int groupStatusID 
                                          , String posName,String posCode) throws Exception
  {
    Statement stmt = con.createStatement();
    Vector groupVector = new Vector();
    Vector groupIDVector=new Vector();
    Vector groupPOSNamesVector = new Vector();
    Vector groupPOSIDsVector = new Vector();
    Vector groupPOSCodesVector = new Vector();
    Vector groupFunctionNamesVector = new Vector();
    Vector groupFunctionIDsVector = new Vector();
    Vector groupFunctionTargetValueVector  = new Vector();
    Vector groupFunctionStatusID = new Vector();    
    
    int groupID = 0;
    int rsGroupStatusID = 0;

  Utility.logger.debug("XXXXXXXXXXXXXXXXXXXXXXXXinXXXXXXXXXXXXXX");
    String groupStatusName = "";
    String rsGroupName = "";
    String groupFunctionStatusName = "";
    String groupDesc = "";
    boolean andFlag = false;
    boolean nestedFlag =false;
    GroupModel groupModel = null;
    String sqlString = "SELECT * FROM VW_DCM_GROUP  ";
    if(groupStatusID != 0)
    {
      if(andFlag==false)
        sqlString += " WHERE "+GroupModel.GROUP_STATUS_ID+" = "+groupStatusID;
      else
        sqlString += " AND "+GroupModel.GROUP_STATUS_ID+" = " +groupStatusID;
      andFlag = true;
    }
    if(!groupName.equals(""))
    {
      if(andFlag==false)
        sqlString += " WHERE "+GroupModel.GROUP_NAME+" LIKE '%"+groupName+"%' ";
      else
        sqlString += " AND "+GroupModel.GROUP_NAME+" LIKE '%"+groupName+"%' ";
      andFlag = true;
    }
    if(!posName.equals(""))
    {
      if(andFlag==false)
        if (nestedFlag == false)
        sqlString += " WHERE GROUP_ID IN(SELECT GROUP_ID FROM VW_DCM_GROUP_POS_FUNCTION WHERE "+GroupModel.DCM_NAME+" LIKE '%" +posName +"%' ";
        else
        sqlString += " AND "+GroupModel.DCM_NAME+" LIKE '%"+posName+"%'";
      else
      if(nestedFlag == false)
        sqlString += " AND GROUP_ID IN(SELECT GROUP_ID FROM VW_DCM_GROUP_POS_FUNCTION WHERE "+GroupModel.DCM_NAME+"LIKE '%"+posName+"%' ";
        else
        sqlString += " AND "+GroupModel.DCM_NAME+" LIKE '%"+posName+"%'";
     andFlag =true; 
     nestedFlag = true;
    }
   if(!posCode.equals("")) 
   {
      if(andFlag==false )
      if (nestedFlag==false)
        sqlString += " WHERE GROUP_ID IN(SELECT GROUP_ID FROM VW_DCM_GROUP_POS_FUNCTION WHERE  "+GroupModel.DCM_CODE+" ='" +posCode +"' ";
        else
        sqlString+= " AND "+GroupModel.DCM_CODE +"='"+posCode+"'";
      else
      if(nestedFlag == false)
        sqlString += " AND GROUP_ID IN(SELECT GROUP_ID FROM VW_DCM_GROUP_POS_FUNCTION WHERE  "+GroupModel.DCM_CODE+" ='"+posCode+"' ";
        else
        sqlString+= " AND "+GroupModel.DCM_CODE +"='"+posCode+"'";

     andFlag =true; 
     nestedFlag = true;
   }
   if(functionID != 0)
   {
     if(andFlag==false)
     if(nestedFlag==false)
      sqlString += "  WHERE GROUP_ID IN(SELECT GROUP_ID FROM VW_DCM_GROUP_POS_FUNCTION WHERE  "+GroupModel.FUNCTION_ID + "= "+ functionID;
      else
      sqlString +=" AND " + GroupModel.FUNCTION_ID +"="+functionID;
    else
    if(nestedFlag ==false)
      sqlString += "  AND GROUP_ID IN(SELECT GROUP_ID FROM VW_DCM_GROUP_POS_FUNCTION WHERE "+GroupModel.FUNCTION_ID + "= "+ functionID;  
            else
      sqlString +=" AND " + GroupModel.FUNCTION_ID +"="+functionID;

    andFlag = true;
    nestedFlag = true;
   }
   if(nestedFlag == true)
     sqlString += ")";
    Utility.logger.debug(sqlString);
   ResultSet rs = stmt.executeQuery(sqlString);
   while(rs.next())
   {
          groupModel = new GroupModel(rs ,con);  
          groupVector.add(groupModel);
    }
    rs.close();
    
        stmt.close();
   return groupVector;
      
  }  

            
    public static Vector getAllGroups(Connection con)throws Exception
    {
      Statement stmt = con.createStatement();
      Vector groupVector = new Vector();
      GroupModel groupModel = null;
      String sqlString = "SELECT * FROM VW_DCM_GROUP  ";
      ResultSet rs = stmt.executeQuery(sqlString);
      while(rs.next())
   {
          groupModel = new GroupModel(rs ,con);  
          groupVector.add(groupModel);
    }
    rs.close();
    
        stmt.close();
   return groupVector;
    }

    public static Vector getTmpPosGroup(Connection con)
  {
    Vector vec =  new Vector();
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from TMP_POS_GROUP order by ROW_NUM";
      ResultSet res = stat.executeQuery(strSql);
      while(res.next())
      {
        vec.add(new PosGroupImportModel(res));
      }
      res.close();
      stat.close();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    return vec;
  }

  public static void deleteTmpPosGroup(Connection con)
  {
    try
    {
      Statement stat = con.createStatement();
      String strSql = "delete from TMP_POS_GROUP ";
      //System.out.print(strSql);
      stat.executeUpdate(strSql);
      stat.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

   
  
public static void addNewGroup(Connection con , String groupName , String groupDesc) throws Exception
{
  Statement stmt = con.createStatement();
  Long group_id = Utility.getSequenceNextVal(con, "SEQ_DCM_GROUP");
  int intGroupID = group_id.intValue();
  String sqlString = "INSERT INTO DCM_GROUP (GROUP_ID,GROUP_NAME,GROUP_STATUS_ID,GROUP_DESCRIPTION) VALUES( "+intGroupID+" , '"+
                      groupName+"',1,'"+groupDesc+"')";
  int rows = stmt.executeUpdate(sqlString);                      
  stmt.close();
}
public static void editGroup(Connection con , String GroupName , String groupDesc , int groupID) throws Exception
{
  Statement stmt = con.createStatement();
  String sqlString = "UPDATE DCM_GROUP SET GROUP_NAME = '"+GroupName+"' , GROUP_DESCRIPTION = '"+groupDesc+"' WHERE GROUP_ID = "+groupID;
  int rows = stmt.executeUpdate(sqlString);
  stmt.close();
}
public static Vector getGroupFunctionNames(Connection con , int groupID) throws Exception
{
  Statement stmt = con.createStatement();
  String sqlString = "SELECT FUNCTION_NAME FROM VW_DCM_GROUP_FUNCTION WHERE GROUP_ID = "+ groupID;
  ResultSet rs = stmt.executeQuery(sqlString);
  Vector functionNames = new Vector();
  while(rs.next())
    functionNames.add(rs.getString(GroupModel.FUNCTION_NAME));
  rs.close();
  stmt.close();
  return functionNames;
}
public static Vector getGroupFunctionIDs(Connection con , int groupID) throws Exception
{
  Statement stmt = con.createStatement();
  String sqlString = "SELECT FUNCTION_ID FROM DCM_GROUP_FUNCTION WHERE GROUP_ID = "+ groupID;
  ResultSet rs = stmt.executeQuery(sqlString);
  Vector functionIDs = new Vector();
  while(rs.next())
    functionIDs.add(rs.getInt(GroupModel.FUNCTION_ID)+"");
  rs.close();
  stmt.close();
  return functionIDs;
}
public static Vector getGroupPOSNames(Connection con ,int groupID) throws Exception
{
  Statement stmt = con.createStatement();
  String sqlString = "SELECT DCM_NAME FROM VW_DCM_GROUP_POS WHERE GROUP_ID = "+ groupID;
  ResultSet rs = stmt.executeQuery(sqlString);
  Vector POSNames = new Vector();
  while(rs.next())
    POSNames.add(rs.getString(GroupModel.DCM_NAME));
  rs.close();
  stmt.close();
  return POSNames;
}
public static Vector getGroupPOSCodes(Connection con ,int groupID) throws Exception
{
  Statement stmt = con.createStatement();
  String sqlString = "SELECT DCM_CODE FROM VW_DCM_GROUP_POS WHERE GROUP_ID = "+ groupID;
  ResultSet rs = stmt.executeQuery(sqlString);
  Vector POSCodes = new Vector();
  while(rs.next())
    POSCodes.add(rs.getString(GroupModel.DCM_CODE));
  rs.close();
  stmt.close();
  return POSCodes;
}
public static Vector getGroupPOSIDs(Connection con ,int groupID) throws Exception
{
  Statement stmt = con.createStatement();
  String sqlString = "SELECT POS_ID FROM VW_DCM_GROUP_POS WHERE GROUP_ID = "+ groupID;
  ResultSet rs = stmt.executeQuery(sqlString);
  Vector POSIDs = new Vector();
  while(rs.next())
    POSIDs.add(rs.getInt(GroupModel.POS_ID)+"");
  rs.close();
  stmt.close();
  return POSIDs;
}
public static String getGroupFunctionTargets(Connection con ,int groupID ,int functionID) throws Exception
{
  Statement stmt = con.createStatement();
  String sqlString = "SELECT TARGET_VALUE FROM DCM_GROUP_FUNCTION WHERE GROUP_ID = "+ groupID+ " AND FUNCTION_ID = "+functionID;
  ResultSet rs = stmt.executeQuery(sqlString);
  String FunctionTargets = "";
  if(rs.next())
    FunctionTargets=rs.getString(GroupModel.TARGET_VALUE);
  rs.close();
  stmt.close();
  return FunctionTargets;
}
public static int getGroupFunctionTargetTypes(Connection con , int groupID , int functionID) throws Exception
{
  Statement stmt = con.createStatement();
  String sqlString = "SELECT TARGET_DURATION_TYPE FROM DCM_GROUP_FUNCTION WHERE GROUP_ID = "+ groupID+ " AND FUNCTION_ID = "+functionID;
  ResultSet rs = stmt.executeQuery(sqlString);
  int FunctionTargets = 0;
  if(rs.next())
    FunctionTargets=rs.getInt(GroupModel.TARGET_DURATION_TYPE);
  rs.close();
  stmt.close();
  Utility.logger.debug(sqlString);
  Utility.logger.debug("Function Target = "+FunctionTargets);

  return FunctionTargets;
}
public static GroupModel getGroupByID(Connection con , String groupID)throws Exception
{
  Statement stmt = con.createStatement();
  String sqlString = "SELECT * FROM DCM_GROUP WHERE GROUP_ID = "+ groupID  ;
  ResultSet rs = stmt.executeQuery(sqlString);
  GroupModel groupModel = null;
  if(rs.next())
    groupModel = new GroupModel(rs, con);
  rs.close();
  stmt.close();
  return groupModel;
}
public static boolean saveGroupPOS(Connection con , String posName , String posCode ,String groupID) throws Exception
{
  Statement stmt = con.createStatement();

  String sqlString = "SELECT DCM_ID FROM GEN_DCM WHERE DCM_CODE='"+posCode+"'";
  ResultSet rs = stmt.executeQuery(sqlString) ;
  int posID = 0;
  boolean saved = false;
  
  if(rs.next())
  {
    posID = rs.getInt("DCM_ID");

      sqlString = " SELECT FUNCTION_ID FROM DCM_GROUP_FUNCTION WHERE GROUP_ID = "+groupID+
                  " AND FUNCTION_ID IN(SELECT"+
                  " FUNCTION_ID FROM VW_DCM_GROUP_POS_FUNCTION WHERE POS_ID = "+posID+")";
    ResultSet res = stmt.executeQuery(sqlString);    
   if(!res.next())
    {
      saved = true;
      Long groupPOSID = Utility.getSequenceNextVal(con ,"SEQ_DCM_GROUP_POS");
      int group_pos_id = groupPOSID.intValue();
      sqlString = "INSERT INTO DCM_GROUP_POS (GROUP_POS_ID ,GROUP_ID , POS_ID ) VALUES ('"+group_pos_id+"','"+groupID+"','"+posID+"')";
      int rows = stmt.executeUpdate(sqlString);
    }
    res.close();
  }
  rs.close();
  stmt.close();
  return saved;  
}
public static boolean saveGroupFunctions (Connection con , String functionID , String functionTarget , 
                                            String functionDuration ,String groupID) throws Exception
{
  Statement stmt = con.createStatement();
  String sqlString = "SELECT * FROM DCM_GROUP_FUNCTION WHERE GROUP_ID = '"+groupID+"' AND FUNCTION_ID='"+functionID+"'";
  ResultSet rs = stmt.executeQuery(sqlString);
  if(rs.next()){
    rs.close();
       stmt.close();
       return false;
  }
    else{
    sqlString = "SELECT * FROM VW_DCM_GROUP_POS_FUNCTION WHERE FUNCTION_ID = "+functionID+
                " AND POS_ID IN(SELECT POS_ID FROM VW_DCM_GROUP_POS WHERE GROUP_ID ="+groupID+")";
    ResultSet res = stmt.executeQuery(sqlString);
    if(!res.next())
    {
      Long groupFunctionID = Utility.getSequenceNextVal(con,"SEQ_DCM_GROUP_FUNCTION");
      int group_function_id = groupFunctionID.intValue();

      sqlString = "INSERT INTO DCM_GROUP_FUNCTION (GROUP_FUNCTION_ID,GROUP_ID , FUNCTION_ID ,TARGET_VALUE , TARGET_DURATION_TYPE) VALUES ('"+
                     group_function_id+"','"+groupID+"','"+functionID+"','"+functionTarget+"','"+functionDuration+"' )";
      Utility.logger.debug(sqlString);                     
      int rows = stmt.executeUpdate(sqlString);
     stmt.close();
      return true;
    }
    else
    {
      return false;
    }
    }

   
}
public static void deleteGroupPOS(Connection con ,String groupID ,String posID)throws Exception
{
  Statement stmt = con.createStatement();
  String sqlString = "DELETE FROM DCM_GROUP_POS WHERE GROUP_ID = " + groupID +"  AND POS_ID = "+posID;
  int rows = stmt.executeUpdate(sqlString);
  stmt.close();
}
public static void deleteGroupFunction(Connection con ,String groupID ,String functionID)throws Exception
{
  Statement stmt = con.createStatement();
  String sqlString = "DELETE FROM DCM_GROUP_FUNCTION WHERE GROUP_ID = " + groupID +"  AND FUNCTION_ID = "+functionID;
  
  int rows = stmt.executeUpdate(sqlString);
  stmt.close();
}
public static void updateGroupStatus(Connection con , String groupID , String groupStatusID) throws Exception
{
  Statement stmt  = con.createStatement();
  String sqlString = "UPDATE DCM_GROUP SET GROUP_STATUS_ID = "+ groupStatusID+" WHERE GROUP_ID = "+ groupID;
  int rows = stmt.executeUpdate(sqlString);
  Utility.logger.debug(sqlString);
  stmt.close();
}
  public GroupDAO()
  {
  }
}