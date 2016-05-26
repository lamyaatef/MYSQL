/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.level_relation_management.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SAND
 */
public class XYZBulkEmail {
    private int id;
    private String sendEmail;
    private String creationDate;
    private String posCode;
    private String senderName;

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    private String subject;

    
    public XYZBulkEmail(ResultSet rs) {
        try {
            this.id = rs.getInt("ID");
            this.sendEmail = rs.getString("SENDEREMAILADDRESS");
            this.posCode = rs.getString("POSCODE");
            this.senderName = rs.getString("SENDERNAME");
            this.subject = rs.getString("SUBJECT");
            this.creationDate = rs.getDate("CREATIONDATE").toString();
            
        } catch (SQLException ex) {
            Logger.getLogger(LevelZeroToLevelOne.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setSenderEmail(String sendEmail) {
        this.sendEmail = sendEmail;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public String getSenderEmail() {
        return sendEmail;
    }


    public String getPosCode() {
        return posCode;
    }

    public String getSubject() {
        return subject;
    }
   
}
