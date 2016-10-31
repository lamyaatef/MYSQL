/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.monthListFile.dao;

import com.mobinil.sds.core.system.monthListFile.model.MonthListFileModel;
import com.mobinil.sds.core.system.sa.crosstabLists.model.CrosstabListsModel;
import com.mobinil.sds.core.system.sa.monthList.model.MonthOfTheListModel;
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
    
    
    
    public static Vector getCrosstabLists(Connection con, String posCode) {
        Vector vec = new Vector();
        System.out.println("getCrosstabLists ");
        try {
            Statement stat = con.createStatement();
            stat.setFetchSize(0);
            String strSql = "SELECT gen_dcm_month_list_detail.dcm_code,\n" +
"  gen_dcm.dcm_name ,\n" +
"  gen_dcm_month_list.list_name,\n" +
"  gen_dcm_month_list.month,\n" +
"  gen_dcm_month_list.year,\n" +
"  dcm_pos_detail.pos_address,\n" +
"  dcm_pos_detail.pos_area_id,\n" +
"  dcm_region.region_name as area_name,\n" +
"  vw_supervisor_assignment.region_name as supervisor_region_name,\n" +
"  vw_supervisor_assignment.gov_region_name as supervisor_govern_name,\n" +
"  vw_supervisor_assignment.city_region_name as supervisor_city_name,\n" +
"  vw_supervisor_assignment.city_region_name as supervisor_district_name,\n" +
"  --vw_sales_rep_assignment.district_region_name as salesrep_district_name,\n" +
"  vw_sales_rep_assignment.salesrep_name as Salesrep_Name,\n" +
"  vw_supervisor_assignment.supervisor_name as Supervisor_Name,\n" +
"  vw_teamleader_assignment.teamleader_name as Teamleader_Name\n" +
"FROM \n" +
"  dcm_region,\n" +
"  dcm_pos_detail,\n" +
"  vw_supervisor_assignment,\n" +
"  vw_sales_rep_assignment,\n" +
"  vw_teamleader_assignment,\n" +
"  gen_dcm,\n" +
"  gen_dcm_month_list,\n" +
"  gen_dcm_month_list_detail\n" +
"WHERE gen_dcm.dcm_code                 = gen_dcm_month_list_detail.dcm_code\n" +
"AND gen_dcm.dcm_code                   = dcm_pos_detail.pos_code\n" +
"AND gen_dcm_month_list.history_file_id   = gen_dcm_month_list_detail.history_file_id\n" +
"AND dcm_pos_detail.pos_area_id                   = dcm_region.region_id\n" +
"AND gen_dcm_month_list_detail.dcm_code ='"+posCode+"'\n" +
"AND dcm_region.parent_REGION_ID  = vw_supervisor_assignment.DISTRICT_REGION_ID\n" +
"AND vw_supervisor_assignment.DISTRICT_REGION_ID = vw_sales_rep_assignment.SALESREP_DISTRICT_ID(+)\n" +
"AND vw_sales_rep_assignment.SALESREP_DISTRICT_ID = vw_teamleader_assignment.DISTRICT_REGION_ID(+) \n" +
"order by list_name";
                    
            System.out.println("getCrosstabLists query "+ strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                vec.add(new CrosstabListsModel(res));
                }
            res.close();
            stat.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vec;
    }
    
    public static Vector getallHistoryFiles(Connection con, String fileId) {
        Vector vec = new Vector();
        System.out.println("getallHistoryFiles ");
        try {
            Statement stat = con.createStatement();
            stat.setFetchSize(0);
            String strSql = "SELECT \n" +
"gen_dcm_month_list.history_file_id,\n" +
"gen_dcm_month_list.list_name , \n "+
"gen_dcm_month_list_detail.dcm_code,gen_dcm_month_list.user_id,\n" +
"gen_dcm_month_list_detail.dcm_id,\n" +
"gen_person.person_full_name,\n" +
"dcm_month_list_file_status.status_name,\n" +
"gen_dcm_month_list.TIMESTAMP,\n" +
"gen_dcm_month_list.month,\n" +
"gen_dcm_month_list.year,\n" +
"gen_channel.channel_name,\n" +
"gen_dcm_payment_level.dcm_payment_level_name,\n" +
"\n" +
"dcm_pos_detail.pos_area_id,\n" +
"  dcm_region.region_name as area_name,\n" +
"  vw_supervisor_assignment.region_name,\n" +
"  vw_supervisor_assignment.gov_region_name as govern_name,\n" +
"  vw_supervisor_assignment.city_region_name as city_name,\n" +
"  vw_sales_rep_assignment.district_region_name as district_name,\n" +
"vw_sales_rep_assignment.salesrep_name as Salesrep_Name,\n" +
"  vw_supervisor_assignment.supervisor_name as Supervisor_Name,\n" +
"  vw_teamleader_assignment.teamleader_name as Teamleader_Name\n" +
"\n" +
"FROM \n" +
"gen_dcm_payment_level,\n" +
"gen_person ,\n" +
"gen_dcm_month_list_detail,\n" +
"gen_dcm_month_list,\n" +
"gen_channel,\n" +
"dcm_month_list_file_status,\n" +
"\n" +
"\n" +
"dcm_pos_detail,\n" +
"dcm_region,\n" +
"vw_supervisor_assignment,\n" +
"vw_teamleader_assignment,\n" +
"vw_sales_rep_assignment\n" +
"\n" +
"WHERE gen_dcm_month_list_detail.history_file_id    = gen_dcm_month_list.history_file_id\n" +
"AND gen_dcm_month_list_detail.channel_id           = gen_channel.channel_id\n" +
"AND gen_dcm_month_list_detail.dcm_payment_level_id = gen_dcm_payment_level.dcm_payment_level_id\n" +
"AND gen_dcm_month_list.status_id            =dcm_month_list_file_status.status_id\n" +
"AND gen_dcm_month_list.user_id              = gen_person.person_id \n" +
"\n" +
"AND gen_dcm_month_list_detail.dcm_code = dcm_pos_detail.pos_code\n" +
"AND dcm_pos_detail.pos_area_id                   = dcm_region.region_id\n" +
"AND dcm_region.parent_REGION_ID                  = vw_sales_rep_assignment.SALESREP_DISTRICT_ID  \n" +
"AND vw_sales_rep_assignment.SALESREP_DISTRICT_ID = vw_supervisor_assignment.DISTRICT_REGION_ID\n" +
"AND vw_sales_rep_assignment.SALESREP_DISTRICT_ID = vw_teamleader_assignment.DISTRICT_REGION_ID\n" +
"\n" +
"AND gen_dcm_month_list.history_file_id = '"+fileId+"' \n" +
"order by gen_dcm_month_list.TIMESTAMP desc";
                    /*"SELECT gen_dcm_payment_level_history.history_file_id,\n" +
"  dcm_payment_level_hist_detail.dcm_code,gen_dcm_payment_level_history.user_id,\n" +
"dcm_payment_level_hist_detail.dcm_id,\n" +
"  gen_person.person_full_name,\n" +
"  DCM_PAY_HISTORY_FILE_STATUS.status_name,\n" +
"  gen_dcm_payment_level_history.TIMESTAMP,\n" +
"  gen_dcm_payment_level_history.month,\n" +
"  gen_dcm_payment_level_history.year,\n" +
"  gen_channel.channel_name,\n" +
"  gen_dcm_payment_level.dcm_payment_level_name\n "
                    + " dcm_pos_detail.pos_area_id,\n" +
"  dcm_region.region_name as area_name,\n" +
"  vw_supervisor_assignment.region_name,\n" +
"  vw_supervisor_assignment.gov_region_name as govern_name,\n" +
"  vw_supervisor_assignment.city_region_name as city_name,\n" +
"  vw_sales_rep_assignment.district_region_name as district_name,\n" +
"vw_sales_rep_assignment.salesrep_name as Salesrep_Name,\n" +
"  vw_supervisor_assignment.supervisor_name as Supervisor_Name,\n" +
"  vw_teamleader_assignment.teamleader_name as Teamleader_Name \n" +
" FROM \n" +
"  gen_dcm_payment_level,\n" +
"  gen_person ,\n" +
"  dcm_payment_level_hist_detail,\n" +
"  gen_dcm_payment_level_history,\n" +
"  gen_channel,\n" +
"  DCM_PAY_HISTORY_FILE_STATUS\n "
                    + "dcm_pos_detail,\n" +
" dcm_region,\n" +
"vw_supervisor_assignment,\n" +
"vw_teamleader_assignment,\n" +
"vw_sales_rep_assignment \n " +
"WHERE dcm_payment_level_hist_detail.history_file_id    = gen_dcm_payment_level_history.history_file_id\n" +
"AND dcm_payment_level_hist_detail.channel_id           = gen_channel.channel_id\n" +
"AND dcm_payment_level_hist_detail.dcm_payment_level_id = gen_dcm_payment_level.dcm_payment_level_id\n" +
"AND gen_dcm_payment_level_history.status_id            =DCM_PAY_HISTORY_FILE_STATUS.status_id\n" +
"AND gen_dcm_payment_level_history.user_id              = gen_person.person_id "
                    + "AND dcm_payment_level_hist_detail.dcm_code = dcm_pos_detail.pos_code\n" +
"AND dcm_pos_detail.pos_area_id                   = dcm_region.region_id\n" +
"AND dcm_region.parent_REGION_ID                  = vw_sales_rep_assignment.SALESREP_DISTRICT_ID  \n" +
"AND vw_sales_rep_assignment.SALESREP_DISTRICT_ID = vw_supervisor_assignment.DISTRICT_REGION_ID\n" +
"AND vw_sales_rep_assignment.SALESREP_DISTRICT_ID = vw_teamleader_assignment.DISTRICT_REGION_ID"
                    + "AND gen_dcm_payment_level_history.history_file_id = '"+fileId+"' order by gen_dcm_payment_level_history.TIMESTAMP desc";*/
            System.out.println("get history query "+ strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                System.out.println("in result month hist ");
                vec.add(new MonthListFileModel(res));
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
        System.out.println("getallFiles Month List");
        try {
            Statement stat = con.createStatement();
            stat.setFetchSize(0);
            
           
            
            String strSql = "SELECT gen_dcm_month_list.history_file_id,\n" +
"  gen_dcm_month_list.user_id,\n" +
"  gen_dcm_month_list.timestamp,\n" +
"  gen_dcm_month_list.month,\n" +
"  gen_dcm_month_list.year,gen_dcm_month_list.LIST_NAME,\n" +
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
                //vec.add(new MonthListFileModel(res));
                vec.add(new MonthOfTheListModel(res));
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
