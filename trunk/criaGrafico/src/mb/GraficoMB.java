package mb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.openfaces.component.chart.ChartModel;
import org.openfaces.component.chart.ChartViewType;
import org.openfaces.component.chart.PlainModel;
import org.openfaces.component.chart.PlainSeries;
import org.primefaces.component.chart.series.ChartSeries;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.CartesianChartModel;

import modelo.DadosDoisEixos;

@ManagedBean(name="graficoMB")
@SessionScoped
public class GraficoMB {
	private String titulo;
	private String labelX;
	private String labelY;
	private ChartModel chartModel;
	private DadosDoisEixos dado;
	private ArrayList<DadosDoisEixos> dados;
	private boolean rendered;
	private boolean renderBar;
	private boolean renderLine;
	private boolean renderPie;
	private boolean enable3D;
	private int tipo;
	private ChartViewType tipoChart;
	
	public GraficoMB() {
		// TODO Auto-generated constructor stub
		preGraficoMB();

	}

	public void reinit() {
		titulo = "";
		enable3D=false;
		labelX="";
		labelY="";
		setChartModel(new PlainModel());
		dado = new DadosDoisEixos();
		dados = new ArrayList<DadosDoisEixos>();
		rendered = false;
	}

	public String preGraficoMB() {
		reinit();
		return "grafico.xhtml?faces-redirect=true";
	}
	public void processa() {
		ArrayList<PlainSeries> series = new ArrayList<PlainSeries>();
		ArrayList<String> nomes = new ArrayList<String>();
		ArrayList<Map> dataList = new ArrayList<Map>();
		for (DadosDoisEixos d : dados) {
			if (nomes.isEmpty() || !nomes.contains(d.getNome())) {
				nomes.add(d.getNome());
				series.add(new PlainSeries(d.getNome()));

			}

		}
		for (DadosDoisEixos d : dados) {
			for (int i = 0; i < series.size(); i++) {
				if (series.get(i).getKey().equals(d.getNome())) {
					if (dataList.size() == i ) {
						Map data = new HashMap<String,Double>();
						dataList.add(data);
					} 
					 
					dataList.get(i).put(d.getX(), d.getY());
					
						
				}
			}

			

		}
		
		PlainModel model = new PlainModel();
		for (int i = 0; i < series.size(); i++) {
			series.get(i).setData(dataList.get(i));			
			model.addSeries(series.get(i));
		}
		
		setChartModel(model);
		rendered = true;
	}

	public void add() {
		dado = new DadosDoisEixos();
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

	public String getLabelX() {
		return labelX;
	}

	public void setLabelX(String labelX) {
		this.labelX = labelX;
	}

	public String getLabelY() {
		return labelY;
	}

	public void setLabelY(String labelY) {
		this.labelY = labelY;
	}

	public void setChartModel(ChartModel chartModel) {
		this.chartModel = chartModel;
	}

	public ChartModel getChartModel() {
		return chartModel;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
		switch (tipo) {
		case 1:
			setTipoChart(tipoChart.BAR);
			renderBar=true;
			renderLine=false;
			renderPie=false;
			break;
		case 2:
			setTipoChart(tipoChart.LINE);
			renderBar=false;
			renderLine=true;
			renderPie=false;
			break;	
		case 3:
			setTipoChart(tipoChart.PIE);
			renderBar=false;
			renderLine=false;
			renderPie=true;
			break;	
		default:
			break;
		}
	}

	public int getTipo() {
		return tipo;
	}
	public void setTipoChart(ChartViewType tipoChart) {
		this.tipoChart = tipoChart;
	}

	public ChartViewType getTipoChart() {
		return tipoChart;
	}

	public boolean isRenderBar() {
		return renderBar;
	}

	public void setRenderBar(boolean renderBar) {
		this.renderBar = renderBar;
	}

	public boolean isRenderLine() {
		return renderLine;
	}

	public void setRenderLine(boolean renderLine) {
		this.renderLine = renderLine;
	}

	public boolean isRenderPie() {
		return renderPie;
	}

	public void setRenderPie(boolean renderPie) {
		this.renderPie = renderPie;
	}

	public void setEnable3D(boolean enable3D) {
		this.enable3D = enable3D;
	}

	public boolean isEnable3D() {
		return enable3D;
	}
}
