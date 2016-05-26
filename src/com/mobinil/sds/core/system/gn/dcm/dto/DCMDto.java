package com.mobinil.sds.core.system.gn.dcm.dto;

/**
 * DCMDto Class holds all DCMs as a Vector of DCMModel.
 * 
 * @version	1.01 April 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import java.io.Serializable;
import java.util.*;
import com.mobinil.sds.core.system.gn.dcm.model.DCMModel;


public class DCMDto  implements Serializable
{
  private Vector dcmModels = new Vector();  
  public DCMDto()
  {
  }

  public void addDCM(DCMModel m)
  {
    dcmModels.add(m);
  }
  
  public DCMModel getDcm(int index)
  {
    return (DCMModel)dcmModels.elementAt(index);
  }
  public int getDcmModelsSize()
  {
    return this.dcmModels.size();
  }

  public void clearDcmModels()
  {
    this.dcmModels.clear();
  }
}