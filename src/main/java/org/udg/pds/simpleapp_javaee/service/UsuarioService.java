package org.udg.pds.simpleapp_javaee.service;

import org.udg.pds.simpleapp_javaee.model.Deporte;
import org.udg.pds.simpleapp_javaee.model.Municipio;
import org.udg.pds.simpleapp_javaee.model.Usuario;
import org.udg.pds.simpleapp_javaee.util.HashPassword;
import request.RequestUsuario.RequestLoginUsuario;
import request.RequestUsuario.RequestRegistroUsuario;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class UsuarioService {

	@PersistenceContext
	private EntityManager em;

	public Usuario verificarPassword(RequestLoginUsuario login) {

		Query q = em.createQuery("select u from Usuario u where u.email=:email");
		q.setParameter("email", login.email);
		Usuario usuario;
		try {
			usuario = (Usuario) q.getSingleResult();
		} catch (Exception e) {
			throw new EJBException("No existe un usuario registrado con el email " + login.email);
		}

		if (HashPassword.validarPassword(login.password, login.email, usuario.getPassword())) {
			usuario.setTokenFireBase(login.tokenFireBase);
			return usuario;
		} else
			throw new EJBException("Email o contraseña incorrectos");

	}

	public Usuario registro(RequestRegistroUsuario registro) {

		Usuario usuario = null;
		Query q = em.createQuery("select u from Usuario u where u.email=:email");
		q.setParameter("email", registro.email);
		try {
			usuario = (Usuario) q.getSingleResult();
			if (usuario != null)
				throw new EJBException("Ya existe un usuario con el email " + registro.email);
		} catch (NoResultException e) {
			q = em.createQuery("select u from Usuario u where u.username=:username");
			q.setParameter("username", registro.username);
			try {
				usuario = (Usuario) q.getSingleResult();
				if (usuario != null)
					throw new EJBException("Ya existe un usuario con el Nick " + registro.username);
			} catch (NoResultException ex) {
				Usuario nuevoUsuario = new Usuario(registro.username, registro.email,
						HashPassword.passwordHash(registro.password, registro.email), registro.nombre,
						registro.apellidos, registro.telefono, registro.tokenFireBase, new Date());
				nuevoUsuario.setMunicipio(em.find(Municipio.class, registro.municipio));
				for (Long deporte : registro.deportesFavoritos) {
					nuevoUsuario.addDeportesFavoritos(em.find(Deporte.class, deporte));
				}
				em.persist(nuevoUsuario);
				return nuevoUsuario;
			}
		}
		throw new EJBException("No se ha podido realizar el registro");

	}

	// @SuppressWarnings({ "rawtypes", "unchecked" })
	public Usuario obtenerPerfilUsuario(Long idUsuario) {

		Usuario usuario = em.find(Usuario.class, idUsuario);
		if (usuario != null) {
			return usuario;
		} else {
			throw new EJBException("El usuario no existe");
		}
	}

	public Usuario getUser(long id) {
		return em.find(Usuario.class, id);
	}

}
