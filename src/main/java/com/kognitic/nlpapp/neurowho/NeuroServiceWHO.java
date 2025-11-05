package com.kognitic.nlpapp.neurowho;

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
public class NeuroServiceWHO {
	@Autowired
	private NeuroRepositoryWHO neuroRepository;

	public Iterable<NeuroWHO> saveAll(List<NeuroWHO> isCancerList) {
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
