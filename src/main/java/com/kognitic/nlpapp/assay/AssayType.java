package com.kognitic.nlpapp.assay;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 
 * @author Gowrisankar v
 *
 */
@Entity
@Table(name = "assayType")
public class AssayType {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String nctId;
	private String biomarker;
	private String assay;
	private String section;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNctId() {
		return nctId;
	}

	public void setNctId(String nctId) {
		this.nctId = nctId;
	}

	public String getBiomarker() {
		return biomarker;
	}

	public void setBiomarker(String biomarker) {
		this.biomarker = biomarker;
	}

	public String getAssay() {
		return assay;
	}

	public void setAssay(String assay) {
		this.assay = assay;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

}
