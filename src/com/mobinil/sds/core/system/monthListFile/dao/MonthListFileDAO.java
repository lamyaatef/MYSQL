/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.monthListFile.dao;

import com.mobinil.sds.core.system.monthListFile.model.MonthListFileModel;
import com.mobinil.sds.core.utilities.DBUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author sand
 */
public class MonthListFileDAO {
    
    public static Vector getallFiles(Connection con, String userId) {
        Vector vec = new Vector();
        System.out.println("getallFiles ");
        try {
            Statement stat = con.createStatement();
            stat.setFetchSize(0);
            String strSql = "SELECT gen_dcm_month_list.history_file_id,\n" +
"  gen_dcm_month_list.user_id,\n" +
"  gen_dcm_month_list.timestamp,\n" +
"  gen_dcm_month_list.month,\n" +
"  gen_dcm_month_list.year,gen_dcm_month_list.LIST_NAME,gen_dcm_month_list.status_id,\n" +
"  gen_person.person_full_name,\n" +
"  dcm_month_list_file_status.status_name\n" +
"FROM gen_dcm_month_list,\n" +
"  gen_person,\n" +
"  dcm_month_list_file_status\n" +
"WHERE gen_dcm_month_list.user_id = gen_person.person_id\n" +
"AND gen_dcm_month_list.status_id = dcm_month_list_file_status.status_id order by gen_dcm_month_list.TIMESTAMP desc";
            System.out.println("get MONTH LIST query "+ strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                System.out.println("in result files");
                vec.add(new MonthListFileModel(res));
                }
            res.close();
            stat.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vec;
    }
    
     public static void delHistoryFileById(Connection con, String file_id) {
        DBUtil.executeSQL("delete from gen_dcm_month_list where history_file_id='" + file_id + "'", con);
         DBUtil.executeSQL("delete from gen_dcm_month_list_detail where history_file_id='" + file_id + "'", con);
       
     
    }
    
}
