package org.udg.pds.simpleapp_javaee.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Tag implements Serializable {

  private static final long serialVersionUID = 1L;

  public Tag() {
	  
  }

  public Tag(String name, String description) {
	  
    this.name = name;
    this.description = description;
    
  }


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String description;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getId() {
    return id;
  }
}
