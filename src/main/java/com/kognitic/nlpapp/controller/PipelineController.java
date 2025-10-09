package com.kognitic.nlpapp.controller;

import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kognitic.nlpapp.gateservice.FilesService;
import com.kognitic.nlpapp.trialslog.TrialsLogService;
import com.kognitic.nlpapp.utils.CoreUtil;
import com.kognitic.nlpapp.utils.ExceptionUtil;
import com.kognitic.nlpapp.utils.ReportUtil;

import gate.Document;
import gate.Factory;

/**
 * 
 * @author Gowrisankar
 * @since 11-11-2021
 */
@Component
public class PipelineController {
	private static final Logger log = LoggerFactory.getLogger(PipelineController.class);

	@Autowired
	private AnnotationService annotationService;

	@Autowired
	private TrialsLogService trialsLogService;

	@Autowired
	private FilesService fileService;

	@Scheduled(fixedRate = 5000)
	public void excuteDocumentProcess() throws Exception {
		Instant start = Instant.now();
		CoreUtil.printRuntimeStat("Before Annota service ");
		excutePipelineOnDocs();
		CoreUtil.printRuntimeStat("After Annota service ");
		log.info(" " + ReportUtil.timeDifference(start));
	}

	private void excutePipelineOnDocs() throws Exception {
		List<Path> files = fileService.loadSelectedFiles(50);
		if (CollectionUtils.isNotEmpty(files)) {
				Instant start = Instant.now();
			for (final Path path : files) {
				Document gateDocument = Factory.newDocument(path.toUri().toURL());
				String nctId = path.getFileName().toString().replaceAll(".xml", "");
				log.debug("Processing document " + nctId + "...");
				try {
					annotationService.handleDocumentAnnotations(nctId, gateDocument);
					//trialsLogService.trialogging(nctId, Boolean.TRUE);
					fileService.moveFile(path);
				} catch (Exception e) {
					log.error("Exception at " + nctId + " " + ExceptionUtil.getStackTrace(e));
					//trialsLogService.trialogging(nctId, Boolean.FALSE);
					fileService.moveErrorFiles(path);
				} finally {
					Factory.deleteResource(gateDocument);
				}
				
			}
			//log.debug(nctId + " " + ReportUtil.timeDifference(start));
		} else {
			log.debug("Zero  trials to process");
		}
	}

}
