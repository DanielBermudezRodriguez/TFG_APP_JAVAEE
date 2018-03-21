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
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Subscripcion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	private Usuario subscriptor;

	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Deporte> deportesSubscritos;

	private String hora;

	private String diaSemana;

	public Subscripcion() {
	}

	public Subscripcion(String hora, String diaSemana) {
		this.hora = hora;
		this.diaSemana = diaSemana;
		this.deportesSubscritos = new ArrayList<>();
	}

	public Usuario getSubscriptor() {
		return subscriptor;
	}

	public void setSubscriptor(Usuario subscriptor) {
		this.subscriptor = subscriptor;
	}

	public List<Deporte> getDeportesSubscritos() {
		return deportesSubscritos;
	}

	public void setDeportesSubscritos(List<Deporte> deportesSubscritos) {
		this.deportesSubscritos = deportesSubscritos;
	}

	public void addDeportesSubscritos(Deporte deportesSubscrito) {
		this.deportesSubscritos.add(deportesSubscrito);
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public Long getId() {
		return id;
	}

}
