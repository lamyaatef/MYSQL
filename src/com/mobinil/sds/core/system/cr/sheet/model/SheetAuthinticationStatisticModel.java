package com.mobinil.sds.core.system.cr.sheet.model;
import java.io.Serializable;

/*
 * sheet authentication statistic model 
 * it holds the following data about a sheet 
 * sheet id 
 * total number of contraacts eligable for authintication 
 * total number of contracts that were accepted authenitcation 
 * total number of contracts that are rejected authintication 
 * total number of contracts needed to commission 
 * sheet serial number
 *
 * 1- get sheet id
 * 2- set sheet id
 * 3- get total contracts eligable for authintication
 * 4- set total contracts eligable for authintication
 * 5- get total contracts authinticated
 * 6- set total contracts authinticated  
 * 7- get total contracts rejected authinticated 
 * 8- set total contracts rejected authinticated 
 * 9- get total contracts needed to commission 
 * 10- set total contracts needed to commission 
 * 11- get sheet serial number
 * 12- set sheet serial number
 * 13- get total accpeted unreachable
 * 14- set total accepted uncreachable
 * 15- get total rejected unreachable
 * 16- set total rejected unreachable
 * 17- get total rejected pos unknown
 * 18- set total rejected pos unknown
 * 19- get total accepted pos unknown
 * 20- set total accepted unknown 
 * 21- get discard unknown unreachable rule  
 * 22- set discard unknownunreachable
 * 23- set total accepted unknown
 * 24- set total rejected pos unknown
 * 25- set total rejected unreachable 
 * 26- set total accepted uncreachable  
 * 27- set total contracts needed to commission
 * 28- set total contracts rejected authinticated 
 * 29- set total contracts authinticated 
 */
public class SheetAuthinticationStatisticModel implements Serializable
{
  
  
  String sheetId;
  int totalContractsEligableForAuthintication=0;
  int totalContractsAuthinticated=0;
  int totalContractsRejectedAuthinticated=0;
  int totalContractsNeededToCommission=0;
  String sheetSerial;
  int totalAcceptedUnrechable=0;
  int totalRejectedUnreachable=0;
  int totalRejectedPosUnknown=0;
  int totalAcceptedPosUnknown=0;
  boolean discardUnknownUnreachble;
  String sheetStatus;

  public SheetAuthinticationStatisticModel()
  {
  }

/*
 * 1- get sheet id 
 */
  public String getSheetId()
  {
    return sheetId;
  }

/*
 * 2- set sheet id 
 */
  public void setSheetId(String newSheetId)
  {
    sheetId = newSheetId;
  }

/*
 * 3- get total contracts eligable for authintication 
 */
  public int getTotalContractsEligableForAuthintication()
  {
    return totalContractsEligableForAuthintication;
  }

/*
 * 4- set total contracts eligable for authintication 
 */
  public void setTotalContractsEligableForAuthintication(String newTotalContractsEligableForAuthintication)
  {
    try{
    totalContractsEligableForAuthintication = Integer.parseInt(newTotalContractsEligableForAuthintication);
    }
    catch(Exception e)
    {
      
    }
  }

/*
 * 30- set total contracts eligable for authintication 
 */
  public void setTotalContractsEligableForAuthintication(int newTotalContractsEligableForAuthintication)
  {
    totalContractsEligableForAuthintication = newTotalContractsEligableForAuthintication;
  }


/*
 * 5- get total contracts authinticated
 */
  public int getTotalContractsAuthinticated()
  {
    return totalContractsAuthinticated;
  }

/*
 * 6- set total contracts authinticated 
 */
  public void setTotalContractsAuthinticated(String newTotalContractsAuthinticated)
  {
    try{
    totalContractsAuthinticated = Integer.parseInt(newTotalContractsAuthinticated);
    }
    catch(Exception e)
    {
      
    }
  }

  
/*
 * 29- set total contracts authinticated 
 */
  public void setTotalContractsAuthinticated(int newTotalContractsAuthinticated)
  {
    totalContractsAuthinticated = newTotalContractsAuthinticated;
  }


/*
 * 7- get total contracts rejected authinticated
 */
  public int getTotalContractsRejectedAuthinticated()
  {
    return totalContractsRejectedAuthinticated;
  }

/*
 * 8- set total contracts rejected authinticated 
 */
  public void setTotalContractsRejectedAuthinticated(int newTotalContractsRejectedAuthinticated)
  {
    totalContractsRejectedAuthinticated = newTotalContractsRejectedAuthinticated;
  }
/*
 * 28- set total contracts rejected authinticated 
 */
  public void setTotalContractsRejectedAuthinticated(String newTotalContractsRejectedAuthinticated)
  {
    try{
    totalContractsRejectedAuthinticated =Integer.parseInt(newTotalContractsRejectedAuthinticated);
    }
    catch(Exception e)
    {
      
    }
  }  

/*
 * 9- get total contracts needed to commission 
 */
  public int getTotalContractsNeededToCommission()
  {
    return totalContractsNeededToCommission;
  }

/*
 * 10- set total contracts needed to commission
 */
  public void setTotalContractsNeededToCommission(int newTotalContractsNeededToCommission)
  {
    totalContractsNeededToCommission = newTotalContractsNeededToCommission;
  }

/*
 * 27- set total contracts needed to commission
 */
  public void setTotalContractsNeededToCommission(String newTotalContractsNeededToCommission)
  {
    try{
    totalContractsNeededToCommission = Integer.parseInt( newTotalContractsNeededToCommission);
    }
    catch(Exception e)
    {      
    }
  }

/*
 * 11- get sheet serial number
 */
  public String getSheetSerial()
  {
    return sheetSerial;
  }

/*
 * 12- set sheet serial number 
 */
  public void setSheetSerial(String newSheetSerial)
  {
    sheetSerial = newSheetSerial;
  }

/*
 * 13- get total accpeted unreachable 
 */
  public int getTotalAcceptedUnrechable()
  {
    return totalAcceptedUnrechable;
  }

/*
 * 14- set total accepted uncreachable 
 */
  public void setTotalAcceptedUnrechable(String newTotalAcceptedUnrechable)
  {
  try{
    totalAcceptedUnrechable = Integer.parseInt(newTotalAcceptedUnrechable);
  }
  catch(Exception e)
  {
    
  }
  }


/*
 * 26- set total accepted uncreachable 
 */
  public void setTotalAcceptedUnrechable(int newTotalAcceptedUnrechable)
  {
    totalAcceptedUnrechable = newTotalAcceptedUnrechable;
  }
  
/*
 * 15- get total rejected unreachable 
 */
  public int getTotalRejectedUnreachable()
  {
    return totalRejectedUnreachable;
  }

/*
 * 16- set total rejected unreachable 
 */
  public void setTotalRejectedUnreachable(String newTotalRejectedUnreachable)
  {
     try{
    totalRejectedUnreachable = Integer.parseInt(newTotalRejectedUnreachable);
     }
     catch(Exception e)
    {
    }
  }


/*
 * 25- set total rejected unreachable 
 */
  public void setTotalRejectedUnreachable(int newTotalRejectedUnreachable)
  {
    totalRejectedUnreachable = newTotalRejectedUnreachable;
  }  

/*
 * 17- get total rejected pos unknown
 */
  public int getTotalRejectedPosUnknown()
  {
    return totalRejectedPosUnknown;
  }

/*
 * 18- set total rejected pos unknown
 */
  public void setTotalRejectedPosUnknown(String newTotalRejectedPosUnknown)
  {
    try{
    totalRejectedPosUnknown =Integer.parseInt( newTotalRejectedPosUnknown);
    }
    catch(Exception e)
    {      
    }
  }

/*
 * 24- set total rejected pos unknown
 */
  public void setTotalRejectedPosUnknown(int newTotalRejectedPosUnknown)
  {
    totalRejectedPosUnknown = newTotalRejectedPosUnknown;
  }  

/*
 * 19- get total accepted pos unknown 
 */
  public int getTotalAcceptedPosUnknown()
  {
   
    return totalAcceptedPosUnknown;
  }

/*
 * 20- set total accepted unknown 
 */
  public void setTotalAcceptedPosUnknown(String newTotalAcceptedPosUnknown)
  {
    try 
    {    
      totalAcceptedPosUnknown = Integer.parseInt(newTotalAcceptedPosUnknown);
    }
    catch(Exception e)
    {      
    }
  }
/*
 * 23- set total accepted unknown 
 */
  public void setTotalAcceptedPosUnknown(int newTotalAcceptedPosUnknown)
  { 
    totalAcceptedPosUnknown = newTotalAcceptedPosUnknown;
  }

/*
 * 21- get discard unknown unreachable rule 
 */
  public boolean getDiscardUnknownUnreachble()
  {
    return discardUnknownUnreachble;
  }

/*
 * 22- set discard unknownunreachable 
 */
  public void setDiscardUnknownUnreachble(boolean newDiscardUnknownUnreachble)
  {
    discardUnknownUnreachble = newDiscardUnknownUnreachble;
  }

  public String getSheetStatus()
  {
    return sheetStatus;
  }

  public void setSheetStatus(String newSheetStatus)
  {
    sheetStatus = newSheetStatus;
  }
}