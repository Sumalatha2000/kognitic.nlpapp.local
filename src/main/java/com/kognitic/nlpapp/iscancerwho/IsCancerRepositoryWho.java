package com.kognitic.nlpapp.iscancerwho;

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
public interface IsCancerRepositoryWho extends JpaRepository<CancerTypeWho, Integer> {

	void deleteByNctId(String nctId);

}
