package com.matoosfe.sisfac.negocio;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.matoosfe.sisfac.entidad.Usuario;

@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {
	
	@PersistenceContext(unitName = "sisfacPU")
	private EntityManager em;

	public UsuarioFacade() {
		super(Usuario.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	public Usuario validarUsuario(String usuario, String clave) throws Exception {
		Usuario usuDev = null;
		
		try {
			TypedQuery<Usuario> conUsu = em.createQuery(
					"Select u from Usuario u where u.usuUsuario = :paramUsu and u.usuClave = :paramCla", Usuario.class);
			
			conUsu.setParameter("paramUsu", usuario);
			conUsu.setParameter("paramCla", clave);
			usuDev = conUsu.getSingleResult();
		} catch (Exception e) {
			throw new Exception("Usuario no encontrado");
		}
		
		return usuDev;
	}
	
	
}
