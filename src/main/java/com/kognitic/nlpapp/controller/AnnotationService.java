package com.kognitic.nlpapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kognitic.nlpapp.assay.AssayTypeReader;
import com.kognitic.nlpapp.biomarkers.BiomarkerReader;
import com.kognitic.nlpapp.dosage.DosageReader;
import com.kognitic.nlpapp.ecog.EcogReader;
import com.kognitic.nlpapp.gateservice.AnnotatedDocument;
import com.kognitic.nlpapp.gateservice.GateProcessor;
import com.kognitic.nlpapp.generic.AscoReader;
import com.kognitic.nlpapp.her2low.Her2lowReader;
import com.kognitic.nlpapp.immunologywho.IsImmunologyReaderwho;
import com.kognitic.nlpapp.indications.IndicationsReader;
import com.kognitic.nlpapp.indicationswho.IndicationsReaderWho;
import com.kognitic.nlpapp.iscancer.IsCancerReader;
import com.kognitic.nlpapp.iscancerwho.IsCancerReaderWho;
import com.kognitic.nlpapp.isimmunology.IsImmunologyReader;
import com.kognitic.nlpapp.lot.LotReader;
import com.kognitic.nlpapp.neuro.NeuroReader;
import com.kognitic.nlpapp.neurosymptomswho.NeuroSymptomswho;
import com.kognitic.nlpapp.neurosymptomswho.NeuroSymptomswhoReader;
import com.kognitic.nlpapp.neurowho.NeuroReaderWHO;
import com.kognitic.nlpapp.pe.PeReader;
import com.kognitic.nlpapp.racialdisparity.RacialDisparityReader;
import com.kognitic.nlpapp.roa.ROAReader;
import com.kognitic.nlpapp.specimen.SpecimenReader;
import com.kognitic.nlpapp.txduration.TxdurationReader;

import gate.AnnotationSet;
import gate.Document;
import gate.util.GateException;

/**
 * 
 * @author Gowrisankar v
 *
 */
@Service
public class AnnotationService {

	private static final Logger log = LoggerFactory.getLogger(AnnotationService.class);

	@Autowired
	private GateProcessor gateProcessor;

	@Autowired
	private IndicationsReader indicationsReader;

	@Autowired
	private BiomarkerReader biomarkerReader;

	@Autowired
	private Her2lowReader her2lowReader;

	@Autowired
	private ROAReader roaReader;

	@Autowired
	private AscoReader ascoReader;

	@Autowired
	private IsCancerReader isCancerReader;

	@Autowired
	private IsCancerReaderWho isCancerReaderWho;

	@Autowired
	private IndicationsReaderWho indicationsReaderWho;

	@Autowired
	private RacialDisparityReader racialdisparityReader;

	@Autowired
	private IsImmunologyReader isImmunologyReader;

	@Autowired
	private EcogReader ecogReader;

	@Autowired
	private TxdurationReader txdurationReader;

	@Autowired
	private DosageReader dosageReader;

	@Autowired
	private PeReader peReader;

	@Autowired
	private LotReader lotReader;

	@Autowired
	private AssayTypeReader assayReader;

	@Autowired
	private SpecimenReader specimenReader;

	@Autowired
	private IsImmunologyReaderwho isImmunologyReaderwho;

	@Autowired
	private NeuroReader neuroReader;
	
	@Autowired
	private NeuroReaderWHO neurowhoReaderWHO;
	
	@Autowired
	private NeuroSymptomswhoReader neuroSymptomswhoReader;

	@Value("${nlp.pipeline.indication.enabled}")
	private Boolean indicationEnabled;

	@Value("${nlp.pipeline.biomarker.enabled}")
	private Boolean biomarkerEnabled;

	@Value("${nlp.pipeline.her2low.enabled}")
	private Boolean her2lowEnabled;

	@Value("${nlp.pipeline.roa.enabled}")
	private Boolean roaEnabled;

	@Value("${nlp.pipeline.asco.enabled}")
	private Boolean ascoEnabled;

	@Value("${nlp.pipeline.iscancer.enabled}")
	private Boolean isCancerEnabled;

	@Value("${nlp.pipeline.pet.enabled}")
	private Boolean petEnabled;

	@Value("${nlp.pipeline.iscancerwho.enabled}")
	private Boolean iscancerWhoEnabled;

	@Value("${nlp.pipeline.indicationwho.enabled}")
	private Boolean indicationWhoEnabled;

	@Value("${nlp.pipeline.isimmunology.enabled}")
	private Boolean isimmunologyEnabled;

	@Value("${nlp.pipeline.isimmunologywho.enabled}")
	private Boolean isimmunologywhoEnabled;

	@Value("${nlp.pipeline.racialdisparity.enabled}")
	private Boolean racialdisparityEnabled;

	@Value("${nlp.pipeline.ecog.enabled}")
	private Boolean ecogEnabled;

	@Value("${nlp.pipeline.txDuration.enabled}")
	private Boolean txDurationEnabled;

	@Value("${nlp.pipeline.dosage.enabled}")
	private Boolean dosageEnabled;

	@Value("${nlp.pipeline.pe.enabled}")
	private Boolean peEnabled;

	@Value("${nlp.pipeline.lot.enabled}")
	private Boolean lotEnabled;

	@Value("${nlp.pipeline.assay.enabled}")
	private Boolean assayEnabled;

	@Value("${nlp.pipeline.specimen.enabled}")
	private Boolean specimenEnabled;

	@Value("${nlp.pipeline.neuro.enabled}")
	private Boolean neuroEnabled;
	
	@Value("${nlp.pipeline.neurowho.enabled}")
	private Boolean neurowhoEnabled;
	
	@Value("${nlp.pipeline.neurosymptomswho.enabled}")
	private Boolean neurosymptomswhoEnabled;

	public void handleDocumentAnnotations(String nctId, Document gateDocument) {
		AnnotatedDocument annotatedDocument = setAnnotatedDocumentDetails(nctId, gateDocument);
		initAnnotationReadingProcess(annotatedDocument);
	}

	private AnnotatedDocument setAnnotatedDocumentDetails(String nctId, Document gateDocument) {
		AnnotatedDocument annotatedDocument = new AnnotatedDocument();
		try {
			annotatedDocument.setNctId(nctId);
			AnnotationSet defaultAnnotationSet = gateProcessor.processDocument2(nctId, gateDocument);
			annotatedDocument.setDefaultAnnotationSet(defaultAnnotationSet);
		} catch (GateException e) {
			e.printStackTrace();
		}
		return annotatedDocument;
	}

	private void initAnnotationReadingProcess(AnnotatedDocument annotatedDocument) {
		if (indicationEnabled) {
			indicationsReader.init(annotatedDocument);
		} else if (biomarkerEnabled) {
			biomarkerReader.init(annotatedDocument);
		} else if (her2lowEnabled) {
			her2lowReader.init(annotatedDocument);
		} else if (roaEnabled) {
			roaReader.init(annotatedDocument);
		} else if (ascoEnabled) {
			ascoReader.init(annotatedDocument);
		} else if (isCancerEnabled) {
			isCancerReader.init(annotatedDocument);
		} else if (iscancerWhoEnabled) {
			isCancerReaderWho.init(annotatedDocument);
		} else if (indicationWhoEnabled) {
			indicationsReaderWho.init(annotatedDocument);
		} else if (isimmunologyEnabled) {
			isImmunologyReader.init(annotatedDocument);
		} else if (racialdisparityEnabled) {
			racialdisparityReader.init(annotatedDocument);
		} else if (ecogEnabled) {
			ecogReader.init(annotatedDocument);
		} else if (txDurationEnabled) {
			txdurationReader.init(annotatedDocument);
		} else if (dosageEnabled) {
			dosageReader.init(annotatedDocument);
		} else if (peEnabled) {
			peReader.init(annotatedDocument);
		} else if (lotEnabled) {
			lotReader.init(annotatedDocument);
		} else if (assayEnabled) {
			assayReader.init(annotatedDocument);
		} else if (specimenEnabled) {
			specimenReader.init(annotatedDocument);
		} else if (isimmunologywhoEnabled) {
			isImmunologyReaderwho.init(annotatedDocument);
		} else if (neuroEnabled) {
			neuroReader.init(annotatedDocument);
		}else if (neurowhoEnabled) {
			neurowhoReaderWHO.init(annotatedDocument);
		} else if (neurosymptomswhoEnabled) {
			neuroSymptomswhoReader.init(annotatedDocument);
		}

	}

}
