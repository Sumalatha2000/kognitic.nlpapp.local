package com.kognitic.nlpapp.txduration;

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
public interface TxdurationRepository extends JpaRepository<Txduration, Integer> {
	@Procedure(procedureName = "FINAL_TXTAG_BY_NCTID")
	void updateFinalTxByNctId(String nctId);

	List<Txduration> findAllByNctId(String nctId);

	void deleteByNctId(String nctId);

}
