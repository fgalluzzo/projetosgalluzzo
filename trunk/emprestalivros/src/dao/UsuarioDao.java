package dao;

import java.sql.BatchUpdateException;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.exception.Configurable;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCExceptionHelper;
import org.hibernate.exception.SQLExceptionConverterFactory;
import org.hibernate.exception.TemplatedViolatedConstraintNameExtracter;
import org.hibernate.exception.ViolatedConstraintNameExtracter;
import org.hibernate.util.JDBCExceptionReporter;
import org.postgresql.translation.messages_bg;

import controller.MessagesController;

import bean.UsuarioBean;

import sun.misc.resources.Messages;
import util.MessagesReader;
import util.PersistenceUtil;

public class UsuarioDao extends AbstractDao<UsuarioBean> {
	
	public UsuarioDao(EntityManager em) {
        super(em);
    }
	
	public void createUsuario(UsuarioBean usuario) throws Exception{
				 		
		
		 try{			 
			 em.getTransaction().begin();
			 	em.persist(usuario); 
			 	em.flush();
			 em.getTransaction().commit();			
		 }catch (PersistenceException e) {
			
			 Throwable lastCause = e;
			 String constraintName =null;
			 while (lastCause != null){		
		    	  if(lastCause.toString().startsWith("java.sql.BatchUpdateException")){
		    		  BatchUpdateException bu = (BatchUpdateException) lastCause;
		    		  constraintName = PersistenceUtil.getViolatedConstraintNameExtracter().extractConstraintName(bu.getNextException());
		    		  
		    	  }
		    	  lastCause = lastCause.getCause();

			 }
		      			
			if(constraintName !=null){
				if(constraintName.equals("usuario_uk")){
					throw new ConstraintViolationException(MessagesReader.getMessages().getProperty("erros.constraint.unique"),new SQLException(),MessagesReader.getMessages().getProperty("alerta.loginUnico"));
				}else if(constraintName.equals("email_uk")){
					throw new ConstraintViolationException(MessagesReader.getMessages().getProperty("erros.constraint.unique"),new SQLException(),MessagesReader.getMessages().getProperty("alerta.emailUnico"));
				}
			}
				 
			  
		}catch (Exception e) {
			 //em.getTransaction().rollback();
			 throw new Exception();
		}finally{
			if (em != null) {
				em.close();
			}
		}
		 	
		 
		 
		 
	}
	
	
	
	
	
}
