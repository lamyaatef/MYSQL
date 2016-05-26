/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.scm.dao;

import com.mobinil.sds.core.system.Model;
import com.mobinil.sds.core.system.commission.model.LabelImportModel;
import com.mobinil.sds.core.system.sa.importdata.DataImportEngine;
import com.mobinil.sds.core.system.scm.model.DistributerStkExcelViewer;
import com.mobinil.sds.core.system.scm.model.GenDcmAddressModel;
import com.mobinil.sds.core.system.scm.model.POSModel;
import com.mobinil.sds.core.system.scm.model.POSPaymentStatusHistory;
import com.mobinil.sds.core.system.scm.model.POSStatusCase;
import com.mobinil.sds.core.system.scm.model.POSStatusHistory;
import com.mobinil.sds.core.system.scm.model.PosDetailAddressModel;
import com.mobinil.sds.core.system.scm.model.PosPaymentLevel;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author Ahmed Adel
 */
public class POSDAO {

    private static String posStatusTempQuery = "TRUNCATE TABLE SCM_POS_STATUS_TEMP ";

    public static Vector<POSModel> getPOSStatus(String POSCode) {
        Connection con;
        try {
            con = Utility.getConnection();
            Vector<POSModel> POSStatus = new Vector<POSModel>();
            // UPDATED BY MEDHAT
            String getStatusquery = "SELECT STATUS.DCM_STATUS_NAME DCM_STATUS , DCM.DCM_NAME, PSTATUSNAME.CAM_STATUS_FOR_PAYMENT PAYMENT_STATUS FROM VW_GEN_DCM_SCM DCM,GEN_DCM_STATUS STATUS,CAM_PAYMENT_SCM_STATUS PSTATUS,CAM_PAYMENT_CAM_STATE PSTATUSNAME"
                    + " WHERE DCM.DCM_CODE=" + "'" + POSCode + "'"
                    + " AND STATUS.DCM_STATUS_ID=DCM.DCM_STATUS_ID"
                    //+ // " AND DCM.DCM_LEVEL_ID=3"+
                    //  " AND DCM.DCM_PAYMENT_LEVEL_ID=4"+
                   // " AND DCM.CHANNEL_ID=1"
                    + " AND PSTATUS.SCM_ID(+)=DCM.DCM_ID"
                    + " AND PSTATUSNAME.ID(+)=PSTATUS.PAYMENT_CAM_STATE_ID";


            System.out.println("Get Pos Status Query:" + getStatusquery);

            POSStatus = DBUtil.executeSqlQueryMultiValue(getStatusquery, POSModel.class, con);

            return POSStatus;


        } catch (SQLException ex) {
            Logger.getLogger(POSDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }



    }

    public static void changePOSStatus(String POSCode, String POSStatus, String userId, String reason) {
        String POSchangestatusquery = "UPDATE GEN_DCM SET DCM_STATUS_ID=" + POSStatus + "  WHERE DCM_CODE=" + "'" + POSCode + "'";
        DBUtil.executeSQL(POSchangestatusquery);
        DBUtil.executeSQL("INSERT INTO SDS.SCM_POS_CHANGE_STATUS_LOG "
                + "(USER_ID, DCM_CODE, UPDATED_IN, REASON,STATUS_ID) "
                + " VALUES (" + userId + "," + "'" + POSCode + "'" + ",sysdate," + "'" + reason + "'" + "," + POSStatus + ") ");
    }

    public static void changePaymentStatusforPOS(String POSCode, String paymentStatus, String userId, String reason) {
        //UPDATED BY MEDHAT
        String paymentchangestatusquery = "update CAM_PAYMENT_SCM_STATUS set PAYMENT_CAM_STATE_ID=" + paymentStatus
                + " where SCM_ID=(SELECT DCM_ID FROM VW_GEN_DCM_SCM WHERE"
                + //" DCM_LEVEL_ID=3"+
                //   " AND DCM_PAYMENT_LEVEL_ID=4"+
                "  CHANNEL_ID=1"
                + " AND DCM_CODE=" + "'" + POSCode + "'" + ")";
        DBUtil.executeSQL(paymentchangestatusquery);
        DBUtil.executeSQL("INSERT INTO SDS.SCM_POS_PAYMENT_STATUS_LOG "
                + "(USER_ID, DCM_CODE, UPDATED_IN, REASON,STATUS_ID) "
                + " VALUES (" + userId + ",'" + POSCode + "',sysdate," + "'" + reason + "'" + "," + paymentStatus + ")");
    }

    public static Vector<POSStatusCase> beforeChangePOSFromExcel() {
        Connection con;
        try {
            con = Utility.getConnection();
            //updated by MEDHAT
            String checkQueryPOSNotExist = "UPDATE SCM_POS_STATUS_TEMP SET EXIST=1 WHERE POS_CODE NOT IN"
                    + "(SELECT DCM_CODE FROM VW_GEN_DCM_SCM "
                    + //          " AND DCM_PAYMENT_LEVEL_ID=4"+
                    " WHERE CHANNEL_ID=1)";



            DBUtil.executeSQL(checkQueryPOSNotExist);

            // updated by medhat
            String checkQueryPOSHasPayment = "UPDATE SCM_POS_STATUS_TEMP SET EXIST=2 WHERE POS_CODE IN"
                    + "( SELECT"
                    + " DCM.DCM_CODE POS_CODE"
                    + " FROM"
                    + " VW_GEN_DCM_SCM DCM,"
                    + " GEN_DCM_STATUS STATUS,"
                    + " CAM_PAYMENT_SCM_STATUS PSTATUS,"
                    + " CAM_PAYMENT_CAM_STATE PSTATUSNAME"
                    + " WHERE"
                    + " DCM.DCM_CODE IN (SELECT POS_CODE FROM SCM_POS_STATUS_TEMP )"
                    + //" AND DCM.DCM_LEVEL_ID=3"  +
                    //       " AND DCM.DCM_PAYMENT_LEVEL_ID=4"+
                    " AND DCM.CHANNEL_ID=1"
                    + " AND STATUS.DCM_STATUS_ID=DCM.DCM_STATUS_ID "
                    + " AND PSTATUS.SCM_ID(+)=DCM.DCM_ID"
                    + " AND PSTATUSNAME.ID(+)=PSTATUS.PAYMENT_CAM_STATE_ID"
                    + " AND PSTATUS.PAYMENT_CAM_STATE_ID IS NULL )";

            DBUtil.executeSQL(checkQueryPOSHasPayment);
            // updated by medhat
            String checkQueryPOSNotActive = "UPDATE SCM_POS_STATUS_TEMP SET EXIST=3 WHERE POS_CODE IN"
                    + "(SELECT DCM_CODE FROM VW_GEN_DCM_SCM "
                    + //  " AND DCM_PAYMENT_LEVEL_ID=4"+
                    " WHERE CHANNEL_ID=1"
                    + " AND DCM_STATUS_ID<>1)";

            DBUtil.executeSQL(checkQueryPOSNotActive);

            String refusedPOS = "SELECT POS_CODE , EXIST  FROM SCM_POS_STATUS_TEMP WHERE EXIST IS NOT NULL";

            Vector<POSStatusCase> notExistPOS = DBUtil.executeSqlQueryMultiValue(refusedPOS, POSStatusCase.class, con);
            return notExistPOS;


        } catch (SQLException ex) {
            Logger.getLogger(POSDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }



    }

    public static int changeposstatusfromExcel(String POSStatus, String userId, String reason) {
        //UPDATED BY MEDHAT

        String query = "UPDATE GEN_DCM SET DCM_STATUS_ID=" + POSStatus + " WHERE DCM_CODE IN (SELECT POS_CODE FROM SCM_POS_STATUS_TEMP WHERE EXIST <> 1 OR EXIST IS NULL)"
                + // " AND DCM_LEVEL_ID=3"+
                " AND DCM_LEVEL_ID IN (2,3)"
                + //  " AND DCM_PAYMENT_LEVEL_ID=4"+
                " AND CHANNEL_ID=1";

        DBUtil.executeSQL(query);

        DBUtil.executeSQL("INSERT INTO SDS.SCM_POS_CHANGE_STATUS_LOG "
                + "(USER_ID, DCM_CODE, UPDATED_IN, REASON,STATUS_ID) "
                + " SELECT " + userId + ",POS_CODE,sysdate," + "'" + reason + "'," + POSStatus + " FROM SCM_POS_STATUS_TEMP WHERE EXIST <> 1 OR EXIST IS NULL");

        Connection con;
        try {
            con = Utility.getConnection();
            int changedPOSs = DBUtil.executeQuerySingleValueInt("SELECT COUNT(*) POSS FROM SCM_POS_STATUS_TEMP WHERE EXIST <> 1 OR EXIST IS NULL", "POSS", con);

            DBUtil.executeSQL(posStatusTempQuery);
            return changedPOSs;

        } catch (SQLException ex) {
            Logger.getLogger(POSDAO.class.getName()).log(Level.SEVERE, null, ex);
            DBUtil.executeSQL(posStatusTempQuery);
            return 0;
        }
    }

    public static int changepaymentstatusfromExcel(String paymentStatus, String userId, String reason) {

        //UPDATED BY MEDHAT

        String query = "update CAM_PAYMENT_SCM_STATUS set PAYMENT_CAM_STATE_ID=" + paymentStatus
                + " where SCM_ID IN (SELECT DCM_ID FROM VW_GEN_DCM_SCM WHERE DCM_CODE IN (SELECT POS_CODE FROM SCM_POS_STATUS_TEMP WHERE EXIST IS NULL OR EXIST <> 3)"
                + //" AND DCM_LEVEL_ID=3"+
                //   " AND DCM_PAYMENT_LEVEL_ID=4"+
                " AND CHANNEL_ID=1"
                + ")";

        DBUtil.executeSQL(query);

        DBUtil.executeSQL("INSERT INTO SDS.SCM_POS_PAYMENT_STATUS_LOG "
                + "(USER_ID, DCM_CODE, UPDATED_IN, REASON,STATUS_ID) "
                + " SELECT " + userId + ",POSCODE,sysdate," + "'" + reason + "'" + paymentStatus + " FROM SCM_POS_STATUS_TEMP WHERE EXIST <> 1 OR EXIST IS NULL");


        Connection con;
        try {
            con = Utility.getConnection();
            int changedPOSs = DBUtil.executeQuerySingleValueInt("SELECT COUNT(*) POSs FROM SCM_POS_STATUS_TEMP WHERE EXIST IS NULL OR EXIST <> 3", "POSs", con);
            DBUtil.executeSQL(posStatusTempQuery);
            return changedPOSs;

        } catch (SQLException ex) {
            Logger.getLogger(POSDAO.class.getName()).log(Level.SEVERE, null, ex);
            DBUtil.executeSQL(posStatusTempQuery);
            return 0;
        }
    }

    public static String getPOSName(String DCM_Id) {
        //updated by medhat
        String query = "SELECT DCM_NAME FROM VW_GEN_DCM_SCM WHERE "
                + //"  GEN_DCM.DCM_LEVEL_ID=3"+
                // " AND DCM_PAYMENT_LEVEL_ID=4"+
                "  CHANNEL_ID=1"
                + " AND DCM_ID=" + DCM_Id;
        String DCM_Name = DBUtil.executeQuerySingleValueString(query, "DCM_NAME");
        return DCM_Name;
    }

    public static String getDistributerName(String DCM_Id) {
        String query = "SELECT DCM_NAME FROM GEN_DCM WHERE "
                + "  GEN_DCM.DCM_LEVEL_ID=1"
                + //            " AND DCM_PAYMENT_LEVEL_ID=1"+
                " AND CHANNEL_ID=1"
                + " AND DCM_ID=" + DCM_Id;
        String DCM_Name = DBUtil.executeQuerySingleValueString(query, "DCM_NAME");
        return DCM_Name;
    }

    public static String getPOSNameFromCode(String DCM_Code) {

        //updated by medhat
        String query = "SELECT DCM_NAME FROM VW_GEN_DCM_SCM WHERE "
                + // "  GEN_DCM.DCM_LEVEL_ID=3"+
                //  " AND DCM_PAYMENT_LEVEL_ID=4"+
                "  CHANNEL_ID=1"
                + " AND DCM_CODE='" + DCM_Code + "'";
        String DCM_Name = DBUtil.executeQuerySingleValueString(query, "DCM_NAME");
        return DCM_Name;
    }

    public static String getDistributerNameFromCode(String DCM_Code) {
        String query = "SELECT DCM_NAME FROM GEN_DCM WHERE "
                + "  GEN_DCM.DCM_LEVEL_ID=1"
                + " AND DCM_PAYMENT_LEVEL_ID=1"
                + " AND CHANNEL_ID=1"
                + " AND DCM_CODE='" + DCM_Code + "'";
        String DCM_Name = DBUtil.executeQuerySingleValueString(query, "DCM_NAME");
        return DCM_Name;
    }

    public static Vector<POSStatusHistory> getPOSStatusHistory(String POSCode) {

        Connection con;
        try {
            con = Utility.getConnection();

            String query = "SELECT P.PERSON_FULL_NAME , H.UPDATED_IN, "
                    + " H.REASON, S.DCM_STATUS_NAME"
                    + " FROM SDS.SCM_POS_CHANGE_STATUS_LOG H , GEN_PERSON P ,SDS.GEN_DCM_STATUS S"
                    + " WHERE P.PERSON_ID=H.USER_ID"
                    + " AND S.DCM_STATUS_ID=H.STATUS_ID"
                    + " AND H.DCM_CODE=" + "'" + POSCode + "'" + "";

            Vector<POSStatusHistory> POSStatusHistory = DBUtil.executeSqlQueryMultiValue(query, POSStatusHistory.class, con);

            return POSStatusHistory;
        } catch (SQLException ex) {
            Logger.getLogger(POSDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }


    }

    public static Vector<POSPaymentStatusHistory> getPOSPaymentHistory(String POSCode) {
        Connection con;
        try {
            con = Utility.getConnection();

            String query = "SELECT P.PERSON_FULL_NAME , H.UPDATED_IN, "
                    + "H.REASON, S.CAM_STATUS_FOR_PAYMENT "
                    + " FROM SDS.SCM_POS_PAYMENT_STATUS_LOG H , GEN_PERSON P ,SDS.CAM_PAYMENT_CAM_STATE S"
                    + " WHERE P.PERSON_ID=H.USER_ID"
                    + " AND S.ID =H.STATUS_ID"
                    + " AND H.DCM_CODE=" + "'" + POSCode + "'" + "";

            Vector<POSPaymentStatusHistory> POSPaymentStatusHistory = DBUtil.executeSqlQueryMultiValue(query, POSPaymentStatusHistory.class, con);

            return POSPaymentStatusHistory;
        } catch (SQLException ex) {
            Logger.getLogger(POSDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public static String getPOSIdFromPOSCode(String POSCode) {
        String query = "SELECT DCM_ID FROM GEN_DCM WHERE DCM_CODE='" + POSCode + "'";

        String POSId = DBUtil.executeQuerySingleValueString(query, "DCM_ID");
        return POSId;
    }

    public static String generateCodeForChildPOS(String parentCode) {
        String newParentCode = parentCode.contains(".") ? parentCode.split("\\.")[0] : parentCode;
        String query = "select max(dcm_code)+0.001 from gen_dcm where  dcm_code like '" + newParentCode + ".%'";
        String genratedCode = DBUtil.executeQuerySingleValueString(query, "max(dcm_code)+0.001");
        return newParentCode + "." + genratedCode.split("\\.")[1];
    }

    public static Vector getAllDistributer(Connection con) {
        String query = "SELECT DCM_NAME,DCM_CODE FROM GEN_DCM WHERE CHANNEL_ID=1 AND DCM_LEVEL_ID=1"
                + " AND DCM_PAYMENT_LEVEL_ID=1 AND DCM_STATUS_ID =1 ORDER BY DCM_NAME ASC";
        Vector<POSModel> distributers = DBUtil.executeSqlQueryMultiValue(query, POSModel.class, con);
        return distributers;
    }

    public static String getChannelForPOS(String parentCode) {
        String query = "SELECT CHANNEL_ID FROM GEN_DCM WHERE DCM_CODE LIKE '" + parentCode + "%'";
        String channel = DBUtil.executeQuerySingleValueString(query, "CHANNEL_ID");
        return channel;
    }

    public static String getLevelForPOS(String parentCode) {
        String query = "SELECT DCM_PAYMENT_LEVEL_ID FROM GEN_DCM WHERE DCM_CODE LIKE '" + parentCode + "%'";
        String level = DBUtil.executeQuerySingleValueString(query, "DCM_PAYMENT_LEVEL_ID");
        return level;
    }

    public static Vector<PosPaymentLevel> getAllPaymentLevel() {

        Connection con = null;
        try {
            con = Utility.getConnection();
            String query = "SELECT * FROM GEN_DCM_PAYMENT_LEVEL ORDER BY DCM_PAYMENT_LEVEL_NAME ASC";

            Vector<PosPaymentLevel> paymentLevels =
                    DBUtil.executePreparedSqlQueryMultiValue(query, PosPaymentLevel.class, con);
            return paymentLevels;

        } catch (SQLException ex) {
            Logger.getLogger(POSDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public static Vector<POSStatusCase> beforeChangePaymentLevelByExcel() {

        Connection con = null;

        try {
            con = Utility.getConnection();
            // updated by medhat
            String checkQueryPOSNotExist = "UPDATE SCM_POS_STATUS_TEMP SET EXIST=1 WHERE POS_CODE NOT IN"
                    + "(SELECT DCM_CODE FROM VW_GEN_DCM_SCM WHERE "
                    + //          " AND DCM_PAYMENT_LEVEL_ID=4"+
                    "  CHANNEL_ID=1)";

            DBUtil.executeSQL(checkQueryPOSNotExist);

            String posNotExist = "SELECT POS_CODE , EXIST FROM  SCM_POS_STATUS_TEMP WHERE EXIST=1";

            Vector<POSStatusCase> posNotExistList = DBUtil.executeSqlQueryMultiValue(posNotExist, POSStatusCase.class, con);

            return posNotExistList;

        } catch (SQLException ex) {
            Logger.getLogger(POSDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }




    }

    public static int changePosPaymentLevel(String DCM_PAYMENT_LEVEL_ID, String user_Id) {
        String updatepayment = "UPDATE GEN_DCM SET DCM_PAYMENT_LEVEL_ID =" + DCM_PAYMENT_LEVEL_ID
                + " WHERE DCM_CODE IN (SELECT POS_CODE FROM SCM_POS_STATUS_TEMP WHERE EXIST IS NULL)";

        DBUtil.executeSQL(updatepayment);

        Connection con;
        try {
            con = Utility.getConnection();
            int changed = DBUtil.executeQuerySingleValueInt("SELECT  COUNT (POS_CODE) FROM SCM_POS_STATUS_TEMP  WHERE EXIST IS NULL", "COUNT(POS_CODE)", con);
            DBUtil.executeSQL(posStatusTempQuery);
            return changed;
        } catch (SQLException ex) {
            Logger.getLogger(POSDAO.class.getName()).log(Level.SEVERE, null, ex);
            DBUtil.executeSQL(posStatusTempQuery);
            return 0;
        }

    }

    public static Vector<DistributerStkExcelViewer> getAllDistributerStkActivation() {
        try {
            Connection con = Utility.getConnection();
            String query = "SELECT * FROM SCM_STK_DIST_ACTIVATION ORDER BY FILE_ID";
            Vector<DistributerStkExcelViewer> distributersStkExcelViewers = DBUtil.executeSqlQueryMultiValue(query, DistributerStkExcelViewer.class, con);
            return distributersStkExcelViewers;
        } catch (SQLException ex) {
            Logger.getLogger(POSDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }



    }

    public static Blob getDistHistoryExcel(String fileId) {


        try {
            Connection con = Utility.getConnection();
            String query = "SELECT FILE_DIR FROM SCM_STK_DIST_ACTIVATION";
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(query);
            Blob file = null;
            while (res.next()) {
                file = res.getBlob("FILE_DIR");
            }

            return file;
        } catch (SQLException ex) {
            Logger.getLogger(POSDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }


    }

    public static String getChannelName(Connection con, String channelId) {
        String query = "SELECT CHANNEL_NAME FROM GEN_CHANNEL WHERE CHANNEL_ID =" + channelId;
        return DBUtil.executeQuerySingleValueString(con, query, "CHANNEL_NAME");
    }

    public static String getPaymentLevelName(Connection con, String paymentlevelId) {
        String query = "SELECT DCM_PAYMENT_LEVEL_NAME FROM GEN_DCM_PAYMENT_LEVEL WHERE DCM_PAYMENT_LEVEL_ID =" + paymentlevelId;
        return DBUtil.executeQuerySingleValueString(con, query, "DCM_PAYMENT_LEVEL_NAME");

    }

    public static String getPaymentStatusName(Connection con, String paymentStatusId) {
        String query = "SELECT CAM_STATUS_FOR_PAYMENT FROM CAM_PAYMENT_CAM_STATE WHERE ID =" + paymentStatusId;
        return DBUtil.executeQuerySingleValueString(con, query, "CAM_STATUS_FOR_PAYMENT");

    }

    public static String getPosLevelName(Connection con, String posLevelId) {
        String query = "SELECT DCM_LEVEL_NAME FROM GEN_DCM_LEVEL WHERE DCM_LEVEL_ID =" + posLevelId;
        return DBUtil.executeQuerySingleValueString(con, query, "DCM_LEVEL_NAME");
    }

    public static Vector<String> uploadApprovedMemoSheet(Connection con, Sheet sheet) {
        Vector<String> errorMessags = new Vector<String>();
        int lastRowNum = sheet.getLastRowNum() + 1;
        String sqlQuery = "update dcm_pos_detail set pos_proposed_doc_id = '4' , pos_doc_num = 'Memo' where pos_code = ? and flage is null";
        String firstCellValue = "";
        int updateStatus = 0;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int currentRow = 1; currentRow < lastRowNum; currentRow++) {
            updateStatus = 0;
            firstCellValue = DataImportEngine.readCell(sheet.getRow(currentRow).getCell(0));
            try {
                ps.setString(1, firstCellValue);
                updateStatus = ps.executeUpdate();
                if (updateStatus == 0) {
                    errorMessags.add(String.format("There is no pos code in line %d like that %s in dcm pos detail table", currentRow + 1, firstCellValue));
                }
            } catch (SQLException ex) {
                errorMessags.add(String.format("Exception in update %s line %d and error message and code is %s , %d", firstCellValue, currentRow + 1, ex.getMessage(), ex.getErrorCode()));
            }
        }
        return errorMessags;
    }
//////////////////////////////////////////////////////////////////////////////

    public static void getTmpAddressGenDcm(Connection con) {
       // Vector genVec = new Vector();
        List<GenDcmAddressModel> GenDcmAddresslist = new ArrayList<GenDcmAddressModel>();
        try {

            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from TMP_ADDRESS_GEN_DCM ");

            while (res.next()) {
                GenDcmAddressModel model = new GenDcmAddressModel();
                model.setCityId(res.getString("DCM_CITY_ID"));
                model.setDistrictId(res.getString("DCM_DISTRICT_ID"));
                model.setAddress(res.getString("DCM_ADDRESS"));
                model.setDcmCode(res.getString("DCM_CODE"));
                GenDcmAddresslist.add(model);

            }
            stat.close();


            for (GenDcmAddressModel model : GenDcmAddresslist) {
                String CityId = model.getCityId();
                String DistrictId = model.getDistrictId();
                String Address = model.getAddress();
                String DcmCode = model.getDcmCode();

                String strSql = "UPDATE GEN_DCM SET DCM_CITY_ID = (SELECT REGION_ID FROM DCM_REGION WHERE REGION_NAME = '" + CityId
                        + "' AND REGION_LEVEL_TYPE_ID=3) , DCM_DISTRICT_ID = (SELECT REGION_ID FROM DCM_REGION WHERE REGION_NAME = '" + DistrictId
                        + "' AND REGION_LEVEL_TYPE_ID=4) , DCM_ADDRESS = '" + Address + "' WHERE DCM_CODE = '" + DcmCode + "'";
                DBUtil.executeSQL(strSql);
                System.out.println("SQL update GenDcm: " + strSql);

            }

            //

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/////////////////////////////////////////////////////////////////////////    

    public static void deleteTmpAddressGenDcm(Connection con) {
        String deleteSql = "delete from TMP_ADDRESS_GEN_DCM";
        DBUtil.executeSQL(deleteSql, con);
    }
//////////////////////////////////////////////////////////////////////////////

    public static void deleteTmpAddressPosDetail(Connection con) {
        String deleteSql = "delete from TMP_ADDRESS_DCM_POS_DETAIL";
        DBUtil.executeSQL(deleteSql, con);
    }
/////////////////////////////////////////////////////////////////////////

    public static void getTmpAddressPosDetail(Connection con) {
        Vector PosVec = new Vector();
        List<PosDetailAddressModel> posList = new ArrayList<PosDetailAddressModel>();
        try {
            Statement stat = con.createStatement();
            ResultSet res2 = stat.executeQuery("select * from TMP_ADDRESS_DCM_POS_DETAIL ");
            while (res2.next()) {
                PosDetailAddressModel mod = new PosDetailAddressModel();
                mod.setPosCode(res2.getString("POS_CODE"));
                mod.setDistrictId(res2.getString("POS_DISTRICT_ID"));
                mod.setCityId(res2.getString("POS_CITY_ID"));
                mod.setRegionId(res2.getString("REGION_ID"));
                mod.setGovernrateId(res2.getString("POS_GOVERNRATE"));
                mod.setAreaId(res2.getString("POS_AREA_ID"));
                mod.setAddress(res2.getString("POS_ADDRESS"));
                mod.setArabicAddress(res2.getString("POS_ARABIC_ADDRESS"));
                posList.add(mod);
            }
            stat.close();

            for (PosDetailAddressModel mod : posList) {
                String PosCode = mod.getPosCode();
                String DistrictId = mod.getDistrictId();
                String CityId = mod.getCityId();
                String RegionId = mod.getRegionId();
                String GovernrateId = mod.getGovernrateId();
                String AreaId = mod.getAreaId();
                String Address = mod.getAddress();
                String ArabicAddress = mod.getArabicAddress();

                String strSql = "UPDATE DCM_POS_DETAIL SET POS_DISTRICT_ID = (SELECT REGION_ID FROM DCM_REGION WHERE REGION_NAME = '" + DistrictId
                        + "'AND REGION_LEVEL_TYPE_ID=4) , POS_CITY_ID = (SELECT REGION_ID FROM DCM_REGION WHERE REGION_NAME = '" + CityId
                        + "'AND REGION_LEVEL_TYPE_ID=3) , REGION_ID = (SELECT REGION_ID FROM DCM_REGION WHERE REGION_NAME = '" + RegionId
                        + "'AND REGION_LEVEL_TYPE_ID=1) , POS_GOVERNRATE = (SELECT REGION_ID FROM DCM_REGION WHERE REGION_NAME = '" + GovernrateId
                        + "'AND REGION_LEVEL_TYPE_ID=2) , POS_AREA_ID = (SELECT REGION_ID FROM DCM_REGION WHERE REGION_NAME = '" + AreaId
                        + "'AND REGION_LEVEL_TYPE_ID=5) , POS_ADDRESS = '" + Address
                        + "' , POS_ARABIC_ADDRESS = '" + ArabicAddress + "'WHERE POS_CODE = '" + PosCode + "'";
                DBUtil.executeSQL(strSql);
                System.out.println("SQL update DcmPosDetail: " + strSql);
            }
            
        }
    
    catch (Exception e){
        e.printStackTrace();
}
  
}

}

