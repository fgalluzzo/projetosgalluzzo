package modelo;

import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class Sheet {
	private int index;
	private String nome;
	private HSSFSheet sheet;
	private int rows;
	private ArrayList<Coluna> header;	
	
	public Sheet(int index,String nome,HSSFSheet sheet) {
		this.index = index;
		this.nome = nome;
		this.sheet = sheet;
	}
	
	public ArrayList<Coluna> getHeader(){
		HSSFRow row =  sheet.getRow(sheet.getFirstRowNum());
		ArrayList<Coluna> colunas  = new ArrayList<Coluna>();
		if(row.getCell(0) == null) {
			return null;
		}
		for(int i = 0;i< row.getLastCellNum();i++){
			Coluna cabecalho = new Coluna();
			cabecalho.setCabecalho(row.getCell(i).getStringCellValue());
			cabecalho.setIndex(row.getCell(i).getColumnIndex());
			
			colunas.add(cabecalho);
		}
		row = sheet.getRow(sheet.getFirstRowNum()+1);
		for (int i = 0; i < colunas.size(); i++) {
			for(int j=0;j<row.getLastCellNum();j++){
				HSSFCell cell = row.getCell(j);
				if(cell.getColumnIndex() == colunas.get(i).getIndex()){
					if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
						colunas.get(i).setEscopo("numerico");
					}else {
						String valor = cell.getStringCellValue();
						try{
							Double d = Double.parseDouble(valor);
							colunas.get(i).setEscopo("numerico");
						} catch(NumberFormatException e) {
							colunas.get(i).setEscopo("texto");
						}
						
					}
				}
				
			}
		}
		return colunas;
		
	}
	public int getRows(){
		return sheet.getLastRowNum();
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}
	public HSSFSheet getSheet() {
		return sheet;
	}
}
