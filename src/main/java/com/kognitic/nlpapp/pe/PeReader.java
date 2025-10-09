package com.kognitic.nlpapp.pe;

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
public class PeReader implements AnnotationReader {
	private static final Logger log = LoggerFactory.getLogger(PeReader.class);

	@Autowired
	private PeService peService;

	private String nctId;
	private AnnotationSet defaultAnnotationSet;
	private AnnotationSet indicationAnnotationSet;
	private List<Pe> indicationsList;

	@Override
	public void init(AnnotatedDocument annotatedDocument) {
		log.info("Intilizing PE reader");
		String nctId = annotatedDocument.getNctId();
		this.nctId = nctId;
		this.defaultAnnotationSet = annotatedDocument.getDefaultAnnotationSet();
		this.indicationsList = new ArrayList<Pe>();

		readRequiredAnnotationSet();
		processAnnotationSet();
		saveAnnotationSet();
		excuteStoredProcedure(nctId);
	}

	public void readRequiredAnnotationSet() {
		indicationAnnotationSet = defaultAnnotationSet.get("Finalpe");

	}

	@Override
	public void processAnnotationSet() {
		try {
			if (indicationAnnotationSet != null && !indicationAnnotationSet.isEmpty()) {
				List<Annotation> annotationList = new ArrayList<>(indicationAnnotationSet);
				Collections.sort(annotationList, new OffsetComparator());
				for (Iterator<Annotation> annotationIterator = annotationList.iterator(); annotationIterator
						.hasNext();) {
					Pe indication = new Pe();
					Annotation annotation = (Annotation) annotationIterator.next();
					FeatureMap features = annotation.getFeatures();

					indication.setNctId(nctId);
					indication.setDrug((String) features.get("drug"));
					indication.setExposure((String) features.get("exposure"));
					indication.setSection((String) features.get("Section"));
					indication.setText((String) features.get("Text"));
					//indication.setPopulation((String) features.get("Population"));
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
			peService.saveAll(indicationsList);
			indicationsList.clear();
		}
	}

	private void excuteStoredProcedure(String nctId) {
		peService.updatePeTagByNctId(nctId);
	}
}
