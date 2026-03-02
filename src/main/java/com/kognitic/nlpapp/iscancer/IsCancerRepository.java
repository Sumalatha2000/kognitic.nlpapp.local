package com.kognitic.nlpapp.iscancer;

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
public interface IsCancerRepository extends JpaRepository<CancerType, Integer> {

	void deleteByNctId(String nctId);

}
