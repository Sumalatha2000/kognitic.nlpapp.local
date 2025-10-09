package com.kognitic.nlpapp.iscancer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
