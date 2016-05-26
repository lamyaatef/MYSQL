package com.mobinil.sds.core.system.te.dto;

public class DailyDTO {
private String dayStart;
private int hourStart;
private int hourEnd;
private String SQL;
private String taskId;
private String taskName;
private String taskDesc;


public String getDayStart() {
	return dayStart;
}
public void setDayStart(String dayStart) {
	this.dayStart = dayStart;
}
public int getHourStart() {
	return hourStart;
}
public void setHourStart(int hourStart) {
	this.hourStart = hourStart;
}
public int getHourEnd() {
	return hourEnd;
}
public void setHourEnd(int hourEnd) {
	this.hourEnd = hourEnd;
}
public String getSQL() {
	return SQL;
}
public void setSQL(String sql) {
	SQL = sql;
}
public String getTaskId() {
	return taskId;
}
public void setTaskId(String taskId) {
	this.taskId = taskId;
}
public void setTaskName(String taskName) {
	this.taskName = taskName;
}
public String getTaskName() {
	return taskName;
}
public void setTaskDesc(String taskDesc) {
	this.taskDesc = taskDesc;
}
public String getTaskDesc() {
	return taskDesc;
}

}
