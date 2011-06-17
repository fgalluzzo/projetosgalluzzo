package factoryImpl;

import java.awt.Color;
import java.util.List;

import modelo.DadosDoisEixos;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.util.TableOrder;

public class PieChartFactory {
	protected static CategoryDataset getDataset(List<DadosDoisEixos> dados) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (DadosDoisEixos dado : dados) {
			dataset.addValue(dado.getY(), dado.getX(), dado.getNome());
		}
		return dataset;
	}
	
	public static JFreeChart getChart(String title,List<DadosDoisEixos> dados){
		JFreeChart chart = ChartFactory.createMultiplePieChart(title,getDataset(dados),TableOrder.BY_COLUMN,true,true,false);
		MultiplePiePlot mPlot = (MultiplePiePlot) chart.getPlot();
		JFreeChart subChart = mPlot.getPieChart();
		PiePlot plot = (PiePlot) subChart.getPlot();
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}"));
		plot.setBackgroundPaint(Color.WHITE);
		return chart;
	}
	public static JFreeChart getChart3D(String title,List<DadosDoisEixos> dados){
		JFreeChart chart = ChartFactory.createMultiplePieChart3D(title,getDataset(dados),TableOrder.BY_COLUMN,true,true,false);
		MultiplePiePlot mPlot = (MultiplePiePlot) chart.getPlot();
		JFreeChart subChart = mPlot.getPieChart();
		PiePlot3D plot = (PiePlot3D) subChart.getPlot();		
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}"));
		plot.setBackgroundPaint(Color.WHITE);
		return chart;
	}
	
	
	
}
