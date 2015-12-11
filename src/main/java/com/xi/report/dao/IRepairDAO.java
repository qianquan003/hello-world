package com.xi.report.dao;

import java.util.List;

import com.xi.common.base.dao.IBaseDao;
import com.xi.report.entity.RepairDTO;

public interface IRepairDAO extends IBaseDao{
	public List<RepairDTO> getRepair(String month);
}
