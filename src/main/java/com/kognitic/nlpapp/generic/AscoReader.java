package com.kognitic.nlpapp.generic;

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
public class AscoReader implements AnnotationReader {
	private static final Logger log = LoggerFactory.getLogger(AscoReader.class);

	@Autowired
	private AscoService indicationService;

	private String nctId;
	private AnnotationSet defaultAnnotationSet;
	private AnnotationSet indicationAnnotationSet;
	private List<Asco> indicationsList;

	@Override
	public void init(AnnotatedDocument annotatedDocument) {
		log.info("Intilizing indication reader");
		String nctId = annotatedDocument.getNctId();
		this.nctId = nctId;
		this.defaultAnnotationSet = annotatedDocument.getDefaultAnnotationSet();
		this.indicationsList = new ArrayList<Asco>();
		
		deleteByNctId(nctId);
		readRequiredAnnotationSet();
		processAnnotationSet();
		saveAnnotationSet();
		excuteStoredProcedure(nctId);
	}

	

	public void readRequiredAnnotationSet() {
		indicationAnnotationSet = defaultAnnotationSet.get("PageHeading");

	}

	@Override
	public void processAnnotationSet() {
		try {
			if (indicationAnnotationSet != null && !indicationAnnotationSet.isEmpty()) {
				List<Annotation> annotationList = new ArrayList<>(indicationAnnotationSet);
				Collections.sort(annotationList, new OffsetComparator());
				for (Iterator<Annotation> annotationIterator = annotationList.iterator(); annotationIterator
						.hasNext();) {
					Asco indication = new Asco();
					Annotation annotation = (Annotation) annotationIterator.next();
					FeatureMap features = annotation.getFeatures();

					indication.setNctId(nctId);
					indication.setKeyWord((String) features.get("Text"));
					indicationsList.add(indication);
				}
			}
		} catch (Exception e) {
			log.error(nctId + "Exception occured in Indication", e);
		} finally {

		}
	}

	public void saveAnnotationSet() {
		if (CollectionUtils.isNotEmpty(indicationsList)) {
			indicationService.softDelete(nctId);
			indicationService.saveAll(indicationsList);
			indicationsList.clear();
		}
	}

	private void excuteStoredProcedure(String nctId) {
//		indicationService.updateFincalIndicationByNctId(nctId);
	}
	
	private void deleteByNctId(String nctId) {
		indicationService.deletebyNctId(nctId);

	}
}
