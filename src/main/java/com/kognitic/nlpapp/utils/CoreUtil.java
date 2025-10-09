package com.kognitic.nlpapp.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

public class CoreUtil {

	/**
	 * Integer.parseInt of given string
	 * 
	 * @param num
	 * @return Integer data type
	 */
	public static Integer parseInt(String num) {
		try {
			return Integer.parseInt(num.trim());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Deletes if already a file exists with this name
	 * 
	 * @param file
	 * @param items
	 */
	public static void writeNewTextFile(String file, List<String> items) {
		File file2 = new File(file);
		fileDeleteIfExistsAndCreateNew(file2);
		writeTextFile(file2, items);
	}

	/**
	 * Deletes if already a file exists with this name
	 * 
	 * @param file
	 * @param items
	 */
	public static void writeNewTextFileWrite(File file, ArrayList<?> items) {
		fileDeleteIfExistsAndCreateNew(file);
		writeTextFile(file, items);
	}

	/**
	 * To write a simple text file
	 * 
	 * @param file
	 *            target filepath
	 * @param items
	 *            array of items
	 */
	public static void writeTextFile(File file, List<?> items) {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			for (int i = 0; i < items.size(); i++) {
				String nct = items.get(i) + "";
				bw.write(nct);
				if (i != items.size() - 1)
					bw.newLine();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// // backing up existing file.
	// public static void createbackup(File dir){
	// File bkp = new File(dir.)
	// if (dir.exists()) {
	// if (alltrbkp.exists()) {
	// CoreUtil.deleteDir(alltrbkp);
	// }
	// alltrdir.renameTo(new File(
	// DPFileManager.CTgovTrialsXMLFilesDir + "_backup"));
	// alltrdir.mkdirs();
	// } else
	// alltrdir.mkdirs();
	//
	// }

	public static void fileDeleteIfExists(File file) {
		if (file.exists())
			file.delete();
	}

	public static void fileDeleteIfExistsAndCreateNew(File file) {
		if (file.exists())
			file.delete();
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @return list of trials loaded from local file.
	 */
	public static List<String> readLocalTextFile(String filePath) {
		List<String> nctIDs = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
			nctIDs = stream.map(String::trim).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nctIDs;
	}

	/**
	 * Reads lines from local text file
	 * 
	 * @param file
	 * @return
	 */
	public static ArrayList<String> readTextFile(File file) {
		if (file.isDirectory())
			return null;

		ArrayList<String> keyList = null;
		FileReader fr;
		BufferedReader br = null;
		int i = 0;
		try {
			keyList = new ArrayList<String>();
			// if file in local folder
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (!line.equals(""))
					keyList.add(line);
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(file + " > Total no.of rows :" + i);
		return keyList;
	}

	/**
	 * Reads input words from local text file
	 */
	public static ArrayList<String> readTextFile(String file) {
		return readTextFile(new File(file));
	}

	/**
	 * Deletes all files and folders in this directory
	 * 
	 * @param dir
	 */
	public static void deleteDir(File dir) {
		if (dir.exists()) {
			for (File filORdir : dir.listFiles()) {
				if (filORdir.isDirectory()) {
					deleteDir(filORdir);
				}
				filORdir.delete();
			}
			dir.delete();
		}
	}

	/**
	 * @param dirName
	 * @return list of files
	 */
	public static ArrayList<File> getFilesList(String dirName) {
		File dir = new File(dirName);
		return getFilesList(dir);
	}

	/**
	 * @param dirName
	 * @return list of files
	 */
	public static ArrayList<File> getFilesList(File dir) {
		if (dir.isDirectory()) {
			ArrayList<File> files = new ArrayList<File>();
			for (File fi : dir.listFiles()) {
				files.add(fi);
			}
			return files;
		}
		return null;
	}

	/**
	 * @param dirName
	 * @return list of fileNames
	 */
	public static ArrayList<String> getFileNames(String dirName) {
		File dir = new File(dirName);
		return getFileNames(dir);
	}

	/**
	 * @param dirName
	 * @return list of fileNames
	 */
	public static ArrayList<String> getFileNames(File dir) {
		if (!dir.isDirectory()) {
			return null;
		}
		ArrayList<String> files = new ArrayList<String>();
		for (File fi : dir.listFiles()) {
			files.add(fi.getName());
		}
		return files;
	}

	/**
	 * Validating given raw file name based on WINDOWS File format as per standards.
	 * 
	 * @param fileName
	 * @return validated windows file name
	 */
	public static String winFileSystem(String fileName) {
		fileName = fileName.replaceAll("[^a-zA-Z0-9\\-\\.#]+", "#");
		return fileName;
	}

	/**
	 * Request Parameters(appends with the URL) with specific symbols and spaces not
	 * allowed to pass through JAVA URL. <br>
	 * Eg: encodeURLParams("Esophagogastreal Junction");
	 * 
	 * @param param
	 * @return encoded url
	 */
	public static String encodeURLParams(String param) {
		// Encode params before URL construction
		try {
			param = URLEncoder.encode(param, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return param;
	}

	/**
	 * Tracks runtime JVM stats.
	 * 
	 * @return null
	 */
	public static String printRuntimeStat() {
		Runtime rt = Runtime.getRuntime();
		String status = "Free Memory: " + rt.freeMemory() / (1024 * 1024) + "mb ; Max Memory: "
				+ rt.maxMemory() / (1024 * 1024) + "mb ; Available Processors: " + rt.availableProcessors();
		System.out.println(status);
		return status;
	}

	public static void printRuntimeStat(String location) {
		Runtime rt = Runtime.getRuntime();
		String status = "Free Memory: " + rt.freeMemory() / (1024 * 1024) + "mb ; Max Memory: "
				+ rt.maxMemory() / (1024 * 1024) + "mb ; Available Processors: " + rt.availableProcessors();
		System.out.println(status + " at " + location);
	}

	/**
	 * Trim input string if not null
	 * 
	 * @param str
	 * @return trimmed String
	 */
	public static String trimIfNotnull(String str) {
		if (str == null)
			return str;
		else
			return str.trim();
	}

	/**
	 * If input string is blank i.e empty or null returns empty.
	 * 
	 * @param str
	 *            to validate
	 * @return string validated string
	 */
	public static String blankToEmpty(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		return str;
	}

	/**
	 * If input string is blank i.e empty or null returns empty.<br>
	 * In input string is not blank then return trim value.
	 * 
	 * @param str
	 *            to validate
	 * @return string validated string
	 */
	public static String blankToEmptyORTrim(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		} else
			return str.trim();
	}

	/**
	 * If input Object is null returns "null" string.
	 * 
	 * @param obj
	 * @return
	 */
	public static Object nullAsStr(Object obj) {
		if (obj == null) {
			return "null";
		}
		return obj;
	}

	/**
	 * If input Object is null returns "null" string.
	 * 
	 * @param obj
	 * @return
	 */
	public static Object nullToEmptyStr(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj;
	}

	/**
	 * find big number
	 * 
	 * @param sizeA
	 * @param sizeB
	 * @return
	 */
	public static int findBigNum(int sizeA, int sizeB) {
		return sizeA > sizeB ? sizeA : sizeB;
	}

	/**
	 * find big number
	 * 
	 * @param args
	 * @return
	 */
	public static int findMaxSize(int... args) {
		int maxSize = 0;
		for (int arg : args) {
			maxSize = findBigNum(maxSize, arg);
		}
		return maxSize;
	}

}
