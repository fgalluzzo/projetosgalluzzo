package factoryImpl;

import java.awt.Color;
import java.util.List;

import modelo.DadosDoisEixos;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.LineRenderer3D;
import org.jfree.data.category.CategoryDataset;

import factory.AbstractDataset;

public class LineChartFactory extends AbstractDataset  {	
	
	public static JFreeChart getChart(String title,String categoria,String valor,List<DadosDoisEixos> dados){
		CategoryDataset dataset = getDataset(dados);
		JFreeChart chart = ChartFactory.createLineChart(title, categoria, valor, dataset, PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();		
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);
		plot.setDomainGridlinesVisible(true);
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		for(int i = 0;i< dataset.getRowCount();i++){			
			renderer.setSeriesShapesVisible(i, true);
		}				
		return chart;
	}
	public static JFreeChart getChart3D(String title,String categoria,String valor,List<DadosDoisEixos> dados){
		CategoryDataset dataset = getDataset(dados);
		JFreeChart chart = ChartFactory.createLineChart3D(title, categoria, valor, dataset, PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);
		plot.setDomainGridlinesVisible(true);	
		LineRenderer3D renderer = (LineRenderer3D) plot.getRenderer();		
		return chart;
	}
	
	public static JFreeChart getChart(String title,String categoria,String valor,List<DadosDoisEixos> dados,CategoryLabelPositions inclinacao){
		CategoryDataset dataset = getDataset(dados);
		JFreeChart chart = ChartFactory.createLineChart(title, categoria, valor, dataset, PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();		
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);
		plot.setDomainGridlinesVisible(true);
		plot.getDomainAxis().setCategoryLabelPositions(inclinacao);
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		for(int i = 0;i< dataset.getRowCount();i++){			
			renderer.setSeriesShapesVisible(i, true);
		}				
		return chart;
	}
}
