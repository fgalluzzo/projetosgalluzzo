package mb;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import modelo.DadoUmEixo;
import modelo.GraficoTorta;

import org.primefaces.model.chart.PieChartModel;

@ManagedBean(name="tortaMB")
@RequestScoped
public class TortaMB {
	private PieChartModel pieModel;	
	private Map<String, Number> data;
	private String valores;
	private String titulo;
	private boolean rendered;

	public TortaMB() {
		 pieModel = new PieChartModel();
		 rendered = false;
	}
	
	
	public void processa(){
		String[] valoresArray = valores.split(";");
		data = new HashMap<String, Number>();
		
		for(String valor : valoresArray){
			int idx = valor.indexOf("=");
			String label = valor.substring(0, idx);	
			String numeroString = valor.substring(idx+1);
			numeroString = numeroString.replace(",", ".");
			Number numero = Double.parseDouble(numeroString);				
			data.put(label, numero);
		}
		
				
		pieModel = new PieChartModel(data);		
		rendered = true;		
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}


	public String getValores() {
		return valores;
	}


	public void setValores(String valores) {
		this.valores = valores;
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

	
	

	
	
	
	
}
