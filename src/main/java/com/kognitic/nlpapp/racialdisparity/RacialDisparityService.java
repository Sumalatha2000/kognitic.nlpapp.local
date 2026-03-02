package com.kognitic.nlpapp.racialdisparity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RacialDisparityService {
	@Autowired
	private RacialDisparityRepository racialDisparityRepository;

	public Iterable<RacialDisparity> saveAll(List<RacialDisparity> biomarkersList) {
		return racialDisparityRepository.saveAll(biomarkersList);
	}

	public List<RacialDisparity> findAllByNctId(String nctId) {
		return racialDisparityRepository.findAllByNctId(nctId);
	}

	public void softDelete(String nctId) {
		List<RacialDisparity> racialDisparities = findAllByNctId(nctId);
		racialDisparities.forEach(x -> x.setDeleted(true));
		saveAll(racialDisparities);
	}

	@Transactional
	public void deleteByNctId(String nctId) {
		 racialDisparityRepository.deleteByNctId(nctId);
	
	}
}
