package com.kognitic.nlpapp.neurosymptoms;

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
public class NeuroSymptomsReader implements AnnotationReader {

	private static final Logger log = LoggerFactory.getLogger(NeuroSymptomsReader.class);

	@Autowired
	private NeuroSymptomsService neuroSymptomsService;
	
	
	private String nctId;
	private AnnotationSet defaultAnnotationSet;

	private AnnotationSet neuroAnnotationSet;
	private List<NeuroSymptoms> neuroList;
	
	

	@Override
	public void init(AnnotatedDocument annotatedDocument) {
		log.info("Initializing NeuroSymptoms reader");
		String nctId = annotatedDocument.getNctId();
		this.nctId = nctId;
		this.defaultAnnotationSet = annotatedDocument.getDefaultAnnotationSet();

		this.neuroList = new ArrayList<NeuroSymptoms>();

		readRequiredAnnotationSet();
		processAnnotationSet();
		saveAnnotationSet();
		executeStoredProcedure(nctId);
	}

	public void readRequiredAnnotationSet() {

		neuroAnnotationSet = defaultAnnotationSet.get("NeuroSymptom"); 

	}

	@Override
	public void processAnnotationSet() {
		try {
			if (neuroAnnotationSet != null && !neuroAnnotationSet.isEmpty()) {
				List<Annotation> annotationList = new ArrayList<>(neuroAnnotationSet);
				Collections.sort(annotationList, new OffsetComparator());
				for (Iterator<Annotation> annotationIterator = annotationList.iterator(); annotationIterator
						.hasNext();) {
					NeuroSymptoms neuroSymptoms = new NeuroSymptoms();
					Annotation annotation = (Annotation) annotationIterator.next();
					FeatureMap features = annotation.getFeatures();

					neuroSymptoms.setNctId(nctId);
					neuroSymptoms.setKeyWord((String) features.get("Text"));
					neuroSymptoms.setFinding((String) features.get("Finding")); 
					neuroSymptoms.setSection((String) features.get("Section"));
					neuroList.add(neuroSymptoms);
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
				neuroSymptomsService.saveAll(neuroList);
				neuroList.clear();
			}
		}
	}

	    private void executeStoredProcedure(String nctId) {
	        try {
	            neuroSymptomsService.updateNeuroSymptomsByNctId(nctId); 
	            //log.info("Stored procedure UPDATE_NEURO_SYMPTOMS_BY_NCT executed for NCT ID: {}", nctId);
	        } catch (Exception e) {
	            log.error("Failed to execute stored procedure UPDATE_NEURO_SYMPTOMS_BY_NCT for NCT ID: " + nctId, e);
	        }
	    }
}
	