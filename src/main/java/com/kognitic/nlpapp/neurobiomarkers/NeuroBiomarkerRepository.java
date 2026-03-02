package com.kognitic.nlpapp.neurobiomarkers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

public interface NeuroBiomarkerRepository extends JpaRepository<NeuroBiomarker, Integer> {
	
	@Procedure(procedureName = "UPDATE_BIOMARKER_STANDARD_KEYWORD")
	void updateFinalKeywordByNctId(String nctId);
	
	List<NeuroBiomarker> findAllByNctId(String nctId);

	void deleteByNctId(String nctId);
}
