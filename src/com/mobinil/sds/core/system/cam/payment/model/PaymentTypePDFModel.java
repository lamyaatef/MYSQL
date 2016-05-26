package com.mobinil.sds.core.system.cam.payment.model;

public class PaymentTypePDFModel {
	private int paymentPdfManagmentId;
	private String paymentTypeName;
	private String queriesParams;	
	private String pdfSql;
	private int tempId;
	public int getTempId() {
		return tempId;
	}



	public void setTempId(int tempId) {
		this.tempId = tempId;
	}



	public PaymentTypePDFModel() {
		super();
	}
	
	

	public PaymentTypePDFModel(int paymentPdfManagmentId,String paymentTypeName, String queriesParams,
			 String pdfSql) {
		super();
		this.paymentPdfManagmentId = paymentPdfManagmentId;
		this.queriesParams = queriesParams;
		this.paymentTypeName = paymentTypeName;
		this.pdfSql = pdfSql;
	}



	public String getQueriesParams() {
		return queriesParams;
	}
	public void setQueriesParams(String queriesParams) {
		this.queriesParams = queriesParams;
	}
	public String getPdfSql() {
		return pdfSql;
	}
	public void setPdfSql(String pdfSql) {
		this.pdfSql = pdfSql;
	}



	public int getPaymentPdfManagmentId() {
		return paymentPdfManagmentId;
	}



	public void setPaymentPdfManagmentId(int paymentPdfManagmentId) {
		this.paymentPdfManagmentId = paymentPdfManagmentId;
	}



	public String getPaymentTypeName() {
		return paymentTypeName;
	}



	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}
	

}
