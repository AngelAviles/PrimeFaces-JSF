package com.matoosfe.sisfac.seguridad.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class LoginBean {
	
	private String usuario;
	private String clave;
	
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	/**
	 * Metodo para validar el usuario
	 */
	public void validarUsuario() {
		
	}
}
