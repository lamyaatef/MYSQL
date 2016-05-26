/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.facade.handlers;




import java.sql.*;
import java.util.*;
import java.util.zip.*;
import java.io.*;

import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.sa.*;
import com.mobinil.sds.web.interfaces.commission.CommissionInterfaceKey;
import com.mobinil.sds.web.interfaces.gn.querybuilder.QueryBuilderInterfaceKey;
import com.mobinil.sds.core.system.gn.querybuilder.domain.QueryBuilderEngine;

import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey;
import com.mobinil.sds.core.system.dcm.genericModel.*;
import com.mobinil.sds.core.system.dcm.genericModel.DAO.*;
import com.mobinil.sds.core.system.commission.dao.*;
import com.mobinil.sds.core.system.commission.demon.*;
import com.mobinil.sds.core.system.commission.factor.dao.*;
import com.mobinil.sds.core.system.commission.factor.model.*;
import com.mobinil.sds.core.system.commission.model.*;
import com.mobinil.sds.core.system.commission.postarget.dao.POSTargetDao;
import com.mobinil.sds.core.system.commission.postarget.report.ExcelGeneratorReport;
import com.mobinil.sds.core.system.payment.model.*;
import com.mobinil.sds.core.system.gn.reports.dto.*;
import com.mobinil.sds.core.system.gn.reports.domain.*;
import com.mobinil.sds.core.system.sa.importdata.DataImportEngine;
import com.mobinil.sds.core.system.sa.importdata.ErrorInfo;
import com.mobinil.sds.core.system.sa.importdata.dao.*;
import com.mobinil.sds.core.system.sa.importdata.model.DataImportReport;
import com.mobinil.sds.core.system.sa.users.dao.UserDAO;
import com.mobinil.sds.core.utilities.Copy;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.GetUploadedFile;
import com.mobinil.sds.web.interfaces.commission.POSTargetInterface;
import javax.servlet.http.HttpServletRequest;

public class ComH 
{
	static final int ACTION_COMMISSION_TEST               = 1 ;
	static final int COMMISSION_SAVE_NEW_COMMISSION       = 2;
	static final int COMMISSION_SEARCH_COMMISSION         = 3;
	static final int COMMISSION_EDIT_COMMISSION           = 4;
	static final int COMMISSION_ADD_NEW_COMMISSION        = 5; 
	static final int COMMISSION_SAVE_COMMISSION_CATEGORY  = 6;
	static final int COMMISSION_SEARCH_PAYMENT            = 7;
	static final int COMMISSION_EDIT_PAYMENT              = 8;
	static final int COMMISSION_ADD_NEW_PAYMENT           = 9;
	static final int COMMISSION_VIEW_PAYMENT              = 10;
	static final int COMMISSION_EXPORT_PAYMENT            = 11;
	static final int COMMISSION_SAVE_NEW_PAYMENT          = 12;
	static final int COMMISSION_CREATE_NEW_COMMISSION     = 13;
	static final int COMMISSION_CREATE_NEW_PAYMENT        = 14;
	static final int SEARCH_PAYMENT                       = 15;
	static final int SEARCH_COMMISSION                    = 16;
	static final int COMMISSION_CREATE_NEW_COMMISSION_CATEGORY = 17;
	static final int COMMISSION_MANAGE_CATEGORY           = 18;
	static final int COMMISSION_EDIT_COMMISSION_CATEGORY  = 19;
	static final int VIEW_READY_COMMISSION                = 20;
	static final int VIEW_PREPARING_COMMISSION            = 21;
	static final int VIEW_CLOSED_COMMISSION               = 22;
	static final int DELETE_COMMISSION                    = 23;
	static final int UPDATE_COMMISSION_STATUS             = 24;
	static final int COMMISSION_FACTORS                   = 25;
	static final int SAVE_COMMISSION_FACTORS              = 26;
	static final int EXPORT_COMMISSION_TO_EXCEL           = 27; 
	static final int VIEW_COMMISSION                      = 28;   
	static final int UPLOAD_FILES                         = 30;
	static final int COMMISSION_SAVE_NEW_COMMISSION_NO_PARAM = 31;
	static final int COMMISSION_SAVE_NEW_COMMISSION_PARAM  = 32;  
	static final int VIEW_DCM_PAYMENTS                     = 33;  
	static final int SEARCH_DCM_PAYMENTS                   = 34;
	static final int VIEW_COMMISSION_LABELS                = 35;
	static final int SHOW_LABEL_DATA                       = 36;
	static final int ADD_NEW_LABEL                         = 37;
	static final int UPDATE_LABEL_DATA                     = 38;
	static final int SAVE_LABEL_DATA                       = 39;
	static final int VIEW_LABEL_DETAILS_DATA               = 40;
	static final int SAVE_LABEL_DETAILS_DATA               = 41;
	static final int UPLOAD_COMMISSION_DATA                = 42;
	static final int COMMISSION_VIEW_CHANNELS			     = 43;
	static final int UPLOAD_COMMISSION_DATA_PROCESS        = 44;
	static final int COMMISSION_EDIT_CHANNEL = 45;
	static final int COMMISSION_NEW_CHANNEL = 46;
	static final int COMMISSION_SAVE_CHANNELS = 47;
	static final int COMMISSION_USER_CHANNELS = 48;
	static final int COMMISSION_ASSIGN_CHANNEL_TO_USER = 49;  
	static final int DELETE_ALL_LABEL_DETAILS = 50;
	static final int EXPORT_LABEL_DETAILS_DATA = 51;
	static final int DELETE_UPLOAD_FILE = 52;
	static final int DELETE_UPLOAD_FILE_PROCESS = 53;
        static final int action_upload_rated_zip_file=54;
        static final int delete_upload_rated_activity=55;
        static final int action_delete_rated_activity=56;
        static final int action_view_rated_activity=57;
        static final int action_view_driving_plan_commission_detail=58;
        static final int ACTION_UPLOAD_POS_TARGET=59;
        static final int ACTION_MANAGEMENT_POS_TARGET=60;
        static final int ACTION_DELETE_FILE_POS_TARGET=61;
        
	private static  void prepareCommissionSearchScreenLookups(HashMap dataHashMap, String userId,Connection con) throws Exception
	{
		GenericModel commissionTypeModel = GenericModelDAO.getColumns(con,"COMMISSION_TYPE");
		Vector commissionTypes = GenericModelDAO.getModels(con,commissionTypeModel);
		dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_TYPE , commissionTypes);

		GenericModel commissionTypeCategoryModel = GenericModelDAO.getColumns(con,"COMMISSION_TYPE_CATEGORY");
		Vector commissionTypeCategories = GenericModelDAO.getModels(con ,commissionTypeCategoryModel,"COMMISSION_TYPE_CATEGORY_NAME");
		dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_TYPE_CATEGORY , commissionTypeCategories);

		GenericModel commissionStatusModel = GenericModelDAO.getColumns(con,"COMMISSION_STATUS_TYPE");
		Vector commissionStatus = GenericModelDAO.getModels(con ,commissionStatusModel);
		dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_STATUS , commissionStatus);

		Vector comChannelVec = GenericModelDAO.getCommissionChannel(con, userId);
		dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,comChannelVec);
	}


	public static HashMap handle(String action, HashMap paramHashMap,Connection con) throws Exception
	{
		int actionType  = 0;
		// Connection con = null;
		HashMap dataHashMap = new HashMap(100);
                System.out.println("action iss "+action);
		try
		{
			// con = Utility.getConnection();
			if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_SAVE_NEW_COMMISSION))
				actionType = COMMISSION_SAVE_NEW_COMMISSION;
			else if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_ADD_NEW_COMMISSION))
				actionType = COMMISSION_ADD_NEW_COMMISSION;
			else if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_ADD_NEW_PAYMENT))
				actionType = COMMISSION_ADD_NEW_PAYMENT;
			else if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_EDIT_COMMISSION))
				actionType = COMMISSION_EDIT_COMMISSION;
			else if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_EDIT_PAYMENT))
				actionType = COMMISSION_EDIT_PAYMENT;
			else if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_EXPORT_PAYMENT))
				actionType = COMMISSION_EXPORT_PAYMENT;
			else if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_SAVE_COMMISSION_CATEGORY))
				actionType = COMMISSION_SAVE_COMMISSION_CATEGORY;
			else if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_SEARCH_COMMISSION))
				actionType = COMMISSION_SEARCH_COMMISSION;
			else if(action.equals(CommissionInterfaceKey.ACTION_SEARCH_PAYMENT))
				actionType = SEARCH_PAYMENT;
			else if(action.equals(CommissionInterfaceKey.ACTION_SEARCH_COMMISSION))
				actionType = SEARCH_COMMISSION;
			else if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_CREATE_NEW_COMMISSION))
				actionType = COMMISSION_CREATE_NEW_COMMISSION;
			else if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_CREATE_NEW_PAYMENT))
				actionType = COMMISSION_CREATE_NEW_PAYMENT;
			else if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_CREATE_NEW_COMMISSION_CATEGORY))
				actionType = COMMISSION_CREATE_NEW_COMMISSION_CATEGORY;
			else if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_MANAGE_CATEGORY))
				actionType = COMMISSION_MANAGE_CATEGORY;
			else if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_EDIT_COMMISSION_CATEGORY))
				actionType = COMMISSION_EDIT_COMMISSION_CATEGORY;
			else if(action.equals(CommissionInterfaceKey.ACTION_VIEW_PREPARING_COMMISSION))
				actionType = VIEW_PREPARING_COMMISSION;
			else if(action.equals(CommissionInterfaceKey.ACTION_VIEW_READY_COMMISSION))
				actionType = VIEW_READY_COMMISSION;
			else if(action.equals(CommissionInterfaceKey.ACTION_VIEW_CLOSED_COMMISSION))
				actionType = VIEW_CLOSED_COMMISSION;
			else if(action.equals(CommissionInterfaceKey.ACTION_DELETE_COMMISSION))
				actionType = DELETE_COMMISSION;
			else if(action.equals(CommissionInterfaceKey.ACTION_UPDATE_COMMISSION_STATUS))
				actionType = UPDATE_COMMISSION_STATUS;
			else if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_FACTORS))
				actionType = COMMISSION_FACTORS;
			else if(action.equals(CommissionInterfaceKey.ACTION_SAVE_COMMISSION_FACTORS))
				actionType = SAVE_COMMISSION_FACTORS;
			else if(action.equals(CommissionInterfaceKey.ACTION_EXPORT_COMMISSION_TO_EXCEL))
				actionType = EXPORT_COMMISSION_TO_EXCEL;
			else if(action.equals(CommissionInterfaceKey.ACTION_VIEW_COMMISSION))
				actionType = VIEW_COMMISSION;
			else if(action.equals(CommissionInterfaceKey.ACTION_UPLOAD_FILES))      
				actionType = UPLOAD_FILES;
			else if(action.equals(CommissionInterfaceKey.ACTION_VIEW_DCM_PAYMENTS))
				actionType = VIEW_DCM_PAYMENTS;
			else if(action.equals(CommissionInterfaceKey.ACTION_SEARCH_DCM_PAYMENTS))
				actionType = SEARCH_DCM_PAYMENTS;
			else if(action.equals(CommissionInterfaceKey.ACTION_VIEW_COMMISSION_LABELS))
				actionType = VIEW_COMMISSION_LABELS;
			else if(action.equals(CommissionInterfaceKey.ACTION_SHOW_LABEL_DATA))
				actionType = SHOW_LABEL_DATA;
			else if(action.equals(CommissionInterfaceKey.ACTION_ADD_NEW_LABEL))
				actionType = ADD_NEW_LABEL;
			else if(action.equals(CommissionInterfaceKey.ACTION_UPDATE_LABEL_DATA))
				actionType = UPDATE_LABEL_DATA;
			else if(action.equals(CommissionInterfaceKey.ACTION_SAVE_LABEL_DATA))
				actionType = SAVE_LABEL_DATA;
			else if(action.equals(CommissionInterfaceKey.ACTION_VIEW_LABEL_DETAILS_DATA))
				actionType = VIEW_LABEL_DETAILS_DATA;
			else if(action.equals(CommissionInterfaceKey.ACTION_EXPORT_LABEL_DETAILS_DATA))
				actionType = EXPORT_LABEL_DETAILS_DATA;
			else if (action.equals(CommissionInterfaceKey.ACTION_DELETE_UPLOAD_FILE))
				actionType = DELETE_UPLOAD_FILE;
			else if (action.equals(CommissionInterfaceKey.ACTION_DELETE_UPLOAD_FILE_PROCESS))
				actionType = DELETE_UPLOAD_FILE_PROCESS;
			else if(action.equals(CommissionInterfaceKey.ACTION_SAVE_LABEL_DETAILS_DATA))
				actionType = SAVE_LABEL_DETAILS_DATA;
			else if(action.equals(CommissionInterfaceKey.ACTION_DELETE_ALL_LABEL_DETAILS))
				actionType = DELETE_ALL_LABEL_DETAILS;
			else if(action.equals(CommissionInterfaceKey.ACTION_UPLOAD_COMMISSION_DATA))
				actionType = UPLOAD_COMMISSION_DATA;
			else if(action.equals(CommissionInterfaceKey.ACTION_UPLOAD_COMMISSION_DATA_PROCESS))
				actionType = UPLOAD_COMMISSION_DATA_PROCESS;
			else if(action.equals(CommissionInterfaceKey.ACTION_VIEW_DRIVING_PLAN_COMMISSION_DETAIL))
				actionType = action_view_driving_plan_commission_detail;
			else if(action.equals(POSTargetInterface.ACTION_UPLOAD_POS_TARGET))                        
                            actionType=ACTION_UPLOAD_POS_TARGET;
			else if(action.equals(POSTargetInterface.ACTION_MANAGEMENT_POS_TARGET))                        
                            actionType=ACTION_MANAGEMENT_POS_TARGET;
			else if(action.equals(POSTargetInterface.ACTION_DELETE_FILE_POS_TARGET))                        
                            actionType=ACTION_DELETE_FILE_POS_TARGET;
                        
			if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_SAVE_NEW_COMMISSION_NO_PARAM))
				actionType = COMMISSION_SAVE_NEW_COMMISSION_NO_PARAM;
			if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_SAVE_NEW_COMMISSION_PARAM))
				actionType = COMMISSION_SAVE_NEW_COMMISSION_PARAM;
			if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_VIEW_CHANNELS))
				actionType = COMMISSION_VIEW_CHANNELS;
			if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_EDIT_CHANNEL))
				actionType = COMMISSION_EDIT_CHANNEL;
			if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_NEW_CHANNEL))
				actionType = COMMISSION_NEW_CHANNEL;
			if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_SAVE_CHANNELS))
				actionType = COMMISSION_SAVE_CHANNELS;
			if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_USER_CHANNELS))
				actionType = COMMISSION_USER_CHANNELS;
			if(action.equals(CommissionInterfaceKey.ACTION_COMMISSION_ASSIGN_CHANNEL_TO_USER))
				actionType = COMMISSION_ASSIGN_CHANNEL_TO_USER;

                        if(action.equals(CommissionInterfaceKey.ACTION_UPLOAD_RATED_ZIP_FILE))
				actionType = action_upload_rated_zip_file;

                        if(action.equals(CommissionInterfaceKey.ACTION_DELETE_RATED_ACTIVITY ))
                            actionType=action_delete_rated_activity;

                        if(action.equals(CommissionInterfaceKey.DELETE_UPLOAD_RATED_ACTIVITY))
                            actionType=delete_upload_rated_activity;

                        if(action.equals(CommissionInterfaceKey.ACTION_VIEW_RATED_ACTIVITY))
                            actionType=action_view_rated_activity;
                        

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        if (strUserID != null && strUserID.compareTo("") != 0) {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        }
                        else
                        {
                        strUserID = getUserId(strUserID, paramHashMap);
                        }
			dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID); 
                        
			switch(actionType)
			{
                       case ACTION_MANAGEMENT_POS_TARGET:
                       {                                
                           POSTargetDao dao = new POSTargetDao( con); 
                           String binPath = new File("sds/pos_target_files").getAbsolutePath();
                           new Copy().copyDirectory(new File(binPath),new File(getTempFolderPath(paramHashMap)));
                           dataHashMap.put(POSTargetInterface.VECTOR_OF_FILES, dao.getAllPosFiles());
                       }
                       break;   
                            case ACTION_DELETE_FILE_POS_TARGET:
                            {   
                                String file_id=(String)paramHashMap.get(POSTargetInterface.CONTROL_HIDDEN_FILE_ID_UPLOAD);
                                POSTargetDao dao = new POSTargetDao( con); 
                                dao.deletePosFileTarget(file_id);
                                String binPath = new File("sds/pos_target_files").getAbsolutePath();
                                File failedFile = new File (binPath+System.getProperty("file.separator") +file_id+"_"+POSTargetInterface.FAILD_FILE_NAME+".xlsx");
                                failedFile.delete();
                                File successFile = new File (binPath+System.getProperty("file.separator")+file_id+"_"+POSTargetInterface.SUCCESS_FILE_NAME+".xlsx");
                                successFile.delete();                                
                                dataHashMap.put(POSTargetInterface.VECTOR_OF_FILES, dao.getAllPosFiles());
                            }
                       break;   
                         case ACTION_UPLOAD_POS_TARGET:
                            {
                                String typeId = (String)paramHashMap.get(POSTargetInterface.CONTROL_SELECT_PERIOD_TYPE_NAME),
                                        yearId=(String)paramHashMap.get(POSTargetInterface.CONTROL_SELECT_YEAR_NAME),
                                        dateTypeId=(String)paramHashMap.get(POSTargetInterface.CONTROL_SELECT_DATE_TYPE_NAME);
                                POSTargetDao dao = new POSTargetDao(typeId, yearId, dateTypeId, strUserID, con);
                                File excelFile = GetUploadedFile.getFile(paramHashMap, POSTargetInterface.COMMISSION_UPLOAD_DIR);                                
                                dao.truncateTempTable();
                                DataImportEngine die = new DataImportEngine();
                                DataImportReport report = die.ImportFileSheetWithRowNumber(excelFile.getAbsolutePath(), AdministrationInterfaceKey.DATA_IMPORT_INSERT, "59", strUserID,2);
                                Vector<ErrorInfo> errorReportVec = report.getReport();
                                long file_id = dao.beginValidateAndInsertDCMCodes();
                                ExcelGeneratorReport excelGen = new ExcelGeneratorReport(getTempFolderPath(paramHashMap), errorReportVec, file_id, dao);
                                String [] filepaths = excelGen.generateReports();
                                String binPath = new File("sds/pos_target_files").getAbsolutePath();
                                new Copy().copyDirectory(new File(excelGen.getFolderPath()), new File(binPath));
                                dataHashMap.put(POSTargetInterface.ARRAY_OF_EXCEL_FILE_PATHS, filepaths);

                       }
                       break;   
			case VIEW_COMMISSION_LABELS:

			{
				Vector labelVec = CommissionDAO.getAllLabels(con);

				dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,labelVec);
			}
			break;

			case SHOW_LABEL_DATA:
			{
				String labelId = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID,labelId);
				LabelModel labelModelData = (LabelModel)CommissionDAO.selectLabelData(con,labelId);
				dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,labelModelData);
			}
			break;

			case ADD_NEW_LABEL:

				break;

			case UPDATE_LABEL_DATA:
			{
				String labelName = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_TEXT_LABEL_NAME);
				
				String labelDescription = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_TEXT_LABEL_DESCRIPTION);
				
				String labelId = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
				
				CommissionDAO.updateLabelData(con,labelId,labelName,labelDescription);
				Vector labelVec = CommissionDAO.getAllLabels(con);
				dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,labelVec);
			}
			break;

			case SAVE_LABEL_DATA:
			{
				Long labelId = Utility.getSequenceNextVal(con,"SEQ_COMMISSION_LABEL_ID");
				String labelName = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_TEXT_LABEL_NAME);
				String labelDescription = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_TEXT_LABEL_DESCRIPTION);
				CommissionDAO.insertNewLabel(con,labelId,labelName,labelDescription);
				Vector labelVec = CommissionDAO.getAllLabels(con);
				dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,labelVec);
			}
			break;

			case VIEW_LABEL_DETAILS_DATA:

			{
				String labelId = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);

				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID,labelId);
				Vector labelDetailsVec = CommissionDAO.getAllLabelsDetails(con,labelId);
				dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,labelDetailsVec);
			}
			break;

			case EXPORT_LABEL_DETAILS_DATA:
			{String labelId = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
			dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID,labelId);
			Vector labelDetailsVec = CommissionDAO.getAllLabelsDetails(con,labelId);
			dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,labelDetailsVec);
			}
			break;

			case DELETE_UPLOAD_FILE:
			{String labelId = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
			dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID,labelId);
			Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("25");
			dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR  , tableDefVector);
			}
			break;

			case DELETE_UPLOAD_FILE_PROCESS:
			{String labelId = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
			dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID,labelId);
			}
			break;


			case DELETE_ALL_LABEL_DETAILS:
			{	
				String labelId = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID,labelId);
				CommissionDAO.deleteAllLabelDetails(con, labelId);
				Vector labelVec = CommissionDAO.getAllLabels(con);
				dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,labelVec);
			}
			break;

			case SAVE_LABEL_DETAILS_DATA:
			{	
				String labelId = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
				
				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID,labelId);
				int labelsDetailsCount = Integer.parseInt((String)paramHashMap.get("labels__count"));
				
				LabelModel labelModel = null;
				String label_details_state = "";
				//Vector labelDetailsVec = new Vector();
				for(int i = 1; i<=labelsDetailsCount ; i++)
				{
					labelModel = new LabelModel();
					String dcmCode = (String)paramHashMap.get("labels__R"+i+"__C1");
					String amount = (String)paramHashMap.get("labels__R"+i+"__C2");
					if(dcmCode.compareTo("")==0)
					{ 
						dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"DCM Code must not be empty.");
					}
					else if(amount.compareTo("")==0)
					{
						dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Amount must not be empty.");
					}
					else
					{
						
						labelModel.setDcmCode(dcmCode);
						labelModel.setAmount(amount);
						String label_details_id = (String)paramHashMap.get("labels__R"+i+"__C3");

						labelModel.setLabelDetailId((String)paramHashMap.get("labels__R"+i+"__C3"));
						
						label_details_state = (String)paramHashMap.get("labels__R"+i+"__C4");
						
						if(label_details_state.equals("new"))
						{
							CommissionDAO.insertLabelDetails(con,labelId,labelModel);
							dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Inserted Successfully.");
						}
						else
						{
							CommissionDAO.updateLabelDetails(con,labelId,labelModel);
							dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Updated Successfully");
						}
					}
				}
				Vector labelDetailsVec = CommissionDAO.getAllLabelsDetails(con,labelId);
				dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,labelDetailsVec);
			}
			break;

			case UPLOAD_COMMISSION_DATA:
			{
				String labelId = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID,labelId);
				Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("10");
				dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR  , tableDefVector);
			}
			break;

			case UPLOAD_COMMISSION_DATA_PROCESS:
			{
				String labelId = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID,labelId);
			}
			break;

			case VIEW_DCM_PAYMENTS:
			{	
				GenericModel paymentStatusModel = GenericModelDAO.getColumns(con,"PAYMENT_STATUS_TYPE");
				Vector paymentStatusVector = GenericModelDAO.getModels(con,paymentStatusModel);
				dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_STATUS , paymentStatusVector);
				Vector searchResults = new Vector();
				dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT ,searchResults);
			}
			break;
			case SEARCH_DCM_PAYMENTS:
			{	GenericModel paymentStatusModel = GenericModelDAO.getColumns(con,"PAYMENT_STATUS_TYPE");
			Vector paymentStatusVector = GenericModelDAO.getModels(con,paymentStatusModel);
			dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_STATUS , paymentStatusVector);

			String strDcmCode = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_TEXT_DCM_CODE);
			dataHashMap.put(CommissionInterfaceKey.INPUT_TEXT_DCM_CODE,strDcmCode);
			String strPaymentStatus = (String)paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_STATUS);
			
			dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_STATUS,strPaymentStatus);
			String strPaymentStartDateFrom = (String)paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_FROM);
			dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_FROM,strPaymentStartDateFrom);
			String strPaymentStartDateTo = (String)paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_TO);
			dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_TO,strPaymentStartDateTo);
			String strPaymentEndDateFrom = (String)paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_FROM);
			dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_FROM,strPaymentEndDateFrom);
			String strPaymentEndDateTo = (String)paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_TO);
			dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_TO,strPaymentEndDateTo);
			Vector searchResults = CommissionDAO.getPaymentForDcm(con,strDcmCode,strPaymentStatus,strPaymentStartDateFrom,strPaymentStartDateTo,strPaymentEndDateFrom,strPaymentEndDateTo);
			dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT ,searchResults);
			HashMap commissionDetails = new HashMap();
			for(int i = 0 ; i < searchResults.size() ; i++)
			{   
				PaymentModel paymentModel = (PaymentModel)searchResults.get(i);
				int paymentId = paymentModel.getPaymentID();
				
				commissionDetails.put(paymentId+"",CommissionDAO.getCommissionsForDcm(con,strDcmCode,paymentId));

				//dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,commissionDetails);
			}
			
			dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,commissionDetails);
			}
			break; 
			case ACTION_COMMISSION_TEST:
			{
			}
			break;
			case COMMISSION_SAVE_NEW_COMMISSION:
			{

				String commissionCategory  = "";
				String commissionType      = "";
				String commissionDataView  = "";
				String dataViewType = "";
				//            String commissionName      = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_NAME);
				String commissionBase      = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_BASE);


				if(commissionBase.equals("0"))
				{
					commissionCategory  = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY);
					commissionType      = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_TYPE);
					commissionDataView  = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DATA_VIEW);
					dataViewType        = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DATA_VIEW_TYPE);               
				}
				else
				{
					CommissionModel commissionModel = CommissionDAO.getCommissionByID(con, commissionBase);
					commissionCategory  = String.valueOf(commissionModel.getCommissionTypeCtageoryID());
					commissionDataView  = String.valueOf(commissionModel.getCommissionDataViewID());
					dataViewType = commissionModel.getCommissionDataViewTypeName();


				}
				String commissionDesc      = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DESCRIPTION);
				String commissionEndDate   = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_END_DATE);
				String commissionStartDate = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_START_DATE);
				String commissionChannel   = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL);
				String commissionSubtractID= (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_SUBTRACT_ID);
				String drivingPlanId= (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DP);

				String commissionName = "";
				String year = commissionStartDate.substring(commissionStartDate.length()-2,commissionStartDate.length());
				String temCommissionStartDate = commissionStartDate.substring(0,commissionStartDate.length()-4)+year;
				temCommissionStartDate = temCommissionStartDate.replaceAll("/", "");

				year = commissionEndDate.substring(commissionEndDate.length()-2,commissionEndDate.length());
				String temCommissionEndDate = commissionEndDate.substring(0,commissionEndDate.length()-4)+year;
				temCommissionEndDate = temCommissionEndDate.replaceAll("/", "");

				String commissionCategoryName =  CommissionDAO.getCommissionCategoryName(con,commissionCategory);
				commissionCategoryName = commissionCategoryName.replaceAll(" ", "_");
				commissionName = CommissionDAO.getChannelName(con,commissionChannel)+"_"+commissionCategoryName+"_"+temCommissionStartDate+"_"+temCommissionEndDate+"_";




				int commissionID = CommissionDAO.addNewCommission(con,commissionName,commissionStartDate,commissionEndDate,
						commissionDesc,commissionCategory,strUserID,commissionDataView,dataViewType,commissionBase,commissionChannel,commissionSubtractID,drivingPlanId);

				CommissionModel commissionModel = CommissionDAO.getCommissionByID(con,commissionID+"");
				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID , commissionID+"");
				dataHashMap.put(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_DATA_VIEW_TYPE,dataViewType);
				if(CommissionDAO.hasParameters(con,commissionDataView)== true)
				{
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, CommissionDAO.getDataViewParameters(con,Integer.parseInt(commissionDataView)));
					dataHashMap.put(CommissionInterfaceKey.COMMISSION_DATA_VIEW_PARAMETER , "PARAM");
				}          
				GenericModel commissionTypeModel = GenericModelDAO.getColumns(con,"COMMISSION_TYPE");
				Vector commissionTypes = GenericModelDAO.getModels(con,commissionTypeModel);
				dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_TYPE , commissionTypes);

				GenericModel commissionTypeCategoryModel = GenericModelDAO.getColumns(con,"COMMISSION_TYPE_CATEGORY");
				Vector commissionTypeCategories = GenericModelDAO.getModels(con ,commissionTypeCategoryModel,"COMMISSION_TYPE_CATEGORY_NAME");
				dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_TYPE_CATEGORY , commissionTypeCategories);

				GenericModel commissionStatusModel = GenericModelDAO.getColumns(con,"COMMISSION_STATUS_TYPE");
				Vector commissionStatusVector = GenericModelDAO.getModels(con ,commissionStatusModel);
				dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_STATUS , commissionStatusVector);
				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ACTION ,
						(String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ACTION));

				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_STATUS ,
						(String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_STATUS));
				
				Vector comChannelVec = GenericModelDAO.getCommissionChannel(con, strUserID);
				dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,comChannelVec);

			}
			break;
			case COMMISSION_SEARCH_COMMISSION:
			{

				String incomingAction = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);            
				String commissionId            = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_ID);

				String commissionName          = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_NAME);
				String commissionStatus        = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_STATUS);
				String commissionChannel       = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL);
				String commissionType          = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_TYPE);            
				String commissionTypeCategory  = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY);
				String commissionStartDateFrom = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_START_DATE_FROM);
				String commissionStartDateTo   = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_START_DATE_TO);            
				String commissionEndDateFrom   = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_END_DATE_FROM);
				String commissionEndDateTo     = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_END_DATE_TO  );            
				
				Vector searchResults = CommissionDAO.searchCommissionByFilter(con,commissionId,commissionName,commissionStatus,commissionType,
						commissionTypeCategory,commissionStartDateFrom,
						commissionStartDateTo,commissionEndDateFrom,commissionEndDateTo,commissionChannel,strUserID);

				
				
				Vector usersDeletePerVector = UserDAO.getUserPaymentPermission(con);
	            dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_USER_VECTOR, usersDeletePerVector);
	            
	            
				for (int i=0;i<searchResults.size();i++){
					CommissionModel cm = ((CommissionModel)searchResults.elementAt(i));
					int commissionID =cm.getCommissionID();
					cm.setCommissionHasPayment(CommissionDAO.getCommissionPayments(con,commissionID));
					searchResults.set(i,cm);
				}

				dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT , searchResults);                                                                           




				prepareCommissionSearchScreenLookups(dataHashMap, strUserID,con);

				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ACTION ,
						(String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ACTION));

				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_STATUS ,
						(String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_STATUS));




				dataHashMap.put(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_ID,commissionId);
				dataHashMap.put(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_NAME,commissionName);
				dataHashMap.put(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_STATUS,commissionStatus);
				dataHashMap.put(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_TYPE,commissionType);            
				dataHashMap.put(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY,commissionTypeCategory);
				dataHashMap.put(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_START_DATE_FROM,commissionStartDateFrom);
				dataHashMap.put(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_START_DATE_TO,commissionStartDateTo);            
				dataHashMap.put(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_END_DATE_FROM,commissionEndDateFrom);
				dataHashMap.put(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_END_DATE_TO,commissionEndDateTo);   
				dataHashMap.put(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL, commissionChannel);
				
				//System.out.println("commissionChannel isssssssssssssssss "+commissionChannel);
			}
			break;
			case COMMISSION_EDIT_COMMISSION:
			{
				String commissionID = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID);
				CommissionModel commissionModel = CommissionDAO.getCommissionByID(con,commissionID);
				dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT,commissionModel);
			}
			break;
			case COMMISSION_ADD_NEW_COMMISSION:
			{

			}
			break;
			case COMMISSION_SAVE_COMMISSION_CATEGORY:
			{


				String commissionType = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_TYPE_ID);
				String commissionCategory = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_CATEGORY_ID);
				            
				String commissionTypeCategoryName = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY_NAME);
				String commissionTypeCategoryDesc = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY_DESC);
				if(commissionCategory.equals(""))
					CommissionDAO.addNewCommissionCategory(con,commissionType , commissionTypeCategoryName , commissionTypeCategoryDesc);
				else
					CommissionDAO.updateCommissionCategory(con,commissionTypeCategoryName,commissionTypeCategoryDesc,commissionCategory);

				GenericModel commissionTypeModel = GenericModelDAO.getColumns(con,"COMMISSION_TYPE");
				Vector commissionTypes = GenericModelDAO.getModels(con,commissionTypeModel);
				dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_TYPE , commissionTypes);

				GenericModel commissionTypeCategoryModel = GenericModelDAO.getColumns(con,"COMMISSION_TYPE_CATEGORY");
				Vector commissionTypeCategories = GenericModelDAO.getModels(con ,commissionTypeCategoryModel,"COMMISSION_TYPE_CATEGORY_NAME");
				dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_TYPE_CATEGORY , commissionTypeCategories);

			}
			break;
			case COMMISSION_SEARCH_PAYMENT:
			{

			}
			break;
			case COMMISSION_EDIT_PAYMENT:
			{

			}
			break;
			case COMMISSION_ADD_NEW_PAYMENT:
			{
			}
			break;
			case COMMISSION_VIEW_PAYMENT:
			{
			}
			break;
			case COMMISSION_EXPORT_PAYMENT:
			{
			}
			break;
			case COMMISSION_CREATE_NEW_COMMISSION     :
			{


				GenericModel commissionTypeModel = GenericModelDAO.getColumns(con,"COMMISSION_TYPE");
				Vector commissionTypes = GenericModelDAO.getModels(con,commissionTypeModel);
				dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_TYPE , commissionTypes);

				GenericModel commissionTypeCategoryModel = GenericModelDAO.getColumns(con,"COMMISSION_TYPE_CATEGORY");
				Vector commissionTypeCategories = GenericModelDAO.getModels(con ,commissionTypeCategoryModel,"COMMISSION_TYPE_CATEGORY_NAME");
				dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_TYPE_CATEGORY , commissionTypeCategories);

				QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;
				Vector vecDataViewsList = queryBuilderEngine.listDataViews ( con,QueryBuilderInterfaceKey.UNIVERSE_TYPE_AD_HOC_REPORTS );
				dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecDataViewsList);
				Vector comChannelVec = GenericModelDAO.getCommissionChannel(con, strUserID);
				dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,comChannelVec);
				Vector commissionVector =new Vector();
				commissionVector = CommissionDAO.searchCommissionByFilter(con,"","","0","0","0","*","*","*","*","0",strUserID);
				dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT , commissionVector);
				//Utility.closeConnection (con);



			}
			break;
			case COMMISSION_CREATE_NEW_PAYMENT:
			{

			}
			break;
			case SEARCH_PAYMENT:
			{


				GenericModel paymentStatusModel = GenericModelDAO.getColumns(con,"PAYMENT_STATUS_TYPE");
				Vector paymentStatus = GenericModelDAO.getModels(con,paymentStatusModel);
				dataHashMap.put(CommissionInterfaceKey.VECTOR_PAYMENT_STATUS , paymentStatus);
			}
			break;


			case SEARCH_COMMISSION:
			{                                  
				prepareCommissionSearchScreenLookups(dataHashMap, strUserID,con);
				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ACTION,CommissionInterfaceKey.INPUT_HIDDEN_ALL_COMMISSION);
				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_STATUS,"0");                       
			}
			break;
			case COMMISSION_CREATE_NEW_COMMISSION_CATEGORY:
			{

				String strTypeID = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_TYPE_ID);
				String strCategoryID = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_CATEGORY_ID);

				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_TYPE_ID,strTypeID);
				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_CATEGORY_ID,strCategoryID);


			}
			break;
			case COMMISSION_MANAGE_CATEGORY:
			{

				GenericModel commissionTypeModel = GenericModelDAO.getColumns(con,"COMMISSION_TYPE");
				Vector commissionTypes = GenericModelDAO.getModels(con,commissionTypeModel);
				dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_TYPE , commissionTypes);

				GenericModel commissionTypeCategoryModel = GenericModelDAO.getColumns(con,"COMMISSION_TYPE_CATEGORY");
				Vector commissionTypeCategories = GenericModelDAO.getModels(con ,commissionTypeCategoryModel,"COMMISSION_TYPE_CATEGORY_NAME");
				dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_TYPE_CATEGORY , commissionTypeCategories);

			}
			break;
			case COMMISSION_EDIT_COMMISSION_CATEGORY:
			{

				String commissionType = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_TYPE_ID);
				String commissionCategory = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_CATEGORY_ID);

				String categoryID = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_CATEGORY_ID);
				GenericModel categoryModel = GenericModelDAO.getModelByID(con ,categoryID , "COMMISSION_TYPE_CATEGORY" );
				dataHashMap.put(CommissionInterfaceKey.MODEL_EDIT_CATEGORY_MODEL , categoryModel);
				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_TYPE_ID,commissionType);
				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_CATEGORY_ID,commissionCategory);


			}
			break;
			case VIEW_PREPARING_COMMISSION:
			{
				dataHashMap = viewPrepCommission(strUserID,con,paramHashMap,dataHashMap);
			}
			break;
			case VIEW_READY_COMMISSION:
			{
				dataHashMap = viewReadyCommission(strUserID,con,paramHashMap,dataHashMap);          
			}
			break;        
			case VIEW_CLOSED_COMMISSION:
			{      
				dataHashMap = viewClosedCommission(strUserID,con,paramHashMap,dataHashMap);
			}
			break;
			case DELETE_COMMISSION:
			{
				String commissionID = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID);                            
				CommissionDAO.updateCommissionStatus(con,commissionID,"5",strUserID);

				String commissionId            = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_ID);
				String commissionName          = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_NAME);
				String commissionStatus        = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_STATUS);
				String commissionType          = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_TYPE);            
				String commissionTypeCategory  = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY);
				String commissionStartDateFrom = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_START_DATE_FROM);
				String commissionStartDateTo   = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_START_DATE_TO);            
				String commissionEndDateFrom   = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_END_DATE_FROM);
				String commissionEndDateTo     = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_END_DATE_TO  );
				String commissionChannel       = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL);
				Vector searchResults = CommissionDAO.searchCommissionByFilter(con,commissionId,commissionName,commissionStatus,commissionType,
						commissionTypeCategory,commissionStartDateFrom,
						commissionStartDateTo,commissionEndDateFrom,commissionEndDateTo,commissionChannel,strUserID);
				for (int i=0;i<searchResults.size();i++){
					CommissionModel cm = ((CommissionModel)searchResults.elementAt(i));
					int IntCommissionID =cm.getCommissionID();
					cm.setCommissionHasPayment(CommissionDAO.getCommissionPayments(con,IntCommissionID));
					searchResults.set(i,cm);
				}                                                                           
				dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT , searchResults);                                                                           
				prepareCommissionSearchScreenLookups(dataHashMap, strUserID,con);
				String incomingStatus = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_STATUS);
				if (incomingStatus.compareTo("2")==0)
				{            	
					dataHashMap = viewReadyCommission(strUserID,con,paramHashMap,dataHashMap);
				}
				if (incomingStatus.compareTo("3")==0)
				{            	
					dataHashMap = viewClosedCommission(strUserID,con,paramHashMap,dataHashMap);
				}

			}

			break;
			case UPDATE_COMMISSION_STATUS:
			{


				String commissionID = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID);

				String incomingStatus = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_STATUS);



				if(((String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ACTION)).
						equals(CommissionInterfaceKey.INPUT_HIDDEN_PAY_COMMISSION))
					CommissionDAO.updateCommissionStatus(con,commissionID,"4",strUserID);
				else if(((String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ACTION)).
						equals(CommissionInterfaceKey.INPUT_HIDDEN_CLOSE_COMMISSION))
					CommissionDAO.updateCommissionStatus(con,commissionID,"3",strUserID);
				else if(((String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ACTION)).
						equals(CommissionInterfaceKey.INPUT_HIDDEN_OPEN_COMMISSION))
					CommissionDAO.updateCommissionStatus(con,commissionID,"2",strUserID);      

				String commissionId            = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_ID);
				String commissionName          = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_NAME);
				String commissionStatus        = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_STATUS);
				String commissionType          = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_TYPE);            
				String commissionTypeCategory  = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY);
				String commissionStartDateFrom = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_START_DATE_FROM);
				String commissionStartDateTo   = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_START_DATE_TO);            
				String commissionEndDateFrom   = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_END_DATE_FROM);
				String commissionEndDateTo     = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_END_DATE_TO  );
				String commissionChannel       = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL);
				Vector searchResults = CommissionDAO.searchCommissionByFilter(con,commissionId,commissionName,commissionStatus,commissionType,
						commissionTypeCategory,commissionStartDateFrom,
						commissionStartDateTo,commissionEndDateFrom,commissionEndDateTo,commissionChannel,strUserID);
				for (int i=0;i<searchResults.size();i++){
					CommissionModel cm = ((CommissionModel)searchResults.elementAt(i));
					int intCommissionID =cm.getCommissionID();
					cm.setCommissionHasPayment(CommissionDAO.getCommissionPayments(con,intCommissionID));
					searchResults.set(i,cm);
				}
				dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT , searchResults);                                                                           

				prepareCommissionSearchScreenLookups(dataHashMap, strUserID,con);

				if (incomingStatus.compareTo("2")==0)
				{            	
					dataHashMap = viewReadyCommission(strUserID,con,paramHashMap,dataHashMap);
				}
				if (incomingStatus.compareTo("3")==0)
				{            	
					dataHashMap = viewClosedCommission(strUserID,con,paramHashMap,dataHashMap);
				}
				if (incomingStatus.compareTo("1")==0)
				{            	
					dataHashMap = viewReadyCommission(strUserID,con,paramHashMap,dataHashMap);
				}

			}
			break;
			case COMMISSION_FACTORS:
			{
				String commissionID = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID);                            
				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID,commissionID);
				CommissionModel commissionModel = CommissionDAO.getCommissionByID(con,commissionID);
				String CommissionType = String.valueOf(commissionModel.getCommissionTypeID());
				Vector commissionFactors = FactorDAO.getCommissionFactorValues(con,commissionID);
				Vector<FactorModel> commissionFactorsNotInDP = FactorDAO.getCommissionFactorNotInDrivingPlan(con,commissionID);
				dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_FACTORS_NOT_IN_DP , commissionFactorsNotInDP);
				dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_FACTORS , commissionFactors);
				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_TYPE_ID,CommissionType);




			}
			break;
			case SAVE_COMMISSION_FACTORS:
			{                       
				dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
				String commissionID = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID);                            
				//dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID,commissionID);
				String commissionType = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_TYPE_ID);
				//dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_TYPE_ID,commissionType);
				int value = 1;
				if(commissionType.equals("1"))
					value = 1;
				else if(commissionType.equals("2"))
					value = -1;

				for(int k=0; k<paramHashMap.size(); k++)
				{
					String tempStatusString = (String)paramHashMap.keySet().toArray()[k];
					if(tempStatusString.startsWith(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_FACTOR_VALUE))
					{
						String factorID = tempStatusString.substring(24);
						
						String factorValue = (String)paramHashMap.get(tempStatusString);
						factorValue = String.valueOf(Double.parseDouble(factorValue)*value); 
						
						FactorDAO.setFactorValue(con ,factorID,commissionID,factorValue);
					}
				}

				prepareCommissionSearchScreenLookups(dataHashMap, strUserID,con);


				Vector searchResults = CommissionDAO.searchCommissionByFilter(con,"","","2","0","0","*","*","*","*","0",strUserID);
				for (int i=0;i<searchResults.size();i++){
					CommissionModel cm = ((CommissionModel)searchResults.elementAt(i));
					int IntCommissionID =cm.getCommissionID();
					cm.setCommissionHasPayment(CommissionDAO.getCommissionPayments(con,IntCommissionID));
					searchResults.set(i,cm);
				}
				dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT,searchResults);

				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ACTION,
						CommissionInterfaceKey.INPUT_HIDDEN_CLOSE_COMMISSION);

				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_STATUS , "2");  
			}
			break;
			case EXPORT_COMMISSION_TO_EXCEL:
			{


				String commissionIDs = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID);
				Vector commissionFactors =  new Vector();
				commissionFactors.add(FactorDAO.getCommissionFactors(con,commissionIDs));                          
				dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_COMMISSION_FACTOR, commissionFactors);         
			}
			break;
			case VIEW_COMMISSION:
			{


				String commissionIDs = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID);
				Vector commissionFactors =  new Vector();
				commissionFactors.add(FactorDAO.getCommissionFactors(con,commissionIDs));                          
				dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_COMMISSION_FACTOR, commissionFactors);
				Vector comVec = GenericModelDAO.getCommissionChannel(con, strUserID);
				dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,comVec);
			}
			break;
			case UPLOAD_FILES:
			{
				String fileName = (String)paramHashMap.get("file");
				File file = new File(fileName);
				ZipFile zipFile = new ZipFile(file);
				Enumeration enumeration = zipFile.entries();
				while (enumeration.hasMoreElements()) {
					ZipEntry zipEntry = (ZipEntry)enumeration.nextElement();
					String zipFileName = zipEntry.getName();

					InputStream inputStream = zipFile.getInputStream(zipEntry);
					OutputStream out = new FileOutputStream("c:\\x\\" + zipFileName);
					byte[] buf = new byte[1024];
					int len;
					while ((len = inputStream.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					out.close();
					inputStream.close();
				}
			}
			break;
			case COMMISSION_SAVE_NEW_COMMISSION_PARAM:
			{

				prepareCommissionSearchScreenLookups(dataHashMap, strUserID,con);


				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ACTION,CommissionInterfaceKey.INPUT_HIDDEN_ALL_COMMISSION);
				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_STATUS,"0");

				int commissionID = Integer.parseInt((String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID));

				int dataViewID = CommissionDAO.getCommissionByID(con ,commissionID+"").getCommissionDataViewID();
				ReportBuilderWizardDTO reportBuilderWizardDTO = CommissionDAO.getDataViewParameters(con,dataViewID);
				ReportEngine reportEngine =  new ReportEngine();
				Vector paramList = reportEngine.constructReportParamList(paramHashMap);
				String SQLQuery = CommissionDAO.previewReport(con,reportBuilderWizardDTO.getReport() ,paramList ,dataViewID);


				String dataViewType = CommissionDAO.getCommissionByID(con ,commissionID+"").getCommissionDataViewTypeName();

				CommissionDAO.updateCommissionSQLString(con,SQLQuery,commissionID);
				String conStr[] = CommissionDAO.getConnectionString(con);

				Thread commissionThread = new CommissionThread(dataViewType , commissionID , strUserID, conStr);
				commissionThread.start();

				dataHashMap.put(CommissionInterfaceKey.SAVED_COMMISSION_ID,commissionID+"" );

			}
			break;

			case COMMISSION_SAVE_NEW_COMMISSION_NO_PARAM:
			{

				int commissionID = Integer.parseInt((String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID));
				String dataViewType = CommissionDAO.getCommissionByID(con,commissionID+"").getCommissionDataViewTypeName();
				String conStr[] = CommissionDAO.getConnectionString(con);            
				
				Thread commissionThread = new CommissionThread(dataViewType , commissionID ,strUserID ,conStr);
				commissionThread.start();
				

				prepareCommissionSearchScreenLookups(dataHashMap, strUserID,con);

				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ACTION,CommissionInterfaceKey.INPUT_HIDDEN_ALL_COMMISSION);
				dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_STATUS,"0");
				dataHashMap.put(CommissionInterfaceKey.SAVED_COMMISSION_ID,commissionID+"" );

			}
			break;
			case COMMISSION_VIEW_CHANNELS:
			{
				dataHashMap.put(CommissionInterfaceKey.CHANNEL_VECTOR,CommissionDAO.getCommissionChannels(con,false,""));
			}
			break;

			case COMMISSION_EDIT_CHANNEL:
			{
				String channelId = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);        	
				dataHashMap.put(CommissionInterfaceKey.CHANNEL_MODEL,CommissionDAO.getCommissionChannels(con,true,channelId));

			}
			break;


			case COMMISSION_SAVE_CHANNELS:
			{
				String channelId = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
				String channelName= (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_CHANNEL_NAME);
				if (channelId.compareTo("")==0||channelId==null)
				{
					CommissionDAO.insertCommissionChannels(con,channelName);	
				}
				else
				{
					CommissionDAO.updateCommissionChannels(con, channelId, channelName);
				}

				dataHashMap.put(CommissionInterfaceKey.CHANNEL_VECTOR,CommissionDAO.getCommissionChannels(con,false,""));
			}
			break;
			case COMMISSION_NEW_CHANNEL:
			{



			}
			break;
			case COMMISSION_USER_CHANNELS:
			{                      

				dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUsersList(con));            
				dataHashMap.put(CommissionInterfaceKey.CHANNEL_VECTOR,CommissionDAO.getCommissionChannels(con,false,""));
				dataHashMap.put(CommissionInterfaceKey.USER_CHANNEL_VECTOR,CommissionDAO.getUserChannels(con));            

			}
			break;
			case COMMISSION_ASSIGN_CHANNEL_TO_USER:
			{   
				int paramHashMapSize = paramHashMap.size();
				String strPersonID = (String)paramHashMap.get(UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID);

				CommissionDAO.deleteUserChannels(con,strPersonID);                  
				for(int i=0;i<paramHashMapSize;i++) 
				{
					String tempKey = (String) paramHashMap.keySet().toArray()[i];

					if(tempKey.startsWith(UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX)) 
					{
						String channelIdAndPersonIdKey = tempKey.substring(9);

						if (channelIdAndPersonIdKey.startsWith(strPersonID)){

							String channelIdKey = channelIdAndPersonIdKey.substring(strPersonID.length());

							CommissionDAO.insertUserChannels(con,strPersonID,channelIdKey);	
						}                   



					}
				}

				dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUsersList(con));
				dataHashMap.put(CommissionInterfaceKey.CHANNEL_VECTOR,CommissionDAO.getCommissionChannels(con,false,""));
				dataHashMap.put(CommissionInterfaceKey.USER_CHANNEL_VECTOR,CommissionDAO.getUserChannels(con));          

			}
			break;
                        case action_upload_rated_zip_file:{

                                String file_name = (String) paramHashMap.get(InterfaceKey.CONTROL_INPUT_FILE);
                                System.out.println("file_name: " + file_name);
                                file_name = Utility.replaceAll(file_name, "#", "/");
                                String datefrom=(String)paramHashMap.get(CommissionInterfaceKey.RATED_ACTIVITY_DATE_FROM);
                                String monthfrom=(String)paramHashMap.get(CommissionInterfaceKey.RATED_ACTIVITY_MONTH_FROM);
                                String yearfrom=(String)paramHashMap.get(CommissionInterfaceKey.RATED_ACTIVITY_YEAR_FROM);
                                String dateto=(String)paramHashMap.get(CommissionInterfaceKey.RATED_ACTIVITY_DATE_TO);
                                String monthto=(String)paramHashMap.get(CommissionInterfaceKey.RATED_ACTIVITY_MONTH_TO);;
                                String yearto=(String)paramHashMap.get(CommissionInterfaceKey.RATED_ACTIVITY_YEAR_TO);


                                Long attach_id = DBUtil.getSequenceNextVal(con,"COMMISSION_RATED_FILE_ID");
                                String fileId=attach_id.toString();
                                CommissionDAO.insertNewRatedFile(fileId,datefrom,monthfrom,yearfrom,dateto,monthto,yearto,strUserID);

                                CommissionZipThread commThread=new CommissionZipThread(fileId,file_name);
                                commThread.start();
                             
                        }
                                break;




                            case  delete_upload_rated_activity:{

                             Vector <RatedFileModel> filesID=CommissionDAO.getAllRatedFiles(con);
                             dataHashMap.put(CommissionInterfaceKey.VECTOR_RATED_FILES,filesID);
                            }
                            break;

                            case action_delete_rated_activity:
                                String fileId=(String)paramHashMap.get(CommissionInterfaceKey.RATED_FILE_ID);
                                if(CommissionDAO.isfileactive(fileId, con)==0||CommissionDAO.isfileactive(fileId, con)==-1)
                                CommissionDAO.deleteFile(fileId);
                                else
                                CommissionDAO.activefile(fileId);
                               Vector <RatedFileModel> filesID=CommissionDAO.getAllRatedFiles(con);
                             dataHashMap.put(CommissionInterfaceKey.VECTOR_RATED_FILES,filesID);

                            break;


                            case action_view_rated_activity:

                               fileId=(String)paramHashMap.get(CommissionInterfaceKey.RATED_FILE_ID);
                                 Integer passRecords=CommissionDAO.getPassRecordsOfZipFile(fileId, con);
                                Integer faildRecords=CommissionDAO.getFalidRecordsOfZipFile(fileId, con);
                                Vector<RatedFileError> falidSIM=CommissionDAO.getFalidSIMSNOfZipFile(fileId, con);
                                dataHashMap.put(CommissionInterfaceKey.NUMBER_OF_PASS_RECORDS,passRecords.toString());
                                dataHashMap.put(CommissionInterfaceKey.NUMBER_OF_FAILD_RECORDS,faildRecords.toString());
                                dataHashMap.put(CommissionInterfaceKey.VECTOR_OF_FAILD_SIM,falidSIM);
                                dataHashMap.put(CommissionInterfaceKey.RATED_FILE_ID,fileId);
                            break;
                            case action_view_driving_plan_commission_detail:
                            {
                                String commissionID = (String)paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID);                                                           
                                dataHashMap.put(CommissionInterfaceKey.COMMISSION_MODEL,CommissionDAO.getCommissionDetailsForDataViewAndDrivingPlan(con,commissionID));
                            }
                            break;                            

			default:

				Utility.logger.debug ("Unknown action received: " + action ); 

			}

		}
		catch(Exception objExp)
		{


			objExp.printStackTrace();
			throw objExp;
		}
		return dataHashMap;
	}

	public static HashMap viewPrepCommission (String userId,Connection con ,HashMap paramHashMap, HashMap dataHashMap) throws Exception
	{
		prepareCommissionSearchScreenLookups(dataHashMap, userId,con);

		Vector searchResults = CommissionDAO.searchCommissionByFilter(con,"","","1","0","0","*","*","*","*","1",userId);
		for (int i=0;i<searchResults.size();i++){
			CommissionModel cm = ((CommissionModel)searchResults.elementAt(i));
			int commissionID =cm.getCommissionID();
			cm.setCommissionHasPayment(CommissionDAO.getCommissionPayments(con,commissionID));
			searchResults.set(i,cm);
		}
		dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT,searchResults);

		dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ACTION,
				CommissionInterfaceKey.INPUT_HIDDEN_EDIT_COMMISSION);
		dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_STATUS , "1");                                  

		return dataHashMap;


	}

	public static HashMap viewReadyCommission (String userId,Connection con ,HashMap paramHashMap,HashMap dataHashMap) throws Exception
	{
		prepareCommissionSearchScreenLookups(dataHashMap, userId,con);

                // modified by Medhat Amin on 7-7-2011
                
		/*Vector searchResults = CommissionDAO.searchCommissionByFilter(con,"","","2","0","0","*","*","*","*","1",userId);

		for (int i=0;i<searchResults.size();i++){
			CommissionModel cm = ((CommissionModel)searchResults.elementAt(i));
			int commissionID =cm.getCommissionID();
			cm.setCommissionHasPayment(CommissionDAO.getCommissionPayments(con,commissionID));
			searchResults.set(i,cm);
		}*/
                
                Vector searchResults = new Vector();
                
                

		dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT,searchResults);

		dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ACTION,
				CommissionInterfaceKey.INPUT_HIDDEN_CLOSE_COMMISSION);

		dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_STATUS , "2");



		return dataHashMap ;
	}

	public static  HashMap viewClosedCommission (String userId,Connection con ,HashMap paramHashMap,HashMap dataHashMap) throws Exception
	{
		prepareCommissionSearchScreenLookups(dataHashMap, userId,con);

                // modified by Medhat Amin on 7-7-2011  
                
		/*Vector searchResults = CommissionDAO.searchCommissionByFilter(con,"","","3","0","0","*","*","*","*","1",userId);

		for (int i=0;i<searchResults.size();i++){
			CommissionModel cm = ((CommissionModel)searchResults.elementAt(i));
			int commissionID =cm.getCommissionID();
			cm.setCommissionHasPayment(CommissionDAO.getCommissionPayments(con,commissionID));
			//  searchResults.set(i,cm);
		}*/
                
                Vector searchResults = new Vector();
		dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT,searchResults);

		//cast searchResults to get commission id and check if this id has payments

		dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ACTION,
				CommissionInterfaceKey.INPUT_HIDDEN_OPEN_COMMISSION);                        
		dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_STATUS , "3");      
		return dataHashMap;
	}
        private static String getUserId(String userId, HashMap paramHashMap) {
        userId = userId == null ? (String) ((HttpServletRequest) paramHashMap.get(InterfaceKey.HASHMAP_KEY_REQUEST_FROM_SERVLET)).getSession(false).getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID) : userId;
        return userId;
    }
        private static String getTempFolderPath(HashMap paramHashMap){
        HttpServletRequest request = (HttpServletRequest) paramHashMap.get(InterfaceKey.HASHMAP_KEY_REQUEST_FROM_SERVLET);
        return  request.getRealPath("/")+"downloadItems";        
        }

}