package com.kognitic.nlpapp.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;

public class KogniticFileUtils {

	private static final String SRC_TRIALS_OFFLINE = "D:\\Xml\\march_source";
	private static final String TRIALS_DONE_DIR = "D:\\Xml\\march_FinalSuccess";
	private static final String TRIALS_ERROR_DIR = "D:\\Xml\\march_ErrorFiles2";

	public static List<File> getSelectedFiles() {
		List<File> listFiles = getPartitionElements();
		if (CollectionUtils.isNotEmpty(listFiles)) {
			return listFiles.stream().map(x -> {
				String src = SRC_TRIALS_OFFLINE + "\\" + x.getName();
				return new File(src);
			}).collect(Collectors.toList());
		}
		return null;
	}

	private static List<File> getPartitionElements() {
		File f = new File(SRC_TRIALS_OFFLINE);
		File[] files = f.listFiles();
		List<File> tempList = Arrays.asList(files);
		List<List<File>> listPartitions = ListUtils.partition(tempList, 100);
		return listPartitions.stream().findFirst().orElse(null);
	}

	public static List<File> getAllFiles() {
		File f = new File(SRC_TRIALS_OFFLINE);
		File[] files = f.listFiles();
		List<File> tempList = Arrays.asList(files);
		return tempList;
	}

	public static void moveErrorFiles(File file) {
		String dir = TRIALS_ERROR_DIR + "\\" + file.getName();
		moveFile(file, new File(dir));
	}

	public static void moveFiles(File file) {
		String dir = TRIALS_DONE_DIR + "\\" + file.getName();
		moveFile(file, new File(dir));
	}

	private static void createDir(String file) {
		Path path = Paths.get(file);
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void moveFiles(List<File> files) {
		for (File src : files) {
			String dir = TRIALS_DONE_DIR + "\\" + src.getName();
			moveFile(src, new File(dir));
		}
	}

	public static void moveFile(File original, File copied) {
		try {
			Files.move(original.toPath(), copied.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void copyFile(String filePath, String dir) {
		Path sourceFile = Paths.get(filePath);
		Path targetDir = Paths.get(dir);

		try {
			if (Files.notExists(targetDir))
				Files.createDirectory(targetDir);
			Path targetFile = targetDir.resolve(sourceFile.getFileName());
			Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			System.err.format("I/O Error when copying file");
		}
	}

	private void copyFileToDir(File file, Path copied) {
		try {
			Path originalPath = file.toPath();
			Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	@Test
//	public void main(String[] args) {
//		String src = "C:\\root_onc\\dp-feeds-trials_offline\\sources\\ctgov\\trials_offline\\NCT04363866.xml";
//		String dir = "C:\\root_onc\\dp-feeds-trials_offline\\sources\\ctgov\\trial_done";
//		copyFile(src, dir);
//	}

}
