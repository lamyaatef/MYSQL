package com.mobinil.sds.core.system.hlp.casepkg.dao;

import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.web.interfaces.hlp.*;
import com.mobinil.sds.core.system.hlp.casepkg.model.*;
import com.mobinil.sds.core.system.hlp.casepkg.dto.*;

import com.mobinil.sds.core.system.sa.users.dao.*;

public class CaseDAO 
{
  public CaseDAO()
  {
  }

  public static Vector getAllCaseStatus(Connection con)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from HLP_CASE_STATUS order by CASE_STATUS_ID");     
     while(res.next())
     {
       vec.add(new CaseStatusModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }
 

  public static Vector getAllCaseInfoElementTypes(Connection con)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from HLP_CASE_INFO_ELEMENT_TYPE order by CASE_INFO_ELEMENT_TYPE_ID");     
     while(res.next())
     {
       vec.add(new CaseInfoElementTypeModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static Vector getAllCaseInfoElements(Connection con,String caseId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from VW_HLP_CASE_INFO_ELEMENT where CASE_ID = "+caseId+" order by CASE_INFO_ELEMENT_TIMESTAMP DESC");     
     while(res.next())
     {
       vec.add(new CaseInfoElementModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static CaseInfoElementModel getCaseInfoElement(Connection con,String caseInfoElementId)
  {
    CaseInfoElementModel caseInfoElementModel = null;
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from VW_HLP_CASE_INFO_ELEMENT where CASE_INFO_ELEMENT_ID = "+caseInfoElementId+" ");     
     if(res.next())
     {
       caseInfoElementModel = new CaseInfoElementModel(res);
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return caseInfoElementModel; 
  }

  public static Vector getAllCaseTypes(Connection con)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select * from HLP_CASE_TYPE,HLP_CASE_SUPER_TYPE where HLP_CASE_TYPE.CASE_SUPER_TYPE_ID = HLP_CASE_SUPER_TYPE.CASE_SUPER_TYPE_ID order by CASE_TYPE_NAME";
     //Utility.logger.debug("The sql isssssssssssssssssssssssssss " + strSql);
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
       CaseTypeModel caseTypeModel = new CaseTypeModel(res) ;
       caseTypeModel.setCaseSuperTypeName(res.getString(caseTypeModel.CASE_SUPER_TYPE_NAME));
       vec.add(caseTypeModel);
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static CaseTypeModel getCaseType(Connection con,String caseTypeId)
  {
    CaseTypeModel caseTypeModel = null;
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select * from HLP_CASE_TYPE,HLP_CASE_SUPER_TYPE where HLP_CASE_TYPE.CASE_SUPER_TYPE_ID = HLP_CASE_SUPER_TYPE.CASE_SUPER_TYPE_ID and HLP_CASE_TYPE.CASE_TYPE_ID = "+caseTypeId+" ";
     ResultSet res= stat.executeQuery(strSql);     
     if(res.next())
     {
       caseTypeModel = new CaseTypeModel(res) ;
       caseTypeModel.setCaseSuperTypeName(res.getString(caseTypeModel.CASE_SUPER_TYPE_NAME));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return caseTypeModel; 
  }
  
  public static Vector getAllCaseCategories(Connection con)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from HLP_CASE_CATEGORY order by CASE_CATEGORY_NAME");     
     while(res.next())
     {
       vec.add(new CaseCategoryModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }
  
  public static CaseCategoryModel getCaseCategory(Connection con,String caseCategoryId)
  {
    CaseCategoryModel caseCategoryModel = null;
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from HLP_CASE_CATEGORY where CASE_CATEGORY_ID = "+caseCategoryId+" ");     
     if(res.next())
     {
       caseCategoryModel = new CaseCategoryModel(res);
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return caseCategoryModel; 
  }

  public static void insertCaseCategory(Connection con,String strCaseCategoryName)
  {
    try
    {
      Statement stat = con.createStatement();
      
      Long lCaseCategoryID = Utility.getSequenceNextVal(con, "SEQ_HLP_CASE_CATEGORY_ID");
      
      String strSql = "insert into HLP_CASE_CATEGORY(CASE_CATEGORY_ID,CASE_CATEGORY_NAME) "+
                      " values("+lCaseCategoryID+",'"+strCaseCategoryName+"')";
      //Utility.logger.debug(strSql);
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void insertCaseType(Connection con,String strCaseTypeName,String strCaseSuperTypeId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      Long lCaseTypeID = Utility.getSequenceNextVal(con, "SEQ_HLP_CASE_TYPE_ID");
      
      String strSql = "insert into HLP_CASE_TYPE(CASE_TYPE_ID,CASE_TYPE_NAME,CASE_SUPER_TYPE_ID) "+
                      " values("+lCaseTypeID+",'"+strCaseTypeName+"',"+strCaseSuperTypeId+")";
      //Utility.logger.debug(strSql);
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void insertCaseStatusWarning(Connection con,String strCaseStatusWarningName,String strCaseStatusId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      Long lCaseStatusWarningID = Utility.getSequenceNextVal(con, "SEQ_HLP_CASE_STATUS_WARNING_ID");
      
      String strSql = "insert into HLP_CASE_STATUS_WARNING(CASE_STATUS_WARNING_ID,CASE_STATUS_WARNING_NAME,CASE_STATUS_ID) "+
                      " values("+lCaseStatusWarningID+",'"+strCaseStatusWarningName+"',"+strCaseStatusId+")";
      //Utility.logger.debug(strSql);
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void updateCaseStatusWarning(Connection con,String strCaseStatusWarningId,String strCaseStatusWarningName,String strCaseStatusId)
  {
    try
    {
      Statement stat = con.createStatement();
            
      String strSql = "update HLP_CASE_STATUS_WARNING set CASE_STATUS_WARNING_NAME = '"+strCaseStatusWarningName+"',CASE_STATUS_ID = "+strCaseStatusId+" where CASE_STATUS_WARNING_ID = "+strCaseStatusWarningId+"   ";
          
      //Utility.logger.debug(strSql);
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void deleteCaseStatusWarning(Connection con,String strCaseStatusWarningId)
  {
    try
    {
      Statement stat = con.createStatement();
            
      String strSql = "delete from HLP_CASE_STATUS_WARNING where CASE_STATUS_WARNING_ID = "+strCaseStatusWarningId+"   ";
          
      //Utility.logger.debug(strSql);
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }
  
  public static void insertCaseSuperType(Connection con,String strCaseSuperTypeName)
  {
    try
    {
      Statement stat = con.createStatement();
      
      Long lCaseSuperTypeID = Utility.getSequenceNextVal(con, "SEQ_HLP_CASE_SUPER_TYPE_ID");
      
      String strSql = "insert into HLP_CASE_SUPER_TYPE(CASE_SUPER_TYPE_ID,CASE_SUPER_TYPE_NAME) "+
                      " values("+lCaseSuperTypeID+",'"+strCaseSuperTypeName+"')";
      //Utility.logger.debug(strSql);
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void insertCaseInfoElement(Connection con,String userId,String caseId,String caseInfoElementTitle,String caseInfoElementDate,String caseInfoElementDescription,String caseInfoElementTypeId,String contactName,String contactInfo)
  {
    try
    {
      Statement stat = con.createStatement();
      
      Long lCaseInfoElementID = Utility.getSequenceNextVal(con, "SEQ_HLP_CASE_INFO_ELEMENT_ID");
      
      String strSql = "insert into HLP_CASE_INFO_ELEMENT(CASE_INFO_ELEMENT_ID,USER_ID,CASE_INFO_ELEMENT_TIMESTAMP,CASE_INFO_ELEMENT_DATE,CASE_INFO_ELEMENT_TYPE_ID,CASE_INFO_ELEMENT_TITLE,CASE_INFO_ELEMENT_DESCRIPTION,CONTACT_NAME,CONTACT_INFO,CASE_ID) "+
                      " values("+lCaseInfoElementID+","+userId+",SYSDATE,TO_DATE('"+caseInfoElementDate+"','mm/dd/yyyy'),"+caseInfoElementTypeId+",'"+caseInfoElementTitle+"','"+caseInfoElementDescription+"','"+contactName+"','"+contactInfo+"',"+caseId+")";
      //Utility.logger.debug(strSql);
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void updateCaseInfoElement(Connection con,String caseInfoElementId,String caseInfoElementTitle,String caseInfoElementDate,String caseInfoElementDescription,String caseInfoElementTypeId,String contactName,String contactInfo)
  {
    try
    {
      Statement stat = con.createStatement();
      
      
      String strSql = "update HLP_CASE_INFO_ELEMENT set CASE_INFO_ELEMENT_DATE = TO_DATE('"+caseInfoElementDate+"','mm/dd/yyyy'), "+
                      " CASE_INFO_ELEMENT_TYPE_ID = "+caseInfoElementTypeId+" , CASE_INFO_ELEMENT_TITLE = '"+caseInfoElementTitle+"' , "+
                      " CASE_INFO_ELEMENT_DESCRIPTION = '"+caseInfoElementDescription+"' , CONTACT_NAME = '"+contactName+"' , CONTACT_INFO = '"+contactInfo+"' where CASE_INFO_ELEMENT_ID = "+caseInfoElementId+" ";
      //Utility.logger.debug(strSql);
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }
  
  public static void updateCaseCategory(Connection con,String strCaseCategoryId,String strCaseCategoryName)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String strSql = "update HLP_CASE_CATEGORY set CASE_CATEGORY_NAME = '"+strCaseCategoryName+"' where CASE_CATEGORY_ID = "+strCaseCategoryId+" ";
      //Utility.logger.debug(strSql);
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void updateCaseType(Connection con,String strCaseTypeId,String strCaseTypeName,String strCaseSuperTypeId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String strSql = "update HLP_CASE_TYPE set CASE_TYPE_NAME = '"+strCaseTypeName+"',CASE_SUPER_TYPE_ID = "+strCaseSuperTypeId+" where CASE_TYPE_ID = "+strCaseTypeId+" ";
      //Utility.logger.debug(strSql);
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void updateCaseSuperType(Connection con,String strCaseSuperTypeId,String strCaseSuperTypeName)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String strSql = "update HLP_CASE_SUPER_TYPE set CASE_SUPER_TYPE_NAME = '"+strCaseSuperTypeName+"' where CASE_SUPER_TYPE_ID = "+strCaseSuperTypeId+" ";
      Utility.logger.debug(strSql);
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void deleteCaseCategory(Connection con,String strCaseCategoryId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String strSql = "delete from HLP_CASE_CATEGORY where CASE_CATEGORY_ID = "+strCaseCategoryId+" ";
      //Utility.logger.debug(strSql);
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void deleteCaseSuperType(Connection con,String strCaseSuperTypeId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String strSql = "delete from HLP_CASE_SUPER_TYPE where CASE_SUPER_TYPE_ID = "+strCaseSuperTypeId+" ";
      //Utility.logger.debug(strSql);
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void deleteCaseType(Connection con,String strCaseTypeId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String strSql = "delete from HLP_CASE_TYPE where CASE_TYPE_ID = "+strCaseTypeId+" ";
      //Utility.logger.debug(strSql);
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static Vector getAllPosCases(Connection con,String posCode)
  {
    Vector vec = new Vector();
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from VW_HLP_CASE where POS_CODE = '"+posCode+"' ORDER BY INITIATE_TIMESTAMP DESC";
      ResultSet res = stat.executeQuery(strSql);
      while (res.next())
      {
        vec.add(new CaseModel(res));
      }
      stat.close();
    }
     catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }
  public static Vector getAllOpenedCases(Connection con)
  {
    Vector vec = new Vector();
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from VW_HLP_CASE where CASE_STATUS_NAME= 'Opened' order by INITIATE_TIMESTAMP DESC";
      ResultSet res = stat.executeQuery(strSql);
      while(res.next())
      {
        vec.add(new CaseModel(res));
      }
      stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec;
  }
  public static Vector getAllCases(Connection con)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from VW_HLP_CASE order by CASE_STATUS_ID,CASE_TITLE");     
     while(res.next())
     {
       vec.add(new CaseModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static Vector getAllCaseDetails(Connection con,String caseId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from VW_HLP_CASE_DETAIL where CASE_ID = "+caseId+" order by CASE_DETAIL_ID DESC");     
     while(res.next())
     {
       vec.add(new CaseDetailModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }
  
  public static CaseModel getCaseById(Connection con,String caseId)
  {
    CaseModel caseModel = null;
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from VW_HLP_CASE where CASE_ID = "+caseId+" ");     
     if(res.next())
     {
       caseModel = new CaseModel(res);
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return caseModel; 
  }
  
  public static Vector getCasesByReceiverId(Connection con,String receiverId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from VW_HLP_CASE where CURRENT_RECEIVER_USER_ID = "+receiverId+" order by CASE_STATUS_ID,CASE_TITLE");     
     while(res.next())
     {
       vec.add(new CaseModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static Vector getOpenedCasesByReceiverId(Connection con,String receiverId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from VW_HLP_CASE where CURRENT_RECEIVER_USER_ID = "+receiverId+" and CASE_STATUS_ID = 1 order by CASE_STATUS_ID,CASE_TITLE");     
     while(res.next())
     {
       vec.add(new CaseModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }
  public static Vector getAllOpenedCasesByFilter(Connection con,String strSearchCaseTitle,String strSearchCaseTypeId,String strSearchCaseCategoryId,String strSearchCaseSenderName,String strSearchCaseSendedFrom,String strSearchCaseSendedTo)
  {
    Vector vec = new Vector();
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from VW_HLP_CASE where CASE_STATUS_NAME= 'Opened'";
      if(strSearchCaseTitle.compareTo("")!=0)strSql += " and CASE_TITLE like '%"+strSearchCaseTitle+"%' "; 
      if(strSearchCaseTypeId.compareTo("")!=0)strSql += " and CASE_TYPE_ID = "+strSearchCaseTypeId+" ";
      if(strSearchCaseCategoryId.compareTo("")!=0)strSql += " and CASE_CATEGORY_ID = "+strSearchCaseCategoryId+" ";
      if(strSearchCaseSenderName.compareTo("")!=0)strSql += " and INITIATOR_FULL_NAME like '%"+strSearchCaseSenderName+"%' ";
      if(strSearchCaseSendedFrom.compareTo("*")!=0)strSql += " and INITIATE_TIMESTAMP >= TO_DATE('"+strSearchCaseSendedFrom+"','mm/dd/yyyy') ";
      if(strSearchCaseSendedTo.compareTo("*")!=0)strSql += " and INITIATE_TIMESTAMP <= TO_DATE('"+strSearchCaseSendedTo+"','mm/dd/yyyy')+1 ";
      strSql += " order by INITIATE_TIMESTAMP DESC"; 
      Utility.logger.debug(strSql); 
      ResultSet res= stat.executeQuery(strSql);     
      while(res.next())
      {
       vec.add(new CaseModel(res));
      }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }
  public static Vector getCasesByFilter(Connection con,String receiverId,String strSearchCaseTitle,String strSearchCaseTypeId,String strSearchCaseCategoryId,String strSearchCaseStatusId,String strSearchCaseSenderName,String strSearchCaseSendedFrom,String strSearchCaseSendedTo)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select * from VW_HLP_CASE where CURRENT_RECEIVER_USER_ID = "+receiverId+" ";
     if(strSearchCaseTitle.compareTo("")!=0)strSql += " and CASE_TITLE like '%"+strSearchCaseTitle+"%' ";   
     if(strSearchCaseTypeId.compareTo("")!=0)strSql += " and CASE_TYPE_ID = "+strSearchCaseTypeId+" ";
     if(strSearchCaseCategoryId.compareTo("")!=0)strSql += " and CASE_CATEGORY_ID = "+strSearchCaseCategoryId+" ";
     if(strSearchCaseStatusId.compareTo("")!=0)strSql += " and CASE_STATUS_ID = "+strSearchCaseStatusId+" ";
     if(strSearchCaseSenderName.compareTo("")!=0)strSql += " and INITIATOR_FULL_NAME like '%"+strSearchCaseSenderName+"%' ";
     if(strSearchCaseSendedFrom.compareTo("*")!=0)strSql += " and INITIATE_TIMESTAMP >= TO_DATE('"+strSearchCaseSendedFrom+"','mm/dd/yyyy') ";
     if(strSearchCaseSendedTo.compareTo("*")!=0)strSql += " and INITIATE_TIMESTAMP <= TO_DATE('"+strSearchCaseSendedTo+"','mm/dd/yyyy')+1 ";
            strSql += " order by CASE_STATUS_ID,CASE_TITLE";
     Utility.logger.debug(strSql);
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
       vec.add(new CaseModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static Vector getCasesDetailByCCReceiverId(Connection con,String ccReceiverId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select VW_HLP_CASE_DETAIL.* from VW_HLP_CASE_DETAIL "+
                     " where VW_HLP_CASE_DETAIL.CASE_DETAIL_ID in (select CASE_DETAIL_ID from VW_HLP_CASE_CC_RECEIVER where CC_RECEIVER_USER_ID = "+ccReceiverId+")";
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
       vec.add(new CaseDetailModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static Vector getCasesDetailByCCReceiverIdByFilter(Connection con,String ccReceiverId,String strSearchCaseTitle,String strSearchCaseTypeId,String strSearchCaseCategoryId,String strSearchCaseStatusId,String strSearchCaseSenderName,String strSearchCaseSendedFrom,String strSearchCaseSendedTo)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select VW_HLP_CASE_DETAIL.* from VW_HLP_CASE_DETAIL "+
                     " where VW_HLP_CASE_DETAIL.CASE_DETAIL_ID in (select CASE_DETAIL_ID from VW_HLP_CASE_CC_RECEIVER where CC_RECEIVER_USER_ID = "+ccReceiverId+") ";
     if(strSearchCaseTitle.compareTo("")!=0)strSql += " and CASE_TITLE like '%"+strSearchCaseTitle+"%' ";   
     if(strSearchCaseTypeId.compareTo("")!=0)strSql += " and CASE_TYPE_ID = "+strSearchCaseTypeId+" ";
     if(strSearchCaseCategoryId.compareTo("")!=0)strSql += " and CASE_CATEGORY_ID = "+strSearchCaseCategoryId+" ";
     if(strSearchCaseStatusId.compareTo("")!=0)strSql += " and CASE_STATUS_ID = "+strSearchCaseStatusId+" ";
     if(strSearchCaseSenderName.compareTo("")!=0)strSql += " and SENDER_FULL_NAME like '%"+strSearchCaseSenderName+"%' ";
     if(strSearchCaseSendedFrom.compareTo("*")!=0)strSql += " and CASE_TIMESTAMP >= TO_DATE('"+strSearchCaseSendedFrom+"','mm/dd/yyyy') ";
     if(strSearchCaseSendedTo.compareTo("*")!=0)strSql += " and CASE_TIMESTAMP <= TO_DATE('"+strSearchCaseSendedTo+"','mm/dd/yyyy')+1 ";
    Utility.logger.debug("The query issssssssssssssss " + strSql);
     ResultSet res= stat.executeQuery(strSql);      
     while(res.next())
     {
       vec.add(new CaseDetailModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }
  
  public static Vector getAllCasePriorities(Connection con)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from HLP_CASE_PRIORITY order by CASE_PRIORITY_ID");     
     while(res.next())
     {
       vec.add(new CasePriorityModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static Vector getAllCaseStatusWarnings(Connection con)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from HLP_CASE_STATUS_WARNING,HLP_CASE_STATUS where HLP_CASE_STATUS_WARNING.CASE_STATUS_ID = HLP_CASE_STATUS.CASE_STATUS_ID order by HLP_CASE_STATUS_WARNING.CASE_STATUS_ID,HLP_CASE_STATUS_WARNING.CASE_STATUS_WARNING_NAME");     
     while(res.next())
     {
       CaseStatusModel caseStatusModel = new CaseStatusModel();
       caseStatusModel.setCaseStatusId(res.getString(caseStatusModel.CASE_STATUS_ID));
       caseStatusModel.setCaseStatusWarningId(res.getString(caseStatusModel.CASE_STATUS_WARNING_ID));
       caseStatusModel.setCaseStatusWarningName(res.getString(caseStatusModel.CASE_STATUS_WARNING_NAME)); 
       caseStatusModel.setCaseStatusName(res.getString(caseStatusModel.CASE_STATUS_NAME));
        
       vec.add(caseStatusModel);
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static Vector getAllDialNumberTypes(Connection con)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from HLP_DIAL_NUMBER_TYPE order by DIAL_NUMBER_TYPE_ID");     
     while(res.next())
     {
       vec.add(new DialNumberTypeModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static Vector getAllCaseDirections(Connection con)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from HLP_CASE_DIRECTION order by CASE_DIRECTION_ID");     
     while(res.next())
     {
       vec.add(new CaseDirectionModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }
  
  public static CaseStatusModel getCaseStatusWarning(Connection con,String caseStatusWarningId)
  {
    CaseStatusModel caseStatusModel = null;
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from HLP_CASE_STATUS_WARNING,HLP_CASE_STATUS where HLP_CASE_STATUS_WARNING.CASE_STATUS_ID = HLP_CASE_STATUS.CASE_STATUS_ID and HLP_CASE_STATUS_WARNING.CASE_STATUS_WARNING_ID = "+caseStatusWarningId+" ");     
     while(res.next())
     {
       caseStatusModel = new CaseStatusModel();
       caseStatusModel.setCaseStatusId(res.getString(caseStatusModel.CASE_STATUS_ID));
       caseStatusModel.setCaseStatusWarningId(res.getString(caseStatusModel.CASE_STATUS_WARNING_ID));
       caseStatusModel.setCaseStatusWarningName(res.getString(caseStatusModel.CASE_STATUS_WARNING_NAME)); 
       caseStatusModel.setCaseStatusName(res.getString(caseStatusModel.CASE_STATUS_NAME));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return caseStatusModel; 
  }

  public static Vector getAllCaseSuperTypes(Connection con)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from HLP_CASE_SUPER_TYPE order by CASE_SUPER_TYPE_NAME");     
     while(res.next())
     {
       CaseTypeModel caseTypeModel = new CaseTypeModel(); 
       caseTypeModel.setCaseSuperTypeId(res.getString(caseTypeModel.CASE_SUPER_TYPE_ID));
       caseTypeModel.setCaseSuperTypeName(res.getString(caseTypeModel.CASE_SUPER_TYPE_NAME));
       vec.add(caseTypeModel);
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static CaseTypeModel getCaseSuperType(Connection con,String caseSuperTypeId)
  {
    CaseTypeModel caseTypeModel = null;
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from HLP_CASE_SUPER_TYPE where CASE_SUPER_TYPE_ID = "+caseSuperTypeId+" ");     
     while(res.next())
     {
       caseTypeModel = new CaseTypeModel(); 
       caseTypeModel.setCaseSuperTypeId(res.getString(caseTypeModel.CASE_SUPER_TYPE_ID));
       caseTypeModel.setCaseSuperTypeName(res.getString(caseTypeModel.CASE_SUPER_TYPE_NAME));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return caseTypeModel; 
  }

  public static CaseDTO getCaseDTO(Connection con)
  {
    CaseDTO caseDTO = new CaseDTO();
    try
    {
      Vector usersVector = UserDAO.getSystemUsersList(con,"PERSON_EMAIL");
      caseDTO.setUserList(usersVector);
      Vector caseTypeList = CaseDAO.getAllCaseTypes(con);
      caseDTO.setCaseTypeList(caseTypeList);
      Vector caseCategoryList = CaseDAO.getAllCaseCategories(con);
      caseDTO.setCaseCategoryList(caseCategoryList);
      Vector casePriorityList = CaseDAO.getAllCasePriorities(con);
      caseDTO.setCasePriorityList(casePriorityList);
      Vector caseSuperTypeList = CaseDAO.getAllCaseSuperTypes(con);
      caseDTO.setCaseSuperTypeList(caseSuperTypeList);
      Vector caseStatusList = CaseDAO.getAllCaseStatus(con);
      caseDTO.setCaseStatusList(caseStatusList);
      Vector caseStatusWarningList = CaseDAO.getAllCaseStatusWarnings(con);
      caseDTO.setCaseStatusWarningList(caseStatusWarningList);
      Vector dialNumberType = CaseDAO.getAllDialNumberTypes(con);
      caseDTO.setDialNumberType(dialNumberType);
      Vector caseDirection = CaseDAO.getAllCaseDirections(con);
      caseDTO.setCaseDirection(caseDirection);
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return caseDTO; 
  }

  public static Long insertCase(Connection con,String strUserId,String strReceiverId,String strCaseTitle,String strCaseDesc,String strCaseTypeId,String strCaseCategoryId,String strCasePriorityId,String strUserComments,String strCCReceiverIds,String posCode,String dialNumber,String dialNumberTypeId,String caseDirectionId)
  {
    Long lCaseID = null;
    try
    {
      Statement stat = con.createStatement();

      lCaseID = Utility.getSequenceNextVal(con, "SEQ_HLP_CASE_ID");
      
      String strSql = "insert into HLP_CASE(CASE_ID,CASE_TITLE,CASE_DESCRIPTION,INITIATOR_USER_ID,INITIATE_TIMESTAMP,CASE_TYPE_ID,CASE_STATUS_ID,CASE_PRIORITY_ID,CASE_CATEGORY_ID,POS_CODE,DIAL_NUMBER,DIAL_NUMBER_TYPE_ID,CASE_DIRECTION_ID) "+
                      " values("+lCaseID+",'"+strCaseTitle+"','"+strCaseDesc+"',"+strUserId+",SYSDATE,"+strCaseTypeId+",1,"+strCasePriorityId+","+strCaseCategoryId+",'"+posCode+"',"+dialNumber+","+dialNumberTypeId+","+caseDirectionId+")";
      Utility.logger.debug(strSql);
      stat.execute(strSql);  
      CaseDAO.insertCaseDetail(con,lCaseID+"",strUserId,strReceiverId,strCaseTitle,strCaseDesc,strCaseTypeId,strCaseCategoryId,strCasePriorityId,strUserComments,"1","",strCCReceiverIds,posCode,dialNumber,dialNumberTypeId,caseDirectionId);
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
    return lCaseID;
  }

  public static void insertCaseDetail(Connection con,String lCaseId,String strUserId,String strReceiverId,String strCaseTitle,String strCaseDesc,String strCaseTypeId,String strCaseCategoryId,String strCasePriorityId,String strUserComments,String strCaseStatusId,String strCaseStatusWarningId,String strCCReceiverIds,String posCode,String dialNumber,String dialNumberTypeId,String caseDirectionId)
  {
    try
    {
      Statement stat = con.createStatement();

      Long lCaseDetailID = Utility.getSequenceNextVal(con, "SEQ_HLP_CASE_DETAIL_ID");
      
      if(strCaseStatusWarningId.compareTo("")==0)strCaseStatusWarningId="null";

      String strSql = "insert into HLP_CASE_DETAIL(CASE_DETAIL_ID,CASE_ID,CASE_TITLE,CASE_DESCRIPTION,SENDER_USER_ID,RECEIVER_USER_ID,CASE_TIMESTAMP,CASE_STATUS_ID,RECEIVER_COMMENT,CASE_PRIORITY_ID,CASE_TYPE_ID,CASE_CATEGORY_ID,CASE_STATUS_WARNING_ID,POS_CODE,DIAL_NUMBER,DIAL_NUMBER_TYPE_ID,CASE_DIRECTION_ID) "+
                      " values("+lCaseDetailID+","+lCaseId+",'"+strCaseTitle+"','"+strCaseDesc+"',"+strUserId+","+strReceiverId+",SYSDATE,"+strCaseStatusId+",'"+strUserComments+"',"+strCasePriorityId+","+strCaseTypeId+","+strCaseCategoryId+","+strCaseStatusWarningId+",'"+posCode+"',"+dialNumber+","+dialNumberTypeId+","+caseDirectionId+")";
      Utility.logger.debug(strSql);
      stat.execute(strSql);

      StringTokenizer st = new StringTokenizer(strCCReceiverIds,",");
      while (st.hasMoreTokens()) 
      {
         String strCCReceiverId = st.nextToken();
         CaseDAO.insertCaseCCReceiver(con,lCaseDetailID,strCCReceiverId);
      }
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void insertCaseCCReceiver(Connection con,Long lCaseDetailId,String strCCReceiverId)
  {
    try
    {
      Statement stat = con.createStatement();

      Long lCaseCCReceiverID = Utility.getSequenceNextVal(con, "SEQ_HLP_CASE_CC_RECEIVER_ID");
      
      String strSql = "insert into HLP_CASE_CC_RECEIVER(CASE_CC_RECEIVER_ID,CASE_DETAIL_ID,CC_RECEIVER_USER_ID) "+
                      " values("+lCaseCCReceiverID+","+lCaseDetailId+","+strCCReceiverId+")";

      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void updateCaseStatus(Connection con,String strCaseId,String strCaseStatusId,String strCaseStatusWarningId)
  {
    try
    {
      Statement stat = con.createStatement();
      if(strCaseStatusWarningId.compareTo("")==0)strCaseStatusWarningId = "null";
      String strSql = "update HLP_CASE set CASE_STATUS_ID = "+strCaseStatusId+",CASE_STATUS_WARNING_ID = "+strCaseStatusWarningId+" where CASE_ID = "+strCaseId+" ";
      //Utility.logger.debug(strSql);
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void updateCase(Connection con,String strCaseId,String strUserId,String strCaseTitle,String strCaseDesc,String strCaseTypeId,String strCaseCategoryId,String strCasePriorityId,String strUserComments,String dialNumber,String dialNumberTypeId,String caseDirectionId,String caseStatusId,String caseStatusWarningId)
  {
    try
    {
      Statement stat = con.createStatement();

      String strSql = "update HLP_CASE set CASE_TITLE = '"+strCaseTitle+"' , "+
                      " CASE_DESCRIPTION = '"+strCaseDesc+"' , "+
                      " CASE_TYPE_ID = "+strCaseTypeId+" , "+
                      " CASE_PRIORITY_ID = "+strCasePriorityId+" , "+
                      " CASE_CATEGORY_ID = "+strCaseCategoryId+" , "+
                      " DIAL_NUMBER = "+dialNumber+" , "+
                      " DIAL_NUMBER_TYPE_ID = "+dialNumberTypeId+" , "+
                      " CASE_DIRECTION_ID = "+caseDirectionId+" , "+
                      " CASE_STATUS_ID = "+caseStatusId+", "+
                      " CASE_STATUS_WARNING_ID = '"+caseStatusWarningId+"' "+
                      " where CASE_ID = "+strCaseId+" ";
      Utility.logger.debug("The Query issssssssssssssssssss  " + strSql);
      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }


  public static String getCaseCCReceiver(Connection con,String caseId)
  {
    String strCCReceiver = ",";
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from HLP_CASE_CC_RECEIVER where CASE_DETAIL_ID = (select max(case_detail_id) from hlp_case_detail where case_id ="+caseId+")"); 
     String strSql = "select * from HLP_CASE_CC_RECEIVER where CASE_DETAIL_ID = (select max(case_detail_id) from hlp_case_detail where case_id ="+caseId+")";
     Utility.logger.debug("The sql isssssssssssss " + strSql);
     while(res.next())
     {
       strCCReceiver += res.getString("CC_RECEIVER_USER_ID")+",";
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return strCCReceiver; 
  }
}