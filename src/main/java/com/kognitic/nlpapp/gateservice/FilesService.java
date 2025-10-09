package com.kognitic.nlpapp.gateservice;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import gate.Corpus;
import gate.Factory;
import gate.creole.ResourceInstantiationException;
import gate.util.ExtensionFileFilter;
import jakarta.annotation.PostConstruct;

/**
 * 
 * @author Gowrisankar v
 * @since 02-18-2022
 *
 */
@Service
public class FilesService {

	@Value("${files.reading.path}")
	private String readingPath;

	@Value("${files.succuss.path}")
	private String succussPath;

	@Value("${files.error.path}")
	private String errorPath;

	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(Paths.get(readingPath));
			Files.createDirectories(Paths.get(succussPath));
			Files.createDirectories(Paths.get(errorPath));
		} catch (IOException e) {
			throw new RuntimeException("Could not create directories!");
		}
	}

	/**
	 * 
	 * @param filename
	 * @return
	 */
	public Resource load(String filename) {
		try {
			Path file = Paths.get(readingPath).resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	/**
	 * 
	 */
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(readingPath).toFile());
	}

	/**
	 * 
	 */
	public void copyFile() {
		try {
			FileSystemUtils.copyRecursively(Paths.get(readingPath).toFile(), Paths.get(succussPath).toFile());
		} catch (IOException e) {
			throw new RuntimeException("Could not copy the files!");
		}
	}

	public void moveErrorFiles(Path path) {
		createDirIfNotExists(errorPath);
		moveFile(path, Paths.get(errorPath).resolve(path.getFileName()));
	}

	public void moveFile(Path path) {
		createDirIfNotExists(succussPath);
		moveFile(path, Paths.get(succussPath).resolve(path.getFileName()));
	}

	public void moveFile(Path original, Path copied) {
		try {
			Files.move(original, copied, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("Could not move the file!");
		}
	}

	/**
	 * Moving list of files one to another directory
	 * 
	 * @param list
	 * @param original
	 * @param copied
	 */
	public void moveListOfFiles(List<String> list, String original, String copied) {
		createDirIfNotExists(original);
		createDirIfNotExists(copied);
		for (String nctId : list) {
			Path originalPath = Paths.get(original).resolve(nctId + ".xml");
			Path copiedPath = Paths.get(copied).resolve(nctId + ".xml");
			moveFile(originalPath, copiedPath);
		}
	}

	public void createDirIfNotExists(String path) {
		try {
			if (!Files.exists(Paths.get(path)))
				Files.createDirectories(Paths.get(path));

		} catch (IOException e) {
			throw new RuntimeException("Could not create directory!");
		}
	}

	/**
	 * 
	 * @return
	 */
	public List<Path> loadAllFiles() {
		try {
			Path root = Paths.get(readingPath);
			if (Files.exists(root)) {
				return Files.walk(root, 1).filter(path -> !path.equals(root)).collect(Collectors.toList());
			}
			return Collections.emptyList();
		} catch (IOException e) {
			throw new RuntimeException("Could not list the files!");
		}
	}

	/**
	 * 
	 * @param count
	 * @return
	 */
	public List<Path> loadSelectedFiles(int count) {
		try {
			Path root = Paths.get(readingPath);
			if (Files.exists(root)) {
				return Files.walk(root, 1).filter(path -> !path.equals(root)).limit(count).collect(Collectors.toList());
			}
			return Collections.emptyList();
		} catch (IOException e) {
			throw new RuntimeException("Could not list the files!");
		}
	}

	/**
	 * 
	 * @throws ResourceInstantiationException
	 * @throws IOException
	 */
	public void createCorpusAllFilesInDir() throws ResourceInstantiationException, IOException {
		Corpus corpus = Factory.newCorpus("My XML Files");
		File directory = new File(readingPath);
		ExtensionFileFilter filter = new ExtensionFileFilter("XML files", "xml");
		URL url = directory.toURI().toURL();
		corpus.populate(url, filter, null, false);
	}

}
