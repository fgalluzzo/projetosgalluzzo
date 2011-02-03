package job;

import java.text.ParseException;

import javax.print.attribute.standard.Chromaticity;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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

		try {
			Logger logger = LoggerFactory.getLogger("agendamento de sorteios pendentes");
			logger.info("Início do agendamento diário de sorteios pendentes");
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sched = sf.getScheduler();
			
			
			
			JobDetail job = new JobDetail("pendentes","grupoJob1",
					SortearPendentesDiario.class);
			CronTrigger ct;
			ct = new CronTrigger("Trigger", "grupo1", "pendentes","grupoJob1");
			ct.setCronExpression("0 0 12pm * * ?");
			sched.scheduleJob(job, ct);
			sched.start();
			logger.info("Fim do agendamento diário de sorteios pendentes");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
