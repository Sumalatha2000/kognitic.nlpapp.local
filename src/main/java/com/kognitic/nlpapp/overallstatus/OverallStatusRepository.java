package com.kognitic.nlpapp.overallstatus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OverallStatusRepository extends JpaRepository<OverallStatus, Integer> 
{
	void deleteByNctId(String nctId);

}
