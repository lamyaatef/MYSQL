/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.paymentHistory.dao;

import com.mobinil.sds.core.system.paymentFileHistory.model.PaymentFileModel;
import com.mobinil.sds.core.system.paymentHistory.model.PaymentHistoryFileModel;
import com.mobinil.sds.core.utilities.DBUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author sand
 */
public class PaymentHistoryFileDAO {
    
    public static Vector getallHistoryFiles(Connection con, String fileId) {
        Vector vec = new Vector();
        System.out.println("getallHistoryFiles ");
        try {
            Statement stat = con.createStatement();
            stat.setFetchSize(0);
            String strSql = "SELECT gen_dcm_payment_level_history.history_file_id,\n" +
"  dcm_payment_level_hist_detail.dcm_code,gen_dcm_payment_level_history.user_id,\n" +
"dcm_payment_level_hist_detail.dcm_id,\n" +
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
"AND gen_dcm_payment_level_history.user_id              = gen_person.person_id AND gen_dcm_payment_level_history.history_file_id = '"+fileId+"' order by gen_dcm_payment_level_history.TIMESTAMP desc";
            System.out.println("get history query "+ strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                System.out.println("in result hist ");
                vec.add(new PaymentHistoryFileModel(res));
                }
            res.close();
            stat.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vec;
    }
    
    
    public static Vector getallFiles(Connection con, String userId) {
        Vector vec = new Vector();
        System.out.println("getallFiles ");
        try {
            Statement stat = con.createStatement();
            stat.setFetchSize(0);
            String strSql = "SELECT gen_dcm_payment_level_history.history_file_id,\n" +
"  gen_dcm_payment_level_history.user_id,\n" +
"  gen_dcm_payment_level_history.timestamp,\n" +
"  gen_dcm_payment_level_history.month,\n" +
"  gen_dcm_payment_level_history.year,gen_dcm_payment_level_history.status_id,\n" +
"  gen_person.person_full_name,\n" +
"  DCM_PAY_HISTORY_FILE_STATUS.status_name\n" +
"FROM gen_dcm_payment_level_history,\n" +
"  gen_person,\n" +
"  DCM_PAY_HISTORY_FILE_STATUS\n" +
"WHERE gen_dcm_payment_level_history.user_id = gen_person.person_id\n" +
"AND gen_dcm_payment_level_history.status_id = DCM_PAY_HISTORY_FILE_STATUS.status_id order by gen_dcm_payment_level_history.TIMESTAMP desc";
            System.out.println("get history query "+ strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                System.out.println("in result files");
                vec.add(new PaymentFileModel(res));
                }
            res.close();
            stat.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vec;
    }
    
       public static void delHistoryFileById(Connection con, String file_id) {
        DBUtil.executeSQL("delete from gen_dcm_payment_level_history where history_file_id='" + file_id + "'", con);
         DBUtil.executeSQL("delete from dcm_payment_level_hist_detail where history_file_id='" + file_id + "'", con);
       
     
    }

    
}
