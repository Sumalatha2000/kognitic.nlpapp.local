package com.kognitic.nlpapp.dosage;

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
public interface DosageRepository extends JpaRepository<Dosage, Integer> {
	@Procedure(procedureName = "FINAL_INDICATION_BY_NCTID_INDICATION")
	void updateFinalIndicationByNctId(String nctId);

	List<Dosage> findAllByNctId(String nctId);

	void deleteByNctId(String nctId);

}
