package com.kognitic.nlpapp.ecog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Gowrisankar v
 *
 */
@Service
public class EcogService {
	@Autowired
	private EcogRepository ecogRepository;

	public Iterable<Ecog> saveAll(List<Ecog> indicationsList) {
		return ecogRepository.saveAll(indicationsList);
	}

	public List<Ecog> findAllByNctId(String nctId) {
		return ecogRepository.findAllByNctId(nctId);
	}

	public void softDelete(String nctId) {
		List<Ecog> ecog = findAllByNctId(nctId);
		ecog.forEach(x -> x.setDeleted(true));
		saveAll(ecog);
	}

	public void updateFincalIndicationByNctId(String nctId) {
		ecogRepository.updateFinalIndicationByNctId(nctId);
	}

}
