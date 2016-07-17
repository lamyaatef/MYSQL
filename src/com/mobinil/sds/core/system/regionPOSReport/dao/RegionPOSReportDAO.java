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
    public static Vector getregionPOSData(Connection con) {
        Vector vec = new Vector();
        System.out.println("getregionPOSData ");
        try {
            Statement stat = con.createStatement();
            stat.setFetchSize(0);
            String strSql = "SELECT gen_dcm.channel_id,\n" +
"  dcm_pos_detail.pos_code,\n" +
"  dcm_pos_detail.pos_name,\n" +
"  dcm_pos_detail.pos_arabic_name,\n" +
"  dcm_pos_owner.pos_owner_name,\n" +
"  dcm_pos_owner.pos_owner_id_number,\n" +
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
"  dcm_pos_detail.HAS_SIGN\n" +
"FROM dcm_pos_detail,\n" +
"  gen_dcm,\n" +
"  dcm_pos_owner,\n" +
"  pos_documents,\n" +
"  dcm_pos_owner_phone,\n" +
"  gen_dcm_payment_level,\n" +
"  dcm_branch_pos\n" +
"WHERE dcm_pos_owner.pos_detail_id              = dcm_pos_detail.pos_detail_id\n" +
"AND gen_dcm_payment_level.DCM_PAYMENT_LEVEL_ID = gen_dcm.DCM_PAYMENT_LEVEL_ID\n" +
"AND dcm_branch_pos.pos_ID                      = dcm_pos_detail.pos_ID";
            System.out.println("get region pos query "+ strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                System.out.println("in result getregionPOSData ");
                vec.add(new RegionPOSReportModel(res));
                }
            res.close();
            stat.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vec;
    }
    
}
