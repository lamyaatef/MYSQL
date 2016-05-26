package com.mobinil.sds.core.facade.handlers;

import com.mobinil.sds.core.system.security.dao.*;
import com.mobinil.sds.core.system.security.dto.*;
import com.mobinil.sds.web.interfaces.*;
import com.mobinil.sds.web.interfaces.sa.*;



import java.util.*;

public class SecurityHandler 
{

//////////////////Static ints used to switch on the actions.//////////////////
  static final int GET_ALL_RULES = 1;

  static final int UPDATE_ALL_RULES = 2;

  static final int CHECK_ALL_RULES = 3;

    static final int GET_LOGS = 4;

        static final int SHOW_LOGS = 5;

  
  
    public static  HashMap handle(String action, HashMap paramHashMap,java.sql.Connection m_conSDSConnection)
  {

    int actionType = 0;
    HashMap dataHashMap = new HashMap(100);

    try
    {
      

      if(action.equals(SecurityInterfaceKey.ACTION_CHECK_RULES))
      {
        actionType = CHECK_ALL_RULES;
      }
      
      if(action.equals(SecurityInterfaceKey.ACTION_SHOW_CURRENT_RULES))
      {
        actionType = GET_ALL_RULES;
      }

      if(action.equals(SecurityInterfaceKey.ACTION_UPDATE_RULES))
      {
        actionType = UPDATE_ALL_RULES;
      }

      if(action.equals(SecurityInterfaceKey.ACTION_GET_LOG))
      {
        actionType = GET_LOGS;
      }
      
      if(action.equals(SecurityInterfaceKey.ACTION_SHOW_LOG))
      {
        actionType = SHOW_LOGS;
      }



      switch (actionType) 
      {
        case SHOW_LOGS:
        {
        }
        break;
        case GET_LOGS:
        {
          String strFromDate = (String) paramHashMap.get(logInterfaceKey.CONTROL_INPUT_NAME_FROM_DATE);
          String strToDate = (String) paramHashMap.get(logInterfaceKey.CONTROL_INPUT_NAME_TO_DATE);
          String strActionName = (String) paramHashMap.get(logInterfaceKey.CONTROL_INPUT_NAME_ACTION_NAME);
          String strActionUrl = (String) paramHashMap.get(logInterfaceKey.CONTROL_INPUT_NAME_ACTION_URL);
          String strUserIp = (String) paramHashMap.get(logInterfaceKey.CONTROL_INPUT_NAME_USER_IP);
          String strUserName = (String) paramHashMap.get(logInterfaceKey.CONTROL_INPUT_NAME_USER_ID);
          
          String condition = "";

          if (strFromDate==null&&strToDate==null&&strActionName.compareTo("")==0
          &&strActionUrl.compareTo("")==0&&strUserIp.compareTo("")==0&&strUserName.compareTo("")==0)
          {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP,"Missing information.");
          }
          else
          {
            if (strFromDate!=null && strToDate!=null)
            {
              condition = " And ACTION_TIME >= to_date('"+strFromDate+"','dd/mm/yyyy') And  ACTION_TIME <= to_date('"+strToDate+"','dd/mm/yyyy')";
            }

            if (strActionName.compareTo("")!=0)
            {
              condition = " And ACTION_NAME='"+strActionName+"'";
            }
            
            if (strActionUrl.compareTo("")!=0)
            {
              condition = " And ACTION_URL='"+strActionUrl+"'";
            }

            if (strUserIp.compareTo("")!=0)
            {
              condition = " And USER_IP='"+strUserIp+"'";
            }

            if (strUserName.compareTo("")!=0)
            {
              condition = " And PERSON_FULL_NAME='"+strUserName+"'";
            }

            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, securityDAO.getUserLogs(m_conSDSConnection,condition));          
          }
        }
        break;
        case GET_ALL_RULES:
        {
         dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, securityDAO.getSystemSetting(m_conSDSConnection));
         dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, securityDAO.getProps(m_conSDSConnection));
        }
        break;
        case UPDATE_ALL_RULES:
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, securityDAO.getSystemSetting(m_conSDSConnection));
         dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, securityDAO.getProps(m_conSDSConnection));
        }
        break;
        case CHECK_ALL_RULES:
        {
        try
         {
        int optionalCount=0;
        int intOptionalEntered = (Integer.valueOf((String)paramHashMap.get(SecurityInterfaceKey.CONTROL_TEXT_NAME_MINIMUM_OPTIONAL))).intValue();

        for(int j=0; j<paramHashMap.size(); j++)
          {
         String tempString = (String)paramHashMap.keySet().toArray()[j];
         if(tempString.startsWith(SecurityInterfaceKey.CONTROL_SELECT_NAME_TYPE_RULE))
         {
          String strOptional = (String)paramHashMap.get(tempString);
          int propId = new Integer(tempString.substring(
                                  tempString.lastIndexOf(
                     SecurityInterfaceKey.CONTROL_SELECT_NAME_TYPE_RULE)+12)).intValue();
          String strStatusProp = (String)paramHashMap.get(SecurityInterfaceKey.CONTROL_SELECT_NAME_STATUS_RULE+propId);                
          if (strOptional.compareTo("1")==0)
          {
          if(strStatusProp.compareTo("1")==0)
          {
            optionalCount++;
          }
            
          }
          }
         }
         if (intOptionalEntered>optionalCount)
         {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, 
                              "System Setting was not saved successfully .The Digit of Minimum Rule Optional greater than selected.");
         dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, securityDAO.getSystemSetting(m_conSDSConnection));
         dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, securityDAO.getProps(m_conSDSConnection));
         }
         else
         {
         dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "System Setting was updated successfully.");
         
        String a = (String)paramHashMap.get(SecurityInterfaceKey.CONTROL_TEXT_NAME_LOCK_TIMES);
        String b = (String)paramHashMap.get(SecurityInterfaceKey.CONTROL_TEXT_NAME_DAYS_EXPIRED_LOGIN);
        String c = (String)paramHashMap.get(SecurityInterfaceKey.CONTROL_TEXT_NAME_LAST_PASSWORD_COUNT);
        String d = (String)paramHashMap.get(SecurityInterfaceKey.CONTROL_TEXT_NAME_PASSWORD_LENGTH_PROP);
        String e = (String)paramHashMap.get(SecurityInterfaceKey.CONTROL_TEXT_NAME_MINIMUM_OPTIONAL);

        Integer.parseInt(a);
        Integer.parseInt(b);
        Integer.parseInt(c);
        Integer.parseInt(d);
        Integer.parseInt(e);

        Vector vecSetting = new Vector();
         securityDTO sdto =null;
          for(int j=0; j<paramHashMap.size(); j++)
          {
             String tempString = (String)paramHashMap.keySet().toArray()[j];
           if(tempString.startsWith(SecurityInterfaceKey.CONTROL_SELECT_NAME_TYPE_RULE)||tempString.startsWith(SecurityInterfaceKey.CONTROL_SELECT_NAME_STATUS_RULE))
            {
             sdto = new securityDTO();
            if (tempString.startsWith(SecurityInterfaceKey.CONTROL_SELECT_NAME_TYPE_RULE))
            {      
             int propId = new Integer(tempString.substring(
                                  tempString.lastIndexOf(
                     SecurityInterfaceKey.CONTROL_SELECT_NAME_TYPE_RULE)+12)).intValue();
            String strPropType = (String)paramHashMap.get(tempString);
            String strPropStatus = (String)paramHashMap.get(SecurityInterfaceKey.CONTROL_SELECT_NAME_STATUS_RULE+propId);
            Integer intPropType = new Integer(Integer.parseInt(strPropType));
            Integer intPropStatus = new Integer(Integer.parseInt(strPropStatus));
            
            sdto.setIntSECURITY_STATUS(intPropStatus);
            sdto.setSECUIRTY_TYPE(intPropType);
            sdto.setID(propId+"");
            vecSetting.addElement(sdto);
            }
          }

         }
         
         securityDAO.updateSysSetting(m_conSDSConnection,vecSetting);
         Vector vecProp = new Vector(); 
         vecProp.addElement((String)paramHashMap.get(SecurityInterfaceKey.CONTROL_TEXT_NAME_LOCK_TIMES));
         vecProp.addElement((String)paramHashMap.get(SecurityInterfaceKey.CONTROL_TEXT_NAME_DAYS_EXPIRED_LOGIN));
         vecProp.addElement((String)paramHashMap.get(SecurityInterfaceKey.CONTROL_TEXT_NAME_LAST_PASSWORD_COUNT));                 
         vecProp.addElement((String)paramHashMap.get(SecurityInterfaceKey.CONTROL_TEXT_NAME_PASSWORD_LENGTH_PROP));
         vecProp.addElement((String)paramHashMap.get(SecurityInterfaceKey.CONTROL_TEXT_NAME_MINIMUM_OPTIONAL));    
         securityDAO.updateProps(m_conSDSConnection,vecProp);
         
         }
        

         
         
         dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, securityDAO.getSystemSetting(m_conSDSConnection));
         dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, securityDAO.getProps(m_conSDSConnection));
         }
         catch(Exception e)
         {
         dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Invalid System Properties.It Must Be Numeric Digit.");
         dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, securityDAO.getSystemSetting(m_conSDSConnection));
         dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, securityDAO.getProps(m_conSDSConnection));
         }
        }
        break;
      }

      
        //Utility.closeConnection(m_conSDSConnection);
    }
    catch(Exception objExp)
    {
      objExp.printStackTrace();
    }
    return dataHashMap;

  }

}