package com.kognitic.nlpapp.specimen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Gowrisankar v
 *
 */
@Service
public class SpecimenService {
	@Autowired
	private SpecimenRepository specimenRepository;

	public Iterable<Specimen> saveAll(List<Specimen> isCancerList) {
		return specimenRepository.saveAll(isCancerList);
	}
	
	public void updateFinalKeywordByNctId(String nctId) {
		specimenRepository.updateFinalKeywordByNctId(nctId);
	}


}
