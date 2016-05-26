package com.mobinil.sds.core.facade.handlers;

import com.mobinil.sds.core.system.sa.scratch_card.dao.ScratchCardDao;
import com.mobinil.sds.core.system.sa.activation.dao.*;
import com.mobinil.sds.core.system.sa.scratch_card.model.ScratchCardModel;

import com.mobinil.sds.core.system.sa.modules.dao.*;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.cam.*;
import com.mobinil.sds.web.interfaces.sop.SOPInterfaceKey;
import com.mobinil.sds.core.system.cam.accrual.model.*;
import com.mobinil.sds.core.system.cam.accrual.dao.*;
import com.mobinil.sds.core.system.gn.dcm.dao.*;
import com.mobinil.sds.core.system.gn.dcm.model.*;
import com.mobinil.sds.core.system.cam.deduction.model.*;
import com.mobinil.sds.core.system.cam.deduction.dao.*;
import com.mobinil.sds.core.system.commission.dao.*;
import com.mobinil.sds.core.system.commission.model.*;
import com.mobinil.sds.core.system.commission.factor.dao.*;
import com.mobinil.sds.core.system.commission.factor.model.*;
import com.mobinil.sds.core.system.cam.common.model.*;
import com.mobinil.sds.core.system.cam.payment.dao.*;
import com.mobinil.sds.core.system.cam.payment.model.*;
import com.mobinil.sds.core.system.payment.dao.*;
import com.mobinil.sds.core.system.payment.model.*;
import com.mobinil.sds.core.utilities.jaspertest.*;
import com.mobinil.sds.core.system.gn.dcm.dto.*;
import com.mobinil.sds.core.system.cr.bundle.dao.*;
import com.mobinil.sds.core.utilities.Wrapper.*;
import java.sql.*;
import java.util.*;



public class CAMDeductionHandler  {


	private static final int VIEW_IMPORT_DEDUCTION = 0;
	private static final int IMPORT_DEDUCTION = 1;
	private static final int VIEW_DEDUCTION_SEARCH = 2;
	private static final int VIEW_DEDUCTION = 3;
	private static final int EDIT_DEDUCTION = 4;
	private static final int UPDATE_DEDUCTION = 5;
	private static final int DELETE_DEDUCTION = 6;
	private static final int VIEW_REASON_SEARCH = 7;
	private static final int VIEW_REASON = 8;
	private static final int NEW_REASON = 9;
	private static final int ADD_REASON = 10;
	private static final int EDIT_REASON = 11;
	private static final int UPDATE_REASON = 12;
	private static final int UPDATE_REASON_STATUS = 13;
	private static final int UDPATE_DEDUCTION_STATUSES = 14;

	private static final int VIEW_DEDUCTION_SEARCH_FOR_VIEW = 15;
	private static final int VIEW_DEDUCTION_FOR_VIEW = 16;

	 static final int SEARCH_PAYMENT_TYPE_GROUP=17;
	 static final int VIEW_PAYMENT_TYPE_GROUP =18;
	 static final int NEW_PAYMENT_TYPE_GROUP =19;
	 static final int EDIT_PAYMENT_TYPE_GROUP =20;
	 static final int ADD_PAYMENT_TYPE_GROUP =21;
	 static final int UPDATE_PAYMENT_TYPE_GROUP =22;
	 static final int DELETE_PAYMENT_TYPE_GROUP =23;
	 static final int PAYMENT_SUMMERY_REPORT =24;
	 static final int UPDATE_PAYMENT_TYPE_GROUP_STATUSES = 25;

	

	public static HashMap handle(String action, HashMap paramHashMap,java.sql.Connection con) {
		int actionType = 0;
		
		if (action.equals(DeductionInterfaceKey.ACTION_VIEW_IMPORT_DEDUCTION))
			actionType = VIEW_IMPORT_DEDUCTION;
		else if (action.equals(DeductionInterfaceKey.ACTION_IMPORT_DEDUCTION))
			actionType = IMPORT_DEDUCTION;
		else if (action
				.equals(DeductionInterfaceKey.ACTION_VIEW_DEDUCTION_SEARCH))
			actionType = VIEW_DEDUCTION_SEARCH;
		else if (action.equals(DeductionInterfaceKey.ACTION_VIEW_DEDUCTION))
			actionType = VIEW_DEDUCTION;
		else if (action
				.equals(DeductionInterfaceKey.ACTION_VIEW_DEDUCTION_SEARCH_FOR_VIEW))
			actionType = VIEW_DEDUCTION_SEARCH_FOR_VIEW;
		else if (action
				.equals(DeductionInterfaceKey.ACTION_VIEW_DEDUCTION_FOR_VIEW))
			actionType = VIEW_DEDUCTION_FOR_VIEW;
		else if (action.equals(DeductionInterfaceKey.ACTION_EDIT_DEDUCTION))
			actionType = EDIT_DEDUCTION;
		else if (action.equals(DeductionInterfaceKey.ACTION_UPDATE_DEDUCTION))
			actionType = UPDATE_DEDUCTION;
		else if (action.equals(DeductionInterfaceKey.ACTION_DELETE_DEDUCTION))
			actionType = DELETE_DEDUCTION;
		else if (action.equals(DeductionInterfaceKey.ACTION_VIEW_REASON_SEARCH))
			actionType = VIEW_REASON_SEARCH;
		else if (action
				.equals(DeductionInterfaceKey.ACTION_UDPATE_DEDUCTION_STATUSES))
			actionType = UDPATE_DEDUCTION_STATUSES;
		else if (action.equals(DeductionInterfaceKey.ACTION_VIEW_REASON))
			actionType = VIEW_REASON;
		else if (action.equals(DeductionInterfaceKey.ACTION_NEW_REASON))
			actionType = NEW_REASON;
		else if (action.equals(DeductionInterfaceKey.ACTION_ADD_REASON))
			actionType = ADD_REASON;
		else if (action.equals(DeductionInterfaceKey.ACTION_EDIT_REASON))
			actionType = EDIT_REASON;
		else if (action.equals(DeductionInterfaceKey.ACTION_UPDATE_REASON))
			actionType = UPDATE_REASON;
		else if (action
				.equals(DeductionInterfaceKey.ACTION_UPDATE_REASON_STATUS))
			actionType = UPDATE_REASON_STATUS;
		 else
		 if(action.equals(DeductionInterfaceKey.ACTION_PAYMENT_SUMMERY_REPORT))
		 actionType = PAYMENT_SUMMERY_REPORT;

		 else
		 if(action.compareTo(PaymentInterfaceKey.ACTION_SEARCH_PAYMENT_TYPE_GROUP)==0)
		 {
		 actionType=SEARCH_PAYMENT_TYPE_GROUP;
		 }
		 else
		 if(action.compareTo(PaymentInterfaceKey.ACTION_VIEW_PAYMENT_TYPE_GROUP)==0)
		 {
		 actionType=VIEW_PAYMENT_TYPE_GROUP;
		 }
		 else
		 if(action.compareTo(PaymentInterfaceKey.ACTION_NEW_PAYMENT_TYPE_GROUP)==0)
		 {
		 actionType=NEW_PAYMENT_TYPE_GROUP;
		 }
		 else
		 if(action.compareTo(PaymentInterfaceKey.ACTION_EDIT_PAYMENT_TYPE_GROUP)==0)
		 {
		 actionType=EDIT_PAYMENT_TYPE_GROUP;
		 }
		 else
		 if(action.compareTo(PaymentInterfaceKey.ACTION_ADD_PAYMENT_TYPE_GROUP)==0)
		 {
		 actionType=ADD_PAYMENT_TYPE_GROUP;
		 }
		 else
		 if(action.compareTo(PaymentInterfaceKey.ACTION_UPDATE_PAYMENT_TYPE_GROUP)==0)
		 {
		 actionType=UPDATE_PAYMENT_TYPE_GROUP;
		 }
		 else
		 if(action.compareTo(PaymentInterfaceKey.ACTION_DELETE_PAYMENT_TYPE_GROUP)==0)
		 {
		 actionType=DELETE_PAYMENT_TYPE_GROUP;
		 }
		 else if (action.compareTo(PaymentInterfaceKey.ACTION_UPDATE_PAYMENT_TYPE_GROUP_STATUSES)==0)
		 {
			 actionType = UPDATE_PAYMENT_TYPE_GROUP_STATUSES;
		 }
			 

		return handlOperations(paramHashMap,actionType,con);

	}

	private static HashMap handlOperations(HashMap paramHashMap , int actionType, Connection con) {
		HashMap dataHashMap = new HashMap(100);
		String strUserID = (String) paramHashMap
				.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		// int user_id = Integer.parseInt(strUserID);
		int user_id = -1;
		if (strUserID != null && strUserID.compareTo("") != 0) {
			dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
			user_id = Integer.parseInt(strUserID);
		}
		//Connection connection = null;
		
		try {
			System.out.println("inc counter from cam deduction handler");
		//	 con = Utility.getConnection();
			switch (actionType) {

			case VIEW_IMPORT_DEDUCTION: {
				dataHashMap.put(InterfaceKey.ATTACH_ID, ""
						+ DeductionDAO.getImportId(con));
			}
				break;

			case IMPORT_DEDUCTION: {
				String file_name = (String) paramHashMap
						.get(InterfaceKey.CONTROL_INPUT_FILE);
				file_name = Utility.replaceAll(file_name, "#", "\\");
				// Vector all_dcm = //KPIDAO.getAllKPI(con, sub_id,
				// "normal",0,0);
				HashMap imported_deductions = ImportExportWrapper
						.importExcelFile(file_name, 0, 0, 0, 0);
				Vector all_missed_deduction_codes = new Vector(10);
				 
				System.out.println("imported_deductions size = " + imported_deductions.size());
				if (imported_deductions != null&& imported_deductions.size() > 0) 
				{
					Iterator keys_itr = imported_deductions.keySet().iterator();
					
					System.out.println("keys s= "+ imported_deductions.keySet().toArray().toString());
					System.out.println(imported_deductions.keySet().toArray().length);
					while (keys_itr.hasNext()) 
					{
						String key_str = (String) keys_itr.next();
						System.out.println("key = " + key_str);
						DCMModel dcm_model = DCMDao.getDCMModel(con,key_str);
						System.out.println("dcm model ="+ dcm_model);
						
						if (dcm_model != null) {
						    System.out.println("model is not null");
							Vector excel_deduction_vec = (Vector) imported_deductions.get(key_str);// vec is dcm_code,
													// deduction_value,
													// reason_name,
													// de_group_name
							String ded_value_str = (String) excel_deduction_vec.get(1); // value
							String reason_name = (String) excel_deduction_vec.get(2); // reason
							
							System.out.println("value = "+ ded_value_str + "   reason_name="+reason_name);
							
							String group_type = "";
							System.out.println(" excel_deduction_vec size =" + excel_deduction_vec.size());
							
							if (excel_deduction_vec.size() > 3)
								group_type = (String) excel_deduction_vec.get(3);
							
							
							if (ded_value_str != null&& !ded_value_str.equals("")&& reason_name != null&& !reason_name.equals("")) 
							{
								double ded_value = Double.parseDouble(ded_value_str);
								ReasonModel reason_model = DeductionDAO.getReasonByName(con,reason_name);
								if (reason_model != null) {
							
							
								    
									PaymentTypeGroupModel payment_type_group=PaymentTypeDAO.getPayGroupTypeByName(con,group_type);
									
							
									StatusModel status_model = DeductionDAO.getStatusByName(con, "New");
							
									DeductionModel deduction = new DeductionModel();
									deduction.setDcm_id(dcm_model.getDcmId());
							
									deduction.setDeduction_value(ded_value);
									deduction.setReason_id(reason_model.getReason_id());
							
							
									String paymentTypeGroup  = payment_type_group.getGroupName();
							
									deduction.setPayment_group_type_name(paymentTypeGroup);
							
									deduction.setStatus_id(status_model.getStatus_id());
									System.out.println("added deduction ***************");
									long inserted_group_id = DeductionDAO.addDeduction(con, deduction);
							
								}
							}

						} else // missed deduction ot exist in DB
						{
						    System.out.println("model is null");
							all_missed_deduction_codes.add(key_str);
						}
						System.out.println("last line in while loop");
					}
				}
				dataHashMap.put(InterfaceKey.ATTACH_ID, ""
						+ DeductionDAO.getImportId(con));
			}
				break;
			case VIEW_DEDUCTION_SEARCH_FOR_VIEW: {
				dataHashMap.put(DeductionInterfaceKey.VECTOR_DEDUCTION_REASON,
						DeductionDAO.getAllActiveDedReason(con));
				dataHashMap.put(DeductionInterfaceKey.VECTOR_DEDUCTION_STATUS,
						DeductionDAO.getAllOptionalDeductionStatus(con,
								"b"));
				dataHashMap.put(DeductionInterfaceKey.VECTOR_PAY_GROUP_TYPE,
						PaymentTypeDAO.getAllActivePayTypeGroup(con));
				;
				dataHashMap.put(
						DeductionInterfaceKey.ACTION_VIEW_DEDUCTION_FOR_VIEW,
						DeductionInterfaceKey.ACTION_VIEW_DEDUCTION_FOR_VIEW);
			}
				break;
			case VIEW_DEDUCTION_FOR_VIEW: {
				String group_id_search = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_SELECT_SEARCH_PAY_GROUP_TYPE);
				String reason_id_search = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_SELECT_SEARCH_REASON);
				String status_id_search = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_SELECT_SEARCH_STATUS);
				String ded_date_from = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_SEARCH_DATE_FROM);
				String ded_date_to = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_SEARCH_DATE_TO);
				int group_id_s = -1;
				int reaon_id_s = -1;
				int status_id_s = -1;

				if (group_id_search != null && !group_id_search.equals("")) {
					group_id_s = Integer.parseInt(group_id_search);
					PaymentTypeGroupModel group = PaymentTypeDAO.getGroupById(
							con, group_id_s);
					group_id_search = group.getGroupName();
				}
				if (reason_id_search != null && !reason_id_search.equals(""))
					reaon_id_s = Integer.parseInt(reason_id_search);
				if (status_id_search != null && !status_id_search.equals(""))
					status_id_s = Integer.parseInt(status_id_search);

				Vector all_result_deduction = (Vector) DeductionDAO.searchDeduction(con, reaon_id_s,group_id_search, status_id_s, ded_date_from,ded_date_to);

				Vector all_deduction_status = DeductionDAO.getAllOptionalDeductionStatus(con, "b");
				Vector filtred_deductions=null;
			
				if (all_result_deduction != null && all_result_deduction.size() > 0) {
					filtred_deductions = new Vector(all_result_deduction.size());
					
					for (int i = 0; i < all_result_deduction.size(); i++) {
						DeductionModel item = (DeductionModel) all_result_deduction.get(i);						 
						for (int j = 0; j < all_deduction_status.size(); j++) 
						{
							StatusModel status = (StatusModel) all_deduction_status.get(j);
							
							if (status.getStatus_name().equalsIgnoreCase(item.getStatus_name())) {
								filtred_deductions.add(item);
								break;
							}
						}
					}
				}

				dataHashMap.put(DeductionInterfaceKey.HASHMAP_DEDUCTION_MAKER_ACTIONS_DETAILS, getAllDeductionDetails(con, all_result_deduction));
				dataHashMap.put(DeductionInterfaceKey.VECTOR_DEDUCTION,
						filtred_deductions);
				dataHashMap.put(DeductionInterfaceKey.VECTOR_DEDUCTION_REASON,
						DeductionDAO.getAllActiveDedReason(con));
				dataHashMap.put(DeductionInterfaceKey.VECTOR_DEDUCTION_STATUS,
						all_deduction_status);
				dataHashMap.put(DeductionInterfaceKey.VECTOR_PAY_GROUP_TYPE,
						PaymentTypeDAO.getAllActivePayTypeGroup(con));
				dataHashMap.put(
						DeductionInterfaceKey.ACTION_VIEW_DEDUCTION_FOR_VIEW,
						DeductionInterfaceKey.ACTION_VIEW_DEDUCTION_FOR_VIEW);
			}
				break;

			case VIEW_DEDUCTION_SEARCH: {
				dataHashMap.put(DeductionInterfaceKey.VECTOR_DEDUCTION_REASON,
						DeductionDAO.getAllActiveDedReason(con));
				dataHashMap.put(DeductionInterfaceKey.VECTOR_DEDUCTION_STATUS,
						DeductionDAO.getAllOptionalDeductionStatus(con,
								"a"));
				dataHashMap.put(DeductionInterfaceKey.VECTOR_PAY_GROUP_TYPE,
						PaymentTypeDAO.getAllActivePayTypeGroup(con));
				;
			}
				break;
			case VIEW_DEDUCTION: {
				String group_id_search = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_SELECT_SEARCH_PAY_GROUP_TYPE);
				String reason_id_search = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_SELECT_SEARCH_REASON);
				String status_id_search = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_SELECT_SEARCH_STATUS);
				String ded_date_from = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_SEARCH_DATE_FROM);
				String ded_date_to = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_SEARCH_DATE_TO);
				int group_id_s = -1;
				int reaon_id_s = -1;
				int status_id_s = -1;

				if (group_id_search != null && !group_id_search.equals("")) {
					group_id_s = Integer.parseInt(group_id_search);
					PaymentTypeGroupModel group = PaymentTypeDAO.getGroupById(
							con, group_id_s);
					group_id_search = group.getGroupName();
				}
				if (reason_id_search != null && !reason_id_search.equals(""))
					reaon_id_s = Integer.parseInt(reason_id_search);
				if (status_id_search != null && !status_id_search.equals(""))
					status_id_s = Integer.parseInt(status_id_search);

				Vector all_result_deduction = (Vector) DeductionDAO.searchDeduction(con, reaon_id_s,group_id_search, status_id_s, ded_date_from,ded_date_to);

				Vector all_deduction_status = DeductionDAO.getAllOptionalDeductionStatus(con, "a");
				Vector filtred_deductions=null;
				if (all_result_deduction != null && all_result_deduction.size() > 0) {
					filtred_deductions = new Vector(all_result_deduction
							.size());
					for (int i = 0; i < all_result_deduction.size(); i++) {
						DeductionModel item = (DeductionModel) all_result_deduction.get(i);
						 
						for (int j = 0; j < all_deduction_status.size(); j++) {
							StatusModel status = (StatusModel) all_deduction_status.get(j);
							if (status.getStatus_name().equalsIgnoreCase(item.getStatus_name())) {
								filtred_deductions.add(item);
								break;
							}
						}
					}
				}

				dataHashMap.put(DeductionInterfaceKey.HASHMAP_DEDUCTION_MAKER_ACTIONS_DETAILS, getAllDeductionDetails(con, all_result_deduction));
				dataHashMap.put(DeductionInterfaceKey.VECTOR_DEDUCTION,
						filtred_deductions);
				dataHashMap.put(DeductionInterfaceKey.VECTOR_DEDUCTION_REASON,
						DeductionDAO.getAllActiveDedReason(con));
				dataHashMap.put(DeductionInterfaceKey.VECTOR_DEDUCTION_STATUS,all_deduction_status);
				dataHashMap.put(DeductionInterfaceKey.VECTOR_PAY_GROUP_TYPE,
						PaymentTypeDAO.getAllActivePayTypeGroup(con));
			}
				break;
			case EDIT_DEDUCTION: {
				String ded_id_str = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_DEDUCTION_ID);
				
				int ded_id = Integer.parseInt(ded_id_str);
				DeductionModel deduction = DeductionDAO.getDeductionById(
						con, ded_id);

				dataHashMap.put(DeductionInterfaceKey.MODEL_DEDUCTION,
						deduction);
				dataHashMap.put(DeductionInterfaceKey.VECTOR_DEDUCTION_REASON,
						DeductionDAO.getAllActiveDedReason(con));
				dataHashMap.put(DeductionInterfaceKey.VECTOR_DEDUCTION_STATUS,
						DeductionDAO.getAllOptionalDeductionStatus(con,
								"a"));
				dataHashMap.put(DeductionInterfaceKey.VECTOR_PAY_GROUP_TYPE,
						PaymentTypeDAO.getAllActivePayTypeGroup(con));
			}
				break;
			case UPDATE_DEDUCTION: {
				String ded_id_str = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_DEDUCTION_ID);
				String status_id_str = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_SELECT_STATUS);
				String reason_id_str = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_SELECT_REASON);
				String deduction_value_str = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_TEXT_DEDUCTION_VALUE);
				String deduction_group_type_id = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_SELECT_PAY_GROUP_TYPE);
				PaymentTypeGroupModel group = PaymentTypeDAO.getGroupById(
						con, Integer.parseInt(deduction_group_type_id));
				int ded_id = Integer.parseInt(ded_id_str);
				DeductionModel deduction = DeductionDAO.getDeductionById(
						con, ded_id);

				deduction.setDeduction_value(Double
						.parseDouble(deduction_value_str));
				deduction.setReason_id(Integer.parseInt(reason_id_str));
				deduction.setPayment_group_type_name(group.getGroupName());
				deduction.setStatus_id(Integer.parseInt(status_id_str));
				DeductionDAO.updateDeduction(con, deduction);

				dataHashMap.put(DeductionInterfaceKey.VECTOR_DEDUCTION_REASON,
						DeductionDAO.getAllActiveDedReason(con));
				dataHashMap.put(DeductionInterfaceKey.VECTOR_DEDUCTION_STATUS,
						DeductionDAO.getAllOptionalDeductionStatus(con,
								"a"));
				dataHashMap.put(DeductionInterfaceKey.VECTOR_PAY_GROUP_TYPE,
						PaymentTypeDAO.getAllActivePayTypeGroup(con));
			}
				break;
			case UDPATE_DEDUCTION_STATUSES: {
				Vector all_deduction = DeductionDAO.getAllDeduction(con);
				if (all_deduction != null && all_deduction.size() > 0) {
					for (int i = 0; i < all_deduction.size(); i++) {
						DeductionModel deduction = (DeductionModel) all_deduction
								.get(i);
						String status_id_str = (String) paramHashMap
								.get(DeductionInterfaceKey.CONTROL_SELECT_STATUS
										+ deduction.getDeduction_id());
						deduction.setStatus_id(Integer.parseInt(status_id_str));
						DeductionDAO.updateDeduction(con, deduction);
					}
				}
				dataHashMap.put(DeductionInterfaceKey.VECTOR_DEDUCTION_REASON,
						DeductionDAO.getAllActiveDedReason(con));
				dataHashMap.put(DeductionInterfaceKey.VECTOR_DEDUCTION_STATUS,
						DeductionDAO.getAllOptionalDeductionStatus(con,
								"a"));
				dataHashMap.put(DeductionInterfaceKey.VECTOR_PAY_GROUP_TYPE,
						PaymentTypeDAO.getAllActivePayTypeGroup(con));
			}
				break;

			case VIEW_REASON_SEARCH: {
				dataHashMap.put(DeductionInterfaceKey.VECTOR_REASON_STATUS,
						DeductionDAO.getAllDedReasonStatus(con));
			}
				break;
			case VIEW_REASON: {
				String reason_name_search = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_TEXT_SEARCH_REASON_NAME);

				String status_id_search = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_SELECT_SEARCH_REASON_STATUS);

				int status_id_s = -1;

				if (status_id_search != null && !status_id_search.equals(""))
					status_id_s = Integer.parseInt(status_id_search);

				Vector all_result_reason = DeductionDAO.searchDeductionReason(
						con, reason_name_search, status_id_s);
				dataHashMap.put(DeductionInterfaceKey.VECTOR_DEDUCTION_REASON,
						all_result_reason);
				dataHashMap.put(DeductionInterfaceKey.VECTOR_REASON_STATUS,
						DeductionDAO.getAllDedReasonStatus(con));
			}
				break;
			case NEW_REASON: {
				dataHashMap.put(DeductionInterfaceKey.VECTOR_REASON_STATUS,
						DeductionDAO.getAllDedReasonStatus(con));
			}
				break;
			case ADD_REASON: {
				String reason_name = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_TEXT_REASON_NAME);
				String reason_desc = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_TEXT_REASON_DESC);
				int status_id = Integer
						.parseInt((String) paramHashMap
								.get(DeductionInterfaceKey.CONTROL_SELECT_REASON_STATUS));
				ReasonModel reason = new ReasonModel();
				reason.setReason_name(reason_name);
				reason.setReason_desc(reason_desc);
				reason.setReason_status_id(status_id);

				int inserted_reason_id = DeductionDAO.addDeductionReason(
						con, reason);
				dataHashMap.put(DeductionInterfaceKey.VECTOR_REASON_STATUS,
						DeductionDAO.getAllDedReasonStatus(con));
			}
				break;
			case EDIT_REASON: {
				String reason_id_str = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_HIDDEN_REASON_ID);
				int reason_id = Integer.parseInt(reason_id_str);
				ReasonModel reason = DeductionDAO.getReasonById(con,
						reason_id);

				dataHashMap.put(DeductionInterfaceKey.MODEL_DEDUCTION_REASON,
						reason);
				dataHashMap.put(DeductionInterfaceKey.VECTOR_REASON_STATUS,
						DeductionDAO.getAllDedReasonStatus(con));
			}
				break;
			case UPDATE_REASON: {
				String reason_id_str = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_HIDDEN_REASON_ID);
				int reason_id = Integer.parseInt(reason_id_str);
				String reason_name = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_TEXT_REASON_NAME);
				String reason_desc = (String) paramHashMap
						.get(DeductionInterfaceKey.CONTROL_TEXT_REASON_DESC);
				int status_id = Integer
						.parseInt((String) paramHashMap
								.get(DeductionInterfaceKey.CONTROL_SELECT_REASON_STATUS));
				ReasonModel reason = new ReasonModel();
				reason.setReason_id(reason_id);
				reason.setReason_name(reason_name);
				reason.setReason_desc(reason_desc);
				reason.setReason_status_id(status_id);

				DeductionDAO.updateDeductionReason(con, reason);
				dataHashMap.put(DeductionInterfaceKey.VECTOR_REASON_STATUS,
						DeductionDAO.getAllDedReasonStatus(con));
			}
				break;
			case UPDATE_REASON_STATUS: {
				Vector all_reason = DeductionDAO
						.getAllDeductionReason(con);
				if (all_reason != null && all_reason.size() > 0) {
					for (int i = 0; i < all_reason.size(); i++) {
						ReasonModel reason = (ReasonModel) all_reason.get(i);
						String status_id_str = (String) paramHashMap
								.get(DeductionInterfaceKey.CONTROL_SELECT_REASON_STATUS
										+ reason.getReason_id());
						if (status_id_str != null) {
							int status_id = Integer.parseInt(status_id_str);
							reason.setReason_status_id(status_id);
							DeductionDAO.updateDeductionReason(con,
									reason);
						}
					}
				}
				dataHashMap.put(DeductionInterfaceKey.VECTOR_REASON_STATUS,
						DeductionDAO.getAllDedReasonStatus(con));
			}
				break;

			// ///////////////////////////
			 case SEARCH_PAYMENT_TYPE_GROUP:
			 {
			 dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_STATUS,PaymentTypeDAO.getAllPayTypeGroupStatus(con));
			 }
			 break;
			 case VIEW_PAYMENT_TYPE_GROUP:
			 {
			 String group_name_search = (String)
			 paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SEARCH_GROUP_NAME);
			 String status_id_search =
			 (String)paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_GROUP_STATUS);
			
			 int status_id_s = -1;
			 if(status_id_search!=null && !status_id_search.equals(""))
			 status_id_s = Integer.parseInt(status_id_search);
			
			 Vector result_group =
			 PaymentTypeDAO.searchPayTypeGroup(con, group_name_search,
			 status_id_s);
			     
			 dataHashMap.put(PaymentInterfaceKey.VECTOR_GROUP,result_group);
			 dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_STATUS,PaymentTypeDAO.getAllPayTypeGroupStatus(con));
			 dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES,PaymentDAO.getAllPaymentType(con,
			 true));
			 }
			 break;
			 case NEW_PAYMENT_TYPE_GROUP:
			 {
			 dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_STATUS,PaymentTypeDAO.getAllPayTypeGroupStatus(con));
			 dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES,PaymentDAO.getAllPaymentType(con,
			 true));
			 }
			 break;
			 case EDIT_PAYMENT_TYPE_GROUP:
			 {
			 int group_id = Integer.parseInt( (String)
			 paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_GROUP_ID) );
			 PaymentTypeGroupModel group_model =
			 PaymentTypeDAO.getGroupById(con, group_id);
			 group_model.setAllAssignedTypes(PaymentTypeDAO.getAllGroupTypes(con, group_id) );			
			 dataHashMap.put(PaymentInterfaceKey.MODEL_GROUP,group_model);
			 dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_STATUS,PaymentTypeDAO.getAllPayTypeGroupStatus(con));
			 dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES,PaymentDAO.getAllPaymentType(con,true));
			 }
			 break;
			 case ADD_PAYMENT_TYPE_GROUP:
			 {
                             String[] payment_type_ids_str  = new String[0];
			 String group_name = (String)
			 paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_GROUP_NAME) ;
			 String group_desc = (String)
			 paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_GROUP_DESC) ;
			 int status_id =
			 Integer.parseInt((String)paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_STATUS));
                         Object result = paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_TYPE);
                         if (result instanceof String[])
                         {
			  payment_type_ids_str =
			 (String[])paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_TYPE)
			 ;}
                         else if (result instanceof String){
                             payment_type_ids_str = new String[1];
                             payment_type_ids_str[0] =
			 (String)paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_TYPE);
                         }
			 // insert group
			 PaymentTypeGroupModel group_model = new PaymentTypeGroupModel();
			 group_model.setGroupName(group_name);
			 group_model.setGroupDesc(group_desc);
			 group_model.setGroupStatusId(status_id);
			 int inserted_id = PaymentTypeDAO.addPayTypeGroup(con,
			 group_model);
			 // assign types to group
			 if(payment_type_ids_str!=null && payment_type_ids_str.length > 0)
			 {
			 for(int i=0;i<payment_type_ids_str.length; i++)
			 {
			 int pay_type_id = Integer.parseInt(payment_type_ids_str[i]);
			 //PaymentTypeDTO pay_type= PaymentDAO.getPaymentType(con,
			 //payment_type_ids_str[i]);
			 PaymentTypeDAO.assignPayTypeToGroup(con, inserted_id,
			 pay_type_id);
			 }
			 }
			    
			 dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_STATUS,PaymentTypeDAO.getAllPayTypeGroupStatus(con));
			 }
			 break;
			 case UPDATE_PAYMENT_TYPE_GROUP:
			 {
			 int group_id = Integer.parseInt( (String)
			 paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_GROUP_ID) );
			 String group_name = (String)
			 paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_GROUP_NAME) ;
			 String group_desc = (String)
			 paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_GROUP_DESC) ;
			 int status_id =
			 Integer.parseInt((String)paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_STATUS));
			 String[] payment_type_ids_str =
			 (String[])paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_TYPE);
			 // insert group
			 PaymentTypeGroupModel group_model = new PaymentTypeGroupModel();
			 group_model.setGroupId(group_id);
			 group_model.setGroupName(group_name);
			 group_model.setGroupDesc(group_desc);
			 group_model.setGroupStatusId(status_id);
			 PaymentTypeDAO.updatePayTypeGroup(con, group_model);
			 // delete all assigned types
			 PaymentTypeDAO.deleteGroupPayeType(con, group_id);
			 // assign types to group
			 if(payment_type_ids_str!=null && payment_type_ids_str.length > 0)
			 {
			 for(int i=0;i<payment_type_ids_str.length; i++)
			 {
			 int pay_type_id = Integer.parseInt(payment_type_ids_str[i]);
//			 PaymentTypeDTO pay_type= PaymentDAO.getPaymentType(con,
//			 payment_type_ids_str[i]);
			 PaymentTypeDAO.assignPayTypeToGroup(con, group_id,
			 pay_type_id);
			 }
			 }
			 dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_STATUS,PaymentTypeDAO.getAllPayTypeGroupStatus(con));
			 }
			 break;
			 
			 
			 case UPDATE_PAYMENT_TYPE_GROUP_STATUSES:
				 try
				 {
					System.out.println("Medhattttttttttttttttttt"); 
					for(int j=0; j<paramHashMap.size(); j++)
	                {
	                  String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
	                  
	                  //System.out.println("keyValue issssssssssss " + keyValue);
	                  if(tempStatusString.startsWith(PaymentInterfaceKey.CONTROL_SELECT_STATUS))
	                  {
	                	  String keyValue = (String)paramHashMap.get(tempStatusString);
	                	System.out.println("keyValue issssssssssss " + keyValue);
	                    int strlength = PaymentInterfaceKey.CONTROL_SELECT_STATUS.length() + 2;
	                    String groupId = tempStatusString.substring(strlength, tempStatusString.length());
	                    System.out.println("Group id issssssssssssss" + groupId);
	                    PaymentTypeDAO.updatePaymentGroupType(con,groupId,keyValue);
	                   // String groupStatusId = (String)paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_STATUS+"_"+groupId);
	                    //System.out.println("groupStatusId issssssssssss " + groupStatusId);
	                  }
	                }
				 }
				 catch(Exception e)
				 {
					 e.printStackTrace();
				 }
			break;
			 

			}
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		
		return dataHashMap;
	}

	public static HashMap getAllDeductionDetails(Connection  con, Vector all_deduction )
	{
		HashMap all_details_HM = new HashMap();
		if(all_deduction!=null && all_deduction.size()>0)
			for(int i=0;i<all_deduction.size();i++)
			{
				DeductionModel deduction = (DeductionModel)all_deduction.get(i);
				Vector all_ded_details = DeductionDAO.getDeductionDetails(con, deduction.getDeduction_id());
				all_details_HM.put(deduction.getDeduction_id()+"", all_ded_details);
			}
		return all_details_HM;
	}
}