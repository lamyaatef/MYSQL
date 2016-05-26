package com.mobinil.sds.core.system.cam.payment.dao;

import com.mobinil.sds.core.system.gn.querybuilder.dao.*;
import com.mobinil.sds.core.system.gn.querybuilder.domain.*;
import com.mobinil.sds.core.system.gn.querybuilder.dto.*;
import com.mobinil.sds.core.system.gn.querybuilder.model.*;
import com.mobinil.sds.core.system.commission.factor.model.*;
import java.sql.*;
import java.util.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.core.system.commission.model.*;
import com.mobinil.sds.core.system.payment.model.*;
import com.mobinil.sds.core.system.commission.model.LabelModel;
import com.mobinil.sds.web.interfaces.commission.*;
import com.mobinil.sds.core.system.gn.reports.dto.*;
import com.mobinil.sds.core.system.gn.dataview.dao.*;
import com.mobinil.sds.web.interfaces.gn.reports.*;
import com.mobinil.sds.web.interfaces.*;
import com.mobinil.sds.web.interfaces.gn.querybuilder.*;
import com.mobinil.sds.core.system.cam.payment.model.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleTypes;
import com.mobinil.sds.core.system.cam.payment.model.PaymentModel;

public class PaymentScmStatusDao {

    private final static boolean debugFlag = false;

    private static void debug(String msg) {
        if (debugFlag) {
            System.out.println(msg);
        }
    }

    public static Vector viewPaymentScmStatus(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.viewScmPaymentStatus";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                PaymentScmStatusModel payment = new PaymentScmStatusModel(rs.getInt("scm_id"), rs.getString("dcm_name"), rs.getString("dcm_code"));
                listToReturn.add(payment);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;

    }

    public static Vector viewPaymentTypes(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.viewPaymentTypes";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                PaymentTypeModel type = new PaymentTypeModel(rs.getInt("PAYMENT_TYPE_ID"), rs.getString("PAYMENT_TYPE_NAME"));

                debug("payment type name in order by: "
                        + rs.getString("PAYMENT_TYPE_NAME"));
                listToReturn.add(type);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;

    }

    public static Vector getMemoPaymentTypes(Connection con, String memoId) {
        Vector listToReturn = new Vector();
        Statement stmt = null;
        ResultSet rs = null;
        try {

            String sql = "SELECT * FROM cam_memo join payment_type"
                    + " on (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)"
                    + " WHERE payment_type.payment_type_status_id = 1 and CAM_MEMO.MEMO_ID= " + memoId;
            stmt = con.createStatement();
            String query = null;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                PaymentTypeModel type = new PaymentTypeModel(rs.getInt("PAYMENT_TYPE_ID"), rs.getString("PAYMENT_TYPE_NAME"));
                debug("payment type name in order by: "
                        + rs.getString("PAYMENT_TYPE_NAME"));
                listToReturn.add(type);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeStatement(stmt);

        }
        return listToReturn;

    }

    public static Vector getPaymentStatusCases(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getPaymentStatusCases";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                PaymentStatusCasesModel paymentCases = new PaymentStatusCasesModel(
                        rs.getInt("ID"), rs.getString("CAM_STATUS_FOR_PAYMENT"));
                listToReturn.add(paymentCases);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;

    }

    public static void editPaymentScmStatus(Connection con, String scmId,
            String statusId, String reasonId, String makerId) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.changeSCMPaymentStatus";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?)}";
            stmt = con.prepareCall(query);
            if (scmId == null || scmId.equals("")) {
                scmId = "-1";
            } else {
                stmt.setInt(1, Integer.parseInt(scmId));
            }

            if (statusId == null || statusId.equals("")) {
                statusId = "-1";
            } else {
                stmt.setInt(2, Integer.parseInt(statusId));
            }

            if (reasonId == null || reasonId.equals("")) {
                reasonId = "-1";
            } else {
                stmt.setInt(3, Integer.parseInt(reasonId));
            }

            if (makerId == null || makerId.equals("")) {
                makerId = "-1";
            } else {
                stmt.setInt(4, Integer.parseInt(makerId));
            }

            stmt.execute();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
    }

    public static Vector viewPaymentScreenManagementAuto(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.viewPaymentAutoManagement";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.INTEGER);
            stmt.registerOutParameter(2, OracleTypes.INTEGER);
            stmt.execute();
            PaymentScreenManagementModel paymentMangementManual = new PaymentScreenManagementModel(
                    stmt.getInt(1), stmt.getInt(2));

            listToReturn.add(paymentMangementManual);
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;
    }

    public static String getAutoOrManual(Connection con) {
        String manOrAuto = "";
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getManOrAuto";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.VARCHAR);
            stmt.execute();
            manOrAuto = stmt.getString(1);

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return manOrAuto;
    }

    public static void setPaymentScreenManagementAuto(Connection con,
            String start, String end, String manualOrAuto) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        // Vector listToReturn = new Vector();
        try {
            String procedureName = "cam_payment_pkg.setPaymentAutoManagement";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);

            stmt.setInt(1, Integer.parseInt(start));

            stmt.setInt(2, Integer.parseInt(end));
            stmt.setString(3, manualOrAuto);

            // stmt.registerOutParameter(2,OracleTypes.DATE);
            stmt.execute();

            // PaymentScreenManagementModel paymentMangementManual=new
            // PaymentScreenManagementModel(stmt.getDate(1),stmt.getDate(1));
            // /listToReturn.add(paymentMangementManual);
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        // return listToReturn;
    }

    public static Vector viewPaymentScreenManagementManual(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.viewPaymentManualManagement";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.VARCHAR);
            stmt.execute();
            PaymentScreenManagementModel paymentMangementManual = new PaymentScreenManagementModel(
                    stmt.getString(1));
            listToReturn.add(paymentMangementManual);
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;
    }

    public static void setPaymentScreenManagementManual(Connection con,
            String flag, String manualOrAuto) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.setPaymentManualManagement";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.setString(1, flag);
            stmt.setString(2, manualOrAuto);
            stmt.execute();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }

    }

    public static Vector searchPaymentEmployeeEmails(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.searchpaymentEmployeeemail";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            // stmt.setString(1,scm_name);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                PaymentDcmEmailsModel emailList = new PaymentDcmEmailsModel(rs.getInt("RECORD_ID"), rs.getString("EMPLOYEE_NAME"), rs.getString("EMPLOYEE_EMAIL"));

                listToReturn.add(emailList);
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;
    }

    public static Vector getPaymentEmployeeEmails(Connection con,
            String employeeName) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getpaymentEmployeeemail";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.setString(1, employeeName);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(2);
            while (rs.next()) {
                PaymentDcmEmailsModel emailList = new PaymentDcmEmailsModel(rs.getInt("RECORD_ID"), rs.getString("EMPLOYEE_NAME"), rs.getString("EMPLOYEE_EMAIL"));
                listToReturn.add(emailList);
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;
    }

    public static void setPaymentDcmEmails(Connection con, String scmName,
            String scmEmail) {
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            String procedureName = "cam_payment_pkg.updatePaymentDCMEmail";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.setString(1, scmName);
            stmt.setString(2, scmEmail);
            stmt.execute();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
    }

    public static Vector insertNewDcmEmails(Connection con, String scmName,
            String scmEmail) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.insertpaymentEmployeeemail";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            if (scmName == null || scmName.equals("")) {
                stmt.setString(1, "");
            } else {
                stmt.setString(1, scmName);
            }
            if (scmEmail == null || scmEmail.equals("")) {
                stmt.setString(2, "");
            } else {
                stmt.setString(2, scmEmail);
            }
            stmt.execute();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;
    }

    public static void updateDcmEmails(Connection con, String oldName,
            String employeeName, String employeeEmail) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.updatepaymentEmployeeemail";
            String query = null;

            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);

            if (oldName == null || oldName.equals("")) {
                stmt.setString(1, "");
            } else {
                stmt.setString(1, oldName);
            }

            if (employeeName == null || employeeName.equals("")) {
                stmt.setString(2, "");
            } else {
                stmt.setString(2, employeeName);
            }

            if (employeeEmail == null || employeeEmail.equals("")) {
                stmt.setString(3, "");
            } else {
                stmt.setString(3, employeeEmail);
            }

            stmt.execute();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }

    }

    public static void deleteEmployeeEmail(Connection con, String employeeName) {

        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.deletepaymentEmployeeemail";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.setString(1, employeeName);
            stmt.execute();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }

    }

    public static Vector searchScmStatus(Connection con, String dcmCode,
            String dcmName, String stateId, String reasonId, int pageNum) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getDcmByPaymentStatus";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?,?,?)}";
            stmt = con.prepareCall(query);

            if (dcmCode == null || dcmCode.equals("")) {
                stmt.setString(1, null);
            } else {
                stmt.setString(1, "'" + dcmCode + "'");
            }

            if (dcmName == null || dcmName.equals("")) {
                stmt.setString(2, null);
            } else {
                stmt.setString(2, "'" + dcmName + "'");
            }

            if (stateId == null || stateId.equals("")) {
                stmt.setInt(3, -1);
            } else {
                stmt.setInt(3, Integer.parseInt(stateId));
            }

            if (reasonId == null || reasonId.equals("")) {
                stmt.setInt(4, -1);
            } else {
                stmt.setInt(4, Integer.parseInt(reasonId));
            }

            stmt.setInt(5, pageNum);

            stmt.registerOutParameter(6, OracleTypes.CURSOR);
            // stmt.registerOutParameter(7,OracleTypes.VARCHAR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(6);
            while (rs.next()) {
                // if(rs.getString("DCM_CODE")!=null&&rs.getString("DCM_CODE").compareTo("")!=0){
                PaymentScmStatusModel payment = new PaymentScmStatusModel(rs.getInt("SCM_ID"), rs.getString("DCM_NAME"), rs.getString("DCM_CODE"));

                listToReturn.add(payment);
                // }
                // listToReturn.add();
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;
    }

    // for the statuses of the dcms
    public static Vector searchEveryScmStatus(Connection con, String dcmCode,
            String dcmName, String stateId, String reasonId, int pageNum) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getEverydcmbypaymentstatus";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?,?,?)}";
            stmt = con.prepareCall(query);

            if (dcmCode == null || dcmCode.equals("")) {
                stmt.setString(1, null);
            } else {
                stmt.setString(1, "'" + dcmCode + "'");
            }

            if (dcmName == null || dcmName.equals("")) {
                stmt.setString(2, null);
            } else {
                stmt.setString(2, "'" + dcmName + "'");
            }

            if (stateId == null || stateId.equals("")) {
                stmt.setInt(3, -1);
            } else {
                stmt.setInt(3, Integer.parseInt(stateId));
            }

            if (reasonId == null || reasonId.equals("")) {
                stmt.setInt(4, -1);
            } else {
                stmt.setInt(4, Integer.parseInt(reasonId));
            }

            stmt.setInt(5, pageNum);

            stmt.registerOutParameter(6, OracleTypes.CURSOR);
            // stmt.registerOutParameter(6,OracleTypes.VARCHAR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(6);
            while (rs.next()) {
                // PaymentScmStatusModel payment=new PaymentScmStatusModel
                // (rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6));
                EveryDcmStatusModel model = new EveryDcmStatusModel(rs.getInt("SCM_PAYMENT_STATUS_ID"), rs.getInt("PAYMENT_CAM_STATE_ID"), rs.getString("CAM_STATUS_FOR_PAYMENT"), rs.getInt("SCM_ID"), rs.getString("REASON"));
                listToReturn.add(model);
                // listToReturn.add();
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;

    }

    public static Vector searchDcmStatusDetails(Connection con, String dcmCode,
            String dcmName, String stkNumber) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getDcmDetails";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?)}";
            stmt = con.prepareCall(query);

            if (dcmName == null || dcmName.equals("")) {
                stmt.setString(1, null);
            } else {
                stmt.setString(1, "'" + dcmName + "'");
            }

            if (dcmCode == null || dcmCode.equals("")) {
                stmt.setString(2, null);
            } else {
                stmt.setString(2, "'" + dcmCode + "'");
            }

            if (stkNumber == null || stkNumber.equals("")) {
                stmt.setString(3, null);
            } else {
                stmt.setString(3, "'" + stkNumber + "'");
            }

            stmt.registerOutParameter(4, OracleTypes.CURSOR);

            stmt.execute();

            rs = (ResultSet) stmt.getObject(4);
            while (rs.next()) {
                // PaymentScmStatusModel payment=new PaymentScmStatusModel
                // (rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6));
                PaymentScmStatusModel model = new PaymentScmStatusModel(rs.getString("DCM_NAME"), rs.getString("DCM_CODE"), rs.getString("DCM_PHONE"), rs.getString("DCM_EMAIL"), rs.getString("CHANNEL_NAME"), rs.getString("CAM_STATUS_FOR_PAYMENT"), rs.getString("REASON"), rs.getString("stk_number"), rs.getString("STK_STATUS"));
                listToReturn.add(model);
                // listToReturn.add();
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        debug("list size: " + listToReturn.size());
        return listToReturn;
    }

    public static void updateEveryDcmPaymentStatus(Connection con, int typeId,
            String typeStatusId) throws Exception {
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            String procedureName = "cam_payment_pkg.UpdateEveryDcmPaymentStatus";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";

            stmt = con.prepareCall(query);

            stmt.setInt(1, typeId);

            stmt.setInt(2, Integer.parseInt(typeStatusId));
            stmt.execute();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }

    }

    public static void updateEveryDcmPaymentStatusReason(Connection con,
            int typeId, String typeStatusId) throws Exception {

        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.UpdateDcmPaymentStatusReason";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";

            stmt = con.prepareCall(query);

            stmt.setInt(1, typeId);

            stmt.setInt(2, Integer.parseInt(typeStatusId));
            stmt.execute();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }

    }

    public static Vector PaymentScmStatusDistributionCh(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.ScmPaymentStatusInDistribution";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                // System.out.println("take 1");

                PaymentScmStatusModel payment = new PaymentScmStatusModel(rs.getInt("scm_payment_status_id"), rs.getString("dcm_name"), rs.getString("dcm_code"));
                listToReturn.add(payment);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;

    }

    public static Vector PaymentScmStatusFranchiseCh(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.ScmPaymentStatusInFranchise";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                PaymentScmStatusModel payment = new PaymentScmStatusModel(rs.getInt("scm_payment_status_id"), rs.getString("dcm_name"), rs.getString("dcm_code"));
                listToReturn.add(payment);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;

    }

    public static Vector PaymentScmStatusComplementaryCh(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.scmpaymentstatusincomp";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                PaymentScmStatusModel payment = new PaymentScmStatusModel(rs.getInt("scm_payment_status_id"), rs.getString("dcm_name"), rs.getString("dcm_code"));
                listToReturn.add(payment);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;

    }

    public static int getDCMCount(Connection con, String dcmCode,
            String dcmName, String stateId, String reasonId, int channelId) {
        int listSize = 0;

        String whereClause = "";
        ResultSet rs = null;
        Statement stmt = null;
        int dcmCount = 0;
        try {
            stmt = con.createStatement();

            String query = " SELECT count(*) FROM"
                    + " CAM_PAYMENT_SCM_STATUS LEFT JOIN CAM_PAYMENT_CAM_STATE"
                    + " ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)"
                    + " LEFT JOIN GEN_DCM ON"
                    + " (CAM_PAYMENT_SCM_STATUS.SCM_ID = GEN_DCM.DCM_ID )"
                    + " WHERE ";

            if (channelId != -1) {
                whereClause += "GEN_DCM.CHANNEL_ID= " + channelId;
            } else {
                whereClause += "(GEN_DCM.CHANNEL_ID= 1 OR GEN_DCM.CHANNEL_ID=2 OR GEN_DCM.CHANNEL_ID=3) ";
            }

            if (dcmCode != null && !dcmCode.equals("")) {
                whereClause += " AND GEN_DCM.DCM_CODE = " + "'" + dcmCode + "'";
            }

            if (dcmName != null && !dcmName.equals("")) {
                whereClause += " AND GEN_DCM.DCM_NAME = " + "'" + dcmName + "'";
            }

            if (stateId != null && !stateId.equals("")) {
                whereClause += " AND CAM_PAYMENT_CAM_STATE.ID= " + stateId;
            }

            if (reasonId != null && !reasonId.equals("")) {
                whereClause += " AND CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID = "
                        + reasonId;
            }
            // debug("query: "+query+whereClause);
            rs = stmt.executeQuery(query + whereClause);
            while (rs.next()) {
                dcmCount = rs.getInt(1);
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeStatement(stmt);

        }
        return dcmCount;
    }

    public static Vector searchDistributionCh(Connection con, String dcmCode,
            String dcmName, String stateId, String reasonId, int pageNum) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.searchDistributionChannel";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?,?,?)}";
            stmt = con.prepareCall(query);

            if (dcmCode == null || dcmCode.equals("")) {
                stmt.setString(1, null);
            } else {
                stmt.setString(1, "'" + dcmCode + "'");
            }

            if (dcmName == null || dcmName.equals("")) {
                stmt.setString(2, null);
            } else {
                stmt.setString(2, "'" + dcmName + "'");
            }

            if (stateId == null || stateId.equals("")) {
                stmt.setInt(3, -1);
            } else {
                stmt.setInt(3, Integer.parseInt(stateId));
            }

            if (reasonId == null || reasonId.equals("")) {
                stmt.setInt(4, -1);
            } else {
                stmt.setInt(4, Integer.parseInt(reasonId));
            }

            stmt.setInt(5, pageNum);
            stmt.registerOutParameter(6, OracleTypes.CURSOR);

            stmt.execute();
            rs = (ResultSet) stmt.getObject(6);
            while (rs.next()) {
                PaymentScmStatusModel payment = new PaymentScmStatusModel(rs.getInt("SCM_ID"), rs.getString("DCM_NAME"), rs.getString("DCM_CODE"));
                listToReturn.add(payment);
                // listToReturn.add();
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;
    }

    public static Vector searchEveryDistributionStatus(Connection con,
            String dcmCode, String dcmName, String stateId, String reasonId,
            int pageNum) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getEveryDistributionstatus";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?,?,?)}";
            stmt = con.prepareCall(query);

            if (dcmCode == null || dcmCode.equals("")) {
                stmt.setString(1, null);
            } else {
                stmt.setString(1, "'" + dcmCode + "'");
            }

            if (dcmName == null || dcmName.equals("")) {
                stmt.setString(2, null);
            } else {
                stmt.setString(2, "'" + dcmName + "'");
            }

            if (stateId == null || stateId.equals("")) {
                stmt.setInt(3, -1);
            } else {
                stmt.setInt(3, Integer.parseInt(stateId));
            }

            if (reasonId == null || reasonId.equals("")) {
                stmt.setInt(4, -1);
            } else {
                stmt.setInt(4, Integer.parseInt(reasonId));
            }

            stmt.setInt(5, pageNum);

            stmt.registerOutParameter(6, OracleTypes.CURSOR);
            // stmt.registerOutParameter(6,OracleTypes.VARCHAR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(6);
            while (rs.next()) {
                // PaymentScmStatusModel payment=new PaymentScmStatusModel
                // (rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6));
                EveryDcmStatusModel model = new EveryDcmStatusModel(rs.getInt("SCM_PAYMENT_STATUS_ID"), rs.getInt("PAYMENT_CAM_STATE_ID"), rs.getString("CAM_STATUS_FOR_PAYMENT"), rs.getInt("SCM_ID"), rs.getString("REASON"));
                listToReturn.add(model);
                // listToReturn.add();
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;
    }

    public static Vector searchFranchiseCh(Connection con, String dcmCode,
            String dcmName, String stateId, String reasonId, int pageNum) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.searchFranchiseChannel";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?,?,?)}";
            stmt = con.prepareCall(query);

            if (dcmCode == null || dcmCode.equals("")) {
                stmt.setString(1, null);
            } else {
                stmt.setString(1, "'" + dcmCode + "'");
            }

            if (dcmName == null || dcmName.equals("")) {
                stmt.setString(2, null);
            } else {
                stmt.setString(2, "'" + dcmName + "'");
            }

            if (stateId == null || stateId.equals("")) {
                stmt.setInt(3, -1);
            } else {
                stmt.setInt(3, Integer.parseInt(stateId));
            }

            if (reasonId == null || reasonId.equals("")) {
                stmt.setInt(4, -1);
            } else {
                stmt.setInt(4, Integer.parseInt(reasonId));
            }

            stmt.setInt(5, pageNum);
            stmt.registerOutParameter(6, OracleTypes.CURSOR);

            stmt.execute();
            rs = (ResultSet) stmt.getObject(6);
            while (rs.next()) {
                PaymentScmStatusModel payment = new PaymentScmStatusModel(rs.getInt("SCM_ID"), rs.getString("DCM_NAME"), rs.getString("DCM_CODE"));
                listToReturn.add(payment);
                // listToReturn.add();
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;
    }

    public static Vector searchEveryFranchiseStatus(Connection con,
            String dcmCode, String dcmName, String stateId, String reasonId,
            int pageNum) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getEveryFranchisestatus";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?,?,?)}";
            stmt = con.prepareCall(query);

            if (dcmCode == null || dcmCode.equals("")) {
                stmt.setString(1, null);
            } else {
                stmt.setString(1, "'" + dcmCode + "'");
            }

            if (dcmName == null || dcmName.equals("")) {
                stmt.setString(2, null);
            } else {
                stmt.setString(2, "'" + dcmName + "'");
            }

            if (stateId == null || stateId.equals("")) {
                stmt.setInt(3, -1);
            } else {
                stmt.setInt(3, Integer.parseInt(stateId));
            }

            if (reasonId == null || reasonId.equals("")) {
                stmt.setInt(4, -1);
            } else {
                stmt.setInt(4, Integer.parseInt(reasonId));
            }

            stmt.setInt(5, pageNum);

            stmt.registerOutParameter(6, OracleTypes.CURSOR);
            // stmt.registerOutParameter(6,OracleTypes.VARCHAR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(6);
            while (rs.next()) {
                // PaymentScmStatusModel payment=new PaymentScmStatusModel
                // (rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6));
                EveryDcmStatusModel model = new EveryDcmStatusModel(rs.getInt("SCM_PAYMENT_STATUS_ID"), rs.getInt("PAYMENT_CAM_STATE_ID"), rs.getString("CAM_STATUS_FOR_PAYMENT"), rs.getInt("SCM_ID"), rs.getString("REASON"));
                listToReturn.add(model);
                // listToReturn.add();
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;
    }

    public static Vector searchComplementaryCh(Connection con, String dcmCode,
            String dcmName, String stateId, String reasonId, int pageNum) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.searchcomplementarychannel";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?,?,?)}";
            stmt = con.prepareCall(query);

            if (dcmCode == null || dcmCode.equals("")) {
                stmt.setString(1, null);
            } else {
                stmt.setString(1, "'" + dcmCode + "'");
            }

            if (dcmName == null || dcmName.equals("")) {
                stmt.setString(2, null);
            } else {
                stmt.setString(2, "'" + dcmName + "'");
            }

            if (stateId == null || stateId.equals("")) {
                stmt.setInt(3, -1);
            } else {
                stmt.setInt(3, Integer.parseInt(stateId));
            }

            if (reasonId == null || reasonId.equals("")) {
                stmt.setInt(4, -1);
            } else {
                stmt.setInt(4, Integer.parseInt(reasonId));
            }

            stmt.setInt(5, pageNum);
            stmt.registerOutParameter(6, OracleTypes.CURSOR);

            stmt.execute();
            rs = (ResultSet) stmt.getObject(6);
            while (rs.next()) {
                PaymentScmStatusModel payment = new PaymentScmStatusModel(rs.getInt("SCM_ID"), rs.getString("DCM_NAME"), rs.getString("DCM_CODE"));
                listToReturn.add(payment);
                // listToReturn.add();
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;
    }

    public static Vector searchEveryComplementaryStatus(Connection con,
            String dcmCode, String dcmName, String stateId, String reasonId,
            int pageNum) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getEverycomplementarystatus";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?,?,?)}";
            stmt = con.prepareCall(query);

            if (dcmCode == null || dcmCode.equals("")) {
                stmt.setString(1, null);
            } else {
                stmt.setString(1, "'" + dcmCode + "'");
            }

            if (dcmName == null || dcmName.equals("")) {
                stmt.setString(2, null);
            } else {
                stmt.setString(2, "'" + dcmName + "'");
            }

            if (stateId == null || stateId.equals("")) {
                stmt.setInt(3, -1);
            } else {
                stmt.setInt(3, Integer.parseInt(stateId));
            }

            if (reasonId == null || reasonId.equals("")) {
                stmt.setInt(4, -1);
            } else {
                stmt.setInt(4, Integer.parseInt(reasonId));
            }

            stmt.setInt(5, pageNum);

            stmt.registerOutParameter(6, OracleTypes.CURSOR);
            // stmt.registerOutParameter(6,OracleTypes.VARCHAR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(6);
            while (rs.next()) {
                // PaymentScmStatusModel payment=new PaymentScmStatusModel
                // (rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6));
                EveryDcmStatusModel model = new EveryDcmStatusModel(rs.getInt("SCM_PAYMENT_STATUS_ID"), rs.getInt("PAYMENT_CAM_STATE_ID"), rs.getString("CAM_STATUS_FOR_PAYMENT"), rs.getInt("SCM_ID"), rs.getString("REASON"));

                listToReturn.add(model);
                // listToReturn.add();
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;
    }

    public static int getNextImportPaymentFileId(Connection con) {
        int ret = 0;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getNextImportPaymentFileID";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.INTEGER);
            stmt.execute();
            ret = stmt.getInt(1);

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return ret;
    }

    public static int getNextImportChannelPaymentFileId(Connection con) {
        int ret = 0;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getnextchannelpaymentfileid";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.INTEGER);
            stmt.execute();
            ret = stmt.getInt(1);

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return ret;
    }

    public static void importPaymentState() {
        CallableStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = Utility.getConnection();
            String procedureName = "cam_payment_pkg.importPaymentState";
            String query = null;
            query = "{call " + procedureName + "()}";
            stmt = con.prepareCall(query);

            stmt.execute();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);
            Utility.closeConnection(con);


        }

    }

    public static void deletePaymentStatusImportData() {
        Statement stat = null;
        ResultSet res = null;
        Connection con = null;

        try {
            con = Utility.getConnection();
            stat = con.createStatement();

            String strSql = ("delete from  CAM_PAYMENT_DATA_IMPORT");

            stat.execute(strSql);
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Utility.closeResultSet(res);
            Utility.closeStatement(stat);
            Utility.closeConnection(con);

        }

    }

    public static void importChannelPaymentState() {
        CallableStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = Utility.getConnection();
            String procedureName = "cam_payment_pkg.importchannelpaymentstate";
            String query = null;
            query = "{call " + procedureName + "()}";
            stmt = con.prepareCall(query);
            // stmt.setString(1, importModel.getScmCode());
            // stmt.setString(2, importModel.getScmStatus());
            // stmt.setString(3, importModel.getFileID());
            // stmt.setString(4, importModel.getReason());

            stmt.execute();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);
            Utility.closeConnection(con);

        }

    }

    public static Vector getPaymentStatusReasons(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getReasons";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                PaymentStatusReasonModel reasons = new PaymentStatusReasonModel(
                        rs.getInt("REASON_ID"), rs.getString("REASON"));
                listToReturn.add(reasons);
                // listToReturn.add();
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;
    }

    public static Vector getPaymentReasons(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getReasons";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                PaymentStatusReasonModel reasons = new PaymentStatusReasonModel(
                        rs.getInt("REASON_ID"), rs.getString("REASON"), rs.getString("REASON_DESCRIPTION"));
                listToReturn.add(reasons);
                // listToReturn.add();
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;
    }

    public static Vector getPaymentReasonById(Connection con, String reasonId) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getReasonById";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(reasonId));
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(2);
            while (rs.next()) {
                PaymentStatusReasonModel reasons = new PaymentStatusReasonModel(
                        rs.getInt("REASON_ID"), rs.getString("REASON"), rs.getString("REASON_DESCRIPTION"));
                listToReturn.add(reasons);
                // listToReturn.add();
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;
    }

    public static void addPaymentStatusReasons(Connection con,
            String reasonName, String reasonDesc) {
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            String procedureName = "cam_payment_pkg.ADDREASON";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.setString(1, reasonName);
            stmt.setString(2, reasonDesc);

            stmt.execute();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }

    }

    public static void deletePaymentStatusReasons(Connection con,
            String reasonId) {
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            String procedureName = "cam_payment_pkg.DELETEPAYMENTREASONS";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(reasonId));

            stmt.execute();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }

    }

    public static void updatePaymentStatusReasons(Connection con,
            String reasonId, String reasonName, String reasonDesc) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.UPDATEPAYMENTREASONS";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(reasonId));
            stmt.setString(2, reasonName);
            stmt.setString(3, reasonDesc);

            stmt.execute();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }

    }

    public static Vector searchStkStatus(Connection con, String dcmCode,
            String dcmName, String stkNumber) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getSTKStatus";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?)}";
            stmt = con.prepareCall(query);

            if (dcmName == null || dcmName.equals("")) {
                stmt.setString(1, null);
            } else {
                stmt.setString(1, "'" + dcmName + "'");
            }

            if (dcmCode == null || dcmCode.equals("")) {
                stmt.setString(2, null);
            } else {
                stmt.setString(2, "'" + dcmCode + "'");
            }

            if (stkNumber == null || stkNumber.equals("")) {
                stmt.setString(3, null);
            } else {
                stmt.setString(3, "'" + stkNumber + "'");
            }

            stmt.registerOutParameter(4, OracleTypes.CURSOR);

            stmt.execute();
            rs = (ResultSet) stmt.getObject(4);
            while (rs.next()) {

                PaymentImportModel model = new PaymentImportModel();
                model.setId(rs.getInt("SCM_PAYMENT_STATUS_ID"));
                model.setScmCode(rs.getString("DCM_CODE"));
                model.setScmName(rs.getString("DCM_NAME"));
                model.setScmStatus(rs.getString("CAM_STATUS_FOR_PAYMENT"));
                model.setReason(rs.getString("REASON"));
                model.setStkNumber(rs.getString("STK_NUMBER"));
                model.setStkNumberStatus(rs.getString("STK_STATUS"));
                model.setStkStatusComment(rs.getString("STATUS_COMMENT"));

                listToReturn.add(model);
                // listToReturn.add();
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;
    }

    public static Vector getStkStatusCases(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getStkStatusCases";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);

            stmt.registerOutParameter(1, OracleTypes.CURSOR);

            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {

                STKStatusModel model = new STKStatusModel(rs.getInt("STK_STATUS_ID"), rs.getString("STK_STATUS"),
                        rs.getString("STK_STATUS_DESC"));
                listToReturn.add(model);
                // listToReturn.add();
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;
    }

    public static void updateStkStatus(Connection con,
            String dcmPaymentStatusId, String statusId, String comment) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.updatedcmstkstatus";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(dcmPaymentStatusId));
            stmt.setInt(2, Integer.parseInt(statusId));
            stmt.setString(3, comment);

            stmt.execute();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }

    }

    public static void insertDcmSTKStatus(Connection con, String dcmCode,
            String stkNumber, String stkStatus) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.insertDCMSTKStatus";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setString(1, dcmCode);
            stmt.setString(2, stkNumber);
            stmt.setInt(3, Integer.parseInt(stkStatus));

            stmt.execute();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }

    }

    public static void insertDcmPaymentStatus(Connection con, String dcmCode,
            String statusId, String reasonId) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.insertDCMPaymentStatus";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setString(1, dcmCode);
            stmt.setInt(2, Integer.parseInt(statusId));
            stmt.setInt(3, Integer.parseInt(reasonId));

            stmt.execute();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }

    }

    public static Vector getPaymentTypeExcelAttributes(Connection con,
            String paymentTypeId) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getpaymentExcelManagement";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);

            stmt.setInt(1, Integer.parseInt(paymentTypeId));

            stmt.registerOutParameter(2, OracleTypes.CURSOR);

            stmt.execute();
            rs = (ResultSet) stmt.getObject(2);
            while (rs.next()) {

                PaymentTypeExcelModel model = new PaymentTypeExcelModel();
                model.setPaymentExcelManagementId(rs.getInt("PAYMENT_EXCEL_MENEGEMENT_ID"));
                model.setPaymentType(rs.getString("PAYMENT_TYPE_NAME"));
                model.setExcelSheetNumber(rs.getInt("EXCEL_SHEET_NUMBER"));
                model.setExcelSheetName(rs.getString("EXCEL_SHEET_NAME"));
                model.setSqlTempId(rs.getInt("SQL_TEMPLATE_ID"));
                //	if(rs.getInt("SQL_TEMPLATE_ID")!=-1){
                model.setExcelSheetSqlStatement(rs.getString("EXCEL_SHEET_SQL_STATEMENT"));

                //	}else{
                //model.setExcelSheetSqlStatement(rs.getString("EXCEL_SHEET_SQL_STATEMENT"));
                //}

                listToReturn.add(model);
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return listToReturn;
    }

    public static PaymentTypeExcelModel getPaymentExcelSheetAttributes(
            Connection con, String paymentTypeExcelSheetId) {
        PaymentTypeExcelModel model = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getPaymentExcelSheetAttributes";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);

            stmt.setInt(1, Integer.parseInt(paymentTypeExcelSheetId));

            stmt.registerOutParameter(2, OracleTypes.CURSOR);

            stmt.execute();
            rs = (ResultSet) stmt.getObject(2);
            while (rs.next()) {

                model = new PaymentTypeExcelModel();
                model.setPaymentExcelManagementId(rs.getInt("PAYMENT_EXCEL_MENEGEMENT_ID"));
                model.setPaymentType(rs.getString("PAYMENT_TYPE_NAME"));
                model.setExcelSheetNumber(rs.getInt("EXCEL_SHEET_NUMBER"));
                model.setExcelSheetName(rs.getString("EXCEL_SHEET_NAME"));
                model.setSqlTempId(rs.getInt("SQL_TEMPLATE_ID"));

                if (rs.getInt("SQL_TEMPLATE_ID") != -1) {
                    model.setExcelSheetSqlStatement(rs.getString("EXCEL_SHEET_SQL_STATEMENT"));
                    // SQL_template_id


                } else {
                    model.setExcelSheetSqlStatement(rs.getString("EXCEL_SHEET_SQL_STATEMENT"));
                }
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        return model;
    }

    public static void updatePaymentExcelSheetNumber(Connection con,
            String paymentExcelId, String sheetNumber) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.updatePaymentExcelSheetNumber";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(paymentExcelId));
            stmt.setInt(2, Integer.parseInt(sheetNumber));

            stmt.execute();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
    }

    public static void updatePaymentExcelSheetName(Connection con,
            String paymentExcelId, String sheetName) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.updatePaymentExcelSheetName";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(paymentExcelId));

            stmt.setString(2, sheetName);

            stmt.execute();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
    }

    public static void updatePaymentExcelSheet(Connection con,
            String paymentExcelId, String sheetNumber, String sheetName,
            String sqlStatement, String templateName, String tempId) {
        CallableStatement stmt = null;
        ResultSet rs = null;


        try {
            if (Integer.parseInt(tempId) == 1) {
                String sqlString = "update CAM_PAYMENT_EXCEL_MANAGEMENT set EXCEL_SHEET_NUMBER = "
                        + sheetNumber
                        + ", EXCEL_SHEET_NAME = '"
                        + sheetName
                        + "', SQL_TEMPLATE_ID = " + tempId + " ,"
                        + " EXCEL_SHEET_SQL_STATEMENT = "
                        + "?"
                        + " where PAYMENT_EXCEL_MENEGEMENT_ID = " + paymentExcelId;
                System.out.println("sql:" + sqlString);
                oracle.jdbc.OraclePreparedStatement pstmt = (oracle.jdbc.OraclePreparedStatement) con.prepareStatement(sqlString);

                pstmt.setStringForClob(1, templateName);
                System.out.println(templateName);
                System.out.println(sqlString);
                pstmt.execute();
                pstmt.close();


                // String procedureName =
                // "cam_payment_pkg.updateExcelSheetAttributes";
                // String query = null;
                // query = "{call " + procedureName + "(?,?,?,?)}";
                // stmt = con.prepareCall(query);
                //			
                // stmt.setInt(1, Integer.parseInt(paymentExcelId));
                //			
                // stmt.setInt(2, Integer.parseInt(sheetNumber));
                //			
                // stmt.setString(3, sheetName);
                //			
                // stmt.setString(4, sqlStatement);
            } else {
                String sqlString = "update CAM_PAYMENT_EXCEL_MANAGEMENT set EXCEL_SHEET_NUMBER = "
                        + sheetNumber
                        + ", EXCEL_SHEET_NAME = '"
                        + sheetName
                        + "', SQL_TEMPLATE_ID = " + tempId
                        + ", EXCEL_SHEET_SQL_STATEMENT = "
                        + "?"
                        + " where PAYMENT_EXCEL_MENEGEMENT_ID = " + paymentExcelId;
                System.out.println("sql:" + sqlString);
                oracle.jdbc.OraclePreparedStatement pstmt = (oracle.jdbc.OraclePreparedStatement) con.prepareStatement(sqlString);

                pstmt.setStringForClob(1, sqlStatement);
                System.out.println(sqlStatement);
                System.out.println(sqlString);
                pstmt.execute();
                pstmt.close();
            }


        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }

    }

    public static void insertPaymentExcelAttributes(Connection con,
            String paymentTypeId, String sheetNumber, String sheetName,
            String sqlStatement, String tempId, String tempSQLId) {
        CallableStatement stmt = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            if (Integer.parseInt(tempId) == 1) {

                String sqlString = "insert into CAM_PAYMENT_EXCEL_MANAGEMENT(PAYMENT_EXCEL_MENEGEMENT_ID,PAYMENT_TYPE_ID,EXCEL_SHEET_NUMBER,EXCEL_SHEET_NAME,SQL_TEMPLATE_ID , EXCEL_SHEET_SQL_STATEMENT)"
                        + "   values("
                        + "PAYMENT_EXCEL_MANAGEMENT_SEQ.nextval,"
                        + paymentTypeId
                        + ","
                        + sheetNumber
                        + " ,'"
                        + sheetName
                        + "' , " + tempId + " , '" + tempSQLId + "')";
                System.out.println("sql:" + sqlString);
                st = con.createStatement();
                st.execute(sqlString);

                st.close();

            } else if (Integer.parseInt(tempId) == -1) {
                String sqlString = "insert into CAM_PAYMENT_EXCEL_MANAGEMENT(PAYMENT_EXCEL_MENEGEMENT_ID,PAYMENT_TYPE_ID,EXCEL_SHEET_NUMBER,EXCEL_SHEET_NAME,SQL_TEMPLATE_ID,EXCEL_SHEET_SQL_STATEMENT)"
                        + "   values("
                        + "PAYMENT_EXCEL_MANAGEMENT_SEQ.nextval,"
                        + paymentTypeId
                        + ","
                        + sheetNumber
                        + " ,'"
                        + sheetName
                        + "' ,-1, '" + sqlStatement + "')";
                System.out.println("sql:" + sqlString);


                st = con.createStatement();
                st.execute(sqlString);

                st.close();


            }





        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
        }
    }

    public static boolean checkExcelSheetUniqueness(Connection con,
            String paymentTypeId, String sheetNumber) {
        String result = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.checkExcelSheetUniqueness";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);

            stmt.setInt(1, Integer.parseInt(paymentTypeId));

            stmt.setInt(2, Integer.parseInt(sheetNumber));

            stmt.registerOutParameter(3, OracleTypes.VARCHAR);

            stmt.execute();

            result = stmt.getString(3);

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);

        }
        if (result.compareTo("data") == 0) {
            return false;
        } else {
            return true;
        }
    }

    public static Vector getDeleteClosedPaymentForm(Connection con) {

        Vector deleteClosedPaymentVector = new Vector();
        PaymentModel paymentModel = new PaymentModel();

        try {

            Statement stmt = con.createStatement();
            String sqlString = "Select payment_detail.PAYMENT_DETAIL_ID , payment_detail.PAYMENT_NAME , payment_detail.PAYMENT_START_DATE , payment_detail.PAYMENT_END_DATE ,"
                    + " payment_status_type.PAYMENT_STATUS_TYPE_NAME , payment_type.PAYMENT_TYPE_NAME , cam_payment.memo_id ,payment_detail.payment_status_type_id,"
                    + " payment_detail.payment_type_id  From payment_detail , payment_status_type , payment_type ,cam_payment  WHERE payment_detail."
                    + " payment_detail_id=cam_payment.payment_id  AND cam_payment.memo_id IS null AND payment_detail.payment_status_type_id ="
                    + " payment_status_type.payment_status_type_id AND payment_detail.payment_type_id = payment_type.payment_type_id "
                   // + " AND payment_detail.payment_status_type_id != 2"
                    + " and payment_detail.payment_detail_id not in (select payment_id from cam_payment where memo_id is not null and payment_id = payment_detail_id)"
                    + " group by payment_detail.payment_detail_id, payment_detail.payment_name,"
                    + "payment_detail.payment_start_date, payment_detail.payment_end_date,"
                    + "payment_status_type.payment_status_type_name,"
                    + "payment_type.payment_type_name, cam_payment.memo_id,"
                    + "payment_detail.payment_status_type_id, payment_detail.payment_type_id";


            System.out.println(sqlString);

            ResultSet deleteClosedPaymentRS = stmt.executeQuery(sqlString);
            while (deleteClosedPaymentRS.next()) {
                paymentModel = new PaymentModel(con, deleteClosedPaymentRS);
                paymentModel.setPaymentStatusTypeName(deleteClosedPaymentRS.getString("PAYMENT_STATUS_TYPE_NAME"));

                deleteClosedPaymentVector.add(paymentModel);
            }
            deleteClosedPaymentRS.close();
            stmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return deleteClosedPaymentVector;
    }

    public static Vector getDeleteClosedPaymentForm(Connection con, String paymentName) {

        Vector deleteClosedPaymentVector = new Vector();
        PaymentModel paymentModel = new PaymentModel();

        try {

            Statement stmt = con.createStatement();

            String sqlString = "Select payment_detail.PAYMENT_DETAIL_ID , payment_detail.PAYMENT_NAME , payment_detail.PAYMENT_START_DATE , payment_detail.PAYMENT_END_DATE ,"
                    + " payment_status_type.PAYMENT_STATUS_TYPE_NAME , payment_type.PAYMENT_TYPE_NAME , cam_payment.memo_id ,payment_detail.payment_status_type_id,"
                    + " payment_detail.payment_type_id  From payment_detail , payment_status_type , payment_type ,cam_payment  WHERE payment_detail."
                    + " payment_detail_id=cam_payment.payment_id  AND cam_payment.memo_id IS null AND payment_detail.payment_status_type_id ="
                    + " payment_status_type.payment_status_type_id AND payment_detail.payment_type_id = payment_type.payment_type_id "
                    + " AND payment_detail.payment_status_type_id != 2"
                    + " and payment_detail.payment_detail_id not in (select payment_id from cam_payment where memo_id is not null and payment_id = payment_detail_id)"
                    + " AND payment_detail.payment_type_id = payment_type.payment_type_id AND PAYMENT_NAME = '" + paymentName + "'"
                    + " group by payment_detail.payment_detail_id, payment_detail.payment_name,"
                    + "payment_detail.payment_start_date, payment_detail.payment_end_date,"
                    + "payment_status_type.payment_status_type_name,"
                    + "payment_type.payment_type_name, cam_payment.memo_id,"
                    + "payment_detail.payment_status_type_id, payment_detail.payment_type_id";

            System.out.println(sqlString);


            ResultSet deleteClosedPaymentRS = stmt.executeQuery(sqlString);
            while (deleteClosedPaymentRS.next()) {
                paymentModel = new PaymentModel(con, deleteClosedPaymentRS);
                paymentModel.setPaymentStatusTypeName(deleteClosedPaymentRS.getString("PAYMENT_STATUS_TYPE_NAME"));

                deleteClosedPaymentVector.add(paymentModel);
            }
            deleteClosedPaymentRS.close();
            stmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return deleteClosedPaymentVector;
    }

    public static Vector getDeleteClosedPaymentForm(Connection con, int paymentId) {

        Vector deleteClosedPaymentVector = new Vector();
        PaymentModel paymentModel = new PaymentModel();

        try {
            Statement stmt = con.createStatement();
            
                        String sqlString = "Select payment_detail.PAYMENT_DETAIL_ID , payment_detail.PAYMENT_NAME , payment_detail.PAYMENT_START_DATE , payment_detail.PAYMENT_END_DATE ,"
                    + " payment_status_type.PAYMENT_STATUS_TYPE_NAME , payment_type.PAYMENT_TYPE_NAME , cam_payment.memo_id ,payment_detail.payment_status_type_id,"
                    + " payment_detail.payment_type_id  From payment_detail , payment_status_type , payment_type ,cam_payment  WHERE payment_detail."
                    + " payment_detail_id=cam_payment.payment_id  AND cam_payment.memo_id IS null AND payment_detail.payment_status_type_id ="
                    + " payment_status_type.payment_status_type_id AND payment_detail.payment_type_id = payment_type.payment_type_id "
                 //   + " AND payment_detail.payment_status_type_id != 2"
                    + " and payment_detail.payment_detail_id not in (select payment_id from cam_payment where memo_id is not null and payment_id = payment_detail_id)"
                    + " AND payment_detail.payment_type_id = payment_type.payment_type_id AND PAYMENT_DETAIL_ID = " + paymentId + ""
                    + " group by payment_detail.payment_detail_id, payment_detail.payment_name,"
                    + "payment_detail.payment_start_date, payment_detail.payment_end_date,"
                    + "payment_status_type.payment_status_type_name,"
                    + "payment_type.payment_type_name, cam_payment.memo_id,"
                    + "payment_detail.payment_status_type_id, payment_detail.payment_type_id";
            
          

            System.out.println(sqlString);

            ResultSet deleteClosedPaymentRS = stmt.executeQuery(sqlString);
            while (deleteClosedPaymentRS.next()) {
                paymentModel = new PaymentModel(con, deleteClosedPaymentRS);
                paymentModel.setPaymentStatusTypeName(deleteClosedPaymentRS.getString("PAYMENT_STATUS_TYPE_NAME"));

                deleteClosedPaymentVector.add(paymentModel);
            }
            deleteClosedPaymentRS.close();
            stmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return deleteClosedPaymentVector;
    }

    public static Vector getDeleteClosedPaymentForm(Connection con, int paymentId, String paymentName) {

        Vector deleteClosedPaymentVector = new Vector();
        PaymentModel paymentModel = new PaymentModel();

        try {

            Statement stmt = con.createStatement();
            
            
            
             String sqlString = "Select payment_detail.PAYMENT_DETAIL_ID , payment_detail.PAYMENT_NAME , payment_detail.PAYMENT_START_DATE , payment_detail.PAYMENT_END_DATE ,"
                    + " payment_status_type.PAYMENT_STATUS_TYPE_NAME , payment_type.PAYMENT_TYPE_NAME , cam_payment.memo_id ,payment_detail.payment_status_type_id,"
                    + " payment_detail.payment_type_id  From payment_detail , payment_status_type , payment_type ,cam_payment  WHERE payment_detail."
                    + " payment_detail_id=cam_payment.payment_id  AND cam_payment.memo_id IS null AND payment_detail.payment_status_type_id ="
                    + " payment_status_type.payment_status_type_id AND payment_detail.payment_type_id = payment_type.payment_type_id "
                  //  + " AND payment_detail.payment_status_type_id != 2"
                    + " and payment_detail.payment_detail_id not in (select payment_id from cam_payment where memo_id is not null and payment_id = payment_detail_id)"
                    + " AND payment_detail.payment_type_id = payment_type.payment_type_id AND PAYMENT_DETAIL_ID = " + paymentId + " AND PAYMENT_NAME = '" + paymentName + "'"
                    + " group by payment_detail.payment_detail_id, payment_detail.payment_name,"
                    + "payment_detail.payment_start_date, payment_detail.payment_end_date,"
                    + "payment_status_type.payment_status_type_name,"
                    + "payment_type.payment_type_name, cam_payment.memo_id,"
                    + "payment_detail.payment_status_type_id, payment_detail.payment_type_id";
         
            System.out.println("Sql:" + sqlString);


            ResultSet deleteClosedPaymentRS = stmt.executeQuery(sqlString);
            while (deleteClosedPaymentRS.next()) {
                paymentModel = new PaymentModel(con, deleteClosedPaymentRS);

                paymentModel.setPaymentStatusTypeName(deleteClosedPaymentRS.getString("PAYMENT_STATUS_TYPE_NAME"));


                deleteClosedPaymentVector.add(paymentModel);
            }
            deleteClosedPaymentRS.close();
            stmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return deleteClosedPaymentVector;
    }

    public static void updatePaymentStatusTypeId(Connection con, String paymentDetailId) {

        int detailId = Integer.parseInt(paymentDetailId);
        String updatequery = "update payment_detail set payment_status_type_id = 2 where payment_detail_id =" + detailId + "";
        DBUtil.executeSQL(updatequery, con);
    }

    public static void deletePaymentStatusTypeId(Connection con, String paymentDetailId) {

        int detailId = Integer.parseInt(paymentDetailId);
        Statement stat = null;


        try {
            con = Utility.getConnection();
            stat = con.createStatement();

            String strSql = ("delete from cam_payment where cam_payment.PAYMENT_ID = " + detailId);

            stat.execute(strSql);
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            Utility.closeStatement(stat);
            Utility.closeConnection(con);

        }

    }
    ////////////////////////////////////////////////////// bye

    public static Vector getReleasePaymentForm(Connection con) {

        Vector releasePaymentVector = new Vector();
        PaymentModel paymentModel = new PaymentModel();

        try {

            Statement stmt = con.createStatement();
            String sqlString = "Select payment_detail.PAYMENT_DETAIL_ID , scm_id, payment_detail.PAYMENT_NAME ,payment_detail.payment_status_type_id ,payment_detail.PAYMENT_START_DATE , payment_detail.PAYMENT_END_DATE , cam_payment.RECORD_ID , cam_payment.PAYMENT_ID , cam_payment.SCM_COMMISSION_VALUE , payment_detail.PAYMENT_TYPE_ID , gen_dcm.DCM_NAME , gen_dcm.DCM_CODE,PAYMENT_TYPE.PAYMENT_TYPE_NAME FROM payment_detail ,cam_payment,gen_dcm,payment_type WHERE cam_payment.PAYMENT_ID=payment_detail.PAYMENT_DETAIL_ID AND cam_payment.FROZEN_FLAG='1' AND payment_detail.PAYMENT_TYPE_ID=payment_type.payment_type_id AND gen_dcm.DCM_ID=cam_payment.SCM_ID";

            ResultSet releasePaymentRS = stmt.executeQuery(sqlString);
            while (releasePaymentRS.next()) {
                paymentModel = new PaymentModel(con, releasePaymentRS);
                paymentModel.setDCMName(releasePaymentRS.getString("DCM_NAME"));
                paymentModel.setDCMCode(releasePaymentRS.getString("DCM_CODE"));
                paymentModel.setPaymentSCMId(releasePaymentRS.getInt("SCM_ID"));
                paymentModel.setPaymentSCMCommissionValue(releasePaymentRS.getInt("SCM_COMMISSION_VALUE"));
                paymentModel.setPaymentRecordId(releasePaymentRS.getInt("RECORD_ID"));
                releasePaymentVector.add(paymentModel);
            }
            releasePaymentRS.close();
            stmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return releasePaymentVector;
    }

    ///////////////////////////////////////////////////////////////////////////
    public static void releasePayment(Connection con, int paymentId, String scmId, String reason) {
        String sqlString = "";
        try {

            Statement stmt = con.createStatement();
            sqlString = "update cam_payment set frozen_flag = 0 where scm_id =" + scmId + " and payment_id =" + paymentId;
            stmt.execute(sqlString);
            sqlString = "insert into cam_payment_frozen_status (PAYMENT_ID,FROZEN_STATUS,SCM_ID,RTYPE ) values (" + paymentId + ",'" + reason + "'," + scmId + ",1 )";
            System.out.println(sqlString);
            stmt.execute(sqlString);
            stmt.close();

        } catch (Exception ex) {
            System.out.println(sqlString);
            ex.printStackTrace();
        }
    }

    public static void freezPayment(Connection con, int paymentId, String scmId, String reason) {
        String sqlString = "";
        try {

            Statement stmt = con.createStatement();
            sqlString = "update cam_payment set frozen_flag = 1 where scm_id =" + scmId + " and payment_id =" + paymentId;
            stmt.execute(sqlString);
            sqlString = "insert into cam_frozen_status (PAYMENT_ID,FROZEN_STATUS,SCM_ID,RTYPE ) values (" + paymentId + ",'" + reason + "'," + scmId + ",2 )";
            System.out.println(sqlString);

            stmt.execute(sqlString);


            stmt.close();

        } catch (Exception ex) {
            System.out.println(sqlString);
            ex.printStackTrace();
        }
    }

    public static Vector getReleasePaymentFormByPaymentTypeName(Connection con, int paymentType) {

        Vector releasePaymentVector = new Vector();
        PaymentModel paymentModel = new PaymentModel();

        try {

            Statement stmt = con.createStatement();
            String sqlString = " Select payment_detail.PAYMENT_DETAIL_ID ,PAYMENT_STATUS_TYPE_ID , scm_id, payment_detail.PAYMENT_NAME , payment_detail.PAYMENT_START_DATE , payment_detail.PAYMENT_END_DATE , cam_payment.RECORD_ID , cam_payment.PAYMENT_ID , cam_payment.SCM_COMMISSION_VALUE , payment_detail.PAYMENT_TYPE_ID , gen_dcm.DCM_NAME , gen_dcm.DCM_CODE,PAYMENT_TYPE.PAYMENT_TYPE_NAME FROM payment_detail ,cam_payment,gen_dcm,payment_type WHERE cam_payment.PAYMENT_ID=payment_detail.PAYMENT_DETAIL_ID AND cam_payment.FROZEN_FLAG='1' AND payment_detail.PAYMENT_TYPE_ID=payment_type.payment_type_id AND gen_dcm.DCM_ID=cam_payment.SCM_ID AND payment_detail.PAYMENT_TYPE_ID=" + paymentType + "";

            ResultSet releasePaymentRS = stmt.executeQuery(sqlString);
            while (releasePaymentRS.next()) {
                paymentModel = new PaymentModel(con, releasePaymentRS);
                paymentModel.setDCMName(releasePaymentRS.getString("DCM_NAME"));
                paymentModel.setDCMCode(releasePaymentRS.getString("DCM_CODE"));

                paymentModel.setPaymentSCMId(releasePaymentRS.getInt("SCM_ID"));

//               paymentModel.setPaymentAccrualId(releasePaymentRS.getInt("ACCRUAL_ID"));
//               paymentModel.setPaymentDeductionValue(releasePaymentRS.getInt("DEDUCTIONS_VALUE"));
//               paymentModel.setPaymentFlag(releasePaymentRS.getString("FLAG"));
//               paymentModel.setPaymentMemoId(releasePaymentRS.getInt("MEMO_ID"));
//               paymentModel.setPaymentFrozenFlag(releasePaymentRS.getString("FROZEN_FLAG"));
                paymentModel.setPaymentSCMCommissionValue(releasePaymentRS.getInt("SCM_COMMISSION_VALUE"));
                paymentModel.setPaymentRecordId(releasePaymentRS.getInt("RECORD_ID"));
                releasePaymentVector.add(paymentModel);
            }
            releasePaymentRS.close();
            stmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return releasePaymentVector;
    }
    /////////////////////////////////////////////////////////////////

    /**
     *
     * @param con
     * @param paymentId
     * @return
     */
    public static Vector getReleasePaymentFormByPaymentName(Connection con, String paymentName) {

        Vector releasePaymentVector = new Vector();
        PaymentModel paymentModel = new PaymentModel();

        try {

            Statement stmt = con.createStatement();
            String sqlString = " Select payment_detail.PAYMENT_DETAIL_ID ,scm_id,PAYMENT_STATUS_TYPE_ID,  payment_detail.PAYMENT_NAME , payment_detail.PAYMENT_START_DATE , payment_detail.PAYMENT_END_DATE , cam_payment.RECORD_ID , cam_payment.PAYMENT_ID , cam_payment.SCM_COMMISSION_VALUE , payment_detail.PAYMENT_TYPE_ID , gen_dcm.DCM_NAME , gen_dcm.DCM_CODE,PAYMENT_TYPE.PAYMENT_TYPE_NAME,PAYMENT_STATUS_TYPE_ID FROM payment_detail ,cam_payment,gen_dcm,payment_type WHERE cam_payment.PAYMENT_ID=payment_detail.PAYMENT_DETAIL_ID AND cam_payment.FROZEN_FLAG='1' AND payment_detail.PAYMENT_TYPE_ID=payment_type.payment_type_id AND gen_dcm.DCM_ID=cam_payment.SCM_ID  AND  PAYMENT_NAME = '" + paymentName + "'";

            System.out.println(sqlString);
            ResultSet releasePaymentRS = stmt.executeQuery(sqlString);
            while (releasePaymentRS.next()) {
                paymentModel = new PaymentModel(con, releasePaymentRS);
                paymentModel.setDCMName(releasePaymentRS.getString("DCM_NAME"));
                paymentModel.setDCMCode(releasePaymentRS.getString("DCM_CODE"));
                paymentModel.setPaymentSCMId(releasePaymentRS.getInt("SCM_ID"));
//               paymentModel.setPaymentAccrualId(releasePaymentRS.getInt("ACCRUAL_ID"));
//               paymentModel.setPaymentDeductionValue(releasePaymentRS.getInt("DEDUCTIONS_VALUE"));
//               paymentModel.setPaymentFlag(releasePaymentRS.getString("FLAG"));
//               paymentModel.setPaymentMemoId(releasePaymentRS.getInt("MEMO_ID"));
//               paymentModel.setPaymentFrozenFlag(releasePaymentRS.getString("FROZEN_FLAG"));
                paymentModel.setPaymentSCMCommissionValue(releasePaymentRS.getInt("SCM_COMMISSION_VALUE"));
                paymentModel.setPaymentRecordId(releasePaymentRS.getInt("RECORD_ID"));
                releasePaymentVector.add(paymentModel);
            }
            releasePaymentRS.close();
            stmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return releasePaymentVector;
    }
    ///////////////////////////////////////////////////////////////////////   

    public static Vector getReleasePaymentFormByDCMCode(Connection con, String DCMCode) {

        Vector releasePaymentVector = new Vector();
        PaymentModel paymentModel = new PaymentModel();

        try {

            Statement stmt = con.createStatement();
            String sqlString = " Select payment_detail.PAYMENT_DETAIL_ID ,scm_id,PAYMENT_STATUS_TYPE_ID,  payment_detail.PAYMENT_NAME , payment_detail.PAYMENT_START_DATE , payment_detail.PAYMENT_END_DATE , cam_payment.RECORD_ID , cam_payment.PAYMENT_ID , cam_payment.SCM_COMMISSION_VALUE , payment_detail.PAYMENT_TYPE_ID , gen_dcm.DCM_NAME , gen_dcm.DCM_CODE,PAYMENT_TYPE.PAYMENT_TYPE_NAME FROM payment_detail ,cam_payment,gen_dcm,payment_type WHERE cam_payment.PAYMENT_ID=payment_detail.PAYMENT_DETAIL_ID AND cam_payment.FROZEN_FLAG='1' AND payment_detail.PAYMENT_TYPE_ID=payment_type.payment_type_id  AND gen_dcm.DCM_ID=cam_payment.SCM_ID AND DCM_CODE = '" + DCMCode + "'";

            ResultSet releasePaymentRS = stmt.executeQuery(sqlString);
            while (releasePaymentRS.next()) {
                paymentModel = new PaymentModel(con, releasePaymentRS);
                paymentModel.setDCMName(releasePaymentRS.getString("DCM_NAME"));
                paymentModel.setDCMCode(releasePaymentRS.getString("DCM_CODE"));

                paymentModel.setPaymentSCMId(releasePaymentRS.getInt("SCM_ID"));


//               paymentModel.setPaymentAccrualId(releasePaymentRS.getInt("ACCRUAL_ID"));
//               paymentModel.setPaymentDeductionValue(releasePaymentRS.getInt("DEDUCTIONS_VALUE"));
//               paymentModel.setPaymentFlag(releasePaymentRS.getString("FLAG"));
//               paymentModel.setPaymentMemoId(releasePaymentRS.getInt("MEMO_ID"));
//               paymentModel.setPaymentFrozenFlag(releasePaymentRS.getString("FROZEN_FLAG"));
                paymentModel.setPaymentSCMCommissionValue(releasePaymentRS.getInt("SCM_COMMISSION_VALUE"));
                paymentModel.setPaymentRecordId(releasePaymentRS.getInt("RECORD_ID"));
                releasePaymentVector.add(paymentModel);
            }
            releasePaymentRS.close();
            stmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return releasePaymentVector;
    }
/////////////////////////////////////////////////////////////////////////////////////        

    public static Vector getFreezePaymentForm(Connection con) {

        Vector releasePaymentVector = new Vector();
        PaymentModel paymentModel = new PaymentModel();

        try {

            Statement stmt = con.createStatement();
            String sqlString = " Select payment_detail.PAYMENT_DETAIL_ID ,scm_id , payment_detail.PAYMENT_NAME ,PAYMENT_STATUS_TYPE_ID , payment_detail.PAYMENT_START_DATE , payment_detail.PAYMENT_END_DATE , cam_payment.RECORD_ID , cam_payment.PAYMENT_ID , cam_payment.SCM_COMMISSION_VALUE , payment_detail.PAYMENT_TYPE_ID , gen_dcm.DCM_NAME , gen_dcm.DCM_CODE,PAYMENT_TYPE.PAYMENT_TYPE_NAME FROM payment_detail ,cam_payment,gen_dcm,payment_type WHERE cam_payment.PAYMENT_ID=payment_detail.PAYMENT_DETAIL_ID AND cam_payment.FROZEN_FLAG='0' AND MEMO_ID IS NULL  AND payment_detail.PAYMENT_TYPE_ID=payment_type.payment_type_id AND gen_dcm.DCM_ID=cam_payment.SCM_ID";

            ResultSet releasePaymentRS = stmt.executeQuery(sqlString);
            while (releasePaymentRS.next()) {
                paymentModel = new PaymentModel(con, releasePaymentRS);
                paymentModel.setDCMName(releasePaymentRS.getString("DCM_NAME"));
                paymentModel.setDCMCode(releasePaymentRS.getString("DCM_CODE"));
                paymentModel.setPaymentSCMId(releasePaymentRS.getInt("SCM_ID"));
//               paymentModel.setPaymentAccrualId(releasePaymentRS.getInt("ACCRUAL_ID"));
//               paymentModel.setPaymentDeductionValue(releasePaymentRS.getInt("DEDUCTIONS_VALUE"));
//               paymentModel.setPaymentFlag(releasePaymentRS.getString("FLAG"));
//               paymentModel.setPaymentMemoId(releasePaymentRS.getInt("MEMO_ID"));
//               paymentModel.setPaymentFrozenFlag(releasePaymentRS.getString("FROZEN_FLAG"));
                paymentModel.setPaymentSCMCommissionValue(releasePaymentRS.getInt("SCM_COMMISSION_VALUE"));
                paymentModel.setPaymentRecordId(releasePaymentRS.getInt("RECORD_ID"));
                releasePaymentVector.add(paymentModel);
            }
            releasePaymentRS.close();
            stmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return releasePaymentVector;
    }
    /////////////////////////////////////////////////////////////////////////////////////

    public static Vector getFreezePaymentFormByPaymentTypeName(Connection con, int paymentType) {

        Vector releasePaymentVector = new Vector();
        PaymentModel paymentModel = new PaymentModel();

        try {

            Statement stmt = con.createStatement();
            String sqlString = " Select payment_detail.PAYMENT_DETAIL_ID ,scm_id,  payment_detail.PAYMENT_NAME ,PAYMENT_STATUS_TYPE_ID , payment_detail.PAYMENT_START_DATE , payment_detail.PAYMENT_END_DATE , cam_payment.RECORD_ID , cam_payment.PAYMENT_ID , cam_payment.SCM_COMMISSION_VALUE , payment_detail.PAYMENT_TYPE_ID , gen_dcm.DCM_NAME , gen_dcm.DCM_CODE,PAYMENT_TYPE.PAYMENT_TYPE_NAME FROM payment_detail ,cam_payment,gen_dcm,payment_type WHERE cam_payment.PAYMENT_ID=payment_detail.PAYMENT_DETAIL_ID AND cam_payment.FROZEN_FLAG='0' AND MEMO_ID is null AND payment_detail.PAYMENT_TYPE_ID=payment_type.payment_type_id AND gen_dcm.DCM_ID=cam_payment.SCM_ID AND payment_detail.PAYMENT_TYPE_ID = '" + paymentType + "'";



            System.out.println(sqlString);

            ResultSet releasePaymentRS = stmt.executeQuery(sqlString);
            while (releasePaymentRS.next()) {
                paymentModel = new PaymentModel(con, releasePaymentRS);
                paymentModel.setDCMName(releasePaymentRS.getString("DCM_NAME"));
                paymentModel.setDCMCode(releasePaymentRS.getString("DCM_CODE"));

                paymentModel.setPaymentSCMId(releasePaymentRS.getInt("SCM_ID"));
//               paymentModel.setPaymentAccrualId(releasePaymentRS.getInt("ACCRUAL_ID"));
//               paymentModel.setPaymentDeductionValue(releasePaymentRS.getInt("DEDUCTIONS_VALUE"));
//               paymentModel.setPaymentFlag(releasePaymentRS.getString("FLAG"));
//               paymentModel.setPaymentMemoId(releasePaymentRS.getInt("MEMO_ID"));
//               paymentModel.setPaymentFrozenFlag(releasePaymentRS.getString("FROZEN_FLAG"));
                paymentModel.setPaymentSCMCommissionValue(releasePaymentRS.getInt("SCM_COMMISSION_VALUE"));
                paymentModel.setPaymentRecordId(releasePaymentRS.getInt("RECORD_ID"));
                releasePaymentVector.add(paymentModel);
            }
            releasePaymentRS.close();
            stmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return releasePaymentVector;
    }
    /////////////////////////////////////////////////////////////////

    /**
     *
     * @param con
     * @param paymentId
     * @return
     */
    public static Vector getFreezePaymentFormByPaymentName(Connection con, String paymentName) {

        Vector releasePaymentVector = new Vector();
        PaymentModel paymentModel = new PaymentModel();

        try {

            Statement stmt = con.createStatement();
            String sqlString = " Select payment_detail.PAYMENT_DETAIL_ID , scm_id, payment_detail.PAYMENT_NAME ,PAYMENT_STATUS_TYPE_ID , payment_detail.PAYMENT_START_DATE , payment_detail.PAYMENT_END_DATE , cam_payment.RECORD_ID , cam_payment.PAYMENT_ID , cam_payment.SCM_COMMISSION_VALUE , payment_detail.PAYMENT_TYPE_ID , gen_dcm.DCM_NAME , gen_dcm.DCM_CODE,PAYMENT_TYPE.PAYMENT_TYPE_NAME FROM payment_detail ,cam_payment,gen_dcm,payment_type WHERE cam_payment.PAYMENT_ID=payment_detail.PAYMENT_DETAIL_ID AND cam_payment.FROZEN_FLAG='0' AND MEMO_ID IS NULL AND payment_detail.PAYMENT_TYPE_ID=payment_type.payment_type_id  AND gen_dcm.DCM_ID=cam_payment.SCM_ID AND PAYMENT_NAME = '" + paymentName + "'";
            System.out.println(sqlString);
            ResultSet releasePaymentRS = stmt.executeQuery(sqlString);
            while (releasePaymentRS.next()) {
                paymentModel = new PaymentModel(con, releasePaymentRS);
                paymentModel.setDCMName(releasePaymentRS.getString("DCM_NAME"));
                paymentModel.setDCMCode(releasePaymentRS.getString("DCM_CODE"));


                paymentModel.setPaymentSCMId(releasePaymentRS.getInt("SCM_ID"));
//               paymentModel.setPaymentAccrualId(releasePaymentRS.getInt("ACCRUAL_ID"));
//               paymentModel.setPaymentDeductionValue(releasePaymentRS.getInt("DEDUCTIONS_VALUE"));
//               paymentModel.setPaymentFlag(releasePaymentRS.getString("FLAG"));
//               paymentModel.setPaymentMemoId(releasePaymentRS.getInt("MEMO_ID"));
//               paymentModel.setPaymentFrozenFlag(releasePaymentRS.getString("FROZEN_FLAG"));
                paymentModel.setPaymentSCMCommissionValue(releasePaymentRS.getInt("SCM_COMMISSION_VALUE"));
                paymentModel.setPaymentRecordId(releasePaymentRS.getInt("RECORD_ID"));
                releasePaymentVector.add(paymentModel);
            }
            releasePaymentRS.close();
            stmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return releasePaymentVector;
    }
    ///////////////////////////////////////////////////////////////////////   

    public static Vector getFreezePaymentFormByDCMCode(Connection con, String DCMCode) {

        Vector releasePaymentVector = new Vector();
        PaymentModel paymentModel = new PaymentModel();

        try {

            Statement stmt = con.createStatement();
            String sqlString = " Select payment_detail.PAYMENT_DETAIL_ID ,scm_id , PAYMENT_STATUS_TYPE_ID , payment_detail.PAYMENT_NAME , payment_detail.PAYMENT_START_DATE , payment_detail.PAYMENT_END_DATE , cam_payment.RECORD_ID , cam_payment.PAYMENT_ID , cam_payment.SCM_COMMISSION_VALUE , payment_detail.PAYMENT_TYPE_ID , gen_dcm.DCM_NAME , gen_dcm.DCM_CODE,PAYMENT_TYPE.PAYMENT_TYPE_NAME FROM payment_detail ,cam_payment,gen_dcm,payment_type WHERE cam_payment.PAYMENT_ID=payment_detail.PAYMENT_DETAIL_ID AND cam_payment.FROZEN_FLAG='0' AND MEMO_ID IS NULL AND payment_detail.PAYMENT_TYPE_ID=payment_type.payment_type_id  AND gen_dcm.DCM_ID=cam_payment.SCM_ID AND DCM_CODE = '" + DCMCode + "'";
            System.out.println("sqlString " + sqlString);
            ResultSet releasePaymentRS = stmt.executeQuery(sqlString);
            while (releasePaymentRS.next()) {
                paymentModel = new PaymentModel(con, releasePaymentRS);
                paymentModel.setDCMName(releasePaymentRS.getString("DCM_NAME"));
                paymentModel.setDCMCode(releasePaymentRS.getString("DCM_CODE"));

                paymentModel.setPaymentSCMId(releasePaymentRS.getInt("SCM_ID"));
//               paymentModel.setPaymentAccrualId(releasePaymentRS.getInt("ACCRUAL_ID"));
//               paymentModel.setPaymentDeductionValue(releasePaymentRS.getInt("DEDUCTIONS_VALUE"));
//               paymentModel.setPaymentFlag(releasePaymentRS.getString("FLAG"));
//               paymentModel.setPaymentMemoId(releasePaymentRS.getInt("MEMO_ID"));
//               paymentModel.setPaymentFrozenFlag(releasePaymentRS.getString("FROZEN_FLAG"));
                paymentModel.setPaymentSCMCommissionValue(releasePaymentRS.getInt("SCM_COMMISSION_VALUE"));
                paymentModel.setPaymentRecordId(releasePaymentRS.getInt("RECORD_ID"));
                releasePaymentVector.add(paymentModel);
            }
            releasePaymentRS.close();
            stmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return releasePaymentVector;
    }
    ///////////////////////////////////////////////////////////////////////////////////////
    
    public static void deleteTmpScmCode(Connection con){
        String deleteSql = "delete from TMP_SCM_CODE";
        DBUtil.executeSQL(deleteSql, con);
        
    }
    //////////////////////////////////////////////////////////////////////////////////////
    
}