/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package filter;


import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import managedBean.UsuarioLogadoMB;

/**
 *
 * @author galluzzo
 */
public class FiltroLogin implements PhaseListener{
	private static final long serialVersionUID = 1817830125632680682L;

	public void afterPhase(PhaseEvent event) {
      FacesContext context = event.getFacesContext();

       boolean pagesWL = context.getViewRoot().getViewId().lastIndexOf("login") > -1? true:false;
       boolean pagesWL2 = context.getViewRoot().getViewId().lastIndexOf("cadastro") > -1? true:false;
       
       // pegar o managed bean de sessão
       Application app = context.getApplication();

       ValueExpression expression = app.getExpressionFactory().createValueExpression(context.getELContext(),
                            String.format("#{%s}", "logado"), Object.class);

       UsuarioLogadoMB usuario = (UsuarioLogadoMB) expression.getValue(context.getELContext());
       //descomentar depois q criar os usuários no banco
       if(!( pagesWL || pagesWL2) && usuario.getUsuario().getApelido()== null ){
           NavigationHandler nh = app.getNavigationHandler();
           nh.handleNavigation(context, null, "login");
       }
    }

    public void beforePhase(PhaseEvent event) {
    
    }

    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

}
