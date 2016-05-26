package com.mobinil.sds.core.system.cr.batch.dto;

import java.io.Serializable;
import java.util.*;
import com.mobinil.sds.core.system.cr.batch.model.*;

/*
 * this is the batch type dto it contain a vector that is filled wiht batchtypeModels 
 * 1- addBatchTypeModel add a batchtyemodel to the batchtypemodels vector
 * 2-get a batch type model of this index
 * 3-get the size of the batchmodels vector
 * 4-clear the batchtypemodels vector
 */

public class BatchTypeDto  implements Serializable
{
  private Vector batchTypeModels = new Vector();  


  /*
   * 1- addBatchTypeModel add a batchtyemodel to the batchtypemodels vector 
   */
  public void addBatchTypeModel(BatchTypeModel m)
  {
    batchTypeModels.add(m);
  }

  /*
   * 2-get a batch type model of this index 
   */
  public BatchTypeModel getBatchTypeModel(int index)
  {
    return (BatchTypeModel)batchTypeModels.elementAt(index);
  }

  /*
   * 3-get the size of the batchmodels vector
   */
  public int getBatchModelsSize()
  {
    return this.batchTypeModels.size();
  }

  /*
   * 4-clear the batchtypemodels vector
   */
  public void clearBatchModels()
  {
    this.batchTypeModels.clear();
  }
}