package com.mobinil.sds.core.system.cr.excelImport;
import java.util.*;
import java.sql.*;

/*
 * This class is responsible of keeping all the sheet information and its contracts
 * the sheet has the following functionality
 * 1- get sheet status
 * 2-get sheet row number 
 * 3-Construct a sheet with its name , status, row number and contracts count
 * 4- insert all sheet contracts to the database 
 * 5-set sheet status
 * 6- is all contracts rejected return true if all the contracts in this sheet are rejected 
 * 7- get the number of contracts that was written on the excel sheet 
 * 8- get sheet name which is the sheet serial number
 * 9- get the name of the sheet status
 * 10- get all the contracts in this sheet in an array sorted by the row number
 * 11- check if there is atleast one contract is rejected in this sheet
 * and return true 
 * else return false
 * 12- set all the contracts in this sheet to the status sent
 * 13- set all none rejected contract to be rejected 
 * and add a warning to those contract that they are rejcted cause the sheet
 * got rejected
 * 
 */

public class Sheet 
{
 //those status are being set initialiy in the excel import from the database 
  public static final int IMPORTED = 1; 
  public static final int REJECTED = -1;
  public static final int PARTIAL_IMPORTED = 2;
  
  private int status ;
  private int rowNum;
  private String name;
  private int contractsCount ;

  public Hashtable contracts = new Hashtable();
  public Hashtable warnings = new Hashtable();

  //1- get sheet status 
  public int getStatus()
  {
    return status;
  }  
  //2-get sheet row number 
  public int getRowNum()
  {
    return this.rowNum;
  }  
  //3-Construct a sheet with its name , status, row number and contracts count
  public Sheet(String name,int status,int rowNum , int contractsCount)
  {
    this.status= status;
    this.rowNum = rowNum;
    this.name = name; 
    this.contractsCount = contractsCount; 
  }

  //4- insert all sheet contracts to the database 
  public void insertToDBAllContracts( Statement stat ,Statement queryStat ) throws Exception
  {
    stat.clearBatch(); 
    Enumeration contracts = this.contracts.elements();
    
    while(contracts.hasMoreElements())
    {
       long startT = System.currentTimeMillis();
      ((Contract)contracts.nextElement()).insertRecord(stat , queryStat);
      //Utility.logger.debug(" insert record took = " + (System.currentTimeMillis() - startT ));
    }    
    long startT = System.currentTimeMillis();
    stat.executeBatch();
    //Utility.logger.debug(" insert executeBatch took = " + (System.currentTimeMillis() - startT ));
  }

  //5-set sheet status
  public void setStatus(int status)
  {
    this.status = status;
  }

/*
 * 13- set all none rejected contract to be rejected 
 * and add a warning to those contract that they are rejcted cause the sheet
 * got rejected
 */
  public void setAllNonRejectedContractsToRejected()
  {
    Enumeration contracts = this.contracts.elements();
    while(contracts.hasMoreElements())
    {
      Contract contract = (Contract)contracts.nextElement();
      if (contract.getStatus() == Contract.REJECTED)
      {        
      }
      else
      { 
        
        contract.setContractStatus(Contract.REJECTED);
        contract.setWarningId(ExcelImport.CONTRACT_WARNING_REJECTED_SHEET_ID);
        contract.setWarningText(ExcelImport.CONTRACT_WARNING_REJECTED_SHEET_TEXT);
      }      
    }
  }
/*
 * 12- set all the contracts in this sheet to the status sent 
 */
  public void setAllContractsStatus(int status_to_set)
  {
    Enumeration contracts = this.contracts.elements();
    while(contracts.hasMoreElements())
    {
      ((Contract)contracts.nextElement()).setContractStatus(status_to_set);      
    }
  }

/*
 * 11- check if there is atleast one contract is rejected in this sheet
 * and return true 
 * else return false
 */
  public boolean isExistContractRejected()
  {
    if (this.contracts.size()==0)
    return false;     
    Enumeration contracts = this.contracts.elements();    
    boolean flag = false;         
    while(contracts.hasMoreElements())
    {
      Contract contract =(Contract)contracts.nextElement();           
      if (contract.getStatus()==Contract.REJECTED)
      {
        return true;
      }      
    }     
    return flag;
  }

  //6- is all contracts rejected return true if all the contracts in this sheet are rejected 
  public boolean isAllContractsRejected()
  {
    if (this.contracts.size()==0)
    return false;     
    Enumeration contracts = this.contracts.elements();    
    boolean flag = true;         
    while(contracts.hasMoreElements())
    {
      Contract contract =(Contract)contracts.nextElement();         
      if (contract.getStatus()==Contract.IMPORTED)
      {
        flag =  false;        
        break;
      }      
    }     
    return flag;
  }
  //7- get the number of contracts that was written on the excel sheet 
  public int getContractsCount()
  {
    return this.contractsCount;
  }
  //8- get sheet name which is the sheet serial number 
  public String getName()
  {
    return this.name;
  }
  //9- get the name of the sheet status 
  public String getStatusName()
  {
    switch (this.status)
    {
      case REJECTED :
      return "Rejected";
      case IMPORTED:
      return "Imported";
      case PARTIAL_IMPORTED:
      return "Partial Import";
    }
    return "";
  }
/*
 * 10- get all the contracts in this sheet in an array sorted by the row number 
 */
  public Contract[] getAllContractsSorted()
  {        
     Contract[] contractArray = new Contract[contracts.size()];
     Enumeration contractsEnum = contracts.elements();
     int index = 0 ;
     while (contractsEnum.hasMoreElements())
     {
       contractArray[index] = (Contract)contractsEnum.nextElement();
       index++;
     }
     for (int i = 0 ; i < contractArray.length-1 ; i++)
     {
       for (int j = i+1; j<contractArray.length   ; j++)
       {
         if (contractArray[i].getRowNum()>contractArray[j].getRowNum())
         {
           Contract temp = contractArray[i];
           contractArray[i] = contractArray[j];
           contractArray[j] = temp;
         }
       }
     }     
     return contractArray;
   }



   public boolean contractExist(String mainSimNo)
   {
     if (this.contracts.size()==0)
      return false;     

    Enumeration contracts = this.contracts.elements();              
    while(contracts.hasMoreElements())
    {
      Contract contract =(Contract)contracts.nextElement();           
      if (contract.getName().compareTo(mainSimNo)==0)
      {
        return true;
      }      
    }     
    return false;     
   }
}