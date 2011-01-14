package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class GraficoTorta extends Grafico implements Serializable {
	
	private ArrayList<DadoUmEixo> dados;
	
	public GraficoTorta() {
		dados = new ArrayList<DadoUmEixo>();		
	}
	
	public ArrayList<DadoUmEixo> getDados() {
		return dados;
	}
	public void setDados(ArrayList<DadoUmEixo> dados) {
		this.dados = dados;
	}
	
	
	
}
