package com.mobinil.sds.core.system.cr.sheet.dto;
import java.io.Serializable;
import java.util.*;
import com.mobinil.sds.core.system.cr.sheet.model.*;

/*
 * sheet status type dto that hold a vector of SheetStatusTypeModel 
 * 1- add a sheet status type model to the internal collection of this dto
 * 2- get sheet status type model in this index in the internal vector
 * 3- get sheet model size which is the vector size 
 * 4- clear the internal vector   
 */

public class SheetStatusTypeDto  implements Serializable
{
  //a vector of SheetStatusTypeModel
  private Vector sheetStatusTypeModels = new Vector();
  

 /*
  * 1- add a sheet status type model to the internal collection of this dto
  */ 
  public void addSheetStatusTypeModel(SheetStatusTypeModel m)
  {
    sheetStatusTypeModels.add(m);
  }

  /*
   * 2- get sheet status type model in this index in the internal vector
   */
  public SheetStatusTypeModel getSheetStatusTypeModel(int index)
  {
    return (SheetStatusTypeModel)sheetStatusTypeModels.elementAt(index);
  }

  /*
   * 3- get sheet model size which is the vector size 
   */
  public int getSheetModelsSize()
  {
    return this.sheetStatusTypeModels.size();
  }

/*
 * 4- clear the internal vector 
 */
  public void clearSheetModels()
  {
    this.sheetStatusTypeModels.clear();
  }
}