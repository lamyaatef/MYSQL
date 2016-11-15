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
    
    //private static final int ROWNUM = 10000;
    public static Vector getRegionPOSData(Connection con, String regionName/*, String regionLevel*/) {
        
        Vector vec = new Vector();
        String supervisorName="";
        String teamleaderName="";
                
        System.out.println("getregionPOSData "+regionName);
        
              
        try {
            Statement stat = con.createStatement();
            Statement stat2 = con.createStatement();
            //stat.setFetchSize(0);
            String strSql = "SELECT vw_supervisor_assignment.region_name AS supervisor_region_name,\n" +
"  vw_supervisor_assignment.region_id        AS supervisor_region_id,\n" +
"  vw_supervisor_assignment.gov_region_name  AS supervisor_govern_name,\n" +
"  vw_supervisor_assignment.city_region_name AS supervisor_city_name,\n" +
"  supervisor_detail.user_full_name          AS supervisor_name,\n" +
"  supervisor.dcm_user_id                    AS supervisor_id,\n" +
"  supervisor.user_level_type_id             AS manag_level_type_id,\n" +
"  vw_sales_rep_assignment.salesrep_name     AS Salesrep_Name,\n" +
"  vw_sales_rep_assignment.salesrep_id,\n" +
"  vw_sales_rep_assignment.SALESREP_DISTRICT_ID,\n" +
"  vw_sales_rep_assignment.district_region_name AS district_name,\n" +
"  dcm_pos_detail.pos_code,\n" +
"  dcm_pos_detail.pos_area_id,\n" +
"  dcm_pos_detail.DISTRICT_CODE,\n" +
"  dcm_pos_detail.pos_name,\n" +
"  dcm_pos_detail.pos_arabic_name,\n" +
"  dcm_pos_detail.pos_address,\n" +
"  dcm_pos_detail.POS_DOC_NUM,\n" +
"  dcm_pos_detail.DCM_LEVEL_ID,\n" +
"  dcm_pos_detail.survey_date AS Entry_DATE,\n" +
"  dcm_pos_detail.POS_ARABIC_ADDRESS,\n" +
"  dcm_pos_detail.DOC_LOCATION,\n" +
"  dcm_pos_detail.SURVEY_ID,\n" +
"  dcm_pos_detail.IS_LEVEL_ONE    AS L1,\n" +
"  dcm_pos_detail.IS_EXCLUSIVE    AS EX,\n" +
"  dcm_pos_detail.IS_QUALITY_CLUB AS QC ,\n" +
"  dcm_pos_detail.HAS_SIGN        AS SIGN,\n" +
"  gen_dcm.channel_id,\n" +
"  dcm_pos_owner.pos_owner_name,\n" +
"  dcm_pos_owner.pos_owner_id_number,\n" +
"  dcm_pos_owner.pos_owner_id_type_id,\n" +
"  dcm_region.region_name AS area_name,\n" +
"  dcm_pos_status_type.pos_status_type_name,\n" +
"  dcm_pos_owner_phone.pos_owner_phone_number,\n" +
"  pos_documents.PosDocuments,\n" +
"  pos_documents.StkDialNo,\n" +
"  pos_documents.stkActvDt,\n" +
"  pos_documents.IqrarRcvDt,\n" +
"  pos_documents.STKVRFCAT_VANTIFCASEIDNO,\n" +
"  scm_iqrar_receving_status.name AS iqrar_rcv_status,\n" +
"  scm_stk_status.name            AS stk_status,\n" +
"  scm_verified_status.name       AS verified_status,\n" +
"  gen_dcm_payment_level.DCM_PAYMENT_LEVEL_NAME,\n" +
"  CAM_PAYMENT_cam_state.cam_status_for_payment AS payment_status\n" +
"FROM dcm_pos_detail,\n" +
"  gen_dcm,\n" +
"  pos_documents,\n" +
"  dcm_pos_owner,\n" +
"  gen_dcm_payment_level,\n" +
"  dcm_pos_owner_phone,\n" +
"  scm_stk_owner,\n" +
"  CAM_PAYMENT_SCM_STATUS,\n" +
"  CAM_PAYMENT_cam_state,\n" +
"  dcm_pos_status_type,\n" +
"  dcm_region,\n" +
"  scm_iqrar_receving_status,\n" +
"  scm_stk_status,\n" +
"  scm_verified_status,\n" +
"  vw_sales_rep_assignment,\n" +
"  vw_supervisor_assignment,\n" +
"  dcm_user,\n" +
"  dcm_user_detail,\n" +
"  dcm_user supervisor,\n" +
"  dcm_user_detail supervisor_detail\n" +
"WHERE dcm_pos_detail.pos_code                    = gen_dcm.dcm_code\n" +
"AND dcm_pos_detail.pos_code                      = pos_documents.code(+)\n" +
"AND dcm_pos_detail.pos_detail_id                 = dcm_pos_owner.pos_detail_id(+)\n" +
"AND gen_dcm.DCM_PAYMENT_LEVEL_ID                 = gen_dcm_payment_level.DCM_PAYMENT_LEVEL_ID\n" +
"AND dcm_pos_owner.pos_owner_id                   = dcm_pos_owner_phone.pos_owner_id (+)\n" +
"AND gen_dcm.DCM_ID                               = scm_stk_owner.DCM_ID (+)\n" +
"AND gen_dcm.DCM_ID                               = CAM_PAYMENT_SCM_STATUS.scm_id (+)\n" +
"AND CAM_PAYMENT_SCM_STATUS.payment_cam_state_id  = CAM_PAYMENT_cam_state.id\n" +
"AND dcm_pos_detail.POS_STATUS_TYPE_ID            = dcm_pos_status_type.POS_STATUS_TYPE_ID (+)\n" +
"AND scm_stk_owner.IQRAR_RECEVING_STATUS_ID       = scm_iqrar_receving_status.iqrar_receving_status_id (+)\n" +
"AND scm_stk_owner.STK_STATUS_ID                  = scm_stk_status.stk_status_id (+)\n" +
"AND scm_stk_owner.DCM_VERIFIED_STATUS_ID         = scm_verified_status.dcm_verified_status_id (+)\n" +
"AND gen_dcm.channel_id                           = 1\n" +
"AND dcm_pos_detail.flage                        IS NULL\n" +
"AND dcm_pos_detail.pos_area_id                   = dcm_region.region_id (+)\n" +
"AND dcm_region.parent_REGION_ID                  = vw_sales_rep_assignment.SALESREP_DISTRICT_ID\n" +
"AND vw_sales_rep_assignment.SALESREP_DISTRICT_ID = vw_supervisor_assignment.DISTRICT_REGION_ID\n" +
"AND LOWER(vw_supervisor_assignment.region_name) LIKE LOWER('%"+regionName+"%')\n" +
"AND vw_sales_rep_assignment.salesrep_id = dcm_user.dcm_user_id\n" +
"AND dcm_user_detail.user_detail_id      = dcm_user.user_detail_id\n" +
"AND dcm_user.manager_dcm_user_id        = supervisor.dcm_user_id\n" +
"AND supervisor_detail.user_detail_id    = supervisor.user_detail_id\n" +
"ORDER BY salesrep_district_id ASC";
            
            System.out.println("SQL ^^^ : \n"+ strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
               
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
    
    public static String getRegionLevel(Connection con, String regionName, String regionLevel) {
        
        int count=0;
        int regionId=-1;
        String regionLevelOneName="";
        boolean isRegionLevelOne= false;
                
        System.out.println("getRegionLevel "+regionName+" "+regionLevel);
        
              
        try {
            Statement stat = con.createStatement();
            
            
            String strSql = "select parent_region_id from dcm_region where lower(region_name) LIKE lower('%"+regionName+"%') and region_level_type_id="+regionLevel;
            System.out.println("parent level sql "+strSql);
            ResultSet res = stat.executeQuery(strSql);
            
            if (res.next()) {
              regionId = res.getInt("parent_region_id");
              while(count>=0 && !isRegionLevelOne)
              {
                  
                  strSql = "select region_level_type_id,parent_region_id,region_name from dcm_region where region_id="+regionId;
                  ResultSet res2 = stat.executeQuery(strSql);
                  if(res2.next())
                  {
                      if(res2.getInt("region_level_type_id") != 1)
                      {
                          regionId = res2.getInt("parent_region_id");
                          count++;
                      }
                      else 
                      {
                          regionLevelOneName = res2.getString("region_name");
                          isRegionLevelOne =true;
                      }
                  }
                  res2.close();
              }  
                
            }
            res.close();
            
            stat.close();
           // con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return regionLevelOneName;
    }
    
    
}
