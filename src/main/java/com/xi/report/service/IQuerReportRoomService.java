package com.xi.report.service;

import java.util.List;

import com.xi.report.entity.RepairDTO;
public interface IQuerReportRoomService {

	public List<?> getMonthlyReport_Room(String starDate,String endDate);
	
	public List<?> getMonthlyReport_Manager(String starDate,String endDate);
	
	public void createMonthlyReport(List<?> list);
	
	public void createMonthlyReportManager(List<?> list);
	
	public void getRepair(List<RepairDTO> list);
	
	
}
