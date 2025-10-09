package com.kognitic.nlpapp.isimmunology;

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
public class IsImmunologyReader implements AnnotationReader {
	private static final Logger log = LoggerFactory.getLogger(IsImmunologyReader.class);

	@Autowired
	private IsImmunologyService isImmunologyService;

	private String nctId;
	private AnnotationSet defaultAnnotationSet;
	private AnnotationSet isCancerAnnotationSet;
	private List<Immunology> isCancerList;

	@Override
	public void init(AnnotatedDocument annotatedDocument) {
		log.info("Intilizing Immunology reader");
		String nctId = annotatedDocument.getNctId();
		this.nctId = nctId;
		this.defaultAnnotationSet = annotatedDocument.getDefaultAnnotationSet();
		this.isCancerList = new ArrayList<Immunology>();

		deleteByNctId(nctId);
		readRequiredAnnotationSet();
		processAnnotationSet();
		saveAnnotationSet();
		excuteStoredProcedure(nctId);
	}

	public void readRequiredAnnotationSet() {
		isCancerAnnotationSet = defaultAnnotationSet.get("FinalINP");

	}

	@Override
	public void processAnnotationSet() {
		try {
			if (isCancerAnnotationSet != null && !isCancerAnnotationSet.isEmpty()) {
				List<Annotation> annotationList = new ArrayList<>(isCancerAnnotationSet);
				Collections.sort(annotationList, new OffsetComparator());
				for (Iterator<Annotation> annotationIterator = annotationList.iterator(); annotationIterator
						.hasNext();) {
					Immunology immunology = new Immunology();
					Annotation annotation = (Annotation) annotationIterator.next();
					FeatureMap features = annotation.getFeatures();

					immunology.setNctId(nctId);
					immunology.setKeyWord((String) features.get("Text"));
					immunology.setFinding((String) features.get("Finding"));
					immunology.setSection((String) features.get("Section"));
					immunology.setSegmentation((String) features.get("Segmentation"));
					isCancerList.add(immunology);
				}
			}
		} catch (Exception e) {
			log.error(nctId + "Exception occured in Indication", e);
		} finally {

		}
	}

	public void saveAnnotationSet() {
		if (CollectionUtils.isNotEmpty(isCancerList)) {
			isImmunologyService.saveAll(isCancerList);
			isCancerList.clear();
		}
	}
	
	private void excuteStoredProcedure(String nctId) {
		isImmunologyService.updateFincalIndicationByNctId(nctId);
	}
	
	@Transactional
	private void deleteByNctId(String nctId) {
		isImmunologyService.deleteByNctId(nctId);
	}

}
