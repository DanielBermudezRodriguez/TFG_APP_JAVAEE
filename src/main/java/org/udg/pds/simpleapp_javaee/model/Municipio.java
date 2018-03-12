package org.udg.pds.simpleapp_javaee.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Municipio implements Serializable{

	private static final long serialVersionUID = 1L;
	
	  // Constructor vacio
	  public Municipio() {
	  }

	  // Constructor con parámetros
	  public Municipio(String municipio) {	  
	    this.municipio = municipio;
	  }
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  
	  @NotNull
	  private String municipio;
	  
	  @JsonIgnore
	  @ManyToOne(fetch = FetchType.LAZY)
	  private Provincia provincia;
	  
	  @NotNull
	  private String slug;
	  
	  @NotNull
	  private double latitud;
	  
	  @NotNull
	  private double longitud;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	  
	
}
