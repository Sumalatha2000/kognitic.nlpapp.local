package com.kognitic.nlpapp.neurowho;

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
import jakarta.transaction.Transactional;

@Component
public class NeuroReaderWHO implements AnnotationReader {
	private static final Logger log = LoggerFactory.getLogger(NeuroReaderWHO.class);

	@Autowired
	private NeuroServiceWHO neuroService;

	private String nctId;
	private AnnotationSet defaultAnnotationSet;
	private AnnotationSet neuroAnnotationSet;
	private List<NeuroWHO> neuroList;

	@Override
	public void init(AnnotatedDocument annotatedDocument) {
		log.info("Intilizing NeuroWHO reader");
		String nctId = annotatedDocument.getNctId();
		this.nctId = nctId;
		this.defaultAnnotationSet = annotatedDocument.getDefaultAnnotationSet();
		this.neuroList = new ArrayList<NeuroWHO>();

		deleteByNctId(nctId);
		readRequiredAnnotationSet();
		processAnnotationSet();
		saveAnnotationSet();
		excuteStoredProcedure(nctId);
	}

	public void readRequiredAnnotationSet() {
		neuroAnnotationSet = defaultAnnotationSet.get("FinalINP");

	}

	@Override
	public void processAnnotationSet() {
		try {
			if (neuroAnnotationSet != null && !neuroAnnotationSet.isEmpty()) {
				List<Annotation> annotationList = new ArrayList<>(neuroAnnotationSet);
				Collections.sort(annotationList, new OffsetComparator());
				for (Iterator<Annotation> annotationIterator = annotationList.iterator(); annotationIterator
						.hasNext();) {
					NeuroWHO neuro = new NeuroWHO();
					Annotation annotation = (Annotation) annotationIterator.next();
					FeatureMap features = annotation.getFeatures();

					neuro.setNctId(nctId);
					neuro.setKeyWord((String) features.get("Text"));
					neuro.setFinding((String) features.get("Finding"));
					neuro.setSection((String) features.get("Section"));
					neuro.setSegmentation((String) features.get("Segmentation"));
					neuroList.add(neuro);
				}
			}
		} catch (Exception e) {
			log.error(nctId + "Exception occured in Indication", e);
		} finally {

		}
	}

	public void saveAnnotationSet() {
		if (CollectionUtils.isNotEmpty(neuroList)) {
			neuroService.saveAll(neuroList);
			neuroList.clear();
		}
	}

	private void excuteStoredProcedure(String nctId) {
		neuroService.updateFincalIndicationByNctId(nctId);
	}

	@Transactional
	private void deleteByNctId(String nctId) {
		neuroService.deleteByNctId(nctId);
	}

}
