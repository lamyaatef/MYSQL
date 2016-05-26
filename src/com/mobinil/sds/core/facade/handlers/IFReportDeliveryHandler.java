package com.mobinil.sds.core.facade.handlers;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Vector;

import com.mobinil.sds.core.system.cam.payment.dao.PaymentScmStatusDao;
import com.mobinil.sds.core.system.ifReportDelivery.dao.IFReportDeliveyDao;
import com.mobinil.sds.core.system.sa.importdata.dao.DataImportTableDefDAO;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.IFReportDelivery.IFReportDeliveryInterfaceKey;
import com.mobinil.sds.web.interfaces.cam.MemoInterfaceKey;
import com.mobinil.sds.web.interfaces.cam.PaymentInterfaceKey;
import com.mobinil.sds.web.interfaces.sa.AdministrationInterfaceKey;


public class IFReportDeliveryHandler {
	
	
	 private final static boolean  debugFlag = false;
	 static final int VIEW_EXPORT_MOBINIL_REPORT_TO_INFO=0;
	 static final int ACTION_SEARCH_EXPORT_MOBINIL_REPORT_TO_INFO=1;
	 static final int ACTION_EXPORT_MOBINIL_REPORT_TO_INFO=2;
	 static final int ACTION_VIEW_IMPORT_INFO_FORT_REPORT=3;
	 static final int ACTION_IMPORT_INFO_FORT_REPORT=4;
	 static final int ACTION_VIEW_DATA_REPORTS_JOBS=5;
	 static final int ACTION_VIEW_JOB_DETAILS=6;
	 static final int ACTION_UPDATE_JOB_DETAILS=7;
	 static final int ACTION_SEARCH_JOBS=8;
	 static final int ACTION_CLOSE_JOB_DETAILS=9;
	 static final int ACTION_DELETE_JOB_DETAILS=10;
	 static final int ACTION_EXPORT_NOT_FOUND=11;
	 static final int ACTION_EXPORT_DIST_ERRORS=12;
	 static final int ACTION_EXPORT_DUPLICATES=13;
	 static final int ACTION_EXPORT_INFO_FORT_ERRORS=14;
	 static final int ACTION_EXPORT_JOB_DETAILS_PAGE=15;
	 static final int ACTION_GENERATE_CHANGE_ERROR_TYPE_TEMPLATE=16;
	 static final int ACTION_VIEW_IMPORT_CHANGE_RECORD_TYPE=17;
	 static final int ACTION_IMPORT_CHANGE_RECORD_TYPE_TEMPLATE=18;
	 static final int ACTION_EXPORT_AUTO_MATCHED=19;
	 
	 
	private static void debug(String msg)
	{
		if(debugFlag) {
			System.out.println(msg);
        }
	}
	
	
	
	public static HashMap handle(String action, HashMap paramHashMap,java.sql.Connection mConSDSConnection)
	  {
	    debug("handler handle");
	    int actionType = 0;
	    HashMap dataHashMap = new HashMap(100);
	    String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	    if(strUserID != null && strUserID.compareTo("") != 0)
	    {
	      dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);           
	    }
	          try
	    {
	     // Connection mConSDSConnection = Utility.getConnection();
	      if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_VIEW_EXPORT_MOBINIL_REPORT_TO_INFO)==0)
	      {
	        actionType = VIEW_EXPORT_MOBINIL_REPORT_TO_INFO;        
	      }else if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_SEARCH_EXPORT_MOBINIL_REPORT_TO_INFO)==0)
	      {
	    	  actionType = ACTION_SEARCH_EXPORT_MOBINIL_REPORT_TO_INFO;
	      }else if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_EXPORT_MOBINIL_REPORT_TO_INFO)==0)
	      {
	    	  actionType = ACTION_EXPORT_MOBINIL_REPORT_TO_INFO;
	      }else if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_VIEW_IMPORT_INFO_FORT_REPORT)==0)
	      {
			  actionType = ACTION_VIEW_IMPORT_INFO_FORT_REPORT;        
		  }else if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_IMPORT_INFO_FORT_REPORT)==0)
	      {
			  actionType = ACTION_IMPORT_INFO_FORT_REPORT;        
		  }else if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_VIEW_DATA_REPORTS_JOBS)==0)
	      {
			  actionType = ACTION_VIEW_DATA_REPORTS_JOBS;        
		  }else if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_VIEW_JOB_DETAILS)==0)
	      {
			  actionType = ACTION_VIEW_JOB_DETAILS;        
		  }else if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_UPDATE_JOB_DETAILS)==0)
	      {
			  actionType = ACTION_UPDATE_JOB_DETAILS;        
		  }else if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_SEARCH_JOBS)==0)
	      {
			  actionType = ACTION_SEARCH_JOBS;        
		  }else if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_CLOSE_JOB_DETAILS)==0)
	      {
			  actionType = ACTION_CLOSE_JOB_DETAILS;        
		  }else if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_DELETE_JOB_DETAILS)==0)
	      {
			  actionType = ACTION_DELETE_JOB_DETAILS;        
		  }else if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_EXPORT_NOT_FOUND)==0)
	      {
			  actionType = ACTION_EXPORT_NOT_FOUND;        
		  }else if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_EXPORT_DIST_ERRORS)==0)
	      {
			  actionType = ACTION_EXPORT_DIST_ERRORS;        
		  }else if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_EXPORT_INFO_FORT_ERRORS)==0)
	      {
			  actionType = ACTION_EXPORT_INFO_FORT_ERRORS;        
		  }else if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_EXPORT_JOB_DETAILS_PAGE)==0)
	      {
			  actionType = ACTION_EXPORT_JOB_DETAILS_PAGE;        
		  }else if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_VIEW_IMPORT_CHANGE_RECORD_TYPE)==0)
	      {
			  actionType = ACTION_VIEW_IMPORT_CHANGE_RECORD_TYPE;        
		  }else if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_IMPORT_CHANGE_RECORD_TYPE_TEMPLATE)==0)
	      {
			  actionType = ACTION_IMPORT_CHANGE_RECORD_TYPE_TEMPLATE;        
		  }else if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_EXPORT_DUPLICATES)==0)
	      {
			  actionType = ACTION_EXPORT_DUPLICATES;        
		  }else if (action.compareTo(IFReportDeliveryInterfaceKey.ACTION_EXPORT_AUTO_MATCHED)==0)
	      {
			  actionType = ACTION_EXPORT_AUTO_MATCHED;        
		  }
	      
	      
	      switch (actionType) 
	      {
	      case VIEW_EXPORT_MOBINIL_REPORT_TO_INFO:
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	    	  Vector batchTypes=IFReportDeliveyDao.getBatchTypes(mConSDSConnection);
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.VECTOR_BATCH_TYPES,batchTypes);
	      }
	      break;
	      case ACTION_SEARCH_EXPORT_MOBINIL_REPORT_TO_INFO:
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	    	  String batchId=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_BATCH_ID);
	    	  debug("batchId:"+batchId);
	    	  String batchDate=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_BATCH_DATE);
	    	  debug("batchDate:"+batchDate);
	    	  String dcmName=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_DCM_NAME);
	    	  debug("dcmName:"+dcmName);
	    	  String batchTypeId=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_BATCH_TYPE);
	    	  debug("batchTypeId:"+batchTypeId);
	    	  
	    	  
	    	  
	    	  
	    	 Vector batches= IFReportDeliveyDao.searchForExport(mConSDSConnection, batchId, batchDate, dcmName,batchTypeId);
	    	 
	    	 dataHashMap.put(IFReportDeliveryInterfaceKey.VECTOR_SEARCH_BATCHES, batches);
	    	 
	    	 Vector batchTypes=IFReportDeliveyDao.getBatchTypes(mConSDSConnection);
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.VECTOR_BATCH_TYPES,batchTypes);
	      }
	      break;
	      case ACTION_EXPORT_MOBINIL_REPORT_TO_INFO:
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	    	  String batchId=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_BATCH_ID, batchId);
	    	  
	      }
	      break;
	      
	      case ACTION_VIEW_IMPORT_INFO_FORT_REPORT:
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	    	  
	    	  
	    	  Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("29");
	    	  debug("in handler: "+tableDefVector.size());
	          dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR ,tableDefVector); 
	    	  
	      }
	      break;
	      case ACTION_IMPORT_INFO_FORT_REPORT:
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	    	  //String reportDate=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_REPORT_CREATION_DATE);
	    	 // debug("report creation date: "+reportDate);
	    	  //dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_REPORT_CREATION_DATE, reportDate);	
	      }
	      break;
	      
	      case ACTION_VIEW_IMPORT_CHANGE_RECORD_TYPE :
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	    	  
	    	  Vector jobs=IFReportDeliveyDao.getJobs(mConSDSConnection,"","","1",false);
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.VECTOR_JOBS, jobs);
	    	  
	    	  Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("30");
	    	  debug("in handler: "+tableDefVector.size());
	          dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR ,tableDefVector); 
	      }
	      break;
	      case ACTION_IMPORT_CHANGE_RECORD_TYPE_TEMPLATE :
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	      }
	      break;
	      case ACTION_VIEW_DATA_REPORTS_JOBS:
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	    	  Vector jobStatuses=IFReportDeliveyDao.getJobstatuses(mConSDSConnection);
	    	  

	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.VECTOR_JOB_STATUSES, jobStatuses);
	    	  Vector jobs=IFReportDeliveyDao.getJobs(mConSDSConnection,"","","",true);
	    	   
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.VECTOR_JOBS, jobs);
	    	  
	    	  
	      }
	      break;
	      case ACTION_SEARCH_JOBS:
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	    	  
	    	  String reportDate=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_REPORT_CREATION_DATE);
	    	  String jobDate=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_JOB_CREATION_DATE);
	    	  String jobStatusId=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_SELECT_JOB_STATUS);
	    	 
	    	  
	    	  
	    	  Vector jobs=IFReportDeliveyDao.getJobs(mConSDSConnection,jobDate,reportDate,jobStatusId,true);
	    	  
	    	  
	    	  Vector jobStatuses=IFReportDeliveyDao.getJobstatuses(mConSDSConnection);
	    	  

	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.VECTOR_JOB_STATUSES, jobStatuses);
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.VECTOR_JOBS, jobs);
	    	  
	      }
	      break;
	      
	      case ACTION_VIEW_JOB_DETAILS:
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	    	  String jobId=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID);
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_JOB_ID, jobId);
	    	  
	    	  int pageNum = Integer.parseInt(paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString());
	    	  
	    	  
	    	  int totalPages =IFReportDeliveyDao.getInfoFortDataCount(mConSDSConnection, jobId);
	    	  
				if(totalPages%50 ==0)
					totalPages = (totalPages/50);
				else
				{
					totalPages = (totalPages/50);
					totalPages ++;
				}
				
				//debug("totalPages: "+totalPages);

				dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_TOTAL_PAGES, totalPages );
				dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER, pageNum);
	    	  
				
				int jobStatusId=IFReportDeliveyDao.getJobStatus(mConSDSConnection, jobId);
				
				dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_SELECT_JOB_STATUS, String.valueOf(jobStatusId));
				
	    	  
	    	  
	    	  
	    	  
	    	  Vector infoFortData=IFReportDeliveyDao.getInfoFortData(mConSDSConnection, jobId,""+((pageNum*50)-50),""+(pageNum*50));
	    	  
	    	  debug("data size in handler: "+infoFortData.size());
	    	  //IFReportDeliveyDao.compare(mConSDSConnection, Integer.parseInt(jobId), Integer.parseInt(strUserID));
	    	  
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.VECTOR_INFO_FORT_REPORTS, infoFortData);
	    	 	    	  
	    	  Vector dataRecordTypes=IFReportDeliveyDao.getDataRecordTypes(mConSDSConnection);
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.VECTOR_DATA_RECORD_TYPES, dataRecordTypes);
	    	  
	    	  }
	      break;
	      case ACTION_EXPORT_JOB_DETAILS_PAGE :
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	    	  String jobId=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID);
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_JOB_ID, jobId);
	    	  
	    	  int jobStatusId=IFReportDeliveyDao.getJobStatus(mConSDSConnection, jobId);
				
			  dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_SELECT_JOB_STATUS, String.valueOf(jobStatusId));
				
	    	  
	    	  String pageNum =(String) paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString();
	    	  debug("page number: "+pageNum);
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER, pageNum);
	      }
	      break;
	      
	      case ACTION_CLOSE_JOB_DETAILS:
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	    	  
	    	  String jobId=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID);
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_JOB_ID, jobId);
	    	  
	    	  IFReportDeliveyDao.closeJob(mConSDSConnection, jobId);
	    	  
	    	  
	    	  Vector jobStatuses=IFReportDeliveyDao.getJobstatuses(mConSDSConnection);
	    	  

	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.VECTOR_JOB_STATUSES, jobStatuses);
	    	  Vector jobs=IFReportDeliveyDao.getJobs(mConSDSConnection,"","","",true);
	    	   
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.VECTOR_JOBS, jobs);
	    	  
	    	  
	      }
	      break;
	      case ACTION_DELETE_JOB_DETAILS:
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	    	  
	    	  
	    	  String jobId=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID);
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_JOB_ID, jobId);
	    	  IFReportDeliveyDao.deleteJob(mConSDSConnection, jobId);
	    	  
	    	  
	    	  Vector jobStatuses=IFReportDeliveyDao.getJobstatuses(mConSDSConnection);
	    	  

	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.VECTOR_JOB_STATUSES, jobStatuses);
	    	  Vector jobs=IFReportDeliveyDao.getJobs(mConSDSConnection,"","","",true);
	    	   
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.VECTOR_JOBS, jobs);
	    	  
	    	  
	      }
	      break;
	      case ACTION_UPDATE_JOB_DETAILS:
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
			  String jobId=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID);
	    	  
	    	  for(int j=0; j<paramHashMap.size(); j++)
				{
					String tempTypeString = (String)paramHashMap.keySet().toArray()[j];   

					if(tempTypeString.startsWith(IFReportDeliveryInterfaceKey.CONTROL_SELECT_RECORD_TYPE))              
					{
						String keyValue = (String)paramHashMap.get(tempTypeString);
						debug("typeId: "+keyValue);


						String simSerial =tempTypeString.substring(19, tempTypeString.length());
						debug("simSerial: "+simSerial);
						
						IFReportDeliveyDao.updateJobDetailsData(strUserID,simSerial, keyValue,jobId);						
					}
					
				}
	    	  
	    	  
	    	  
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_JOB_ID, jobId);
	    	  
	    	  int jobStatusId=IFReportDeliveyDao.getJobStatus(mConSDSConnection, jobId);				
			  dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_SELECT_JOB_STATUS, String.valueOf(jobStatusId));
			  
			  String pageNum =(String) paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString();
	    	  debug("page number: "+pageNum);
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER, pageNum);
	    	  
	    	  int totalPages =IFReportDeliveyDao.getInfoFortDataCount(mConSDSConnection, jobId);
	    	  
				if(totalPages%50 ==0)
					totalPages = (totalPages/50);
				else
				{
					totalPages = (totalPages/50);
					totalPages ++;
				}
				
				dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_TOTAL_PAGES, totalPages );
				
	    	  Vector infoFortData=IFReportDeliveyDao.getInfoFortData(mConSDSConnection, jobId,""+((Integer.parseInt(pageNum)*50)-50),""+(Integer.parseInt(pageNum)*50));
	    	  
	    	  
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.VECTOR_INFO_FORT_REPORTS, infoFortData);
	    	 	    	  
	    	  Vector dataRecordTypes=IFReportDeliveyDao.getDataRecordTypes(mConSDSConnection);
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.VECTOR_DATA_RECORD_TYPES, dataRecordTypes);
	      }
	      break;
	      case ACTION_EXPORT_NOT_FOUND:
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	    	  String jobId=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID);
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_JOB_ID, jobId);
	      }
	      break;
	      case ACTION_EXPORT_DIST_ERRORS:
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	    	  String jobId=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID);
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_JOB_ID, jobId);
	      }
	      
	      break;
	      case ACTION_EXPORT_DUPLICATES:
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	    	  String jobId=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID);
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_JOB_ID, jobId);
	      }
	      break;
	      case ACTION_EXPORT_AUTO_MATCHED:
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	    	  String jobId=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID);
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_JOB_ID, jobId);
	      }
	      break;
	      case ACTION_EXPORT_INFO_FORT_ERRORS:
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	    	  String jobId=(String)paramHashMap.get(IFReportDeliveryInterfaceKey.CONTROL_HIDDEN_JOB_ID);
	    	  dataHashMap.put(IFReportDeliveryInterfaceKey.CONTROL_JOB_ID, jobId);
	      }
	      break;
	      
	      case ACTION_GENERATE_CHANGE_ERROR_TYPE_TEMPLATE:
	      {
	    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
	      }
	      break;
	      
	      }
	      
	      
	    }catch(Exception objExp)
	    {
	        objExp.printStackTrace();
	      }
	      return dataHashMap;
	  }
	          
}
