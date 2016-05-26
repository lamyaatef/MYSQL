package com.mobinil.sds.core.system.cam.deduction.model;

import java.sql.Date;
import java.util.Vector;

public class DeductionModel {

    private int deduction_id, status_id, reason_id, dcm_id;
    private double deduction_value, deduction_remaining_value;
    private String status_name, reason_name, dcm_name, dcm_code, payment_group_type_name;
    private Date deduction_date;
    private Vector all_maker_details;
    private String paymentName;

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public DeductionModel() {
    }

    public DeductionModel(int deduction_id, double deduction_value, int reason_id, int status_id, double deduction_remaining_value,
            int dcm_id, String payment_group_type_name, Date deduction_date,
            String status_name, String reason_name, String dcm_name) {
        super();
        this.deduction_id = deduction_id;
        this.status_id = status_id;
        this.reason_id = reason_id;
        this.dcm_id = dcm_id;
        this.deduction_value = deduction_value;
        this.deduction_remaining_value = deduction_remaining_value;
        this.status_name = status_name;
        this.reason_name = reason_name;
        this.dcm_name = dcm_name;
        this.deduction_date = deduction_date;
        this.payment_group_type_name = payment_group_type_name;
    }

    public String getPayment_group_type_name() {
        return (payment_group_type_name == null) ? "" : payment_group_type_name;
    }

    public void setPayment_group_type_name(String payment_group_type_name) {
        this.payment_group_type_name = payment_group_type_name;
    }

    public int getDeduction_id() {
        return deduction_id;
    }

    public void setDeduction_id(int deduction_id) {
        this.deduction_id = deduction_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getReason_id() {
        return reason_id;
    }

    public void setReason_id(int reason_id) {
        this.reason_id = reason_id;
    }

    public int getDcm_id() {
        return dcm_id;
    }

    public void setDcm_id(int dcm_id) {
        this.dcm_id = dcm_id;
    }

    public double getDeduction_value() {
        return deduction_value;
    }

    public void setDeduction_value(double deduction_value) {
        this.deduction_value = deduction_value;
    }

    public double getDeduction_remaining_value() {
        return deduction_remaining_value;
    }

    public void setDeduction_remaining_value(double deduction_remaining_value) {
        this.deduction_remaining_value = deduction_remaining_value;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getReason_name() {
        return reason_name;
    }

    public void setReason_name(String reason_name) {
        this.reason_name = reason_name;
    }

    public String getDcm_name() {
        return dcm_name;
    }

    public void setDcm_name(String dcm_name) {
        this.dcm_name = dcm_name;
    }

    public Date getDeduction_date() {
        return deduction_date;
    }

    public void setDeduction_date(Date deduction_date) {
        this.deduction_date = deduction_date;
    }

    public Vector getAll_maker_details() {
        return all_maker_details;
    }

    public void setAll_maker_details(Vector all_maker_details) {
        this.all_maker_details = all_maker_details;
    }

    public String getDcm_code() {
        return dcm_code;
    }

    public void setDcm_code(String dcm_code) {
        this.dcm_code = dcm_code;
    }
}