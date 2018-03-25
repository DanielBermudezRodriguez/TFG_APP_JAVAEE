package org.udg.pds.simpleapp_javaee.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import org.udg.pds.simpleapp_javaee.rest.serializer.JsonDateDeserializer;
import org.udg.pds.simpleapp_javaee.rest.serializer.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@NamedQueries({
		@NamedQuery(name = "@HQL_GET_EVENTOS_NO_FINALIZADOS", query = "select e from Evento as e where e.fechaEvento <= :fechaActual  and e.estado.id <> :idCancelado") })
public class Evento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(length = 20, nullable = false)
	private String titulo;

	@Column(length = 100, nullable = false)
	private String descripcion;

	@NotNull
	private int duracion;

	@NotNull
	private int numeroParticipantes;

	@NotNull
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(as = JsonDateDeserializer.class)
	private Date fechaEvento;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	private Foro foro;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	private Ubicacion ubicacionGPS;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Deporte deporte;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario administrador;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Municipio municipio;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "eventosRegistrado", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Usuario> participantes;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private Estado estado;

	public Evento() {
	}

	public Evento(String titulo, String descripcion, int duracion, int numeroParticipantes, Date fechaEvento,
			Estado estado) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.duracion = duracion;
		this.numeroParticipantes = numeroParticipantes;
		this.fechaEvento = fechaEvento;
		this.estado = estado;

		this.participantes = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public int getNumeroParticipantes() {
		return numeroParticipantes;
	}

	public void setNumeroParticipantes(int numeroParticipantes) {
		this.numeroParticipantes = numeroParticipantes;
	}

	public Date getFechaEvento() {
		return fechaEvento;
	}

	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

	public Ubicacion getUbicacionGPS() {
		return ubicacionGPS;
	}

	public void setUbicacionGPS(Ubicacion ubicacionGPS) {
		this.ubicacionGPS = ubicacionGPS;
	}

	public Deporte getDeporte() {
		return deporte;
	}

	public void setDeporte(Deporte deporte) {
		this.deporte = deporte;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public Usuario getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Usuario administrador) {
		this.administrador = administrador;
	}

	public List<Usuario> getParticipantes() {
		participantes.size();
		return participantes;
	}

	public void setParticipantes(List<Usuario> participantes) {
		this.participantes = participantes;
	}

	public void addParticipantes(Usuario participante) {
		this.participantes.add(participante);
	}

	public Foro getForo() {
		return foro;
	}

	public void setForo(Foro foro) {
		this.foro = foro;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
