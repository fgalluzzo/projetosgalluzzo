package job;

import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import modelo.Sorteio;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.PersistenceUtil;
import config.Config;
import dao.SorteioDao;

public class SortearPendentesInicializacao implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched;		
		try {
			sched = sf.getScheduler();
			sched.shutdown();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		SortearPendentes.sortear();
		Logger logger = LoggerFactory.getLogger("agendamento de sorteios pendentes");
		try {
			
			logger.info("Início do agendamento de sorteios pendentes");
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sched = sf.getScheduler();
			
			SorteioDao sorteioDao = new SorteioDao(PersistenceUtil.getEntityManager());
			List<Sorteio> sorteios = (List<Sorteio>) sorteioDao.findSorteiosASortear();
		
			int i =0;
			for (Sorteio sorteio : sorteios) {
				JobDetail job = new JobDetail(sorteio.getId().toString(), Config.JOBGROUP,
						Sortear.class);
				SimpleTrigger disparo = new SimpleTrigger(Config.TRIGGER
						+ sorteio.getId().toString(), Config.JOBGROUP,
						sorteio.getDataFimD());
				sched.scheduleJob(job, disparo);
				i++;
			}
			sched.start();
			logger.info("Foram agendados "+i+" sorteios");
			logger.info("Fim do agendamento de sorteios pendentes");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			logger.info("Erro de agendamento:" +e.getStackTrace());			
		} catch (Exception e) {
			logger.info("Erro:" +e.getStackTrace());			
		}

	}

}
