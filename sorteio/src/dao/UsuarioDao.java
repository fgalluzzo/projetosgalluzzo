package dao;

import javax.persistence.EntityManager;

import modelo.Usuario;

public class UsuarioDao extends AbstractDao<Usuario>{

	public UsuarioDao(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}
	
}
