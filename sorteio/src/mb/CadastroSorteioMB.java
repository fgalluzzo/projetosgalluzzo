package mb;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import job.Sortear;
import modelo.Sorteio;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import util.PersistenceUtil;
import dao.SorteioDao;

@ManagedBean(name="cadastroSorteioMB")
@RequestScoped
public class CadastroSorteioMB {
	
	private Sorteio sorteio;
	private SorteioDao sorteioDao;
	
	public CadastroSorteioMB() {
		sorteio = new Sorteio();

	}
	
	public String cadastrar(){
		sorteioDao = new SorteioDao(PersistenceUtil.getEntityManager());
		
		try {
			
			sorteioDao.createSorteio(sorteio);
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sched = sf.getScheduler();		

			JobDetail job = new JobDetail(sorteio.getId().toString(),Sortear.class);			
			SimpleTrigger disparo = new SimpleTrigger("Disparo do sorteio",sorteio.getDataFim());
			sched.scheduleJob(job, disparo);
			 
			sched.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return "index";
	}
	public Sorteio getSorteio() {
		return sorteio;
	}

	public void setSorteio(Sorteio sorteio) {
		this.sorteio = sorteio;
	}
	
	
}
