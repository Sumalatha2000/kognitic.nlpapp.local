package com.kognitic.nlpapp.indications;

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
public interface IndicationRepository extends JpaRepository<Indications, Integer> {
	@Procedure(procedureName = "FINAL_INDICATION_BY_NCTID_INDICATION")
	void updateFinalIndicationByNctId(String nctId);

	List<Indications> findAllByNctId(String nctId);
	
	int deleteByNctId(String nctId);

}
