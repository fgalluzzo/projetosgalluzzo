package dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;

import controller.MessagesController;

import bean.UsuarioBean;

import util.PersistenceUtil;

public class UsuarioDao extends AbstractDao<UsuarioBean> {
	
	public UsuarioDao(EntityManager em) {
        super(em);
    }
	
	public void createUsuario(UsuarioBean usuario){
				 		
		
		 try{			 
			 em.getTransaction().begin();
			 	em.persist(usuario); 
			 em.getTransaction().commit();			
		 }catch (Exception e) {
			 em.getTransaction().rollback();			 
		}finally{
			if (em != null) {
				em.close();
			}
		}
		 	
		 
		 
		 
	}
	
	
	
	
	
}
