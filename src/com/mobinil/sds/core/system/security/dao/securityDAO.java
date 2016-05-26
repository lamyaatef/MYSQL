package com.mobinil.sds.core.system.security.dao;
import com.mobinil.sds.core.system.security.dto.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;
import java.util.Vector;
public class securityDAO 
{

public static Vector getUserLogs(Connection argCon, String argCondition)
  {
    Vector vecUserLog = new Vector();
    try 
    {
      Statement stmtLog = argCon.createStatement();
      String strQryLog = "select logt.id, logt.ACTION_NAME,logt.USER_ID,logt.USER_IP,to_char(logt.ACTION_TIME,'DD-MM-YYYY') ACTION_TIME,logt.ACTION_URL, persont.PERSON_FULL_NAME USER_NAME from SDS_ACTION_LOG logt left join GEN_PERSON persont on logt.USER_ID=persont.PERSON_ID where 1=1 " +argCondition ;
      System.out.println("sql in get logs is "+strQryLog);
      ResultSet rstLog = stmtLog.executeQuery(strQryLog);
      while(rstLog.next())
      {
      logDTO lDTO = new logDTO();

      lDTO.setId(new Integer(rstLog.getInt("ID")));
      lDTO.setActionName(rstLog.getString("ACTION_NAME"));
      lDTO.setActionTime(rstLog.getString("ACTION_TIME"));
      lDTO.setActionUrl(rstLog.getString("ACTION_URL"));
      lDTO.setUserIp(rstLog.getString("USER_IP"));
      lDTO.setUserId(String.valueOf(rstLog.getInt("USER_ID")));
      lDTO.setUserName(rstLog.getString("USER_NAME"));
      
      vecUserLog.addElement(lDTO);
       
      }
      rstLog.close();
      stmtLog.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return vecUserLog;
  }

    public static Vector getSystemSetting(Connection con , String securityTablePropName,String type)
    {
    Vector sdto = new Vector();
    try
      {
  
   Statement stat = con.createStatement();
    String SQL = "select * from " + securityTablePropName+ " where SECURITY_TYPE='"+type+"' and SECURITY_STATUS!=2";
    ResultSet rs = stat.executeQuery(SQL);
    
        while (rs.next())
          {
            securityDTO dto = new securityDTO();
            dto.setID(rs.getInt("ID") + "");
            dto.setSECUIRTY_NAME(rs.getString("SECUIRTY_NAME"));
            dto.setSECUIRTY_TYPE(Integer.valueOf(rs.getInt("SECURITY_TYPE")+""));
            Boolean b = Boolean.FALSE;
            if (rs.getInt("SECURITY_STATUS") == 1)
              {
                b = Boolean.TRUE;
              }
            if (rs.getInt("SECURITY_STATUS") == 2)
              {
                b = Boolean.FALSE;
              }
            dto.setSECURITY_STATUS(b);
            sdto.add(dto);

          }
          rs.close();
          stat.close();
          
          
        return sdto;
      }
    catch (SQLException e)
      {
    	e.printStackTrace();
        return null;
      }

  }

  public static Vector getSystemSetting(Connection con)
    {
    Vector sdto = new Vector();
    try
      {
//    Connection con = Utility.getConnection();
   Statement stat = con.createStatement();
    String SQL = "select * from SDS_SECURITY_CONTROLLER";
    ResultSet rs = stat.executeQuery(SQL);
    
        while (rs.next())
          {
            securityDTO dto = new securityDTO();
            dto.setID(rs.getInt("ID") + "");
            dto.setSECUIRTY_NAME(rs.getString("SECUIRTY_NAME"));
            dto.setSECUIRTY_TYPE(new Integer(rs.getInt("SECURITY_TYPE")));
            dto.setIntSECURITY_STATUS(new Integer(rs.getInt("SECURITY_STATUS")));

//            Boolean b = Boolean.FALSE;
//            if (rs.getInt("SECURITY_STATUS") == 1)
//              {
//                b = Boolean.TRUE;
//              }
//            if (rs.getInt("SECURITY_STATUS") == 2)
//              {
//                b = Boolean.FALSE;
//              }
//            dto.setSECURITY_STATUS(b);
            sdto.add(dto);

          }
//          Utility.closeConnection(con);
          
          rs.close();
          stat.close();
        return sdto;
      }
    catch (SQLException e)
      {
    	e.printStackTrace();
        return null;
      }

  }

public static Integer getProps(Connection con,String propName){
  Integer property=new Integer(0);

  try
      {
   // Connection con = Utility.getConnection();
   Statement stat = con.createStatement();
 
    ResultSet propertyRS = stat.executeQuery("SELECT PROPERTIES from SDS_PROPERTIES where REASONE='"+propName+"'");
    
      if (propertyRS.next())
        {
          property = Integer.valueOf(propertyRS.getInt("PROPERTIES")+"");
  
        }
      propertyRS.close();
      stat.close();
  //    Utility.closeConnection(con);
      
      
      return property;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      return Integer.valueOf(0+"");
    }



}

public static Vector getProps(Connection con){
  Vector properties=new Vector();

  try
      {
//    Connection con = Utility.getConnection();
   Statement stat = con.createStatement();
    ResultSet propertyRS = stat.executeQuery("SELECT PROPERTIES from SDS_PROPERTIES ");
    
      while (propertyRS.next())
        {
          properties.addElement(new Integer(propertyRS.getInt("PROPERTIES")));
  
        }
//      Utility.closeConnection(con);
      propertyRS.close();
      stat.close();
      
      return properties;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      return null;
    }



}

public static void updateProps(Connection con,Vector vec){


  try
      {
//    Connection con = Utility.getConnection();
   Statement stat = con.createStatement();

   for(int i=0;i<vec.size();i++){
   
    int ss = (Integer.valueOf((String)vec.get(i))).intValue();
    if (i==2)
   {
   Integer lastPassCount =  getProps(con,"LAST_PASSWORD_COUNT");
    if (lastPassCount.intValue()>ss)
      {
      updatePasswordHistory(con,lastPassCount.intValue(),ss);
      }
   }
    stat.executeUpdate("update SDS_PROPERTIES  set PROPERTIES = '"+ss+"' where ID = "+(i+1));

   }

    stat.close();
      
      
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      
    }



}
    public static void updatePasswordHistory(Connection con,int intLastPass,int intNewPass){
        String strGetUsersId = "select User_id from GEN_USER";
        String strGetUserHistory = "select MAX(PASSWORD_INDEX) passCount from SDS_PASS_TRACE where SYSTEM_USER_ID=";
try 
{
       Statement stat = con.createStatement();
       Vector vec = new Vector();
       ResultSet userIdsRs = stat.executeQuery(strGetUsersId);
   
      while (userIdsRs.next())
        {
          vec.addElement(new Integer(userIdsRs.getInt(1)));  
        }        
      userIdsRs.close();
      for (int i=0;i<vec.size();i++)
      {      

      ResultSet userHistroyRs = stat.executeQuery(strGetUserHistory+((Integer) vec.get(i)));

      if (userHistroyRs.next())
      {
        if (userHistroyRs.getInt("passCount")!=0 && intLastPass>intNewPass)
        {  

        String SQL1 = "update SDS_PASS_TRACE set PASSWORD_INDEX= PASSWORD_INDEX-(select max(PASSWORD_INDEX)-"+intNewPass+" from SDS_PASS_TRACE) where SYSTEM_USER_ID="+((Integer) vec.get(i));
        String SQL2 = "delete from SDS_PASS_TRACE where PASSWORD_INDEX<=0 and SYSTEM_USER_ID="+((Integer) vec.get(i));
          stat.executeUpdate(SQL1);
          stat.executeUpdate(SQL2);

        }
      }
      userHistroyRs.close();
      stat.close();
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      
    }
      }      
public static void updateSysSetting(Connection con,Vector vec){


  try
      {
//    Connection con = Utility.getConnection();
   Statement stat = con.createStatement();

   for(int i=0;i<vec.size();i++){
String settingId = ((securityDTO)vec.get(i)).getID();   
int settingStatus = ((securityDTO)vec.get(i)).getIntSECURITY_STATUS().intValue();
int settingType =((securityDTO)vec.get(i)).getSECUIRTY_TYPE().intValue(); 
    stat.executeUpdate("update SDS_SECURITY_CONTROLLER  set SECURITY_STATUS = '"+settingStatus+"' ,SECURITY_TYPE = '"+settingType+"'  where ID = "+settingId);

   }

    stat.close();
      
      
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      
    }



}


public static Integer getMandatory(Connection con){
  Integer securityMandatoryCount=Integer.valueOf(0+"");
try
      {
   // Connection con = Utility.getConnection();
   Statement stat = con.createStatement();
String SQL="select count (SECURITY_TYPE) SECURITY_TYPE from SDS_SECURITY_CONTROLLER where SECURITY_TYPE=2";

    ResultSet mandatoryRS = stat.executeQuery(SQL);
   
      if (mandatoryRS.next())
        {
          securityMandatoryCount = Integer.valueOf (mandatoryRS.getInt("SECURITY_TYPE")+"");
  
        }
     
      
      mandatoryRS.close();
      stat.close();
     // Utility.closeConnection(con);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      return Integer.valueOf(0+"");
    }
    return securityMandatoryCount;

}
}