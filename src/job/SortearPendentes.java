package job;

import util.PersistenceUtil;
import dao.SorteioDao;

public class SortearPendentes {
	private static SorteioDao sorteioDao;
	
	public static void sortear() {
		sorteioDao = new SorteioDao(PersistenceUtil.getEntityManager());
		sorteioDao.sortearPendentes();
	}
}
