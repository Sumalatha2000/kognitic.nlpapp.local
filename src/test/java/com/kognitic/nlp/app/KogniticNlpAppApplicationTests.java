package com.kognitic.nlp.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.Resource;

import com.kognitic.nlpapp.gateservice.GateProcessor;

import gate.Document;
import gate.Factory;

@SpringBootTest
@ImportResource("/gate-beans.xml")
class KogniticNlpAppApplicationTests {
	@Value("classpath:trials_offline/*.xml")
	private Resource[] files;

	@Autowired
	private GateProcessor gateProcessor;

	@Test
	void annotationSetProcessTest() throws Exception {
		for (final Resource res : files) {
			Document gateDocument = Factory.newDocument(res.getURL());
			try {
				gateProcessor.processDocument(gateDocument);
			} finally {
				Factory.deleteResource(gateDocument);
			}
		}

	}

}
