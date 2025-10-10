package com.kognitic.nlpapp.neurobiomarkers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NeuroBiomarkerService {
	@Autowired
	private NeuroBiomarkerRepository biomarkerRepository;

	public Iterable<NeuroBiomarker> saveAll(List<NeuroBiomarker> biomarkersList) {
		return biomarkerRepository.saveAll(biomarkersList);
	}

	public List<NeuroBiomarker> findAllByNctId(String nctId) {
		return biomarkerRepository.findAllByNctId(nctId);
	}

	public void softDelete(String nctId) {
		List<NeuroBiomarker> biomarkers = findAllByNctId(nctId);
		biomarkers.forEach(x -> x.setDeleted(true));
		saveAll(biomarkers);
	}
	
	public void updateFinalKeywordByNctId(String nctId) {
		biomarkerRepository.updateFinalKeywordByNctId(nctId);
	}
}
