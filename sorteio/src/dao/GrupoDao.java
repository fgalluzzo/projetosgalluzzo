package dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import modelo.Grupo;
import modelo.Sorteio;

public class GrupoDao extends AbstractDao<Grupo> {
	
	public GrupoDao(EntityManager em) {
		super(em);
	}
	
	public boolean verificaCodigoExistente(String codigo) {
		String q = "FROM Grupo g WHERE g.codigo = :codigo ";
		Query query = em.createQuery(q);
		query.setParameter("codigo", codigo);

		if (query.getResultList().size() > 0) {
			return true;
		}

		return false;
	}
	public Grupo findByCodigo(String codigo) throws NoResultException {
		String q = "FROM Grupo g WHERE g.codigo = :codigo ";
		Query query = em.createQuery(q);
		query.setParameter("codigo", codigo);
		
		try {
			Grupo grupo = (Grupo) query.getSingleResult();
			return grupo;
		} catch(NoResultException e) {
			throw new NoResultException();
		}
	}
	
	public void createGrupo(Grupo grupo) throws Exception {
		try {
			em.getTransaction().begin();
			em.persist(grupo);
			em.getTransaction().commit();

		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception();
		}
	}
}
