package com.kognitic.nlpapp.assay;

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
public interface AssayTypeRepository extends JpaRepository<AssayType, Integer> {
	
	@Procedure(procedureName = "UPDATE_ASSAY_STANDARD_KEYWORD")
	void updateFinalKeywordByNctId(String nctId);
	void deleteByNctId(String nctId);

}
