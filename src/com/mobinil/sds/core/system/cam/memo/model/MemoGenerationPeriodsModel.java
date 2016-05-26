package com.mobinil.sds.core.system.cam.memo.model;

public class MemoGenerationPeriodsModel {
	private int memoGenerationPeriodId;
	private String memoGenerationPeriod;
	public MemoGenerationPeriodsModel(int memoGenerationPeriodId,
			String memoGenerationPeriod) {
		super();
		this.memoGenerationPeriodId = memoGenerationPeriodId;
		this.memoGenerationPeriod = memoGenerationPeriod;
	}
	public int getMemoGenerationPeriodId() {
		return memoGenerationPeriodId;
	}
	public void setMemoGenerationPeriodId(int memoGenerationPeriodId) {
		this.memoGenerationPeriodId = memoGenerationPeriodId;
	}
	public String getMemoGenerationPeriod() {
		return memoGenerationPeriod;
	}
	public void setMemoGenerationPeriod(String memoGenerationPeriod) {
		this.memoGenerationPeriod = memoGenerationPeriod;
	}
	

}
