package org.udg.pds.simpleapp_javaee.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notificacion implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@Column(nullable = false, columnDefinition = "BIT", length = 1)
	private Boolean altaUsuario = true;
	
	@Column(nullable = false, columnDefinition = "BIT", length = 1)
	private Boolean bajaUsuario = true;
	
	@Column(nullable = false, columnDefinition = "BIT", length = 1)
	private Boolean eventoCancelado = true;
	
	@Column(nullable = false, columnDefinition = "BIT", length = 1)
	private Boolean datosModificados = true;
	
	@Column(nullable = false, columnDefinition = "BIT", length = 1)
	private Boolean usuarioEliminado = true;
	
	public Notificacion() {}

	public Notificacion(Boolean altaUsuario, Boolean bajaUsuario, Boolean eventoCancelado, Boolean datosModificados,
			Boolean usuarioEliminado) {
		this.altaUsuario = altaUsuario;
		this.bajaUsuario = bajaUsuario;
		this.eventoCancelado = eventoCancelado;
		this.datosModificados = datosModificados;
		this.usuarioEliminado = usuarioEliminado;
	}

	public Boolean getAltaUsuario() {
		return altaUsuario;
	}

	public void setAltaUsuario(Boolean altaUsuario) {
		this.altaUsuario = altaUsuario;
	}

	public Boolean getBajaUsuario() {
		return bajaUsuario;
	}

	public void setBajaUsuario(Boolean bajaUsuario) {
		this.bajaUsuario = bajaUsuario;
	}

	public Boolean getEventoCancelado() {
		return eventoCancelado;
	}

	public void setEventoCancelado(Boolean eventoCancelado) {
		this.eventoCancelado = eventoCancelado;
	}

	public Boolean getDatosModificados() {
		return datosModificados;
	}

	public void setDatosModificados(Boolean datosModificados) {
		this.datosModificados = datosModificados;
	}

	public Boolean getUsuarioEliminado() {
		return usuarioEliminado;
	}

	public void setUsuarioEliminado(Boolean usuarioEliminado) {
		this.usuarioEliminado = usuarioEliminado;
	}
	
	

}
