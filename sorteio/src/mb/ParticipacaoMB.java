package mb;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajax4jsf.model.KeepAlive;

import modelo.Participacao;
import modelo.Participante;
import modelo.Sorteio;
import util.PersistenceUtil;
import dao.ParticipacaoDao;
import dao.ParticipanteDao;
import dao.SorteioDao;

@ManagedBean(name = "participacaoMB")
@RequestScoped
public class ParticipacaoMB {
	private Sorteio sorteio;
	private SorteioDao sorteioDao;
	private Participacao participacao;
	private ParticipacaoDao participacaoDao;
	private ParticipanteDao participanteDao;
	private boolean temSorteio = false;

	public ParticipacaoMB() {
		participacao = new Participacao();
		FacesContext context = FacesContext.getCurrentInstance();
		Application app = context.getApplication();

		ValueExpression expression = app.getExpressionFactory()
				.createValueExpression(context.getELContext(),
						String.format("#{%s}", "sorteioDTOMB"), Object.class);
		SorteioDTOMB sorteioDTOMB = (SorteioDTOMB) expression.getValue(context
				.getELContext());

		this.sorteio = sorteioDTOMB.getSorteio();

	}

	public String processar() {
		HttpServletRequest httpServletRequest = 
			   (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String ip = httpServletRequest.getRemoteAddr();		 
		
		sorteioDao = new SorteioDao(PersistenceUtil.getEntityManager());
		participacaoDao = new ParticipacaoDao(PersistenceUtil.getEntityManager());
		participanteDao = new ParticipanteDao(PersistenceUtil.getEntityManager());
		boolean temEmailCadastradoSorteio = participacaoDao.hasEmailParticipante(sorteio.getId(), participacao.getParticipante().getEmail());
		boolean temIpCadastradoNoSorteio = participacaoDao.hasSorteioAndIp(sorteio.getId(), ip);
		boolean temNomeSobrenomeCadastrado = participacaoDao.hasNomeSobrenomeParticipante(sorteio.getId(), 
				participacao.getParticipante().getNome(), participacao.getParticipante().getSobrenome());
		String cookieValue = "";	
		
		Cookie[] cookies = httpServletRequest.getCookies();
		if (cookies != null) {
			 for(int i=0; i<cookies.length; i++){
				 if (cookies[i].getName().equalsIgnoreCase(sorteio.getId().toString())){
					 cookieValue = cookies[i].getValue();
				 }
			 }
		 }	
		if(temEmailCadastradoSorteio) {
			return "naoInscrito";
		}
		if(temIpCadastradoNoSorteio && temNomeSobrenomeCadastrado ){			
			return "naoInscrito";
		}

		if(!cookieValue.equals(sorteio.getId().toString())){
			
			HttpServletResponse httpServletResponse = 
				   (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
			Cookie cookie = new Cookie("c", sorteio.getId().toString());   
			cookie.setMaxAge(365);				  
		    httpServletResponse.addCookie(cookie);  
		    sorteio.setInscritos(sorteio.getInscritos()+1);
		    participacao.setDataInscricao(new GregorianCalendar());
		    participacao.setSorteio(sorteioDao.findById(Sorteio.class, sorteio.getId()));
		    participacao.setIp(ip);
		    participacao.setNumeroInscricao(sorteio.getInscritos().toString());
		    
		    try {		    	
		    	sorteioDao.update(sorteio);
		    	participanteDao.createParticipante(participacao.getParticipante());
				participacaoDao.createParticipacao(participacao);				
				return "inscrito";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "naoInscrito";
			}
		
			
		}else
			return "naoInscrito";
		
		
	}

	public Sorteio getSorteio() {
		return sorteio;
	}

	public void setSorteio(Sorteio sorteio) {
		this.sorteio = sorteio;
	}

	public Participacao getParticipacao() {
		return participacao;
	}

	public void setParticipacao(Participacao participacao) {
		this.participacao = participacao;
	}

	public boolean isTemSorteio() {
		return temSorteio;
	}

	public void setTemSorteio(boolean temSorteio) {
		this.temSorteio = temSorteio;
	}

}
