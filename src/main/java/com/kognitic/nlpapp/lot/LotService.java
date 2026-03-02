package com.kognitic.nlpapp.lot;

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
public class LotService {
	@Autowired
	private LotRepository lotRepository;

	public Iterable<Lot> saveAll(List<Lot> indicationsList) {
		return lotRepository.saveAll(indicationsList);
	}

	public List<Lot> findAllByNctId(String nctId) {
		return lotRepository.findAllByNctId(nctId);
	}

	public void softDelete(String nctId) {
		List<Lot> lot = findAllByNctId(nctId);
		lot.forEach(x -> x.setDeleted(true));
		saveAll(lot);
	}

	public void updateLoTTagByNctId(String nctId) {
		lotRepository.updateLoTTagByNctId(nctId);
	}

	@Transactional
	public void deleteByNctId(String nctId) {
		lotRepository.deleteByNctId(nctId);

		
	}

}
