package modelo;

public class DadoUmEixo {
	private String nome;
	private Double valor;
	
	public DadoUmEixo() {
		// TODO Auto-generated constructor stub
	}
	public DadoUmEixo(String nome,Double valor) {
		this.nome = nome;
		this.valor = valor;
	}
	public String getNome() {
		return nome;
	}
	public Double getValor() {
		return valor;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
}
