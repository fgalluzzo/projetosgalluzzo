package mb;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import modelo.DadoUmEixo;
import modelo.DadosDoisEixos;

import org.primefaces.component.chart.series.ChartSeries;
import org.primefaces.model.chart.CartesianChartModel;

@ManagedBean(name="linhaMB")
@SessionScoped
public class LinhaMB {
	private String titulo;	
	private CartesianChartModel cartesianModel;
	private DadosDoisEixos dado;
	private ArrayList<DadosDoisEixos> dados;
	private boolean rendered;
	private ArrayList<ChartSeries> series;
	private ArrayList<String> nomes;
	public LinhaMB() {
		// TODO Auto-generated constructor stub
		reinit();
		
	}
	
	public void reinit(){
		cartesianModel = new CartesianChartModel();
		dado = new DadosDoisEixos();
		dados = new ArrayList<DadosDoisEixos>();
		rendered = false;
		nomes = new ArrayList<String>();;
	}
	public void processa(){
		series = new ArrayList<ChartSeries>();
		 
		for(DadosDoisEixos d : dados){
			if(nomes.isEmpty() || !nomes.contains(d.getNome())){
				nomes.add(d.getNome());
				series.add(new ChartSeries(d.getNome()));
				
			}
				
			
		}
		for(DadosDoisEixos d : dados){
			for(int i =0;i<series.size();i++){
				if(series.get(i).getLabel().equals(d.getNome())){
					series.get(i).set(d.getX(), d.getY());					
				}
			}
		}
		for(ChartSeries s : series){
			cartesianModel.addSeries(s);
		}
		rendered = true;	
	}
	public void add(){
		dado = new DadosDoisEixos();
	}
	public CartesianChartModel getCartesianModel() {
		return cartesianModel;
	}

	public void setCartesianModel(CartesianChartModel cartesianModel) {
		this.cartesianModel = cartesianModel;
	}

	public DadosDoisEixos getDado() {
		return dado;
	}

	public void setDado(DadosDoisEixos dado) {
		this.dado = dado;
	}

	public ArrayList<DadosDoisEixos> getDados() {
		return dados;
	}

	public void setDados(ArrayList<DadosDoisEixos> dados) {
		this.dados = dados;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
