package mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import util.PersistenceUtil;
import dao.SorteioDao;
import modelo.Sorteio;

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
