package com.kognitic.nlpapp.her2low;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kognitic.nlpapp.gateservice.AnnotatedDocument;
import com.kognitic.nlpapp.gateservice.AnnotationReader;

import gate.Annotation;
import gate.AnnotationSet;
import gate.FeatureMap;
import gate.util.OffsetComparator;

@Component
public class Her2lowReader implements AnnotationReader {
	private static final Logger log = LoggerFactory.getLogger(Her2lowReader.class);

	@Autowired
	private Her2lowRepository her2lowRepository;

	private String nctId;
	private AnnotationSet defaultAnnotationSet;
	private AnnotationSet Her2lowAnnotationSet;
	private List<Her2low> her2lowList;

	@Override
	public void init(AnnotatedDocument annotatedDocument) {
		log.debug("Intilizing Her2low reader");
		String nctId = annotatedDocument.getNctId();
		this.nctId = nctId;
		this.defaultAnnotationSet = annotatedDocument.getDefaultAnnotationSet();
		this.her2lowList = new ArrayList<Her2low>();

		deleteByNctId(nctId);
		readRequiredAnnotationSet();
		processAnnotationSet();
		saveAnnotationSet();
		excuteStoredProcedure(nctId);
	}

	
	public void readRequiredAnnotationSet() {
		Her2lowAnnotationSet = defaultAnnotationSet.get("FinalHer2Low");

	}

	@Override
	public void processAnnotationSet() {
		try {
			if (Her2lowAnnotationSet != null && !Her2lowAnnotationSet.isEmpty()) {
				List<Annotation> annotationList = new ArrayList<>(Her2lowAnnotationSet);
				Collections.sort(annotationList, new OffsetComparator());
				for (Iterator<Annotation> annotationIterator = annotationList.iterator(); annotationIterator
						.hasNext();) {
					Her2low her2low = new Her2low();
					Annotation annotation = (Annotation) annotationIterator.next();
					FeatureMap features = annotation.getFeatures();

					her2low.setNctId(nctId);
					her2low.setKeyWord((String) features.get("Text"));
					her2low.setSection((String) features.get("Section"));
					her2lowList.add(her2low);
				}
			}
		} catch (Exception e) {
			log.error(nctId + "Exception occured in Indication", e);
		} finally {

		}
	}

	public void saveAnnotationSet() {
		if (CollectionUtils.isNotEmpty(her2lowList)) {
			her2lowRepository.saveAll(her2lowList);
			her2lowList.clear();
		}
	}

	private void excuteStoredProcedure(String nctId) {
//		her2lowRepository.moveFindalIndicationUpdateTest();
	}
	
	@Transactional
	private void deleteByNctId(String nctId2) {
		her2lowRepository.deleteByNctId(nctId2);
		
	}


}
