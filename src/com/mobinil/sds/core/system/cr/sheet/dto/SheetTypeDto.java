package com.mobinil.sds.core.system.cr.sheet.dto;
import java.io.Serializable;
import java.util.*;
import com.mobinil.sds.core.system.cr.sheet.model.*;
import java.util.Vector;

/*
 * sheet type status dto that hold a vector of SheetTypeModel objects 
 * and a vector of SheetTypeStatusModel objects.
 * 
 * 1- add a sheet type status model to the internal collection of this dto
 * 2- get sheet type status model in this index in the internal vector
 * 3- get sheet type status model size which is the vector size 
 * 4- clear the internal vector of sheet type status models   
 * 5- add a sheet type model to the internal collection of this dto
 * 6- get sheet type model in this index in the internal vector 
 * 7- get sheet type model size which is the vector size 
 * 8- clear the internal vector of sheet type models
 * 9- set the internal vector of sheet type status models 
 * 10- set the internal vector of sheet type  models  
 */

public class SheetTypeDto  implements Serializable
{
  //a vector of SheetTypeModel
  private Vector sheetTypeModels = new Vector();
  
  //a vector of SheetTypeStatusModel
  private Vector sheetTypeStatusModels = new Vector();
  

 /*
  * 1- add a sheet type status model to the internal collection of this dto
  */ 
  public void addSheetTypeStatusModel(SheetTypeStatusModel m)
  {
    sheetTypeStatusModels.add(m);
  }

  /*
   * 2- get sheet type status model in this index in the internal vector
   */
  public SheetTypeStatusModel getSheetTypeStatusModel(int index)
  {
    return (SheetTypeStatusModel)sheetTypeStatusModels.elementAt(index);
  }

  /*
   * 3- get sheet type status model size which is the vector size 
   */
  public int getSheetTypeStatusModelsSize()
  {
    return this.sheetTypeStatusModels.size();
  }

/*
 * 4- clear the internal vector of sheet type status models
 */
  public void clearSheetTypeStatusModels()
  {
    this.sheetTypeStatusModels.clear();
  }

 /*
  * 5- add a sheet type model to the internal collection of this dto
  */ 
  public void addSheetTypeModel(SheetTypeModel m)
  {
    sheetTypeModels.add(m);
  }

  /*
   * 6- get sheet type model in this index in the internal vector
   */
  public SheetTypeModel getSheetTypeModel(int index)
  {
    return (SheetTypeModel)sheetTypeModels.elementAt(index);
  }

  /*
   * 7- get sheet type model size which is the vector size 
   */
  public int getSheetTypeModelsSize()
  {
    return this.sheetTypeModels.size();
  }

/*
 * 8- clear the internal vector of sheet type models 
 */
  public void clearSheetTypeModels()
  {
    this.sheetTypeModels.clear();
  }

/*
 * 9- set the internal vector of sheet type status models 
 */
  public void setSheetTypeStatusModels(Vector argSheetTypeStatusModels)
  {
    sheetTypeStatusModels = argSheetTypeStatusModels;
  }


/*
 * 10- set the internal vector of sheet type  models 
 */
  public void setSheetTypeModels(Vector argSheetTypeModels)
  {
    this.sheetTypeModels = argSheetTypeModels;
  }
}