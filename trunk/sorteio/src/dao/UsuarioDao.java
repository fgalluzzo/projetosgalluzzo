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
	public void create(Usuario usuario) throws Exception {
		try {
			em.getTransaction().begin();
				em.persist(usuario);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception();
		}
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
	public boolean verificaLoginExistente(String apelido)  {
		String q = "FROM Usuario u WHERE u.apelido = :apelido ";
		Query query = em.createQuery(q);
		query.setParameter("apelido", apelido);
		
		if (query.getResultList().size() > 0) {
			return true;
		}

		return false;
	}
	public Usuario findByLoginSenha(Usuario usuario) throws NoResultException{
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
	public Usuario findByUsuarioEmail(Usuario usuario) throws NoResultException {
		String q = "FROM Usuario u WHERE u.apelido = :apelido AND u.email = :email";
		Query query = em.createQuery(q);
		query.setParameter("apelido", usuario.getApelido());
		query.setParameter("email", usuario.getEmail());
		try{
			Usuario usuLogin = (Usuario) query.getSingleResult();
			return usuLogin;
		} catch (NoResultException e) {
			throw new NoResultException();
		}			
	}
	public Usuario findByEmail(Usuario usuario)  throws NoResultException {
		String q = "FROM Usuario u WHERE u.email = :email";
		Query query = em.createQuery(q);
		query.setParameter("email", usuario.getEmail());
		try{
			Usuario usuLogin = (Usuario) query.getSingleResult();
			return usuLogin;
		} catch (NoResultException e) {
			throw new NoResultException();
		}			
	}
}
