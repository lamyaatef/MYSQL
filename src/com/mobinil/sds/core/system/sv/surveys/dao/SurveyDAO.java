package com.mobinil.sds.core.system.sv.surveys.dao;

import com.mobinil.sds.core.system.sv.surveys.model.*;

import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.utilities.*;

public class SurveyDAO 
{
  public SurveyDAO()
  {
  }

  public static Vector getAllSurveys(Connection con)
  {
      Vector srvVec = new Vector();
      try
      {
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_SRV_SURVEY order by SURVEY_STATUS_ID,SURVEY_NAME");
       while(res.next())
       {
         srvVec.add(new SurveyModel(res));
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return srvVec; 
  }

  public static Vector getAllMissionGroups(Connection con)
  {
      Vector srvVec = new Vector();
      try
      {
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_SRV_MISSION_GROUP order by MISSION_GROUP_STATUS_ID,MISSION_GROUP_NAME");
       while(res.next())
       {
         srvVec.add(new MissionGroupModel(res));
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
      return srvVec; 
  }

  public static MissionGroupModel getMissionGroupById(Connection con,String missionGroupId)
  {
      MissionGroupModel missionGroupModel = null;
      try
      {
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_SRV_MISSION_GROUP where MISSION_GROUP_ID = "+missionGroupId+" ");
       while(res.next())
       {
         missionGroupModel = new MissionGroupModel(res);
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
      return missionGroupModel; 
  }

  public static Vector getFilSurevyMissionGroup(Connection con,String missionGroupId)
  {
      Vector vec = new Vector();
      try
      {
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from SRV_FIL_SURVEY_MISSION_GROUP where MISSION_GROUP_ID = "+missionGroupId+" ");
       while(res.next())
       {
         String strFilSurveyId = res.getString("FIL_SURVEY_ID");
         vec.add(strFilSurveyId);
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
      return vec; 
  }

  public static Vector getAllFilSurveys(Connection con)
  {
      Vector srvVec = new Vector();
      try
      {
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_SRV_FIL_SURVEY order by FIL_SURVEY_STATUS_ID,FIL_SURVEY_NAME");
       while(res.next())
       {
         srvVec.add(new FilSurveyModel(res));
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return srvVec; 
  }

  public static Vector getFilSurveyById(Connection con,String filSurveyId)
  {
      Vector srvVec = new Vector();
      try
      {
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_SRV_FIL_SURVEY where FIL_SURVEY_ID = "+filSurveyId+" ");
       while(res.next())
       {
         srvVec.add(new FilSurveyModel(res));
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return srvVec; 
  }  
  
  public static FilSurveyModel getFilSurveyByName(Connection con,String filSurveyName)
  {
      FilSurveyModel filSurveyModel = null;
      try
      {
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_SRV_FIL_SURVEY where FIL_SURVEY_NAME = '"+filSurveyName+"' ");
       if(res.next())
       {
         filSurveyModel = new FilSurveyModel(res);
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return filSurveyModel; 
  }

  public static Vector getAllFilGroupsBySurveyId(Connection con,String filSurveyId)
  {
      Vector srvVec = new Vector();
      try
      {
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_SRV_FIL_GROUP where FIL_SURVEY_ID = "+filSurveyId+" order by FIL_GROUP_ORDER,FIL_GROUP_STATUS_ID,FIL_GROUP_NAME");
       while(res.next())
       {
         srvVec.add(new FilGroupModel(res));
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return srvVec; 
  }

  public static Vector getAllFilQuestionsByGroupId(Connection con,String filGroupId)
  {
      Vector srvVec = new Vector();
      try
      {
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_SRV_FIL_QUESTION where FIL_GROUP_ID = "+filGroupId+" order by FIL_QUESTION_ORDER,FIL_QUESTION_ORDER,FIL_QUESTION");
       while(res.next())
       {
         srvVec.add(new FilQuestionModel(res));
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return srvVec; 
  }

  public static Vector getAllActiveSurveys(Connection con)
  {
      Vector srvVec = new Vector();
      try
      {
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_SRV_SURVEY where SURVEY_STATUS_ID = 1 order by SURVEY_STATUS_ID,SURVEY_NAME");
       while(res.next())
       {
         srvVec.add(new SurveyModel(res));
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return srvVec; 
  }

  public static Vector getSurveyById(Connection con,String surveyId)
  {
      Vector srvVec = new Vector();
      try
      {
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_SRV_SURVEY where SURVEY_ID = "+surveyId+"");
       while(res.next())
       {
         srvVec.add(new SurveyModel(res));
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return srvVec; 
  }

  public static SurveyModel getSurveyByName(Connection con,String surveyName)
  {
      SurveyModel surveyModel = null;
      try
      {
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_SRV_SURVEY where SURVEY_NAME = '"+surveyName+"'");
       if(res.next())
       {
         surveyModel = new SurveyModel(res);
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return surveyModel; 
  }
  
  public static Vector getGroupsBySurveyId(Connection con,String surveyId)
  {
      Vector grpVec = new Vector();
      try
      {
       Statement stat = con.createStatement();
       String strSql = "select * from VW_SRV_GROUP where SURVEY_ID = "+surveyId+" order by GROUP_ORDER,GROUP_STATUS_ID,GROUP_NAME";
       Utility.logger.debug(strSql);
       ResultSet res= stat.executeQuery(strSql);
       while(res.next())
       {
         grpVec.add(new GroupModel(res));
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return grpVec; 
  }

  public static Vector getActiveGroupsBySurveyId(Connection con,String surveyId)
  {
      Vector grpVec = new Vector();
      try
      {
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_SRV_GROUP where SURVEY_ID = "+surveyId+" and GROUP_STATUS_ID = 1 order by GROUP_ORDER,GROUP_STATUS_ID,GROUP_NAME");
       while(res.next())
       {
         grpVec.add(new GroupModel(res));
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return grpVec; 
  }

  public static Vector getGroupsById(Connection con,String groupId)
  {
      Vector grpVec = new Vector();
      try
      {
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_SRV_GROUP where GROUP_ID = "+groupId+"");
       while(res.next())
       {
         grpVec.add(new GroupModel(res));
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return grpVec; 
  }
  
  public static Vector getQuestionsByGroupId(Connection con,String groupId)
  {
      Vector qusVec = new Vector();
      try
      {
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_SRV_QUESTION where GROUP_ID = "+groupId+" order by QUESTION_ORDER,QUESTION_STATUS_ID,QUESTION");
       while(res.next())
       {
         qusVec.add(new QuestionModel(res));
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return qusVec; 
  }

  public static Vector getActiveQuestionsByGroupId(Connection con,String groupId)
  {
      Vector qusVec = new Vector();
      try
      {
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_SRV_QUESTION where GROUP_ID = "+groupId+" and QUESTION_STATUS_ID = 1 order by QUESTION_ORDER,QUESTION_STATUS_ID,QUESTION");
       while(res.next())
       {
         qusVec.add(new QuestionModel(res));
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return qusVec; 
  }

  public static Vector getQuestionsById(Connection con,String questionId)
  {
      Vector qusVec = new Vector();
      try
      {
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_SRV_QUESTION where QUESTION_ID = "+questionId+"");
       while(res.next())
       {
         qusVec.add(new QuestionModel(res));
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return qusVec; 
  }

  public static Vector getChoicesByQuestionId(Connection con,String questionId)
  {
      Vector chsVec = new Vector();
      try
      {
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_SRV_QUESTION_CHOICE where QUESTION_ID = "+questionId+" order by CHOICE_ORDER,CHOICE_ID");
       while(res.next())
       {
         chsVec.add(new ChoiceModel(res));
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return chsVec; 
  }

  public static Vector getAllSurveyStatus(Connection con)
  {
    Vector surveyStatusVec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from SRV_SURVEY_STATUS order by SURVEY_STATUS_ID");     
     while(res.next())
     {
       surveyStatusVec.add(new SurveyStatusModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return surveyStatusVec; 
  }

  public static Vector getAllMissionGroupStatus(Connection con)
  {
    Vector surveyStatusVec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from SRV_MISSION_GROUP_STATUS order by MISSION_GROUP_STATUS_ID");     
     while(res.next())
     {
       surveyStatusVec.add(new MissionGroupStatusModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return surveyStatusVec; 
  }

  public static Vector getAllFilSurveyStatus(Connection con)
  {
    Vector surveyStatusVec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from SRV_FIL_SURVEY_STATUS order by FIL_SURVEY_STATUS_ID");     
     while(res.next())
     {
       surveyStatusVec.add(new FilSurveyStatusModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return surveyStatusVec; 
  }

  public static Vector getAllSurveyTypes(Connection con)
  {
    Vector surveyTypesVec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from VW_SRV_SURVEY_TYPE order by SURVEY_TYPE_NAME");     
     while(res.next())
     {
       surveyTypesVec.add(new SurveyTypeModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return surveyTypesVec; 
  }
  
  public static Vector getAllGroupStatus(Connection con)
  {
    Vector groupStatusVec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from SRV_GROUP_STATUS order by GROUP_STATUS_ID");     
     while(res.next())
     {
       groupStatusVec.add(new GroupStatusModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return groupStatusVec; 
  }

  public static Vector getAllQuestionStatus(Connection con)
  {
    Vector questionStatusVec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from SRV_QUESTION_STATUS order by QUESTION_STATUS_ID");     
     while(res.next())
     {
       questionStatusVec.add(new QuestionStatusModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return questionStatusVec; 
  }

  public static Vector getAllQuestionTypes(Connection con)
  {
    Vector questionTypeVec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from SRV_QUESTION_TYPE order by QUESTION_TYPE_ID");     
     while(res.next())
     {
       questionTypeVec.add(new QuestionTypeModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return questionTypeVec; 
  }

  public static Vector getAllQuestionCategories(Connection con)
  {
    Vector questionCategoryVec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from SRV_QUESTION_CATEGORY order by QUESTION_CATEGORY_ID");     
     while(res.next())
     {
       questionCategoryVec.add(new QuestionCategoryModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return questionCategoryVec; 
  }  
  
  public static void insertSurvey(Connection con,Long surveyID,String surveyName,String surveyTypeId,String surveyDescription,String surveyStatusId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "insert into SRV_SURVEY(SURVEY_ID, "+
                         "SURVEY_NAME, SURVEY_TYPE_ID,SURVEY_DESCRIPTION,SURVEY_STATUS_ID) "+
                         "values("+surveyID+",'"+surveyName+"',"+surveyTypeId+",'"+surveyDescription+"',"+surveyStatusId+")";                            
      //Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

  public static String insertMissionGroup(Connection con,String missionGroupName)
  {
    Long lMissionGroupID = null;
    try
    {
      Statement stat = con.createStatement();

      lMissionGroupID = Utility.getSequenceNextVal(con, "SEQ_SRV_MISSION_GROUP");
            
      String insertSql = "insert into SRV_MISSION_GROUP(MISSION_GROUP_ID, "+
                         "MISSION_GROUP_NAME,MISSION_GROUP_STATUS_ID) "+
                         "values("+lMissionGroupID+",'"+missionGroupName+"',1)";                            
      //Utility.logger.debug(insertSql);
      stat.execute(insertSql);  
      
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    } 
    return lMissionGroupID+"";
  }

  public static void insertFilSurevyMissionGroup(Connection con,String missionGroupId,String filSurveyId)
  {
    try
    {
      Statement stat = con.createStatement();
            
      String insertSql = "insert into SRV_FIL_SURVEY_MISSION_GROUP(MISSION_GROUP_ID, "+
                         "FIL_SURVEY_ID) "+
                         "values("+missionGroupId+","+filSurveyId+")";                            
      //Utility.logger.debug(insertSql);
      stat.execute(insertSql);  
      
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    } 
  }

  public static void updateMissionGroupName(Connection con,String missionGroupId,String missionGroupName)
  {
    try
    {
      Statement stat = con.createStatement();
            
      String insertSql = "update SRV_MISSION_GROUP set MISSION_GROUP_NAME = '"+missionGroupName+"' "+
                         " where MISSION_GROUP_ID = "+missionGroupId+" ";             
      //Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

  public static void updateMissionGroupStatus(Connection con,String missionGroupId,String missionGroupStatusId)
  {
    try
    {
      Statement stat = con.createStatement();
            
      String insertSql = "update SRV_MISSION_GROUP set MISSION_GROUP_STATUS_ID = "+missionGroupStatusId+" "+
                         " where MISSION_GROUP_ID = "+missionGroupId+" ";             
      //Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

  public static void insertFilSurvey(Connection con,Long filSurveyId,String filSurveyName,String filSurveyStatusId,String filSurveyTypeId,String filSurveyDescription,String filSurveyReferenceType,String filSurveyReferenceId,String filSurveyDate ,String filSurveyCompleted,String filSurveyValue,String surveyId,String filSurveyUserId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "insert into SRV_FIL_SURVEY "+
                         "(FIL_SURVEY_ID,FIL_SURVEY_NAME,FIL_SURVEY_STATUS_ID,FIL_SURVEY_TYPE_ID,FIL_SURVEY_DESCRIPTION ,FIL_SURVEY_REFERENCE_TYPE,FIL_SURVEY_REFERENCE_ID,FIL_SURVEY_DATE ,FIL_SURVEY_COMPLETED,FIL_SURVEY_VALUE,SURVEY_ID,FIL_SURVEY_USER_ID) "+
                         "values("+filSurveyId+",'"+filSurveyName+"',"+filSurveyStatusId+","+filSurveyTypeId+",'"+filSurveyDescription+"','"+filSurveyReferenceType+"',"+filSurveyReferenceId+",TO_DATE('"+filSurveyDate+"','MM/DD/YYYY'),"+filSurveyCompleted+","+filSurveyValue+","+surveyId+","+filSurveyUserId+")";                            
      Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

  public static void updateSurvey(Connection con,String surveyID,String surveyName,String surveyTypeId,String surveyDescription,String surveyStatusId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "update SRV_SURVEY "+
                         "SET SURVEY_NAME = '"+surveyName+"', SURVEY_TYPE_ID = "+surveyTypeId+",SURVEY_DESCRIPTION = '"+surveyDescription+"',SURVEY_STATUS_ID = "+surveyStatusId+""+
                         " WHERE SURVEY_ID = "+surveyID+"";
      //System.out.println("The update query issssss " + insertSql);
      //Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

  public static void updateSurveyStatus(Connection con,String surveyID,String surveyStatusId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "update SRV_SURVEY "+
                         "SET SURVEY_STATUS_ID = "+surveyStatusId+""+
                         "WHERE SURVEY_ID = "+surveyID+"";                            
      //Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  } 

  public static void updateFilSurveyStatus(Connection con,String filSurveyID,String filSurveyStatusId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "update SRV_FIL_SURVEY "+
                         "SET FIL_SURVEY_STATUS_ID = "+filSurveyStatusId+""+
                         "WHERE FIL_SURVEY_ID = "+filSurveyID+"";                            
      //Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  } 

  public static void updateFilGroupValue(Connection con,String filGroupID,int intFilGroupValue)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "update SRV_FIL_GROUP "+
                         " SET FIL_GROUP_VALUE = "+intFilGroupValue+""+
                         " WHERE FIL_GROUP_ID = "+filGroupID+"";                            
      //Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

  public static void updateFilSurveyValue(Connection con,String filSurveyID,float intFilSurveyValue)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "update SRV_FIL_SURVEY "+
                         " SET FIL_SURVEY_VALUE = "+intFilSurveyValue+""+
                         " WHERE FIL_SURVEY_ID = "+filSurveyID+"";                            
      Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }
  
  public static void updateFilSurveyComplete(Connection con,String filSurveyID,String filSurveyComplete)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "update SRV_FIL_SURVEY "+
                         "SET FIL_SURVEY_COMPLETED = "+filSurveyComplete+" , FIL_SURVEY_COMPLETED_TIMESTAMP = SYSDATE "+
                         "WHERE FIL_SURVEY_ID = "+filSurveyID+"";                            
      //Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }
  
  public static void updateFilQuestionAnswer(Connection con,String filQuestionID,String filQuestionAnswerId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "update SRV_FIL_QUESTION "+
                         "SET FIL_QUESTION_ANSWER = '"+filQuestionAnswerId+"'"+
                         "WHERE FIL_QUESTION_ID = "+filQuestionID+"";                            
      //Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  } 
  
  public static void insertGroup(Connection con,Long groupID,String groupName,String surveyId,String groupWeight,String groupOrder,String groupReference,String groupDescription,String groupStatusId,String groupCategoryID)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "insert into SRV_GROUP(GROUP_ID, "+
                         "GROUP_NAME, SURVEY_ID,GROUP_WEIGHT,GROUP_ORDER,GROUP_REFERENCE,GROUP_DESCRIPTION,GROUP_STATUS_ID,GROUP_CATEGORY_ID) "+
                         "values("+groupID+",'"+groupName+"','"+surveyId+"',"+groupWeight+","+groupOrder+","+groupReference+",'"+groupDescription+"',"+groupStatusId+","+groupCategoryID+")";   
      Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  } 

  public static void updateGroup(Connection con,String groupID,String groupName,String groupWeight,String groupOrder,String groupReference,String groupDescription,String groupStatusId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "update SRV_GROUP "+
                         " SET GROUP_NAME = '"+groupName+"',GROUP_WEIGHT = "+groupWeight+",GROUP_ORDER = "+groupOrder+",GROUP_REFERENCE = "+groupReference+",GROUP_DESCRIPTION = '"+groupDescription+"',GROUP_STATUS_ID = "+groupStatusId+" "+
                         " WHERE GROUP_ID = "+groupID+"";                            
      Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  } 

  public static void updateGroupStatus(Connection con,String groupID,String groupStatusId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "update SRV_GROUP "+
                         " SET GROUP_STATUS_ID = "+groupStatusId+" "+
                         " WHERE GROUP_ID = "+groupID+"";                            
      //Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

  public static void updateGroupOrder(Connection con,String groupID,String groupOrder)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "update SRV_GROUP "+
                         " SET GROUP_ORDER = "+groupOrder+" "+
                         " WHERE GROUP_ID = "+groupID+"";                            
      //Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }
  public static void insertQuestion(Connection con,Long questionID,Long groupQuestionID,String GroupId,String question,String questionWeight,String questionOrder,String questionMandatory,String questionType,String questionCategory,String questionStatusId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "insert into VW_SRV_QUESTION(QUESTION_ID, GROUP_QUESTION_ID,"+
                         "QUESTION, QUESTION_STATUS_ID,QUESTION_TYPE_ID,QUESTION_CATEGORY_ID,GROUP_ID,QUESTION_WEIGHT,QUESTION_ORDER,QUESTION_MANDATORY) "+
                         "values("+questionID+","+groupQuestionID+",'"+question+"',"+questionStatusId+","+questionType+","+questionCategory+","+GroupId+","+questionWeight+","+questionOrder+","+questionMandatory+")";
      stat.execute(insertSql);
      //String insertSql1 = "insert into SRV_QUESTION (QUESTION_ID,QUESTION,QUESTION_STATUS_ID,QUESTION_TYPE_ID,QUESTION_CATEGORY_ID)"+
      			//		   "values ("+questionID+",'"+question+"',"+questionStatusId+","+questionType+","+questionCategory+")";
     // System.out.println("The first insert query isssssssss " + insertSql1);
      //stat.execute(insertSql1);
      
      //Statement stat2 = con.createStatement();
      //String insertSql2 = "insert into SRV_GROUP_QUESTION (QUESTION_ID,GROUP_QUESTION_ID,GROUP_ID,QUESTION_WEIGHT,QUESTION_ORDER,QUESTION_MANDATORY)"+
      		//			  "values ("+questionID+","+groupQuestionID+","+GroupId+","+questionWeight+","+questionOrder+","+questionMandatory+")";
      //System.out.println("The second insert query isssssss  " + insertSql2);
      //Utility.logger.debug(insertSql);
      //stat2.execute(insertSql2);
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

  public static void updateQuestion(Connection con,String questionID,String question,String questionWeight,String questionOrder,String questionMandatory,String questionType,String questionCategory,String questionStatusId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "update VW_SRV_QUESTION "+
                         " SET QUESTION = '"+question+"', QUESTION_STATUS_ID = "+questionStatusId+",QUESTION_TYPE_ID = "+questionType+",QUESTION_CATEGORY_ID = "+questionCategory+",QUESTION_WEIGHT = "+questionWeight+",QUESTION_ORDER = "+questionOrder+",QUESTION_MANDATORY = "+questionMandatory+" "+
                         " WHERE QUESTION_ID = "+questionID+" ";                            
      Utility.logger.debug(insertSql);
      stat.execute(insertSql);
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

  public static void updateQuestionStatus(Connection con,String questionID,String questionStatusId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "update VW_SRV_QUESTION "+
                         " SET QUESTION_STATUS_ID = "+questionStatusId+""+
                         " WHERE QUESTION_ID = "+questionID+" ";                            
      //Utility.logger.debug(insertSql);
      stat.execute(insertSql);
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

  public static void updateQuestionOrder(Connection con,String questionID,String questionOrder)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "update VW_SRV_QUESTION "+
                         " SET QUESTION_ORDER = "+questionOrder+""+
                         " WHERE QUESTION_ID = "+questionID+" ";                            
      //Utility.logger.debug(insertSql);
      stat.execute(insertSql);
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }
  
  public static void insertQuestionChoice(Connection con,Long questionChoiceID,Long questionID,String choice,String choiceValue,int choiceOrder)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "insert into SRV_QUESTION_CHOICE(CHOICE_ID, QUESTION_ID,"+
                         "CHOICE,CHOICE_VALUE,CHOICE_ORDER) "+
                         "values("+questionChoiceID+","+questionID+",'"+choice+"',"+choiceValue+","+choiceOrder+")";                            
      //Utility.logger.debug(insertSql);
      stat.execute(insertSql);
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  } 

  public static void deleteQuestionChoice(Connection con,String questionID)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "delete from SRV_QUESTION_CHOICE "+
                         " where QUESTION_ID = "+questionID+" ";                            
      //Utility.logger.debug(insertSql);
      stat.execute(insertSql);
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

  public static void deleteFilSurevyMissionGroup(Connection con,String missionGroupID)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "delete from SRV_FIL_SURVEY_MISSION_GROUP "+
                         " where MISSION_GROUP_ID = "+missionGroupID+" ";                            
      //Utility.logger.debug(insertSql);
      stat.execute(insertSql);
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

//////////////////methods saved from delete and decompiled/////////////
    public static void insertFilGroup(Connection con, Long filGroupID, String filGroupName, Long filSurveyId, String filGroupWeight, String filGroupOrder, String filGroupReference, String filGroupDescription,String filGroupStatusId, String groupId, String filGroupValue)
    {
        try
        {
            Statement stat = con.createStatement();
            String insertSql = "insert into SRV_FIL_GROUP  (FIL_GROUP_ID,FIL_GROUP_NAME,FIL_SURVEY_ID,FIL_GROUP_WEIGHT ,FIL_GROUP_ORDER ,FIL_GROUP_REFERENCE ,FIL_GROUP_STATUS_ID ,FIL_GROUP_DESCRIPTION ,GROUP_ID,FIL_GROUP_VALUE) "+
                                                  "values("+ filGroupID + ",'" + filGroupName + "'," + filSurveyId + "," + filGroupWeight + "," + filGroupOrder + "," + filGroupReference + "," + filGroupStatusId + ",'" + filGroupDescription + "'," + groupId + "," + filGroupValue + ")";
            //Utility.logger.debug(insertSql);
            stat.execute(insertSql);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public static void insertFilQuestion(Connection con, Long filQuestionID, String filQuestion, String filQuestionTypeID, String filQuestionCategoryID, Long filGroupID, String filQuestionWeight, String filQuestionAnswer,String filQuestionOrder, String filQuestionMandatory, String questionId)
    {
        try
        {
            Statement stat = con.createStatement();
            String insertSql = "insert into SRV_FIL_QUESTION  (FIL_QUESTION_ID,FIL_QUESTION,FIL_QUESTION_TYPE_ID,FIL_QUESTION_CATEGORY_ID,FIL_GROUP_ID,FIL_QUESTION_WEIGHT,FIL_QUESTION_ANSWER,FIL_QUESTION_ORDER,FIL_QUESTION_MANDATORY,QUESTION_ID)"+
                                                    "values("+ filQuestionID + ",'" + filQuestion + "'," + filQuestionTypeID + "," + filQuestionCategoryID + "," + filGroupID + "," + filQuestionWeight + "," + filQuestionAnswer + "," + filQuestionOrder + "," + filQuestionMandatory + "," + questionId + ")";
            //Utility.logger.debug(insertSql);
            stat.execute(insertSql);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }   


    public static Vector getFilAllActiveSurveys(Connection con)
    {
        Vector srvVec = new Vector();
        try
        {
            Statement stat = con.createStatement();
            String sqlString = "select * from VW_SRV_FIL_SURVEY where FIL_SURVEY_STATUS_ID = 1 order by FIL_SURVEY_STATUS_ID,FIL_SURVEY_NAME";
            ResultSet res = stat.executeQuery(sqlString);
            while( res.next())
            {
            srvVec.add(new FilSurveyModel(res)) ;
            }
            stat.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return srvVec;
    }  

    public static Vector getFilAllSurveyStatus(Connection con)
    {
        Vector surveyStatusVec = new Vector();
        try
        {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from SRV_FIL_SURVEY_STATUS order by FIL_SURVEY_STATUS_ID");
            while( res.next())
            {
            surveyStatusVec.add(new FilSurveyStatusModel(res)) ;
            }
            stat.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return surveyStatusVec;
    }    
    public static String getSurveyType(Connection con , String surveyID)
    {
      String surveyType ="";
      try
      {
        Statement stmt = con.createStatement();
        String sqlString = "SELECT SURVEY_TYPE_ID FROM VW_SRV_SURVEY WHERE SURVEY_ID = "+surveyID;
        ResultSet rs = stmt.executeQuery(sqlString);
        if(rs.next())
        {
          surveyType = rs.getString("SURVEY_TYPE_ID");
        }
        rs.close();
        stmt.close();
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
      return surveyType;
    }
    public static String getGroupType(Connection con , String GroupID)
    {
      String GroupType ="";
      try
      {
        Statement stmt = con.createStatement();
        String sqlString = "SELECT GROUP_CATEGORY_ID FROM VW_SRV_GROUP WHERE GROUP_ID = "+GroupID;
        ResultSet rs = stmt.executeQuery(sqlString);
        if(rs.next())
        {
          GroupType = rs.getString("GROUP_CATEGORY_ID");
        }
        rs.close();
        stmt.close();
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
      return GroupType;
    }    
    public static void insertPOSQuestion(Connection con , Long QuestionID , String Question , String QuestionType , 
                                        String QuestionCategory,String QuestionStatus )
    {
      try
      {
        Statement stmt = con.createStatement();
        String sqlString = "INSERT INTO SRV_QUESTION (QUESTION_ID,QUESTION,QUESTION_STATUS_ID,QUESTION_TYPE_ID,QUESTION_CATEGORY_ID)"+
                          " VALUES ("+QuestionID+",'"+Question+"',"+QuestionStatus+","+QuestionType+","+QuestionCategory+")";
                          Utility.logger.debug(sqlString);
        int rows =stmt.executeUpdate(sqlString);
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
    }
    public static Vector getPOSQuestions(Connection con)
    {
      Statement stmt = null;
      QuestionModel questionModel = null;
      Vector questionVector = new Vector();
      try
      {
        stmt = con.createStatement();
        String sqlString = "SELECT * FROM SRV_QUESTION WHERE QUESTION_CATEGORY_ID = 2";
        ResultSet rs = stmt.executeQuery(sqlString);
        Utility.logger.debug(sqlString);
        
        while(rs.next())
        {
          questionModel = new QuestionModel();
          questionModel.setQuestion(rs.getString("QUESTION"));
          questionModel.setQuestionId(rs.getInt("QUESTION_ID")+"");
          questionModel.setQuestionStatusId(rs.getString("QUESTION_STATUS_ID"));
          questionModel.setQuestionTypeId(rs.getInt("QUESTION_TYPE_ID")+"");
          questionModel.setQuestionCategoryId(rs.getString("QUESTION_CATEGORY_ID"));
          questionVector.add(questionModel);
        }
        rs.close();
        stmt.close();
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
      return questionVector;
    }
    public static Vector getPOSGroupQuestions(Connection con,String groupID)
    {
      Statement stmt = null;
      Vector GroupQuestions = new Vector();
      QuestionModel questionModel = null;
      try
      {
        stmt = con.createStatement();
        String sqlString = "SELECT * FROM VW_SRV_QUESTION WHERE GROUP_ID = "+groupID+" AND QUESTION_CATEGORY_ID = 2";
        ResultSet rs = stmt.executeQuery(sqlString);
        Utility.logger.debug(sqlString);        
        while(rs.next())
        {
          questionModel = new QuestionModel(rs);
          GroupQuestions.add(questionModel);
          questionModel =null;
        }
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
      return GroupQuestions;
    }
 public static Vector getPOSGroup(Connection con, String flag )
 {
  Statement stmt = null;
  Vector POSGroup =  new Vector();
  GroupModel groupModel =  null;
  String isNull = "";
  if(!flag.equals("0"))
    isNull = "SURVEY_ID ="+flag+" OR SURVEY_ID IS NULL AND ";
  else
    isNull = "";
   try
   {
     stmt = con.createStatement();
     String sqlString = "SELECT * FROM VW_SRV_GROUP WHERE "+isNull+" GROUP_CATEGORY_ID = 2";
        Utility.logger.debug(sqlString);     
     ResultSet rs = stmt.executeQuery(sqlString);
     while(rs.next())
     {
       groupModel = new GroupModel(rs);
       POSGroup.add(groupModel);
       groupModel = null;

     }


   }
   catch(Exception ex)
   {
     ex.printStackTrace();
   }
        return POSGroup;
 }
public static void updatePOSGroupQuestion(Connection con , String groupID ,String questionId , 
                                          String questionWeight , String questionOrder ,String questionMandatory,String flag)    
{
  
  try
  {
    Statement stmt =  con.createStatement();
    String sqlString  = "";
    Long groupQuestionID ;
    if(flag.equals("new"))
    { 
      groupQuestionID = Utility.getSequenceNextVal(con,"SEQ_SRV_GROUP_QUESTION_ID");
      sqlString = " INSERT INTO SRV_GROUP_QUESTION (GROUP_QUESTION_ID , GROUP_ID,"+
                  " QUESTION_ID,QUESTION_WEIGHT ,QUESTION_ORDER,QUESTION_MANDATORY)"+
                  " VALUES ("+groupQuestionID+","+groupID+","+questionId+","+questionWeight+","+questionOrder+","+questionMandatory+")";
    }
    else if(flag.equals("old"))
    sqlString = " UPDATE SRV_GROUP_QUESTION SET QUESTION_WEIGHT = "+questionWeight+", QUESTION_ORDER = "+questionOrder+
                " , QUESTION_MANDATORY = "+questionMandatory+" WHERE GROUP_ID = "+groupID +" AND QUESTION_ID = "+questionId;
    Utility.logger.debug(sqlString);
    int rows = stmt.executeUpdate(sqlString);
    stmt.close();
    
  }
  catch(Exception ex)
  {
    ex.printStackTrace();
  }
}
public static void deletePOSQuestionFromGroup(Connection con , String QuestionId , String GroupId)
{
  try
  {
    Statement stmt = con.createStatement();
    String sqlString = "DELETE FROM SRV_GROUP_QUESTION WHERE GROUP_ID = "+GroupId +" AND QUESTION_ID = "+QuestionId;
    Utility.logger.debug(sqlString);
    int rows =  stmt.executeUpdate(sqlString);
    stmt.close();
  }
  catch(Exception ex)
  {
    ex.printStackTrace();
  }
}
public static Vector getPOSSurvey(Connection con)
{
    Vector POSSurvey = new Vector();

  try
  {
    Statement stmt = con.createStatement();
    String sqlString = " SELECT * FROM VW_SRV_SURVEY WHERE SURVEY_TYPE_ID = 2 ";
    ResultSet rs = stmt.executeQuery(sqlString);
    SurveyModel surveyModel = null;
    while(rs.next())
    {
      surveyModel = new SurveyModel(rs);
      POSSurvey.add(surveyModel);
      surveyModel = null;
    }
  }
  catch(Exception ex)
  {
    ex.printStackTrace();
  }
  return POSSurvey;
}
public static void updatePOSSurveyGroup(Connection con , String surveyID , String groupID , String groupWeight)
{
  try
  {
    Statement stmt = con.createStatement();
    String sqlString = "UPDATE SRV_GROUP SET SURVEY_ID = "+surveyID+" , GROUP_WEIGHT = "+groupWeight+" WHERE GROUP_ID = "+groupID;
    Utility.logger.debug(sqlString);
    int rows = stmt.executeUpdate(sqlString);
    stmt.close();
  }
  catch(Exception ex)
  {
    ex.printStackTrace();
  }
}
public static void deletePOSGroupFromSurvey(Connection con , String groupID)
{
  try
  {
    Statement stmt = con.createStatement();
    String sqlString = "UPDATE SRV_GROUP SET SURVEY_ID = NULL WHERE GROUP_ID = "+groupID;
    Utility.logger.debug(sqlString);
    int rows = stmt.executeUpdate(sqlString);
    stmt.close();
  }
  catch(Exception ex)
  {
    ex.printStackTrace();
  }
}
}