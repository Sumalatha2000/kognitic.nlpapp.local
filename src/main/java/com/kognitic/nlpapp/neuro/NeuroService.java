package com.kognitic.nlpapp.neuro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

/**
 * 
 * @author Raghav
 *
 */
@Service
public class NeuroService {
	@Autowired
	private NeuroRepository neuroRepository;

	public Iterable<Neuro> saveAll(List<Neuro> isCancerList) {
		return neuroRepository.saveAll(isCancerList);
	}
	
	public void updateFincalIndicationByNctId(String nctId) {
		neuroRepository.updateFinalIndicationByNctId(nctId);
	}
	
	@Transactional
	public void deleteByNctId(String nctId) {
		neuroRepository.deleteByNctId(nctId);
		neuroRepository.flush();
	}

}
