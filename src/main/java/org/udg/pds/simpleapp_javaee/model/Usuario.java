package org.udg.pds.simpleapp_javaee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"email", "username"}))
public class Usuario implements Serializable {

  private static final long serialVersionUID = 1L;

  // Constructor vacio
  public Usuario() {
  }

  // Constructor con parámetros
  public Usuario(String username, String email, String password, String nombre, String apellidos, String telefono) {
	  
    this.username = username;
    this.email = email;
    this.password = password;
    this.nombre = nombre;
    this.apellidos = apellidos;
    this.telefono = telefono;
    this.tasks = new ArrayList<>();
    
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonView(Views.Private.class)
  protected Long id;

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

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  @JsonView(Views.Complete.class)
  private Collection<Task> tasks;

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

public Collection<Task> getTasks() {
    // Dado que las tareas están controladas por JPA, tiene carga LAZY por defecto.
	// Esto significa que tiene que consultar el objeto (llamando size()), para obtener la lista inicializada. 
    // More: http://www.javabeat.net/jpa-lazy-eager-loading/
    tasks.size();
    return tasks;
  }

  public void setTasks(List<Task> ts) {
    this.tasks = ts;
  }

  public void addTask(Task task) {
    tasks.add(task);
  }

}
