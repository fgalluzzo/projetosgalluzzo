package dao;


import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Participacao;

public class ParticipacaoDao extends AbstractDao<Participacao> {

	public ParticipacaoDao(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}
	
	public boolean hasSorteioAndIp(Long sorteio_id,String ip){
		String q = "FROM Participacao p WHERE p.sorteio.id = :id AND p.ip = :ip";
		Query query = em.createQuery(q);
		query.setParameter("id", sorteio_id);
		query.setParameter("ip", ip );
		
		if(query.getResultList().size() >0){
			return true;
		} 
		
		return false;
		
	}
	
	public void createParticipacao(Participacao p) throws Exception{
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
