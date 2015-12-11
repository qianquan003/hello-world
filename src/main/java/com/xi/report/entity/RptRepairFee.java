package com.xi.report.entity;

import java.sql.Timestamp;

public class RptRepairFee {
	private Integer id;
	private Integer areaId;
	private Integer hourseId;
	private Integer hourseType;
	private Integer feeType;
	private String feeName;
	private Double fee;
	private Integer feeNum;
	private Timestamp settlementTime;
	private String settlementOperator;
	private Timestamp createTime;
	private String createOperator;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public Integer getHourseId() {
		return hourseId;
	}
	public void setHourseId(Integer hourseId) {
		this.hourseId = hourseId;
	}
	public Integer getHourseType() {
		return hourseType;
	}
	public void setHourseType(Integer hourseType) {
		this.hourseType = hourseType;
	}
	public Integer getFeeType() {
		return feeType;
	}
	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Integer getFeeNum() {
		return feeNum;
	}
	public void setFeeNum(Integer feeNum) {
		this.feeNum = feeNum;
	}
	public Timestamp getSettlementTime() {
		return settlementTime;
	}
	public void setSettlementTime(Timestamp settlementTime) {
		this.settlementTime = settlementTime;
	}
	public String getSettlementOperator() {
		return settlementOperator;
	}
	public void setSettlementOperator(String settlementOperator) {
		this.settlementOperator = settlementOperator;
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
