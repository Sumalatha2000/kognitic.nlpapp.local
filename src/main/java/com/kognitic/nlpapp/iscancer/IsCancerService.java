package com.kognitic.nlpapp.iscancer;

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
public class IsCancerService {
	@Autowired
	private IsCancerRepository isCancerRepository;

	public Iterable<CancerType> saveAll(List<CancerType> isCancerList) {
		return isCancerRepository.saveAll(isCancerList);
	}

	@Transactional
	public void deleteByNctID(String nctId) {
		 isCancerRepository.deleteByNctId(nctId);
		
	}

}
