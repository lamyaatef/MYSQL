package com.mobinil.sds.core.system.cam.memo.model;

public class MemoPaymentTypeSettingsModel {
	private int memoSeetingsId;
	private String paymentType;
	private String generationPeriod;
	private int trackingFlag;
	private int[][] weeks=new int[52][1];
	private int[][] months=new int[12][1];
	private int[][] quarters=new int[4][2];
	public MemoPaymentTypeSettingsModel(int memoSeetingsId,
			String paymentType, String generationPeriod,int trackingFlag) {
		super();
		this.memoSeetingsId = memoSeetingsId;
		this.paymentType = paymentType;
		this.generationPeriod = generationPeriod;
		this.trackingFlag=trackingFlag;
	}
	public int getMemoSeetingsId() {
		return memoSeetingsId;
	}
	public void setMemoSeetingsId(int memoSeetingsId) {
		this.memoSeetingsId = memoSeetingsId;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getGenerationPeriod() {
		return generationPeriod;
	}
	public void setGenerationPeriod(String generationPeriod) {
		this.generationPeriod = generationPeriod;
	}
	public int getTrackingFlag() {
		return trackingFlag;
	}
	public void setTrackingFlag(int trackingFlag) {
		this.trackingFlag = trackingFlag;
	}
	public int[][] getWeeks() {
		return weeks;
	}
	public void setWeeks(int[][] weeks) {
		this.weeks = weeks;
	}
	public int[][] getMonths() {
		return months;
	}
	public void setMonths(int[][] months) {
		this.months = months;
	}
	public int[][] getQuarters() {
		return quarters;
	}
	public void setQuarters(int[][] quarters) {
		this.quarters = quarters;
	}
	
		

}
