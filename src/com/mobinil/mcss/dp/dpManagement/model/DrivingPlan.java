/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dp.dpManagement.model;

import com.mobinil.mcss.dp.factor.model.Factor;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.validator.*;

/**
 *
 * @author Ahmed Adel
 */
@Entity
@Table(name = "DP_DRIVING_PLAN")
public class DrivingPlan implements Serializable {


    private DPKey id;
    @Id
    @Column(name = "DP_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_DRIVING_PLAN_SEQ")
    @SequenceGenerator(name = "S_DRIVING_PLAN_SEQ", sequenceName = "DRIVING_PLAN_SEQ", allocationSize = 1)
    private long dpId;
   
    @Column(name = "DP_Name")
    private String planName;
    @ManyToOne
    @JoinColumn(name = "DP_STATUS_TYPE_ID")
    private DPStatus planStatus;
    @ManyToOne
    @JoinColumn(name = "DP_PAYMENT_TYPE_ID")
    private PaymentType paymentType;
    @ManyToOne
    @JoinColumn(name ="DP_COMMISION_CATEGORY")
    private CommissionCategory commissionCategory;
    @Column(name="USER_ID")
    private String user;
    @OneToMany(mappedBy="drivingPlan",targetEntity=Factor.class,fetch= FetchType.EAGER,cascade= CascadeType.ALL)
    private Set<Factor> factors;
    
    
 

    /**
     * @return the id
     */
    public DPKey getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(DPKey id) {
        this.id = id;
    }

    /**
     * @return the planName
     */
    public String getPlanName() {
        return planName;
    }

    /**
     * @param planName the planName to set
     */
    public void setPlanName(String planName) {
        this.planName = planName;
    }

    /**
     * @return the planStatus
     */
    public DPStatus getPlanStatus() {
        return planStatus;
    }

    /**
     * @param planStatus the planStatus to set
     */
    public void setPlanStatus(DPStatus planStatus) {
        this.planStatus = planStatus;
    }

    /**
     * @return the dpId
     */
    public long getDpId() {
        return dpId;
    }

    /**
     * @param dpId the dpId to set
     */
    public void setDpId(long dpId) {
        this.dpId = dpId;
    }

    /**
     * @return the paymentType
     */
    public PaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    public Set<Factor> getFactors() {
        return factors;
    }

    public void setFactors(Set<Factor> factors) {
        this.factors = factors;
    }

    /**
     * @return the commissionType
     */
    public CommissionCategory getCommissionCategory() {
        return commissionCategory;
    }

    /**
     * @param commissionType the commissionType to set
     */
    public void setCommissionCategory(CommissionCategory commissionCategory) {
        this.commissionCategory = commissionCategory;
    }
}
