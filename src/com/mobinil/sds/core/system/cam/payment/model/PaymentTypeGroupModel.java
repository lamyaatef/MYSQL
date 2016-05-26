package com.mobinil.sds.core.system.cam.payment.model;
import java.util.*;
import com.mobinil.sds.core.system.payment.dto.*;
public class PaymentTypeGroupModel 
{
private int groupId, groupStatusId;
private String statusName, groupName, groupDesc;
private Vector allAssignedTypes;
  public PaymentTypeGroupModel()
  {
  }
  public PaymentTypeGroupModel(int groupId,
			String groupName, String groupDesc, int groupStatusId, String statusName) {
		super();
		this.groupId = groupId;
		this.groupStatusId = groupStatusId;
		this.statusName = statusName;
		this.groupName = groupName;
		this.groupDesc = groupDesc;
	}
 
public int getGroupId() {
	return groupId;
}
public void setGroupId(int groupId) {
	this.groupId = groupId;
}
public int getGroupStatusId() {
	return groupStatusId;
}
public void setGroupStatusId(int groupStatusId) {
	this.groupStatusId = groupStatusId;
}
public String getStatusName() {
	return statusName;
}
public void setStatusName(String statusName) {
	this.statusName = statusName;
}
public String getGroupName() {
	return groupName;
}
public void setGroupName(String groupName) {
	this.groupName = groupName;
}
public String getGroupDesc() {
	return groupDesc;
}
public void setGroupDesc(String groupDesc) {
	this.groupDesc = groupDesc;
}
public Vector getAllAssignedTypes() {
	return allAssignedTypes;
}
public void setAllAssignedTypes(Vector allAssignedTypes) {
	this.allAssignedTypes = allAssignedTypes;
}
public void addAssignedType(PaymentTypeDTO pay_dto)
{
  this.allAssignedTypes.add(pay_dto);
}
}