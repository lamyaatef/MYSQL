package com.mobinil.sds.core.system.cr.sheet.model;
/*
 * sheet contract verification statistic model that hold the number of verified contracts count 
 * 
 * 1- constructor take as input the number of verified contracts
 * 2- get verified contracts count
 * 3- set the verified contracts count 
 */
public class SheetContractVerificationStatisticModel 
{

  int verifiedContractsCount;

/*
 * 1- constructor take as input the number of verified contracts  
 */
  public SheetContractVerificationStatisticModel(int verifiedContractsCount)
  {
    this.verifiedContractsCount= verifiedContractsCount;
  }

/*
 * 2- get verified contracts count 
 */
  public int getVerifiedContractsCount()
  {
    return verifiedContractsCount;
  }

/*
 * 3- set the verified contracts count 
 */
  public void setVerifiedContractsCount(int newVerifiedContractsCount)
  {
    verifiedContractsCount = newVerifiedContractsCount;
  }


}

