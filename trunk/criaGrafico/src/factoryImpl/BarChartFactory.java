package factoryImpl;

import java.util.List;

import modelo.DadosDoisEixos;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;

import factory.AbstractDataset;


public class BarChartFactory extends AbstractDataset  {
	
	public static JFreeChart getChart(String title,String categoria,String valor,List<DadosDoisEixos> dados){
		JFreeChart chart = ChartFactory.createBarChart(title, categoria, valor, getDataset(dados), PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();		
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setLegendItemLabelGenerator(new StandardCategorySeriesLabelGenerator());	
		return chart;
	}
	public static JFreeChart getChart3D(String title,String categoria,String valor,List<DadosDoisEixos> dados){
		JFreeChart chart = ChartFactory.createBarChart3D(title, categoria, valor, getDataset(dados), PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();
		renderer.setLegendItemLabelGenerator(new StandardCategorySeriesLabelGenerator());
		return chart;
	}
}
