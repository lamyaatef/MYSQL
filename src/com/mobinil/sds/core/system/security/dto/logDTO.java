package com.mobinil.sds.core.system.security.dto;

public class logDTO 
{
    private Integer id;
    private String userId;
    private String userName;
    private String userIp;
    private String actionName;
    private String actionUrl;
    private String actionTime;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getActionTime() {
        return actionTime;
    }

  public logDTO()
  {
  }

}