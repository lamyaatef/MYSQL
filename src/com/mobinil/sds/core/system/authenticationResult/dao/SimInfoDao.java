/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.authenticationResult.dao;

import com.mobinil.sds.core.system.authenticationResult.model.SimInfoModel;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import java.awt.event.FocusAdapter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author mabdelaal
 */
public class SimInfoDao {

    Statement updateStatement = null;
    private SimInfoModel simInfoModel;
    private Connection con;
    private int isFileInserted =0;

    public SimInfoDao(Connection con, SimInfoModel simInfoModel) {
        this.simInfoModel = simInfoModel;
        this.con = con;
        try {
            updateStatement = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateXyzDataSims() throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append(" update SIM_INFO_UPLOAD_TEMP ss set (ss.XYZ_REQ_CREATION_TIME , ss.XYZ_REQ_POS_CODE , ss.XYZ_STATUS_DESCRIPTION) ");
        query.append(" = (SELECT A.REQ_CREATION_TIME , A.REQ_POS_CODE,  A.STATUS_DESCRIPTION  FROM VW_NLA_XYZ_TRANSACTIONS A where ss.SIM_SERIAL = a.SIM_SERIAL ) ");
        query.append(" where FILE_ID=");
        query.append(simInfoModel.getFileId());
        DBUtil.executeSQL(query.toString(), updateStatement);
    }

    private void updateNTRADataSims() throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append(" update SIM_INFO_UPLOAD_TEMP ss set (ss.NTRA_POS_CODE , ss.NTRA_TIME_STAMP , ss.NTRA_STATUS_DESCRIPTION ) ");
        query.append(" = ( SELECT a.POS_CODE, a.TIME_STAMP, a.STATUS_DESCRIPTION FROM NTRA_EGY_ACTIVE_SIM a where ss.SIM_SERIAL = a.SIM_SERIAL ) ");
        query.append(" where file_id=");
        query.append(simInfoModel.getFileId());
        DBUtil.executeSQL(query.toString(), updateStatement);
    }

    private void updateSFRDataSims() throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append(" update SIM_INFO_UPLOAD_TEMP ss set (ss.SFR_POS_CODE , ss.SFR_SECOND_POS_CODE , ss.SFR_COMMISSION_DATE ) ");
        query.append(" = ( SELECT (SELECT DCM_CODE FROM GEN_DCM WHERE DCM_ID=SH.POS_ID) SFR_POS_CODE ,  ");
        query.append(" (SELECT DCM_CODE FROM GEN_DCM WHERE DCM_ID=SH.SECOND_POS_ID)  SFR_SECOND_POS_CODE ");
        query.append(" , B.BATCH_DATE SFR_COMMISSION_DATE FROM SFR_BATCH  B , SFR_SHEET SH , SFR_SIM SI WHERE  ");
        query.append(" B.BATCH_ID = SH.BATCH_ID ");
        query.append(" AND SH.SHEET_ID = SI.SHEET_ID and ss.SIM_SERIAL = SI.SIM_SERIAL ) ");
        query.append(" where file_id=");
        query.append(simInfoModel.getFileId());
        DBUtil.executeSQL(query.toString(), updateStatement);

    }

    private void updateFileID() throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append(" update SIM_INFO_UPLOAD_TEMP set FILE_ID=");
        query.append(simInfoModel.getFileId());
        query.append(" where file_id is null");
        DBUtil.executeSQL(query.toString(), updateStatement);
    }
    private void updateFileStatus(String status) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append(" update AUTH_SIM_INFO_FILE set STATUS='").append(status);        
        query.append("' where file_id=");
        query.append(simInfoModel.getFileId());
        DBUtil.executeSQL(query.toString(), updateStatement);
    }    
    private void updateFileJobEndTime() throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append(" update AUTH_SIM_INFO_FILE set END_TIME=sysdate");
        query.append(" where file_id=");
        query.append(simInfoModel.getFileId());
        DBUtil.executeSQL(query.toString(), updateStatement);
    }
    private void updateFileRowsInserted() throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append(" update AUTH_SIM_INFO_FILE set ROW_COUNT=(Select count(file_id) from SIM_INFO_UPLOAD_TEMP where FILE_ID=");
        query.append(simInfoModel.getFileId());
        query.append(") where file_id=");
        query.append(simInfoModel.getFileId());
        DBUtil.executeSQL(query.toString(), updateStatement);
    }

        public Long insertFileInfo() throws SQLException {
        Long fileId = DBUtil.getSequenceNextVal(con, "AUTH_SIM_INFO_FILE_SEQ");
        simInfoModel.setFileId(String.valueOf(fileId));
        StringBuilder query = new StringBuilder();
        query.append(" insert into AUTH_SIM_INFO_FILE (FILE_ID, STATUS, TIME_STAMP, USER_ID, LABEL_ID,START_TIME) ");
        query.append(" values ('").append(fileId).append("','").append(simInfoModel.getStatus()).append("',sysdate,'");
        query.append(simInfoModel.getUserId());
        query.append("','");
        query.append(simInfoModel.getLabelId());
        query.append("',sysdate)");
        
        isFileInserted = DBUtil.executeSQL(query.toString(), updateStatement);
        return fileId;
    }

    public void runAllUpdates() throws SQLException {
//        int isFileInserted = insertFileInfo();
        if (isFileInserted > 0) {            
            updateFileID();
            updateXyzDataSims();
            updateNTRADataSims();
            updateSFRDataSims();
            updateFileStatus("ACTIVE");
            updateFileJobEndTime();
            updateFileRowsInserted();
        }
        DBUtil.close(updateStatement);
    }
}
