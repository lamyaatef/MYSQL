package com.mobinil.sds.core.facade.handlers;

import java.util.Vector;
import java.util.HashMap;

import com.mobinil.sds.web.interfaces.sv.SurveyInterfaceKey;
import com.mobinil.sds.web.interfaces.InterfaceKey;

import com.mobinil.sds.core.utilities.Utility;

import com.mobinil.sds.core.system.sv.surveys.dao.*;
import com.mobinil.sds.core.system.sv.surveys.model.*;
import com.mobinil.sds.core.system.gn.dcm.dao.*;
import com.mobinil.sds.core.system.gn.dcm.dto.*;

public class SurveyHandler 
{
  static final int CREATE_NEW_SURVEY              =  1;
  static final int SAVE_SURVEY                    =  2;
  static final int VIEW_ALL_SURVEYS               =  3;
  static final int EDIT_SURVEY                    =  4;
  static final int UPDATE_SURVEY                  =  5;
  static final int UPDATE_SURVEYS_STATUS          =  6;
  static final int VIEW_SURVEY_GROUPS             =  7;
  static final int CREATE_NEW_GROUP               =  8;
  static final int SAVE_GROUP                     =  9;
  static final int EDIT_GROUP                     =  10;
  static final int UPDATE_GROUP                   =  11;
  static final int UPDATE_GROUPS_STATUS           =  12;
  static final int VIEW_GROUP_QUESTIONS           =  13;
  static final int CREATE_NEW_QUESTION            =  14;
  static final int SAVE_QUESTION                  =  15;
  static final int EDIT_QUESTION                  =  16;
  static final int UPDATE_QUESTION                =  17;
  static final int UPDATE_QUESTION_STATUS         =  18;
  static final int FILLING_CREATE_NEW_SURVEY      =  19;
  static final int FILLING_SAVE_SURVEY            =  20;
  static final int FILLING_VIEW_ALL_SURVEYS       =  21;
  static final int FILLING_VIEW_SURVEY_GROUPS     =  22;
  static final int FILLING_UPDATE_SURVEY_STATUS   =  23;
  static final int FILLING_UPDATE_QUESTION_ANSWERS =  24;
  static final int VIEW_MISSION_GROUPS            =  25;
  static final int CREATE_NEW_MISSION_GROUP       =  26;
  static final int SAVE_MISSION_GROUP             =  27;
  static final int EDIT_MISSION_GROUP             =  28; 
  static final int FILLING_COMPLETE_FIL_SURVEY    =  29;
  static final int SRV_POS_SURVEY_GROUP           = 30; 
  static final int SRV_POS_GROUP_QUESTION         = 31;
  static final int SRV_SAVE_GROUP_QUESTION        = 32;
  static final int SRV_CREATE_NEW_POS_GROUP       = 33;
  static final int SRV_CREATE_NEW_POS_SURVEY      = 34;
  static final int SRV_SAVE_POS_SURVEY_GROUP      = 35;
  static final int SRV_POS_QUESTIONS              = 36; 
  static final int SRV_POS_SURVEY                 = 37;
  static final int SRV_POS_GROUP                  = 38;
  static final int SRV_CREATE_NEW_POS_QUESTION    = 39;
  static final int SRV_SAVE_POS_QUESTION          = 40;
  static final int SRV_SAVE_POS_GROUP             = 41;
 
  public static HashMap handle(String action, HashMap paramHashMap,java.sql.Connection con)
  {
    int actionType = 0;
    HashMap dataHashMap = null;
   // Connection  con = null;

    
    try 
    {
     //  con = Utility.getConnection();
       if (action.equals(SurveyInterfaceKey.ACTION_CREATE_NEW_SURVEY))                                   
        actionType = CREATE_NEW_SURVEY;
       else if (action.equals(SurveyInterfaceKey.ACTION_SAVE_SURVEY))                                   
        actionType = SAVE_SURVEY;
       else if (action.equals(SurveyInterfaceKey.ACTION_VIEW_ALL_SURVEYS))                                   
        actionType = VIEW_ALL_SURVEYS; 
       else if (action.equals(SurveyInterfaceKey.ACTION_EDIT_SURVEY))                                   
        actionType = EDIT_SURVEY;  
       else if (action.equals(SurveyInterfaceKey.ACTION_UPDATE_SURVEY))                                   
        actionType = UPDATE_SURVEY;  
       else if (action.equals(SurveyInterfaceKey.ACTION_UPDATE_SURVEYS_STATUS))                                   
        actionType = UPDATE_SURVEYS_STATUS;  
       else if (action.equals(SurveyInterfaceKey.ACTION_VIEW_SURVEY_GROUPS))                                   
        actionType = VIEW_SURVEY_GROUPS;
       else if (action.equals(SurveyInterfaceKey.ACTION_CREATE_NEW_GROUP))                                   
        actionType = CREATE_NEW_GROUP;
       else if (action.equals(SurveyInterfaceKey.ACTION_SAVE_GROUP))                                   
        actionType = SAVE_GROUP;
       else if (action.equals(SurveyInterfaceKey.ACTION_EDIT_GROUP))                                   
        actionType = EDIT_GROUP;
       else if (action.equals(SurveyInterfaceKey.ACTION_UPDATE_GROUP))                                   
        actionType = UPDATE_GROUP;
       else if (action.equals(SurveyInterfaceKey.ACTION_UPDATE_GROUPS_STATUS))                                   
        actionType = UPDATE_GROUPS_STATUS; 
       else if (action.equals(SurveyInterfaceKey.ACTION_VIEW_ALL_QUESTIONS))                                   
        actionType = VIEW_GROUP_QUESTIONS;
       else if (action.equals(SurveyInterfaceKey.ACTION_CREATE_NEW_QUESTION))                                   
        actionType = CREATE_NEW_QUESTION;
       else if (action.equals(SurveyInterfaceKey.ACTION_SAVE_QUESTION))                                   
        actionType = SAVE_QUESTION;
       else if (action.equals(SurveyInterfaceKey.ACTION_EDIT_QUESTION))                                   
        actionType = EDIT_QUESTION;
       else if (action.equals(SurveyInterfaceKey.ACTION_UPDATE_QUESTION))                                   
        actionType = UPDATE_QUESTION;
       else if (action.equals(SurveyInterfaceKey.ACTION_UPDATE_QUESTION_STATUS))                                   
        actionType = UPDATE_QUESTION_STATUS;
       else if (action.equals(SurveyInterfaceKey.ACTION_FILLING_CREATE_NEW_SURVEY))                                   
        actionType = FILLING_CREATE_NEW_SURVEY;
       else if (action.equals(SurveyInterfaceKey.ACTION_FILLING_SAVE_SURVEY))                                   
        actionType = FILLING_SAVE_SURVEY;
       else if (action.equals(SurveyInterfaceKey.ACTION_FILLING_VIEW_ALL_SURVEYS))                                   
        actionType = FILLING_VIEW_ALL_SURVEYS;
       else if (action.equals(SurveyInterfaceKey.ACTION_FILLING_VIEW_SURVEY_GROUPS))                                   
        actionType = FILLING_VIEW_SURVEY_GROUPS;
       else if (action.equals(SurveyInterfaceKey.ACTION_FILLING_UPDATE_SURVEY_STATUS))                                   
        actionType = FILLING_UPDATE_SURVEY_STATUS;
       else if (action.equals(SurveyInterfaceKey.ACTION_FILLING_UPDATE_QUESTION_ANSWERS))                                   
        actionType = FILLING_UPDATE_QUESTION_ANSWERS;
       else if (action.equals(SurveyInterfaceKey.ACTION_VIEW_MISSION_GROUPS))                                   
        actionType = VIEW_MISSION_GROUPS;
       else if (action.equals(SurveyInterfaceKey.ACTION_CREATE_NEW_MISSION_GROUP))                                   
        actionType = CREATE_NEW_MISSION_GROUP;
       else if (action.equals(SurveyInterfaceKey.ACTION_SAVE_MISSION_GROUP))                                   
        actionType = SAVE_MISSION_GROUP;
       else if (action.equals(SurveyInterfaceKey.ACTION_EDIT_MISSION_GROUP))                                   
        actionType = EDIT_MISSION_GROUP;
       else if (action.equals(SurveyInterfaceKey.ACTION_FILLING_COMPLETE_FIL_SURVEY))                                   
        actionType = FILLING_COMPLETE_FIL_SURVEY;
       else if (action.equals(SurveyInterfaceKey.ACTION_SRV_POS_SURVEY_GROUP))
         actionType = SRV_POS_SURVEY_GROUP;
        else if (action.equals(SurveyInterfaceKey.ACTION_SRV_POS_GROUP_QUESTION))         
          actionType = SRV_POS_GROUP_QUESTION;         
        else if (action.equals(SurveyInterfaceKey.ACTION_SRV_SAVE_GROUP_QUESTION))       
          actionType = SRV_SAVE_GROUP_QUESTION;       
        else if (action.equals(SurveyInterfaceKey.ACTION_SRV_CREATE_NEW_POS_GROUP))     
          actionType = SRV_CREATE_NEW_POS_GROUP;       
        else if (action.equals(SurveyInterfaceKey.ACTION_SRV_CREATE_NEW_POS_SURVEY))   
          actionType = SRV_CREATE_NEW_POS_SURVEY;      
        else if (action.equals(SurveyInterfaceKey.ACTION_SRV_SAVE_POS_SURVEY_GROUP)) 
          actionType = SRV_SAVE_POS_SURVEY_GROUP;      
        else if (action.equals(SurveyInterfaceKey.ACTION_SRV_POS_QUESTIONS))
          actionType = SRV_POS_QUESTIONS;              
        else if (action.equals(SurveyInterfaceKey.ACTION_SRV_POS_SURVEY))
          actionType = SRV_POS_SURVEY;                
        else if (action.equals(SurveyInterfaceKey.ACTION_SRV_POS_GROUP))
          actionType = SRV_POS_GROUP;
        else if (action.equals(SurveyInterfaceKey.ACTION_SRV_CREATE_NEW_POS_QUESTION))
          actionType = SRV_CREATE_NEW_POS_QUESTION;
        else if(action.equals(SurveyInterfaceKey.ACTION_SRV_SAVE_POS_QUESTION))
          actionType =SRV_SAVE_POS_QUESTION;
        else if(action.equals(SurveyInterfaceKey.ACTION_SRV_SAVE_POS_GROUP))
          actionType = SRV_SAVE_POS_GROUP;
        
        
      switch (actionType)                                                                                     
      {
        case CREATE_NEW_SURVEY:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            Vector surveyTypesVector = SurveyDAO.getAllSurveyTypes(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , surveyTypesVector);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ACTION , SurveyInterfaceKey.ACTION_CREATE_NEW_SURVEY);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case VIEW_MISSION_GROUPS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            Vector missionGroupsVector = SurveyDAO.getAllMissionGroups(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , missionGroupsVector);
            Vector missionGroupStatusVector = SurveyDAO.getAllMissionGroupStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , missionGroupStatusVector);            
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case SAVE_MISSION_GROUP:
          try                                                                                                 
          {
            String strMissionGroupID = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_MISSION_GROUP_ID);
            String strMissionGroupName = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_MISSION_GROUP_NAME);
  
            if(strMissionGroupID.compareTo("")==0)
            {
              strMissionGroupID = SurveyDAO.insertMissionGroup(con,strMissionGroupName);
            }
            else
            {
              SurveyDAO.updateMissionGroupName(con,strMissionGroupID,strMissionGroupName);
            }
            
            SurveyDAO.deleteFilSurevyMissionGroup(con,strMissionGroupID);

            String strFilSurveysCount = (String) paramHashMap.get("user_defined_data_view_count");
            int intFilSurveysCount = Integer.parseInt(strFilSurveysCount);

            for(int i=1;i<=intFilSurveysCount;i++)
            {
              if(paramHashMap.containsKey("user_defined_data_view__R"+i+"__C1"))
              {
                String filSurveyId = (String) paramHashMap.get("user_defined_data_view__R"+i+"__C1");
                if(filSurveyId != null)
                {
                  SurveyDAO.insertFilSurevyMissionGroup(con,strMissionGroupID,filSurveyId);
                }
              }
            }
            
            dataHashMap = new HashMap ();
            Vector missionGroupsVector = SurveyDAO.getAllMissionGroups(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , missionGroupsVector);
            Vector missionGroupStatusVector = SurveyDAO.getAllMissionGroupStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , missionGroupStatusVector);            
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case CREATE_NEW_MISSION_GROUP:
          try                                                                                                 
          {
                dataHashMap = new HashMap();
                Vector surveysVector = SurveyDAO.getFilAllActiveSurveys(con);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, surveysVector);
                
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case EDIT_MISSION_GROUP:
          try                                                                                                 
          {
                String strMissionGroupId = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_MISSION_GROUP_ID);
                MissionGroupModel missionGroupModel = SurveyDAO.getMissionGroupById(con,strMissionGroupId);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, missionGroupModel);  
                Vector vecFilSurveyMissionGroup = SurveyDAO.getFilSurevyMissionGroup(con,strMissionGroupId);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, vecFilSurveyMissionGroup);  
                
                dataHashMap = new HashMap();
                Vector surveysVector = SurveyDAO.getFilAllActiveSurveys(con);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, surveysVector);  
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case SAVE_SURVEY:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();

            Long lSurveyID = Utility.getSequenceNextVal(con, "SEQ_SRV_SURVEY");
            String strSurveyName = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_SURVEY_NAME);
            String strSurveyStatus = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_STATUS);
            String strSurveyType = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_SELECT_SURVEY_TYPE);
            String strSurveyDescription = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_TEXTAREA_SURVEY_DESCRIPTION);            

            SurveyModel surveyModel = SurveyDAO.getSurveyByName(con,strSurveyName);
            if(surveyModel == null)
            {
              SurveyDAO.insertSurvey(con,lSurveyID,strSurveyName,strSurveyType,strSurveyDescription,strSurveyStatus);
            }
            else
            {
              dataHashMap.put( InterfaceKey.HASHMAP_KEY_MESSAGE , "There is survey with the same name.");
            }
            Vector surveysVector = SurveyDAO.getAllSurveys(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , surveysVector);
            Vector surveyStatusVector = SurveyDAO.getAllSurveyStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , surveyStatusVector);            
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case VIEW_ALL_SURVEYS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            Vector surveysVector = SurveyDAO.getAllSurveys(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , surveysVector);
            Vector surveyStatusVector = SurveyDAO.getAllSurveyStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , surveyStatusVector);            
            Utility.logger.debug("wwwwwwwwwwwww");
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case EDIT_SURVEY:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            Vector surveyTypesVector = SurveyDAO.getAllSurveyTypes(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , surveyTypesVector);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ACTION , SurveyInterfaceKey.ACTION_EDIT_SURVEY);

            String strSurveyId = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID);
            Vector survey = SurveyDAO.getSurveyById(con,strSurveyId);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , survey);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case UPDATE_SURVEY:
          try                                                                                                 
          {
            String strSurveyID = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID);
            String strSurveyName = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_SURVEY_NAME);
            String strSurveyStatus = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_STATUS);
            String strSurveyType = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_SELECT_SURVEY_TYPE);
            String strSurveyDescription = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_TEXTAREA_SURVEY_DESCRIPTION);            
            SurveyDAO.updateSurvey(con,strSurveyID,strSurveyName,strSurveyType,strSurveyDescription,strSurveyStatus);

            dataHashMap = new HashMap ();
            Vector surveysVector = SurveyDAO.getAllSurveys(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , surveysVector);
            Vector surveyStatusVector = SurveyDAO.getAllSurveyStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , surveyStatusVector);  
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case UPDATE_SURVEYS_STATUS:
          try                                                                                                 
          {
            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              String keyValue = (String)paramHashMap.get(tempStatusString);
              //Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
              if(tempStatusString.startsWith(SurveyInterfaceKey.INPUT_SELECT_SURVEY_STATUS))
              {
                int strlength = SurveyInterfaceKey.INPUT_SELECT_SURVEY_STATUS.length() + 1;
                String strSurveyId = tempStatusString.substring(strlength, tempStatusString.length());
                SurveyDAO.updateSurveyStatus(con,strSurveyId,keyValue);
              } 
            }

            dataHashMap = new HashMap ();
            Vector surveysVector = SurveyDAO.getAllSurveys(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , surveysVector);
            Vector surveyStatusVector = SurveyDAO.getAllSurveyStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , surveyStatusVector);  
            
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case VIEW_SURVEY_GROUPS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strSurveyId = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID);
            Vector surveyGroups = SurveyDAO.getGroupsBySurveyId(con,strSurveyId);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , surveyGroups);
            Vector surveyDetails = SurveyDAO.getSurveyById(con,strSurveyId);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , surveyDetails);
            Vector groupStatusList = SurveyDAO.getAllGroupStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2 , groupStatusList);
            String groupType = SurveyDAO.getSurveyType(con , strSurveyId);
            dataHashMap.put(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_TYPE_ID, groupType);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case CREATE_NEW_GROUP:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strSurveyId = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID);
            dataHashMap.put( SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID, strSurveyId);
            String surveyTypeID = SurveyDAO.getSurveyType(con , strSurveyId);
            dataHashMap.put(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_TYPE_ID , surveyTypeID);
            String strSumGroupWeight = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SUM_OF_GROUP_WEIGHTS);
            dataHashMap.put( SurveyInterfaceKey.INPUT_HIDDEN_SUM_OF_GROUP_WEIGHTS, strSumGroupWeight);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ACTION, SurveyInterfaceKey.ACTION_CREATE_NEW_GROUP);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case SAVE_GROUP:
          try                                                                                                 
          {
            Long lGroupID = Utility.getSequenceNextVal(con, "SEQ_SRV_GROUP_ID");
            String strGroupName = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_GROUP_NAME);
            String strGroupWeight = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_GROUP_WEIGHT);
            String strGroupOrder = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_GROUP_ORDER);
            String strGroupReference = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_GROUP_REFERENCE);
            String strGroupDescription = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXTAREA_GROUP_DESCRIPTION);
            String newstrGroupWeight = strGroupWeight.replace('%',' ');
            dataHashMap = new HashMap ();
            String strSurveyId = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID);
            String strGroupStatus = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_STATUS_ID);
            String GroupCategoryID = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_TYPE_ID);
            SurveyDAO.insertGroup(con,lGroupID,strGroupName,strSurveyId,newstrGroupWeight,strGroupOrder,strGroupReference,strGroupDescription,strGroupStatus,GroupCategoryID);

            Vector surveyGroups = SurveyDAO.getGroupsBySurveyId(con,strSurveyId);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , surveyGroups);
            String surveyTypeID = SurveyDAO.getSurveyType(con , strSurveyId);
            dataHashMap.put(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_TYPE_ID , surveyTypeID);           
            Vector surveyDetails = SurveyDAO.getSurveyById(con,strSurveyId);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , surveyDetails);
            Vector groupStatusList = SurveyDAO.getAllGroupStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2 , groupStatusList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case EDIT_GROUP:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strGroupId = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID);
            Vector groupVec = SurveyDAO.getGroupsById(con,strGroupId);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION, groupVec);
            
            String strSurveyId = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID);
            dataHashMap.put( SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID, strSurveyId);
            String strSumGroupWeight = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SUM_OF_GROUP_WEIGHTS);
            dataHashMap.put( SurveyInterfaceKey.INPUT_HIDDEN_SUM_OF_GROUP_WEIGHTS, strSumGroupWeight);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ACTION, SurveyInterfaceKey.ACTION_EDIT_GROUP);
          } 
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case UPDATE_GROUP:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strGroupID = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID);
            String strGroupName = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_GROUP_NAME);
            String strGroupWeight = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_GROUP_WEIGHT);
            String strGroupOrder = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_GROUP_ORDER);
            String strGroupReference = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_GROUP_REFERENCE);
            String strGroupDescription = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXTAREA_GROUP_DESCRIPTION);
            String newstrGroupWeight = strGroupWeight.replace('%',' ');
            String strSurveyId = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID);
            String strGroupStatus = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_STATUS_ID);

            SurveyDAO.updateGroup(con,strGroupID,strGroupName,newstrGroupWeight,strGroupOrder,strGroupReference,strGroupDescription,strGroupStatus);

            Vector surveyGroups = SurveyDAO.getGroupsBySurveyId(con,strSurveyId);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , surveyGroups);
            Vector surveyDetails = SurveyDAO.getSurveyById(con,strSurveyId);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , surveyDetails);
            Vector groupStatusList = SurveyDAO.getAllGroupStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2 , groupStatusList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case UPDATE_GROUPS_STATUS:
          try                                                                                                 
          {
          String strSumGroupWeight = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SUM_OF_GROUP_WEIGHTS);
          int intSumGroupWeight = Integer.parseInt(strSumGroupWeight); 
          for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              String keyValue = (String)paramHashMap.get(tempStatusString);
              //Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
              if(tempStatusString.startsWith(SurveyInterfaceKey.INPUT_SELECT_GROUP_STATUS))
              {
                int strlength = SurveyInterfaceKey.INPUT_SELECT_GROUP_STATUS.length() + 1;
                String strGroupId = tempStatusString.substring(strlength, tempStatusString.length());
                //String strGroupPreviousStatus = tempStatusString.substring(strlength-1,strlength);
                //String keyGroupWeight = SurveyInterfaceKey.INPUT_HIDDEN_GROUP_WEIGHT+"_"+strGroupId;
                //String groupWeight = (String)paramHashMap.get(keyGroupWeight);
                //int intgroupWeight = Integer.parseInt(groupWeight);
                //if(strGroupPreviousStatus.compareTo("1")==0 && keyValue.compareTo("1")!=0)
                //{
                //  intSumGroupWeight = intSumGroupWeight - intgroupWeight;
                //}
                //else if(strGroupPreviousStatus.compareTo("1")!=0 && keyValue.compareTo("1")==0)
                //{
                //  intSumGroupWeight = intSumGroupWeight + intgroupWeight;  
                //}
                //Utility.logger.debug("Group Previous Status : "+strGroupPreviousStatus+" Group Next Status : "+keyValue);
                //Utility.logger.debug("Group Weight : "+groupWeight);
                //Utility.logger.debug(strGroupId);
                //if(intSumGroupWeight > 100)
                //{
                //  strSumGroupWeight = ""+intSumGroupWeight;
                //}
                //else
                //{
                  SurveyDAO.updateGroupStatus(con,strGroupId,keyValue);
                //}
              }
              else if(tempStatusString.startsWith(SurveyInterfaceKey.INPUT_TEXT_GROUP_ORDER))
              {
                int strlength = SurveyInterfaceKey.INPUT_TEXT_GROUP_ORDER.length() + 1;
                String strGroupId = tempStatusString.substring(strlength, tempStatusString.length());
                //Utility.logger.debug(strGroupId);
                SurveyDAO.updateGroupOrder(con,strGroupId,keyValue);                
              }
            }

            dataHashMap = new HashMap ();
            //if(intSumGroupWeight > 100)
            //{
            //dataHashMap.put( SurveyInterfaceKey.ERROR_SUM_OF_GROUP_WEIGHTS , strSumGroupWeight);
            //}
            String strSurveyId = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID);
            Vector surveyGroups = SurveyDAO.getGroupsBySurveyId(con,strSurveyId);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , surveyGroups);
            Vector surveyDetails = SurveyDAO.getSurveyById(con,strSurveyId);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , surveyDetails);
            Vector groupStatusList = SurveyDAO.getAllGroupStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2 , groupStatusList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
         break; 
        case VIEW_GROUP_QUESTIONS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strGroupId = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID);
            Vector groupQuestions = SurveyDAO.getQuestionsByGroupId(con,strGroupId);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , groupQuestions);
            Vector questionStatusList = SurveyDAO.getAllQuestionStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2 , questionStatusList);
            Vector groupDetails = SurveyDAO.getGroupsById(con,strGroupId);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , groupDetails);
            String GroupType = SurveyDAO.getGroupType(con , strGroupId);
            dataHashMap.put(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_TYPE_ID , GroupType);           
            
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
         break; 
        case CREATE_NEW_QUESTION:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strGroupId = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID);
            dataHashMap.put( SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID , strGroupId);
            Vector questionCategory = SurveyDAO.getAllQuestionCategories(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , questionCategory);
            Vector questionType = SurveyDAO.getAllQuestionTypes(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , questionType);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ACTION , SurveyInterfaceKey.ACTION_CREATE_NEW_QUESTION);
            String strSumQuestionWeight = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SUM_OF_QUESTION_WEIGHTS);
            dataHashMap.put( SurveyInterfaceKey.INPUT_HIDDEN_SUM_OF_QUESTION_WEIGHTS, strSumQuestionWeight);
            String groupType = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_TYPE_ID);
            Utility.logger.debug("eeeee"+groupType);
            dataHashMap.put(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_TYPE_ID , groupType);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
         break; 
         case SAVE_QUESTION:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            Long lQuestionID = Utility.getSequenceNextVal(con, "SEQ_SRV_QUESTION_ID");
            Long lGroupQuestionID = Utility.getSequenceNextVal(con, "SEQ_SRV_GROUP_QUESTION_ID");
            String strQuestion = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_TEXTAREA_QUESTION);
            String strQuestionWeight = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_QUESTION_WEIGHT);
            String strQuestionOrder = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_QUESTION_ORDER);
            String strQuestionMandatory = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_SELECT_QUESTION_MANDATORY);
            String strQuestionType = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_SELECT_QUESTION_TYPE);
            String strQuestionCategory = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_SELECT_QUESTION_CATEGORY);
            String strGroupId = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID);
            String strQuestionStatusId = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_QUESTION_STATUS_ID);
            String groupType = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_TYPE_ID);
            

            SurveyDAO.insertQuestion(con,lQuestionID,lGroupQuestionID,strGroupId,strQuestion,strQuestionWeight,strQuestionOrder,strQuestionMandatory,strQuestionType,strQuestionCategory,strQuestionStatusId);
            String strNoOfChoices = (String) paramHashMap.get("user_defined_data_view_count");
            
            int intNoOfChoices = Integer.parseInt(strNoOfChoices);
            
            for(int j=1; j<=intNoOfChoices; j++)
            {
                Long lQuestionChoiceID = Utility.getSequenceNextVal(con, "SEQ_SRV_QUESTION_CHOICE_ID");
                String strChoice = (String) paramHashMap.get("user_defined_data_view__R"+j+"__C1");
                String strChoiceValue = (String) paramHashMap.get("user_defined_data_view__R"+j+"__C2");
                SurveyDAO.insertQuestionChoice(con,lQuestionChoiceID,lQuestionID,strChoice,strChoiceValue,j);
            }
          
            
            Vector groupQuestions = SurveyDAO.getQuestionsByGroupId(con,strGroupId);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , groupQuestions);
            Vector questionStatusList = SurveyDAO.getAllQuestionStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2 , questionStatusList);
            Vector groupDetails = SurveyDAO.getGroupsById(con,strGroupId);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , groupDetails);          
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
         break; 
         case EDIT_QUESTION:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strGroupId = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID);
            dataHashMap.put( SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID , strGroupId);
            Vector questionCategory = SurveyDAO.getAllQuestionCategories(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , questionCategory);
            Vector questionType = SurveyDAO.getAllQuestionTypes(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , questionType);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ACTION , SurveyInterfaceKey.ACTION_UPDATE_QUESTION);

            String questionId = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_QUESTION_ID);
            Vector questionDetails = SurveyDAO.getQuestionsById(con,questionId);
            dataHashMap.put( SurveyInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_QUESTION_DETAILS , questionDetails);
            Vector questionChoices = SurveyDAO.getChoicesByQuestionId(con,questionId);
            dataHashMap.put( SurveyInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_QUESTION_CHOICES , questionChoices);
            String strSumQuestionWeight = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SUM_OF_QUESTION_WEIGHTS);
            dataHashMap.put( SurveyInterfaceKey.INPUT_HIDDEN_SUM_OF_QUESTION_WEIGHTS, strSumQuestionWeight);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
         break; 
         case UPDATE_QUESTION:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strQuestionID = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_QUESTION_ID);
            String strQuestion = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_TEXTAREA_QUESTION);
            String strQuestionWeight = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_QUESTION_WEIGHT);
            String strQuestionOrder = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_QUESTION_ORDER);
            String strQuestionMandatory = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_SELECT_QUESTION_MANDATORY);
            String strQuestionType = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_SELECT_QUESTION_TYPE);
            String strQuestionCategory = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_SELECT_QUESTION_CATEGORY);
            String strGroupId = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID);
            String strQuestionStatusId = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_QUESTION_STATUS_ID);

            SurveyDAO.updateQuestion(con,strQuestionID,strQuestion,strQuestionWeight,strQuestionOrder,strQuestionMandatory,strQuestionType,strQuestionCategory,strQuestionStatusId);
            SurveyDAO.deleteQuestionChoice(con,strQuestionID);
            String strNoOfChoices = (String) paramHashMap.get("user_defined_data_view_count");
            
            int intNoOfChoices = Integer.parseInt(strNoOfChoices);
            
            for(int j=1; j<=intNoOfChoices; j++)
            {
                Long lQuestionChoiceID = Utility.getSequenceNextVal(con, "SEQ_SRV_QUESTION_CHOICE_ID");
                String strChoice = (String) paramHashMap.get("user_defined_data_view__R"+j+"__C1");
                String strChoiceValue = (String) paramHashMap.get("user_defined_data_view__R"+j+"__C2");
                Long lQuestionID = new Long(strQuestionID);
                SurveyDAO.insertQuestionChoice(con,lQuestionChoiceID,lQuestionID,strChoice,strChoiceValue,j);
            }
          
            
            Vector groupQuestions = SurveyDAO.getQuestionsByGroupId(con,strGroupId);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , groupQuestions);
            Vector questionStatusList = SurveyDAO.getAllQuestionStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2 , questionStatusList);
            Vector groupDetails = SurveyDAO.getGroupsById(con,strGroupId);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , groupDetails);          
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
         break; 
         case UPDATE_QUESTION_STATUS:
          try                                                                                                 
          {
          String strSumQuestionWeight = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SUM_OF_QUESTION_WEIGHTS);          
          int intSumQuestionWeight = Integer.parseInt(strSumQuestionWeight);  
          for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              String keyValue = (String)paramHashMap.get(tempStatusString);
              //Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
              if(tempStatusString.startsWith(SurveyInterfaceKey.INPUT_SELECT_QUESTION_STATUS))
              {
                int strlength = SurveyInterfaceKey.INPUT_SELECT_QUESTION_STATUS.length() + 1;
                String strQuestionId = tempStatusString.substring(strlength, tempStatusString.length());
                //String strQuestionPreviousStatus = tempStatusString.substring(strlength-1,strlength);
                //String keyQuestionWeight = SurveyInterfaceKey.INPUT_HIDDEN_QUESTION_WEIGHT+"_"+strQuestionId;
                //String questionWeight = (String)paramHashMap.get(keyQuestionWeight);
                //int intQuestionWeight = Integer.parseInt(questionWeight);
                //if(strQuestionPreviousStatus.compareTo("1")==0 && keyValue.compareTo("1")!=0)
                //{
                //  intSumQuestionWeight = intSumQuestionWeight - intQuestionWeight;
                //}
                //else if(strQuestionPreviousStatus.compareTo("1")!=0 && keyValue.compareTo("1")==0)
                //{
                //  intSumQuestionWeight = intSumQuestionWeight + intQuestionWeight;  
                //}  
                //Utility.logger.debug(strGroupId);
                //if(intSumQuestionWeight > 100)
                //{
                //  strSumQuestionWeight = ""+intSumQuestionWeight;
                //}
                //else
                //{
                  SurveyDAO.updateQuestionStatus(con,strQuestionId,keyValue); 
                ///}
              }
              else if(tempStatusString.startsWith(SurveyInterfaceKey.INPUT_TEXT_QUESTION_ORDER))
              {
                int strlength = SurveyInterfaceKey.INPUT_TEXT_QUESTION_ORDER.length() + 1;
                String strQuestionId = tempStatusString.substring(strlength, tempStatusString.length());
                //Utility.logger.debug(strGroupId);
                SurveyDAO.updateQuestionOrder(con,strQuestionId,keyValue);
              }
            }

            dataHashMap = new HashMap ();
            //if(intSumQuestionWeight > 100)
            //{
            //dataHashMap.put( SurveyInterfaceKey.ERROR_SUM_OF_QUESTION_WEIGHTS , strSumQuestionWeight);
            //}
            String strGroupId = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID);
            Vector groupQuestions = SurveyDAO.getQuestionsByGroupId(con,strGroupId);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , groupQuestions);
            Vector questionStatusList = SurveyDAO.getAllQuestionStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2 , questionStatusList);
            Vector groupDetails = SurveyDAO.getGroupsById(con,strGroupId);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , groupDetails);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
         break;
         case FILLING_CREATE_NEW_SURVEY:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_USER_ID , strUserID);
            DCMDto dcmdto = DCMDao.getAllDCM(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , dcmdto);
            Vector surveyList = SurveyDAO.getAllActiveSurveys(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , surveyList);
            Vector surveyType = SurveyDAO.getAllSurveyTypes(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2 , surveyType);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
         break;
         case FILLING_SAVE_SURVEY:
                try
                {
                    dataHashMap = new HashMap();
                    String dcmID = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_SELECT_DCM_NAME);
                    String surveyId = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_SELECT_SURVEY);
                    String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                      
                    String surevyCreateDate = (String)paramHashMap.get("survey_create_date5");
                    Vector vecSurveyModel = SurveyDAO.getSurveyById(con, surveyId);
                    SurveyModel surveyModel = (SurveyModel)vecSurveyModel.get(0);
                    //String newsurevyCreateDate = "";
                    //if(surevyCreateDate.charAt(0) == '0')
                    //{
                    //    newsurevyCreateDate = surevyCreateDate.substring(1);
                    //    Utility.logger.debug(newsurevyCreateDate);
                    //}
                    String surveyName = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_SURVEY_NAME);
                    FilSurveyModel filSurevyModel= SurveyDAO.getFilSurveyByName(con,surveyName);
                    if(filSurevyModel == null)
                    {
                      //String surveyName = surveyModel.getSurveyName();
                      String surveyStatusId = surveyModel.getSurveyStatusId();
                      String surveyStatusName = surveyModel.getSurveyStatusName();
                      String surveyTypeId = surveyModel.getSurveyTypeId();
                      String surveyTypeName = surveyModel.getSurveyTypeName();
                      String surveyDescription = surveyModel.getSurveyDescription();
                      String surveyCategoryId = surveyModel.getSurveyCategoryId();
                      String surveyCategoryName = surveyModel.getSurveyCategoryName();
                      String surveyTypeStatusId = surveyModel.getSurveyTypeStatusId();
                      String surveyTypeStatusName = surveyModel.getSurveyTypeStatusName();
                      String surveyCategoryStatusId = surveyModel.getSurveyCategoryStatusId();
                      String surveyCategoryStatusName = surveyModel.getSurveyCategoryStatusName();
                      Long lfilSurveyID = Utility.getSequenceNextVal(con, "SEQ_SRV_FIL_SURVEY_ID");
                      SurveyDAO.insertFilSurvey(con, lfilSurveyID, surveyName, "1", surveyTypeId, surveyDescription, "GEN_DCM", dcmID, surevyCreateDate, "0", "0", surveyId, strUserID);
                      Vector listOfGroups = SurveyDAO.getActiveGroupsBySurveyId(con, surveyId);
                      for(int gr = 0; gr < listOfGroups.size(); gr++)
                      {
                          GroupModel groupModel = (GroupModel)listOfGroups.get(gr);
                          String groupId = groupModel.getGroupId();
                          String groupName = groupModel.getGroupName();
                          String groupWeight = groupModel.getGroupWeight();
                          String groupOrder = groupModel.getGroupOrder();
                          String groupReference = groupModel.getGroupReference();
                          String groupStatusId = groupModel.getGroupStatusId();
                          String groupStatusName = groupModel.getGroupStatusName();
                          String groupDescription = groupModel.getGroupDescription();
                          Long lfilGroupID = Utility.getSequenceNextVal(con, "SEQ_SRV_FIL_GROUP_ID");
                          SurveyDAO.insertFilGroup(con, lfilGroupID, groupName, lfilSurveyID, groupWeight, groupOrder, groupReference, groupDescription, "1", groupId, "0");
                          Vector listOfQuestions = SurveyDAO.getActiveQuestionsByGroupId(con, groupId);
                          for(int qu = 0; qu < listOfQuestions.size(); qu++)
                          {
                              QuestionModel questionModel = (QuestionModel)listOfQuestions.get(qu);
                              String questionId = questionModel.getQuestionId();
                              String question = questionModel.getQuestion();
                              String questionStatusId = questionModel.getQuestionStatusId();
                              String questionStatusName = questionModel.getQuestionStatusName();
                              String questionTypeId = questionModel.getQuestionTypeId();
                              String questionTypeName = questionModel.getQuestionTypeName();
                              String questionCategoryId = questionModel.getQuestionCategoryId();
                              String questionCategoryName = questionModel.getQuestionCategoryName();
                              String questionWeight = questionModel.getQuestionWeight();
                              String questionOrder = questionModel.getQuestionOrder();
                              String questionMandatory = questionModel.getQuestionMandatory();
                              Long lfilQuestionID = Utility.getSequenceNextVal(con, "SEQ_SRV_FIL_QUESTION_ID");
                              SurveyDAO.insertFilQuestion(con, lfilQuestionID, question, questionTypeId, questionCategoryId, lfilGroupID, questionWeight, "0", questionOrder, questionMandatory, questionId);
                          }

                      }
                    }
                    else
                    {
                      dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "There is Fil Survey with the same name.");
                    }
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    Vector surveysVector = SurveyDAO.getFilAllActiveSurveys(con);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, surveysVector);
                    Vector surveyStatusVector = SurveyDAO.getFilAllSurveyStatus(con);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, surveyStatusVector);
                }
                catch(Exception objExp)
                {
                    objExp.printStackTrace();
                }
         break;
         case FILLING_VIEW_ALL_SURVEYS:
            try
            {
                dataHashMap = new HashMap();
                String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                dataHashMap.put( InterfaceKey.HASHMAP_KEY_USER_ID , strUserID);
                Vector surveysVector = SurveyDAO.getFilAllActiveSurveys(con);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, surveysVector);
                Vector surveyStatusVector = SurveyDAO.getFilAllSurveyStatus(con);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, surveyStatusVector);
            }
            catch(Exception objExp)
            {
                objExp.printStackTrace();
            }
         break;
         case FILLING_VIEW_SURVEY_GROUPS:
            try
            {
                dataHashMap = new HashMap();
                String filSurveyId = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID);
                dataHashMap.put( SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID , filSurveyId);
                Vector vecfilSurvey = SurveyDAO.getFilSurveyById(con,filSurveyId);
                Vector vecFilGroup = SurveyDAO.getAllFilGroupsBySurveyId(con,filSurveyId);
                HashMap hashMapFilQuestion = new HashMap();
                HashMap hashMapQuestionChoices = new HashMap();
                for(int i=0;i<vecFilGroup.size();i++)
                {
                    FilGroupModel filGroupModel = (FilGroupModel)vecFilGroup.get(i);
                    String filGroupId = filGroupModel.getFilGroupId();
                    Vector vecFilQuestion = SurveyDAO.getAllFilQuestionsByGroupId(con,filGroupId);
                    hashMapFilQuestion.put(filGroupId,vecFilQuestion);
                    for(int j=0;j<vecFilQuestion.size();j++)
                    {
                      FilQuestionModel filQuestionModel = (FilQuestionModel)vecFilQuestion.get(j);
                      String questionId = filQuestionModel.getQuestionId();
                      Vector questionChoices = SurveyDAO.getChoicesByQuestionId(con,questionId);
                      hashMapQuestionChoices.put(questionId,questionChoices);
                    }
                }
                String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                dataHashMap.put( InterfaceKey.HASHMAP_KEY_USER_ID , strUserID);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecFilGroup);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,hashMapFilQuestion);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,vecfilSurvey);
                dataHashMap.put(SurveyInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_QUESTION_CHOICES,hashMapQuestionChoices);
                
            }
            catch(Exception objExp)
            {
                objExp.printStackTrace();
            }
         break;
         case FILLING_COMPLETE_FIL_SURVEY:
            try
            {
                dataHashMap = new HashMap();
                String filSurveyId = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID);
                dataHashMap.put( SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID , filSurveyId);
                SurveyDAO.updateFilSurveyComplete(con,filSurveyId,"1");
                Vector vecfilSurvey = SurveyDAO.getFilSurveyById(con,filSurveyId);
                Vector vecFilGroup = SurveyDAO.getAllFilGroupsBySurveyId(con,filSurveyId);
                HashMap hashMapFilQuestion = new HashMap();
                HashMap hashMapQuestionChoices = new HashMap();
                for(int i=0;i<vecFilGroup.size();i++)
                {
                    FilGroupModel filGroupModel = (FilGroupModel)vecFilGroup.get(i);
                    String filGroupId = filGroupModel.getFilGroupId();
                    Vector vecFilQuestion = SurveyDAO.getAllFilQuestionsByGroupId(con,filGroupId);
                    hashMapFilQuestion.put(filGroupId,vecFilQuestion);
                    for(int j=0;j<vecFilQuestion.size();j++)
                    {
                      FilQuestionModel filQuestionModel = (FilQuestionModel)vecFilQuestion.get(j);
                      String questionId = filQuestionModel.getQuestionId();
                      Vector questionChoices = SurveyDAO.getChoicesByQuestionId(con,questionId);
                      hashMapQuestionChoices.put(questionId,questionChoices);
                    }
                }
                String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                dataHashMap.put( InterfaceKey.HASHMAP_KEY_USER_ID , strUserID);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecFilGroup);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,hashMapFilQuestion);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,vecfilSurvey);
                dataHashMap.put(SurveyInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_QUESTION_CHOICES,hashMapQuestionChoices);
            }
            catch(Exception objExp)
            {
                objExp.printStackTrace();
            }
         break;
         case FILLING_UPDATE_SURVEY_STATUS:
            try
            {
                for(int j=0; j<paramHashMap.size(); j++)
                {
                  String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
                  String keyValue = (String)paramHashMap.get(tempStatusString);
                  //Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
                  if(tempStatusString.startsWith(SurveyInterfaceKey.INPUT_SELECT_SURVEY_STATUS))
                  {
                    int strlength = SurveyInterfaceKey.INPUT_SELECT_SURVEY_STATUS.length() + 1;
                    String strFilSurveyId = tempStatusString.substring(strlength, tempStatusString.length());
                    SurveyDAO.updateFilSurveyStatus(con,strFilSurveyId,keyValue);
                  } 
                }
                dataHashMap = new HashMap();
                String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                dataHashMap.put( InterfaceKey.HASHMAP_KEY_USER_ID , strUserID);
                Vector surveysVector = SurveyDAO.getFilAllActiveSurveys(con);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, surveysVector);
                Vector surveyStatusVector = SurveyDAO.getFilAllSurveyStatus(con);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, surveyStatusVector);
            }
            catch(Exception objExp)
            {
                objExp.printStackTrace();
            }
         break;
         case FILLING_UPDATE_QUESTION_ANSWERS:
            try
            {
                for(int j=0; j<paramHashMap.size(); j++)
                {
                  String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
                  String keyValue = (String)paramHashMap.get(tempStatusString);
                  //Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
                  if(tempStatusString.startsWith(SurveyInterfaceKey.INPUT_QUESTION_ANSWER))
                  {
                    int strlength = SurveyInterfaceKey.INPUT_QUESTION_ANSWER.length() + 1;
                    String strFilQuestionId = tempStatusString.substring(strlength, tempStatusString.length());
                    SurveyDAO.updateFilQuestionAnswer(con,strFilQuestionId,keyValue);
                  } 
                }
                dataHashMap = new HashMap();
                String filSurveyId = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID);
                Vector vecfilSurvey = SurveyDAO.getFilSurveyById(con,filSurveyId);
                Vector vecFilGroup = SurveyDAO.getAllFilGroupsBySurveyId(con,filSurveyId);
                HashMap hashMapFilQuestion = new HashMap();
                HashMap hashMapQuestionChoices = new HashMap();
                Vector vecGroupPercentageValue = new Vector();
                int totalFilGroupWeights = 0;
                float totalFilGroupPercentageValues = 0;
                float filSurveyValue = 0;
                for(int i=0;i<vecFilGroup.size();i++)
                {
                    FilGroupModel filGroupModel = (FilGroupModel)vecFilGroup.get(i);
                    String filGroupId = filGroupModel.getFilGroupId();
                    String strGroupWeight = filGroupModel.getFilGroupWeight();
                    int intGroupWeight = Integer.parseInt(strGroupWeight);
                    totalFilGroupWeights += intGroupWeight;
                    int intGroupValue = 0;
                    Vector vecFilQuestion = SurveyDAO.getAllFilQuestionsByGroupId(con,filGroupId);
                    hashMapFilQuestion.put(filGroupId,vecFilQuestion);
                    for(int j=0;j<vecFilQuestion.size();j++)
                    {
                      FilQuestionModel filQuestionModel = (FilQuestionModel)vecFilQuestion.get(j);
                      String questionId = filQuestionModel.getQuestionId();
                      String strFilQuestionTypeId = filQuestionModel.getFilQuestionTypeId();
                      String strFilQuestionAnswer = filQuestionModel.getFilQuestionAnswer();
                      int intFilQuestionAnswer = Integer.parseInt(strFilQuestionAnswer);
                      String strFilQuestionWeight = filQuestionModel.getFilQuestionWeight();
                      int intFilQuestionWeight = Integer.parseInt(strFilQuestionWeight);
                      Vector questionChoices = SurveyDAO.getChoicesByQuestionId(con,questionId);
                      if(strFilQuestionTypeId.compareTo("1")==0 && strFilQuestionAnswer.compareTo("0")!=0)
                      {
                        for(int k=0;k<questionChoices.size();k++)
                        {
                          ChoiceModel choiceModel = (ChoiceModel)questionChoices.get(k);
                          String choiceId = choiceModel.getChoiceId();
                          String choiceValue = choiceModel.getChoiceValue();
                          int intChoiceValue = Integer.parseInt(choiceValue);
                          if(choiceId.compareTo(strFilQuestionAnswer)==0)
                          {
                             if(intChoiceValue > intFilQuestionWeight)
                             {
                               intFilQuestionAnswer = intFilQuestionWeight;
                             }
                             else
                             {
                               intFilQuestionAnswer = intChoiceValue;
                             }
                          }
                        }
                      }
                      else if(strFilQuestionTypeId.compareTo("2")==0)
                      {
                        if(intFilQuestionAnswer > intFilQuestionWeight)
                        {
                          intFilQuestionAnswer = intFilQuestionWeight;
                        }
                      }
                      else if(strFilQuestionTypeId.compareTo("3")==0)
                      {
                        intFilQuestionAnswer = 0;
                      }
                      //Utility.logger.debug("question answer : " + intFilQuestionAnswer);    
                      intGroupValue += intFilQuestionAnswer;
                      hashMapQuestionChoices.put(questionId,questionChoices);
                    }
                    //update group value
                    SurveyDAO.updateFilGroupValue(con,filGroupId,intGroupValue);
                    filGroupModel.setFilGroupValue(intGroupValue+"");
                    float groupPercentageValue = intGroupValue*intGroupWeight;
                    groupPercentageValue = groupPercentageValue/100;
                    //Utility.logger.debug("group value : " + intGroupValue);    
                    //Utility.logger.debug("group weight : " + intGroupWeight);    
                    //Utility.logger.debug("group percentage : " + groupPercentageValue);   
                    totalFilGroupPercentageValues += groupPercentageValue;
                }
                //Utility.logger.debug("total group percentage : " + totalFilGroupPercentageValues);
                //Utility.logger.debug("total group weights : " + totalFilGroupWeights);
                if(totalFilGroupWeights != 0)
                {
                  filSurveyValue = totalFilGroupPercentageValues/totalFilGroupWeights*100 ;
                }
                SurveyDAO.updateFilSurveyValue(con,filSurveyId,filSurveyValue);
                
                String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                dataHashMap.put( InterfaceKey.HASHMAP_KEY_USER_ID , strUserID);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecFilGroup);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,hashMapFilQuestion);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,vecfilSurvey);
                dataHashMap.put(SurveyInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_QUESTION_CHOICES,hashMapQuestionChoices);
            }
            catch(Exception objExp)
            {
                objExp.printStackTrace();
            }
         break;
        case SRV_POS_SURVEY_GROUP:
        {
          dataHashMap = new HashMap();
          String surveyID = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID);
          Vector surveyGroups = SurveyDAO.getGroupsBySurveyId(con , surveyID);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, surveyGroups);
          Vector POSGroup = SurveyDAO.getPOSGroup(con,surveyID);
          dataHashMap.put(SurveyInterfaceKey.VECTOR_POS_GROUP,POSGroup);
          dataHashMap.put(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID,surveyID);
        }
        break;
        case SRV_POS_GROUP_QUESTION:
        {
          dataHashMap =  new HashMap();
          String groupID = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID);
          Vector posQuestionVec = SurveyDAO.getPOSQuestions(con);
          Vector groupQuestions = SurveyDAO.getPOSGroupQuestions(con,groupID);
          dataHashMap.put(SurveyInterfaceKey.VECTOR_POS_GROUP_QUESTION,groupQuestions);
          dataHashMap.put(SurveyInterfaceKey.VECTOR_POS_QUESTION,posQuestionVec);
          dataHashMap.put(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID , groupID);
          
          
        }
        break;
        case SRV_SAVE_GROUP_QUESTION:
        {
          dataHashMap = new HashMap();
          try{
          String groupID = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID);
          int totalWeights = 0;
          for(int j=0; j<paramHashMap.size(); j++)
            {
             String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
             String keyValue = (String)paramHashMap.get(tempStatusString);
             if(tempStatusString.startsWith(SurveyInterfaceKey.INPUT_HIDDEN_QUESTION_ID+"_"))
             {
               String QuestionID  =  (String)paramHashMap.get(tempStatusString);
               if(paramHashMap.containsKey(SurveyInterfaceKey.INPUT_TEXT_QUESTION_ORDER+"_"+QuestionID))
               {
                 String QuestionWeight = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_QUESTION_WEIGHT+"_"+QuestionID) ;
                 totalWeights += Integer.parseInt(QuestionWeight);
                 String QuestionOrder = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_QUESTION_ORDER+"_"+QuestionID) ;
                 String QuestionMandatory = "0";
                 if(paramHashMap.containsKey(SurveyInterfaceKey.INPUT_SELECT_QUESTION_MANDATORY+"_"+QuestionID))
                  QuestionMandatory = "1";
                  String flag = (String)paramHashMap.get("state_"+QuestionID);
                  if(totalWeights <= 100)        
                    {SurveyDAO.updatePOSGroupQuestion(con,groupID,QuestionID, QuestionWeight,QuestionOrder,QuestionMandatory, flag);}
                  else
                    {
                       String err_msg = "Total Group weights should be smaller that 100";
                       dataHashMap.put(SurveyInterfaceKey.ERROR_SUM_OF_QUESTION_WEIGHTS,err_msg);               
                    }
                    
               }
               else
               {
                 if(paramHashMap.containsKey("state_"+QuestionID))
                 {
                   String state = (String) paramHashMap.get("state_"+QuestionID);
                   if(state.equals("old"))
                   {
                     SurveyDAO.deletePOSQuestionFromGroup(con,QuestionID ,groupID);
                   }
                 }
               }
               
             }
              //Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
            }
          Vector posQuestionVec = SurveyDAO.getPOSQuestions(con);
          Vector groupQuestions = SurveyDAO.getPOSGroupQuestions(con,groupID);
          dataHashMap.put(SurveyInterfaceKey.VECTOR_POS_GROUP_QUESTION,groupQuestions);
          dataHashMap.put(SurveyInterfaceKey.VECTOR_POS_QUESTION,posQuestionVec);
          dataHashMap.put(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID , groupID);
        }
        catch(Exception ex)
        {
          ex.printStackTrace();
        }
        }
        break;
        case SRV_CREATE_NEW_POS_GROUP:
        {
            dataHashMap = new HashMap ();
            dataHashMap.put(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_TYPE_ID , "2");
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ACTION, SurveyInterfaceKey.ACTION_SRV_CREATE_NEW_POS_GROUP);          
        }
        break;
        case SRV_CREATE_NEW_POS_SURVEY:
        {
            dataHashMap = new HashMap ();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ACTION , SurveyInterfaceKey.ACTION_CREATE_NEW_SURVEY);                    
        }
        break;
        case SRV_SAVE_POS_SURVEY_GROUP:
        {
          dataHashMap = new HashMap();
          String surveyId = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID);
          Vector groups = SurveyDAO.getPOSGroup(con,surveyId)          ;
          dataHashMap.put(SurveyInterfaceKey.VECTOR_POS_GROUP , groups);

          for(int j=0; j<paramHashMap.size(); j++)
            {
             String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
             String keyValue = (String)paramHashMap.get(tempStatusString);
             if(tempStatusString.startsWith(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID+"_"))
             {
               String groupID  =  (String)paramHashMap.get(tempStatusString);
               if(paramHashMap.containsKey(SurveyInterfaceKey.INPUT_TEXT_GROUP_WEIGHT+"_"+groupID))
               {
                 String groupWeight = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_GROUP_WEIGHT+"_"+groupID) ;
                    SurveyDAO.updatePOSSurveyGroup(con,surveyId , groupID , groupWeight);
               }
               else
               {
                 if(paramHashMap.containsKey("state_"+groupID))
                 {
                   String state = (String) paramHashMap.get("state_"+groupID);
                   if(state.equals("old"))
                   {
                     SurveyDAO.deletePOSGroupFromSurvey(con,groupID);
                   }
                 }
               }
               
             }
              Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
            }

            Vector POSSurvey = SurveyDAO.getPOSSurvey(con);
            dataHashMap.put(SurveyInterfaceKey.VECTOR_POS_SURVEY,POSSurvey);

        }
        break;         
        case SRV_POS_QUESTIONS :
        {
          dataHashMap = new HashMap();
          Vector questions = SurveyDAO.getPOSQuestions(con);
          dataHashMap.put(SurveyInterfaceKey.VECTOR_POS_QUESTION,questions);
        }
        break;
        case SRV_POS_SURVEY :
        {
          dataHashMap = new HashMap();
          Vector POSSurvey = SurveyDAO.getPOSSurvey(con);
          dataHashMap.put(SurveyInterfaceKey.VECTOR_POS_SURVEY , POSSurvey);
        }
        break;
        case SRV_POS_GROUP :
        {
          dataHashMap = new HashMap();
          Vector POSGroup = SurveyDAO.getPOSGroup(con,"0");
          dataHashMap.put(SurveyInterfaceKey.VECTOR_POS_GROUP,POSGroup);
          
        }
        break;
        case SRV_CREATE_NEW_POS_QUESTION:
        {
            dataHashMap = new HashMap ();
            Vector questionCategory = SurveyDAO.getAllQuestionCategories(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , questionCategory);
            Vector questionType = SurveyDAO.getAllQuestionTypes(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , questionType);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ACTION , SurveyInterfaceKey.ACTION_SRV_CREATE_NEW_POS_QUESTION);
            dataHashMap.put(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_TYPE_ID , "2");          
        }
        break;  
        case SRV_SAVE_POS_QUESTION:
        {
            dataHashMap = new HashMap ();
            Long lQuestionID = Utility.getSequenceNextVal(con, "SEQ_SRV_QUESTION_ID");
            String strQuestion = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_TEXTAREA_QUESTION);
            String strQuestionType = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_SELECT_QUESTION_TYPE);
            String strQuestionCategory = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_SELECT_QUESTION_CATEGORY);
            String strQuestionStatusId = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_QUESTION_STATUS_ID);
            String groupType = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_TYPE_ID);
            
            SurveyDAO.insertPOSQuestion(con,lQuestionID,strQuestion,strQuestionType,strQuestionCategory,"1");
            String strNoOfChoices = (String) paramHashMap.get("user_defined_data_view_count");
            
            int intNoOfChoices = Integer.parseInt(strNoOfChoices);
            
            for(int j=1; j<=intNoOfChoices; j++)
            {
                Long lQuestionChoiceID = Utility.getSequenceNextVal(con, "SEQ_SRV_QUESTION_CHOICE_ID");
                String strChoice = (String) paramHashMap.get("user_defined_data_view__R"+j+"__C1");
                String strChoiceValue = (String) paramHashMap.get("user_defined_data_view__R"+j+"__C2");
                SurveyDAO.insertQuestionChoice(con,lQuestionChoiceID,lQuestionID,strChoice,strChoiceValue,j);
            }
          
            
            Vector questionStatusList = SurveyDAO.getAllQuestionStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2 , questionStatusList);
          Vector questions = SurveyDAO.getPOSQuestions(con);
          dataHashMap.put(SurveyInterfaceKey.VECTOR_POS_QUESTION,questions);
            
          
        }
        break;
        case SRV_SAVE_POS_GROUP:
        {
            Long lGroupID = Utility.getSequenceNextVal(con, "SEQ_SRV_GROUP_ID");
            String strGroupName = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_GROUP_NAME);
            String strGroupWeight = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_GROUP_WEIGHT);
            String strGroupOrder = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_GROUP_ORDER);
            String strGroupReference = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXT_GROUP_REFERENCE);
            String strGroupDescription = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_TEXTAREA_GROUP_DESCRIPTION);
            String newstrGroupWeight = strGroupWeight.replace('%',' ');
            dataHashMap = new HashMap ();
//            String strSurveyId = (String) paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID);
            String strGroupStatus = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_STATUS_ID);
            String GroupCategoryID = (String)paramHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_TYPE_ID);
            SurveyDAO.insertGroup(con,lGroupID,strGroupName,"",newstrGroupWeight,strGroupOrder,strGroupReference,strGroupDescription,strGroupStatus,GroupCategoryID);

            Vector GroupVec = SurveyDAO.getPOSGroup(con,"0");
            dataHashMap.put(SurveyInterfaceKey.VECTOR_POS_GROUP , GroupVec);
          
        }
        break;
        default:
          Utility.logger.debug ("Unknown action received: " + action );  
      }

       
    }
    catch(Exception objExp)
    {
      objExp.printStackTrace();
    }

    return dataHashMap;
  }
}