package org.udg.tfg.javaee.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Provincia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String provincia;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private Pais pais;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "provincia",fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Municipio> municipios;

	public Provincia() {
	}

	public Provincia(String provincia) {
		this.provincia = provincia;
		this.municipios = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public List<Municipio> getMunicipios() {
		municipios.size();
		return municipios;
	}

	public void setMunicipios(List<Municipio> municipios) {
		this.municipios = municipios;
	}

	public void addMunicipios(Municipio municipio) {
		municipios.add(municipio);
	}

}
