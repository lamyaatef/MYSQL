package com.mobinil.sds.core.system.gn.reports.dto;
import java.util.Vector;
public class GroupDTO 
{
private String groupName;
private Integer groupId;
private Vector groupReport;
private String groupDesc;
private Integer groupStatus;

public GroupDTO()
{
}
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
   }

   public Vector getGroupReport() {
        return groupReport;
    }

    public void setGroupReport(Vector groupReport) {
        this.groupReport = groupReport;
    }

      public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupId() {
        return groupId;
    }

   public String getGroupDesc() {
        return groupDesc;
    }

    public Integer getGroupStatus() {
        return groupStatus;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public void setGroupStatus(Integer groupStatus) {
        this.groupStatus = groupStatus;
    }
}