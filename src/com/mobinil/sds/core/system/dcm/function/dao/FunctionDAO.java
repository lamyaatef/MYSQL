package com.mobinil.sds.core.system.dcm.function.dao;

import java.sql.*;
import java.util.*;
import com.mobinil.sds.core.system.dcm.function.model.*;
import com.mobinil.sds.core.utilities.*;



public class FunctionDAO 
{
  public static FunctionModel get_function_by_id(Connection con,int functionID) throws Exception
  {
    FunctionModel functionModel =  new FunctionModel();
    String fn_name   = "";
    String fn_desc   = "";
    int fn_id        = 0 ;
    int fn_status_id = 0 ;
    String fn_units = "";
    String fn_status_name ="";
          Statement stat = con.createStatement();
          String sql = "SELECT * FROM VW_DCM_FUNCTION WHERE FUNCTION_ID = '" + functionID + "'";
          ResultSet res = stat.executeQuery(sql);    
          while(res.next())
          {
            fn_name      = res.getString("FUNCTION_NAME");
            fn_id        = res.getInt("FUNCTION_ID");
            fn_status_id = res.getInt("FUNCTION_STATUS_ID");
            fn_desc      = res.getString("FUNCTION_DESCRIPTION");
            fn_status_name = res.getString("FUNCTION_STATUS_NAME");
            fn_units = res.getString("FUNCTION_UNIT");
            Utility.logger.debug("ssssssss  "+res.getString("FUNCTION_DESCRIPTION"));
            functionModel.set_function_desc(fn_desc);
            functionModel.set_function_name(fn_name);
            functionModel.set_function_id(fn_id);
            functionModel.set_function_status_id(fn_status_id);
            functionModel.set_function_units(fn_units);
            functionModel.set_function_status_name(fn_status_name);
            
          }
      

        res.close();
        stat.close();
        return functionModel;
  }
  public static Vector getFunctionsByFilter(Connection con ,String functionName ,int functionStatusID,String functionType) throws Exception
  {
    Vector functions = new Vector();
    String fn_name   = "";
    String fn_desc   = "";
    int fn_id        = 0 ;
    int fn_status_id = 0 ;
    String fn_status_name = "";
    String fn_units = "";
    boolean andFlag = false;
    Statement stmt = con.createStatement();
    String sqlString = "SELECT * FROM VW_DCM_FUNCTION ";
    if(!functionName.equals(""))
    {
      if(andFlag == false)
        sqlString += " WHERE FUNCTION_NAME LIKE '%"+functionName +"%' ";
      else
        sqlString += " AND FUNCTION_NAME LIKE '%"+functionName +"%' ";  

      andFlag =true;
    }
    if(functionStatusID != 0)
    {
      if(andFlag == false)
        sqlString += " WHERE FUNCTION_STATUS_ID = " + functionStatusID+" ";
      else 
        sqlString += " AND FUNCTION_STATUS_ID = " + functionStatusID+" ";
      andFlag =true;
    }
    if(!functionType.equals(""))
    {
      if(andFlag == false)
          sqlString +=" WHERE FUNCTION_TYPE_ID = "+functionType;
      else
          sqlString += " AND FUNCTION_TYPE_ID = "+functionType;
      andFlag = true;      
    }
    ResultSet rs = stmt.executeQuery(sqlString);
    Utility.logger.debug(sqlString);
    FunctionModel functionModel =  null;
    while(rs.next())
          {
            functionModel =  new FunctionModel();
            fn_name      = rs.getString("FUNCTION_NAME");
            fn_id        = rs.getInt("FUNCTION_ID");
            fn_status_id = rs.getInt("FUNCTION_STATUS_ID");
            fn_desc      = rs.getString("FUNCTION_DESCRIPTION");
            fn_status_name = rs.getString("FUNCTION_STATUS_NAME");
            fn_units = rs.getString("FUNCTION_UNIT");

            
            functionModel.set_function_desc(fn_desc);
            functionModel.set_function_name(fn_name);
            functionModel.set_function_id(fn_id);
            functionModel.set_function_status_id(fn_status_id);
            functionModel.set_function_status_name(fn_status_name);
            functionModel.set_function_units(fn_units);
            functionModel.setFunctionTypeID(rs.getInt("FUNCTION_TYPE_ID"));
            functionModel.setFunctionTypeName(rs.getString("FUNCTION_TYPE_NAME"));            
            functions.add(functionModel);
          }
    rs.close();
    stmt.close();
    return functions;
  }
  public static void addNewFunction(Connection con , String functionName, String functionDesc , String functionUnit) throws Exception
  {
    Statement stmt = con.createStatement();
    Long functionID = Utility.getSequenceNextVal(con,"SEQ_DCM_FUNCTION"); 
    int function_id = functionID.intValue();
    String sqlString = "INSERT INTO DCM_FUNCTION (FUNCTION_ID , FUNCTION_NAME , FUNCTION_STATUS_ID ,FUNCTION_DESCRIPTION ,FUNCTION_UNIT,FUNCTION_TYPE_ID)"+
                       " VALUES("+function_id+",'"+functionName+"',1,'"+functionDesc+"','"+functionUnit+"',2)";
    int rows = stmt.executeUpdate(sqlString);
    stmt.close();
  }
  public static void saveEditedFunction(Connection con, String functionName , String functionDesc , String functionUnit, int functionID) throws Exception
  {
    Statement stmt = con.createStatement();
    String sqlString = "UPDATE DCM_FUNCTION SET FUNCTION_NAME ='"+functionName+"' , FUNCTION_DESCRIPTION = '"+functionDesc+"',"+
                        "FUNCTION_UNIT='"+functionUnit+"' WHERE FUNCTION_ID = "+functionID;
    int rows = stmt.executeUpdate(sqlString);
    stmt.close();
  } 
  public static void updateFunctionStatus(Connection con , int functionStatusID , int functionID) throws Exception
  {
    Statement stmt = con.createStatement();
    String sqlString = "UPDATE DCM_FUNCTION SET FUNCTION_STATUS_ID = "+functionStatusID+" WHERE FUNCTION_ID ="+functionID;
    int rows = stmt.executeUpdate(sqlString);
    stmt.close();
  }
  
  public FunctionDAO()
  {
  }
}