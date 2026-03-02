package com.kognitic.nlpapp.neurosymptomswho;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Gowrisankar v
 *
 */
@Service
public class NeuroSymptomswhoService {

	@Autowired
	private NeuroSymptomswhoRepository neuroSymptomswhoRepository;

	public Iterable<NeuroSymptomswho> saveAll(List<NeuroSymptomswho> neuroList) {
		return neuroSymptomswhoRepository.saveAll(neuroList);
	}
	@Transactional
	public void deleteByNctId(String nctId) {
		 neuroSymptomswhoRepository.deleteByNctId(nctId);
	}

}
