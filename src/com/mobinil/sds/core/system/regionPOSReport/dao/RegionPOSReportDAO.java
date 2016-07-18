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
            stat.setFetchSize(0);
            String strSql = "SELECT gen_dcm.channel_id,\n" +
"  dcm_pos_detail.pos_code,\n" +
"  dcm_pos_detail.pos_name,\n" +
"  dcm_pos_detail.pos_arabic_name,\n" +
"  dcm_pos_owner.pos_owner_name,\n" +
"  dcm_pos_owner.pos_owner_id_number,\n" +
"  dcm_pos_detail.DISTRICT_CODE,\n" +
"  dcm_pos_owner.pos_owner_id_type_id,\n" +
"  dcm_pos_detail.region_id,\n" +
"  dcm_pos_detail.POS_GOVERNRATE,\n" +
"  dcm_pos_detail.POS_CITY_ID,\n" +
"  dcm_pos_detail.POS_DISTRICT_ID,\n" +
"  dcm_pos_detail.POS_AREA_ID,\n" +
"  dcm_pos_detail.pos_address,\n" +
"  dcm_pos_detail.POS_DOC_NUM,\n" +
"  pos_documents.PosDocuments,\n" +
"  dcm_pos_detail.POS_STATUS_TYPE_ID,\n" +
"  dcm_pos_owner_phone.pos_owner_phone_number,\n" +
"  dcm_pos_detail.DCM_LEVEL_ID,\n" +
"  pos_documents.StkDialNo,\n" +
"  pos_documents.stkActvDt,\n" +
"  pos_documents.IqrarRcvDt,\n" +
"  gen_dcm_payment_level.DCM_PAYMENT_LEVEL_NAME,\n" +
"  dcm_pos_detail.POS_ARABIC_ADDRESS,\n" +
"  dcm_pos_detail.DOC_LOCATION,\n" +
"  dcm_pos_detail.SURVEY_ID,\n" +
"  dcm_pos_owner_phone.POS_OWNER_PHONE_NUMBER,\n" +
"  dcm_branch_pos.BRANCH_ID,\n" +
"  dcm_pos_detail.IS_LEVEL_ONE,\n" +
"  dcm_pos_detail.IS_EXCLUSIVE,\n" +
"  dcm_pos_detail.IS_QUALITY_CLUB,\n" +
"  dcm_pos_detail.HAS_SIGN,\n" +
"  dcm_region.region_name,\n" +
"  dcm_user_detail.user_full_name as sales_rep_name,\n" +
"  dcm_user.dcm_user_id as rep_id\n" +
"FROM dcm_pos_detail,\n" +
"  gen_dcm,\n" +
"  dcm_pos_owner,\n" +
"  pos_documents,\n" +
"  dcm_pos_owner_phone,\n" +
"  gen_dcm_payment_level,\n" +
"  dcm_branch_pos,\n" +
"  dcm_region,\n" +
"  dcm_user,\n" +
"  dcm_user_detail\n" +
"WHERE dcm_pos_owner.pos_detail_id              = dcm_pos_detail.pos_detail_id\n" +
"AND gen_dcm_payment_level.DCM_PAYMENT_LEVEL_ID = gen_dcm.DCM_PAYMENT_LEVEL_ID\n" +
"AND dcm_branch_pos.pos_ID                      = dcm_pos_detail.pos_ID\n" +
"AND dcm_pos_detail.region_id= dcm_region.region_id\n" +
"AND dcm_user_detail.region_id= dcm_region.region_id\n" +
"AND region_level_type_id = '"+regionLevel+"' \n" +
"AND dcm_region.region_name='"+regionName+"'\n" +
"AND dcm_user.user_level_type_id = 3";
            System.out.println("sql for region pos data with salesrep name : "+ strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                String supervisorSql ="SELECT dcm_user_detail.user_full_name as supervisor_name\n" +
                                        "FROM scm_rep_supervisors ,\n" +
                                        "  dcm_user_detail\n" +
                                        "WHERE scm_rep_supervisors.rep_id='"+res.getString("rep_id")+"'\n" +
                                        "AND scm_rep_supervisors.sup_id  = dcm_user_detail.user_id";
                System.out.println("sql for supervisor name for sales rep id "+res.getString("rep_id")+" "+supervisorSql);
                String teamleaderSql ="SELECT dcm_user_detail.user_full_name as teamleader_name\n" +
                                        "FROM scm_rep_teamleaders ,\n" +
                                        "  dcm_user_detail\n" +
                                        "WHERE scm_rep_teamleaders.rep_id='"+res.getString("rep_id")+"'\n" +
                                        "AND scm_rep_teamleaders.teamlead_id  = dcm_user_detail.user_id";
                System.out.println("sql for teamleader name for sales rep id "+res.getString("rep_id")+" "+teamleaderSql);
                
                ResultSet rs1 = stat.executeQuery(supervisorSql);
                if(rs1.next())
                    supervisorName = rs1.getString("supervisor_name");
                ResultSet rs2 = stat.executeQuery(teamleaderSql);
                if(rs2.next())
                    teamleaderName = rs2.getString("teamleader_name");
                vec.add(new RegionPOSReportModel(res,supervisorName,teamleaderName));
                }
            res.close();
            stat.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vec;
    }
    
}
