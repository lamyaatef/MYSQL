/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.commission.postarget.dao;

import com.mobinil.sds.core.system.commission.postarget.model.POSTargetFileModel;
import com.mobinil.sds.core.system.commission.postarget.model.POSTargetModel;
import com.mobinil.sds.core.system.commission.postarget.utils.CalendarUtility;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.commission.POSTargetInterface;
import java.sql.Connection;
import java.util.Vector;

/**
 *
 * @author mabdelaal
 */
public class POSTargetDao {
    private String typeId,yearId,dateListId,userId;
    private Connection con;
    String comma = ",";
   
    
    public Vector<POSTargetModel> getSuccessCode(long file_id){
        String sql = "select * from COMMISSION_POS_TARGET where File_id = "+file_id;
    return  DBUtil.executeSqlQueryMultiValue(sql, POSTargetModel.class, con);
    }
    public Vector<POSTargetModel> getFailedCode(){
        String sql = "select DCM_CODE POS_CODE, TARGET, ROW_NUM from POS_TARGET_UPLOAD_TEMP where case_id =0 ";
    return  DBUtil.executeSqlQueryMultiValue(sql, POSTargetModel.class, con);
    }
    public void deletePosFileTarget(String file_id){
        String sql = "delete from  COMMISSION_POS_TARGET where FILE_ID="+file_id;
        DBUtil.executeSQL(sql, con);
         sql = "delete from  COMMISSION_POS_TAR_FILE where FILE_ID="+file_id;
         DBUtil.executeSQL(sql, con);
    }
    
    public Vector<POSTargetFileModel> getAllPosFiles(){
    String sql="select * from COMMISSION_POS_TAR_FILE order by file_id";
    return DBUtil.executeSqlQueryMultiValue(sql,POSTargetFileModel.class, con);
    }
    
    public long beginValidateAndInsertDCMCodes(){
    validateDCMCodeExists();
    return  insertFileRow();
    }
    
    private long insertFileRow(){
    long file_id = Utility.getSequenceNextVal(con, "COMMISSION_POS_TARGET_FILE_SEQ");    
    StringBuilder sql = new StringBuilder();
    sql.append("insert into COMMISSION_POS_TAR_FILE ");
    sql.append("(FILE_ID, YEAR, PERIOD_TYPE, MONTH, WEEK, QUARTER,USER_ID,SYSTEM_DATE) values (");
    sql.append(file_id);
    sql.append(comma);
    sql.append(getYearId());
    sql.append(comma);
    sql.append(getTypeId());
    sql.append(comma);
    sql.append(insertFileByTypeId(file_id));
    sql.append(comma);
    sql.append(getUserId());
    sql.append(comma);
    sql.append("sysdate");
    sql.append(")");
    System.out.println(sql.toString());
    DBUtil.executeSQL(sql.toString(), con);
    transferCodesFromTempToPOS(file_id);    
    return file_id;
    }
    private String insertFileByTypeId(long file_id){    
        if(typeId.compareTo(POSTargetInterface.CONSTANT_QUARTER_TYPE)==0){
           return  selectQueryQuarterFile(file_id);
        }
        else if (typeId.compareTo(POSTargetInterface.CONSTANT_MONTH_TYPE)==0){
           return  selectQueryMonthFile(file_id);
        }
        else if (typeId.compareTo(POSTargetInterface.CONSTANT_WEEK_TYPE)==0)
        {
           return  selectQueryWeekFile(file_id);
        }

        
        return "";
        
    }
    
    private void transferCodesFromTempToPOS(long file_id){    
    StringBuilder sql = new StringBuilder("insert into COMMISSION_POS_TARGET ");
    sql.append("(POS_CODE, TARGET, POS_TARGET_ID, FILE_ID)");    
    sql.append("(select DCM_CODE, Target,COMMISSION_POS_TARGET_ID_SEQ.nextval");    
    sql.append(comma);
    sql.append(file_id);
    sql.append(" from POS_TARGET_UPLOAD_TEMP where case_id=1)");
        System.out.println(sql.toString());
    DBUtil.executeSQL(sql.toString(), con);
    }
    
    
    
    private String selectQueryQuarterFile(long file_id){
    StringBuilder values = new StringBuilder();
    values.append(file_id);
    values.append(comma);
    values.append(file_id);
    values.append(comma);
    values.append(getDateListId());
    return values.toString();
    }
    private String selectQueryMonthFile(long file_id){
    StringBuilder values = new StringBuilder();
    values.append(getDateListId());
    values.append(comma);
    values.append("100");
    values.append(comma);
    values.append(file_id);
    return values.toString();
    }
    private String selectQueryWeekFile(long file_id){
    String [] week_month     = getDateListId().split("\\_");
    StringBuilder values = new StringBuilder();
    values.append(week_month[1]);
    values.append(comma);
    values.append(week_month[0]);
    values.append(comma);
    values.append(file_id);
    return values.toString();
    }
    
    public void truncateTempTable(){
    String sql="truncate table POS_TARGET_UPLOAD_TEMP";
    System.out.println(sql.toString());
    DBUtil.executeSQL(sql, con);
    }
    
    private void validateDCMCodeExists(){        
    String sql="update POS_TARGET_UPLOAD_TEMP set case_id=1 where DCM_CODE=(select dcm_code from gen_dcm where POS_TARGET_UPLOAD_TEMP.DCM_CODE=gen_dcm.DCM_CODE)";
    System.out.println(sql.toString());
    DBUtil.executeSQL(sql, con);
    }

    @Override
    public String toString() {
        return "POSTargetDao{" + "typeId=" + typeId + ", yearId=" + yearId + ", dateListId=" + dateListId + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final POSTargetDao other = (POSTargetDao) obj;
        if ((this.typeId == null) ? (other.typeId != null) : !this.typeId.equals(other.typeId)) {
            return false;
        }
        if ((this.yearId == null) ? (other.yearId != null) : !this.yearId.equals(other.yearId)) {
            return false;
        }
        if ((this.dateListId == null) ? (other.dateListId != null) : !this.dateListId.equals(other.dateListId)) {
            return false;
        }
        return true;
    }

    public POSTargetDao(Connection con) {
        this.con = con;
    }

    public POSTargetDao(String typeId, String yearId, String dateListId, String userId, Connection con) {
        this.typeId = typeId;
        this.yearId = yearId;
        this.dateListId = dateListId;
        this.userId = userId;
        this.con = con;
    }

    

  

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.typeId != null ? this.typeId.hashCode() : 0);
        hash = 97 * hash + (this.yearId != null ? this.yearId.hashCode() : 0);
        hash = 97 * hash + (this.dateListId != null ? this.dateListId.hashCode() : 0);
        return hash;
    }

    public POSTargetDao() {
    }
    
     public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getYearId() {
        int year = new CalendarUtility().getYearById(yearId);
        return String.valueOf(year);
    }

    public void setYearId(String yearId) {
        this.yearId = yearId;
    }

    public String getDateListId() {
        return dateListId;
    }

    public void setDateListId(String dateListId) {
        this.dateListId = dateListId;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    
}
