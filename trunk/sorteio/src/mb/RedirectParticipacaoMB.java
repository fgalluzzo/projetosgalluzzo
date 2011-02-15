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

@ManagedBean(name = "redirectMB")
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
		HttpServletRequest request = ((HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest());
		String codigo = request.getParameter("sorteio");
		String embed = request.getParameter("embed");
		FacesContext context = FacesContext.getCurrentInstance();
		Application app = context.getApplication();
		NavigationHandler nh = app.getNavigationHandler();
		if (codigo != null && !codigo.isEmpty()) {

			sorteioDao = new SorteioDao(PersistenceUtil.getEntityManager());
			try {

				this.sorteio = sorteioDao.findByCodigo(codigo);
				Calendar dtAtual = new GregorianCalendar();

				if (this.sorteio != null) {
					temSorteio = true;
					ValueExpression expression = app
							.getExpressionFactory()
							.createValueExpression(
									context.getELContext(),
									String.format("#{%s}", "cadastroSorteioMB"),
									Object.class);
					CadastroSorteioMB csMB = (CadastroSorteioMB) expression
							.getValue(context.getELContext());
					if (dtAtual.after(this.sorteio.getDataInicioCal())
							&& dtAtual.before(this.sorteio.getDataFimCal())) {
						expression = app.getExpressionFactory()
								.createValueExpression(context.getELContext(),
										String.format("#{%s}", "sorteioDTOMB"),
										Object.class);
						SorteioDTOMB sorteioDTOMB = (SorteioDTOMB) expression
								.getValue(context.getELContext());
						sorteioDTOMB.setSorteio(this.sorteio);

						if (embed != null && !embed.isEmpty()) {
							csMB.setUltimaPagina(csMB.EMBED);
							nh.handleNavigation(context, null,
									"participarE.xhtml?faces-redirect=true");
						} else {
							csMB.setUltimaPagina(csMB.NOTEMBED);
							nh.handleNavigation(context, null,
									"participar.xhtml?faces-redirect=true");
						}

					} else if (this.sorteio.isSorteado()) {

						csMB.setSorteio(this.sorteio);
						if (embed != null && !embed.isEmpty()) {
							nh.handleNavigation(context, null,
									"resultadoE.xhtml?faces-redirect=true");
						} else {
							nh.handleNavigation(context, null,
									"resultado.xhtml?faces-redirect=true");
						}
					} else {
						temSorteio = false;
						if (embed != null && !embed.isEmpty()) {
							nh.handleNavigation(context, null,
									"indexE.xhtml?faces-redirect=true");
						} else {
							nh.handleNavigation(context, null,
									"index.xhtml?faces-redirect=true");
						}
					}

				} else {
					if (embed != null && !embed.isEmpty()) {
						nh.handleNavigation(context, null,
								"indexE.xhtml?faces-redirect=true");
					} else {
						nh.handleNavigation(context, null,
								"index.xhtml?faces-redirect=true");
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
