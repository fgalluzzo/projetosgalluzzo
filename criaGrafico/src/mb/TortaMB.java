package mb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import modelo.DadoUmEixo;
import modelo.GraficoTorta;

import org.primefaces.model.chart.PieChartModel;

@ManagedBean(name="tortaMB")
@SessionScoped
public class TortaMB {
	private PieChartModel pieModel;	
	private Map<String, Number> data;
	private String titulo;	
	private DadoUmEixo dado;
	private ArrayList<DadoUmEixo> dados;
	private boolean rendered;

	public TortaMB() {
		preTortaMB();
	}
	
	public String preTortaMB(){
		reinit();
		return "torta.xhtml?faces-redirect=true";
	}
	
	
	public void reinit(){
		rendered = false;
		titulo = new String() ;
		pieModel = new PieChartModel();
		rendered = false;
		dados = new ArrayList<DadoUmEixo>();
		dado = new DadoUmEixo();		
	}
	public void add(){
		dado = new DadoUmEixo();
	}
	public void processa(){
		//String[] valoresArray = valores.split(";");
		data = new HashMap<String, Number>();
		/*for(String valor : valoresArray){
			int idx = valor.indexOf("=");
			String label = valor.substring(0, idx);	
			String numeroString = valor.substring(idx+1);
			numeroString = numeroString.replace(",", ".");
			Number numero = Double.parseDouble(numeroString);				
			data.put(label, numero);
		}*/
		for(DadoUmEixo d :dados){
			data.put(d.getNome(), d.getValor());
		}
				
		pieModel = new PieChartModel(data);		
	
		rendered = true;		
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public boolean isRendered() {
		return rendered;
	}


	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}


	public DadoUmEixo getDado() {
		return dado;
	}


	public void setDado(DadoUmEixo dado) {
		this.dado = dado;
	}


	public ArrayList<DadoUmEixo> getDados() {
		return dados;
	}


	public void setDados(ArrayList<DadoUmEixo> dados) {
		this.dados = dados;
	}

	
	

	
	
	
	
}
