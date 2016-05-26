package com.mobinil.sds.core.system.cr.batch.dto;
import java.io.Serializable;
import java.util.*;
import com.mobinil.sds.core.system.cr.batch.model.*;
import com.mobinil.sds.core.system.cr.sheet.model.*;
import com.mobinil.sds.core.system.cr.sheet.dto.*;
import com.mobinil.sds.core.system.cr.batch.model.BatchModel;

import java.util.Vector;

/*
 * this is a batch dto responsoble of holding the information of a batch in a batch model object 
 * and a sheetdto objec that contain all the information of the sheets that belong to this batch 
 * 1-get the sheetdto size ie how many sheetmodel in that dto 
 * it return 01 if there was no sheetdto 
 * 2-get a sheet at the index sent to this method from the sheetdto inside this dto
 * 3-get the batch model
 * 4-set the batch model
 * 5-set the sheet dto
 * 6-get the sheet dto
 */
public class BatchDto  implements Serializable
{
  
  BatchModel batchModel;
  SheetDto sheetDto;
  public int sheetClosed;     // 0 open .. 1 closed

  public BatchDto()
  {
  }

  /*
   * 1-get the sheetdto size ie how many sheetmodel in that dto
   * it return 01 if there was no sheetdto 
   */
  public int getSheetModelsSize()
  {
    if (sheetDto!=null)
    return sheetDto.getSheetModelsSize();
    else
    return -1;
  }

  /*
   * 2-get a sheet at the index sent to this method from the sheetdto inside this dto 
   */
  public SheetModel getSheetAt(int index)
  {
    return (SheetModel)this.sheetDto.getSheet(index);
  }

  /*
   * 3-get the batch model 
   */
  public BatchModel getBatchModel()
  {
    return batchModel;
  }

  /*
   * 4-set the batch model 
   */
  public void setBatchModel(BatchModel batchModel)
  {
   this.batchModel = batchModel;
  }

 /*
  * 5-set the sheet dto 
  */
  public void setSheetDto(SheetDto sheetDto)
  {
    this.sheetDto = sheetDto;
  }

/*
 * 6-get the sheet dto
 */
  public SheetDto getSheetDto()
  {
    return this.sheetDto;
  }
}
