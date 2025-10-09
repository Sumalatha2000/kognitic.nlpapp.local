package com.kognitic.nlpapp.gateservice;

/**
 * 
 * @author Gowrisankar v
 *
 */
public interface AnnotationReaderCopy {
	void init(AnnotatedDocument annotatedDocument);

	void readRequiredAnnotationSet();

	void processAnnotationSet();

	void saveAnnotationSet();
}
