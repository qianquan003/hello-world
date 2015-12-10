package com.xi.quartz.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

/**
 * <p> ��ֻ��һ���򵥵Ĺ���,���ղ�����ά��״̬  </p>
 */
//С��,�����ע�ͺ���Ҫ</span>
@PersistJobDataAfterExecution  // ������JobDataMap���ݵĲ���
@DisallowConcurrentExecution  //��֤�������䲻��ͬʱִ��.�����ڶ�����ִ��ʱ��ü���
public class ColorJob implements Job {
	// ��̬����
	public static final String FAVORITE_COLOR = "favorite color";
	public static final String EXECUTION_COUNT = "count";

	// Quartz ��ÿ�ν�������ʵ�������� ���Ǿ�̬�ĳ�Ա����������������״̬
	private int _counter = 1;

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// job ������
		String jobName = context.getJobDetail().getKey().getName();
		// ����ִ�е�ʱ��
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy �� MM �� dd ��  HH ʱ mm �� ss ��");
		String jobRunTime = dateFormat.format(Calendar.getInstance().getTime());

		// ��ȡ JobDataMap , ������ȡ������ 
		JobDataMap data = context.getJobDetail().getJobDataMap();
		String favoriteColor = data.getString(FAVORITE_COLOR);
		int count = data.getInt(EXECUTION_COUNT);
		System.out
				.println("ColorJob: " + jobName + " �� " + jobRunTime + " ִ���� ...  \n"
						+ "      ϲ������ɫ�ǣ�  " + favoriteColor + "\n"
						+ "      ִ�д���ͳ��(from job jobDataMap)�� " + count + "\n"
						+ "      ִ�д���ͳ��( from job ��ĳ�Ա�� �� ): "
						+ _counter+ " \n ");
		// ÿ��+1 ���Ż�Map ��
		count++;
		data.put(EXECUTION_COUNT, count);
		// ��Ա����������û�����壬ÿ��ʵ���������ʱ��� ͬʱ��ʼ���ñ���
		_counter++;
	}
}
