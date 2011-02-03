package modelo;

import java.net.InetAddress;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="participacao")
public class Participacao {
	@Id	
	@SequenceGenerator(name="participacao_seq",sequenceName="participacao_seq")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="participacao_seq")	
	private Long id;
	
	@Column(name="dt_inscricao")
	private Calendar dataInscricao;
	
	@OneToOne(targetEntity=Participante.class,fetch=FetchType.LAZY)
	private Participante participante;
	
	@Column(name="endereco_ip")
	private String ip;
	
	@Column(name="inscricao")
	private String numeroInscricao;
	
	@ManyToOne(targetEntity=Sorteio.class,fetch=FetchType.LAZY)
	private Sorteio sorteio;
	
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getNumeroInscricao() {
		return numeroInscricao;
	}
	public void setNumeroInscricao(String numeroInscricao) {
		this.numeroInscricao = numeroInscricao;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Sorteio getSorteio() {
		return sorteio;
	}
	public void setSorteio(Sorteio sorteio) {
		this.sorteio = sorteio;
	}
	
	
}
