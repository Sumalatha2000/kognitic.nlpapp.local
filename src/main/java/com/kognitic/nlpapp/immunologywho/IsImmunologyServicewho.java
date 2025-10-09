package com.kognitic.nlpapp.immunologywho;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Gowrisankar v
 *
 */
@Service
public class IsImmunologyServicewho {
	@Autowired
	private IsImmunologyRepositorywho isImmunologyRepositoryWho;

	public Iterable<ImmunologyWho> saveAll(List<ImmunologyWho> isCancerList) {
		return isImmunologyRepositoryWho.saveAll(isCancerList);
	}
	
	public void updateFincalIndicationByNctId(String nctId) {
		isImmunologyRepositoryWho.updateFinalIndicationByNctId(nctId);
	}

}
