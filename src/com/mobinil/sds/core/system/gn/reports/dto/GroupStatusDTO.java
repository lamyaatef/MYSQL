package com.mobinil.sds.core.system.gn.reports.dto;

public class GroupStatusDTO 
{
private Integer id;
private String status;
  public GroupStatusDTO()
  {
  }
  public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}