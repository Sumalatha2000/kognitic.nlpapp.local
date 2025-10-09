package com.kognitic.nlpapp.indicationswho;

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
public interface IndicationRepositoryWho extends JpaRepository<IndicationsWho, Integer> {
	@Procedure(procedureName = "FINAL_INDICATION_BY_NCTID_INDICATION_WHO")
	void updateFinalIndicationByNctId(String nctId);

	List<IndicationsWho> findAllByNctId(String nctId);

}
