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

		// ���ڸ�ʽ��
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy �� MM �� dd ��  HH ʱ mm �� ss ��");
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		System.out.println("--------------- ��ʼ�� -------------------");

		// ��һ��10�룬������ȥ��API
		Date startTime = nextGivenSecondDate(null, 10);

		// job1 ��10��ִ��һ�Σ���ִ��5��
		JobDetail job1 = newJob(ColorJob.class).withIdentity("job1", "group1")
				.build();

		SimpleTrigger trigger1 = newTrigger()
				.withIdentity("trigger1", "group1")
				.startAt(startTime)
				.withSchedule(
						simpleSchedule().withIntervalInSeconds(10)
								.withRepeatCount(4)).build();

		// ��ʼ������Ĳ���
		job1.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "######  ��   #####");
		job1.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);

		Date scheduleTime1 = sched.scheduleJob(job1, trigger1);
		System.out.println(job1.getKey().getName() + " ���� : "
				+ dateFormat.format(scheduleTime1) + " ִ��, ���ظ� : "
				+ trigger1.getRepeatCount() + " ��, ÿ�μ��   "
				+ trigger1.getRepeatInterval() / 1000 + " ��");

		// job2 ÿ10��ִ��һ�Σ���ִ��5��
		JobDetail job2 = newJob(ColorJob.class).withIdentity("job2", "group1")
				.build();

		SimpleTrigger trigger2 = newTrigger()
				.withIdentity("trigger2", "group1")
				.startAt(startTime)
				.withSchedule(
						simpleSchedule().withIntervalInSeconds(10)
								.withRepeatCount(4)).build();

		// ��ʼ������Ĳ���
		job2.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "######  ��   #####");
		job2.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);

		Date scheduleTime2 = sched.scheduleJob(job2, trigger2);
		System.out.println(job2.getKey().getName() + " ���� : "
				+ dateFormat.format(scheduleTime2) + " ִ��, ���ظ� : "
				+ trigger2.getRepeatCount() + " ��, ÿ�μ��   "
				+ trigger2.getRepeatInterval() / 1000 + " ��");

		System.out.println("------- ��ʼ���� (����.start()����) ----------------");
		sched.start();

		System.out.println("------- �ȴ�60�� ... -------------");
		try {
			Thread.sleep(60L * 1000L);
		} catch (Exception e) {
		}

		sched.shutdown(true);
		System.out.println("------- �����ѹر� ---------------------");

		// ��ʾһ�� �Ѿ�ִ�е�������Ϣ
		SchedulerMetaData metaData = sched.getMetaData();
		System.out.println("~~~~~~~~~~  ִ���� "
				+ metaData.getNumberOfJobsExecuted() + " �� jobs.");

		/*
		��������� ĳ��jobִ�е�ʱ�������,������jobִ�й����жԲ��������޸�,������jobִ����Ϻ�Ѳ�������
		��ô����Ҫѧϰһ�����ڵ����������,��Ϊ������������Ҫ��
		 */
	}

}
