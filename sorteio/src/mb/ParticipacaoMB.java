package mb;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.PersistenceUtil;

import dao.ParticipacaoDao;
import dao.ParticipanteDao;
import dao.SorteioDao;

import modelo.Participacao;
import modelo.Sorteio;

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
		this.sorteio = new Sorteio();
		HttpServletRequest request = ((HttpServletRequest) FacesContext.getCurrentInstance().
				getExternalContext().getRequest());
		String sorteio = request.getParameter("sorteio");		
		
		if(sorteio !=null && !sorteio.isEmpty() ){
			
			sorteioDao = new SorteioDao(PersistenceUtil.getEntityManager());
			try{
				
				Long numero = Long.parseLong(sorteio);			
				this.sorteio = sorteioDao.findById(Sorteio.class, numero);
				Calendar dtAtual = new GregorianCalendar();
				
				if(this.sorteio != null){
					if(dtAtual.after(this.sorteio.getDataInicio()) && dtAtual.before(this.sorteio.getDataFimCal())){
						temSorteio = true;
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		}
		
		
	}

	
	public String processar() {
		sorteioDao = new SorteioDao(PersistenceUtil.getEntityManager());
		participacaoDao = new ParticipacaoDao(PersistenceUtil.getEntityManager());
		participanteDao = new ParticipanteDao(PersistenceUtil.getEntityManager());
		String cookieValue = "";
		Integer numero = (int) (Math.round(Math.random()* 10000));
		 HttpServletRequest httpServletRequest = 
			   (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		 String ip = httpServletRequest.getRemoteAddr();		 
		 Cookie[] cookies = httpServletRequest.getCookies();
		 if (cookies != null) {
			 for(int i=0; i<cookies.length; i++){
				 if (cookies[i].getName().equalsIgnoreCase(sorteio.getId().toString())){
					 cookieValue = cookies[i].getValue();
				 }
			 }
		 }		
		if(participacaoDao.hasSorteioAndIp(sorteio.getId(), ip)){
			return "naoInscrito";
		}
		if(!cookieValue.equals(sorteio.getId().toString())){
			
			HttpServletResponse httpServletResponse = 
				   (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
			Cookie cookie = new Cookie("c", sorteio.getId().toString());   
			cookie.setMaxAge(365);				  
		    httpServletResponse.addCookie(cookie);  
					    
		    participacao.setDataInscricao(new GregorianCalendar());
		    participacao.setSorteio(sorteioDao.findById(Sorteio.class, sorteio.getId()));
		    participacao.setIp(ip);
		    participacao.setNumeroInscricao(numero.toString());
		    try {
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
