package com.xi.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class DateUtil {
	private static Logger logger = Logger.getLogger(DateUtil.class.getName());

	private static String format = "yyyy-MM-dd HH:mm:ss";
	private static String format1 = "yyyy-MM-dd-HH-mm-ss";

	public static Date formatCurrentDate(String pattern) {
		Date startDate;
		DateFormat formatter = new SimpleDateFormat(pattern);
		try {
			startDate = formatter.parse(formatter.format(new Date()));
		} catch (Exception e) {
			startDate = new Date();
		}

		return startDate;
	}
	/**
	 * string to Date
	 * @param dateStr
	 * @return
	 */
	public static Date formatCurrent(String dateStr){
        Date date = new Date();   
        //注意format的格式要与日期String的格式相匹配   
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        try {   
            date = sdf.parse(dateStr);   
        } catch (Exception e) {   
            e.printStackTrace();   
        }
		return date;  
	}

	public static Date formatCurrentDate() {
		Date startDate;
		DateFormat formatter = new SimpleDateFormat(format);
		try {
			startDate = formatter.parse(formatter.format(new Date()));
		} catch (Exception e) {
			startDate = new Date();
		}

		return startDate;
	}

	public static java.sql.Date toSqlDate(java.util.Date date) {
		java.sql.Date d = new java.sql.Date(date.getTime());
		return d;
	}

	public static java.sql.Timestamp toSqlTimeStamp(Date date) {
		Timestamp d = new Timestamp(date.getTime());
		return d;
	}
	/**
	 * 得到这个月的总天数
	 * @return
	 */
	public static int getDayCount(String date){
		Calendar calendar = Calendar.getInstance();  
		calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));  
		calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(5, 7)) -1);  
		int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return day;
	}
	public static java.sql.Timestamp dateToSqlTimeStamp(java.util.Date date) {
		return Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
	}

	public static Date dateBegin(Date date) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date begin = formatter.parse(formatter.format(date));

		return begin;
	}

	public static Date dateEnd(Date date) throws ParseException {
		Date end = new Date(date.getTime() + 24 * 60 * 60 * 1000);

		return end;
	}

	public static Date dateAdd(Date date, int dayNum) throws ParseException {
		Calendar cal = Calendar.getInstance();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(formatter.parse(formatter.format(date)));
		cal.add(Calendar.DATE, dayNum);
		return cal.getTime();
	}

	public static Date dateMinus(Date date, int dayNum) throws ParseException {
		Calendar cal = Calendar.getInstance();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(formatter.parse(formatter.format(date)));
		cal.add(Calendar.DATE, -(dayNum));
		return cal.getTime();
	}

	/**
	 * 根据参数date，计算星期天对应的日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static java.util.Date sunday(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// int diff = cal.DAY_OF_WEEK;

		int diff = cal.get(Calendar.DAY_OF_WEEK) - 1;
		// System.out.println("------DAY_OF_WEEK=" + diff);
		return dateMinus(date, diff);
	}

	/**
	 * 根据参数date，计算date对应的月初的日期
	 * 
	 * @param date
	 *            ：运行程序的当日
	 * @return
	 * @throws ParseException
	 */
	public static Date thisMonthOfDate(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int diff = cal.get(Calendar.DAY_OF_MONTH) - 1;

		// System.out.println("------DAY_OF_MONTH=" + diff);
		return dateMinus(date, diff);
	}

	/**
	 * 根据参数date，计算date对应的年初的日期。即1月1日的日期
	 * 
	 * @param date
	 *            ：运行程序的当日
	 * @return
	 * @throws ParseException
	 */
	public static Date thisYearBeginOfDate(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int diff = cal.get(Calendar.DAY_OF_YEAR) - 1;
		// System.out.println("------DAY_OF_YEAR=" + diff);
		return dateMinus(date, diff);
	}

	public static Date stringToDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;

		try {
			d = sdf.parse(date);

		} catch (Exception e) {
		}
		return d;
	}

	public static Date stringToDate2(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;

		try {
			d = sdf.parse(date);

		} catch (Exception e) {
			logger.error("stringToDate2转换出错。date=[" + date + "]" + e.getMessage());
		}
		return d;
	}

	public static String dateToString(Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

	public static String dateToStringShort(Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
	
	public static String dateToStringjs(Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM");
		return formatter.format(date);
	}

	public static String getFormatday(int num) {
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String date1 = sdf.format(new Date());
			date = sdf.parse(date1);
			cal.setTime(date);
			cal.add(Calendar.DATE, num);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sdf.format(cal.getTime());
	}

	/**
	 * 得到本月的最后一天
	 * 
	 * @return
	 */
	public static String getMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(calendar.getTime());
	}

	/**
	 * 给当前日期-5分钟
	 * */
	public static String getSubtractTime(int num) {
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String date1 = sdf.format(new Date());
			date = sdf.parse(date1);
			cal.setTime(date);
			cal.add(Calendar.DATE, num);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sdf.format(cal.getTime());
	}

	/**
	 * 得到两个日期之间的时间差 以毫秒返回后面时间-前面时间
	 * */
	public static Long getDateSubtractTime(String date1, String date2) {
		long datetime = 0;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date firstdate = df.parse(date1);
			Date enddate = df.parse(date2);
			datetime = enddate.getTime() - firstdate.getTime();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return datetime;
	}

	public static Long dateDiff(String date1, String date2, String format) {
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff = 0;

		try {
			diff = sd.parse(date2).getTime() - sd.parse(date1).getTime();
			long day = diff / nd;// 计算差多少天
			long hour = diff % nd / nh;// 计算差多少小时
			long min = diff % nd % nh / nm;// 计算差多少分钟
			long sec = diff % nd % nh % nm / ns;// 计算差多少秒
//			System.out.println("时间相差：" + day + "天" + hour + "小时" + min + "分钟" + sec + "秒。");
			diff = day;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return diff;
	}
	
	public static Long dateDiff(Date begin,Date end){
		 long diff = end.getTime() - begin.getTime();    
		 long days = diff / (24 * 60 * 60 * 1000);
		 return days;
	}

	public static String transferToWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK);

		String[] weekNames = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

		return weekNames[week - 1];
	}

	/**
	 * 
	 * 方法说明: <br>
	 * 根据输入日期，计算出该所在的那周的星期一
	 * 
	 * @param inputDate
	 *            yyyy-MM-dd日期
	 * @return 返回星期一
	 * 
	 */
	public static String getThisMondayDateFrom(String inputDate) {
		String thisMonday = "";

		Date d = DateUtil.stringToDate2(inputDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int week = cal.get(Calendar.DAY_OF_WEEK);

		if (week == 1) {
			cal.add(Calendar.DATE, -6);
		} else {
			cal.add(Calendar.DATE, 2 - week);
		}

		thisMonday = DateUtil.dateToStringShort(cal.getTime());
		return thisMonday;
	}

	/**
	 * 
	 * 方法说明: <br>
	 * 根据输入日期，计算出该所在的那周的星期一
	 * 
	 * @param inputDate
	 *            yyyy-MM-dd日期
	 * @return 返回星期一
	 * 
	 */
	public static String getThisMondayDateFrom(Date inputDate) {
		String thisMonday = "";

		Calendar cal = Calendar.getInstance();
		cal.setTime(inputDate);
		int week = cal.get(Calendar.DAY_OF_WEEK);

		if (week == 1) {
			cal.add(Calendar.DATE, -6);
		} else {
			cal.add(Calendar.DATE, 2 - week);
		}

		thisMonday = DateUtil.dateToStringShort(cal.getTime());
		return thisMonday;
	}

	/**
	 * 
	 * 方法说明: <br>
	 * 根据星期一，计算出星期日
	 * 
	 * @param monday
	 *            星期一
	 * @return 返回星期一所在周的星期日
	 * 
	 */
	public static String getThisSundayFromMonday(String monday) {
		String thisSunday = "";

		Date d = DateUtil.stringToDate2(monday);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);

		cal.add(Calendar.DATE, 6);
		thisSunday = DateUtil.dateToStringShort(cal.getTime());

		return thisSunday;
	}
	
	public static Date getMonthAfter(Date date, int afterMonthNumber) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		c.set(Calendar.MONTH, month + afterMonthNumber);
		return c.getTime();
	}
	
	public static Date getYearAfter(Date date, int afterYearNumber) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		c.set(Calendar.YEAR, year + afterYearNumber);
		return c.getTime();
	}	
	

	/**
	 * 得到date月的最后一天
	 * 
	 * @return
	 */
	public static String getMonthLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(calendar.getTime());
	}

	/**
	 * 得到date月的最后一天
	 * 
	 * @return
	 */
	public static Timestamp getMonthLastDayTimestamp(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new Timestamp(calendar.getTime().getTime());		
	}
	/**
	 * 上月一个月第一天和最后一天
	 * 
	 * @param
	 * @return
	 * @throws ParseException 
	 */
	public static Map<String, String> getFirstday_Lastday_Month() throws ParseException
	{
		Date datea = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(datea);
		calendar.add(Calendar.MONTH, -1);
		Date theDate = calendar.getTime();

		// 上个月第一天
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first);//.append(" 00:00:00");
		day_first = str.toString();

		// 上个月最后一天
		calendar.add(Calendar.MONTH, 1); // 加一个月
		calendar.set(Calendar.DATE, 1); // 设置为该月第一天
		calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
		String day_last = df.format(calendar.getTime());
		StringBuffer endStr = new StringBuffer().append(day_last);//.append(" 23:59:59");
		day_last = endStr.toString();

		Map<String, String> map = new HashMap<String, String>();
		map.put("first", day_first);
		map.put("last", day_last);
		return map;
	}
	
	/**
	 * String类型转换为date类型
	 * @return
	 */
	public static Date getStringToDate(String str) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		try {
			return sf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * date类型转换为String类型
	 */
	public static String getDateToString(Date str) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(str);
	}
	
	/**
	 * 获得当前系统时间 String类型
	 */
	public static String getSystemDate() {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(new Date());
	}
	
	/**
	 * 获得当前系统时间 String类型 yyyy-MM-dd-HH-mm-ss
	 */
	public static String getSystemYMDHMS() {
		SimpleDateFormat sf = new SimpleDateFormat(format1);
		return sf.format(new Date());
	}
	
	/**
	 * 获得当前系统时间 Timestamp类型
	 */
	public static Timestamp getSystemTimestamp() {
		return new Timestamp(new Date().getTime());
	}
	
	/**
	 * date 转换为 timestamp
	 */
	public static Timestamp getDateToTimestamp(Date date){
		Timestamp ts = new Timestamp(date.getTime());
		return ts;
	}
	
	/**
	 * String的 yyyy-MM-dd 类型转换为date类型的 yyyy-MM-dd
	 * @return
	 */
	public static Date getStringAddDate(String str) {
		String ft = "yyyy-MM-dd";
		SimpleDateFormat sf = new SimpleDateFormat(ft);
		try {
			return sf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据传递过来的时间和参数 得到 后几个月的最后一天 如果是入住日是1号 则少一个月
	 */
	public static Timestamp getDateBeforeMonth(Date date,int number) {
		String ft = "dd";
		SimpleDateFormat sf = new SimpleDateFormat(ft);
		String dd = sf.format(date);
		if("01".equals(dd)){
			number = number-1;
		}
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, number);
        //System.out.println("---"+getDateToString(cal.getTime()));
        //System.out.println(getMonthLastDayTimestamp(new Timestamp(cal.getTime().getTime())));
        return  getMonthLastDayTimestamp(new Timestamp(cal.getTime().getTime()));
    }
	
	/**
	 * 根据2个时间 判断第二个参数的日和第一个参数的日相差多少天
	 */
	public static Integer getDateDayLag(Date date1,Date date2){
		SimpleDateFormat sf = new SimpleDateFormat("dd");
		Integer day1 = Integer.parseInt(sf.format(date1));
		//如果是1号 就是0天
		if(1==day1){
			return 0;
		}
		Integer day2 = Integer.parseInt(sf.format(date2));
		Integer day3 = day2-day1;
		day3+=1;
		if(day3<1){
			day3=0;
		}
		
		return day3;
	}

	/**  
	   * 得到几天后的时间  
	   * @return  
	   */  
	public static Date getDateAfter(Date d,int day){   
		Calendar now =Calendar.getInstance();   
		now.setTime(d);   
		now.set(Calendar.DATE,now.get(Calendar.DATE)+day);   
		return now.getTime();   
	}   
	
	/**
	 * 比较2个时间的大小
	 * @return
	 */

	public static Boolean compareDate(String date1,String date2){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                //System.out.println("dt1在dt2大");
                return true;
            }else if (dt1.getTime() < dt2.getTime()) {
                //System.out.println("dt1在dt2小");
                return false;
            }else if(dt1.getTime() == dt2.getTime()){
            	//System.out.println("一样大");
            	return true;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
		return false;
	}
	/**
	 * 上月一个月第一天和最后一天
	 * 
	 * @param datea
	 * @return
	 * @throws ParseException 
	 */
	public static Map<String, String> getFirstday_Lastday_Month(Date datea) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(datea);
		calendar.add(Calendar.MONTH, -1);
		Date theDate = calendar.getTime();

		// 上个月第一天
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first);//.append(" 00:00:00");
		day_first = str.toString();

		// 上个月最后一天
		calendar.add(Calendar.MONTH, 1); // 加一个月
		calendar.set(Calendar.DATE, 1); // 设置为该月第一天
		calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
		String day_last = df.format(calendar.getTime());
		StringBuffer endStr = new StringBuffer().append(day_last);//.append(" 23:59:59");
		day_last = endStr.toString();

		Map<String, String> map = new HashMap<String, String>();
		map.put("first", day_first);
		map.put("last", day_last);
		return map;
	}
	

//	public static String getLocalDate(){
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		String localDate = df.format(new Date());
//		return localDate;
//	}
	public static void main(String[] args) {
		//System.out.println(DateUtil.getDateDayLag(DateUtil.getStringToDate("2013-10-31 10:10:00"),DateUtil.getStringToDate("2013-10-31 10:10:00")));
		//new DateUtil().dateDiff( "2013-09-09","2014-1-30", "yyyy-MM-dd");
		//new DateUtil().dateDiff( "2013-02-09","2013-10-28", "yyyy-MM-dd");
		//System.out.println(getMonthLastDay(new Date()));
		//System.out.println(getMonthLastDayTimestamp(new Date()));
		//System.out.println(getDateToString(getMonthAfter(new Date(), 4)));
		//getDateBeforeMonth(getStringToDate("2013-10-11 10:10:00"),4);
		//System.out.println(getDateDayLag(new Date(), getStringToDate("2013-10-30 10:11:11")));
		
		//System.out.println(dateToStringShort(new Date()).equals("2013-12-20"));
		System.out.println(new Date().toString());
		System.out.println(compareDate("2014-02-21 11:31:22", getDateToString(new Date())));
		System.out.println(getDateToTimestamp(new Date()));
	}
}