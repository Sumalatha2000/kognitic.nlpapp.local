package com.kognitic.nlpapp.gateservice;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import gate.Annotation;
import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.util.GateException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
/**
 * 
 * @author Gowrisankar v
 *
 */
@Service
public class GateService {
	private static final Logger log = LoggerFactory.getLogger(GateService.class);

	private static AtomicInteger indexCounter = new AtomicInteger(0);

	private int myIndex = indexCounter.getAndIncrement();

	// the GATE application, injected by Spring
	@Autowired
	private CorpusController application;

	// delay length, configured in application.properties
	@Value("${gate.processingDelay}")
	private long delay;

	private Corpus corpus;

	// called after dependencies have been injected, but before processWithGate
	// can be called
	@PostConstruct
	public void init() throws GateException {
		corpus = Factory.newCorpus("Service " + myIndex + " corpus");
		application.setCorpus(corpus);
	}

	// called at application shutdown time
	@PreDestroy
	public void destroy() {
		Factory.deleteResource(corpus);
		Factory.deleteResource(application);
	}

	public FeatureMap processWithGate(Document doc) throws GateException {
		try {
			log.info("Processing {} characters with handler {}", doc.getContent().size(), myIndex);
			corpus.add(doc);
			// run the pipeline
			application.execute();
			try {
				// fake a slow process
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

			// store the handler ID for display in the results table
			doc.getFeatures().put("handledBy", myIndex);
			for (Annotation document : doc.getAnnotations()) {
				System.out.println(document);
			}
			return doc.getFeatures();

		} finally {
			corpus.clear();
		}
	}
}
