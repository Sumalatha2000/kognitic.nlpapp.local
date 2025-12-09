package com.kognitic.nlpapp.neurosymptomswho;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kognitic.nlpapp.gateservice.AnnotatedDocument;
import com.kognitic.nlpapp.gateservice.AnnotationReader;

import gate.Annotation;
import gate.AnnotationSet;
import gate.FeatureMap;
import gate.util.OffsetComparator;

@Component
public class NeuroSymptomswhoReader implements AnnotationReader {

	private static final Logger log = LoggerFactory.getLogger(NeuroSymptomswhoReader.class);

	@Autowired
	private NeuroSymptomswhoService neuroSymptomswhoService;

	private String nctId;
	private AnnotationSet defaultAnnotationSet;

	private AnnotationSet neuroAnnotationSet;
	private List<NeuroSymptomswho> neuroList;

	@Override
	public void init(AnnotatedDocument annotatedDocument) {
		log.info("Initializing NeuroSymptomswho reader");
		String nctId = annotatedDocument.getNctId();
		this.nctId = nctId;
		this.defaultAnnotationSet = annotatedDocument.getDefaultAnnotationSet();

		this.neuroList = new ArrayList<NeuroSymptomswho>();

		readRequiredAnnotationSet();
		processAnnotationSet();
		saveAnnotationSet();
	}

	public void readRequiredAnnotationSet() {

		neuroAnnotationSet = defaultAnnotationSet.get("NeuroSymptomwho"); // check this

	}

	@Override
	public void processAnnotationSet() {
		try {
			if (neuroAnnotationSet != null && !neuroAnnotationSet.isEmpty()) {
				List<Annotation> annotationList = new ArrayList<>(neuroAnnotationSet);
				Collections.sort(annotationList, new OffsetComparator());
				for (Iterator<Annotation> annotationIterator = annotationList.iterator(); annotationIterator
						.hasNext();) {
					NeuroSymptomswho neuroSymptomswho = new NeuroSymptomswho();
					Annotation annotation = (Annotation) annotationIterator.next();
					FeatureMap features = annotation.getFeatures();

					neuroSymptomswho.setNctId(nctId);
					neuroSymptomswho.setKeyWord((String) features.get("Text"));
					neuroSymptomswho.setFinding((String) features.get("Finding")); // check this
					neuroSymptomswho.setSection((String) features.get("Section"));
					neuroList.add(neuroSymptomswho);
				}
			}
		} catch (Exception e) {
			log.error(nctId + "Exception occured in Indication", e);
		} finally {

		}
	}

	public void saveAnnotationSet() {
		{
			if (CollectionUtils.isNotEmpty(neuroList)) {
				neuroSymptomswhoService.saveAll(neuroList);
				neuroList.clear();
			}
		}

	}
}
