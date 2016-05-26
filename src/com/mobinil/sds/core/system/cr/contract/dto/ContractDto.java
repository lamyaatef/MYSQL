package com.mobinil.sds.core.system.cr.contract.dto;
import java.io.Serializable;
import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.system.cr.contract.model.*;

/*
 * Contract Dto is the class the encapsulate a vector contractmodels of ContractModels objects
 * and a hashtable contractwarning that contains all the warnings of all the contracts in the vector
 * 1-add contract warning 
 * this method take as input 
 * a contract id and a result set of the warnings of this contract 
 * the method take each row in teh warning results set and get its data 
 * in contract warning model and add it to the vector of warnings that is save
 * in the hashtable of all contracts warnings in a vector
 * 2- get contract warning takes a contract id and retrive the vector of contract warning models
 * that is stored in the conteractswarning hashtable and return it
 * if no vector ie no warnings to this contract the method return null 
 * 3- get contracts models return the vector contractsModel
 * 4- add contract model this method take a ContractModel m object
 * and add it to the vector of contractModels
 * 5- get contract from the contractModles vector in the index sent to the method
 * 6- get contract models size this return an int which is the size of the contractModels vector 
 * which means the number of contracts in this dto * 5- get contract from the contractModles vector in the index sent to the method
 * 7- clear contract Models delete all the contracts from the contractModels
 */

public class ContractDto implements Serializable 
{
  //this vector has ContractModel objects
  private Vector contractModels = new Vector();
  //this hashtable composed of keys which are the contract ids and vectors each vector
  //has objects of ContractWarningModel 
  private Hashtable contractsWarning = new Hashtable();
  private Hashtable contractsSystemWarning = new Hashtable();
  
  public ContractDto()
  {
  }

  /*
   * 1-add contract warning 
   * this method take as input 
   * a contract id and a result set of the warnings of this contract 
   * the method take each row in teh warning results set and get its data 
   * in contract warning model and add it to the vector of warnings that is save
   * in the hashtable of all contracts warnings in a vector 
   */
  public void addContractWarning(String contractId, ResultSet res)
  {
    Vector contractWarningsVector = null;
    if (this.contractsWarning.containsKey(contractId))
    {
      contractWarningsVector = (Vector) this.contractsWarning.get(contractId);
    }
    else
    {
      contractWarningsVector = new Vector();
      contractsWarning.put(contractId,contractWarningsVector);
    }
      contractWarningsVector.add(new ContractWarningModel(res));
  }

  public void addContractSystemWarning(String contractId, ResultSet res)
  {
    Vector contractSystemWarningsVector = null;
    if (this.contractsSystemWarning.containsKey(contractId))
    {
      contractSystemWarningsVector = (Vector) this.contractsSystemWarning.get(contractId);
    }
    else
    {
      contractSystemWarningsVector = new Vector();
      contractsSystemWarning.put(contractId,contractSystemWarningsVector);
    }
      contractSystemWarningsVector.add(new ContractWarningModel(res));
  }

  /*
   * 2- get contract warning takes a contract id and retrive the vector of contract warning models
   * taht is stored in the conteractswarning hashtable and return it
   * if no vector ie no warnings to this contract the method return null
   */
  public Vector getContractWarning(String contractId)
  {
    if (this.contractsWarning.containsKey(contractId))
    {
      return (Vector) this.contractsWarning.get(contractId);
    }
    else
    {
      return null;
    }
  }

  public Vector getContractSystemWarning(String contractId)
  {
    if (this.contractsSystemWarning.containsKey(contractId))
    {
      return (Vector) this.contractsSystemWarning.get(contractId);
    }
    else
    {
      return null;
    }
  }

 /*
  * 3- get contracts models return the vector contractModels
  */
  public Vector getContractModels()
  {
    return this.contractModels;
  }

/*
 * 4- add contract model this method take a ContractModel m object
 * and add it to the vector of contractModels
 */
  public void addContractModel(ContractModel m)
  {
    contractModels.add(m);
  }

  /*
   * 5- get contract from the contractModles vector in the index sent to the method 
   */
  public ContractModel getContract(int index)
  {
    return (ContractModel)contractModels.elementAt(index);
  }

  /*
   * 6- get contract models size this return an int which is the size of the contractModels vector 
   * which means the number of contracts in this dto * 5- get contract from the contractModles vector in the index sent to the method
   */ 
  public int getContractModelsSize()
  {
    return this.contractModels.size();
  }

/*
 * 7- clear contract Models delete all the contracts from the contractModels 
 */
  public void clearContractModels()
  {
    this.contractModels.clear();
    this.contractsWarning.clear();
  }
}