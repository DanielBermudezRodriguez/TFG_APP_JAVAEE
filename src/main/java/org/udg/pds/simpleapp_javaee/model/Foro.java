package org.udg.pds.simpleapp_javaee.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Foro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, columnDefinition = "BIT", length = 1)
	private Boolean esPublico;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	private Evento evento;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "foro",fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Mensaje> mensajes;

	public Foro() {
	}

	public Foro(Boolean esPublico) {
		this.esPublico = esPublico;
		this.mensajes = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Boolean getEsPublico() {
		return esPublico;
	}

	public void setEsPublico(Boolean esPublico) {
		this.esPublico = esPublico;
	}

	public List<Mensaje> getMensajes() {
		mensajes.size();
		return mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public void addMensaje(Mensaje mensaje) {
		this.mensajes.add(mensaje);
	}

}
