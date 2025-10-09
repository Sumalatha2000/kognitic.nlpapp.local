package com.kognitic.nlp.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndicationsReader {
	private static final Logger log = LoggerFactory.getLogger(IndicationsReader.class);
	private String nctId;

	public void init(String nctID) {
		log.info("Intilizing indication reader");
		this.nctId = nctID;
	}

	public void saveAnnotationSet() {
		System.out.println(nctId);
	}

}
