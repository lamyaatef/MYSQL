package com.mobinil.sds.core.system.cr.excelImport;

/*
 * This is the class that is responsible of carrying the information
 * of one error info item 
 * the infomration is composed of error name and row number that this error
 * happend on it 
 * and the error details  of this error this is just a data holder class 
 * that has getters for all the fields it has since it is used to transfer 
 * the error information in an organized way to manage to displaoy the errors 
 * in the gui in a table format 
 * no setters in this class since all its details are being set in the constructoer
 * and it doesn't change during its life cycle 
 * 
 * 1-Constructor that take errorName, row number , error details as input
 * 2- get the error Name of this error information
 * 3-get the row number of this error information
 * 4- get the error details of this error information
 */


public class ErrorInfo 
{
  private String errorName;  
  private int rowNum;
  private String errorDetail;


  /*
   * 1-Constructor that take errorName, row number , error details as input
   */
  public ErrorInfo(String errorName, int rowNum, String errorDetail)
  {
   this.errorName=  errorName;
   this.rowNum = rowNum;
   this.errorDetail = errorDetail;
  }

  /*
   * 2- get the error Name of this error information 
   */
  public String getErrorName()
  {
    return this.errorName;
  }
  /*
   * 3-get the row number of this error information
   */
  public int getRowNum()
  {
    return this.rowNum;
  }
  /*
   * 4- get the error details of this error information
   */
  public String getErrorDetail()
  {
    return this.errorDetail;
  }
  
}