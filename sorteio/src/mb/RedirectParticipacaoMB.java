package mb;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import modelo.Sorteio;
import util.PersistenceUtil;
import dao.SorteioDao;

@ManagedBean(name="redirectMB")
@RequestScoped
public class RedirectParticipacaoMB {
	
	private SorteioDao sorteioDao;
	private String sorteioId;
	private Sorteio sorteio;
	private boolean temSorteio = false;	
	
	public boolean isTemSorteio() {
		return temSorteio;
	}

	public void setTemSorteio(boolean temSorteio) {
		this.temSorteio = temSorteio;
	}

	public RedirectParticipacaoMB() {
		HttpServletRequest request = ((HttpServletRequest) FacesContext.getCurrentInstance().
				getExternalContext().getRequest());
		String codigo = request.getParameter("sorteio");	
		if(codigo !=null && !codigo.isEmpty() ){
			
			sorteioDao = new SorteioDao(PersistenceUtil.getEntityManager());
			try{
								
				this.sorteio = sorteioDao.findByCodigo(codigo);
				Calendar dtAtual = new GregorianCalendar();
				
				if(this.sorteio != null){
					if(dtAtual.after(this.sorteio.getDataInicioCal()) && dtAtual.before(this.sorteio.getDataFimCal())){
						temSorteio = true;
						FacesContext context = FacesContext.getCurrentInstance();
						Application app = context.getApplication();

						ValueExpression expression = app.getExpressionFactory().createValueExpression(context.getELContext(),
					                            String.format("#{%s}", "sorteioDTOMB"), Object.class);
						SorteioDTOMB sorteioDTOMB = (SorteioDTOMB) expression.getValue(context.getELContext());	
						sorteioDTOMB.setSorteio(this.sorteio);
						
						NavigationHandler nh = app.getNavigationHandler();
				        nh.handleNavigation(context, null, "participar.xhtml?faces-redirect=true");
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String getSorteioId() {
		return sorteioId;
	}

	public void setSorteioId(String sorteioId) {
		this.sorteioId = sorteioId;
	}
	
	
}