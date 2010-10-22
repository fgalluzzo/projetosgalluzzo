package dao;

import java.sql.BatchUpdateException;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.exception.ConstraintViolationException;

import util.MessagesReader;
import util.PersistenceUtil;

import bean.LivroBean;

public class LivroDao extends AbstractDao<LivroBean> {

	public LivroDao(EntityManager em) {
		super(em);
	}

	public void createLivro(LivroBean livro) throws Exception {

		try {
			em.getTransaction().begin();	
				em.persist(livro);						
			em.getTransaction().commit();
		} catch (PersistenceException e) {	
			
			Throwable lastCause = e;
			String constraintName = null;
			while (lastCause != null) {
				if (lastCause.toString().startsWith(
						"java.sql.BatchUpdateException")) {
					BatchUpdateException bu = (BatchUpdateException) lastCause;
					constraintName = PersistenceUtil
							.getViolatedConstraintNameExtracter()
							.extractConstraintName(bu.getNextException());

				}
				lastCause = lastCause.getCause();

			}

			if (constraintName != null) {
				if (constraintName.equals("livro_uk")) {
					
					throw new ConstraintViolationException(MessagesReader
							.getMessages().getProperty(
									"erros.constraint.unique"),
							new SQLException(), MessagesReader.getMessages()
									.getProperty("alerta.isbnUnico"));
				}
			}else{
				
				throw new Exception();
			}
		}catch (Exception e) {			 
			 
			 throw new Exception();
		}finally{
			if (em != null) {
				em.close();
			}
		}
	}

	public LivroBean findByISBN(String isbn){
		
		Query q = em.createQuery("FROM LivroBean l WHERE l.isbn = :isbn");
		q.setParameter("isbn", isbn);
		try{
			return (LivroBean) q.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
						
		
	}
}
