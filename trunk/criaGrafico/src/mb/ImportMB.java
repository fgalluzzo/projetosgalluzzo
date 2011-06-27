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

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import modelo.Coluna;
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
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import util.MessagesReader;

import factoryImpl.BarChartFactory;
import factoryImpl.LineChartFactory;
import factoryImpl.PieChartFactory;

@ManagedBean(name = "importMB")
@SessionScoped
public class ImportMB {
	private final int TAB_CONFIGURACOES = 0;
	private final int TAB_GRAFICO = 1;

	private File arquivo;
	private ArrayList<Sheet> sheets;
	private Sheet planilhaEscolhida;
	private boolean renderDadosPlanilhaEscolhida;
	private String nomeArquivo;
	private int colunaRotuloIndex;
	private int colunaValorIndex;
	private Coluna colunaRotulo;
	private Coluna colunaValor;
	private StreamedContent grafico;
	private boolean rendered;
	private UploadedFile file;
	private int activeTab;
	private int progress;
	private String cor;
	private Color color;
	private int tipo;
	private String stringTipo;
	private boolean enable3D;
	private boolean planilhaSemDados;

	private void reinit() {
		arquivo = null;
		sheets = new ArrayList<Sheet>();
		planilhaEscolhida = null;
		renderDadosPlanilhaEscolhida = false;
		nomeArquivo = null;
		colunaRotuloIndex = 0;
		colunaValorIndex = 0;
		colunaRotulo = new Coluna();
		colunaValor = new Coluna();
		rendered = false;
		activeTab = TAB_CONFIGURACOES;
		tipo=0;
		enable3D = false;
		color = null;
	}

	public String preImport() {
		reinit();
		return "import.xhtml?faces-redirect=true";
	}

	public void reset() {
		colunaRotuloIndex = 0;
		colunaValorIndex = 0;
		colunaRotulo = new Coluna();
		colunaValor = new Coluna();
		rendered = false;
		tipo=0;
		enable3D = false;
		color = null;

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
			continuar();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("importconfig.xhtml?faces-redirect=true");

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void listenerX(DragDropEvent event) {
		Coluna coluna = (Coluna) event.getData();
		colunaRotulo.setCabecalho(coluna.getCabecalho());

	}

	public void listenerY(DragDropEvent event) {
		Coluna coluna = (Coluna) event.getData();
		if (!coluna.getEscopo().equals("numerico")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					MessagesReader.getMessages()
							.getProperty("erroTipoNumerico"), MessagesReader
							.getMessages().getProperty("erroTipoNumerico"));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			colunaValor = new Coluna();
		} else
			colunaValor.setCabecalho(coluna.getCabecalho());
	}

	public void back(DragDropEvent event) {
		Coluna coluna = (Coluna) event.getData();
		if (colunaValor.getCabecalho() != null
				&& colunaValor.getCabecalho().equals(coluna.getCabecalho())) {
			colunaValor = new Coluna();
		} else if (colunaRotulo.getCabecalho() != null
				&& colunaRotulo.getCabecalho().equals(coluna.getCabecalho())) {
			colunaRotulo = new Coluna();
		}
	}
	public void tabChangedListener(TabChangeEvent event){
		activeTab = TAB_CONFIGURACOES;
	}
	public void continuar() {
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
		// return "importconfig.xhtml?faces-redirect=true";
	}

	public void gerarGrafico() {
		try {
			progress = 0;
			if (colunaRotulo.getCabecalho() != null
					&& colunaValor.getCabecalho() != null
					&& !colunaRotulo.getCabecalho().isEmpty()
					&& !colunaValor.getCabecalho().isEmpty()) {
				HSSFSheet sheet = planilhaEscolhida.getSheet();
				Iterator iter = sheet.rowIterator();
				ArrayList<DadosDoisEixos> dados = new ArrayList<DadosDoisEixos>();
				progress = 10;
				while (iter.hasNext()) {
					HSSFRow row = (HSSFRow) iter.next();
					Iterator cellIter = row.cellIterator();
					DadosDoisEixos dado = new DadosDoisEixos();
					boolean discard = false;
					while (cellIter.hasNext()) {

						HSSFCell cell = (HSSFCell) cellIter.next();
						if (row.getRowNum() == sheet.getFirstRowNum()) {
							if (cell.getStringCellValue().equals(
									colunaRotulo.getCabecalho())) {
								discard = true;
								colunaRotuloIndex = cell.getColumnIndex();
							} else if (cell.getStringCellValue().equals(
									colunaValor.getCabecalho())) {
								discard = true;
								colunaValorIndex = cell.getColumnIndex();
							}
						} else {
							if (cell.getColumnIndex() == colunaRotuloIndex) {
								if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
									dado.setX(String.valueOf(cell
											.getNumericCellValue()));
								} else if (cell.getCellType() == cell.CELL_TYPE_STRING) {
									dado.setX(cell.getStringCellValue());
								}

							} else if (cell.getColumnIndex() == colunaValorIndex) {
								if (cell.getCellType() == cell.CELL_TYPE_STRING)
									dado.setY(Double.parseDouble(cell
											.getStringCellValue()));
								else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
									dado.setY(cell.getNumericCellValue());
								}
							}
						}

					}

					if (!discard) {
						dado.setNome("");
						dados.add(dado);
					}

				}
				progress = 25;
				JFreeChart jFreeChart = null;
				switch (tipo) {
				case 0:
					if (!enable3D) {

						jFreeChart = BarChartFactory.getChart(
								"Gráfico "
										+ nomeArquivo.substring(0,
												nomeArquivo.lastIndexOf(".")),
								colunaRotulo.getCabecalho(),
								colunaValor.getCabecalho(), dados, color);
						stringTipo = "Barra";
					} else {
						jFreeChart = BarChartFactory.getChart3D(
								"Gráfico "
										+ nomeArquivo.substring(0,
												nomeArquivo.lastIndexOf(".")),
								colunaRotulo.getCabecalho(),
								colunaValor.getCabecalho(), dados, color);
						stringTipo = "Barra3d";
					}
					break;
				case 1:
					if (!enable3D) {
						jFreeChart = LineChartFactory.getChart(
								"Gráfico "
										+ nomeArquivo.substring(0,
												nomeArquivo.lastIndexOf(".")),
								colunaRotulo.getCabecalho(),
								colunaValor.getCabecalho(), dados,
								CategoryLabelPositions.STANDARD, color);
						stringTipo = "Linha";
					} else {
						jFreeChart = LineChartFactory.getChart3D(
								"Gráfico "
										+ nomeArquivo.substring(0,
												nomeArquivo.lastIndexOf(".")),
								colunaRotulo.getCabecalho(),
								colunaValor.getCabecalho(), dados,
								CategoryLabelPositions.STANDARD, color);
						stringTipo = "Linha3d";
					}
					break;
				case 2:
					if (!enable3D) {
						jFreeChart = PieChartFactory.getChart("Gráfico "
								+ nomeArquivo.substring(0,
										nomeArquivo.lastIndexOf(".")), dados);
						stringTipo = "Torta";
					} else {
						jFreeChart = PieChartFactory.getChart3D("Gráfico "
								+ nomeArquivo.substring(0,
										nomeArquivo.lastIndexOf(".")), dados);
						stringTipo = "Torta3d";
					}
					break;

				}
				
				jFreeChart.setBackgroundPaint(Color.white);
				jFreeChart.setBackgroundImageAlpha(Color.TRANSLUCENT);
				/*
				 * String filePath = FacesContext.getCurrentInstance()
				 * .getExternalContext().getRealPath("/WEB-INF/"); File file =
				 * new File(filePath + "/grafico"); if (!file.exists()) {
				 * 
				 * file.createNewFile();
				 * 
				 * }
				 */
				/*
				 * graficoDownload = new DefaultStreamedContent(new
				 * FileInputStream( file), "image/png",
				 * "GraficoDe"+stringTipo+"-"+titulo.hashCode() + ".png");
				 */
				progress = 50;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ChartUtilities.writeChartAsPNG(baos, jFreeChart, 750, 600);
				// ChartUtilities.saveChartAsPNG(file, jFreeChart, 650, 400);
				ByteArrayInputStream bais = new ByteArrayInputStream(
						baos.toByteArray());
				progress = 75;
				setGrafico(new DefaultStreamedContent(bais));
				rendered = true;
				setActiveTab(TAB_GRAFICO);
				progress = 100;
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
						MessagesReader.getMessages().getProperty(
								"naoPodeGerarGrafico"), MessagesReader
								.getMessages().getProperty(
										"naoPodeGerarGrafico"));
				FacesContext.getCurrentInstance().addMessage(null, msg);
				rendered = false;

			}

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
		if(planilhaEscolhida.getRows()>0){
			this.renderDadosPlanilhaEscolhida = true;
			this.planilhaSemDados = false;
		} else{
			this.renderDadosPlanilhaEscolhida = false;
			this.planilhaSemDados = true;
		}
			
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

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public UploadedFile getFile() {
		return file;
	}

	public Coluna getColunaRotulo() {
		return colunaRotulo;
	}

	public void setColunaRotulo(Coluna colunaRotulo) {
		this.colunaRotulo = colunaRotulo;
	}

	public Coluna getColunaValor() {
		return colunaValor;
	}

	public void setColunaValor(Coluna colunaValor) {
		this.colunaValor = colunaValor;
	}

	public void setActiveTab(int activeTab) {
		this.activeTab = activeTab;
	}

	public int getActiveTab() {
		return activeTab;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public int getProgress() {
		return progress;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getCor() {
		return cor;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getTipo() {
		return tipo;
	}

	public boolean isEnable3D() {
		return enable3D;
	}

	public void setEnable3D(boolean enable3d) {
		enable3D = enable3d;
	}

	public void setPlanilhaSemDados(boolean planilhaSemDados) {
		this.planilhaSemDados = planilhaSemDados;
	}

	public boolean isPlanilhaSemDados() {
		return planilhaSemDados;
	}

}
