package com.mobinil.sds.core.system.cr.worker;

import com.mobinil.sds.core.system.cr.batch.dao.BatchDao;
import com.mobinil.sds.core.system.cr.batch.dto.BatchDto;
import com.mobinil.sds.core.system.cr.batch.dto.BatchStatusTypeDto;
import com.mobinil.sds.core.system.cr.batch.dto.BatchTypeDto;
import com.mobinil.sds.core.system.cr.contract.dao.ContractDao;
import com.mobinil.sds.core.system.cr.contract.dto.ContractDto;
import com.mobinil.sds.core.system.cr.contract.model.ContractModel;
import com.mobinil.sds.core.system.cr.contract.model.ContractWarningModel;
import com.mobinil.sds.core.system.cr.sheet.dto.SheetDto;
import com.mobinil.sds.core.system.cr.sheet.model.SheetModel;
import com.mobinil.sds.core.system.gn.dcm.dao.DCMDao;
import com.mobinil.sds.core.system.gn.dcm.dto.DCMDto;
import com.mobinil.sds.core.utilities.AbstractWorker;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.cr.ContractRegistrationInterfaceKey;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

public class OpenArchivingWorker extends AbstractWorker
{
  private String batchId;
  private String strUserID;
  private String dcmId;
  private String date;
  private String batchType;
  private String batchStatusTypeId;
        
  public OpenArchivingWorker(String strUserID,String batchId,String dcmId,String date,String batchType,String batchStatusTypeId)
  {
    this.batchId = batchId;
    this.strUserID = strUserID;
    this.dcmId = dcmId;
    this.date = date;
    this.batchType = batchType;
    this.batchStatusTypeId = batchStatusTypeId;
  }

  public void run ()
  {
    HashMap data=null;
    try
    {
      Connection con = Utility.getConnection();

      Hashtable additionalCollection = new Hashtable();
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
            String automaticFlag = contractModel.getAutomaticFlag();
            String contractLastStatusId = contractModel.getContractLastStatusId();
            String contractStatusName = contractModel.getStatusTypeName();
            
            Vector contractUserWarnings = contractDto.getContractWarning(contractId);
            Vector contractSystemWarningModelVector = ContractDao.validateContractInContractVerification(con,contractId,automaticFlag,contractLastStatusId);
            String contractSystemGeneratedStatusId = "5";
            //5 is accpeted verify status
            for(int k=0;k<contractSystemWarningModelVector.size();k++)
            {
              ContractWarningModel contractWarningModel = (ContractWarningModel)contractSystemWarningModelVector.elementAt(k);
              if(contractWarningModel != null)
              {
                String warningSuggestedStatus = contractWarningModel.getContractwarningSuggestedStatusID();
                if(warningSuggestedStatus.compareTo("2")==0)
                {
                  //suggested 2 means rejected

                  //6 is rejected verify status
                  contractSystemGeneratedStatusId = "6";
                }
              }
            }
            //update here contract status with system generated status
            String[] user_warnings = new String[0];
            if(contractStatusName.compareTo(ContractRegistrationInterfaceKey.CONST_CONTRACT_STATUS_ACCEPTED_VERIFY)!=0 && contractStatusName.compareTo(ContractRegistrationInterfaceKey.CONST_CONTRACT_STATUS_REJECTED_VERIFY)!=0)
            {
              ContractDao.updateContract(contractId, strUserID, sheetId, batchId, contractSystemGeneratedStatusId, user_warnings , contractSystemWarningModelVector);
            }  
          }
        }
      
        BatchDao.updateBatchArchivingFlag(batchId,ContractRegistrationInterfaceKey.CONST_ARCHIVING_FLAG_ENTERED_ARCHIVING_PROCESS); 
        
        DCMDto dcmDto = DCMDao.getContractRegisterationUserDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR, strUserID,con);
        BatchTypeDto batchTypeDto = BatchDao.getBatchTypes(con);         
        BatchStatusTypeDto batchStatusTypeDto = BatchDao.getBatchStatusTypeAllowedInContractVerification(con);
          
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_TYPE_DTO,batchTypeDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_STATUS_TYPE_DTO,batchStatusTypeDto);

        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID,dcmId);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
        
        data = new HashMap();
        data.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);
        data.put(InterfaceKey.HASHMAP_KEY_COLLECTION , BatchDao.SearchBatch(dcmId, date, batchType, batchStatusTypeId, ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE,con));
        

      
      Utility.closeConnection(con);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
   this.myBuffer.put(data);
  }
}