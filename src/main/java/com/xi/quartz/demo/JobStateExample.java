package com.xi.quartz.demo;
import static org.quartz.DateBuilder.nextGivenSecondDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

public class JobStateExample {

	public static void main(String[] args) throws Exception {
		JobStateExample example = new JobStateExample();
		example.run();
	}

	public void run() throws Exception {

		// 日期格式化
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy 年 MM 月 dd 日  HH 时 mm 分 ss 秒");
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		System.out.println("--------------- 初始化 -------------------");

		// 下一个10秒，不懂的去查API
		Date startTime = nextGivenSecondDate(null, 10);

		// job1 第10秒执行一次，共执行5次
		JobDetail job1 = newJob(ColorJob.class).withIdentity("job1", "group1")
				.build();

		SimpleTrigger trigger1 = newTrigger()
				.withIdentity("trigger1", "group1")
				.startAt(startTime)
				.withSchedule(
						simpleSchedule().withIntervalInSeconds(10)
								.withRepeatCount(4)).build();

		// 初始化传入的参数
		job1.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "######  绿   #####");
		job1.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);

		Date scheduleTime1 = sched.scheduleJob(job1, trigger1);
		System.out.println(job1.getKey().getName() + " 将在 : "
				+ dateFormat.format(scheduleTime1) + " 执行, 并重复 : "
				+ trigger1.getRepeatCount() + " 次, 每次间隔   "
				+ trigger1.getRepeatInterval() / 1000 + " 秒");

		// job2 每10秒执行一次，共执行5次
		JobDetail job2 = newJob(ColorJob.class).withIdentity("job2", "group1")
				.build();

		SimpleTrigger trigger2 = newTrigger()
				.withIdentity("trigger2", "group1")
				.startAt(startTime)
				.withSchedule(
						simpleSchedule().withIntervalInSeconds(10)
								.withRepeatCount(4)).build();

		// 初始化传入的参数
		job2.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "######  红   #####");
		job2.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);

		Date scheduleTime2 = sched.scheduleJob(job2, trigger2);
		System.out.println(job2.getKey().getName() + " 将在 : "
				+ dateFormat.format(scheduleTime2) + " 执行, 并重复 : "
				+ trigger2.getRepeatCount() + " 次, 每次间隔   "
				+ trigger2.getRepeatInterval() / 1000 + " 秒");

		System.out.println("------- 开始调度 (调用.start()方法) ----------------");
		sched.start();

		System.out.println("------- 等待60秒 ... -------------");
		try {
			Thread.sleep(60L * 1000L);
		} catch (Exception e) {
		}

		sched.shutdown(true);
		System.out.println("------- 调度已关闭 ---------------------");

		// 显示一下 已经执行的任务信息
		SchedulerMetaData metaData = sched.getMetaData();
		System.out.println("~~~~~~~~~~  执行了 "
				+ metaData.getNumberOfJobsExecuted() + " 个 jobs.");

		/*
		如果你想在 某个job执行的时候传入参数,参数在job执行过程中对参数有所修改,并且在job执行完毕后把参数返回
		那么你需要学习一下现在的这个例子了,因为它正是你所想要的
		 */
	}

}
