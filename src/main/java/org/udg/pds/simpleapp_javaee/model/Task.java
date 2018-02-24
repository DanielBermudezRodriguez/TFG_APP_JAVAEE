package org.udg.pds.simpleapp_javaee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.udg.pds.simpleapp_javaee.rest.serializer.JsonDateDeserializer;
import org.udg.pds.simpleapp_javaee.rest.serializer.JsonDateSerializer;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
public class Task implements Serializable {

  private static final long serialVersionUID = 1L;

  public Task() {
	  
  }

  public Task(Date dateCreated, Date dateLimit, Boolean completed, String text) {
	  
    this.dateCreated = dateCreated;
    this.dateLimit = dateLimit;
    this.completed = completed;
    this.text = text;
    
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonSerialize(using = JsonDateSerializer.class)
  @JsonDeserialize(as=JsonDateDeserializer.class)
  private Date dateCreated;

  @JsonSerialize(using = JsonDateSerializer.class)
  @JsonDeserialize(as=JsonDateDeserializer.class)
  private Date dateLimit;

  private Boolean completed;

  private String text;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @JsonIgnore
  @ManyToMany
  private Collection<Tag> tags;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String s) {
    text = s;
  }

  public Date getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  public Date getDateLimit() {
    return dateLimit;
  }

  public void setDateLimit(Date dateLimit) {
    this.dateLimit = dateLimit;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void addTag(Tag tag) {
    tags.add(tag);
  }

  public Collection<Tag> getTags() {
    tags.size();
    return tags;
  }
}
