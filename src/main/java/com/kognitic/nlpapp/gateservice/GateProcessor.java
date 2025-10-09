package com.kognitic.nlpapp.gateservice;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import gate.AnnotationSet;
import gate.Document;
import gate.util.DocumentProcessor;
import gate.util.GateException;

/**
 * 
 * @author Gowrisankar v
 * @since 11-11-2021
 *
 */
@Service
public class GateProcessor {
	private static final Logger logger = LoggerFactory.getLogger(GateProcessor.class);

	@Autowired
	private DocumentProcessor documentProcessor;

	public GateProcessor() {
		logger.info("DocumentProcessor Bean initialized: " + documentProcessor);
	}

	/**
	 * 
	 * @param gateDocument
	 * @throws Exception
	 */
	public void processDocument(Document gateDocument) throws Exception {
		documentProcessor.processDocument(gateDocument);
		AnnotationSet annotationSet = gateDocument.getAnnotations();

		logger.info("Current Thread Processing:" + Thread.currentThread().getName());

//		collectExtractedAnnotationsInfo(annotationSet, "FinalIndications");
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException ex) {
			logger.debug("Exception raised in Thread:" + ex);
		}
	}

	/**
	 * 
	 * @param nctId
	 * @param gateDocument
	 * @return
	 * @throws GateException
	 * @throws Exception
	 */
	public AnnotatedDocument processDocument1(String nctId, Document gateDocument) throws GateException {
		AnnotatedDocument annotatedDocument = new AnnotatedDocument();
		documentProcessor.processDocument(gateDocument);
		AnnotationSet annotationSet = gateDocument.getAnnotations();
		logger.debug("Current Thread Processing:" + Thread.currentThread().getName());
		annotatedDocument.setNctId(nctId);
		annotatedDocument.setDefaultAnnotationSet(annotationSet);
		return annotatedDocument;
	}

	/**
	 * 
	 * @param nctId
	 * @param gateDocument
	 * @return
	 * @throws GateException
	 */
	public AnnotationSet processDocument2(String nctId, Document gateDocument) throws GateException {
		documentProcessor.processDocument(gateDocument);
		return gateDocument.getAnnotations();
	}

}
