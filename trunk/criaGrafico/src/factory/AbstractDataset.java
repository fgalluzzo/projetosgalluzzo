package factory;

import java.util.List;

import modelo.DadosDoisEixos;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public abstract class AbstractDataset {
	protected static CategoryDataset getDataset(List<DadosDoisEixos> dados) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (DadosDoisEixos dado : dados) {
			dataset.addValue(dado.getY(),  dado.getNome(),dado.getX());
		}
		return dataset;
	}
	
	public static JFreeChart getChart(String title,String categoria,String valor,List<DadosDoisEixos> dados){
		return null;		
	}
	public static JFreeChart getChart(String title,List<DadosDoisEixos> dados){
		return null;
		
	}
	public static JFreeChart getChart3D(String title,List<DadosDoisEixos> dados){
		return null;
		
	}
	public static JFreeChart getChart3D(String title,String categoria,String valor,List<DadosDoisEixos> dados){
		return null;
		
	}
}
