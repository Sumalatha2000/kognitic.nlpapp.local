package com.kognitic.nlpapp.pe;

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
public interface PeRepository extends JpaRepository<Pe, Integer> {
	@Procedure(procedureName = "FINAL_PETAG_BY_NCTID")
	void updateFinalPeTagByNctId(String nctId);

	List<Pe> findAllByNctId(String nctId);

	void deleteByNctId(String nctId);

}
