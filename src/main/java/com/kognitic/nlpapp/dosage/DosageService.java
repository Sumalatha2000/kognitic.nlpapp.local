package com.kognitic.nlpapp.dosage;

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
public class DosageService {
	@Autowired
	private DosageRepository dosageRepository;

	public Iterable<Dosage> saveAll(List<Dosage> indicationsList) {
		return dosageRepository.saveAll(indicationsList);
	}

	public List<Dosage> findAllByNctId(String nctId) {
		return dosageRepository.findAllByNctId(nctId);
	}

	public void softDelete(String nctId) {
		List<Dosage> dosage = findAllByNctId(nctId);
		dosage.forEach(x -> x.setDeleted(true));
		saveAll(dosage);
	}

	public void updateFincalIndicationByNctId(String nctId) {
		dosageRepository.updateFinalIndicationByNctId(nctId);
	}

	@Transactional
	public void deleteByNctId(String nctId) {
		dosageRepository.deleteByNctId(nctId);	
		
		
	}

}
