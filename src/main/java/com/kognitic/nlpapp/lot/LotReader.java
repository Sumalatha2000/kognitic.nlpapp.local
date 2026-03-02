package com.kognitic.nlpapp.lot;

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
public class LotReader implements AnnotationReader {
	private static final Logger log = LoggerFactory.getLogger(LotReader.class);

	@Autowired
	private LotService lotService;

	private String nctId;
	private AnnotationSet defaultAnnotationSet;
	private AnnotationSet indicationAnnotationSet;
	private List<Lot> indicationsList;

	@Override
	public void init(AnnotatedDocument annotatedDocument) {
		log.info("Intilizing LoT reader");
		String nctId = annotatedDocument.getNctId();
		this.nctId = nctId;
		this.defaultAnnotationSet = annotatedDocument.getDefaultAnnotationSet();
		this.indicationsList = new ArrayList<Lot>();
		
		deleteByNctId(nctId);
		readRequiredAnnotationSet();
		processAnnotationSet();
		saveAnnotationSet();
		excuteStoredProcedure(nctId);
	}

	

	public void readRequiredAnnotationSet() {
		indicationAnnotationSet = defaultAnnotationSet.get("FinalLot");

	}

	@Override
	public void processAnnotationSet() {
		try {
			if (indicationAnnotationSet != null && !indicationAnnotationSet.isEmpty()) {
				List<Annotation> annotationList = new ArrayList<>(indicationAnnotationSet);
				Collections.sort(annotationList, new OffsetComparator());
				for (Iterator<Annotation> annotationIterator = annotationList.iterator(); annotationIterator
						.hasNext();) {
					Lot indication = new Lot();
					Annotation annotation = (Annotation) annotationIterator.next();
					FeatureMap features = annotation.getFeatures();

					indication.setNctId(nctId);
					indication.setLotKey((String) features.get("Lt"));
					indication.setPrefix((String) features.get("Prefix"));
					indication.setNumber((String) features.get("Number"));
					//indication.setText((String) features.get("Text"));
					indication.setSection((String) features.get("Section"));
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
			lotService.saveAll(indicationsList);
			indicationsList.clear();
		}
	}

	private void excuteStoredProcedure(String nctId) {
		lotService.updateLoTTagByNctId(nctId);
	}
	
	private void deleteByNctId(String nctId) {
		 lotService.deleteByNctId(nctId);

		
	}
}
