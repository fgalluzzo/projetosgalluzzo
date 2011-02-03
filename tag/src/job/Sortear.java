package job;

import java.util.ArrayList;
import java.util.List;

import modelo.Participante;
import modelo.Sorteio;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import util.PersistenceUtil;
import dao.ParticipacaoDao;
import dao.SorteioDao;

public class Sortear implements Job{
	Sorteio sorteio;
	SorteioDao sorteioDao;
	ParticipacaoDao participacaoDao;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		sorteioDao = new SorteioDao(PersistenceUtil.getEntityManager());
		participacaoDao = new ParticipacaoDao(PersistenceUtil.getEntityManager());
		Sorteio sorteio = sorteioDao.findById(Sorteio.class, Long.parseLong(context.getJobDetail().getName()));
		List<Participante> ganhadores = participacaoDao.sorteiaParticipanteSorteio(sorteio);				
		sorteio.setGanhadores(ganhadores);
		sorteio.setSorteado(true);
		try {
			sorteioDao.update(sorteio);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
