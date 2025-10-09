package com.kognitic.nlpapp.neuro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Raghav
 *
 */
@Repository
public interface NeuroRepository extends JpaRepository<Neuro, Integer> {
	@Procedure(procedureName = "FINAL_INDICATION_BY_NCTID_INDICATION_NEUROLOGY")
	void updateFinalIndicationByNctId(String nctId);
	
	int deleteByNctId(String nctId);

}
