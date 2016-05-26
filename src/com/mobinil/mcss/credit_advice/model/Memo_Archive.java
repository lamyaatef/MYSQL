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
 * @author SAND
 */
public class Memo_Archive {

    private long id;
    private long memoId;
    private long crdFileTraceId;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the memoId
     */
    public long getMemoId() {
        return memoId;
    }

    /**
     * @param memoId the memoId to set
     */
    public void setMemoId(long memoId) {
        this.memoId = memoId;
    }

    /**
     * @return the crdFileTraceId
     */
    public long getCrdFileTraceId() {
        return crdFileTraceId;
    }

    /**
     * @param crdFileTraceId the crdFileTraceId to set
     */
    public void setCrdFileTraceId(long crdFileTraceId) {
        this.crdFileTraceId = crdFileTraceId;
    }
    /**
     * @return the id
     */
}
