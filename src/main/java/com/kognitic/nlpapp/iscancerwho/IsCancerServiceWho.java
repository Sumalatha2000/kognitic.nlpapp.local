package com.kognitic.nlpapp.iscancerwho;

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
public class IsCancerServiceWho {
	@Autowired
	private IsCancerRepositoryWho isCancerRepositoryWho;

	public Iterable<CancerTypeWho> saveAll(List<CancerTypeWho> isCancerList) {
		return isCancerRepositoryWho.saveAll(isCancerList);
	}


	@Transactional
	public void deleteByNctId(String nctId) {
		 isCancerRepositoryWho.deleteByNctId(nctId);
	}

}
