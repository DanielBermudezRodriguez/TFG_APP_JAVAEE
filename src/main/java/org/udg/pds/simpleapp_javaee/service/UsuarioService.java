package org.udg.pds.simpleapp_javaee.service;

import org.udg.pds.simpleapp_javaee.model.Usuario;
import org.udg.pds.simpleapp_javaee.rest.RESTService;
import org.udg.pds.simpleapp_javaee.util.HashPassword;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class UsuarioService {

  @PersistenceContext
  private EntityManager em;

  public Usuario verificarPassword(String email, String password) {
	  
    Query q = em.createQuery("select u from Usuario u where u.email=:email");
    q.setParameter("email", email);
    Usuario usuario;
    try {
    	usuario = (Usuario) q.getSingleResult(); 
    } catch (Exception e) {
      throw new EJBException("No existe un usuario registrado con el email " + email);
    }
    // Ciframos contraseña enviada por parámetro para comparar con la almacenada en base de datos:
    if (HashPassword.validarPassword(password,email, usuario.getPassword())) return usuario;
    else throw new EJBException("La contraseña es incorrecta");
    
  }

	public Usuario registro(String username, String email, String password, String nombre, String apellidos,String telefono) {
		Usuario usuario = null;
		Query q = em.createQuery("select u from Usuario u where u.email=:email");
		q.setParameter("email", email);
		try {
			usuario = (Usuario) q.getSingleResult();
			if (usuario != null) throw new EJBException("Ya existe un usuario con el email " + email);
		}catch (NoResultException e) {
			// No hay ningun usuario con ese mail
			q = em.createQuery("select u from Usuario u where u.username=:username");
			q.setParameter("username", username);
			try {
				usuario = (Usuario) q.getSingleResult();
				if (usuario != null) throw new EJBException("Ya existe un usuario con el nick " + username);
			}catch(NoResultException ex) {
				// No hay ningun usuario con ese nick
				Usuario nuevoUsuario = new Usuario(username, email, HashPassword.passwordHash(password,email), nombre, apellidos, telefono);
				em.persist(nuevoUsuario);
				return nuevoUsuario;
			}
		}
		throw new EJBException("No se ha podido realizar el registro");
	}

  public Usuario getUser(long id) {
    return em.find(Usuario.class, id);
  }

  public Usuario getUserComplete(Long loggedId) {
    Usuario u = getUser(loggedId);
    u.getTasks().size();
    return u;
  }
  
  public RESTService.ID remove(Long userId) {
	    Usuario u = getUser(userId);
	    em.remove(u);
	    return new RESTService.ID(userId);
	  }

}
