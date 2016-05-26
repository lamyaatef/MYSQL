package com.mobinil.sds.core.system.cr.batch.model;
import java.io.Serializable;
import com.mobinil.sds.core.system.cr.sheet.dto.*;
/*
 * this is the batch model
 * 1-get batch sheet
 * 2-set batch Sheet with a sheetDto object
 * 3-constructor that take batchId , batch date, batch distributer id , batch type , batch dcm name
 * 4- get batch id
 * 5-set the batch id for this sheet dto
 * 6- get the batch date 
 * 7- set the batch date
 * 8-get the dcm id
 * 9- set the batch dcm id
 * 10-get the batch status 
 * 12- get the batch type 
 * 13- set batch type
 * 14- get batch dcm name
 * 15-set the batch dcm 
 */



public class BatchModel  implements Serializable
{
  String batchId;
  String batchDate;
  String batchDCMId;  
  String batchStatus;
  String batchType;
  SheetDto sheetModel;
  String batchDCMName;
  String archivingFlag;

  
  public static final String STATUS_NEW = "NEW";  
  public static final String STATUS_CLOSED_IMPORT = "REJECTED IMPORT";
  public static final String STATUS_OPEN_DELIVERY = "OPEN DELIVERY";
  public static final String STATUS_CLOSED_DELIVERY ="REJECTED DELIVERY";
  public static final String STATUS_OPEN_VERIFY = "OPEN VERIFY";
  public static final String STATUS_CLOSED_VERIFY = "REJECTED VERIFY";
  public static final String STATUS_OPEN_AUTHINTICATION = "OPEN AUTHINTICATION";                                                         
  public static final String STATUS_CLOSED_AUTHINTICATION = "REJECTED AUTHINTICATION";
  public static final String STATUS_OPEN_COMMISSION="OPEN COMMISSION";
  public static final String STATUS_CLOSED_COMMISSION = "REJECTED COMMISSION";
  

/*
 * 1-get batch sheets Dto
 */
  public SheetDto getBatchSheet()
  {
    return this.sheetModel;
  }

/*
 * 2-set batch Sheet with a sheetDto object 
 */
  public void setBatchSheet(SheetDto sheetModel)
  {
    this.sheetModel = sheetModel;
  }



/*
 * 3-constructor that take batchId , batch date, batch distributer id , batch type , batch dcm name
 */
  public BatchModel(String batchId,String batchDate, String batchDCMId,String batchType, String batchDCMName , String archivingFlag)
  {
  this.batchId = batchId;
  this.batchDate = batchDate;
  this.batchDCMId = batchDCMId;
  this.batchType = batchType;
  this.batchDCMName = batchDCMName;
  this.archivingFlag = archivingFlag;
  }


/*
 * 4- get batch id 
 */
  public String getBatchId()
  {
    return batchId;
  }


/*
 * 5-set the batch id for this sheet dto 
 */
  public void setBatchId(String newBatchId)
  {
    batchId = newBatchId;
  }

/*
 * 6- get the batch date 
 */
  public String getBatchDate()
  {
    return batchDate;
  }

/*
 * 7- set the batch date
 */
  public void setBatchDate(String newBatchDate)
  {
    batchDate = newBatchDate;
  }

/*
 * 8-get the dcm id 
 */
  public String getBatchDCMId()
  {
    return batchDCMId;
  }

/*
 * 9- set the batch dcm id 
 */
  public void setBatchDCMId(String newBatchDCMId)
  {
    batchDCMId = newBatchDCMId;
  }

/*
 * 10-get the batch status 
 */
  public String getBatchStatus()
  {
    return batchStatus;
  }
/*
 * 11-set the batch status
 */
  public void setBatchStatus(String newBatchStatus)
  {
    batchStatus = newBatchStatus;
  }

/*
 * 12- get the batch type
 */
  public String getBatchType()
  {
    return batchType;
  }

/*
 * 13- set batch type
 */
  public void setBatchType(String newBatchType)
  {
    batchType = newBatchType;
  }


/*
 * 14- get batch dcm name 
 */
  public String getBatchDCMName()
  {
    return batchDCMName;
  }

/*
 * 15-set the batch dcm 
 */
  public void setBatchDCMName(String newBatchDCMName)
  {
    batchDCMName = newBatchDCMName;
  }



  public String getArchivingFlag()
  {
    return archivingFlag;
  }
  public void setArchivingFlag(String newArchivingFlag)
  {
    archivingFlag = newArchivingFlag;
  }
}