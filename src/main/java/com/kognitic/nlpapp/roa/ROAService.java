package com.kognitic.nlpapp.roa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service

public class ROAService {
	@Autowired ROARepository roaRepository;
	
	public Iterable<ROA> saveAll(List<ROA> roaList) {
		return roaRepository.saveAll(roaList);
	}
	
	public void updateStandardRouteByNct_ID(String nctId) {
		roaRepository.updateStandardRouteByNct_ID(nctId);
	}

	@Transactional
	public void deleteByNctId(String nctId) {
		roaRepository.deleteByNctId(nctId);
	}

}
