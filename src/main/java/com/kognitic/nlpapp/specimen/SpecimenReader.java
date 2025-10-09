package com.kognitic.nlpapp.specimen;

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
public class SpecimenReader implements AnnotationReader {
	private static final Logger log = LoggerFactory.getLogger(SpecimenReader.class);

	@Autowired
	private SpecimenService specimenService;

	private String nctId;
	private AnnotationSet defaultAnnotationSet;
	private AnnotationSet specimenAnnotationSet;
	private List<Specimen> specimenList;

	@Override
	public void init(AnnotatedDocument annotatedDocument) {
		log.info("Intilizing Specimen reader");
		String nctId = annotatedDocument.getNctId();
		this.nctId = nctId;
		this.defaultAnnotationSet = annotatedDocument.getDefaultAnnotationSet();
		this.specimenList = new ArrayList<Specimen>();

		readRequiredAnnotationSet();
		processAnnotationSet();
		saveAnnotationSet();
		excuteStoredProcedure(nctId);
	}

	public void readRequiredAnnotationSet() {
		specimenAnnotationSet = defaultAnnotationSet.get("FinalSpecimen");
		

	}

	@Override
	public void processAnnotationSet() {
		try {
			if (specimenAnnotationSet != null && !specimenAnnotationSet.isEmpty()) {
				List<Annotation> annotationList = new ArrayList<>(specimenAnnotationSet);
				Collections.sort(annotationList, new OffsetComparator());
				for (Iterator<Annotation> annotationIterator = annotationList.iterator(); annotationIterator
						.hasNext();) {
					Specimen specimen = new Specimen();
					Annotation annotation = (Annotation) annotationIterator.next();
					FeatureMap features = annotation.getFeatures();

					specimen.setNctId(nctId);
					specimen.setBiomarker((String) features.get("Biomarker"));
					specimen.setSpecimen((String) features.get("SText"));
					specimen.setSection((String) features.get("Section"));
					specimenList.add(specimen);
				}
			}
		} catch (Exception e) {
			log.error(nctId + "Exception occured in Indication", e);
		} finally {

		}
	}

	public void saveAnnotationSet() {
		if (CollectionUtils.isNotEmpty(specimenList)) {
			specimenService.saveAll(specimenList);
			specimenList.clear();
		}
	}
	
	private void excuteStoredProcedure(String nctId) {
		specimenService.updateFinalKeywordByNctId(nctId);
	}

}
