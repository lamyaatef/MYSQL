/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dp.dpManagement.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 *
 * @author Ahmed Adel
 */
@Entity
@Table(name="GEN_CHANNEL")
public class GenChannel implements Serializable{
    @Id
    @Column (name = "CHANNEL_ID")
    private long channelId;
   
    @Column (name = "CHANNEL_NAME")
    private String channelName;

    /**
     * @return the channelId
     */
    public long getChannelId() {
        return channelId;
    }

    /**
     * @param channelId the channelId to set
     */
    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    /**
     * @return the channelName
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * @param channelName the channelName to set
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

}
