package mb;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;

import modelo.DadosDoisEixos;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import factoryImpl.BarChartFactory;
import factoryImpl.LineChartFactory;
import factoryImpl.PieChartFactory;

@ManagedBean(name = "grafMB")
@SessionScoped
public class GrafMB {
	private String titulo;
	private String labelX;
	private String labelY;
	private DadosDoisEixos dado;
	private List<DadosDoisEixos> dados;
	private boolean rendered;
	private boolean enable3D;
	private int tipo;
	private String stringTipo;
	private String labelPosition;
	private StreamedContent grafico;
	private StreamedContent graficoDownload;
	private String cor;
	private boolean renderCor;

	public GrafMB() {
		// TODO Auto-generated constructor stub
		preGraficoMB();

	}

	public void reinit() {
		titulo = "";
		labelX = "";
		labelY = "";
		dado = new DadosDoisEixos();
		dados = new ArrayList<DadosDoisEixos>();
		rendered = false;
		renderCor = true;
	}

	public String preGraficoMB() {
		reinit();
		return "grafico.xhtml?faces-redirect=true";
	}
	
	public void mudaTipo(ValueChangeEvent event) {
		int valor = (Integer) event.getNewValue();
		switch (valor) {
			case 1:
				renderCor = true;
				break;
			case 2:
				renderCor = true;
				break;
			case 3:
				renderCor = false;
				break;
		}
		
	}
	public void processa() {
		try {
			JFreeChart jFreeChart = null;
			switch (tipo) {
			case 1:
				if (!enable3D) {

					jFreeChart = BarChartFactory.getChart(titulo, labelX,
							labelY, dados,cor);
					stringTipo = "Barra";
				} else {
					jFreeChart = BarChartFactory.getChart3D(titulo, labelX,
							labelY, dados,cor);
					stringTipo = "Barra3d";
				}
				break;
			case 2:
				if (!enable3D) {
					jFreeChart = LineChartFactory.getChart(titulo, labelX,
							labelY, dados,cor);
					stringTipo = "Linha";
				} else {
					jFreeChart = LineChartFactory.getChart3D(titulo, labelX,
							labelY, dados,cor);
					stringTipo = "Linha3d";
				}
				break;
			case 3:
				if (!enable3D) {
					jFreeChart = PieChartFactory.getChart(titulo, dados);
					stringTipo = "Torta";
				} else {
					jFreeChart = PieChartFactory.getChart3D(titulo, dados);
					stringTipo = "Torta3d";
				}
				break;

			}
			jFreeChart.setBackgroundPaint(Color.white);
			jFreeChart.setBackgroundImageAlpha(Color.TRANSLUCENT);	
			String filePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/");
			File file = new File(filePath+ "/grafico");
			if(!file.exists()) {
				file.createNewFile();
			}
			graficoDownload = new DefaultStreamedContent(new FileInputStream(
					file), "image/png", "GraficoDe"+stringTipo+"-"+titulo.hashCode() + ".png");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ChartUtilities.writeChartAsPNG(baos, jFreeChart, 650, 400);
			ChartUtilities.saveChartAsPNG(file, jFreeChart, 650, 400);			
			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());
			grafico = new DefaultStreamedContent(bais);

			rendered = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void add() {
		processa();
		String nome = dado.getNome();
		dado = new DadosDoisEixos();
		dado.setNome(nome);
		
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

	public DadosDoisEixos getDado() {
		return dado;
	}

	public void setDado(DadosDoisEixos dado) {
		this.dado = dado;
	}

	public List<DadosDoisEixos> getDados() {
		return dados;
	}

	public void setDados(List<DadosDoisEixos> dados) {
		this.dados = dados;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getLabelPosition() {
		return labelPosition;
	}

	public void setLabelPosition(String labelPosition) {
		this.labelPosition = labelPosition;
	}

	public StreamedContent getGrafico() {
		return grafico;
	}

	public void setGrafico(StreamedContent grafico) {
		this.grafico = grafico;
	}

	public boolean isEnable3D() {
		return enable3D;
	}

	public void setEnable3D(boolean enable3d) {
		enable3D = enable3d;
	}

	public void setGraficoDownload(StreamedContent graficoDownload) {
		this.graficoDownload = graficoDownload;
	}

	public StreamedContent getGraficoDownload() {
		return graficoDownload;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getCor() {
		return cor;
	}

	public void setRenderCor(boolean renderCor) {
		this.renderCor = renderCor;
	}

	public boolean isRenderCor() {
		return renderCor;
	}

}
