package com.xi.report.service;

import java.util.List;
import com.xi.report.entity.RepairDTO;

public interface IRepairService {
	public List<RepairDTO> getRepair(String month);
	
}
