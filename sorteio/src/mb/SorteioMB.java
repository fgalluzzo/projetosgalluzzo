package mb;

import java.util.List;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import util.PersistenceUtil;

import dao.SorteioDao;

import modelo.Sorteio;

@ManagedBean(name="sorteioMB")
@RequestScoped
public class SorteioMB {
	private List<Sorteio> sorteios;
	private SorteioDao sorteioDao;
	FacesContext context = FacesContext.getCurrentInstance();
	Application app = context.getApplication();

	ValueExpression expression = app.getExpressionFactory().createValueExpression(context.getELContext(),
                            String.format("#{%s}", "loginMB"), Object.class);
	LoginMB loginMB = (LoginMB) expression.getValue(context.getELContext());
	
	public SorteioMB(){
		sorteioDao = new SorteioDao(PersistenceUtil.getEntityManager());
		sorteios = sorteioDao.findByGrupo(loginMB.getUsuario().getGrupo());
		
		
	}
	public List<Sorteio> getSorteios() {
		return sorteios;
	}

	public void setSorteios(List<Sorteio> sorteios) {
		this.sorteios = sorteios;
	}
}
