package com.kognitic.nlpapp.specimen;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Gowrisankar v
 *
 */
@Repository
public interface SpecimenRepository extends JpaRepository<Specimen, Integer> {
	
	@Procedure(procedureName = "UPDATE_SPECIMEN_STANDARD_KEYWORD")
	void updateFinalKeywordByNctId(String nctId);

}
