/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.xyzpos.model;

import com.mobinil.mcss.dp.dpManagement.model.GenUser;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Gado
 */
@Entity
@Table(name="new_xyz_bulk_av_pos_backup")
public class DeletePOS {

    @Id
    @Column(name="ID")
    private String posCode;
    @Transient
    private String posName;
    @ManyToOne
    @JoinColumn(name="USER_ID")
    private GenUser  USER_ID ;
    @Column (name="entry_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date enrtyDate;


    /**
     * @return the posCode
     */
    public String getPosCode() {
        return posCode;
    }

    /**
     * @param posCode the posCode to set
     */
    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    /**
     * @return the posName
     */
    public String getPosName() {
        return posName;
    }

    /**
     * @param posName the posName to set
     */
    public void setPosName(String posName) {
        this.posName = posName;
    }

    

    /**
     * @return the enrtyDate
     */
    public Date getEnrtyDate() {
        return enrtyDate;
    }

    /**
     * @param enrtyDate the enrtyDate to set
     */
    public void setEnrtyDate(Date enrtyDate) {
        this.enrtyDate = enrtyDate;
    }

    /**
     * @return the USER_ID
     */
    public GenUser getUSER_ID() {
        return USER_ID;
    }

    /**
     * @param USER_ID the USER_ID to set
     */
    public void setUSER_ID(GenUser USER_ID) {
        this.USER_ID = USER_ID;
    }
}
