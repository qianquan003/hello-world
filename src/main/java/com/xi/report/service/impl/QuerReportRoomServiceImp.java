package com.xi.report.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.xi.report.dao.ISaleReportDAO;
import com.xi.report.entity.RepairDTO;
import com.xi.report.entity.RptAchievements05;
import com.xi.report.entity.RptAchievements06;
import com.xi.report.entity.RptRepairFee;
import com.xi.report.service.IQuerReportRoomService;

@Service("querReportRoomService")
@Transactional(value = "defaultTx", readOnly = true, propagation = Propagation.SUPPORTS)
public class QuerReportRoomServiceImp implements IQuerReportRoomService{
	private static final Logger logger = Logger.getLogger(QuerReportRoomServiceImp.class);
	@Autowired
	private ISaleReportDAO dao;
	
	//房管员查询

	public List<?> getMonthlyReport_Room(String starDate,String endDate){
		return dao.getMonthlyReport_Room(starDate, endDate);
	}
	//服务中心经理

	public List<?> getMonthlyReport_Manager(String starDate,String endDate){
		return dao.getMonthlyReport_Manager(starDate, endDate);
	}
	//生成房管员记录
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void createMonthlyReport(List<?> list) {
		Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date d=cal.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String createTime=format.format(d);
		logger.info("-----------房管员每日销售报表---开始---------");
		try{
			List<RptAchievements06>  listDto = new ArrayList<RptAchievements06>();
			if(list.size()>0){
				for(int i = 0; i < list.size(); i++){
					Object[] decimal = (Object[]) list.get(i);
					RptAchievements06 dto = new RptAchievements06();
					dto.setSeviceCenterId(Integer.parseInt(decimal[0]+""));
					dto.setSeviceCenterName(decimal[1]+"");
					dto.setUserId(Integer.parseInt(decimal[2]+""));
					dto.setManagerName(decimal[3]+"");
					dto.setCreateTime(createTime);
					dto.setSigningXQCount(Integer.parseInt(decimal[4]+""));
					dto.setSigningXQB(Integer.parseInt(decimal[15]+""));
					dto.setSigningXQY(Integer.parseInt(decimal[16]+""));
					dto.setSigningXZ(Integer.parseInt(decimal[6]+""));
					dto.setSigningZZ(Integer.parseInt(decimal[7]+""));
					dto.setSigningHF(Integer.parseInt(decimal[8]+""));
					dto.setCheckOutXZ(Integer.parseInt(decimal[9]+""));
					dto.setCheckOutZZ(Integer.parseInt(decimal[10]+""));
					dto.setCheckOutHF(Integer.parseInt(decimal[11]+""));
					dto.setCheckOutTF(Integer.parseInt(decimal[12]+""));
					dto.setCheckOutCount(Integer.parseInt(decimal[13]+""));
					dto.setProfitCount(Integer.parseInt(decimal[14]+""));
					dto.setCeaOrderField(Integer.parseInt(decimal[17]+""));
					listDto.add(dto);
				}
			}
			if(listDto.size()>0){
				this.dao.saveOrUpdateAll(listDto);
			}
		}catch (Exception e) {
			logger.error("-----------房管员每日销售报表:"+e);
			e.printStackTrace();
			}
		logger.info("-----------房管员每日销售报表生成---结束---------");
	
	}
	//生成服务中心经理记录
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void createMonthlyReportManager(List<?> list) {
		Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date d=cal.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String createTime=format.format(d);
		logger.info("-----------服务中心房经理月度销售报表---开始---------");
		try{
			List<RptAchievements05>  listDto = new ArrayList<RptAchievements05>();
			if(list.size()>0){
				for(int i = 0; i < list.size(); i++){
					Object[] decimal = (Object[]) list.get(i);
					RptAchievements05 dto = new RptAchievements05();
					dto.setSeviceCenterId(Integer.parseInt(decimal[0]+""));
					dto.setSeviceCenterName(decimal[1]+"");
					dto.setUserId(Integer.parseInt(decimal[2]==null?0+"":decimal[2]+""));
					dto.setManagerName(decimal[3]+"");
					dto.setCreateTime(createTime);
					dto.setSigningXQCount(Integer.parseInt(decimal[4]+""));
					dto.setSigningXQ(Integer.parseInt(decimal[5]+""));
					dto.setSigningXZ(Integer.parseInt(decimal[6]+""));
					dto.setSigningZZ(Integer.parseInt(decimal[7]+""));
					dto.setSigningHF(Integer.parseInt(decimal[8]+""));
					dto.setCheckOutXZ(Integer.parseInt(decimal[9]+""));
					dto.setCheckOutZZ(Integer.parseInt(decimal[10]+""));
					dto.setCheckOutHF(Integer.parseInt(decimal[11]+""));
					dto.setCheckOutTF(Integer.parseInt(decimal[12]+""));
					dto.setCheckOutCount(Integer.parseInt(decimal[13]+""));
					dto.setProfitCount(Integer.parseInt(decimal[14]+""));
					dto.setCeaOrderField(Integer.parseInt(decimal[15]+""));
					listDto.add(dto);
				}
			}
			if(listDto.size()>0){
				this.dao.saveOrUpdateAll(listDto);
			}
		}catch (Exception e) {
			logger.error("-----------服务中心经理月度销售报表:"+e);
			e.printStackTrace();
			}
		logger.info("-----------服务中心经理月度销售报表生成---结束---------");
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void getRepair(List<RepairDTO> list){
		List<RptRepairFee> listRpt = new ArrayList<RptRepairFee>();
		for (RepairDTO dto : list) {
			Integer romId = dao.getRoomId(dto.getAddress());
			if(romId!=null&&romId!=0){
				RptRepairFee fee = new RptRepairFee();
				fee.setAreaId(dto.getBelongArea());
				fee.setCreateOperator(dto.getCreateOperator());
				fee.setFee(dto.getFee());
				fee.setHourseId(romId);
				fee.setCreateTime(dto.getCreateTime());
				listRpt.add(fee);
			}
		}
		if(listRpt.size()>0){
			dao.saveOrUpdateAll(listRpt);
		}
	}
	
}
