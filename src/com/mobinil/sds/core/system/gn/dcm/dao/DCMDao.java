package com.mobinil.sds.core.system.gn.dcm.dao;

import java.sql.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.core.system.gn.dcm.dto.DCMDto;
import com.mobinil.sds.core.system.gn.dcm.model.DCMModel;

import com.mobinil.sds.web.interfaces.cr.*;
import com.mobinil.sds.web.interfaces.sop.*;
import com.mobinil.sds.web.interfaces.hlp.*;

public class DCMDao 
{

  public static final String DCM_LEVEL_DISTRIBUTOR ="DISTRIBUTER";
  public static final String DCM_LEVEL_SUPER_DEALER ="SUPER DEALER";
  public static final String DCM_LEVEL_POS ="POS";
  public static final String DCM_LEVEL_FRANCHISE = "Franchise";


  private DCMDao() //i dont want it to be instantiated since all its members are static
  {
  } 
  
  public static DCMDto getAllDCM(Connection con) throws SQLException
  {
    String sql = "select * from vw_gen_dcm , vw_gen_dcm_level where vw_gen_Dcm.DCM_LEVEL_ID =+ vw_gen_dcm_level.DCM_LEVEL_ID order by vw_gen_dcm.dcm_name";
    //Utility.logger.debug("The Query issssssssssssssss   " + sql);
    return createDCMDto(sql,con);
  }

  public static DCMDto getAllDCMAssignedToMission(String missionId,String dcmCategory,Connection con) throws SQLException
  {
    String sql = "";
    if(dcmCategory.compareTo(HLPInterfaceKey.DCM_CATEGORY_SURVEYS_ALL)==0)
    {
      sql = "select vw_gen_dcm.*,vw_gen_dcm_level.* "+ 
                 " from vw_gen_dcm , vw_gen_dcm_level,HLP_MISSION_DCM "+
                 " where vw_gen_Dcm.DCM_LEVEL_ID =+ vw_gen_dcm_level.DCM_LEVEL_ID "+
                 " and vw_gen_Dcm.DCM_ID = HLP_MISSION_DCM.DCM_ID "+
                 " and HLP_MISSION_DCM.MISSION_ID ="+missionId+" "+
                 " and HLP_MISSION_DCM.MISSION_DCM_STATUS_ID = 1 "+
                 " order by vw_gen_dcm.dcm_name";
    }
    else if(dcmCategory.compareTo(HLPInterfaceKey.DCM_CATEGORY_SURVEYS_COMPLETED)==0)
    {
      sql = "select vw_gen_dcm.*,vw_gen_dcm_level.* "+ 
                 " from vw_gen_dcm , vw_gen_dcm_level,HLP_MISSION_DCM,SRV_FIL_SURVEY "+
                 " where vw_gen_Dcm.DCM_LEVEL_ID =+ vw_gen_dcm_level.DCM_LEVEL_ID "+
                 " and vw_gen_Dcm.DCM_ID = HLP_MISSION_DCM.DCM_ID "+
                 " and HLP_MISSION_DCM.MISSION_ID ="+missionId+" "+
                 " and HLP_MISSION_DCM.FIL_SURVEY_ID = SRV_FIL_SURVEY.FIL_SURVEY_ID "+
                 " and SRV_FIL_SURVEY.FIL_SURVEY_COMPLETED = 1 "+
                 " and HLP_MISSION_DCM.MISSION_DCM_STATUS_ID = 1 "+
                 " order by vw_gen_dcm.dcm_name";
    }
    else if(dcmCategory.compareTo(HLPInterfaceKey.DCM_CATEGORY_SURVEYS_INCOMPLETED)==0)
    {
      sql = "select vw_gen_dcm.*,vw_gen_dcm_level.* "+  
               " from vw_gen_dcm , vw_gen_dcm_level,HLP_MISSION_DCM "+
               " where vw_gen_Dcm.DCM_LEVEL_ID =+ vw_gen_dcm_level.DCM_LEVEL_ID "+
               " and vw_gen_Dcm.DCM_ID = HLP_MISSION_DCM.DCM_ID "+
               " and HLP_MISSION_DCM.MISSION_ID ="+missionId+" "+
               " and HLP_MISSION_DCM.DCM_ID not in "+
               " (select HLP_MISSION_DCM.DCM_ID from HLP_MISSION_DCM,SRV_FIL_SURVEY where HLP_MISSION_DCM.MISSION_ID = "+missionId+" and HLP_MISSION_DCM.FIL_SURVEY_ID = SRV_FIL_SURVEY.FIL_SURVEY_ID and SRV_FIL_SURVEY.FIL_SURVEY_COMPLETED = 1) "+
               " and HLP_MISSION_DCM.MISSION_DCM_STATUS_ID = 1 "+ 
               " order by vw_gen_dcm.dcm_name";
    }
    return createDCMDto(sql,con);
  }

  public static DCMDto getRandomDCMAssignedToMission(String missionId,String dcmCategory,int randomNumber,Connection con) throws SQLException
  {
    String sql = "";
    if(dcmCategory.compareTo(HLPInterfaceKey.DCM_CATEGORY_SURVEYS_ALL)==0)
    {
      sql = "select * from(select ROWNUM as row_num,vw_gen_dcm.*,vw_gen_dcm_level.DCM_LEVEL_NAME "+ 
                 " from vw_gen_dcm , vw_gen_dcm_level,HLP_MISSION_DCM "+
                 " where vw_gen_Dcm.DCM_LEVEL_ID =+ vw_gen_dcm_level.DCM_LEVEL_ID "+
                 " and vw_gen_Dcm.DCM_ID = HLP_MISSION_DCM.DCM_ID "+
                 " and HLP_MISSION_DCM.MISSION_ID ="+missionId+" "+
                 " and HLP_MISSION_DCM.MISSION_DCM_STATUS_ID = 1) "+
                 " where row_num = "+randomNumber+" ";
    }
    else if(dcmCategory.compareTo(HLPInterfaceKey.DCM_CATEGORY_SURVEYS_COMPLETED)==0)
    {
      sql = "select * from(select ROWNUM as row_num,vw_gen_dcm.*,vw_gen_dcm_level.DCM_LEVEL_NAME "+ 
                 " from vw_gen_dcm , vw_gen_dcm_level,HLP_MISSION_DCM,SRV_FIL_SURVEY "+
                 " where vw_gen_Dcm.DCM_LEVEL_ID =+ vw_gen_dcm_level.DCM_LEVEL_ID "+
                 " and vw_gen_Dcm.DCM_ID = HLP_MISSION_DCM.DCM_ID "+
                 " and HLP_MISSION_DCM.MISSION_ID ="+missionId+" "+
                 " and HLP_MISSION_DCM.FIL_SURVEY_ID = SRV_FIL_SURVEY.FIL_SURVEY_ID "+
                 " and SRV_FIL_SURVEY.FIL_SURVEY_COMPLETED = 1 "+
                 " and HLP_MISSION_DCM.MISSION_DCM_STATUS_ID = 1) "+
                 " where row_num = "+randomNumber+" ";
    }
    else if(dcmCategory.compareTo(HLPInterfaceKey.DCM_CATEGORY_SURVEYS_INCOMPLETED)==0)
    {
      sql = "select * from(select ROWNUM as row_num,vw_gen_dcm.*,vw_gen_dcm_level.DCM_LEVEL_NAME "+  
               " from vw_gen_dcm , vw_gen_dcm_level,HLP_MISSION_DCM "+
               " where vw_gen_Dcm.DCM_LEVEL_ID =+ vw_gen_dcm_level.DCM_LEVEL_ID "+
               " and vw_gen_Dcm.DCM_ID = HLP_MISSION_DCM.DCM_ID "+
               " and HLP_MISSION_DCM.MISSION_ID ="+missionId+" "+
               " and HLP_MISSION_DCM.DCM_ID not in "+
               " (select HLP_MISSION_DCM.DCM_ID from HLP_MISSION_DCM,SRV_FIL_SURVEY where HLP_MISSION_DCM.MISSION_ID = "+missionId+" and HLP_MISSION_DCM.FIL_SURVEY_ID = SRV_FIL_SURVEY.FIL_SURVEY_ID and SRV_FIL_SURVEY.FIL_SURVEY_COMPLETED = 1) "+
               " and HLP_MISSION_DCM.MISSION_DCM_STATUS_ID = 1) "+ 
               " where row_num = "+randomNumber+" ";
    }
    return createDCMDto(sql,con);
  }

  public static DCMDto getDCMListByLevel(String level,Connection con) throws SQLException
  {

    DCMDto dcmDto=null;
    String sql = "select * from vw_gen_dcm , vw_gen_dcm_level where vw_gen_Dcm.DCM_LEVEL_ID = vw_gen_dcm_level.DCM_LEVEL_ID  and dcm_level_name=\'" + level +"\' order by vw_gen_dcm.dcm_name";
    dcmDto = createDCMDto(sql,con);
              
    return dcmDto;
  }

  public static DCMDto getDCMListByLevel(String level,String channelId,Connection con) throws SQLException
  {

    DCMDto dcmDto=null;

    String sql = "select * from vw_gen_dcm , vw_gen_dcm_level where vw_gen_Dcm.CHANNEL_ID = '"+channelId+"' and  vw_gen_Dcm.DCM_LEVEL_ID = vw_gen_dcm_level.DCM_LEVEL_ID  and dcm_level_name=\'" + level +"\' order by vw_gen_dcm.dcm_name";
    dcmDto = createDCMDto(sql,con);
    CachEngine.storeObject(ContractRegistrationInterfaceKey.CACH_OBJ_DCM_LIST_ALL , dcmDto);              
    return dcmDto;
  }
  public static DCMDto getContractRegisterationUserDCMListByLevel(String level, String userID,Connection con) throws SQLException
  {

    DCMDto dcmDto=null;
      String sql = "select * from vw_gen_dcm , vw_gen_dcm_level"+
                   " where vw_gen_Dcm.DCM_LEVEL_ID = vw_gen_dcm_level.DCM_LEVEL_ID"+
                   " and dcm_level_name=\'"+ level +"\'"+
                   " and dcm_id in (select dcm_id from VW_CR_USER_DCM_ACCESS "+
                   " where user_id = "+userID+") order by vw_gen_dcm.dcm_name";
      //Utility.logger.debug(sql);             
      dcmDto = createDCMDto(sql,con);             
              
    return dcmDto;
  }

  public static DCMDto getContractRegisterationUserDCMListByLevel(String level, String userID,String channelId,Connection con) throws SQLException
  {  
    DCMDto dcmDto=null;
    String sql = "select * from vw_gen_dcm , vw_gen_dcm_level"+
                   " where vw_gen_dcm.CHANNEL_ID = "+channelId+" and vw_gen_Dcm.DCM_LEVEL_ID = vw_gen_dcm_level.DCM_LEVEL_ID"+
                   " and dcm_level_name=\'"+ level +"\' "+
                   " and dcm_id in (select dcm_id from VW_CR_USER_DCM_ACCESS "+
                   " where user_id = "+userID+") order by vw_gen_dcm.dcm_name";
    
      dcmDto = createDCMDto(sql,con);             

    return dcmDto;
  }

  public static DCMDto getSOPUserDCMListByLevel(String level, String userID,String channelId,Connection con) throws SQLException
  {
   

   
    DCMDto dcmDto=null;

   
   
      String sql = "select * from vw_gen_dcm , vw_gen_dcm_level"+
                   " where vw_gen_Dcm.DCM_LEVEL_ID = vw_gen_dcm_level.DCM_LEVEL_ID"+
                   " and dcm_level_name=\'"+ level +"\'"+
                   " and vw_gen_Dcm.CHANNEL_ID = '"+channelId+"'"+
                   " and dcm_id in (select dcm_id from SOP_USER_DCM_ACCESS "+
                   " where user_id = "+userID+") order by vw_gen_dcm.dcm_name";
                   //Utility.logger.debug(sql);
      dcmDto = createDCMDto(sql,con);             
   
    return dcmDto;
  }

  public static DCMDto getAuthenticationCallDCMListByLevel(String level, String userID,Connection con) throws SQLException
  {
    String sql = "select * from vw_gen_dcm , vw_gen_dcm_level"+
                 " where vw_gen_Dcm.DCM_LEVEL_ID = vw_gen_dcm_level.DCM_LEVEL_ID"+
                 " and dcm_level_name=\'"+ level +"\'"+
                 " and dcm_id in (select dcm_id from VW_ATH_USER_DCM_ACCESS "+
                 " where user_id = "+userID+") order by vw_gen_dcm.dcm_name";
    DCMDto dcmDto = createDCMDto(sql,con);  

    return dcmDto;
  }

  private static DCMDto createDCMDto(String sql,Connection con)  throws SQLException
  {
      
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery(sql);        
    DCMDto dcmDto = new DCMDto();
    while (res.next())
    {
      String dcmName = res.getString("DCM_NAME");
      String dcmCode = res.getString("DCM_CODE");
      String dcmLevel = res.getString("DCM_LEVEL_NAME");
      int dcmId = res.getInt("DCM_ID");
      String channelName = res.getString("CHANNEL_NAME");
      String stkNumber = res.getString("STK_NUMBER");
      DCMModel dcmModel = new DCMModel( dcmName, dcmCode,  dcmId,  dcmLevel, channelName,stkNumber);    
      dcmDto.addDCM(dcmModel);            
    }
    res.close();
    stat.close();
    
    return dcmDto;
  }



  //---------------------------------------------------------------
  // used in KPIEngine.previewKPI()
  public static String getDcmCode (Connection argCon, String stkNumber)
  {
     ResultSet rst ;
    PreparedStatement stmt ;
    String strQuery = null ;
    String dcmCode = null;
    try
    {
      strQuery = new String("select DCM_CODE from GEN_DCM where STK_NUMBER = '"+ stkNumber+"'");
      //Utility.logger.debug("The Query isssssssssssssssssssssss " + strQuery);
      stmt = argCon.prepareStatement (strQuery) ;
      rst = stmt.executeQuery();
      while(rst.next())
      {
         dcmCode = rst.getString("DCM_CODE");
      }
       rst.close();
      stmt.close();
    }
    catch(Exception objExp)
    {
      objExp.printStackTrace();
    }
    return dcmCode;
    }
  
  public static DCMModel getDCMModel (Connection argCon, int nDCMID)
  {
    ResultSet rst ;
    PreparedStatement stmt ;
    String strQuery = null ;
    DCMModel dCMModel = null ;
    try
    {
      strQuery = new String (
        " select VW_GEN_DCM.*,gen_dcm_level.DCM_LEVEL_NAME " +
        " from   VW_GEN_DCM,gen_dcm_level " +
        " where  VW_GEN_DCM.DCM_LEVEL_ID = gen_dcm_level.DCM_LEVEL_ID and VW_GEN_DCM.DCM_ID = ? "
        );
      stmt = argCon.prepareStatement (strQuery) ;
      stmt.setInt (1, nDCMID);
      rst = stmt.executeQuery ();
      while(rst.next())
      {
        dCMModel = new DCMModel (
                        rst.getString("DCM_NAME"), 
                        rst.getString("DCM_CODE"), 
                        rst.getInt("DCM_ID"), 
                        rst.getString("DCM_LEVEL_NAME"), 
                        rst.getString("CHANNEL_NAME"),
                        rst.getString("STK_NUMBER")
                        ) ;
      }
      rst.close();
      stmt.close();
    }
    catch(Exception objExp)
    {
      objExp.printStackTrace();
    }
    return dCMModel ;
  }

  
  public static DCMModel getDCMModel (Connection argCon, String strDCMCode)
  {
    ResultSet rst ;
    PreparedStatement stmt ;
    String strQuery = null ;
    DCMModel dCMModel = null ;
    try
    {
      strQuery = new String (
        " select VW_GEN_DCM.*,GEN_DCM_LEVEL.DCM_LEVEL_NAME " +
        " from   VW_GEN_DCM,GEN_DCM_LEVEL " +
        " where  VW_GEN_DCM.DCM_LEVEL_ID = GEN_DCM_LEVEL.DCM_LEVEL_ID and VW_GEN_DCM.DCM_CODE = ? "
        );
        //Utility.logger.debug(strQuery);
      stmt = argCon.prepareStatement (strQuery) ;
      stmt.setString (1, strDCMCode);
      rst = stmt.executeQuery ();
      while(rst.next())
      {
        dCMModel = new DCMModel (
                        rst.getString("DCM_NAME"), 
                        rst.getString("DCM_CODE"), 
                        rst.getInt("DCM_ID"), 
                        rst.getString("DCM_LEVEL_NAME"),
                        rst.getString("CHANNEL_NAME"),
                        rst.getString("STK_NUMBER")
                        ) ;
      }
      rst.close();
      stmt.close();
    }
    catch(Exception objExp)
    {
      objExp.printStackTrace();
    }
    return dCMModel ;
  }
  
}