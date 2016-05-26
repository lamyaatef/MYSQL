package com.mobinil.sds.core.facade.handlers;


import java.util.Vector;
import java.util.HashMap;
import java.sql.*;
import com.mobinil.sds.core.system.security.dao.securityDAO;

import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.dcm.DCMInterfaceKey;
import com.mobinil.sds.web.interfaces.sa.*;
import com.mobinil.sds.web.interfaces.hlp.*;
import com.mobinil.sds.web.interfaces.gn.ua.UserAccountInterfaceKey;

import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.core.system.sa.importdata.dao.*;
import com.mobinil.sds.core.system.dcm.pos.model.*;
import com.mobinil.sds.core.system.dcm.pos.dao.*;
import com.mobinil.sds.core.system.dcm.user.dao.*;
import com.mobinil.sds.core.system.dcm.user.model.*;

import com.mobinil.sds.core.system.sa.persons.model.*;
import com.mobinil.sds.core.system.sa.users.model.*;
import com.mobinil.sds.core.system.sa.users.dao.*;
import com.mobinil.sds.core.system.sa.users.dto.*;

import com.mobinil.sds.core.system.dcm.genericModel.*;
import com.mobinil.sds.core.system.dcm.genericModel.DAO.*;
import com.mobinil.sds.core.system.dcm.city.dao.*;
import com.mobinil.sds.core.system.dcm.owner.dao.*;
import com.mobinil.sds.core.system.dcm.manager.dao.*;
import com.mobinil.sds.core.system.dcm.region.dao.*;
import com.mobinil.sds.core.system.dcm.branch.dao.*;
import com.mobinil.sds.core.system.dcm.branch.model.*;
import com.mobinil.sds.core.system.dcm.function.dao.*;
import com.mobinil.sds.core.system.dcm.function.model.*;
import com.mobinil.sds.core.system.dcm.group.dao.*;
import com.mobinil.sds.core.system.dcm.group.model.*;
import com.mobinil.sds.core.system.gn.dcm.dao.*;
import com.mobinil.sds.core.system.gn.dcm.model.DCMModel;
import com.mobinil.sds.core.system.dcm.service.dao.*;
import com.mobinil.sds.core.system.dcm.service.model.*;
import com.mobinil.sds.core.system.hlp.casepkg.dao.*;
import com.mobinil.sds.core.system.hlp.casepkg.dto.*;

import com.mobinil.sds.core.system.dcm.worker.*;

public class DCMHandler  
{
  static final int DCM_SA_POS_LIST                                            = 1;
  static final int DCM_SA_CREATE_NEW_POS                                      = 2;
  static final int DCM_SA_EDIT_POS                                            = 3;
  static final int DCM_USER_POS_LIST                                          = 4; 
  static final int DCM_USER_VIEW_POS_DETAILS                                  = 5;
  static final int DCM_USER_VIEW_POS_RELATIONSHIPS                            = 6;
  static final int DCM_POS_GROUPS                                             = 7;  
  static final int DCM_EDIT_POS_GROUP                                         = 8;
  static final int DCM_CREATE_NEW_POS_GROUP                                   = 9;
  static final int DCM_ASSIGN_POS_TO_POS_GROUP                                = 10;
  static final int DCM_ASSIGN_FUNCTION_TO_POS_GROUP                           = 11;
  static final int DCM_POS_FUNCTIONS                                          = 12;
  static final int DCM_EDIT_POS_FUNCTION                                      = 13;
  static final int DCM_CREATE_NEW_POS_FUNCTION                                = 14;
  static final int DCM_POS_BRANCHES                                           = 15;
  static final int DCM_EDIT_USER_ACCOUNT                                      = 16;
  static final int DCM_ADMIN_EDIT_SALES_AGENT                                 = 17;
  static final int DCM_ADMIN_CREATE_NEW_SALES_AGENT                           = 18;
  static final int DCM_ASSIGN_TARGET_TO_GROUP                                 = 19;
  static final int DCM_MANAGERS                                               = 20;
  static final int DCM_ASSIGN_SALES_AGENT_TO_MANAGER                          = 21;
  static final int DCM_CREATE_NEW_MANAGER                                     = 22;
  static final int DCM_MANAGER_SALES_AGENTS                                   = 23;
  static final int DCM_MANAGER_VIEW_SALES_AGENT_TARGET                        = 24;
  static final int DCM_MANAGER_EDIT_SALES_AGENT_TARGET                        = 25;
  static final int DCM_SALES_AGENT_VISITS                                     = 26;
  static final int DCM_EDIT_SALES_AGENT_VISIT                                 = 27;
  static final int DCM_CREATE_NEW_SALES_AGENT_VISIT                           = 28;
  static final int DCM_ADMIN_SALES_AGENTS_ASSIGN_FUNCTION                     = 29;
  static final int DCM_ADMIN_SALES_AGENTS_ASSIGN_GROUP                        = 30;
  static final int DCM_ASSIGN_FUNCTION_TO_SALES_AGENT                         = 31;
  static final int DCM_SALES_AGENT_VISITS_FOR_AGENTS                          = 32;
  static final int DCM_VIEW_SALES_AGENT_VISIT_DETAILS                         = 33;
  static final int DCM_SALES_AGENT_ACTUAL_VISITS_FOR_AGENTS                   = 34;
  static final int DCM_VIEW_SALES_AGENT_ACTUAL_VISIT_DETAILS                  = 35;  
  static final int DCM_CREATE_NEW_SALES_AGENT_ACTUAL_VISIT                    = 36;
  static final int DCM_SALES_AGENT_BLUE_COPY_ENTRY_FORM                       = 37;
  static final int DCM_MANAGER_BLUE_COPIES_COLLECTED                          = 38;
  static final int DCM_MANAGER_EDIT_BLUE_COPIES_COLLECTED                     = 39;
  static final int DCM_SALES_AGENT_CREATE_SERVICE_REQUEST                     = 40;
  static final int DCM_MANAGER_EDIT_SERVICE_REQUEST                           = 41;
  static final int DCM_MANAGER_SERVICE_REQUEST_LIST                           = 42;
  static final int DCM_MANAGER_SERVICES_LIST                                  = 43;
  static final int DCM_EDIT_REGION                                            = 44;
  static final int DCM_CHANGED_SALES_AGENTS_DATA_MANAGEMENT                   = 45;
  static final int DCM_ASSIGN_TARGET_TO_POS                                   = 46;
  static final int DCM_CREATE_NEW_SERVICE                                     = 47;
  static final int DCM_EDIT_SERVICE                                           = 48;
  static final int DCM_ASSIGN_POS_TO_SERVICE                                  = 49;
  static final int DCM_UPLOAD_SERVICE_ELIGIBILITY_LIST                        = 50;
  static final int DCM_ADMIN_VIEW_POS_CHANGES_DETAILS                         = 51;
  static final int DCM_UPLOAD_ASSIGN_SALES_AGENT_TO_MANAGER                   = 52;
  static final int DCM_SALES_AGENT_BLUE_COPIES_COLLECTED                      = 53;
  static final int DCM_MOVE_REGIONS_SELECT_SOURCES                            = 54;
  static final int DCM_MOVE_REGIONS_SELECT_DESTINATION                        = 55;
  static final int DCM_SAVE_NEW_POS                                           = 56;
  static final int DCM_ADD_NEW_DCM_USER                                       = 57;
  static final int DCM_SEARCH_POS                                             = 58;
  static final int LIST_TABLE_FIELDS                                          = 59;  
  static final int EDIT_POS                                                   = 60;
  static final int DCM_UPDATE_POS_STATUS                                      = 61;
  static final int DCM_SAVE_EDITED_POS                                        = 62;
  static final int DCM_SEARCH_USER_POS                                        = 63;
  static final int DCM_USER_UPDATE_POS_STATUS                                 = 64;
  static final int DCM_GENERIC_MODEL_SELECT_TABLE                             = 65;
  static final int DCM_GENERIC_MODEL_EDIT_GENERIC_ITEM                        = 66;
  static final int DCM_GENERIC_MODEL_ADD_NEW_GENERIC_ITEM                     = 67;               
  static final int DCM_GENERIC_MODEL_SAVE_NEW_GENERIC_ITEM                    = 68;
  static final int DCM_GENERIC_MODEL_GET_TABLE                                = 69;
  static final int DCM_SAVE_ASSIGN_SALES_AGENT_TO_MANAGER                     = 70;
  static final int SEARCH_BRANCHES                                            = 71;
  static final int DCM_UPDATE_DCM_USER_STATUS_TYPE                            = 72;
  static final int SEARCH_DCM_MANAGERS                                        = 73;
  static final int DCM_SAVE_NEW_BRANCH                                        = 74;
  static final int DCM_UPDATE_DCM_USER                                        = 75;
  static final int DCM_SAVE_NEW_FUNCTION                                      = 76;
  static final int DCM_CHANGE_USER_DETAIL_STATUS                              = 77;
  static final int DCM_SEARCH_FUNCTION                                        = 78;
  static final int DCM_EDIT_FUNCTION                                          = 79;
  static final int DCM_SEARCH_ADMIN_SALES_AGENTS_ASSIGN_FUNCTION              = 80;
  static final int DCM_SAVE_ASSIGN_FUNCTION_TO_SALES_AGENT                    = 81;
  static final int DCM_UPDATE_FUNCTION_STATUS                                 = 82;
  static final int DCM_SEARCH_GROUPS                                          = 83;
  static final int DCM_SAVE_NEW_GROUP                                         = 84;
  static final int DCM_SAVE_EDITED_GROUP                                      = 85;
  static final int DCM_SAVE_ASSIGN_TARGET_TO_SALES_AGENT                      = 86;
  static final int DCM_SAVE_GROUP_POS                                         = 87;
  static final int DCM_SAVE_GROUP_FUNCTION                                    = 88;
  static final int DCM_SEARCH_ADMIN_SALES_AGENTS_ASSIGN_GROUP                 = 89;
  static final int DCM_SEARCH_MANAGER_SALES_AGENTS                            = 90;
  static final int DCM_SAVE_VISIT_PLAN_DETAILS                                = 91;
  static final int DCM_UPDATE_SALES_AGENT_VISITS_STATUS                       = 92;
  static final int DCM_SEARCH_SALES_AGENT_VISITS                              = 93;
  static final int DCM_BRANCH_UPDATE_STATUS                                   = 94;  
  static final int DCM_UPDATE_GROUP_STATUS                                    = 95;
  static final int DCM_SAVE_SERVICE                                           = 96;
  static final int DCM_SEARCH_MANAGER_SERVICES_LIST                           = 97;
  static final int DCM_UPDATE_SERVICES_STATUS                                 = 98;
  static final int DCM_SAVE_ASSIGN_POS_TO_SERVICE                             = 99;
  static final int DCM_UPLOAD_SERVICE_ELIGIBILITY_LIST_PROCESS                = 100;
  static final int DCM_EDIT_SALES_AGENT_VISIT_FOR_AGENT                       = 101;
  static final int DCM_CREATE_NEW_SALES_AGENT_VISIT_FOR_AGENT                 = 102;
  static final int DCM_SAVE_VISIT_PLAN_DETAILS_FOR_AGENT                      = 103;
  static final int DCM_SEARCH_SALES_AGENT_VISITS_FOR_AGENT                    = 104;
  static final int DCM_SAVE_SALES_AGENT_ACTUAL_VISIT                          = 105;
  static final int DCM_SEARCH_SALES_AGENT_ACTUAL_VISITS_FOR_AGENTS            = 106;
  static final int DCM_CREATE_NEW_CASE_FROM_ACTUAL_VISIT                      = 107;
  static final int DCM_SAVE_SALES_AGENT_BLUE_COPY_ENTRY_FORM                  = 108;
  static final int DCM_CREATE_NEW_CASE_FROM_ACTUAL_VISIT_FOR_SERVICE_REQUEST  = 109;
  static final int DCM_SEARCH_MANAGER_SERVICE_REQUEST_LIST                    = 110;  
  static final int DCM_USER_CREATE_NEW_POS                                    = 111;
  static final int DCM_ACTUAL_VISIT_EDIT_POS                                  = 112;
  
  public void ejbCreate()
  {
  }

  public void ejbActivate()
  {
  }

  public void ejbPassivate()
  {
  }

  public void ejbRemove()
  {
  }

 

  public static HashMap handle(String action, HashMap paramHashMap, Connection con) throws Exception
  {
    //Vector chainList = null;
    Vector chainLevelList = null;
    Vector  chainPaymentLevelList =null;
    Vector  chainChannelList = null;
    Vector chainCityList = null;
    Vector chainDistrictList = null;
    Vector chainStatusList = null;
    String chainCode = null;
    String chainName = null;
    String chainPhone = null;
    String chainMail = null;
    String chainRank = null;
    String chainStkNumber = null;
    String chainAddress = null;
    String chainStatus = null;
    String chainLevel = null;
    String chainPaymentLevel = null;
    String chainChannel = null;
    String chainCity = null;
    String chainDistrict = null;

    Utility.logger.debug("Handel of dcm ");    
    int actionType = 0;
    HashMap dataHashMap = null;
   // Connection  con = null;

      try 
      {
       con = Utility.getConnection();
       if (action.equals(DCMInterfaceKey.ACTION_DCM_SA_POS_LIST))                                   
        actionType = DCM_SA_POS_LIST;
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_SA_CREATE_NEW_POS))                                   
        actionType = DCM_SA_CREATE_NEW_POS;
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_SA_EDIT_POS))                                   
        actionType = DCM_SA_EDIT_POS;
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_USER_POS_LIST))                                   
        actionType = DCM_USER_POS_LIST;
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_USER_VIEW_POS_DETAILS))                                   
        actionType = DCM_USER_VIEW_POS_DETAILS;
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_USER_VIEW_POS_RELATIONSHIPS))                                   
        actionType = DCM_USER_VIEW_POS_RELATIONSHIPS;
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_POS_GROUPS))                                   
        actionType = DCM_POS_GROUPS;                   
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_EDIT_POS_GROUP))                                   
        actionType = DCM_EDIT_POS_GROUP;
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_CREATE_NEW_POS_GROUP))                                   
        actionType = DCM_CREATE_NEW_POS_GROUP; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_ASSIGN_POS_TO_POS_GROUP))                                   
        actionType = DCM_ASSIGN_POS_TO_POS_GROUP; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_ASSIGN_FUNCTION_TO_POS_GROUP))                                   
        actionType = DCM_ASSIGN_FUNCTION_TO_POS_GROUP; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_POS_FUNCTIONS))                                   
        actionType = DCM_POS_FUNCTIONS; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_EDIT_POS_FUNCTION))                                   
        actionType = DCM_EDIT_POS_FUNCTION;  
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_CREATE_NEW_POS_FUNCTION))                                   
        actionType = DCM_CREATE_NEW_POS_FUNCTION; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_POS_BRANCHES))                                   
        actionType = DCM_POS_BRANCHES; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_EDIT_USER_ACCOUNT))                                   
        actionType = DCM_EDIT_USER_ACCOUNT; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_ADMIN_EDIT_SALES_AGENT))                                   
        actionType = DCM_ADMIN_EDIT_SALES_AGENT; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_ADMIN_CREATE_NEW_SALES_AGENT))                                   
        actionType = DCM_ADMIN_CREATE_NEW_SALES_AGENT; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_ASSIGN_TARGET_TO_GROUP))                                   
        actionType = DCM_ASSIGN_TARGET_TO_GROUP; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_MANAGERS))                                   
        actionType = DCM_MANAGERS; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_ASSIGN_SALES_AGENT_TO_MANAGER))                                   
        actionType = DCM_ASSIGN_SALES_AGENT_TO_MANAGER; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_CREATE_NEW_MANAGER))                                   
        actionType = DCM_CREATE_NEW_MANAGER; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_MANAGER_SALES_AGENTS))                                   
        actionType = DCM_MANAGER_SALES_AGENTS; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_MANAGER_VIEW_SALES_AGENT_TARGET))                                   
        actionType = DCM_MANAGER_VIEW_SALES_AGENT_TARGET; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_MANAGER_EDIT_SALES_AGENT_TARGET))                                   
        actionType = DCM_MANAGER_EDIT_SALES_AGENT_TARGET; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_SALES_AGENT_VISITS))                                   
        actionType = DCM_SALES_AGENT_VISITS;  
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_EDIT_SALES_AGENT_VISIT))                                   
        actionType = DCM_EDIT_SALES_AGENT_VISIT; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_CREATE_NEW_SALES_AGENT_VISIT))                                   
        actionType = DCM_CREATE_NEW_SALES_AGENT_VISIT; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_ADMIN_SALES_AGENTS_ASSIGN_FUNCTION))                                   
        actionType = DCM_ADMIN_SALES_AGENTS_ASSIGN_FUNCTION; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_ADMIN_SALES_AGENTS_ASSIGN_GROUP))                                   
        actionType = DCM_ADMIN_SALES_AGENTS_ASSIGN_GROUP; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_ASSIGN_FUNCTION_TO_SALES_AGENT))                                   
        actionType = DCM_ASSIGN_FUNCTION_TO_SALES_AGENT; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_SALES_AGENT_VISITS_FOR_AGENTS))                                   
        actionType = DCM_SALES_AGENT_VISITS_FOR_AGENTS; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_VIEW_SALES_AGENT_VISIT_DETAILS))                                   
        actionType = DCM_VIEW_SALES_AGENT_VISIT_DETAILS; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_SALES_AGENT_ACTUAL_VISITS_FOR_AGENTS))                                   
        actionType = DCM_SALES_AGENT_ACTUAL_VISITS_FOR_AGENTS; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_VIEW_SALES_AGENT_ACTUAL_VISIT_DETAILS))                                   
        actionType = DCM_VIEW_SALES_AGENT_ACTUAL_VISIT_DETAILS; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_CREATE_NEW_SALES_AGENT_ACTUAL_VISIT))                                   
        actionType = DCM_CREATE_NEW_SALES_AGENT_ACTUAL_VISIT; 
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_SALES_AGENT_BLUE_COPY_ENTRY_FORM))                                   
        actionType = DCM_SALES_AGENT_BLUE_COPY_ENTRY_FORM;       
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_MANAGER_BLUE_COPIES_COLLECTED))                                   
        actionType = DCM_MANAGER_BLUE_COPIES_COLLECTED;       
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_MANAGER_EDIT_BLUE_COPIES_COLLECTED))                                   
        actionType = DCM_MANAGER_EDIT_BLUE_COPIES_COLLECTED;  
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_SALES_AGENT_CREATE_SERVICE_REQUEST))                                   
        actionType = DCM_SALES_AGENT_CREATE_SERVICE_REQUEST;  
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_MANAGER_EDIT_SERVICE_REQUEST))                                   
        actionType = DCM_MANAGER_EDIT_SERVICE_REQUEST;  
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_MANAGER_SERVICE_REQUEST_LIST))                                   
        actionType = DCM_MANAGER_SERVICE_REQUEST_LIST;  
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_MANAGER_SERVICES_LIST))                                   
        actionType = DCM_MANAGER_SERVICES_LIST;  
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_EDIT_REGION))                                   
        actionType = DCM_EDIT_REGION;  
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_CHANGED_SALES_AGENTS_DATA_MANAGEMENT))                                   
        actionType = DCM_CHANGED_SALES_AGENTS_DATA_MANAGEMENT;  
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_ASSIGN_TARGET_TO_POS))                                   
        actionType = DCM_ASSIGN_TARGET_TO_POS;  
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_CREATE_NEW_SERVICE))                                   
        actionType = DCM_CREATE_NEW_SERVICE;  
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_EDIT_SERVICE))                                   
        actionType = DCM_EDIT_SERVICE;  
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_ASSIGN_POS_TO_SERVICE))                                   
        actionType = DCM_ASSIGN_POS_TO_SERVICE;  
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_UPLOAD_SERVICE_ELIGIBILITY_LIST))                                   
        actionType = DCM_UPLOAD_SERVICE_ELIGIBILITY_LIST;  
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_ADMIN_VIEW_POS_CHANGES_DETAILS))                                   
        actionType = DCM_ADMIN_VIEW_POS_CHANGES_DETAILS;  
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_UPLOAD_ASSIGN_SALES_AGENT_TO_MANAGER))                                   
        actionType = DCM_UPLOAD_ASSIGN_SALES_AGENT_TO_MANAGER;  
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_SALES_AGENT_BLUE_COPIES_COLLECTED))                                   
        actionType = DCM_SALES_AGENT_BLUE_COPIES_COLLECTED;  
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_MOVE_REGIONS_SELECT_SOURCES))                                   
        actionType = DCM_MOVE_REGIONS_SELECT_SOURCES;  
       else if (action.equals(DCMInterfaceKey.ACTION_DCM_MOVE_REGIONS_SELECT_DESTINATION))                                   
        actionType = DCM_MOVE_REGIONS_SELECT_DESTINATION;  
       else if(action.equals(DCMInterfaceKey.ACTION_SAVE_NEW_POS))
        actionType = DCM_SAVE_NEW_POS;
       else if(action.equals(DCMInterfaceKey.ACTION_DCM_ADD_NEW_DCM_USER))
        actionType = DCM_ADD_NEW_DCM_USER;
      else if(action.equals(DCMInterfaceKey.ACTION_SEARCH_POS))
        actionType = DCM_SEARCH_POS;
      else if(action.equals(DCMInterfaceKey.ACTION_LIST_TABLE_FIELDS))
        actionType = LIST_TABLE_FIELDS;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_UPDATE_POS_STATUS))
        actionType = DCM_UPDATE_POS_STATUS;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SAVE_EDITED_POS))
        actionType = DCM_SAVE_EDITED_POS;
      else if(action.equals(DCMInterfaceKey.ACTION_USER_SEARCH_POS))
        actionType = DCM_SEARCH_USER_POS;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_USER_UPDATE_POS_STATUS))
        actionType = DCM_USER_UPDATE_POS_STATUS;
      else if(action.equals(DCMInterfaceKey.ACTION_SELECT_GENERIC_TABLE))
        actionType = DCM_GENERIC_MODEL_SELECT_TABLE;
      else if(action.equals(DCMInterfaceKey.ACTION_EDIT_GENERIC_ITEM))
          actionType = DCM_GENERIC_MODEL_EDIT_GENERIC_ITEM;
      else if(action.equals(DCMInterfaceKey.ACTION_ADD_NEW_GENERIC_ITEM))
          actionType = DCM_GENERIC_MODEL_ADD_NEW_GENERIC_ITEM;
      else if(action.equals(DCMInterfaceKey.ACTION_SAVE_NEW_GENERIC_ITEM))
          actionType = DCM_GENERIC_MODEL_SAVE_NEW_GENERIC_ITEM;
      else if(action.equals(DCMInterfaceKey.ACTION_FIND_GENERIC_TABLE))
          actionType = DCM_GENERIC_MODEL_GET_TABLE;    
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SAVE_ASSIGN_SALES_AGENT_TO_MANAGER))
          actionType = DCM_SAVE_ASSIGN_SALES_AGENT_TO_MANAGER;  
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SEARCH_BRACHES))
          actionType = SEARCH_BRANCHES;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_UPDATE_DCM_USER_STATUS_TYPE))
          actionType = DCM_UPDATE_DCM_USER_STATUS_TYPE;
      else if(action.equals(DCMInterfaceKey.ACTION_SEARCH_DCM_MANAGERS))
          actionType = SEARCH_DCM_MANAGERS;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SAVE_NEW_BRANCH))
          actionType = DCM_SAVE_NEW_BRANCH;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_UPDATE_DCM_USER))
          actionType = DCM_UPDATE_DCM_USER;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SAVE_NEW_FUNCTION))
          actionType = DCM_SAVE_NEW_FUNCTION;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_CHANGE_USER_DETAIL_STATUS))
          actionType = DCM_CHANGE_USER_DETAIL_STATUS;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SEARCH_FUNCTION))
          actionType = DCM_SEARCH_FUNCTION;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_EDIT_POS_FUNCTION))
          actionType = DCM_EDIT_FUNCTION;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SEARCH_ADMIN_SALES_AGENTS_ASSIGN_FUNCTION))
          actionType = DCM_SEARCH_ADMIN_SALES_AGENTS_ASSIGN_FUNCTION;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SAVE_ASSIGN_FUNCTION_TO_SALES_AGENT))
          actionType = DCM_SAVE_ASSIGN_FUNCTION_TO_SALES_AGENT;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_UPDATE_FUNCTION_STATUS))
          actionType = DCM_UPDATE_FUNCTION_STATUS;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_GROUP_SEARCH))
          actionType = DCM_SEARCH_GROUPS;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SAVE_NEW_GROUP)) 
          actionType = DCM_SAVE_NEW_GROUP;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SAVE_EDITED_GROUP)) 
          actionType = DCM_SAVE_EDITED_GROUP;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SAVE_ASSIGN_TARGET_TO_SALES_AGENT)) 
          actionType = DCM_SAVE_ASSIGN_TARGET_TO_SALES_AGENT;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SAVE_GROUP_POS))
          actionType = DCM_SAVE_GROUP_POS;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SAVE_GROUP_FUNCTION))
          actionType = DCM_SAVE_GROUP_FUNCTION;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SEARCH_ADMIN_SALES_AGENTS_ASSIGN_GROUP))
          actionType = DCM_SEARCH_ADMIN_SALES_AGENTS_ASSIGN_GROUP;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SEARCH_MANAGER_SALES_AGENTS))
          actionType = DCM_SEARCH_MANAGER_SALES_AGENTS;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SAVE_VISIT_PLAN_DETAILS))
          actionType = DCM_SAVE_VISIT_PLAN_DETAILS;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_UPDATE_SALES_AGENT_VISITS_STATUS))
          actionType = DCM_UPDATE_SALES_AGENT_VISITS_STATUS;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SEARCH_SALES_AGENT_VISITS))
          actionType = DCM_SEARCH_SALES_AGENT_VISITS;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_BRANCH_UPDATE_STATUS))
          actionType = DCM_BRANCH_UPDATE_STATUS;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_UPDATE_GROUP_STATUS))
          actionType = DCM_UPDATE_GROUP_STATUS;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SAVE_SERVICE))
          actionType = DCM_SAVE_SERVICE;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SEARCH_MANAGER_SERVICES_LIST))
          actionType = DCM_SEARCH_MANAGER_SERVICES_LIST;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_UPDATE_SERVICES_STATUS))
          actionType = DCM_UPDATE_SERVICES_STATUS;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SAVE_ASSIGN_POS_TO_SERVICE))
          actionType = DCM_SAVE_ASSIGN_POS_TO_SERVICE;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_UPLOAD_SERVICE_ELIGIBILITY_LIST_PROCESS))
          actionType = DCM_UPLOAD_SERVICE_ELIGIBILITY_LIST_PROCESS;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_EDIT_SALES_AGENT_VISIT_FOR_AGENT))
          actionType = DCM_EDIT_SALES_AGENT_VISIT_FOR_AGENT;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_CREATE_NEW_SALES_AGENT_VISIT_FOR_AGENT))
          actionType = DCM_CREATE_NEW_SALES_AGENT_VISIT_FOR_AGENT;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SAVE_VISIT_PLAN_DETAILS_FOR_AGENT))
          actionType = DCM_SAVE_VISIT_PLAN_DETAILS_FOR_AGENT;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SEARCH_SALES_AGENT_VISITS_FOR_AGENT))
          actionType = DCM_SEARCH_SALES_AGENT_VISITS_FOR_AGENT;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SAVE_SALES_AGENT_ACTUAL_VISIT))
          actionType = DCM_SAVE_SALES_AGENT_ACTUAL_VISIT;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SEARCH_SALES_AGENT_ACTUAL_VISITS_FOR_AGENTS))
          actionType = DCM_SEARCH_SALES_AGENT_ACTUAL_VISITS_FOR_AGENTS;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_CREATE_NEW_CASE_FROM_ACTUAL_VISIT))
          actionType = DCM_CREATE_NEW_CASE_FROM_ACTUAL_VISIT;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SAVE_SALES_AGENT_BLUE_COPY_ENTRY_FORM))
          actionType = DCM_SAVE_SALES_AGENT_BLUE_COPY_ENTRY_FORM;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_CREATE_NEW_CASE_FROM_ACTUAL_VISIT_FOR_SERVICE_REQUEST))
          actionType = DCM_CREATE_NEW_CASE_FROM_ACTUAL_VISIT_FOR_SERVICE_REQUEST;
      else if(action.equals(DCMInterfaceKey.ACTION_DCM_SEARCH_MANAGER_SERVICE_REQUEST_LIST))
          actionType = DCM_SEARCH_MANAGER_SERVICE_REQUEST_LIST;
      else if (action.equals(DCMInterfaceKey.ACTION_DCM_USER_CREATE_NEW_POS))
          actionType = DCM_USER_CREATE_NEW_POS;
      else if(action.equals(DCMInterfaceKey.ACTION_ACTUAL_VISIT_EDIT_POS))
          actionType = DCM_ACTUAL_VISIT_EDIT_POS;
          
      switch (actionType)                                                                                     
      {
        case DCM_SA_POS_LIST:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            GenericModel statusModel = new GenericModel();
            Vector statusVector = new Vector();
            Vector cityVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_POS_STATUS_TYPE");
            statusVector = genericModelDAO.getModels(con , statusModel);
            cityVector = CityDAO.get_all_cities(con);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_CITY , cityVector);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_STATUS, statusVector);
            dataHashMap.put(DCMInterfaceKey.POS_SEARCH_STATUS , "");

            String strFlagSuperAdmin = "-1";
            dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG,strFlagSuperAdmin);

          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        
        case DCM_SA_CREATE_NEW_POS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector regions            = new Vector();
            Vector IDTypeVector       = new Vector();
            Vector legalFormVec       = new Vector();
            Vector placeTypeVec       = new Vector();
            
            GenericModel gm           = new GenericModel();
            GenericModel placeTypeGM  = new GenericModel();
            GenericModel IDTypeModel  = new GenericModel();
           
            GenericModelDAO gmDAO     = new GenericModelDAO();

            IDTypeModel = gmDAO.getColumns(con , "DCM_ID_TYPE");
            IDTypeVector = gmDAO.getModels(con , IDTypeModel);
            gm = gmDAO.getColumns(con , "DCM_LEGAL_FORM");
            legalFormVec = gmDAO.getModels(con , gm);
            placeTypeGM = gmDAO.getColumns(con , "DCM_POS_PLACE_TYPE");
            placeTypeVec = gmDAO.getModels(con , placeTypeGM);
            regions = RegionDAO.getAllRegions(con);
            //regions = RegionDAO.getRegionsByUserId(con,strUserID);
            dataHashMap.put(DCMInterfaceKey.VECTOR_ID_TYPE , IDTypeVector);
            dataHashMap.put(DCMInterfaceKey.VECTOR_LEGAL_FORM , legalFormVec);
            dataHashMap.put(DCMInterfaceKey.VECTOR_PLACE_TYPE ,placeTypeVec);
            dataHashMap.put(DCMInterfaceKey.VECTOR_REGIONS , regions);
            dataHashMap.put(DCMInterfaceKey.DCM_SAVE_POS_TYPE , DCMInterfaceKey.DCM_SAVE_POS_TYPE_NEW);
            //dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            Utility.logger.debug("USERID:  "+ strUserID);

            String strFlagSuperAdmin = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
            dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG,strFlagSuperAdmin);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_SAVE_NEW_POS:
        {
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID          = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String posName            = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_NAME);
            String posCode            = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_CODE);
            String posEmail           = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_EMAIL);
            String posAddress         = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS);
            int posRegion             = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_REGION));
            int posCommercialNumber   = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_COMMERCIAL_NUMBER));
            int posTaxID              = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_TAX_ID));
            int posLegalForm          = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_LEGAL_FORM));
            int posPlace              = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_PLACE_TYPE));
            String posOwnerName       = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME);
            String posOwnerBirthDate  = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_BIRTH_DATE);
            int posOwnerIDType        = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE));
            int posOwnerIDNumber      = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER));
            String posManagerName     = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME);
            String posManagerBirthDate= (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_BIRTH_DATE);
            int posManagerIDType      = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE));
            int posManagerIDNumber    = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER));
            String userId             = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            int UserID                = Integer.parseInt(userId);

            String strFlagSuperAdmin = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
            dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG,strFlagSuperAdmin);

            String Phones = (String)paramHashMap.get("manager_phones__R2__C1");
            Vector POSPhones = new Vector();
            String POSPhonesCount = (String)paramHashMap.get("phones_count");

            int count = Integer.parseInt(POSPhonesCount);
            for(int i = 1 ; i <= count ; i++)
            {
              String phone = (String)paramHashMap.get("phones__R"+i+"__C1");
              POSPhones.add(phone);
            }
            String partners = (String)paramHashMap.get("partners__R1__C1");
            Vector partnerVector = new Vector();
            String PartnersCount = (String)paramHashMap.get("partners_count");
            count =  Integer.parseInt(PartnersCount);
                        Utility.logger.debug("partnersCount "+count);
            for(int i = 1 ; i <= count ; i++)
            {
              String partner = (String)paramHashMap.get("partners__R"+i+"__C1");
              partnerVector.add(partner);
            }
            Vector ownerPhones = new Vector();
            String ownerPhonesCount = (String)paramHashMap.get("owner_phones_count");
             count = Integer.parseInt(ownerPhonesCount);
            for(int i = 1 ; i <= count ; i++)
            {
              String phone = (String)paramHashMap.get("owner_phones__R"+i+"__C1");
              ownerPhones.add(phone);
            }
            Vector managerPhones = new Vector();
            String managerPhonesCount = (String)paramHashMap.get("manager_phones_count");
             count = Integer.parseInt(managerPhonesCount);
            for(int i = 1 ; i <= count ; i++)
            {
              String phone = (String)paramHashMap.get("manager_phones__R"+i+"__C1");
              managerPhones.add(phone);
            }
            Date OwnerBirthDate;
            POSDetailModel mdl = new POSDetailModel();
            mdl.setPosName(posName);
            mdl.setPOSCode(posCode);
            mdl.setPosAddress(posAddress);
            mdl.setPosEmail(posEmail);
            mdl.setUserID(UserID);
            mdl.setPosRegionID(posRegion);
            mdl.setPosPhones(POSPhones);
            mdl.setPosManagerPhones(managerPhones);
            mdl.setPosOwnerPhones(ownerPhones);
            mdl.setPosCommercialNumber(posCommercialNumber);
            mdl.setPosTaxID(posTaxID);
            mdl.setPosLegalFormID(posLegalForm);
            mdl.setPosPlaceTypeID(posPlace);
            mdl.setPosOwnerName(posOwnerName);
            mdl.setPosOwnerBirthDate(posOwnerBirthDate);
            mdl.setPosOwnerIDTypeID(posOwnerIDType);
            mdl.setPosOwnerIDNumber(posOwnerIDNumber+"");
            mdl.setPosManagerName(posManagerName);
            mdl.setPosManagerBirthDate(posManagerBirthDate);
            mdl.setPosManagerIDTypeID(posManagerIDType);
            mdl.setPosManagerIDNumber(posManagerIDNumber+"");
            mdl.setPosPartners(partnerVector);
            
           /*for(int j=0; j<paramHashMap.size(); j++)
            {
             String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
             String keyValue = (String)paramHashMap.get(tempStatusString);
              Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
            }*/

            PosDAO dao = new PosDAO();
            dao.addNewPOS(con , mdl,strFlagSuperAdmin);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            GenericModel statusModel = new GenericModel();
            Vector statusVector = new Vector();
            Vector cityVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_POS_STATUS_TYPE");
            statusVector = genericModelDAO.getModels(con , statusModel);
            cityVector = CityDAO.get_all_cities(con);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_CITY , cityVector);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_STATUS, statusVector);
            dataHashMap.put(DCMInterfaceKey.POS_SEARCH_STATUS , "");
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }

      }
        break;

        case DCM_SA_EDIT_POS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strPosID = (String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_POS_ID);
            int posID = Integer.parseInt(strPosID);
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("POS IDDDDDDDDDDD: "+ strPosID);
            Utility.logger.debug("USER ID:  "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            Vector regionVec =  new Vector();
            Vector legalFormVec =  new Vector();
            Vector IDTypeVec =  new Vector();
            Vector placeTypeVec =  new Vector();
            POSDetailModel posDetailModel = null;
            Vector POSPhones = new Vector();
            Vector OwnerPhones = new Vector();
            Vector ManagerPhones = new Vector();
            Vector POSPartner =  new Vector();
            GenericModel gm           = new GenericModel();
            GenericModel placeTypeGM  = new GenericModel();
            GenericModel IDTypeModel  = new GenericModel();
            
            GenericModelDAO gmDAO     = new GenericModelDAO();

            IDTypeModel = gmDAO.getColumns(con , "DCM_ID_TYPE");
            IDTypeVec = gmDAO.getModels(con , IDTypeModel);
            
            gm = gmDAO.getColumns(con , "DCM_LEGAL_FORM");
            legalFormVec = gmDAO.getModels(con , gm);
            placeTypeGM = gmDAO.getColumns(con , "DCM_POS_PLACE_TYPE");
            placeTypeVec = gmDAO.getModels(con , placeTypeGM);
            regionVec = RegionDAO.getAllRegions(con);
            dataHashMap.put(DCMInterfaceKey.VECTOR_ID_TYPE , IDTypeVec);
            dataHashMap.put(DCMInterfaceKey.VECTOR_LEGAL_FORM , legalFormVec);
            dataHashMap.put(DCMInterfaceKey.VECTOR_PLACE_TYPE ,placeTypeVec);
            dataHashMap.put(DCMInterfaceKey.VECTOR_REGIONS , regionVec);

            posDetailModel = PosDAO.getPOSByID(con , posID);
            POSPhones = PosDAO.getPOSPhones(con , posID);
            posDetailModel.setPosPhones(POSPhones);
            OwnerPhones = OwnerDAO.getOwnerPhones(con , posID);
            ManagerPhones = ManagerDAO.getManagerPhones(con , posID);
            posDetailModel.setPosManagerPhones(ManagerPhones);
            posDetailModel.setPosOwnerPhones(OwnerPhones);
            POSPartner = PosDAO.getPOSPartners(con , posID);
            posDetailModel.setPosPartners(POSPartner);
            dataHashMap.put(DCMInterfaceKey.POS_DETAIL_MODEL , posDetailModel);
            dataHashMap.put(DCMInterfaceKey.DCM_SAVE_POS_TYPE , DCMInterfaceKey.DCM_SAVE_POS_TYPE_EDIT);
            dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_POS_ID, strPosID);

            String strFlagSuperAdmin = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
            dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG,strFlagSuperAdmin);
            
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_USER_POS_LIST:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            GenericModel statusModel = new GenericModel();
            Vector statusVector = new Vector();
            Vector cityVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            
            statusModel = genericModelDAO.getColumns(con , "DCM_POS_STATUS_TYPE");
            statusVector = genericModelDAO.getModels(con , statusModel);
            
            cityVector = CityDAO.get_all_cities(con);

            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_CITY , cityVector);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_STATUS, statusVector);

            String strFlagSuperAdmin = "0";
            dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG,strFlagSuperAdmin);
            
          }
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_USER_VIEW_POS_DETAILS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            int posID = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_POS_ID));
            POSDetailModel acceptedPOSModel = new POSDetailModel();

            String strFlagSuperAdmin = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
            dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG,strFlagSuperAdmin);

            String selectStatus = "1";
            if(strFlagSuperAdmin.compareTo("1")==0)selectStatus = "2";
            
            Vector pendingModelVector = PosDAO.getPOSByIDandStatus(con , posID,selectStatus);
            acceptedPOSModel = PosDAO.getAcceptedPOSByID(con , posID);
            GenericModel statusModel = new GenericModel();
            Vector statusVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_POS_STATUS_TYPE");
            statusVector = GenericModelDAO.getModels(con , statusModel);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_STATUS,statusVector);
            dataHashMap.put(DCMInterfaceKey.DCM_ACCEPTED_POS , acceptedPOSModel);
            dataHashMap.put(DCMInterfaceKey.DCM_PENDING_POS , pendingModelVector);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_USER_VIEW_POS_RELATIONSHIPS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            int branchID = 0;
            if((String)paramHashMap.get(DCMInterfaceKey.DCM_BRANCHES_HIDDEN_ID)!=null)
              branchID = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.DCM_BRANCHES_HIDDEN_ID));
            BranchModel branchModel = BranchDAO.getBranchByID(con, branchID);
            dataHashMap.put(DCMInterfaceKey.DCM_BRANCHES_POS_VECTOR , branchModel);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_POS_GROUPS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            GenericModel genericModel = GenericModelDAO.getColumns(con,"DCM_GROUP_STATUS");
            Vector groupStatusVector = GenericModelDAO.getModels(con,genericModel);
            dataHashMap.put(DCMInterfaceKey.VECTOR_GROUP_STATUS , groupStatusVector);
            Vector groupFunctionVector = FunctionDAO.getFunctionsByFilter(con,"",1,"2");
            dataHashMap.put(DCMInterfaceKey.VECTOR_GROUP_FUNCTION , groupFunctionVector);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_NAME,"");          
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_NAME,"");
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_CODE,"");
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_STATUS,"0" );
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_FUNCTION_NAME,"0");
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_UPDATE_GROUP_STATUS:
        {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strGroupID = "";
            int groupID = 0;
            int groupStatusID = 0;
            int groupCurrentStatus = 0;
            for(int j=0; j<paramHashMap.size(); j++)
            {
             String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
             Utility.logger.debug("tempStatusString "+tempStatusString);
             String keyValue = (String)paramHashMap.get(tempStatusString);
             if(tempStatusString.startsWith(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_STATUS) &&
                !tempStatusString.equals(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_STATUS))
             {
                strGroupID = tempStatusString.substring(13);
                groupID = Integer.parseInt(strGroupID);
                groupStatusID = Integer.parseInt(keyValue);
                Utility.logger.debug("POS ID :"+groupID);
                Utility.logger.debug("POS new Status :"+groupStatusID);
                groupCurrentStatus = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_GROUP_STATUS+"_"+groupID));
                Utility.logger.debug("POS ID :"+groupID);
                Utility.logger.debug("POS new Status :"+groupStatusID);
                Utility.logger.debug("POS current Status :"+groupCurrentStatus);
             
               if(groupStatusID != groupCurrentStatus)
               {
                  GroupDAO.updateGroupStatus(con,groupID+"" , groupStatusID+"");                                    
               }
             }
            }
            String groupName = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_NAME);          
            String groupPOSName = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_NAME);
            String groupPOSCode = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_CODE);
            groupStatusID = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_STATUS));
            int groupFunctionID = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_FUNCTION_NAME));
            Vector groupSearchResult = GroupDAO.getGroupsByFilter(con,groupName,groupFunctionID,groupStatusID,groupPOSName,groupPOSCode);
            dataHashMap.put(DCMInterfaceKey.DCM_GROUP_SEARCH_RESULT , groupSearchResult);
            GenericModel genericModel = GenericModelDAO.getColumns(con,"DCM_GROUP_STATUS");
            Vector groupStatusVector = GenericModelDAO.getModels(con,genericModel);
            dataHashMap.put(DCMInterfaceKey.VECTOR_GROUP_STATUS , groupStatusVector);
            Vector groupFunctionVector = FunctionDAO.getFunctionsByFilter(con,"",1,"2");
            dataHashMap.put(DCMInterfaceKey.VECTOR_GROUP_FUNCTION , groupFunctionVector);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_NAME,groupName);          
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_NAME,groupPOSName);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_CODE,groupPOSCode);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_STATUS,groupStatusID+"" );
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_FUNCTION_NAME,groupFunctionID+"");

          
        }
        break;
        case DCM_EDIT_POS_GROUP:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String groupID = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_GROUP_ID);
            GroupModel groupModel = GroupDAO.getGroupByID(con , groupID);
            dataHashMap.put(DCMInterfaceKey.DCM_EDITED_GROUP_MODEL , groupModel);
           
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_ASSIGN_POS_TO_POS_GROUP:
         {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String groupID = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_GROUP_ID);
            GroupModel groupModel = GroupDAO.getGroupByID(con,groupID);
            dataHashMap.put(DCMInterfaceKey.DCM_EDITED_GROUP_MODEL , groupModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"");
         }  
         
        break;

        case DCM_ASSIGN_FUNCTION_TO_POS_GROUP:
        {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String groupID = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_GROUP_ID);
            Utility.logger.debug("eeeeeeeeeee+  "+ groupID);
            GroupModel groupModel = GroupDAO.getGroupByID(con,groupID);
            if(groupModel == null)
            Utility.logger.debug("eeeeeeeeeee+  "+ groupID);
            dataHashMap.put(DCMInterfaceKey.DCM_EDITED_GROUP_MODEL , groupModel);
            Vector functionVector = FunctionDAO.getFunctionsByFilter(con,"",1,"2");
            dataHashMap.put(DCMInterfaceKey.DCM_GROUP_FUNCTION_VECTOR , functionVector);
            GenericModel targetModel  = GenericModelDAO.getColumns(con,"DCM_TARGET_DURATION_TYPE");
            Vector targetTypes = GenericModelDAO.getModels(con , targetModel);
            dataHashMap.put(DCMInterfaceKey.VECTOR_GROUP_FUNCTION_TARGET , targetTypes);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE , "");
         
         }
        break;

        case DCM_POS_FUNCTIONS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            GenericModel genericModel = GenericModelDAO.getColumns(con,"DCM_FUNCTION_STATUS");
            Vector functionStatus = GenericModelDAO.getModels(con, genericModel);
            dataHashMap.put(DCMInterfaceKey.DCM_FUNCTION_STATUS_VECTOR , functionStatus);
            
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_EDIT_POS_FUNCTION:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            int functionID = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.DCM_FUNCTION_HIDDEN_ID));
            FunctionModel functionModel =  FunctionDAO.get_function_by_id(con , functionID);
            dataHashMap.put(DCMInterfaceKey.DCM_EDITED_FUNCTION_MODEL , functionModel);

          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_CREATE_NEW_POS_FUNCTION:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            FunctionDAO.getFunctionsByFilter(con,"",0,"2");
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_POS_BRANCHES:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            GenericModel branchStatusModel = GenericModelDAO.getColumns(con, "DCM_BRANCH_STATUS");
            Vector branchStausVector = GenericModelDAO.getModels(con ,branchStatusModel);
             dataHashMap.put(DCMInterfaceKey.DCM_POS_BRANCH_STATUS_VECTOR , branchStausVector);
             dataHashMap.put(DCMInterfaceKey.DCM_SEARCH_FLAG,false+"");
            
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_EDIT_USER_ACCOUNT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            
            CachEngine.removeObject(UserInterfaceKey.CACH_OBJ_USER_LIST);
            //UserCMPHome userCMPHome = (UserCMPHome)Utility.getEJBHome("UserCMP", 
            //          "com.mobinil.sds.core.system.sa.users.dao.cmp.UserCMPHome");
            //UserCMP userCMPRemote = userCMPHome.findByPrimaryKey(
            //                            new Long((String)paramHashMap.get(
            //                                UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID)));

            Vector userVector = UserDAO.getUsersListByUserId(strUserID);
            UserDTO newUserDTO = (UserDTO)userVector.get(0);
            UserModel newUserModel = newUserDTO.getUserModel();
            PersonModel newPersonModel = newUserModel.getPersonModel();    
          
            /*int userID = userCMPRemote.getUser_id().intValue();
            int userStatusID = userCMPRemote.getUser_status_id().intValue();
            String strUserStatusName = userCMPRemote.getUser_status_name();
            PersonModel newPersonModel = new PersonModel(userID,
                                                        userCMPRemote.getPerson_full_name(),
                                                        userCMPRemote.getPerson_address(),
                                                        userCMPRemote.getPerson_status_id().intValue(),
                                                        userCMPRemote.getPerson_status_name(),
                                                        userCMPRemote.getPerson_type_id().intValue(),
                                                        userCMPRemote.getPerson_type_name(),
                                                        userCMPRemote.getPerson_type_status_id().intValue(),
                                                        userCMPRemote.getPerson_type_status_name(),
                                                        userCMPRemote.getPerson_email());
            UserModel newUserModel = new UserModel(newPersonModel, userStatusID, strUserStatusName);
            */
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, newUserModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, UserDAO.getUserStatuses(con));

            int intDcmUserLevelTypeId = DCMUserDAO.getDCMUserLevelTypeId(con,strUserID);
            dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_LEVEL_TYPE_ID,intDcmUserLevelTypeId+"");
            
            Vector regions = RegionDAO.getAllRegions(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,regions);

            DCMUserDetailModel dcmUserDetailModel = DCMUserDAO.getDCMUserDetailById(con,strUserID);
            dataHashMap.put(DCMInterfaceKey.DCM_HASHMAP_KEY_COLLECTION,dcmUserDetailModel);
            
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_UPDATE_DCM_USER:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            int intUserID = Integer.parseInt(strUserID);
            
            CachEngine.removeObject(UserInterfaceKey.CACH_OBJ_USER_LIST);

            int intDcmUserLevelTypeId = DCMUserDAO.getDCMUserLevelTypeId(con,strUserID);
            dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_LEVEL_TYPE_ID,intDcmUserLevelTypeId+"");

            String strPersonName = (String)paramHashMap.get(UserInterfaceKey.CONTROL_TEXT_NAME_USER_NAME);
            //String strPersonAddress = (String)paramHashMap.get(UserInterfaceKey.CONTROL_TEXT_NAME_USER_ADDRESS);
            String strPersonEmail = (String)paramHashMap.get(UserInterfaceKey.CONTROL_TEXT_NAME_USER_EMAIL);

            String strUserAddress = (String)paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_DCM_USER_ADDRESS);
            String strPersonAddress = strUserAddress;
            String strUserMobile = (String)paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_DCM_USER_MOBILE);
            String strRegionId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_REGION_ID);
            //System.out.println("The region ID issssssssssss " + strRegionId);

            int intDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);

            boolean checkUserDetailPending = DCMUserDAO.checkDcmUserDetail(con,intUserID,"4");

            Long lDcmUserDetailId = null;
            if(checkUserDetailPending && intDcmUserLevelTypeId == 3)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"There is a pending request exists,any changes made will be lost.");
            }
            else
            {
              //insert user_detail
              lDcmUserDetailId = DCMUserDAO.insertDcmUserDetail(con,intUserID,strPersonName,strUserAddress,strPersonEmail,strUserMobile,strRegionId,"4",strUserID);
              if(intDcmUserLevelTypeId == 3)
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Change request have been made.");
              }
            }
            
            if(intDcmUserLevelTypeId == 0 || intDcmUserLevelTypeId == 1 || intDcmUserLevelTypeId == 2)
            {
              //if admin update dcm_user set user_detail_id = the new user_detail
              DCMUserDAO.updateDcmUser(con,strUserID,lDcmUserDetailId+"");
              
              //if admin update vw_gen_user_details
              int nPersonID = new Integer((String)paramHashMap.get(UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID)).intValue();
              Long lPersonID = new Long((long)nPersonID);
              String serverName = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_NAME);
              String serverPort = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_PORT);
              String appName = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_CONTEXT_PATH);

              int userStatusID = new Integer((String)paramHashMap.get(UserInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+nPersonID)).intValue();

              //PersonModel newPersonModel = new PersonModel(nPersonID, strPersonName, strPersonAddress, strPersonEmail);
              PersonModel newPersonModel = UserDAO.getPerson(con,nPersonID);
          
              UserModel newUserModel = null;
              //UserCMPHome userCMPHome = (UserCMPHome)Utility.getEJBHome("UserCMP", 
              //          "com.mobinil.sds.core.system.sa.users.dao.cmp.UserCMPHome");
              //UserCMP userCMPRemote = userCMPHome.findByPrimaryKey(lPersonID);
              //Utility.logger.debug("Old pass -->"+userCMPRemote.getPassword());
              Vector userVector = UserDAO.getUsersListByUserId(nPersonID+"");
              UserDTO newUserDTO = (UserDTO)userVector.get(0);
              UserModel newUserModelX = newUserDTO.getUserModel();
              PersonModel newPersonModelX = newUserModelX.getPersonModel();    
              try 
              {
                //if(userCMPRemote.getPerson_email().compareToIgnoreCase(strPersonEmail) !=0)
                if(newPersonModelX.getPersonEMail().compareToIgnoreCase(strPersonEmail) !=0)
                {
                  String strUserPassword = Math.random()+"";
                  strUserPassword = strUserPassword.substring(strUserPassword.lastIndexOf(".")+1,strUserPassword.lastIndexOf(".")+7);
                  Utility.logger.debug("Password"+strUserPassword);
                  String strEncryptedUserPassword = EncryptUtil.encryptIt(strUserPassword + strPersonEmail);
                  newUserModel = UserDAO.getUser(con,nPersonID,newPersonModel);
                  Utility.sendPasswordByMail(serverName, serverPort, appName, strPersonEmail, strPersonName, strUserPassword);
                  dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, 
                                  "User Data was saved successfully .The new password will be sent to the new email provided.");
                }
                else
                {
                  newUserModel = UserDAO.getUser(con,nPersonID,newPersonModel);
                }
                //userCMPRemote.updateUser(newUserModel);
                int user_id = newPersonModel.getPersonID();
                String user_login = newPersonModel.getPersonEMail();
                String password = newUserModel.getUserPassword();
                int user_status_id = newUserModel.getUserStatusID();
                String user_status_name = newUserModel.getUserStatusName();
                String person_full_name = newPersonModel.getPersonFullName();
                String person_address = newPersonModel.getPersonAddress();
                int person_status_id = newPersonModel.getPersonStatusID();
                String person_status_name = newPersonModel.getPersonStatusName();
                int person_type_id = newPersonModel.getPersonTypeID();
                String person_type_name = newPersonModel.getPersonTypeName();
                int person_type_status_id = newPersonModel.getPersonTypeStatusID();
                String person_type_status_name = newPersonModel.getPersonTypeStatusName();
                UserDAO.updateVwGenUserDetails(user_id,strPersonEmail,password,userStatusID,user_status_name,strPersonName,strPersonAddress,person_status_id,person_status_name,person_type_id,person_type_name,person_type_status_id,person_type_status_name);
                //Utility.logger.debug("New pass -->"+userCMPRemote.getPassword());
              } 
              catch (Exception ex) 
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Another User with the same E-Mail already exists");
              }
            }

            //UserCMPHome userCMPHome = (UserCMPHome)Utility.getEJBHome("UserCMP", 
            //          "com.mobinil.sds.core.system.sa.users.dao.cmp.UserCMPHome");
            //UserCMP userCMPRemote = userCMPHome.findByPrimaryKey(
            //                            new Long((String)paramHashMap.get(
            //                                UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID)));

            Vector userVector = UserDAO.getUsersListByUserId(strUserID);
            UserDTO newUserDTO = (UserDTO)userVector.get(0);
            UserModel newUserModel = newUserDTO.getUserModel();
            PersonModel newPersonModel = newUserModel.getPersonModel();    
          
            /*int userID = userCMPRemote.getUser_id().intValue();
            int userStatusID = userCMPRemote.getUser_status_id().intValue();
            String strUserStatusName = userCMPRemote.getUser_status_name();
            PersonModel newPersonModel = new PersonModel(userID,
                                                        userCMPRemote.getPerson_full_name(),
                                                        userCMPRemote.getPerson_address(),
                                                        userCMPRemote.getPerson_status_id().intValue(),
                                                        userCMPRemote.getPerson_status_name(),
                                                        userCMPRemote.getPerson_type_id().intValue(),
                                                        userCMPRemote.getPerson_type_name(),
                                                        userCMPRemote.getPerson_type_status_id().intValue(),
                                                        userCMPRemote.getPerson_type_status_name(),
                                                        userCMPRemote.getPerson_email());
            UserModel newUserModel = new UserModel(newPersonModel, userStatusID, strUserStatusName);
            */
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, newUserModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, UserDAO.getUserStatuses(con));

            
            Vector regions = RegionDAO.getAllRegions(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,regions);

            DCMUserDetailModel dcmUserDetailModel = DCMUserDAO.getDCMUserDetailById(con,strUserID);
            dataHashMap.put(DCMInterfaceKey.DCM_HASHMAP_KEY_COLLECTION,dcmUserDetailModel);
            
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_ADMIN_EDIT_SALES_AGENT:
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

        case DCM_ADMIN_CREATE_NEW_SALES_AGENT:
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

        case DCM_ASSIGN_TARGET_TO_GROUP:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String userIdAssignedTo = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID);
            DCMUserDetailModel dcmUserDetailModel = DCMUserDAO.getDCMUserDetailById(con,userIdAssignedTo);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserDetailModel);
            Vector functionsAssignedTo = DCMUserDAO.getDcmUserAssignedFunctionModels(con,userIdAssignedTo);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionsAssignedTo);
            Vector groupsList = GroupDAO.getGroupsByFilter(con,"",0,0,"","");
            Utility.logger.debug("group vector size : " +groupsList.size());
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,groupsList);

            GenericModel targetPeriodModel = new GenericModel();
            Vector dcmTargetPeriodVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            targetPeriodModel = genericModelDAO.getColumns(con , "DCM_TARGET_DURATION_TYPE");
            dcmTargetPeriodVector = genericModelDAO.getModels(con , targetPeriodModel);
            dataHashMap.put(DCMInterfaceKey.DCM_HASHMAP_KEY_COLLECTION,dcmTargetPeriodVector);

            Vector userGroupFunctionTarget = DCMUserDAO.getDCMUserGroupFunctionTargetByUser(con,userIdAssignedTo);
            dataHashMap.put(DCMInterfaceKey.DCM_HASHMAP_KEY_ADDITIONAL_COLLECTION,userGroupFunctionTarget);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_MANAGERS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector dcmUserManagersList = new Vector();
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManager(con,strDcmUserId,strUserID);
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserManagersList);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_USER_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            GenericModel userTypeModel = new GenericModel();
            Vector dcmUserTypeVector = new Vector();
            userTypeModel = genericModelDAO.getColumns(con , "DCM_USER_LEVEL_TYPE");
            dcmUserTypeVector = genericModelDAO.getModels(con , userTypeModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmUserTypeVector);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_CHANGE_USER_DETAIL_STATUS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String dcmUserDetailId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_DETAIL_ID);
            String dcmUserDetailStatusId  = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_DETAIL_STATUS_ID);
            String changedUserId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID);
            
            String strPersonEmail = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_EMAIL);
            String strPersonName = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_NAME);
            String strPersonAddress = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ADDRESS);
            
            if(dcmUserDetailStatusId.compareTo(DCMInterfaceKey.CONST_DCM_USER_DETIAL_STATUS_ACTIVE_ID)==0)
            {
              //change user to this user_detail_id
              //and change in vw_gen_user_details
              
              //if admin update dcm_user set user_detail_id = the new user_detail
              DCMUserDAO.updateDcmUser(con,changedUserId,dcmUserDetailId);
              
              //if admin update vw_gen_user_details
              int nPersonID = new Integer(changedUserId).intValue();
              Long lPersonID = new Long((long)nPersonID);
              String serverName = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_NAME);
              String serverPort = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_PORT);
              String appName = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_CONTEXT_PATH);

              int userStatusID = new Integer(dcmUserDetailStatusId).intValue();

              //PersonModel newPersonModel = new PersonModel(nPersonID, strPersonName, strPersonAddress, strPersonEmail);
              PersonModel newPersonModel = UserDAO.getPerson(con,nPersonID);
          
              UserModel newUserModel = null;
              //UserCMPHome userCMPHome = (UserCMPHome)Utility.getEJBHome("UserCMP", 
              //          "com.mobinil.sds.core.system.sa.users.dao.cmp.UserCMPHome");
              //UserCMP userCMPRemote = userCMPHome.findByPrimaryKey(lPersonID);
              //Utility.logger.debug("Old pass -->"+userCMPRemote.getPassword());
              Vector userVector = UserDAO.getUsersListByUserId(nPersonID+"");
              UserDTO newUserDTO = (UserDTO)userVector.get(0);
              UserModel newUserModelX = newUserDTO.getUserModel();
              PersonModel newPersonModelX = newUserModelX.getPersonModel();    
              try 
              {
                //if(userCMPRemote.getPerson_email().compareToIgnoreCase(strPersonEmail) !=0)
                if(newPersonModelX.getPersonEMail().compareToIgnoreCase(strPersonEmail) !=0)
                {
                  String strUserPassword = Math.random()+"";
                  strUserPassword = strUserPassword.substring(strUserPassword.lastIndexOf(".")+1,strUserPassword.lastIndexOf(".")+7);
                  Utility.logger.debug("Password"+strUserPassword);
                  String strEncryptedUserPassword = EncryptUtil.encryptIt(strUserPassword + strPersonEmail);
                  newUserModel = UserDAO.getUser(con,nPersonID,newPersonModel);
                  Utility.sendPasswordByMail(serverName, serverPort, appName, strPersonEmail, strPersonName, strUserPassword);
                  dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, 
                                  "User Data was saved successfully .The new password will be sent to the new email provided.");
                }
                else
                {
                  newUserModel = UserDAO.getUser(con,nPersonID,newPersonModel);
                }
                //userCMPRemote.updateUser(newUserModel);
                int user_id = newPersonModel.getPersonID();
                String user_login = newPersonModel.getPersonEMail();
                String password = newUserModel.getUserPassword();
                int user_status_id = newUserModel.getUserStatusID();
                String user_status_name = newUserModel.getUserStatusName();
                String person_full_name = newPersonModel.getPersonFullName();
                String person_address = newPersonModel.getPersonAddress();
                int person_status_id = newPersonModel.getPersonStatusID();
                String person_status_name = newPersonModel.getPersonStatusName();
                int person_type_id = newPersonModel.getPersonTypeID();
                String person_type_name = newPersonModel.getPersonTypeName();
                int person_type_status_id = newPersonModel.getPersonTypeStatusID();
                String person_type_status_name = newPersonModel.getPersonTypeStatusName();
                UserDAO.updateVwGenUserDetails(user_id,strPersonEmail,password,userStatusID,user_status_name,strPersonName,strPersonAddress,person_status_id,person_status_name,person_type_id,person_type_name,person_type_status_id,person_type_status_name);
                //Utility.logger.debug("New pass -->"+userCMPRemote.getPassword());
              } 
              catch (Exception ex) 
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Another User with the same E-Mail already exists");
              }
            
            }
            else if(dcmUserDetailStatusId.compareTo(DCMInterfaceKey.CONST_DCM_USER_DETIAL_STATUS_REJECTED_ID)==0)
            {
              //change this user_detail_id to rejected
              DCMUserDAO.updateDcmUserDetailStatus(con,dcmUserDetailId,dcmUserDetailStatusId);
            }

            Vector dcmUserManagersList = new Vector();
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManager(con,strDcmUserId,strUserID);
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserManagersList);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_USER_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            GenericModel userTypeModel = new GenericModel();
            Vector dcmUserTypeVector = new Vector();
            userTypeModel = genericModelDAO.getColumns(con , "DCM_USER_LEVEL_TYPE");
            dcmUserTypeVector = genericModelDAO.getModels(con , userTypeModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmUserTypeVector);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case SEARCH_DCM_MANAGERS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String searchDcmUserName = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME,searchDcmUserName);    
            String searchDcmUserStatusTypeId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_LIST_STATUS);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_LIST_STATUS,searchDcmUserStatusTypeId);
            String searchDcmUserLevelTypeId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_TYPE);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_TYPE,searchDcmUserLevelTypeId);    
            
            Vector dcmUserManagersList = new Vector();
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManagerByFilter(con,strDcmUserId,strUserID,searchDcmUserName,searchDcmUserStatusTypeId,searchDcmUserLevelTypeId,"","");
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserManagersList);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_USER_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            GenericModel userTypeModel = new GenericModel();
            Vector dcmUserTypeVector = new Vector();
            userTypeModel = genericModelDAO.getColumns(con , "DCM_USER_LEVEL_TYPE");
            dcmUserTypeVector = genericModelDAO.getModels(con , userTypeModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmUserTypeVector);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_UPDATE_DCM_USER_STATUS_TYPE:
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
              if(tempStatusString.startsWith(DCMInterfaceKey.INPUT_SELECT_DCM_USER_LIST_STATUS))
              {
                int strlength = DCMInterfaceKey.INPUT_SELECT_DCM_USER_LIST_STATUS.length() + 1;
                String strDcmUserId = tempStatusString.substring(strlength, tempStatusString.length());
                String dcmUsertOldStatusId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_LIST_STATUS+"_"+strDcmUserId);
                if(dcmUsertOldStatusId.compareTo(keyValue)!=0)
                {
                  //update
                  DCMUserDAO.updateDcmUserStatusType(con,strDcmUserId,keyValue);
                }
              }
            }

            Vector dcmUserManagersList = new Vector();
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManager(con,strDcmUserId,strUserID);
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserManagersList);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_USER_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            GenericModel userTypeModel = new GenericModel();
            Vector dcmUserTypeVector = new Vector();
            userTypeModel = genericModelDAO.getColumns(con , "DCM_USER_LEVEL_TYPE");
            dcmUserTypeVector = genericModelDAO.getModels(con , userTypeModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmUserTypeVector);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_SAVE_ASSIGN_SALES_AGENT_TO_MANAGER:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strDcmAssignedUserId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID);
            
            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              String keyValue = (String)paramHashMap.get(tempStatusString);
              if(tempStatusString.startsWith("user_defined_data_view__R") && tempStatusString.endsWith("__C3"))
              {
                String tempString2 = tempStatusString.substring(0,tempStatusString.length()-1);
                tempString2 += "1";
                if(paramHashMap.containsKey(tempString2))
                {
                  String keyValue2 = (String)paramHashMap.get(tempString2);
                  if(keyValue2.compareTo(keyValue)!=0)
                  {
                    //remove
                    DCMUserDAO.deleteDcmUserAsignedUser(con,strDcmAssignedUserId,keyValue);
                  }
                }
                else
                {
                  //remove
                  DCMUserDAO.deleteDcmUserAsignedUser(con,strDcmAssignedUserId,keyValue);
                }
              }
            }

            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              String keyValue = (String)paramHashMap.get(tempStatusString);
              if(tempStatusString.startsWith("user_defined_data_view__R") && tempStatusString.endsWith("__C1"))
              {
                String tempString2 = tempStatusString.substring(0,tempStatusString.length()-1);
                tempString2 += "3";
                String keyValue3 = (String)paramHashMap.get(tempString2);
                if(keyValue3 == null)
                {
                  DCMUserDAO.insertDcmUserAsignedUser(con,strDcmAssignedUserId,keyValue);
                }
                else
                {
                  if(keyValue3.compareTo(keyValue)!=0)
                  {
                    DCMUserDAO.insertDcmUserAsignedUser(con,strDcmAssignedUserId,keyValue);
                  }
                }
              }
            }
            
            Vector dcmUserManagersList = new Vector();
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManager(con,strDcmUserId,strUserID);
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserManagersList);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_USER_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            GenericModel userTypeModel = new GenericModel();
            Vector dcmUserTypeVector = new Vector();
            userTypeModel = genericModelDAO.getColumns(con , "DCM_USER_LEVEL_TYPE");
            dcmUserTypeVector = genericModelDAO.getModels(con , userTypeModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmUserTypeVector);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_ASSIGN_SALES_AGENT_TO_MANAGER:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String userIdAssignedTo = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID);
            String userIdAssignedToLevelTypeId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_LEVEL_TYPE_ID);
            Vector usersAssingedToSameManagerButNextLevel = new Vector();
            if(userIdAssignedToLevelTypeId.compareTo(DCMInterfaceKey.CONST_DCM_USER_LEVEL_TYPE_REGIONAL_MANAGER_ID)==0)
            {
              usersAssingedToSameManagerButNextLevel = DCMUserDAO.getDCMUserForRegionalManager(con,strUserID);
            }
            else if(userIdAssignedToLevelTypeId.compareTo(DCMInterfaceKey.CONST_DCM_USER_LEVEL_TYPE_MANAGER_ID)==0)
            {
              usersAssingedToSameManagerButNextLevel = DCMUserDAO.getDCMUserForManager(con,strUserID,userIdAssignedTo);
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,usersAssingedToSameManagerButNextLevel);
            DCMUserDetailModel dcmUserDetailModel = DCMUserDAO.getDCMUserDetailById(con,userIdAssignedTo);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserDetailModel);
            Vector usersAssignedTo = DCMUserDAO.getDcmUserAssignedUser(con,userIdAssignedTo);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,usersAssignedTo);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_CREATE_NEW_MANAGER:
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

        case DCM_MANAGER_SALES_AGENTS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector dcmUserManagersList = new Vector();
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManagerByFilter(con,strDcmUserId,strUserID,"","","3","","");
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserManagersList);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_USER_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_SEARCH_MANAGER_SALES_AGENTS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector dcmUserManagersList = new Vector();

            String searchDcmUserName = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME,searchDcmUserName);    
            String searchDcmUserStatusTypeId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_LIST_STATUS);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_LIST_STATUS,searchDcmUserStatusTypeId);
            
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManagerByFilter(con,strDcmUserId,strUserID,searchDcmUserName,searchDcmUserStatusTypeId,"3","","");
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserManagersList);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_USER_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_MANAGER_VIEW_SALES_AGENT_TARGET:
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

        case DCM_MANAGER_EDIT_SALES_AGENT_TARGET:
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
        case DCM_SALES_AGENT_VISITS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector dcmUserManagersList = new Vector();
            Vector vecSalesAgentsVisitPlans = new Vector();
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManagerByFilter(con,strDcmUserId,strUserID,"","","3","","");
            }
            if(dcmUserManagersList.size()!=0)
            {
              vecSalesAgentsVisitPlans = DCMUserDAO.getDCMVisitPlanByManagerUserId(con,dcmUserManagersList);
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecSalesAgentsVisitPlans);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_VISIT_PLAN_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionList);  
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_SEARCH_SALES_AGENT_VISITS_FOR_AGENT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            
            String searchPosCode = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE,searchPosCode);
            String searchPosName = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME,searchPosName);
            String searchFunctionId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_FUNCTION_ID);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_FUNCTION_ID,searchFunctionId);
            String searchVisitStatusId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_VISIT_STATUS_ID);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_VISIT_STATUS_ID,searchVisitStatusId);
            String searchCreationDateFrom = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_FROM);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_FROM,searchCreationDateFrom);
            String searchCreationDateTo = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_TO);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_TO,searchCreationDateTo);
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            Vector vecSalesAgentsVisitPlans = DCMUserDAO.getDCMVisitPlanByFilter(con,strUserID,"",searchPosCode,searchPosName,searchFunctionId,searchVisitStatusId,searchCreationDateFrom,searchCreationDateTo);
            
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecSalesAgentsVisitPlans);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_VISIT_PLAN_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionList);  
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_SEARCH_SALES_AGENT_VISITS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector dcmUserManagersList = new Vector();
            Vector vecSalesAgentsVisitPlans = new Vector();

            String searchSalesAgentName = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME,searchSalesAgentName);
            String searchPosCode = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE,searchPosCode);
            String searchPosName = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME,searchPosName);
            String searchFunctionId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_FUNCTION_ID);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_FUNCTION_ID,searchFunctionId);
            String searchVisitStatusId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_VISIT_STATUS_ID);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_VISIT_STATUS_ID,searchVisitStatusId);
            String searchCreationDateFrom = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_FROM);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_FROM,searchCreationDateFrom);
            String searchCreationDateTo = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_TO);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_TO,searchCreationDateTo);
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManagerByFilter(con,strDcmUserId,strUserID,"","","3","","");
            }
            if(dcmUserManagersList.size()!=0)
            {
              vecSalesAgentsVisitPlans = DCMUserDAO.getDCMVisitPlanByFilter(con,dcmUserManagersList,searchSalesAgentName,searchPosCode,searchPosName,searchFunctionId,searchVisitStatusId,searchCreationDateFrom,searchCreationDateTo);
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecSalesAgentsVisitPlans);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_VISIT_PLAN_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionList);  
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_UPDATE_SALES_AGENT_VISITS_STATUS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              if(tempStatusString.startsWith(DCMInterfaceKey.INPUT_SELECT_VISIT_STATUS_ID))
              {
                String strNewStatusId = (String)paramHashMap.get(tempStatusString);
                int strlength = DCMInterfaceKey.INPUT_SELECT_VISIT_STATUS_ID.length() + 1;
                String strVisitPlanId = tempStatusString.substring(strlength, tempStatusString.length());
                String strOldStatusId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_VISIT_STATUS_ID+"_"+strVisitPlanId);
                if(strNewStatusId.compareTo(strOldStatusId)!=0)
                {
                  DCMUserDAO.updateDcmVisitPlan(con,strNewStatusId,strVisitPlanId);
                }
              }
            }

            Vector dcmUserManagersList = new Vector();
            Vector vecSalesAgentsVisitPlans = new Vector();
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManagerByFilter(con,strDcmUserId,strUserID,"","","3","","");
            }
            if(dcmUserManagersList.size()!=0)
            {
              vecSalesAgentsVisitPlans = DCMUserDAO.getDCMVisitPlanByManagerUserId(con,dcmUserManagersList);
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecSalesAgentsVisitPlans);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_VISIT_PLAN_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionList);  
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_SAVE_VISIT_PLAN_DETAILS_FOR_AGENT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String visitPlanId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_VISIT_PLAN_ID);
            String visitPlanPOSCode = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_CODE);
            String visitPlanUserId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID);
            String visitPlanFunctionId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SELECT_FUNCTION_ID);
            String visitPlanComments = (String)paramHashMap.get(DCMInterfaceKey.INPUT_TEXTAREA_VISIT_PLAN_COMMENTS);
            String visitPlanDate = (String)paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_VISIT_DATE);
            String visitPlanStatusTypeId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SELECT_VISIT_STATUS_ID);

            DCMModel dcmModel = DCMDao.getDCMModel(con,visitPlanPOSCode);
            if(dcmModel == null)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"POS Code does not exist in the system.");  
            }
            else
            {
              int visitPlanPOSId = dcmModel.getDcmId();
              if(visitPlanId.compareTo("")==0)
              {
                //insert
                DCMUserDAO.insertDcmVisitPlan(con,visitPlanDate,visitPlanPOSId+"",visitPlanUserId,visitPlanFunctionId,visitPlanComments,strUserID,visitPlanStatusTypeId);
              }
              else
              {
                //update
                DCMUserDAO.updateDcmVisitPlan(con,visitPlanDate,visitPlanPOSId+"",visitPlanUserId,visitPlanFunctionId,visitPlanComments,strUserID,visitPlanStatusTypeId,visitPlanId);
              }
            }


            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            Vector vecSalesAgentsVisitPlans = DCMUserDAO.getDCMVisitPlanByManagerUserId(con,strUserID);
            
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecSalesAgentsVisitPlans);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_VISIT_PLAN_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionList);  
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_SAVE_VISIT_PLAN_DETAILS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String visitPlanId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_VISIT_PLAN_ID);
            String visitPlanPOSCode = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_CODE);
            String visitPlanUserId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID);
            String visitPlanFunctionId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SELECT_FUNCTION_ID);
            String visitPlanComments = (String)paramHashMap.get(DCMInterfaceKey.INPUT_TEXTAREA_VISIT_PLAN_COMMENTS);
            String visitPlanDate = (String)paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_VISIT_DATE);
            String visitPlanStatusTypeId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SELECT_VISIT_STATUS_ID);

            DCMModel dcmModel = DCMDao.getDCMModel(con,visitPlanPOSCode);
            if(dcmModel == null)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"POS Code does not exist in the system.");  
            }
            else
            {
              int visitPlanPOSId = dcmModel.getDcmId();
              if(visitPlanId.compareTo("")==0)
              {
                //insert
                DCMUserDAO.insertDcmVisitPlan(con,visitPlanDate,visitPlanPOSId+"",visitPlanUserId,visitPlanFunctionId,visitPlanComments,strUserID,visitPlanStatusTypeId);
              }
              else
              {
                //update
                DCMUserDAO.updateDcmVisitPlan(con,visitPlanDate,visitPlanPOSId+"",visitPlanUserId,visitPlanFunctionId,visitPlanComments,strUserID,visitPlanStatusTypeId,visitPlanId);
              }
            }


            Vector dcmUserManagersList = new Vector();
            Vector vecSalesAgentsVisitPlans = new Vector();
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManagerByFilter(con,strDcmUserId,strUserID,"","","3","","");
            }
            if(dcmUserManagersList.size()!=0)
            {
              vecSalesAgentsVisitPlans = DCMUserDAO.getDCMVisitPlanByManagerUserId(con,dcmUserManagersList);
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecSalesAgentsVisitPlans);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_VISIT_PLAN_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionList);  
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_EDIT_SALES_AGENT_VISIT_FOR_AGENT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector dcmUserManagersList = new Vector();
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManagerByFilter(con,strDcmUserId,strUserID,"","","3","","");
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserManagersList);

            Vector vecUserAssignedFunctions = DCMUserDAO.getDcmUserAssignedFunctions(con,strUserID);
            HashMap hashMapUserAssignedFunctions = new HashMap();
            hashMapUserAssignedFunctions.put(strUserID,vecUserAssignedFunctions);
            dataHashMap.put(DCMInterfaceKey.DCM_HASHMAP_KEY_ADDITIONAL_COLLECTION,hashMapUserAssignedFunctions);
            

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_VISIT_PLAN_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionList); 

            String visitPlanId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_VISIT_PLAN_ID);
            VisitPlanModel visitPlanModel = DCMUserDAO.getDCMVisitPlanById(con,visitPlanId);
            dataHashMap.put(DCMInterfaceKey.DCM_HASHMAP_KEY_COLLECTION,visitPlanModel);

            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,DCMInterfaceKey.ACTION_DCM_SAVE_VISIT_PLAN_DETAILS_FOR_AGENT);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_EDIT_SALES_AGENT_VISIT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector dcmUserManagersList = new Vector();
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManagerByFilter(con,strDcmUserId,strUserID,"","","3","","");
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserManagersList);

            HashMap usersAssignedFunctions = DCMUserDAO.getDcmUsersAssignedFunctions(con,dcmUserManagersList);
            dataHashMap.put(DCMInterfaceKey.DCM_HASHMAP_KEY_ADDITIONAL_COLLECTION,usersAssignedFunctions);
            

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_VISIT_PLAN_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionList); 

            String visitPlanId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_VISIT_PLAN_ID);
            VisitPlanModel visitPlanModel = DCMUserDAO.getDCMVisitPlanById(con,visitPlanId);
            dataHashMap.put(DCMInterfaceKey.DCM_HASHMAP_KEY_COLLECTION,visitPlanModel);

            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,DCMInterfaceKey.ACTION_DCM_SAVE_VISIT_PLAN_DETAILS);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_CREATE_NEW_SALES_AGENT_VISIT_FOR_AGENT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector dcmUserManagersList = new Vector();
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManagerByFilter(con,strDcmUserId,strUserID,"","","3","","");
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserManagersList);

            Vector vecUserAssignedFunctions = DCMUserDAO.getDcmUserAssignedFunctions(con,strUserID);
            HashMap hashMapUserAssignedFunctions = new HashMap();
            hashMapUserAssignedFunctions.put(strUserID,vecUserAssignedFunctions);
            dataHashMap.put(DCMInterfaceKey.DCM_HASHMAP_KEY_ADDITIONAL_COLLECTION,hashMapUserAssignedFunctions);
            
            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_VISIT_PLAN_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionList);

            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,DCMInterfaceKey.ACTION_DCM_SAVE_VISIT_PLAN_DETAILS_FOR_AGENT);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_CREATE_NEW_SALES_AGENT_VISIT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector dcmUserManagersList = new Vector();
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManagerByFilter(con,strDcmUserId,strUserID,"","","3","","");
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserManagersList);
            
            HashMap usersAssignedFunctions = DCMUserDAO.getDcmUsersAssignedFunctions(con,dcmUserManagersList);
            dataHashMap.put(DCMInterfaceKey.DCM_HASHMAP_KEY_ADDITIONAL_COLLECTION,usersAssignedFunctions);
            
            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_VISIT_PLAN_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionList);

            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,DCMInterfaceKey.ACTION_DCM_SAVE_VISIT_PLAN_DETAILS);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_ADMIN_SALES_AGENTS_ASSIGN_FUNCTION:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector dcmUserManagersList = new Vector();
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManagerByFilter(con,strDcmUserId,strUserID,"","","3","","");
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserManagersList);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_USER_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_SAVE_ASSIGN_FUNCTION_TO_SALES_AGENT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector dcmUserManagersList = new Vector();

            String strDcmAssignedUserId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID);
            
            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              String keyValue = (String)paramHashMap.get(tempStatusString);
              if(tempStatusString.startsWith("user_defined_data_view__R") && tempStatusString.endsWith("__C3"))
              {
                String tempString2 = tempStatusString.substring(0,tempStatusString.length()-1);
                tempString2 += "1";
                if(paramHashMap.containsKey(tempString2))
                {
                  String keyValue2 = (String)paramHashMap.get(tempString2);
                  if(keyValue2.compareTo(keyValue)!=0)
                  {
                    //remove
                    DCMUserDAO.deleteDcmUserFunction(con,strDcmAssignedUserId,keyValue);
                  }
                }
                else
                {
                  //remove
                  DCMUserDAO.deleteDcmUserFunction(con,strDcmAssignedUserId,keyValue);
                }
              }
            }

            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              String keyValue = (String)paramHashMap.get(tempStatusString);
              if(tempStatusString.startsWith("user_defined_data_view__R") && tempStatusString.endsWith("__C1"))
              {
                String tempString2 = tempStatusString.substring(0,tempStatusString.length()-1);
                tempString2 += "3";
                String keyValue3 = (String)paramHashMap.get(tempString2);
                if(keyValue3 == null)
                {
                  DCMUserDAO.insertDcmUserFunction(con,strDcmAssignedUserId,keyValue);
                }
                else
                {
                  if(keyValue3.compareTo(keyValue)!=0)
                  {
                    DCMUserDAO.insertDcmUserFunction(con,strDcmAssignedUserId,keyValue);
                  }
                }
              }
            }
            
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManagerByFilter(con,strDcmUserId,strUserID,"","","3","","");
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserManagersList);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_USER_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_SEARCH_ADMIN_SALES_AGENTS_ASSIGN_FUNCTION:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strSearchDcmUserName = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME,strSearchDcmUserName);
            String strSearchDcmUserStatusTypeId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_LIST_STATUS);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_LIST_STATUS,strSearchDcmUserStatusTypeId);
            String strSearchDcmUserFunctionId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_FUNCTION_ID);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_FUNCTION_ID,strSearchDcmUserFunctionId);
            
            Vector dcmUserManagersList = new Vector();
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManagerByFilter(con,strDcmUserId,strUserID,strSearchDcmUserName,strSearchDcmUserStatusTypeId,"3",strSearchDcmUserFunctionId,"");
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserManagersList);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_USER_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_ADMIN_SALES_AGENTS_ASSIGN_GROUP:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector dcmUserManagersList = new Vector();
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManagerByFilter(con,strDcmUserId,strUserID,"","","3","","");
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserManagersList);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_USER_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            Vector groupsList = GroupDAO.getGroupsByFilter(con,"",0,0,"","");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,groupsList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_SEARCH_ADMIN_SALES_AGENTS_ASSIGN_GROUP:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strSearchDcmUserName = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME,strSearchDcmUserName);
            String strSearchDcmUserStatusTypeId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_LIST_STATUS);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_LIST_STATUS,strSearchDcmUserStatusTypeId);
            String strSearchDcmUserGroupId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_GROUP_ID);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_GROUP_ID,strSearchDcmUserGroupId);
            

            Vector dcmUserManagersList = new Vector();
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManagerByFilter(con,strDcmUserId,strUserID,strSearchDcmUserName,strSearchDcmUserStatusTypeId,"3","",strSearchDcmUserGroupId);
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserManagersList);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_USER_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            Vector groupsList = GroupDAO.getGroupsByFilter(con,"",0,0,"","");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,groupsList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_SAVE_ASSIGN_TARGET_TO_SALES_AGENT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strUserIdAssignedTo = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID);
            String strErr = "";
            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              if(tempStatusString.endsWith("__C7"))
              {
                String keyValue = (String)paramHashMap.get(tempStatusString);
                String tempString2 = tempStatusString.substring(0,tempStatusString.length()-1);
                tempString2 += "1";
                if(paramHashMap.containsKey(tempString2))
                {
                  //String strGroupId = (String)paramHashMap.get("fun_"+strFunctionId+"__R"+strRowNumber+"__C1");
                  //String strTargetValue = (String)paramHashMap.get("fun_"+strFunctionId+"__R"+strRowNumber+"__C2");
                  //String strTargetDurationTypeId = (String)paramHashMap.get("fun_"+strFunctionId+"__R"+strRowNumber+"__C3");
                  //String strTargetDurationNumber = (String)paramHashMap.get("fun_"+strFunctionId+"__R"+strRowNumber+"__C5");
                }
                else
                {
                  //remove
                  DCMUserDAO.deleteDCMUserGroupFunctionTargetByUser(con,keyValue);
                }
              }
            }

            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              if(tempStatusString.endsWith("__C6"))
              {
                String strFunctionId = (String)paramHashMap.get(tempStatusString);
                String strRowNumber = tempStatusString.substring(("fun_"+strFunctionId+"__R").length(),tempStatusString.length()-4);

                String strGroupId = (String)paramHashMap.get("fun_"+strFunctionId+"__R"+strRowNumber+"__C1");
                String strTargetValue = (String)paramHashMap.get("fun_"+strFunctionId+"__R"+strRowNumber+"__C2");
                String strTargetDurationTypeId = (String)paramHashMap.get("fun_"+strFunctionId+"__R"+strRowNumber+"__C3");
                String strTargetDurationNumber = (String)paramHashMap.get("fun_"+strFunctionId+"__R"+strRowNumber+"__C5");
                if(paramHashMap.containsKey("fun_"+strFunctionId+"__R"+strRowNumber+"__C7"))
                {
                  //update
                  String strUserGroupFunctionTargetId = (String)paramHashMap.get("fun_"+strFunctionId+"__R"+strRowNumber+"__C7");
                  boolean exists = DCMUserDAO.checkDcmUserGroupFunctionTarget(con,strUserGroupFunctionTargetId,strGroupId,strFunctionId);
                  if(!exists)
                  {
                    DCMUserDAO.updateDcmUserGroupFunctionTarget(con,strUserIdAssignedTo,strGroupId,strFunctionId,strTargetValue,strTargetDurationTypeId,strUserGroupFunctionTargetId);
                  }
                  else
                  {
                    String strFunctionName = DCMUserDAO.getFunctionName(con,strFunctionId);
                    String strGroupName = DCMUserDAO.getGroupName(con,strGroupId);
                    strErr += "Target for function : "+strFunctionName+" and group : "+strGroupName+" is assigned to another sales agent. \r\n";
                  }
                }
                else
                {
                  //insert
                  boolean exists = DCMUserDAO.checkDcmUserGroupFunctionTarget(con,"",strGroupId,strFunctionId);
                  if(!exists)
                  {
                    DCMUserDAO.insertDcmUserGroupFunctionTarget(con,strUserIdAssignedTo,strGroupId,strFunctionId,strTargetValue,strTargetDurationTypeId);
                  }
                  else
                  {
                    String strFunctionName = DCMUserDAO.getFunctionName(con,strFunctionId);
                    String strGroupName = DCMUserDAO.getGroupName(con,strGroupId);
                    strErr += "Target for function : "+strFunctionName+" and group : "+strGroupName+" is assigned to another sales agent. \r\n";
                  }
                }
              }
            }

            Vector dcmUserManagersList = new Vector();
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              //dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }
            else
            {
              dcmUserManagersList = DCMUserDAO.getDCMUserByManagerByFilter(con,strDcmUserId,strUserID,"","","3","","");
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserManagersList);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_USER_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            Vector groupsList = GroupDAO.getGroupsByFilter(con,"",0,0,"","");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,groupsList);
            if(strErr.compareTo("")!=0)
            {
              strErr = strErr.substring(0,strErr.length()-4); 
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,strErr);
            }
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_ASSIGN_FUNCTION_TO_SALES_AGENT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String userIdAssignedTo = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID);
            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,functionList);
            DCMUserDetailModel dcmUserDetailModel = DCMUserDAO.getDCMUserDetailById(con,userIdAssignedTo);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserDetailModel);
            Vector functionsAssignedTo = DCMUserDAO.getDcmUserAssignedFunctions(con,userIdAssignedTo);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionsAssignedTo);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_SALES_AGENT_VISITS_FOR_AGENTS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }

            Vector vecSalesAgentsVisitPlans = DCMUserDAO.getDCMVisitPlanByManagerUserId(con,strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecSalesAgentsVisitPlans);

            GenericModel statusModel = new GenericModel();
            Vector dcmUserStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_VISIT_PLAN_STATUS_TYPE");
            dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);

            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_VIEW_SALES_AGENT_VISIT_DETAILS:
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
        case DCM_SEARCH_SALES_AGENT_ACTUAL_VISITS_FOR_AGENTS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String searchPosCode = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE);
            String searchPosName = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME);
            String searchActualVisitDateFrom = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_FROM);
            String searchActualVisitDateTo = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_TO);
            String searchFuntionId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_FUNCTION_ID);

            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE,searchPosCode);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME,searchPosName);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_FROM,searchActualVisitDateFrom);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_TO,searchActualVisitDateTo);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_FUNCTION_ID,searchFuntionId);
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }

            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionList);

            Vector actualVisits = DCMUserDAO.getDCMActualVisitByFilter(con,strUserID,searchPosCode,searchPosName,searchActualVisitDateFrom,searchActualVisitDateTo);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,actualVisits);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_SAVE_SALES_AGENT_ACTUAL_VISIT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String actualVisitId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID);
            String posCode = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_ACTUAL_VISIT_POS_CODE);
            String actualVisitDate = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_ACTUAL_VISIT_DATE);
            String actualVisitComment = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_ACTUAL_VISIT_COMMENTS);
            String functionId = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_SELECT_DCM_ACTUAL_VISIT_FUNCTION_ID);
            
            boolean posExists = ServiceDAO.checkPosCodeExists(con,posCode);
            if(posExists)
            {
              if(actualVisitId.compareTo("")==0)
              {
                //insert
                DCMUserDAO.insertDcmActualVisit(con,posCode,strUserID,actualVisitDate,actualVisitComment,functionId);
              }
              else
              {
                //update
                DCMUserDAO.updateDcmActualVisit(con,posCode,strUserID,actualVisitDate,actualVisitComment,functionId,actualVisitId);
              }
            }
            else
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"POS Code does not exist in the system.");
            }
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }

            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionList);

            Vector actualVisits = DCMUserDAO.getDCMActualVisitByFilter(con,strUserID,"","","*","*");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,actualVisits);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_SALES_AGENT_ACTUAL_VISITS_FOR_AGENTS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            
            int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
            if(strDcmUserId == -1)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
            }

            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,functionList);

            Vector actualVisits = new Vector();
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,actualVisits);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        
        case DCM_SAVE_SALES_AGENT_BLUE_COPY_ENTRY_FORM:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String actualVisitId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID);
            String numberOfContractsCollected = (String)paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_SALES_FORM_CONTRACTS_COLLECTED);

            DCMUserDAO.insertDcmActualVisitDetail(con,actualVisitId,DCMInterfaceKey.CONST_DCM_PREDEFINED_FUNCTION_SALES_FORM,numberOfContractsCollected);
            
            ActualVisitModel actualVisitModel = DCMUserDAO.getDCMActualVisitById(con,actualVisitId);
            if(actualVisitModel!=null)dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,actualVisitModel);

            Vector actualVisitDetailList = DCMUserDAO.getDCMActualVisitDetailById(con,actualVisitId);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,actualVisitDetailList);

            Vector functionList = FunctionDAO.getFunctionsByFilter(con,"",0,"2");
            dataHashMap.put(DCMInterfaceKey.DCM_HASHMAP_KEY_COLLECTION,functionList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_VIEW_SALES_AGENT_ACTUAL_VISIT_DETAILS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String actualVisitId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID);

            ActualVisitModel actualVisitModel = DCMUserDAO.getDCMActualVisitById(con,actualVisitId);
            if(actualVisitModel!=null)dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,actualVisitModel);

            Vector actualVisitDetailList = DCMUserDAO.getDCMActualVisitDetailById(con,actualVisitId);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,actualVisitDetailList);

            Vector actualVisitFunctionList = FunctionDAO.getFunctionsByFilter(con,"",0,"1");
            dataHashMap.put(DCMInterfaceKey.DCM_HASHMAP_KEY_COLLECTION,actualVisitFunctionList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_CREATE_NEW_SALES_AGENT_ACTUAL_VISIT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector actualVisitFunctionList = FunctionDAO.getFunctionsByFilter(con,"",0,"1");
            dataHashMap.put(DCMInterfaceKey.DCM_HASHMAP_KEY_COLLECTION,actualVisitFunctionList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_SALES_AGENT_BLUE_COPY_ENTRY_FORM:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String actualVisitId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID);

            ActualVisitModel actualVisitModel = DCMUserDAO.getDCMActualVisitById(con,actualVisitId);
            if(actualVisitModel!=null)dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,actualVisitModel);
            
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_MANAGER_BLUE_COPIES_COLLECTED:
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

        case DCM_MANAGER_EDIT_BLUE_COPIES_COLLECTED:
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

        case DCM_SALES_AGENT_CREATE_SERVICE_REQUEST:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String actualVisitId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID);

            ActualVisitModel actualVisitModel = DCMUserDAO.getDCMActualVisitById(con,actualVisitId);
            if(actualVisitModel!=null)dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,actualVisitModel);

            Vector listOfServices = ServiceDAO.getPosServicesByFilter(con,"","1","","","");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,listOfServices);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_MANAGER_EDIT_SERVICE_REQUEST:
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
        
        case DCM_SEARCH_MANAGER_SERVICE_REQUEST_LIST:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String searchPosCode = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE);
            String searchPosName = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME);
            String searchServiceName = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_SERVICE_NAME);
            String searchCaseTitle = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE);
            String searchServiceRequestInitiateDateFrom = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_SERVICE_REQUEST_INITIATE_DATE_FROM);
            String searchServiceRequestInitiateDateTo = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_SERVICE_REQUEST_INITIATE_DATE_TO);
            String searchServiceRequestStatusTypeId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_SERVICE_REQUEST_STATUS_TYPE_ID);

            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE,searchPosCode);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME,searchPosName);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_SERVICE_NAME,searchServiceName);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE,searchCaseTitle);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_SERVICE_REQUEST_INITIATE_DATE_FROM,searchServiceRequestInitiateDateFrom);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_SERVICE_REQUEST_INITIATE_DATE_TO,searchServiceRequestInitiateDateTo);
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_SERVICE_REQUEST_STATUS_TYPE_ID,searchServiceRequestStatusTypeId);
            
            Vector serviceRequestList = ServiceDAO.getPosServiceRequestsByFilter(con,searchCaseTitle,searchServiceName,searchPosName,searchPosCode,searchServiceRequestInitiateDateFrom,searchServiceRequestInitiateDateTo,searchServiceRequestStatusTypeId);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,serviceRequestList);

            GenericModel serviceRequestStatusModel = new GenericModel();
            Vector serviceRequestStatusTypeVector = new Vector();
            serviceRequestStatusModel = GenericModelDAO.getColumns(con , "DCM_SERVICE_REQ_STATUS_TYPE");
            serviceRequestStatusTypeVector = GenericModelDAO.getModels(con , serviceRequestStatusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,serviceRequestStatusTypeVector);

            GenericModel serviceRequestWarningModel = new GenericModel();
            Vector serviceRequestWarningTypeVector = new Vector();
            serviceRequestWarningModel = GenericModelDAO.getColumns(con , "DCM_SERVICE_REQ_WARNING_TYPE");
            serviceRequestWarningTypeVector = GenericModelDAO.getModels(con , serviceRequestWarningModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,serviceRequestWarningTypeVector);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_MANAGER_SERVICE_REQUEST_LIST:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector serviceRequestList = new Vector();
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,serviceRequestList);

            GenericModel serviceRequestStatusModel = new GenericModel();
            Vector serviceRequestStatusTypeVector = new Vector();
            serviceRequestStatusModel = GenericModelDAO.getColumns(con , "DCM_SERVICE_REQ_STATUS_TYPE");
            serviceRequestStatusTypeVector = GenericModelDAO.getModels(con , serviceRequestStatusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,serviceRequestStatusTypeVector);

            GenericModel serviceRequestWarningModel = new GenericModel();
            Vector serviceRequestWarningTypeVector = new Vector();
            serviceRequestWarningModel = GenericModelDAO.getColumns(con , "DCM_SERVICE_REQ_WARNING_TYPE");
            serviceRequestWarningTypeVector = GenericModelDAO.getModels(con , serviceRequestWarningModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,serviceRequestWarningTypeVector);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_MANAGER_SERVICES_LIST:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            GenericModel statusModel = new GenericModel();
            Vector dcmServiceStatusTypeVector = new Vector();
            statusModel = GenericModelDAO.getColumns(con , "DCM_POS_SERVICE_STATUS_TYPE");
            dcmServiceStatusTypeVector = GenericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmServiceStatusTypeVector);

            GenericModel serviceEligibilityTypeModel = new GenericModel();
            Vector dcmServiceEligibilityTypeVector = new Vector();
            serviceEligibilityTypeModel = GenericModelDAO.getColumns(con , "DCM_SERVICE_ELIGIBILITY_TYPE");
            dcmServiceEligibilityTypeVector = GenericModelDAO.getModels(con , serviceEligibilityTypeModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmServiceEligibilityTypeVector);

            Vector listOfServices = new Vector();
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,listOfServices);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_SAVE_ASSIGN_POS_TO_SERVICE:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String posServiceId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID);

            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              String keyValue = (String)paramHashMap.get(tempStatusString);
              if(tempStatusString.startsWith("user_defined_data_view__R") && tempStatusString.endsWith("__C4"))
              {
                String tempString2 = tempStatusString.substring(0,tempStatusString.length()-1);
                tempString2 += "1";
                if(paramHashMap.containsKey(tempString2))
                {
                  String keyValue2 = (String)paramHashMap.get(tempString2);
                  if(keyValue2.compareTo(keyValue)!=0)
                  {
                    //remove
                    ServiceDAO.deletePosEligiableForService(con,keyValue,posServiceId);
                  }
                }
                else
                {
                  //remove
                  ServiceDAO.deletePosEligiableForService(con,keyValue,posServiceId);
                }
              }
            }

            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              String keyValue = (String)paramHashMap.get(tempStatusString);
              if(tempStatusString.startsWith("user_defined_data_view__R") && tempStatusString.endsWith("__C1"))
              {
                String tempString2 = tempStatusString.substring(0,tempStatusString.length()-1);
                tempString2 += "4";
                String keyValue3 = (String)paramHashMap.get(tempString2);
                if(keyValue3 == null)
                {
                  //insert
                  boolean posExists = ServiceDAO.checkPosCodeExists(con,keyValue);
                  if(posExists)
                  {
                    boolean posServicEligibilityExists = ServiceDAO.checkPosServiceEligibilityExists(con,keyValue,posServiceId);
                    if(!posServicEligibilityExists)ServiceDAO.insertPosEligiableForService(con,keyValue,posServiceId);
                  }
                }
                else
                {
                  if(keyValue3.compareTo(keyValue)!=0)
                  {
                    //insert
                    boolean posExists = ServiceDAO.checkPosCodeExists(con,keyValue);
                    if(posExists)
                    {
                      boolean posServicEligibilityExists = ServiceDAO.checkPosServiceEligibilityExists(con,keyValue,posServiceId);
                      if(!posServicEligibilityExists)ServiceDAO.insertPosEligiableForService(con,keyValue,posServiceId);
                    }
                  }
                }
              }
            }

            GenericModel statusModel = new GenericModel();
            Vector dcmServiceStatusTypeVector = new Vector();
            statusModel = GenericModelDAO.getColumns(con , "DCM_POS_SERVICE_STATUS_TYPE");
            dcmServiceStatusTypeVector = GenericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmServiceStatusTypeVector);

            GenericModel serviceEligibilityTypeModel = new GenericModel();
            Vector dcmServiceEligibilityTypeVector = new Vector();
            serviceEligibilityTypeModel = GenericModelDAO.getColumns(con , "DCM_SERVICE_ELIGIBILITY_TYPE");
            dcmServiceEligibilityTypeVector = GenericModelDAO.getModels(con , serviceEligibilityTypeModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmServiceEligibilityTypeVector);

            Vector listOfServices = new Vector();
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,listOfServices);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_SEARCH_MANAGER_SERVICES_LIST:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            GenericModel statusModel = new GenericModel();
            Vector dcmServiceStatusTypeVector = new Vector();
            statusModel = GenericModelDAO.getColumns(con , "DCM_POS_SERVICE_STATUS_TYPE");
            dcmServiceStatusTypeVector = GenericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmServiceStatusTypeVector);

            GenericModel serviceEligibilityTypeModel = new GenericModel();
            Vector dcmServiceEligibilityTypeVector = new Vector();
            serviceEligibilityTypeModel = GenericModelDAO.getColumns(con , "DCM_SERVICE_ELIGIBILITY_TYPE");
            dcmServiceEligibilityTypeVector = GenericModelDAO.getModels(con , serviceEligibilityTypeModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmServiceEligibilityTypeVector);

            String searchServiceName = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_SERVICE_NAME);
            String searchServiceStatusTypeId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_POS_SERVICE_STATUS_TYPE_ID);
            String searchServiceEligibilityTypeId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_POS_SERVICE_ELIGIBILITY_TYPE_ID);
            String searchPosCode = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE);
            String searchPosName = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME);

            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_SERVICE_NAME,searchServiceName);            
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_POS_SERVICE_STATUS_TYPE_ID,searchServiceStatusTypeId);  
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_POS_SERVICE_ELIGIBILITY_TYPE_ID,searchServiceEligibilityTypeId);  
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE,searchPosCode);  
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME,searchPosName);  
            
            Vector listOfServices = ServiceDAO.getPosServicesByFilter(con,searchServiceName,searchServiceStatusTypeId,searchServiceEligibilityTypeId,searchPosName,searchPosCode);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,listOfServices);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_UPDATE_SERVICES_STATUS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            GenericModel statusModel = new GenericModel();
            Vector dcmServiceStatusTypeVector = new Vector();
            statusModel = GenericModelDAO.getColumns(con , "DCM_POS_SERVICE_STATUS_TYPE");
            dcmServiceStatusTypeVector = GenericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmServiceStatusTypeVector);

            GenericModel serviceEligibilityTypeModel = new GenericModel();
            Vector dcmServiceEligibilityTypeVector = new Vector();
            serviceEligibilityTypeModel = GenericModelDAO.getColumns(con , "DCM_SERVICE_ELIGIBILITY_TYPE");
            dcmServiceEligibilityTypeVector = GenericModelDAO.getModels(con , serviceEligibilityTypeModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmServiceEligibilityTypeVector);

            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              String keyValue = (String)paramHashMap.get(tempStatusString);
              //Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
              if(tempStatusString.startsWith(DCMInterfaceKey.INPUT_SELECT_POS_SERVICE_STATUS_TYPE_ID))
              {
                int strlength = DCMInterfaceKey.INPUT_SELECT_POS_SERVICE_STATUS_TYPE_ID.length() + 1;
                String strServiceId = tempStatusString.substring(strlength, tempStatusString.length());
                String strOldStatusId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_STATUS_TYPE_ID+"_"+strServiceId);
                if(keyValue.compareTo(strOldStatusId)!=0)
                {
                  ServiceDAO.updatePosService(con,strServiceId,keyValue);
                }
              }
            }

            String searchServiceName = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_SERVICE_NAME);
            String searchServiceStatusTypeId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_POS_SERVICE_STATUS_TYPE_ID);
            String searchServiceEligibilityTypeId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_POS_SERVICE_ELIGIBILITY_TYPE_ID);
            String searchPosCode = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE);
            String searchPosName = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME);

            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_SERVICE_NAME,searchServiceName);            
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_POS_SERVICE_STATUS_TYPE_ID,searchServiceStatusTypeId);  
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_POS_SERVICE_ELIGIBILITY_TYPE_ID,searchServiceEligibilityTypeId);  
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE,searchPosCode);  
            dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME,searchPosName);  
            
            Vector listOfServices = ServiceDAO.getPosServicesByFilter(con,searchServiceName,searchServiceStatusTypeId,searchServiceEligibilityTypeId,searchPosName,searchPosCode);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,listOfServices);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_SAVE_SERVICE:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String posServiceId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID);
            String posServiceName = (String)paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_POS_SERVICE_NAME);
            String posServiceDesc = (String)paramHashMap.get(DCMInterfaceKey.INPUT_TEXTAREA_POS_SERVICE_DESC);
            String serviceEligibilityTypeId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SELECT_POS_SERVICE_ELIGIBILITY_TYPE_ID);
            String posServiceStatusTypeId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_SELECT_POS_SERVICE_STATUS_TYPE_ID);

            if(posServiceId.compareTo("")==0)
            {
              ServiceDAO.insertPosService(con,posServiceName,posServiceDesc,posServiceStatusTypeId,serviceEligibilityTypeId);
            }
            else
            {
              ServiceDAO.updatePosService(con,posServiceId,posServiceName,posServiceDesc,posServiceStatusTypeId,serviceEligibilityTypeId);
            }
            
            GenericModel statusModel = new GenericModel();
            Vector dcmServiceStatusTypeVector = new Vector();
            statusModel = GenericModelDAO.getColumns(con , "DCM_POS_SERVICE_STATUS_TYPE");
            dcmServiceStatusTypeVector = GenericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmServiceStatusTypeVector);

            GenericModel serviceEligibilityTypeModel = new GenericModel();
            Vector dcmServiceEligibilityTypeVector = new Vector();
            serviceEligibilityTypeModel = GenericModelDAO.getColumns(con , "DCM_SERVICE_ELIGIBILITY_TYPE");
            dcmServiceEligibilityTypeVector = GenericModelDAO.getModels(con , serviceEligibilityTypeModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmServiceEligibilityTypeVector);

            Vector listOfServices = new Vector();
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,listOfServices);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        
        case DCM_EDIT_REGION:
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

        case DCM_CHANGED_SALES_AGENTS_DATA_MANAGEMENT:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String userIdAssignedTo = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID);

            DCMUserDetailModel dcmUserDetailModel = DCMUserDAO.getDCMUserDetailById(con,userIdAssignedTo);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserDetailModel);

            Vector dcmUserDetailPendingList = DCMUserDAO.getDCMUserDetailByStatusId(con,userIdAssignedTo,"4");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserDetailPendingList);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_ASSIGN_TARGET_TO_POS:
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

        case DCM_CREATE_NEW_SERVICE:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            GenericModel statusModel = new GenericModel();
            Vector dcmServiceStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_POS_SERVICE_STATUS_TYPE");
            dcmServiceStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmServiceStatusTypeVector);

            GenericModel serviceEligibilityTypeModel = new GenericModel();
            Vector dcmServiceEligibilityTypeVector = new Vector();
            serviceEligibilityTypeModel = genericModelDAO.getColumns(con , "DCM_SERVICE_ELIGIBILITY_TYPE");
            dcmServiceEligibilityTypeVector = genericModelDAO.getModels(con , serviceEligibilityTypeModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmServiceEligibilityTypeVector);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_EDIT_SERVICE:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            GenericModel statusModel = new GenericModel();
            Vector dcmServiceStatusTypeVector = new Vector();
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_POS_SERVICE_STATUS_TYPE");
            dcmServiceStatusTypeVector = genericModelDAO.getModels(con , statusModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmServiceStatusTypeVector);

            GenericModel serviceEligibilityTypeModel = new GenericModel();
            Vector dcmServiceEligibilityTypeVector = new Vector();
            serviceEligibilityTypeModel = genericModelDAO.getColumns(con , "DCM_SERVICE_ELIGIBILITY_TYPE");
            dcmServiceEligibilityTypeVector = genericModelDAO.getModels(con , serviceEligibilityTypeModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmServiceEligibilityTypeVector);

            String posServiceId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID);
            ServiceModel serviceModel = ServiceDAO.getPosServicesById(con,posServiceId);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,serviceModel);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_ASSIGN_POS_TO_SERVICE:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String posServiceId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID);
            ServiceModel serviceModel = ServiceDAO.getPosServicesById(con,posServiceId);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,serviceModel);

            HashMap posAssignedToService = ServiceDAO.getPosEligiableForService(con,posServiceId);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,posAssignedToService);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_UPLOAD_SERVICE_ELIGIBILITY_LIST:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("6");
            dataHashMap.put(  AdministrationInterfaceKey.TABLE_DEF_VECTOR  , tableDefVector);
            Vector listOfServices = ServiceDAO.getPosServicesByFilter(con,"","1","","","");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,listOfServices);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_UPLOAD_SERVICE_ELIGIBILITY_LIST_PROCESS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String serviceId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID);
            dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID,serviceId);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case DCM_ADMIN_VIEW_POS_CHANGES_DETAILS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            int posID = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_POS_ID));
            Utility.logger.debug("POSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS: "+posID);
            POSDetailModel changesModel = PosDAO.getPOSByID(con , posID);
            dataHashMap.put(DCMInterfaceKey.DCM_CHANGES_POS, changesModel);

          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_UPLOAD_ASSIGN_SALES_AGENT_TO_MANAGER:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("7");
            dataHashMap.put(  AdministrationInterfaceKey.TABLE_DEF_VECTOR  , tableDefVector);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_SALES_AGENT_BLUE_COPIES_COLLECTED:
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

        case DCM_MOVE_REGIONS_SELECT_SOURCES:
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

        case DCM_MOVE_REGIONS_SELECT_DESTINATION:
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

        case DCM_ADD_NEW_DCM_USER:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            Vector dcmUserManagersList = new Vector();
            
            CachEngine.removeObject(UserInterfaceKey.CACH_OBJ_USER_LIST);
            Long lPersonID = Utility.getSequenceNextVal(con, "SEQ_GEN_PERSON_ID");
            int nPersonID = lPersonID.intValue();
            String serverName = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_NAME);
            String serverPort = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_PORT);
            String appName = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_CONTEXT_PATH);

            String strPersonName = (String)paramHashMap.get(UserInterfaceKey.CONTROL_TEXT_NAME_USER_NAME);
            String strPersonAddress = (String)paramHashMap.get(UserInterfaceKey.CONTROL_TEXT_NAME_USER_ADDRESS);
            String strPersonEmail = (String)paramHashMap.get(UserInterfaceKey.CONTROL_TEXT_NAME_USER_EMAIL);
            String strUserPassword = Math.random()+"";
            strUserPassword = strUserPassword.substring(strUserPassword.lastIndexOf(".")+1,strUserPassword.lastIndexOf(".")+7);
            PersonModel newPersonModel = new PersonModel(nPersonID, strPersonName,
                                                         strPersonAddress, strPersonEmail);
            String strEncryptedUserPassword = EncryptUtil.encryptIt(strUserPassword + strPersonEmail);
            String isLocked="0";
          
             String expireDate="sysDate+"+securityDAO.getProps(con,"DAYS_FOR_EXPIRED_LOGIN");
            UserModel newUserModel = new UserModel(newPersonModel, strEncryptedUserPassword,isLocked,expireDate);
            //UserCMPHome userCMPHome = (UserCMPHome)Utility.getEJBHome("UserCMP", "com.mobinil.sds.core.system.sa.users.dao.cmp.UserCMPHome");
            //UserCMP userCMPRemote = null;
            try 
            {
              //userCMPRemote = userCMPHome.create(newUserModel);
              int user_id = newPersonModel.getPersonID();
              String user_login = newPersonModel.getPersonEMail();
              String password = newUserModel.getUserPassword();
              isLocked = newUserModel.getM_isLocked();
            expireDate = newUserModel.getM_expire();
              //int user_status_id = newUserModel.getUserStatusID();
              int user_status_id = 1;
              String user_status_name = newUserModel.getUserStatusName();
              String person_full_name = newPersonModel.getPersonFullName();
              String person_address = newPersonModel.getPersonAddress();
              //int person_status_id = newPersonModel.getPersonStatusID();
              int person_status_id = 1;
              String person_status_name = newPersonModel.getPersonStatusName();
              //int person_type_id = newPersonModel.getPersonTypeID();
              int person_type_id = 1;
              String person_type_name = newPersonModel.getPersonTypeName();
              //int person_type_status_id = newPersonModel.getPersonTypeStatusID();
              int person_type_status_id = 1;
              String person_type_status_name = newPersonModel.getPersonTypeStatusName();
              UserDAO.insertVwGenUserDetails(con,user_id,user_login,password,user_status_id,user_status_name,person_full_name,person_address,person_status_id,person_status_name,person_type_id,person_type_name,person_type_status_id,person_type_status_name,isLocked,expireDate);

              //get level type id
              String strUserLevelTypeId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_LEVEL_TYPE_ID);
              String strRoleId = "";
              if(strUserLevelTypeId.compareTo("1")==0)strRoleId = DCMInterfaceKey.CONST_REGIONAL_MANAGER_ROLE_ID;
              else if(strUserLevelTypeId.compareTo("2")==0)strRoleId = DCMInterfaceKey.CONST_MANAGER_ROLE_ID;
              else if(strUserLevelTypeId.compareTo("3")==0)strRoleId = DCMInterfaceKey.CONST_SALES_AGENT_ROLE_ID;

              //insert adm_user_role
              DCMUserDAO.insertAdmUserRole(con,user_id,strRoleId);

              //insert dcm_user
              int intDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
              int intUserLevelId = DCMUserDAO.getDCMUserLevelId(con,strUserID);
              String strRegionId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_REGION_ID);
              //System.out.println("Region Id isssssssssssss " + strRegionId);
              Long dcmUserId = DCMUserDAO.insertDcmUser(con,user_id,strUserLevelTypeId,strRegionId,intDcmUserId,"1",intUserLevelId+1);
              //System.out.println("The DCM User ID issssssssssssssssss " + dcmUserId);
              //insert dcm_user_detail
              String strDcmUserAddress = (String)paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_DCM_USER_ADDRESS);
              String strDcmUserMobile = (String)paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_DCM_USER_MOBILE);
              //Long dcmUserDetailId = DCMUserDAO.insertDcmUserDetail(con,user_id,person_full_name,strDcmUserAddress,user_login,strDcmUserMobile,strRegionId,"1",strUserID);
              Long dcmUserDetailId = DCMUserDAO.insertMultipleDcmUserDetail(con,dcmUserId,user_id,person_full_name,strDcmUserAddress,user_login,strDcmUserMobile,strRegionId,"1",strUserID);
              DCMUserDAO.updateDcmUser(con,user_id+"",dcmUserDetailId+"");

              
              Utility.logger.debug("Password "+strUserPassword);
              Utility.sendPasswordByMail(serverName, serverPort, appName, strPersonEmail, strPersonName, strUserPassword);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "User password was sent to the email provided.");
            } 
            catch (Exception ex) 
            {
              ex.printStackTrace();
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Another User with the same E-Mail already exists");
            } 
            finally 
            {
              int strDcmUserId = DCMUserDAO.getDCMUserId(con,strUserID);
              if(strDcmUserId == -1)
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Current User is not a DCM user.");
              }
              else
              {
                dcmUserManagersList = DCMUserDAO.getDCMUserByManager(con,strDcmUserId,strUserID);
              }
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmUserManagersList);

              GenericModel statusModel = new GenericModel();
              Vector dcmUserStatusTypeVector = new Vector();
              GenericModelDAO genericModelDAO = new GenericModelDAO();
              statusModel = genericModelDAO.getColumns(con , "DCM_USER_STATUS_TYPE");
              dcmUserStatusTypeVector = genericModelDAO.getModels(con , statusModel);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmUserStatusTypeVector);
      
              GenericModel userTypeModel = new GenericModel();
              Vector dcmUserTypeVector = new Vector();
              userTypeModel = genericModelDAO.getColumns(con , "DCM_USER_LEVEL_TYPE");
              dcmUserTypeVector = genericModelDAO.getModels(con , userTypeModel);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,dcmUserTypeVector);
            }
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case DCM_SEARCH_POS:
        { 
        try{
            dataHashMap = new HashMap();
            String posName = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_NAME);
            String posCity = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_CITY);
            String posCode = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_CODE);
            String posCommercialNumber = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_COMMERCIAL_NUMBER);
            String posStatus = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_STATUS);
            String posPhone = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_PHONE);            
            PosDAO posDAO = new PosDAO();
            Vector posResult = posDAO.getPOSByFilter(con , posName,posCode,posPhone,posCommercialNumber,posCity,posStatus);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_RESULT , posResult);
            if(posName != null) dataHashMap.put(DCMInterfaceKey.INPUT_POS_NAME,posName);
            if(posCode != null) dataHashMap.put(DCMInterfaceKey.INPUT_POS_CODE,posCode);
            if(posCommercialNumber != null) dataHashMap.put(DCMInterfaceKey.INPUT_POS_COMMERCIAL_NUMBER,posCommercialNumber);
            if(posPhone != null) dataHashMap.put(DCMInterfaceKey.INPUT_POS_PHONE,posPhone);
            
            Vector cityVector = CityDAO.get_all_cities(con);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_CITY, cityVector);
            Vector statusVector = new Vector();
            GenericModel statusModel = new GenericModel();
            GenericModelDAO statusDAO = new GenericModelDAO();
            statusModel = statusDAO.getColumns(con , "DCM_POS_STATUS_TYPE");
            statusVector = statusDAO.getModels(con , statusModel);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_STATUS , statusVector);
            if(posStatus != null)dataHashMap.put(DCMInterfaceKey.POS_SEARCH_STATUS , posStatus);
            if(posCity != null) dataHashMap.put(DCMInterfaceKey.POS_SEARCH_CITY ,posCity);

            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION , strUserID);

            String strFlagSuperAdmin = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
            dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG,strFlagSuperAdmin);
            
            }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
          
        }
        break;

        case LIST_TABLE_FIELDS:
        {
          
        try{
          dataHashMap = new HashMap();
          dataHashMap = paramHashMap;
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }

        }
        break;        

        case DCM_UPDATE_POS_STATUS:
        {
        try{
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strFlagSuperAdmin = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
            dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG,strFlagSuperAdmin);
            
            GenericModel statusModel = new GenericModel();


            Vector statusVector = new Vector();
            Vector cityVector = new Vector();
            
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            

            statusModel = genericModelDAO.getColumns(con , "DCM_POS_STATUS_TYPE");
            statusVector = genericModelDAO.getModels(con , statusModel);
            
            cityVector = CityDAO.get_all_cities(con);

            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_CITY , cityVector);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_STATUS, statusVector);
            dataHashMap.put(DCMInterfaceKey.POS_SEARCH_STATUS , "");
//            Utility.logger.debug((String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_POS_ID));
            String strPosID = "";
            int posID = 0;
            int posStatusID = 0;
            int posCurrentStatus = 0;
            for(int j=0; j<paramHashMap.size(); j++)
            {
             String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
             String keyValue = (String)paramHashMap.get(tempStatusString);
             if(tempStatusString.startsWith(DCMInterfaceKey.INPUT_DCM_POS_LIST_STATUS))
             {
                strPosID = tempStatusString.substring(20);
                posID = Integer.parseInt(strPosID);
                posStatusID = Integer.parseInt(keyValue);
//                Utility.logger.debug((String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_POS_CURRENT_STATUS+"_"+strPosID));
                posCurrentStatus = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_POS_CURRENT_STATUS+"_"+strPosID));
/*                Utility.logger.debug("POS ID :"+posID);
                Utility.logger.debug("POS new Status :"+posStatusID);
                Utility.logger.debug("POS current Status :"+posCurrentStatus);
*/             
               if(posStatusID != posCurrentStatus)
               {
                   PosDAO.updatePosStatus(con , posID,posStatusID,strFlagSuperAdmin);                 
               }
             }

            }
            dataHashMap = new HashMap();
            String posName = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_NAME);
            String posCity = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_CITY);
            String posCode = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_CODE);
            String posCommercialNumber = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_COMMERCIAL_NUMBER);
            String posStatus = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_STATUS);
            String posPhone = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_PHONE);            
            PosDAO posDAO = new PosDAO();
            Vector posResult = posDAO.getPOSByFilter(con , posName,posCode,posPhone,posCommercialNumber,posCity,posStatus);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_RESULT , posResult);
            if(posName != null) dataHashMap.put(DCMInterfaceKey.INPUT_POS_NAME,posName);
            if(posCode != null) dataHashMap.put(DCMInterfaceKey.INPUT_POS_CODE,posCode);
            if(posCommercialNumber != null) dataHashMap.put(DCMInterfaceKey.INPUT_POS_COMMERCIAL_NUMBER,posCommercialNumber);
            if(posPhone != null) dataHashMap.put(DCMInterfaceKey.INPUT_POS_PHONE,posPhone);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_CITY, cityVector);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_STATUS , statusVector);
            Utility.logger.debug("CITY OF THE POS : "+posPhone);
            if(posStatus != null)dataHashMap.put(DCMInterfaceKey.POS_SEARCH_STATUS , posStatus);
            if(posCity != null) dataHashMap.put(DCMInterfaceKey.POS_SEARCH_CITY ,posCity);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION , strUserID);
            }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        }
        break;

        case DCM_SAVE_EDITED_POS:
        {
            try                                                                                                 
          {
            dataHashMap = new HashMap ();
            int posID                 = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_POS_ID));
            String strUserID          = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String posName            = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_NAME);
            String posCode            = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_CODE);
            String posEmail           = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_EMAIL);
            String posAddress         = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS);
            int posRegion             = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_REGION));
            int posCommercialNumber   = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_COMMERCIAL_NUMBER));
            int posTaxID              = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_TAX_ID));
            int posLegalForm          = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_LEGAL_FORM));
            int posPlace              = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_PLACE_TYPE));
            String posOwnerName       = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME);
            String posOwnerBirthDate  = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_BIRTH_DATE);
            int posOwnerIDType        = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE));
            String posOwnerIDNumber   = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER);
            String posManagerName     = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME);
            String posManagerBirthDate= (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_BIRTH_DATE);
            int posManagerIDType      = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE));
            String posManagerIDNumber = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER);
            String userId             = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            int UserID                = Integer.parseInt(userId);
            Utility.logger.debug("REGION ID : "+posRegion);

            String Phones = (String)paramHashMap.get("manager_phones__R2__C1");
            Vector POSPhones = new Vector();
            String POSPhonesCount = (String)paramHashMap.get("phones_count");

            int count = Integer.parseInt(POSPhonesCount);
            for(int i = 1 ; i <= count ; i++)
            {
              String phone = (String)paramHashMap.get("phones__R"+i+"__C1");
              POSPhones.add(phone);
            }
            String partners = (String)paramHashMap.get("partners__R1__C1");
            Vector partnerVector = new Vector();
            String PartnersCount = (String)paramHashMap.get("partners_count");
            count =  Integer.parseInt(PartnersCount);
                        Utility.logger.debug("partnersCount "+count);
            for(int i = 1 ; i <= count ; i++)
            {
              String partner = (String)paramHashMap.get("partners__R"+i+"__C1");
              partnerVector.add(partner);
            }
            Vector ownerPhones = new Vector();
            String ownerPhonesCount = (String)paramHashMap.get("owner_phones_count");
             count = Integer.parseInt(ownerPhonesCount);
            for(int i = 1 ; i <= count ; i++)
            {
              String phone = (String)paramHashMap.get("owner_phones__R"+i+"__C1");
              ownerPhones.add(phone);
            }
            Vector managerPhones = new Vector();
            String managerPhonesCount = (String)paramHashMap.get("manager_phones_count");
             count = Integer.parseInt(managerPhonesCount);
            for(int i = 1 ; i <= count ; i++)
            {
              String phone = (String)paramHashMap.get("manager_phones__R"+i+"__C1");
              managerPhones.add(phone);
            }
            Date OwnerBirthDate;
            POSDetailModel mdl = new POSDetailModel();
            mdl.setPosName(posName);
            mdl.setPOSCode(posCode);
            mdl.setPosAddress(posAddress);
            mdl.setPosEmail(posEmail);
            mdl.setUserID(UserID);
            mdl.setPosRegionID(posRegion);
            mdl.setPosPhones(POSPhones);
            mdl.setPosManagerPhones(managerPhones);
            mdl.setPosOwnerPhones(ownerPhones);
            mdl.setPosCommercialNumber(posCommercialNumber);
            mdl.setPosTaxID(posTaxID);
            mdl.setPosLegalFormID(posLegalForm);
            mdl.setPosPlaceTypeID(posPlace);
            mdl.setPosOwnerName(posOwnerName);
            mdl.setPosOwnerBirthDate(posOwnerBirthDate);
            mdl.setPosOwnerIDTypeID(posOwnerIDType);
            mdl.setPosOwnerIDNumber(posOwnerIDNumber+"");
            mdl.setPosManagerName(posManagerName);
            mdl.setPosManagerBirthDate(posManagerBirthDate);
            mdl.setPosManagerIDTypeID(posManagerIDType);
            mdl.setPosManagerIDNumber(posManagerIDNumber+"");
            mdl.setPosPartners(partnerVector);
            mdl.setPoslastPOSID(posID);
           
           /*for(int j=0; j<paramHashMap.size(); j++)
            {
             String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
             String keyValue = (String)paramHashMap.get(tempStatusString);
              Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
            }*/
            String strFlagSuperAdmin = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
            dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG,strFlagSuperAdmin);
            
            PosDAO dao = new PosDAO();
            Utility.logger.debug("LegalForm Handler:  "  + mdl.getPosLegalFormName());
            Utility.logger.debug("PlaceType Handler:  "  + mdl.getPosPlaceTypeName());
            int pos_detail_id = dao.editExistingPOS(con , mdl,strFlagSuperAdmin);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
                        Vector regions            = new Vector();
            Vector IDTypeVector       = new Vector();
            Vector legalFormVec       = new Vector();
            Vector placeTypeVec       = new Vector();
            
            GenericModel gm           = new GenericModel();
            GenericModel placeTypeGM  = new GenericModel();
            GenericModel IDTypeModel  = new GenericModel();
            
            GenericModelDAO gmDAO     = new GenericModelDAO();

            IDTypeModel = gmDAO.getColumns(con , "DCM_ID_TYPE");
            IDTypeVector = gmDAO.getModels(con , IDTypeModel);
            
            gm = gmDAO.getColumns(con , "DCM_LEGAL_FORM");
            legalFormVec = gmDAO.getModels(con , gm);

            placeTypeGM = gmDAO.getColumns(con , "DCM_POS_PLACE_TYPE");
            placeTypeVec = gmDAO.getModels(con , placeTypeGM);
                  
            regions = RegionDAO.getAllRegions(con);

            dataHashMap.put(DCMInterfaceKey.VECTOR_ID_TYPE , IDTypeVector);
            dataHashMap.put(DCMInterfaceKey.VECTOR_LEGAL_FORM , legalFormVec);
            dataHashMap.put(DCMInterfaceKey.VECTOR_PLACE_TYPE ,placeTypeVec);
            dataHashMap.put(DCMInterfaceKey.VECTOR_REGIONS , regions);

            Vector regionVec =  new Vector();
            Vector IDTypeVec =  new Vector();
            POSDetailModel posDetailModel = null;
            Vector OwnerPhones = new Vector();
            Vector ManagerPhones = new Vector();
            Vector POSPartner =  new Vector();
            

            IDTypeModel = gmDAO.getColumns(con , "DCM_ID_TYPE");
            IDTypeVec = gmDAO.getModels(con , IDTypeModel);
            
            gm = gmDAO.getColumns(con , "DCM_LEGAL_FORM");
            legalFormVec = gmDAO.getModels(con , gm);
            placeTypeGM = gmDAO.getColumns(con , "DCM_POS_PLACE_TYPE");
            placeTypeVec = gmDAO.getModels(con , placeTypeGM);
            regionVec = RegionDAO.getAllRegions(con);
            dataHashMap.put(DCMInterfaceKey.VECTOR_ID_TYPE , IDTypeVec);
            dataHashMap.put(DCMInterfaceKey.VECTOR_LEGAL_FORM , legalFormVec);
            dataHashMap.put(DCMInterfaceKey.VECTOR_PLACE_TYPE ,placeTypeVec);
            dataHashMap.put(DCMInterfaceKey.VECTOR_REGIONS , regionVec);

            posDetailModel = PosDAO.getPOSByID(con , pos_detail_id);
            POSPhones = PosDAO.getPOSPhones(con , pos_detail_id);
            if(POSPhones == null)
            POSPhones = new Vector();
            posDetailModel.setPosPhones(POSPhones);
            OwnerPhones = OwnerDAO.getOwnerPhones(con , pos_detail_id);
            ManagerPhones = ManagerDAO.getManagerPhones(con , pos_detail_id);
            posDetailModel.setPosManagerPhones(ManagerPhones);
            posDetailModel.setPosOwnerPhones(OwnerPhones);
            POSPartner = PosDAO.getPOSPartners(con , pos_detail_id);
            posDetailModel.setPosPartners(POSPartner);
            dataHashMap.put(DCMInterfaceKey.POS_DETAIL_MODEL , posDetailModel);
            dataHashMap.put(DCMInterfaceKey.DCM_SAVE_POS_TYPE , DCMInterfaceKey.DCM_SAVE_POS_TYPE_EDIT);
            dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_POS_ID, posID+"");
            

          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        }
          
        break;

        case DCM_SEARCH_USER_POS:
        {
        try{
          dataHashMap = new HashMap ();
          String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID , strUserID);
            String posName = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_NAME);
            String posCity = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_CITY);
            String posCode = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_CODE);
            String posCommercialNumber = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_COMMERCIAL_NUMBER);
            String posStatus = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_STATUS);
            String posPhone = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_PHONE);            
            Utility.logger.debug("xxxxxxxxxxxx"+posStatus);
            PosDAO posDAO = new PosDAO();
            Vector posResult = posDAO.getPOSByFilter(con , posName,posCode,posPhone,posCommercialNumber,posCity,posStatus);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_RESULT , posResult);
            if(posName != null) dataHashMap.put(DCMInterfaceKey.INPUT_POS_NAME,posName);
            if(posCode != null) dataHashMap.put(DCMInterfaceKey.INPUT_POS_CODE,posCode);
            if(posCommercialNumber != null) dataHashMap.put(DCMInterfaceKey.INPUT_POS_COMMERCIAL_NUMBER,posCommercialNumber);
            if(posPhone != null) dataHashMap.put(DCMInterfaceKey.INPUT_POS_PHONE,posPhone);
            
            Utility.logger.debug("DATAHASHMAPSIZE: "+dataHashMap.size());

            GenericModel warningModel = new GenericModel();
            GenericModelDAO warningDAO = new GenericModelDAO();
            Vector warningVector = new Vector();
            warningModel = warningDAO.getColumns(con , "DCM_POS_WARNING_TYPE");
            warningVector = warningDAO.getModels(con , warningModel);
            Utility.logger.debug("asasasasasasasasaaaaaaaa: "+warningVector.size());            
            dataHashMap.put(DCMInterfaceKey.DCM_POS_WARNING_VECTOR,warningVector);
            Utility.logger.debug("Shiiiiiiiiiiiiiiiiiiiiiiit");

            Vector cityVector = CityDAO.get_all_cities(con);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_CITY, cityVector);
            Vector statusVector = new Vector();
            GenericModel statusModel = new GenericModel();
            GenericModelDAO statusDAO = new GenericModelDAO();
            statusModel = statusDAO.getColumns(con , "DCM_POS_STATUS_TYPE");
            statusVector = statusDAO.getModels(con , statusModel);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_STATUS , statusVector);
            Utility.logger.debug("CITY OF THE POS : "+posPhone);
            if(posStatus != null)dataHashMap.put(DCMInterfaceKey.POS_SEARCH_STATUS , posStatus);
            if(posCity != null) dataHashMap.put(DCMInterfaceKey.POS_SEARCH_CITY ,posCity);


            Utility.logger.debug("ssssssssssssss"+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION , strUserID);

            String strFlagSuperAdmin = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
            dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG,strFlagSuperAdmin);
            
            }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
          
        }
        break;

        case DCM_USER_UPDATE_POS_STATUS:
        {
        try{
             dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strFlagSuperAdmin = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
            dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG,strFlagSuperAdmin);
            GenericModel statusModel = new GenericModel();
            Vector statusVector = new Vector();
            Vector cityVector = new Vector();         
            GenericModelDAO genericModelDAO = new GenericModelDAO();
            statusModel = genericModelDAO.getColumns(con , "DCM_POS_STATUS_TYPE");
            statusVector = genericModelDAO.getModels(con , statusModel);
            cityVector = CityDAO.get_all_cities(con);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_CITY , cityVector);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_STATUS, statusVector);
            dataHashMap.put(DCMInterfaceKey.POS_SEARCH_STATUS , "");
            Utility.logger.debug((String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_POS_ID));
            String strPosID = "";
            int posID = 0;
            int posStatusID = 0;
            int posCurrentStatus = 0;
            for(int j=0; j<paramHashMap.size(); j++)
            {
             String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
             Utility.logger.debug("zxzxzxzxzxString:  "+tempStatusString);             
             String keyValue = (String)paramHashMap.get(tempStatusString);
             Utility.logger.debug("zxzxzxzxzx:  "+keyValue);
             if(tempStatusString.startsWith(DCMInterfaceKey.INPUT_DCM_POS_LIST_STATUS))
             {
                strPosID = tempStatusString.substring(20);
                posID = Integer.parseInt(strPosID);
                posStatusID = Integer.parseInt(keyValue);
                Utility.logger.debug((String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_POS_CURRENT_STATUS+"_"+strPosID));
                posCurrentStatus = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_POS_CURRENT_STATUS+"_"+strPosID));
                Utility.logger.debug("POS ID :"+posID);
                Utility.logger.debug("POS new Status :"+posStatusID);
                Utility.logger.debug("POS current Status :"+posCurrentStatus);
             
               if(posStatusID != posCurrentStatus)
               {
                   PosDAO.updatePosStatus(con , posID,posStatusID,strFlagSuperAdmin);                 
               }
             }

            }
            dataHashMap = new HashMap();
            String posName = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_NAME);
            String posCity = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_CITY);
            String posCode = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_CODE);
            String posCommercialNumber = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_COMMERCIAL_NUMBER);
            String posStatus = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_STATUS);
            String posPhone = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_PHONE);            
            Utility.logger.debug("xxxxxxxxxxxx"+posStatus);
            PosDAO posDAO = new PosDAO();
            Vector posResult = posDAO.getPOSByFilter(con , posName,posCode,posPhone,posCommercialNumber,posCity,posStatus);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_RESULT , posResult);
            if(posName != null) dataHashMap.put(DCMInterfaceKey.INPUT_POS_NAME,posName);
            if(posCode != null) dataHashMap.put(DCMInterfaceKey.INPUT_POS_CODE,posCode);
            if(posCommercialNumber != null) dataHashMap.put(DCMInterfaceKey.INPUT_POS_COMMERCIAL_NUMBER,posCommercialNumber);
            if(posPhone != null) dataHashMap.put(DCMInterfaceKey.INPUT_POS_PHONE,posPhone);
            
            Utility.logger.debug("DATAHASHMAPSIZE: "+dataHashMap.size());
            
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_CITY, cityVector);
            
            GenericModel warningModel = new GenericModel();
            GenericModelDAO warningDAO = new GenericModelDAO();
            Vector warningVector = new Vector();
            warningModel = warningDAO.getColumns(con , "DCM_POS_WARNING_TYPE");
            warningVector = warningDAO.getModels(con , warningModel);
            dataHashMap.put(DCMInterfaceKey.VECTOR_POS_STATUS , statusVector);
            dataHashMap.put(DCMInterfaceKey.DCM_POS_WARNING_VECTOR,warningVector);
            
            Utility.logger.debug("CITY OF THE POS : "+posPhone);
            if(posStatus != null)dataHashMap.put(DCMInterfaceKey.POS_SEARCH_STATUS , posStatus);
            if(posCity != null) dataHashMap.put(DCMInterfaceKey.POS_SEARCH_CITY ,posCity);

            Utility.logger.debug("ssssssssssssss"+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION , strUserID);

            }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        }
        break;

        case DCM_GENERIC_MODEL_SELECT_TABLE:
        {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String genericTableName = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_GENERIC_TABLE_NAME);
            if(paramHashMap.containsKey(InterfaceKey.HASHMAP_KEY_JOB_ID))
            {
              String jobId = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_JOB_ID);
              HashMap workerHashMap = WorkerDataManagement.getWorkerData(jobId);
              if(workerHashMap == null)
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_JOB_ID,jobId);
              }
              else
              {
                dataHashMap = workerHashMap;
                WorkerDataManagement.removeWorkerData(jobId);
              }
            }
            else
            {
                GenericModelWorker gmWorker = new GenericModelWorker(strUserID,genericTableName);
                String jobId = WorkerDataManagement.addWorker(gmWorker);
                HashMap workerHashMap = WorkerDataManagement.getWorkerData(jobId);
                if(workerHashMap == null)
                {
                  dataHashMap.put(InterfaceKey.HASHMAP_KEY_JOB_ID,jobId);
                }
                else
                {
                  dataHashMap = workerHashMap;
                  WorkerDataManagement.removeWorkerData(jobId);
                }
              /*genericModelVector = genericDAO.getModels(con , genericModel);
              int columnCount = genericDAO.get_column_count(con , genericTableName);
              int rowCount = genericDAO.get_row_count(con , genericTableName);
              Vector columnNames = genericDAO.get_column_Names(con , genericTableName);
              dataHashMap.put(DCMInterfaceKey.DCM_GENERIC_MODEL_DATA_VECTOR,genericModelVector);
              dataHashMap.put(DCMInterfaceKey.DCM_GENERIC_TABLE_COLUMN_COUNT,columnCount+"");
              dataHashMap.put(DCMInterfaceKey.VECTOR_COLUMN_NAMES , columnNames);
              dataHashMap.put(DCMInterfaceKey.DCM_GENERIC_TABLE_ROW_COUNT,rowCount+"");   
              Utility.logger.debug(genericTableName +"  "+columnCount + "   "+rowCount);
              dataHashMap.put(DCMInterfaceKey.GENERIC_TABLE_NAME , genericTableName);*/
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_GENERIC_TABLE_NAME,genericTableName);
        }
        break;

        case DCM_GENERIC_MODEL_EDIT_GENERIC_ITEM:
        {
            dataHashMap = new HashMap();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("USERSSSSSSSSSSSSSSSSSS: "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String itemID = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_SELECTED_GENERIC_ITEM_ID);
            String tableName = (String)paramHashMap.get(DCMInterfaceKey.GENERIC_TABLE_NAME);
            //System.out.println("The table name isssssssssssssssss " + tableName);
            dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_DCM_GENERIC_MODEL_TABLE_NAME,tableName);
            GenericModel genericModel = GenericModelDAO.getModelByID(con , itemID , tableName);
            dataHashMap.put(DCMInterfaceKey.DCM_GENERIC_MODEL_EDIT_VALUES , genericModel);
            Vector columnNames = GenericModelDAO.get_column_Names(con , tableName);
            dataHashMap.put(DCMInterfaceKey.VECTOR_COLUMN_NAMES , columnNames);
            
          
        }
        break;

        case DCM_GENERIC_MODEL_ADD_NEW_GENERIC_ITEM:
        {
           dataHashMap = new HashMap();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("USERSSSSSSSSSSSSSSSSSS: "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String tableName = (String)paramHashMap.get(DCMInterfaceKey.GENERIC_TABLE_NAME);
            dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_DCM_GENERIC_MODEL_TABLE_NAME,tableName);
            Vector columnNames = GenericModelDAO.get_column_Names(con , tableName);
            dataHashMap.put(DCMInterfaceKey.VECTOR_COLUMN_NAMES , columnNames);
            GenericModel genericModel = GenericModelDAO.getModelByID(con , "00" , tableName);
            dataHashMap.put(DCMInterfaceKey.DCM_GENERIC_MODEL_EDIT_VALUES , genericModel);
            dataHashMap.put(DCMInterfaceKey.GENERIC_TABLE_NAME,tableName);

          
        }
        break; 

        case DCM_GENERIC_MODEL_SAVE_NEW_GENERIC_ITEM:
        {
           dataHashMap = new HashMap();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("USERSSSSSSSSSSSSSSSSSS: "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String tableName = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_GENERIC_MODEL_TABLE_NAME);
            int columnCount = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.DCM_GENERIC_TABLE_COLUMN_COUNT));
            Vector columnNames = GenericModelDAO.get_column_Names(con , (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_GENERIC_MODEL_TABLE_NAME));
            //String tableName = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_GENERIC_MODEL_TABLE_NAME);
            
            Vector fields = new Vector();
            GenericModel genericModel = new GenericModel();
            String pk_name = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_GENERIC_MODEL_PK_NAME);
            genericModel.set_primary_key_name(pk_name);

            for(int i = 0 ; i < columnCount ; i ++)
            {
              fields.add((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GENERIC_MODEL_FIELD+"_"+i));
              switch(i)
              {
                case 0:
                {
                  genericModel.set_field_1_value((String)fields.get(i));
                  genericModel.set_field_1_name((String)columnNames.get(i));
                  if(genericModel.get_field_1_name().equals(genericModel.get_primary_key_name()))
                    genericModel.set_primary_key_value(genericModel.get_field_1_value());
                }
                break;
               case 1:
                {
                  genericModel.set_field_2_value((String)fields.get(i));
                  genericModel.set_field_2_name((String)columnNames.get(i));
                  if(genericModel.get_field_2_name().equals(genericModel.get_primary_key_name()))
                    genericModel.set_primary_key_value(genericModel.get_field_2_value());
                  
                }
                break;
                case 2:
                {
                  genericModel.set_field_3_value((String)fields.get(i));
                  genericModel.set_field_3_name((String)columnNames.get(i));
                  if(genericModel.get_field_3_name().equals(genericModel.get_primary_key_name()))
                    genericModel.set_primary_key_value(genericModel.get_field_3_value());
                  
                }
                break;
                case 3:
                {
                  genericModel.set_field_4_value((String)fields.get(i));
                  genericModel.set_field_4_name((String)columnNames.get(i));
                  if(genericModel.get_field_4_name().equals(genericModel.get_primary_key_name()))
                    genericModel.set_primary_key_value(genericModel.get_field_4_value());
                  
                }
                break;                
                case 4:
                {
                  genericModel.set_field_5_value((String)fields.get(i));    
                  genericModel.set_field_5_name((String)columnNames.get(i));
                  if(genericModel.get_field_5_name().equals(genericModel.get_primary_key_name()))
                    genericModel.set_primary_key_value(genericModel.get_field_5_value());
                  
                }
                break;                
              }
            }
            //int pk_value =Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_GENERIC_MODEL_PK_VALUE));
            genericModel.set_columnCount(columnCount);
            
            genericModel.set_columnCount(columnCount);
            GenericModelDAO.addNewGenericItem(con , tableName, genericModel);
            tableName = (String)paramHashMap.get(DCMInterfaceKey.GENERIC_TABLE_NAME);
             columnNames = GenericModelDAO.get_column_Names(con , tableName);
            dataHashMap.put(DCMInterfaceKey.VECTOR_COLUMN_NAMES , columnNames);
             genericModel = GenericModelDAO.getModelByID(con , "00" , tableName);
            dataHashMap.put(DCMInterfaceKey.DCM_GENERIC_MODEL_EDIT_VALUES , genericModel);
            dataHashMap.put(DCMInterfaceKey.GENERIC_TABLE_NAME,tableName);
            
            
        }
        break;

        case DCM_GENERIC_MODEL_GET_TABLE:
        {
          dataHashMap = new HashMap();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("USERSSSSSSSSSSSSSSSSSS: "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
        }
        break;

        case SEARCH_BRANCHES:
        {
            dataHashMap = new HashMap();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("USERSSSSSSSSSSSSSSSSSS: "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String branchName = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_NAME);        
            String branchCode = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_CODE);            
            int branchStatus = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_STATUS));
            String mainBranchName = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_MAIN_BRANCH_NAME);
            String startDateFrom = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_START_DATE_FROM);
            String startDateTo = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_START_DATE_TO);            
            String endDateFrom = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_END_DATE_FROM);
            String endDateTo = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_END_DATE_TO);            
            Vector branchesSearchResult  = BranchDAO.getBranchesByFilter(con ,branchName,branchCode,mainBranchName,branchStatus,
                                                                          startDateFrom , startDateTo ,endDateFrom ,endDateTo);
            GenericModel branchStatusModel = GenericModelDAO.getColumns(con, "DCM_BRANCH_STATUS");
            Vector branchStausVector = GenericModelDAO.getModels(con ,branchStatusModel);
             dataHashMap.put(DCMInterfaceKey.DCM_POS_BRANCH_STATUS_VECTOR , branchStausVector);  
             dataHashMap.put(DCMInterfaceKey.DCM_POS_BRANCHES_SEARCH_RESULT , branchesSearchResult);

            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_NAME,branchName);        
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_CODE,branchCode);            
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_STATUS,branchStatus+"");
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_MAIN_BRANCH_NAME,mainBranchName);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_START_DATE_FROM,startDateFrom);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_START_DATE_TO,startDateTo);            
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_END_DATE_FROM,endDateFrom);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_END_DATE_TO,endDateTo);
            dataHashMap.put(DCMInterfaceKey.DCM_SEARCH_FLAG , true+"");
        }
        break;
        case DCM_BRANCH_UPDATE_STATUS:
        {
            dataHashMap = new HashMap();  
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("USERSSSSSSSSSSSSSSSSSS: "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strBranchID = "";
            int branchID = 0;
            int branchStatusID = 0;
            int branchCurrentStatus = 0;
            for(int j=0; j<paramHashMap.size(); j++)
            {
             String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
             Utility.logger.debug("tempStatusString "+tempStatusString);
             String keyValue = (String)paramHashMap.get(tempStatusString);
             if(tempStatusString.startsWith(DCMInterfaceKey.DCM_POS_SEARCH_BRANCH_STATUS) &&
                !tempStatusString.equals(DCMInterfaceKey.DCM_POS_SEARCH_BRANCH_STATUS))
             {
                strBranchID = tempStatusString.substring(21);
                branchID = Integer.parseInt(strBranchID);
                branchStatusID = Integer.parseInt(keyValue);
                Utility.logger.debug("POS ID :"+branchID);
                Utility.logger.debug("POS new Status :"+branchStatusID);
                branchCurrentStatus = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_BRANCH_STATUS+"_"+branchID));
                Utility.logger.debug("POS ID :"+branchID);
                Utility.logger.debug("POS new Status :"+branchStatusID);
                Utility.logger.debug("POS current Status :"+branchCurrentStatus);
             
               if(branchStatusID != branchCurrentStatus)
               {
                  BranchDAO.updateBranchStatus(con,branchStatusID+"" , branchID+"");                                    
               }
             }
            }
            String branchName = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_NAME);        
            String branchCode = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_CODE);            
            int branchStatus = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_STATUS));
            String mainBranchName = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_MAIN_BRANCH_NAME);
            String startDateFrom = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_START_DATE_FROM);
            String startDateTo = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_START_DATE_TO);            
            String endDateFrom = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_END_DATE_FROM);
            String endDateTo = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_END_DATE_TO);            
            Vector branchesSearchResult  = BranchDAO.getBranchesByFilter(con ,branchName,branchCode,mainBranchName,branchStatus,
                                                                          startDateFrom , startDateTo ,endDateFrom ,endDateTo);
            GenericModel branchStatusModel = GenericModelDAO.getColumns(con, "DCM_BRANCH_STATUS");
            Vector branchStausVector = GenericModelDAO.getModels(con ,branchStatusModel);
             dataHashMap.put(DCMInterfaceKey.DCM_POS_BRANCH_STATUS_VECTOR , branchStausVector);  
             dataHashMap.put(DCMInterfaceKey.DCM_POS_BRANCHES_SEARCH_RESULT , branchesSearchResult);

            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_NAME,branchName);        
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_CODE,branchCode);            
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_STATUS,branchStatus+"");
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_MAIN_BRANCH_NAME,mainBranchName);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_START_DATE_FROM,startDateFrom);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_START_DATE_TO,startDateTo);            
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_END_DATE_FROM,endDateFrom);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_END_DATE_TO,endDateTo);
            dataHashMap.put(DCMInterfaceKey.DCM_SEARCH_FLAG , true+"");

            
        }
        break;
        case DCM_SAVE_NEW_BRANCH:
        {
            dataHashMap = new HashMap();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("USERSSSSSSSSSSSSSSSSSS: "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String branchName = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_NAME);
            int branchPOSCount = Integer.parseInt((String)paramHashMap.get("branches_count"));
            String branchID = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_BRANCH_ID);
            Utility.logger.debug("branchID :  "+branchID);
            String mainBranch = "" , mainSalesBranch= "" , branchPOSID = "";
            BranchModel branchModel = null;
            Vector branchesVector = new Vector();
            String branch_state = "";
            for(int i = 1 ; i <= branchPOSCount ; i++ )
            {
               branchModel = new BranchModel();
               branchModel.set_pos_code((String)paramHashMap.get("branches__R"+i+"__C1"));
               branchModel.set_start_date(Date.valueOf((String)paramHashMap.get("branches__R"+i+"__C3")));
               branchModel.set_end_date(Date.valueOf((String)paramHashMap.get("branches__R"+i+"__C5")));
               mainBranch =(String)paramHashMap.get("CHK__branches__R"+i+"__C7");
               mainSalesBranch=(String)paramHashMap.get("CHK__branches__R"+i+"__C8");
               Utility.logger.debug(mainBranch);
               Utility.logger.debug(mainSalesBranch);
               
               if(mainSalesBranch != null)
                 if(mainSalesBranch.equals("true")) branchModel.set_main_sales_branch(1);
               if(mainSalesBranch != null)
                 if(mainSalesBranch.equals("false")) branchModel.set_main_sales_branch(0);
               if(mainBranch != null)
                if(mainBranch.equals("true")) branchModel.set_main_branch(1);
               if(mainBranch != null)
                if(mainBranch.equals("false")) branchModel.set_main_branch(0);
                
               if(mainSalesBranch == null) branchModel.set_main_sales_branch(0);
               if(mainBranch == null) branchModel.set_main_branch(0);
               String branch_pos_id = (String)paramHashMap.get("branches__R"+i+"__C11");
               Utility.logger.debug("branch_pos_id: "+branch_pos_id);
               if(branch_pos_id !=null)
                 branchModel.set_branch_pos_id(Integer.parseInt(branch_pos_id));
                else
                branchModel.set_branch_pos_id(0);
               branchModel.set_branch_name(branchName);
               branch_state = (String)paramHashMap.get("branches__R"+i+"__C10");
               if(!branchID.equals("0"))
               {
                 if(branch_state!=null && branch_state.equals("new"))
                   BranchDAO.saveEditedBranch(con , branchModel , branchID , 1);
                 else
                  BranchDAO.saveEditedBranch(con , branchModel , branchID , 0);
               }
               else
               {
                 branchesVector.add(branchModel);
               }
            }
            if(branchesVector != null )
              BranchDAO.addNewBranch(con , branchesVector);

            GenericModel branchStatusModel = GenericModelDAO.getColumns(con, "DCM_BRANCH_STATUS");
            Vector branchStausVector = GenericModelDAO.getModels(con ,branchStatusModel);
             dataHashMap.put(DCMInterfaceKey.DCM_POS_BRANCH_STATUS_VECTOR , branchStausVector);
             dataHashMap.put(DCMInterfaceKey.DCM_SEARCH_FLAG,false+"");
              
 
          
        }
        break;

        case DCM_SAVE_NEW_FUNCTION:
        {
            dataHashMap = new HashMap();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("USERSSSSSSSSSSSSSSSSSS: "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            if(Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.DCM_FUNCTION_HIDDEN_ID))==0){
            Utility.logger.debug("INSERT" +Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.DCM_FUNCTION_HIDDEN_ID)));
            String functionName = (String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_NAME);
            String functionDesc = (String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_DESCRIPTION);
            String functionUnit = (String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_UNIT);            
            FunctionDAO.addNewFunction(con,functionName,functionDesc,functionUnit);
            }
            else
            {
            Utility.logger.debug("UPDATE" +Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.DCM_FUNCTION_HIDDEN_ID)));            
              String functionName = (String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_NAME);
              String functionDesc = (String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_DESCRIPTION);
              String functionUnit = (String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_UNIT);            
              int functionID = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.DCM_FUNCTION_HIDDEN_ID));
              FunctionDAO.saveEditedFunction(con,functionName,functionDesc,functionUnit,functionID);
              
            }
            GenericModel genericModel = GenericModelDAO.getColumns(con,"DCM_FUNCTION_STATUS");
            Vector functionStatus = GenericModelDAO.getModels(con, genericModel);
            dataHashMap.put(DCMInterfaceKey.DCM_FUNCTION_STATUS_VECTOR , functionStatus);
            
        }
        break;

        case DCM_SEARCH_FUNCTION:
        {
            dataHashMap = new HashMap();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("USERSSSSSSSSSSSSSSSSSS: "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String functionName = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_NAME);
            int functionStatusID = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_STATUS));
            Vector functionSearchResult = FunctionDAO.getFunctionsByFilter(con, functionName , functionStatusID,"2");
            dataHashMap.put(DCMInterfaceKey.DCM_FUNCTION_SEARCH_VECTOR , functionSearchResult);
            GenericModel genericModel = GenericModelDAO.getColumns(con,"DCM_FUNCTION_STATUS");
            Vector functionStatus = GenericModelDAO.getModels(con, genericModel);
            dataHashMap.put(DCMInterfaceKey.DCM_FUNCTION_STATUS_VECTOR , functionStatus);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_NAME , functionName);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_STATUS , functionStatusID+"");

            
          
        }
        break;

        case DCM_EDIT_FUNCTION:
        {
            dataHashMap = new HashMap();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("USERSSSSSSSSSSSSSSSSSS: "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            Utility.logger.debug("dsdsdsdsd "+(String)paramHashMap.get(DCMInterfaceKey.DCM_FUNCTION_HIDDEN_ID));
            int functionID = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.DCM_FUNCTION_HIDDEN_ID));
            FunctionModel functionModel =  FunctionDAO.get_function_by_id(con , functionID);
            dataHashMap.put(DCMInterfaceKey.DCM_EDITED_FUNCTION_MODEL , functionModel);
          
        }
        break;

        case DCM_UPDATE_FUNCTION_STATUS:
        {
            dataHashMap = new HashMap();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("USERSSSSSSSSSSSSSSSSSS: "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strFunctionID = "";
            int functionID = 0;
            int functionStatusID = 0;
            int functionCurrentStatus = 0;
            for(int j=0; j<paramHashMap.size(); j++)
            {
             String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
             String keyValue = (String)paramHashMap.get(tempStatusString);
             if(tempStatusString.startsWith(DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_STATUS) &&
                !tempStatusString.equals(DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_STATUS))
             {
                strFunctionID = tempStatusString.substring(16);
                functionID = Integer.parseInt(strFunctionID);
                functionStatusID = Integer.parseInt(keyValue);
                Utility.logger.debug("POS ID :"+functionID);
                Utility.logger.debug("POS new Status :"+functionStatusID);
                functionCurrentStatus = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_FUNCTION_STATUS+"_"+functionID));
                Utility.logger.debug("POS ID :"+functionID);
                Utility.logger.debug("POS new Status :"+functionStatusID);
                Utility.logger.debug("POS current Status :"+functionCurrentStatus);
             
               if(functionStatusID != functionCurrentStatus)
               {
                  FunctionDAO.updateFunctionStatus(con,functionStatusID , functionID);                                    
               }
             }
            }
                       
            
            String functionName = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_NAME);
            functionStatusID = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_STATUS));
            Vector functionSearchResult = FunctionDAO.getFunctionsByFilter(con, functionName , functionStatusID,"2");
            dataHashMap.put(DCMInterfaceKey.DCM_FUNCTION_SEARCH_VECTOR , functionSearchResult);
            GenericModel genericModel = GenericModelDAO.getColumns(con,"DCM_FUNCTION_STATUS");
            Vector functionStatus = GenericModelDAO.getModels(con, genericModel);
            dataHashMap.put(DCMInterfaceKey.DCM_FUNCTION_STATUS_VECTOR , functionStatus);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_NAME , functionName);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_STATUS , functionStatusID+"");
        }
        break;

        case DCM_SEARCH_GROUPS:
        {
            dataHashMap = new HashMap();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("USERSSSSSSSSSSSSSSSSSS: "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String groupName = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_NAME);          
            String groupPOSName = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_NAME);
            String groupPOSCode = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_CODE);
            int groupStatusID = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_STATUS));
            int groupFunctionID = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_FUNCTION_NAME));
            Vector groupSearchResult = GroupDAO.getGroupsByFilter(con,groupName,groupFunctionID,groupStatusID,groupPOSName,groupPOSCode);
            dataHashMap.put(DCMInterfaceKey.DCM_GROUP_SEARCH_RESULT , groupSearchResult);
            GenericModel genericModel = GenericModelDAO.getColumns(con,"DCM_GROUP_STATUS");
            Vector groupStatusVector = GenericModelDAO.getModels(con,genericModel);
            dataHashMap.put(DCMInterfaceKey.VECTOR_GROUP_STATUS , groupStatusVector);
            Vector groupFunctionVector = FunctionDAO.getFunctionsByFilter(con,"",1,"2");
            dataHashMap.put(DCMInterfaceKey.VECTOR_GROUP_FUNCTION , groupFunctionVector);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_NAME,groupName);          
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_NAME,groupPOSName);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_CODE,groupPOSCode);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_STATUS,groupStatusID+"" );
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_FUNCTION_NAME,groupFunctionID+"");
        }
        break;

        case DCM_SAVE_NEW_GROUP:
        {
            dataHashMap = new HashMap();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("USERSSSSSSSSSSSSSSSSSS: "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String groupName = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_NAME);
            String groupDesc = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_DESC);
            GroupDAO.addNewGroup(con,groupName , groupDesc);   
            GenericModel genericModel = GenericModelDAO.getColumns(con,"DCM_GROUP_STATUS");
            Vector groupStatusVector = GenericModelDAO.getModels(con,genericModel);
            dataHashMap.put(DCMInterfaceKey.VECTOR_GROUP_STATUS , groupStatusVector);
            Vector groupFunctionVector = FunctionDAO.getFunctionsByFilter(con,"",1,"2");
            dataHashMap.put(DCMInterfaceKey.VECTOR_GROUP_FUNCTION , groupFunctionVector);
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_NAME,"");          
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_NAME,"");
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_CODE,"");
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_STATUS,"0" );
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_FUNCTION_NAME,"0");
            
          
        }
        break;

        case DCM_SAVE_EDITED_GROUP:
        {
            dataHashMap = new HashMap();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("USERSSSSSSSSSSSSSSSSSS: "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String groupName = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_NAME);
            int groupID = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_GROUP_ID));
            String groupDesc = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_DESC);
            GroupDAO.editGroup(con,groupName,groupDesc, groupID);
            

          
        }
        break;

        case DCM_SAVE_GROUP_FUNCTION:
        {
            dataHashMap = new HashMap();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("USERSSSSSSSSSSSSSSSSSS: "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            int functionCount=Integer.parseInt((String)paramHashMap.get("functions_count"));
            String functionID = "";
            String functionTarget = "";
            String functionTargetType = "";
            String status = "";
            String error = "";
            String groupID = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_GROUP_ID);
              Utility.logger.debug("functions:  count is :  "+functionCount);
            for(int i = 1 ; i <= functionCount ; i++)
            {
            
              functionID = (String)paramHashMap.get("functions__R"+i+"__C1");
              Utility.logger.debug(functionID);
              functionTarget = (String)paramHashMap.get("functions__R"+i+"__C2");
              Utility.logger.debug(functionTarget);              
              functionTargetType = (String)paramHashMap.get("functions__R"+i+"__C3");
              Utility.logger.debug(functionTargetType);              
              status  = (String)paramHashMap.get("functions__R"+i+"__C4");
              Utility.logger.debug(status); 
              Utility.logger.debug("functions: count now is " + i);
              if(status == null)
              {
                Utility.logger.debug("Functions I am In");
                functionID = (String)paramHashMap.get("functions__R"+i+"__C7");
                GroupDAO.deleteGroupFunction(con,groupID,functionID);
                Utility.logger.debug("function id   : "+functionID);
                continue;
              }
              if(status.equalsIgnoreCase("new")){
               boolean saved = GroupDAO.saveGroupFunctions(con , functionID , functionTarget , functionTargetType, groupID);
              Utility.logger.debug("SAVED!!!!!!!!!!!!");    
              if(saved == false)
              error = "One or more fields is not correct";
              }
              
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE , error);            
            GroupModel groupModel = GroupDAO.getGroupByID(con,groupID);
            dataHashMap.put(DCMInterfaceKey.DCM_EDITED_GROUP_MODEL , groupModel);
            Vector functionVector = FunctionDAO.getFunctionsByFilter(con,"",1,"2");
            dataHashMap.put(DCMInterfaceKey.DCM_GROUP_FUNCTION_VECTOR , functionVector);
            GenericModel targetModel  = GenericModelDAO.getColumns(con,"DCM_TARGET_DURATION_TYPE");
            Vector targetTypes = GenericModelDAO.getModels(con , targetModel);
            dataHashMap.put(DCMInterfaceKey.VECTOR_GROUP_FUNCTION_TARGET , targetTypes);
        }
        break;
        
        case DCM_SAVE_GROUP_POS:
        {
            dataHashMap = new HashMap();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("USERSSSSSSSSSSSSSSSSSS: "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID); 
            int posCount=Integer.parseInt((String)paramHashMap.get("pos_count"));
            String posName = "";
            String posCode = "";
            String status = "";
     
            String error = "";
            String groupID = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_GROUP_ID);
            Utility.logger.debug("COUNT IS :  "+ posCount);
            for(int i = 1 ; i <= posCount ; i++)
            {
              Utility.logger.debug("CIOUNT NOW IS: "+i);
              posName = (String)paramHashMap.get("pos__R"+i+"__C2");
              posCode = (String)paramHashMap.get("pos__R"+i+"__C1");
              status  = (String)paramHashMap.get("pos__R"+i+"__C3");
              if(status == null) 
              {
              Utility.logger.debug("I AM IN");
              String posID = (String)paramHashMap.get("pos__R"+i+"C__6");
              GroupDAO.deleteGroupPOS(con,groupID,posID);
              Utility.logger.debug("POS ID :  "+posID);
               continue;
              }
              if(status.equalsIgnoreCase("new")){
                boolean saved = GroupDAO.saveGroupPOS(con , posName , posCode , groupID);
              Utility.logger.debug("saved "+saved);
              if(saved == false)
              error = "One or more fields is not correct";
              }
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE , error);
            GroupModel groupModel = GroupDAO.getGroupByID(con,groupID);
            dataHashMap.put(DCMInterfaceKey.DCM_EDITED_GROUP_MODEL , groupModel);

            GenericModel genericModel = GenericModelDAO.getColumns(con,"DCM_GROUP_STATUS");
            Vector groupStatusVector = GenericModelDAO.getModels(con,genericModel);
            dataHashMap.put(DCMInterfaceKey.VECTOR_GROUP_STATUS , groupStatusVector);
            Vector groupFunctionVector = FunctionDAO.getFunctionsByFilter(con,"",1,"2");
            dataHashMap.put(DCMInterfaceKey.VECTOR_GROUP_FUNCTION , groupFunctionVector);

            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_NAME,"");          
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_NAME,"");
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_CODE,"");
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_STATUS,"0" );
            dataHashMap.put(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_FUNCTION_NAME,"0");

        }
        break;
        case DCM_CREATE_NEW_POS_GROUP:
        {
            dataHashMap = new HashMap();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("USERSSSSSSSSSSSSSSSSSS: "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID); 
          
        }
        break;
        case DCM_CREATE_NEW_CASE_FROM_ACTUAL_VISIT_FOR_SERVICE_REQUEST:
        {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strPosCode = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_ACTUAL_VISIT_POS_CODE);
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
            
            CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseDTO);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_CREATE_NEW_CASE);
            dataHashMap.put(HLPInterfaceKey.PAGE_HEADER,"Open New Case");

            String actualVisitId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID);
            dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID,actualVisitId);
            dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_FUNCTION_ID,DCMInterfaceKey.CONST_DCM_PREDEFINED_FUNCTION_SERVICE_REQUEST);
            String serviceId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID);
            dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID,serviceId);
            
        }
        break;
        case DCM_CREATE_NEW_CASE_FROM_ACTUAL_VISIT:
        {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            String strPosCode = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_ACTUAL_VISIT_POS_CODE);
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
            
            CaseDTO caseDTO = CaseDAO.getCaseDTO(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,caseDTO);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,HLPInterfaceKey.ACTION_HLP_CREATE_NEW_CASE);
            dataHashMap.put(HLPInterfaceKey.PAGE_HEADER,"Open New Case");

            String actualVisitId = (String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID);
            dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID,actualVisitId);
            dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_FUNCTION_ID,DCMInterfaceKey.CONST_DCM_PREDEFINED_FUNCTION_CREATE_NEW_CASE);
        }
        break;
        case DCM_USER_CREATE_NEW_POS:
        {
            dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);

            Vector regions            = new Vector();
            Vector IDTypeVector       = new Vector();
            Vector legalFormVec       = new Vector();
            Vector placeTypeVec       = new Vector();
            
            GenericModel gm           = new GenericModel();
            GenericModel placeTypeGM  = new GenericModel();
            GenericModel IDTypeModel  = new GenericModel();
           
            GenericModelDAO gmDAO     = new GenericModelDAO();

            IDTypeModel = gmDAO.getColumns(con , "DCM_ID_TYPE");
            IDTypeVector = gmDAO.getModels(con , IDTypeModel);
            gm = gmDAO.getColumns(con , "DCM_LEGAL_FORM");
            legalFormVec = gmDAO.getModels(con , gm);
            placeTypeGM = gmDAO.getColumns(con , "DCM_POS_PLACE_TYPE");
            placeTypeVec = gmDAO.getModels(con , placeTypeGM);
            regions = RegionDAO.getAllRegions(con);
            dataHashMap.put(DCMInterfaceKey.VECTOR_ID_TYPE , IDTypeVector);
            dataHashMap.put(DCMInterfaceKey.VECTOR_LEGAL_FORM , legalFormVec);
            dataHashMap.put(DCMInterfaceKey.VECTOR_PLACE_TYPE ,placeTypeVec);
            dataHashMap.put(DCMInterfaceKey.VECTOR_REGIONS , regions);
            dataHashMap.put(DCMInterfaceKey.DCM_SAVE_POS_TYPE , DCMInterfaceKey.DCM_USER_SAVE_POS_TYPE_NEW);
            //dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            Utility.logger.debug("USERID:  "+ strUserID);

            String strFlagSuperAdmin = (String)paramHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
            dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG,strFlagSuperAdmin);
          
        }
        break;
        
        case DCM_ACTUAL_VISIT_EDIT_POS:
        {
            dataHashMap = new HashMap ();
            String strPosID = (String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_POS_ID);
            int posID = Integer.parseInt(strPosID);
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Utility.logger.debug("POS IDDDDDDDDDDD: "+ strPosID);
            Utility.logger.debug("USER ID:  "+strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            Vector regionVec =  new Vector();
            Vector legalFormVec =  new Vector();
            Vector IDTypeVec =  new Vector();
            Vector placeTypeVec =  new Vector();
            POSDetailModel posDetailModel = null;
            Vector POSPhones = new Vector();
            Vector OwnerPhones = new Vector();
            Vector ManagerPhones = new Vector();
            Vector POSPartner =  new Vector();
            GenericModel gm           = new GenericModel();
            GenericModel placeTypeGM  = new GenericModel();
            GenericModel IDTypeModel  = new GenericModel();
            
            GenericModelDAO gmDAO     = new GenericModelDAO();

            IDTypeModel = gmDAO.getColumns(con , "DCM_ID_TYPE");
            IDTypeVec = gmDAO.getModels(con , IDTypeModel);
            
            gm = gmDAO.getColumns(con , "DCM_LEGAL_FORM");
            legalFormVec = gmDAO.getModels(con , gm);
            placeTypeGM = gmDAO.getColumns(con , "DCM_POS_PLACE_TYPE");
            placeTypeVec = gmDAO.getModels(con , placeTypeGM);
            regionVec = RegionDAO.getAllRegions(con);
            dataHashMap.put(DCMInterfaceKey.VECTOR_ID_TYPE , IDTypeVec);
            dataHashMap.put(DCMInterfaceKey.VECTOR_LEGAL_FORM , legalFormVec);
            dataHashMap.put(DCMInterfaceKey.VECTOR_PLACE_TYPE ,placeTypeVec);
            dataHashMap.put(DCMInterfaceKey.VECTOR_REGIONS , regionVec);

            posDetailModel = PosDAO.getPOSByPOSID(con , posID);
            int pos_detail_id = posDetailModel.getPosID();
            POSPhones = PosDAO.getPOSPhones(con , pos_detail_id);
            posDetailModel.setPosPhones(POSPhones);
            OwnerPhones = OwnerDAO.getOwnerPhones(con , pos_detail_id);
            ManagerPhones = ManagerDAO.getManagerPhones(con , pos_detail_id);
            posDetailModel.setPosManagerPhones(ManagerPhones);
            posDetailModel.setPosOwnerPhones(OwnerPhones);
            POSPartner = PosDAO.getPOSPartners(con , pos_detail_id);
            posDetailModel.setPosPartners(POSPartner);
            dataHashMap.put(DCMInterfaceKey.POS_DETAIL_MODEL , posDetailModel);
            dataHashMap.put(DCMInterfaceKey.DCM_SAVE_POS_TYPE , DCMInterfaceKey.DCM_SAVE_POS_TYPE_EDIT);
            dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_POS_ID, strPosID);
          
        }
        break;
        
        default:
         Utility.logger.debug ("Unknown action received: " + action ); 
      }

     
    }

    catch(Exception objExp)
    {
      objExp.printStackTrace();
      
      
      throw objExp;
    }

    return dataHashMap;
  }
  
}
