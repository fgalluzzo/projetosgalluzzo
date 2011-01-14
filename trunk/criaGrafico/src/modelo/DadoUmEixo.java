package modelo;

public class DadoUmEixo {
	private String nome;
	private Number valor;
	
	public DadoUmEixo() {
		// TODO Auto-generated constructor stub
	}
	public DadoUmEixo(String nome,Number valor) {
		this.nome = nome;
		this.valor = valor;
	}
	public String getNome() {
		return nome;
	}
	public Number getValor() {
		return valor;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setValor(Number valor) {
		this.valor = valor;
	}
	
}
