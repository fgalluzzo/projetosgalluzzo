package modelo;

import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class Sheet {
	private int index;
	private String nome;
	private HSSFSheet sheet;
	private int rows;
	private ArrayList<String> header;	
	
	public Sheet(int index,String nome,HSSFSheet sheet) {
		this.index = index;
		this.nome = nome;
		this.sheet = sheet;
	}
	
	public ArrayList<String> getHeader(){
		HSSFRow row =  sheet.getRow(sheet.getFirstRowNum());
		ArrayList<String> colunas  = new ArrayList<String>();
		for(int i = 0;i< row.getLastCellNum();i++){
			String cabecalho = row.getCell(i).getStringCellValue();
			colunas.add(cabecalho);
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
