/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.level_relation_management.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samy
 */
public class LevelZeroToLevelOne {

    private int id;
    private String levelZeroID;
    private String levelZeroName;
    private String levelOneDcmCode;
    private String levelOneName;
    private String flag;
    private boolean guaranteeFlag;

    public LevelZeroToLevelOne(ResultSet rs) {
        try {
            this.id = rs.getInt("ID");
            this.levelZeroID = rs.getString("LEVELZEROID");
            this.levelZeroName = rs.getString("LEVELZERONAME");
            this.levelOneDcmCode = rs.getString("LEVELONEDCMCODE");
            this.levelOneName = rs.getString("LEVELONENAME");
            this.flag = rs.getString("FLAG");
            if (flag != null) {
                if (flag.equals("0")) {
                    this.guaranteeFlag = false;
                } else {
                    this.guaranteeFlag = true;
                }
            } else {
                this.guaranteeFlag = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LevelZeroToLevelOne.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LevelZeroToLevelOne() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevelZeroName() {
        return levelZeroName;
    }

    public void setLevelZeroName(String levelZeroName) {
        this.levelZeroName = levelZeroName;
    }

    public String getLevelZeroID() {
        return levelZeroID;
    }

    public void setLevelZeroID(String levelZeroID) {
        this.levelZeroID = levelZeroID;
    }

    public String getLevelOneDcmCode() {
        return levelOneDcmCode;
    }

    public void setLevelOneDcmCode(String levelOneDcmCode) {
        this.levelOneDcmCode = levelOneDcmCode;
    }

    public String getLevelOneName() {
        return levelOneName;
    }

    public void setLevelOneName(String levelOneName) {
        this.levelOneName = levelOneName;
    }

    public boolean isGuaranteeFlag() {
        return guaranteeFlag;
    }

    public void setGuaranteeFlag(boolean guaranteeFlag) {
        this.guaranteeFlag = guaranteeFlag;
    }
}
