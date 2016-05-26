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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mabdelaal
 */
@Entity
@Table(name = "ACCM_REV_UPLOAD")
public class AccmRevUpload extends Model implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACCM_REV_ID")
    private BigDecimal accmRevId;
    @Size(max = 20)
    @Column(name = "CUSTOMER_CODE")
    private String customerCode;
    @Size(max = 200)
    @Column(name = "ACCOUNT_MGR")
    private String accountMgr;
    @Column(name = "SUM_OF_INVOICE")
    private BigInteger sumOfInvoice;
    @Column(name = "SUM_OF_ACTIVE")
    private BigInteger sumOfActive;
    @Column(name = "FILE_ID")
    private BigInteger fileId;

    public AccmRevUpload() {
    }

    public AccmRevUpload(BigDecimal accmRevId) {
        this.accmRevId = accmRevId;
    }

    public BigDecimal getAccmRevId() {
        return accmRevId;
    }

    public void setAccmRevId(BigDecimal accmRevId) {
        this.accmRevId = accmRevId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getAccountMgr() {
        return accountMgr;
    }

    public void setAccountMgr(String accountMgr) {
        this.accountMgr = accountMgr;
    }

    public BigInteger getSumOfInvoice() {
        return sumOfInvoice;
    }

    public void setSumOfInvoice(BigInteger sumOfInvoice) {
        this.sumOfInvoice = sumOfInvoice;
    }

    public BigInteger getSumOfActive() {
        return sumOfActive;
    }

    public void setSumOfActive(BigInteger sumOfActive) {
        this.sumOfActive = sumOfActive;
    }

    public BigInteger getFileId() {
        return fileId;
    }

    public void setFileId(BigInteger fileId) {
        this.fileId = fileId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accmRevId != null ? accmRevId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccmRevUpload)) {
            return false;
        }
        AccmRevUpload other = (AccmRevUpload) object;
        if ((this.accmRevId == null && other.accmRevId != null) || (this.accmRevId != null && !this.accmRevId.equals(other.accmRevId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mobinil.sds.core.system.aacm.model.AccmRevUpload[ accmRevId=" + accmRevId + " ]";
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            setAccmRevId(res.getBigDecimal("ACCM_REV_ID"));
            setAccountMgr(res.getString("ACCOUNT_MGR"));
            setCustomerCode(res.getString("CUSTOMER_CODE"));
            setSumOfInvoice(res.getBigDecimal("SUM_OF_INVOICE").toBigInteger());
            setSumOfActive(res.getBigDecimal("SUM_OF_ACTIVE").toBigInteger());
            setFileId(res.getBigDecimal("FILE_ID").toBigInteger());
        } catch (Exception e) {
        }
            
        
    }
    
}
