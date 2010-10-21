package dao;

import java.sql.BatchUpdateException;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

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
			em.flush();
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
}
