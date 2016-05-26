package com.mobinil.sds.core.system.cr.worker;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Vector;

import com.mobinil.sds.core.system.cr.batch.dao.BatchDao;
import com.mobinil.sds.core.system.cr.batch.dto.BatchDto;
import com.mobinil.sds.core.system.cr.contract.dao.ContractDao;
import com.mobinil.sds.core.system.cr.contract.dto.ContractDto;
import com.mobinil.sds.core.system.cr.contract.model.ContractModel;
import com.mobinil.sds.core.system.cr.sheet.dto.SheetDto;
import com.mobinil.sds.core.system.cr.sheet.model.SheetModel;
import com.mobinil.sds.core.system.sa.importdata.ErrorInfo;
import com.mobinil.sds.core.utilities.AbstractWorker;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.cr.ContractRegistrationInterfaceKey;

public class ImportArchivingWorkerReject extends AbstractWorker
{
	  private String tableId;
	  private String operation;
	  private String strUserID;
	  private String batchId;
	  private Vector reportImport;

	  public ImportArchivingWorkerReject(String tableId,String operation,String strUserID,String batchId,Vector reportImport)
	  {
	    this.tableId = tableId;
	    this.strUserID = strUserID;
	    this.operation = operation;
	    this.batchId = batchId;
	    this.reportImport = reportImport;
	  }
	  public void run ()
	  {
	    HashMap data=null;
	    try
	    {
	      Connection con = Utility.getConnection();
	      ErrorInfo sheetError = null;
	      Vector report = new Vector();
	      if(batchId.compareTo("")==0)
	      {
	          sheetError = new ErrorInfo();
	          sheetError.setCellName(batchId);
	          sheetError.setErrorMsg("Batch Id is null or empty.");
	          sheetError.setRowNum(0);
	          report.add(sheetError);
	      }
	      else
	      {
	          boolean batchExistsInVerificationAndOenedForArchiving = ContractDao.getBatchOpenedForArchiving(con,batchId);
	          if(batchExistsInVerificationAndOenedForArchiving)
	          {
	            HashMap contractsFromArchiving = ContractDao.getTmpCrArchivingContracts(con);
	            HashMap contractWarningTypesSuggestAccept = ContractDao.getContractWarningTypeId(con,"1");
	            HashMap contractWarningTypesSuggestReject = ContractDao.getContractWarningTypeId(con,"2");
	            BatchDto batchDto = BatchDao.getBatchAndSheets(batchId,ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE,con);
	            SheetDto sheetDto = batchDto.getSheetDto();
	            Vector listOfSheets = sheetDto.getSheetModels();
	            for(int i=0;i<listOfSheets.size();i++)
	            {
	              SheetModel sheetModel = (SheetModel)listOfSheets.get(i);   
	              String sheetId = sheetModel.getSheetId();
	              ContractDto contractDto = ContractDao.getSheetContracts(sheetId,con);
	              Vector contractModels = contractDto.getContractModels();
	              for(int j=0;j<contractModels.size();j++)
	              {
	                ContractModel contractModel = (ContractModel)contractModels.get(j);
	                String contractId = contractModel.getId();
	                String contractMainSIMNumbre = contractModel.getMainSimNum();
	                String automaticFlag = contractModel.getAutomaticFlag();
	                String contractLastStatusId = contractModel.getContractLastStatusId();
	                String contractStatusName = contractModel.getStatusTypeName();

	                //Utility.logger.debug("SIM : "+contractMainSIMNumbre);
	                
	                Vector contractUserWarnings = contractDto.getContractWarning(contractId);
	                Vector contractSystemWarningModelVector = ContractDao.validateContractInContractVerification(con,contractId,automaticFlag,contractLastStatusId);
	                if(contractStatusName.compareTo(ContractRegistrationInterfaceKey.CONST_CONTRACT_STATUS_ACCEPTED_VERIFY)==0)
	                {
	                  String[] user_warnings = new String[1];
	                  if(contractsFromArchiving.containsKey(contractMainSIMNumbre))
	                  {
                              String contractSimNumber = (String)contractsFromArchiving.get(contractMainSIMNumbre);
	                    String warningTypeName = (String)contractsFromArchiving.get(contractMainSIMNumbre);
	                    //Utility.logger.debug("WARNING : "+warningTypeName);
	                    //String contractNextStatus = "5";
	                    String contractNextStatus = "6";
	                    
	                    if(contractWarningTypesSuggestAccept.containsKey(warningTypeName))
	                    {
	                      //Utility.logger.debug("Warning found in accepted hashmap");
	                      user_warnings[0] = (String)contractWarningTypesSuggestAccept.get(warningTypeName);
	                    }
	                    else if(contractWarningTypesSuggestReject.containsKey(warningTypeName))
	                    {
	                      //Utility.logger.debug("Warning found in rejected hashmap");
	                      //user_warnings[0] = (String)contractWarningTypesSuggestReject.get(warningTypeName);
	                      //contractNextStatus = "6";
	                      user_warnings[0] = "";
	                    }
	                    else
	                    {
	                      user_warnings[0] = "";  
	                    }
	                    //Utility.logger.debug("wwwwwwwwwww"+user_warnings[0]);
	                    if(user_warnings[0].compareTo("")!=0)
	                    {
	                      //update contract with accepted verify with the new warning
	                      ContractDao.updateContract(contractId, strUserID, sheetId, batchId, contractNextStatus , user_warnings , contractSystemWarningModelVector);
	                    }
	                  }
	                  else
	                  {
	                    //update contract with rejected verify with warning physical not found which has id 91
	                   // user_warnings[0] = "91";
	                    //ContractDao.updateContract(contractId, strUserID, sheetId, batchId, "6" , user_warnings , contractSystemWarningModelVector);
	                  }
	                }  
	              }
	            }
	            BatchDao.updateBatchArchivingFlag(batchId,"2");
	          }
	          else
	          {
	            sheetError = new ErrorInfo();
	            sheetError.setCellName(batchId);
	            sheetError.setErrorMsg("Batch is not in verification phase or not opened for archiving.");
	            sheetError.setRowNum(0);
	            report.add(sheetError);
	          }
	      }
	  
	      data = new HashMap();
	      data.put(ContractRegistrationInterfaceKey.OBJ_REPORT_IMPORT_ARCHIVING,report);
	      data.put(ContractRegistrationInterfaceKey.OBJ_SYS_REPORT_IMPORT_ARCHIVING,reportImport);
	      
	      ContractDao.deleteTmpCrArchivingContracts(con);
	      Utility.closeConnection(con);
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	   this.myBuffer.put(data);
	  }
	}

