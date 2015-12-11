package com.xi.quartz.task;

import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class BaseQuartzTask extends QuartzJobBean {

	public ApplicationContext getApplicationContext(final JobExecutionContext jobexecutioncontext) throws RuntimeException{
		try {
			return (ApplicationContext) jobexecutioncontext.getScheduler().getContext().get("applicationContextKey");
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
}
