package com.kognitic.nlpapp.assay;

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
public class AssayTypeReader implements AnnotationReader {
	private static final Logger log = LoggerFactory.getLogger(AssayTypeReader.class);

	@Autowired
	private AssayTypeService assayTypeService;

	private String nctId;
	private AnnotationSet defaultAnnotationSet;
	private AnnotationSet assayAnnotationSet;
	private List<AssayType> assayTypeList;

	@Override
	public void init(AnnotatedDocument annotatedDocument) {
		log.info("Intilizing Assay reader");
		String nctId = annotatedDocument.getNctId();
		this.nctId = nctId;
		this.defaultAnnotationSet = annotatedDocument.getDefaultAnnotationSet();
		this.assayTypeList = new ArrayList<AssayType>();

		readRequiredAnnotationSet();
		processAnnotationSet();
		saveAnnotationSet();
		excuteStoredProcedure(nctId);
	}

	public void readRequiredAnnotationSet() {
		assayAnnotationSet = defaultAnnotationSet.get("FinalAssay");

	}

	@Override
	public void processAnnotationSet() {
		try {
			if (assayAnnotationSet != null && !assayAnnotationSet.isEmpty()) {
				List<Annotation> annotationList = new ArrayList<>(assayAnnotationSet);
				Collections.sort(annotationList, new OffsetComparator());
				for (Iterator<Annotation> annotationIterator = annotationList.iterator(); annotationIterator
						.hasNext();) {
					AssayType assayType = new AssayType();
					Annotation annotation = (Annotation) annotationIterator.next();
					FeatureMap features = annotation.getFeatures();

					assayType.setNctId(nctId);
					assayType.setBiomarker((String) features.get("Biomarker"));
					assayType.setAssay((String) features.get("AText"));
					assayType.setSection((String) features.get("Section"));
					assayTypeList.add(assayType);
				}
			}
		} catch (Exception e) {
			log.error(nctId + "Exception occured in Indication", e);
		} finally {

		}
	}

	public void saveAnnotationSet() {
		if (CollectionUtils.isNotEmpty(assayTypeList)) {
			assayTypeService.saveAll(assayTypeList);
			assayTypeList.clear();
		}
	}
	
	private void excuteStoredProcedure(String nctId) {
		assayTypeService.updateFinalKeywordByNctId(nctId);
	}

}
