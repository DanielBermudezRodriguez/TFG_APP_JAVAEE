package org.udg.pds.simpleapp_javaee.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Pais implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String pais;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pais")
	@JsonIgnore
	private List<Provincia> provincias;

	public Pais() {
	}

	public Pais(String pais) {
		this.pais = pais;
		this.provincias = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public List<Provincia> getProvincias() {
		provincias.size();
		return provincias;
	}

	public void setProvincias(List<Provincia> provincias) {
		this.provincias = provincias;
	}

	public void addProvincias(Provincia provincia) {
		provincias.add(provincia);
	}

}
