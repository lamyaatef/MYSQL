package com.mobinil.sds.core.system.hlp.mission.dao;
import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.web.interfaces.hlp.*;
import com.mobinil.sds.core.system.hlp.mission.model.*;
import com.mobinil.sds.core.system.sa.persons.model.*;
import com.mobinil.sds.core.system.sa.users.dto.*;
import com.mobinil.sds.core.system.sa.users.model.*;
import com.mobinil.sds.core.system.sa.users.dao.UserDAO;
public class MissionDAO 
{
  public MissionDAO()
  {
  }

  public static Vector getAllMissions(Connection con)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from VW_HLP_MISSION order by MISSION_STATUS_TYPE_ID,MISSION_NAME");     
     while(res.next())
     {
       vec.add(new MissionModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static Vector getMissionsForUser(Connection con,String userId,String missionStatusTypeId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select VW_HLP_MISSION.* "+
                     " from VW_HLP_MISSION,HLP_MISSION_USER "+
                     " where VW_HLP_MISSION.MISSION_ID = HLP_MISSION_USER.MISSION_ID "+
                     " and HLP_MISSION_USER.USER_ID = "+userId+" "+
                     " and VW_HLP_MISSION.MISSION_STATUS_TYPE_ID = "+missionStatusTypeId+" "+
                     " order by VW_HLP_MISSION.MISSION_STATUS_TYPE_ID,VW_HLP_MISSION.MISSION_NAME ";
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
       vec.add(new MissionModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static Vector getMissionsByFilter(Connection con,String missionName,String missionStatusTypeId,String missionStartDateFrom,String missionStartDateTo,String missionEndDateFrom,String missionEndDateTo)
  {
    Vector vec = new Vector();
    boolean andFlag = false;
    try
    {
     Statement stat = con.createStatement();
     String sqlQuery = "select * from VW_HLP_MISSION ";
     if(missionName.compareTo("")!=0)
     {
        if(!andFlag){sqlQuery += " where ";}
        else{sqlQuery += " and ";}
        sqlQuery += " MISSION_NAME like '%"+missionName+"%' ";
        andFlag = true;
     }
     if(missionStatusTypeId.compareTo("")!=0)
     {
        if(!andFlag){sqlQuery += " where ";}
        else{sqlQuery += " and ";}
        sqlQuery += " MISSION_STATUS_TYPE_ID = "+missionStatusTypeId+" ";
        andFlag = true;
     }
     if(missionStartDateFrom.compareTo("*")!=0)
     {
        if(!andFlag){sqlQuery += " where ";}
        else{sqlQuery += " and ";}
        sqlQuery += " MISSION_START_DATE >= TO_DATE('"+missionStartDateFrom+"','mm/dd/yyyy') ";
        andFlag = true;
     }
     if(missionStartDateTo.compareTo("*")!=0)
     {
        if(!andFlag){sqlQuery += " where ";}
        else{sqlQuery += " and ";}
        sqlQuery += " MISSION_START_DATE <= TO_DATE('"+missionStartDateTo+"','mm/dd/yyyy') ";
        andFlag = true;
     }
     if(missionEndDateFrom.compareTo("*")!=0)
     {
        if(!andFlag){sqlQuery += " where ";}
        else{sqlQuery += " and ";}
        sqlQuery += " MISSION_END_DATE >= TO_DATE('"+missionEndDateFrom+"','mm/dd/yyyy') ";
        andFlag = true;
     }
     if(missionEndDateTo.compareTo("*")!=0)
     {
        if(!andFlag){sqlQuery += " where ";}
        else{sqlQuery += " and ";}
        sqlQuery += " MISSION_END_DATE <= TO_DATE('"+missionEndDateTo+"','mm/dd/yyyy') ";
        andFlag = true;
     }
     sqlQuery += " order by MISSION_STATUS_TYPE_ID,MISSION_NAME";
     
     ResultSet res= stat.executeQuery(sqlQuery);     
     while(res.next())
     {
       vec.add(new MissionModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static Vector getMissionsByFilterForUser(Connection con,String missionName,String missionStatusTypeId,String missionStartDateFrom,String missionStartDateTo,String missionEndDateFrom,String missionEndDateTo,String userId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     String sqlQuery = "select VW_HLP_MISSION.* from VW_HLP_MISSION,HLP_MISSION_USER "+
                       " where VW_HLP_MISSION.MISSION_ID = HLP_MISSION_USER.MISSION_ID "+
                       " and HLP_MISSION_USER.USER_ID = "+userId+" "; 
     if(missionName.compareTo("")!=0)
     {
        sqlQuery += " and VW_HLP_MISSION.MISSION_NAME like '%"+missionName+"%' ";
     }
     if(missionStatusTypeId.compareTo("")!=0)
     {
        sqlQuery += " and VW_HLP_MISSION.MISSION_STATUS_TYPE_ID = "+missionStatusTypeId+" ";
     }
     if(missionStartDateFrom.compareTo("*")!=0)
     {
        sqlQuery += " and VW_HLP_MISSION.MISSION_START_DATE >= TO_DATE('"+missionStartDateFrom+"','mm/dd/yyyy') ";
     }
     if(missionStartDateTo.compareTo("*")!=0)
     {
        sqlQuery += " and VW_HLP_MISSION.MISSION_START_DATE <= TO_DATE('"+missionStartDateTo+"','mm/dd/yyyy') ";
     }
     if(missionEndDateFrom.compareTo("*")!=0)
     {
        sqlQuery += " and VW_HLP_MISSION.MISSION_END_DATE >= TO_DATE('"+missionEndDateFrom+"','mm/dd/yyyy') ";
     }
     if(missionEndDateTo.compareTo("*")!=0)
     {
        sqlQuery += " and VW_HLP_MISSION.MISSION_END_DATE <= TO_DATE('"+missionEndDateTo+"','mm/dd/yyyy') ";
     }
     sqlQuery += " order by VW_HLP_MISSION.MISSION_STATUS_TYPE_ID,VW_HLP_MISSION.MISSION_NAME";
     
     ResultSet res= stat.executeQuery(sqlQuery);     
     while(res.next())
     {
       vec.add(new MissionModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static Vector getTmpMissionDCM()
  {
      Vector hlpVec = new Vector();
      try
      {
       Connection con = Utility.getConnection();
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from TMP_HLP_MISSION_DCM order by ROW_NUM ");
       while(res.next())
       {
         hlpVec.add(new MissionDCMImportModel(res));
       }
       stat.close();
       Utility.closeConnection(con);
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
      return hlpVec; 
  }

  public static void deleteTmpMissionDCM()
  {
      try
      {
       Connection con = Utility.getConnection();
       Statement stat = con.createStatement();
       stat.execute("delete from TMP_HLP_MISSION_DCM ");
       
       stat.close();
       Utility.closeConnection(con);
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
  }
  
  public static MissionModel getMissionById(Connection con,String missionId)
  {
    MissionModel missionModel = null;
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from VW_HLP_MISSION where MISSION_ID = "+missionId+" ");     
     if(res.next())
     {
       missionModel = new MissionModel(res);
       int intNumberOfTargetUsers = MissionDAO.getNumberOfMissionDCM(con,missionId);
       int intNumberOfSurveysCompleted = MissionDAO.getNumberOfSurveysCompleted(con,missionId);
       int intNumberOfSurveysIncompleted = intNumberOfTargetUsers - intNumberOfSurveysCompleted;
       missionModel.setNumberOfUsers(MissionDAO.getNumberOfMissionUser(con,missionId));
       missionModel.setNumberOfTargetUsers(intNumberOfTargetUsers);
       missionModel.setNumberOfSurveysCompleted(intNumberOfSurveysCompleted); 
       missionModel.setNumberOfSurveysIncompleted(intNumberOfSurveysIncompleted);
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return missionModel; 
  }

  public static MissionModel getMissionById(String missionId)
  {
    MissionModel missionModel = null;
    try
    {
     Connection con = Utility.getConnection(); 
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from VW_HLP_MISSION where MISSION_ID = "+missionId+" ");     
     if(res.next())
     {
       missionModel = new MissionModel(res);
       missionModel.setNumberOfUsers(MissionDAO.getNumberOfMissionUser(con,missionId));
       missionModel.setNumberOfTargetUsers(MissionDAO.getNumberOfMissionDCM(con,missionId));
     }
     stat.close();
     Utility.closeConnection(con);
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return missionModel; 
  }

  public static Vector getMissionUser(Connection con,String missionId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from HLP_MISSION_USER where MISSION_ID = "+missionId+" ");     
     while(res.next())
     {
       String userId = res.getString("USER_ID");
       vec.add(userId);
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static int getNumberOfMissionUser(Connection con,String missionId)
  {
    int numberOfUsers = 0;
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select count(*) from HLP_MISSION_USER where MISSION_ID = "+missionId+" ");     
     if(res.next())
     {
       numberOfUsers = res.getInt(1);
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return numberOfUsers; 
  }

  public static int getNumberOfSurveysCompleted(Connection con,String missionId)
  {
    int numberOfSurveysCompleted = 0;
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select count(*) from hlp_mission_dcm,srv_fil_survey "+ 
                     " where hlp_mission_dcm.MISSION_ID = "+missionId+" "+
                     " and hlp_mission_dcm.FIL_SURVEY_ID = srv_fil_survey.FIL_SURVEY_ID "+
                     " and srv_fil_survey.FIL_SURVEY_COMPLETED = 1 "+
                     " and MISSION_DCM_STATUS_ID = 1 ";
     ResultSet res= stat.executeQuery(strSql);     
     if(res.next())
     {
       numberOfSurveysCompleted = res.getInt(1);
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return numberOfSurveysCompleted; 
  }

  public static int getNumberOfMissionDCM(Connection con,String missionId)
  {
    int numberOfDCMs = 0;
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select count(*) from HLP_MISSION_DCM where MISSION_ID = "+missionId+" and MISSION_DCM_STATUS_ID = 1 ");     
     if(res.next())
     {
       numberOfDCMs = res.getInt(1);
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return numberOfDCMs; 
  }

  public static Vector getMissionDCM(Connection con,String missionId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from HLP_MISSION_DCM where MISSION_ID = "+missionId+" ");     
     while(res.next())
     {
       String dcmId = res.getString("DCM_ID");
       vec.add(dcmId);
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static boolean getMissionDCM(String missionId,String dcmId)
  {
    boolean missionDCMExists = false;
    try
    {
     Connection con = Utility.getConnection(); 
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from HLP_MISSION_DCM where MISSION_ID = "+missionId+" and DCM_ID = "+dcmId+" and MISSION_DCM_STATUS_ID = 1 ");     
     if(res.next())
     {
       missionDCMExists = true;
     }
     stat.close();
     Utility.closeConnection(con);
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return missionDCMExists; 
  }

  public static String getMissionDCMFilSurveyId(Connection con,String missionId,String dcmId)
  {
    String strFilSurveyId = "*";
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from HLP_MISSION_DCM where MISSION_ID = "+missionId+" and DCM_ID = "+dcmId+" ");     
     if(res.next())
     {
       strFilSurveyId = res.getString("FIL_SURVEY_ID");
       if(strFilSurveyId == null)strFilSurveyId="";
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return strFilSurveyId; 
  }

  public static Vector getAllMissionStatus(Connection con,String missionId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from VW_HLP_MISSION_STATUS where MISSION_ID = "+missionId+" order by MISSION_STATUS_ID");     
     while(res.next())
     {
       vec.add(new MissionStatusModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static Vector getAllMissionStatusTypes(Connection con)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from HLP_MISSION_STATUS_TYPE order by MISSION_STATUS_TYPE_ID");     
     while(res.next())
     {
       vec.add(new MissionStatusTypeModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static void insertMission(Connection con,String strMissionName,String strMissionDesc,String strMissionStartDate,String strMissionEndDate,String strSurveyId,String userId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      Long lMissionID = Utility.getSequenceNextVal(con, "SEQ_HLP_MISSION_ID");
      Long lMissionStatusID = Utility.getSequenceNextVal(con, "SEQ_HLP_MISSION_STATUS_ID");
      
      if(strSurveyId.compareTo("")==0)strSurveyId = "null";
      
      String strSql = "insert into HLP_MISSION(MISSION_ID,MISSION_NAME,MISSION_DESCRIPTION,MISSION_START_DATE,MISSION_END_DATE,SURVEY_ID,MISSION_STATUS_ID,CREATION_DATE) "+
                      " values("+lMissionID+",'"+strMissionName+"','"+strMissionDesc+"',TO_DATE('"+strMissionStartDate+"','mm/dd/yyyy'),TO_DATE('"+strMissionEndDate+"','mm/dd/yyyy'),"+strSurveyId+","+lMissionStatusID+",SYSDATE)";
      Utility.logger.debug(strSql);
      
      stat.execute(strSql);  

      MissionDAO.insertMissionStaus(con,lMissionStatusID+"",lMissionID+"",userId,"1");
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void updateMission(Connection con,String strMissionId,String strMissionName,String strMissionDesc,String strMissionStartDate,String strMissionEndDate,String strSurveyId,String userId)
  {
    try
    {
      Statement stat = con.createStatement();
          
      if(strSurveyId.compareTo("")==0)strSurveyId = "null";
      
      String strSql = "update HLP_MISSION set MISSION_NAME = '"+strMissionName+"' , MISSION_DESCRIPTION = '"+strMissionDesc+"' ,MISSION_START_DATE =TO_DATE('"+strMissionStartDate+"','mm/dd/yyyy') "+
                      " , MISSION_END_DATE = TO_DATE('"+strMissionEndDate+"','mm/dd/yyyy'),SURVEY_ID = "+strSurveyId+" where MISSION_ID = "+strMissionId+" ";
      Utility.logger.debug(strSql);
      
      stat.execute(strSql);  
      
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void updateMissionStatusTypeId(Connection con,String strMissionId,String strMissionStatusTypeId,String userId)
  {
    try
    {
      Statement stat = con.createStatement();

      Long lMissionStatusID = Utility.getSequenceNextVal(con, "SEQ_HLP_MISSION_STATUS_ID");
      
      String strSql = "update HLP_MISSION set MISSION_STATUS_ID = "+lMissionStatusID+"  "+
                      " where MISSION_ID = "+strMissionId+" ";
      Utility.logger.debug(strSql);
      
      stat.execute(strSql);  
      MissionDAO.insertMissionStaus(con,lMissionStatusID+"",strMissionId,userId,strMissionStatusTypeId);
      
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }


  public static void insertMissionStaus(Connection con,String missionStatusId,String missionId,String userId,String missionStatusTypeId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String strSql = "insert into HLP_MISSION_STATUS(MISSION_STATUS_ID,MISSION_ID,MISSION_STATUS_TYPE_ID,MISSION_STATUS_TIMESTAMP,USER_ID) "+
                      " values("+missionStatusId+","+missionId+","+missionStatusTypeId+",SYSDATE,"+userId+")";
      Utility.logger.debug(strSql);
      
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void insertMissionUser(Connection con,String missionId,String userId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String strSql = "insert into HLP_MISSION_USER(MISSION_ID,USER_ID,ASSIGNMENT_TIMESTAMP) "+
                      " values("+missionId+","+userId+",SYSDATE)";
      Utility.logger.debug(strSql);
      
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void insertMissionDcm(String missionId,String dcmId)
  {
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      
      String strSql = "insert into HLP_MISSION_DCM(MISSION_ID,DCM_ID,MISSION_DCM_STATUS_ID,ASSIGNMENT_TIMESTAMP) "+
                      " values("+missionId+","+dcmId+",1,SYSDATE)";
      Utility.logger.debug(strSql);
      
      stat.execute(strSql);  
      stat.close();
      Utility.closeConnection(con);
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void updateMissionDcm(Connection con,String missionId,String dcmId,String filSurveyId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String strSql = "update HLP_MISSION_DCM set FIL_SURVEY_ID = "+filSurveyId+" where MISSION_ID = "+missionId+" and DCM_ID = "+dcmId+" ";
                  
      Utility.logger.debug(strSql);
      
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void deleteMissionUser(Connection con,String missionId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String strSql = "delete from HLP_MISSION_USER where MISSION_ID = "+missionId+"  ";
      Utility.logger.debug(strSql);
      
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }


  public static void deleteMissionDcm(Connection con,String missionId,String dcmId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String strSql = "delete from HLP_MISSION_DCM "+
                      " where MISSION_ID = "+missionId+" "+
                      " and DCM_ID = "+dcmId+" ";
      Utility.logger.debug(strSql);
      
      String filSurveyId = MissionDAO.getMissionDCMFilSurveyId(con,missionId,dcmId);
      if(filSurveyId.compareTo("*")!=0)
      {
         String strSql2 = "delete from SRV_FIL_QUESTION where FIL_GROUP_ID in (select FIL_GROUP_ID from SRV_FIL_GROUP where FIL_SURVEY_ID = "+filSurveyId+") ";
         Utility.logger.debug(strSql2);         
         stat.execute(strSql2);           

         String strSql3 = "delete from SRV_FIL_GROUP where FIL_SURVEY_ID = "+filSurveyId+" ";
         Utility.logger.debug(strSql3);         
         stat.execute(strSql3);           

         String strSql4 = "delete from SRV_FIL_SURVEY where FIL_SURVEY_ID = "+filSurveyId+" ";
         Utility.logger.debug(strSql4);         
         stat.execute(strSql4);           
      }

      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  
  public static Vector getSystemUsersListAssignedToMission(Connection argCon,String missionId)
  {
    Vector vecUsersList = new Vector();
    try 
    {
      Statement stmtUsersList = argCon.createStatement();
      String strUsersListQuery = "select VW_GEN_USER_DETAILS.* from VW_GEN_USER_DETAILS,HLP_MISSION_USER "+
                                 " where VW_GEN_USER_DETAILS.USER_STATUS_NAME in('Active','InActive') "+
                                 " and VW_GEN_USER_DETAILS.PERSON_TYPE_ID = 1 "+
                                 " and VW_GEN_USER_DETAILS.PERSON_FULL_NAME not in('Administrator') "+
                                 " and VW_GEN_USER_DETAILS.USER_ID = HLP_MISSION_USER.USER_ID "+
                                 " and HLP_MISSION_USER.MISSION_ID = "+missionId+" "+
                                 " order by VW_GEN_USER_DETAILS.PERSON_FULL_NAME ";
      ResultSet rstUsersList = stmtUsersList.executeQuery(strUsersListQuery);
      while(rstUsersList.next())
      {
        /**
         * A person is a more general object than a user. The system may have many
         * persons but not all of them are users. But a user must be a person.
         * That is why a UserModel must take a PersonModel in its constructor.
         * A user is a person who has an account that he can use to login to the 
         * system. Privileges and DCMs are assigned to a user account.
         */
        PersonModel newPersonModel = new PersonModel(rstUsersList.getInt(1));
        //hagry modified this to compile
        /*
        UserModel newUserModel = new UserModel(newPersonModel, 
                                               rstUsersList.getString(2), 
                                               rstUsersList.getInt(3),
                                               rstUsersList.getString(4));
        newPersonModel.setPersonFullName(rstUsersList.getString(5));
        newPersonModel.setPersonAddress(rstUsersList.getString(6));
        newPersonModel.setPersonStatusID(rstUsersList.getInt(7));
        newPersonModel.setPersonStatusName(rstUsersList.getString(8));
        newPersonModel.setPersonTypeID(rstUsersList.getInt(9));
        newPersonModel.setPersonTypeName(rstUsersList.getString(10));
        newPersonModel.setPersonTypeStatusID(rstUsersList.getInt(11));
        newPersonModel.setPersonTypeStatusName(rstUsersList.getString(12));
        newPersonModel.setPersonEMail(rstUsersList.getString(13));
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setUserModel(newUserModel);
        newUserDTO.setUserDCMs(UserDAO.getContractRegisterationUserDCMs(argCon, newPersonModel.getPersonID()));
        vecUsersList.addElement(newUserDTO);
        */
      }
      rstUsersList.close();
      stmtUsersList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return vecUsersList;
  }
}