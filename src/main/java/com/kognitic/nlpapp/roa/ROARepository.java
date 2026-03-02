package com.kognitic.nlpapp.roa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface ROARepository extends JpaRepository<ROA, Integer> {
	@Procedure(procedureName = "UPDATE_ROA_STANDARD_KEYWORD")
	void updateStandardRouteByNct_ID(String nctId);
	
	void deleteByNctId(String nctId);
}
