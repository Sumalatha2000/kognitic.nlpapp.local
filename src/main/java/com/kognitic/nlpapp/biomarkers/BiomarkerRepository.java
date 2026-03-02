package com.kognitic.nlpapp.biomarkers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

public interface BiomarkerRepository extends JpaRepository<Biomarker, Integer> {
	
	@Procedure(procedureName = "UPDATE_BIOMARKER_STANDARD_KEYWORD")
	void updateFinalKeywordByNctId(String nctId);
	
	List<Biomarker> findAllByNctId(String nctId);

	void deleteByNctId(String nctId);
}
