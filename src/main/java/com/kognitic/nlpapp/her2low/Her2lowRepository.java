package com.kognitic.nlpapp.her2low;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface Her2lowRepository extends JpaRepository<Her2low, Integer> {

	@Procedure(procedureName = "FINAL_INDICATION_UPDATE_TEST")
	void moveFindalIndicationUpdateTest();

}
