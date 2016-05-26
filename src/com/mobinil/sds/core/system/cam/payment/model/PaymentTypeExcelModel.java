package com.mobinil.sds.core.system.cam.payment.model;

public class PaymentTypeExcelModel {
	public PaymentTypeExcelModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	private int paymentExcelManagementId;
	private int paymentTypeId;
	private String paymentType;
	private int excelSheetNumber;
	private String excelSheetName;
	private String excelSheetSqlStatement;
	private int tempId;
	private int sqlTempId;
	
	public PaymentTypeExcelModel(int paymentExcelManagementId,
			String paymentType, int excelSheetNumber,
			String excelSheetName, String excelSheetSqlStatement) {
		super();
		this.paymentExcelManagementId = paymentExcelManagementId;
		this.paymentType = paymentType;
		this.excelSheetNumber = excelSheetNumber;
		this.excelSheetName = excelSheetName;
		this.excelSheetSqlStatement= excelSheetSqlStatement;
	}
	public PaymentTypeExcelModel(int paymentExcelManagementId,
			int paymentTypeId, int excelSheetNumber,
			String excelSheetName, String excelSheetSqlStatement) {
		super();
		this.paymentExcelManagementId = paymentExcelManagementId;
		this.paymentTypeId = paymentTypeId;
		this.excelSheetNumber = excelSheetNumber;
		this.excelSheetName = excelSheetName;
		this.excelSheetSqlStatement= excelSheetSqlStatement;
	}
	
	public PaymentTypeExcelModel(int paymentExcelManagementId,
			int paymentTypeId, String paymentType, int excelSheetNumber,
			String excelSheetName, String excelSheetSqlStatement) {
		super();
		this.paymentExcelManagementId = paymentExcelManagementId;
		this.paymentTypeId = paymentTypeId;
		this.paymentType = paymentType;
		this.excelSheetNumber = excelSheetNumber;
		this.excelSheetName = excelSheetName;
		this.excelSheetSqlStatement = excelSheetSqlStatement;
	}
	public int getPaymentExcelManagementId() {
		return paymentExcelManagementId;
	}
	public void setPaymentExcelManagementId(int paymentExcelManagementId) {
		this.paymentExcelManagementId = paymentExcelManagementId;
	}
	public int getPaymentTypeId() {
		return paymentTypeId;
	}
	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public int getExcelSheetNumber() {
		return excelSheetNumber;
	}
	public int getTempId() {
		return tempId;
	}
	public void setTempId(int tempId) {
		this.tempId = tempId;
	}
	public void setExcelSheetNumber(int excelSheetNumber) {
		this.excelSheetNumber = excelSheetNumber;
	}
	public String getExcelSheetName() {
		return excelSheetName;
	}
	public void setExcelSheetName(String excelSheetName) {
		this.excelSheetName = excelSheetName;
	}
	public String getExcelSheetSqlStatement() {
		return excelSheetSqlStatement;
	}
	public void setExcelSheetSqlStatement(String excelSheetSqlStatement) {
		this.excelSheetSqlStatement = excelSheetSqlStatement;
	}
   public void setSqlTempId(int sqlTempId)
   {
      this.sqlTempId = sqlTempId;
   }
   public int getSqlTempId()
   {
      return sqlTempId;
   }
	
	

}
