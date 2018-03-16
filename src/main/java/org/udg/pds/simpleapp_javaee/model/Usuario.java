package org.udg.pds.simpleapp_javaee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "email", "username" }))
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	// Constructor vacio
	public Usuario() {
	}

	// Constructor con parámetros
	public Usuario(String username, String email, String password, String nombre, String apellidos, String telefono,
			String tokenFireBase) {

		this.username = username;
		this.email = email;
		this.password = password;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.tokenFireBase = tokenFireBase;

		this.municipio = new Municipio();
		this.ubicacionGPS = new Ubicacion();

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Private.class)
	protected Long id;

	@NotNull
	@JsonIgnore
	private String tokenFireBase;

	@NotNull
	@JsonView(Views.Public.class)
	private String username;

	@NotNull
	@JsonView(Views.Public.class)
	private String nombre;

	@NotNull
	@JsonView(Views.Public.class)
	private String apellidos;

	@NotNull
	@JsonView(Views.Public.class)
	private String telefono;

	@NotNull
	@JsonView(Views.Private.class)
	private String email;

	@NotNull
	@JsonIgnore
	private String password;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Municipio municipio;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Ubicacion ubicacionGPS;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
