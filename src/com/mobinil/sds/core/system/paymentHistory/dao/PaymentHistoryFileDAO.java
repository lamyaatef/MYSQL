/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.paymentHistory.dao;

import com.mobinil.sds.core.system.paymentHistory.model.PaymentHistoryFileModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author sand
 */
public class PaymentHistoryFileDAO {
    
    public static Vector getallHistoryFiles(Connection con, String userId) {
        Vector vec = new Vector();
        String personName="";
        System.out.println("getallHistoryFiles ");
        try {
            Statement stat = con.createStatement();
            //String strSql = "select gf.GEN_DCM_NOMAD_FILE_ID, gf.USER_ID, gf.FILE_CREATION_DATE, gf.FILE_UPLOAD_DATE, gf.TOTAL_NUMBER_OF_RECORDS, gl.NOMAD_LABEL_NAME from  GEN_DCM_NOMAD_FILE gf, GEN_DCM_NOMAD_LABEL gl where gf.user_id = '"+userId+"' and gf.label_id = gl.NOMAD_LABEL_ID and gf.status <> 'Deleted'";
            System.out.println("^^^^^^^^^^");
            String strSql = "SELECT gen_dcm_payment_level_history.history_file_id,\n" +
"  dcm_payment_level_hist_detail.dcm_code,\n" +
"  gen_person.person_full_name,\n" +
"  DCM_PAY_HISTORY_FILE_STATUS.status_name,\n" +
"  gen_dcm_payment_level_history.TIMESTAMP,\n" +
"  gen_dcm_payment_level_history.month,\n" +
"  gen_dcm_payment_level_history.year,\n" +
"  gen_channel.channel_name,\n" +
"  gen_dcm_payment_level.dcm_payment_level_name\n" +
"FROM \n" +
"  gen_dcm_payment_level,\n" +
"  gen_person ,\n" +
"  dcm_payment_level_hist_detail,\n" +
"  gen_dcm_payment_level_history,\n" +
"  gen_channel,\n" +
"  DCM_PAY_HISTORY_FILE_STATUS\n" +
"WHERE dcm_payment_level_hist_detail.history_file_id    = gen_dcm_payment_level_history.history_file_id\n" +
"AND dcm_payment_level_hist_detail.channel_id           = gen_channel.channel_id\n" +
"AND dcm_payment_level_hist_detail.dcm_payment_level_id = gen_dcm_payment_level.dcm_payment_level_id\n" +
"AND gen_dcm_payment_level_history.status_id            =DCM_PAY_HISTORY_FILE_STATUS.status_id\n" +
"AND gen_dcm_payment_level_history.user_id              = gen_person.person_id order by gen_dcm_payment_level_history.TIMESTAMP desc";
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%");
            System.out.println("get history query "+ strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                System.out.println("in resultttt ");
                vec.add(new PaymentHistoryFileModel(res));
                }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vec;
    }

    
}
