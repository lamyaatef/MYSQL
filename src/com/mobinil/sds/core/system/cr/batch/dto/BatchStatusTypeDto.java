package com.mobinil.sds.core.system.cr.batch.dto;

import java.io.Serializable;
import java.util.*;
import com.mobinil.sds.core.system.cr.batch.model.*;


public class BatchStatusTypeDto  implements Serializable
{
  private Vector batchStatusTypeModels = new Vector();  

 
  public void addBatchStatusTypeModel(BatchStatusTypeModel m)
  {
    batchStatusTypeModels.add(m);
  }
  
  public BatchStatusTypeModel getBatchStatusTypeModel(int index)
  {
    return (BatchStatusTypeModel)batchStatusTypeModels.elementAt(index);
  }
  public int getBatchModelsSize()
  {
    return this.batchStatusTypeModels.size();
  }

  public void clearBatchModels()
  {
    this.batchStatusTypeModels.clear();
  }
}