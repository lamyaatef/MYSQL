package com.mobinil.sds.core.system.cam.memo.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

import com.mobinil.sds.core.system.cam.memo.model.*;
import com.mobinil.sds.core.system.cam.payment.model.DcmStatusHistoryModel;
import com.mobinil.sds.core.system.cam.payment.model.PaymentScmStatusModel;
import com.mobinil.sds.core.system.cam.payment.model.PaymentTypeExcelModel;
import com.mobinil.sds.core.system.cam.payment.model.PaymentTypeMethodModel;
import com.mobinil.sds.core.system.cam.payment.model.PaymentTypePDFModel;
import com.mobinil.sds.core.system.sa.importdata.model.DataImportTableDefModel;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;

import oracle.jdbc.OracleTypes;

public class MemoDAO {

    private final static boolean debugFlag = false;

    private static void debug(String msg) {
        if (debugFlag) {
            System.out.println(msg);
        }
    }

    public static Vector getMemoTypes(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            String procedureName = "cam_memo_pkg.getMemoTypes";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                MemoTypeModel types = new MemoTypeModel(rs.getInt("MEMO_TYPE_ID"), rs.getString("MEMO_TYPE"));
                listToReturn.add(types);
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static String getMemoType(Connection con, int memoId) {
        String memoTypeName = null;
        ResultSet rs = null;
        CallableStatement stmt = null;
        try {
            String procedureName = "cam_memo_pkg.getMemoType";
            String query = null;
            query = "{call " + procedureName + "(?, ?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, memoId);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(2);

            if (rs.next()) {
                memoTypeName = rs.getString(1);
            }

            /*
             * while (rs.next()) { memoTypeName = rs.getString(1); break; }
             */
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return memoTypeName;

    }

    public static String getMemoTitle(Connection con, int memoId) {
        String memoTitle = null;
//		CallableStatement stmt=null;
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select MEMO_COMMENT_TEXT from cam_memo where memo_id='" + memoId + "'");
//			String procedureName = "cam_memo_pkg.getMemoTitle";
//			String query = null;
//			query = "{call " + procedureName + "(?, ?)}";
//			stmt = con.prepareCall(query);
//			stmt.setInt(1, memoId);
//			stmt.registerOutParameter(2, OracleTypes.CURSOR);
//			stmt.execute();
//			rs = (ResultSet) stmt.getObject(2);
            if (rs.next()) {
                memoTitle = rs.getString("MEMO_COMMENT_TEXT");
                System.out.println("aaaaaaaaaa: " + memoTitle);
            }
            /*
             * while (rs.next()) { memoTitle= rs.getString("MEMO_COMMENT_TEXT");
             * break; }
             */
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
//			Utility.closeCallbaleStatement(stmt);


        }
        return memoTitle;

    }

    public static String getMemoPaymentMethodType(Connection con, int memoId) {
        String PymentTypeMethod = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getMemoPaymentMethodType";
            String query = null;
            query = "{call " + procedureName + "(?, ?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, memoId);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(2);
            if (rs.next()) {
                PymentTypeMethod = rs.getString("payment_type_method_name");
            }
            /*
             * while (rs.next()) { PymentTypeMethod =
             * rs.getString("payment_type_method_name"); break; }
             */
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return PymentTypeMethod;

    }

    public static void setNegativeCommissionToZero(Connection con, int memoId) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.setNegativeCommissionToZero";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, memoId);
            stmt.execute();


            /*
             * while (rs.next()) { PymentTypeMethod =
             * rs.getString("payment_type_method_name"); break; }
             */
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }


    }

    public static Vector getMemoStates(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getMemoStates";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                MemoStateModel states = new MemoStateModel(rs.getInt("STATE_ID"), rs.getString("STATE"));
                listToReturn.add(states);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static Vector getDcmChannels(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getDcmChannel";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                ChannelModel channels = new ChannelModel(rs.getInt("CHANNEL_ID"), rs.getString("CHANNEL_NAME"));
                debug("CHANNEL_ID");
                debug(String.valueOf(rs.getInt("CHANNEL_ID")));
                debug("CHANNEL_NAME");
                debug(String.valueOf(rs.getString("CHANNEL_NAME")));
                listToReturn.add(channels);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static Vector getPymentMethod(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.GETPAYMENTMETHODS";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                PaymentTypeMethodModel method = new PaymentTypeMethodModel(rs.getInt("PAYMENT_TYPE_METHOD_ID"), rs.getString("PAYMENT_TYPE_METHOD_NAME"));
                debug("PAYMENT_TYPE_METHOD_ID");
                debug(String.valueOf(rs.getString("PAYMENT_TYPE_METHOD_NAME")));

                listToReturn.add(method);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static Vector getDcmSubChannels(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getdcmsubchannel";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                SubChannelModel channels = new SubChannelModel(rs.getInt("sub_channel_id"), rs.getString("criteria_name"));
                debug("sub_channel_id");
                debug(String.valueOf(rs.getString("criteria_name")));

                listToReturn.add(channels);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static int checkPaymentAvailForSpecific(Connection con,
            String paymentTypeId, String channelId) {
        int payments = 0;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            String procedureName = "cam_memo_pkg.checkPaymentsavailspecific";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(paymentTypeId));// payment_type
            stmt.setInt(2, Integer.parseInt(channelId));// channel id
            stmt.registerOutParameter(3, OracleTypes.INTEGER);

            stmt.execute();

            payments = stmt.getInt(3);


        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return payments;
    }

    public static void createSpeificMemo(Connection con, String makerId,
            String memoDesc, String paymentTypeId, String channelId) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.create_new_specific_memo";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?)}";
            stmt = con.prepareCall(query);
            System.out.println("maker id=" + makerId);

            stmt.setInt(1, Integer.parseInt(makerId));

            stmt.setString(2, memoDesc);

            System.out.println("memo Deec " + memoDesc);

            stmt.setInt(3, Integer.parseInt(paymentTypeId));// payment_type

            System.out.println("payment type id =" + paymentTypeId);
            stmt.setInt(4, Integer.parseInt(channelId));// channel id
            System.out.println("chnanle id =" + channelId);
            stmt.execute();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
    }

    public static int checkPaymentAvailForCollective(Connection con,
            String paymentMethodId, String channelId) {
        int payments = 0;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.checkPaymentsavailcollective";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(paymentMethodId));// payment_type
            stmt.setInt(2, Integer.parseInt(channelId));// channel id
            stmt.registerOutParameter(3, OracleTypes.INTEGER);

            stmt.execute();

            payments = stmt.getInt(3);


        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return payments;
    }

    public static void createCollectionMemo(Connection con, String makerId,
            String memoDesc, String channelId, String paymentMethodId) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.create_new_collection_memo";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(makerId));
            stmt.setString(2, memoDesc);
            stmt.setInt(3, Integer.parseInt(paymentMethodId));
            stmt.setInt(4, Integer.parseInt(channelId));// channel id

            stmt.execute();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
    }

    public static Vector searchSpecificMemo(Connection con, String memoName,
            String statusId, String channelId, String paymentTypeId) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.searchSpecificMemo";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setString(1, memoName);
            stmt.setInt(2, Integer.parseInt(statusId));
            stmt.setInt(3, Integer.parseInt(channelId));
            stmt.setInt(4, Integer.parseInt(paymentTypeId));
            stmt.registerOutParameter(5, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(5);
            while (rs.next()) {
                MemoModel memo = new MemoModel(rs.getInt("MEMO_ID"), rs.getString("MEMO_NAME"),
                        rs.getString("DESCRIPTION"), rs.getDate("START_DATE"), rs.getString("CHANNEL_NAME"), rs.getString("PAYMENT_TYPE_NAME"), rs.getString("STATE"), rs.getString("MEMO_TYPE"));
                listToReturn.add(memo);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;
    }

    public static Vector searchCollectionMemo(Connection con, String memoName,
            String statusId, String channelId) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.searchCollectionMemo";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setString(1, memoName);
            stmt.setInt(2, Integer.parseInt(statusId));
            stmt.setInt(3, Integer.parseInt(channelId));
            stmt.registerOutParameter(4, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(5);
            while (rs.next()) {
                MemoModel memo = new MemoModel(rs.getInt("ID"), rs.getString("NAME"),
                        rs.getString("DESCRIPTION"), rs.getDate("START_DATE"), rs.getString("CHANNEL_NAME"), rs.getString("STATE"), rs.getString("TYPE"));
                listToReturn.add(memo);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;
    }

    public static Vector searchSpecificCollectionMemo(Connection con,
            int memoTypeId, String memoName , String statusId,
            String channelId, String subChannelId, String paymentTypeId,
            String fromDate, String toDate, String paymentMethodId) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;

        System.out.println("memoTypeid = " + memoTypeId);

        if (memoTypeId != -1) {
            try {
                String procedureName = "cam_memo_pkg.searchSpecificCollectionMemo";
                String query = null;
                query = "{call " + procedureName + "(?,?,?,?,?,?,?,?,?,?)}";
                stmt = con.prepareCall(query);

                stmt.setInt(1, memoTypeId);


                if (memoName == null || memoName.equals("")) {
                    stmt.setString(2, null);
                } else {
                    stmt.setString(2, "'" + memoName + "'");
                }



                if (statusId == null || statusId.equals("")) {
                    stmt.setInt(3, -1);
                } else {
                    stmt.setInt(3, Integer.parseInt(statusId));
                }


                if (channelId == null || channelId.equals("")) {
                    stmt.setInt(4, -1);
                } else {
                    stmt.setInt(4, Integer.parseInt(channelId));
                }


                if (subChannelId == null || subChannelId.equals("")) {
                    stmt.setInt(5, -1);
                } else {
                    stmt.setInt(5, Integer.parseInt(subChannelId));
                }


                if (paymentTypeId == null || paymentTypeId.equals("")) {
                    stmt.setInt(6, -1);
                } else {
                    stmt.setInt(6, Integer.parseInt(paymentTypeId));
                }


                if (fromDate == null || fromDate.equals("Start_Date")
                        || fromDate.equals("")) {
                    stmt.setString(7, null);
                } else {
                    stmt.setString(7, "'" + fromDate + "'");
                }


                if (toDate == null || toDate.equals("End_Date")
                        || toDate.equals("")) {
                    stmt.setString(8, null);
                } else {
                    stmt.setString(8, "'" + toDate + "'");
                }

                if (paymentMethodId == null || paymentMethodId.equals("")) {
                    stmt.setInt(9, -1);
                } else {
                    stmt.setInt(9, Integer.parseInt(paymentMethodId));
                }

                

                stmt.registerOutParameter(10, OracleTypes.CURSOR);
                stmt.execute();
                rs = (ResultSet) stmt.getObject(10);
                while (rs.next()) {
                    /*
                     * MemoModel memo = new MemoModel(rs.getInt("MEMO_ID"), rs
                     * .getString("MEMO_NAME"), rs .getString("DESCRIPTION"), rs
                     * .getDate("CREATION_DATE"), rs .getString("CHANNEL_NAME"),
                     * rs .getString("PAYMENT_TYPE_NAME"), rs
                     * .getString("STATE"), rs.getString("MEMO_TYPE"));
                     *
                     * listToReturn.add(memo);
                     */
                    MemoModel memo = new MemoModel(rs.getInt("MEMO_ID"), rs.getString("MEMO_NAME"), rs.getString("DESCRIPTION"), rs.getDate("CREATION_DATE"), rs.getString("CHANNEL_NAME"), rs.getString("PAYMENT_TYPE_NAME"), rs.getString("PAYMENT_TYPE_METHOD_NAME"), rs.getString("STATE"), rs.getString("MEMO_TYPE"));
                    listToReturn.add(memo);
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            } finally {
                Utility.closeResultSet(rs);
                Utility.closeCallbaleStatement(stmt);


            }
        } else {
            try {
                String procedureName = "cam_memo_pkg.searchSpecificCollectionMemo";
                String query = null;
                query = "{call " + procedureName + "(?,?,?,?,?,?,?,?,?,?)}";
                stmt = con.prepareCall(query);

                stmt.setInt(1, -1);


                if (memoName == null || memoName.equals("")) {
                    stmt.setString(2, null);
                } else {
                    stmt.setString(2, "'" + memoName + "'");
                }

                if (statusId == null || statusId.equals("")) {
                    stmt.setInt(3, -1);
                } else {
                    stmt.setInt(3, Integer.parseInt(statusId));
                }

                if (channelId == null || channelId.equals("")) {
                    stmt.setInt(4, -1);
                } else {
                    stmt.setInt(4, Integer.parseInt(channelId));
                }

                if (subChannelId == null || subChannelId.equals("")) {
                    stmt.setInt(5, -1);
                } else {
                    stmt.setInt(5, Integer.parseInt(subChannelId));
                }


                if (paymentTypeId == null || paymentTypeId.equals("")) {
                    stmt.setInt(6, -1);
                } else {
                    stmt.setInt(6, Integer.parseInt(paymentTypeId));
                }


                if (fromDate == null || fromDate.equals("Start_Date")
                        || fromDate.equals("")) {
                    stmt.setString(7, null);
                } else {
                    stmt.setString(7, "'" + fromDate + "'");
                }


                if (toDate == null || toDate.equals("End_Date")
                        || toDate.equals("")) {
                    stmt.setString(8, null);
                } else {
                    stmt.setString(8, "'" + toDate + "'");
                }
                
                

                if (paymentMethodId == null || paymentMethodId.equals("")) {
                    stmt.setInt(9, -1);
                } else {
                    stmt.setInt(9, Integer.parseInt(paymentMethodId));
                }

 
                stmt.registerOutParameter(10, OracleTypes.CURSOR);
                stmt.execute();
                rs = (ResultSet) stmt.getObject(10);
                while (rs.next()) {
                    MemoModel memo = new MemoModel(rs.getInt("MEMO_ID"), rs.getString("MEMO_NAME"), rs.getString("DESCRIPTION"), rs.getDate("CREATION_DATE"), rs.getString("CHANNEL_NAME"), rs.getString("PAYMENT_TYPE_NAME"), rs.getString("PAYMENT_TYPE_METHOD_NAME"), rs.getString("STATE"), rs.getString("MEMO_TYPE"));
                    listToReturn.add(memo);
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            } finally {
                Utility.closeResultSet(rs);
                Utility.closeCallbaleStatement(stmt);


            }
        }

        return listToReturn;
    }

    public static Vector searchMemoHistory(Connection con, int memoTypeId,
            String memoName, String statusId, String channelId,
            String subChannelId, String paymentTypeId, String periodId, String fromDate,
            String toDate) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        if (memoTypeId != -1) {
            try {
                String procedureName = "cam_memo_pkg.searchmemohistory";
                String query = null;
                query = "{call " + procedureName + "(?,?,?,?,?,?,?,?,?,?)}";
                stmt = con.prepareCall(query);

                stmt.setInt(1, memoTypeId);


                if (memoName == null || memoName.equals("")) {
                    stmt.setString(2, null);
                } else {
                    stmt.setString(2, "'" + memoName + "'");
                }


                if (statusId == null || statusId.equals("")) {
                    stmt.setInt(3, -1);
                } else {
                    stmt.setInt(3, Integer.parseInt(statusId));
                }


                if (channelId == null || channelId.equals("")) {
                    stmt.setInt(4, -1);
                } else {
                    stmt.setInt(4, Integer.parseInt(channelId));
                }


                if (subChannelId == null || subChannelId.equals("")) {
                    stmt.setInt(5, -1);
                } else {
                    stmt.setInt(5, Integer.parseInt(subChannelId));
                }


                if (paymentTypeId == null || paymentTypeId.equals("")) {
                    stmt.setInt(6, -1);
                } else {
                    stmt.setInt(6, Integer.parseInt(paymentTypeId));
                }


                if (periodId == null || periodId.equals("")) {
                    stmt.setInt(7, -1);
                } else {
                    stmt.setInt(7, Integer.parseInt(periodId));
                }


                if (fromDate == null || fromDate.equals("Start_Date")
                        || fromDate.equals("")) {
                    stmt.setString(8, null);
                } else {
                    stmt.setString(8, "'" + fromDate + "'");
                }


                if (toDate == null || toDate.equals("End_Date")
                        || toDate.equals("")) {
                    stmt.setString(9, null);
                } else {
                    stmt.setString(9, "'" + toDate + "'");
                }


                stmt.registerOutParameter(10, OracleTypes.CURSOR);
                stmt.execute();
                rs = (ResultSet) stmt.getObject(10);
                while (rs.next()) {
                    if (rs.getInt("type_id") == 1) {
                        MemoModel memo = new MemoModel();
                        memo.setId(rs.getInt("MEMO_ID"));
                        debug("in search History dao");
                        memo.setName(rs.getString("MEMO_NAME"));
                        memo.setSendForValidationDate(rs.getDate("SEND_FOR_VALIDATION_DATE"));
                        debug("SEND_FOR_VALIDATION_DATE: " + memo.getSendForValidationDate());
                        memo.setApprovalDate(rs.getDate("APPROVAL_DATE"));
                        debug("APPROVAL_DATE; " + memo.getApprovalDate());
                        memo.setSalesManagerApprovalDate(rs.getDate("SALES_MANAGER_APPROVAL_DATE"));
                        debug("SALES_MANAGER_APPROVAL_DATE: " + memo.getSalesManagerApprovalDate());
                        memo.setSalesBackOfficeApprovalDate(rs.getDate("SALES_BACK_OFFICE_APPROVAL_DAT"));
                        debug("SALES_BACK_OFFICE_APPROVAL_DAT: " + memo.getSalesBackOfficeApprovalDate());
                        memo.setSalesDirectiveApprovalDate(rs.getDate("SALES_DIRECTIVE_APPROVAL_DATE"));
                        debug("SALES_DIRECTIVE_APPROVAL_DATE: " + memo.getSalesDirectiveApprovalDate());
                        memo.setFinanceReceiveDate(rs.getDate("FINANCE_RECEIVE_DATE"));
                        debug("FINANCE_RECEIVE_DATE: " + memo.getFinanceReceiveDate());
                        memo.setPaymentDate(rs.getDate("PAYMENT_DATE"));
                        debug("PAYMENT_DATE: " + memo.getPaymentDate());
                        memo.setPeriodId(rs.getInt("period_id"));
                        memo.setCalcTargetedDate(rs.getDate("CALC_TARGETED_DATE"));
                        memo.setCommCalcDelay(rs.getInt("COMM_CALC_DELAY"));
                        memo.setFinishedOn(rs.getDate("FINISHED_ON"));
                        memo.setPaymentDelay(rs.getInt("PAYMENT_DELAY"));
                        memo.setPaymentTargetedDate(rs.getDate("PAYMENT_TARGETED_DATE"));
                        memo.setReadyCommClacDate(rs.getDate("READY_COMM_CALC_DATE"));
                        Vector memoreasons = getMemoDelayReasons(con, String.valueOf(rs.getInt("MEMO_ID")));
                        memo.setDelayReason(memoreasons);
                        listToReturn.add(memo);
                    } else if (rs.getInt("type_id") == 2) {
                        MemoModel memo = new MemoModel();
                        memo.setId(rs.getInt("MEMO_ID"));
                        debug("in search History dao");
                        memo.setName(rs.getString("MEMO_NAME"));
                        memo.setSendForValidationDate(rs.getDate("SEND_FOR_VALIDATION_DATE"));
                        debug("SEND_FOR_VALIDATION_DATE: " + memo.getSendForValidationDate());
                        memo.setApprovalDate(rs.getDate("APPROVAL_DATE"));
                        debug("APPROVAL_DATE; " + memo.getApprovalDate());
                        memo.setSalesManagerApprovalDate(rs.getDate("SALES_MANAGER_APPROVAL_DATE"));
                        debug("SALES_MANAGER_APPROVAL_DATE: " + memo.getSalesManagerApprovalDate());
                        memo.setSalesBackOfficeApprovalDate(rs.getDate("SALES_BACK_OFFICE_APPROVAL_DAT"));
                        debug("SALES_BACK_OFFICE_APPROVAL_DAT: " + memo.getSalesBackOfficeApprovalDate());
                        memo.setSalesDirectiveApprovalDate(rs.getDate("SALES_DIRECTIVE_APPROVAL_DATE"));
                        debug("SALES_DIRECTIVE_APPROVAL_DATE: " + memo.getSalesDirectiveApprovalDate());
                        memo.setFinanceReceiveDate(rs.getDate("FINANCE_RECEIVE_DATE"));
                        debug("FINANCE_RECEIVE_DATE: " + memo.getFinanceReceiveDate());
                        memo.setPaymentDate(rs.getDate("PAYMENT_DATE"));
                        debug("PAYMENT_DATE: " + memo.getPaymentDate());
                        memo.setPeriodId(rs.getInt("period_id"));
                        memo.setCalcTargetedDate(rs.getDate("CALC_TARGETED_DATE"));
                        memo.setCommCalcDelay(rs.getInt("COMM_CALC_DELAY"));
                        memo.setFinishedOn(rs.getDate("FINISHED_ON"));
                        memo.setPaymentDelay(rs.getInt("PAYMENT_DELAY"));
                        memo.setPaymentTargetedDate(rs.getDate("PAYMENT_TARGETED_DATE"));
                        memo.setReadyCommClacDate(rs.getDate("READY_COMM_CALC_DATE"));
                        Vector memoreasons = getMemoDelayReasons(con, String.valueOf(rs.getInt("MEMO_ID")));
                        memo.setDelayReason(memoreasons);
                        listToReturn.add(memo);
                    }


                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            } finally {
                Utility.closeResultSet(rs);
                Utility.closeCallbaleStatement(stmt);


            }
        } else {
            try {
                String procedureName = "cam_memo_pkg.searchmemohistory";
                String query = null;
                query = "{call " + procedureName + "(?,?,?,?,?,?,?,?,?,?)}";
                stmt = con.prepareCall(query);

                stmt.setInt(1, -1);


                if (memoName == null || memoName.equals("")) {
                    stmt.setString(2, null);
                } else {
                    stmt.setString(2, "'" + memoName + "'");
                }

                if (statusId == null || statusId.equals("")) {
                    stmt.setInt(3, -1);
                } else {
                    stmt.setInt(3, Integer.parseInt(statusId));
                }

                if (channelId == null || channelId.equals("")) {
                    stmt.setInt(4, -1);
                } else {
                    stmt.setInt(4, Integer.parseInt(channelId));
                }

                if (subChannelId == null || subChannelId.equals("")) {
                    stmt.setInt(5, -1);
                } else {
                    stmt.setInt(5, Integer.parseInt(subChannelId));
                }


                if (paymentTypeId == null || paymentTypeId.equals("")) {
                    stmt.setInt(6, -1);
                } else {
                    stmt.setInt(6, Integer.parseInt(paymentTypeId));
                }


                if (periodId == null || periodId.equals("")) {
                    stmt.setInt(7, -1);
                } else {
                    stmt.setInt(7, Integer.parseInt(periodId));
                }


                if (fromDate == null || fromDate.equals("Start_Date")
                        || fromDate.equals("")) {
                    stmt.setString(8, null);
                } else {
                    stmt.setString(8, "'" + fromDate + "'");
                }


                if (toDate == null || toDate.equals("End_Date")
                        || toDate.equals("")) {
                    stmt.setString(9, null);
                } else {
                    stmt.setString(9, "'" + toDate + "'");
                }


                stmt.registerOutParameter(10, OracleTypes.CURSOR);

                stmt.execute();

                rs = (ResultSet) stmt.getObject(10);
                while (rs.next()) {
                    if (rs.getInt("type_id") == 1) {
                        MemoModel memo = new MemoModel();
                        memo.setId(rs.getInt("MEMO_ID"));
                        debug("in search History dao");
                        memo.setName(rs.getString("MEMO_NAME"));
                        memo.setSendForValidationDate(rs.getDate("SEND_FOR_VALIDATION_DATE"));
                        debug("SEND_FOR_VALIDATION_DATE: " + memo.getSendForValidationDate());
                        memo.setApprovalDate(rs.getDate("APPROVAL_DATE"));
                        debug("APPROVAL_DATE; " + memo.getApprovalDate());
                        memo.setSalesManagerApprovalDate(rs.getDate("SALES_MANAGER_APPROVAL_DATE"));
                        debug("SALES_MANAGER_APPROVAL_DATE: " + memo.getSalesManagerApprovalDate());
                        memo.setSalesBackOfficeApprovalDate(rs.getDate("SALES_BACK_OFFICE_APPROVAL_DAT"));
                        debug("SALES_BACK_OFFICE_APPROVAL_DAT: " + memo.getSalesBackOfficeApprovalDate());
                        memo.setSalesDirectiveApprovalDate(rs.getDate("SALES_DIRECTIVE_APPROVAL_DATE"));
                        debug("SALES_DIRECTIVE_APPROVAL_DATE: " + memo.getSalesDirectiveApprovalDate());
                        memo.setFinanceReceiveDate(rs.getDate("FINANCE_RECEIVE_DATE"));
                        debug("FINANCE_RECEIVE_DATE: " + memo.getFinanceReceiveDate());
                        memo.setPaymentDate(rs.getDate("PAYMENT_DATE"));
                        debug("PAYMENT_DATE: " + memo.getPaymentDate());
                        memo.setPeriodId(rs.getInt("period_id"));
                        memo.setCalcTargetedDate(rs.getDate("CALC_TARGETED_DATE"));
                        memo.setCommCalcDelay(rs.getInt("COMM_CALC_DELAY"));
                        memo.setFinishedOn(rs.getDate("FINISHED_ON"));
                        memo.setPaymentDelay(rs.getInt("PAYMENT_DELAY"));
                        memo.setPaymentTargetedDate(rs.getDate("PAYMENT_TARGETED_DATE"));
                        memo.setReadyCommClacDate(rs.getDate("READY_COMM_CALC_DATE"));
                        Vector memoreasons = getMemoDelayReasons(con, String.valueOf(rs.getInt("MEMO_ID")));
                        memo.setDelayReason(memoreasons);
                        listToReturn.add(memo);
                    } else if (rs.getInt("type_id") == 2) {
                        MemoModel memo = new MemoModel();
                        memo.setId(rs.getInt("MEMO_ID"));
                        debug("in search History dao");
                        memo.setName(rs.getString("MEMO_NAME"));
                        memo.setSendForValidationDate(rs.getDate("SEND_FOR_VALIDATION_DATE"));
                        debug("SEND_FOR_VALIDATION_DATE: " + memo.getSendForValidationDate());
                        memo.setApprovalDate(rs.getDate("APPROVAL_DATE"));
                        debug("APPROVAL_DATE; " + memo.getApprovalDate());
                        memo.setSalesManagerApprovalDate(rs.getDate("SALES_MANAGER_APPROVAL_DATE"));
                        debug("SALES_MANAGER_APPROVAL_DATE: " + memo.getSalesManagerApprovalDate());
                        memo.setSalesBackOfficeApprovalDate(rs.getDate("SALES_BACK_OFFICE_APPROVAL_DAT"));
                        debug("SALES_BACK_OFFICE_APPROVAL_DAT: " + memo.getSalesBackOfficeApprovalDate());
                        memo.setSalesDirectiveApprovalDate(rs.getDate("SALES_DIRECTIVE_APPROVAL_DATE"));
                        debug("SALES_DIRECTIVE_APPROVAL_DATE: " + memo.getSalesDirectiveApprovalDate());
                        memo.setFinanceReceiveDate(rs.getDate("FINANCE_RECEIVE_DATE"));
                        debug("FINANCE_RECEIVE_DATE: " + memo.getFinanceReceiveDate());
                        memo.setPaymentDate(rs.getDate("PAYMENT_DATE"));
                        debug("PAYMENT_DATE: " + memo.getPaymentDate());
                        memo.setPeriodId(rs.getInt("period_id"));
                        memo.setCalcTargetedDate(rs.getDate("CALC_TARGETED_DATE"));
                        memo.setCommCalcDelay(rs.getInt("COMM_CALC_DELAY"));
                        memo.setFinishedOn(rs.getDate("FINISHED_ON"));
                        memo.setPaymentDelay(rs.getInt("PAYMENT_DELAY"));
                        memo.setPaymentTargetedDate(rs.getDate("PAYMENT_TARGETED_DATE"));
                        memo.setReadyCommClacDate(rs.getDate("READY_COMM_CALC_DATE"));

                        Vector memoreasons = getMemoDelayReasons(con, String.valueOf(rs.getInt("MEMO_ID")));
                        memo.setDelayReason(memoreasons);
                        listToReturn.add(memo);
                    }
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            } finally {
                Utility.closeResultSet(rs);
                Utility.closeCallbaleStatement(stmt);


            }
        }

        return listToReturn;
    }

    public static Vector searchMemoMonitor(Connection con, String channelId, String subChannelId, String fromDate,
            String toDate, String periodId) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.searchmemomonitor";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?,?,?)}";
            stmt = con.prepareCall(query);

            if (channelId == null || channelId.equals("")) {
                stmt.setInt(1, -1);
            } else {
                stmt.setInt(1, Integer.parseInt(channelId));
            }


            if (subChannelId == null || subChannelId.equals("")) {
                stmt.setInt(2, -1);
            } else {
                stmt.setInt(2, Integer.parseInt(subChannelId));
            }



            if (periodId == null || periodId.equals("")) {
                stmt.setInt(3, -1);
            } else {
                stmt.setInt(3, Integer.parseInt(periodId));
            }


            if (fromDate == null || fromDate.equals("Start_Date")
                    || fromDate.equals("")) {
                stmt.setString(4, null);
            } else {
                stmt.setString(4, "'" + fromDate + "'");
            }


            if (toDate == null || toDate.equals("End_Date")
                    || toDate.equals("")) {
                stmt.setString(5, null);
            } else {
                stmt.setString(5, "'" + toDate + "'");
            }


            stmt.registerOutParameter(6, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(6);
            while (rs.next()) {
                MemoModel memo = new MemoModel();
                memo.setId(rs.getInt("MEMO_ID"));
                debug("in search History dao");
                memo.setName(rs.getString("MEMO_NAME"));
                memo.setState(rs.getString("STATE"));
                memo.setChannel(rs.getString("CHANNEL_NAME"));
                memo.setSendForValidationDate(rs.getDate("SEND_FOR_VALIDATION_DATE"));
                debug("SEND_FOR_VALIDATION_DATE: " + memo.getSendForValidationDate());
                memo.setApprovalDate(rs.getDate("APPROVAL_DATE"));
                debug("APPROVAL_DATE; " + memo.getApprovalDate());
                memo.setSalesManagerApprovalDate(rs.getDate("SALES_MANAGER_APPROVAL_DATE"));
                debug("SALES_MANAGER_APPROVAL_DATE: " + memo.getSalesManagerApprovalDate());
                memo.setSalesBackOfficeApprovalDate(rs.getDate("SALES_BACK_OFFICE_APPROVAL_DAT"));
                debug("SALES_BACK_OFFICE_APPROVAL_DAT: " + memo.getSalesBackOfficeApprovalDate());
                memo.setSalesDirectiveApprovalDate(rs.getDate("SALES_DIRECTIVE_APPROVAL_DATE"));
                debug("SALES_DIRECTIVE_APPROVAL_DATE: " + memo.getSalesDirectiveApprovalDate());
                memo.setFinanceReceiveDate(rs.getDate("FINANCE_RECEIVE_DATE"));
                debug("FINANCE_RECEIVE_DATE: " + memo.getFinanceReceiveDate());
                memo.setPaymentDate(rs.getDate("PAYMENT_DATE"));
                debug("PAYMENT_DATE: " + memo.getPaymentDate());
                memo.setPeriodId(rs.getInt("period_id"));
                memo.setCalcTargetedDate(rs.getDate("CALC_TARGETED_DATE"));
                memo.setCommCalcDelay(rs.getInt("COMM_CALC_DELAY"));
                memo.setFinishedOn(rs.getDate("FINISHED_ON"));
                memo.setPaymentDelay(rs.getInt("PAYMENT_DELAY"));
                memo.setPaymentTargetedDate(rs.getDate("PAYMENT_TARGETED_DATE"));
                memo.setReadyCommClacDate(rs.getDate("READY_COMM_CALC_DATE"));

                if (Integer.parseInt(periodId) == 1) {
                    memo.setWeekId(rs.getInt("WEEK_ID"));
                    memo.setDayInWeek(rs.getInt("DAY_OF_WEEK_ID"));

                } else if (Integer.parseInt(periodId) == 2) {

                    memo.setMonthId(rs.getInt("MONTH_ID"));

                    memo.setDayInMonth(rs.getInt("DATE_IN_MONTH"));


                } else if (Integer.parseInt(periodId) == 3) {
                    memo.setQuarterId(rs.getInt("QUARTER_ID"));
                    memo.setMonthInQuarter(rs.getInt("MONTH_IN_QUARTER"));
                    memo.setDayInMonthInQuarter(rs.getInt("DAY_IN_MONTH"));

                }
                Vector memoreasons = getMemoDelayReasons(con, String.valueOf(rs.getInt("MEMO_ID")));
                memo.setDelayReason(memoreasons);
                listToReturn.add(memo);

            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }

        return listToReturn;
    }

    public static void updateMemoHistory(Connection con, int memoId,
            String sendForValidationDate, String approvalDate,
            String salesManagerApprovalDate,
            String salesBackOfficeApprovalDate,
            String salesDirectivrApprovalDate, String financeReceiveDate,
            String paymentDate,
            String readyCommCalcDate,
            String calcTargetedDate,
            String paymentTargetedDate,
            String finishedOn,
            String commCalcDelay,
            String paymentDelay) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.updatememohistory";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

            stmt = con.prepareCall(query);

            stmt.setInt(1, memoId);
            debug("printing dates");

            if (sendForValidationDate.compareTo("Send_For_Validation_Date") == 0) {
                stmt.setString(2, null);
            } else {
                debug("sendForValidationDate: " + sendForValidationDate);
                debug(adjustDate(sendForValidationDate));
                stmt.setString(2, adjustDate(sendForValidationDate));
            }

            if (approvalDate.compareTo("Approval_Date") == 0) {
                stmt.setString(3, null);
            } else {
                debug("approvalDate: " + approvalDate);
                debug(adjustDate(approvalDate));
                stmt.setString(3, adjustDate(approvalDate));
            }

            if (salesManagerApprovalDate.compareTo("Sales_Manager_Approval_Date") == 0) {
                stmt.setString(4, null);
            } else {
                debug("salesManagerApprovalDate: " + salesManagerApprovalDate);
                debug(adjustDate(salesManagerApprovalDate));
                stmt.setString(4, adjustDate(salesManagerApprovalDate));
            }

            if (salesBackOfficeApprovalDate.compareTo("Sales_Back_Office_Approval_Date") == 0) {
                stmt.setString(5, null);
            } else {
                debug("salesBackOfficeApprovalDate: " + salesBackOfficeApprovalDate);
                debug(adjustDate(salesBackOfficeApprovalDate));
                stmt.setString(5, adjustDate(salesBackOfficeApprovalDate));
            }

            if (salesDirectivrApprovalDate.compareTo("Sales_Directive_Approval_Date") == 0) {
                stmt.setString(6, null);
            } else {
                debug("salesDirectivrApprovalDate: " + salesDirectivrApprovalDate);
                debug(adjustDate(salesDirectivrApprovalDate));
                stmt.setString(6, adjustDate(salesDirectivrApprovalDate));
            }

            if (financeReceiveDate.compareTo("Finance_Receive_Date") == 0) {
                stmt.setString(7, null);
            } else {
                debug("financeReceiveDate: " + financeReceiveDate);
                debug(adjustDate(financeReceiveDate));
                stmt.setString(7, adjustDate(financeReceiveDate));
            }

            if (paymentDate.compareTo("Payment_Date") == 0) {
                stmt.setString(8, null);
            } else {
                debug("paymentDate: " + paymentDate);
                debug(adjustDate(paymentDate));
                stmt.setString(8, adjustDate(paymentDate));
            }

            if (readyCommCalcDate.compareTo("Commission_Calc_Date") == 0) {
                stmt.setString(9, null);
            } else {
                debug("readyCommCalcDate: " + readyCommCalcDate);
                debug(adjustDate(readyCommCalcDate));
                stmt.setString(9, adjustDate(readyCommCalcDate));
            }

            if (calcTargetedDate.compareTo("Calc_Targeted_Date") == 0) {
                stmt.setString(10, null);
            } else {
                debug("calcTargetedDate: " + calcTargetedDate);
                debug(adjustDate(calcTargetedDate));
                stmt.setString(10, adjustDate(calcTargetedDate));
            }

            if (paymentTargetedDate.compareTo("Payment_Targeted_Date") == 0) {
                stmt.setString(11, null);
            } else {
                debug("paymentTargetedDate: " + paymentTargetedDate);
                debug(adjustDate(paymentTargetedDate));
                stmt.setString(11, adjustDate(paymentTargetedDate));
            }

            if (finishedOn.compareTo("Finished_On_Date") == 0) {
                stmt.setString(12, null);
            } else {
                debug("finishedOn: " + finishedOn);
                debug(adjustDate(finishedOn));
                stmt.setString(12, adjustDate(finishedOn));
            }

            if (commCalcDelay.compareTo("") == 0) {
                stmt.setString(13, null);
            } else {
                stmt.setString(13, commCalcDelay);
            }

            if (paymentDelay.compareTo("") == 0) {
                stmt.setString(14, null);
            } else {
                stmt.setString(14, paymentDelay);
            }
            System.out.println("excute the statement");
            stmt.execute();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
    }

    public static Vector getMemoHistoryById(Connection con, String memoId) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {

            String procedureName = "cam_memo_pkg.getmemohistorybyid";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoId));
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(2);
            while (rs.next()) {
                MemoModel memo = new MemoModel();
                memo.setId(rs.getInt("MEMO_ID"));
                memo.setName(rs.getString("MEMO_NAME"));
                memo.setSendForValidationDate(rs.getDate("SEND_FOR_VALIDATION_DATE"));
                memo.setApprovalDate(rs.getDate("APPROVAL_DATE"));
                memo.setSalesManagerApprovalDate(rs.getDate("SALES_MANAGER_APPROVAL_DATE"));
                memo.setSalesBackOfficeApprovalDate(rs.getDate("SALES_BACK_OFFICE_APPROVAL_DAT"));
                memo.setSalesDirectiveApprovalDate(rs.getDate("SALES_DIRECTIVE_APPROVAL_DATE"));
                memo.setFinanceReceiveDate(rs.getDate("FINANCE_RECEIVE_DATE"));
                memo.setPaymentDate(rs.getDate("PAYMENT_DATE"));

                memo.setCalcTargetedDate(rs.getDate("CALC_TARGETED_DATE"));
                debug("in dao: " + rs.getDate("CALC_TARGETED_DATE"));
                memo.setCommCalcDelay(rs.getInt("COMM_CALC_DELAY"));
                debug("in dao: " + memo.getCommCalcDelay());
                memo.setFinishedOn(rs.getDate("FINISHED_ON"));
                debug("in dao: " + memo.getFinishedOn());
                memo.setPaymentDelay(rs.getInt("PAYMENT_DELAY"));
                debug("in dao: " + memo.getPaymentDelay());
                memo.setPaymentTargetedDate(rs.getDate("PAYMENT_TARGETED_DATE"));
                debug("in dao: " + memo.getPaymentTargetedDate());
                memo.setReadyCommClacDate(rs.getDate("READY_COMM_CALC_DATE"));
                debug("in dao: " + memo.getReadyCommClacDate());


                listToReturn.add(memo);
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;
    }

    public static int[] getMemoSettingsData(Connection con, String memoId) {
        int[] vec = new int[2];
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getmemosettingsdata";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoId));
            stmt.registerOutParameter(2, OracleTypes.VARCHAR);
            stmt.registerOutParameter(3, OracleTypes.VARCHAR);
            stmt.execute();
            int settingsId = stmt.getInt(2);


            int periodId = stmt.getInt(3);

            vec[0] = settingsId;
            vec[1] = periodId;

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return vec;
    }

    public static int[] getMemoWeeksData(Connection con, String memoId, String settingsId, String periodId) {
        int[] vec = new int[2];
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getmemoweekdata";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoId));
            stmt.setInt(2, Integer.parseInt(settingsId));
            stmt.setInt(3, Integer.parseInt(periodId));
            stmt.registerOutParameter(4, OracleTypes.VARCHAR);
            stmt.registerOutParameter(5, OracleTypes.VARCHAR);
            stmt.execute();

            vec[0] = stmt.getInt(4);
            vec[1] = stmt.getInt(5);

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return vec;
    }

    public static void updateMemoWeeksData(Connection con, String memoId, String weekId, String assignedPeriodId) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.updateMemoWeeksData";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoId));
            stmt.setInt(2, Integer.parseInt(weekId));
            stmt.setInt(3, Integer.parseInt(assignedPeriodId));
            stmt.execute();


        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }

    }

    public static int[] getMemoMonthsData(Connection con, String memoId, String settingsId, String periodId) {
        int[] vec = new int[2];
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getmemomonthdata";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoId));
            stmt.setInt(2, Integer.parseInt(settingsId));
            stmt.setInt(3, Integer.parseInt(periodId));
            stmt.registerOutParameter(4, OracleTypes.VARCHAR);
            stmt.registerOutParameter(5, OracleTypes.VARCHAR);
            stmt.execute();

            vec[0] = stmt.getInt(4);
            vec[1] = stmt.getInt(5);

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return vec;
    }

    public static void updateMemoMonthsData(Connection con, String memoId, String monthId, String assignedPeriodId) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.updatememomonthdata";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoId));
            stmt.setInt(2, Integer.parseInt(monthId));
            stmt.setInt(3, Integer.parseInt(assignedPeriodId));
            stmt.execute();


        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }

    }

    public static String[] getMemoQrtrData(Connection con, String memoId, String settingsId, String periodId) {
        String[] vec = new String[3];
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getmemoqrtrdata";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoId));
            stmt.setInt(2, Integer.parseInt(settingsId));
            stmt.setInt(3, Integer.parseInt(periodId));
            stmt.registerOutParameter(4, OracleTypes.INTEGER);
            stmt.registerOutParameter(5, OracleTypes.VARCHAR);
            stmt.registerOutParameter(6, OracleTypes.INTEGER);
            stmt.execute();

            vec[0] = String.valueOf(stmt.getInt(4));

            vec[1] = stmt.getString(5);

            vec[2] = stmt.getString(6);


        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return vec;
    }

    public static void updateMemoQrtrData(Connection con, String memoId, String qrtrId, String assignedPeriodId) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.updatememoqrtrdata";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoId));
            stmt.setInt(2, Integer.parseInt(qrtrId));
            stmt.setInt(3, Integer.parseInt(assignedPeriodId));
            stmt.execute();


        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }

    }

    public static Vector viewMemo(Connection con, String memoId) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.view_memo";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoId));
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(2);
            while (rs.next()) {
                MemoMembersModel types = new MemoMembersModel(rs.getInt("scm_id"), rs.getString("dcm_name"), rs.getInt("commission"));
                listToReturn.add(types);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static Vector<MemoMembersModel> viewClosedPayments(Connection con, String paymentName,
            String dcmName, String dcmCode, String startDate, String endDate, String paymentTypeId, String addStartDate, String addEndDate, String frozenStatus) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.viewClosedPayments";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?,?,?,?,?,?,?)}";
            stmt = con.prepareCall(query);
            // stmt.setInt(1, Integer.parseInt(memo_id));
            if (paymentName == null || paymentName.equals("")) {
                stmt.setString(1, null);
            } else {
                stmt.setString(1, paymentName);
            }
            debug("payment: " + paymentName);


            if (dcmName == null || dcmName.equals("")) {
                stmt.setString(2, null);
            } else {
                stmt.setString(2, "'" + dcmName + "'");
            }


            debug("dcmName: " + dcmName);


            if (dcmCode == null || dcmCode.equals("")) {
                stmt.setString(3, null);
            } else {
                stmt.setString(3, "'" + dcmCode + "'");
            }
            debug("dcmCode: " + dcmCode);


            if (startDate == null || startDate.equals("Start_Date")) {
                stmt.setString(4, null);
            } else {
                stmt.setString(4, "'" + startDate + "'");
            }
            debug("startDate: " + startDate);


            if (endDate == null || endDate.equals("End_Date")) {
                stmt.setString(5, null);
            } else {
                stmt.setString(5, "'" + endDate + "'");
            }
            debug("endDate: " + endDate);


            if (paymentTypeId == null || paymentTypeId.equals("")) {
                stmt.setInt(6, -1);
            } else {
                stmt.setInt(6, Integer.parseInt(paymentTypeId));
            }
            debug("paymentTypeId: " + paymentTypeId);


            if (addStartDate == null || addStartDate.equals("Start_Date")) {
                stmt.setString(7, null);
            } else {
                stmt.setString(7, "'" + addStartDate + "'");
            }
            debug("addStartDate: " + addStartDate);


            if (addEndDate == null || addEndDate.equals("End_Date")) {
                stmt.setString(8, null);
            } else {
                stmt.setString(8, "'" + addEndDate + "'");
            }
            debug("addEndDate: " + addEndDate);


            if (frozenStatus == null || frozenStatus.equals("")) {
                stmt.setString(9, null);
            } else {
                stmt.setString(9, "'" + frozenStatus + "'");
            }
            debug("frozenStatus: " + frozenStatus);

            stmt.registerOutParameter(10, OracleTypes.CURSOR);

            stmt.execute();

            rs = (ResultSet) stmt.getObject(10);

            while (rs.next()) {
                MemoMembersModel types = new MemoMembersModel(rs.getInt("payment_id"), rs.getString("payment_name"), rs.getInt("commission"), rs.getString("PAYMENT_START_DATE"), rs.getString("PAYMENT_END_DATE"), rs.getString("FROZEN_FLAG"));
                types.setAddToAccountingModuleDate(rs.getString("accdate"));
                listToReturn.add(types);
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

    public static Vector getMemoPayments(Connection con, String memoId) {
        Vector listToReturn = new Vector();

        ResultSet rs = null;
        Statement stmt = null;
        try {
            String query = "SELECT DISTINCT(PAYMENT_ID) ,PAYMENT_DETAIL.PAYMENT_NAME,PAYMENT_TYPE.PAYMENT_TYPE_NAME,PAYMENT_TYPE.PAYMENT_TYPE_ID"
                    + " FROM CAM_PAYMENT JOIN PAYMENT_DETAIL"
                    + " ON CAM_PAYMENT.PAYMENT_ID = PAYMENT_DETAIL.PAYMENT_DETAIL_ID"
                    + " join payment_type"
                    + " on(PAYMENT_TYPE.PAYMENT_TYPE_ID=PAYMENT_DETAIL.PAYMENT_TYPE_ID)"
                    + " join cam_memo"
                    + " on(CAM_MEMO.memo_id = CAM_PAYMENT.memo_id)"
                    + " where cam_payment.memo_id=cam_memo.memo_id and MEMO_ID=" + memoId;

            System.out.println("sql: " + query);



            stmt = con.createStatement();



            rs = stmt.executeQuery(query);


            while (rs.next()) {
                MemoMembersModel types = new MemoMembersModel();
                types.setPaymentId(rs.getInt("PAYMENT_ID"));
                types.setPaymentTypeId(rs.getInt("PAYMENT_TYPE_ID"));
                types.setPaymentTypename(rs.getString("PAYMENT_TYPE_NAME"));
                types.setPaymetName(rs.getString("payment_name"));
                listToReturn.add(types);
            }
            stmt.close();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);



        }
        debug("list size: " + listToReturn.size());
        return listToReturn;

    }

    public static void setPaymentEligibleToDCM(Connection con, String dcmCode) {

        String query = " insert into cam_payment_scm_status(SCM_PAYMENT_STATUS_ID, scm_id,payment_cam_state_id,payment_status_reason_id)"
                + " select seq_payment_scm_status.NEXTVAL,dcm_id,1,1 from gen_dcm where GEN_DCM.DCM_CODE = '"
                + dcmCode.trim() + "' and dcm_id not in (select scm_id from cam_payment_scm_status)";

        System.out.println("sql: " + query);


        DBUtil.executeSQL(query, con);

    }

    public static void setPaymentEligibleToDCMFromGen(Connection con) {
        Statement stmt = null;
        try {
            String query = " insert into cam_payment_scm_status(SCM_PAYMENT_STATUS_ID, scm_id,payment_cam_state_id,payment_status_reason_id)"
                    + " select seq_payment_scm_status.NEXTVAL,dcm_id,1,1 from gen_dcm where GEN_DCM.CHANNEL_ID in(1,2,3) and GEN_DCM.DCM_LEVEL_ID in(1,2) and dcm_id not in (select scm_id from cam_payment_scm_status)";

            System.out.println("sql: " + query);
            stmt = con.createStatement();
            stmt.close();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }

    public static Vector getSqlTemplates(Connection con, String templateId) {
        Vector listToReturn = new Vector();

        ResultSet rs = null;
        Statement stmt = null;
        try {
            String query = "SELECT * from CAM_SQL_TEMPLATES ";
            if (templateId != null && templateId.compareTo("") != 0) {
                query += " where TEMPLATE_ID= " + templateId;
            }

            System.out.println("sql: " + query);



            stmt = con.createStatement();



            rs = stmt.executeQuery(query);


            while (rs.next()) {
                SqlTemplateModel temp = new SqlTemplateModel();
                temp.setTemplateId(rs.getInt("TEMPLATE_ID"));
                temp.setTemplateSql(rs.getString("TEMPLATE_SQL"));
                temp.setTemplateName(rs.getString("TEMPLATE_NAME"));
                listToReturn.add(temp);
            }
            stmt.close();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);



        }
        debug("list size: " + listToReturn.size());
        return listToReturn;

    }

    public static Vector updateSqlTemplate(Connection con, String templateId, String templateSql, String templateName) {
        Vector listToReturn = new Vector();


        try {
            String query = "update CAM_SQL_TEMPLATES set TEMPLATE_SQL=? , TEMPLATE_NAME=? where TEMPLATE_ID= " + templateId;


            System.out.println("sql: " + query);

            oracle.jdbc.OraclePreparedStatement pstmt = (oracle.jdbc.OraclePreparedStatement) con.prepareStatement(query);


            pstmt.setStringForClob(1, templateSql);
            pstmt.setStringForClob(2, templateName);

            pstmt.execute();
            pstmt.close();


        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
        }
        debug("list size: " + listToReturn.size());
        return listToReturn;

    }

    public static void addSqlTemplate(Connection con, String templateSql, String templateName) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        Statement st = null;


        try {
            String query = "insert into CAM_SQL_TEMPLATES (TEMPLATE_SQL ,TEMPLATE_NAME , TEMPLATE_ID) values('" + templateSql + "' , '" + templateName + "', CAM_SQL_TEMPLATE.NEXTVAL )";


            System.out.println("sql: " + query);


            st = con.createStatement();
            st.execute(query);


        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
        }


    }

    public static Vector viewDcmInClosedPayments(Connection con,
            String dcmName, String dcmCode, String stkNumber) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.viewDCMInClosedPayments";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?)}";
            stmt = con.prepareCall(query);
            // stmt.setInt(1, Integer.parseInt(memo_id));

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
                MemoMembersModel types = new MemoMembersModel(rs.getInt("payment_id"), rs.getString("payment_name"), rs.getInt("scm_commission_value"), rs.getString("PAYMENT_START_DATE"), rs.getString("PAYMENT_END_DATE"));
                listToReturn.add(types);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static Vector viewDcmStatusHistory(Connection con, String dcmName,
            String dcmCode, String stkNumber) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getDcmStatusHistory";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?)}";
            stmt = con.prepareCall(query);
            // stmt.setInt(1, Integer.parseInt(memo_id));

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
                debug("status history");
                DcmStatusHistoryModel dcmstatus = new DcmStatusHistoryModel(rs.getString("ACTION_NAME"), rs.getString("REASON"), rs.getString("TIME_STAMP"), rs.getString("USER_LOGIN"));
                listToReturn.add(dcmstatus);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static Vector viewDcmMemoHistory(Connection con, String dcmName,
            String dcmCode, String stkNumber) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getDcmMemoHistory";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?)}";
            stmt = con.prepareCall(query);
            // stmt.setInt(1, Integer.parseInt(memo_id));

            if (dcmName == null || dcmName.equals("")) {
                stmt.setString(1, null);
            } else {
                stmt.setString(1, "'" + dcmName + "'");
            }
            debug("dcmName:>" + dcmName + "<");

            if (dcmCode == null || dcmCode.equals("")) {
                stmt.setString(2, null);
            } else {
                stmt.setString(2, "'" + dcmCode + "'");
            }
            debug("dcmCode:>" + dcmCode + "<");

            if (stkNumber == null || stkNumber.equals("")) {
                stmt.setString(3, null);
            } else {
                stmt.setString(3, "'" + stkNumber + "'");
            }
            debug("stkNumber:>" + stkNumber + "<");

            stmt.registerOutParameter(4, OracleTypes.CURSOR);

            stmt.execute();

            rs = (ResultSet) stmt.getObject(4);
            while (rs.next()) {

                DcmMemoHistoryModel dcmMemo = new DcmMemoHistoryModel(rs.getString("MEMO_NAME"), rs.getString("ACTION_NAME"),
                        rs.getString("TIME_STAMP"), rs.getString("USER_LOGIN"),
                        rs.getString("REASON_NAME"));
                listToReturn.add(dcmMemo);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static Vector viewGenDcmMemoHistory(Connection con, String dcmName,
            String dcmCode, String stkNumber) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getDcmMemoHistory";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?)}";
            stmt = con.prepareCall(query);
            // stmt.setInt(1, Integer.parseInt(memo_id));

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

                DcmMemoHistoryModel dcmMemo = new DcmMemoHistoryModel(rs.getString("MEMO_NAME"), rs.getString("ACTION_NAME"),
                        rs.getString("TIME_STAMP"), rs.getString("USER_LOGIN"),
                        rs.getString("REASON_NAME"));
                listToReturn.add(dcmMemo);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static void updatePaymentFrozenStatus(Connection con, String paymentId, String frozenStatusId) {

        CallableStatement stmt = null;

        try {
            String procedureName = "cam_memo_pkg.updateFrozenPayment";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);

            if (paymentId == null || paymentId.equals("")) {
                stmt.setString(1, null);
            } else {
                stmt.setString(1, paymentId);
            }

            if (Integer.parseInt(frozenStatusId) == 0) {
                stmt.setString(2, "FROZEN");
            } else if (Integer.parseInt(frozenStatusId) == 1) {
                stmt.setString(2, "NOT FROZEN");
            }


            stmt.execute();


        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {

            Utility.closeCallbaleStatement(stmt);


        }
    }

    public static void updateDcmFrozenStatus(Connection con, String paymentId, String dcmId, String frozenStatusId) {

        CallableStatement stmt = null;

        try {
            String procedureName = "cam_memo_pkg.updateFrozenDcm";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);

            stmt.setInt(1, Integer.parseInt(paymentId));

            stmt.setInt(2, Integer.parseInt(dcmId));

            if (Integer.parseInt(frozenStatusId) == 0) {
                stmt.setString(3, "FROZEN");
            } else if (Integer.parseInt(frozenStatusId) == 1) {
                stmt.setString(3, "NOT FROZEN");
            }


            stmt.execute();


        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {

            Utility.closeCallbaleStatement(stmt);


        }
    }

    public static Vector viewPayment(Connection con, String paymentId) {
        Vector listToReturn = new Vector();
        Statement stmt = null;
        ResultSet rs = null;
        try {

            stmt = con.createStatement();

            String sql =
                    " SELECT cam_payment.payment_id, cam_payment.scm_id,"
                    + " payment_detail.payment_name, gen_dcm.dcm_name, gen_dcm.dcm_code,"
                    + " cam_payment.scm_commission_value, cam_payment.frozen_flag , cam_payment_cam_State.CAM_STATUS_FOR_PAYMENT"
                    + " FROM cam_payment  , payment_detail , "
                    + " gen_dcm , CAM_PAYMENT_SCM_STATUS ,CAM_PAYMENT_CAM_STATE "
                    + " WHERE (cam_payment.memo_id = NULL OR cam_payment.flag = \'NOT INCLUDED\')"
                    + " and cam_payment.payment_id = payment_detail.payment_detail_id"
                    + " and cam_payment.scm_id = gen_dcm.dcm_id "
                    + " and cam_payment_scm_status.payment_cam_state_id = cam_payment_cam_state.id(+) "
                    + " and cam_payment.SCM_ID = cam_payment_scm_status.SCM_ID (+) ";




            if (paymentId != null && !paymentId.equals("")) {

                sql = sql + "AND CAM_PAYMENT.PAYMENT_ID= " + paymentId;
            }


            System.out.println(sql);


            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                MemoMembersModel types = new MemoMembersModel();
                types.setPaymentId(rs.getInt("payment_id"));

                types.setPaymetName(rs.getString("payment_name"));

                types.setDcmName(rs.getString("dcm_name"));
                types.setDcmCode(rs.getString("dcm_code"));
                types.setScmId(rs.getInt("scm_id"));

                types.setScmCommissionValue(rs.getInt("scm_commission_value"));
                types.setFrozenStatus(rs.getString("FROZEN_FLAG"));
                types.setPaymentStatusName(rs.getString("CAM_STATUS_FOR_PAYMENT"));
                listToReturn.add(types);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeStatement(stmt);

        }
        return listToReturn;

    }

    public static Vector getDcmMemos(Connection con, String dcmName,
            String dcmCode, String stkNumber) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getDcmMemos";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?)}";
            stmt = con.prepareCall(query);
            // stmt.setInt(1, Integer.parseInt(memo_id));
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
                MemoModel memo = new MemoModel();
                memo.setId(rs.getInt("MEMO_ID"));
                memo.setName(rs.getString("MEMO_NAME"));
                memo.setDesc(rs.getString("DESCRIPTION"));
                memo.setType(rs.getString("MEMO_TYPE"));
                memo.setStartDate(rs.getDate("CREATION_DATE"));
                memo.setState(rs.getString("STATE"));
                memo.setChannel(rs.getString("CHANNEL_NAME"));
                memo.setCommissionValue(rs.getInt("commission"));
                memo.setDeductionValue(rs.getInt("DEDUCTION"));
                memo.setPaymentDate(rs.getDate("PAYMENT_DATE"));
                memo.setIssueDate(rs.getDate("issuedate"));
                memo.setFinanceIssueDate(rs.getDate("financeIssueDate"));

                debug("memo id in search: " + memo.getId());
                memo.setPaymentNames(searchInMemo(con, memo.getId() + "", dcmCode, dcmName, stkNumber, ""));
                debug("payments size: " + memo.getPaymentNames().size());

                listToReturn.add(memo);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static Vector getDcmDeductions(Connection con, String dcmName,
            String dcmCode, String stkNumber) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getDcmDeductios";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?)}";
            stmt = con.prepareCall(query);
            // stmt.setInt(1, Integer.parseInt(memo_id));
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
                DcmDeductionsModel dcmDeductions = new DcmDeductionsModel(rs.getInt("DEDUCTION_VALUE"),
                        rs.getString("REASON_NAME"), rs.getString("STATUS_NAME"), rs.getString("DEDUCTION_DATE"), rs.getString("GROUP_PAY_TYPE_NAME"), rs.getInt("REMAINING_VALUE"));
                listToReturn.add(dcmDeductions);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static void deleteMemo(Connection con, String memoId, String userId) {
        // Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {

            System.out.println("memoID: " + memoId);
            System.out.println("userId: " + userId);
            String procedureName = "cam_memo_pkg.deleteMemo";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoId));
            stmt.setInt(2, Integer.parseInt(userId));
            stmt.execute();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        // return listToReturn;

    }

    public static void editMemo(Connection con, String memoId,
            String memoComment, String userId, String memo_title) {
        // Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.editmemo";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoId));
            stmt.setString(2, memoComment);
            stmt.setString(3, memo_title);
            stmt.setInt(4, Integer.parseInt(userId));
            stmt.execute();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        // return listToReturn;

    }

    public static String[] getMemoText(Connection con, String memoId) {
        String[] comment_title_arr = new String[2];
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getmemotext";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoId));
            stmt.registerOutParameter(2, OracleTypes.VARCHAR);
            stmt.registerOutParameter(3, OracleTypes.VARCHAR);
            stmt.execute();
            comment_title_arr[0] = (stmt.getString(2) == null) ? "" : stmt.getString(2);
            comment_title_arr[1] = (stmt.getString(3) == null) ? "" : stmt.getString(3);

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return comment_title_arr;
    }

    public static String issueMemo(Connection con, String memoId,
            String userId) {
        // Vector listToReturn = new Vector();
        String errMsg = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.issueMemo";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoId));
            stmt.setInt(2, Integer.parseInt(userId));
            stmt.registerOutParameter(3, OracleTypes.VARCHAR);
            stmt.execute();
            errMsg = stmt.getString(3);


        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        // return listToReturn;
        return errMsg;

    }

    public static void financeIssueMemo(Connection con, String memoId,
            String userId) {
        // Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.financeIssueMemo";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoId));
            stmt.setInt(2, Integer.parseInt(userId));
            stmt.execute();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        // return listToReturn;

    }

    public static void approveToAp(Connection con, String memoId,
            String userId) {
        String sqlString1 = "";
        String sqlString2 = "";

        try {
            con = Utility.getConnection();
            Statement stmt = con.createStatement();
            sqlString1 = "UPDATE CAM_MEMO SET CAM_MEMO.state_id = 6 WHERE CAM_MEMO.memo_id = " + memoId;
            stmt.executeUpdate(sqlString1);
            sqlString2 = "INSERT INTO CAM_MEMO_MAKER (CAM_MEMO_MAKER.maker_id, CAM_MEMO_MAKER.action_id , CAM_MEMO_MAKER.record_id, CAM_MEMO_MAKER.time_stamp, CAM_MEMO_MAKER.user_id ) VALUES (seq_cam_memo_maker.NEXTVAL, 3," + memoId + ", SYSDATE," + userId + ")";
            stmt.executeUpdate(sqlString2);
            stmt.close();
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    public static void approveToLogestic(Connection con, String memoId,
            String userId) {
        String sqlString1 = "";
        String sqlString2 = "";

        try {
            con = Utility.getConnection();
            Statement stmt = con.createStatement();
            sqlString1 = "UPDATE CAM_MEMO SET CAM_MEMO.state_id = 7 WHERE CAM_MEMO.memo_id = " + memoId;
            stmt.executeUpdate(sqlString1);
            sqlString2 = "INSERT INTO CAM_MEMO_MAKER (CAM_MEMO_MAKER.maker_id, CAM_MEMO_MAKER.action_id , CAM_MEMO_MAKER.record_id, CAM_MEMO_MAKER.time_stamp, CAM_MEMO_MAKER.user_id ) VALUES (seq_cam_memo_maker.NEXTVAL, 3," + memoId + ", SYSDATE," + userId + ")";
            stmt.executeUpdate(sqlString2);
            stmt.close();
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    public static void addToCreditModule(Connection con, String memoId,
            String userId) {
        String sqlString1 = "";
        String sqlString2 = "";

        try {
            con = Utility.getConnection();
            Statement stmt = con.createStatement();
            sqlString1 = "UPDATE CAM_MEMO SET CAM_MEMO.state_id = 8 WHERE CAM_MEMO.memo_id = " + memoId;
            stmt.executeUpdate(sqlString1);
            sqlString2 = "INSERT INTO CAM_MEMO_MAKER (CAM_MEMO_MAKER.maker_id, CAM_MEMO_MAKER.action_id , CAM_MEMO_MAKER.record_id, CAM_MEMO_MAKER.time_stamp, CAM_MEMO_MAKER.user_id ) VALUES (seq_cam_memo_maker.NEXTVAL, 3," + memoId + ", SYSDATE," + userId + ")";
            stmt.executeUpdate(sqlString2);
            stmt.close();
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    public static void financeIssueMemo(Connection con, String memoId) {
        // Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.issueMemo";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoId));
            stmt.execute();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        // return listToReturn;

    }

    public static void removeMembersOFMemo(Connection con, String memoId,
            String dcmId, String makerId) {
        // Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.removeMembersOfMemo";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(dcmId));
            stmt.setInt(2, Integer.parseInt(memoId));
            stmt.setInt(3, Integer.parseInt(makerId));
            stmt.execute();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        // return listToReturn;

    }

    public static void removePyamentInMemo(Connection con, String memoId,
            String paymentId, String makerId) {
        // Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;

        System.out.println("removePyamentInMemo ");
        System.out.println("memoId :" + memoId);
        System.out.println("paymentId :" + paymentId);
        System.out.println("removePyamentInMemo ");
        try {
            String procedureName = "cam_memo_pkg.removepaymentinmemo";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(paymentId));
            stmt.setInt(2, Integer.parseInt(memoId));
            stmt.setInt(3, Integer.parseInt(makerId));
            stmt.setInt(4, 1);
            stmt.execute();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        // return listToReturn;

    }

    public static int getMemoReportID(Connection con) {
        int id = 0;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getNextMemoFileID";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.INTEGER);
            stmt.execute();
            id = stmt.getInt(1);
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return id;

    }

    public static Vector searchInMemo(Connection con, String memoId,
            String dcmCode, String dcmName, String stknumber, String paymentId) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.searchInMemo";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?,?,?)}";
            stmt = con.prepareCall(query);
            if (memoId == null || memoId.equals("")) {
                stmt.setInt(1, -1);
            } else {
                stmt.setInt(1, Integer.parseInt(memoId));
            }
            debug("memoId: " + memoId);

            if (dcmCode == null || dcmCode.equals("")) {
                stmt.setString(2, null);
            } else {
                stmt.setString(2, "'" + dcmCode + "'");
            }

            if (dcmName == null || dcmName.equals("")) {
                stmt.setString(3, null);
            } else {
                stmt.setString(3, "'" + dcmName + "'");
            }
            if (stknumber == null || stknumber.equals("")) {
                stmt.setString(4, null);
            } else {
                stmt.setString(4, "'" + stknumber + "'");
            }


            if (paymentId == null || paymentId.equals("")) {
                stmt.setInt(5, -1);
            } else {
                stmt.setInt(5, Integer.parseInt(paymentId));
            }
            stmt.registerOutParameter(6, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(6);
            while (rs.next()) {
                MemoMembersModel types = new MemoMembersModel(rs.getInt("payment_id"), rs.getInt("scm_id"), rs.getString("payment_name"), rs.getString("dcm_name"),
                        rs.getInt("scm_commission_value"));


                listToReturn.add(types);
            }



        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static void getMemoSettings(Connection connection, String[] params,
            int paramCategoryNumber) {
        Statement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.createStatement();
            String query_str = "Select * from CAM_MEMO_SETTINGS WHERE MEMO_CATEGORY_NUM="
                    + paramCategoryNumber;

            System.out.println(query_str);
            rs = stm.executeQuery(query_str);
            String sett_key = "";
            String sett_value = "";
            while (rs.next()) {
                sett_key = rs.getString("SETT_KEY");
                sett_value = rs.getString("SET_VALUE");// logo_image_path
                if (sett_key.equalsIgnoreCase("logo_image_path")) {
                    params[2] = "\"" + sett_value + "\"";
                } else if (sett_key.equalsIgnoreCase("To")) {
                    params[3] = sett_value;
                } else if (sett_key.equalsIgnoreCase("From")) {
                    params[4] = sett_value;
                } else if (sett_key.equalsIgnoreCase("CC")) {
                    params[5] = sett_value;
                } else if (sett_key.equalsIgnoreCase("Subject")) {
                    params[6] = sett_value;
                } else if (sett_key.equalsIgnoreCase("report_reviewer")) {
                    params[7] = sett_value;
                } else if (sett_key.equalsIgnoreCase("report_reviewer_position")) {
                    params[8] = sett_value;
                } else if (sett_key.equalsIgnoreCase("report_reviewer_team")) {
                    params[9] = "";
                } else if (sett_key.equalsIgnoreCase("report_owner")) {
                    params[10] = sett_value;
                } else if (sett_key.equalsIgnoreCase("report_owner_position")) {
                    params[11] = sett_value;
                } else if (sett_key.equalsIgnoreCase("approval_person_1")) {
                    params[12] = sett_value;
                } else if (sett_key.equalsIgnoreCase("approval_person_1_position")) {
                    params[13] = sett_value;
                } else if (sett_key.equalsIgnoreCase("approval_person_2")) {
                    params[14] = sett_value;
                } else if (sett_key.equalsIgnoreCase("approval_person_2_position")) {
                    params[15] = sett_value;
                } else if (sett_key.equalsIgnoreCase("footer_summery_line")) {
                    params[16] = sett_value;
                } else if (sett_key.equalsIgnoreCase("himself_manager")) {
                    params[21] = sett_value;
                } else if (sett_key.equalsIgnoreCase("himself_manager_position")) {
                    params[22] = sett_value;
                }

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeStatement(stm);


        }

    }

    public static MemoModel getMemoById(Connection con, int memoId) {

        Statement stmt = null;
        ResultSet rs = null;
        MemoModel memo = null;
        try {

            String query = "SELECT * FROM cam_memo WHERE cam_memo.memo_id = " + memoId;
            System.out.println("query = " + query);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {

                memo = new MemoModel();

                memo.setId(rs.getInt(1));
                memo.setName(rs.getString(7));
                memo.setDesc("");
                memo.setStartDate(rs.getDate(3));
                memo.setChannel(rs.getString("channel_id"));
                memo.setPaymentType(rs.getString("payment_Type_id"));
                memo.setType("");
                memo.setState("");



            }



        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeStatement(stmt);
        }
        return memo;
    }

    public static Vector getMemoReasons(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getMemoREASONS";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                MemoReasonModel memoReason = new MemoReasonModel(rs.getInt("REASON_ID"), rs.getString("REASON_NAME"), rs.getString("REASON_DESC"));
                listToReturn.add(memoReason);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static Vector getMemoDelayReasons(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getmemodelayreasons";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                MemoReasonModel memoReason = new MemoReasonModel(rs.getInt("REASON_ID"), rs.getString("REASON_NAME"), rs.getString("REASON_DESC"));
                listToReturn.add(memoReason);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static Vector getMemoReasonsById(Connection con, String reasonId) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getMemoREASONSByID";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(reasonId));
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(2);
            while (rs.next()) {
                MemoReasonModel memoReason = new MemoReasonModel(rs.getInt("REASON_ID"), rs.getString("REASON_NAME"), rs.getString("REASON_DESC"));
                listToReturn.add(memoReason);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static Vector getMemoDelayReasonsById(Connection con, String reasonId) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getmemodelayreasonsbyid";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(reasonId));
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(2);
            while (rs.next()) {
                MemoReasonModel memoReason = new MemoReasonModel(rs.getInt("REASON_ID"), rs.getString("REASON_NAME"), rs.getString("REASON_DESC"));
                listToReturn.add(memoReason);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static Vector getMemoDelayReasons(Connection con, String memoId) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getmemodelays";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoId));
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(2);
            while (rs.next()) {
                MemoReasonModel memoReason = new MemoReasonModel(rs.getInt("REASON_ID"), rs.getString("REASON_NAME"), rs.getString("REASON_DESC"));
                listToReturn.add(memoReason);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static Vector getMemos(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getMemoS";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                MemoModel memo = new MemoModel(rs.getInt("MEMO_ID"), rs.getString("MEMO_NAME"));
                listToReturn.add(memo);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static Vector viewMemoReasons(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.viewMemoREASONS";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                MemoReasonModel memoReason = new MemoReasonModel(rs.getInt("REASON_ID"), rs.getString("REASON_NAME"), rs.getString("REASON_DESC"));
                listToReturn.add(memoReason);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return listToReturn;

    }

    public static void addMemoReason(Connection con, String reasonName,
            String reasonDesc) {
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            String procedureName = "cam_memo_pkg.ADDMEMOREASON";
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

    public static void addMemoDelayReason(Connection con, String reasonName,
            String reasonDesc) {
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            String procedureName = "cam_memo_pkg.addmemodelayreason";
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

    public static void deleteMemoReason(Connection con, String reasonId) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.DELETEMEMOREASONS";
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

    public static void deleteMemoDelayReason(Connection con, String reasonId) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.deletememodelayreason";
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

    public static void updateMemoReason(Connection con, String reasonId,
            String reasonName, String reasonDesc) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.UPDATEMEMOREASONS";
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

    public static void updateMemoDelayReason(Connection con, String reasonId,
            String reasonName, String reasonDesc) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.updatememoDelayreason";
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

    public static void updateMemoDelayReasons(Connection con, String reasonId, String memoId) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.updatememodelayreasons";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoId));
            stmt.setInt(2, Integer.parseInt(reasonId));
            stmt.execute();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }

    }

    public static void deleteMemoDelayReasons(Connection con, String memoId) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.deletememodelayreasons";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoId));
            stmt.execute();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }

    }

    public static int getMemoUploadFileId(Connection con) {
        int id = 0;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.GETMEMOREMOVALDATAFILEID";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.INTEGER);
            stmt.execute();
            id = stmt.getInt(1);
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return id;

    }

    public static void uploadMemoRemoval(Connection con,
            MemoRemovalImportModel memoRemoval, String memoId,
            String fileName, String userId) {

        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            String procedureName = "cam_memo_pkg.IMPORTMEMOREMOVALDATA";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setString(1, fileName);
            stmt.setString(2, memoRemoval.getDcmCode());
            stmt.setString(3, memoRemoval.getReason());
            stmt.setInt(4, Integer.parseInt(memoId));
            stmt.setString(5, userId);
            stmt.execute();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }

    }

    public static Vector getTableDefByCategory(String categoryId) {
        Vector tableVec = new Vector();
        Statement stat = null;
        ResultSet rs = null;
        try {
            Connection con = Utility.getConnection();
            stat = con.createStatement();
            rs = stat.executeQuery("select * from vw_ADM_DATA_IMPORT_DEF where TABLE_CATEGORY_ID = " + categoryId + " ");
            while (rs.next()) {
                tableVec.add(new DataImportTableDefModel(rs));
            }
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeStatement(stat);


        }

        return tableVec;
    }

    public static DataImportTableDefModel getTableDef(String tableId) {

        DataImportTableDefModel tableDefModel = null;
        Statement stat = null;
        ResultSet res = null;
        try {
            Connection con = Utility.getConnection();
            stat = con.createStatement();
            res = stat.executeQuery("select * from vw_ADM_DATA_IMPORT_DEF where table_id=" + tableId);
            if (res.next()) {
                tableDefModel = new DataImportTableDefModel(res);
            }
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            Utility.closeResultSet(res);
            Utility.closeStatement(stat);


        }

        return tableDefModel;
    }

    public static Vector getTmpMemoRemoval() {
        Vector vec = new Vector();
        Statement stat = null;
        ResultSet res = null;
        try {
            Connection con = Utility.getConnection();
            stat = con.createStatement();
            res = stat.executeQuery("select * from CAM_MEMO_REMOVAL_DATA ");

            while (res.next()) {
                vec.add(new MemoRemovalImportModel(res));
            }
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Utility.closeResultSet(res);
            Utility.closeStatement(stat);


        }

        return vec;
    }

    public static void deleteTmpMemoRemoval() {
        Statement stat = null;
        ResultSet res = null;

        try {
            Connection con = Utility.getConnection();
            stat = con.createStatement();


            String strSql = ("delete from  CAM_MEMO_REMOVAL_DATA");



            stat.execute(strSql);
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Utility.closeResultSet(res);
            Utility.closeStatement(stat);


        }


    }

    public static void insertNewMemoRemovalData(String tmScmCode, String tmReason, String memoId,
            String userId) {


        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            Connection con = Utility.getConnection();
            String procedureName = "cam_memo_pkg.IMPORTMEMOREMOVALDATA";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?)}";
            stmt = con.prepareCall(query);

            stmt.setString(1, tmScmCode);
            stmt.setString(2, tmReason);
            stmt.setInt(3, Integer.parseInt(memoId));
            stmt.setString(4, userId);
            stmt.execute();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }

    }

    public static Vector getMemoTypeExcelAttributes(Connection con, String memoTypeId) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getMemoExcelManagement";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);

            stmt.setInt(1, Integer.parseInt(memoTypeId));

            stmt.registerOutParameter(2, OracleTypes.CURSOR);

            stmt.execute();
            rs = (ResultSet) stmt.getObject(2);
            while (rs.next()) {

                PaymentTypeExcelModel model = new PaymentTypeExcelModel();
                model.setPaymentExcelManagementId(rs.getInt("MEMO_EXCEL_MENEGEMENT_ID"));
                model.setPaymentType(rs.getString("PAYMENT_TYPE_NAME"));
                model.setExcelSheetNumber(rs.getInt("EXCEL_SHEET_NUMBER"));
                model.setExcelSheetName(rs.getString("EXCEL_SHEET_NAME"));
                if (rs.getInt("SQL_TEMPLATE_ID") != -1) {
                    model.setExcelSheetSqlStatement(rs.getString("TEMPLATE_SQL"));

                } else {
                    model.setExcelSheetSqlStatement(rs.getString("EXCEL_SHEET_SQL_STATEMENT"));
                }



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

    public static PaymentTypeExcelModel getMemoExcelSheetAttributes(Connection con, String memoTypeExcelSheetId) {
        PaymentTypeExcelModel model = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getMemoExcelSheetAttributes";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);

            stmt.setInt(1, Integer.parseInt(memoTypeExcelSheetId));

            stmt.registerOutParameter(2, OracleTypes.CURSOR);

            stmt.execute();
            rs = (ResultSet) stmt.getObject(2);
            while (rs.next()) {


                model = new PaymentTypeExcelModel();
                model.setPaymentExcelManagementId(rs.getInt("memo_EXCEL_MENEGEMENT_ID"));
                model.setPaymentType(rs.getString("PAYMENT_TYPE_NAME"));
                model.setExcelSheetNumber(rs.getInt("EXCEL_SHEET_NUMBER"));
                model.setExcelSheetName(rs.getString("EXCEL_SHEET_NAME"));
                if (rs.getInt("SQL_TEMPLATE_ID") != -1) {
                    model.setExcelSheetSqlStatement(rs.getString("TEMPLATE_SQL"));

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

    public static void updateMemoExcelSheet(Connection con, String memoExcelId, String sheetNumber, String sheetName, String sqlStatement) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String sqlString = "update CAM_MEMO_EXCEL_MANAGEMENT set EXCEL_SHEET_NUMBER = "
                    + sheetNumber
                    + ", EXCEL_SHEET_NAME = '"
                    + sheetName
                    + "', EXCEL_SHEET_SQL_STATEMENT = "
                    + "?"
                    + " where MEMO_EXCEL_MENEGEMENT_ID= "
                    + memoExcelId;
            System.out.println("sql:" + sqlString);
            oracle.jdbc.OraclePreparedStatement pstmt = (oracle.jdbc.OraclePreparedStatement) con.prepareStatement(sqlString);


            pstmt.setStringForClob(1, sqlStatement);



//		String procedureName = "cam_memo_pkg.updateExcelSheetAttributes";
//		String query = null;
//		query = "{call " + procedureName + "(?,?,?,?)}";
//		stmt = con.prepareCall(query);		
//
//		stmt.setInt(1, Integer.parseInt(memoExcelId));
//
//		stmt.setInt(2, Integer.parseInt(sheetNumber));
//
//		stmt.setString(3, sheetName);
//
//		stmt.setString(4, sqlStatement);


            pstmt.execute();
            pstmt.close();

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        /*
         * try{ String sql="update CAM_PAYMENT_EXCEL_MANAGEMENT set
         * EXCEL_SHEET_NUMBER=?, EXCEL_SHEET_NAME=?, EXCEL_SHEET_SQL_STATEMENT=?
         * where PAYMENT_EXCEL_MENEGEMENT_ID=?"; PreparedStatement
         * stmt=con.prepareStatement(sql); stmt.setInt(1,
         * Integer.parseInt(sheetNumber)); stmt.setString(2, sheetName);
         * stmt.setString(3, sqlStatement); stmt.setInt(4,
         * Integer.parseInt(paymentExcelId)); stmt.addBatch();
         * stmt.executeBatch(); }catch(SQLException sqlex){
         * sqlex.printStackTrace(); }
         */
    }

    public static void insertMemoExcelAttributes(Connection con, String paymentTypeId, String sheetNumber, String sheetName, String sqlStatement, String tempId) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        Statement st = null;
        try {
            if (Integer.parseInt(tempId) != -1) {
                String sqlString = "insert into CAM_MEMO_EXCEL_MANAGEMENT(MEMO_EXCEL_MENEGEMENT_ID,PAYMENT_TYPE_ID,EXCEL_SHEET_NUMBER,EXCEL_SHEET_NAME,SQL_TEMPLATE_ID)  "
                        + " values("
                        + "MEMO_EXCEL_MANAGEMENT_SEQ.nextval,"
                        + paymentTypeId
                        + ","
                        + sheetNumber
                        + " ,'"
                        + sheetName
                        + "',"
                        + "2)";
                System.out.println("sql:" + sqlString);
                st = con.createStatement();
                st.execute(sqlString);

                st.close();

            } else {
                String sqlString = "insert into CAM_MEMO_EXCEL_MANAGEMENT(MEMO_EXCEL_MENEGEMENT_ID,PAYMENT_TYPE_ID,EXCEL_SHEET_NUMBER,EXCEL_SHEET_NAME,SQL_TEMPLATE_ID,EXCEL_SHEET_SQL_STATEMENT)  "
                        + " values("
                        + "MEMO_EXCEL_MANAGEMENT_SEQ.nextval,"
                        + paymentTypeId
                        + ","
                        + sheetNumber
                        + " ,'"
                        + sheetName
                        + "',"
                        + "-1,?)";
                System.out.println("sql:" + sqlString);
                oracle.jdbc.OraclePreparedStatement pstmt = (oracle.jdbc.OraclePreparedStatement) con.prepareStatement(sqlString);


                pstmt.setStringForClob(1, sqlStatement);



//	String procedureName = "cam_memo_pkg.insertMemoExcelManagement";
//	String query = null;
//	query = "{call " + procedureName + "(?,?,?,?)}";
//	stmt = con.prepareCall(query);			
//	stmt.setInt(1, Integer.parseInt(paymentTypeId));
//	stmt.setInt(2, Integer.parseInt(sheetNumber));
//	stmt.setString(3, sheetName);
//	stmt.setString(4, sqlStatement);


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

    public static boolean checkExcelSheetUniqueness(Connection con, String paymentTypeId, String sheetNumber) {
        String result = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.checkExcelSheetUniqueness";
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

    public static Vector getMemoGenerationSettings(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getMemoGenerationSettings";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {

                MemoPaymentTypeSettingsModel model = new MemoPaymentTypeSettingsModel(rs.getInt("MEMO_SETTINGS_ID"), rs.getString("PAYMENT_TYPE_NAME"), rs.getString("MEMO_GENERATION_PERIOD"), rs.getInt("TRCKING_FLAG"));
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

    public static MemoPaymentTypeSettingsModel getMemoPaymentTypeSettings(Connection con, String memoSettingsId) {
        MemoPaymentTypeSettingsModel model = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getpaymentTypeSettings";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoSettingsId));
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(2);
            while (rs.next()) {
                model = new MemoPaymentTypeSettingsModel(rs.getInt("MEMO_SETTINGS_ID"), rs.getString("PAYMENT_TYPE_NAME"), rs.getString("MEMO_GENERATION_PERIOD"), rs.getInt("TRCKING_FLAG"));


            }



        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return model;
    }

    public static Vector getMemoGenerationPeriods(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getMemoGenerationperiods";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {

                MemoGenerationPeriodsModel model = new MemoGenerationPeriodsModel(rs.getInt("MEMO_GENERATION_PERIOD_ID"), rs.getString("MEMO_GENERATION_PERIOD"));
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

    public static void updateMemoGenerationSettings(Connection con, String memoSettingsId, String period, String trackingFlag) {
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            String procedureName = "cam_memo_pkg.updatePaymentTypeSeetings";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);

            stmt.setInt(1, Integer.parseInt(memoSettingsId));

            stmt.setInt(2, Integer.parseInt(period));

            stmt.setInt(3, Integer.parseInt(trackingFlag));

            stmt.execute();




        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }

    }

    public static void insertMemoWeekSettings(Connection con, String memoSettingsId, int weekId, String dayOfWeekId) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.insertMemoWeekSettings";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoSettingsId));
            stmt.setInt(2, weekId);

            stmt.setInt(3, Integer.parseInt(dayOfWeekId));
            stmt.execute();




        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }

    }

    public static int[][] getMemoWeekSettings(Connection con, String memoSettingsId) {
        int[][] weeks = new int[52][1];
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            String procedureName = "cam_memo_pkg.getMemoWeekSettings";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);

            stmt.setInt(1, Integer.parseInt(memoSettingsId));
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(2);
            int i = 0;
            while (rs.next()) {
                weeks[i][0] = rs.getInt("DAY_OF_WEEK_ID");
                debug("day of week " + i + "is: " + rs.getInt("DAY_OF_WEEK_ID"));
                i++;
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return weeks;

    }

    public static void insertMemoMonthSettings(Connection con, String memoSettingsId, int monthId, String dayInMonthId) {
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            String procedureName = "cam_memo_pkg.insertmemomonthSettings";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoSettingsId));

            stmt.setInt(2, monthId);
            stmt.setInt(3, Integer.parseInt(dayInMonthId));
            stmt.execute();




        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }

    }

    public static int[][] getMemoMonthSettings(Connection con, String memoSettingsId) {
        int[][] months = new int[12][1];
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getmemomonthSettings";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoSettingsId));
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(2);
            int i = 0;
            while (rs.next()) {
                months[i][0] = rs.getInt("DATE_IN_MONTH");
                i++;

            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return months;
    }

    public static void insertMemoQuarterSettings(Connection con, String memoSettingsId, int qrtrId, int monthInQrtr, int dayInMonth) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.insertmemoquartersettings";
            String query = null;
            query = "{call " + procedureName + "(?,?,?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoSettingsId));
            stmt.setInt(2, qrtrId);
            stmt.setInt(3, monthInQrtr);
            stmt.setInt(4, dayInMonth);
            stmt.execute();




        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }

    }

    public static void deleteMemoSettings(Connection con, String memoSettingsId) {
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            String procedureName = "cam_memo_pkg.deletememosettings";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoSettingsId));
            stmt.execute();




        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }

    }

    public static int[][] getMemoQuarterSettings(Connection con, String memoSettingsId) {
        int[][] qrtr = new int[4][2];
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.getmemoquartersettings";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(memoSettingsId));
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(2);
            int i = 0;
            int monthInQrtr = 0;
            int dayInMonth = 0;
            while (rs.next()) {
                monthInQrtr = rs.getInt("MONTH_IN_QUARTER");
                dayInMonth = rs.getInt("DAY_IN_MONTH");
                qrtr[i][0] = monthInQrtr;
                qrtr[i][1] = dayInMonth;
                i++;

            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        return qrtr;
    }

    public static Vector getPaymentTypePdfAttributes(Connection con, String paymentTypeId) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_payment_pkg.getmemoPdfManagement";
            String query = null;
            query = "{call " + procedureName + "(?,?)}";
            stmt = con.prepareCall(query);

            stmt.setInt(1, Integer.parseInt(paymentTypeId));

            stmt.registerOutParameter(2, OracleTypes.CURSOR);

            stmt.execute();
            rs = (ResultSet) stmt.getObject(2);
            while (rs.next()) {

                PaymentTypePDFModel model = new PaymentTypePDFModel();
                model.setPaymentPdfManagmentId(rs.getInt("PAYMENT_PDF_MENEGEMENT_ID"));
                model.setPaymentTypeName(rs.getString("PAYMENT_TYPE_NAME"));
                model.setQueriesParams(rs.getString("QUERIES_PARAMS"));
                if (rs.getInt("SQL_TEMPLATE_ID") != -1) {
                    model.setPdfSql(rs.getString("TEMPLATE_SQL"));

                } else {
                    model.setPdfSql(rs.getString("PDF_SQL_STATEMENT"));

                }


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

    public static void insertPaymentPdfAttributes(Connection con, String paymentTypeId, String pdfName, String sqlStatement, String tempId) {
        CallableStatement stmt = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            if (Integer.parseInt(tempId) != -1) {
                String sqlString = "insert into CAM_MEMO_PDF_MANAGEMENT(PAYMENT_PDF_MENEGEMENT_ID,PAYMENT_TYPE_ID,QUERIES_PARAMS,SQL_TEMPLATE_ID)"
                        + "   values(MEMO_PDF_MANAGEMENT_SEQ.nextval,"
                        + paymentTypeId
                        + ",'"
                        + pdfName
                        + "' ,"
                        + "3)";
                System.out.println("sql:" + sqlString);


                st = con.createStatement();
                st.execute(sqlString);

                st.close();

            } else {
                String sqlString = "insert into CAM_MEMO_PDF_MANAGEMENT(PAYMENT_PDF_MENEGEMENT_ID,PAYMENT_TYPE_ID,QUERIES_PARAMS,SQL_TEMPLATE_ID,PDF_SQL_STATEMENT)"
                        + "   values(MEMO_PDF_MANAGEMENT_SEQ.nextval,"
                        + paymentTypeId
                        + ",'"
                        + pdfName
                        + "' ,"
                        + "-1,?)";
                System.out.println("sql:" + sqlString);
                oracle.jdbc.OraclePreparedStatement pstmt = (oracle.jdbc.OraclePreparedStatement) con.prepareStatement(sqlString);


                pstmt.setStringForClob(1, sqlStatement);

//	String procedureName = "cam_payment_pkg.insertmemoPdfManagement";
//	String query = null;
//	query = "{call " + procedureName + "(?,?,?)}";
//	stmt = con.prepareCall(query);			
//	stmt.setInt(1, Integer.parseInt(paymentTypeId));
//	stmt.setString(2, pdfName);
//	stmt.setString(3, sqlStatement);


                pstmt.execute();
                pstmt.close();
            }



        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);



        }
    }

    public static void updatePaymentPdfSheet(Connection con, String paymentPdfId, String queriesParams, String sqlStatement) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String sqlString = " update CAM_MEMO_PDF_MANAGEMENT set QUERIES_PARAMS = '"
                    + queriesParams
                    + "', PDF_SQL_STATEMENT = "
                    + "? where PAYMENT_PDF_MENEGEMENT_ID = "
                    + paymentPdfId;
            System.out.println("sql:" + sqlString);
            oracle.jdbc.OraclePreparedStatement pstmt = (oracle.jdbc.OraclePreparedStatement) con.prepareStatement(sqlString);


            pstmt.setStringForClob(1, sqlStatement);
//		String procedureName = "cam_payment_pkg.updatememoPdfAttributes";
//		String query = null;
//		query = "{call " + procedureName + "(?,?,?)}";
//		stmt = con.prepareCall(query);		
//		stmt.setInt(1, Integer.parseInt(paymentPdfId));
//		stmt.setString(2, queriesParams);
//		stmt.setString(3, sqlStatement);
            pstmt.execute();
            pstmt.close();


        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
        /*
         * try{ String sql="update CAM_PAYMENT_EXCEL_MANAGEMENT set
         * EXCEL_SHEET_NUMBER=?, EXCEL_SHEET_NAME=?, EXCEL_SHEET_SQL_STATEMENT=?
         * where PAYMENT_EXCEL_MENEGEMENT_ID=?"; PreparedStatement
         * stmt=con.prepareStatement(sql); stmt.setInt(1,
         * Integer.parseInt(sheetNumber)); stmt.setString(2, sheetName);
         * stmt.setString(3, sqlStatement); stmt.setInt(4,
         * Integer.parseInt(paymentExcelId)); stmt.addBatch();
         * stmt.executeBatch(); }catch(SQLException sqlex){
         * sqlex.printStackTrace(); }
         */
    }

    public static void clearSearchDcmDetailsList(Connection con) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.clearsearchdcmDetailslist";
            String query = null;
            query = "{call " + procedureName + "()}";
            stmt = con.prepareCall(query);
            stmt.execute();


        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
    }

    public static void setSearchDcmDetailsList(Connection con, String dcmName, String dcmCode, String stkNumaber) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.setsearchdcmDetailslist";
            String query = null;
            query = "{call " + procedureName + "(?,?,?)}";
            stmt = con.prepareCall(query);
            if (dcmName == null || dcmName.compareTo("null") == 0) {
                stmt.setString(1, null);
            } else {
                stmt.setString(1, dcmName);
            }

            if (dcmCode == null || dcmCode.compareTo("null") == 0) {
                stmt.setString(2, null);
            } else {
                stmt.setString(2, dcmCode);
            }

            if (stkNumaber == null || stkNumaber.compareTo("null") == 0) {
                stmt.setString(3, null);
            } else {
                stmt.setString(3, stkNumaber);
            }


            stmt.execute();


        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeCallbaleStatement(stmt);


        }
    }

    public static Vector viewSearchDcmDetailsList(Connection con) {
        Vector listToReturn = new Vector();
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            String procedureName = "cam_memo_pkg.viewsearchdcmDetailslist";
            String query = null;
            query = "{call " + procedureName + "(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {

                PaymentScmStatusModel model = new PaymentScmStatusModel();
                model.setDcmName(rs.getString("dcm_name"));
                model.setDcmCode(rs.getString("dcm_code"));
                model.setStkNumber(rs.getString("STK_NUMBER"));

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

    private static String adjustDate(String date) {
        String temp = date.substring(6, date.length());

        temp += "-";
        temp += date.substring(0, 2);

        temp += "-";
        temp += date.substring(3, 5);

        return temp;
    }

    public static void createPaymentExcelsFile(String path) {
        System.out.println("extract payment excel file");

        Statement st = null;
        ResultSet rs = null;
        try {
            Connection con = Utility.getConnection();
            String sqlString = "select CAM_PAYMENT_EXCEL_MANAGEMENT.PAYMENT_EXCEL_MENEGEMENT_ID,"
                    + " CAM_PAYMENT_EXCEL_MANAGEMENT.PAYMENT_TYPE_ID,"
                    + " payment_type.PAYMENT_TYPE_NAME, "
                    + " CAM_PAYMENT_EXCEL_MANAGEMENT.EXCEL_SHEET_NUMBER,"
                    + " CAM_PAYMENT_EXCEL_MANAGEMENT.EXCEL_SHEET_NAME,"
                    + " CAM_PAYMENT_EXCEL_MANAGEMENT.EXCEL_SHEET_SQL_STATEMENT,"
                    + " CAM_PAYMENT_EXCEL_MANAGEMENT.SQL_TEMPLATE_ID,"
                    + " CAM_SQL_TEMPLATES.TEMPLATE_SQL"
                    + " from CAM_PAYMENT_EXCEL_MANAGEMENT join payment_type"
                    + " on(CAM_PAYMENT_EXCEL_MANAGEMENT.PAYMENT_TYPE_ID=payment_type.PAYMENT_TYPE_ID)"
                    + " left join CAM_SQL_TEMPLATES"
                    + " on CAM_SQL_TEMPLATES.TEMPLATE_ID=CAM_PAYMENT_EXCEL_MANAGEMENT.SQL_TEMPLATE_ID "
                    + " order by PAYMENT_EXCEL_MENEGEMENT_ID,excel_sheet_number";
            System.out.println("sql:" + sqlString);


            st = con.createStatement();
            rs = st.executeQuery(sqlString);

            PrintWriter pw = new PrintWriter(path + "/payment excels");
            System.out.println("filePath: " + path + "/payment excels");
            pw.print("table id");
            pw.print(",");
            pw.print("payment type");
            pw.print(",");
            pw.print("sheet number");
            pw.print(",");
            pw.print("sheet name");
            pw.print(",");
            pw.print("sql");
            pw.println();
            pw.println();

            while (rs.next()) {
                pw.print(rs.getInt("PAYMENT_EXCEL_MENEGEMENT_ID"));
                pw.print(",");
                pw.print(rs.getString("payment_type_name"));
                pw.print(",");
                pw.print(rs.getInt("excel_sheet_number"));
                pw.print(",");
                pw.print(rs.getString("excel_sheet_name"));
                pw.print(",");
                if (rs.getInt("SQL_TEMPLATE_ID") != -1) {
                    pw.print(rs.getString("TEMPLATE_SQL"));
                } else {
                    pw.print(rs.getString("excel_sheet_sql_statement"));
                }
                pw.println();
                pw.println();
            }
            pw.close();


//		FileInputStream in =new FileInputStream(path+"\\payment excels.txt");
//		GZIPOutputStream out=new GZIPOutputStream(new FileOutputStream(path+"\\payment excels.zip"));
//		byte[] buffer=new byte[4096];
//		int bytesRead=0;
//		while((bytesRead=in.read(buffer))!=-1)
//			out.write(buffer,0,bytesRead);
//		
//		in.close();
//		out.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void createMemoExcelsFile(String path) {
        System.out.println("extract memo excel file");
        Statement st = null;
        ResultSet rs = null;
        try {
            Connection con = Utility.getConnection();
            String sqlString = "SELECT cam_memo_excel_management.memo_excel_menegement_id,"
                    + " payment_type.payment_type_name,"
                    + " cam_memo_excel_management.excel_sheet_number,"
                    + " cam_memo_excel_management.excel_sheet_name,"
                    + " cam_memo_excel_management.excel_sheet_sql_statement, "
                    + " CAM_MEMO_EXCEL_MANAGEMENT.SQL_TEMPLATE_ID,"
                    + " CAM_SQL_TEMPLATES.TEMPLATE_SQL "
                    + " FROM cam_memo_excel_management JOIN payment_type"
                    + " ON (cam_memo_excel_management.payment_type_id ="
                    + " payment_type.payment_type_id )"
                    + " left join CAM_SQL_TEMPLATES"
                    + " on CAM_SQL_TEMPLATES.TEMPLATE_ID=CAM_MEMO_EXCEL_MANAGEMENT.SQL_TEMPLATE_ID"
                    + " ORDER BY memo_excel_menegement_id,cam_memo_excel_management.excel_sheet_number";
            System.out.println("sql:" + sqlString);


            st = con.createStatement();
            rs = st.executeQuery(sqlString);

            PrintWriter pw = new PrintWriter(path + "\\memo excels.xls");
            System.out.println("filePath: " + path + "\\memo excels.xls");
            pw.print("table id");
            pw.print(",");
            pw.print("payment type");
            pw.print(",");
            pw.print("sheet number");
            pw.print(",");
            pw.print("sheet name");
            pw.print(",");
            pw.print("sql");
            pw.println();
            pw.println();

            while (rs.next()) {
                pw.print(rs.getInt("memo_excel_menegement_id"));
                pw.print(",");
                pw.print(rs.getString("payment_type_name"));
                System.out.println("payment Type: " + rs.getString("payment_type_name"));
                pw.print(",");
                pw.print(rs.getInt("excel_sheet_number"));
                pw.print(",");
                pw.print(rs.getString("excel_sheet_name"));
                pw.print(",");
                if (rs.getInt("SQL_TEMPLATE_ID") != -1) {
                    pw.print(rs.getString("TEMPLATE_SQL"));
                } else {
                    pw.print(rs.getString("excel_sheet_sql_statement"));
                }
                pw.println();
                pw.println();

            }
            pw.close();

//		FileInputStream in =new FileInputStream(path+"\\memo excels.txt");
//		GZIPOutputStream out=new GZIPOutputStream(new FileOutputStream(path+"\\memo excels.zip"));
//		byte[] buffer=new byte[4096];
//		int bytesRead=0;
//		while((bytesRead=in.read(buffer))!=-1)
//			out.write(buffer,0,bytesRead);
//		
//		in.close();
//		out.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void createMemoPdfsFile(String path) {
        System.out.println("extract memo pdf file");
        Statement st = null;
        ResultSet rs = null;
        try {
            Connection con = Utility.getConnection();
            String sqlString = "select CAM_MEMO_PDF_MANAGEMENT.PAYMENT_PDF_MENEGEMENT_ID,"
                    + " payment_type.PAYMENT_TYPE_NAME,"
                    + " CAM_MEMO_PDF_MANAGEMENT.QUERIES_PARAMS ,"
                    + " CAM_MEMO_PDF_MANAGEMENT.PDF_SQL_STATEMENT,"
                    + " CAM_MEMO_PDF_MANAGEMENT.SQL_TEMPLATE_ID,"
                    + " CAM_SQL_TEMPLATES.TEMPLATE_SQL"
                    + " from CAM_MEMO_PDF_MANAGEMENT join payment_type"
                    + " on(CAM_MEMO_PDF_MANAGEMENT.PAYMENT_TYPE_ID=payment_type.PAYMENT_TYPE_ID)"
                    + " left join CAM_SQL_TEMPLATES"
                    + " on CAM_SQL_TEMPLATES.TEMPLATE_ID=CAM_MEMO_PDF_MANAGEMENT.SQL_TEMPLATE_ID "
                    + " order by PAYMENT_PDF_MENEGEMENT_ID";
            System.out.println("sql:" + sqlString);


            st = con.createStatement();
            rs = st.executeQuery(sqlString);

            PrintWriter pw = new PrintWriter(path + "\\memo pdfs.xls");
            System.out.println("filePath: " + path + "\\memo pdfs.xls");
            pw.print("table id");
            pw.print(",");
            pw.print("payment type");
            pw.print(",");
            pw.print("queries params");
            pw.print(",");
            pw.print("sql");
            pw.println();
            pw.println();

            while (rs.next()) {
                pw.print(rs.getInt("PAYMENT_PDF_MENEGEMENT_ID"));
                pw.print(",");
                pw.print(rs.getString("payment_type_name"));
                pw.print(",");
                pw.print(rs.getString("QUERIES_PARAMS"));
                pw.print(",");
                if (rs.getInt("SQL_TEMPLATE_ID") != -1) {
                    pw.print(rs.getString("TEMPLATE_SQL"));
                } else {
                    pw.print(rs.getString("PDF_SQL_STATEMENT"));
                }
                pw.println();
                pw.println();

            }
            pw.close();


//		FileInputStream in =new FileInputStream(path+"\\memo pdfs.txt");
//		GZIPOutputStream out=new GZIPOutputStream(new FileOutputStream(path+"\\memo pdfs.zip"));
//		byte[] buffer=new byte[4096];
//		int bytesRead=0;
//		while((bytesRead=in.read(buffer))!=-1)
//			out.write(buffer,0,bytesRead);
//		
//		in.close();
//		out.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Vector getTmpFocPaymentSearchList(Connection con) throws SQLException {

        Statement st = null;
        ResultSet rs = null;
        Vector listToReturn = new Vector();
        try {
            String sqlString = "SELECT dcm_name,dcm_code,STK_NUMBER FROM GEN_DCM WHERE dcm_code IN(SELECT SCM_CODE FROM TMP_SCM_CODE)";
            System.out.println("sql:" + sqlString);


            st = con.createStatement();
            rs = st.executeQuery(sqlString);
            while (rs.next()) {

                PaymentScmStatusModel model = new PaymentScmStatusModel();
                model.setDcmName(rs.getString("dcm_name"));
                model.setDcmCode(rs.getString("dcm_code"));
                model.setStkNumber(rs.getString("STK_NUMBER"));

                listToReturn.add(model);
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            Utility.closeResultSet(rs);
            Utility.closeStatement(st);

        }
        return listToReturn;


    }
}