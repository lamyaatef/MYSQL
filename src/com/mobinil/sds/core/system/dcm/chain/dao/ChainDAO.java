package com.mobinil.sds.core.system.dcm.chain.dao;
import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.web.interfaces.dcm.*;
import com.mobinil.sds.core.system.dcm.chain.model.*;

public class ChainDAO 
{
  public ChainDAO()
  {
  }

  public static Vector getAllChains(Connection con)
  {
    Vector chainVec = new Vector();
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from vw_gen_dcm , vw_gen_dcm_level ,vw_gen_dcm_payment_level,vw_gen_city "+
                       "where vw_gen_Dcm.DCM_LEVEL_ID = vw_gen_dcm_level.DCM_LEVEL_ID(+)"+ 
                      " and vw_gen_dcm.DCM_PAYMENT_LEVEL_ID = VW_GEN_DCM_PAYMENT_LEVEL.DCM_PAYMENT_LEVEL_ID(+)"+
                      " and vw_gen_dcm.DCM_CITY_ID = VW_GEN_CITY.CITY_CODE(+)";
      Utility.logger.debug("The Query isssssssssssssssssss " +strSql);
      ResultSet res = stat.executeQuery(strSql);
      while (res.next())
      {
        chainVec.add(new ChainModel(res));
      }
      stat.close();
       res.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
   return chainVec;
  }

  public static Vector getAllChainStatus(Connection con)
  {
    Vector chainStatusVec = new Vector();
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from GEN_DCM_STATUS order by DCM_STATUS_NAME";
      ResultSet res = stat.executeQuery(strSql);
      while(res.next())
      {
        chainStatusVec.add(new chainStatusModel(res));
      }
      stat.close();
       res.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return chainStatusVec;
  }

  public static Vector getAllChainLevel(Connection con)
  {
    Vector chainLevelVec = new Vector();
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from GEN_DCM_LEVEL order by DCM_LEVEL_Name";
      ResultSet res = stat.executeQuery(strSql);
      while(res.next())
      {
        chainLevelVec.add(new chainLevelModel(res));
      }
      stat.close();
       res.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return chainLevelVec;
  }

  public static Vector getAllChainCity(Connection con)
  {
    Vector chainCityVec = new Vector();
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from GEN_CITY order by CITY_ENGLISH";
      ResultSet res = stat.executeQuery(strSql);
      while(res.next())
      {
        chainCityVec.add(new chainCityModel(res));
      }
      stat.close();
       res.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return chainCityVec;
  }

  public static Vector getAllChainDistrict(Connection con)
  {
    Vector chainDistrictVec = new Vector();
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from GEN_DISTRICT order by DISTRICT_ENGLISH";
      ResultSet res = stat.executeQuery(strSql);
      while(res.next())
      {
        chainDistrictVec.add(new chainDistrictModel(res));
      }
      stat.close();
       res.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return chainDistrictVec;
  }

  public static Vector getAllChainPaymentLevel(Connection con)
  {
  Utility.logger.debug("getAllChainPaymentLevel");
  
    Vector chainPaymentLevelVec = new Vector();
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from GEN_DCM_PAYMENT_LEVEL order by DCM_PAYMENT_LEVEL_NAME";
      ResultSet res = stat.executeQuery(strSql);
      while(res.next())
      {
        chainPaymentLevelVec.add(new chainPaymentLevelModel(res));
      }
      stat.close();
      res.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  Utility.logger.debug("getAllChainPaymentLevel");    
    return chainPaymentLevelVec;
  }

  public static Vector getAllChainChannel(Connection con)
  {
    Vector chainChannelVec = new Vector();
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from GEN_CHANNEL order by CHANNEL_ID";
      ResultSet res = stat.executeQuery(strSql);
      while(res.next())
      {
        chainChannelVec.add(new chainChannelModel(res));
      }
      stat.close();
      res.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return chainChannelVec;
  }

  public static Vector getChainByFilter(Connection con,int rowNum,String chainCode,String chainLevel,String chainChannel,String chainPaymentLevel,String chainCity,String chainDistrict,String chainStatus,String chainStkNumber)
  {
    Vector chainVec = new Vector();
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from ( select ROWNUM AS row_num,vw_gen_dcm.DCM_ID , vw_gen_dcm.DCM_NAME, vw_gen_dcm.DCM_CODE,vw_gen_dcm.DCM_PHONE,vw_gen_dcm.DCM_EMAIL,"+
                      " vw_gen_dcm.STK_NUMBER,vw_gen_dcm.DCM_STATUS_NAME,vw_gen_dcm.DCM_RANK,vw_gen_dcm.DCM_ADDRESS,vw_gen_dcm.CHANNEL_NAME,"+
                      " vw_gen_dcm.DCM_STATUS_ID,vw_gen_dcm.DCM_LEVEL_ID,vw_gen_dcm.DCM_CITY_ID,vw_gen_dcm.DCM_DISTRICT_ID,"+
                      " vw_gen_dcm.DCM_PAYMENT_LEVEL_ID,vw_gen_dcm.CHANNEL_ID,"+
                      " vw_gen_dcm_level.DCM_LEVEL_NAME,vw_gen_dcm_payment_level.DCM_PAYMENT_LEVEL_NAME,"+
                      " vw_gen_city.CITY_ENGLISH,vw_gen_district.DISTRICT_ENGLISH"+
                      " from vw_gen_dcm , vw_gen_dcm_level ,vw_gen_dcm_payment_level,vw_gen_city,vw_gen_district "+
                      " where vw_gen_Dcm.DCM_LEVEL_ID = vw_gen_dcm_level.DCM_LEVEL_ID(+)"+ 
                      " and vw_gen_dcm.DCM_PAYMENT_LEVEL_ID = VW_GEN_DCM_PAYMENT_LEVEL.DCM_PAYMENT_LEVEL_ID(+)"+
                      " and vw_gen_dcm.DCM_CITY_ID = VW_GEN_CITY.CITY_CODE(+)"+
                      " and vw_gen_dcm.DCM_DISTRICT_ID = VW_GEN_DISTRICT.DISTRICT_CODE(+)";
      if(chainCode.compareTo("")!=0)strSql += " and DCM_CODE = '"+chainCode+"' "; 
      if(chainLevel.compareTo("")!=0)strSql += " and vw_gen_dcm.DCM_LEVEL_ID = '"+chainLevel+"' ";
      if(chainChannel.compareTo("")!=0)strSql += " and CHANNEL_ID = '"+chainChannel+"' ";
      if(chainPaymentLevel.compareTo("")!=0)strSql += " and vw_gen_dcm.DCM_PAYMENT_LEVEL_ID = '"+chainPaymentLevel+"' ";
      if(chainCity.compareTo("")!=0)strSql += " and vw_gen_dcm.DCM_CITY_ID = '"+chainCity+"' ";
      if(chainDistrict.compareTo("")!=0)strSql += " and vw_gen_dcm.DCM_DISTRICT_ID = '"+chainDistrict+"' ";
      if(chainStatus.compareTo("")!=0)strSql += " and DCM_STATUS_ID = '"+chainStatus+"' ";
      if(chainStkNumber.compareTo("")!=0)strSql += " and STK_NUMBER = '"+chainStkNumber+"' ";
      
    strSql += " )where row_num > '"+rowNum+"'*49 and row_num <= ('"+rowNum+"'+1)*49 order by DCM_ID";
    Utility.logger.debug("The Query isssssssssssssssssssss " + strSql);
    ResultSet res = stat.executeQuery(strSql);
    while (res.next())
    {
      ChainModel chainModel = new ChainModel(res);
      chainVec.add(chainModel);
      
    }
    Utility.logger.debug("The vector size isssssssss " + chainVec.size());
    stat.close();
     res.close();
    }
    
    catch(Exception e)
    {
    e.printStackTrace();
    }
  
  return chainVec;
}

public static Vector getChainByFilterwithoutRow(Connection con,String chainCode,String chainLevel,String chainChannel,String chainPaymentLevel,String chainCity,String chainDistrict,String chainStatus,String chainStkNumber)
  {
    Vector chainVec = new Vector();
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select *  "+
                      " from vw_gen_dcm , vw_gen_dcm_level ,vw_gen_dcm_payment_level,vw_gen_city,vw_gen_district "+
                      " where vw_gen_Dcm.DCM_LEVEL_ID = vw_gen_dcm_level.DCM_LEVEL_ID(+)"+ 
                      " and vw_gen_dcm.DCM_PAYMENT_LEVEL_ID = VW_GEN_DCM_PAYMENT_LEVEL.DCM_PAYMENT_LEVEL_ID(+)"+
                      " and vw_gen_dcm.DCM_CITY_ID = VW_GEN_CITY.CITY_CODE(+)"+
                      " and vw_gen_dcm.DCM_DISTRICT_ID = VW_GEN_DISTRICT.DISTRICT_CODE(+)";
      if(chainCode.compareTo("")!=0)strSql += " and DCM_CODE = '"+chainCode+"' "; 
      if(chainLevel.compareTo("")!=0)strSql += " and vw_gen_dcm.DCM_LEVEL_ID = '"+chainLevel+"' ";
      if(chainChannel.compareTo("")!=0)strSql += " and CHANNEL_ID = '"+chainChannel+"' ";
      if(chainPaymentLevel.compareTo("")!=0)strSql += " and vw_gen_dcm.DCM_PAYMENT_LEVEL_ID = '"+chainPaymentLevel+"' ";
      if(chainCity.compareTo("")!=0)strSql += " and vw_gen_dcm.DCM_CITY_ID = '"+chainCity+"' ";
      if(chainDistrict.compareTo("")!=0)strSql += " and vw_gen_dcm.DCM_DISTRICT_ID = '"+chainDistrict+"' ";
      if(chainStatus.compareTo("")!=0)strSql += " and DCM_STATUS_ID = '"+chainStatus+"' ";
      if(chainStkNumber.compareTo("")!=0)strSql += " and STK_NUMBER = '"+chainStkNumber+"' ";
      
    strSql += " order by DCM_ID";
    Utility.logger.debug("The Query isssssssssssssssssssss " + strSql);
    ResultSet res = stat.executeQuery(strSql);
    while (res.next())
    {
      ChainModel chainModel = new ChainModel(res);
      chainVec.add(chainModel);
      
    }
    stat.close();
     res.close();
    }
    
    catch(Exception e)
    {
    e.printStackTrace();
    }
  
  return chainVec;
}

public static int countOfTheTable (Connection con,String chainCode,String chainLevel,String chainChannel,String chainPaymentLevel,String chainCity,String chainDistrict,String chainStatus,String chainStkNumber)
{
  int count = 0;
  try
    {
      Statement stat = con.createStatement();
      String strSql = "select count(*) as count  "+
                      " from vw_gen_dcm , vw_gen_dcm_level ,vw_gen_dcm_payment_level,vw_gen_city,vw_gen_district "+
                      " where vw_gen_Dcm.DCM_LEVEL_ID = vw_gen_dcm_level.DCM_LEVEL_ID(+)"+ 
                      " and vw_gen_dcm.DCM_PAYMENT_LEVEL_ID = VW_GEN_DCM_PAYMENT_LEVEL.DCM_PAYMENT_LEVEL_ID(+)"+
                      " and vw_gen_dcm.DCM_CITY_ID = VW_GEN_CITY.CITY_CODE(+)"+
                      " and vw_gen_dcm.DCM_DISTRICT_ID = VW_GEN_DISTRICT.DISTRICT_CODE(+)";
      if(chainCode.compareTo("")!=0)strSql += " and DCM_CODE = '"+chainCode+"' "; 
      if(chainLevel.compareTo("")!=0)strSql += " and vw_gen_dcm.DCM_LEVEL_ID = '"+chainLevel+"' ";
      if(chainChannel.compareTo("")!=0)strSql += " and CHANNEL_ID = '"+chainChannel+"' ";
      if(chainPaymentLevel.compareTo("")!=0)strSql += " and vw_gen_dcm.DCM_PAYMENT_LEVEL_ID = '"+chainPaymentLevel+"' ";
      if(chainCity.compareTo("")!=0)strSql += " and vw_gen_dcm.DCM_CITY_ID = '"+chainCity+"' ";
      if(chainDistrict.compareTo("")!=0)strSql += " and vw_gen_dcm.DCM_DISTRICT_ID = '"+chainDistrict+"' ";
      if(chainStatus.compareTo("")!=0)strSql += " and DCM_STATUS_ID = '"+chainStatus+"' ";
      if(chainStkNumber.compareTo("")!=0)strSql += " and STK_NUMBER = '"+chainStkNumber+"' ";
      Utility.logger.debug("The query isssssssssssssssssssss " +strSql);
      ResultSet res = stat.executeQuery(strSql);
    while (res.next())
    {
      count = res.getInt("count");
    }

  stat.close();
     res.close();
    }
    
    catch(Exception e)
    {
    e.printStackTrace();
    }
  
  return count;
  
}

public static ChainModel selectDcm(Connection con,String dcmId)
{
  ChainModel chainModel = null;
  try
  {
    Statement stat = con.createStatement();
    String strSql = "select * from vw_gen_dcm , vw_gen_dcm_level ,vw_gen_dcm_payment_level,vw_gen_city,vw_gen_district "+
                       "where vw_gen_Dcm.DCM_LEVEL_ID = vw_gen_dcm_level.DCM_LEVEL_ID(+)"+ 
                      " and vw_gen_dcm.DCM_PAYMENT_LEVEL_ID = VW_GEN_DCM_PAYMENT_LEVEL.DCM_PAYMENT_LEVEL_ID(+)"+
                      " and vw_gen_dcm.DCM_CITY_ID = VW_GEN_CITY.CITY_CODE(+)"+
                      " and vw_gen_dcm.DCM_DISTRICT_ID = VW_GEN_DISTRICT.DISTRICT_CODE(+)"+
                      " and vw_gen_dcm.DCM_ID = '"+dcmId+"'";
    Utility.logger.debug("StrSql isssssssssss " + strSql);
    ResultSet res = stat.executeQuery(strSql);
    while (res.next())
    {
      chainModel = new ChainModel(res);
    }
    stat.close();
     res.close();
  }
  catch(Exception e)
  {
    e.printStackTrace();
  }
  return chainModel;
}

public static void updateGenDcm(Connection con,String chainId,String chainCode,String chainName,String chainMail,String chainPhone,
                                String chainRank,String chainAddress,String stkNumber,String chainChannel,String chainLevel,
                                String chainPaymentLevel,String chainCity,String chainDistrict,String chainStatus)
{
  try
  {
    Statement stat = con.createStatement();
    String strSql = "update GEN_DCM"+
                    " SET DCM_NAME ='"+chainName+"' , DCM_EMAIL = '"+chainMail+"' , DCM_PHONE = '"+chainPhone+"' , DCM_STATUS_ID = '"+chainStatus+"',"+
                    " DCM_LEVEL_ID = '"+chainLevel+"' , DCM_CODE = '"+chainCode+"' , DCM_RANK = '"+chainRank+"', DCM_CITY_ID = '"+chainCity+"',"+
                    " DCM_DISTRICT_ID = '"+chainDistrict+"' , DCM_PAYMENT_LEVEL_ID = '"+chainPaymentLevel+"', DCM_ADDRESS = '"+chainAddress+"',"+
                    " CHANNEL_ID = '"+chainChannel+"' , STK_NUMBER = '"+stkNumber+"'"+
                    " where DCM_ID = '"+chainId+"'";
  Utility.logger.debug("The update Query issssssssss " + strSql);
  stat.executeUpdate(strSql);
  stat.close();
  
  }
  catch(Exception e)
  {
    e.printStackTrace();
  }
}

public static void insertGenDcm(Connection con,Long chainId,String chainCode,String chainName,String chainMail,String chainPhone,
                                String chainRank,String chainAddress,String stkNumber,String chainChannel,String chainLevel,
                                String chainPaymentLevel,String chainCity,String chainDistrict,String chainStatus)
{
  try
  {
    Statement stat = con.createStatement();
    String strSql = "insert into GEN_DCM"+
                    "(DCM_ID,DCM_NAME,DCM_EMAIL,DCM_PHONE,DCM_STATUS_ID,DCM_LEVEL_ID,DCM_CODE,DCM_RANK,DCM_CITY_ID,DCM_DISTRICT_ID,DCM_PAYMENT_LEVEL_ID,DCM_ADDRESS,CHANNEL_ID,STK_NUMBER)"+
                    " values('"+chainId+"','"+chainName+"','"+chainMail+"','"+chainPhone+"','"+chainStatus+"','"+chainLevel+"','"+chainCode+"',"+
                    "'"+chainRank+"','"+chainCity+"','"+chainDistrict+"','"+chainPaymentLevel+"','"+chainAddress+"','"+chainChannel+"','"+stkNumber+"')";
   Utility.logger.debug("The insert query is " +strSql);
   stat.executeUpdate(strSql);
   stat.close();
  }
  catch(Exception e)
  {
    e.printStackTrace();
  }
}
}