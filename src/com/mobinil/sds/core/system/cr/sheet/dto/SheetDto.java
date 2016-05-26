package com.mobinil.sds.core.system.cr.sheet.dto;
import java.io.Serializable;
import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.system.cr.sheet.model.*;
import com.mobinil.sds.core.system.cr.contract.model.*;
import com.mobinil.sds.core.system.cr.warning.model.*;

/*
 * SheetDto encapsulate Sheets information in a vectro sheetModels of SheetModel 
 * and their warnings stored in the hashtable sheetWarning 
 * and their contracts as ContractDto in another hashtable sheetContracts
 * 
 * 1- add sheet warning by sheet id and a result set that contain the details of warning 
 * here it is a hashtable that contain the warnings of all the sheet 
 * each sheet has a vector in this sheetWarningVector in its value the key is the shet id
 * the warning it self is added  to the vector as a SheetWarningModel 
 * 2- get sheet warning by sheet id it return the vectro stored int eh sheetWarning hashtable 
 * the vector is consisting of SheetWarningModel 
 * if the sheet has no warning it will return null  
 * 3- add a contracts to a sheet 
 * the method add a contract model to the vector sheetContractsVector that has 
 * the information of this contract and store this vector in the hashtable sheetContractsVector 
 * 4- get sheet contracts by sheet id 
 * the method return the vector that has all the contracts models of all the contracts 
 * in this sheet 
 * the vector it self is stored in the sheet contracts hashtable 
 * if the sheet has no contracts it returns null  
 * 5- get sheetModels vector which is a vector that contain SheetModel objects
 * 6- add a sheet model to sheetModels vector 
 * 7- get sheet model from the sheetModels Vector by this index  
 * 8- get number of sheets in this sheetdto which is the size of the sheetModels
 * 9- clear sheetModels vector  
 * 
 */

public class SheetDto  implements Serializable
{
  private Vector sheetModels = new Vector();  
  private Hashtable sheetsWarning = new Hashtable();  
  private Hashtable sheetContracts = new Hashtable(); 
  
  public SheetDto()
  {
  }

/*
 * 1- add sheet warning by sheet id and a result set that contain the details of warning 
 * here it is a hashtable that contain the warnings of all the sheet 
 * each sheet has a vector in this sheetWarningVector in its value the key is the shet id
 * the warning it self is added  to the vector as a SheetWarningModel 
 */
  public void addSheetWarning(String sheetId, ResultSet res)
  {
    Vector sheetWarningsVector = null;
    if (this.sheetsWarning.containsKey(sheetId))
    {
      sheetWarningsVector = (Vector) this.sheetsWarning.get(sheetId);
    }
    else
    {
      sheetWarningsVector = new Vector();
      sheetsWarning.put(sheetId,sheetWarningsVector);
    }
      sheetWarningsVector.add(new SheetWarningModel(res));
  }

/*
 * 2- get sheet warning by sheet id it return the vectro stored int eh sheetWarning hashtable 
 * the vector is consisting of SheetWarningModel 
 * if the sheet has no warning it will return null 
 */
  public Vector getSheetWarning(String sheetId)
  {
    if (this.sheetsWarning.containsKey(sheetId))
    {
      return (Vector) this.sheetsWarning.get(sheetId);
    }
    else
    {
      return null;
    }
  }

  /*
   * 3- add a contracts to a sheet 
   * the method add a contract model to the vector sheetContractsVector that has 
   * the information of this contract and store this vector in the hashtable sheetContractsVector
   */
  public void addSheetContracts(String sheetId, String contractId)
  {
    Vector sheetContractsVector = null;
    if (this.sheetContracts.containsKey(sheetId))
    {
      sheetContractsVector = (Vector) this.sheetContracts.get(sheetId);
    }
    else
    {
      sheetContractsVector = new Vector();
      sheetContracts.put(sheetId,sheetContractsVector);
    }
      sheetContractsVector.add(new ContractModel(contractId));
  }

  public void addSheetContracts(String sheetId, Vector contarctModels)
  {
      sheetContracts.put(sheetId,contarctModels);
  }

/*
 * 4- get sheet contracts by sheet id 
 * the method return the vector that has all the contracts models of all the contracts 
 * in this sheet 
 * the vector it self is stored in the sheet contracts hashtable 
 * if the sheet has no contracts it returns null 
 */
  public Vector getSheetContracts(String sheetId)
  {
    if (this.sheetContracts.containsKey(sheetId))
    {
      return (Vector) this.sheetContracts.get(sheetId);
    }
    else
    {
      return null;
    }
  }

  /*
   * 5- get sheetModels vector which is a vector that contain SheetModel objects 
   */
  public Vector getSheetModels()
  {
    return this.sheetModels;
  }
  
/*
 * 6- add a sheet model to sheetModels vector
 */
  public void addSheetModel(SheetModel m)
  {
    sheetModels.add(m);
  }

/*
 * 7- get sheet model from the sheetModels Vector by this index 
 */
  public SheetModel getSheet(int index)
  {
    return (SheetModel)sheetModels.elementAt(index);
  }

/*
 * 8- get number of sheets in this sheetdto which is the size of the sheetModels
 */
  public int getSheetModelsSize()
  {
    return this.sheetModels.size();
  }

/*
 * 9- clear sheetModels vector 
 */
  public void clearSheetModels()
  {
    this.sheetModels.clear();
  }
}