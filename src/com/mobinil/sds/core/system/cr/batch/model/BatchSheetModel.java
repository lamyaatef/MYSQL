package com.mobinil.sds.core.system.cr.batch.model;
import com.mobinil.sds.core.system.Model;
import java.io.Serializable;
import java.sql.ResultSet;

public class BatchSheetModel extends Model implements Serializable{

	private static final long serialVersionUID = 1L;
	
	String contractMainSimNo = "";
	String archivingFlag = "";
	String sheetSerialId = "";
	String batchId = "";
	String batchDate = "";
	String batchType = "";
	String batchStatusType = "";
	String sheetId = "";
	String dcmId = "";
	String posCode = "";
	String posName = "";
	String posAddress = "";
	String cityName = "";
	public String getPosName() {
		return posName;
	}
	public void setPosName(String posName) {
		this.posName = posName;
	}
	public String getPosAddress() {
		return posAddress;
	}
	public void setPosAddress(String posAddress) {
		this.posAddress = posAddress;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	String superDealerCode = "";
	public String getPosCode() {
		return posCode;
	}
	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}
	public String getSuperDealerCode() {
		return superDealerCode;
	}
	public void setSuperDealerCode(String superDealerCode) {
		this.superDealerCode = superDealerCode;
	}
	public String getContractMainSimNo() {
		return contractMainSimNo;
	}
	public void setContractMainSimNo(String contractMainSimNo) {
		this.contractMainSimNo = contractMainSimNo;
	}
	public String getArchivingFlag() {
		return archivingFlag;
	}
	public void setArchivingFlag(String archivingFlag) {
		this.archivingFlag = archivingFlag;
	}
	public String getSheetSerialId() {
		return sheetSerialId;
	}
	public void setSheetSerialId(String sheetSerialId) {
		this.sheetSerialId = sheetSerialId;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getBatchDate() {
		return batchDate;
	}
	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
	}
	public String getBatchType() {
		return batchType;
	}
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	public String getBatchStatusType() {
		return batchStatusType;
	}
	public void setBatchStatusType(String batchStatusType) {
		this.batchStatusType = batchStatusType;
	}
	public String getSheetId() {
		return sheetId;
	}
	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}
	public String getDcmId() {
		return dcmId;
	}
	public void setDcmId(String dcmId) {
		this.dcmId = dcmId;
	}
	
	public static final String CONTRACT_MAIN_SIM_NO = "CONTRACT_MAIN_SIM_NO";
	public static final String ARCHIVING_FLAG = "ARCHIVING_FLAG";
	public static final String SHEET_SERIAL_ID = "SHEET_SERIAL_ID";
	public static final String BATCH_ID = "BATCH_ID";
	public static final String BATCH_DATE = "BATCH_DATE";
	public static final String BATCH_TYPE_ID = "BATCH_TYPE_ID";
	public static final String BATCH_LAST_STATUS_TYPE_ID = "BATCH_LAST_STATUS_TYPE_ID";
	public static final String SHEET_ID = "SHEET_ID";
	public static final String DCM_ID = "DCM_ID";
	public static final String SHEET_POS_CODE = "SHEET_POS_CODE";
	public static final String SHEET_SUPER_DEALER_CODE = "SHEET_SUPER_DEALER_CODE";
	public static final String CITY_ENGLISH = "CITY_ENGLISH";
	public static final String DCM_NAME = "DCM_NAME";
	public static final String DCM_ADDRESS = "DCM_ADDRESS";
	
	public BatchSheetModel (){
		
	}
	
	public  void fillInstance(java.sql.ResultSet res)
	{
		try
		{
			contractMainSimNo = res.getString(CONTRACT_MAIN_SIM_NO);
			archivingFlag = res.getString(ARCHIVING_FLAG);
			sheetSerialId = res.getString(SHEET_SERIAL_ID);
			batchId = res.getString(BATCH_ID);
			batchDate = res.getString(BATCH_DATE);
			batchType = res.getString(BATCH_TYPE_ID);
			batchStatusType = res.getString(BATCH_LAST_STATUS_TYPE_ID);
			sheetId = res.getString(SHEET_ID);
			dcmId = res.getString(DCM_ID);
			posCode = res.getString(SHEET_POS_CODE);
			superDealerCode = res.getString(SHEET_SUPER_DEALER_CODE);
			cityName = res.getString(CITY_ENGLISH);
			posName = res.getString(DCM_NAME);
			posAddress = res.getString(DCM_ADDRESS);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		
	}
	
	public BatchSheetModel (ResultSet res)
	{
		fillInstance(res);
	}

}
