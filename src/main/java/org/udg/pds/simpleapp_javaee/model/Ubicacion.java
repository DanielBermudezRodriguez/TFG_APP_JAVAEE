package org.udg.pds.simpleapp_javaee.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Ubicacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@NotNull
	private double latitud;

	@NotNull
	private double longitud;

	@NotNull
	private String direccion;

	@NotNull
	private String municipio;

	@NotNull
	private String provincia;

	@NotNull
	private String pais;

	public Ubicacion() {

	}

	public Ubicacion(double latitud, double longitud, String direccion, String municipio, String provincia,
			String pais) {
		this.latitud = latitud;
		this.longitud = longitud;
		this.direccion = direccion;
		this.municipio = municipio;
		this.provincia = provincia;
		this.pais = pais;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

}
