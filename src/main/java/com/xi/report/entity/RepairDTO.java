package com.xi.report.entity;

import java.sql.Timestamp;

public class RepairDTO {
	private Integer belongArea;
	private String address;
	private Double fee;
	private Timestamp createTime;
	private String createOperator;
	public Integer getBelongArea() {
		return belongArea;
	}
	public void setBelongArea(Integer belongArea) {
		this.belongArea = belongArea;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getCreateOperator() {
		return createOperator;
	}
	public void setCreateOperator(String createOperator) {
		this.createOperator = createOperator;
	}
}
