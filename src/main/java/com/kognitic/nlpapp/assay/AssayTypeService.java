package com.kognitic.nlpapp.assay;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Gowrisankar v
 *
 */
@Service
public class AssayTypeService {
	@Autowired
	private AssayTypeRepository assayTypeRepository;

	public Iterable<AssayType> saveAll(List<AssayType> isCancerList) {
		return assayTypeRepository.saveAll(isCancerList);
	}
	
	public void updateFinalKeywordByNctId(String nctId) {
		assayTypeRepository.updateFinalKeywordByNctId(nctId);
	}

}
