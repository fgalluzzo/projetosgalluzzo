package filtro;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import mb.LoginMB;


public class LoginFilter implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		 FacesContext context = event.getFacesContext();
		   boolean isParticipacaoOuCadastro = context.getViewRoot().getViewId().contains("participar") ||
		   context.getViewRoot().getViewId().contains("cadastro");
	       boolean pagesWL = context.getViewRoot().getViewId().lastIndexOf("login") > -1? true:false;	 
	       boolean pageRec = context.getViewRoot().getViewId().contains("recuperar");
	       String view = context.getViewRoot().getViewId();
	       // pegar o managed bean de sessão
	       Application app = context.getApplication();

	       ValueExpression expression = app.getExpressionFactory().createValueExpression(context.getELContext(),
	                            String.format("#{%s}", "loginMB"), Object.class);

	       LoginMB loginMB = (LoginMB) expression.getValue(context.getELContext());
	       //descomentar depois q criar os usuários no banco
	       if(!isParticipacaoOuCadastro && !pageRec && !( pagesWL) && loginMB.getUsuario().getApelido()== null ) {
	           NavigationHandler nh = app.getNavigationHandler();
	           nh.handleNavigation(context, null, "login?faces-redirect=true");
	       }else if(pagesWL && loginMB.getUsuario().getApelido()!= null) {
	    	   NavigationHandler nh = app.getNavigationHandler();
	           nh.handleNavigation(context, null,"index?faces-redirect=true");
	       }
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {

		
	}

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.RESTORE_VIEW;
	}

}