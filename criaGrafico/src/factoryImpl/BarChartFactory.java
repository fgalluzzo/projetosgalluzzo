package factoryImpl;

import java.awt.Color;
import java.util.List;

import javax.swing.Renderer;

import modelo.DadosDoisEixos;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.primefaces.component.chart.bar.BarChartRenderer;

import util.ColorHandler;

import factory.AbstractDataset;


public class BarChartFactory extends AbstractDataset  {
	
	public static JFreeChart getChart(String title,String categoria,String valor,List<DadosDoisEixos> dados){
		JFreeChart chart = ChartFactory.createBarChart(title, categoria, valor, getDataset(dados), PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();				
		
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);
		plot.setDomainGridlinesVisible(true);
		BarRenderer renderer = (BarRenderer) plot.getRenderer();		
		//renderer.setSeriesPaint(0, new Color(ColorHandler.getR(cor),ColorHandler.getG(cor),ColorHandler.getB(cor)));
		return chart;
	}
	public static JFreeChart getChart3D(String title,String categoria,String valor,List<DadosDoisEixos> dados){
		JFreeChart chart = ChartFactory.createBarChart3D(title, categoria, valor, getDataset(dados), PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);
		plot.setDomainGridlinesVisible(true);
		BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();
		//renderer.setSeriesPaint(0, new Color(ColorHandler.getR(cor),ColorHandler.getG(cor),ColorHandler.getB(cor)));
		
		return chart;
	}
	
	public static JFreeChart getChart(String title,String categoria,String valor,List<DadosDoisEixos> dados,Color cor){
		JFreeChart chart = ChartFactory.createBarChart(title, categoria, valor, getDataset(dados), PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();				
		
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);
		plot.setDomainGridlinesVisible(true);
		BarRenderer renderer = (BarRenderer) plot.getRenderer();		
		renderer.setSeriesPaint(0, cor);
		return chart;
	}
	
	public static JFreeChart getChart3D(String title,String categoria,String valor,List<DadosDoisEixos> dados,Color cor){
		JFreeChart chart = ChartFactory.createBarChart3D(title, categoria, valor, getDataset(dados), PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();				
		
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);
		plot.setDomainGridlinesVisible(true);
		BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();	
		renderer.setSeriesPaint(0, cor);
		return chart;
	}
}
