package com.kognitic.nlpapp.ecog;

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
public interface EcogRepository extends JpaRepository<Ecog, Integer> {
	@Procedure(procedureName = "FINAL_INDICATION_BY_NCTID_INDICATION")
	void updateFinalIndicationByNctId(String nctId);

	List<Ecog> findAllByNctId(String nctId);

}
