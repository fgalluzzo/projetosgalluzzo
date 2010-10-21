package util;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.exception.JDBCExceptionHelper;
import org.hibernate.exception.TemplatedViolatedConstraintNameExtracter;
import org.hibernate.exception.ViolatedConstraintNameExtracter;

public class PersistenceUtil {
	
	private static EntityManagerFactory emf = null;
	private static EntityManager em = null;
	
	
	/**
	 * Singleton para criar factory apenas uma vez
	 * @return
	 */
	public static EntityManagerFactory getEntityManagerFactory() {
		if (emf == null)
			emf = Persistence.createEntityManagerFactory("el");
		return emf;
	}
	
	/**
	 * Retorna ultimo EM aberto e em execucao ou cria um novo.
	 * @return
	 */
	public static EntityManager getEntityManager() {
		if (em != null && em.isOpen()) return em;
		else {
			em = getEntityManagerFactory().createEntityManager();
			return em;
		}
	}
	
	/**
	 * Retorna uma nova instancia de EntityManager sem substituir o anterior.
	 * @return
	 */
	public static EntityManager createEntityManager() {
		return getEntityManagerFactory().createEntityManager();
	}
	
	@Override
	protected void finalize() throws Throwable {		
		super.finalize();
				
		emf.close();
		
		System.out.println("finalize");
	}
	
	public static ViolatedConstraintNameExtracter getViolatedConstraintNameExtracter() {
		
        return EXTRACTER;
   }
	
	private static ViolatedConstraintNameExtracter EXTRACTER = new TemplatedViolatedConstraintNameExtracter() {

	      /**
	       * Extract the name of the violated constraint from the given SQLException.
	       *
	       * @param sqle The exception that was the result of the constraint violation.
	       * @return The extracted constraint name.
	       */		
	      public String extractConstraintName(SQLException sqle) {
	         String constraintName = null;
	         
	         int sqlError = Integer.valueOf(JDBCExceptionHelper.extractSqlState(sqle));
	     
	         if(sqlError == 23505){
	        	
	            constraintName = extractUsingTemplate("violates unique constraint \"","\"", sqle.getMessage());
	         }	         

	         return constraintName;
	      }

	   };
}



