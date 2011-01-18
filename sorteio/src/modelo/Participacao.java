package modelo;

import java.net.InetAddress;
import java.util.Calendar;

public class Participacao {
	private Calendar dataInscricao;
	private Participante participante;
	private InetAddress ip;
	private String numeroInscricao;
	
	public Participacao() {
		participante = new Participante();

	}
	public Calendar getDataInscricao() {
		return dataInscricao;
	}
	public void setDataInscricao(Calendar dataInscricao) {
		this.dataInscricao = dataInscricao;
	}
	public Participante getParticipante() {
		return participante;
	}
	public void setParticipante(Participante participante) {
		this.participante = participante;
	}
	public InetAddress getIp() {
		return ip;
	}
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}
	public String getNumeroInscricao() {
		return numeroInscricao;
	}
	public void setNumeroInscricao(String numeroInscricao) {
		this.numeroInscricao = numeroInscricao;
	}
	
	
}
