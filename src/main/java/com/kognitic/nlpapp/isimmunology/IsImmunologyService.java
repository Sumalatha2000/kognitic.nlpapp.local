package com.kognitic.nlpapp.isimmunology;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

/**
 * 
 * @author Gowrisankar v
 *
 */
@Service
public class IsImmunologyService {
	@Autowired
	private IsImmunologyRepository isImmunologyRepository;

	public Iterable<Immunology> saveAll(List<Immunology> isCancerList) {
		return isImmunologyRepository.saveAll(isCancerList);
	}
	
	public void updateFincalIndicationByNctId(String nctId) {
		isImmunologyRepository.updateFinalIndicationByNctId(nctId);
	}
	
	@Transactional
	public void deleteByNctId(String nctId) {
		isImmunologyRepository.deleteByNctId(nctId);
		isImmunologyRepository.flush();
	}

}
