package com.kognitic.nlpapp.lot;

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
public interface LotRepository extends JpaRepository<Lot, Integer> {
	@Procedure(procedureName = "UPDATE_LOT_VALIDATION_BY_NCTID")
	void updateLoTTagByNctId(String nctId);

	List<Lot> findAllByNctId(String nctId);

	void deleteByNctId(String nctId);

}
