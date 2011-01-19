package mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Participacao;
import modelo.Sorteio;

@ManagedBean(name = "participacaoMB")
@RequestScoped
public class ParticipacaoMB {
	private Sorteio sorteio;
	private Participacao participacao;
	private boolean temSorteio = false;	

	public ParticipacaoMB() {
		participacao = new Participacao();
		this.sorteio = new Sorteio();
		String sorteio = ((HttpServletRequest) FacesContext.getCurrentInstance().
				getExternalContext().getRequest()).getParameter("sorteio");
		if(sorteio !=null && !sorteio.isEmpty() ){
			temSorteio = true;
			this.sorteio.setNome(sorteio);
		}
		
		
	}

	
	public String processar() {
		String cookieValue = "";
		Integer numero = (int) (Math.round(Math.random()* 10000));
		 HttpServletRequest httpServletRequest = 
			   (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();   
		 Cookie[] cookies = httpServletRequest.getCookies();
		 if (cookies != null) {
			 for(int i=0; i<cookies.length; i++){
				 if (cookies[i].getName().equalsIgnoreCase("c")){
					 cookieValue = cookies[i].getValue();
				 }
			 }
		 }
		System.out.println(httpServletRequest.getRemoteAddr());
		if(!cookieValue.equals("xx")){
			participacao.setNumeroInscricao(numero.toString());
			System.out.println(numero);
			HttpServletResponse httpServletResponse = 
				   (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
				  Cookie cookie = new Cookie("c", "xx");   
				  cookie.setMaxAge(365);
				  cookie.setComment("teste");
				  httpServletResponse.addCookie(cookie);  
			return "inscrito";
		}else
			return "naoInscrito";
		
		
	}
	
	public void processa(){
		System.out.println("oi");
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
