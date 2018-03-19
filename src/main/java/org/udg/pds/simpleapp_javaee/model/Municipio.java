package org.udg.pds.simpleapp_javaee.model;

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
public class Municipio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String municipio;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private Provincia provincia;

	@NotNull
	@JsonIgnore
	private String slug;

	@NotNull
	private double latitudEstimada;

	@NotNull
	private double longitudEstimada;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "municipio")
	@JsonIgnore
	private List<Evento> eventos;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "municipio")
	@JsonIgnore
	private List<Usuario> usuarios;

	public Municipio() {
	}

	public Municipio(String municipio) {
		this.municipio = municipio;
		this.eventos = new ArrayList<>();
		this.usuarios = new ArrayList<>();
	}

	public Long getId() {
		return id;
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

	public double getLatitudEstimada() {
		return latitudEstimada;
	}

	public void setLatitudEstimada(double latitud) {
		this.latitudEstimada = latitud;
	}

	public double getLongitudEstimada() {
		return longitudEstimada;
	}

	public void setLongitudEstimada(double longitud) {
		this.longitudEstimada = longitud;
	}

	public List<Evento> getEventos() {
		eventos.size();
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public void addEvento(Evento evento) {
		this.eventos.add(evento);
	}

	public List<Usuario> getUsuarios() {
		usuarios.size();
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public void addUsuario(Usuario usuario) {
		this.usuarios.add(usuario);
	}

}
