/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dp.dpManagement.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *
 * @author Gado
 */
@Entity
@Table(name="MONTHS")
public class Month implements Serializable {
   
    @Id
    @Column(name="ID")
    private Integer index;
    @Column(name="Name")
    private String monthName;
   
    

    public Month() {
    }

    /**
     * @return the index
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    /**
     * @return the monthName
     */
    public String getMonthName() {
        return monthName;
    }

    /**
     * @param monthName the monthName to set
     */
    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }
    
   
}
