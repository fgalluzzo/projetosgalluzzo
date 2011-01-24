package modelo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="sorteio")
public class Sorteio {
	
	@Id	
	@SequenceGenerator(name="sorteio_seq",sequenceName="sorteio_seq")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="sorteio_seq")	
	private Long id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="dt_inicio")
	private Calendar dataInicio;
	
	@Column(name="dt_fim")
	private Calendar dataFim;
	
	@Column(name="descricao")
	private String descricao;
	
	@OneToMany(mappedBy="sorteio")
	private List<Participacao> participacoes;
	
	@ManyToOne(targetEntity=Grupo.class,fetch=FetchType.LAZY)
	private Grupo grupo;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataInicio() {
		if(dataInicio != null) {
			return dataInicio.getTime();
		}else{
			return null;
		}
			
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = new GregorianCalendar();
		this.dataInicio.setTime(dataInicio);
	}
	public Date getDataFim() {
		if(dataFim != null){
			return dataFim.getTime();
		}else {
			return null;
		}
			
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = new GregorianCalendar();
		this.dataFim.setTime(dataFim);
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<Participacao> getParticipacoes() {
		return participacoes;
	}
	public void setParticipacoes(List<Participacao> participacoes) {
		this.participacoes = participacoes;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Calendar getDataInicioCal(){
		return this.dataInicio;
	}
	
	public Calendar getDataFimCal() {
		return this.dataFim;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	
}
