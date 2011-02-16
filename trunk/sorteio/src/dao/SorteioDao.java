package dao;

import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Grupo;
import modelo.Participante;
import modelo.Sorteio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.PersistenceUtil;

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

	public Sorteio findByCodigo(String codigo) {
		String q = "FROM Sorteio s WHERE s.codigo = :codigo";
		Query query = em.createQuery(q);
		query.setParameter("codigo", codigo);

		return (Sorteio) query.getSingleResult();

	}

	public List<Sorteio> findByGrupo(Grupo grupo) {
		String q = "FROM Sorteio s WHERE s.grupo.id = :grupo";
		Query query = em.createQuery(q);
		query.setParameter("grupo", grupo.getId());

		return (List<Sorteio>) query.getResultList();

	}

	public void update(Sorteio sorteio) throws Exception {

		try {
			em.getTransaction().begin();
			em.merge(sorteio);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception();
		}
	}

	public void excluir(Sorteio sorteio) throws Exception {
		try {
			em.getTransaction().begin();
			em.remove(sorteio);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception();
		}
	}

	public void sortearPendentes() {
		ParticipacaoDao participacaoDao = new ParticipacaoDao(
				PersistenceUtil.getEntityManager());
		Logger log = LoggerFactory.getLogger("logInicio");
		log.info("Iniciando verificação de sorteios pendentes");

		String q = "From Sorteio s WHERE s.dataFim < :agora AND s.sorteado = false";
		Query query = em.createQuery(q);
		query.setParameter("agora", new GregorianCalendar());

		List<Sorteio> sorteios = (List<Sorteio>) query.getResultList();
		int i = 0;
		for (Sorteio sorteio : sorteios) {
			List<Participante> ganhadores = participacaoDao
					.sorteiaParticipanteSorteio(sorteio);
			sorteio.setGanhadores(ganhadores);
			sorteio.setSorteado(true);
			try {
				update(sorteio);
				i++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		log.info("Foram sorteados " + i + " sorteios pendentes");
		log.info("Final verificação de sorteios pendentes");

	}

	public List<Sorteio> buscar(String termo,Grupo grupo) {	
		String q = "FROM Sorteio s WHERE s.grupo.id =:grupo AND " +
				"(lower(s.nome) LIKE lower(:termo) OR lower(s.descricao)" +
				" LIKE lower(:termo))";
		Query query = em.createQuery(q);
		query.setParameter("termo", "%" + termo + "%");
		query.setParameter("grupo", grupo.getId());

		return (List<Sorteio>) query.getResultList();
	}
	public List<Sorteio> findSorteiosASortear() {
		String q = "FROM Sorteio s WHERE s.dataFim > :agora AND s.sorteado = false";		
		Query query = em.createQuery(q);
		query.setParameter("agora", new GregorianCalendar());

		List<Sorteio> sorteios = (List<Sorteio>) query.getResultList();
		return sorteios;
	}

}
