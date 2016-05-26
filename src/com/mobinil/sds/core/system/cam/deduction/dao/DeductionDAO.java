package com.mobinil.sds.core.system.cam.deduction.dao;

import java.sql.*;
import java.text.SimpleDateFormat;

import oracle.jdbc.*;
import java.util.*;
import com.mobinil.sds.core.system.cam.accrual.model.*;
import com.mobinil.sds.core.system.cam.common.model.*;
import com.mobinil.sds.core.system.cam.deduction.model.*;
import com.mobinil.sds.core.utilities.DBUtil;

import oracle.jdbc.driver.OracleResultSet;

public class DeductionDAO {
    
    public static long addDeduction(Connection argCon, DeductionModel model) {
        long seqValue = DBUtil.getSequenceNextVal(argCon, "CAM_DEDUCTION_SEQ");
        
        String sql =
                "INSERT INTO CAM_DEDUCTION(DEDUCTION_ID, DEDUCTION_VALUE, DEDUCTION_REASON_ID, DEDUCTION_STATUS_ID, REMAINING_VALUE, DCM_ID, GROUP_PAY_TYPE_NAME, DEDUCTION_DATE)"
                + " VALUES(" + seqValue + "," + model.getDeduction_value() + "," + model.getReason_id() + ", "
                + model.getStatus_id() + ", " + model.getDeduction_value()
                + ", " + model.getDcm_id() + ", '" + model.getPayment_group_type_name() + "', sysdate) ";
        
        DBUtil.executeSQL(sql, argCon);
        
        
        
        
        return seqValue;
        
        
    }

///////////////////////////////////////////
    public static void updateDeduction(Connection argCon, DeductionModel model) {
        String error = "";
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.updateDeduction(?, ?, ?, ?, ?, ?, ?, ?); end;";
            
            CallableStatement proc = argCon.prepareCall(procCall);
            
            proc.setInt(1, model.getDeduction_id());
            proc.setDouble(2, model.getDeduction_value());
            proc.setDouble(3, model.getDeduction_remaining_value());
            proc.setInt(4, model.getReason_id());
            proc.setInt(5, model.getStatus_id());
            proc.setInt(6, model.getDcm_id());
            proc.setString(7, model.getPayment_group_type_name());
            
            proc.registerOutParameter(8, OracleTypes.VARCHAR);
            
            proc.execute();
            error = proc.getString(8);
            if (error != null) {
                System.out.println("Error : " + error);
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////
    public static Vector getAllDeduction(Connection argCon) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.getAllDeduction(?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            proc.registerOutParameter(2, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(1);
            error = proc.getString(2);
            while (result_set.next()) {
                DeductionModel item = new DeductionModel(
                        result_set.getInt(1),
                        result_set.getDouble(2),
                        result_set.getInt(3),
                        result_set.getInt(4),
                        result_set.getDouble(5),
                        result_set.getInt(6),
                        result_set.getString(7),
                        result_set.getDate(8),
                        result_set.getString(9),
                        result_set.getString(10),
                        result_set.getString(11));
                list_to_return.add(item);
            }
            if (error != null) {
                System.out.println("Error : " + error);
                return null;
            }
            proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_to_return;
    }
    //////////////////////////////

    public static Vector searchDeduction(Connection argCon, int reason_id, String group_type_name, int status_id, String date_from, String date_to) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.searchDeduction(?, ?, ?, ?, ?, ?, ?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            
            proc.setInt(1, reason_id);
            proc.setInt(2, status_id);
            
            
            if (group_type_name == null || group_type_name.equals("")) {
                group_type_name = null;
                proc.setString(3, group_type_name);
            } else {
                proc.setString(3, "'" + group_type_name + "'");
            }

            //SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");

            proc.setString(6, null);
            if (date_from != null && !date_from.equals("")) {
                //java.sql.Date d_from = new java.sql.Date(formatter.parse(date_from).getTime());
                proc.setString(4, "'" + date_from + "'");
                proc.setString(6, "'" + "mm/dd/yyyy" + "'");
            } else {
                proc.setDate(4, null);
                
            }
            
            if (date_to != null && !date_to.equals("")) {
                //java.sql.Date d_to = new java.sql.Date(formatter.parse(date_to).getTime());
                proc.setString(5, "'" + date_to + "'");
                proc.setString(6, "'" + "mm/dd/yyyy" + "'");
            } else {
                proc.setDate(5, null);
                
            }
            
            proc.registerOutParameter(7, OracleTypes.CURSOR);
            proc.registerOutParameter(8, OracleTypes.VARCHAR);
            proc.execute();
            error = proc.getString(8);
            ResultSet result_set = (ResultSet) proc.getObject(7);
            
            while (result_set.next()) {
                DeductionModel item = new DeductionModel(
                        result_set.getInt(1),
                        result_set.getDouble(2),
                        result_set.getInt(3),
                        result_set.getInt(4),
                        result_set.getDouble(5),
                        result_set.getInt(6),
                        result_set.getString(7),
                        result_set.getDate(8),
                        result_set.getString(9),
                        result_set.getString(10),
                        result_set.getString(11));
                item.setDcm_code(result_set.getString("DCM_CODE"));
                //    item.setPaymentName(result_set.getString("PAYMENT_NAME"));
                list_to_return.add(item);
            }
            if (error != null) {
                System.out.println("Error : " + error);
                return null;
            }
            proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_to_return;
    }
    //////////////////////////////

    public static Vector getAllAvailableDeduction(Connection argCon) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.getAllAvailableDeduction(?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            proc.registerOutParameter(2, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(1);
            error = proc.getString(2);
            while (result_set.next()) {
                DeductionModel item = new DeductionModel(
                        result_set.getInt(1),
                        result_set.getDouble(2),
                        result_set.getInt(3),
                        result_set.getInt(4),
                        result_set.getDouble(5),
                        result_set.getInt(6),
                        result_set.getString(7),
                        result_set.getDate(8),
                        result_set.getString(9),
                        result_set.getString(10),
                        result_set.getString(11));
                list_to_return.add(item);
            }
            if (error != null) {
                System.out.println("Error : " + error);
                return null;
            }
            proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_to_return;
    }
///////////////////////////////////////////
///////////////////////////////////////////

    public static DeductionModel getDeductionById(Connection argCon, int ded_id) {
        String error = "";
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.getDeductionById(?, ?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.setInt(1, ded_id);
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            proc.registerOutParameter(3, OracleTypes.VARCHAR);
            proc.execute();
            
            
            ResultSet result_set = (ResultSet) proc.getObject(2);
            error = proc.getString(3);
            while (result_set.next()) {
                DeductionModel item = new DeductionModel(
                        result_set.getInt(1),
                        result_set.getDouble(2),
                        result_set.getInt(3),
                        result_set.getInt(4),
                        result_set.getDouble(5),
                        result_set.getInt(6),
                        result_set.getString(7),
                        result_set.getDate(8),
                        result_set.getString(9),
                        result_set.getString(10),
                        result_set.getString(11));
                return item;
            }
            if (error != null) {
                System.out.println("Error : " + error);
                return null;
            }
            proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    ///////////////////////////////////////////

    public static Vector getAllDeductionStatus(Connection argCon) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.getAllDeductionStatus(?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            proc.registerOutParameter(2, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(1);
            error = proc.getString(2);
            while (result_set.next()) {
                StatusModel item = new StatusModel(
                        result_set.getInt(1),
                        result_set.getString(2),
                        result_set.getString(3));
                list_to_return.add(item);
            }
            if (error != null) {
                System.out.println("Error : " + error);
                return null;
            }
            
            proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_to_return;
    }
    ///////////////////////////////////////////

    public static Vector getAllOptionalDeductionStatus(Connection argCon, String mode) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.getAllOptionalDeductionStatus(?, ?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.setString(1, mode);
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            proc.registerOutParameter(3, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(2);
            error = proc.getString(3);
            while (result_set.next()) {
                StatusModel item = new StatusModel(
                        result_set.getInt(1),
                        result_set.getString(2),
                        result_set.getString(3));
                list_to_return.add(item);
            }
            if (error != null) {
                System.out.println("Error : " + error);
                return null;
            }
            
            proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_to_return;
    }

    ///////////////////////////////////////////
    public static StatusModel getStatusById(Connection connection, int status_id) {
        Vector all_statuses = getAllDeductionStatus(connection);
        for (int i = 0; i < all_statuses.size(); i++) {
            StatusModel status = (StatusModel) all_statuses.get(i);
            if (status.getStatus_id() == status_id) {
                return status;
            }
        }
        return null;
    }
    ///////////////////////////////////////////

    public static StatusModel getStatusByName(Connection connection, String status_name) {
        Vector all_statuses = getAllDeductionStatus(connection);
        for (int i = 0; i < all_statuses.size(); i++) {
            StatusModel status = (StatusModel) all_statuses.get(i);
            if (status.getStatus_name().equalsIgnoreCase(status_name)) {
                return status;
            }
        }
        return null;
    }
    
    public static int addDeductionReason(Connection argCon, ReasonModel reason) {
        String error = "";
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.addDeductionReason(?, ?, ?, ?, ?); end;";
            
            CallableStatement proc = argCon.prepareCall(procCall);
            
            proc.setString(1, reason.getReason_name());
            proc.setString(2, reason.getReason_desc());
            proc.setInt(3, reason.getReason_status_id());
            
            proc.registerOutParameter(4, OracleTypes.INTEGER);
            proc.registerOutParameter(5, OracleTypes.VARCHAR);
            
            proc.execute();
            error = proc.getString(5);
            if (error != null) {
                System.out.println("Error : " + error);
                return -1;
            } else {
                return proc.getInt(4);
            }

            //proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    ///////////////////////////////////////////

    public static void updateDeductionReason(Connection argCon, ReasonModel reason) {
        String error = "";
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.updateDeductionReason(?, ?, ?, ?, ? ); end;";
            
            CallableStatement proc = argCon.prepareCall(procCall);
            
            proc.setInt(1, reason.getReason_id());
            proc.setString(2, reason.getReason_name());
            proc.setString(3, reason.getReason_desc());
            proc.setInt(4, reason.getReason_status_id());
            
            proc.registerOutParameter(5, OracleTypes.VARCHAR);
            proc.execute();
            error = proc.getString(5);
            if (error != null) {
                System.out.println("Error : " + error);
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////
    public static Vector getAllDeductionReason(Connection argCon) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.getAllDeductionReason(?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            proc.registerOutParameter(2, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(1);
            error = proc.getString(2);
            while (result_set.next()) {
                ReasonModel item = new ReasonModel(
                        result_set.getInt(1),
                        result_set.getString(2),
                        result_set.getString(3),
                        result_set.getInt(4),
                        result_set.getString(5));
                list_to_return.add(item);
            }
            if (error != null) {
                System.out.println("Error : " + error);
                return null;
            }
            
            proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_to_return;
    }
    ///////////////////////////////////////////
///////////////////////////////////////////

    public static Vector getAllActiveDedReason(Connection argCon) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.getAllActiveDedReason(?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            proc.registerOutParameter(2, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(1);
            error = proc.getString(2);
            while (result_set.next()) {
                ReasonModel item = new ReasonModel(
                        result_set.getInt(1),
                        result_set.getString(2),
                        result_set.getString(3),
                        result_set.getInt(4),
                        result_set.getString(5));
                list_to_return.add(item);
            }
            if (error != null) {
                System.out.println("Error : " + error);
                return null;
            }
            
            proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_to_return;
    }
///////////////////////////////////////////

    public static Vector getAllDedReasonStatus(Connection argCon) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.getAllDedReasonStatus(?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            proc.registerOutParameter(2, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(1);
            error = proc.getString(2);
            while (result_set.next()) {
                StatusModel item = new StatusModel(
                        result_set.getInt(1),
                        result_set.getString(2),
                        result_set.getString(3));
                list_to_return.add(item);
            }
            if (error != null) {
                System.out.println("Error : " + error);
                return null;
            }
            
            proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_to_return;
    }
///////////////////////////////////////////

    public static ReasonModel getReasonById(Connection connection, int id) {
        Vector all_reason = getAllDeductionReason(connection);
        for (int i = 0; i < all_reason.size(); i++) {
            ReasonModel reason = (ReasonModel) all_reason.get(i);
            if (reason.getReason_id() == id) {
                return reason;
            }
        }
        return null;
    }
///////////////////////////////////////////

    public static ReasonModel getReasonByName(Connection connection, String name) {
        Vector all_reason = getAllDeductionReason(connection);
        for (int i = 0; i < all_reason.size(); i++) {
            ReasonModel reason = (ReasonModel) all_reason.get(i);
            if (reason.getReason_name().equalsIgnoreCase(name)) {
                return reason;
            }
        }
        return null;
    }
//////////////////////////////

    public static Vector searchDeductionReason(Connection argCon, String reason_name, int status_id) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.searchDeductionReason(?, ?, ?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            
            if (reason_name.equals("")) {
                reason_name = null;
                proc.setString(1, reason_name);
            } else {
                proc.setString(1, "'" + reason_name + "'");
            }
            
            proc.setInt(2, status_id);
            
            
            proc.registerOutParameter(3, OracleTypes.CURSOR);
            proc.registerOutParameter(4, OracleTypes.VARCHAR);
            proc.execute();
            error = proc.getString(4);
            ResultSet result_set = (ResultSet) proc.getObject(3);
            
            while (result_set.next()) {
                ReasonModel item = new ReasonModel(
                        result_set.getInt(1),
                        result_set.getString(2),
                        result_set.getString(3),
                        result_set.getInt(4),
                        result_set.getString(5));
                list_to_return.add(item);
                
            }
            if (error != null) {
                System.out.println("Error : " + error);
                return null;
            }
            proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_to_return;
    }
    ///////////////////////////////////////////

    public static int getImportId(Connection argCon) {
        String error = "";
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.getImportId(?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            
            proc.registerOutParameter(1, OracleTypes.INTEGER);
            proc.registerOutParameter(2, OracleTypes.VARCHAR);
            proc.execute();
            error = proc.getString(2);
            int attach_id = proc.getInt(1);
            
            if (error != null) {
                System.out.println("Error : " + error);
                return -1;
            }
            
            proc.close();
            return attach_id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    ///////////////////////////////////////////
    //////////////////////////////

    public static Vector getDeductionDetails(Connection argCon, int deduction_id) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.getDeductionDetails(?, ?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.setInt(1, deduction_id);
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            proc.registerOutParameter(3, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(2);
            error = proc.getString(3);
            while (result_set.next()) {
                DeductionDetailsModel item = new DeductionDetailsModel(
                        result_set.getInt(1),
                        result_set.getInt(2),
                        result_set.getInt(3),
                        result_set.getInt(4),
                        result_set.getDate(5),
                        result_set.getInt(6),
                        result_set.getInt(7),
                        result_set.getDouble(8),
                        result_set.getString(9),
                        result_set.getString(10),
                        result_set.getString(11),
                        result_set.getString(12));
                list_to_return.add(item);
            }
            if (error != null) {
                System.out.println("Error : " + error);
                return null;
            }
            proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_to_return;
    }
    //////////////////////////////

    public static int getFileSequence(Connection argCon) {
        String error = "";
        
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.getFileSequence(?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            PreparedStatement dd = argCon.prepareStatement(procCall);
            
            proc.registerOutParameter(1, OracleTypes.INTEGER);
            proc.registerOutParameter(2, OracleTypes.VARCHAR);
            proc.execute();
            error = proc.getString(2);
            return proc.getInt(1);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
