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
    public static Vector getRegionPOSData(Connection con/*, String regionName, String regionLevel*/) {
        
        Vector vec = new Vector();
      //  String supervisorName="";
     //   String teamleaderName="";
                
       // System.out.println("getregionPOSData "+regionName);
        
              
        try {
            Statement stat = con.createStatement();
            //Statement stat2 = con.createStatement();
            //stat.setFetchSize(0);
            StringBuilder strSql = new StringBuilder("SELECT gen_dcm.channel_id AS channel_code,");
            strSql.append("  gen_dcm.dcm_code AS pos_code," );

            strSql.append("  gen_dcm.dcm_name  AS pos_english_name," );
            strSql.append("  dcm_pos_detail.pos_arabic_name,") ;
            strSql.append("  dcm_pos_owner.pos_owner_name,");
            strSql.append("  dcm_pos_owner.pos_owner_id_number,");

            strSql.append("  dcm_id_type.id_type_name," );
            strSql.append("  dcm_region.region_name," );
            strSql.append("  city.region_name as city_name,");
            strSql.append("  govern.region_name as govern_name," );
            strSql.append("  dcm_pos_detail.district_code,");
            strSql.append("  district.region_name as district_name,");
            strSql.append("  area.region_code as area_code,");
            strSql.append("  area.region_name as area_name," );
            strSql.append("  dcm_pos_detail.pos_address,");
            strSql.append("  dcm_pos_detail.pos_doc_num,");
            strSql.append("  pos_documents.posdocuments,");
            strSql.append("  pos_documents.assign_date,");
            strSql.append("  gen_dcm_status.dcm_status_name as pos_status,");
            strSql.append("  dcm_pos_owner_phone.pos_owner_phone_number," );
            strSql.append("  gen_dcm_level.dcm_level_id as pos_level_code,");
            strSql.append("  scm_supervisor.supervisor_name," );
            strSql.append("  scm_teamleader.teamleader_name,");
            strSql.append("  scm_salesrep.salesrep_name," );
            strSql.append("  pos_documents.StkDialNo," );
            strSql.append("  scm_stk_status.name as stk_status,");
            strSql.append("  pos_documents.stkactvdt as stk_activation_date," );
            strSql.append("  pos_documents.iqrarrcvdt as iqrar_received_date," );
            strSql.append("  CAM_PAYMENT_cam_state.cam_status_for_payment as payment_status,");
            strSql.append("  gen_dcm_payment_level.dcm_payment_level_name as payment_level,");
            strSql.append("  dcm_pos_detail.pos_arabic_address,");
            //strSql.append("  --scm_iqrar_receving_status.name as is_iqrar_received," );
            //strSql.append("  --scm_verified_status.name as is_verified," );
            strSql.append("  dcm_pos_detail.doc_location," );
            strSql.append("  dcm_pos_detail.survey_id,");
            strSql.append("  dcm_pos_detail.is_level_one as L1,");
            strSql.append("  dcm_pos_detail.is_exclusive as Ex,");
            strSql.append("  dcm_pos_detail.has_sign as Sign," );
            strSql.append("  dcm_pos_detail.is_quality_club as Qc" );
            strSql.append(" FROM gen_dcm," );
            strSql.append("  dcm_pos_detail,");
            strSql.append("  dcm_pos_owner," );
            strSql.append("  dcm_id_type,");
            strSql.append("  dcm_region," );
            strSql.append("  dcm_region city,");
            strSql.append("  dcm_region govern,");
            strSql.append("  dcm_region district," );
            strSql.append("  dcm_region area," );
            strSql.append("  pos_documents," );
            strSql.append("  gen_dcm_status,");
            strSql.append("  dcm_pos_owner_phone," );
            strSql.append("  gen_dcm_level,");
            strSql.append("  scm_supervisor,");
            strSql.append("  scm_teamleader,");
            strSql.append("  scm_salesrep," );
            strSql.append("  scm_stk_status,");
            strSql.append("  CAM_PAYMENT_SCM_STATUS," );
            strSql.append("  CAM_PAYMENT_cam_state,");
            strSql.append("  gen_dcm_payment_level " );
            //strSql.append(" -- scm_stk_owner,");
            //strSql.append(" -- scm_iqrar_receving_status,");
            //strSql.append(" -- scm_verified_status");
            strSql.append(" WHERE gen_dcm.dcm_id            = dcm_pos_detail.pos_id" );
            strSql.append(" AND gen_dcm.dcm_code            = dcm_pos_detail.pos_code" );
            strSql.append(" AND dcm_pos_owner.pos_detail_id = dcm_pos_detail.pos_detail_id" );
            strSql.append(" AND dcm_pos_owner.pos_owner_id_type_id = dcm_id_type.id_type_id");
            strSql.append(" AND dcm_region.region_id = dcm_pos_detail.region_id");
            strSql.append(" AND city.region_id = dcm_pos_detail.pos_city_id");
            strSql.append(" AND govern.region_id = dcm_pos_detail.pos_governrate");
            strSql.append(" AND district.region_id = dcm_pos_detail.pos_district_id");
            strSql.append(" AND area.region_id = dcm_pos_detail.pos_area_id");
            strSql.append(" AND pos_documents.code = dcm_pos_detail.pos_code");
            strSql.append(" AND gen_dcm_status.dcm_status_id = dcm_pos_detail.pos_status_type_id");
            strSql.append(" AND dcm_pos_owner.pos_owner_id = dcm_pos_owner_phone.pos_owner_id  (+)");
            strSql.append(" AND gen_dcm_level.dcm_level_id = dcm_pos_detail.DCM_LEVEL_ID" );

            strSql.append(" AND scm_supervisor.supervisor_id = dcm_pos_detail.supervisor_id");
            strSql.append(" AND scm_teamleader.teamleader_id = dcm_pos_detail.teamleader_id");
            strSql.append(" AND scm_salesrep.salesrep_id = dcm_pos_detail.salesrep_id");

            strSql.append(" AND scm_stk_status.stk_status_id = CAM_PAYMENT_SCM_STATUS.stk_status");
            strSql.append(" AND CAM_PAYMENT_SCM_STATUS.scm_id = gen_dcm.dcm_id");
            strSql.append(" AND CAM_PAYMENT_cam_state.id = CAM_PAYMENT_SCM_STATUS.PAYMENT_cam_state_id" );
            strSql.append(" AND gen_dcm_payment_level.dcm_payment_level_id = dcm_pos_detail.dcm_payment_level_id " );
            //strSql.append(" --AND scm_iqrar_receving_status.iqrar_receving_status_id = scm_stk_owner.iqrar_receving_status_id");
            //strSql.append(" --AND scm_stk_owner.dcm_id = dcm_pos_detail.pos_id ");
            //strSql.append("--AND scm_verified_status.dcm_verified_status_id = scm_stk_owner.dcm_verified_status_id");
            strSql.append(" AND dcm_pos_detail.flage       IS NULL ");
           /*AND dcm_region.region_name='Alexandria'*/ 
            
            System.out.println("SQL ^^^ : \n"+ strSql);
            ResultSet res = stat.executeQuery(strSql.toString());
            while (res.next()) {
               System.out.println("result POS Region : ");
               
                vec.add(new RegionPOSReportModel(res/*,supervisorName,teamleaderName*/));
                }
            res.close();
            //stat2.close();
           // stat.close();
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
            //System.out.println("parent level sql "+strSql);
            ResultSet res = stat.executeQuery(strSql);
            
            if (res.next()) {
              regionId = res.getInt("parent_region_id");
              while(count>=0 && !isRegionLevelOne)
              {
                  
                  strSql = "select region_level_type_id,parent_region_id,region_name from dcm_region where region_id="+regionId;
                  //System.out.println("region level sql "+strSql);
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
            System.out.println("WHILE - END");
            res.close();
            
            stat.close();
           // con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return regionLevelOneName;
    }
    
    
}
