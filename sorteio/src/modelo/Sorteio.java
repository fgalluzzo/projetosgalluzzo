package modelo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

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
	
	@Transient
	private Date dataInicioD;
	
	@Column(name="dt_fim")
	private Calendar dataFim;
	
	@Transient
	private Date dataFimD;
	
	@Column(name="descricao")
	private String descricao;
	
	@Column(name="sorteado")	
	private boolean sorteado = false;
	
	@OneToMany(mappedBy="sorteio",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Participacao> participacoes;
	
	@ManyToOne(targetEntity=Grupo.class,fetch=FetchType.LAZY)
	private Grupo grupo;
	
	@OneToMany(targetEntity=Participante.class,fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private List<Participante> ganhadores;
	
	@Column(name="quantidadeGanhadores")
	private Integer quantidadeGanhadores;
	
	@Column(name="inscritos")
	private Integer inscritos;
	
	@Column(name="codigo",unique=true)
	private String codigo;
	
	@Transient
	private String sorteadoStr;
	
	@Transient
	private boolean temGanhadores;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
	public boolean isSorteado() {
		return sorteado;
	}
	public void setSorteado(boolean sorteado) {
		this.sorteado = sorteado;
	}
	public List<Participante> getGanhadores() {
		return ganhadores;
	}
	public void setGanhadores(List<Participante> ganhadores) {
		this.ganhadores = ganhadores;
	}
	public Integer getQuantidadeGanhadores() {
		return quantidadeGanhadores;
	}
	public void setQuantidadeGanhadores(Integer quantidadeGanhadores) {
		this.quantidadeGanhadores = quantidadeGanhadores;
	}
	public String getSorteadoStr() {
		return sorteado?"Sim":"Não";
	}
	public boolean isTemGanhadores() {
		if(ganhadores != null && !ganhadores.isEmpty()){
			return true;
		}
		return false;
	}
	public Calendar getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Calendar dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataInicioD() {
		if(dataInicio!=null)
			return dataInicio.getTime();
		else
			return null;
	}
	public void setDataInicioD(Date dataInicioD) {	
		if(this.dataInicio ==null) {
			this.dataInicio = new GregorianCalendar();
			
		} 
		this.dataInicio.setTime(dataInicioD);
	}
	public Calendar getDataFim() {	
		return dataFim;
	}
	public void setDataFim(Calendar dataFim) {
		this.dataFim = dataFim;
	}
	public Date getDataFimD() {
		if(dataFim != null)
			return dataFim.getTime();
		else
			return null;
	}
	public void setDataFimD(Date dataFimD) {
		if(this.dataFim == null) {
			this.dataFim = new GregorianCalendar();
		}
		this.dataFim.setTime(dataFimD);
	}

	public Integer getInscritos() {
		return inscritos;
	}
	public void setInscritos(Integer inscritos) {
		this.inscritos = inscritos;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	
}
