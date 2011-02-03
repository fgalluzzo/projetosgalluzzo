package dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import modelo.Usuario;

public class UsuarioDao extends AbstractDao<Usuario>{

	public UsuarioDao(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}
	public void update(Usuario usuario) throws Exception {
		try {
			em.getTransaction().begin();
				em.merge(usuario);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception();
		}
		
	}
	public Usuario findByLoginSenha(Usuario usuario){
		String q = "FROM Usuario u WHERE u.apelido = :apelido AND u.senha = :senha";
		Query query = em.createQuery(q);
		query.setParameter("apelido", usuario.getApelido());
		query.setParameter("senha", usuario.getSenha());
		try{
			Usuario usuLogin = (Usuario) query.getSingleResult();
			return usuLogin;
		} catch (NoResultException e) {
			throw new NoResultException();
		}			
				
	}
}
