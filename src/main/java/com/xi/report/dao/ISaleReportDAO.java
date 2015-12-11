package com.xi.report.dao;

import java.util.List;
import com.xi.common.base.dao.IBaseDao;

public interface ISaleReportDAO extends IBaseDao{
	
	public List<?> getMonthlyReport_Room(String starDate,String endDate);
	
	public List<?> getMonthlyReport_Manager(String starDate,String endDate);
	
	public Integer getRoomId(String address);

}
