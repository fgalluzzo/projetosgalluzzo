package modelo;

import java.util.ArrayList;
import java.util.Calendar;

public class Sorteio {
	private String nome;
	private Calendar dataInicio;
	private Calendar dataFim;
	private String descricao;
	private ArrayList<Participacao> participacoes;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Calendar getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Calendar dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Calendar getDataFim() {
		return dataFim;
	}
	public void setDataFim(Calendar dataFim) {
		this.dataFim = dataFim;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public ArrayList<Participacao> getParticipacoes() {
		return participacoes;
	}
	public void setParticipacoes(ArrayList<Participacao> participacoes) {
		this.participacoes = participacoes;
	}
	
	
}
