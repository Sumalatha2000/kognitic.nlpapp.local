package com.kognitic.nlpapp.isimmunology;

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
public interface IsImmunologyRepository extends JpaRepository<Immunology, Integer> {
	@Procedure(procedureName = "FINAL_INDICATION_BY_NCTID_INDICATION_IMMUNOLOGY")
	void updateFinalIndicationByNctId(String nctId);
	
	int deleteByNctId(String nctId);

}
