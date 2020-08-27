package com.zhwld.iscada.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils
{

	public static final String[] months =
	{ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月", };

	public static final String[] quarters =
	{ "一季度", "二季度", "三季度", "四季度" };

	public static final String dateFormatStr = "yyyy-MM-dd HH:mm:ss";

	public DateUtils()
	{
	}

	/**
	 * 获取日期字符串。
	 * 
	 * <pre>
	 *  日期字符串格式： yyyy-MM-dd
	 *  其中：
	 *      yyyy   表示4位年。
	 *      MM     表示2位月。
	 *      dd     表示2位日。
	 * </pre>
	 * 
	 * @return String "yyyy-MM-dd"格式的日期字符串。
	 */
	public static String getDate()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(new Date());
	}

	/**
	 * 获取日期字符串。指定日期格式
	 * 
	 * <pre>
	 *  日期字符串格式： yyyy-MM-dd HH:mm:ss/yyyy-MM-dd HH:mm:ss.SSS
	 *  其中：
	 *      yyyy   表示4位年。
	 *      MM     表示2位月。
	 *      dd     表示2位日。
	 * </pre>
	 * 
	 * @return String "yyyy-MM-dd"格式的日期字符串。
	 */
	public static String getDate(String format)
	{
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			return formatter.format(new Date());
		} catch (Exception e)
		{
			return null;
		}
	}

	static List<String> getDateTimePatternList()
	{
		return Arrays.asList("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM-dd",
				"yyyy-MM-dd HH:mm:ss:SSS", "yyyy/MM/dd HH:mm:ss", "yyyyy/MM/dd HH:mm", "yyyy/MM/dd HH", "yyyy/MM/dd",
				"yyyy/MM/dd HH:mm:ss:SSS", "yyyy年MM月dd日 HH:mm:ss", "yyyy年MM月dd日 HH:mm", "yyyy年MM月dd日 HH", "yyyy年MM月dd日",
				"yyyy年MM月dd日 HH:mm:ss:SSS");
	}

	public static Date tryParseDateTimeString(String datetimeString)
	{
		Date ret = null;
		List<String> listPattern = getDateTimePatternList();
		for (int i = 0; i < listPattern.size(); i++)
		{
			try
			{
				ret = new SimpleDateFormat(listPattern.get(i)).parse(datetimeString);
				break;
			} catch (Exception ex)
			{

			}
		}
		return ret;
	}

	public static String tryGetDateTimeString(Date date, String dateTimePattern)
	{
		String ret = "";
		if (date == null)
		{
			return "";
		}
		try
		{
			ret = new SimpleDateFormat(dateTimePattern).format(date);
		} catch (Exception ex)
		{
			ret = "";
		}

		return ret;
	}

	/**
	 * 为日期添加时间后缀
	 * 
	 * @param date
	 *            日期格式
	 * @param appendTime
	 *            :追加的时间串，格式为：HH:mm:ss，如23：59：59
	 * @return 日期对象
	 */
	public static Date tryDateAppendTime(Date date, String appendTime)
	{
		String strDateTime = tryGetDateTimeString(date, "yyyy-MM-dd");
		strDateTime = String.format("%s %s", strDateTime, appendTime);
		return tryParseDateTimeString(strDateTime);
	}

	/**
	 * 获取一天开始时间：包含时分秒
	 * 
	 * @param dateTimeString
	 * @return
	 */
	public static String getStartDateTime(String dateTimeString)
	{
		String ret = null;
		try
		{
			Date d = tryParseDateTimeString(dateTimeString);
			if (d != null)
			{

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(d);
				ret = String.format("%04d-%02d-%02d 00:00:00", calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
			}
		} catch (Exception ex)
		{

		}
		return ret;
	}

	/**
	 * 获取一天结束时间：包含时分秒
	 * 
	 * @param dateTimeString
	 * @return
	 */
	public static String getEndDateTime(String dateTimeString)
	{
		String ret = null;
		try
		{
			Date d = tryParseDateTimeString(dateTimeString);
			if (d != null)
			{

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(d);
				ret = String.format("%04d-%02d-%02d 23:59:59", calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
			}
		} catch (Exception ex)
		{

		}
		return ret;
	}

	/**
	 * 获取日期表示串
	 * 
	 * @param dateTimeString
	 * @return
	 */
	public static String getDateString(String dateTimeString)
	{
		String ret = null;
		try
		{
			Date d = tryParseDateTimeString(dateTimeString);
			if (d != null)
			{
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(d);
				ret = String.format("%04d-%02d-%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
						calendar.get(Calendar.DATE));
			}
		} catch (Exception ex)
		{

		}
		return ret;
	}

	public static String getDateTimeString(String dateTimeString)
	{
		String ret = "";
		try
		{
			Date d = tryParseDateTimeString(dateTimeString);
			if (d != null)
			{
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(d);
				ret = String.format("%04d-%02d-%02d %02d:%02d:%02d", calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE),
						calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)

				);
			}
		} catch (Exception ex)
		{

		}
		return ret;
	}

	/**
	 * 获取当前年度字符串。
	 * 
	 * <pre>
	 *  日期字符串格式： yyyy
	 *  其中：
	 *      yyyy   表示4位年。
	 * </pre>
	 * 
	 * @return String "yyyy"格式的当前年度字符串。
	 */
	public static String getNowYear()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		return formatter.format(new Date());
	}

	/**
	 * 获取当前月度字符串。
	 * 
	 * <pre>
	 *  日期字符串格式： MM
	 *  其中：
	 *      MM   表示4位年。
	 * </pre>
	 * 
	 * @return String "yyyy"格式的当前月度字符串。
	 */
	public static String getNowMonth()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		return formatter.format(new Date());
	}

	/**
	 * 获取当前月度字符串。
	 * 
	 * <pre>
	 *  日期字符串格式： dd
	 *  其中：
	 *      dd   表示4位年。
	 * </pre>
	 * 
	 * @return String "yyyy"格式的当前月度字符串。
	 */
	public static String getNowDay()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd");
		return formatter.format(new Date());
	}

	/**
	 * 获取日期字符串。
	 * 
	 * <pre>
	 *  日期字符串格式： yyyyMMdd
	 *  其中：
	 *      yyyy   表示4位年。
	 *      MM     表示2位月。
	 *      dd     表示2位日。
	 * </pre>
	 * 
	 * @param date
	 *            需要转化的日期。
	 * @return String "yyyyMMdd"格式的日期字符串。
	 */
	public static String getDate(Date date)
	{
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			return formatter.format(date);
		} catch (Exception ex)
		{

		}
		return null;
	}

	public static String getFullDate(Date date)
	{
		try
		{

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return formatter.format(date);
		} catch (Exception ex)
		{

		}
		return null;
	}

	/**
	 * 返回指定格式的日期字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDate(Date date, String format)
	{
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			return formatter.format(date);
		} catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * 将"yyyyMMdd"格式的日期字符串转换为日期对象。
	 * 
	 * @param source
	 *            日期字符串。
	 * @return Date 日期对象。
	 */
	public static Date parsePlainDate(String source)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			return sdf.parse(source, new ParsePosition(0));
		} catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * 将“yyyy-MM-dd”格式的日期字符串转换为日期对象。
	 * 
	 * @param source
	 *            日期字符串。
	 * @return Date 日期对象。
	 */
	public static Date parseDate(String source)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(source, new ParsePosition(0));
		} catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * 将“yyyy-MM-dd HH:mm:ss”格式的日期字符串转换为日期对象。
	 *
	 * @param source
	 *            日期字符串。
	 * @return Date 日期对象。
	 */
	public static Date parseFullDate(String source)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.parse(source, new ParsePosition(0));
		} catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * 将指定格式的日期字符串转换为日期对象。
	 * 
	 * @param source
	 *            日期字符串。
	 * @param pattern
	 *            模式。
	 * @return Date 日期对象。
	 */
	public static Date parseDate(String source, String pattern)
	{
		if (source == null)
		{
			return null;
		}
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(source, new ParsePosition(0));
		} catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * 将“yyyy-MM-dd”格式的日期字符串转换为“yyyyMMdd”格式的日期字符串。
	 * 
	 * @param source
	 *            日期字符串。
	 * @return String "yyyymmdd"格式的日期字符串。
	 */
	public static String toPlainDate(String source)
	{
		Date date = parseDate(source);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(date);
	}

	/**
	 * 获取时间戳，将日期对象转换为时间戳类型。
	 * 
	 * @param date
	 *            日期对象
	 * @return Timestamp 时间戳
	 */
	public static Timestamp getTimestamp(Date date)
	{
		return new Timestamp(date.getTime());
	}

	/**
	 * 获取时间戳，将当前日期转换为时间戳类型。
	 * 
	 * @return Timestamp 时间戳
	 */
	public static Timestamp getTimestamp()
	{
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 将“yyyyMMdd”格式的日期字符串转换为Timestamp类型的对象。
	 * 
	 * @param source
	 *            日期字符串
	 * @return Timestamp 时间戳
	 */
	public static Timestamp parseTimestamp(String source)
	{
		Date date = parsePlainDate(source);
		return getTimestamp(date);
	}

	/**
	 * 获得年度周期 <br>
	 * Example:<br>
	 * DateUtils.getPeriodYear("20040229" , -1);<br>
	 * DateUtils.getPeriodYear("20040228" , -1);<br>
	 * DateUtils.getPeriodYear("20020230" , 2);<br>
	 * 
	 * @param source
	 *            时间串
	 * @param yearPeriod
	 *            年度周期 -1代表本时间的上一年度,以次类推。
	 * @return String 时间。
	 */
	public static String getPeriodYear(String source, int yearPeriod)
	{
		int p = Integer.parseInt(source.substring(0, 4)) + yearPeriod;
		String newYear = String.valueOf(p) + source.substring(4, source.length());
		Date date = parsePlainDate(newYear);
		String s = getDate(date);
		int ny = Integer.parseInt(s);
		int sy = Integer.parseInt(newYear);

		while (ny > sy)
		{
			sy--;
			ny = Integer.parseInt(getDate(parsePlainDate(String.valueOf(sy))));
		}

		return String.valueOf(sy);
	}

	/**
	 * 获取当前日期和时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 */
	public static String getCurrentDateStr()
	{
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	/**
	 * 获取当前日期和时间 yyyy-MM-dd HH:mm:ss 去掉毫秒
	 * 
	 * @return
	 */
	public static Date getCurrentDateTime()
	{
		return parseDate(getCurrentDateStr(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 日期相加
	 * 
	 * @param day
	 *            天数
	 * @return 返回相加后的日期yyyy-MM-dd HH:mm:ss
	 */
	public static String addDate(int day)
	{
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis() + ((long) day) * 24 * 3600 * 1000);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(c.getTime());
	}

	public static String addDate(int day, String format)
	{
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis() + ((long) day) * 24 * 3600 * 1000);
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(c.getTime());
	}

	public static Date getCurrentDate(int day)
	{
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis() + ((long) day) * 24 * 3600 * 1000);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(df.format(c.getTime()), new ParsePosition(0));
	}

	public static Date getCurrentDate(int day, String format)
	{
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis() + ((long) day) * 24 * 3600 * 1000);
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.parse(df.format(c.getTime()), new ParsePosition(0));
	}

	public static Date getCurrentDateForHour(int hour)
	{
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis() + ((long) hour) * 3600 * 1000);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(df.format(c.getTime()), new ParsePosition(0));
	}

	public static Date getCurrentDateForMinutes(int minutes)
	{
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis() + ((long) minutes) * 60 * 1000);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(df.format(c.getTime()), new ParsePosition(0));
	}

	/**
	 * 返回毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(Date date)
	{
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 根据毫秒返回日期
	 * 
	 * @param millis
	 * @return
	 */
	public static Date getDate(long millis)
	{
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(millis);
		return c.getTime();
	}

	/**
	 * 获取当前日期和时间
	 * 
	 * @param format
	 *            日期格式 例：yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String getNowDate(String format)
	{
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static String getTimeStr(String dateStr)
	{
		Date date = parseDate(dateStr, "yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(date);
	}

	public static String getTimeStr()
	{
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(new Date());
	}

	/**
	 * @param millisecond
	 * @return
	 */
	public static Date parseDate(Long millisecond)
	{
		if (null == millisecond)
		{
			return null;
		} else
		{
			return new Date(millisecond);
		}
	}

	/**
	 * 获取当年的第一天
	 * 
	 * @return
	 */
	public static Date getCurrYearFirst()
	{
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 获取当年的最后一天
	 * 
	 * @return
	 */
	public static Date getCurrYearLast()
	{
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearFirst(int year)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取某年最后一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearLast(int year)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}

	/**
	 * 获取两个日期的天数差
	 * 
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	public static String dateToDateTimeString(Date t, String format)
	{
		String ret = "";
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			ret = sdf.format(t);
		} catch (Exception ex)
		{

		}
		return ret;
	}

	/**
	 * 两时间相减，获取相差的秒数
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	public static long subtractDateToSeconds(Date startDate, Date endDate)
	{
		String strPattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat dfs = new SimpleDateFormat(strPattern);
		long between = 0;
		try
		{
			java.util.Date begin = dfs.parse(tryGetDateTimeString(startDate, strPattern));
			java.util.Date end = dfs.parse(tryGetDateTimeString(endDate, strPattern));
			between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		} catch (Exception ex)
		{

		}
		return between;
	}

	public static String getDateString(Date date)
	{
		String ret = "";
		if (date == null)
		{
			return ret;
		}
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.format(date);
			Calendar cal = sdf.getCalendar();
			ret = String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1/* 月份从0开始，加1校正 */,
					cal.get(Calendar.DAY_OF_MONTH));
		} catch (Exception ex)
		{
			ret = "";
		}
		return ret;
	}

	public static LocalDateTime getLocalDateTime(Date date)
	{
		if (date != null)
		{

			try
			{
				Instant instant = date.toInstant();
				ZoneId zoneId = ZoneId.systemDefault();

				LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
				return localDateTime;
			} catch (Exception e)
			{
				// TODO: handle exception
			}
		}
		return null;
	}

	public static Date getDate(LocalDateTime localDateTime)
	{
		if (localDateTime != null)
		{

			try
			{
				ZoneId zoneId = ZoneId.systemDefault();

				ZonedDateTime zdt = localDateTime.atZone(zoneId);

				Date date = Date.from(zdt.toInstant());
				return date;
			} catch (Exception e)
			{
				// TODO Auto-generated catch block

			}
		}
		return null;
	}

	public static LocalDateTime getLocalDateTime(String value)
	{
		return getLocalDateTime(value, "yyyy-MM-dd HH:mm:ss");
	}

	public static LocalDateTime getLocalDateTime(String value, String fomatter)
	{
		LocalDateTime localDate = null;
		if (!StringUtils.isEmpty(value))
		{
			DateTimeFormatter dateTimeFormatter = null;
			try
			{
				if (!StringUtils.isEmpty(fomatter))
				{
					dateTimeFormatter = DateTimeFormatter.ofPattern(fomatter);
				} else
				{
					dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				}
				localDate = LocalDateTime.parse(value, dateTimeFormatter);
			} catch (Exception e)
			{
				// TODO: handle exception
			}
		}
		return localDate;
	}

	public static String getDateTime(LocalDateTime dateTime, String fomatter)
	{
		String value = "";
		DateTimeFormatter dateTimeFormatter = null;
		if (dateTime != null)
		{
			if (!StringUtils.isEmpty(fomatter))
			{
				dateTimeFormatter = DateTimeFormatter.ofPattern(fomatter);
			} else
			{
				dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			}
			value = dateTime.format(dateTimeFormatter);
		}
		return value;
	}

	public static String getDateTime(LocalDateTime dateTime)
	{
		return getDateTime(dateTime, "yyyy-MM-dd HH:mm:ss");
	}

	public static String getTimeString(Date date, String formatPattern)
	{
		String ret = "";
		if (date == null)
		{
			return ret;
		}
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.format(date);
			Calendar cal = sdf.getCalendar();
			if (formatPattern.toUpperCase().equals("HH:MM:SS") == true)
			{
				ret = String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE),
						cal.get(Calendar.SECOND));
			} else if (formatPattern.toUpperCase().equals("HH:MM") == true)
			{
				ret = String.format("%02d:%02d", cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));
			} else if (formatPattern.toUpperCase().equals("HH") == true)
			{
				ret = String.format("%02d", cal.get(Calendar.HOUR));
			} else if (formatPattern.toUpperCase().equals("HH:MM:SS:SSS") == true)
			{
				ret = String.format("%02d:%02d:%02d:%03d", cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE),
						cal.get(Calendar.SECOND), cal.get(Calendar.MILLISECOND));
			} else
			{// 默认时分秒
				ret = String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE),
						cal.get(Calendar.SECOND));
			}
		} catch (Exception ex)
		{
			ret = "";
		}
		return ret;
	}

	public static LocalDateTime UDateToLocalDateTime(Date date)
	{
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		return localDateTime;
	}

	// 02. java.util.Date --> java.time.LocalDate
	public static LocalDate UDateToLocalDate(Date date)
	{

		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		LocalDate localDate = localDateTime.toLocalDate();
		return localDate;
	}

	// 03. java.util.Date --> java.time.LocalTime
	public static LocalTime UDateToLocalTime(Date date)
	{

		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		LocalTime localTime = localDateTime.toLocalTime();
		return localTime;
	}

	// 05. java.time.LocalDate --> java.util.Date
	public static Date LocalDateToUdate(LocalDate localDate)
	{

		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
		java.util.Date date = Date.from(instant);
		return date;
	}

	// 06. java.time.LocalTime --> java.util.Date
	public static Date LocalTimeToUdate(LocalDateTime localDateTime)
	{

		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		java.util.Date date = Date.from(instant);
		return date;
	}
}
