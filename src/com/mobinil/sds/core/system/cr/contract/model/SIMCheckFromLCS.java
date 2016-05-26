package com.mobinil.sds.core.system.cr.contract.model;
import java.io.Serializable;
import java.sql.*;

public class SIMCheckFromLCS implements Serializable 
{
  public static final String MAIN_SIM_NO = "MAIN_SIM_NO";
  public static final String LCS_DCM_ID = "LCS_DCM_ID";             
  public static final String LCS_CONTRACT_TYPE_ID = "LCS_CONTRACT_TYPE_ID";   
  public static final String TRANSACTION_TYPE_ID = "TRANSACTION_TYPE_ID";    
  public static final String LCS_INIT_DATE = "LCS_INIT_DATE";          
  public static final String DIAL_NO = "DIAL_NO";                
  public static final String TYPE = "TYPE";                   
  public static final String DCM_NAME_FROM_LCS = "DCM_NAME_FROM_LCS";      
  public static final String CONTRACT_TYPE_FROM_LCS = "CONTRACT_TYPE_FROM_LCS";
  public static final String PRODUCT_NAME = "PRODUCT_NAME";
  public static final String TRANSACTION_TYPE_NAME = "TRANSACTION_TYPE_NAME";  
  public static final String LCS_CURRENT_STATUS = "LCS_CURRENT_STATUS";
  public static final String LCS_ISSUE_DATE = "LCS_ISSUE_DATE";

  String mainSimNo;
  String lcsDcmId;
  String lcsContractTypeId;
  String transactionTypeId;
  String lcsInitDate;
  String dialNo;
  String type;
  String dcmNameFromLcs;
  String contractTypeFromLcs;
  String productName;
  String transactionTypeName;
  String lcsCurrentStatus;
  String lcsIssueDate;
  
  public SIMCheckFromLCS()
  {
  }

  public SIMCheckFromLCS(ResultSet res)
  {
    try
    {
      mainSimNo = res.getString(MAIN_SIM_NO);
      lcsDcmId = res.getString(LCS_DCM_ID);
      lcsContractTypeId = res.getString(LCS_CONTRACT_TYPE_ID);
      transactionTypeId = res.getString(TRANSACTION_TYPE_ID);
      lcsInitDate = res.getString(LCS_INIT_DATE);
      dialNo = res.getString(DIAL_NO);
      type = res.getString(TYPE);
      dcmNameFromLcs = res.getString(DCM_NAME_FROM_LCS);
      contractTypeFromLcs = res.getString(CONTRACT_TYPE_FROM_LCS); 
      productName = res.getString(PRODUCT_NAME);
      transactionTypeName = res.getString(TRANSACTION_TYPE_NAME);
      lcsCurrentStatus = res.getString(LCS_CURRENT_STATUS);
      lcsIssueDate = res.getString(LCS_ISSUE_DATE);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public String getContractTypeFromLcs()
  {
    return contractTypeFromLcs;
  }

  public void setContractTypeFromLcs(String newContractTypeFromLcs)
  {
    contractTypeFromLcs = newContractTypeFromLcs;
  }

  public String getDcmNameFromLcs()
  {
    return dcmNameFromLcs;
  }

  public void setDcmNameFromLcs(String newDcmNameFromLcs)
  {
    dcmNameFromLcs = newDcmNameFromLcs;
  }

  public String getDialNo()
  {
    return dialNo;
  }

  public void setDialNo(String newDialNo)
  {
    dialNo = newDialNo;
  }

  public String getLcsContractTypeId()
  {
    return lcsContractTypeId;
  }

  public void setLcsContractTypeId(String newLcsContractTypeId)
  {
    lcsContractTypeId = newLcsContractTypeId;
  }

  public String getLcsDcmId()
  {
    return lcsDcmId;
  }

  public void setLcsDcmId(String newLcsDcmId)
  {
    lcsDcmId = newLcsDcmId;
  }

  public String getLcsInitDate()
  {
    return lcsInitDate;
  }

  public void setLcsInitDate(String newLcsInitDate)
  {
    lcsInitDate = newLcsInitDate;
  }

  public String getMainSimNo()
  {
    return mainSimNo;
  }

  public void setMainSimNo(String newMainSimNo)
  {
    mainSimNo = newMainSimNo;
  }

  public String getTransactionTypeId()
  {
    return transactionTypeId;
  }

  public void setTransactionTypeId(String newTransactionTypeId)
  {
    transactionTypeId = newTransactionTypeId;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String newType)
  {
    type = newType;
  }

  public String getProductName()
  {
    return productName;
  }

  public void setProductName(String newProductName)
  {
    productName = newProductName;
  }

  public String getTransactionTypeName()
  {
    return transactionTypeName;
  }

  public void setTransactionTypeName(String newTransactionTypeName)
  {
    transactionTypeName = newTransactionTypeName;
  }

  public String getLcsCurrentStatus()
  {
    return lcsCurrentStatus;
  }

  public void setLcsCurrentStatus(String newLcsCurrentStatus)
  {
    lcsCurrentStatus = newLcsCurrentStatus;
  }

  public String getLcsIssueDate()
  {
    return lcsIssueDate;
  }

  public void setLcsIssueDate(String newLcsIssueDate)
  {
    lcsIssueDate = newLcsIssueDate;
  }

  
}