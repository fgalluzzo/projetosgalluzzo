package mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import modelo.Sorteio;

@ManagedBean(name="sorteioDTOMB")
@SessionScoped
public class SorteioDTOMB {

	private Sorteio sorteio;

	public Sorteio getSorteio() {
		return sorteio;
	}

	public void setSorteio(Sorteio sorteio) {
		this.sorteio = sorteio;
	}

	
	
	
}
