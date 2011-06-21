package mb;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import modelo.DadosDoisEixos;
import modelo.Sheet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import factoryImpl.LineChartFactory;

@ManagedBean(name = "importMB")
@SessionScoped
public class ImportMB {

	private File arquivo;
	private ArrayList<Sheet> sheets;
	private Sheet planilhaEscolhida;
	private boolean renderDadosPlanilhaEscolhida;
	private String nomeArquivo;
	private int colunaRotuloIndex;
	private int colunaValorIndex;
	private String colunaRotulo;
	private String colunaValor;
	private StreamedContent grafico;
	private boolean rendered;

	private void reinit() {
		arquivo = null;
		sheets = new ArrayList<Sheet>();
		planilhaEscolhida = null;
		renderDadosPlanilhaEscolhida = false;
		nomeArquivo = null;
		colunaRotuloIndex = 0;
		colunaValorIndex = 0;
		colunaRotulo = "";
		colunaValor = "";		
		rendered = false;
	}

	public String preImport() {
		reinit();
		return "import.xhtml?faces-redirect=true";
	}

	public void upload(FileUploadEvent e) {
		try {

			String filePath = FacesContext.getCurrentInstance()
					.getExternalContext().getRealPath("/WEB-INF/temp/");

			File path = new File(filePath);
			if (!path.exists()) {
				path.mkdir();
			}
			nomeArquivo = e.getFile().getFileName();
			arquivo = new File(filePath + "/" + nomeArquivo);
			if (!arquivo.exists()) {
				arquivo.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(arquivo);
			fos.write(e.getFile().getContents());

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void listenerX(DragDropEvent event) {
		colunaRotulo = (String) event.getData();

	}

	public void listenerY(DragDropEvent event) {
		colunaValor = (String) event.getData();
	}

	public String continuar() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(arquivo);
			Workbook wb = new HSSFWorkbook(fis);
			sheets = new ArrayList<Sheet>();
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				Sheet sheet = new Sheet(i, wb.getSheetName(i),
						(HSSFSheet) wb.getSheetAt(i));
				sheets.add(sheet);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "importconfig.xhtml?faces-redirect=true";
	}

	public void gerarGrafico() {
		try {
			HSSFSheet sheet = planilhaEscolhida.getSheet();
			Iterator iter = sheet.rowIterator();
			ArrayList<DadosDoisEixos> dados = new ArrayList<DadosDoisEixos>();
			while (iter.hasNext()) {
				HSSFRow row = (HSSFRow) iter.next();
				Iterator cellIter = row.cellIterator();
				DadosDoisEixos dado = new DadosDoisEixos();
				boolean discard = false;				
				while (cellIter.hasNext()) {

					HSSFCell cell = (HSSFCell) cellIter.next();
					if(row.getRowNum() == sheet.getFirstRowNum()) {
						if (cell.getStringCellValue().equals(colunaRotulo)) {
							discard = true;
							colunaRotuloIndex = cell.getColumnIndex();
						} else if (cell.getStringCellValue().equals(colunaValor)) {
							discard = true;
							colunaValorIndex = cell.getColumnIndex();
						}
					}else {
						if (cell.getColumnIndex() == colunaRotuloIndex) {
							dado.setX(cell.getStringCellValue());
							
						} else if (cell.getColumnIndex() == colunaValorIndex) {
							if(cell.getCellType() == cell.CELL_TYPE_STRING)
								dado.setY(Double.parseDouble(cell.getStringCellValue()));
							else if(cell.getCellType() == cell.CELL_TYPE_NUMERIC){
								dado.setY(cell.getNumericCellValue());
							}
						}
					}
					 

				}
				
				if(!discard){
					dado.setNome("");
					dados.add(dado);
				}
					
			}
			JFreeChart jFreeChart = LineChartFactory.getChart("Gráfico "
					+ nomeArquivo.substring(0, nomeArquivo.lastIndexOf(".")),
					colunaRotulo, colunaValor, dados,CategoryLabelPositions.DOWN_90);
			jFreeChart.setBackgroundPaint(Color.white);
			jFreeChart.setBackgroundImageAlpha(Color.TRANSLUCENT);
			/*String filePath = FacesContext.getCurrentInstance()
					.getExternalContext().getRealPath("/WEB-INF/");
			File file = new File(filePath + "/grafico");
			if (!file.exists()) {

				file.createNewFile();

			}*/
			/*
			 * graficoDownload = new DefaultStreamedContent(new FileInputStream(
			 * file), "image/png", "GraficoDe"+stringTipo+"-"+titulo.hashCode()
			 * + ".png");
			 */
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ChartUtilities.writeChartAsPNG(baos, jFreeChart,1024,768);
			// ChartUtilities.saveChartAsPNG(file, jFreeChart, 650, 400);
			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());
			setGrafico(new DefaultStreamedContent(bais));
			rendered = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}

	public File getArquivo() {
		return arquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setSheets(ArrayList<Sheet> sheets) {
		this.sheets = sheets;
	}

	public ArrayList<Sheet> getSheets() {
		return sheets;
	}

	public void setPlanilhaEscolhida(Sheet planilhaEscolhida) {
		this.renderDadosPlanilhaEscolhida = true;
		this.planilhaEscolhida = planilhaEscolhida;
	}

	public Sheet getPlanilhaEscolhida() {
		return planilhaEscolhida;
	}

	public void setRenderDadosPlanilhaEscolhida(
			boolean renderDadosPlanilhaEscolhida) {
		this.renderDadosPlanilhaEscolhida = renderDadosPlanilhaEscolhida;
	}

	public boolean isRenderDadosPlanilhaEscolhida() {
		return renderDadosPlanilhaEscolhida;
	}

	public String getColunaRotulo() {
		return colunaRotulo;
	}

	public void setColunaRotulo(String colunaRotulo) {
		this.colunaRotulo = colunaRotulo;
	}

	public String getColunaValor() {
		return colunaValor;
	}

	public void setColunaValor(String colunaValor) {
		this.colunaValor = colunaValor;
	}

	public void setGrafico(StreamedContent grafico) {
		this.grafico = grafico;
	}

	public StreamedContent getGrafico() {
		return grafico;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public boolean isRendered() {
		return rendered;
	}

}
