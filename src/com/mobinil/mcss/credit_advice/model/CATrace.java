/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.model;


import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;

/**
 *
 * @author JOE
 */
public class CATrace {

    private long filetraceId;
    private long cafileTraceId;
    private long cafileTraceUserId;
    private Date cafileTraceDate;
    private long cafileTraceStatusId;
    private StatusFile statusId;

    public StatusFile getStatusId() {
        return statusId;
    }

    public void setStatusId(StatusFile statusId) {
        this.statusId = statusId;
    }

    public long getCafileTraceId() {
        return cafileTraceId;
    }

    public void setCafileTraceId(long cafileTraceId) {
        this.cafileTraceId = cafileTraceId;
    }

    public long getCafileTraceUserId() {
        return cafileTraceUserId;
    }

    public void setCafileTraceUserId(long cafileTraceUserId) {
        this.cafileTraceUserId = cafileTraceUserId;
    }

    public long getFiletraceId() {
        return filetraceId;
    }

    public void setFiletraceId(long filetraceId) {
        this.filetraceId = filetraceId;
    }

    public Date getCafileTraceDate() {
        return cafileTraceDate;
    }

    public void setCafileTraceDate(Date cafileTraceDate) {
        this.cafileTraceDate = cafileTraceDate;
    }

    public long getCafileTraceStatusId() {
        return cafileTraceStatusId;
    }

    public void setCafileTraceStatusId(long cafileTraceStatusId) {
        this.cafileTraceStatusId = cafileTraceStatusId;
    }

}
