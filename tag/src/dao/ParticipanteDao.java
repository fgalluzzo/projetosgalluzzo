package dao;

import javax.persistence.EntityManager;

import modelo.Participante;

public class ParticipanteDao extends AbstractDao<Participante> {

	public ParticipanteDao(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}
	
	public void createParticipante(Participante p) throws Exception{
		
		try{
			em.getTransaction().begin();
				em.persist(p);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception();
		}
	}
}
