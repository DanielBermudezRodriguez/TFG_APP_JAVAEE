package org.udg.pds.simpleapp_javaee.service;

import org.udg.pds.simpleapp_javaee.model.Deporte;
import org.udg.pds.simpleapp_javaee.model.Evento;
import org.udg.pds.simpleapp_javaee.model.Imagen;
import org.udg.pds.simpleapp_javaee.model.Municipio;
import org.udg.pds.simpleapp_javaee.model.Usuario;
import org.udg.pds.simpleapp_javaee.util.Global;
import org.udg.pds.simpleapp_javaee.util.HashPassword;
import request.RequestUsuario.RequestLoginUsuario;
import request.RequestUsuario.RequestModificarUsuario;
import request.RequestUsuario.RequestRegistroUsuario;
import response.ResponseEvento;
import response.ResponseEvento.ResponseEventoInformacion;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class UsuarioService {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private Logger log;

	public Usuario verificarPassword(RequestLoginUsuario login) {

		Usuario usuario = existeUsuarioConMail(login.email);
		if (usuario != null) {
			if (HashPassword.validarPassword(login.password, login.email, usuario.getPassword())) {
				log.log(Level.INFO, "Token en el login: " + login.tokenFireBase);
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
				log.log(Level.INFO, "Token en el registro: " + registro.tokenFireBase);
				Usuario nuevoUsuario = new Usuario(registro.username, registro.email,
						HashPassword.passwordHash(registro.password, registro.email), registro.nombre,
						registro.apellidos, registro.tokenFireBase, new Date());
				nuevoUsuario.setMunicipio(obtenerMunicipio(registro.municipio));
				nuevoUsuario.setDeportesFavoritos(obtenerDeportesFavoritos(registro.deportesFavoritos));
				Imagen imagenPorDefecto = new Imagen(Global.NO_IMAGEN_PERFIL);
				em.persist(imagenPorDefecto);
				nuevoUsuario.setImagen(imagenPorDefecto);
				em.persist(nuevoUsuario);
				return nuevoUsuario;
			}

		}

	}

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

	public List<ResponseEventoInformacion> obtenerEventosUsuario(Long idUsuario, Long tipo) {
		Usuario u = em.find(Usuario.class, idUsuario);
		if (u != null) {
			List<Evento> eventos = new ArrayList<>();
			// Eventos administrados
			if (tipo.equals(0L)) {
				eventos = u.getEventosCreados();
			} // Eventos apuntado
			else if (tipo.equals(1L)) {
				eventos = u.getEventosRegistrado();
			} else
				throw new EJBException(
						"El tipo no es válido. Introduzca (0) para recuperar sus eventos creados o (1) para recuperar los eventos en que está inscrito");
			List<ResponseEvento.ResponseEventoInformacion> responseEventos = new ArrayList<>();
			for (Evento e : eventos) {
				if (!(tipo.equals(1L) && e.getAdministrador().getId().equals(idUsuario)))
					responseEventos.add(new ResponseEvento.ResponseEventoInformacion(e, e.getParticipantes().size()));
			}
			return responseEventos;
		} else
			throw new EJBException("No existe el usuario");
	}

	public Imagen guardarImagenPerfil(Long idUsuario, List<String> imagenSubida, Path baseDir) {
		Usuario u = em.find(Usuario.class, idUsuario);
		if (u != null) {
			if (imagenSubida != null && !imagenSubida.isEmpty()) {
				Imagen imagen = u.getImagen();
				eliminarImagenPerfil(imagen, baseDir);
				imagen.setRuta(imagenSubida.get(0));
				return imagen;
			} else
				throw new EJBException("No se puede subir la imagen");
		} else
			throw new EJBException("El usuario no existe");
	}

	private void eliminarImagenPerfil(Imagen imagen, Path baseDir) {
		if (!imagen.getRuta().equals(Global.NO_IMAGEN_PERFIL)) {
			File file = new File(baseDir.toString() + "\\" + imagen.getRuta());
			if (file.delete())
				log.log(Level.INFO, "Imagen eliminada correctamente");
			else
				log.log(Level.INFO, "No se pudo eliminar la imagen");
		}
	}

	public String obtenerImagen(Long idUsuario) {
		Usuario u = em.find(Usuario.class, idUsuario);
		if (u != null) {
			return u.getImagen().getRuta();
		} else
			throw new EJBException("El usuario no existe");
	}

	public Imagen eliminarImagenPerfil(Long idUsuario, Path baseDir) {
		Usuario u = em.find(Usuario.class, idUsuario);
		if (u != null) {
			Imagen imagen = u.getImagen();
			eliminarImagenPerfil(imagen, baseDir);
			imagen.setRuta(Global.NO_IMAGEN_PERFIL);
			return imagen;

		} else
			throw new EJBException("El usuario no existe");
	}
}
