package org.udg.pds.simpleapp_javaee.service;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.udg.pds.simpleapp_javaee.model.Notificacion;
import org.udg.pds.simpleapp_javaee.model.Usuario;

import request.RequestNotificacion.RequestModificarNotificacion;

@Stateless
@LocalBean
public class NotificacionService {

	@PersistenceContext
	protected EntityManager em;

	public Notificacion obtenerConfiguracionNotificaciones(Long idUsuario) {
		Usuario usuario = em.find(Usuario.class, idUsuario);
		if (usuario != null) {
			return usuario.getNotificacion();
		} else
			throw new EJBException("El usuario no está registrado");
	}

	public Notificacion modificarNotificaciones(Long idUsuario, RequestModificarNotificacion datosNotificaciones) {
		Usuario usuario = em.find(Usuario.class, idUsuario);
		if (usuario != null) {
			usuario.getNotificacion().setAltaUsuario(datosNotificaciones.altaUsuario);
			usuario.getNotificacion().setBajaUsuario(datosNotificaciones.bajaUsuario);
			usuario.getNotificacion().setDatosModificados(datosNotificaciones.datosModificados);
			usuario.getNotificacion().setEventoCancelado(datosNotificaciones.eventoCancelado);
			usuario.getNotificacion().setUsuarioEliminado(datosNotificaciones.usuarioEliminado);
			em.persist(usuario);
			return usuario.getNotificacion();
		} else
			throw new EJBException("El usuario no está registrado");
	}

}
