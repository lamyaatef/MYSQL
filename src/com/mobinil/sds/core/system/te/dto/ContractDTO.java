package com.mobinil.sds.core.system.te.dto;

public class ContractDTO {

	private int cif_type;

public int getCif_type() {
		return cif_type;
	}
	public void setCif_type(int cif_type) {
		this.cif_type = cif_type;
	}
public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCONTRACT_TYPE() {
		return CONTRACT_TYPE;
	}
	public void setCONTRACT_TYPE(String contract_type) {
		CONTRACT_TYPE = contract_type;
	}
	public String getCONTRACT_TYPE_VALUE() {
		return CONTRACT_TYPE_VALUE;
	}
	public void setCONTRACT_TYPE_VALUE(String contract_type_value) {
		CONTRACT_TYPE_VALUE = contract_type_value;
	}
	public String getCONTRACT_SEARCH_CRITERIA() {
		return CONTRACT_SEARCH_CRITERIA;
	}
	public void setCONTRACT_SEARCH_CRITERIA(String contract_search_criteria) {
		CONTRACT_SEARCH_CRITERIA = contract_search_criteria;
	}
	public String getCONTRACT_UPDATE_CRITERIA() {
		return CONTRACT_UPDATE_CRITERIA;
	}
	public void setCONTRACT_UPDATE_CRITERIA(String contract_update_criteria) {
		CONTRACT_UPDATE_CRITERIA = contract_update_criteria;
	}
private Integer id;
private String CONTRACT_TYPE;
private String CONTRACT_TYPE_VALUE;
private String CONTRACT_SEARCH_CRITERIA;
private String CONTRACT_UPDATE_CRITERIA;
private String START_DATE;

public String getSTART_DATE() {
	return START_DATE;
}
public void setSTART_DATE(String start_date) {
	START_DATE = start_date;
}
public String getEND_DATE() {
	return END_DATE;
}
public void setEND_DATE(String end_date) {
	END_DATE = end_date;
}
private String END_DATE;


}
