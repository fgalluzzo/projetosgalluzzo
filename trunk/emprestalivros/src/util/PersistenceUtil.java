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
		// TODO Auto-generated method stub
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
	         
	         int errorCode = JDBCExceptionHelper.extractErrorCode(sqle);

	         if ( errorCode == -8 ) {
	            constraintName = extractUsingTemplate( "Integrity constraint violation ", " table:", sqle.getMessage() );
	         }
	         else if ( errorCode == -9 ) {
	            constraintName = extractUsingTemplate( "Violation of unique index: ", " in statement [", sqle.getMessage() );
	         }
	         else if ( errorCode == -104 ) {
	            constraintName = extractUsingTemplate( "Unique constraint violation: ", " in statement [", sqle.getMessage() );
	         }
	         else if ( errorCode == -177 ) {
	            constraintName = extractUsingTemplate( "Integrity constraint violation - no parent ", " table:", sqle.getMessage() );
	         }

	         return constraintName;
	      }

	   };
}



