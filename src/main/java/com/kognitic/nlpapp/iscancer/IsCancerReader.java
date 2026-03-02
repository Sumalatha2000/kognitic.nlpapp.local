package com.kognitic.nlpapp.iscancer;

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
public class IsCancerReader implements AnnotationReader {
	private static final Logger log = LoggerFactory.getLogger(IsCancerReader.class);

	@Autowired
	private IsCancerService isCancerService;

	private String nctId;
	private AnnotationSet defaultAnnotationSet;
	private AnnotationSet isCancerAnnotationSet;
	private List<CancerType> isCancerList;

	@Override
	public void init(AnnotatedDocument annotatedDocument) {
		log.info("Intilizing isCancer reader");
		String nctId = annotatedDocument.getNctId();
		this.nctId = nctId;
		this.defaultAnnotationSet = annotatedDocument.getDefaultAnnotationSet();
		this.isCancerList = new ArrayList<CancerType>();

		deleteByNctId(nctId);
		readRequiredAnnotationSet();
		processAnnotationSet();
		saveAnnotationSet();
	}


	public void readRequiredAnnotationSet() {
		isCancerAnnotationSet = defaultAnnotationSet.get("CaTrail");

	}

	@Override
	public void processAnnotationSet() {
		try {
			if (isCancerAnnotationSet != null && !isCancerAnnotationSet.isEmpty()) {
				List<Annotation> annotationList = new ArrayList<>(isCancerAnnotationSet);
				Collections.sort(annotationList, new OffsetComparator());
				for (Iterator<Annotation> annotationIterator = annotationList.iterator(); annotationIterator
						.hasNext();) {
					CancerType cancerType = new CancerType();
					Annotation annotation = (Annotation) annotationIterator.next();
					FeatureMap features = annotation.getFeatures();

					cancerType.setNctId(nctId);
					cancerType.setKeyWord((String) features.get("Text"));
					cancerType.setFinding((String) features.get("isCancer"));
					cancerType.setSection((String) features.get("Section"));
					isCancerList.add(cancerType);
				}
			}
		} catch (Exception e) {
			log.error(nctId + "Exception occured in Indication", e);
		} finally {

		}
	}

	public void saveAnnotationSet() {
		if (CollectionUtils.isNotEmpty(isCancerList)) {
			isCancerService.saveAll(isCancerList);
			isCancerList.clear();
		}
	}
	

	private void deleteByNctId(String nctId) {
		 isCancerService.deleteByNctID(nctId);
		// TODO Auto-generated method stub
		
	}

}
