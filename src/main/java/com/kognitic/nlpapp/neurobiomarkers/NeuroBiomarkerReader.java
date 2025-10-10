package com.kognitic.nlpapp.neurobiomarkers;

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
public class NeuroBiomarkerReader implements AnnotationReader {
	private static final Logger log = LoggerFactory.getLogger(NeuroBiomarkerReader.class);

	@Autowired
	private NeuroBiomarkerService biomarkerService;
	
	@Autowired
	private NeuroBiomarkerRepository biomarkerRepository;

	private String nctId;
	private AnnotationSet defaultAnnotationSet;
	private AnnotationSet indicationAnnotationSet;
	private List<NeuroBiomarker> biomarkersList;

	@Override
	public void init(AnnotatedDocument annotatedDocument) {
		log.info("Intilizing Neuro Biomarker reader");
		String nctId = annotatedDocument.getNctId();
		this.nctId = nctId;
		this.defaultAnnotationSet = annotatedDocument.getDefaultAnnotationSet();
		this.biomarkersList = new ArrayList<NeuroBiomarker>();
		readRequiredAnnotationSet();
		processAnnotationSet();
		saveAnnotationSet();
//		excuteStoredProcedure(nctId);
	}

	public void readRequiredAnnotationSet() {
		indicationAnnotationSet = defaultAnnotationSet.get("FinalBiomarkerKeyWords");
	}

	@Override
	public void processAnnotationSet() {
		try {
			if (indicationAnnotationSet != null && !indicationAnnotationSet.isEmpty()) {
				List<Annotation> annotationList = new ArrayList<>(indicationAnnotationSet);
				Collections.sort(annotationList, new OffsetComparator());
				for (Iterator<Annotation> annotationIterator = annotationList.iterator(); annotationIterator
						.hasNext();) {
					NeuroBiomarker biomarker = new NeuroBiomarker();
					Annotation annotation = (Annotation) annotationIterator.next();
					FeatureMap features = annotation.getFeatures();

					biomarker.setNctId(nctId);
					biomarker.setKeyWord((String) features.get("Text"));
					biomarker.setFinding((String) features.get("Finding"));
					biomarker.setSection((String) features.get("Section"));
					biomarker.setFirstPosted((String) features.get("FirstPosted"));
					biomarker.setLastPosted((String) features.get("LastPosted"));
					biomarker.setOverAllStatus((String) features.get("OverAllStatus"));
					biomarker.setPhase((String) features.get("Phase"));
					biomarkersList.add(biomarker);
				}
			}
		} catch (Exception e) {
			log.error(nctId + "Exception occured in Indication", e);
		} finally {

		}
	}

	public void saveAnnotationSet() {
		if (CollectionUtils.isNotEmpty(biomarkersList)) {
			biomarkerService.softDelete(nctId);
			biomarkerService.saveAll(biomarkersList);
			biomarkersList.clear();
		}
	}

	private void excuteStoredProcedure(String nctId) {
		biomarkerService.updateFinalKeywordByNctId(nctId);
	}

}
