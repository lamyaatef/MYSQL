/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dp.dpManagement.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Gado
 */
@Entity
@Table(name="GEN_USER")
public class GenUser implements Serializable{
    @Id
    @Column(name="USER_ID")
    private Long userId;
    @OneToOne
    @JoinColumn(name="USER_ID")
    private GenPerson person;
    

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the name
     */
    public GenPerson getPerson() {
        return person;
    }

    /**
     * @param name the name to set
     */
    public void setPerson(GenPerson person) {
        this.person = person;
    }
    
}
