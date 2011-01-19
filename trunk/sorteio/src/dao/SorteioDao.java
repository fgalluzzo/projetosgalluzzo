package dao;

import javax.persistence.EntityManager;

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
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

}
