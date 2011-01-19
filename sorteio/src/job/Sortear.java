package job;

import modelo.Sorteio;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import dao.SorteioDao;

public class Sortear implements Job{
	Sorteio sorteio;
	SorteioDao sorteioDao;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		
		System.out.println("Sorteando sorteio:" + context.getJobDetail().getName());
		
	}

}
