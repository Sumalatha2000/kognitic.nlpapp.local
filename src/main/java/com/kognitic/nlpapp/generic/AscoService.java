package com.kognitic.nlpapp.generic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Gowrisankar v
 *
 */
@Service
public class AscoService {
	@Autowired
	private AscoRepository indicationRepository;

	public Iterable<Asco> saveAll(List<Asco> indicationsList) {
		return indicationRepository.saveAll(indicationsList);
	}

	public List<Asco> findAllByNctId(String nctId) {
		return indicationRepository.findAllByNctId(nctId);
	}

	public void softDelete(String nctId) {
		List<Asco> indications = findAllByNctId(nctId);
		indications.forEach(x -> x.setDeleted(true));
		saveAll(indications);
	}

}
