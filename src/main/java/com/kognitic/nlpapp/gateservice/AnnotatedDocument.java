package com.kognitic.nlpapp.gateservice;

import gate.AnnotationSet;
import gate.Document;
import lombok.Data;

/**
 * 
 * @author Gowrisankar v
 *
 */
@Data
public class AnnotatedDocument {
	private String nctId;
//	private Document document;
	private AnnotationSet defaultAnnotationSet;
}
