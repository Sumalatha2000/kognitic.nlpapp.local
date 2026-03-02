package com.kognitic.nlpapp.racialdisparity;

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
public class RacialDisparityReader implements AnnotationReader {
	private static final Logger log = LoggerFactory.getLogger(RacialDisparityReader.class);

	@Autowired
	private RacialDisparityService racialDisparityService;

	private String nctId;
	private AnnotationSet defaultAnnotationSet;
	private AnnotationSet indicationAnnotationSet;
	private List<RacialDisparity> biomarkersList;

	@Override
	public void init(AnnotatedDocument annotatedDocument) {
		log.info("Intilizing Biomarker reader");
		String nctId = annotatedDocument.getNctId();
		this.nctId = nctId;
		this.defaultAnnotationSet = annotatedDocument.getDefaultAnnotationSet();
		this.biomarkersList = new ArrayList<RacialDisparity>();
		
		deleteByNctId(nctId);
		readRequiredAnnotationSet();
		processAnnotationSet();
		saveAnnotationSet();

	}

	

	public void readRequiredAnnotationSet() {
		indicationAnnotationSet = defaultAnnotationSet.get("FinalRacialDisparity");
	}

	@Override
	public void processAnnotationSet() {
		try {
			if (indicationAnnotationSet != null && !indicationAnnotationSet.isEmpty()) {
				List<Annotation> annotationList = new ArrayList<>(indicationAnnotationSet);
				Collections.sort(annotationList, new OffsetComparator());
				for (Iterator<Annotation> annotationIterator = annotationList.iterator(); annotationIterator
						.hasNext();) {
					RacialDisparity racialDisparity = new RacialDisparity();
					Annotation annotation = (Annotation) annotationIterator.next();
					FeatureMap features = annotation.getFeatures();

					racialDisparity.setNctId(nctId);
					racialDisparity.setKeyWord((String) features.get("Text"));
					racialDisparity.setSection((String) features.get("Section"));
					racialDisparity.setOverAllStatus((String) features.get("OverAllStatus"));
					racialDisparity.setPhase((String) features.get("Phase"));
					racialDisparity.setCountry((String) features.get("Country"));
					racialDisparity.setTitle((String) features.get("BriefTitle"));
					;
					biomarkersList.add(racialDisparity);
				}
			}
		} catch (Exception e) {
			log.error(nctId + "Exception occured in Indication", e);
		} finally {

		}
	}

	public void saveAnnotationSet() {
		if (CollectionUtils.isNotEmpty(biomarkersList)) {
			racialDisparityService.softDelete(nctId);
			racialDisparityService.saveAll(biomarkersList);
			biomarkersList.clear();
		}
	}

	private void excuteStoredProcedure(String nctId) {
//		indicationRepository.moveFindalIndicationUpdateTest();
	}
	
	private void deleteByNctId(String nctId) {
		racialDisparityService.deleteByNctId(nctId);
		
	}

}
