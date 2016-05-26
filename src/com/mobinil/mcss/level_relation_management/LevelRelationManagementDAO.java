/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.level_relation_management;

import com.mobinil.mcss.level_relation_management.model.GenLevelZero;
import com.mobinil.mcss.level_relation_management.model.GenWarehouse;
import com.mobinil.mcss.level_relation_management.model.LevelZeroToLevelOne;
import com.mobinil.mcss.level_relation_management.model.XYZBulkEmail;
import com.mobinil.sds.core.system.dataMigration.model.POS_ELG_CHKModel;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
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

/**
 *
 * @author samy
 */
public class LevelRelationManagementDAO {

    public static void truncateTableWarehouseToLevelZeroTemp() {
        String sql = "TRUNCATE TABLE WAREHOUSE_TO_LEVEL_ZERO_TEMP";
        System.out.println("TRUNCATE TABLE WAREHOUSE_TO_LEVEL_ZERO_TEMP");
        try {
            Connection con = Utility.getConnection();
            Statement s = con.createStatement();
            s.executeUpdate(sql);
            Utility.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(LevelRelationManagementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static void truncateTableLevelZeroToLevelOneTemp() {
        String sql = "TRUNCATE TABLE LEVEL_ZERO_TO_LEVEL_ONE_TEMP";
        
        try {
            Connection con = Utility.getConnection();
            Statement s = con.createStatement();
            s.executeUpdate(sql);
            Utility.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(LevelRelationManagementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    

    public static List<String> getTmpWarehouseToLevelZeroData() {
        List<String> list = new ArrayList<String>();

        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select *  FROM WAREHOUSE_TO_LEVEL_ZERO_TEMP");
            while (res.next()) {
                list.add(res.getString("DCM_CODE"));
            }
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static List<String> getTmpLevelZeroToLevelOneData() {
        List<String> list = new ArrayList<String>();

        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select *  FROM LEVEL_ZERO_TO_LEVEL_ONE_TEMP");
            while (res.next()) {
                list.add(res.getString("DCM_CODE"));
            }
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static String ckDcmCodeExist(String DCM_CODE) {
        String q = "SELECT * FROM GEN_DCM where DCM_CODE=?";
        String dcm_id = "";
        try {
            Connection c = Utility.getConnection();
            PreparedStatement stat = c.prepareStatement(q);
            stat.setString(1, DCM_CODE);
            ResultSet res = stat.executeQuery();
            while (res.next()) {
                dcm_id = res.getInt("DCM_ID") + "";
                System.out.println("Existt : " + dcm_id);

            }
            Utility.closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(LevelRelationManagementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Existt");
        return dcm_id;
    }
    
    public static String DcmCodeExist(String DCM_CODE) {
        String q = "SELECT * FROM GEN_DCM where DCM_CODE=? ";
        String dcm_id = "";
        try {
            Connection c = Utility.getConnection();
            PreparedStatement stat = c.prepareStatement(q);
            stat.setString(1, DCM_CODE);
            ResultSet res = stat.executeQuery();
            while (res.next()) {
                dcm_id = res.getInt("DCM_ID") + "";
                System.out.println("Existt : " + dcm_id);

            }
            Utility.closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(LevelRelationManagementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Existt");
        return dcm_id;
    }

    public static void insertWarehouseToLevelZero(String warehouseId, String dcm_id) {
        try {
            String maxStat = "SELECT MAX(id) as maxId FROM WAREHOUSE_TO_LEVEL_ZERO";
            Connection con = Utility.getConnection();
            Statement s = con.createStatement();

            ResultSet res = s.executeQuery(maxStat);

            int maxId = 0;
            while (res.next()) {
                maxId = res.getInt(1);
            }


            String q = "INSERT INTO WAREHOUSE_TO_LEVEL_ZERO values(" + (maxId + 1) + "," + warehouseId + "," + dcm_id + "," + 1 + ")";
            System.out.println("qqqqqqqqq :" + q);
            s.executeUpdate(q);
            Utility.closeConnection(con);

        } catch (SQLException ex) {
            Logger.getLogger(LevelRelationManagementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void insertLevelZeroToLevelOne(String levelZero, String dcm_id) {
        try {
            String maxStat = "SELECT MAX(id) as maxId FROM LEVEL_ZERO_TO_LEVEL_ONE";
            Connection con = Utility.getConnection();
            Statement s = con.createStatement();

            ResultSet res = s.executeQuery(maxStat);

            int maxId = 0;
            while (res.next()) {
                maxId = res.getInt(1);
            }


            String q = "INSERT INTO LEVEL_ZERO_TO_LEVEL_ONE values(" + (maxId + 1) + "," + levelZero + "," + dcm_id + "," + 1 + ")";
            System.out.println("qqqqqqqqq :" + q);
            s.executeUpdate(q);
            Utility.closeConnection(con);

        } catch (SQLException ex) {
            Logger.getLogger(LevelRelationManagementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Vector<GenWarehouse> getWarehouseData() {
        Vector<GenWarehouse> gws = new Vector<GenWarehouse>();
        try {
            String q = "select * from gen_warehouse";
            Connection c = Utility.getConnection();
            Statement stat = c.createStatement();
            ResultSet rs = stat.executeQuery(q);
            while (rs.next()) {
                GenWarehouse gw = new GenWarehouse(rs);
                gws.add(gw);
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(LevelRelationManagementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gws;
    }

    public static Vector<LevelZeroToLevelOne> viewLevelzeroToLevelOneData() {
        Vector<LevelZeroToLevelOne> vector = new Vector<LevelZeroToLevelOne>();
        String q = "select levelzero.id as ID, levelzero.level_zero_id as levelZeroId ,gendcmZero.dcm_name  as levelZeroName  ,levelzero.level_one_id as levelOneDcmCode ,gendcmOne.dcm_name as levelOneName ,levelzero.flag as FLAG from level_zero_to_level_one levelZero inner join gen_dcm genDcmZero on levelzero.level_zero_id=genDcmZero.dcm_id inner join gen_dcm genDcmOne on levelzero.level_one_id = gendcmone.dcm_id order by levelZero.LEVEL_ZERO_ID";
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(q);
            while (rs.next()) {
                LevelZeroToLevelOne levelZeroToLevelOne = new LevelZeroToLevelOne(rs);
                vector.add(levelZeroToLevelOne);
            }
            Utility.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(LevelRelationManagementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;

    }

    public static int editLevelZeroToLevelOne(List<String> ids, Connection con) {
        int status = -1;
        String id = "";
        if (ids.size() > 0) {
            for (String string : ids) {
                id += string + ",";
            }
            id = id.substring(0, id.length() - 1);
        }

        String preQuery = "update level_zero_to_level_one set flag=0";
        String q = "update level_zero_to_level_one set flag=1 where id in (" + id + ")";
        System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ " + q);
        try {
            Statement stat = con.createStatement();
            stat.executeQuery(preQuery);
            if (ids.size() > 0) {
                status = stat.executeUpdate(q);
            }
            Utility.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(LevelRelationManagementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    public static void deleteLevelZeroToLevelOne(String id){
        String q="delete from level_zero_to_level_one where id ="+id;
        try {
            Connection c=Utility.getConnection();
            Statement statement=c.createStatement();
            statement.executeUpdate(q);
            Utility.closeConnection(c);
        } catch (SQLException ex) {
            Logger.getLogger(LevelRelationManagementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public static Vector<GenLevelZero> getLevelZeroData() {
        Vector<GenLevelZero> lz = new Vector<GenLevelZero>();
        try {
            String q = "SELECT dcm_id , dcm_name FROM GEN_DCM WHERE GEN_DCM.DCM_PAYMENT_LEVEL_ID=1 AND DCM_LEVEL_ID=1";
            Connection c = Utility.getConnection();
            Statement stat = c.createStatement();
            ResultSet rs = stat.executeQuery(q);
            while (rs.next()) {
                GenLevelZero glz = new GenLevelZero(rs);
                lz.add(glz);
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(LevelRelationManagementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lz;
    }
     
     //NR - SDS
     public static Vector searchEmailsByFilter(Connection con,
            String senderEmail,
            String posCode ,  String fromDate,
            String toDate)
            throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "SELECT ID,POSCODE, SENDEREMAILADDRESS ,SENDERNAME ,SUBJECT ,CREATIONDATE FROM NEW_XYZ_BULK_EMAIL Where CREATIONDATE >= to_date( '" +fromDate+ "','dd/mm/yyyy') and CREATIONDATE < to_date('"+toDate + "','dd/mm/yyyy')";
        if(posCode.equals("")!=true)
            sqlString+=" and POSCODE ='"+ posCode +"'";
        if(senderEmail.equals("")!=true)
        sqlString+=" and SENDEREMAILADDRESS='"+
                senderEmail + "'";
        System.out.println("Email Search SQL:  " + sqlString);

        ResultSet rs = stmt.executeQuery(sqlString);
        XYZBulkEmail xyzBulkEmail = null;
        Vector emailSearchResults = new Vector();
        while (rs.next()) {
            xyzBulkEmail = new XYZBulkEmail(rs);
            emailSearchResults.add(xyzBulkEmail);
        }
        rs.close();
        stmt.close();
        return emailSearchResults;
    }
     //END - NR -SDS
}
