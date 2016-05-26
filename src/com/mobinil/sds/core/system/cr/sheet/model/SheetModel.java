package com.mobinil.sds.core.system.cr.sheet.model;
import java.io.Serializable;
import java.util.*;
import java.sql.*;

/*
 * Sheet Model it encapsulate the following information of the sheet in it
 * sheet Id;
 * contract Count;
 * sheet Type Id;
 * sheet Type Name;
 * sheet Status Name;
 * sheet Name;
 * sheet Status Id;
 * contractsTypesCount which is a hashtable has each type of contracts in this sheet and the count of this type
 * sheet POS Code;
 * sheet Super Dealer Code;
 * sheet Status Date;
 * sheet Batch ID;
 * sheet DCM Code;
 * sheet Batch Date;
 * 
 * also the contants of the vairous status type that a sheet can be is in this class as constants 
 *
 * 1- set contract types count it take a reuslt set of all the contract type and its count 
 * and fill the hashtable of contracts types count with the (name of product , number of contracts of this product)
 * 2- get contract types count return a hashtable that has type in its key and in its value an Integet object which is the 
 * count of this product type contractsw in this sheet 
 * 3- get the sheet name
 * 4- set the sheet name
 * 5- sheet model constructoer that take the  follwoing as input 
 * sheet id , contract count , sheet name , sheet type id , sheet type name 
 * pos code , super dealer code
 * 6- get the sheet type name 
 * 7- set sheet type name 
 * 8- get sheet id 
 * 9- set sheet id 
 * 10-get contract count  
 * 11-set contract count 
 * 12- get sheet type id 
 * 13- set sheet type id  
 * 14- get sheet status name  
 * 15- set sheet status name 
 * 16- get sheet status id  
 * 17- set sheet status id  
 * 18- get sheet pos code 
 * 19- set sheet pos code  
 * 20- get sheet super dealer code  
 * 21- set sheet super dealer code 
 * 22- get sheet status date 
 * 23-set sheet status date 
 * 24- get sheet batch id 
 * 25- set sheet batch id  
 * 26- get sheet dcm code 
 * 27- set sheet dcm code
 * 28- get sheet batch date
 * 29- set sheet batch date  
 * 
 */
public class SheetModel implements Serializable 
{
  public static final String STATUS_NEW = "NEW";
  public static final String STATUS_REJECTED_IMPORT = "REJECTED IMPORT";
  public static final String STATUS_ACCEPTED_DELIVERY = "ACCEPTED DELIVERY";
  public static final String STATUS_REJECTED_DELIVERY = "REJECTED DELIVERY";
  public static final String STATUS_ACCEPTED_VERIFY ="ACCEPTED VERIFY";
  public static final String STATUS_REJECTED_VERIFY = "REJECTED VERIFY";
  public static final String STATUS_REJECTED_AUTHINTICATION ="REJECTED AUTHINTICATION";
  public static final String STATUS_ACCEPTED_AUTHINTICATION ="ACCEPTED AUTHINTICATION";
  public static final String STATUS_ACCEPTED_COMMISSION ="ACCEPTED COMMISSION";
  public static final String STATUS_REJECTED_COMMISSION ="REJECTED COMMISSION";

  public static final String SHEET_SERIAL_ID  = "SHEET_SERIAL_ID";
  public static final String BATCH_ID="BATCH_ID";
  public static final String SHEET_STATUS_DATE = "SHEET_STATUS_DATE";
  public static final String SHEET_ID ="SHEET_ID";  
  public static final String SHEET_CONTRACT_COUNT= "SHEET_CONTRACT_COUNT";
  public static final String SHEET_TYPE_ID = "SHEET_TYPE_ID";
  public static final String SHEET_TYPE_NAME= "SHEET_TYPE_NAME";
  public static final String SHEET_DISTRIBUTER_CODE ="SHEET_DISTRIBUTER_CODE";
  public static final String SHEET_DISTRIBUTER_NAME = "DISTRIBUTER_NAME";
  public static final String SHEET_POS_CODE = "SHEET_POS_CODE";
  public static final String SHEET_POS_NAME = "POS_NAME";
  public static final String SHEET_STATUS_NAME= "SHEET_STATUS_TYPE_NAME";
  public static final String SHEET_STATUS_ID ="SHEET_STATUS_ID";
  public static final String SHEET_SUPER_DEALER_CODE= "SHEET_SUPER_DEALER_CODE";
  public static final String SHEET_SUPER_DEALER_NAME = "SUPER_DEALER_NAME";
  public static final String SHEET_STATUS_USER_NAME ="PERSON_FULL_NAME";

  public static final String SHEET_RE_IMPORT_FLAG ="SHEET_RE_IMPORT_FLAG";
  public static final String SHEET_LOCAL_AUTH_PERCENTAGE ="SHEET_LOCAL_AUTH_PERCENTAGE";




  String sheetId;
  int contractCount;
  String sheetTypeId;
  String sheetTypeName;
  String sheetStatusName;
  String sheetName;
  int sheetStatusId;
  Hashtable contractsTypesCount = new Hashtable();
  String sheetPOSCode;
  String sheetPOSName;
  String sheetSuperDealerCode;
  String sheetSuperDealerName;
  String sheetStatusDate;
  String sheetBatchID;
  String sheetDCMCode;
  String sheetDCMName;
  String sheetBatchDate;
  String statusUserId;
  String statusUserName;
  boolean sheetReImportFlag;
  String sheetLocalAuthPercentage;



  public SheetModel(ResultSet res)
  {
    try
    {
    	sheetId = res.getString(this.SHEET_ID);
        contractCount = res.getInt(this.SHEET_CONTRACT_COUNT);
        sheetTypeId = res.getString(this.SHEET_TYPE_ID);       
        sheetTypeName = res.getString(this.SHEET_TYPE_NAME);     
        sheetStatusName = res.getString(this.SHEET_STATUS_NAME);     
        sheetName = res.getString(this.SHEET_SERIAL_ID);
        sheetStatusId = res.getInt(this.SHEET_STATUS_ID);
        sheetPOSCode = res.getString(this.SHEET_POS_CODE); 
        sheetSuperDealerCode = res.getString(this.SHEET_SUPER_DEALER_CODE);
        sheetStatusDate = res.getString(this.SHEET_STATUS_DATE); 
        sheetBatchID = res.getString(this.BATCH_ID);     
        sheetDCMCode = res.getString(this.SHEET_DISTRIBUTER_CODE);       
        statusUserName =  res.getString(this.SHEET_STATUS_USER_NAME);
    }
    catch(Exception e)
    {
    e.printStackTrace();
      
    }
  }

/*
 * 1- set contract types count it take a reuslt set of all the contract type and its count 
 * and fill the hashtable of contracts types count with the (name of product , number of contracts of this product)
 */
  public void setContractsTypesCount(ResultSet res)
  {
    try
    {
      while (res.next())
      {
        String typeName = res.getString("product_name");
        String count = res.getString("contracts_count");
       
        if (typeName==null )
        {
        }
        else if (typeName.length()!=0)
        {
          contractsTypesCount.put(typeName,count);
      
        }      
      }      
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }


  /*
   * this function do the same thing as above function but in a different way to optimize it
   * it assume a cross tab like query that has all the products count in the row
   */

  public void setContractsTypesCount(ResultSet res , int productsCount)
  {
    try
    {
     for (int i = 0; i < productsCount; i++)
      {
        String type = res.getString(i*2 + 3);
        int count = res.getInt(i*2 +  4) ;

        if (count ==0)
        {
                
        }
        else
        {
        contractsTypesCount.put(type,count+"");
        }
      }
  
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }



  
/*
 * 2- get contract types count return a hashtable that has type in its key and in its value an Integet object which is the 
 * count of this product type contractsw in this sheet 
 */
  public Hashtable getContractsTypesCount()
  {
    return this.contractsTypesCount;
  }

  /*
   * 3- get the sheet name
   */
  public String getSheetName()
  {
    return sheetName;
  }
  /*
   * 4- set the sheet name
   */
  public void setSheetName(String name)
  {
    this.sheetName= name;
  }

  /*
   * 5- sheet model constructoer that take the  follwoing as input 
   * sheet id , contract count , sheet name , sheet type id , sheet type name 
   * pos code , super dealer code 
   */  
  public SheetModel(String sheetId, int contractCount , String sheetName, String sheetTypeId, String sheetTypeName,String posCode, String superDealerCode)
  {
  this.sheetId = sheetId;
  this.contractCount = contractCount ; 
  this.sheetName= sheetName;
  this.sheetTypeId= sheetTypeId;
  this.sheetTypeName = sheetTypeName;
  this.sheetPOSCode = posCode;
  this.sheetSuperDealerCode = superDealerCode;
  }

/*
 * 6- get the sheet type name 
 */
  public String getSheetTypeName()
  {
    return this.sheetTypeName;    
  }

/*
 * 7- set sheet type name 
 */
  public void setSheetTypeName(String sheetTypeName)
  {
    this.sheetTypeName = sheetTypeName;
  }

/*
 * 8- get sheet id 
 */
  public String getSheetId()
  {
    return sheetId;
  }

/*
 * 9- set sheet id 
 */
  public void setSheetId(String newSheetId)
  {
    sheetId = newSheetId;
  }

/*
 * 10-get contract count 
 */
  public int getContractsCount()
  {
    return contractCount;
  }

/*
 * 11-set contract count
 */
  public void setContractsCount(int newContractsCount)
  {
    contractCount = newContractsCount;
  }

/*
 * 12- get sheet type id 
 */
  public String getSheetTypeID()
  {
    return sheetTypeId;
  }

/*
 * 13- set sheet type id 
 */
  public void setSheetTypeID(String newSheetType)
  {
    sheetTypeId = newSheetType;
  }

/*
 * 14- get sheet status name 
 */
  public String getSheetStatusName()
  {
    return sheetStatusName;
  }

/*
 * 15- set sheet status name 
 */
  public void setSheetStatusName(String newSheetStatus)
  {
    sheetStatusName = newSheetStatus;
  }

/*
 * 16- get sheet status id 
 */
  public int getSheetStatusId()
  {
    return sheetStatusId;
  }

/*
 * 17- set sheet status id 
 */ 
  public void setSheetStatusId(int newSheetStatusId)
  {
    sheetStatusId = newSheetStatusId;
  }

/*
 * 18- get sheet pos code 
 */
  public String getSheetPOSCode()
  {
    return sheetPOSCode;
  }

/*
 * 19- set sheet pos code 
 */
  public void setSheetPOSCode(String newSheetPOSCode)
  {
    sheetPOSCode = newSheetPOSCode;
  }

/*
 * 20- get sheet pos name
 */
  public String getSheetPOSName()
  {
    return sheetPOSName;
  }

/*
 * 21- set sheet pos name
 */
  public void setSheetPOSName(String newSheetPOSName)
  {
    sheetPOSName = newSheetPOSName;
  }

/*
 * 22- get sheet super dealer code 
 */
  public String getSheetSuperDealerCode()
  {
    return sheetSuperDealerCode;
  }

/*
 * 23- set sheet super dealer code 
 */
  public void setSheetSuperDealerCode(String newSheetSuperDealerCode)
  {
    sheetSuperDealerCode = newSheetSuperDealerCode;
  }

  public String getSheetSuperDealerName()
  {
    return sheetSuperDealerName;
  }

  public void setSheetSuperDealerName(String newSheetSuperDealerName)
  {
    sheetSuperDealerName = newSheetSuperDealerName;
  }

/*
 * 24- get sheet status
 */
  public String getSheetStatusDate()
  {
    return sheetStatusDate;
  }

/*
 * 25-set sheet status date 
 */
  public void setSheetStatusDate(String newSheetStatusDate)
  {
    sheetStatusDate = newSheetStatusDate;
  }

/*
 * 26- get sheet batch id 
 */
  public String getSheetBatchID()
  {
    return sheetBatchID;
  }

/*
 * 25- set sheet batch id 
 */
  public void setSheetBatchID(String newBatchID)
  {
    sheetBatchID = newBatchID;
  }

/*
 * 26- get sheet dcm code 
 */
  public String getSheetDCMCode()
  {
    return sheetDCMCode;
  }

/*
 * 27- set sheet dcm code 
 */

  public void setSheetDCMCode(String newSheetDCMCode)
  {
    sheetDCMCode = newSheetDCMCode;
  }

  public String getSheetDCMName()
  {
    return sheetDCMName;
  }

  public void setSheetDCMName(String newSheetDCMName)
  {
    sheetDCMName = newSheetDCMName;
  }

/*
 * 28- get sheet batch date
 */
  public String getSheetBatchDate()
  {
    return sheetBatchDate;
  }

/*
 * 29- set sheet batch date 
 */
  public void setSheetBatchDate(String newSheetBatchDate)
  {
    sheetBatchDate = newSheetBatchDate;
  }

  public String getStatusUserId()
  {
    return statusUserId;
  }

  public void setStatusUserId(String newStatusUserId)
  {
    statusUserId = newStatusUserId;
  }

  public String getStatusUserName()
  {
    return statusUserName;
  }

  public void setStatusUserName(String newStatusUserName)
  {
    statusUserName = newStatusUserName;
  }

  public boolean isSheetReImportFlag()
  {
    return sheetReImportFlag;
  }

  public void setSheetReImportFlag(boolean newSheetReImportFlag)
  {
    sheetReImportFlag = newSheetReImportFlag;
  }

  public void setSheetReImportFlag(int importFlag)
  {
    sheetReImportFlag = importFlag==1 ? true : false ;
  }


  public String getSheetLocalAuthPercentage()
  {
    return sheetLocalAuthPercentage;
  }

  public void setSheetLocalAuthPercentage(String newSheetLocalAuthPercentage)
  {
    sheetLocalAuthPercentage = newSheetLocalAuthPercentage;
  }
}