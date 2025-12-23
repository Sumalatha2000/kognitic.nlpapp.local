package com.kognitic.nlpapp.neurosymptoms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Gowrisankar v
 *
 */
@Service
public class NeuroSymptomsService {

	@Autowired
	private NeuroSymptomsRepository neuroSymptomsRepository;

	public Iterable<NeuroSymptoms> saveAll(List<NeuroSymptoms> neuroList) {
		return neuroSymptomsRepository.saveAll(neuroList);
	}

}
