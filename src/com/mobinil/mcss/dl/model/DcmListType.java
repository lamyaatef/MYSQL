/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dl.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Gado
 */
@Entity
@Table(name="DL_LIST_TYPE")
public class DcmListType implements Serializable{
    @Id
    @Column(name="DL_LIST_TYPE_ID")        
    private long typeId;
    @Column(name="DL_LIST_TYPE_NAME") 
    private String typeName;
    
    /**
     * @return the typeId
     */
    public long getTypeId() {
        return typeId;
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    /**
     * @return the typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * @param typeName the typeName to set
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
}
