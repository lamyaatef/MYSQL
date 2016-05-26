package com.mobinil.sds.core.system.cr.excelImport;
import java.util.*;
import com.mobinil.sds.core.system.cr.excelImport.Contract;
/*
* This class is responsible of handling the import details 
* to be transferd to the web tier to show a whole
* information report of all the sheets that were managed to be read from the excel file
* the data structure of this class is based on a hashtable of Sheet.java objects
* 1- this is to add a sheet to the import report by sending 
* the sheetId, sheetSerial , status, rowNum,contractsCount
* then if the sheetid was not found in the already sheets in the class
* the method create a new Sheet object using the supplied infomration 
* and add it to the hashtable of contracts using the sheetId as a key
* 1- this is to add a sheet to the import report by sending 
* the sheetId, sheetSerial , status, rowNum,contractsCount
* then if the sheetid was not found in the already sheets in the class
* the method create a new Sheet object using the supplied infomration 
* and add it to the hashtable of contracts using the sheetId as a key
* 2- this is to a get  a Sheet object from the hashtable of sheets
* 3- adds a contract to the sheet 
* it find the Sheet object in the hashtable using the sheetId 
* if not found it return a null
* other wise it uses the information supplied 
* contractId, simSerial, row number 
* to create a Contract object and add it to the sheet
* and return it
* 4-This is to change a sheet status 
* using the sheet id it retrieve a sheet object from the hashtable
* and change the sheet status if it wasn't already rejected
* 5-this methods return an array of Sheet object that are all of the same status   
* 
*/

public class ImportReport 
{
  public Hashtable sheetReports = new Hashtable(); 

  /*
   * 1- this is to add a sheet to the import report by sending 
   * the sheetId, sheetSerial , status, rowNum,contractsCount
   * then if the sheetid was not found in the already sheets in the class
   * the method create a new Sheet object using the supplied infomration 
   * and add it to the hashtable of contracts using the sheetId as a key 
   * 
   */
  public void addSheet(String sheetId,String sheetSerial,  int status,int rowNum , int contractsCount)
  {
   if (this.sheetReports.get(sheetId)==null)
   sheetReports.put(sheetId,new Sheet(sheetSerial,status,rowNum,contractsCount));   
  }


/*
 * 2- this is to a get  a Sheet object from the hashtable of sheets 
 */
  public Sheet getSheet(String sheetId)
  {
    return (Sheet) this.sheetReports.get(sheetId);
  }


/*
 * 3- adds a contract to the sheet 
 * it find the Sheet object in the hashtable using the sheetId 
 * if not found it return a null
 * other wise it uses the information supplied 
 * contractId, simSerial, row number 
 * to create a Contract object and add it to the sheet
 * and return it 
 */
  public Contract addContract(String sheetId, String contractId, String simSerial,int rowNum, String userId)
  {        
    Sheet sheetRep = (Sheet)this.sheetReports.get(sheetId);
    if (sheetRep == null)
    return null;         
    Contract contractRep = (Contract)(sheetRep).contracts.get(contractId);
      if(contractRep ==null)
      {
        Contract contract = new Contract(simSerial,contractId,rowNum,userId);
        sheetRep.contracts.put(contractId,contract);
        return contract; 
      }
      else return null;       
   }


/*
 * 4-This is to change a sheet status 
 * using the sheet id it retrieve a sheet object from the hashtable
 * and change the sheet status if it wasn't already rejected
 */
public boolean  changeSheetStatus(String sheetId,  int status)
  {        
    Sheet sheetRep = (Sheet)this.sheetReports.get(sheetId);    
    if (sheetRep == null)
    return false;
    else
    {
      if (sheetRep.getStatus()<0) //sheet is rejected can't be changed its status
      return false; 
      
      sheetRep.setStatus(status);
      return true; 
    }     
   }
   
/*
 * 5-this methods return an array of Sheet object that are all of the same status
 */
   public Sheet[] getAllSheetOfType(int status)
   {
     Hashtable table = new Hashtable();
     Enumeration keys = this.sheetReports.keys();
     Enumeration values = this.sheetReports.elements();
     int max = -1;
     int min = 10000;
     int count = 0; 
     int orderSheetKeys[] = new int[10000];           
     while(keys.hasMoreElements())
     {
        String key = (String) keys.nextElement();
        Sheet sheet = (Sheet) values.nextElement();
        if (sheet.getStatus() == status)
        {
            table.put(key,sheet);                                    
        }        
     }
     Sheet sheetArray[] = new Sheet[table.size()];
     Enumeration sheets = table.elements();
     int index = 0 ;
     while (sheets.hasMoreElements())
     {
       sheetArray[index] = (Sheet)sheets.nextElement();
       index++;
     }
     for (int i = 0 ; i < sheetArray.length-1 ; i++)
     {
       for (int j = i+1; j<sheetArray.length   ; j++)
       {
         if (sheetArray[i].getRowNum()>sheetArray[j].getRowNum())
         {
           Sheet temp = sheetArray[i];
           sheetArray[i] = sheetArray[j];
           sheetArray[j] = temp;
         }
       }
     }     
     return sheetArray;
   }
/*
  public Hashtable getSheetContracts (String sheetId)
  {
    Sheet sheetRep = (Sheet)this.sheetReports.get(sheetId);
    if (sheetRep == null)
    {
      return -1;
    }
    return sheetRep.contracts;        
  }
*/

}