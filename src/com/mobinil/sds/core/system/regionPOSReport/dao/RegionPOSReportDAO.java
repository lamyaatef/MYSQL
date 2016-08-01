/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.regionPOSReport.dao;

import com.mobinil.sds.core.system.paymentHistory.model.PaymentHistoryFileModel;
import com.mobinil.sds.core.system.regionReport.model.RegionPOSReportModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author sand
 */
public class RegionPOSReportDAO {
    public static Vector getregionPOSData(Connection con, String regionName, String regionLevel) {
        
        Vector vec = new Vector();
        String supervisorName="";
        String teamleaderName="";
                
        System.out.println("getregionPOSData ");
        
        if(regionLevel.compareTo("1")==0 && regionName.compareToIgnoreCase("Cairo")==0)
                  regionName = "Greater Cairo";
              
        try {
            Statement stat = con.createStatement();
            Statement stat2 = con.createStatement();
            //stat.setFetchSize(0);
            String strSql = "SELECT gen_dcm.channel_id, " +
"   dcm_pos_detail.pos_code, " +
"  dcm_pos_detail.pos_name, " +
"  dcm_pos_detail.pos_arabic_name, " +
"  dcm_pos_owner.pos_owner_name, " +
"  dcm_pos_owner.pos_owner_id_number, " +
"  dcm_pos_detail.DISTRICT_CODE, " +
"  dcm_pos_owner.pos_owner_id_type_id, " +
"  dcm_pos_detail.region_id, " +
"  dcm_pos_detail.POS_GOVERNRATE, " +
"  dcm_pos_detail.POS_CITY_ID, " +
"  dcm_pos_detail.POS_DISTRICT_ID, " +
"  dcm_pos_detail.POS_AREA_ID, " +
"  dcm_pos_detail.pos_address, " +
"  dcm_pos_detail.POS_DOC_NUM, " +
"  pos_documents.PosDocuments, " +
"  dcm_pos_detail.POS_STATUS_TYPE_ID, " +
"  dcm_pos_owner_phone.pos_owner_phone_number, " +
"  dcm_pos_detail.DCM_LEVEL_ID, " +
"  pos_documents.StkDialNo, " +
"  pos_documents.stkActvDt, " +
"  pos_documents.IqrarRcvDt, " +
"  scm_iqrar_receving_status.name as iqrar_rcv_status, " +
"  scm_stk_status.name as stk_status, " +
"  /*scm_temp_pos_pay_status.paystatus as pay_status,*/ " +
"  scm_verified_status.name as verified_status, " +
"  dcm_pos_detail.survey_date AS Entry_DATE, " +
"  /*SCM_DISTRICT_CHILDS.area_code,*/ " +
"  pos_documents.STKVRFCAT_VANTIFCASEIDNO, " +
"  gen_dcm_payment_level.DCM_PAYMENT_LEVEL_NAME, " +
"  dcm_pos_detail.POS_ARABIC_ADDRESS, " +
"  dcm_pos_detail.DOC_LOCATION, " +
"  dcm_pos_detail.SURVEY_ID, " +
"  /*dcm_branch_pos.BRANCH_ID,*/ " +
"  dcm_pos_detail.IS_LEVEL_ONE, " +
"  dcm_pos_detail.IS_EXCLUSIVE, " +
"  dcm_pos_detail.IS_QUALITY_CLUB, " +
"  dcm_pos_detail.HAS_SIGN, " +
"  dcm_region.region_name, " +
"  dcm_user_detail.user_full_name as sales_rep_name, " +
"  dcm_user.dcm_user_id as rep_id " +
"FROM dcm_pos_detail, " +
"/*pos_documents right join dcm_pos_detail on pos_documents.code = dcm_pos_detail.pos_code,*/ " +
"  gen_dcm, " +
"  scm_iqrar_receving_status, " +
"  scm_stk_status, " +
"  scm_verified_status, " +
"  /*scm_temp_pos_pay_status,*/ " +
"  scm_stk_owner, " +
"  /*SCM_DISTRICT_CHILDS,*/ " +
"  dcm_pos_owner, " +
"  pos_documents, " +
"  dcm_pos_owner_phone, " +
"  gen_dcm_payment_level, " +
"  /*dcm_branch_pos,*/ " +
"  dcm_region, " +
"  dcm_user, " +
"  dcm_user_detail " +
"WHERE dcm_pos_owner.pos_detail_id              = dcm_pos_detail.pos_detail_id " +
"AND gen_dcm_payment_level.DCM_PAYMENT_LEVEL_ID = gen_dcm.DCM_PAYMENT_LEVEL_ID " +
"/*AND dcm_branch_pos.pos_ID                      = dcm_pos_detail.pos_ID*/ " +
"/*AND dcm_pos_detail.pos_code = pos_documents.code */ " +
"AND scm_iqrar_receving_status.iqrar_receving_status_id = scm_stk_owner.IQRAR_RECEVING_STATUS_ID " +
"AND scm_stk_status.stk_status_id = scm_stk_owner.STK_STATUS_ID " +
"AND scm_verified_status.dcm_verified_status_id = scm_stk_owner.DCM_VERIFIED_STATUS_ID " +
"AND scm_stk_owner.DCM_ID = gen_dcm.DCM_ID " +
"/*AND scm_temp_pos_pay_status.poscode = dcm_pos_detail.pos_code*/ " +
"/*AND SCM_DISTRICT_CHILDS.family = dcm_region.region_id*/ " +
"/*AND dcm_pos_detail.region_id= dcm_region.region_id*/ " +
"AND dcm_user_detail.region_id= dcm_region.region_id " +
"AND dcm_region.region_level_type_id = '"+regionLevel+"' " +
"AND dcm_region.region_name='"+regionName+"' " +
"AND dcm_user.user_level_type_id = 3";
            System.out.println("sql for region pos data with salesrep name : "+ strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
               
                String supervisorSql ="SELECT dcm_user_detail.user_full_name as supervisor_name " +
                                        "FROM scm_rep_supervisors , " +
                                        "  dcm_user_detail " +
                                        "WHERE scm_rep_supervisors.rep_id='"+res.getString("rep_id")+"' " +
                                        "AND scm_rep_supervisors.sup_id  = dcm_user_detail.user_id";
                System.out.println("sql for supervisor name for sales rep id "+res.getString("rep_id")+" "+supervisorSql);
                String teamleaderSql ="SELECT dcm_user_detail.user_full_name as teamleader_name " +
                                        "FROM scm_rep_teamleaders , " +
                                        "  dcm_user_detail " +
                                        "WHERE scm_rep_teamleaders.rep_id='"+res.getString("rep_id")+"' " +
                                        "AND scm_rep_teamleaders.teamlead_id  = dcm_user_detail.user_id";
                System.out.println("sql for teamleader name for sales rep id "+res.getString("rep_id")+" "+teamleaderSql);
                
                ResultSet rs1 = stat2.executeQuery(supervisorSql);
                if(rs1.next())
                    supervisorName = rs1.getString("supervisor_name");
                ResultSet rs2 = stat2.executeQuery(teamleaderSql);
                if(rs2.next())
                    teamleaderName = rs2.getString("teamleader_name");
               
                vec.add(new RegionPOSReportModel(res,supervisorName,teamleaderName));
                }
            res.close();
            stat2.close();
            stat.close();
            con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vec;
    }
    
}
