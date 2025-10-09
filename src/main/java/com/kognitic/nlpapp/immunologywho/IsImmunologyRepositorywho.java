package com.kognitic.nlpapp.immunologywho;

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
public interface IsImmunologyRepositorywho extends JpaRepository<ImmunologyWho, Integer> {
	@Procedure(procedureName = "FINAL_INDICATION_BY_NCTID_INDICATION_IMMUNOLOGY_WHO")
	void updateFinalIndicationByNctId(String nctId);

}
