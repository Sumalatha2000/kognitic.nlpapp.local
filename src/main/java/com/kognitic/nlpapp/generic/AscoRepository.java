package com.kognitic.nlpapp.generic;

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
public interface AscoRepository extends JpaRepository<Asco, Integer> {
	List<Asco> findAllByNctId(String nctId);

	void deleteByNctId(String nctId);

}
