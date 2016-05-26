/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.aacm.model;

import com.mobinil.sds.core.system.Model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author mabdelaal
 */
@Entity
@Table(name = "ACCM_REV_FILE")
public class AccmRevFile extends Model  implements Serializable 
{
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Column(name = "FILE_ID")
    private BigDecimal fileId;
    @Column(name = "SUM_INSERTED_ROWS")
    private BigInteger sumInsertedRows;
    @Column(name = "YEAR_NUMER")
    private BigInteger yearNumer;
    @Column(name = "MONTH_NUMER")
    private BigInteger monthNumer;
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column(name = "USER_ID")
    private BigInteger userId;

    public AccmRevFile() {
    }

    public AccmRevFile(BigDecimal fileId) {
        this.fileId = fileId;
    }

    public BigDecimal getFileId() {
        return fileId;
    }

    public void setFileId(BigDecimal fileId) {
        this.fileId = fileId;
    }

    public BigInteger getSumInsertedRows() {
        return sumInsertedRows;
    }

    public void setSumInsertedRows(BigInteger sumInsertedRows) {
        this.sumInsertedRows = sumInsertedRows;
    }

    public BigInteger getYearNumer() {
        return yearNumer;
    }

    public void setYearNumer(BigInteger yearNumer) {
        this.yearNumer = yearNumer;
    }

    public BigInteger getMonthNumer() {
        return monthNumer;
    }

    public void setMonthNumer(BigInteger monthNumer) {
        this.monthNumer = monthNumer;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fileId != null ? fileId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccmRevFile)) {
            return false;
        }
        AccmRevFile other = (AccmRevFile) object;
        if ((this.fileId == null && other.fileId != null) || (this.fileId != null && !this.fileId.equals(other.fileId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mobinil.sds.core.system.aacm.model.AccmRevFile[ fileId=" + fileId + " ]";
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            
        
        setFileId(res.getBigDecimal("FILE_ID"));
        setCreationDate(res.getDate("CREATION_DATE"));
        setMonthNumer(BigInteger.valueOf(res.getInt("MONTH_NUMER")));        
        setYearNumer(BigInteger.valueOf(res.getInt("YEAR_NUMER")));
        setSumInsertedRows(BigInteger.valueOf(res.getInt("SUM_INSERTED_ROWS")));
        setUserId(BigInteger.valueOf(res.getInt("USER_ID")));
        } catch (Exception e) {
        }
    }
    
}
