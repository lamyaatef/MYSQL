
package com.mobinil.sds.core.system.cr.contract.model;
import java.io.Serializable;
import java.sql.*;

public class ContractHistoryModel implements Serializable 
{
  public static final String CONTRACT_ID = "CONTRACT_ID";
  public static final String MAIN_SIM_NO = "CONTRACT_MAIN_SIM_NO";
  public static final String CONTRACT_TYPE_ID = "CONTRACT_TYPE_ID";
  public static final String CONTRACT_DIAL_NO = "CONTRACT_DIAL_NO";
  public static final String CONTRACT_CUSTOMER_NAME ="CONTRACT_CUSTOMER_NAME";
  public static final String CONTRACT_ADDRESS = "CONTRACT_ADDRESS";
  public static final String CONTRACT_AREA = "CONTRACT_AREA";
  public static final String CONTRACT_CUSTOMER_ID = "CONTRACT_CUSTOMER_ID";
  public static final String CONTRACT_CUSTOMER_ID_TYPE = "CONTRACT_CUSTOMER_ID_TYPE";
  public static final String CONTRACT_CUSTOMER_ID_TYPE_NAME = "CUSTOMER_ID_TYPE_NAME";
  public static final String SHEET_ID = "SHEET_ID";
  public static final String CONTRACT_HOME_PHONE = "CONTRACT_HOME_PHONE";
  public static final String CONTRACT_TYPE_NAME = "PRODUCT_NAME";

  public static final String CONTRACT_SHEET_ID = "SHEET_ID";
  public static final String CONTRACT_SHEET_NAME = "SHEET_SERIAL_ID";
  public static final String CONTRACT_STATUS_TYPE_NAME = "CONTRACT_STATUS_TYPE_NAME";
  public static final String CONTRACT_SHEET_SERIAL ="SHEET_SERIAL_ID";
  public static final String CONTRACT_WARNING_ID = "CONTRACT_WARNING_ID";
  public static final String CONTRACT_WARNING_NAME ="CONTRACT_WARNING_TYPE_NAME";
  public static final String CONTRACT_STATUS_DATE="CONTRACT_STATUS_DATE";
  public static final String CONTRACT_STATUS_NAME ="CONTRACT_STATUS_TYPE_NAME";
  public static final String CONTRACT_STATUS_ID = "CONTRACT_STATUS_ID";

  public static final String USER_ID = "USER_ID";
  public static final String USER_FULL_NAME = "PERSON_FULL_NAME";
  
  
  
  
  
  
  
  public static final String STATUS_IMPORTED = "IMPORTED";
  public static final String STATUS_REJECTED_IMPORT = "REJECTED IMPORT";
  public static final String STATUS_ACCEPTED_DELIVERY = "ACCEPTED DELIVERY";
  public static final String STATUS_REJECTED_DELIVERY = "REJECTED DELIVERY";
  public static final String STATUS_REJECTED_VERIFY ="REJECTED VERIFY";
  public static final String STATUS_ACCEPTED_VERIFY ="ACCEPTED VERIFY";
  public static final String STATUS_REJECTED_AUTHINTICATION ="REJECTED AUTHINTICATION";
  public static final String STATUS_ACCEPTED_AUTHINTICATION ="ACCEPTED AUTHINTICATION";
  
  
  String mainSimNum;
  String id;
  String typeId;
  String typeName;
  String dialNum;
  String customerName;
  String address;
  String area;
  String customerIdNum;
  String customerIdType;
  String customerIdTypeName;
  String sheetId;
  String homePhone;
  String statusTypeName;
  String sheetSerialNo;
  String contractSheetSerial;
  String contractWarningId;
  String contractWarningName="";
  String contractStatusDate;
  String contractStatusName;
  String contractStatusId;
  String userId;
  String userName;
  public ContractHistoryModel()
  {
  
  }

  public ContractHistoryModel(String contractID)
  {
    id = contractID;
  }

  public ContractHistoryModel(ResultSet res)
  {
    try
    {
      this.mainSimNum = res.getString(this.MAIN_SIM_NO);
      this.typeId =  res.getString(this.CONTRACT_TYPE_ID);
      this.id = res.getString(this.CONTRACT_ID);
      this.statusTypeName = res.getString(this.CONTRACT_STATUS_TYPE_NAME);
      this.typeName = res.getString(this.CONTRACT_TYPE_NAME);
      this.dialNum = res.getString(this.CONTRACT_DIAL_NO);
      this.customerName = res.getString(this.CONTRACT_CUSTOMER_NAME);
      this.address = res.getString(this.CONTRACT_ADDRESS);
      this.area = res.getString(this.CONTRACT_AREA);
      this.customerIdNum =res.getString(this.CONTRACT_CUSTOMER_ID);
      this.customerIdType =res.getString(this.CONTRACT_CUSTOMER_ID_TYPE);
      //this.customerIdTypeName =res.getString(this.CONTRACT_CUSTOMER_ID_TYPE_NAME);
      this.sheetId =res.getString(this.CONTRACT_SHEET_ID);
      this.homePhone = res.getString(this.CONTRACT_HOME_PHONE);
      this.sheetSerialNo = res.getString(this.CONTRACT_SHEET_NAME);   
      this.contractSheetSerial = this.sheetSerialNo;
      this.contractWarningId = res.getString(CONTRACT_WARNING_ID);
      this.contractWarningName= res.getString(CONTRACT_WARNING_NAME);
      this.contractStatusDate = res.getString(CONTRACT_STATUS_DATE);
      this.contractStatusName = res.getString(CONTRACT_STATUS_NAME);
      this.contractStatusId = res.getString(CONTRACT_STATUS_ID);
      this.userId = res.getString(this.USER_ID);
      this.userName = res.getString(this.USER_FULL_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      //return null;
    }
  }

  public String getMainSimNum()
  {
    return mainSimNum;
  }

  public void setMainSimNum(String newMainSimNum)
  {
    mainSimNum = newMainSimNum;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String newId)
  {
    id = newId;
  }

  public String getTypeId()
  {
    return typeId;
  }

  public void setTypeId(String newTypeId)
  {
    typeId = newTypeId;
  }

  public String getTypeName()
  {
    return typeName;
  }

  public void setTypeName(String newTypeName)
  {
    typeName = newTypeName;
  }

  public String getDialNum()
  {
    return dialNum;
  }

  public void setDialNum(String newDialNum)
  {
    dialNum = newDialNum;
  }

  public String getCustomerName()
  {
    return customerName;
  }

  public void setCustomerName(String newCustomerName)
  {
    customerName = newCustomerName;
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String newAddress)
  {
    address = newAddress;
  }

  public String getArea()
  {
    return area;
  }

  public void setArea(String newArea)
  {
    area = newArea;
  }

  public String getCustomerIdNum()
  {
    return customerIdNum;
  }

  public void setCustomerIdNum(String newCustomerIdNum)
  {
    customerIdNum = newCustomerIdNum;
  }

  public String getCustomerIdType()
  {
    return customerIdType;
  }

  public void setCustomerIdType(String newCustomerIdType)
  {
    customerIdType = newCustomerIdType;
  }

  public String getCustomerIdTypeName()
  {
    return customerIdTypeName;
  }

  public void setCustomerIdTypeName(String newCustomerIdTypeName)
  {
    customerIdTypeName = newCustomerIdTypeName;
  }

  public String getSheetId()
  {
    return sheetId;
  }

  public void setSheetId(String newSheetId)
  {
    sheetId = newSheetId;
  }

  public String getHomePhone()
  {
    return homePhone;
  }

  public void setHomePhone(String newHomePhone)
  {
    homePhone = newHomePhone;
  }

  public String getStatusTypeName()
  {
    return statusTypeName;
  }

  public void setStatusTypeName(String newStatusTypeName)
  {
    statusTypeName = newStatusTypeName;
  }

  public String getSheetSerialNo()
  {
    return sheetSerialNo;
  }

  public boolean isExistMissingInfo()
  {
    if (this.address==null || this.area==null || this.customerIdNum ==null || this.customerIdType ==null 
        || this.customerIdTypeName == null || this.customerName == null || this.dialNum==null || this.homePhone==null
         || this.mainSimNum == null ||this.statusTypeName== null || this.typeId== null )
      return true; 
      else return false; 
          
        
  }

  public String getContractSheetSerial()
  {
    return contractSheetSerial;
  }

  public void setContractSheetSerial(String newContractSheetSerial)
  {
    contractSheetSerial = newContractSheetSerial;
  }

  public String getContractWarningId()
  {
    return contractWarningId;
  }

  public void setContractWarningId(String newContractWarningId)
  {
    contractWarningId = newContractWarningId;
  }

  public String getContractWarningName()
  {
    return contractWarningName;
  }

  public void setContractWarningName(String newContractWarningName)
  {
    contractWarningName = newContractWarningName;
  }

  public String getContractStatusDate()
  {
    return contractStatusDate;
  }

  public void setContractStatusDate(String newContractStatusDate)
  {
    contractStatusDate = newContractStatusDate;
  }

  public String getContractStatusName()
  {
    return contractStatusName;
  }

  public void setContractStatusName(String newContractStatusName)
  {
    contractStatusName = newContractStatusName;
  }

  public String getContractStatusId()
  {
    return contractStatusId;
  }

  public void setContractStatusId(String newContractStatusId)
  {
    contractStatusId = newContractStatusId;
  }

  public String getUserId()
  {
    return userId;
  }

  public void setUserId(String newUserId)
  {
    userId = newUserId;
  }

  public String getUserName()
  {
    return userName;
  }

  public void setUserName(String newUserName)
  {
    userName = newUserName;
  }
}
