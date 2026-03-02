package com.kognitic.nlpapp.biomarkers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class BiomarkerService {
	@Autowired
	private BiomarkerRepository biomarkerRepository;

	public Iterable<Biomarker> saveAll(List<Biomarker> biomarkersList) {
		return biomarkerRepository.saveAll(biomarkersList);
	}

	public List<Biomarker> findAllByNctId(String nctId) {
		return biomarkerRepository.findAllByNctId(nctId);
	}

	public void softDelete(String nctId) {
		List<Biomarker> biomarkers = findAllByNctId(nctId);
		biomarkers.forEach(x -> x.setDeleted(true));
		saveAll(biomarkers);
	}
	
	public void updateFinalKeywordByNctId(String nctId) {
		biomarkerRepository.updateFinalKeywordByNctId(nctId);
	}
	
	@Transactional
	public void deleteByNctId(String nctId) {
	   biomarkerRepository.deleteByNctId(nctId);
	}
}
