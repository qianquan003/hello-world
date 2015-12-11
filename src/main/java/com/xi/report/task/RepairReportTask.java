package com.xi.report.task;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.xi.common.util.DateUtil;
import com.xi.common.util.PropertiesUtil;
import com.xi.quartz.task.BaseQuartzTask;
import com.xi.report.entity.RepairDTO;
import com.xi.report.service.IQuerReportRoomService;
import com.xi.report.service.IRepairService;
import com.xi.report.service.impl.QuerReportRoomServiceImp;
import com.xi.report.service.impl.RepairServiceImp;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution//不允许并发执行
public class RepairReportTask extends BaseQuartzTask {
	private static final Logger logger = LoggerFactory.getLogger(RepairReportTask.class);
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)throws JobExecutionException {
		IRepairService querReportRoomService = getApplicationContext(jobExecutionContext).getBean("repairServiceImp",RepairServiceImp.class);
		IQuerReportRoomService querService = (QuerReportRoomServiceImp)getApplicationContext(jobExecutionContext).getBean("querReportRoomService",QuerReportRoomServiceImp.class);
		Date monthDate = null;
		try {
			monthDate = DateUtil.dateMinus(new Date(), PropertiesUtil.getIntProperty("income_month"));// 当前时间前一天时间
			System.out.println("当前执行任务时间："+DateUtil.dateToStringShort(monthDate)+"------------dateToStringjs-");
			List<RepairDTO> repList = querReportRoomService.getRepair(DateUtil.dateToStringjs(monthDate));
			querService.getRepair(repList);
		} catch (ParseException e1) {
			logger.error("jobexecutioncontext.getScheduler().getContext() error!", e1);
			e1.printStackTrace();
		}
		if(monthDate==null)
		{
			return ;
		}
	}
}
