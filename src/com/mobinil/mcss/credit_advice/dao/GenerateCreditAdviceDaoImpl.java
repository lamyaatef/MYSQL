/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.dao;

import com.mobinil.mcss.credit_advice.model.*;
import com.mobinil.mcss.credit_advice.util.HibernateUtil;
import com.mobinil.sds.core.system.commission.model.LabelModel;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author JOE
 */
public class GenerateCreditAdviceDaoImpl implements GenerateCreditAdviceDao {

    Connection newConnection = null;

    @Override
    public List getPreparedCredits(Long memoID, String memoName, String dateFrom, String dateTo, Long fileID) {

        List<CAMemo> CAMemoList = new ArrayList<CAMemo>();
        List<CAFile> preparedCredits = new ArrayList<CAFile>();


        try {
            newConnection = Utility.getConnection();
            Statement stat = newConnection.createStatement();



            StringBuilder str = new StringBuilder("SELECT MEMO_ID,MEMO_NAME,DESCRIPTION,CREDIT_ADVICE_FILE_ID FROM CAM_MEMO ,CRD_FILE_TRACE");
            str.append(" WHERE CAM_MEMO.CREDIT_ADVICE_FILE_ID IS NOT NULL ");
            str.append(" AND CRD_FILE_TRACE.FILE_TRACE_DATE =(SELECT max(FILE_TRACE_DATE) FROM CRD_FILE_TRACE WHERE FILE_ID=CAM_MEMO.CREDIT_ADVICE_FILE_ID )");
            str.append(" AND CRD_FILE_TRACE.STATUS_ID=1");

            if (memoID != null && memoID != 0) {
                str.append(" AND CAM_MEMO.MEMO_ID=").append(memoID);
            }
            if (memoName != null && !memoName.trim().equals("")) {
                str.append(" AND CAM_MEMO.MEMO_NAME='").append(memoName).append("'");
            }
            if (dateFrom != null && dateTo != null) {
                str.append(" AND CRD_FILE_TRACE.FILE_TRACE_DATE BETWEEN to_date ('").append(dateFrom).append("' , 'yyyy/mm/dd') AND to_date ('").append(dateTo).append("' ,'yyyy/mm/dd')");
            }
            if (fileID != null && fileID != 0) {
                str.append(" AND CAM_MEMO.CREDIT_ADVICE_FILE_ID=").append(fileID);
            }
            str.append(" GROUP BY MEMO_ID,MEMO_NAME,DESCRIPTION,CREDIT_ADVICE_FILE_ID");

            System.out.println("SQL:" + str);
            //StringBuilder str2 = new StringBuilder("SELECT * FROM CRD_FILE_TRACE");



            ResultSet res = stat.executeQuery(str.toString());
            while (res.next()) {

                CAMemo camMemo = new CAMemo();
                camMemo.setMemoId(res.getLong("MEMO_ID"));
                camMemo.setMemoName(res.getString("MEMO_NAME"));
                camMemo.setDesc(res.getString("DESCRIPTION"));
                camMemo.setCafileId(res.getLong("CREDIT_ADVICE_FILE_ID"));

                CAMemoList.add(camMemo);

            }

            HashMap<Long, CAFile> recordsMap = new HashMap<Long, CAFile>();

            for (CAMemo cAMemo : CAMemoList) {


                //   for (CAFile cAFile : preparedCredits) {
                //       if (cAMemo.getCafileId() != cAFile.getFileId()) {

                if (recordsMap.get(cAMemo.getCafileId()) == null) {

                    CAFile cAFileNew = new CAFile();
                    cAFileNew.setFileId(cAMemo.getCafileId());
                    cAFileNew.getCamMemos().add(cAMemo);
                    recordsMap.put(cAMemo.getCafileId(), cAFileNew);
                } else {

                    CAFile cAFile = recordsMap.get(cAMemo.getCafileId());

                    cAFile.getCamMemos().add(cAMemo);

                }

                preparedCredits = new ArrayList<CAFile>(recordsMap.values());

//                    } else if (cAMemo.getCafileId() == cAFile.getFileId()) {
//                        cAFile.getCamMemos().add(cAMemo);
//                    }
                //    }


            }




        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            Utility.closeConnection(newConnection);
        }
        return preparedCredits;
    }

    @Override
    public List viewCredits(Long fileID) {
        List<CAMemo> CAMemoList = new ArrayList<CAMemo>();


        try {
            newConnection = Utility.getConnection();
            Statement stat = newConnection.createStatement();

            StringBuilder str = new StringBuilder("SELECT GEN_DCM.DCM_ID,GEN_DCM.DCM_NAME,CAM_PAYMENT.MEMO_ID,CAM_MEMO.memo_id");
            str.append(",CAM_PAYMENT.SCM_ID,NVL (vw_dcm_grouping.dcm_code, gen_dcm.dcm_code) AS parent_code,CAM_MEMO.CREDIT_ADVICE_FILE_ID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) AS SCM_COMMISSION_VALUE");
            str.append(",SUM(CAM_PAYMENT.DEDUCTIONS_VALUE) AS DEDUCTIONS_VALUE,CAM_MEMO.memo_name,ACC_WITH_HOLD_TAX.WITH_HOLD_TAX FROM CAM_MEMO ");
            str.append(",CAM_PAYMENT,GEN_DCM ,ACC_WITH_HOLD_TAX,vw_dcm_grouping");
            str.append(" WHERE CAM_PAYMENT.MEMO_ID=CAM_MEMO.memo_id");
            str.append(" AND CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID");
            str.append(" AND gen_dcm.dcm_code = franchise_code(+)");
            str.append(" AND GEN_DCM.DCM_CODE=ACC_WITH_HOLD_TAX.dcm_code");
            str.append(" AND CAM_MEMO.CREDIT_ADVICE_FILE_ID=").append(fileID);
            str.append(" GROUP BY GEN_DCM.DCM_ID,GEN_DCM.DCM_NAME,CAM_PAYMENT.MEMO_ID,CAM_MEMO.memo_id");
            str.append(",CAM_PAYMENT.SCM_ID,ACC_WITH_HOLD_TAX.dcm_code,CAM_MEMO.CREDIT_ADVICE_FILE_ID,CAM_MEMO.memo_name,ACC_WITH_HOLD_TAX.WITH_HOLD_TAX,NVL (vw_dcm_grouping.dcm_code, gen_dcm.dcm_code)");
            str.append(" ORDER BY GEN_DCM.DCM_ID");
            System.out.println("SQL:" + str);
            ResultSet res = stat.executeQuery(str.toString());

            while (res.next()) {
                CAMemo caMemo = new CAMemo();
                caMemo.setMemoId(res.getLong("MEMO_ID"));
                // caMemo.setStateId(res.getLong("STATE_ID"));
                caMemo.setMemoName(res.getString("MEMO_NAME"));
                //  caMemo.setDesc(res.getString("DESCRIPTION"));
                //   caMemo.setChannelId(res.getLong("CHANNEL_ID"));
                //   caMemo.setStartDate(res.getDate("CREATION_DATE"));
                //    caMemo.setMemoComment(res.getString("MEMO_COMMENT_TEXT"));
                //   caMemo.setSendForValidationDate(res.getDate("SEND_FOR_VALIDATION_DATE"));
                //     caMemo.setApprovalDate(res.getDate("APPROVAL_DATE"));
                //  caMemo.setSalesManagerApprovalDate(res.getDate("SALES_MANAGER_APPROVAL_DATE"));
//                caMemo.setSalesBackOfficeApprovalDate(res.getDate("SALES_BACK_OFFICE_APPROVAL_DAT"));
//                caMemo.setSalesDirectiveApprovalDate(res.getDate("SALES_DIRECTIVE_APPROVAL_DATE"));
//                caMemo.setFinanceReceiveDate(res.getDate("FINANCE_RECEIVE_DATE"));
//                caMemo.setPaymentDate(res.getDate("PAYMENT_DATE"));
                caMemo.setCafileId(res.getLong("CREDIT_ADVICE_FILE_ID"));
                caMemo.setScmId(res.getLong("SCM_ID"));
                caMemo.setScmCommissionValue(res.getLong("SCM_COMMISSION_VALUE"));
                caMemo.setDeductionsValue(res.getLong("DEDUCTIONS_VALUE"));
                caMemo.setDcmName(res.getString("DCM_NAME"));
                caMemo.setDcmCode(res.getString("PARENT_CODE"));
                caMemo.setWithHoldTax(res.getFloat("WITH_HOLD_TAX"));



                CAMemoList.add(caMemo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Utility.closeConnection(newConnection);
        }

        return CAMemoList;

    }

    @Override
    public void deleteCredits(Long fileId, String userID) {


        try {
            newConnection = Utility.getConnection();
            Statement stat = newConnection.createStatement();

            String sql4 = "SELECT MEMO_ID FROM CAM_MEMO WHERE CREDIT_ADVICE_FILE_ID=" + fileId;
            ResultSet res = stat.executeQuery(sql4);

            List<Long> memoIds = new ArrayList<Long>();
            while (res.next()) {

                memoIds.add(res.getLong("MEMO_ID"));
            }


            String sql = "UPDATE CAM_MEMO set CREDIT_ADVICE_FILE_ID =NULL WHERE CREDIT_ADVICE_FILE_ID=" + fileId;
            stat.executeUpdate(sql);

            System.out.println(sql);

            int fileTraceId = getSeqValue(newConnection, "CRD_FILE_TRACE_SEQ");
            String sql2 = "insert into CRD_FILE_TRACE (ID,USER_ID,file_id,FILE_TRACE_DATE,STATUS_ID) values (" + fileTraceId + "," + userID + "," + fileId + ", SysDate ,3)";
            stat.executeUpdate(sql2);
            System.out.println(sql2);


            for (Long memoId : memoIds) {
                String sql3 = "insert into CRD_MEMO_ARCHIVE (ID,MEMO_ID,CRD_FILE_TRACE_ID) values (CRD_MEMO_ARCHIVE_ID_SEQ.nextval," + memoId + "," + fileTraceId + ")";
                stat.executeUpdate(sql3);
                System.out.println(sql3);
            }




        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Utility.closeConnection(newConnection);

        }

    }

    @Override
    public void generateCAFile(String userID, List<Long> fileIds) {
        try {
            newConnection = Utility.getConnection();
            Statement stat = newConnection.createStatement();

            for (Long fileId : fileIds) {


                String sql = "SELECT MEMO_ID FROM CAM_MEMO WHERE CREDIT_ADVICE_FILE_ID = " + fileId;
                System.out.println("SQL:" + sql);
                ResultSet res = stat.executeQuery(sql);
                List<Long> memoIds = new ArrayList<Long>();
                while (res.next()) {

                    memoIds.add(res.getLong("MEMO_ID"));
                }
                String sql2 = "";

                List<CamPayment> caPayment = new ArrayList<CamPayment>();

                for (Long memoId : memoIds) {
                    sql2 = "SELECT cdcm.dcm_id scm_id ,CAM_PAYMENT.RECORD_ID,CAM_PAYMENT.deductions_value,CAM_PAYMENT.scm_commission_value FROM CAM_PAYMENT,GEN_DCM,GEN_DCM cdcm,vw_dcm_grouping WHERE CAM_PAYMENT.scm_id=GEN_DCM.dcm_id AND NVL(vw_dcm_grouping.dcm_code, GEN_DCM.dcm_code)=cdcm.dcm_code AND GEN_DCM.dcm_code = franchise_code(+) AND MEMO_ID=" + memoId;
                    System.out.println("SQL 2:" + sql2);
                    ResultSet resPayments = stat.executeQuery(sql2);

                    //List<Long> recordIds = new ArrayList<Long>();

                    HashMap<Long, Integer> recordsMap = new HashMap<Long, Integer>();


                    while (resPayments.next()) {
                        CamPayment camPayment = new CamPayment();
                        camPayment.setScmId(resPayments.getLong("SCM_ID"));
                        camPayment.setRecordId(resPayments.getLong("RECORD_ID"));
                        camPayment.setDeductionsValue(resPayments.getLong("deductions_value"));
                        camPayment.setScmCommissionValue(resPayments.getLong("scm_commission_value"));

                        Long deductionvalue = camPayment.getDeductionsValue();
                        Long commissionvalue = camPayment.getScmCommissionValue();
                        float total = commissionvalue - deductionvalue;

                        camPayment.setTotal(total);

                        caPayment.add(camPayment);

                    }


                    for (CamPayment camPayment : caPayment) {


                        if (recordsMap.get(camPayment.getScmId()) == null) {
                            Integer creditId = getSeqValue(newConnection, "CRD_CREDIT_DETAILS_SEQ");

                            String sql3 = "insert into CRD_CREDIT_DETAILS (CREDIT_SERIAL,CREDIT_ADVICE_ID,STATUS_ID,AMOUNT) values (CREDIT_SERIAL_SEQ.nextval," + creditId + ",1," + camPayment.getTotal() + ")";
                            System.out.println("SQL 3:" + sql3);
                            stat.executeQuery(sql3);
                            recordsMap.put(camPayment.getScmId(), creditId);
                            String sql4 = "UPDATE CAM_PAYMENT SET CREDIT_ADVICE_ID=" + creditId + " WHERE RECORD_ID=" + camPayment.getRecordId();
                            System.out.println("SQL UPDATE PAYMENT:" + sql4);
                            stat.executeUpdate(sql4);
                        } else {
                            Integer oldCreditId = recordsMap.get(camPayment.getScmId());

                            String sql6 = "SELECT amount from CRD_CREDIT_DETAILS WHERE CREDIT_ADVICE_ID=" + oldCreditId;
                            ResultSet res3 = stat.executeQuery(sql6);


                            while (res3.next()) {
                                long amount = res3.getLong("AMOUNT");
                                amount = (long) (amount + camPayment.getTotal());
                                String sql7 = "Update CRD_CREDIT_DETAILS SET AMOUNT=" + amount + " WHERE CREDIT_ADVICE_ID=" + oldCreditId;
                                stat.executeUpdate(sql7);
                            }

                            String sql4 = "UPDATE CAM_PAYMENT SET CREDIT_ADVICE_ID=" + oldCreditId + " WHERE RECORD_ID=" + camPayment.getRecordId();
                            System.out.println("SQL UPDATE PAYMENT:" + sql4);
                            stat.executeUpdate(sql4);

                        }
                    }




                }


                // int fileTraceId = getSeqValue(newConnection, "CRD_FILE_TRACE_SEQ");
                String sql5 = "insert into CRD_FILE_TRACE (ID,USER_ID,file_id,FILE_TRACE_DATE,STATUS_ID) values (CRD_FILE_TRACE_SEQ.nextval," + userID + "," + fileId + ", SysDate ,2)";
                System.out.println("SQLLLL:" + sql5);

                stat.executeUpdate(sql5);
                System.out.println(sql5);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Utility.closeConnection(newConnection);
        }
    }

    public static int getSeqValue(Connection con, String seqName) throws SQLException {

        String sql = "select " + seqName + ".nextVal val from dual";
        Statement st = con.createStatement();
        ResultSet seqValueRS = st.executeQuery(sql);
        int seqValue = 0;
        try {

            if (seqValueRS.next()) {
                seqValue = seqValueRS.getInt("val");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return seqValue;
    }
}
