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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Access;
import static javax.persistence.AccessType.FIELD;
import static javax.persistence.AccessType.PROPERTY;

@Entity
public class Deporte implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@NotNull
	private String deporte;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "deportesFavoritos",fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Usuario> usuarios;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "deportesSubscritos",fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Subscripcion> subscripciones;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "deporte",fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Evento> eventosCreados;

	public Deporte() {

	}

	public Deporte(String deporte) {
		this.deporte = deporte;
		this.usuarios = new ArrayList<>();
		this.eventosCreados = new ArrayList<>();
		this.subscripciones = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public String getDeporte() {
		return deporte;
	}

	public void setDeporte(String deporte) {
		this.deporte = deporte;
	}

	public List<Usuario> getUsuarios() {
		usuarios.size();
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public void addUsuarios(Usuario usuario) {
		usuarios.add(usuario);
	}

	public List<Evento> getEventosCreados() {
		eventosCreados.size();
		return eventosCreados;
	}

	public void setEventosCreados(List<Evento> eventosCreados) {
		this.eventosCreados = eventosCreados;
	}

	public void addEventoDeportivo(Evento evento) {
		this.eventosCreados.add(evento);
	}

	public List<Subscripcion> getSubscripciones() {
		subscripciones.size();
		return subscripciones;
	}

	public void setSubscripciones(List<Subscripcion> subscripciones) {
		this.subscripciones = subscripciones;
	}

	public void addSubscripciones(Subscripcion subscripcion) {
		this.subscripciones.add(subscripcion);
	}

}
