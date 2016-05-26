package com.mobinil.sds.core.system.te.dto;

import java.sql.Connection;
import java.util.Date;

/**
 *
 * @author mabdelaal
 */
public class TaskDTO {

    private int taskId;
    private String taskName;
    private int userId;
    private int taskStatusId;
    private String taskDescription;
    private Date taskCreation;
    private Date taskEnd;
    private Connection con;
    private String tableName;
    private int taskCurrentStatusId;
    private String taskStatusTypeName;
    private int task_type_id;
    private int task_set_id;
    private int task_set_start_hour;
    private int task_set_end_hour;
    private int task_id;
    private int task_mode_id;

    public int getTask_set_id() {
        return task_set_id;
    }

    public void setTask_set_id(int task_set_id) {
        this.task_set_id = task_set_id;
    }

    public int getTask_set_start_hour() {
        return task_set_start_hour;
    }

    public void setTask_set_start_hour(int task_set_start_hour) {
        this.task_set_start_hour = task_set_start_hour;
    }

    public int getTask_set_end_hour() {
        return task_set_end_hour;
    }

    public void setTask_set_end_hour(int task_set_end_hour) {
        this.task_set_end_hour = task_set_end_hour;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getTask_mode_id() {
        return task_mode_id;
    }

    public void setTask_mode_id(int task_mode_id) {
        this.task_mode_id = task_mode_id;
    }

    public int getTask_type_id() {
        return task_type_id;
    }

    public void setTask_type_id(int task_type_id) {
        this.task_type_id = task_type_id;
    }

    /**
     * @return the taskId
     */
    public int getTaskId() {
        return taskId;
    }

    /**
     * @param taskId the taskId to set
     */
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the taskStatusId
     */
    public int getTaskStatusId() {
        return taskStatusId;
    }

    /**
     * @param taskStatusId the taskStatusId to set
     */
    public void setTaskStatusId(int taskStatusId) {
        this.taskStatusId = taskStatusId;
    }

    /**
     * @return the taskDescription
     */
    public String getTaskDescription() {
        return taskDescription;
    }

    /**
     * @param taskDescription the taskDescription to set
     */
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    /**
     * @return the taskCreation
     */
    public Date getTaskCreation() {
        return taskCreation;
    }

    /**
     * @param taskCreation the taskCreation to set
     */
    public void setTaskCreation(Date taskCreation) {
        this.taskCreation = taskCreation;
    }

    /**
     * @return the con
     */
    public Connection getCon() {
        return con;
    }

    /**
     * @param con the con to set
     */
    public void setCon(Connection con) {
        this.con = con;
    }

    /**
     * @return the taskName
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * @param taskName the taskName to set
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the taskStatusTypeName
     */
    public String getTaskStatusTypeName() {
        return taskStatusTypeName;
    }

    /**
     * @param taskStatusTypeName the taskStatusTypeName to set
     */
    public void setTaskStatusTypeName(String taskStatusTypeName) {
        this.taskStatusTypeName = taskStatusTypeName;
    }

    /**
     * @return the taskCurrentStatusId
     */
    public int getTaskCurrentStatusId() {
        return taskCurrentStatusId;
    }

    /**
     * @param taskCurrentStatusId the taskCurrentStatusId to set
     */
    public void setTaskCurrentStatusId(int taskCurrentStatusId) {
        this.taskCurrentStatusId = taskCurrentStatusId;
    }

    public Date getTaskEnd() {
        return taskEnd;
    }

    public void setTaskEnd(Date taskEnd) {
        this.taskEnd = taskEnd;
    }
}
