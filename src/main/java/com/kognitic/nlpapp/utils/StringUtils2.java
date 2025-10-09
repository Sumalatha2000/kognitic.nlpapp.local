package com.kognitic.nlpapp.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class StringUtils2 {

	public static String subString(String text, int beginIndex, int endIndex) {
		text = Optional.ofNullable(text).orElse("");
		return text.substring(beginIndex, endIndex);
	}

	/**
	 * regex \n to replace paragraph text values<br>
	 * regex \n12spaces is to replace entire bullet point space.<br>
	 * Official, detailed description etc.. text values with bullet points they
	 * occupied \n12 spaces which is not able to replace by \n. <br>
	 * <br>
	 * 
	 * A newline (line feed) character ('\n'),<br>
	 * A carriage-return character followed immediately by a newline character
	 * ("\r\n"),<br>
	 * A standalone carriage-return character ('\r'),<br>
	 * A next-line character ('\u0085'),<br>
	 * A line-separator character ('\u2028'),<br>
	 * or A paragraph-separator character ('\u2029).
	 * 
	 * @param text
	 * @return String
	 */
	@Deprecated
	public static String stripText12spacesBulletpoint(String text) {
		if (StringUtils.isNotBlank(text)) {
			text = text.trim();
			if (text.contains("\n")) {
				/*
				 * Handling text with bullet format line is splitting with 12 spaces
				 */
				text = text.replace("\n            ", "");
				// Handling text which is paragraph with multiple lines
				text = text.replace("\n", "");
			}
		}
		return text;
	}

	/**
	 * Generate a string, from a set of strings which are separated by given
	 * Delimeter
	 * 
	 * @param values
	 * @return string
	 */
	public static <E> String joinByDelimater(Collection<E> collection, String delemeter) {
		return Optional.ofNullable(collection).orElseGet(Collections::emptyList).stream().map(String::valueOf)
				.collect(Collectors.joining(delemeter));
	}

	/**
	 * Delimiter is ||
	 * 
	 * @param <E>
	 * @param collection
	 * @return
	 */
	public static <E> String delimitedString(Collection<E> collection) {
		return delimitedString(collection, "||");
	}

	/**
	 * 
	 * @param <E>
	 * @param collection
	 * @return
	 */
	public static <E> String delimitedString(Collection<E> collection, String delimiter) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (E e : collection) {
			i++;
			if (i == 1)
				sb.append(e);
			else
				sb.append(delimiter + e);
		}
		return sb.toString();
	}

	/**
	 * Converts Throwable exception to string
	 * 
	 * @param throwable
	 * @return String
	 */
	public static String exceptionToString(Throwable throwable) {
		StringWriter sw = new StringWriter();
		throwable.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	/**
	 * Caseinsensitive and exactmatch
	 * 
	 * @param text
	 * @param match
	 * @return true if text contains keyword else false
	 */
	public static boolean containsTextExcatMatch(String text, String keyword) {
		return containsText(text, "(?i).*(\\W|\\A)" + keyword + "(\\W|\\z).*");
	}

	/**
	 * Caseinsensitive and fuzzymatch
	 * 
	 * @param text
	 * @param match
	 * @return boolean
	 */
	public static boolean containsTextFuzzyMatch(String text, String keyword) {
		return containsText(text, "(?i).*" + keyword + ".*");
	}

	/**
	 * Accept text and predicate to match the string
	 * 
	 * @param text
	 * @param match
	 * @return boolean
	 */
	public static boolean containsText(String text, String predicate) {
		boolean isMatch = Boolean.FALSE;
		String optText = Optional.ofNullable(text).orElse("");
		if (optText.matches(predicate))
			isMatch = Boolean.TRUE;
		return isMatch;
	}

	/**
	 * Cleanup nonword characters of string at prefix and suffix
	 * 
	 * @param text
	 * @return String
	 */
	public static String replaceNonwordsWithEmptyString(String text) {
		text = text.replaceAll("(^\\W+)|(\\W+$)", "");
		return text;
	}
//
//	@Test
//	public void test() {
//		String s = null;
//		test(s);
//		s = "";
//		test(s);
//		s = " ";
//		test(s);
//	}

	public void test(String s) {
		System.out.println(StringUtils.isBlank(s));
		System.out.println(StringUtils.isEmpty(s));
	}

}
