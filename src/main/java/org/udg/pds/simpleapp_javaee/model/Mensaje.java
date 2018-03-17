package org.udg.pds.simpleapp_javaee.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.udg.pds.simpleapp_javaee.rest.serializer.JsonDateDeserializer;
import org.udg.pds.simpleapp_javaee.rest.serializer.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
public class Mensaje implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(length = 100, nullable = false)
	private String mensaje;

	@NotNull
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(as = JsonDateDeserializer.class)
	private Date fechaMensaje;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Foro foro;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;

	public Mensaje() {

	}

	public Mensaje(String mensaje, Date fechaMensaje) {
		this.mensaje = mensaje;
		this.fechaMensaje = fechaMensaje;
	}

	public Long getId() {
		return id;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Date getFechaMensaje() {
		return fechaMensaje;
	}

	public void setFechaMensaje(Date fechaMensaje) {
		this.fechaMensaje = fechaMensaje;
	}

	public Foro getForo() {
		return foro;
	}

	public void setForo(Foro foro) {
		this.foro = foro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
