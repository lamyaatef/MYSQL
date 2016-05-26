package com.mobinil.sds.core.facade.handlers;

import java.sql.*;
import java.util.*;

import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.core.utilities.jaspertest.JRXMLDesignerManegerForXLS;
import com.mobinil.sds.core.utilities.jaspertest.JasperManager;
import com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey;
import com.mobinil.sds.web.interfaces.*;
import com.mobinil.sds.web.interfaces.cam.AccrualInterfaceKey;
import com.mobinil.sds.web.interfaces.cam.MemoInterfaceKey;
import com.mobinil.sds.web.interfaces.commission.CommissionInterfaceKey;
import com.mobinil.sds.core.system.payment.dao.*;
import com.mobinil.sds.core.system.cam.accrual.dao.AccrualDAO;
import com.mobinil.sds.core.system.cam.deduction.dao.DeductionDAO;
import com.mobinil.sds.core.system.cam.payment.dao.PaymentScmStatusDao;
import com.mobinil.sds.core.system.cam.payment.model.PaymentTypeExcelModel;
import com.mobinil.sds.core.system.commission.model.*;
import com.mobinil.sds.core.system.commission.factor.dao.*;
import com.mobinil.sds.core.system.payment.model.*;
import com.mobinil.sds.core.system.payment.dto.*;
import com.mobinil.sds.core.system.sa.users.dao.UserDAO;
import com.mobinil.sds.core.system.dcm.genericModel.DAO.*;
import com.mobinil.sds.core.system.dcm.genericModel.*;

public class PaymentHandler
    {
        static final int SEARCH_PAYMENT = 1;
        static final int PAYMENT_SEARCH_PAYMENT = 2;
        static final int VIEW_READY_PAYMENT = 3;
        static final int VIEW_CLOSED_PAYMENT = 4;
        static final int CREATE_NEW_PAYMENT = 5;
        static final int SAVE_NEW_PAYMENT = 6;
        static final int PAY_PAYMENT = 7;
        static final int DELETE_PAYMENT = 8;
        static final int CLOSE_PAYMENT = 9;
        static final int EXPORT_PAYMENT = 10;
        static final int UPDATE_PAYMENT_STATUS = 11;
        static final int PAYMENT_COMMISSION = 12;
        static final int SAVE_PAYMENT_COMMISSION = 13;
        static final int EXPORT_PAYMENT_TO_EXCEL = 14;
        static final int VIEW_PAYMENT = 15;
        static final int VIEW_PAYMENT_TYPE = 16;
        static final int NEW_PAYMENT_TYPE = 17;
        static final int SAVE_PAYMENT_TYPE_STATUS = 18;
        static final int SAVE_PAYMENT_TYPE = 19;
        static final int ASSIGN_COMMISSION_CATEGORY_TO_PAYMENT_TYPE = 20;
        static final int SAVE_CATEGORY_TO_PAYMENT_TYPE = 21;
        static final int ADD_TO_ACCOUNT_MODULE = 22;
        static final int ACTION_EXPORT_PAYMENT_SHEETS_TO_EXCEL = 23;

        @SuppressWarnings("unchecked")
        private static void preparePaymentSearch(HashMap dataHashMap,
                String userId, Connection con) throws Exception
            {
                GenericModel paymentStatusModel = GenericModelDAO.getColumns(con, "PAYMENT_STATUS_TYPE");
                Vector paymentStatusVector = GenericModelDAO.getModels(con, paymentStatusModel);
                dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_STATUS, paymentStatusVector);

                GenericModel paymentTypeModel = GenericModelDAO.getColumns(con, "PAYMENT_TYPE");
                Vector paymentTypeVector = GenericModelDAO.getModels(con, paymentTypeModel, "PAYMENT_TYPE_NAME");
                dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE, paymentTypeVector);

                Vector comChannelVec = GenericModelDAO.getCommissionChannel(con, userId);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, comChannelVec);

            }

        public static HashMap handle(String action, HashMap paramHashMap,
                java.sql.Connection con) throws Exception
            {
                int actionType = 0;

                HashMap dataHashMap = new HashMap(100);
                try
                    {

                        if (action.equals(PaymentInterfaceKey.ACTION_SEARCH_PAYMENT))
                            actionType = SEARCH_PAYMENT;
                        else if (action.equals(PaymentInterfaceKey.ACTION_PAYMENT_SEARCH_PAYMENT))
                            actionType = PAYMENT_SEARCH_PAYMENT;
                        else if (action.equals(PaymentInterfaceKey.ACTION_VIEW_CLOSED_PAYMENT))
                            actionType = VIEW_CLOSED_PAYMENT;
                        else if (action.equals(PaymentInterfaceKey.ACTION_VIEW_READY_PAYMENT))
                            actionType = VIEW_READY_PAYMENT;
                        else if (action.equals(PaymentInterfaceKey.ACTION_CREATE_NEW_PAYMENT))
                            actionType = CREATE_NEW_PAYMENT;
                        else if (action.equals(PaymentInterfaceKey.ACTION_SAVE_NEW_PAYMENT))
                            actionType = SAVE_NEW_PAYMENT;
                        else if (action.equals(PaymentInterfaceKey.ACTION_UPDATE_PAYMENT_STATUS))
                            actionType = UPDATE_PAYMENT_STATUS;
                        else if (action.equals(PaymentInterfaceKey.ACTION_DELETE_PAYMENT))
                            actionType = DELETE_PAYMENT;
                        else if (action.equals(PaymentInterfaceKey.ACTION_PAYMENT_COMMISSION))
                            actionType = PAYMENT_COMMISSION;
                        else if (action.equals(PaymentInterfaceKey.ACTION_SAVE_PAYMENT_COMMISSION))
                            actionType = SAVE_PAYMENT_COMMISSION;
                        else if (action.equals(PaymentInterfaceKey.ACTION_EXPORT_PAYMENT_TO_EXCEL))
                            actionType = EXPORT_PAYMENT_TO_EXCEL;
                        else if (action.equals(PaymentInterfaceKey.ACTION_VIEW_PAYMENT))
                            actionType = VIEW_PAYMENT;
                        else if (action.equals(PaymentInterfaceKey.ACTION_VIEW_PAYMENT_TYPE))
                            actionType = VIEW_PAYMENT_TYPE;
                        else if (action.equals(PaymentInterfaceKey.ACTION_NEW_PAYMENT_TYPE))
                            actionType = NEW_PAYMENT_TYPE;
                        else if (action.equals(PaymentInterfaceKey.ACTION_SAVE_PAYMENT_TYPE_STATUS))
                            actionType = SAVE_PAYMENT_TYPE_STATUS;
                        else if (action.equals(PaymentInterfaceKey.ACTION_SAVE_PAYMENT_TYPE))
                            actionType = SAVE_PAYMENT_TYPE;
                        else if (action.equals(PaymentInterfaceKey.ACTION_ASSIGN_COMMISSION_CATEGORY_TO_PAYMENT_TYPE))
                            actionType = ASSIGN_COMMISSION_CATEGORY_TO_PAYMENT_TYPE;
                        else if (action.equals(PaymentInterfaceKey.ACTION_SAVE_CATEGORY_TO_PAYMENT_TYPE))
                            actionType = SAVE_CATEGORY_TO_PAYMENT_TYPE;
                        else if (action.equals(PaymentInterfaceKey.ACTION_ADD_TO_ACCOUNT_MODULE))
                            actionType = ADD_TO_ACCOUNT_MODULE;
                        else if (action.equals(PaymentInterfaceKey.ACTION_EXPORT_PAYMENT_SHEETS_TO_EXCEL))
                            actionType = ACTION_EXPORT_PAYMENT_SHEETS_TO_EXCEL;

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                        switch (actionType)
                            {
                                case SEARCH_PAYMENT :
                                    {
                                        preparePaymentSearch(dataHashMap, strUserID, con);
                                        Vector usersDeletePerVector = UserDAO.getUserPaymentPermission(con);
                        	            dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_USER_VECTOR, usersDeletePerVector);
                                        dataHashMap.put(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION, PaymentInterfaceKey.INPUT_HIDDEN_ALL_PAYMENT);
                                    }
                                    break;
                                case PAYMENT_SEARCH_PAYMENT :
                                    {

                                        String paymentName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_NAME);
                                        String paymentStatus = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_STATUS);
                                        String paymentTypa = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_TYPE);

                                        String paymentChannel = (String) paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL);
                                        System.out.println("The payment Channel isssssssssssss "
                                                + paymentChannel);
                                        String paymentStartDateFrom = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_FROM);
                                        String paymentStartDateTo = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_TO);
                                        String paymentEndDateFrom = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_FROM);
                                        String paymentEndDateTo = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_TO);

                                        Vector searchResults = PaymentDAO.getPaymentByFilter(con, paymentName, paymentStatus, paymentTypa, paymentChannel, paymentStartDateFrom, paymentStartDateTo, paymentEndDateFrom, paymentEndDateTo);
                                        dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT, searchResults);

                                        preparePaymentSearch(dataHashMap, strUserID, con);
                                        dataHashMap.put(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_STATUS, (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_STATUS));

                                        dataHashMap.put(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION, (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION));

                                        dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_NAME, paymentName);
                                        dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_STATUS, paymentStatus);
                                        dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_TYPE, paymentTypa);
                                        dataHashMap.put(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL, paymentChannel);
                                        dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_FROM, paymentStartDateFrom);
                                        dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_TO, paymentStartDateTo);
                                        dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_FROM, paymentEndDateFrom);
                                        dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_TO, paymentEndDateTo);
                                        Vector usersDeletePerVector = UserDAO.getUserPaymentPermission(con);
                        	            dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_USER_VECTOR, usersDeletePerVector);

                                    }
                                    break;
                                case VIEW_READY_PAYMENT :
                                    {
                                        dataHashMap = viewReadyPayment(con, paramHashMap, dataHashMap);
                                        dataHashMap.put(PaymentInterfaceKey.ACTION_VIEW_READY_PAYMENT, PaymentInterfaceKey.ACTION_VIEW_READY_PAYMENT);
                                        Vector usersDeletePerVector = UserDAO.getUserPaymentPermission(con);
                        	            dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_USER_VECTOR, usersDeletePerVector);
                                        
                                    }
                                    break;
                                case VIEW_CLOSED_PAYMENT :
                                    {

                                        dataHashMap = viewClosedPayment(con, paramHashMap, dataHashMap);
                                        dataHashMap.put(PaymentInterfaceKey.ACTION_VIEW_READY_PAYMENT, PaymentInterfaceKey.ACTION_VIEW_CLOSED_PAYMENT);
                                        Vector usersDeletePerVector = UserDAO.getUserPaymentPermission(con);
                        	            dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_USER_VECTOR, usersDeletePerVector);
                                        
                                    }
                                    break;
                                case CREATE_NEW_PAYMENT :
                                    {

                                        GenericModel paymentTypeModel = GenericModelDAO.getColumns(con, "PAYMENT_TYPE");
                                        Vector paymentTypeVector = GenericModelDAO.getModels(con, paymentTypeModel, "PAYMENT_TYPE_NAME");
                                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE, paymentTypeVector);
                                        Vector usersDeletePerVector = UserDAO.getUserPaymentPermission(con);
                        	            dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_USER_VECTOR, usersDeletePerVector);

                                    }
                                    break;
                                case SAVE_NEW_PAYMENT :
                                    {

                                        String paymentName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_NAME);
                                        String paymentType = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_TYPE);
                                        // String paymentChannel = (String)
                                        // paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL);
                                        String paymentStartDate = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE);
                                        String paymentEndDate = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE);

                                        PaymentDAO.addNewPayment(con, paymentName, paymentType, "1", paymentStartDate, paymentEndDate, strUserID);

                                        preparePaymentSearch(dataHashMap, strUserID, con);
Vector usersDeletePerVector = UserDAO.getUserPaymentPermission(con);
                        	            dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_USER_VECTOR, usersDeletePerVector);
                                        dataHashMap.put(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION, PaymentInterfaceKey.INPUT_HIDDEN_ALL_PAYMENT);

                                    }
                                    break;
                                case UPDATE_PAYMENT_STATUS :
                                    {

                                        String paymentID = (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID);
                                        String paymentAction = (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION);
Vector usersDeletePerVector = UserDAO.getUserPaymentPermission(con);
                        	            dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_USER_VECTOR, usersDeletePerVector);
                                        if (paymentAction.compareTo("Close") == 0)
                                            {

                                                int s1 = PaymentDAO.getPaymentCommissions(con, paymentID, true).size();
                                                int s2 = PaymentDAO.getPaymentCommissions(con, paymentID, false).size();

                                                if (s2 > s1)
                                                    {
                                                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "There are some commissions does not belog to this payment type of this payment");
                                                    } else
                                                    {
                                                        int paymetTypeId = PaymentDAO.getPaymentTypeByPaymentId(con, paymentID);
                                                        Vector paymentTypeMandatory = PaymentDAO.getPaymentTypeMandatoryCommissionCategory(con, paymetTypeId
                                                                + "");
                                                        Vector paymentTypeComCategory = PaymentDAO.getPaymentTypeCommissionCategory(con, paymentID);
                                                        boolean bridg = true;
                                                        for (int l = 0; l < paymentTypeComCategory.size(); l++)
                                                            {
                                                                Integer catId = (Integer) paymentTypeComCategory.elementAt(l);
                                                                if (!paymentTypeMandatory.contains(catId))
                                                                    {
                                                                        bridg = false;
                                                                    }
                                                            }

                                                        if (!bridg)
                                                            {
                                                                dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Current commissions does not achieve payment type constrains.");
                                                            } else
                                                            {

                                                                if (paymentAction.equals(PaymentInterfaceKey.INPUT_HIDDEN_CLOSE_PAYMENT))
                                                                    PaymentDAO.updatePaymentStatus(con, paymentID, "2", strUserID);
                                                                else if (paymentAction.equals(PaymentInterfaceKey.INPUT_HIDDEN_PAY_PAYMENT))
                                                                    PaymentDAO.updatePaymentStatus(con, paymentID, "3", strUserID);

                                                            }

                                                    }
                                            } else
                                            {
                                                if (paymentAction.equals(PaymentInterfaceKey.INPUT_HIDDEN_CLOSE_PAYMENT))
                                                    PaymentDAO.updatePaymentStatus(con, paymentID, "2", strUserID);
                                                else if (paymentAction.equals(PaymentInterfaceKey.INPUT_HIDDEN_PAY_PAYMENT))
                                                    PaymentDAO.updatePaymentStatus(con, paymentID, "3", strUserID);
                                            }
                                        String paymentName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_NAME);
                                        String paymentStatus = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_STATUS);
                                        String paymentTypa = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_TYPE);
                                        String paymentChannel = (String) paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL);
                                        String paymentStartDateFrom = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_FROM);
                                        String paymentStartDateTo = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_TO);
                                        String paymentEndDateFrom = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_FROM);
                                        String paymentEndDateTo = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_TO);

                                        Vector searchResults = PaymentDAO.getPaymentByFilter(con, paymentName, paymentStatus, paymentTypa, paymentChannel, paymentStartDateFrom, paymentStartDateTo, paymentEndDateFrom, paymentEndDateTo);
                                        dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT, searchResults);

                                        preparePaymentSearch(dataHashMap, strUserID, con);

                                        dataHashMap.put(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_STATUS, (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_STATUS));

                                        dataHashMap.put(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION, (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION));

                                        String tempPaymentStatus = (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_STATUS);

                                        if (tempPaymentStatus.compareTo("1") == 0)
                                            {
                                                // dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,comChannelVec);
                                                dataHashMap = viewReadyPayment(con, paramHashMap, dataHashMap);
                                            }
                                        if (tempPaymentStatus.compareTo("2") == 0)
                                            {
                                                // dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,comChannelVec);
                                                dataHashMap = viewClosedPayment(con, paramHashMap, dataHashMap);
                                            }

                                    }
                                    break;
                                case DELETE_PAYMENT :
                                    {

                                        String paymentID = (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID);
                                        String paymentAction = (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION);

                                        PaymentDAO.updatePaymentStatus(con, paymentID, "4", strUserID);

                                        String paymentName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_NAME);
                                        String paymentStatus = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_STATUS);
                                        String paymentTypa = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_TYPE);
                                        String paymentChannel = (String) paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL);
                                        String paymentStartDateFrom = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_FROM);
                                        String paymentStartDateTo = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_TO);
                                        String paymentEndDateFrom = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_FROM);
                                        String paymentEndDateTo = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_TO);

                                        Vector searchResults = PaymentDAO.getPaymentByFilter(con, paymentName, paymentStatus, paymentTypa, paymentChannel, paymentStartDateFrom, paymentStartDateTo, paymentEndDateFrom, paymentEndDateTo);
                                        dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT, searchResults);

                                        preparePaymentSearch(dataHashMap, strUserID, con);

                                        dataHashMap.put(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_STATUS, (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_STATUS));

                                        dataHashMap.put(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION, (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION));

                                        String tempPaymentStatus = (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_STATUS);

                                        if (tempPaymentStatus.compareTo("1") == 0)
                                            {
                                                dataHashMap = viewReadyPayment(con, paramHashMap, dataHashMap);
                                            }
                                        if (tempPaymentStatus.compareTo("2") == 0)
                                            {
                                                dataHashMap = viewClosedPayment(con, paramHashMap, dataHashMap);
                                            }

                                        Vector usersDeletePerVector = UserDAO.getUserPaymentPermission(con);
                        	            dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_USER_VECTOR, usersDeletePerVector);

                                    }
                                    break;
                                case EXPORT_PAYMENT :
                                    {

                                    }
                                    break;
                                case PAYMENT_COMMISSION :
                                    {

                                        String paymentID = (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID);
                                        Vector commissionList = PaymentDAO.getPaymentCommission(con, paymentID);
                                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_COMMISSION_LIST, commissionList);
                                        Vector paymentCommission = PaymentDAO.getCommissionFromPayment(con, paymentID);
                                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_COMMISSION, paymentCommission);
                                        dataHashMap.put(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID, paymentID);
                                        String paymentName = PaymentDAO.selectPaymentName(con, paymentID);
                                        dataHashMap.put(PaymentInterfaceKey.INPUT_TEXT_PAGE_HEADER, paymentName);
                                    }
                                    break;
                                case SAVE_PAYMENT_COMMISSION :
                                    try
                                        {
                                            dataHashMap = new HashMap();

                                            String paymentID = (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID);

                                            dataHashMap.put(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID, paymentID);
                                            String paymentName = PaymentDAO.selectPaymentName(con, paymentID);
                                            dataHashMap.put(PaymentInterfaceKey.INPUT_TEXT_PAGE_HEADER, paymentName);
                                            PaymentDAO.deleteCommissionPayment(con, paymentID);
                                            Vector v = PaymentDAO.getPaymentCommission(con, paymentID);
                                            for (int i = 0; i < v.size(); i++)
                                                {
                                                    CommissionModel commissionModel2 = (CommissionModel) v.get(i);
                                                    int commission_id = commissionModel2.getCommissionID();
                                                    String commission_check = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_COMMISSION_CHECKED
                                                            + commission_id);

                                                    if (commission_check != null)
                                                        {

                                                            String commissionId = Integer.toString(commission_id);
                                                            PaymentDAO.addCommissionToPayment(con, commissionId, paymentID);
                                                        }

                                                }

                                            Vector commissionList = PaymentDAO.getPaymentCommission(con, paymentID);
                                            dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_COMMISSION_LIST, commissionList);
                                            Vector paymentCommission = PaymentDAO.getCommissionFromPayment(con, paymentID);
                                            dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_COMMISSION, paymentCommission);
                                            Vector usersDeletePerVector = UserDAO.getUserPaymentPermission(con);
                        	            dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_USER_VECTOR, usersDeletePerVector);

                                        } catch (Exception e)
                                        {
                                            e.printStackTrace();
                                        }
                                    break;
                                case EXPORT_PAYMENT_TO_EXCEL :
                                    {

                                        String paymentID = (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID);
                                        Vector paymentCommissions = PaymentDAO.getCommissionFromPayment(con, paymentID);
                                        PaymentModel paymentModel = PaymentDAO.getPaymentByID(con, paymentID);
                                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, paymentModel);

                                        Vector paymentCommissionFactors = new Vector();
                                        String commissionIDs = "";
                                        if (paymentCommissions.size() != 0)
                                            {
                                                for (int i = 0; i < paymentCommissions.size(); i++)
                                                    {
                                                        CommissionModel commissionModel = (CommissionModel) paymentCommissions.get(i);
                                                        if (i == 0)
                                                            commissionIDs = String.valueOf(commissionModel.getCommissionID());
                                                        else
                                                            commissionIDs += " OR COMMISSION_DETAIL_ID = "
                                                                    + String.valueOf(commissionModel.getCommissionID());

                                                    }
                                                paymentCommissionFactors.add(FactorDAO.getCommissionFactors(con, commissionIDs));

                                            }

                                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_COMMISSION_FACTOR, paymentCommissionFactors);

                                    }
                                    break;
                                case VIEW_PAYMENT :
                                    {
                                        String paymentID = (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID);
                                        Vector paymentCommissions = PaymentDAO.getCommissionFromPayment(con, paymentID);
                                        PaymentModel paymentModel = PaymentDAO.getPaymentByID(con, paymentID);
                                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, paymentModel);
                                        Vector paymentCommissionFactors = new Vector();
                                        String commissionIDs = "";
                                        Utility.logger.debug("paymentCommissions.size() :"
                                                + paymentCommissions.size());
                                        if (paymentCommissions.size() != 0)
                                            {
                                                for (int i = 0; i < paymentCommissions.size(); i++)
                                                    {
                                                        CommissionModel commissionModel = (CommissionModel) paymentCommissions.get(i);
                                                        if (i == 0)
                                                            commissionIDs = String.valueOf(commissionModel.getCommissionID());
                                                        else
                                                            commissionIDs += " OR COMMISSION_DETAIL_ID = "
                                                                    + String.valueOf(commissionModel.getCommissionID());

                                                    }
                                                paymentCommissionFactors.add(FactorDAO.getCommissionFactors(con, commissionIDs));

                                            }
                                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_COMMISSION_FACTOR, paymentCommissionFactors);
                                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, paymentModel);
                                    }
                                    break;
                                case VIEW_PAYMENT_TYPE :// base
                                    {

                                        dataHashMap.put(PaymentInterfaceKey.VEC_PAYMENT_TYPE, PaymentDAO.getAllPaymentType(con, false));

                                        dataHashMap.put(PaymentInterfaceKey.VEC_PAYMENT_TYPE_STATUS, PaymentDAO.getPaymentTypeStatus(con));
                                    }
                                    break;
                                case SAVE_PAYMENT_TYPE_STATUS :
                                    {

                                        for (int j = 0; j < paramHashMap.size(); j++)
                                            {
                                                String tempStatusString = (String) paramHashMap.keySet().toArray()[j];

                                                if (tempStatusString.startsWith("selectPaymentType"))
                                                    {
                                                        String keyValue = (String) paramHashMap.get(tempStatusString);
                                                        int typeID = new Integer(Integer.parseInt(tempStatusString.substring(17, tempStatusString.length()))).intValue();
                                                        PaymentDAO.updatePaymentTypeStatus(con, typeID, keyValue);
                                                    }
                                            }
                                        dataHashMap.put(PaymentInterfaceKey.VEC_PAYMENT_TYPE, PaymentDAO.getAllPaymentType(con, false));

                                        dataHashMap.put(PaymentInterfaceKey.VEC_PAYMENT_TYPE_STATUS, PaymentDAO.getPaymentTypeStatus(con));
                                    }
                                    break;

                                case SAVE_PAYMENT_TYPE :
                                    {

                                        String strTypeId = (String) paramHashMap.get(PaymentInterfaceKey.PARAM_LOAD_PAYMENT_TYPE_ID);
                                        String statusId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_STATUS);
                                        String methodId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD);
                                        String typeName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_INPUT_TEXT_PAYMENT_TYPE_NAME);
                                        String accrualId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ACRRUAL);
                                        
                                        if (strTypeId != null)
                                            {
                                                int intTypeId = 0;
                                                if (strTypeId.startsWith("_"))
                                                {
                                                    System.out.println("inside if of _");
                                                    strTypeId = strTypeId.substring(1);
                                                    System.out.println(strTypeId);
                                                }
                                                int nTypeId = new Integer(strTypeId).intValue();
                                                PaymentDAO.updatePaymentType(con, nTypeId, typeName, statusId, methodId, accrualId);
                                            } else
                                            {
                                                PaymentDAO.insertPaymentType(con, typeName, statusId, methodId, accrualId);
                                            }

                                        dataHashMap.put(PaymentInterfaceKey.VEC_PAYMENT_TYPE, PaymentDAO.getAllPaymentType(con, false));

                                        dataHashMap.put(PaymentInterfaceKey.VEC_PAYMENT_TYPE_STATUS, PaymentDAO.getPaymentTypeStatus(con));
                                    }
                                    break;

                                case NEW_PAYMENT_TYPE :
                                    {

                                        String strTypeId = (String) paramHashMap.get(PaymentInterfaceKey.PARAM_LOAD_PAYMENT_TYPE_ID);

                                        if (strTypeId != null
                                                && strTypeId.compareTo("") != 0)
                                            {
                                                int intTypeId = 0;
                                                int nTypeId = new Integer(strTypeId).intValue();
                                                PaymentTypeDTO ptdto = PaymentDAO.getPaymentType(con, strTypeId);
                                                // ptdto.setTypeId(new
                                                // Integer(nTypeId));

                                                dataHashMap.put(PaymentInterfaceKey.DTO_PAYMENT_TYPE_DETAIL, ptdto);
                                                dataHashMap.put(PaymentInterfaceKey.PARAM_LOAD_PAYMENT_TYPE_ID, strTypeId);
                                            }

                                        dataHashMap.put(PaymentInterfaceKey.VEC_PAYMENT_TYPE_STATUS, PaymentDAO.getPaymentTypeStatus(con));
                                        dataHashMap.put(PaymentInterfaceKey.VEC_PAYMENT_METHODS, PaymentDAO.getPaymentTypeMethods(con));

                                        // the accruals
                                        Vector accruals = AccrualDAO.searchAccrual(con, "", -1, -1);
                                        dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL, accruals);
                                        Vector usersDeletePerVector = UserDAO.getUserPaymentPermission(con);
                        	            dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_USER_VECTOR, usersDeletePerVector);

                                    }
                                    break;

                                case ASSIGN_COMMISSION_CATEGORY_TO_PAYMENT_TYPE :
                                    {

                                        dataHashMap.put(PaymentInterfaceKey.VEC_PAYMENT_TYPE, PaymentDAO.getAllPaymentType(con, true));
                                        dataHashMap.put(PaymentInterfaceKey.VEC_COMMISSIION_CATEGORY, PaymentDAO.getAllComsionCategry(con));
                                        dataHashMap.put(PaymentInterfaceKey.VEC_COMMISSION_CONSTRAIN, PaymentDAO.getAllComsionConstrain(con));
                                    }
                                    break;
                                case SAVE_CATEGORY_TO_PAYMENT_TYPE :
                                    {

                                        String strTypeId = (String) paramHashMap.get(PaymentInterfaceKey.PARAM_PAYMENT_TYPE_ID);
                                        System.out.println("strTypeId = "+ strTypeId);
                                        if (strTypeId != null
                                                && strTypeId.compareTo("") != 0)
                                            {
                                                int intTypeId = 0;
                                                int nTypeId = new Integer(strTypeId).intValue();
                                                
                                                System.out.println("delete payment "+ strTypeId);
                                                
                                                PaymentDAO.deletePaymentType(con, strTypeId);
                                                
                                                for (int j = 0; j < paramHashMap.size(); j++)
                                                    {
                                                        String tempStatusString = (String) paramHashMap.keySet().toArray()[j];

                                                        if (tempStatusString.startsWith(PaymentInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX
                                                                + strTypeId+"_"))
                                                            {

                                                                String keyValueofPayType = (String) paramHashMap.get(tempStatusString);
                                                              
                                                                String s = tempStatusString.substring(
                                                                        (PaymentInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX + strTypeId+"_").length()
                                                                        , tempStatusString.length());
                                                                
                                                                System.out.println("s =" + s);
                                                                
                                                                if (s == null || (s.compareTo(""))==0)
                                                                    continue;
                                                                
                                                                int catID = new Integer(s).intValue();
                                                                
                                                                String keyValueofConstrain = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_STATUS
                                                                        + strTypeId+"_"
                                                                        + (catID + ""));
                                                                
                                                                System.out.println("keyValueofConstrain " + keyValueofConstrain);
                                                                
                                                                if (keyValueofConstrain != null)
                                                                {
                                                                    System.out.println("inserted value " + keyValueofConstrain);
                                                                    PaymentDAO.insertToCommissionControl(con, keyValueofConstrain, catID
                                                                            + "", strTypeId);
                                                                }
                                                                
                                                                
                                                            }
                                                    }

                                            }

                                        dataHashMap.put(PaymentInterfaceKey.VEC_PAYMENT_TYPE, PaymentDAO.getAllPaymentType(con, true));
                                        dataHashMap.put(PaymentInterfaceKey.VEC_COMMISSIION_CATEGORY, PaymentDAO.getAllComsionCategry(con));
                                        dataHashMap.put(PaymentInterfaceKey.VEC_COMMISSION_CONSTRAIN, PaymentDAO.getAllComsionConstrain(con));
                                    }
                                    break;
                                case ADD_TO_ACCOUNT_MODULE :
                                    {

                                        String paymentID = (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID);
                                        String paymentAction = (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION);

                                        if (paymentAction.equals(PaymentInterfaceKey.INPUT_HIDDEN_ADD_TO_ACCOUNT))
                                            PaymentDAO.updatePaymentStatus(con, paymentID, "5", strUserID);
                                        PaymentDAO.AddToAccountModule(con, paymentID, strUserID);
                                        String paymentName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_NAME);
                                        String paymentStatus = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_STATUS);
                                        String paymentTypa = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_TYPE);
                                        String paymentChannel = (String) paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL);
                                        String paymentStartDateFrom = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_FROM);
                                        String paymentStartDateTo = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_TO);
                                        String paymentEndDateFrom = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_FROM);
                                        String paymentEndDateTo = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_TO);

                                        Vector searchResults = PaymentDAO.getPaymentByFilter(con, paymentName, paymentStatus, paymentTypa, paymentChannel, paymentStartDateFrom, paymentStartDateTo, paymentEndDateFrom, paymentEndDateTo);
                                        dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT, searchResults);

                                        preparePaymentSearch(dataHashMap, strUserID, con);

                                        dataHashMap.put(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_STATUS, (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_STATUS));

                                        dataHashMap.put(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION, (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION));

                                    }
                                    break;
                                case ACTION_EXPORT_PAYMENT_SHEETS_TO_EXCEL :
                                    {

                                        String app_name = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_APPLIACTION_CONTEXT);
                                        String payment_id = (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID);
                                        PaymentModel paymentModel = PaymentDAO.getPaymentByID(con, payment_id);
                                        System.out.println("paymentModel="+ paymentModel);
                                        String paymentType_id = ""
                                                + paymentModel.getPaymentTypeID();
                                        System.out.println("paymentType_id= "+ paymentType_id);
                                        Vector excel_sheets = PaymentScmStatusDao.getPaymentTypeExcelAttributes(con, paymentType_id);
                                         System.out.println("excel_sheets lenght = "+
                                         excel_sheets.size());
                                        String excel_file_specific_path = "";

                                        String[] all_queries = new String[excel_sheets.size()];
                                        String unique_payment_file = paymentModel.getPaymentName()
                                                + "_"
                                                + DeductionDAO.getFileSequence(con)
                                                + "_"
                                                + paymentModel.getPaymentID();
                                        ArrayList all_generatedXMLFile_path = new ArrayList();
                                        String[] all_sheet_name = new String[excel_sheets.size()];

                                        if (excel_sheets != null)
                                            {
                                                for (int i = 0; i < excel_sheets.size(); i++)
                                                    {
                                                        PaymentTypeExcelModel sheet_model = (PaymentTypeExcelModel) excel_sheets.get(i);
                                                        all_sheet_name[i] = sheet_model.getExcelSheetName();
                                                        String query = sheet_model.getExcelSheetSqlStatement();
                                                        all_queries[i] = JasperManager.setQueryParameter(query, payment_id);
                                                        // System.out.println("add ing "+
                                                        // all_queries[i]);
                                                    }

                                                boolean all_sheets_empty = true;
                                                ArrayList error_msgs = new ArrayList();
                                                ArrayList error_msgs_toBeViewed = new ArrayList();
                                                ArrayList all_queries_al = new ArrayList();
                                                ArrayList all_sheets_names = new ArrayList();
                                                for (int i = 0; i < all_queries.length; i++)
                                                    {
                                                        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                                                        JRXMLDesignerManegerForXLS manager = new JRXMLDesignerManegerForXLS();
                                                        System.out.println("***********************");
                                                        ResultSet result_set = Utility.executeSelect(con, all_queries[i]);

                                                        if (result_set != null
                                                                && result_set.next()) // query
                                                                                      // has
                                                                                      // data
                                                            {
                                                                result_set = Utility.executeSelect(con, all_queries[i]);
                                                                Vector all_report_column = Utility.getResultSetFields(result_set);
                                                                String generated_xml_file_path = app_name
                                                                        + "/generated XML files/"
                                                                        + unique_payment_file
                                                                        + "_classic2_xls_"
                                                                        + i
                                                                        + ".jrxml";
                                                                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^");
                                                                System.out.println(app_name
                                                                        + "/templates/temp2_xls.jrxml");
                                                                manager.designJRXMLFile(app_name
                                                                        + "/templates/temp2_xls.jrxml", all_queries[i], all_report_column, generated_xml_file_path, false);
                                                                all_generatedXMLFile_path.add(generated_xml_file_path);
                                                                all_sheets_empty = false;
                                                                all_queries_al.add(all_queries[i]);
                                                                all_sheets_names.add(all_sheet_name[i]);
                                                                // System.out.println("aaaaaaaaa: "+all_queries[i]);
                                                            } else
                                                            {
                                                                error_msgs_toBeViewed.add("Sheet ("
                                                                        + all_sheet_name[i]
                                                                        + ") returned no data.");
                                                            }
                                                    }
                                                if (all_sheets_empty == false)
                                                    { // at least one query
                                                      // returned data
                                                        String file_name = app_name
                                                                + "/generated XLS files/"
                                                                + unique_payment_file
                                                                + ".xls";
                                                        // System.out.println("eeeeeee "+all_sheet_name[0]);
                                                        String[] all_qes_temp = new String[all_queries_al.size()];
                                                        String[] all_paths_temp = new String[all_generatedXMLFile_path.size()];
                                                        String[] all_sheets_names_temp = new String[all_sheets_names.size()];
                                                        for (int i = 0; i < all_queries_al.size(); i++)
                                                            {
                                                                all_qes_temp[i] = (String) all_queries_al.get(i);
                                                                all_paths_temp[i] = (String) all_generatedXMLFile_path.get(i);
                                                                all_sheets_names_temp[i] = (String) all_sheets_names.get(i);
                                                            }
                                                        JasperManager.setParameter(all_paths_temp, all_qes_temp, file_name, all_sheets_names_temp);
                                                        JasperManager.exportXLSReport(con);
                                                        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@2222");
                                                        
                                                        dataHashMap.put(MemoInterfaceKey.CONTROL_FILE_NAME, unique_payment_file
                                                                + ".xls");
                                                        dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, unique_payment_file
                                                                + ".xls");
                                                        dataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "/cam/camMemosFiles/generated XLS files/");
                                                    } else
                                                    {
                                                        error_msgs.add("Sheets queries doesn't return data. Report is empty.");
                                                    }

                                                dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_REPORT_ERROR_MSG, error_msgs);
                                                dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_REPORT_ERROR_MSG_TOBEVIEWED, error_msgs_toBeViewed);
                                                dataHashMap.put(PaymentInterfaceKey.ACTION_VIEW_READY_PAYMENT, (String) paramHashMap.get(PaymentInterfaceKey.ACTION_VIEW_READY_PAYMENT));

                                            }

                                        preparePaymentSearch(dataHashMap, strUserID, con);

                                        dataHashMap.put(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION, PaymentInterfaceKey.INPUT_HIDDEN_ALL_PAYMENT);
                                        dataHashMap.put(MemoInterfaceKey.CONTROL_INCOMING_ACITON, PaymentInterfaceKey.ACTION_EXPORT_PAYMENT_SHEETS_TO_EXCEL);

                                    }
                                    break;
                                default :
                                    Utility.logger.debug("Unknown action received: "
                                            + action);
                            }

                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                       
                        throw objExp;
                    }

                return dataHashMap;
            }

        public static HashMap viewReadyPayment(Connection con,
                HashMap paramHashMap, HashMap dataHashMap) throws Exception
            {
                String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                // modified by Medhat Amin on 7-7-2011
                
                //Vector searchResults = PaymentDAO.getPaymentByFilter(con, "", "1", "0", "0", "*", "*", "*", "*");
                
                Vector searchResults = new Vector();
                
                dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT, searchResults);

                preparePaymentSearch(dataHashMap, strUserID, con);

                dataHashMap.put(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION, PaymentInterfaceKey.INPUT_HIDDEN_CLOSE_PAYMENT);
                dataHashMap.put(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_STATUS, "1");

                return dataHashMap;

            }

        public static HashMap viewClosedPayment(Connection con,
                HashMap paramHashMap, HashMap dataHashMap) throws Exception
            {
                String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                Utility.logger.debug("USER ID PAYMENT  HANDLER:  " + strUserID);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                
                // Modified by Medhat Amin on 7-7-2011
                // Vector searchResults = PaymentDAO.getPaymentByFilter(con, "", "2", "0", "0", "*", "*", "*", "*");
                Vector searchResults = new Vector();
                
                dataHashMap.put(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT, searchResults);

                preparePaymentSearch(dataHashMap, strUserID, con);

                dataHashMap.put(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION, PaymentInterfaceKey.INPUT_HIDDEN_PAY_PAYMENT);
                dataHashMap.put(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_STATUS, "2");
                return dataHashMap;
            }

    }