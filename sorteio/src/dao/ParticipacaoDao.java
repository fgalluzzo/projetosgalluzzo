package dao;


import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Participacao;
import modelo.Participante;
import modelo.Sorteio;

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
	
	public List<Participante> sorteiaParticipanteSorteio(Sorteio sorteio) {
		String q = "SELECT count(id) FROM Participacao p WHERE p.sorteio = :sorteio ";
		Query countQuery = em.createQuery(q);
		countQuery.setParameter("sorteio", sorteio);
		long count = (Long)countQuery.getSingleResult();

	    
	    Random random = new Random();
	    Integer number =  random.nextInt((int) count);

	    
		q = "SELECT pa FROM Participacao p JOIN p.participante pa " +
				"WHERE p.sorteio = :sorteio";
		Query query = em.createQuery(q);
		query.setParameter("sorteio", sorteio);
		
		query.setFirstResult(number);
		query.setMaxResults(sorteio.getQuantidadeGanhadores());
		return (List<Participante>)query.getResultList();
		
	}
}
