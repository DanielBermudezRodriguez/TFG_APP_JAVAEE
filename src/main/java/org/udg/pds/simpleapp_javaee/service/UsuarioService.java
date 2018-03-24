package org.udg.pds.simpleapp_javaee.service;

import org.udg.pds.simpleapp_javaee.model.Deporte;
import org.udg.pds.simpleapp_javaee.model.Municipio;
import org.udg.pds.simpleapp_javaee.model.Usuario;
import org.udg.pds.simpleapp_javaee.util.HashPassword;
import request.RequestUsuario.RequestLoginUsuario;
import request.RequestUsuario.RequestModificarUsuario;
import request.RequestUsuario.RequestRegistroUsuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class UsuarioService {

	@PersistenceContext
	private EntityManager em;

	public Usuario verificarPassword(RequestLoginUsuario login) {

		Usuario usuario = existeUsuarioConMail(login.email);
		if (usuario != null) {
			if (HashPassword.validarPassword(login.password, login.email, usuario.getPassword())) {
				usuario.setTokenFireBase(login.tokenFireBase);
				return usuario;
			} else
				throw new EJBException("Email o contraseña incorrectos");
		} else
			throw new EJBException("No existe un usuario registrado con el email " + login.email);

	}

	public Usuario registro(RequestRegistroUsuario registro) {

		Usuario usuario = existeUsuarioConMail(registro.email);
		if (usuario != null)
			throw new EJBException("Ya existe un usuario con el email " + registro.email);
		else {
			usuario = existeUsuarioConNick(registro.username);
			if (usuario != null)
				throw new EJBException("Ya existe un usuario con el Nick " + registro.username);
			else {
				Usuario nuevoUsuario = new Usuario(registro.username, registro.email,
						HashPassword.passwordHash(registro.password, registro.email), registro.nombre,
						registro.apellidos, registro.telefono, registro.tokenFireBase, new Date());
				nuevoUsuario.setMunicipio(obtenerMunicipio(registro.municipio));
				nuevoUsuario.setDeportesFavoritos(obtenerDeportesFavoritos(registro.deportesFavoritos));
				em.persist(nuevoUsuario);
				return nuevoUsuario;
			}

		}

	}

	// @SuppressWarnings({ "rawtypes", "unchecked" })
	public Usuario obtenerPerfilUsuario(Long idUsuario) {

		Usuario usuario = em.find(Usuario.class, idUsuario);
		if (usuario != null) {
			// inicializar explicitamente colección deportes inicializada a lazy
			usuario.getDeportesFavoritos().size();
			return usuario;
		} else {
			throw new EJBException("El usuario no existe");
		}
	}

	public Usuario modificarPerfil(RequestModificarUsuario datosPerfil, Long idUsuario) {
		Usuario usuario = em.find(Usuario.class, idUsuario);
		if (usuario != null) {
			// Validar que el nick no esté en uso
			Usuario u = existeUsuarioConNick(datosPerfil.username);
			if (u != null) {
				// Nick ya existe y no es el del usuario actual
				if (!u.getId().equals(idUsuario))
					throw new EJBException("El nick ya está registrado. Por favor escoja otro");
			} else
				usuario.setUsername(datosPerfil.username);

			// Validar municipio existe
			usuario.setMunicipio(obtenerMunicipio(datosPerfil.municipio));

			// Validar deportes
			usuario.setDeportesFavoritos(obtenerDeportesFavoritos(datosPerfil.deportesFavoritos));

			usuario.setNombre(datosPerfil.nombre);
			usuario.setApellidos(datosPerfil.apellidos);
			usuario.setTelefono(datosPerfil.telefono);

			em.persist(usuario);
			return usuario;
		} else {
			throw new EJBException("El usuario no existe");
		}
	}

	// MÉTODOS PRIVADOS
	// -----------------------------------------------------------------------------

	private List<Deporte> obtenerDeportesFavoritos(List<Long> deportesFavoritos) {
		List<Deporte> deportes = new ArrayList<>();
		for (Long deporte : deportesFavoritos) {
			Deporte d = em.find(Deporte.class, deporte);
			if (d != null)
				deportes.add(d);
			else
				throw new EJBException("El deporte no existe");

		}
		return deportes;
	}

	private Municipio obtenerMunicipio(Long idMunicipio) {
		Municipio municipio = em.find(Municipio.class, idMunicipio);
		if (municipio != null)
			return municipio;
		else
			throw new EJBException("El municipio no existe");
	}

	private Usuario existeUsuarioConNick(String username) {
		Query q = em.createQuery("select u from Usuario u where u.username=:username");
		q.setParameter("username", username);
		Usuario usuario;
		try {
			usuario = (Usuario) q.getSingleResult();
			if (usuario != null)
				return usuario;
			else
				return null;
		} catch (Exception e) {
			return null;
		}
	}

	private Usuario existeUsuarioConMail(String email) {
		Query q = em.createQuery("select u from Usuario u where u.email=:email");
		q.setParameter("email", email);
		Usuario usuario;
		try {
			usuario = (Usuario) q.getSingleResult();
			if (usuario != null)
				return usuario;
			else
				return null;
		} catch (Exception e) {
			return null;
		}
	}

}
