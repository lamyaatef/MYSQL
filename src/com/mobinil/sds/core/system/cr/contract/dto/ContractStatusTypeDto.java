package com.mobinil.sds.core.system.cr.contract.dto;
import java.io.Serializable;
import java.util.*;
import com.mobinil.sds.core.system.cr.contract.model.*;

/*
 * Contract Status Type Dto this dto carries a vector of contract status type models
 * 1- add a contract status type model to the vector
 * 2- get a contract status type model from the vector using this index
 * 3- get contract status type model size
 * 4- clear contract models reamove all the objects in the vector
 */
public class ContractStatusTypeDto  implements Serializable
{
  //vector that has objects of type ContractStatusTypeModel
  private Vector contractStatusTypeModels = new Vector();  

 /*
  * 1- add a contract status type model to the vector 
  */
  public void addContractStatusTypeModel(ContractStatusTypeModel m)
  {
    contractStatusTypeModels.add(m);
  }

  /*
   * 2- get a contract status type model from the vector using this index 
   */
  public ContractStatusTypeModel getContractStatusTypeModel(int index)
  {
    return (ContractStatusTypeModel)contractStatusTypeModels.elementAt(index);
  }

  /*
   * 3- get contract status type model size 
   */
  public int getContractStatusTypeModelsSize()
  {
    return this.contractStatusTypeModels.size();
  }

/*
 * 4- clear contract models reamove all the objects in the vector 
 */
  public void clearContractModels()
  {
    this.contractStatusTypeModels.clear();
  }
}