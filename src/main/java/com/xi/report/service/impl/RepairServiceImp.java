package com.xi.report.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.xi.report.dao.IRepairDAO;
import com.xi.report.entity.RepairDTO;
import com.xi.report.service.IRepairService;

@Service
@Scope("prototype")
@Transactional(value = "hw_Tx", readOnly = true, propagation = Propagation.SUPPORTS)
public class RepairServiceImp implements IRepairService{
	
	@Autowired
	private IRepairDAO dao;
	
	public List<RepairDTO> getRepair(String month){
		return dao.getRepair(month);
	}
}
