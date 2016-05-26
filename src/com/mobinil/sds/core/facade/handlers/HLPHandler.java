package com.mobinil.sds.core.facade.handlers;


import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.hlp.HLPInterfaceKey;
import com.mobinil.sds.web.interfaces.dcm.DCMInterfaceKey;
import com.mobinil.sds.web.interfaces.sa.*;

import com.mobinil.sds.core.system.sa.users.dao.*;
import com.mobinil.sds.core.system.hlp.casepkg.dao.*;
import com.mobinil.sds.core.system.hlp.casepkg.dto.*;
import com.mobinil.sds.core.system.hlp.casepkg.model.*;
import com.mobinil.sds.core.system.hlp.mission.dao.*;
import com.mobinil.sds.core.system.hlp.mission.model.*;  
import com.mobinil.sds.core.system.gn.dcm.dao.*;
import com.mobinil.sds.core.system.gn.dcm.dto.*;
import com.mobinil.sds.core.system.sfr.sheets.dao.*;
import com.mobinil.sds.core.system.sv.surveys.dao.*;
import com.mobinil.sds.core.system.sv.surveys.model.*;
import com.mobinil.sds.web.interfaces.sv.SurveyInterfaceKey;
import com.mobinil.sds.core.system.gn.dcm.model.DCMModel;
import com.mobinil.sds.core.system.dcm.user.dao.*;
import com.mobinil.sds.core.system.dcm.service.dao.*;

import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.core.system.sa.importdata.dao.*;
import java.util.*;

public class HLPHandler  
{
  static final int HLP_CREATE_NEW_CASE                                         = 1;
  static final int HLP_VIEW_CASES                                              = 2;
  static final int HLP_VIEW_CASE_DETAIL                                        = 4; 
  static final int HLP_CREATE_NEW_MISSION                                      = 5;
  static final int HLP_ADMIN_VIEW_MISSIONS                                     = 6;
  static final int HLP_VIEW_MISSIONS                                           = 7;
  static final int HLP_ASSIGN_MISSION_USERS                                    = 8;
  static final int HLP_EDIT_MISSION                                            = 9;
  static final int HLP_UPLOAD_MISSION_TARGET_USERS                             = 10;  
  static final int HLP_UPLOAD_MISSION_TARGET_USERS_PROCESS                     = 11;
  static final int HLP_VIEW_MISSION_DETAILS                                    = 12;
  static final int HLP_SAVE_NEW_CASE                                           = 13;
  static final int HLP_UPDATE_CASE_STATUS                                      = 14; 
  static final int HLP_SEARCH_CASE                                             = 15;
  static final int HLP_FORWARD_CASE                                            = 16; 
  static final int HLP_VIEW_CASE_HISTORY                                       = 17; 
  static final int HLP_SAVE_NEW_MISSION                                        = 18;
  static final int HLP_UPDATE_MISSION_DETAILS                                  = 19; 
  static final int HLP_SAVE_MISSION_USERS                                      = 20; 
  static final int HLP_UPDATE_MISSION_STATUS                                   = 21;
  static final int HLP_ADMIN_SEARCH_MISSION                                    = 22; 
  static final int HLP_SEARCH_MISSION                                          = 23; 
  static final int HLP_SELECT_MISSION_TARGET_USER                              = 24;
  static final int HLP_VIEW_TARGET_USER_SURVEY                                 = 25;
  static final int HLP_VIEW_MISSION_DETAILS_USER_LIST                          = 26; 
  static final int HLP_VIEW_MISSION_DETAILS_DCM_LIST                           = 27;
  static final int HLP_CASE_CATEGORY_MANAGEMENT                                = 28;
  static final int HLP_CASE_TYPE_MANAGEMENT                                    = 29;
  static final int HLP_CASE_WARNING_MANAGEMENT                                 = 30;
  static final int HLP_EDIT_CASE_CATEGORY                                      = 31; 
  static final int HLP_ADD_CASE_CATEGORY                                       = 32; 
  static final int HLP_EDIT_CASE_TYPE                                          = 33; 
  static final int HLP_ADD_CASE_TYPE                                           = 34;
  static final int HLP_EDIT_CASE_WARNING                                       = 35;
  static final int HLP_ADD_CASE_WARNING                                        = 36;
  static final int HLP_SAVE_CASE_CATEGORY                                      = 37;
  static final int HLP_SAVE_CASE_TYPE                                          = 38; 
  static final int HLP_SAVE_CASE_WARNING                                       = 39;
  static final int HLP_DELETE_CASE_CATEGORY                                    = 40; 
  static final int HLP_DELETE_CASE_TYPE                                        = 41;
  static final int HLP_DELETE_CASE_WARNING                                     = 42; 
  static final int HLP_CASE_TYPE_CATEGORY_MANAGEMENT                           = 43;
  static final int HLP_EDIT_CASE_TYPE_CATEGORY                                 = 44;
  static final int HLP_ADD_CASE_TYPE_CATEGORY                                  = 45;
  static final int HLP_SAVE_CASE_TYPE_CATEGORY                                 = 46;
  static final int HLP_DELETE_CASE_TYPE_CATEGORY                               = 47;
  static final int HLP_VIEW_CASES_USER_IN_CC                                   = 48;
  static final int HLP_SEARCH_CASE_USER_IN_CC                                  = 49;
  static final int HLP_UPDATE_CASE_DETAIL                                      = 50;
  static final int HLP_SAVE_UPDATED_CASE                                       = 51;   
  static final int HLP_CREATE_NEW_CASE_ENTER_POS                               = 52;   
  static final int HLP_ADD_CASE_INFO_ELEMENT                                   = 53;
  static final int HLP_SAVE_CASE_INFO_ELEMENT                                  = 54;
  static final int HLP_EDIT_CASE_INFO_ELEMENT                                  = 55;
  static final int HLP_UPDATE_CASE_INFO_ELEMENT                                = 56;
  static final int HLP_VIEW_CASE_DETAIL_USER_IN_CC                             = 57;
  static final int HLP_DELETE_DCM_FROM_MISSION                                 = 58;
  static final int HLP_VIEW_USER_MISSION_DCM_LIST                              = 59;   
  static final int HLP_GET_RANDOM_INCOMPLETED_DCM                              = 60;
  static final int SHOW_POS_CASES                                              = 61;
  static final int VIEW_POS_CASES                                              = 62;
  static final int HLP_ADMIN_VIEW_CASES                                        = 63;
  static final int HLP_ADMIN_SEARCH_CASE                                       = 64;
  static final int HLP_ADMIN_FORWARD_CASE                                      = 65;
  static final int HLP_ADMIN_VIEW_CASE_DETAIL                                  = 66;
  


  public static HashMap handle(String action, HashMap paramHashMap,java.sql.Connection con)
  {
    int actionType = 0;
    HashMap dataHashMap = null;
    //Connection  con = null;

    
    try 
    {
     //  con = Utility.getConnection();
       if (action.equals(HLPInterfaceKey.ACTION_HLP_CREATE_NEW_CASE))                                   
        actionType = HLP_CREATE_NEW_CASE;
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_VIEW_CASES))                                   
        actionType = HLP_VIEW_CASES;
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_VIEW_CASE_DETAIL))                                   
        actionType = HLP_VIEW_CASE_DETAIL;
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_CREATE_NEW_MISSION))                                   
        actionType = HLP_CREATE_NEW_MISSION;
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_ADMIN_VIEW_MISSIONS))                                   
        actionType = HLP_ADMIN_VIEW_MISSIONS;
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_VIEW_MISSIONS))                                   
        actionType = HLP_VIEW_MISSIONS;
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_ASSIGN_MISSION_USERS))                                   
        actionType = HLP_ASSIGN_MISSION_USERS;
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_EDIT_MISSION))                                   
        actionType = HLP_EDIT_MISSION;
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_UPLOAD_MISSION_TARGET_USERS))                                   
        actionType = HLP_UPLOAD_MISSION_TARGET_USERS;
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_UPLOAD_MISSION_TARGET_USERS_PROCESS))                                   
        actionType = HLP_UPLOAD_MISSION_TARGET_USERS_PROCESS; 
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_VIEW_MISSION_DETAILS))                                   
        actionType = HLP_VIEW_MISSION_DETAILS; 
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_SAVE_NEW_CASE))                                   
        actionType = HLP_SAVE_NEW_CASE; 
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_UPDATE_CASE_STATUS))                                   
        actionType = HLP_UPDATE_CASE_STATUS; 
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_SEARCH_CASE))                                   
        actionType = HLP_SEARCH_CASE; 
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_FORWARD_CASE))                                   
        actionType = HLP_FORWARD_CASE;  
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_VIEW_CASE_HISTORY))                                   
        actionType = HLP_VIEW_CASE_HISTORY;  
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_UPDATE_MISSION_DETAILS))                                   
        actionType = HLP_UPDATE_MISSION_DETAILS;  
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_SAVE_MISSION_USERS))                                   
        actionType = HLP_SAVE_MISSION_USERS;  
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_UPDATE_MISSION_STATUS))                                   
        actionType = HLP_UPDATE_MISSION_STATUS;  
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_ADMIN_SEARCH_MISSION))                                   
        actionType = HLP_ADMIN_SEARCH_MISSION;  
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_SEARCH_MISSION))                                   
        actionType = HLP_SEARCH_MISSION;  
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_SELECT_MISSION_TARGET_USER))                                   
        actionType = HLP_SELECT_MISSION_TARGET_USER;  
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_SAVE_NEW_MISSION))                                   
        actionType = HLP_SAVE_NEW_MISSION;  
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_VIEW_TARGET_USER_SURVEY))                                   
        actionType = HLP_VIEW_TARGET_USER_SURVEY;  
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_VIEW_MISSION_DETAILS_USER_LIST))                                   
        actionType = HLP_VIEW_MISSION_DETAILS_USER_LIST;  
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_VIEW_MISSION_DETAILS_DCM_LIST))                                   
        actionType = HLP_VIEW_MISSION_DETAILS_DCM_LIST;  
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_CASE_TYPE_CATEGORY_MANAGEMENT))                                   
        actionType = HLP_CASE_TYPE_CATEGORY_MANAGEMENT;   
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_CASE_CATEGORY_MANAGEMENT))                                   
        actionType = HLP_CASE_CATEGORY_MANAGEMENT; 
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_CASE_TYPE_MANAGEMENT))                                   
        actionType = HLP_CASE_TYPE_MANAGEMENT;    
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_CASE_WARNING_MANAGEMENT))                                   
        actionType = HLP_CASE_WARNING_MANAGEMENT;    
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_EDIT_CASE_TYPE_CATEGORY))                                   
        actionType = HLP_EDIT_CASE_TYPE_CATEGORY;     
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_ADD_CASE_TYPE_CATEGORY))                                   
        actionType = HLP_ADD_CASE_TYPE_CATEGORY;     
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_EDIT_CASE_CATEGORY))                                   
        actionType = HLP_EDIT_CASE_CATEGORY;     
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_ADD_CASE_CATEGORY))                                   
        actionType = HLP_ADD_CASE_CATEGORY;   
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_EDIT_CASE_TYPE))                                   
        actionType = HLP_EDIT_CASE_TYPE;     
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_ADD_CASE_TYPE))                                   
        actionType = HLP_ADD_CASE_TYPE;     
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_EDIT_CASE_WARNING))                                   
        actionType = HLP_EDIT_CASE_WARNING;     
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_ADD_CASE_WARNING))                                   
        actionType = HLP_ADD_CASE_WARNING;     
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_SAVE_CASE_TYPE_CATEGORY))                                   
        actionType = HLP_SAVE_CASE_TYPE_CATEGORY;      
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_SAVE_CASE_CATEGORY))                                   
        actionType = HLP_SAVE_CASE_CATEGORY;  
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_SAVE_CASE_TYPE))                                   
        actionType = HLP_SAVE_CASE_TYPE;      
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_SAVE_CASE_WARNING))                                   
        actionType = HLP_SAVE_CASE_WARNING;      
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_DELETE_CASE_TYPE_CATEGORY))                                   
        actionType = HLP_DELETE_CASE_TYPE_CATEGORY;  
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_DELETE_CASE_CATEGORY))                                   
        actionType = HLP_DELETE_CASE_CATEGORY;       
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_DELETE_CASE_TYPE))                                   
        actionType = HLP_DELETE_CASE_TYPE;       
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_DELETE_CASE_WARNING))                                   
        actionType = HLP_DELETE_CASE_WARNING;       
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_VIEW_CASES_USER_IN_CC))                                   
        actionType = HLP_VIEW_CASES_USER_IN_CC;       
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_SEARCH_CASE_USER_IN_CC))                                   
        actionType = HLP_SEARCH_CASE_USER_IN_CC;       
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_UPDATE_CASE_DETAIL))                                   
        actionType = HLP_UPDATE_CASE_DETAIL;       
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_SAVE_UPDATED_CASE))                                   
        actionType = HLP_SAVE_UPDATED_CASE;       
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_CREATE_NEW_CASE_ENTER_POS))                                   
        actionType = HLP_CREATE_NEW_CASE_ENTER_POS;       
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_ADD_CASE_INFO_ELEMENT))                                   
        actionType = HLP_ADD_CASE_INFO_ELEMENT;       
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_SAVE_CASE_INFO_ELEMENT))                                   
        actionType = HLP_SAVE_CASE_INFO_ELEMENT;       
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_EDIT_CASE_INFO_ELEMENT))                                   
        actionType = HLP_EDIT_CASE_INFO_ELEMENT;       
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_UPDATE_CASE_INFO_ELEMENT))                                   
        actionType = HLP_UPDATE_CASE_INFO_ELEMENT;       
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_VIEW_CASE_DETAIL_USER_IN_CC))                                   
        actionType = HLP_VIEW_CASE_DETAIL_USER_IN_CC;       
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_DELETE_DCM_FROM_MISSION))                                   
        actionType = HLP_DELETE_DCM_FROM_MISSION;       
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_VIEW_USER_MISSION_DCM_LIST))                                   
        actionType = HLP_VIEW_USER_MISSION_DCM_LIST;       
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_GET_RANDOM_INCOMPLETED_DCM))                                   
        actionType = HLP_GET_RANDOM_INCOMPLETED_DCM; 
       else if (action.equals(HLPInterfaceKey.ACTION_SHOW_POS_CASES))
        actionType = SHOW_POS_CASES;
       else if (action.equals(HLPInterfaceKey.ACTION_VIEW_POS_CASES))
        actionType = VIEW_POS_CASES;
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_ADMIN_VIEW_CASES))
        actionType = HLP_ADMIN_VIEW_CASES;
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_ADMIN_SEARCH_CASE))
        actionType = HLP_ADMIN_SEARCH_CASE;
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_ADMIN_FORWARD_CASE))
        actionType = HLP_ADMIN_FORWARD_CASE;
       else if (action.equals(HLPInterfaceKey.ACTION_HLP_ADMIN_VIEW_CASE_DETAIL))
        actionType = HLP_ADMIN_VIEW_CASE_DETAIL;
        
        
      switch (actionType)                                                                                     
      {
        case HLP_ADMIN_VIEW_CASES:
        try
        {
          dataHashMap = new HashMap ();
          String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
          Vector vecCaseList = new Vector();
          //Vector vecCaseList = CaseDAO.getAllOpenedCases(con);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecCaseList);
          CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseDTO);
        }
         catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case SHOW_POS_CASES:
          try
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,HLPInterfaceKey.ACTION_VIEW_POS_CASES);
          }
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 
        
        case HLP_CREATE_NEW_CASE:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strPosCode1 = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_DCM_CODE);
            //Utility.logger.debug("strposCode issssssssssssssssss " + strPosCode1);
            String stkNumber = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_STK_NUMBER);
            if (stkNumber.compareTo("")!=0)
            {
              String strPosCode = DCMDao.getDcmCode(con,stkNumber);
              if(strPosCode == null)
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"STK Number does not exist in the system.");
                strPosCode1 = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_DCM_CODE);
              }
             
              else if(strPosCode1.equals(strPosCode))
              {
                strPosCode1 = strPosCode;
              }
              
              else if((!strPosCode1.equals(strPosCode))&&(strPosCode1.compareTo("")!=0))
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"POS Code for the STK Number and POS Code are not the same.");
                strPosCode1 = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_DCM_CODE);
              }
              else
              {
              strPosCode1 = strPosCode;
              }
                           
            }
            if(strPosCode1.compareTo("")!=0)
            {
              DCMModel dcmModel = DCMDao.getDCMModel(con,strPosCode1);
              if(dcmModel == null)
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"POS Code does not exist in the system.");  
              }
              else
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmModel);
              }
            }
            
            
            CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseDTO);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_CREATE_NEW_CASE);
            dataHashMap.put(HLPInterfaceKey.PAGE_HEADER,"Open New Case");
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_CREATE_NEW_CASE_ENTER_POS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_CREATE_NEW_CASE);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_CASE_TYPE_CATEGORY_MANAGEMENT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector caseSuperTypeList = CaseDAO.getAllCaseSuperTypes(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseSuperTypeList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_DELETE_CASE_TYPE_CATEGORY:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strCaseSuperTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_SUPER_TYPE_ID);

            CaseDAO.deleteCaseSuperType(con,strCaseSuperTypeId);
            Vector caseSuperTypeList = CaseDAO.getAllCaseSuperTypes(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseSuperTypeList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_SAVE_CASE_TYPE_CATEGORY:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strCaseSuperTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_SUPER_TYPE_ID);
            String strCaseSuperTypeName = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_SUPER_TYPE_NAME);
            Utility.logger.debug(strCaseSuperTypeName);
            if(strCaseSuperTypeId.compareTo("")==0)
            {
              CaseDAO.insertCaseSuperType(con,strCaseSuperTypeName);
            }
            else
            {
              CaseDAO.updateCaseSuperType(con,strCaseSuperTypeId,strCaseSuperTypeName);
            }
            
            Vector caseSuperTypeList = CaseDAO.getAllCaseSuperTypes(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseSuperTypeList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_EDIT_CASE_TYPE_CATEGORY:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strCaseSuperTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_SUPER_TYPE_ID);
            
            CaseTypeModel caseSuperTypeModel = CaseDAO.getCaseSuperType(con,strCaseSuperTypeId);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseSuperTypeModel);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_ADD_CASE_TYPE_CATEGORY:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_CASE_CATEGORY_MANAGEMENT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector caseCategoryList = CaseDAO.getAllCaseCategories(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseCategoryList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_DELETE_CASE_CATEGORY:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strCaseCategoryId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_CATEGORY_ID);

            CaseDAO.deleteCaseCategory(con,strCaseCategoryId);
            Vector caseCategoryList = CaseDAO.getAllCaseCategories(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseCategoryList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_SAVE_CASE_CATEGORY:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strCaseCategoryId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_CATEGORY_ID);
            String strCaseCategoryName = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_CASE_CATEGORY_NAME);
            if(strCaseCategoryId.compareTo("")==0)
            {
              CaseDAO.insertCaseCategory(con,strCaseCategoryName);
            }
            else
            {
              CaseDAO.updateCaseCategory(con,strCaseCategoryId,strCaseCategoryName);
            }
            
            Vector caseCategoryList = CaseDAO.getAllCaseCategories(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseCategoryList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_EDIT_CASE_CATEGORY:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strCaseCategoryID = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_CATEGORY_ID);
            
            CaseCategoryModel  caseCategoryModel = CaseDAO.getCaseCategory(con,strCaseCategoryID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseCategoryModel);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_ADD_CASE_CATEGORY:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_CASE_TYPE_MANAGEMENT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector caseTypeList = CaseDAO.getAllCaseTypes(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseTypeList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_DELETE_CASE_TYPE:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String caseTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_TYPE_ID);
            CaseDAO.deleteCaseType(con,caseTypeId);
            
            Vector caseTypeList = CaseDAO.getAllCaseTypes(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseTypeList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_SAVE_CASE_TYPE:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String caseTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_TYPE_ID);
            String caseTypeName = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_CASE_TYPE_NAME);
            String caseSuperTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_SUPER_TYPE_ID);
            if(caseTypeId.compareTo("")==0)
            {
              //add
              CaseDAO.insertCaseType(con,caseTypeName,caseSuperTypeId);
            }
            else
            {
              //update
              CaseDAO.updateCaseType(con,caseTypeId,caseTypeName,caseSuperTypeId);
            }
            Vector caseTypeList = CaseDAO.getAllCaseTypes(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseTypeList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_EDIT_CASE_TYPE:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String caseTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_TYPE_ID);
            CaseTypeModel caseTypeModel = CaseDAO.getCaseType(con,caseTypeId);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseTypeModel);
            Vector caseSuperTypeList = CaseDAO.getAllCaseSuperTypes(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseSuperTypeList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_ADD_CASE_TYPE:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector caseSuperTypeList = CaseDAO.getAllCaseSuperTypes(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseSuperTypeList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_CASE_WARNING_MANAGEMENT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector caseStatusWarningList = CaseDAO.getAllCaseStatusWarnings(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseStatusWarningList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_DELETE_CASE_WARNING:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String caseWarningId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_STATUS_WARNING_ID);
            CaseDAO.deleteCaseStatusWarning(con,caseWarningId);
            
            Vector caseStatusWarningList = CaseDAO.getAllCaseStatusWarnings(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseStatusWarningList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_SAVE_CASE_WARNING:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String caseWarningId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_STATUS_WARNING_ID);
            String caseWarningName = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_STATUS_WARNING_NAME);
            String caseStatusId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_STATUS_ID);

            if(caseWarningId.compareTo("")==0)
            {
              CaseDAO.insertCaseStatusWarning(con,caseWarningName,caseStatusId);
            }
            else
            {
              CaseDAO.updateCaseStatusWarning(con,caseWarningId,caseWarningName,caseStatusId);
            }
            
            Vector caseStatusWarningList = CaseDAO.getAllCaseStatusWarnings(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseStatusWarningList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_EDIT_CASE_WARNING:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String caseWarningId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_STATUS_WARNING_ID);
            
            CaseStatusModel caseStatusWarningModel = CaseDAO.getCaseStatusWarning(con,caseWarningId);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseStatusWarningModel);
            Vector caseStatusList = CaseDAO.getAllCaseStatus(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseStatusList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_ADD_CASE_WARNING:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector caseStatusList = CaseDAO.getAllCaseStatus(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseStatusList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_SAVE_NEW_CASE:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strReceiverId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_RECEIVER_ID);
            String strCCReceiverIds = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CC_RECEIVER_ID);
            System.out.println("The CC Receiver ID isssssssssss " + strCCReceiverIds);
            String strCaseTitle = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_CASE_TITLE);
            String strCaseDesc = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXTAREA_CASE_DESCRIPTION);
            String strCaseTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_TYPE_ID);
            String strCaseCategoryId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_CATEGORY_ID);
            String strCasePriorityId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_PRIORITY_ID);
            String strUserComment = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXTAREA_CASE_RECEIVER_COMMENT);

            String strDcmCode = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_DCM_CODE);
            String strDialNumber = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_DIAL_NUMBER);
            if(strDialNumber.compareTo("")==0)strDialNumber = "null";
            String strDialNumberType = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_DIAL_NUMBER_TYPE_ID);
            if(strDialNumberType.compareTo("")==0)strDialNumberType = "null";
            String strCaseDirection = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_CASE_DIRECTION_ID);
            
            Long lCaseId = CaseDAO.insertCase(con,strUserID,strReceiverId,strCaseTitle,strCaseDesc,strCaseTypeId,strCaseCategoryId,strCasePriorityId,strUserComment,strCCReceiverIds,strDcmCode,strDialNumber,strDialNumberType,strCaseDirection);
            
            CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseDTO);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_CREATE_NEW_CASE);
            dataHashMap.put(HLPInterfaceKey.PAGE_HEADER,"Open New Case");

            if(paramHashMap.containsKey(DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID))
            {
              String actualVisitId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID);
              String functionId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_FUNCTION_ID);

              if(functionId.compareTo(DCMInterfaceKey.CONST_DCM_PREDEFINED_FUNCTION_CREATE_NEW_CASE)==0)
              {
                DCMUserDAO.insertDcmActualVisitDetail(con,actualVisitId,functionId,lCaseId+"");
              }
              else if(functionId.compareTo(DCMInterfaceKey.CONST_DCM_PREDEFINED_FUNCTION_SERVICE_REQUEST)==0)
              {
                String serviceId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID);
                Long serviceRequestId = ServiceDAO.insertDcmServiceRequest(con,lCaseId+"",strDcmCode,serviceId);
                DCMUserDAO.insertDcmActualVisitDetail(con,actualVisitId,functionId,serviceRequestId+"");
              }
              dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID,actualVisitId);

              String strPosCode = strDcmCode;
              if(strPosCode.compareTo("")!=0)
              {
                DCMModel dcmModel = DCMDao.getDCMModel(con,strPosCode);
                if(dcmModel == null)
                {
                  dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"POS Code does not exist in the system.");  
                }
                else
                {
                  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmModel);
                }
              }
            }
            
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Case created successfully.");
          }                                                                                                   
          catch(Exception objExp)                                                                             
          { 
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Error in creating case.");
            objExp.printStackTrace();
          }
        break; 
        
        case VIEW_POS_CASES:
        try
        {
          dataHashMap = new HashMap ();
          String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
          String strPosCode = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_DCM_CODE);
          dataHashMap.put(HLPInterfaceKey.INPUT_TEXT_DCM_CODE,strPosCode);
          Vector casesList = CaseDAO.getAllPosCases(con,strPosCode);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,casesList);
        }
        catch(Exception objExp)                                                                             
        {                                     
          objExp.printStackTrace();
        }
        break;
        
        case HLP_VIEW_CASES:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            //Vector casesList = CaseDAO.getOpenedCasesByReceiverId(con,strUserID);
            Vector casesList = new Vector();
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,casesList);

            //Vector casesUserInCC = CaseDAO.getCasesDetailByCCReceiverId(con,strUserID);
            //dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,casesUserInCC);
            
            CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseDTO);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_VIEW_CASES_USER_IN_CC:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            //Vector casesList = CaseDAO.getCasesByReceiverId(con,strUserID);
            //dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,casesList);

            //Vector casesUserInCC = CaseDAO.getCasesDetailByCCReceiverId(con,strUserID);
            Vector casesUserInCC = new Vector();
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,casesUserInCC);
            
            CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseDTO);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_ADMIN_FORWARD_CASE:
        try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strCaseId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_ID);
            String strCaseStatusId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_STATUS_ID);
            String strStatusWarningId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_STATUS_WARNING_ID);
            String strReceiverId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_RECEIVER_ID);
            String strCCReceiverIds = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CC_RECEIVER_ID);
            String strCaseTitle = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_CASE_TITLE);
            String strCaseDesc = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXTAREA_CASE_DESCRIPTION);
            String strCaseTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_TYPE_ID);
            String strCaseCategoryId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_CATEGORY_ID);
            String strCasePriorityId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_PRIORITY_ID);
            String strUserComment = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXTAREA_CASE_RECEIVER_COMMENT);

            String strDcmCode = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_DCM_CODE);
            String strDialNumber = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_DIAL_NUMBER);
            if(strDialNumber.compareTo("")==0)strDialNumber = "null";
            String strDialNumberType = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_DIAL_NUMBER_TYPE_ID);
            if(strDialNumberType.compareTo("")==0)strDialNumberType = "null";
            String strCaseDirection = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_CASE_DIRECTION_ID);
            
            CaseDAO.insertCaseDetail(con,strCaseId,strUserID,strReceiverId,strCaseTitle,strCaseDesc,strCaseTypeId,strCaseCategoryId,strCasePriorityId,strUserComment,strCaseStatusId,strStatusWarningId,strCCReceiverIds,strDcmCode,strDialNumber,strDialNumberType,strCaseDirection);
                            
            Vector casesList = CaseDAO.getOpenedCasesByReceiverId(con,strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,casesList);

            //Vector casesUserInCC = CaseDAO.getCasesDetailByCCReceiverId(con,strUserID);
            //dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,casesUserInCC);
            
            CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseDTO);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        
        case HLP_FORWARD_CASE:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strCaseId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_ID);
            String strCaseStatusId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_STATUS_ID);
            String strStatusWarningId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_STATUS_WARNING_ID);
            String strReceiverId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_RECEIVER_ID);
            String strCCReceiverIds = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CC_RECEIVER_ID);
            String strCaseTitle = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_CASE_TITLE);
            String strCaseDesc = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXTAREA_CASE_DESCRIPTION);
            String strCaseTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_TYPE_ID);
            String strCaseCategoryId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_CATEGORY_ID);
            String strCasePriorityId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_PRIORITY_ID);
            String strUserComment = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXTAREA_CASE_RECEIVER_COMMENT);

            String strDcmCode = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_DCM_CODE);
            String strDialNumber = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_DIAL_NUMBER);
            if(strDialNumber.compareTo("")==0)strDialNumber = "null";
            String strDialNumberType = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_DIAL_NUMBER_TYPE_ID);
            if(strDialNumberType.compareTo("")==0)strDialNumberType = "null";
            String strCaseDirection = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_CASE_DIRECTION_ID);
            
            CaseDAO.insertCaseDetail(con,strCaseId,strUserID,strReceiverId,strCaseTitle,strCaseDesc,strCaseTypeId,strCaseCategoryId,strCasePriorityId,strUserComment,strCaseStatusId,strStatusWarningId,strCCReceiverIds,strDcmCode,strDialNumber,strDialNumberType,strCaseDirection);
                            
            Vector casesList = CaseDAO.getOpenedCasesByReceiverId(con,strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,casesList);

            //Vector casesUserInCC = CaseDAO.getCasesDetailByCCReceiverId(con,strUserID);
            //dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,casesUserInCC);
            
            CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseDTO);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_SAVE_UPDATED_CASE:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strCaseId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_ID);
            String strCaseStatusId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_STATUS_ID);
            String strStatusWarningId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_STATUS_WARNING_ID);
            String strCaseTitle = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_CASE_TITLE);
            String strCaseDesc = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXTAREA_CASE_DESCRIPTION);
            String strCaseTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_TYPE_ID);
            String strCaseCategoryId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_CATEGORY_ID);
            String strCasePriorityId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_PRIORITY_ID);
            String strUserComment = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXTAREA_CASE_RECEIVER_COMMENT);

            String strDcmCode = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_DCM_CODE);
            String strDialNumber = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_DIAL_NUMBER);
            if(strDialNumber.compareTo("")==0)strDialNumber = "null";
            String strDialNumberType = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_DIAL_NUMBER_TYPE_ID);
            if(strDialNumberType.compareTo("")==0)strDialNumberType = "null";
            String strCaseDirection = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_CASE_DIRECTION_ID);
            
            CaseDAO.updateCase(con,strCaseId,strUserID,strCaseTitle,strCaseDesc,strCaseTypeId,strCaseCategoryId,strCasePriorityId,strUserComment,strDialNumber,strDialNumberType,strCaseDirection,strCaseStatusId,strStatusWarningId);
            String strCCReceiverIds = CaseDAO.getCaseCCReceiver(con,strCaseId);
            Utility.logger.debug("The id issssssssssssssss " + strCCReceiverIds);
            CaseDAO.insertCaseDetail(con,strCaseId,strUserID,strUserID,strCaseTitle,strCaseDesc,strCaseTypeId,strCaseCategoryId,strCasePriorityId,strUserComment,strCaseStatusId,strStatusWarningId,strCCReceiverIds,strDcmCode,strDialNumber,strDialNumberType,strCaseDirection);
                            
            Vector casesList = CaseDAO.getOpenedCasesByReceiverId(con,strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,casesList);

            //Vector casesUserInCC = CaseDAO.getCasesDetailByCCReceiverId(con,strUserID);
            //dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,casesUserInCC);
            
            CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseDTO);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_ADMIN_SEARCH_CASE:
        try
        {
          dataHashMap = new HashMap ();
          String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
          String strSearchCaseTitle = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE);
          String strSearchCaseTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_TYPE_ID);
          String strSearchCaseCategoryId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_CATEGORY_ID);
          String strSearchCaseSenderName = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDER_NAME);
          String strSearchCaseSendedFrom = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_FROM);
          String strSearchCaseSendedTo = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_TO);

          dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE,strSearchCaseTitle);
          dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_TYPE_ID,strSearchCaseTypeId);
          dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_CATEGORY_ID,strSearchCaseCategoryId);
          dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDER_NAME,strSearchCaseSenderName);
          dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_FROM,strSearchCaseSendedFrom);
          dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_TO,strSearchCaseSendedTo);

          Vector vecCaseList = CaseDAO.getAllOpenedCasesByFilter(con,strSearchCaseTitle,strSearchCaseTypeId,strSearchCaseCategoryId,strSearchCaseSenderName,strSearchCaseSendedFrom,strSearchCaseSendedTo);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecCaseList);
          
          CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseDTO);
          dataHashMap.put(HLPInterfaceKey.HLP_INPUT_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_ADMIN_VIEW_CASE_DETAIL);

        }
        catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        
        case HLP_SEARCH_CASE:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strSearchCaseTitle = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE);
            String strSearchCaseTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_TYPE_ID);
            String strSearchCaseCategoryId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_CATEGORY_ID);
            String strSearchCaseStatusId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_STATUS_ID);
            String strSearchCaseSenderName = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDER_NAME);
            String strSearchCaseSendedFrom = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_FROM);
            String strSearchCaseSendedTo = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_TO);

            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE,strSearchCaseTitle);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_TYPE_ID,strSearchCaseTypeId);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_CATEGORY_ID,strSearchCaseCategoryId);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_STATUS_ID,strSearchCaseStatusId);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDER_NAME,strSearchCaseSenderName);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_FROM,strSearchCaseSendedFrom);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_TO,strSearchCaseSendedTo);
            
            Vector casesList = CaseDAO.getCasesByFilter(con,strUserID,strSearchCaseTitle,strSearchCaseTypeId,strSearchCaseCategoryId,strSearchCaseStatusId,strSearchCaseSenderName,strSearchCaseSendedFrom,strSearchCaseSendedTo);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,casesList);

            Vector casesUserInCC = CaseDAO.getCasesDetailByCCReceiverId(con,strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,casesUserInCC);
            
            CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseDTO);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_SEARCH_CASE_USER_IN_CC:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strSearchCaseTitle = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE);
            String strSearchCaseTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_TYPE_ID);
            String strSearchCaseCategoryId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_CATEGORY_ID);
            String strSearchCaseStatusId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_STATUS_ID);
            String strSearchCaseSenderName = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDER_NAME);
            String strSearchCaseSendedFrom = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_FROM);
            String strSearchCaseSendedTo = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_TO);

            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE,strSearchCaseTitle);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_TYPE_ID,strSearchCaseTypeId);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_CATEGORY_ID,strSearchCaseCategoryId);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_STATUS_ID,strSearchCaseStatusId);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDER_NAME,strSearchCaseSenderName);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_FROM,strSearchCaseSendedFrom);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_TO,strSearchCaseSendedTo);
            
            //Vector casesList = CaseDAO.getCasesByFilter(con,strUserID,strSearchCaseTitle,strSearchCaseTypeId,strSearchCaseCategoryId,strSearchCaseStatusId,strSearchCaseSenderName,strSearchCaseSendedFrom,strSearchCaseSendedTo);
            //dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,casesList);

            Vector casesUserInCC = CaseDAO.getCasesDetailByCCReceiverIdByFilter(con,strUserID,strSearchCaseTitle,strSearchCaseTypeId,strSearchCaseCategoryId,strSearchCaseStatusId,strSearchCaseSenderName,strSearchCaseSendedFrom,strSearchCaseSendedTo);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,casesUserInCC);
            
            CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseDTO);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_UPDATE_CASE_STATUS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              String keyValue = (String)paramHashMap.get(tempStatusString);
              //Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
              if(tempStatusString.startsWith(HLPInterfaceKey.INPUT_HIDDEN_CASE_TITLE))
              {
                int strlength = HLPInterfaceKey.INPUT_HIDDEN_CASE_TITLE.length() + 1;
                String strCaseId = tempStatusString.substring(strlength, tempStatusString.length());
                String strNewStatusId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_STATUS_ID+"_"+strCaseId);
                String strOldStatusId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_STATUS_ID+"_"+strCaseId);
                String strStatusWarningId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_STATUS_WARNING_ID+"_"+strCaseId);
                String strOldStatusWarningId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_STATUS_WARNING_ID+"_"+strCaseId);
                if(strNewStatusId.compareTo(strOldStatusId) !=0 || strStatusWarningId.compareTo(strOldStatusWarningId)!=0)
                {
                  String strCaseTitle = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_TITLE+"_"+strCaseId);
                  String strCaseDesc = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_DESCRIPTION+"_"+strCaseId);
                  String strCaseTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_TYPE_ID+"_"+strCaseId);
                  String strCaseCategoryId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_CATEGORY_ID+"_"+strCaseId);
                  String strCasePriorityId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_PRIORITY_ID+"_"+strCaseId);
                  String strReceiverId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_RECEIVER_ID+"_"+strCaseId);

                  String strDcmCode = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_DCM_CODE+"_"+strCaseId);
                  String strDialNumber = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_DIAL_NUMBER+"_"+strCaseId);
                  if(strDialNumber.compareTo("")==0)strDialNumber = "null";
                  String strDialNumberType = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_DIAL_NUMBER_TYPE_ID+"_"+strCaseId);
                  if(strDialNumberType.compareTo("")==0)strDialNumberType = "null";
                  String strCaseDirection = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_CASE_DIRECTION_ID+"_"+strCaseId);
            
                  
                  CaseDAO.insertCaseDetail(con,strCaseId,strUserID,strReceiverId,strCaseTitle,strCaseDesc,strCaseTypeId,strCaseCategoryId,strCasePriorityId,"",strNewStatusId,strStatusWarningId,"",strDcmCode,strDialNumber,strDialNumberType,strCaseDirection);
                  CaseDAO.updateCaseStatus(con,strCaseId,strNewStatusId,strStatusWarningId);
                }
              }
            }

            String strSearchCaseTitle = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE);
            String strSearchCaseTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_TYPE_ID);
            String strSearchCaseCategoryId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_CATEGORY_ID);
            String strSearchCaseStatusId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_STATUS_ID);
            String strSearchCaseSenderName = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDER_NAME);
            String strSearchCaseSendedFrom = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_FROM);
            String strSearchCaseSendedTo = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_TO);

            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE,strSearchCaseTitle);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_TYPE_ID,strSearchCaseTypeId);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_CATEGORY_ID,strSearchCaseCategoryId);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_STATUS_ID,strSearchCaseStatusId);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDER_NAME,strSearchCaseSenderName);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_FROM,strSearchCaseSendedFrom);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_TO,strSearchCaseSendedTo);
            
            Vector casesList = CaseDAO.getCasesByFilter(con,strUserID,strSearchCaseTitle,strSearchCaseTypeId,strSearchCaseCategoryId,strSearchCaseStatusId,strSearchCaseSenderName,strSearchCaseSendedFrom,strSearchCaseSendedTo);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,casesList);

            //Vector casesUserInCC = CaseDAO.getCasesDetailByCCReceiverId(con,strUserID);
            //dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,casesUserInCC);
            
            CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseDTO);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        
        case HLP_ADMIN_VIEW_CASE_DETAIL:
        try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strCaseId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_ID);

            CaseModel caseModel = CaseDAO.getCaseById(con,strCaseId);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseModel);
            String strPosCode = caseModel.getPosCode();

            if(strPosCode != null)
            {
              DCMModel dcmModel = DCMDao.getDCMModel(con,strPosCode);
              if(dcmModel != null)
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmModel);
              }
            }
            
            CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseDTO);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_VIEW_CASE_DETAIL);
            dataHashMap.put(HLPInterfaceKey.PAGE_HEADER,"Case Detail");
            dataHashMap.put(HLPInterfaceKey.HLP_INPUT_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_ADMIN_VIEW_CASE_DETAIL);
            Utility.logger.debug("ACTIONssdsd:  "+action);
            
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 
        
        case HLP_VIEW_CASE_DETAIL:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strCaseId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_ID);

            CaseModel caseModel = CaseDAO.getCaseById(con,strCaseId);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseModel);
            String strPosCode = caseModel.getPosCode();

            if(strPosCode != null)
            {
              DCMModel dcmModel = DCMDao.getDCMModel(con,strPosCode);
              if(dcmModel != null)
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmModel);
              }
            }
            
            CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseDTO);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_VIEW_CASE_DETAIL);
            dataHashMap.put(HLPInterfaceKey.PAGE_HEADER,"Case Detail");
            dataHashMap.put(HLPInterfaceKey.HLP_INPUT_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_VIEW_CASE_DETAIL);
            Utility.logger.debug("ACTIONAAAAA:  "+action); 
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_UPDATE_CASE_DETAIL:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strCaseId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_ID);

            CaseModel caseModel = CaseDAO.getCaseById(con,strCaseId);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseModel);

            String strPosCode = caseModel.getPosCode();
            if(strPosCode != null)
            {
              DCMModel dcmModel = DCMDao.getDCMModel(con,strPosCode);
              if(dcmModel != null)
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmModel);
              }
            }
            
            CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
            Vector caseInfoElementList = CaseDAO.getAllCaseInfoElements(con,strCaseId);
            caseDTO.setCaseInfoElementList(caseInfoElementList);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseDTO);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_UPDATE_CASE_DETAIL);
            dataHashMap.put(HLPInterfaceKey.PAGE_HEADER,"Case Detail");
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_VIEW_CASE_DETAIL_USER_IN_CC:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strCaseId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_ID);

            CaseModel caseModel = CaseDAO.getCaseById(con,strCaseId);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseModel);

            String strPosCode = caseModel.getPosCode();
            if(strPosCode != null)
            {
              DCMModel dcmModel = DCMDao.getDCMModel(con,strPosCode);
              if(dcmModel != null)
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmModel);
              }
            }
            
            CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
            Vector caseInfoElementList = CaseDAO.getAllCaseInfoElements(con,strCaseId);
            caseDTO.setCaseInfoElementList(caseInfoElementList);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseDTO);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_VIEW_CASE_DETAIL_USER_IN_CC);
            dataHashMap.put(HLPInterfaceKey.PAGE_HEADER,"Case Detail");
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_SAVE_CASE_INFO_ELEMENT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strCaseId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_ID);

            String caseInfoElementTitle = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_CASE_INFO_ELEMENT_TITLE);
            String caseInfoElementDate = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_CASE_INFO_ELEMENT_DATE);
            String caseInfoElementDescription = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXTAREA_CASE_INFO_ELEMENT_DESCRIPTION);
            String caseInfoElementTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_CASE_INFO_ELEMENT_TYPE_ID);
            String contactName = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_CONTACT_NAME);
            String contactInfo = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXTAREA_CONTACT_INFORMATION);

            CaseDAO.insertCaseInfoElement(con,strUserID,strCaseId,caseInfoElementTitle,caseInfoElementDate,caseInfoElementDescription,caseInfoElementTypeId,contactName,contactInfo);

            CaseModel caseModel = CaseDAO.getCaseById(con,strCaseId);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseModel);

            String strPosCode = caseModel.getPosCode();
            if(strPosCode != null)
            {
              DCMModel dcmModel = DCMDao.getDCMModel(con,strPosCode);
              if(dcmModel != null)
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmModel);
              }
            }
            
            CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
            Vector caseInfoElementList = CaseDAO.getAllCaseInfoElements(con,strCaseId);
            caseDTO.setCaseInfoElementList(caseInfoElementList);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseDTO);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_UPDATE_CASE_DETAIL);
            dataHashMap.put(HLPInterfaceKey.PAGE_HEADER,"Case Detail");
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_UPDATE_CASE_INFO_ELEMENT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strCaseId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_ID);

            
            String caseInfoElementId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_INFO_ELEMENT_ID);
            String caseInfoElementTitle = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_CASE_INFO_ELEMENT_TITLE);
            String caseInfoElementDate = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_CASE_INFO_ELEMENT_DATE);
            String caseInfoElementDescription = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXTAREA_CASE_INFO_ELEMENT_DESCRIPTION);
            String caseInfoElementTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_CASE_INFO_ELEMENT_TYPE_ID);
            String contactName = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_CONTACT_NAME);
            String contactInfo = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXTAREA_CONTACT_INFORMATION);

            CaseDAO.updateCaseInfoElement(con,caseInfoElementId,caseInfoElementTitle,caseInfoElementDate,caseInfoElementDescription,caseInfoElementTypeId,contactName,contactInfo);

            CaseModel caseModel = CaseDAO.getCaseById(con,strCaseId);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseModel);

            String strPosCode = caseModel.getPosCode();
            if(strPosCode != null)
            {
              DCMModel dcmModel = DCMDao.getDCMModel(con,strPosCode);
              if(dcmModel != null)
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmModel);
              }
            }
            
            CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
            Vector caseInfoElementList = CaseDAO.getAllCaseInfoElements(con,strCaseId);
            caseDTO.setCaseInfoElementList(caseInfoElementList);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseDTO);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_UPDATE_CASE_DETAIL);
            dataHashMap.put(HLPInterfaceKey.PAGE_HEADER,"Case Detail");
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_VIEW_CASE_HISTORY:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strCaseId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_ID);

            CaseModel caseModel = CaseDAO.getCaseById(con,strCaseId);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseModel);

            String strPosCode = caseModel.getPosCode();
            if(strPosCode != null)
            {
              DCMModel dcmModel = DCMDao.getDCMModel(con,strPosCode);
              if(dcmModel != null)
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmModel);
              }
            }
            
            Vector caseDetails = CaseDAO.getAllCaseDetails(con,strCaseId);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseDetails);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_CREATE_NEW_MISSION:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            Vector surveysVector = SurveyDAO.getAllActiveSurveys(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,surveysVector);
                
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_CREATE_NEW_MISSION);
            dataHashMap.put(HLPInterfaceKey.PAGE_HEADER,"Create New Mission");
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_SAVE_NEW_MISSION:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            Vector surveysVector = SurveyDAO.getAllActiveSurveys(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,surveysVector);
                
            String strMissionName = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_MISSION_NAME);
            String strMissionDesc = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXTAREA_MISSION_DESCRIPTION);
            String strMissionStartDate = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_MISSION_START_DATE);
            String strMissionEndDate = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_MISSION_END_DATE);
            String strFilSurveyId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_FIL_SURVEY_ID);

            MissionDAO.insertMission(con,strMissionName,strMissionDesc,strMissionStartDate,strMissionEndDate,strFilSurveyId,strUserID);
            
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_CREATE_NEW_MISSION);
            dataHashMap.put(HLPInterfaceKey.PAGE_HEADER,"Create New Mission");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Mission created successfully.");
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_UPDATE_MISSION_DETAILS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
          
            String strMissionId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_MISSION_ID);    
            String strMissionName = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_MISSION_NAME);
            String strMissionDesc = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXTAREA_MISSION_DESCRIPTION);
            String strMissionStartDate = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_MISSION_START_DATE);
            String strMissionEndDate = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_MISSION_END_DATE);
            String strFilSurveyId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SELECT_FIL_SURVEY_ID);

            MissionDAO.updateMission(con,strMissionId,strMissionName,strMissionDesc,strMissionStartDate,strMissionEndDate,strFilSurveyId,strUserID);
            
            Vector vecMissions = MissionDAO.getAllMissions(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecMissions);
            Vector vecMissionsStatusTypes = MissionDAO.getAllMissionStatusTypes(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecMissionsStatusTypes);
          
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_ADMIN_VIEW_MISSIONS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            //Vector vecMissions = MissionDAO.getAllMissions(con);
            Vector vecMissions = new Vector();
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecMissions);
            Vector vecMissionsStatusTypes = MissionDAO.getAllMissionStatusTypes(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecMissionsStatusTypes);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_ADMIN_SEARCH_MISSION:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String missionName = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_NAME);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_NAME,missionName);
            String missionStatusTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_MISSION_STATUS);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_SELECT_MISSION_STATUS,missionStatusTypeId);
            String missionStartDateFrom = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_START_DATE_FROM);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_START_DATE_FROM,missionStartDateFrom);
            String missionStartDateTo = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_START_DATE_TO);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_START_DATE_TO,missionStartDateTo);
            String missionEndDateFrom = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_END_DATE_FROM);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_END_DATE_FROM,missionEndDateFrom);
            String missionEndDateTo = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_END_DATE_TO);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_END_DATE_TO,missionEndDateTo);

            Vector vecMissions = MissionDAO.getMissionsByFilter(con,missionName,missionStatusTypeId,missionStartDateFrom,missionStartDateTo,missionEndDateFrom,missionEndDateTo);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecMissions);
            Vector vecMissionsStatusTypes = MissionDAO.getAllMissionStatusTypes(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecMissionsStatusTypes);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_UPDATE_MISSION_STATUS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              String keyValue = (String)paramHashMap.get(tempStatusString);
              //Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
              if(tempStatusString.startsWith(HLPInterfaceKey.INPUT_SELECT_MISSION_STATUS_ID))
              {
                int strlength = HLPInterfaceKey.INPUT_SELECT_MISSION_STATUS_ID.length() + 1;
                String missionId = tempStatusString.substring(strlength, tempStatusString.length());
                String missionOldStatusTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_OLD_MISSION_STATUS_ID+"_"+missionId);
                if(keyValue.compareTo(missionOldStatusTypeId) != 0)
                {
                  MissionDAO.updateMissionStatusTypeId(con,missionId,keyValue,strUserID);
                }
              }
            }
            Vector vecMissions = MissionDAO.getAllMissions(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecMissions);
            Vector vecMissionsStatusTypes = MissionDAO.getAllMissionStatusTypes(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecMissionsStatusTypes);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_SAVE_MISSION_USERS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            Vector vecMissions = MissionDAO.getAllMissions(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecMissions);
            Vector vecMissionsStatusTypes = MissionDAO.getAllMissionStatusTypes(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecMissionsStatusTypes);

            String missionId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_MISSION_ID);
            MissionDAO.deleteMissionUser(con,missionId);
            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              String keyValue = (String)paramHashMap.get(tempStatusString);
              //Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
              if(tempStatusString.startsWith(HLPInterfaceKey.INPUT_CHECKBOX_MISSION_USER_ID))
              {
                int strlength = HLPInterfaceKey.INPUT_CHECKBOX_MISSION_USER_ID.length() + 1;
                String userId = tempStatusString.substring(strlength, tempStatusString.length());
                MissionDAO.insertMissionUser(con,missionId,userId);
              }
            }
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_VIEW_MISSIONS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            Vector vecMissions = MissionDAO.getMissionsForUser(con,strUserID,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecMissions);
            Vector vecMissionsStatusTypes = MissionDAO.getAllMissionStatusTypes(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecMissionsStatusTypes);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_SEARCH_MISSION:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String missionName = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_NAME);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_NAME,missionName);
            String missionStatusTypeId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_MISSION_STATUS);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_SELECT_MISSION_STATUS,missionStatusTypeId);
            String missionStartDateFrom = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_START_DATE_FROM);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_START_DATE_FROM,missionStartDateFrom);
            String missionStartDateTo = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_START_DATE_TO);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_START_DATE_TO,missionStartDateTo);
            String missionEndDateFrom = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_END_DATE_FROM);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_END_DATE_FROM,missionEndDateFrom);
            String missionEndDateTo = (String)paramHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_END_DATE_TO);
            dataHashMap.put(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_END_DATE_TO,missionEndDateTo);

            Vector vecMissions = MissionDAO.getMissionsByFilterForUser(con,missionName,missionStatusTypeId,missionStartDateFrom,missionStartDateTo,missionEndDateFrom,missionEndDateTo,strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecMissions);
            Vector vecMissionsStatusTypes = MissionDAO.getAllMissionStatusTypes(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecMissionsStatusTypes);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break; 

        case HLP_ASSIGN_MISSION_USERS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strMissionID = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_MISSION_ID);
            Vector usersVector = UserDAO.getSystemUsersList(con,"PERSON_FULL_NAME");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,usersVector);
            MissionModel missionModel = MissionDAO.getMissionById(con,strMissionID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,missionModel);
            Vector vecAssignedUsers = MissionDAO.getMissionUser(con,strMissionID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,vecAssignedUsers);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_EDIT_MISSION:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strMissionID = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_MISSION_ID);
            MissionModel missionModel = MissionDAO.getMissionById(con,strMissionID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,missionModel);

            Vector surveysVector = SurveyDAO.getAllActiveSurveys(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,surveysVector);
                
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_EDIT_MISSION);
            dataHashMap.put(HLPInterfaceKey.PAGE_HEADER,"Edit Mission");
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_UPLOAD_MISSION_TARGET_USERS_PROCESS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String missionId = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_MISSION_ID);
            Utility.logger.debug("mission Id"+missionId);
            dataHashMap.put(HLPInterfaceKey.INPUT_HIDDEN_MISSION_ID,missionId);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_UPLOAD_MISSION_TARGET_USERS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("8");
            dataHashMap.put(  AdministrationInterfaceKey.TABLE_DEF_VECTOR  , tableDefVector);
            Vector missionVector = MissionDAO.getAllMissions(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,missionVector);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_VIEW_MISSION_DETAILS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strMissionID = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_MISSION_ID);
            MissionModel missionModel = MissionDAO.getMissionById(con,strMissionID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,missionModel);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_DELETE_DCM_FROM_MISSION:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strMissionID = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_MISSION_ID);
            String strDcmCode = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_DCM_CODE);
            String dcmID = SheetDAO.getPOSId(strDcmCode,con);
            
            MissionDAO.deleteMissionDcm(con,strMissionID,dcmID);

            MissionModel missionModel = MissionDAO.getMissionById(con,strMissionID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,missionModel);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_VIEW_MISSION_DETAILS_DCM_LIST:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strMissionID = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_MISSION_ID);
            MissionModel missionModel = MissionDAO.getMissionById(con,strMissionID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,missionModel);

            String dcmSurveysCategory = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_DCM_CATEGORY);
            DCMDto dcmdto = DCMDao.getAllDCMAssignedToMission(strMissionID,dcmSurveysCategory,con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , dcmdto);
            dataHashMap.put(HLPInterfaceKey.MISSION_DETAILS_COMING_TABLE,"dcm");
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_VIEW_MISSION_DETAILS_USER_LIST:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strMissionID = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_MISSION_ID);
            MissionModel missionModel = MissionDAO.getMissionById(con,strMissionID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,missionModel);

            Vector vecMissionUser = MissionDAO.getSystemUsersListAssignedToMission(con,strMissionID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecMissionUser);
            dataHashMap.put(HLPInterfaceKey.MISSION_DETAILS_COMING_TABLE,"user");
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_SELECT_MISSION_TARGET_USER:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strMissionID = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_MISSION_ID);
            MissionModel missionModel = MissionDAO.getMissionById(con,strMissionID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,missionModel);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_GET_RANDOM_INCOMPLETED_DCM:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strMissionID = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_MISSION_ID);
            MissionModel missionModel = MissionDAO.getMissionById(con,strMissionID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,missionModel);

            int numberOfSurveysIncompleted = missionModel.getNumberOfSurveysIncompleted();
            if(numberOfSurveysIncompleted == 0)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"All surveys are completed.");
            }
            else
            {
              Random r = new Random();
              int randint = r.nextInt(numberOfSurveysIncompleted+1);
              if(randint == 0)randint = 1;
              DCMDto dcmDto = DCMDao.getRandomDCMAssignedToMission(strMissionID,HLPInterfaceKey.DCM_CATEGORY_SURVEYS_INCOMPLETED,randint,con);
              dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , dcmDto);
            }
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_VIEW_USER_MISSION_DCM_LIST:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strMissionID = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_MISSION_ID);
            MissionModel missionModel = MissionDAO.getMissionById(con,strMissionID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,missionModel);

            String dcmSurveysCategory = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_DCM_CATEGORY);
            DCMDto dcmdto = DCMDao.getAllDCMAssignedToMission(strMissionID,dcmSurveysCategory,con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , dcmdto);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_VIEW_TARGET_USER_SURVEY:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String errMsg = "";
            String strMissionID = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_MISSION_ID);
            MissionModel missionModel = MissionDAO.getMissionById(con,strMissionID);
            String surveyId = missionModel.getSurveyId();
            Calendar calendar = Calendar.getInstance();
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
            int currentMonth = calendar.get(Calendar.MONTH)+1;
            Utility.logger.debug(calendar.get(Calendar.YEAR)+"");
            int currentYear = calendar.get(Calendar.YEAR);
            Utility.logger.debug(currentYear+"");
            
            String strDcmCode = (String)paramHashMap.get(HLPInterfaceKey.INPUT_TEXT_DCM_CODE);
            String dcmID = SheetDAO.getPOSId(strDcmCode,con);
            String strFilSurveyId = "";
            if(dcmID.compareTo("")==0)
            {
              errMsg = "Dcm code "+strDcmCode+" does not exists in the system";
            }
            else
            {
              strFilSurveyId = MissionDAO.getMissionDCMFilSurveyId(con,strMissionID,dcmID);
              if(strFilSurveyId.compareTo("*")==0)
              {
                errMsg = "Dcm code "+strDcmCode+" not assigned to Mission Id "+strMissionID+" "; 
              }
              else if(strFilSurveyId.compareTo("")==0)
              {
                //fil survey not created yet
                //create new fil survey for selected dcm
                String surevyCreateDate = currentMonth+"/"+currentDay+"/"+currentYear;
                Vector vecSurveyModel = SurveyDAO.getSurveyById(con, surveyId);
                SurveyModel surveyModel = (SurveyModel)vecSurveyModel.get(0);
                String newsurevyCreateDate = surevyCreateDate;
                if(surevyCreateDate.charAt(0) == '0')
                {
                    newsurevyCreateDate = surevyCreateDate.substring(1);
                    Utility.logger.debug(newsurevyCreateDate);
                }
                String surveyName = "MIS"+strMissionID+"-"+"DCM"+strDcmCode;
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
                  strFilSurveyId = lfilSurveyID+"";
                  SurveyDAO.insertFilSurvey(con, lfilSurveyID, surveyName, "1", surveyTypeId, surveyDescription, "GEN_DCM", dcmID, newsurevyCreateDate, "0", "0", surveyId, strUserID);
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
                  errMsg = "There is Fil Survey with the same name : "+surveyName+" .";
                }
                ///////////
                //update hlp_mission_dcm set fil_survey_id with the id just created
                MissionDAO.updateMissionDcm(con,strMissionID,dcmID,strFilSurveyId);
                /////////////////
                //get the fil survey to edit 
              }
            }
            if(errMsg.compareTo("")==0)
            {
              Vector vecfilSurvey = SurveyDAO.getFilSurveyById(con,strFilSurveyId);
              Vector vecFilGroup = SurveyDAO.getAllFilGroupsBySurveyId(con,strFilSurveyId);
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
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecFilGroup);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,hashMapFilQuestion);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,vecfilSurvey);
              dataHashMap.put(SurveyInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_QUESTION_CHOICES,hashMapQuestionChoices);
            }
            else
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,errMsg);
            }
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_ADD_CASE_INFO_ELEMENT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strCaseID = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_ID);
            dataHashMap.put(HLPInterfaceKey.INPUT_HIDDEN_CASE_ID,strCaseID);
            Vector caseInfoElementTypeList = CaseDAO.getAllCaseInfoElementTypes(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseInfoElementTypeList);

            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_SAVE_CASE_INFO_ELEMENT);
            dataHashMap.put(HLPInterfaceKey.PAGE_HEADER,"Add Case Info Element");
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case HLP_EDIT_CASE_INFO_ELEMENT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strCaseID = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_ID);
            dataHashMap.put(HLPInterfaceKey.INPUT_HIDDEN_CASE_ID,strCaseID);
            String strCaseInfoElementID = (String)paramHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_INFO_ELEMENT_ID);

            CaseInfoElementModel caseInfoElementModel = CaseDAO.getCaseInfoElement(con,strCaseInfoElementID);          
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseInfoElementModel);

            Vector caseInfoElementTypeList = CaseDAO.getAllCaseInfoElementTypes(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,caseInfoElementTypeList);

            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_UPDATE_CASE_INFO_ELEMENT);
            dataHashMap.put(HLPInterfaceKey.PAGE_HEADER,"Edit Case Info Element");
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
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