package com.kognitic.nlpapp.txduration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Gowrisankar v
 *
 */
@Service
public class TxdurationService {
	@Autowired
	private TxdurationRepository txdurationRepository;

	public Iterable<Txduration> saveAll(List<Txduration> indicationsList) {
		return txdurationRepository.saveAll(indicationsList);
	}

	public List<Txduration> findAllByNctId(String nctId) {
		return txdurationRepository.findAllByNctId(nctId);
	}

	public void softDelete(String nctId) {
		List<Txduration> txduration = findAllByNctId(nctId);
		txduration.forEach(x -> x.setDeleted(true));
		saveAll(txduration);
	}

	public void updateFinalTxByNctId(String nctId) {
		txdurationRepository.updateFinalTxByNctId(nctId);
	}

}
