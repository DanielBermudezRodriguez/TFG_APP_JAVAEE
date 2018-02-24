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
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  // Constructor vacio
  public User() {
  }

  // Constructor con parámetros
  public User(String username, String email, String password) {
	  
    this.username = username;
    this.email = email;
    this.password = password;
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
