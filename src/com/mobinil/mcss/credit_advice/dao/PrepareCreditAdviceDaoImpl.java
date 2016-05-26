/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.dao;

import com.mobinil.mcss.credit_advice.model.CAFile;
import com.mobinil.mcss.credit_advice.model.CATrace;
import com.mobinil.mcss.credit_advice.model.CAMemo;
import com.mobinil.mcss.credit_advice.model.CreditDetails;
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
import java.util.List;
//import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Brain
 */
public class PrepareCreditAdviceDaoImpl implements PrepareCreditAdviceDao {

    Connection newConnection = null;

    @Override
    public List getAvailableMemos(Long memoID, String memoName, String dateFrom, String dateTo) {

        List<CAMemo> caMemoList = new ArrayList<CAMemo>();


        try {
            newConnection = Utility.getConnection();
            Statement stat = newConnection.createStatement();

            StringBuilder str = new StringBuilder("SELECT * From cam_memo WHERE state_id = 8 and CREDIT_ADVICE_FILE_ID is null ");
            if (memoID != null && memoID != 0) {
                str.append(" AND cam_memo.memo_id=").append(memoID);
            }
            if (memoName != null && !memoName.trim().equals("")) {
                str.append(" AND cam_memo.MEMO_NAME='").append(memoName).append("'");
            }
            if (dateFrom != null && dateTo != null) {
                str.append(" AND CREATION_DATE BETWEEN to_date ('").append(dateFrom).append("' , 'yyyy/mm/dd') AND to_date ('").append(dateTo).append("' ,'yyyy/mm/dd')");
            }

            System.out.println("SQL:" + str);
            ResultSet res = stat.executeQuery(str.toString());
            while (res.next()) {
                CAMemo caMemo = new CAMemo();
                caMemo.setMemoId(res.getLong("MEMO_ID"));
                caMemo.setStateId(res.getLong("STATE_ID"));
                caMemo.setMemoName(res.getString("MEMO_NAME"));
                caMemo.setDesc(res.getString("DESCRIPTION"));
                caMemo.setChannelId(res.getLong("CHANNEL_ID"));
                caMemo.setStartDate(res.getDate("CREATION_DATE"));
                caMemo.setMemoComment(res.getString("MEMO_COMMENT_TEXT"));
                caMemo.setSendForValidationDate(res.getDate("SEND_FOR_VALIDATION_DATE"));
                caMemo.setApprovalDate(res.getDate("APPROVAL_DATE"));
                caMemo.setSalesManagerApprovalDate(res.getDate("SALES_MANAGER_APPROVAL_DATE"));
                caMemo.setSalesBackOfficeApprovalDate(res.getDate("SALES_BACK_OFFICE_APPROVAL_DAT"));
                caMemo.setSalesDirectiveApprovalDate(res.getDate("SALES_DIRECTIVE_APPROVAL_DATE"));
                caMemo.setFinanceReceiveDate(res.getDate("FINANCE_RECEIVE_DATE"));
                caMemo.setPaymentDate(res.getDate("PAYMENT_DATE"));
                caMemo.setCafileId(res.getLong("CREDIT_ADVICE_FILE_ID"));
                caMemoList.add(caMemo);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            Utility.closeConnection(newConnection);
        }

        return caMemoList;

    }

    public List reviewCrPayment(Long memoID) {

        List<CAMemo> caMemoList = new ArrayList<CAMemo>();


        try {
            newConnection = Utility.getConnection();
            Statement stat = newConnection.createStatement();

            StringBuilder str = new StringBuilder("SELECT   cam_memo.memo_id,gen_channel.channel_name, NVL (vw_dcm_grouping.dcm_code, gen_dcm.dcm_code) AS parent_code,");
            str.append("gen_dcm.dcm_name, SUM(cam_payment.deductions_value) as deductions_value,");
            str.append("SUM(cam_payment.scm_commission_value) as scm_commission_value,cam_payment.scm_id ,acc_with_hold_tax.with_hold_tax");
            str.append(" FROM cam_memo, cam_payment, gen_dcm, acc_with_hold_tax, gen_channel,vw_dcm_grouping");
            str.append(" WHERE cam_payment.memo_id = cam_memo.memo_id");
            str.append(" AND cam_payment.scm_id = gen_dcm.dcm_id");
            str.append(" AND gen_dcm.dcm_code = franchise_code(+)");
            str.append(" AND gen_dcm.dcm_code = acc_with_hold_tax.dcm_code");
            str.append(" AND cam_memo.channel_id = gen_channel.channel_id");
            str.append(" AND cam_memo.memo_id=").append(memoID);
            str.append(" GROUP BY cam_memo.memo_id,gen_channel.channel_name,gen_dcm.dcm_code,gen_dcm.dcm_name,cam_payment.scm_id ,acc_with_hold_tax.with_hold_tax");
            str.append(" ,NVL (vw_dcm_grouping.dcm_code, gen_dcm.dcm_code)");

            System.out.println("SQL:" + str);

            ResultSet res = stat.executeQuery(str.toString());
            while (res.next()) {
                CAMemo caMemo = new CAMemo();
                caMemo.setMemoId(res.getLong("MEMO_ID"));
                //     caMemo.setStateId(res.getLong("STATE_ID"));
                //   caMemo.setMemoName(res.getString("MEMO_NAME"));
                //   caMemo.setDesc(res.getString("DESCRIPTION"));
                //   caMemo.setChannelId(res.getLong("CHANNEL_ID"));
                //     caMemo.setStartDate(res.getDate("CREATION_DATE"));
                //     caMemo.setMemoComment(res.getString("MEMO_COMMENT_TEXT"));
                //     caMemo.setSendForValidationDate(res.getDate("SEND_FOR_VALIDATION_DATE"));
                //   caMemo.setApprovalDate(res.getDate("APPROVAL_DATE"));
                //   caMemo.setSalesManagerApprovalDate(res.getDate("SALES_MANAGER_APPROVAL_DATE"));
                // caMemo.setSalesBackOfficeApprovalDate(res.getDate("SALES_BACK_OFFICE_APPROVAL_DAT"));
                //  caMemo.setSalesDirectiveApprovalDate(res.getDate("SALES_DIRECTIVE_APPROVAL_DATE"));
                //   caMemo.setFinanceReceiveDate(res.getDate("FINANCE_RECEIVE_DATE"));
                //   caMemo.setPaymentDate(res.getDate("PAYMENT_DATE"));
                //  caMemo.setCafileId(res.getLong("CREDIT_ADVICE_FILE_ID"));
                caMemo.setScmId(res.getLong("SCM_ID"));
                caMemo.setScmCommissionValue(res.getLong("SCM_COMMISSION_VALUE"));
                caMemo.setDeductionsValue(res.getLong("DEDUCTIONS_VALUE"));
                caMemo.setDcmName(res.getString("DCM_NAME"));
                caMemo.setChannelName(res.getString("CHANNEL_NAME"));
                caMemo.setDcmCode(res.getString("PARENT_CODE"));
                caMemo.setWithHoldTax(res.getFloat("WITH_HOLD_TAX"));



                caMemoList.add(caMemo);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            Utility.closeConnection(newConnection);
        }

        return caMemoList;


    }

    public void prepareCA(List<CAMemo> memosList, String userID) {
        String sqlString = "";

        try {
            newConnection = Utility.getConnection();
            Statement stmt = newConnection.createStatement();
            int fileId = getSeqValue(newConnection, "CRD_ADVICE_FILE_SEQ");
            sqlString = "insert into CRD_ADVICE_FILE (FILE_ID,PRINTING ) values (" + fileId + ",NULL )";
            stmt.executeUpdate(sqlString);
            System.out.println("XXXXXXXXXXXX" + fileId);


            for (CAMemo cAMemo : memosList) {
                Long memoId = cAMemo.getMemoId();
                String sqlString1 = "update CAM_MEMO set CREDIT_ADVICE_FILE_ID = " + fileId + " WHERE MEMO_ID =" + memoId;
                stmt.executeUpdate(sqlString1);
                String sqlString2 = "insert into CRD_FILE_TRACE (ID,FILE_ID,USER_ID,FILE_TRACE_DATE,STATUS_ID ) values (CRD_FILE_TRACE_SEQ.nextval," + fileId + "," + userID + ", SysDate ,1 )";
                stmt.executeUpdate(sqlString2);


            }

            stmt.close();

        } catch (Exception ex) {
            System.out.println(sqlString);
            ex.printStackTrace();
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
