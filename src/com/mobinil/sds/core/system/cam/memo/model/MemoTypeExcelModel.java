package com.mobinil.sds.core.system.cam.memo.model;

public class MemoTypeExcelModel {
	
		private int memoExcelManagementId;		
		private int memoTypeId;
		private String memoType;
		private int excelSheetNumber;
		private String excelSheetName;
		private String excelSheetSqlStatement;
		public MemoTypeExcelModel(int memoExcelManagementId,
				int memoTypeId, String memoType, int excelSheetNumber,
				String excelSheetName, String excelSheetSqlStatement) {
			super();
			this.memoExcelManagementId = memoExcelManagementId;
			this.memoTypeId= memoTypeId;
			this.memoType = memoType;
			this.excelSheetNumber = excelSheetNumber;
			this.excelSheetName = excelSheetName;
			this.excelSheetSqlStatement = excelSheetSqlStatement;
		}
		public MemoTypeExcelModel(int memoExcelManagementId, String  memoType, int excelSheetNumber,
				String excelSheetName, String excelSheetSqlStatement) {
			super();
			this.memoExcelManagementId = memoExcelManagementId;
			this.memoType= memoType;
			this.excelSheetNumber= excelSheetNumber;
			this.excelSheetName = excelSheetName;
			this.excelSheetSqlStatement = excelSheetSqlStatement;
		}
		public int getMemoExcelManagementId() {
			return memoExcelManagementId;
		}
		public void setMemoExcelManagementId(int memoExcelManagementId) {
			this.memoExcelManagementId = memoExcelManagementId;
		}
		public int getMemoTypeId() {
			return memoTypeId;
		}
		public void setMemoTypeId(int memoTypeId) {
			this.memoTypeId = memoTypeId;
		}
		public String getMemoType() {
			return memoType;
		}
		public void setMemoType(String memoType) {
			this.memoType = memoType;
		}
		public int getExcelSheetNumber() {
			return excelSheetNumber;
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
		
		
		
		

	

}
