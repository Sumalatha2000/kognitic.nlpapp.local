package com.kognitic.nlpapp.roa;

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
public class ROAReader implements AnnotationReader {
	private static final Logger log = LoggerFactory.getLogger(ROAReader.class);


	@Autowired
	private ROAService roaService;
	

	private String nctId;
	private AnnotationSet defaultAnnotationSet;
	private AnnotationSet roaAnnotationSet;
	private List<ROA> roaList;

	@Override
	public void init(AnnotatedDocument annotatedDocument) {
		log.debug("Intilizing ROA reader");
		String nctId = annotatedDocument.getNctId();
		this.nctId = nctId;
		this.defaultAnnotationSet = annotatedDocument.getDefaultAnnotationSet();
		this.roaList = new ArrayList<ROA>();

		deleteByNctId(nctId);
		readRequiredAnnotationSet();
		processAnnotationSet();
		saveAnnotationSet();
		excuteStoredProcedure(nctId);
	}



	public void readRequiredAnnotationSet() {
		roaAnnotationSet = defaultAnnotationSet.get("FinalRoA");

	}

	@Override
	public void processAnnotationSet() {
		try {
			if (roaAnnotationSet != null && !roaAnnotationSet.isEmpty()) {
				List<Annotation> annotationList = new ArrayList<>(roaAnnotationSet);
				Collections.sort(annotationList, new OffsetComparator());
				for (Iterator<Annotation> annotationIterator = annotationList.iterator(); annotationIterator
						.hasNext();) {
					ROA roa = new ROA();
					Annotation annotation = (Annotation) annotationIterator.next();
					FeatureMap features = annotation.getFeatures();

					roa.setNctId(nctId);
					roa.setDrug((String) features.get("drugs"));
					roa.setRoute((String) features.get("Route"));
					roa.setSection((String) features.get("Section"));
					roaList.add(roa);
				}
			}
		} catch (Exception e) {
			log.error(nctId + "Exception occured in ROA", e);
		} finally {

		}
	}

	public void saveAnnotationSet() {
		if (CollectionUtils.isNotEmpty(roaList)) {
			roaService.saveAll(roaList);
			roaList.clear();
		}
	}

	private void excuteStoredProcedure(String nctId) {
		roaService.updateStandardRouteByNct_ID(nctId);
	}
	
	private void deleteByNctId(String nctId) {
		 roaService.deleteByNctId(nctId);
		
		
	}

}
