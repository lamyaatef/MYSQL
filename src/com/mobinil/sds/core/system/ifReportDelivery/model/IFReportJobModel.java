package com.mobinil.sds.core.system.ifReportDelivery.model;

public class IFReportJobModel {
	private int jobId;
	private String jobDate;
	private int jobStatusId;
	private int userId;
	private String reportCreationDate;
	private int processingTime;
	private String jobStatus;
	private int numberOfNotFound;
	private int numberOfDistErrors;
	private int numberOfInfoFortErros;
	private int numberOfDuplicates;
	private int numberOfDistincts;
	private int numberofAutoMatched;
	
	
	
	
	
	public int getNumberofAutoMatched() {
		return numberofAutoMatched;
	}
	public void setNumberofAutoMatched(int numberofAutoMatched) {
		this.numberofAutoMatched = numberofAutoMatched;
	}
	public int getNumberOfDistincts() {
		return numberOfDistincts;
	}
	public void setNumberOfDistincts(int numberOfDistincts) {
		this.numberOfDistincts = numberOfDistincts;
	}
	public int getNumberOfDuplicates() {
		return numberOfDuplicates;
	}
	public void setNumberOfDuplicates(int numberOfDuplicates) {
		this.numberOfDuplicates = numberOfDuplicates;
	}
	public int getNumberOfNotFound() {
		return numberOfNotFound;
	}
	public void setNumberOfNotFound(int numberOfNotFound) {
		this.numberOfNotFound = numberOfNotFound;
	}
	public int getNumberOfInfoFortErros() {
		return numberOfInfoFortErros;
	}
	public void setNumberOfInfoFortErros(int numberOfInfoFortErros) {
		this.numberOfInfoFortErros = numberOfInfoFortErros;
	}
	public int getNumberOfDistErrors() {
		return numberOfDistErrors;
	}
	public void setNumberOfDistErrors(int numberOfDistErrors) {
		this.numberOfDistErrors = numberOfDistErrors;
	}
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public String getJobDate() {
		return jobDate;
	}
	public void setJobDate(String jobDate) {
		this.jobDate = jobDate;
	}
	public int getJobStatusId() {
		return jobStatusId;
	}
	public void setJobStatusId(int jobStatusId) {
		this.jobStatusId = jobStatusId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getReportCreationDate() {
		return reportCreationDate;
	}
	public void setReportCreationDate(String reportCreationDate) {
		this.reportCreationDate = reportCreationDate;
	}
	public int getProcessingTime() {
		return processingTime;
	}
	public void setProcessingTime(int processingTime) {
		this.processingTime = processingTime;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	

}
