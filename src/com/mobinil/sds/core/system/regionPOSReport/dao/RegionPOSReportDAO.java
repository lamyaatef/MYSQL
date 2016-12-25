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
            Statement stat2 = con.createStatement();
            //stat.setFetchSize(0);
            String strSql = "SELECT gen_dcm.channel_id AS channel_code,\n" +
"  gen_dcm.dcm_code        AS pos_code,\n" +
"  gen_dcm.dcm_name        AS pos_english_name,\n" +
"  dcm_pos_detail.pos_arabic_name,\n" +
"  dcm_pos_owner.pos_owner_name,\n" +
"  dcm_pos_owner.pos_owner_id_number,\n" +
"  dcm_id_type.id_type_name,\n" +
"  dcm_region.region_name,\n" +
"  city.region_name as city_name,\n" +
"  govern.region_name as govern_name,\n" +
"  dcm_pos_detail.district_code,\n" +
"  district.region_name as district_name,\n" +
"  area.region_code as area_code,\n" +
"  area.region_name as area_name,\n" +
"  dcm_pos_detail.pos_address,\n" +
"  dcm_pos_detail.pos_doc_num,\n" +
"  pos_documents.posdocuments,\n" +
"  pos_documents.assign_date,\n" +
"  gen_dcm_status.dcm_status_name as pos_status,\n" +
"  dcm_pos_owner_phone.pos_owner_phone_number,\n" +
"  gen_dcm_level.dcm_level_id as pos_level_code,\n" +
"  scm_supervisor.supervisor_name,\n" +
"  scm_teamleader.teamleader_name,\n" +
"  scm_salesrep.salesrep_name,\n" +
"  gen_dcm.stk_number,\n" +
"  scm_stk_status.name as stk_status,\n" +
"  pos_documents.stkactvdt as stk_activation_date,\n" +
"  pos_documents.iqrarrcvdt as iqrar_received_date,\n" +
"  CAM_PAYMENT_cam_state.cam_status_for_payment as payment_status,\n" +
"  gen_dcm_payment_level.dcm_payment_level_name as payment_level,\n" +
"  dcm_pos_detail.pos_arabic_address,\n" +
"  scm_iqrar_receving_status.name as is_iqrar_received,\n" +
"  scm_verified_status.name as is_verified,\n" +
"  dcm_pos_detail.doc_location,\n" +
"  dcm_pos_detail.survey_id,\n" +
"  dcm_pos_detail.is_level_one as L1,\n" +
"  dcm_pos_detail.is_exclusive as Ex,\n" +
"  dcm_pos_detail.has_sign as Sign,\n" +
"  dcm_pos_detail.is_quality_club as Qc\n" +
"FROM gen_dcm,\n" +
"  dcm_pos_detail,\n" +
"  dcm_pos_owner,\n" +
"  dcm_id_type,\n" +
"  dcm_region,\n" +
"  dcm_region city,\n" +
"  dcm_region govern,\n" +
"  dcm_region district,\n" +
"  dcm_region area,\n" +
"  pos_documents,\n" +
"  gen_dcm_status,\n" +
"  dcm_pos_owner_phone,\n" +
"  gen_dcm_level,\n" +
"  scm_supervisor,\n" +
"  scm_teamleader,\n" +
"  scm_salesrep,\n" +
"  scm_stk_status,\n" +
"  CAM_PAYMENT_SCM_STATUS,\n" +
"  CAM_PAYMENT_cam_state,\n" +
"  gen_dcm_payment_level,\n" +
"  scm_stk_owner,\n" +
"  scm_iqrar_receving_status,\n" +
"  scm_verified_status\n" +
"WHERE gen_dcm.dcm_id            = dcm_pos_detail.pos_id\n" +
"AND gen_dcm.dcm_code            = dcm_pos_detail.pos_code\n" +
"AND dcm_pos_owner.pos_detail_id = dcm_pos_detail.pos_detail_id\n" +
"AND dcm_pos_owner.pos_owner_id_type_id = dcm_id_type.id_type_id\n" +
"AND dcm_region.region_id = dcm_pos_detail.region_id\n" +
"AND city.region_id = dcm_pos_detail.pos_city_id\n" +
"AND govern.region_id = dcm_pos_detail.pos_governrate\n" +
"AND district.region_id = dcm_pos_detail.pos_district_id\n" +
"AND area.region_id = dcm_pos_detail.pos_area_id\n" +
"AND pos_documents.code = dcm_pos_detail.pos_code\n" +
"AND gen_dcm_status.dcm_status_id = dcm_pos_detail.pos_status_type_id\n" +
"AND dcm_pos_owner.pos_owner_id = dcm_pos_owner_phone.pos_owner_id\n" +
"AND gen_dcm_level.dcm_level_id = dcm_pos_detail.DCM_LEVEL_ID\n" +
"AND scm_supervisor.supervisor_id = dcm_pos_detail.supervisor_id\n" +
"AND scm_teamleader.teamleader_id = dcm_pos_detail.teamleader_id\n" +
"AND scm_salesrep.salesrep_id = dcm_pos_detail.salesrep_id\n" +
"AND scm_stk_status.stk_status_id = CAM_PAYMENT_SCM_STATUS.stk_status\n" +
"AND CAM_PAYMENT_SCM_STATUS.scm_id = gen_dcm.dcm_id\n" +
"AND CAM_PAYMENT_cam_state.id = CAM_PAYMENT_SCM_STATUS.PAYMENT_cam_state_id\n" +
"AND gen_dcm_payment_level.dcm_payment_level_id = dcm_pos_detail.dcm_payment_level_id\n" +
"AND scm_iqrar_receving_status.iqrar_receving_status_id = scm_stk_owner.iqrar_receving_status_id\n" +
"AND scm_verified_status.dcm_verified_status_id = scm_stk_owner.dcm_verified_status_id\n" +
"AND scm_stk_owner.dcm_id = dcm_pos_detail.pos_id\n" +
"\n" +
"AND dcm_pos_detail.flage       IS NULL";
            
            System.out.println("SQL ^^^ : \n"+ strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
               
                vec.add(new RegionPOSReportModel(res/*,supervisorName,teamleaderName*/));
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
