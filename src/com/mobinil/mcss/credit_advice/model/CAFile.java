/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JOE
 */
public class CAFile {

    private long fileId;
    private List<CAMemo> camMemos = new ArrayList<CAMemo>();
    private long printing;

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public long getPrinting() {
        return printing;
    }

    public void setPrinting(long printing) {
        this.printing = printing;
    }

    public List<CAMemo> getCamMemos() {
        return camMemos;
    }

    public void setCamMemos(List<CAMemo> camMemos) {
        this.camMemos = camMemos;
    }
}
