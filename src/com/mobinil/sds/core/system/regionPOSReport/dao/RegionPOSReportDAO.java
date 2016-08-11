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
    
    private static final int ROWNUM = 10000;
    public static Vector getregionPOSData(Connection con, String regionName, String regionLevel) {
        
        Vector vec = new Vector();
        String supervisorName="";
        String teamleaderName="";
                
        System.out.println("getregionPOSData ");
        
              
        try {
            Statement stat = con.createStatement();
            Statement stat2 = con.createStatement();
            //stat.setFetchSize(0);
            String strSql = "SELECT gen_dcm.channel_id,\n" +
"   dcm_pos_detail.pos_code,\n" +
"  dcm_pos_detail.pos_name,\n" +
"  dcm_pos_detail.pos_arabic_name,\n" +
"  dcm_pos_owner.pos_owner_name,\n" +
"  dcm_pos_owner.pos_owner_id_number,\n" +
"  dcm_pos_detail.DISTRICT_CODE,\n" +
"  dcm_pos_owner.pos_owner_id_type_id,\n" +
"  /*dcm_pos_detail.region_id,\n" +
"  dcm_pos_detail.POS_GOVERNRATE,\n" +
"  dcm_pos_detail.POS_CITY_ID,\n" +
"  dcm_pos_detail.POS_DISTRICT_ID,\n" +
"  dcm_pos_detail.POS_AREA_ID,*/\n" +
"  vw_supervisor_assignment.region_id as supervisor_region_id,\n" +
"  vw_supervisor_assignment.gov_region_id as supervisor_govern_id,\n" +
"  vw_supervisor_assignment.city_region_id as supervisor_city_id,\n" +
"  vw_sales_rep_assignment.SALESREP_DISTRICT_ID as salesrep_district_id,\n" +
"  vw_sales_rep_assignment.area_region_id as salesrep_area_id,\n" +
"  dcm_pos_detail.pos_address,\n" +
"  dcm_pos_detail.POS_DOC_NUM,\n" +
"  pos_documents.PosDocuments,\n" +
"  dcm_pos_status_type.pos_status_type_name,\n" +
"  dcm_pos_owner_phone.pos_owner_phone_number,\n" +
"  dcm_pos_detail.DCM_LEVEL_ID,\n" +
"  pos_documents.StkDialNo,\n" +
"  pos_documents.stkActvDt,\n" +
"  pos_documents.IqrarRcvDt,\n" +
"  \n" +
"  scm_iqrar_receving_status.name as iqrar_rcv_status,\n" +
"  scm_stk_status.name as stk_status,\n" +
"  \n" +
"  \n" +
"  \n" +
"  scm_verified_status.name as verified_status,\n" +
"  \n" +
"  dcm_pos_detail.survey_date AS Entry_DATE,\n" +
"  \n" +
"  \n" +
"  pos_documents.STKVRFCAT_VANTIFCASEIDNO,\n" +
"  \n" +
"  gen_dcm_payment_level.DCM_PAYMENT_LEVEL_NAME,\n" +
"  \n" +
"  dcm_pos_detail.POS_ARABIC_ADDRESS,\n" +
"  dcm_pos_detail.DOC_LOCATION,\n" +
"  dcm_pos_detail.SURVEY_ID,\n" +
"  \n" +
"  \n" +
"  \n" +
"  \n" +
"  dcm_pos_detail.IS_LEVEL_ONE as L1,\n" +
"  dcm_pos_detail.IS_EXCLUSIVE as EX,\n" +
"  dcm_pos_detail.IS_QUALITY_CLUB as QC ,\n" +
"  dcm_pos_detail.HAS_SIGN as Sign,\n" +
"  \n" +
"  vw_supervisor_assignment.region_name as supervisor_region_name,\n" +
"  vw_supervisor_assignment.gov_region_name as supervisor_govern_name,\n" +
"  vw_supervisor_assignment.city_region_name as supervisor_city_name,\n" +
"  vw_sales_rep_assignment.district_region_name as salesrep_district_name,\n" +
"  vw_sales_rep_assignment.area_region_name as salesrep_area_name,\n" +
"  --dcm_region.region_name,\n" +
"  vw_sales_rep_assignment.salesrep_name as Salesrep_Name,\n" +
"  vw_supervisor_assignment.supervisor_name as Supervisor_Name,\n" +
"  vw_teamleader_assignment.teamleader_name as Teamleader_Name,\n" +
"  vw_sales_rep_assignment.user_detail_id as rep_id\n" +
"  \n" +
"FROM dcm_pos_detail,\n" +
"  gen_dcm,\n" +
"  pos_documents,\n" +
"  dcm_pos_owner,\n" +
"  gen_dcm_payment_level,\n" +
"  dcm_pos_owner_phone,\n" +
"  scm_stk_owner,\n" +
"  CAM_PAYMENT_SCM_STATUS,\n" +
"  CAM_PAYMENT_cam_state,\n" +
"  scm_iqrar_receving_status,\n" +
"  scm_stk_status,\n" +
"  scm_verified_status,\n" +
"  dcm_region,\n" +
"  vw_sales_rep_assignment,\n" +
"  vw_supervisor_assignment,\n" +
"  vw_teamleader_assignment,\n" +
"  dcm_pos_status_type\n" +
"WHERE dcm_pos_detail.pos_code                    = gen_dcm.dcm_code\n" +
"AND dcm_pos_detail.pos_code                      = pos_documents.code(+)\n" +
"AND dcm_pos_detail.pos_detail_id                 = dcm_pos_owner.pos_detail_id(+)\n" +
"AND gen_dcm.DCM_PAYMENT_LEVEL_ID                 = gen_dcm_payment_level.DCM_PAYMENT_LEVEL_ID\n" +
"AND dcm_pos_owner.pos_owner_id                   = dcm_pos_owner_phone.pos_owner_id (+)\n" +
"AND dcm_pos_detail.pos_area_id                   = dcm_region.region_id (+)\n" +
"AND gen_dcm.DCM_ID                               = scm_stk_owner.DCM_ID (+)\n" +
"AND gen_dcm.DCM_ID                               = CAM_PAYMENT_SCM_STATUS.scm_id (+)\n" +
"AND dcm_pos_detail.POS_STATUS_TYPE_ID            = dcm_pos_status_type.POS_STATUS_TYPE_ID (+)\n" +
"AND CAM_PAYMENT_SCM_STATUS.payment_cam_state_id  = CAM_PAYMENT_cam_state.id\n" +
"AND scm_stk_owner.IQRAR_RECEVING_STATUS_ID       = scm_iqrar_receving_status.iqrar_receving_status_id (+)\n" +
"AND scm_stk_owner.STK_STATUS_ID                  = scm_stk_status.stk_status_id (+)\n" +
"AND scm_stk_owner.DCM_VERIFIED_STATUS_ID         = scm_verified_status.dcm_verified_status_id (+)\n" +
"AND gen_dcm.channel_id                           = 1\n" +
"AND dcm_pos_detail.flage                        IS NULL\n" +
"AND dcm_region.REGION_ID                  = vw_sales_rep_assignment.SALESREP_DISTRICT_ID  \n" +
"AND vw_sales_rep_assignment.region_level_type_id = 4\n" +
"AND vw_sales_rep_assignment.SALESREP_DISTRICT_ID = vw_supervisor_assignment.DISTRICT_REGION_ID(+)\n" +
"AND vw_sales_rep_assignment.SALESREP_DISTRICT_ID = vw_teamleader_assignment.DISTRICT_REGION_ID(+)and LOWER(vw_supervisor_assignment.region_name) like LOWER('%"+regionName+"%')";
            
            System.out.println("sql for region pos data with salesrep name : "+ strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
               
                /*String supervisorSql ="SELECT dcm_user_detail.user_full_name as supervisor_name " +
                                        "FROM scm_rep_supervisors , " +
                                        "  dcm_user_detail " +
                                        "WHERE scm_rep_supervisors.rep_id='"+res.getString("rep_id")+"' " +
                                        "AND scm_rep_supervisors.sup_id  = dcm_user_detail.user_id";
                String teamleaderSql ="SELECT dcm_user_detail.user_full_name as teamleader_name " +
                                        "FROM scm_rep_teamleaders , " +
                                        "  dcm_user_detail " +
                                        "WHERE scm_rep_teamleaders.rep_id='"+res.getString("rep_id")+"' " +
                                        "AND scm_rep_teamleaders.teamlead_id  = dcm_user_detail.user_id";
                
                ResultSet rs1 = stat2.executeQuery(supervisorSql);
                if(rs1.next())
                    supervisorName = rs1.getString("supervisor_name");
                ResultSet rs2 = stat2.executeQuery(teamleaderSql);
                if(rs2.next())
                    teamleaderName = rs2.getString("teamleader_name");*/
               
                vec.add(new RegionPOSReportModel(res,supervisorName,teamleaderName));
                }
            res.close();
            stat2.close();
            stat.close();
           // con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vec;
    }
    
}
