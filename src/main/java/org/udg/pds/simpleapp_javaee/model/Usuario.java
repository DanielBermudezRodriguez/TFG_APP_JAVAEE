package org.udg.pds.simpleapp_javaee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.udg.pds.simpleapp_javaee.rest.serializer.JsonDateDeserializer;
import org.udg.pds.simpleapp_javaee.rest.serializer.JsonDateSerializer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*EntityGraph graph = this.em.getEntityGraph("infoPerfilUsuario");
Map hints = new HashMap();
hints.put("javax.persistence.fetchgraph", graph);
Usuario usuario = em.find(Usuario.class, idUsuario, hints);*/

/*@NamedEntityGraphs({ @NamedEntityGraph(name = "infoPerfilUsuario", attributeNodes = {
		@NamedAttributeNode(value = "municipio", subgraph = "provincias"),
		@NamedAttributeNode(value = "deportesFavoritos") }, subgraphs = @NamedSubgraph(name = "provincias", attributeNodes = @NamedAttributeNode(value = "provincia", subgraph = "paises"))),
		@NamedEntityGraph(name = "eventosRegistrado", attributeNodes = { @NamedAttributeNode("eventosRegistrado") }) })*/
@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@NotNull
	@JsonIgnore
	private String tokenFireBase;

	@NotNull
	private String username;

	@NotNull
	private String nombre;

	@NotNull
	private String apellidos;

	@NotNull
	private String email;

	@NotNull
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(as = JsonDateDeserializer.class)
	private Date fechaRegistro;

	@NotNull
	@JsonIgnore
	private String password;

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	private Imagen imagen;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	private Notificacion notificacion;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private Municipio municipio;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	private Ubicacion ubicacionGPS;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	private Subscripcion subscripcion;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Deporte> deportesFavoritos;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "administrador", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Evento> eventosCreados;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "eventos_registrado",
	    joinColumns = {@JoinColumn(name = "evento_ID")},
	    inverseJoinColumns = {@JoinColumn(name = "participante_ID")}
	  )
	@JsonIgnore
	private List<Evento> eventosRegistrado;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "eventos_en_cola",
    joinColumns = {@JoinColumn(name = "evento_ID")},
    inverseJoinColumns = {@JoinColumn(name = "participante_ID")}
  )
	@JsonIgnore
	private List<Evento> eventosEnCola;

	public Usuario() {
	}

	public Usuario(String username, String email, String password, String nombre, String apellidos,
			String tokenFireBase, Date fechaRegistro) {

		this.username = username;
		this.email = email;
		this.password = password;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.tokenFireBase = tokenFireBase;
		this.fechaRegistro = fechaRegistro;
		this.deportesFavoritos = new ArrayList<>();
		this.eventosCreados = new ArrayList<>();
		this.eventosRegistrado = new ArrayList<>();
		this.eventosEnCola = new ArrayList<>();

	}

	public Long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public String getTokenFireBase() {
		return tokenFireBase;
	}

	public void setTokenFireBase(String tokenFireBase) {
		this.tokenFireBase = tokenFireBase;
	}

	public Ubicacion getUbicacionGPS() {
		return ubicacionGPS;
	}

	public void setUbicacionGPS(Ubicacion ubicacionGPS) {
		this.ubicacionGPS = ubicacionGPS;
	}

	public List<Deporte> getDeportesFavoritos() {
		deportesFavoritos.size();
		return deportesFavoritos;
	}

	public void setDeportesFavoritos(List<Deporte> deportesFavoritos) {
		this.deportesFavoritos = deportesFavoritos;
	}

	public void addDeportesFavoritos(Deporte deporte) {
		deportesFavoritos.add(deporte);
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public List<Evento> getEventosCreados() {
		eventosCreados.size();
		return eventosCreados;
	}

	public void setEventosCreados(List<Evento> eventos) {
		this.eventosCreados = eventos;
	}

	public void addEventosCreados(Evento evento) {
		this.eventosCreados.add(evento);
	}

	public List<Evento> getEventosRegistrado() {
		eventosRegistrado.size();
		return eventosRegistrado;
	}

	public void setEventosRegistrado(List<Evento> eventosRegistrado) {
		this.eventosRegistrado = eventosRegistrado;
	}

	public void addEventosRegistrado(Evento evento) {
		this.eventosRegistrado.add(evento);
	}
	
	public void addEventosEnCola(Evento evento) {
		this.eventosEnCola.add(evento);
	}

	public List<Evento> getEventosEnCola() {
		eventosEnCola.size();
		return eventosEnCola;
	}

	public void setEventosEnCola(List<Evento> eventosEnCola) {
		this.eventosEnCola = eventosEnCola;
	}

	public Subscripcion getSubscripcion() {
		return subscripcion;
	}

	public void setSubscripcion(Subscripcion subscripcion) {
		this.subscripcion = subscripcion;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public Notificacion getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(Notificacion notificacion) {
		this.notificacion = notificacion;
	}
	
	

}
