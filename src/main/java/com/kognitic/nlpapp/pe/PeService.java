package com.kognitic.nlpapp.pe;

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
public class PeService {
	@Autowired
	private PeRepository peRepository;

	public Iterable<Pe> saveAll(List<Pe> indicationsList) {
		return peRepository.saveAll(indicationsList);
	}

	public List<Pe> findAllByNctId(String nctId) {
		return peRepository.findAllByNctId(nctId);
	}

	public void softDelete(String nctId) {
		List<Pe> pe = findAllByNctId(nctId);
		pe.forEach(x -> x.setDeleted(true));
		saveAll(pe);
	}

	public void updatePeTagByNctId(String nctId) {
		peRepository.updateFinalPeTagByNctId(nctId);
	}

	@Transactional
	public void deleteByNctId(String nctId) {
		peRepository.deleteByNctId(nctId);

	}

}
