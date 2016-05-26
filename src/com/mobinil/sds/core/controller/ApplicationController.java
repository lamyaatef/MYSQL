package com.mobinil.sds.core.controller;

import com.mobinil.mcss.commission.CommissionIIIHandler;
import com.mobinil.mcss.commissionLabel.CommissionLabelHandler;
import com.mobinil.mcss.level_relation_management.LevelRelationManagementHandler;
import com.mobinil.sds.core.facade.handlers.*;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.ma.MAInterfaceKey;

import java.util.HashMap;

public class ApplicationController {

    public HashMap handleEvent(final String action, final String handlerName, final HashMap paramHashMap, java.sql.Connection con) throws Exception {
        HashMap dataHashMap = null;

        //initialize the handler

        try {


            if (handlerName.equalsIgnoreCase("UserAccountHandlerEJB")) {

                dataHashMap = UserAccountHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("AdministratorHandlerEJB")) {

                dataHashMap = AdministratorHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("RoleHandlerEJB")) {

                dataHashMap = RoleHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("UserHandlerEJB")) {

                dataHashMap = UserHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("QueryBuilderHandlerEJB")) {
                // QueryBuilderHandler queryBuilderHandlerEJBBean = new QueryBuilderHandler();
                dataHashMap = QueryBuilderHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("ReportHandlerEJB")) {
                //ReportHandler reportHandlerEJBBean = new ReportHandler();
                dataHashMap = ReportHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("ContractRegistrationHandler")) {
                //ContractRegistrationHandler ContractRegistrationHandlerBean = new ContractRegistrationHandler();
                dataHashMap = ContractRegistrationHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("AuthinticationCallHandlerEJB")) {

                //AuthinticationCallHandler authinticationCallHandlerEJBBean = new AuthinticationCallHandler();
                dataHashMap = AuthinticationCallHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("SurveyHandlerEJB")) {
                //  SurveyHandler surveyHandlerEJBBean = new SurveyHandler();
                dataHashMap = SurveyHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("SOPHandlerEJB")) {
                //  SOPHandler sOPHandlerEJBBean = new SOPHandler();
                dataHashMap = SOPHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("SFRHandlerEJB")) {
                //  SFRHandler sFRHandlerEJBBean = new SFRHandler();
                dataHashMap = SFRHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("DEUHandler")) {
                // DEUHandler dEUHandlerBean = new DEUHandler();
                dataHashMap = DEUHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("HLPHandlerEJB")) {
                //HLPHandler hLPHandlerEJBBean = new HLPHandler();
                dataHashMap = HLPHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("DCMHandlerEJB")) {
                // DCMHandler dCMHandlerEJBBean = new DCMHandler(); 
                dataHashMap = DCMHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("CommissionIIHandlerEJB")) {
                // CommissionIIHandler commissionIIHandlerEJBBean = new CommissionIIHandler();
                dataHashMap = CommissionIIHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("CommissionLabelHandlerEJB")) {
                dataHashMap = CommissionLabelHandler.hadle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("PaymentHandlerEJB")) {
                // PaymentHandler paymentHandlerEJBBean = new PaymentHandler();
                dataHashMap = PaymentHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("DCMIIHandlerEJB")) {
                //   DCMIIHandler DCMIIHandlerEJBBean = new DCMIIHandler();
                dataHashMap = DCMIIHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("SecurityHandlerEJB")) {
                //  SecurityHandler securityHandlerEJBBean = new SecurityHandler();
                dataHashMap = SecurityHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("CamPaymentHandlerEJB")) {

                dataHashMap = CamPaymentHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("CamMemoHandlerEJB")) {

                dataHashMap = CamMemoHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("CAMAccrualHandlerEJB")) {
                // CAMAccrualHandler handler = new CAMAccrualHandler();
                dataHashMap = CAMAccrualHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("CAMDeductionHandlerEJB")) {
                CAMDeductionHandler handler = new CAMDeductionHandler();
                dataHashMap = CAMDeductionHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("TaskHandlerEJB")) {
                //TaskHandler taskHandlerEJBBean = new TaskHandler();
                dataHashMap = TaskHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("DataMigrationHandler")) {
                DataMigrationHandler handler = new DataMigrationHandler();
                dataHashMap = handler.handle(action, paramHashMap);
            } else if (handlerName.equalsIgnoreCase("AACMHandlerEJB")) {
                AACMHandler aacmHandlerEJBBean = new AACMHandler();
                dataHashMap = aacmHandlerEJBBean.handle(action, paramHashMap);
            } else if (handlerName.equalsIgnoreCase("CCMHandlerEJB")) {
                CCMHandler CCMHandlerEJBBean = new CCMHandler();
                dataHashMap = CCMHandlerEJBBean.handle(action, paramHashMap);
            } else if (handlerName.equalsIgnoreCase("AccrualCalculationHandler")) {
                //	AccrualCalculationHandler accrualCalculationHandler = new AccrualCalculationHandler();
                dataHashMap = AccrualCalculationHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("AuthResHandlerEJB")) {

                dataHashMap = AuthResHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("IFReportDeliveryHandler")) {
                // IFReportDeliveryHandler ifReportDelivery=new IFReportDeliveryHandler();
                dataHashMap = IFReportDeliveryHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("MaintenanceHandler")) {

                dataHashMap = MaintenanceHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("SIPHandler")) {

                dataHashMap = SIPHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("SCMHandler")) {

                dataHashMap = SCMHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("SCMRequestHandler")) {
                dataHashMap = SCMRequestHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("ComH")) {
                dataHashMap = ComH.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("CommissionIIIHandlerEJB")) {
                dataHashMap = CommissionIIIHandler.handle(action, paramHashMap, con);
            } else if (handlerName.equalsIgnoreCase("LevelRelationManagementHandler")) {
                System.out.println("instiating LevelRelationManagementHandler");
                dataHashMap = LevelRelationManagementHandler.handle(action, paramHashMap);
            }
        } catch (Exception e) {
            Utility.logger.debug("Error in instantiating handler : " + e.toString());
            e.printStackTrace();
        }



        return dataHashMap;
    }
}