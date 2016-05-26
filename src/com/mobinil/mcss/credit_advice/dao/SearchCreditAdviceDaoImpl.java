/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.dao;

import com.mobinil.mcss.credit_advice.model.*;
import com.mobinil.sds.core.utilities.Utility;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Brain
 */
public class SearchCreditAdviceDaoImpl implements SearchCreditAdviceDao {

    Connection newConnection = null;

    @Override
    public List getMemos(Long memoID, String memoName, Date dateFrom, Date dateTo, String scmCode, String scmName, String creditSerial, String amount, int creditStatus, Long creditBatchID) {
        List<CAMemo> camMemoList = new ArrayList<CAMemo>();
        List<CAFile> genertaedCredits = new ArrayList<CAFile>();

        System.out.println("SCM NAAAAAAAAAAAAME:" + scmName);
        try {
            newConnection = Utility.getConnection();
            Statement stat = newConnection.createStatement();


            StringBuilder str = new StringBuilder("SELECT CAM_MEMO.memo_id,CAM_PAYMENT.memo_id,CRD_FILE_TRACE.FILE_TRACE_DATE,CRD_FILE_TRACE.STATUS_ID,CRD_ADVICE_FILE.FILE_ID,CAM_MEMO.memo_name,CAM_MEMO.CREATION_DATE");
            str.append(" FROM CAM_MEMO ,CAM_PAYMENT,CRD_FILE_TRACE,CRD_ADVICE_FILE,gen_dcm ");
            str.append(" WHERE CAM_PAYMENT.memo_id=CAM_MEMO.memo_id");
            str.append(" AND CRD_ADVICE_FILE.FILE_ID=CAM_MEMO.CREDIT_ADVICE_FILE_ID");
            str.append(" AND CRD_FILE_TRACE.FILE_TRACE_DATE =(SELECT max(FILE_TRACE_DATE) FROM CRD_FILE_TRACE WHERE FILE_ID=CAM_MEMO.CREDIT_ADVICE_FILE_ID)");
            str.append(" AND CRD_FILE_TRACE.STATUS_ID=2");
            str.append(" AND cam_payment.scm_id=gen_dcm.dcm_id");
            if (memoID != null && memoID != 0) {
                str.append(" AND CAM_MEMO.MEMO_ID=").append(memoID);
            }
            if (memoName != null && !memoName.trim().equals("")) {
                str.append(" AND cam_memo.MEMO_NAME='").append(memoName).append("'");
            }
            if (dateFrom != null && dateTo != null) {
                str.append(" AND CREATION_DATE BETWEEN ").append(dateFrom).append("AND").append(dateTo);
            }
            if (scmCode != null && !scmCode.trim().equals("")) {
                str.append(" AND GEN_DCM.DCM_CODE='").append(scmCode).append("'");
            }
            if (scmName != null && !scmName.trim().equals("")) {
                str.append(" AND GEN_DCM.DCM_NAME='").append(scmName).append("'");
            }
            if (creditSerial != null && !creditSerial.trim().equals("")) {
                str.append(" AND CRD_CREDIT_DETAILS.CREDIT_SERIAL='").append(creditSerial).append("'");
            }
            if (amount != null && !amount.trim().equals("")) {
                str.append(" AND CRD_CREDIT_DETAILS.AMOUNT=").append(amount);
            }
            if (creditStatus != 0) {
                str.append(" AND CRD_STATUS_CREDIT.STATUS_ID=").append(creditStatus).append("");
            }
            if (creditBatchID != null && creditBatchID != 0) {
                str.append(" AND CRD_ADVICE_FILE.FILE_ID=").append(creditBatchID);
            }
            str.append(" GROUP BY CAM_MEMO.memo_id,CAM_PAYMENT.memo_id");
            str.append(" ,CRD_FILE_TRACE.FILE_TRACE_DATE,CRD_FILE_TRACE.STATUS_ID,CRD_ADVICE_FILE.FILE_ID,CAM_MEMO.memo_name,CAM_MEMO.CREATION_DATE");



            System.out.println("SQL:" + str);

            ResultSet res = stat.executeQuery(str.toString());
            while (res.next()) {
                CAMemo caMemo = new CAMemo();
                caMemo.setMemoId(res.getLong("MEMO_ID"));
                caMemo.setMemoName(res.getString("MEMO_NAME"));
                caMemo.setStartDate(res.getDate("CREATION_DATE"));
                caMemo.setCafileId(res.getLong("FILE_ID"));
                camMemoList.add(caMemo);

            }

            HashMap<Long, CAFile> recordsMap = new HashMap<Long, CAFile>();

            for (CAMemo cAMemo : camMemoList) {


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

                genertaedCredits = new ArrayList<CAFile>(recordsMap.values());

            }




        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            Utility.closeConnection(newConnection);
        }

        return genertaedCredits;

    }

    @Override
    public List<StatusFile> fillStatus() {
        List<StatusFile> statusFileList = new ArrayList<StatusFile>();

        try {
            newConnection = Utility.getConnection();
            Statement stat = newConnection.createStatement();


            String str = "SELECT * FROM CRD_STATUS_CREDIT";

            ResultSet res = stat.executeQuery(str.toString());
            while (res.next()) {
                StatusFile statusFile = new StatusFile();
                statusFile.setStatusId(res.getLong("status_id"));
                statusFile.setStatusName(res.getString("status_name"));
                statusFileList.add(statusFile);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            Utility.closeConnection(newConnection);
        }

        return statusFileList;
    }

    @Override
    public List getCreditAdvices(Long memoId, Long fileId) {

        List<CreditDetails> CreditDetails = new ArrayList<CreditDetails>();

        try {
            newConnection = Utility.getConnection();
            Statement stat = newConnection.createStatement();




            StringBuilder str = new StringBuilder("SELECT CAM_PAYMENT.SCM_ID,CAM_PAYMENT.credit_advice_id,");
            str.append("SUM(CRD_CREDIT_DETAILS.AMOUNT) AS AMOUNT,CRD_CREDIT_DETAILS.CREDIT_SERIAL,CRD_FILE_TRACE.FILE_TRACE_DATE ");
            str.append(",GEN_DCM.DCM_NAME,GEN_DCM.DCM_CODE,CRD_STATUS_CREDIT.STATUS_NAME");
            str.append(",NVL (vw_dcm_grouping.dcm_code, gen_dcm.dcm_code) AS parent_code ,vw_dcm_grouping.FRANCHISE_GROUP_NAME");
            str.append(" FROM CAM_PAYMENT,CRD_CREDIT_DETAILS ,CRD_FILE_TRACE,GEN_DCM,CRD_STATUS_CREDIT,vw_dcm_grouping ");
            str.append(" WHERE CAM_PAYMENT.memo_id=");
            str.append(memoId);
            str.append(" AND CRD_STATUS_CREDIT.STATUS_ID=CRD_CREDIT_DETAILS.STATUS_ID");
            str.append(" AND gen_dcm.dcm_code = franchise_code(+)");
            str.append(" AND GEN_DCM.DCM_ID=CAM_PAYMENT.SCM_ID AND CAM_PAYMENT.credit_advice_id=CRD_CREDIT_DETAILS.credit_advice_id AND CRD_FILE_TRACE.FILE_TRACE_DATE =(SELECT max(FILE_TRACE_DATE) FROM CRD_FILE_TRACE WHERE FILE_ID=");
            str.append(fileId);
            str.append(" AND STATUS_ID=2) GROUP BY CAM_PAYMENT.SCM_ID ,CAM_PAYMENT.credit_advice_id ,CRD_CREDIT_DETAILS.CREDIT_SERIAL,CRD_FILE_TRACE.FILE_TRACE_DATE,GEN_DCM.DCM_NAME,GEN_DCM.DCM_CODE ");
            str.append(",CRD_STATUS_CREDIT.STATUS_NAME ");
            str.append(",NVL (vw_dcm_grouping.dcm_code, gen_dcm.dcm_code)");
            str.append(",vw_dcm_grouping.FRANCHISE_GROUP_NAME");

            System.out.println("SQL:" + str);

            ResultSet res = stat.executeQuery(str.toString());



            while (res.next()) {

                CreditDetails CreditDetail = new CreditDetails();


                CreditDetail.setScmId(res.getLong("SCM_ID"));
                CreditDetail.setCaId(res.getLong("credit_advice_id"));
                CreditDetail.setCreditAmount(res.getLong("AMOUNT"));
                CreditDetail.setCreditSerial(res.getString("CREDIT_SERIAL"));
                CreditDetail.setFileTraceDate(res.getDate("FILE_TRACE_DATE"));
                CreditDetail.setDcmCode(res.getString("PARENT_CODE"));
                CreditDetail.setScmName(res.getString("FRANCHISE_GROUP_NAME"));
                CreditDetail.setStatusName(res.getString("STATUS_NAME"));

                CreditDetails.add(CreditDetail);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            Utility.closeConnection(newConnection);
        }

        return CreditDetails;

    }

    @Override
    public void updateCAFilePrinting(Long fileId) {
        String sqlString = "";

        try {
            newConnection = Utility.getConnection();
            Statement stmt = newConnection.createStatement();

            StringBuilder str = new StringBuilder("SELECT * FROM CRD_ADVICE_FILE where FILE_ID= ");

            str.append(fileId);

            System.out.println(str);

            Long print = null;
            ResultSet res = stmt.executeQuery(str.toString());
            while (res.next()) {

                print = res.getLong("PRINTING");
            }

            Long printing = print + 1;
            System.out.println("Printing:" + printing);

            sqlString = "update CRD_ADVICE_FILE set PRINTING = " + printing + " WHERE FILE_ID =" + fileId;

            stmt.executeUpdate(sqlString);




            stmt.close();

        } catch (Exception ex) {
            System.out.println(sqlString);
            ex.printStackTrace();

        } finally {

            Utility.closeConnection(newConnection);
        }
    }

    @Override
    public void updateCAPrinting(List<Long> caIdList) {
        String sqlString = "";

        try {
            newConnection = Utility.getConnection();
            Statement stmt = newConnection.createStatement();

            for (Long caId : caIdList) {


                StringBuilder str = new StringBuilder("SELECT * FROM CRD_CREDIT_DETAILS where CREDIT_ADVICE_ID= ");

                str.append(caId);

                ResultSet res = stmt.executeQuery(str.toString());

                Long printing;
                while (res.next()) {

                    Long print = res.getLong("PRINTING");
                    printing = print + 1;
                    sqlString = "update CRD_CREDIT_DETAILS set PRINTING = " + printing + " WHERE CREDIT_ADVICE_ID =" + caId;
                    System.out.println(sqlString);    
                    stmt.executeUpdate(sqlString);

                }


            }
            stmt.close();


        } catch (Exception ex) {
            System.out.println(sqlString);
            ex.printStackTrace();
        } finally {

            Utility.closeConnection(newConnection);
        }
    }
}
