/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.dao;

import com.mobinil.sds.core.system.scm.model.DistributerSTKDetailsModel;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.core.system.scm.model.DistributerStaticDataModel;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed Adel
 */
public class DistributerSTKDataDAO {

public static Vector<DistributerStaticDataModel> getDistributerStaticData ()
{
    
    String query="SELECT DATA_ID,DATA_NAME,DATA_VALUE FROM SCM_DISTRIBUTER_STATIC_DATA ORDER BY DATA_ID";
    Connection con;
        try {
            con = Utility.getConnection();
            Vector<DistributerStaticDataModel> DistributerStaticData =DBUtil.executeSqlQueryMultiValue(query,DistributerStaticDataModel.class,con);
            return DistributerStaticData;
        } catch (SQLException ex) {
            Logger.getLogger(DistributerSTKDataDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

}




public static Vector<DistributerSTKDetailsModel> getDistributerSTKDetails(String dateFrom,String dateTo)
{
String query="SELECT DCM.DCM_NAME ,DCM.DCM_CODE ,DCM_OWNER.POS_OWNER_NAME,CITY.REGION_NAME CITY_ENGLISH ,DCM.DCM_ADDRESS ,STK_STOCK.STK_NUMBER,STK_OWNER.STK_ASSIGN_DATE"+
" FROM GEN_DCM DCM,DCM_REGION CITY,SCM_STK_OWNER STK_OWNER,SCM_STK_STOCK STK_STOCK,DCM_POS_DETAIL DETAIL,DCM_POS_OWNER DCM_OWNER"+
" WHERE DCM.DCM_ID=STK_OWNER.DCM_ID AND STK_OWNER.STK_ID=STK_STOCK.STK_ID AND DETAIL.POS_CITY_ID=CITY.REGION_ID"+
" AND DETAIL.FLAGE IS NULL "+
" AND DCM.DCM_LEVEL_ID=1"+
" AND DCM.DCM_PAYMENT_LEVEL_ID=1"+
" AND DCM.CHANNEL_ID=1"+
" AND STK_OWNER.DCM_VERIFIED_STATUS_ID=2"+
" AND STK_OWNER.STK_STATUS_ID IN (2,4)"+
" AND DCM.DCM_ID=DETAIL.POS_ID"+
" AND DETAIL.POS_DETAIL_ID=DCM_OWNER.POS_DETAIL_ID"+
" AND STK_OWNER.STK_ASSIGN_DATE BETWEEN TO_DATE('"+dateFrom+"','mm/dd/yyyy') AND TO_DATE('"+dateTo+"','mm/dd/yyyy')";

Connection con;
        try {
            con = Utility.getConnection();
            Vector<DistributerSTKDetailsModel> DistributerSTKData=DBUtil.executeSqlQueryMultiValue(query,DistributerSTKDetailsModel.class,con);

           return DistributerSTKData;
        } catch (SQLException ex) {
            Logger.getLogger(DistributerSTKDataDAO.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        }

}

public static int getValidDistributersForActivation()
{
    String query="SELECT COUNT(SCM_STK_OWNER.DCM_ID) DISTRIBUTERS FROM SCM_STK_OWNER,GEN_DCM"+
                 " WHERE SCM_STK_OWNER.DCM_VERIFIED_STATUS_ID=2"+
                 " AND SCM_STK_OWNER.STK_STATUS_ID IN (2,4)"+
                 " AND GEN_DCM.DCM_ID=SCM_STK_OWNER.DCM_ID"+
                 " AND GEN_DCM.DCM_LEVEL_ID=1"+
                " AND GEN_DCM.DCM_PAYMENT_LEVEL_ID=1"+
                 " AND GEN_DCM.CHANNEL_ID=1"  ;
    Connection con;
        try {
            con = Utility.getConnection();
            int numberOfDistributers=DBUtil.executeQuerySingleValueInt(query,"DISTRIBUTERS",con);
            return numberOfDistributers;
        } catch (SQLException ex) {
            Logger.getLogger(DistributerSTKDataDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
}

public static void activeSTKsForValidDistributers(String dateFrom,String dateTo)
{
  String query=  " UPDATE  SCM_STK_OWNER SET STK_STATUS_ID=4,STK_ACTIVE_DATE=sysdate WHERE DCM_ID IN(SELECT  SCM_STK_OWNER.DCM_ID DISTRIBUTERS FROM SCM_STK_OWNER,GEN_DCM"+
                 " WHERE SCM_STK_OWNER.DCM_VERIFIED_STATUS_ID=2"+
                 " AND SCM_STK_OWNER.STK_STATUS_ID IN (2,4)"+
                 " AND SCM_STK_OWNER.DCM_VERIFICATION_DATE BETWEEN TO_DATE('"+dateFrom+"','mm/dd/yyyy') AND TO_DATE('"+dateTo+"','mm/dd/yyyy')"+
                 " AND GEN_DCM.DCM_ID=SCM_STK_OWNER.DCM_ID"+
                 " AND GEN_DCM.DCM_LEVEL_ID=1"+
                " AND GEN_DCM.DCM_PAYMENT_LEVEL_ID=1"+
                 " AND GEN_DCM.CHANNEL_ID=1)"  ;

DBUtil.executeSQL(query);

}
public static int activeSTKsForValidDistributers(String DCM_Code)
{
   Connection con;
        try {
            con = Utility.getConnection();
            String query=  " UPDATE  SCM_STK_OWNER SET STK_STATUS_ID=4,STK_ACTIVE_DATE=sysdate WHERE DCM_ID ="+
          "(SELECT DCM_ID FROM GEN_DCM WHERE DCM_CODE='"+DCM_Code+"')"+
           " AND SCM_STK_OWNER.DCM_VERIFIED_STATUS_ID=2"+
           " AND SCM_STK_OWNER.STK_STATUS_ID=2"+
          " AND STK_ID IN(SELECT STK_ID FROM SCM_STK_STOCK WHERE STK_DIAL IN " +
          "(SELECT STK_DIAL FROM SCM_STK_DIST_ACTIVE_TEMP WHERE STKCASE IS NULL) )";

DBUtil.executeSQL(query);
int activedSTKS=DBUtil.executeQuerySingleValueInt("SELECT COUNT(STK_DIAL) FROM SCM_STK_DIST_ACTIVE_TEMP WHERE STKCASE IS NULL", query,con);
DBUtil.executeSQL("TRUNCATE TABLE SCM_STK_DIST_ACTIVE_TEMP");
return activedSTKS;
        } catch (SQLException ex) {
            Logger.getLogger(DistributerSTKDataDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
  
}
    public static int activeForValidRows(Statement st, String DCM_Code, String mobileNumber) {



        String query = " UPDATE  SCM_STK_OWNER SET STK_STATUS_ID=4,STK_ACTIVE_DATE=sysdate WHERE DCM_ID ="
                + "(SELECT DCM_ID FROM GEN_DCM WHERE DCM_ID='" + DCM_Code + "')"
                + " AND SCM_STK_OWNER.DCM_VERIFIED_STATUS_ID=2"
                + " AND SCM_STK_OWNER.STK_STATUS_ID=2"
                + " AND STK_ID IN(SELECT STK_ID FROM SCM_STK_STOCK WHERE STK_NUMBER IN "
                + "(SELECT STK_DIAL FROM SCM_UPLOAD_DIST_TEMP WHERE STKCASE IS NULL AND STK_DIAL='" + mobileNumber + "') )";

       return DBUtil.executeSQL(query, st);
    }
    public static int doAfterActivate(Connection con){
    int activedSTKS = DBUtil.executeQuerySingleValueInt("SELECT COUNT(STK_DIAL) STK_DIAL_Count FROM SCM_UPLOAD_DIST_TEMP WHERE STKCASE IS NULL", "STK_DIAL_Count", con);
            DBUtil.executeSQL("TRUNCATE TABLE SCM_UPLOAD_DIST_TEMP", con);
            return activedSTKS;
    }
public static Vector<DistributerSTKDetailsModel> getDistributerSTKDetails(String DCM_Code)
{
String query="SELECT DCM.DCM_NAME ,DCM.DCM_CODE ,DCM_OWNER.POS_OWNER_NAME,CITY.CITY_ENGLISH ,DCM.DCM_ADDRESS ,STK_STOCK.STK_NUMBER,STK_OWNER.STK_ASSIGN_DATE"+
" FROM GEN_DCM DCM,GEN_CITY CITY,SCM_STK_OWNER STK_OWNER,SCM_STK_STOCK STK_STOCK,DCM_POS_DETAIL DETAIL,DCM_POS_OWNER DCM_OWNER"+
" WHERE DCM.DCM_CODE='"+DCM_Code+"'"+
" AND DETAIL.FLAGE IS NULL "+
" AND DCM.DCM_ID=STK_OWNER.DCM_ID AND STK_OWNER.STK_ID=STK_STOCK.STK_ID AND DCM.DCM_CITY_ID=CITY.CITY_CODE"+
" AND DCM.DCM_ID=DETAIL.POS_ID"+
" AND STK_OWNER.DCM_VERIFIED_STATUS_ID=2"+
" AND STK_OWNER.STK_STATUS_ID IN (2,4)"+
" AND DETAIL.POS_DETAIL_ID=DCM_OWNER.POS_DETAIL_ID"+
" AND STK_STOCK.STK_NUMBER IN (SELECT STK_DIAL FROM SCM_STK_DIST_ACTIVE_TEMP WHERE STKCASE IS NULL)";

Connection con;
        try {
            con = Utility.getConnection();
            Vector<DistributerSTKDetailsModel> DistributerSTKData=DBUtil.executeSqlQueryMultiValue(query,DistributerSTKDetailsModel.class,con);

           return DistributerSTKData;
        } catch (SQLException ex) {
            Logger.getLogger(DistributerSTKDataDAO.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        }

}

 public static void insertRequest(Connection con,String code,String filePath){

            StringBuilder sql = new StringBuilder();
            sql.append(" insert into SCM_STK_DIST_ACTIVATION ");
            sql.append(" (FILE_ID, DCM_ID, DATE_ACTIVATION, FILE_DIR) ");
            sql.append(" values  ");
            sql.append(" (SCM_STK_DIST_ACTIVATION_ID_SEQ.nextval,?,sysdate,?) ");
            try {
//                System.out.println("filePath ss "+filePath);
                con.setAutoCommit(false);
                File file = new File(filePath);
                FileInputStream fis = new FileInputStream(file);
                PreparedStatement ps = con.prepareStatement(sql.toString());
                ps.setString(1, code);
                ps.setBinaryStream(2, fis, (int) file.length());
                ps.executeUpdate();
                con.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }




        }

}

