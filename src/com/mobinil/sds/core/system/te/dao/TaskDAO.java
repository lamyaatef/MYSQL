package com.mobinil.sds.core.system.te.dao;

import java.sql.*;
import java.util.*;
import com.mobinil.sds.core.system.te.dto.*;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskDAO {

    public TaskDAO() {
    }

    public static Vector getTaskStatus(Connection con, boolean oneTaskSelected, String taskId, boolean manageTaskPage, String statusId, int taskTYpeId) throws SQLException {

        String strTaskId = "";
        String strTaskStatus = "";
        if (oneTaskSelected) {
            strTaskId = "and tt.TASK_ID=" + taskId;
        }
        if (manageTaskPage) {
            strTaskStatus = statusId;
        }
        String sql = "select ts.task_Id,tt.task_name,tt.TASK_DESCRIPTION,max(ts.TASK_STATUS_TYPE_ID) currentStatus from Tsk_status ts ,TSK_TASK tt,TSK_STATUS_TYPE tst where ts.TASK_ID = tt.TASK_ID and tt.TASK_TYPE_ID=" + taskTYpeId + " " + strTaskId + " " + strTaskStatus + " group by ts.task_id,tt.task_name,tt.TASK_DESCRIPTION order by ts.task_Id";
        System.out.println("sql get task status: "+sql);
        Vector taskVec = new Vector();
        TaskDTO ttdto = null;
        Statement st = con.createStatement();
        System.out.println("sql in getTask status is " + sql);
        ResultSet taskStatusRs = st.executeQuery(sql);
        HashMap statusNamesMap = new HashMap();
        statusNamesMap = getTaskTypeStatusNames(con);
        while (taskStatusRs.next()) {
            ttdto = new TaskDTO();
            ttdto.setTaskCurrentStatusId(taskStatusRs.getInt("currentStatus"));
            ttdto.setTaskId(taskStatusRs.getInt("task_Id"));

            Set statusNamesSet = statusNamesMap.entrySet();
            Iterator statusNamesI = statusNamesSet.iterator();
            while (statusNamesI.hasNext()) {
                Map.Entry statusNamesMe = (Map.Entry) statusNamesI.next();
                String strKey = (String) statusNamesMe.getKey();
                String strValue = (String) statusNamesMe.getValue();
                String strComKey = ttdto.getTaskCurrentStatusId() + "";
                if (strKey.compareTo(strComKey) == 0) {

                    ttdto.setTaskStatusTypeName(strValue);
                }
            }
            ttdto.setTaskName(taskStatusRs.getString("task_name"));
            ttdto.setTaskDescription(taskStatusRs.getString("TASK_DESCRIPTION"));

            taskVec.addElement(ttdto);
        }
        return taskVec;
    }

    public static HashMap getBatchTypes(Connection con) throws SQLException {
        HashMap hm = new HashMap();
        String sql = "select BATCH_TYPE_ID, BATCH_TYPE_NAME from CR_Batch_type";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            String Key = rs.getInt(1) + "";
            String Value = rs.getString(2);
            hm.put(Key, Value);

        }


        return hm;
    }

    public static HashMap getArbuPartitons(Connection con) throws SQLException {
        HashMap hm = new HashMap();
        Vector postArbuPartitons = new Vector();
        Vector preArbuPartitons = new Vector();
        String sql = "select TABLE_NAME,PARTITION_NAME from user_tab_partitions where table_name in ('ARPU_DATA_POST','ARPU_DATA')";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            String tableName = rs.getString("table_name");
            String partitonName = rs.getObject("PARTITION_NAME").toString();

            if (tableName.compareTo("ARPU_DATA") == 0) {
                preArbuPartitons.add(partitonName);
            }

            if (tableName.compareTo("ARPU_DATA_POST") == 0) {
                postArbuPartitons.add(partitonName);
            }

        }

        hm.put("pre", preArbuPartitons);
        hm.put("post", postArbuPartitons);
        return hm;
    }

    public static int insertTask(Connection con, TaskDTO tdto, String userId, String taskStatus) throws SQLException {
        Statement st = con.createStatement();
        int taskId = getSeqValue(con, "TSK_SEQ_ID");
        int taskStatusId = getSeqValue(con, "TSK_SEQ_STATUS_ID");
        int taskSettingId = 0;

        if (tdto.getTask_type_id() == 4 || tdto.getTask_type_id() == 7 || tdto.getTask_type_id() == 8 || tdto.getTask_type_id() == 9) {
            tdto.setTask_mode_id(1);
        }

        String sql = "insert into TSK_TASK (TASK_ID,TASK_NAME,TASK_DESCRIPTION,TASK_CREATION_DATE,TASK_STATUS_ID,USER_ID,TASK_TYPE_ID,TASK_SETTING_ID) values (" + taskId + ",'" + tdto.getTaskName() + "','" + tdto.getTaskDescription() + "',sysdate,null," + userId + "," + tdto.getTask_type_id() + ",null)";
        st.executeUpdate(sql);

        sql = "insert into TSK_STATUS (TASK_STATUS_ID,TASK_ID,TASK_STATUS_TYPE_ID,TIME_STAMP,DEMON_ID) values(" + taskStatusId + "," + taskId + "," + taskStatus + ",sysdate,null)";
        st.executeUpdate(sql);



        if (tdto.getTask_type_id() == 3 || tdto.getTask_type_id() == 1 || tdto.getTask_type_id() == 4) {
            taskSettingId = getSeqValue(con, "SEQ_TASK_SETTING_ID");

            sql = "insert into TSK_TASK_SETTING"
                    + "(TASK_SET_ID,TASK_SET_START_HOUR,TASK_SET_END_HOUR,TASK_ID,TASK_MODE_ID) values ";
            if (tdto.getTask_mode_id() == 2 || tdto.getTask_mode_id() == 3) {
                sql += "(" + taskSettingId + "," + tdto.getTask_set_start_hour() + "," + tdto.getTask_set_end_hour() + "," + taskId + "," + tdto.getTask_mode_id() + ")";
            }
            if (tdto.getTask_mode_id() == 1 || tdto.getTask_type_id() == 4) {
                sql += "(" + taskSettingId + ",null,null," + taskId + "," + tdto.getTask_mode_id() + ")";
            }
            System.out.println("sql is " + sql);
            st.executeUpdate(sql);
        }

        sql = "update TSK_TASK set TASK_STATUS_ID=" + taskStatusId;
        if (tdto.getTask_type_id() != 2) {
            sql += ",TASK_SETTING_ID = " + taskSettingId;
        }
        sql += "where TASK_ID=" + taskId;


        st.executeUpdate(sql);

        return taskId;
    }

    public static int getSeqValue(Connection con, String seqName) throws SQLException {

        String sql = "select " + seqName + ".nextVal val from dual";
        Statement st = con.createStatement();
        ResultSet seqValueRS = st.executeQuery(sql);
        int seqValue = 0;
        try {

            if (seqValueRS.next()) {
                seqValue = seqValueRS.getInt("val");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return seqValue;
    }

    public static void deleteTask(Connection con, int taskId) {
        try {
            Statement st = con.createStatement();

            String sql = "delete from TSK_SIM_DATA where TASK_ID=" + taskId;
            st.executeUpdate(sql);
            sql = "update TSK_TASK set TASK_STATUS_ID=null, TASK_SETTING_ID = null Where TASK_ID=" + taskId;
            st.executeUpdate(sql);
            sql = "delete from TSK_STATUS Where TASK_ID=" + taskId;
            st.executeUpdate(sql);
            sql = "delete from TSK_TASK_SETTING Where TASK_ID=" + taskId;
            st.executeUpdate(sql);
            sql = "delete from TSK_TASK Where TASK_ID=" + taskId;
            st.executeUpdate(sql);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void updateTask(Connection con, TaskDTO tdto) {
        try {
            Statement st = con.createStatement();
            String sql = "update TSK_TASK set TASK_NAME='" + tdto.getTaskName() + "' , TASK_DESCRIPTION='" + tdto.getTaskDescription() + "' Where TASK_ID=" + tdto.getTaskId();

            st.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static HashMap getTaskTypeStatusNames(Connection con) throws SQLException {

        String sql = "select * from TSK_STATUS_TYPE order by TASK_STATUS_TYPE_ID";
        Statement st = con.createStatement();
        ResultSet getTaskStatusRS = st.executeQuery(sql);
        HashMap statusNameMap = new HashMap();
        try {

            while (getTaskStatusRS.next()) {
                String key = getTaskStatusRS.getInt("TASK_STATUS_TYPE_ID") + "";
                String value = getTaskStatusRS.getString("TASK_STATUS_TYPE_NAME");
                statusNameMap.put(key, value);
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return statusNameMap;
    }

    public static boolean checkTaskName(Connection con, String taskName) throws SQLException {

        String sql = "select * from TSK_TASK where TASK_NAME='" + taskName + "'";
        Statement st = con.createStatement();
        ResultSet checkTaskRS = st.executeQuery(sql);
        boolean status = false;
        try {

            if (checkTaskRS.next()) {
                status = true;
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return status;
    }

    public static void insertSimForTask(Connection con, String taskId, Vector sims) throws SQLException {
        PreparedStatement ps = null;
        String insertSimsSQL = "insert into TSK_SIM_DATA (SIM_DATA_ID,SIM_NO,TASK_ID) values (SEQ_TSK_SIM_DATA.nextval,?," + taskId + ")";
        ps = con.prepareStatement(insertSimsSQL);

        for (int i = 0; i < sims.size(); i++) {
            String simNo = (String) sims.elementAt(i);
            ps.setString(1, simNo);
            ps.executeUpdate();

        }
        if (sims.size() != 0) {
            insertSimsSQL = "update Tsk_status set TASK_STATUS_TYPE_ID=1 where TASK_ID=" + taskId;
            ps = con.prepareStatement(insertSimsSQL);
            ps.executeUpdate();
        }
        ps.close();

    }

    public static String checkSQL(Connection con, String sql) {
        String error = null;
        try {
            Statement st = con.createStatement();
            if (sql.startsWith("select")) {
                st.executeQuery(sql);
            } else {
                con.setAutoCommit(false);
                st.executeUpdate(sql);
                con.rollback();
            }
            return error;

        } catch (Exception e) {
            // TODO: handle exception

            String ss = e.getMessage();
            ss = ss.substring((ss.indexOf(":") + 2), ss.length() - 1);
            System.out.println("exception is " + ss);
            return ss;
        }
    }

    public static void insertContractForTask(Connection con, ContractDTO cdto) throws SQLException {
        Statement stat = null;
//            
        String sql = "insert into TSK_CONTRACT (ID,CONTRACT_TYPE,CONTRACT_TYPE_VALUE,CONTRACT_SEARCH_CRITERIA,CONTRACT_UPDATE_CRITERIA,TASK_ID,CONTRACT_START_DATE, CONTRACT_END_DATE,CIF_TYPE)"
                + " values (SEQ_TSK_CONTRACT_ID.nextval,'" + cdto.getCONTRACT_TYPE() + "','" + cdto.getCONTRACT_TYPE_VALUE() + "','" + cdto.getCONTRACT_SEARCH_CRITERIA() + "','" + cdto.getCONTRACT_UPDATE_CRITERIA() + "'," + cdto.getId().intValue() + ",to_date('" + cdto.getSTART_DATE() + "','dd/mm/yyyy'),to_date('" + cdto.getEND_DATE() + "','dd/mm/yyyy')," + cdto.getCif_type() + ")";
        System.out.println("insert new contract task sql is " + sql);
        stat = con.createStatement();

        stat.executeUpdate(sql);




    }

    public static void insertArbuTask(Connection con, String arbuType, String partitonDate, String taskId, String updateConditions) throws SQLException {
        Statement stat = null;

        String sql = "INSERT INTO TSK_ARBU ( ID, ARBU_TYPE, PARTITION_VALUE, TASK_ID,UPDATE_CRITERIA ) VALUES ("
                + " SEQ_TSK_ARBU_ID.nextval,'" + arbuType + "','" + partitonDate + "','" + taskId + "','" + updateConditions + "')";
        System.out.println("insert new contract task sql is " + sql);
        stat = con.createStatement();

        stat.executeUpdate(sql);




    }

    public static void insertDailyTask(Connection con, DailyDTO ddto) throws SQLException {
        Statement stat = null;
//            
        String sql = "insert into TSK_DAILY (TASK_DAILY_ID,DEMON_ID,TASK_DAY_START,TASK_HOUR_START,TASK_HOUR_END,TASK_SQL,TASK_ID)"
                + "values (SEQ_TSK_DAILY_ID.nextval,null,to_date('" + ddto.getDayStart() + "','dd/mm/yyyy')," + ddto.getHourStart() + "," + ddto.getHourEnd() + ",'" + ddto.getSQL() + "'," + ddto.getTaskId() + ")";
        stat = con.createStatement();
        stat.executeUpdate(sql);

//               int taskSettingId = getSeqValue(con,"SEQ_TSK_DAILY_SETTING_ID");
//
//               sql = "insert into TSK_TASK_SETTING (TASK_SET_ID,TASK_SET_START_HOUR,TASK_SET_END_HOUR,TASK_ID,TASK_MODE_ID)"+ 
//               "values ("+taskSettingId+","+ddto.getHourStart()+","+ddto.getHourEnd()+","+ddto.getTaskId()+","++")";
//           stat = con.createStatement();
//              stat.executeUpdate(sql);
//
//              
//              sql = "update TSK_TASK set TASK_STATUS_ID="+taskSettingId+"where TASK_ID="+ddto.getTaskId();
//              
//
//              stat.executeUpdate(sql);


    }

    public static Vector getTaskRows(Connection con, String taskId) throws SQLException {

        String sql = "select tsd.SIM_DATA_ID , tsd.SIM_NO, tsd.TASK_ID, to_char(tsd.ACTIVATION_DATE,'dd/mm/yyyy') ACTIVATION_DATE, tsd.LCS_DCM_ID,gd.DCM_NAME,gd.DCM_CODE, "
                + "tsd.LCS_TRANS_TYPE_ID,cty.TRANSACTION_TYPE_NAME, tsd.LCS_PRODUCT_ID,gp.PRODUCT_NAME, to_char(tsd.LCS_ISSUE_DATA,'dd/mm/yyyy')LCS_ISSUE_DATA, tsd.LCS_STATUS_ID from "
                + "tsk_sim_data tsd ,  gen_dcm gd, gen_product gp, cr_transaction_type cty "
                + "where tsd.LCS_DCM_ID = gd.DCM_ID(+) "
                + "and  tsd.LCS_PRODUCT_ID = gp.PRODUCT_ID(+) "
                + "and tsd.LCS_TRANS_TYPE_ID=cty.TRANSACTION_TYPE_ID(+) and TASK_ID='" + taskId + "' ";
        System.out.println("SQL is SSSSSSSS " + sql);
        Statement st = con.createStatement();
        SimDataDTO sddto = null;
        Vector simDataVec = new Vector();
        ResultSet getTaskRowsRS = st.executeQuery(sql);
        try {
//        	private String productName;
//        	private String dcmName;
//        	private String dcmCode;
//        	private String transactionTypeName;
            while (getTaskRowsRS.next()) {
                sddto = new SimDataDTO();
                sddto.setActivation(getTaskRowsRS.getString("ACTIVATION_DATE"));

                sddto.setProductName(getTaskRowsRS.getString("PRODUCT_NAME"));
                sddto.setDcmName(getTaskRowsRS.getString("DCM_NAME"));
                sddto.setDcmCode(getTaskRowsRS.getString("DCM_CODE"));
                sddto.setTransactionTypeName(getTaskRowsRS.getString("TRANSACTION_TYPE_NAME"));

                sddto.setLcsId(new Integer(getTaskRowsRS.getInt("LCS_DCM_ID")));
                sddto.setLcsIssueDate(getTaskRowsRS.getString("LCS_ISSUE_DATA"));
                sddto.setLcsProductId(new Integer(getTaskRowsRS.getInt("LCS_PRODUCT_ID")));
                sddto.setLcsStatusId(new Integer(getTaskRowsRS.getInt("LCS_STATUS_ID")));
                sddto.setLcsTransactionTypeId(new Integer(getTaskRowsRS.getInt("LCS_TRANS_TYPE_ID")));
                sddto.setSimId(new Integer(getTaskRowsRS.getInt("SIM_DATA_ID")));
                sddto.setSimNo(getTaskRowsRS.getString("SIM_NO"));

                sddto.setTaskID(new Integer(getTaskRowsRS.getInt("TASK_ID")));
                simDataVec.addElement(sddto);
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return simDataVec;
    }
    //ahmed adel

    public static void insertIVRTask(String taskName, String userId, Connection con) {
        try {
            int taskId = getSeqValue(con, "TSK_SEQ_ID");
            int taskStatusId = getSeqValue(con, "TSK_SEQ_STATUS_ID");
            String taskquery = "INSERT INTO SDS.TSK_TASK (TASK_ID, TASK_NAME, TASK_DESCRIPTION,"
                    + " TASK_CREATION_DATE, TASK_STATUS_ID, USER_ID, "
                    + " TASK_TYPE_ID, TASK_SETTING_ID) "
                    + " VALUES (" + taskId + ",'" + taskName + "','',"
                    + " sysdate," + taskStatusId + "," + userId + ",5,null)";

            String ivrquery = "INSERT INTO SDS.TSK_IVR (TASK_ID, TASK_NAME, UPDATED_IN, USER_ID)"
                    + "VALUES (" + taskId + ",'" + taskName + "',sysdate," + userId + ")";

            String Statusquery = "INSERT INTO SDS.TSK_STATUS (TASK_STATUS_ID, TASK_ID, TASK_STATUS_TYPE_ID, "
                    + "TIME_STAMP, DEMON_ID)"
                    + "VALUES (" + taskStatusId + "," + taskId + ",1,sysdate,'')";
            DBUtil.executeSQL(taskquery);
            DBUtil.executeSQL(ivrquery);
            DBUtil.executeSQL(Statusquery);

        } catch (SQLException ex) {
            Logger.getLogger(TaskDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static int insertNomadTask(String taskName, String userId, Connection con) {
        System.out.println("CALL NOMAD");
        int taskId = 0;
        int taskStatusId = 0;
        try {
            System.out.println("inside insert nomad task");
            taskId = getSeqValue(con, "task_seq_ID");
            System.out.println("task id : "+taskId);
          //  taskStatusId = getSeqValue(con, "task_seq_status_ID");
            taskStatusId = getSeqValue(con, "tsk_seq_status_ID");
            System.out.println("task status id : "+taskStatusId);
            
            String taskquery = "INSERT INTO SDS.TSK_TASK (TASK_ID, TASK_NAME, TASK_DESCRIPTION,"
                    + " TASK_CREATION_DATE, TASK_STATUS_ID, USER_ID, "
                    + " TASK_TYPE_ID, TASK_SETTING_ID) "
                    + " VALUES (" + taskId + ",'" + taskName + "','',"
                    + " sysdate," + taskStatusId + "," + userId + ",5,null)"; // 5= nomad import

            String nomadquery = "INSERT INTO SDS.TSK_NOMAD (TASK_ID, TASK_NAME, UPDATED_IN, USER_ID)"
                    + "VALUES (" + taskId + ",'" + taskName + "',sysdate," + userId + ")";

            
            String Statusquery = "INSERT INTO SDS.TSK_STATUS (TASK_STATUS_ID, TASK_ID, TASK_STATUS_TYPE_ID, "
                    + "TIME_STAMP, DEMON_ID)"
                    + "VALUES (" + taskStatusId + "," + taskId + ",1,sysdate,null)"; //1 = new , null for no demon is handling (hagry) 
            System.out.println("task query : "+taskquery);
            System.out.println("nomad query : "+nomadquery);
            System.out.println("status query : "+Statusquery);
            DBUtil.executeSQL(taskquery);
            DBUtil.executeSQL(nomadquery);
            DBUtil.executeSQL(Statusquery);

        } catch (SQLException ex) {
            Logger.getLogger(TaskDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return taskId;

    }
    public static Vector<IVRTaskDTO> getIVRTaskTimeData(Connection con) {
        String query = "SELECT TASK_ID,START_TIME,END_TIME,UPDATED_ROWS FROM TSK_IVR  ORDER BY TASK_ID";
        Vector<IVRTaskDTO> IVRTask = DBUtil.executeSqlQueryMultiValue(query, IVRTaskDTO.class, con);

        return IVRTask;
    }

    public static Vector<NomadTaskDTO> getNomadTaskReportData(Connection con) {
        String query = "SELECT TASK_ID,TASK_NAME,START_TIME,END_TIME,UPDATED_ROWS FROM TSK_NOMAD  ORDER BY TASK_ID DESC";
        System.out.println("show task query : "+query);
        Vector<NomadTaskDTO> nomadTaskRepData = DBUtil.executeSqlQueryMultiValue(query, NomadTaskDTO.class, con);

        return nomadTaskRepData;
    }
    
    public static Vector<NomadTaskDTO> getNomadTaskByID(Connection con, int taskId) {
        String query = "SELECT TASK_ID,TASK_NAME,START_TIME,END_TIME,UPDATED_ROWS FROM TSK_NOMAD where TASK_ID = '"+taskId+"'";
        System.out.println("show task query by id : "+query);
        Vector<NomadTaskDTO> nomadTaskRepData = DBUtil.executeSqlQueryMultiValue(query, NomadTaskDTO.class, con);

        return nomadTaskRepData;
    }
    
    public static Vector<NTRATaskDTO> getNTRATaskReportData(Connection con) {
        String query = "SELECT TASK_ID,START_TIME,END_TIME,UPDATED_ROWS,to_char((end_time-start_time)*1440*60 ) UPDATED_IN FROM TSK_NTRA ORDER BY TASK_ID";
        Vector<NTRATaskDTO> ntraTaskRepData = DBUtil.executeSqlQueryMultiValue(query, NTRATaskDTO.class, con);

        return ntraTaskRepData;
    }

    public static void main(String[] args) {
        try {
            getArbuPartitons(Utility.getConnection());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Vector getAuthAgentTasks(Connection con) throws SQLException {


        String sql = "SELECT TASK_NAME,START_DATE,END_DATE,UPDATE_ROWS FROM auth_agent_task,TSK_TASK WHERE auth_agent_task.TASK_ID=TSK_TASK.TASK_ID";
        Vector taskVec = new Vector();
        AuthAgentArpuUpdateTaskDTO ttdto = null;
        Statement st = con.createStatement();
        ResultSet taskRs = st.executeQuery(sql);

        while (taskRs.next()) {
            ttdto = new AuthAgentArpuUpdateTaskDTO();
            ttdto.setStartTime(taskRs.getTimestamp("START_DATE"));
            ttdto.setEndTime(taskRs.getTimestamp("END_DATE"));
            ttdto.setTaskName(taskRs.getString("TASK_NAME"));
            ttdto.setUpdatedRows(taskRs.getString("UPDATE_ROWS"));
            taskVec.addElement(ttdto);
        }
        return taskVec;
    }
}