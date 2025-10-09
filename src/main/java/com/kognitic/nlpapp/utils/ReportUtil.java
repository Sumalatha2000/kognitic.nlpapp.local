package com.kognitic.nlpapp.utils;

import java.time.Instant;

public class ReportUtil {

	/**
	 * Utility classes, which are collections of static members, are not meant to be
	 * instantiated
	 */
	private ReportUtil() {
	}

	/**
	 * Total time taken message
	 * 
	 * @param start
	 * @return
	 */
	public static String timeDifference(Instant start) {
		return String.format("Time taken : %s", DateAndTimeUtil.getTimeDifference(start));
	}

}
