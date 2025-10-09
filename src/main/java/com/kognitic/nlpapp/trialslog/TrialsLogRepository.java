package com.kognitic.nlpapp.trialslog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrialsLogRepository extends JpaRepository<TrialsInProcess, Integer> {
	List<TrialsInProcess> findAllByNctId(String nctId);
}
