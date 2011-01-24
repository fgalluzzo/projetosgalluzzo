package job;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.PersistenceUtil;

import dao.SorteioDao;

public class SortearPendentes implements ServletContextListener {
	SorteioDao sorteioDao;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		sorteioDao = new SorteioDao(PersistenceUtil.getEntityManager());
		sorteioDao.sortearPendentes();
		
	}

}
