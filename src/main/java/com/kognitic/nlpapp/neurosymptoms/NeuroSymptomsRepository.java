package com.kognitic.nlpapp.neurosymptoms;

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
public interface NeuroSymptomsRepository extends JpaRepository<NeuroSymptoms, Integer> {

}
