package com.mobinil.sds.core.system.cr.contract.model;
import java.io.Serializable;
import java.sql.*;

public class ContractModel implements Serializable 
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
  public static final String CONTRACT_RE_IMPORT_FLAG = "RE_IMPORTED_FLAG";
  public static final String USER_ID = "USER_ID";
  public static final String USER_FULL_NAME = "PERSON_FULL_NAME";
  public static final String CONTRACT_LAST_STATUS = "CONTRACT_LAST_STATUS_ID";
  public static final String CONTRACT_CUSTOMER_BIRTH_DATE = "CONTRACT_CUSTOMER_BIRTH_DATE";
  
  public static final String SHEET_POS_NAME ="sheet_pos_name";
  public static final String DCM_ADDRESS ="DCM_ADDRESS";
  
  public static final String SHEET_SUPER_DEALER_CODE = "SHEET_SUPER_DEALER_CODE";
  public static final String CITY_ARABIC = "CITY_ARABIC";
  public static final String CITY_ENGLISH = "CITY_ENGLISH";
  public static final String CITY_GOV_CODE = "CITY_GOV_CODE";
  
  public static final String STATUS_IMPORTED = "IMPORTED";
  public static final String STATUS_REJECTED_IMPORT = "REJECTED IMPORT";
  public static final String STATUS_ACCEPTED_DELIVERY = "ACCEPTED DELIVERY";
  public static final String STATUS_REJECTED_DELIVERY = "REJECTED DELIVERY";
  public static final String STATUS_REJECTED_VERIFY ="REJECTED VERIFY";
  public static final String STATUS_ACCEPTED_VERIFY ="ACCEPTED VERIFY";
  public static final String STATUS_REJECTED_AUTHINTICATION ="REJECTED AUTHINTICATION";
  public static final String STATUS_ACCEPTED_AUTHINTICATION ="ACCEPTED AUTHINTICATION";
  public static final String AUTOMATIC_FLAG = "AUTOMATIC_FLAG";

  public String automaticFlag;
  public String mainSimNum;
  public String id;
  public String typeId;
  public String typeName;
 public  String dialNum;
  public String customerName;
  public String address;
  public String area;
  public String customerIdNum;
  public String customerIdType;
  public String customerIdTypeName;
  public String sheetId;
  public String homePhone;
  public String statusTypeName;
  public String sheetSerialNo;
  public boolean reImportedFlag;
  public String contractLastStatusId ;

  public String customerBirthDate;
  
  public String superDealerCode;
  public String cityEnglish;
  public String cityArabic;
  public String cityGovCode;
  public String destName;
//this is the only flag not set by the res since it is a specail case flag that is only 
//needed in the authentication phase after the new ammendemnt which require to 
//distinguish contracts that were previously rejected in the authentication phase 
  boolean contractPrevRejectedInAuthentication;
  String userId;
  String userName;
  String posName;
  String posAddress;
  //String contractFormNumber;

  public ContractModel()
  {
  
  }

  public ContractModel(String contractID)
  {
    id = contractID;
  }

  public ContractModel(ResultSet res)
  {
    try
    {
      this.contractLastStatusId = res.getString(this.CONTRACT_LAST_STATUS);
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
      this.customerIdTypeName =res.getString(this.CONTRACT_CUSTOMER_ID_TYPE_NAME);
      this.sheetId =res.getString(this.CONTRACT_SHEET_ID);
      this.homePhone = res.getString(this.CONTRACT_HOME_PHONE);
      this.sheetSerialNo = res.getString(this.CONTRACT_SHEET_NAME);
      this.reImportedFlag = res.getBoolean(this.CONTRACT_RE_IMPORT_FLAG);
      this.userId = res.getString(this.USER_ID);
      this.userName = res.getString(this.USER_FULL_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      //return null;
    }
  }

public static ContractModel getContractModelPlusAuthData (ResultSet res)
  {
   ContractModel contract = null;
    try
    {
      contract= new ContractModel(res);
      
      contract.superDealerCode = res.getString(SHEET_SUPER_DEALER_CODE);
      contract.cityArabic = res.getString(CITY_ARABIC);
      contract.cityEnglish = res.getString(CITY_ENGLISH);
      contract.cityGovCode = res.getString(CITY_GOV_CODE);    
      contract.destName = res.getString("district_english");
      }
    catch(Exception e)
    {
      e.printStackTrace();
      //return null;
    }
    return contract;
  }

  public String getAutomaticFlag()
  {
    return automaticFlag;
  }

  public void setAutomaticFlag(String newAutomaticFlag)
  {
    automaticFlag = newAutomaticFlag;
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

  public String getCustomerBirthDate()
  {
    return customerBirthDate;
  }

  public void setCustomerBirthDate(String newCustomerBirthDate)
  {
    customerBirthDate = newCustomerBirthDate;
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

  public boolean getReImportedFlag()
  {
    return reImportedFlag;
  }

  public void setReImportedFlag(boolean newReImportedFlag)
  {
    reImportedFlag = newReImportedFlag;
  }

  public boolean isContractPrevRejectedInAuthentication()
  {
    return contractPrevRejectedInAuthentication;
  }

  public void setContractPrevRejectedInAuthentication(boolean newContractPrevRejectedInAuthentication)
  {
    contractPrevRejectedInAuthentication = newContractPrevRejectedInAuthentication;
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
  public String getContractLastStatusId()
  {
  return this.contractLastStatusId;
  }

  public String getPosName()
  {
    return posName;
  }

  public void setPosName(String newPosName)
  {
    posName = newPosName;
  }

  public String getPosAddress()
  {
    return posAddress;
  }

  public void setPosAddress(String newPosAddress)
  {
    posAddress = newPosAddress;
  }  
/*
  public String getContractFormNumber()
  {
    return contractFormNumber;
  }

  public void setContractFormNumber(String newContractFormNumber)
  {
    contractFormNumber = newContractFormNumber;
  }
  */
  public String getSuperDealerCode()
  {
    return superDealerCode;
  }

  public void setSuperDealerCode(String newSuperDealerCode)
  {
    superDealerCode = newSuperDealerCode;
  }
/////////////////////
  public String getCityEnglish()
  {
    return cityEnglish;
  }

  public void setCityEnglish(String newCityEnglish)
  {
    cityEnglish = newCityEnglish;
  }
/////////////////////
  public String getCityArabic()
  {
    return cityArabic;
  }

  public void setCityArabic(String newCityArabic)
  {
    cityArabic = newCityArabic;
  }
/////////////////////
  public String getCityGovCode()
  {
    return cityGovCode;
  }

  public void setCityGovCode(String newCityGovCode)
  {
    cityGovCode = newCityGovCode;
  }
/////////////////////
}