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
	private String telefono;

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
	@ManyToOne(fetch = FetchType.LAZY)
	private Municipio municipio;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	private Ubicacion ubicacionGPS;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	private Subscripcion subscripcion;

	@ManyToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Deporte> deportesFavoritos;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "administrador")
	@JsonIgnore
	private List<Evento> eventosCreados;

	@ManyToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Evento> eventosRegistrado;

	public Usuario() {
	}

	public Usuario(String username, String email, String password, String nombre, String apellidos, String telefono,
			String tokenFireBase, Date fechaRegistro) {

		this.username = username;
		this.email = email;
		this.password = password;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.tokenFireBase = tokenFireBase;
		this.fechaRegistro = fechaRegistro;

		this.deportesFavoritos = new ArrayList<>();
		this.eventosCreados = new ArrayList<>();
		this.eventosRegistrado = new ArrayList<>();

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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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

	public Subscripcion getSubscripcion() {
		return subscripcion;
	}

	public void setSubscripcion(Subscripcion subscripcion) {
		this.subscripcion = subscripcion;
	}

}
