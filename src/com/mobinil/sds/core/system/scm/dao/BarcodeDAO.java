/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.scm.dao;

import com.mobinil.sds.core.system.scm.dto.STKDistRequestViewerDTO;
import com.mobinil.sds.core.system.scm.model.*;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed Adel
 */
public class BarcodeDAO {

    public static void updateDownloadCount(Connection con,String requestId)
    {
    DBUtil.executeSQL("update SCM_BARCODE_POS_REQUEST set NO_OF_DOWN=NO_OF_DOWN+1 where REQUEST_ID='"+requestId+"'", con);
    }
    public static void updateDistDownloadCount(Connection con,String requestId)
    {
    DBUtil.executeSQL("update SCM_STK_DIST_REQUEST set NO_OF_DOWN=NO_OF_DOWN+1 where REQUEST_ID='"+requestId+"'", con);
    }

    public static int getRecievedBarcodefromCR(String posCode) {

        Connection con;
        try {
            con = Utility.getConnection();
            String receievedsql = "SELECT COUNT(CONTRACT_ID) Quantity FROM CR_CONTRACT CRC, CR_SHEET CRS "
                    + "WHERE CRC.SHEET_ID=CRS.SHEET_ID "
                    + "AND CRS.SHEET_RECIEVE_DATE >= add_months(trunc(sysdate,'MM'),-2) "
                    + "AND CRS.SHEET_RECIEVE_DATE<= sysdate AND CRS.SHEET_POS_CODE='" + posCode + "'";

            int receievedQuantity = DBUtil.executeQuerySingleValueInt(receievedsql, "Quantity", con);

            return receievedQuantity;

        } catch (SQLException ex) {
            Logger.getLogger(BarcodeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }




     public static Vector<BarCodeCaseModel> beforeGetRecievedBarcodeForExcelSheet() {

        Connection con;
        try {
            con = Utility.getConnection();

            String posNotexist="update SCM_POS_BARCODE_TEMP SET STATUS=1 WHERE POS_CODE NOT IN"+"(SELECT DCM_CODE FROM GEN_DCM where SCM_POS_BARCODE_TEMP.POS_CODE = gen_dcm.dcm_code" +
                               // " AND DCM_LEVEL_ID=3"+
                               // " AND DCM_PAYMENT_LEVEL_ID=4"+
                                " AND CHANNEL_ID=1"+
                                " AND DCM_STATUS_ID=1)";

            String quantityLessThanZero="update SCM_POS_BARCODE_TEMP SET STATUS=2 WHERE QUANTITY < 0";

            DBUtil.executeSQL(posNotexist);

            DBUtil.executeSQL(quantityLessThanZero);

            Vector<BarCodeCaseModel> refusedPOSs=DBUtil.executePreparedSqlQueryMultiValue("SELECT POS_CODE ,STATUS FROM SCM_POS_BARCODE_TEMP WHERE STATUS IS NOT NULL",BarCodeCaseModel.class, con);
            return refusedPOSs;
        } catch (SQLException ex) {
            Logger.getLogger(BarcodeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    
public static Vector<BarCodePOSRequestDataModel> GetRecievedBarcodeForExcelSheet()
{
String query ="UPDATE SCM_POS_BARCODE_TEMP SET CR=(SELECT COUNT(CONTRACT_ID) CR FROM CR_CONTRACT CRC, CR_SHEET CRS"+
                    " WHERE CRC.SHEET_ID=CRS.SHEET_ID"+
                    " AND CRS.SHEET_RECIEVE_DATE >= add_months(trunc(sysdate,'MM'),-2) "+
                    " AND CRS.SHEET_RECIEVE_DATE<= sysdate AND CRS.SHEET_POS_CODE = SCM_POS_BARCODE_TEMP.POS_CODE),"+
                    " SFR=(SELECT COUNT(SIM_ID) SFR FROM SFR_SIM SIM, SFR_SHEET SH,SFR_BATCH SB, GEN_DCM G "+
                    " WHERE SB.BATCH_ID=SH.BATCH_ID AND SIM.SHEET_ID=SH.SHEET_ID "+
                    " AND SB.BATCH_DATE  >= add_months(trunc(sysdate,'MM'),-2) "+
                    " AND SB.BATCH_DATE <= sysdate AND G.DCM_ID=SH.POS_ID AND G.DCM_CODE=SCM_POS_BARCODE_TEMP.POS_CODE), "+
                    " BALANCE=(SELECT NVL(SUM(QUANTITY),0) Balance FROM SCM_BARCODE_POS_REQUEST "+
                    " WHERE  UPDATE_IN >= add_months(trunc(sysdate,'MM'),-2) AND UPDATE_IN <= sysdate "+
                    " AND POS_ID=(SELECT DCM_ID FROM GEN_DCM WHERE "+
                    " DCM_CODE=SCM_POS_BARCODE_TEMP.POS_CODE))"+
                    " WHERE STATUS IS NULL";

DBUtil.executeSQL(query);

String getStaticts="SELECT POS_CODE,BALANCE,SFR,CR,QUANTITY FROM SCM_POS_BARCODE_TEMP WHERE STATUS IS NULL";
Connection con;
        try {
            con = Utility.getConnection();
            
            Vector<BarCodePOSRequestDataModel> BarCodePOSRequestExcel =DBUtil.executePreparedSqlQueryMultiValue(getStaticts,BarCodePOSRequestDataModel.class,con);
            DBUtil.executeSQL("TRUNCATE TABLE SCM_POS_BARCODE_TEMP");

        return BarCodePOSRequestExcel;
        } catch (SQLException ex) {
            Logger.getLogger(BarcodeDAO.class.getName()).log(Level.SEVERE, null, ex);
        return null;
        }


}

    public static int getRecievedBarcodefromSFR(String posCode) {

        Connection con;
        try {
            con = Utility.getConnection();
            String receievedsql = "SELECT COUNT(SIM_ID) Quantity FROM SFR_SIM SIM, SFR_SHEET SH,SFR_BATCH SB, GEN_DCM G "
                    + "WHERE SB.BATCH_ID=SH.BATCH_ID AND SIM.SHEET_ID=SH.SHEET_ID "
                    + " AND SB.BATCH_DATE  >= add_months(trunc(sysdate,'MM'),-2)"
                    + " AND SB.BATCH_DATE <= sysdate AND G.DCM_ID=SH.POS_ID AND G.DCM_CODE='" + posCode + "'";

            int receievedQuantity = DBUtil.executeQuerySingleValueInt(receievedsql, "Quantity", con);

            return receievedQuantity;

        } catch (SQLException ex) {
            Logger.getLogger(BarcodeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public static int getBalanceforPOS(String posCode) {
        try {
            Connection con = Utility.getConnection();

            String quantityBalancequery = "SELECT NVL(SUM(QUANTITY),0) Balance FROM SCM_BARCODE_POS_REQUEST "
                    + "WHERE  UPDATE_IN >= add_months(trunc(sysdate,'MM'),-2) AND UPDATE_IN <= sysdate "
                    + "AND POS_ID=(SELECT DCM_ID FROM GEN_DCM WHERE " +
                    //" DCM_LEVEL_ID=3 "+
                  //  " AND DCM_PAYMENT_LEVEL_ID=4"+
                    //" AND CHANNEL_ID=1"+
                    // Medhat 
                    " CHANNEL_ID=1"+
                    " AND DCM_CODE= '" + posCode + "')";

            int quantityBalance = DBUtil.executeQuerySingleValueInt(quantityBalancequery, "Balance", con);

            return quantityBalance;

        } catch (SQLException ex) {
            Logger.getLogger(BarcodeDAO.class.getName()).log(Level.SEVERE, null, ex);

            return 0;
        }
    }

    public static int getBarcodeStockRemainingQuantity(Connection con) {
        String sqlStatement;
        int quantityReaminingInStock = 0;
        sqlStatement = "SELECT "
                + "NVL((SELECT SUM(QUANTITY) FROM SCM_BARCODE_STOCK BR WHERE BARCODE_STOCK_TYPE_ID=1),0)"
                + "-NVL((SELECT SUM(QUANTITY) FROM SCM_BARCODE_STOCK BR WHERE BARCODE_STOCK_TYPE_ID=2),0)"
                + " AS REMAINING FROM DUAL";
        quantityReaminingInStock = DBUtil.executeQuerySingleValueInt(sqlStatement, "REMAINING", con);
        return quantityReaminingInStock;

    }

    public static void insertNewBarCodeRequest(BarcodePOSRequestModel barcodePOSRequestModel, BarcodeStockModel barcodeStockModel, Connection con) {
        Long requestId=DBUtil.getSequenceNextVal(con,"BARCODEREQUEST_SEQ");
        String insertNewRequestStatement = "INSERT INTO SCM_BARCODE_POS_REQUEST "
                + "(REQUEST_ID,POS_ID, QUANTITY, UPDATE_IN, DCM_USER_ID, USER_ID) VALUES ("+requestId+",?,?,sysdate,?,?)";
        DBUtil.executePreparedStatment(insertNewRequestStatement, con, new Object[]{barcodePOSRequestModel.getPosId(), barcodePOSRequestModel.getQuantity(), barcodePOSRequestModel.getDcmUserId(), barcodePOSRequestModel.getUserId()});

        insertIntoStock(barcodeStockModel, con);

    }
    public static void insertNewBarCodeRequestByExcelSheet(BarcodePOSRequestModel barcodePOSRequestModel,Long requestId ,Connection con) {
        String insertNewRequestStatement = "INSERT INTO SCM_BARCODE_POS_REQUEST "
                + "(REQUEST_ID,POS_ID, QUANTITY, UPDATE_IN, DCM_USER_ID, USER_ID) VALUES ("+requestId+",?,?,sysdate,?,?)";
        DBUtil.executePreparedStatment(insertNewRequestStatement, con, new Object[]{barcodePOSRequestModel.getPosId(), barcodePOSRequestModel.getQuantity(), barcodePOSRequestModel.getDcmUserId(), barcodePOSRequestModel.getUserId()});

    }

    public static void insertIntoStock(BarcodeStockModel barcodeStockModel, Connection con) {
        String insertNewStockStatement = "INSERT INTO SDS.SCM_BARCODE_STOCK "
                + "(QUANTITY, USER_ID, BARCODE_STOCK_TYPE_ID,UPDATE_IN) VALUES (?,?,?,sysdate )";

        DBUtil.executePreparedStatment(insertNewStockStatement, con, new Object[]{barcodeStockModel.getQuantity(), barcodeStockModel.getUserId(), barcodeStockModel.getBarcodeStockTypeId()});
    }

    public static Vector<DCMUserDetailModel> getReps(Connection con) {

        String repQuery = "SELECT USER_ID,USER_FULL_NAME FROM DCM_USER_DETAIL WHERE USER_DETAIL_STATUS_ID=1 ORDER BY LOWER(USER_FULL_NAME) ";

        Vector<DCMUserDetailModel> reps = DBUtil.executeSqlQueryMultiValue(repQuery, DCMUserDetailModel.class, con);
        return reps;
    }

    /*
     * Check if the the  POS is exist , POS Level and ACTIVE
    @return DCM_ID if it is Found, -2 if an exception occurred, -1 if POS not found
     */
    public static int checkPOS(Connection argCon, String posCode) {

        String sqlStatement;
        sqlStatement = "SELECT DCM_ID FROM GEN_DCM WHERE  DCM_CODE=? "/*AND DCM_LEVEL_ID=3*/+" AND DCM_STATUS_ID=1"+
                      // " AND DCM_PAYMENT_LEVEL_ID=4"+
                       " AND CHANNEL_ID=1"  ;
        POSModel posModel = (POSModel) DBUtil.executePreparedSqlQuerySingleValue(sqlStatement, POSModel.class, argCon, new Object[]{posCode});
        if (posModel != null) {
            return posModel.getPOS_ID();
        } else {
            return -1;
        }
    }

    public static Vector<BarcodeStockReportModel> getStockStatictics(Connection con)
    {
        String stockStaticticsquery="SELECT BS.QUANTITY,GP.PERSON_FULL_NAME,BST.NAME AS BARCODE_STOCK_TYPE,BS.BARCODE_STOCK_TYPE_ID,BS.UPDATE_IN " +
                "FROM GEN_PERSON GP,SCM_BARCODE_STOCK BS,SCM_BARCODE_STOCK_TYPE BST " +
                "WHERE BS.USER_ID=GP.PERSON_ID AND BS.BARCODE_STOCK_TYPE_ID=BST.BARCODE_STOCK_TYPE_ID ORDER BY BS.UPDATE_IN DESC";

        Vector<BarcodeStockReportModel> barcodeStockReportStats =new Vector();
        barcodeStockReportStats=DBUtil.executeSqlQueryMultiValue(stockStaticticsquery,BarcodeStockReportModel.class , con);
        return barcodeStockReportStats;


    }
    public static Vector<BarcodePOSRequestModel> getallRequests(Connection con,String rowNum)
    {
//        String query ="SELECT S.REQUEST_ID , "+
//                      " MAX(S.UPDATE_IN) UPDATE_IN "+
//                      " FROM SDS.SCM_BARCODE_POS_REQUEST S GROUP BY REQUEST_ID ORDER BY REQUEST_ID";
        String query = "select * from (select REQUEST_ID , UPDATE_IN ,USER_FULL_NAME,NO_OF_DOWN,Rownum rw from ("+
                "SELECT S.REQUEST_ID , MAX(S.UPDATE_IN) UPDATE_IN ,   DD.USER_FULL_NAME,s.NO_OF_DOWN  "+
                         "FROM SDS.SCM_BARCODE_POS_REQUEST S, DCM_USER_DETAIL DD where s.DCM_USER_ID = DD.USER_ID "+
                         "GROUP BY REQUEST_ID,DD.USER_FULL_NAME,s.NO_OF_DOWN  ORDER BY REQUEST_ID) )where rw > = ('"+rowNum+"'*20)+1 AND rw < = ('"+rowNum+"'+1)*20";

       Vector <BarcodePOSRequestModel> requests = DBUtil.executeSqlQueryMultiValue(query,BarcodePOSRequestModel.class, con);
    
       return requests;
    
    }
    public static Integer getallRequestsCountPages(Connection con)
    {
//        String query ="SELECT S.REQUEST_ID , "+
//                      " MAX(S.UPDATE_IN) UPDATE_IN "+
//                      " FROM SDS.SCM_BARCODE_POS_REQUEST S GROUP BY REQUEST_ID ORDER BY REQUEST_ID";
        String query = "select count(REQUEST_ID)/20 total_pgs from (select REQUEST_ID , UPDATE_IN ,USER_FULL_NAME,NO_OF_DOWN,Rownum rw from ("+
                "SELECT S.REQUEST_ID , MAX(S.UPDATE_IN) UPDATE_IN ,   DD.USER_FULL_NAME,s.NO_OF_DOWN  "+
                         "FROM SDS.SCM_BARCODE_POS_REQUEST S, DCM_USER_DETAIL DD where s.DCM_USER_ID = DD.USER_ID "+
                         "GROUP BY REQUEST_ID,DD.USER_FULL_NAME,s.NO_OF_DOWN  ORDER BY REQUEST_ID) )";
       return DBUtil.executeQuerySingleValueInt(query, "total_pgs", con);

    }
    public static Vector<BarcodePOSRequestModel> getallRequests(Connection con)
    {
//        String query ="SELECT S.REQUEST_ID , "+
//                      " MAX(S.UPDATE_IN) UPDATE_IN "+
//                      " FROM SDS.SCM_BARCODE_POS_REQUEST S GROUP BY REQUEST_ID ORDER BY REQUEST_ID";
        String query = "SELECT S.REQUEST_ID , MAX(S.UPDATE_IN) UPDATE_IN ,   DD.USER_FULL_NAME,s.NO_OF_DOWN  "+
                         "FROM SDS.SCM_BARCODE_POS_REQUEST S, DCM_USER_DETAIL DD where s.DCM_USER_ID = DD.USER_ID "+
                         "GROUP BY REQUEST_ID,DD.USER_FULL_NAME,s.NO_OF_DOWN  ORDER BY REQUEST_ID";

       Vector <BarcodePOSRequestModel> requests = DBUtil.executeSqlQueryMultiValue(query,BarcodePOSRequestModel.class, con);

       return requests;

    }
    public static Vector<STKDistRequestViewerDTO> getallDistRequests(Connection con)
    {
StringBuilder sql = new StringBuilder();
sql.append(" select DR.*,D.DCM_NAME,P.PERSON_FULL_NAME from SCM_STK_DIST_REQUEST DR, GEN_DCM D, GEN_PERSON P ");
sql.append(" where ");
sql.append(" DR.DIST_ID = D.DCM_ID  ");
sql.append(" and  ");
sql.append(" DR.USER_ID = P.PERSON_ID  order by DR.REQUEST_ID ");

       Vector <STKDistRequestViewerDTO> requests = DBUtil.executeSqlQueryMultiValue(sql.toString(),STKDistRequestViewerDTO.class, con);

       return requests;

    }

    public static STKDistRequestViewerDTO getDistRequestsById(Connection con, String requestId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select DR.*,D.DCM_NAME,P.PERSON_FULL_NAME from SCM_STK_DIST_REQUEST DR, GEN_DCM D, GEN_PERSON P ");
        sql.append(" where ");
        sql.append(" DR.DIST_ID = D.DCM_ID  ");
        sql.append(" and  ");
        sql.append(" DR.USER_ID = P.PERSON_ID ");
        sql.append(" and  ");
        sql.append(" DR.REQUEST_ID = '");
        sql.append(requestId);
        sql.append("'");

        return DBUtil.executeSqlQuerySingleValue(sql.toString(), STKDistRequestViewerDTO.class, con);
    }

    public static Vector <BarcodeRequestExcelModel> getExcelData(Connection con,String RequestId)
    {
        String query="SELECT GEN_DCM.DCM_CODE ,SCM_BARCODE_POS_REQUEST.QUANTITY " +
                ", GEN_DCM.DCM_NAME , GEN_DCM.DCM_PAYMENT_LEVEL_ID ,DCM_REGION.REGION_NAME  , " +
                "GEN_PERSON.PERSON_FULL_NAME REP_FULL_NAME, GEN_DCM_LEVEL.DCM_LEVEL_NAME"+
                " FROM SCM_BARCODE_POS_REQUEST,SCM_POS_ASSIGNED_GROUP,SCM_REP_POS_GROUP,DCM_USER "+
                " ,GEN_PERSON,GEN_DCM,DCM_REGION,GEN_DCM_LEVEL,DCM_POS_DETAIL"+
                " WHERE GEN_DCM.DCM_ID = SCM_BARCODE_POS_REQUEST.POS_ID AND "+
                "SCM_POS_ASSIGNED_GROUP.DCM_ID =GEN_DCM.DCM_ID AND  "+
                "SCM_REP_POS_GROUP.\"GROUP_ID\" = SCM_POS_ASSIGNED_GROUP.\"GROUP_ID\" AND "+
                " DCM_USER.DCM_USER_ID =SCM_REP_POS_GROUP.DCM_USER_ID AND "+
                " DCM_USER.USER_ID = GEN_PERSON.PERSON_ID AND "+
                " DCM_POS_DETAIL.POS_ID = GEN_DCM.DCM_ID AND "+
                "DCM_POS_DETAIL.POS_DISTRICT_ID = DCM_REGION.REGION_ID AND "+
                "GEN_DCM_LEVEL.DCM_LEVEL_ID = GEN_DCM.DCM_LEVEL_ID AND "+
                "DCM_POS_DETAIL.FLAGE IS NULL AND "+
                "SCM_BARCODE_POS_REQUEST.REQUEST_ID = "+RequestId;

        Vector <BarcodeRequestExcelModel>  BarcodeRequestExcelModels = DBUtil.executeSqlQueryMultiValue(query,BarcodeRequestExcelModel.class, con);
        System.out.print(query);
        return BarcodeRequestExcelModels;
    }
public static void main(String [] args){

String ss = "10.11.252.67";

if (ss.compareTo("10.11.252.66")!=0 && ss.compareTo("10.11.252.67")!=0 )
{
    System.out.println("invalid");
}
}
}
