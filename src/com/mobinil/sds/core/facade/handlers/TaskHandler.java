package com.mobinil.sds.core.facade.handlers;

import java.util.*;
import java.util.Date;
import java.lang.Object;
import java.sql.*;
import java.io.*;

import com.mobinil.sds.web.interfaces.te.TaskInterfaceKey;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.core.system.te.dao.*;
import com.mobinil.sds.core.system.te.dto.*;

import com.mobinil.sds.core.utilities.Utility;
import javax.servlet.http.HttpServletRequest;

public class TaskHandler {

    static final int SHOW_TASKS = 1;
    static final int MANAGE_TASKS = 2;
    static final int DELETE_TASKS = 3;
    static final int NEW_EDIT_TASK = 4;
    static final int SAVE_TASK = 5;
    static final int UPLOAD_TASKS = 6;
    static final int PROCESS_FILE = 7;
    static final int COMPLETED_TASKS = 8;
    static final int EXPORT_COMPLETED_TASKS = 9;
    static final int ADD_CONTRACT_TASK = 10;
    static final int VIEW_CONTRACT_TASK = 11;
    static final int SHOW_CONTRACT_TASKS = 12;
    static final int ADD_DIALY_TASK = 13;
    static final int CHECK_DB_BEFORE_SUBMIT = 14;
    static final int ACTION_SHOW_ARBU_TASKS_STATUS = 15;
    static final int ACTION_VIEW_ARBU_PAGE = 16;
    static final int ACTION_SAVE_ARBU_TASK = 17;
    static final int action_save_ivr_task = 18;
    static final int ACTION_SAVE_NTRA_TASK = 19;
    static final int ACTION_VIEW_NEW_TASK_PAGE = 20;
    static final int ACTION_SAVE_NEW_TASK = 21;
    static final int ACTION_SHOW_AUTH_AGENT_TASK = 22;
    static final int action_save_nomad_task = 23;
    
    
    public static HashMap handle(String action, HashMap paramHashMap, java.sql.Connection con) {
        System.out.println("ACTION IS : "+action);
        int actionType = 0;
        HashMap dataHashMap = new HashMap(100);

        int taskTypeFalg = 0;
        int nomadTaskId = 0;
        int nomadTaskStatusId=0;
        try {

            if (action.equals(TaskInterfaceKey.ACTION_SHOW_TASKS)) {
                actionType = SHOW_TASKS;
                taskTypeFalg = 1;
            }
            if (action.equals(TaskInterfaceKey.ACTION_SHOW_CONTRACT_TASKS)) {
                actionType = SHOW_TASKS;
                taskTypeFalg = 2;
            }
            if (action.equals(TaskInterfaceKey.ACTION_SHOW_DAILY_TASKS)) {
                actionType = SHOW_TASKS;
                taskTypeFalg = 3;
            }
            if (action.equals(TaskInterfaceKey.ACTION_SHOW_ARBU_TASKS_STATUS)) {
                actionType = SHOW_TASKS;
                taskTypeFalg = 4;
            }
            if (action.equals(TaskInterfaceKey.ACTION_SHOW_IVR_TASKS_STATUS)) {
                actionType = SHOW_TASKS;
                taskTypeFalg = 5;
            }
            if (action.equals(TaskInterfaceKey.ACTION_SHOW_NTRA_TASKS_STATUS)) {
                actionType = SHOW_TASKS;
                taskTypeFalg = 6;
            }
            //lamya
            if (action.equals(TaskInterfaceKey.ACTION_SHOW_NOMAD_TASKS_STATUS)) {
                System.out.println("SHOW nomad TASKS after save : "+taskTypeFalg);
                actionType = SHOW_TASKS;
                taskTypeFalg = 7;
            }
            
            
            if (action.equals(TaskInterfaceKey.ACTION_SHOW_NOMAD)) {
                System.out.println("SHOW nomad TASKS alone : "+taskTypeFalg);
                actionType = SHOW_TASKS;
                taskTypeFalg = 8;
            }

            if (action.equals(TaskInterfaceKey.ACTION_MANAGE_TASKS)) {
                actionType = MANAGE_TASKS;
            }
            if (action.equals(TaskInterfaceKey.ACTION_SAVE_TASK)) {
                actionType = SAVE_TASK;
            }
            if (action.equals(TaskInterfaceKey.ACTION_UPLOAD_FILE)) {
                actionType = UPLOAD_TASKS;
            }
            if (action.equals(TaskInterfaceKey.ACTION_DELETE_TASK)) {
                actionType = DELETE_TASKS;
            }
            if (action.equals(TaskInterfaceKey.ACTION_NEW_EDIT_TASK)) {
                actionType = NEW_EDIT_TASK;
            }
            if (action.equals(TaskInterfaceKey.ACTION_PROCESS_FILE)) {
                actionType = PROCESS_FILE;
            }
            if (action.equals(TaskInterfaceKey.ACTION_COMPLETED_TASKS)) {
                actionType = COMPLETED_TASKS;
            }
            if (action.equals(TaskInterfaceKey.ACTION_EXPORT_COMPLETED_TASKS)) {
                actionType = EXPORT_COMPLETED_TASKS;
            }
            if (action.equals(TaskInterfaceKey.ACTION_DELETE_COMPLETED_TASKS)) {
                actionType = DELETE_TASKS;
            }
            if (action.equals(TaskInterfaceKey.ACTION_ADD_NEW_TASK_CONTRACT)) {
                actionType = ADD_CONTRACT_TASK;
            }
            if (action.equals("view_contract_task")) {
                actionType = VIEW_CONTRACT_TASK;
            }
            if (action.equals(TaskInterfaceKey.ACTION_ADD_NEW_DAILY_CONTRACT)) {
                actionType = ADD_DIALY_TASK;
            }
            if (action.equals(TaskInterfaceKey.ACTION_CHECK_DB_BEFORE_SUBMIT)) {
                actionType = CHECK_DB_BEFORE_SUBMIT;
            }
            if (action.equals(TaskInterfaceKey.ACTION_SAVE_ARBU_TASK)) {
                actionType = ACTION_SAVE_ARBU_TASK;
            }
            if (action.equals(TaskInterfaceKey.ACTION_VIEW_ARBU_PAGE)) {
                actionType = ACTION_VIEW_ARBU_PAGE;
            }
            if (action.equals(TaskInterfaceKey.ACTION_SAVE_IVR_TASK)) {
                actionType = action_save_ivr_task;
            }
            if (action.equals(TaskInterfaceKey.ACTION_SAVE_NTRA_TASK)) {
                actionType = ACTION_SAVE_NTRA_TASK;
            }
            if (action.equals(TaskInterfaceKey.ACTION_VIEW_NEW_TASK_PAGE)) {
                actionType = ACTION_VIEW_NEW_TASK_PAGE;
            }
            if (action.equals(TaskInterfaceKey.ACTION_SAVE_NEW_TASK)) {
                actionType = ACTION_SAVE_NEW_TASK;
            }
            if (action.equals(TaskInterfaceKey.ACTION_SHOW_AUTH_AGENT_TASK)) {
                actionType = ACTION_SHOW_AUTH_AGENT_TASK;
            }
            if (action.equals(TaskInterfaceKey.ACTION_SAVE_NOMAD_TASK)) {
                actionType = action_save_nomad_task;
            }
            System.out.println("ACTION TYPE : "+actionType);
            String userID = getUserId(null, paramHashMap);
            String message = null;

            switch (actionType) {
                case DELETE_TASKS: {
                    getSelectItem(con, paramHashMap, "taskCheck");
                }
                break;

                case EXPORT_COMPLETED_TASKS: {
                    dataHashMap = exportTaskData(con, paramHashMap, dataHashMap);
                }
                break;
                case SAVE_TASK: {
                    message = saveTask(con, paramHashMap, message);
                }
                break;
                case UPLOAD_TASKS: {
                    String taskId = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID);
                    dataHashMap.put(TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID, taskId);
                }
                break;
                case VIEW_CONTRACT_TASK: {

                    dataHashMap.put(TaskInterfaceKey.HASHMAP_BATCH_TYPES, TaskDAO.getBatchTypes(con));

                }
                break;

                case NEW_EDIT_TASK: {
                    dataHashMap = newEditTask(con, paramHashMap, dataHashMap);
                }
                break;
                case ADD_CONTRACT_TASK: {
                    dataHashMap = newContractTask(con, paramHashMap, dataHashMap, userID);
                }
                break;
                case ADD_DIALY_TASK: {
                    System.out.println("here in ADD_DIALY_TASK and sql is " + (String) paramHashMap.get("message1"));
                    dataHashMap = newDaliyTask(con, paramHashMap, dataHashMap, userID);
                }
                break;
                case CHECK_DB_BEFORE_SUBMIT: {
                    dataHashMap = checkDailyValues(con, paramHashMap, dataHashMap, userID);
                }
                break;
                case ACTION_VIEW_ARBU_PAGE: {
                    dataHashMap = viewArbuTaskPage(con, dataHashMap);
                }
                break;
                case ACTION_SAVE_ARBU_TASK: {
                    dataHashMap = newArbuTask(con, paramHashMap, dataHashMap, userID);
                }
                break;
                case action_save_ivr_task: {
                    String taskName = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_IVR_TASK_NAME);
                    TaskDAO.insertIVRTask(taskName, userID, con);
                }
                break;
                    //lamya
                case action_save_nomad_task: {
                    System.out.println("SAVE NOMAD ACTION");
                    taskTypeFalg = 7;
                    String taskName = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_NOMAD_TASK_NAME);
                    System.out.println("save nomad action : "+taskName);
                    nomadTaskId = TaskDAO.insertNomadTask(taskName, userID, con);
                    Vector<NomadTaskDTO> nomadTaskRepData = TaskDAO.getNomadTaskByID(con,nomadTaskId);
                    System.out.println("nomadTaskRepData : "+nomadTaskRepData.size());
                    dataHashMap.put(TaskInterfaceKey.VEC_NOMAD_TASKS, nomadTaskRepData);
                    System.out.println("TaskInterfaceKey.VEC_NOMAD_TASKS : "+((Vector<NomadTaskDTO>)dataHashMap.get(TaskInterfaceKey.VEC_NOMAD_TASKS)).size());
                    dataHashMap.put(TaskInterfaceKey.CONTROL_NOMAD_TASK_TIME_DATA, nomadTaskRepData);
                    dataHashMap.put(TaskInterfaceKey.CONTROL_TASK_TYPE, Integer.toString(taskTypeFalg));
                }
                break;
                    
                case ACTION_SAVE_NTRA_TASK: {
                    String taskName = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_NTRA_TASK_NAME);
                    TaskDTO tdto = new TaskDTO();
                    tdto.setTaskName(taskName);
                    tdto.setTaskDescription(taskName);
                    tdto.setTask_type_id(6);
                    TaskDAO.insertTask(con, tdto, userID, "1");

                }
                case ACTION_VIEW_NEW_TASK_PAGE: {
                }
                break;
                case ACTION_SAVE_NEW_TASK: {
                    String taskName = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_NEW_TASK_NAME);
                    String taskType = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_NEW_TASK_TYPE);
                    TaskDTO tdto = new TaskDTO();
                    tdto.setTaskName(taskName);
                    tdto.setTaskDescription(taskName);
                    if (Integer.parseInt(taskType) == 7) {
                        tdto.setTask_type_id(7);
                        TaskDAO.insertTask(con, tdto, userID, "1");
                    } else if (Integer.parseInt(taskType) == 8) {
                        tdto.setTask_type_id(8);
                        TaskDAO.insertTask(con, tdto, userID, "1");
                    } else if (Integer.parseInt(taskType) == 9) {
                        tdto.setTask_type_id(9);
                        TaskDAO.insertTask(con, tdto, userID, "1");
                    }


                }
                break;
                case ACTION_SHOW_AUTH_AGENT_TASK: {
                    System.out.println("SHOW_TASKS isssssssssss " + ACTION_SHOW_AUTH_AGENT_TASK);
                    dataHashMap.put(TaskInterfaceKey.VEC_TASKS, TaskDAO.getAuthAgentTasks(con));
                  
                }
                break;

                default:
                    Utility.logger.debug("Unknown action received: " + action);
            }
            if (actionType == SHOW_TASKS) {
                System.out.println("SHOW_TASKS isssssssssss " + SHOW_TASKS+"task type flag: "+taskTypeFalg);
             if (taskTypeFalg != 7 )//optimization i already did noamd show need to work on others to remove this status need
             {
                dataHashMap.put(TaskInterfaceKey.VEC_TASKS, TaskDAO.getTaskStatus(con, false, "0", true, "and ts.TASK_STATUS_TYPE_ID!=5", taskTypeFalg));
             }
                dataHashMap.put(TaskInterfaceKey.CONTROL_TASK_TYPE, Integer.toString(taskTypeFalg));

                if (taskTypeFalg == 5) {
                    
                    

                    Vector<IVRTaskDTO> ivr = TaskDAO.getIVRTaskTimeData(con);

                    dataHashMap.put(TaskInterfaceKey.CONTROL_IVR_TASK_TIME_DATA, ivr);


                }
                if (taskTypeFalg == 6) {

                    Vector<NTRATaskDTO> ntraTaskRepData = TaskDAO.getNTRATaskReportData(con);

                    dataHashMap.put(TaskInterfaceKey.CONTROL_NTRA_TASK_TIME_DATA, ntraTaskRepData);


                }
                //lamya
                if (taskTypeFalg == 7) {

                    System.out.println("in if type=7");
                    Vector<NomadTaskDTO> nomadTaskRepData = TaskDAO.getNomadTaskReportData(con);

                    dataHashMap.put(TaskInterfaceKey.CONTROL_NOMAD_TASK_TIME_DATA, nomadTaskRepData);
                    dataHashMap.put(TaskInterfaceKey.VEC_NOMAD_TASKS, nomadTaskRepData);//lamya
                    //dataHashMap.put(TaskInterfaceKey.VEC_TASKS, nomadTaskRepData);
                     System.out.println("adding nomad data ");
                }
                if (taskTypeFalg == 8) {

                    System.out.println("in if type=8");
                    Vector<NomadTaskDTO> nomadTaskRepData = TaskDAO.getNomadTaskReportData(con);

                    dataHashMap.put(TaskInterfaceKey.VEC_NOMAD_TASKS, nomadTaskRepData);
                    dataHashMap.put(TaskInterfaceKey.CONTROL_NOMAD_TASK_TIME_DATA, nomadTaskRepData);

                }
                //////
            }
            if (actionType == MANAGE_TASKS || actionType == SAVE_TASK
                    || actionType == DELETE_TASKS) {
                dataHashMap.put(TaskInterfaceKey.VEC_TASKS, TaskDAO.getTaskStatus(con, false, "0", true, "and ts.TASK_STATUS_TYPE_ID=5", 1));
            }

            if (actionType == COMPLETED_TASKS) {
                dataHashMap.put(TaskInterfaceKey.VEC_TASKS, TaskDAO.getTaskStatus(con, false, "0", true, "and ts.TASK_STATUS_TYPE_ID=3", 1));
            }

            if (message != null) {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, message);
            }


        } catch (Exception objExp) {
            objExp.printStackTrace();
        }

        return dataHashMap;

    }

    public static void getSelectItem(Connection con, HashMap paramMap, String crlName)
            throws SQLException {
        System.out.println("Here in get Item task data");
        for (int j = 0; j < paramMap.size(); j++) {
            String tempStatusString = (String) paramMap.keySet().toArray()[j];

            if (tempStatusString.startsWith(crlName)) {
                String keyValue = (String) paramMap.get(tempStatusString);
                int id = new Integer(Integer.parseInt(tempStatusString.substring((crlName.length()), tempStatusString.length()))).intValue();
                TaskDAO.deleteTask(con, id);
            }

        }

    }

    public static String saveTask(Connection con, HashMap paramHashMap, String message) throws SQLException {

        String taskId = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID);
        String taskMode = (String) paramHashMap.get("selectTskType");
        String taskStartHour = "";
        String taskEndHour = "";

        TaskDTO tdto = new TaskDTO();
        String taskName = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_TEXT_TASK_NAME);

        tdto.setTaskName(taskName);
        tdto.setTaskDescription((String) paramHashMap.get(TaskInterfaceKey.CONTROL_TEXT_TASK_DESCRIPTION));
        boolean checkTask = TaskDAO.checkTaskName(con, taskName);
        if (!checkTask) {

            if (taskId.compareTo("") == 0 || taskId == null
                    || taskId.compareTo("0") == 0) {
                if (taskMode.compareTo("x_2") == 0) {
                    taskStartHour = (String) paramHashMap.get("start_hour");
                    taskEndHour = (String) paramHashMap.get("end_hour");
                    tdto.setTask_set_end_hour(Integer.parseInt(taskEndHour));
                    tdto.setTask_set_start_hour(Integer.parseInt(taskStartHour));
                    tdto.setTask_mode_id(2);
                }
                if (taskMode.compareTo("x_1") == 0) {
                    tdto.setTask_mode_id(1);
                }
                tdto.setTask_type_id(1);
                TaskDAO.insertTask(con, tdto, (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID), "5");
            } else {
                tdto.setTaskId(Integer.parseInt(taskId));
                TaskDAO.updateTask(con, tdto);
            }
        } else {
            message = taskName + " already exists.";
        }
        return message;
    }

    public static HashMap newEditTask(Connection con, HashMap paramHashMap, HashMap dataHashMap) throws SQLException {

        String taskId = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID);
        TaskDTO tdto = new TaskDTO();
        if (taskId.compareTo("") == 0 || taskId == null) {
            tdto.setTaskName("");
            tdto.setTaskDescription("");
            dataHashMap.put(TaskInterfaceKey.DTO_TASK, tdto);
        } else {
            Vector vecTask = TaskDAO.getTaskStatus(con, true, taskId,
                    true, "", 1);
            dataHashMap.put(TaskInterfaceKey.DTO_TASK,
                    ((TaskDTO) vecTask.get(0)));

        }
        dataHashMap.put(TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID, taskId);

        return dataHashMap;
    }

    public static HashMap exportTaskData(Connection con, HashMap paramHashMap, HashMap dataHashMap) throws SQLException, IOException {

        String taskId = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID);
        String path = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        System.out.println("Path is   " + path);
        dataHashMap.put(TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID, taskId);
        Vector simDataVec = TaskDAO.getTaskRows(con, taskId);
        path = path + "export" + taskId + ".csv";
        System.out.println("Path + file name is   " + path);
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
        dataHashMap.put(TaskInterfaceKey.FILE_EXPORT, path);
        FileOutputStream fis = new FileOutputStream(path);
        for (int k = 0; k < simDataVec.size(); k++) {
            SimDataDTO sddto = (SimDataDTO) simDataVec.elementAt(k);
            if (k == 0) {
                String temp = "Sim No, Activation Date,Dcm Name,Dcm Code,Transaction Name,Product Name, Lcs Issue Data \\n";
                fis.write(temp.getBytes());
                fis.flush();
            }
            String activation = "";
            String dcmName = "";
            String dcmCode = "";
            String productName = "";
            String transacionTypeName = "";
            String issue = "";
            if (sddto.getActivation() != null) {
                activation = sddto.getActivation().toString();
            }
            if (sddto.getDcmCode() != null) {
                dcmCode = sddto.getDcmCode().toString();
            }
            if (sddto.getDcmName() != null) {
                dcmName = sddto.getDcmName().toString();
            }
            if (sddto.getProductName() != null) {
                productName = sddto.getProductName().toString();
            }
            if (sddto.getTransactionTypeName() != null) {
                transacionTypeName = sddto.getTransactionTypeName().toString();
            }
            if (sddto.getLcsIssueDate() != null) {
                issue = sddto.getLcsIssueDate().toString();
            }
            String temp = sddto.getSimNo() + ","
                    + activation + ","
                    + dcmName + ","
                    + dcmCode + ","
                    + transacionTypeName + ","
                    + productName + ","
                    + issue
                    + "\n";
            fis.write(temp.getBytes());
            fis.flush();

        }
        fis.close();
        System.out.println("fis closed");

        return dataHashMap;
    }

    public static HashMap newDaliyTask(Connection con, HashMap paramHashMap, HashMap dataHashMap, String userID) throws SQLException {
        String taskName = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_TEXT_TASK_NAME);
        String dayStart = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_INPUT_DAY_STAER_FROM_DATE);
        String sql = (String) paramHashMap.get("message1");
        String startHour = (String) paramHashMap.get("start_hour");
        String endHour = (String) paramHashMap.get("end_hour");
        String taskDescription = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_TEXT_TASK_DESCRIPTION);

        DailyDTO ddto = new DailyDTO();
        ddto.setDayStart(dayStart);
        ddto.setHourEnd(Integer.parseInt(endHour));
        ddto.setHourStart(Integer.parseInt(startHour));
        ddto.setSQL(sql);

        ddto.setTaskDesc(taskDescription);


        TaskDTO tdto = new TaskDTO();
        tdto.setTaskName(taskName);
        tdto.setTaskDescription(taskDescription);
        tdto.setTask_type_id(3);
        int taskid = TaskDAO.insertTask(con, tdto, userID, "1");

        ddto.setTaskId(taskid + "");
        TaskDAO.insertDailyTask(con, ddto);
        return dataHashMap;
    }

    public static HashMap checkDailyValues(Connection con, HashMap paramHashMap, HashMap dataHashMap, String userID) throws SQLException {

        String taskName = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_TEXT_TASK_NAME);
        String dayStart = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_INPUT_DAY_STAER_FROM_DATE);
        String sql = (String) paramHashMap.get("message1");
        String startHour = (String) paramHashMap.get("start_hour");
        String endHour = (String) paramHashMap.get("end_hour");
        String taskDescription = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_TEXT_TASK_DESCRIPTION);

        DailyDTO ddto = new DailyDTO();
        ddto.setDayStart(dayStart);
        ddto.setHourEnd(Integer.parseInt(endHour));
        ddto.setHourStart(Integer.parseInt(startHour));
        ddto.setSQL(sql);
        ddto.setTaskName(taskName);
        ddto.setTaskDesc(taskDescription);
        dataHashMap.put(TaskInterfaceKey.Daily_DTO_FLY_VALUES, ddto);

        String message[] = new String[5];
        boolean flag = false;

        // check task name
        boolean repeatedName = TaskDAO.checkTaskName(con, taskName);
        System.out.println("step 4 " + repeatedName);
        if (repeatedName) {
            message[0] = "change task name. This task name already exists.";
            flag = true;
        }
        // check date		  
        System.out.println("day start is " + dayStart);

        if (checkDateForDailyTask(dayStart)) {
            message[1] = "change start day.";
            flag = true;
        }


        // check task sql
        if (sql.length() > 4000) {
            System.out.println("step 7 " + sql.length());

            message[2] = "check sql statement characters not greater than 4000";
            flag = true;
        } else {

            message[3] = TaskDAO.checkSQL(con, sql);
            if (message[3] != null) {
                flag = true;
            }
        }
        for (int i = 0; i < message.length; i++) {
            System.out.println("message in handler is " + message[i]);
        }

        if (flag) {
            message[4] = null;
        } else {
            message[4] = "";
        }
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, message);


        return dataHashMap;
    }

    public static HashMap newContractTask(Connection con, HashMap paramHashMap, HashMap dataHashMap, String userID) throws SQLException {
        String BatchFile = "1";
        String BatchType = "2";
        String CIF = "1";
        String SFR = "2";

        String contractFlag = (String) paramHashMap.get("contractFlag");
        String selectType = (String) paramHashMap.get("selectType");


        String contractTaskName = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_INPUT_NAME_CONTRACT_NAME);
        String fromDateValue = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_INPUT_NAME_FROM_DATE);
        String toDateValue = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_INPUT_NAME_TO_DATE);

        String contrackType = "";
        String contrackTypeValue = "";
        String fileValue = "";
        String conditionsSearch = "";
        String conditionsUpdate = "";


        if (selectType.compareTo(BatchFile) == 0) {
            fileValue = (String) paramHashMap.get("filePath");
            String contents = "";
            if (fileValue != null) {
                File aFile = new File(fileValue);


                try {
                    BufferedReader input = new BufferedReader(new FileReader(aFile));
                    try {
                        String line = null; //not declared within while loop

                        while ((line = input.readLine()) != null) {
                            String[] fields = line.split(",");
                            if (fields != null && fields.length != 0) {
                                contents += line + ",";
                            }
                        }
                    } finally {
                        input.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                contrackTypeValue = contents.substring(0, contents.length() - 1);
            }



        }
        if (contrackTypeValue == null) {
            contrackTypeValue = "";
        }

        if (contractFlag.compareTo(SFR) == 0) {
            contrackType = "SFR";
        }
        if (contractFlag.compareTo(CIF) == 0) {
            contrackType = "CIF";
            if (selectType.compareTo(BatchType) == 0) {
                contrackTypeValue = (String) paramHashMap.get("batch_type");
            }
        }

        Vector searchVec = constructSelectList(paramHashMap, TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_SEARCH_SELECT_LIST);

//		boolean isFound = false;
        for (int i = 0; i < searchVec.size(); i++) {
            int ss = ((Integer) searchVec.get(i)).intValue();

            if (ss == 1) {
                conditionsSearch += "OR (LCS_dCM_ID IS NULL OR LCS_DCM_ID = -1) ";
            }
            if (ss == 2) {
                conditionsSearch += "OR LCS_INIT_DATE IS NULL ";
            }
            if (ss == 3) {
                conditionsSearch += "OR (LCS_CONTRACT_TYPE_ID IS NULL OR LCS_CONTRACT_tYPE_ID = -1 ) ";
            }
            if (ss == 7) {
                conditionsSearch += "OR LCS_ISSUE_DATE IS NULL  ";
            }
            if (ss == 5) {
                conditionsSearch += "OR (RATE_PLAN IS NULL OR RATE_PLAN = -1 ) ";
            }
            if (ss == 6) {
                conditionsSearch += "OR (SERVICE_PACKAGE IS NULL OR SERVICE_PACKAGE = -1 ) ";
            }



        }
        if (conditionsSearch.compareTo("") != 0) {
            conditionsSearch = conditionsSearch.substring(3, conditionsSearch.length());
            System.out.println("conditionsSearch after cutting is " + conditionsSearch);
        }

        Vector updateVec = constructSelectList(paramHashMap, TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST);
//		System.out.println("updateVec size is from paramhashmap "+updateVec.size());
//		System.out.println("searchVec size is from paramhashmap "+searchVec.size());
        for (int i = 0; i < updateVec.size(); i++) {
            int ss = ((Integer) updateVec.get(i)).intValue();

            if (ss == 1) {
                conditionsUpdate += " LCS_DCM_ID = #1,";
            }
            if (ss == 2) {
                conditionsUpdate += " LCS_INIT_DATE = NVL(LCS_INIT_DATE, GET_SIM_ACT_DATE_BY_CATEGORY (#0, (select product_category_id from gen_product where product_id =#2)) ),";
            }
            if (ss == 3) {
                conditionsUpdate += " LCS_CONTRACT_TYPE_ID= #3,";
            }
            if (ss == 4) {
                conditionsUpdate += " TRANSACTION_TYPE_ID = #4,";
            }
            if (ss == 7) {
                conditionsUpdate += " LCS_ISSUE_DATE = #7,";
            }
            if (ss == 5) {
                conditionsUpdate += " RATE_PLAN = #5,";
            }
            if (ss == 6) {
                conditionsUpdate += " SERVICE_PACKAGE = #6,";
            }

        }

        if (conditionsUpdate.compareTo("") != 0) {
            conditionsUpdate = conditionsUpdate.substring(0, conditionsUpdate.length() - 1);
            System.out.println("conditionsUpdate after cutting is " + conditionsUpdate);
        }

//		System.out.println("contrackType is "+contrackType);
//		System.out.println("contrackTypeValue is "+contrackTypeValue);
//		System.out.println("conditionsSearch is "+conditionsSearch);
//		System.out.println("conditionsUpdate is "+conditionsUpdate);
//		System.out.println("selectType is "+selectType);
//		System.out.println("contractFlag is "+contractFlag);








        TaskDTO tdto = new TaskDTO();
        tdto.setTaskName(contractTaskName);
        tdto.setTaskDescription("");
        tdto.setTask_type_id(2);
        int taskid = TaskDAO.insertTask(con, tdto, userID, "1");

        ContractDTO cdto = new ContractDTO();
        cdto.setId(new Integer(taskid));
        if (conditionsSearch.compareTo("") != 0) {
            cdto.setCONTRACT_SEARCH_CRITERIA(conditionsSearch);
        }
        if (contrackType.compareTo("") != 0) {
            cdto.setCONTRACT_TYPE(contrackType);
        }
        if (conditionsUpdate.compareTo("") != 0) {
            cdto.setCONTRACT_UPDATE_CRITERIA(conditionsUpdate);
        }
        cdto.setSTART_DATE(fromDateValue);
        cdto.setEND_DATE(toDateValue);
        cdto.setCif_type(Integer.parseInt(selectType));
        System.out.println("select type is " + selectType);
        if (contrackTypeValue.compareTo("") != 0) {
            cdto.setCONTRACT_TYPE_VALUE(contrackTypeValue);
        }

        TaskDAO.insertContractForTask(con, cdto);





        dataHashMap.put(TaskInterfaceKey.HASHMAP_BATCH_TYPES, TaskDAO.getBatchTypes(con));
        return dataHashMap;
    }

    public static Vector constructSelectList(HashMap paramHashMap, String objInHashMap) {
        //int     nNumParam ;
        //String  fieldID ;
        Vector vecSelectList = null;

        try {
            vecSelectList = new Vector();
//        System.out.println("param size is "+paramHashMap.size());
//        System.out.println("objInHashMap is "+objInHashMap);

            // --------------------- constructing select list -----------------------

            //nNumParam = new Integer ( (String) paramHashMap.get (ReportInterfaceKey.PARAM_SAVE_CUSTOMIZED_REPORT_SELECT_NUM) ) ;


            String[] arrSelect = null;
            Object objSelect = paramHashMap.get(objInHashMap);
//        System.out.println("objSelect is "+objSelect);
            if (objSelect == null) // if the object is null
            {
                arrSelect = new String[0];  // initialize with empty array
            } else if (!(objSelect instanceof String)) // if the object is a genuine array
            {
                //Utility.logger.debug("class name = "+ o.getClass().getName());

                arrSelect = (String[]) objSelect;
                //for (int i = 0;i<applied_user_warnings.length;i++)
                //  Utility.logger.debug("warning " + applied_user_warnings[i]);

            } else if (objSelect instanceof String) // if the object is just a string
            {
                arrSelect = new String[1];
                arrSelect[0] = (String) objSelect;
            }
//       System.out.println("arrSelect.length is "+arrSelect.length);
            for (int i = 0; i < arrSelect.length; i++) {
                String ele = arrSelect[i];
                boolean flag = false;
                if (ele != null) {
                    if (ele.compareTo("") != 0) {
                        flag = true;
                    }
                }
                System.out.println("arrSelect.length is " + arrSelect.length);

                System.out.println("arrSelect[" + i + "] is " + arrSelect[i]);
                if (flag) {
                    String tempString[] = arrSelect[i].split(",");
                    for (int o = 0; o < tempString.length; o++) {

                        vecSelectList.addElement(new Integer(Integer.parseInt(tempString[o])));
                    }
                }

            }
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return vecSelectList;
    }

    public static boolean checkDateForDailyTask(String dateStart) {
        boolean flag = false;
        Date currentDate = new Date(System.currentTimeMillis());
        int currentDay = currentDate.getDate();
        int currentYear = currentDate.getYear() + 1900;
        int currentmonth = currentDate.getMonth() + 1;

        int startDay = Integer.parseInt(dateStart.substring(0, dateStart.indexOf("/")));
        System.out.println("startDay is " + startDay);
        int startMonth = Integer.parseInt(dateStart.substring(dateStart.indexOf("/") + 1, dateStart.lastIndexOf("/")));
        System.out.println("startMonth is " + startMonth);
        int startYear = Integer.parseInt(dateStart.substring(dateStart.lastIndexOf("/") + 1, dateStart.length()));
        System.out.println("startYear is " + startYear);

        if (currentYear > startYear) {
            System.out.println("step 1");
            flag = true;
        }

        if (currentmonth > startMonth) {
            System.out.println("step 2");
            if (!flag) {
                flag = true;
            }
        }


        if (currentDay > startDay) {
            System.out.println("step 3");
            System.out.println("step 3 " + flag);
            if (!flag) {
                flag = true;
            }


        }




        return flag;
    }

    public static HashMap viewArbuTaskPage(Connection con, HashMap dataHashMap) throws SQLException {
        HashMap hm = TaskDAO.getArbuPartitons(con);
        dataHashMap.put(TaskInterfaceKey.POST_PAID_PARTITIONS_VEC, hm.get("post"));
        dataHashMap.put(TaskInterfaceKey.PRE_PAID_PARTITIONS_VEC, hm.get("pre"));
        return dataHashMap;
    }

    public static HashMap newArbuTask(Connection con, HashMap paramHashMap, HashMap dataHashMap, String userID) throws SQLException {
        dataHashMap = viewArbuTaskPage(con, dataHashMap);
        String arbuSelectedType = (String) paramHashMap.get("selectTskType");
        String taskName = (String) paramHashMap.get(TaskInterfaceKey.CONTROL_TEXT_TASK_NAME);
        String partitonDate = "";
        String arbuType = "";
        String conditionsUpdate = "";

        if (arbuSelectedType.compareTo("x_1") == 0) {
            String temp = (String) paramHashMap.get("prepaidSelect");
            partitonDate = (temp).substring(1, temp.length());
            arbuType = "pre";
        }

        if (arbuSelectedType.compareTo("x_2") == 0) {
            String temp = (String) paramHashMap.get("postpaidSelect");
            partitonDate = (temp).substring(1, temp.length());
            arbuType = "post";
        }
        TaskDTO tdto = new TaskDTO();
        tdto.setTaskName(taskName);
        tdto.setTaskDescription("");
        tdto.setTask_type_id(4);
        int taskid = TaskDAO.insertTask(con, tdto, userID, "1");


        Vector updateVec = constructSelectList(paramHashMap, TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST);

        for (int i = 0; i < updateVec.size(); i++) {
            int ss = ((Integer) updateVec.get(i)).intValue();

            if (ss == 1) {
                conditionsUpdate += " LCS_DCM_ID = #1,";
            }
            if (ss == 2) {
                conditionsUpdate += " ACTIVATION_DATE = NVL(ACTIVATION_DATE, GET_SIM_ACT_DATE_BY_CATEGORY (#0, (select product_category_id from gen_product where product_id =#2)) ),";
            }
            if (ss == 3) {
                conditionsUpdate += " LCS_CONTRACT_TYPE_ID= #3,";
            }
            if (ss == 4) {
                conditionsUpdate += " TRANSACTION_TYPE_ID = #4,";
            }

        }

        if (conditionsUpdate.compareTo("") != 0) {
            conditionsUpdate = conditionsUpdate.substring(0, conditionsUpdate.length() - 1);

        }

        TaskDAO.insertArbuTask(con, arbuType, partitonDate, taskid + "", conditionsUpdate);


        return dataHashMap;
    }

    private static String getUserId(String userId, HashMap paramHashMap) {
        userId = userId == null ? (String) ((HttpServletRequest) paramHashMap.get(InterfaceKey.HASHMAP_KEY_REQUEST_FROM_SERVLET)).getSession(false).getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID) : userId;
        return userId;
    }
}