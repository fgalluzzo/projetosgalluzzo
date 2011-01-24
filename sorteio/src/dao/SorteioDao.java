package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Grupo;
import modelo.Sorteio;

public class SorteioDao extends AbstractDao<Sorteio> {

	public SorteioDao(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}

	public void createSorteio(Sorteio sorteio) throws Exception {
		try {
			em.getTransaction().begin();
				em.persist(sorteio);
			em.getTransaction().commit();

		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception();
		}
	}
	
	public List<Sorteio> findByGrupo(Grupo grupo){
		String q = "FROM Sorteio s WHERE s.grupo.id = :grupo";
		Query query = em.createQuery(q);
		query.setParameter("grupo", grupo.getId());
		
		return (List<Sorteio>) query.getResultList();
		
	}
	
	public void update(Sorteio sorteio) throws Exception{
		
		try {
			em.getTransaction().begin();
				em.merge(sorteio);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception();
		}
	}
	

}
