package com.kognitic.nlpapp.racialdisparity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RacialDisparityRepository extends JpaRepository<RacialDisparity, Integer> {
	List<RacialDisparity> findAllByNctId(String nctId);

	void deleteByNctId(String nctId);
}
