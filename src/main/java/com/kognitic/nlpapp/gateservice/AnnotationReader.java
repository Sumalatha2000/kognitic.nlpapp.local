package com.kognitic.nlpapp.gateservice;

/**
 * 
 * @author Gowrisankar v
 *
 */
public interface AnnotationReader {

	void init(AnnotatedDocument annotatedDocument);

	void processAnnotationSet();
}
