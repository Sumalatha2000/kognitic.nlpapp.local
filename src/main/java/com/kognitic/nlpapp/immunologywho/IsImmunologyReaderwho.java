package com.kognitic.nlpapp.immunologywho;

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
public class IsImmunologyReaderwho implements AnnotationReader {
	private static final Logger log = LoggerFactory.getLogger(IsImmunologyReaderwho.class);

	@Autowired
	private IsImmunologyServicewho isImmunologyServicewho;

	private String nctId;
	private AnnotationSet defaultAnnotationSet;
	private AnnotationSet isImmuWhoAnnotationSet;
	private List<ImmunologyWho> isimmunologywhoList;

	@Override
	public void init(AnnotatedDocument annotatedDocument) {
		log.info("Intilizing Immunologywho reader");
		String nctId = annotatedDocument.getNctId();
		this.nctId = nctId;
		this.defaultAnnotationSet = annotatedDocument.getDefaultAnnotationSet();
		this.isimmunologywhoList = new ArrayList<ImmunologyWho>();

		deleteByNctId(nctId);
		readRequiredAnnotationSet();
		processAnnotationSet();
		saveAnnotationSet();
		// excuteStoredProcedure(nctId);
	}

	public void readRequiredAnnotationSet() {
		isImmuWhoAnnotationSet = defaultAnnotationSet.get("FinalINP");

	}

	@Override
	public void processAnnotationSet() {
		try {
			if (isImmuWhoAnnotationSet != null && !isImmuWhoAnnotationSet.isEmpty()) {
				List<Annotation> annotationList = new ArrayList<>(isImmuWhoAnnotationSet);
				Collections.sort(annotationList, new OffsetComparator());
				for (Iterator<Annotation> annotationIterator = annotationList.iterator(); annotationIterator
						.hasNext();) {
					ImmunologyWho immunology = new ImmunologyWho();
					Annotation annotation = (Annotation) annotationIterator.next();
					FeatureMap features = annotation.getFeatures();

					immunology.setNctId(nctId);
					immunology.setKeyWord((String) features.get("Text"));
					immunology.setFinding((String) features.get("Finding"));
					immunology.setSection((String) features.get("Section"));
					immunology.setSegmentation((String) features.get("Segmentation"));
					isimmunologywhoList.add(immunology);
				}
			}
		} catch (Exception e) {
			log.error(nctId + "Exception occured in Indication", e);
		} finally {

		}
	}

	public void saveAnnotationSet() {
		if (CollectionUtils.isNotEmpty(isimmunologywhoList)) {
			isImmunologyServicewho.saveAll(isimmunologywhoList);
			isimmunologywhoList.clear();
		}
	}
	
	private void excuteStoredProcedure(String nctId) {
		isImmunologyServicewho.updateFincalIndicationByNctId(nctId);
	}
	
	private void deleteByNctId(String nctId) {
		isImmunologyServicewho.deleteByNctId(nctId);
	}

	


}
