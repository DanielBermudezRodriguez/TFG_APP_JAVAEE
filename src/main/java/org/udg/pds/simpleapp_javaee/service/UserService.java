package org.udg.pds.simpleapp_javaee.service;

import org.udg.pds.simpleapp_javaee.model.User;
import org.udg.pds.simpleapp_javaee.rest.RESTService;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class UserService {

  @PersistenceContext
  private EntityManager em;

  public User matchPassword(String email, String password) {
    Query q = em.createQuery("select u from User u where u.email=:email");
    q.setParameter("email", email);

    User u;
    try {
      u = (User) q.getSingleResult();
    } catch (Exception e) {
      throw new EJBException("No existe un usuario registrado con el email " + email);
    }

    if (u.getPassword().equals(password))
      return u;
    else
      throw new EJBException("La contraseña es incorrecta");
  }

  public User register(String username, String email, String password) {

    Query q = em.createQuery("select u from User u where u.email=:email");
    q.setParameter("email", email);
    try {
      User u = (User) q.getSingleResult();
    } catch (Exception e) {
      throw new EJBException("Ya existe un usuario con la cuenta de correo electrónico " + email + ".");
    }

    q = em.createQuery("select u from User u where u.username=:username");
    q.setParameter("username", username);
    try {
      User u = (User) q.getSingleResult();
    } catch (Exception e) {
      throw new EJBException("Ya existe un usuario con el nombre " + username + ".");
    }

    User nu = new User(username, email, password);
    em.persist(nu);
    return nu;
  }

  public User getUser(long id) {
    return em.find(User.class, id);
  }

  public RESTService.ID remove(Long userId) {
    User u = getUser(userId);
    em.remove(u);
    return new RESTService.ID(userId);
  }

  public User getUserComplete(Long loggedId) {
    User u = getUser(loggedId);
    u.getTasks().size();
    return u;
  }
}
