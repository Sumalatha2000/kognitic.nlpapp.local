package com.kognitic.nlpapp.indicationswho;

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
public class IndicationServiceWho {
	@Autowired
	private IndicationRepositoryWho indicationRepositoryWho;

	public Iterable<IndicationsWho> saveAll(List<IndicationsWho> indicationsList) {
		return indicationRepositoryWho.saveAll(indicationsList);
	}

	public List<IndicationsWho> findAllByNctId(String nctId) {
		return indicationRepositoryWho.findAllByNctId(nctId);
	}

	public void softDelete(String nctId) {
		List<IndicationsWho> indicationsWho = findAllByNctId(nctId);
		indicationsWho.forEach(x -> x.setDeleted(true));
		saveAll(indicationsWho);
	}

	public void updateFincalIndicationByNctId(String nctId) {
		indicationRepositoryWho.updateFinalIndicationByNctId(nctId);
	}

	@Transactional
	public void deleteByNctId(String nctId) {
		indicationRepositoryWho.deleteByNctId(nctId);

		
	}

}
