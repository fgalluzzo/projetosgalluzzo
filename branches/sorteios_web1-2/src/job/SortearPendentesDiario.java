package job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SortearPendentesDiario implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SortearPendentes.sortear();
		
	}

}
