package com.kognitic.nlpapp.indications;

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
public class IndicationService {
	@Autowired
	private IndicationRepository indicationRepository;

	public Iterable<Indications> saveAll(List<Indications> indicationsList) {
		return indicationRepository.saveAll(indicationsList);
	}

	public List<Indications> findAllByNctId(String nctId) {
		return indicationRepository.findAllByNctId(nctId);
	}

	public void softDelete(String nctId) {
		List<Indications> indications = findAllByNctId(nctId);
		indications.forEach(x -> x.setDeleted(true));
		saveAll(indications);
	}

	public void updateFincalIndicationByNctId(String nctId) {
		indicationRepository.updateFinalIndicationByNctId(nctId);
	}
	
	@Transactional
	public void deleteByNctId(String nctId) {
		indicationRepository.deleteByNctId(nctId);
		indicationRepository.flush();
	}
	




}
