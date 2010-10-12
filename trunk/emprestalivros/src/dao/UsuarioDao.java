package dao;

import java.sql.BatchUpdateException;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.exception.ConstraintViolationException;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QEncoderStream;

import util.MessagesReader;
import util.PersistenceUtil;
import bean.UsuarioBean;

public class UsuarioDao extends AbstractDao<UsuarioBean> {
	
	public static final int OK = 1;
	public static final int NOK = 0;
	
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
	
	public UsuarioBean findByLoginSenha(String login,String senha){
		
		try{
			Query query = em.createQuery("FROM UsuarioBean WHERE apelido = :apelido AND senha = :senha");
			
			query.setParameter("apelido", login);
			query.setParameter("senha", senha);
			return (UsuarioBean)query.getSingleResult();
		}catch (NoResultException e) {
			throw new NoResultException();
		}
		
				
		
	}
	
	
	
	
	
}
