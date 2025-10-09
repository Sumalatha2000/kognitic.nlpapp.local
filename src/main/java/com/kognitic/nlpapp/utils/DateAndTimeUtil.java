package com.kognitic.nlpapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class DateAndTimeUtil {

	public final static SimpleDateFormat logDateFormat = new SimpleDateFormat("yyyy.MM.dd'_'HH.mm.ss z");

	/**
	 * October 1999
	 */
	public final static String dateFormat_my = "MMMM yyyy";

	/**
	 * December 17, 2004
	 */
	public final static String dateFormat_mdy = "MMMM d, yyyy";

	/**
	 * 3 November 1999 <br>
	 * 17 December 2004
	 */
	public final static String dateFormat_dmy = "d MMMM yyyy";

	/**
	 * 13/12/2016
	 */
	public final static String dateFormat_slash_dmy = "dd/MM/yyyy";

	/**
	 * 2016/10/18
	 */
	public final static String dateFormat_slash_ymd = "yyyy/MM/dd";

	/**
	 * 30-05-2008
	 */
	public final static String dateFormat_hyphen_dmy = "dd-MM-yyyy";

	/**
	 * 2016-12-21
	 */
	public final static String dateFormat_hyphen_ymd = "yyyy-MM-dd";

	/**
	 * 01/2006
	 */
	public final static String dateFormat_slash_my = "MM/yyyy";

	// Frequent test of Date text formats is required

	/**
	 * Parse and get Date of given text in specific format<br>
	 * Few Examples:(from Clinical trials)<br>
	 * October 1999 "MMMM yyyy" <br>
	 * <br>
	 * December 17, 2004 "MMMM d, yyyy" <br>
	 * November 3, 1999 <br>
	 * <br>
	 * 13/12/2016 "dd/MM/yyyy"; <br>
	 * 1/11/2004<br>
	 * 1/02/2005<br>
	 * 1/7/2011<br>
	 * 23/1/1998<br>
	 * 1/1/2011<br>
	 * <br>
	 * 2016/10/18 "yyyy/MM/dd" <br>
	 * <br>
	 * 30-05-2008 "dd-MM-yyyy" <br>
	 * <br>
	 * 2016-12-21 "yyyy-MM-dd"
	 * 
	 * 
	 * @param dateText
	 * @return
	 */
	public static Date parseTexttoDate(String dateText) {
		if (dateText == null)
			return null;
		Date dt = null;
		try {
			/*
			 * The metacharacters supported by REGEX API are: <([{\^-=$!|]})?*+.>
			 */

			// October 1999
			if (dateText.matches("[a-zA-Z]+\\s....")) {
				dt = DateAndTimeUtil.parseTexttoDate(dateText, dateFormat_my);
			}

			// December 17, 2004
			// November 3, 1999
			else if (dateText.matches("[a-zA-Z]+\\s.{1,2},\\s....")) {
				dt = DateAndTimeUtil.parseTexttoDate(dateText, dateFormat_mdy);
			}

			// 3 November 1999
			// 17 December 2004
			else if (dateText.matches(".{1,2}\\s[a-zA-Z]+\\s....")) {
				dt = DateAndTimeUtil.parseTexttoDate(dateText, dateFormat_dmy);
			}

			// 13/12/2016
			// 1/02/2005
			// 23/1/1998
			else if (dateText.matches(".{1,2}/.{1,2}/....")) {
				dt = DateAndTimeUtil.parseTexttoDate(dateText, dateFormat_slash_dmy);
			}

			// 2016/10/18
			else if (dateText.matches("..../../..")) {
				dt = DateAndTimeUtil.parseTexttoDate(dateText, dateFormat_slash_ymd);
			}

			// 30-05-2008
			else if (dateText.matches("..\\-..\\-....")) {
				dt = DateAndTimeUtil.parseTexttoDate(dateText, dateFormat_hyphen_dmy);
			}

			// 2016-12-21
			else if (dateText.matches("....\\-..\\-..")) {
				dt = DateAndTimeUtil.parseTexttoDate(dateText, dateFormat_hyphen_ymd);
			}

			// 01/2006
			else if (dateText.matches("../....")) {
				dt = DateAndTimeUtil.parseTexttoDate(dateText, dateFormat_slash_my);
			}

			else {
				System.out.println("Unknown Time format: " + dateText);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Issue at Time format: " + dateText);
		}
		return dt;
	}

	/**
	 * Parse and get Date of given text in specific format<br>
	 * 
	 * Few Examples:<br>
	 * 
	 * October 1999 i.e MMMM yyyy<br>
	 * 
	 * December 17, 2004 i.e MMMM d, yyyy<br>
	 * 
	 * 13/12/2016 i.e dd/MM/yyyy<br>
	 * 
	 * 1/11/2004<br>
	 * 
	 * 1/02/2005<br>
	 * 
	 * 30-05-2008 i.e dd-MM-yyyy<br>
	 * 
	 * @param dateText
	 * @param dateFormat
	 * @return
	 */
	public static Date parseTexttoDate(String dateText, String dateFormat) {
		if (dateText == null)
			return null;
		Date dt = null;
		try {
			dt = new SimpleDateFormat(dateFormat, Locale.ENGLISH).parse(dateText);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return dt;
	}

	/**
	 * Get current date in a specified format <br>
	 * Using this method append current time format for Result files or log files
	 * 
	 * @return Current Date in a specific format.
	 */
	public static String getCurrentLogTime() {
		return logDateFormat.format(System.currentTimeMillis());
	}

	/**
	 * Get current date in a specified format <br>
	 * Using this method append current time format for Result files or log files
	 * 
	 * @param past
	 * 
	 * @return Current Date in a specific format.
	 */
	public static String getTimeDifference(Instant start) {
		Instant end = Instant.now();
		// milliseconds
		long different = Duration.between(start, end).toMillis();

		// System.out.println("startDate : " + startDate);
		// System.out.println("endDate : "+ endDate);
		// System.out.println("different : " + different);

		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;

		long elapsedDays = different / daysInMilli;
		different = different % daysInMilli;

		long elapsedHours = different / hoursInMilli;
		different = different % hoursInMilli;

		long elapsedMinutes = different / minutesInMilli;
		different = different % minutesInMilli;

		long elapsedSeconds = different / secondsInMilli;
		different = different % secondsInMilli;

		Long elapsedMilliseconds = different;
		return String.format("%d minute(s), %d second(s), %d millisecond(s)%n", elapsedMinutes, elapsedSeconds,
				elapsedMilliseconds);

	}

	public static boolean equals(Date date1, Date date2) {
		LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate1.equals(localDate2);
	}

	public static boolean after(Date date1, Date date2) {
		LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate1.isAfter(localDate2);
	}

	public static boolean before(Date date1, Date date2) {
		LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate1.isBefore(localDate2);
	}

//	@Test
//	public void getTimeDifferenceTest() {
//		Instant start = Instant.now();
//		start = start.minusMillis(1000 * 60 * 102);
//		System.out.println(start);
//		System.out.println(Instant.now());
//		System.out.println(getTimeDifference(start));
//
//	}

}
