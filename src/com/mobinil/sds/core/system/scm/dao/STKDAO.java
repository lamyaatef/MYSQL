package com.mobinil.sds.core.system.scm.dao;

import com.mobinil.sds.core.system.request.dao.RequestDao;
import com.mobinil.sds.core.system.scm.dto.STKPaymentLevel;
import java.sql.ResultSet;

import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Vector;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.core.system.scm.model.CaseModel;
import com.mobinil.sds.core.system.scm.model.POSModel;
import com.mobinil.sds.core.system.scm.model.STKDistributerStatisticsModel;
import com.mobinil.sds.core.system.scm.model.STKOwnerModel;
import com.mobinil.sds.core.system.scm.model.STKReportModel;
import com.mobinil.sds.core.system.scm.model.STKStatusCase;
import com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey;
import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.apache.tools.ant.taskdefs.Execute;

public class STKDAO {
    
    Connection con = null;
    Statement st = null;
    
    public STKDAO(Connection con) {
        this.con = con;
    }
    
    public STKDAO(Statement st) {
        this.st = st;
    }
    
    public STKDAO() {
    }
    
    
    public int deleteStkNumber(String stkNumber, String userId) {
        int insrtResult = 0;
        StringBuilder query = new StringBuilder("delete from scm_stk_owner where stk_id in (select stk_id from scm_stk_stock where STK_NUMBER = '");
        query.append(stkNumber);
        query.append("')");
        insrtResult = DBUtil.executeSQL(query.toString(), st);
        // if (insrtResult > 0) {
        query = new StringBuilder("delete from scm_stk_stock where stk_number = '");
        query.append(stkNumber);
        query.append("'");
        insrtResult = DBUtil.executeSQL(query.toString(), st);
        query = new StringBuilder("insert into SCM_STK_NUMBER_DEL_LOG values ('");
        query.append(stkNumber);
        query.append("','");
        query.append(userId);
        query.append("',sysdate)");
        DBUtil.executeSQL(query.toString(), st);
        //      }
        return insrtResult;
    }
    private static final String deleteTempSTKQuery = "truncate table  SCM_STK_STOCK_TEMP";
    private static final String deletePOSSTKQuery = "truncate table SCM_STK_POS_TEMP";
    private static final String deleteSTKStatusTempQuery = "delete  SCM_STK_STATUS_TEMP";
    
    public static void insertRequest(Connection con, String code, String qnt, String userId, String filePath) {
        
        StringBuilder sql = new StringBuilder();
        sql.append(" insert into SCM_STK_DIST_REQUEST ");
        sql.append(" (REQUEST_ID, DIST_ID, QUANTITY, UPDATE_IN, USER_ID, NO_OF_DOWN, REQUEST_FILE) ");
        sql.append(" values  ");
        sql.append(" (SCM_STK_DIST_REQUEST_ID_SEQ.nextval,(select DCM_ID from GEN_DCM where DCM_CODE=?),?,sysdate,?,'0',?) ");
        try {
//                System.out.println("filePath ss "+filePath);
            con.setAutoCommit(false);
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            PreparedStatement ps = con.prepareStatement(sql.toString());
            ps.setString(1, code);
            ps.setString(2, qnt);
            ps.setString(3, userId);
            ps.setBinaryStream(4, fis, (int) file.length());
            ps.executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
        
    }
    
    public static ArrayList<Integer> beforeInsertSTK() {
        Connection con = null;
        ArrayList<Integer> rowsArray = new ArrayList<Integer>();
        try {
            con = Utility.getConnection();
            Statement stmt = con.createStatement();
            
            String query = "update SCM_STK_STOCK_TEMP SET exist=1 WHERE STK_NUMBER IN"
                    + "(select STK_NUMBER from SCM_STK_STOCK WHERE STK_STATUS_ID <>3)";
            
            DBUtil.executeSQL(query);
            
            
            
            ResultSet rows = stmt.executeQuery("SELECT ROW_NUM from SCM_STK_STOCK_TEMP WHERE exist=1");
            while (rows.next()) {
                rowsArray.add(rows.getInt("ROW_NUM") + 1);
            }
            DBUtil.close(stmt);
            DBUtil.close(rows);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            rowsArray = null;
        } finally {
            Utility.closeConnection(con);
            return rowsArray;
        }
        
        
    }
    
    public static int InsertSTK(String strUserID, String stockId) {
        Connection con = null;
        int rowcount = 0;
        try {
            con = Utility.getConnection();
            
            String insertQuery = "insert into SCM_STK_STOCK"
                    + "(STK_ID,STK_NUMBER,SERIAL_NUMBER,BOX_NUMBER_ID,STK_STATUS_ID"
                    + ",OWNER_BY,UPDATE_IN,LAST_STATUS,USER_ID,STK_IMPORT_DATE,STOCK_ID)"
                    + "SELECT STK_ID_SEQ.NEXTVAL,STK_NUMBER,SERIAL_NUMBER,BOX_NUMBER_ID,"
                    + "1,1,sysdate,1," + strUserID
                    + " ,sysdate," + stockId + " FROM SCM_STK_STOCK_TEMP where exist is NULL";
            
            
            System.out.println("STKDAO , InsertSTK :" + insertQuery);
            
            rowcount = DBUtil.executeQuerySingleValueInt(
                    "SELECT COUNT(*) AS IMPORTED from SCM_STK_STOCK_TEMP WHERE exist is NULL",
                    "IMPORTED", con);
            
            
            
            
            DBUtil.executeSQL(insertQuery);
            
            DBUtil.executeSQL(deleteTempSTKQuery);
            
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            
        } finally {
            Utility.closeConnection(con);
            return rowcount;
        }
        
    }
    
    public static int deleteSTK(String strUserID, String stockId) {
        try {
            Connection con = Utility.getConnection();
            
            String DeleteSTKQuery = "update SCM_STK_STOCK set STK_STATUS_ID = 3 , UPDATE_IN=sysdate , USER_ID ="
                    + strUserID
                    + "WHERE STK_NUMBER IN(SELECT STK_NUMBER FROM SCM_STK_STOCK_TEMP) and STK_STATUS_ID=1 and stock_id=" + stockId;
            
            DBUtil.executeSQL(DeleteSTKQuery);
            String Deleted = "update SCM_STK_STOCK_TEMP SET exist=0 WHERE STK_NUMBER IN (SELECT STK_NUMBER FROM SCM_STK_STOCK WHERE STK_STATUS_ID = 3 )";
            String stkused = "update SCM_STK_STOCK_TEMP SET exist=01 WHERE STK_NUMBER  IN (SELECT STK_NUMBER FROM SCM_STK_STOCK WHERE STK_STATUS_ID <> 3 )";
            DBUtil.executeSQL(Deleted);
            DBUtil.executeSQL(stkused);
            int rowDeleted = DBUtil.executeQuerySingleValueInt(
                    "select  count(DISTINCT STK_NUMBER) AS DELETED from SCM_STK_STOCK where STK_STATUS_ID =3 and stock_id=" + stockId + " and STK_NUMBER IN(SELECT STK_NUMBER FROM SCM_STK_STOCK_TEMP WHERE exist=0 )",
                    "DELETED", con);
            
            
            
            con.close();
            
            return rowDeleted;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        
    }
    
    public static ArrayList<Integer> getUsedSTKNonDelete() {
        try {
            Connection con = Utility.getConnection();
            Statement stmt = con.createStatement();
            
            ArrayList<Integer> rowsArray = new ArrayList<Integer>();
            
            ResultSet rows = stmt.executeQuery("SELECT ROW_NUM from SCM_STK_STOCK_TEMP WHERE exist =01");
            while (rows.next()) {
                rowsArray.add(rows.getInt("ROW_NUM") + 1);
            }
            
            stmt.close();
            con.close();
            
            return rowsArray;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
        
        
    }
    
    public static ArrayList<Integer> afterdelete() {
        try {
            Connection con = Utility.getConnection();
            Statement stmt = con.createStatement();
            
            ArrayList<Integer> rowsArray = new ArrayList<Integer>();
            
            ResultSet rows = stmt.executeQuery("SELECT ROW_NUM from SCM_STK_STOCK_TEMP WHERE exist IS NULL");
            while (rows.next()) {
                rowsArray.add(rows.getInt("ROW_NUM") + 1);
            }
            DBUtil.executeSQL(deleteTempSTKQuery);
            stmt.close();
            con.close();
            
            return rowsArray;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
        
        
    }
    
    public static Vector<CaseModel> beforeInsertSTK_POS() {
        Vector<CaseModel> Cases = null;
        Connection con = null;
        try {
            
            con = Utility.getConnection();
            // UPDATED BY MEDHAT
            String posHasStk = "update SCM_STK_POS_TEMP SET POSCASE=1 WHERE DCM_ID IN"
                    + "(select DCM_CODE from VW_GEN_DCM_SCM WHERE DCM_ID IN (SELECT DCM_ID FROM SCM_STK_OWNER) and DCM_STATUS_ID <>7 ) AND STK_DIAL IN (SELECT STK_NUMBER FROM SCM_STK_STOCK WHERE STK_STATUS_ID<>3)";
            
            
            
            String stkUsed = "update SCM_STK_POS_TEMP SET STKCASE=1 WHERE STK_DIAL IN"
                    + "(select STK_NUMBER from SCM_STK_STOCK where stock_id=" + SCMInterfaceKey.POS_STOCK_ID + " and STK_ID IN (select STK_ID from SCM_STK_OWNER WHERE DCM_USER_ID IS NULL)AND STK_STATUS_ID<>3)";
            
            
            
            String stkDeleted = "update SCM_STK_POS_TEMP SET STKCASE=2 WHERE STK_DIAL IN" + "(SELECT STK_NUMBER FROM SCM_STK_STOCK WHERE stock_id=" + SCMInterfaceKey.POS_STOCK_ID + " and  STK_STATUS_ID = 3)";
            
            String stkmulti = "update SCM_STK_POS_TEMP SET STKCASE = NULL WHERE STK_DIAL IN (Select STK_NUMBER from SCM_STK_STOCK WHERE stock_id=" + SCMInterfaceKey.POS_STOCK_ID + " and STK_STATUS_ID = 1)";

            //UPDATED BY MEDHAT
            String posDeleted = "update SCM_STK_POS_TEMP SET POSCASE=2 WHERE DCM_ID IN" + "(SELECT DCM_CODE FROM VW_GEN_DCM_SCM WHERE DCM_STATUS_ID =7 "
                    + // " AND DCM_LEVEL_ID=3"+
                    // " AND DCM_PAYMENT_LEVEL_ID=4"+
                    " AND CHANNEL_ID=1)";

            //UPDATED BY MEDHAT
            String posNotexist = "update SCM_STK_POS_TEMP SET POSCASE=3 WHERE DCM_ID NOT IN" + "(SELECT DCM_CODE FROM VW_GEN_DCM_SCM where scm_stk_pos_temp.dcm_id = VW_GEN_DCM_SCM.dcm_code"
                    + // " AND DCM_LEVEL_ID=3"+
                    //  " AND DCM_PAYMENT_LEVEL_ID=4"+
                    " AND CHANNEL_ID=1)";
            
            
            String stkNotexist = "update SCM_STK_POS_TEMP SET STKCASE=3 WHERE STK_DIAL NOT IN" + "(SELECT STK_NUMBER FROM SCM_STK_STOCK)";
            System.err.println("issssssssssss  " + stkNotexist);
            // UPDATED BY MEDHAT
            String isPOS = "update SCM_STK_POS_TEMP SET POSCASE=4 WHERE DCM_ID IN"
                    + "(Select DCM_CODE from GEN_DCM where DCM_LEVEL_ID <> 3 AND DCM_LEVEL_ID <>2"
                    + //    " AND DCM_PAYMENT_LEVEL_ID<>4"+
                    " AND CHANNEL_ID<>1"
                    + ")";
            
            
            
            DBUtil.executeSQL(posHasStk, con);
            DBUtil.executeSQL(stkDeleted, con);
            DBUtil.executeSQL(stkmulti, con);
            DBUtil.executeSQL(stkUsed, con);
            DBUtil.executeSQL(posDeleted, con);
            DBUtil.executeSQL(isPOS, con);
            DBUtil.executeSQL(posNotexist, con);
            DBUtil.executeSQL(stkNotexist, con);
            
            String duplicatePOS = "UPDATE SCM_STK_POS_TEMP A SET POSCASE=1  WHERE a.rowid >ANY"
                    + "(SELECT B.rowid FROM SCM_STK_POS_TEMP B WHERE A.DCM_ID = B.DCM_ID AND B.POSCASE IS NULL AND B.STKCASE IS NULL)AND A.POSCASE IS NULL AND A.STKCASE IS NULL";
            
            String duplicateSTK = "UPDATE SCM_STK_POS_TEMP A SET STKCASE=1  WHERE a.rowid >ANY"
                    + "(SELECT B.rowid FROM SCM_STK_POS_TEMP B WHERE A.DCM_ID = B.DCM_ID AND B.POSCASE IS NULL AND B.STKCASE IS NULL)AND A.POSCASE IS NULL AND A.STKCASE IS NULL";
            
            String sqlIsPOSStock = "UPDATE SCM_STK_POS_TEMP A SET STKCASE=10 where STK_DIAL NOT IN (SELECT STK_NUMBER FROM SCM_STK_STOCK WHERE stock_id=" + SCMInterfaceKey.POS_STOCK_ID + ")";
            
            DBUtil.executeSQL(duplicatePOS, con);
            DBUtil.executeSQL(duplicateSTK, con);
            DBUtil.executeSQL(sqlIsPOSStock, con);
            
            
            Cases = new Vector<CaseModel>();
            
            
            Cases = DBUtil.executeSqlQueryMultiValue("SELECT * FROM SCM_STK_POS_TEMP WHERE POSCASE IS NOT NULL OR STKCASE IS NOT NULL", CaseModel.class, con);
            
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            Utility.closeConnection(con);
        }
        
        return Cases;
        
    }
    
    public static int InsertSTK_POS(String strUserID) {
        try {
            Connection con = Utility.getConnection();
            int insertedRows = 0;
            
            String insertquery = "INSERT INTO SDS.SCM_STK_OWNER "
                    + "(STK_ID, DCM_ID, DCM_USER_ID,USER_ID, UPDATED_IN, DCM_VERIFIED_STATUS_ID,IQRAR_RECEVING_STATUS_ID,STK_STATUS_ID)"
                    + "Select S.STK_ID,D.DCM_ID,0," + strUserID + ",SYSDATE,1,1,2 "
                    + "from SCM_STK_POS_TEMP T , SCM_STK_STOCK S , GEN_DCM D where T.STK_DIAL=S.STK_NUMBER and POSCASE IS NULL and STKCASE IS NULL and T.DCM_ID=D.DCM_CODE ";
            
            String updatequery = "update SCM_STK_STOCK set STK_STATUS_ID =2 , STK_ASSIGN_DATE = sysdate ,UPDATE_IN=sysdate where  STK_ID IN (SELECT STK_ID from SCM_STK_POS_TEMP T JOIN SCM_STK_STOCK S ON (T.STK_DIAL=S.STK_NUMBER)and POSCASE IS NULL and STKCASE IS NULL) and STK_STATUS_ID <>3";
            
            
            insertedRows = DBUtil.executeQuerySingleValueInt("Select count(*) AS ROWS_NUM from SCM_STK_POS_TEMP"
                    + " WHERE POSCASE IS NULL and STKCASE IS NULL ", "ROWS_NUM", con);
            
            
            System.out.println("insertquery iss " + insertquery);
            System.out.println("updatequery is " + updatequery);
            System.out.println("deletePOSSTKQuery is " + deletePOSSTKQuery);
            System.out.println("insertedRows iss " + insertedRows);
            DBUtil.executeSQL(insertquery);
            DBUtil.executeSQL(updatequery);
            DBUtil.executeSQL(deletePOSSTKQuery);
            return insertedRows;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
        
        
        
    }
    
    public static String beforeInsertFieldSTK_POS(String posCode, String stkDial) {
        
        Connection con;
        try {
            con = Utility.getConnection();
            
            String posHasStk = "Select DCM_ID from SCM_STK_OWNER where DCM_ID =(Select DCM_ID FROM GEN_DCM WHERE DCM_CODE = " + "'" + posCode + "'" + ")";
            
            System.out.println("the query for has STK " + posHasStk);
            
            
            String stkUsed = "Select STK_ID from SCM_STK_OWNER where STK_ID="
                    + "(Select STK_ID from SCM_STK_STOCK where stock_id =" + SCMInterfaceKey.POS_STOCK_ID + " and STK_NUMBER='" + stkDial + "' and STK_STATUS_ID <> 3 )";
            String stkDeleted = "Select STK_ID from SCM_STK_STOCK where stock_id =" + SCMInterfaceKey.POS_STOCK_ID + " and STK_NUMBER ='" + stkDial + "' and STK_STATUS_ID = 3";
            String posDeleted = "Select DCM_ID from GEN_DCM where DCM_CODE=" + "'" + posCode + "'" + " and DCM_STATUS_ID = 7";
            //UPDATED BY MEDHAT
            String posNotexist = "Select DCM_ID from VW_GEN_DCM_SCM where DCM_CODE = " + "'" + posCode + "'" + " "
                    + // " AND DCM_PAYMENT_LEVEL_ID=4"+
                    " AND CHANNEL_ID=1";
            
            System.out.println("the query isssssss " + posNotexist);
            String stkNotexist = "Select STK_ID from SCM_STK_STOCK where stock_id =" + SCMInterfaceKey.POS_STOCK_ID + " and STK_NUMBER = '" + stkDial + "'";
            // UPDATED BY MEDHAT
            String isPOS = "Select DCM_ID from VW_GEN_DCM_SCM where DCM_CODE=" + "'" + posCode + "'" + " "
                    + //  " AND DCM_PAYMENT_LEVEL_ID=4"+
                    " AND CHANNEL_ID=1";
            System.out.println("the second query isssssss " + isPOS);
            String stkmulticase = "Select STK_ID from SCM_STK_STOCK where stock_id =" + SCMInterfaceKey.POS_STOCK_ID + " and STK_NUMBER ='" + stkDial + "' and STK_STATUS_ID = 1";
            
            System.out.println("the query for stkmulticase:" + stkmulticase);
            
            String Msg = "";
            String Msgflag = "";
            boolean DCMused = DBUtil.executeSQLExistCheck(posHasStk);
            boolean STKused = DBUtil.executeSQLExistCheck(stkUsed);
            boolean STKdeleted = DBUtil.executeSQLExistCheck(stkDeleted);
            boolean stkmulti = DBUtil.executeSQLExistCheck(stkmulticase);
            boolean DCMdeleted = DBUtil.executeSQLExistCheck(posDeleted);
            boolean posexist = DBUtil.executeSQLExistCheck(posNotexist);
            boolean stkexist = DBUtil.executeSQLExistCheck(stkNotexist);
            boolean POS = DBUtil.executeSQLExistCheck(isPOS);
            
            
            if (!posexist) {
                Msg = "This POS is not exist";
                
            } else if (!POS) {
                Msg = "This is not a POS";
                
            } else if (DCMdeleted) {
                
                Msg = "This POS is closed";
                
            } else if (DCMused) {
                Msg = "This POS already has STK";
            }
            
            if (Msg != "") {
                Msgflag = " & ";
            }
            if (!stkexist) {
                
                Msg = Msg + Msgflag + "This STK is not exist";
                
            } else if (STKdeleted) {
                if (!stkmulti) {
                    Msg = Msg + Msgflag + "This STK is deleted";
                }
                
            } else if (STKused) {
                Msg = Msg + Msgflag + "This STK already used by another POS";
                
            }
            
            
            return Msg;
            
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        
    }
    
    public static String getSTKIDFromDial(String STKDial) {
        String stkID = DBUtil.executeQuerySingleValueString("Select STK_ID from SCM_STK_STOCK "
                + "where stock_id =" + SCMInterfaceKey.POS_STOCK_ID + " and STK_NUMBER ='" + STKDial + "'", "STK_ID");
        return stkID;
    }
    
    public static void insertPOSSTKQuery(String stkID, String posCode, String strUserID) {
        Connection con;
        try {
            con = Utility.getConnection();
            int DCM_ID = DBUtil.executeQuerySingleValueInt("select DCM_ID from GEN_DCM where DCM_CODE=" + "'" + posCode + "'", "DCM_ID", con);
            String insertquery = "INSERT INTO SDS.SCM_STK_OWNER "
                    + "(STK_ID, DCM_ID, DCM_USER_ID,USER_ID, UPDATED_IN, DCM_VERIFIED_STATUS_ID,IQRAR_RECEVING_STATUS_ID,STK_STATUS_ID)"
                    + "values(" + stkID + "," + DCM_ID + ",0," + strUserID + ",SYSDATE,1,1,2)";
            String updatequery = "update SCM_STK_STOCK set STK_STATUS_ID=2 , UPDATE_IN = sysdate , STK_ASSIGN_DATE = sysdate  where stock_id =" + SCMInterfaceKey.POS_STOCK_ID + " and STK_ID=" + stkID + "";
            
            DBUtil.executeSQL(insertquery);
            DBUtil.executeSQL(updatequery);
        } catch (Exception ex) {
            Logger.getLogger(STKDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public static int InStockSTKcount(Connection con, String stock_id) {
        int currentStock = DBUtil.executeQuerySingleValueInt("Select count(STK_ID)AS InStock from SCM_STK_STOCK where STK_STATUS_ID=1 and stock_id=" + stock_id, "InStock", con);
        return currentStock;
    }
    
    public static boolean isPOSDeleted(String distCode) {
        String posDeleted = "Select DCM_ID from GEN_DCM where DCM_CODE=" + "'" + distCode + "'" + " AND DCM_STATUS_ID = 7"
                + // " AND DCM_PAYMENT_LEVEL_ID=4"+
                " AND CHANNEL_ID=1";
        boolean DCMdeleted = DBUtil.executeSQLExistCheck(posDeleted);
        
        
        return DCMdeleted;
    }
    
    public static boolean isPOSExist(String distCode) {
        //UPDATED BY MEDHAT
        String posNotexist = "Select DCM_ID from VW_GEN_DCM_SCM where DCM_CODE = " + "'" + distCode + "'" + " "
                + //  " AND DCM_PAYMENT_LEVEL_ID=4"+
                " AND CHANNEL_ID=1";
        boolean posexist = DBUtil.executeSQLExistCheck(posNotexist);
        return posexist;
    }
    
    public static boolean isDisDeleted(String distCode) {
        String posDeleted = "Select DCM_ID from GEN_DCM where DCM_CODE=" + "'" + distCode + "'" + " AND DCM_STATUS_ID = 7"
                + " AND DCM_PAYMENT_LEVEL_ID=1"
                + " AND CHANNEL_ID=1"
                + " AND DCM_LEVEL_ID=1";
        
        boolean DCMdeleted = DBUtil.executeSQLExistCheck(posDeleted);
        
        
        return DCMdeleted;
    }
    
    public static boolean isDistExist(String distCode) {
        String posNotexist = "Select DCM_ID from GEN_DCM where DCM_CODE = " + "'" + distCode + "'" + " AND DCM_LEVEL_ID=1"
                + " AND DCM_PAYMENT_LEVEL_ID=1"
                + " AND CHANNEL_ID=1";
        boolean posexist = DBUtil.executeSQLExistCheck(posNotexist);
        return posexist;
    }
    
    public static boolean isPOsDistributer(String distCode) {
        String isDistrib = "Select DCM_ID from GEN_DCM where DCM_CODE = " + "'" + distCode + "'" + " and DCM_LEVEL_ID =1"
                + " AND DCM_PAYMENT_LEVEL_ID=1"
                + " AND CHANNEL_ID=1"
                + " AND DCM_STATUS_ID =1";
        boolean isdistributer = DBUtil.executeSQLExistCheck(isDistrib);
        return isdistributer;
    }
    
    public static void insertSTKQuantity(Connection con, String distCode, String strUserID, String stkQuantity, String stock_id) {
        
        
        
        int DCM_ID = DBUtil.executeQuerySingleValueInt("select DCM_ID from GEN_DCM where DCM_CODE=" + "'" + distCode + "'" + "", "DCM_ID", con);
        String insertquery = "INSERT INTO SDS.SCM_STK_OWNER "
                + "(STK_ID, DCM_ID, DCM_USER_ID,USER_ID, UPDATED_IN, DCM_VERIFIED_STATUS_ID,IQRAR_RECEVING_STATUS_ID,STK_STATUS_ID,STK_ASSIGN_DATE)"
                + "select STK_ID," + DCM_ID + ",0," + strUserID + ",sysdate,1,1,2,sysdate from SCM_STK_STOCK where STK_STATUS_ID =1 and stock_id=" + stock_id + " and rownum<=" + stkQuantity + "";
        DBUtil.executeSQL(insertquery, con);
        
        
        
        
    }
    
    public static void updateSTK_to_ASSIGN_Distributer(Connection con, String stkQuantity, String stockId) {
        
        String updatequery = "update SCM_STK_STOCK set STK_STATUS_ID=2, STK_ASSIGN_DATE=sysdate, UPDATE_IN=sysdate where stock_id=" + stockId + " and STK_ID IN (select STK_ID from SCM_STK_STOCK where STK_STATUS_ID =1 and rownum<=" + stkQuantity + ")";
        DBUtil.executeSQL(updatequery, con);
    }
    
    public static HashMap<String, String> getExcelForDistributer(Connection con, String stkQuantity, String stockId) {
        String excelData = "select rownum,concat(STK_Number,concat('_',SERIAL_NUMBER)) excelFields from SCM_STK_STOCK where STK_STATUS_ID =1 and stock_id=" + stockId + " and rownum<=" + stkQuantity;
        HashMap<String, String> excelMap = DBUtil.getMap(con, excelData);
        return excelMap;
        
        
    }
    
    public static int getPOSStatusTotalPages(String POScode, String IqrarStatus, String CbillStatus, String StatusDateFrom, String StatusDateTo) {
        Connection con;
        try {
            con = Utility.getConnection();
            
            String POSStatusQuery = "Select ceil(count (DCM.DCM_ID)/20) count"
                    + " from SCM_STK_OWNER OWNER ,SCM_STK_STOCK STOCK , VW_GEN_DCM_SCM DCM"
                    //UPDATED BY MEDHAT
                    + " where (OWNER.DCM_ID=DCM.DCM_ID) and (OWNER.STK_ID=STOCK.STK_ID) "
                    + // " AND DCM.DCM_PAYMENT_LEVEL_ID=4"+
                    " AND DCM.CHANNEL_ID=1";
            
            if (!POScode.equals(null) && !POScode.equals("")) {
                POSStatusQuery = POSStatusQuery + " and DCM.DCM_CODE='" + POScode + "'";
            }
            if (!IqrarStatus.equals(null) && !IqrarStatus.equals("")) {
                POSStatusQuery = POSStatusQuery + " and IQRAR_RECEVING_STATUS_ID=" + IqrarStatus;
            }
            if (!CbillStatus.equals(null) && !CbillStatus.equals("")) {
                POSStatusQuery = POSStatusQuery + " and DCM_VERIFIED_STATUS_ID=" + CbillStatus;
            }
            if (!StatusDateFrom.equals("*")) {
                POSStatusQuery = POSStatusQuery + " and TO_DATE(TO_CHAR (UPDATED_IN,'dd/mm/yyyy'),'dd/mm/yyyy')>=TO_DATE('" + StatusDateFrom + "','mm/dd/yyyy')";
            }
            if (!StatusDateTo.equals("*")) {
                POSStatusQuery = POSStatusQuery + " and TO_DATE(TO_CHAR (UPDATED_IN,'dd/mm/yyyy'),'dd/mm/yyyy')<=TO_DATE('" + StatusDateTo + "','mm/dd/yyyy')";
            }
            
            int totalPages = 0;
            
            totalPages = DBUtil.executeQuerySingleValueInt(POSStatusQuery, "count", con);
            
            return totalPages;
            
        } catch (Exception ex) {
            Logger.getLogger(STKDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public static Vector<POSModel> getPOSStatusSeperate(String POScode, String IqrarStatus, String CbillStatus, String StatusDateFrom, String StatusDateTo, String rowNum) {
        Connection con;
        try {
            con = Utility.getConnection();
            
            String POSStatusQuery = "SELECT * FROM (Select ROWNUM as row_num , DCM.DCM_ID,DCM.DCM_CODE,DCM.DCM_NAME,STOCK.STK_NUMBER,OWNER.DCM_VERIFIED_STATUS_ID CBILL_STATUS,OWNER.IQRAR_RECEVING_STATUS_ID IQRAR_STATUS,OWNER.STK_STATUS_ID"
                    + " from SCM_STK_OWNER OWNER ,SCM_STK_STOCK STOCK , VW_GEN_DCM_SCM DCM"
                    //UPDATED BY MEDHAT
                    + " where (OWNER.DCM_ID=DCM.DCM_ID) and (OWNER.STK_ID=STOCK.STK_ID) "
                    + //" AND DCM.DCM_PAYMENT_LEVEL_ID=4"+
                    " AND DCM.CHANNEL_ID=1";
            
            if (!POScode.equals(null) && !POScode.equals("")) {
                POSStatusQuery = POSStatusQuery + " and DCM.DCM_CODE='" + POScode + "'";
            }
            if (!IqrarStatus.equals(null) && !IqrarStatus.equals("")) {
                POSStatusQuery = POSStatusQuery + " and IQRAR_RECEVING_STATUS_ID=" + IqrarStatus;
            }
            if (!CbillStatus.equals(null) && !CbillStatus.equals("")) {
                POSStatusQuery = POSStatusQuery + " and DCM_VERIFIED_STATUS_ID=" + CbillStatus;
            }
            if (!StatusDateFrom.equals("*")) {
                POSStatusQuery = POSStatusQuery + " and TO_DATE(TO_CHAR (UPDATED_IN,'dd/mm/yyyy'),'dd/mm/yyyy')>=TO_DATE('" + StatusDateFrom + "','mm/dd/yyyy')";
            }
            if (!StatusDateTo.equals("*")) {
                POSStatusQuery = POSStatusQuery + " and TO_DATE(TO_CHAR (UPDATED_IN,'dd/mm/yyyy'),'dd/mm/yyyy')<=TO_DATE('" + StatusDateTo + "','mm/dd/yyyy')";
            }
            
            Vector<POSModel> POSs = new Vector<POSModel>();
            
            POSStatusQuery = POSStatusQuery + ")WHERE row_num > = ('" + rowNum + "'*20)+1 AND row_num < = ('" + rowNum + "'+1)*20  ORDER BY ROWNUM ";
            
            POSs = DBUtil.executePreparedSqlQueryMultiValue(POSStatusQuery, POSModel.class, con);
            
            return POSs;
        } catch (Exception ex) {
            Logger.getLogger(STKDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public static Vector<POSModel> getPOSStatus(String POScode, String IqrarStatus, String CbillStatus, String StatusDateFrom, String StatusDateTo) {
        Connection con;
        try {
            con = Utility.getConnection();
            
            String POSStatusQuery = "Select DCM.DCM_ID,DCM.DCM_CODE,DCM.DCM_NAME,STOCK.STK_NUMBER,OWNER.DCM_VERIFIED_STATUS_ID CBILL_STATUS,OWNER.IQRAR_RECEVING_STATUS_ID IQRAR_STATUS,OWNER.STK_STATUS_ID"
                    + " from SCM_STK_OWNER OWNER ,SCM_STK_STOCK STOCK , VW_GEN_DCM_SCM DCM"
                    //UPDATED BY MEDHAT
                    + " where (OWNER.DCM_ID=DCM.DCM_ID) and (OWNER.STK_ID=STOCK.STK_ID) "
                    + // " AND DCM.DCM_PAYMENT_LEVEL_ID=4"+
                    " AND DCM.CHANNEL_ID=1";
            
            if (!POScode.equals(null) && !POScode.equals("")) {
                POSStatusQuery = POSStatusQuery + " and DCM.DCM_CODE='" + POScode + "'";
            }
            if (!IqrarStatus.equals(null) && !IqrarStatus.equals("")) {
                POSStatusQuery = POSStatusQuery + " and IQRAR_RECEVING_STATUS_ID=" + IqrarStatus;
            }
            if (!CbillStatus.equals(null) && !CbillStatus.equals("")) {
                POSStatusQuery = POSStatusQuery + " and DCM_VERIFIED_STATUS_ID=" + CbillStatus;
            }
            if (!StatusDateFrom.equals("*")) {
                POSStatusQuery = POSStatusQuery + " and TO_DATE(TO_CHAR (UPDATED_IN,'dd/mm/yyyy'),'dd/mm/yyyy')>=TO_DATE('" + StatusDateFrom + "','mm/dd/yyyy')";
            }
            if (!StatusDateTo.equals("*")) {
                POSStatusQuery = POSStatusQuery + " and TO_DATE(TO_CHAR (UPDATED_IN,'dd/mm/yyyy'),'dd/mm/yyyy')<=TO_DATE('" + StatusDateTo + "','mm/dd/yyyy')";
            }
            
            Vector<POSModel> POSs = new Vector<POSModel>();
            
            POSs = DBUtil.executePreparedSqlQueryMultiValue(POSStatusQuery, POSModel.class, con);
            return POSs;
        } catch (Exception ex) {
            Logger.getLogger(STKDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public static Vector<POSModel> getNonActiveValidPOS() {
        
        Connection con;
        try {
            con = Utility.getConnection();
            
            
            String POSStatusQuery = "Select DCM.DCM_ID,DCM.DCM_CODE,DCM.DCM_NAME,STOCK.STK_NUMBER,OWNER.DCM_VERIFIED_STATUS_ID CBILL_STATUS,OWNER.IQRAR_RECEVING_STATUS_ID IQRAR_STATUS"
                    + " from SCM_STK_OWNER OWNER ,SCM_STK_STOCK STOCK , VW_GEN_DCM_SCM DCM"
                    + " where (OWNER.DCM_ID=DCM.DCM_ID) and (OWNER.STK_ID=STOCK.STK_ID)"
                    + " and OWNER.IQRAR_RECEVING_STATUS_ID=2 and OWNER.DCM_VERIFIED_STATUS_ID=2"
                    + //UPDATED BY MEDHAT
                    " and OWNER.STK_STATUS_ID<>4 and OWNER.STK_STATUS_ID<>7 and "
                    + //" AND DCM.DCM_PAYMENT_LEVEL_ID=4"+
                    " AND DCM.CHANNEL_ID=1";
            
            Vector<POSModel> POSs = new Vector<POSModel>();
            
            POSs = DBUtil.executePreparedSqlQueryMultiValue(POSStatusQuery, POSModel.class, con);
            return POSs;
        } catch (Exception ex) {
            Logger.getLogger(STKDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public static Vector<POSModel> activeSTKforPOS(Vector<POSModel> POSs) throws Exception {
        Vector<POSModel> activePOSs = new Vector<POSModel>();
        Connection con = Utility.getConnection();
        for (int i = 0; i < POSs.size(); i++) {
            DBUtil.executeSQL("UPDATE SCM_STK_OWNER SET STK_STATUS_ID=4 , STK_ACTIVE_DATE=sysdate WHERE IQRAR_RECEVING_STATUS_ID=2 and STK_STATUS_ID<>4 and STK_STATUS_ID<>3 and DCM_VERIFIED_STATUS_ID=2 and DCM_ID IN (SELECT DCM_ID FROM GEN_DCM "
                    + "WHERE DCM_CODE='" + POSs.get(i).getPOS_Code() + "')");
        }
        for (int i = 0; i < POSs.size(); i++) {
            if (POSs.get(i).getSTK_Status() != 4 && POSs.get(i).getSTK_Status() != 3 && POSs.get(i).getCbill_Status() == 2 && POSs.get(i).getIqrar_Status() == 2) {
                activePOSs.add(POSs.get(i));
                RequestDao.updatePaymentStatus(con, "1", Integer.toString(POSs.get(i).getPOS_ID()));
            }
        }
        
        
        
        return activePOSs;
    }
    
    public static Vector<STKReportModel> getAssignedSTKfromStock(Connection conn, String stockId) {
        
        
        Vector<STKReportModel> vectorOfAssignedStk = new Vector();
        
        String sqlStatement = "SELECT D.STK_ASSIGN_DATE \"DATE\",G.PERSON_FULL_NAME \"USER\",D.STK_COUNT \"COUNT\" "
                + "FROM(SELECT STK_ASSIGN_DATE,USER_ID,COUNT(STK_ID) STK_COUNT,STK_STATUS_ID FROM SCM_STK_STOCK where stock_id=" + stockId
                + " GROUP BY STK_ASSIGN_DATE,USER_ID,STK_STATUS_ID HAVING STK_ASSIGN_DATE IS NOT NULL and STK_STATUS_ID <>3) D, GEN_PERSON G "
                + " WHERE D.USER_ID=G.PERSON_ID order by D.STK_ASSIGN_DATE desc";
        vectorOfAssignedStk = DBUtil.executeSqlQueryMultiValue(sqlStatement, STKReportModel.class, conn);
        return vectorOfAssignedStk;
        
        
    }
    
    public static Vector<STKReportModel> getimportedstkfromStock(Connection con, String stockId) {
        
        
        Vector<STKReportModel> vectorofImportedStk = new Vector<STKReportModel>();
        
        String sqlStatement = "SELECT D.STK_IMPORT_DATE \"DATE\",G.PERSON_FULL_NAME \"USER\",D.STK_COUNT \"COUNT\" "
                + "FROM (SELECT STK_IMPORT_DATE,USER_ID,COUNT(STK_ID) STK_COUNT,STK_STATUS_ID "
                + "FROM SCM_STK_STOCK where stock_id=" + stockId + " GROUP BY STK_IMPORT_DATE,USER_ID,STK_STATUS_ID HAVING STK_STATUS_ID <>3) D, "
                + "GEN_PERSON G WHERE D.USER_ID=G.PERSON_ID order by D.STK_IMPORT_DATE desc";
        vectorofImportedStk = DBUtil.executeSqlQueryMultiValue(sqlStatement, STKReportModel.class, con);
        return vectorofImportedStk;
        
        
        
        
        
        
    }
    
    public static int getRemainingSTKFromStock(Connection con, String stockId) {
        String sqlStatement = "SELECT COUNT(STK_ID) \"COUNT\" FROM SCM_STK_STOCK WHERE  stock_id=" + stockId + " and STK_ASSIGN_DATE IS NULL AND STK_STATUS_ID =1";
        return DBUtil.executeQuerySingleValueInt(sqlStatement, "COUNT", con);
    }
    
    public static STKOwnerModel getSTKStatus(String STKDial) {
        Connection con;
        try {
            con = Utility.getConnection();
            String query = "SELECT STATUS.NAME STK_STATUS_NAME ,SCM_STK_OWNER.REASON REASON FROM SCM_STK_OWNER,SCM_STK_STATUS STATUS WHERE STK_ID=(SELECT STK_ID FROM SCM_STK_STOCK WHERE STK_NUMBER=" + "'" + STKDial + "'" + ")"
                    + "AND STATUS.STK_STATUS_ID=SCM_STK_OWNER.STK_STATUS_ID";
            STKOwnerModel STKStatus = null;
            STKStatus = DBUtil.executeSqlQuerySingleValue(query, STKOwnerModel.class, con);
            
            System.out.println("getSTKStatus query:" + query);
            
            return STKStatus;
        } catch (Exception ex) {
            Logger.getLogger(STKDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
    }
    
    public static void changeSTKStatus(String STKDial, String STKStatus, String userId, String Reason) {
        
        String query = "UPDATE SCM_STK_OWNER SET STK_STATUS_ID =" + STKStatus + " ,USER_ID=" + userId + " ,REASON='" + Reason + "',UPDATED_IN=sysdate WHERE STK_ID= (SELECT STK_ID FROM SCM_STK_STOCK WHERE STK_NUMBER= " + "'" + STKDial + "'" + ")";
        String dcmId = DBUtil.executeQuerySingleValueString("SELECT DCM_ID FROM SCM_STK_OWNER WHERE STK_ID= (SELECT STK_ID FROM SCM_STK_STOCK WHERE STK_NUMBER= " + "'" + STKDial + "'" + ")", "DCM_ID");
        if (STKStatus.equals("4")) {
            Connection con;
            try {
                
                con = Utility.getConnection();
                RequestDao.updatePaymentStatus(con, "1", dcmId);
            } catch (Exception ex) {
                Logger.getLogger(STKDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        DBUtil.executeSQL(query);
    }
    
    public static Vector<STKStatusCase> beforeSTKExcelChange() {
//change by hagry
/*
         * String query="UPDATE SCM_STK_STATUS_TEMP SET EXIST=1 WHERE STK_DIAL
         * NOT IN " + "(SELECT STK_NUMBER FROM SCM_STK_STOCK WHERE STK_ID IN " +
         * "(SELECT STK_ID FROM SCM_STK_OWNER))";
         */
        String query = "   UPDATE SCM_STK_STATUS_TEMP SET EXIST=1  "
                + " WHERE STK_DIAL NOT IN (SELECT STK_NUMBER FROM SCM_STK_STOCK, SCM_STK_OWNER  "
                + " WHERE SCM_STK_STOCK.STK_NUMBER = stk_dial AND   SCM_STK_STOCK.STK_ID = SCM_STK_OWNER.stk_id ) ";
        
        System.out.println("query =" + query);
        DBUtil.executeSQL(query);
        System.out.println("query =" + query);
        
        String refuesdSTK = "SELECT STK_DIAL ,EXIST  FROM SCM_STK_STATUS_TEMP WHERE EXIST=1";
        
        System.out.println(refuesdSTK);
        Connection con;
        try {
            con = Utility.getConnection();
            Vector<STKStatusCase> cases = new Stack<STKStatusCase>();
            cases = DBUtil.executeSqlQueryMultiValue(refuesdSTK, STKStatusCase.class, con);
            
            return cases;
        } catch (Exception ex) {
            Logger.getLogger(STKDAO.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return null;
        }
        
        
    }
    
    public static int STKExcelChange(String STKStatus, String userId, String Reason) {
        String query = "UPDATE SCM_STK_OWNER SET STK_STATUS_ID=" + STKStatus
                + ",USER_ID =" + userId + ",REASON='" + Reason + "',UPDATED_IN=sysdate WHERE STK_ID IN (SELECT STK_ID FROM SCM_STK_STOCK WHERE STK_NUMBER IN (SELECT STK_DIAL FROM SCM_STK_STATUS_TEMP WHERE EXIST IS NULL))";
        
        DBUtil.executeSQL(query);
        
        String STKCOUNT = "SELECT COUNT(*)STKS FROM SCM_STK_STATUS_TEMP WHERE EXIST IS NULL";
        int changedSTKCount = 0;
        Connection con = null;
        try {
            con = Utility.getConnection();
            changedSTKCount = DBUtil.executeQuerySingleValueInt(STKCOUNT, "STKS", con);
            
            
        } catch (Exception ex) {
            Logger.getLogger(STKDAO.class.getName()).log(Level.SEVERE, null, ex);
            
            
        } finally {
            Utility.closeConnection(con);
            try {
                DBUtil.executeSQL(deleteSTKStatusTempQuery);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        return changedSTKCount;
        
    }
    
    public static Vector<STKDistributerStatisticsModel> getSTKsDistributerStatistics() {
        String query = "select DCM.DCM_CODE, DCM.DCM_NAME , DCM.DCM_ID , COUNT(STK_ID)QUANTITY FROM GEN_DCM DCM,SCM_STK_OWNER STK WHERE DCM.DCM_ID=STK.DCM_ID"
                + " AND DCM.DCM_LEVEL_ID=1"
                + " AND DCM.DCM_PAYMENT_LEVEL_ID=1"
                + " AND DCM.CHANNEL_ID=1"
                + " GROUP BY DCM_NAME,DCM_CODE,DCM.DCM_ID";
        
        Connection con;
        try {
            con = Utility.getConnection();
            Vector<STKDistributerStatisticsModel> stkDistributerstatics = DBUtil.executeSqlQueryMultiValue(query, STKDistributerStatisticsModel.class, con);
            return stkDistributerstatics;
            
            
        } catch (Exception ex) {
            Logger.getLogger(STKDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public static void updateDistributersVerifiedStatus(Connection con, String stockId) {
        String query = "UPDATE SCM_STK_OWNER SET DCM_VERIFIED_STATUS_ID =2 , DCM_VERIFICATION_DATE=sysdate  WHERE DCM_ID IN"
                + "(SELECT DCM_ID FROM GEN_DCM WHERE DCM_CODE IN (SELECT POS_CODE FROM IVR_ACTIVATION WHERE ACTIVATION_DATE IS NOT NULL))"
                + "AND STK_ID IN"
                + "(SELECT STK_ID FROM SCM_STK_STOCK WHERE stock_id=" + stockId + " and STK_NUMBER IN (SELECT CUST_DIAL FROM IVR_ACTIVATION WHERE ACTIVATION_DATE IS NOT NULL))"
                + "AND DCM_VERIFIED_STATUS_ID <>2";
        
        DBUtil.executeSQL(query, con);
        DBUtil.executeSQL("TRUNCATE TABLE SCM_STK_DIST_ACTIVE_TEMP", con);
    }
    
    public static Vector<STKOwnerModel> getDistributerSTKs(String DCM_ID) {
        String query = "SELECT STK.STK_NUMBER STK_DIAL,VERIFI.NAME DCM_VERIFIED_STATUS_NAME ,STKSTATUS.NAME STK_STATUS_NAME FROM"
                + " SCM_STK_OWNER OWNER,SCM_STK_STOCK STK,"
                + " SCM_VERIFIED_STATUS VERIFI,SCM_STK_STATUS STKSTATUS"
                + " WHERE OWNER.DCM_ID=" + DCM_ID
                + " AND OWNER.STK_ID=STK.STK_ID"
                + " AND OWNER.STK_STATUS_ID=STKSTATUS.STK_STATUS_ID"
                + " AND OWNER.DCM_VERIFIED_STATUS_ID=VERIFI.DCM_VERIFIED_STATUS_ID";
        Connection con;
        try {
            con = Utility.getConnection();
            Vector<STKOwnerModel> DistributerSTKs = DBUtil.executeSqlQueryMultiValue(query, STKOwnerModel.class, con);
            return DistributerSTKs;
        } catch (Exception ex) {
            Logger.getLogger(STKDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
        
    }
    
    public static void updateAllTempTable(String DCM_CODE, Connection con) {
        DBUtil.executeSQL("update SCM_UPLOAD_DIST_TEMP set DCM_ID=(SELECT DCM_ID FROM GEN_DCM WHERE DCM_CODE ='" + DCM_CODE + "' )", con);
    }
    
    public static Vector<CaseModel> beforeImportSTKsTOActive(String DCM_CODE, Connection con) {
        String querySTKNotExist = "UPDATE SCM_STK_DIST_ACTIVE_TEMP SET STKCASE=1 WHERE STK_DIAL NOT IN"
                + " (SELECT STK_NUMBER FROM SCM_STK_STOCK WHERE STK_ID IN (SELECT STK_ID FROM SCM_STK_OWNER))";
        
        String querySTKNotForThisDist = "UPDATE SCM_STK_DIST_ACTIVE_TEMP SET STKCASE=2 WHERE STK_DIAL NOT IN"
                + " (SELECT STK_NUMBER FROM SCM_STK_STOCK WHERE STK_ID IN (SELECT STK_ID FROM SCM_STK_OWNER WHERE DCM_ID ="
                + " (SELECT DCM_ID FROM GEN_DCM WHERE DCM_CODE ='" + DCM_CODE + "' )))";
        
        String querySTKNOTVerified = "UPDATE SCM_STK_DIST_ACTIVE_TEMP SET STKCASE=3 WHERE STK_DIAL IN"
                + " (SELECT STK_NUMBER FROM SCM_STK_STOCK WHERE STK_ID IN (SELECT STK_ID FROM SCM_STK_OWNER WHERE DCM_ID ="
                + " (SELECT DCM_ID FROM GEN_DCM WHERE DCM_CODE ='" + DCM_CODE + "' )AND DCM_VERIFIED_STATUS_ID<>2))";

        /*
         * String querySTKISAlreadyActive="UPDATE SCM_STK_DIST_ACTIVE_TEMP SET
         * STKCASE=4 WHERE STK_DIAL IN"+ " (SELECT STK_NUMBER FROM SCM_STK_STOCK
         * WHERE STK_ID IN (SELECT STK_ID FROM SCM_STK_OWNER WHERE DCM_ID ="+ "
         * (SELECT DCM_ID FROM GEN_DCM WHERE DCM_CODE ='"+DCM_CODE+"' )AND
         * STK_STATUS_ID=4))";
         */
        
        String querySTKStatus = "UPDATE SCM_STK_DIST_ACTIVE_TEMP SET STKCASE=5 WHERE STK_DIAL IN"
                + " (SELECT STK_NUMBER FROM SCM_STK_STOCK WHERE STK_ID IN (SELECT STK_ID FROM SCM_STK_OWNER WHERE DCM_ID ="
                + " (SELECT DCM_ID FROM GEN_DCM WHERE DCM_CODE ='" + DCM_CODE + "' )AND STK_STATUS_ID=7))";



        //DBUtil.executeSQL(querySTKISAlreadyActive);
        DBUtil.executeSQL(querySTKNOTVerified);
        DBUtil.executeSQL(querySTKNotForThisDist);
        DBUtil.executeSQL(querySTKNotExist);
        DBUtil.executeSQL(querySTKStatus);
        
        String queryGetSTKStatus = "SELECT STKCASE,ROW_NUM FROM SCM_STK_DIST_ACTIVE_TEMP WHERE STKCASE IS NOT NULL ";
        
        Vector<CaseModel> STKBeforeActiveChecks = DBUtil.executeSqlQueryMultiValue(queryGetSTKStatus, CaseModel.class, con);
        
        return STKBeforeActiveChecks;
        
    }
    
    public static Vector<CaseModel> beforeImportTOActive(String DCM_CODE, Connection con) {
        String querySTKNotExist = "UPDATE SCM_UPLOAD_DIST_TEMP SET STKCASE=1 WHERE STK_DIAL NOT IN"
                + " (SELECT STK_NUMBER FROM SCM_STK_STOCK WHERE STK_ID IN (SELECT STK_ID FROM SCM_STK_OWNER))";
        
        String querySTKNotForThisDist = "UPDATE SCM_UPLOAD_DIST_TEMP SET STKCASE=2 WHERE STK_DIAL NOT IN"
                + " (SELECT STK_NUMBER FROM SCM_STK_STOCK WHERE STK_ID IN (SELECT STK_ID FROM SCM_STK_OWNER WHERE DCM_ID ="
                + " (SELECT DCM_ID FROM GEN_DCM WHERE DCM_CODE ='" + DCM_CODE + "' )))";
        
        String querySTKNOTVerified = "UPDATE SCM_UPLOAD_DIST_TEMP SET STKCASE=3 WHERE STK_DIAL IN"
                + " (SELECT STK_NUMBER FROM SCM_STK_STOCK WHERE STK_ID IN (SELECT STK_ID FROM SCM_STK_OWNER WHERE DCM_ID ="
                + " (SELECT DCM_ID FROM GEN_DCM WHERE DCM_CODE ='" + DCM_CODE + "' )AND DCM_VERIFIED_STATUS_ID<>2))";

        /*
         * String querySTKISAlreadyActive="UPDATE SCM_STK_DIST_ACTIVE_TEMP SET
         * STKCASE=4 WHERE STK_DIAL IN"+ " (SELECT STK_NUMBER FROM SCM_STK_STOCK
         * WHERE STK_ID IN (SELECT STK_ID FROM SCM_STK_OWNER WHERE DCM_ID ="+ "
         * (SELECT DCM_ID FROM GEN_DCM WHERE DCM_CODE ='"+DCM_CODE+"' )AND
         * STK_STATUS_ID=4))";
         */
        
        String querySTKStatus = "UPDATE SCM_UPLOAD_DIST_TEMP SET STKCASE=5 WHERE STK_DIAL IN"
                + " (SELECT STK_NUMBER FROM SCM_STK_STOCK WHERE STK_ID IN (SELECT STK_ID FROM SCM_STK_OWNER WHERE DCM_ID ="
                + " (SELECT DCM_ID FROM GEN_DCM WHERE DCM_CODE ='" + DCM_CODE + "' )AND STK_STATUS_ID <> 2))";



        //DBUtil.executeSQL(querySTKISAlreadyActive);
        DBUtil.executeSQL(querySTKNOTVerified, con);
        DBUtil.executeSQL(querySTKNotForThisDist, con);
        DBUtil.executeSQL(querySTKNotExist, con);
        DBUtil.executeSQL(querySTKStatus, con);
        return getValidInvalidRows(con, false);
        
    }
    
    public static Vector<CaseModel> getValidInvalidRows(Connection con, Boolean isValid) {
        String queryGetSTKStatus = "SELECT STKCASE,ROW_NUM FROM SCM_UPLOAD_DIST_TEMP WHERE STKCASE IS " + (isValid ? "" : "NOT") + " NULL ";
        return DBUtil.executeSqlQueryMultiValue(queryGetSTKStatus, CaseModel.class, con);
        
    }
    
    public static HashMap<String, String> getDCMByMobileNumber(Connection con) {
        String queryGetSTKStatus = "SELECT STK_DIAL,DCM_ID FROM SCM_UPLOAD_DIST_TEMP WHERE STKCASE IS NULL";
        return DBUtil.getMap(con, queryGetSTKStatus);
        
    }
    
    public static void deleteInvalidRows(Connection con, Boolean addWhere) {
        String queryGetSTKStatus = "delete FROM SCM_UPLOAD_DIST_TEMP " + (addWhere ? "WHERE STKCASE IS NOT NULL " : "");
        DBUtil.executeSQL(queryGetSTKStatus, con);
        
    }
    
    public static int getReadySTKsForActive(Connection con) {
        String query = "SELECT COUNT(STK_DIAL) FROM SCM_STK_DIST_ACTIVE_TEMP WHERE STKCASE IS NULL";
        int STKCount = DBUtil.executeQuerySingleValueInt(query, "COUNT(STK_DIAL)", con);
        return STKCount;
    }
    
    public static void changeSTKDialNumber(Connection con, String oldSTKDial, String newSTKDial) {
        String query = "update SCM_STK_STOCK SET STK_NUMBER='" + newSTKDial + "' "
                + "WHERE STK_NUMBER='" + oldSTKDial + "'";
        DBUtil.executeSQL(query);
        
    }
    
    public static boolean isSTKDialExist(String STKDial) {
        String query = "SELECT STK_ID FROM SCM_STK_STOCK "
                + " WHERE STK_NUMBER='" + STKDial + "' AND"
                + " STK_STATUS_ID<>3";
        
        return DBUtil.executeSQLExistCheck(query);
    }
    
    public static Vector<String> STKStockRemainingDialsNumbers(Connection con, String stockId) {
        String qurey = "SELECT STK_NUMBER FROM SCM_STK_STOCK WHERE STK_STATUS_ID=1 and stock_id=" + stockId;
        return DBUtil.executeQueryMultiValueString(qurey, "STK_NUMBER");
    }
    
    public static HashMap<String, String> getStocksHM(Connection con) {
        return DBUtil.getMap(con, "Select STOCK_ID , STOCK_NAME from SCM_STOCKS");
    }

   public static void deleteTmpPaymentLevel(Connection con) {
        String deleteSql = "delete from TEMP_PAYMENT_LIST_GEN_DCM";
        DBUtil.executeSQL(deleteSql, con);
        String deleteSql2 = "delete from temp_dcm_pos_detail";
        DBUtil.executeSQL(deleteSql2, con);
    }
   public static void getTmpPaymentLevel(Connection con) {
       
        List<STKPaymentLevel> stkPaymentLevelList = new ArrayList<STKPaymentLevel>();
        try {

            Statement stat = con.createStatement();
            StringBuilder str = new StringBuilder("select temp_payment_list_gen_dcm.DCM_CODE ,GEN_DCM_PAYMENT_LEVEL.DCM_PAYMENT_LEVEL_ID from temp_payment_list_gen_dcm,GEN_DCM_PAYMENT_LEVEL WHERE gen_dcm_payment_level.dcm_payment_level_name=temp_payment_list_gen_dcm.dcm_payment_level_id");
           //StringBuilder str2 = new StringBuilder("select POS_CODE , DCM_PAYMENT_LEVEL_ID from temp_dcm_pos_detail");
             ResultSet res = stat.executeQuery(str.toString());
             while (res.next()) {
                STKPaymentLevel stkPaymentLevel = new STKPaymentLevel();
                stkPaymentLevel.setDcmCode(res.getString("DCM_CODE"));
                stkPaymentLevel.setGendcmpaymentLevelid(res.getString("DCM_PAYMENT_LEVEL_ID"));
                stkPaymentLevelList.add(stkPaymentLevel);
               
            }
           
             for (STKPaymentLevel stkPaymentLevel : stkPaymentLevelList) {
                 
                String dcmCode = stkPaymentLevel.getDcmCode();
                String genPaymentLevelId= stkPaymentLevel.getGendcmpaymentLevelid();
                
                String sqlString1 = "update gen_dcm set DCM_PAYMENT_LEVEL_ID =" + genPaymentLevelId + " where DCM_CODE ='" + dcmCode+"'";
                
                System.out.println("updateeeeeeeeeeeeeeeeeee"+sqlString1);
                stat.executeUpdate(sqlString1);
                
            }
             

             
           // ResultSet res = stat.executeQuery("select * from TMP_COMMISSION_LABEL ");
           // while (res.next()) {
             //   paymentVec.add(new STKPaymentLevel(res));
           // }
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
   
   
   public static void getTmpPaymentLevel2(Connection con) {
       
        List<STKPaymentLevel> stkPaymentLevelList = new ArrayList<STKPaymentLevel>();
        try {

            Statement stat = con.createStatement();
            StringBuilder str = new StringBuilder("select temp_dcm_pos_detail.POS_CODE ,GEN_DCM_PAYMENT_LEVEL.DCM_PAYMENT_LEVEL_ID from temp_dcm_pos_detail,GEN_DCM_PAYMENT_LEVEL WHERE gen_dcm_payment_level.dcm_payment_level_name=temp_dcm_pos_detail.dcm_payment_level_id");
            ResultSet res = stat.executeQuery(str.toString());
            
             while (res.next()) {
                STKPaymentLevel stkPaymentLevel = new STKPaymentLevel();
                stkPaymentLevel.setPoscode(res.getString("POS_CODE"));
                stkPaymentLevel.setPosdetaildcmpaymentlevelid(res.getString("DCM_PAYMENT_LEVEL_ID"));
                stkPaymentLevelList.add(stkPaymentLevel);
               
            }
           
             for (STKPaymentLevel stkPaymentLevel : stkPaymentLevelList) {
                 
                String posCode = stkPaymentLevel.getPoscode();
                String posPaymentLevelId= stkPaymentLevel.getPosdetaildcmpaymentlevelid();
                String sqlString1 = "update DCM_POS_DETAIL set DCM_PAYMENT_LEVEL_ID =" + posPaymentLevelId + " where POS_CODE ='" + posCode+"'";
                
                System.out.println("updateeeeeeeeeeeeeeeeeee"+sqlString1);
                stat.executeUpdate(sqlString1);
                
            }
             

             
           // ResultSet res = stat.executeQuery("select * from TMP_COMMISSION_LABEL ");
           // while (res.next()) {
             //   paymentVec.add(new STKPaymentLevel(res));
           // }
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }




}
