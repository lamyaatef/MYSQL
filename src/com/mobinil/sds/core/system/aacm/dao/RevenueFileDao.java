/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.aacm.dao;

import com.mobinil.sds.core.system.aacm.model.AccmRevFile;
import com.mobinil.sds.core.system.aacm.model.AccmRevUpload;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import java.sql.Connection;
import java.util.Vector;

/**
 *
 * @author mabdelaal
 */
public class RevenueFileDao {

    public RevenueFileDao(Connection con) {
        this.con = con;
    }
     private String yearId,monthId,userId;
    private Connection con;
    String comma = ",";
    private boolean isExists= false;

    public RevenueFileDao(String yearId, String monthId, String userId, Connection con) {
        this.yearId = yearId;
        this.monthId = monthId;
        this.userId = userId;
        this.con = con;
        this.isExists = checkYearMonthExsists();
    }

    public RevenueFileDao() {
    }
    
    private boolean checkYearMonthExsists(){
    
    return DBUtil.executeSQLExistCheck("select * from ACCM_REV_FILE where YEAR_NUMER="+yearId+" and MONTH_NUMER="+monthId, con);
    }
    public void truncateTempTable(){
    String sql="truncate table ACCM_REV_UPLOAD_TEMP";
    System.out.println(sql.toString());
    DBUtil.executeSQL(sql, con);
    }
    
    public long beginValidateAndInsertDCMCodes(){    
    return  insertFileRow();
    }
    
    private long insertFileRow(){
    long file_id = Utility.getSequenceNextVal(con, "ACCM_REV_FILE_SEQ");    
    StringBuilder sql = new StringBuilder();
    sql.append("insert into ACCM_REV_FILE ");
    sql.append("(FILE_ID, SUM_INSERTED_ROWS, YEAR_NUMER, MONTH_NUMER, CREATION_DATE, USER_ID) values (");
    sql.append(file_id);
    sql.append(comma);
    sql.append("0");
    sql.append(comma);
    sql.append(yearId);
    sql.append(comma);
    sql.append(monthId);
    sql.append(comma);
    sql.append("sysdate");
    sql.append(comma);
    sql.append(userId);
    sql.append(")");    
    DBUtil.executeSQL(sql.toString(), con);
    transferDateFromTempTable(file_id);    
    return file_id;
    }
    private void transferDateFromTempTable(long file_id){    
    StringBuilder sql = new StringBuilder("insert into ACCM_REV_UPLOAD ");
    sql.append("(ACCM_REV_ID, CUSTOMER_CODE, ACCOUNT_MGR, SUM_OF_INVOICE, SUM_OF_ACTIVE, FILE_ID)");    
    sql.append("(select ACCM_REV_FILE_DETAIL_SEQ.nextval, CUSTOMER_CODE, ACCOUNT_MGR, SUM_OF_INVOICE, SUM_OF_ACTIVE,");        
    sql.append(file_id);
    sql.append(" from ACCM_REV_UPLOAD_TEMP where case_id=0)");        
    DBUtil.executeSQL(sql.toString(), con);
    sql = new StringBuilder("UPDATE ACCM_REV_FILE SET SUM_INSERTED_ROWS=(SELECT COUNT(*) FROM ACCM_REV_UPLOAD WHERE FILE_ID=");
    sql.append(file_id);
    sql.append(") WHERE FILE_ID=");
    sql.append(file_id);
    DBUtil.executeSQL(sql.toString(), con);
    }
    public void deleteRevenueFile(String file_id){    
    StringBuilder sql = new StringBuilder("delete from ACCM_REV_UPLOAD where FILE_ID=");
    sql.append(file_id);
    DBUtil.executeSQL(sql.toString(), con);
    sql = new StringBuilder("delete from ACCM_REV_FILE WHERE FILE_ID=");
    sql.append(file_id);
    DBUtil.executeSQL(sql.toString(), con);    
    }

    /**
     * @return the isExists
     */
    public boolean isIsExists() {
        return isExists;
    }

    /**
     * @param isExists the isExists to set
     */
    public void setIsExists(boolean isExists) {
        this.isExists = isExists;
    }

    public Vector<AccmRevFile> getAllRevnFiles() {
        return DBUtil.executeSqlQueryMultiValue("select * from ACCM_REV_FILE", AccmRevFile.class, con);
    }

    public Vector<AccmRevUpload> getFileRecords(long file_id) {
        return DBUtil.executeSqlQueryMultiValue("select * from ACCM_REV_UPLOAD where file_id="+file_id, AccmRevUpload.class, con);
    }
}
