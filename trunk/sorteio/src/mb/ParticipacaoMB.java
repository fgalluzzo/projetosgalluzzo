package mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

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
		String sorteio = ((HttpServletRequest) FacesContext.getCurrentInstance().
				getExternalContext().getRequest()).getParameter("sorteio");
		if(sorteio !=null && !sorteio.isEmpty() ){
			temSorteio = true;
			this.sorteio = new Sorteio();
			this.sorteio.setNome(sorteio);
		}
		 
		
	}

	public String processar() {
		Integer numero = (int) (Math.round(Math.random()) * 10000);
		participacao.setNumero(numero);
		System.out.println(numero);
		return "inscrito";
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
