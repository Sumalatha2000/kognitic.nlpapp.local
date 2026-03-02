package com.kognitic.nlpapp.overallstatus;

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
public class OverallStatusReader implements AnnotationReader {

	private static final Logger log = LoggerFactory.getLogger(OverallStatusReader.class);

	@Autowired
	private OverallStatusService overallStatusService;

	private String nctId;
	private AnnotationSet defaultAnnotationSet;
	private AnnotationSet overallStatusAnnotationSet;
	private List<OverallStatus> overallStatusList;

	@Override
	public void init(AnnotatedDocument annotatedDocument) {

		log.info("Initializing overall status reader");

		String nctId = annotatedDocument.getNctId();
		this.nctId = nctId;

		this.defaultAnnotationSet = annotatedDocument.getDefaultAnnotationSet();

		this.overallStatusList = new ArrayList<>();

		deleteByNctId(nctId);

		readRequiredAnnotationSet();

		processAnnotationSet();

		saveAnnotationSet();
	}

	public void readRequiredAnnotationSet() {

		// This must match your GATE annotation name
		overallStatusAnnotationSet = defaultAnnotationSet.get("OverallStatus");

	}

	@Override
	public void processAnnotationSet() {

		try {

			if (overallStatusAnnotationSet != null && !overallStatusAnnotationSet.isEmpty()) {

				List<Annotation> annotationList = new ArrayList<>(overallStatusAnnotationSet);

				Collections.sort(annotationList, new OffsetComparator());

				for (Iterator<Annotation> iterator = annotationList.iterator(); iterator.hasNext();) {

					Annotation annotation = iterator.next();

					FeatureMap features = annotation.getFeatures();

					OverallStatus status = new OverallStatus();

					status.setNctId(nctId);

					status.setOverallStatus((String) features.get("OverallStatus"));

					status.setReason((String) features.get("Reason"));

					overallStatusList.add(status);
				}
			}

		} catch (Exception e) {

			log.error(nctId + " Exception occurred in OverallStatusReader", e);

		}

	}

	public void saveAnnotationSet() {

		if (CollectionUtils.isNotEmpty(overallStatusList)) {

			overallStatusService.saveAll(overallStatusList);

			overallStatusList.clear();

		}

	}

    private void deleteByNctId(String nctId) {

        overallStatusService.deleteByNctId(nctId);

    }

}
