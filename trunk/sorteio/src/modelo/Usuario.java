package modelo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="usuario")
public class Usuario {
	@Id
	@SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "usuario_seq")
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "apelido", unique = true)
	private String apelido;

	@Column(name = "senha")
	private String senha;

	@Column(name = "data_nascimento")
	private Calendar dt_nascimento;
	
	@Transient
	private Date dt_nascimentoD;

	@Column(name = "email", unique = true)
	private String email;
	
	@ManyToOne(targetEntity=Grupo.class,fetch=FetchType.LAZY)
	private Grupo grupo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Calendar getDt_nascimento() {
		return dt_nascimento;
	}

	public void setDt_nascimento(Calendar dt_nascimento) {
		this.dt_nascimento = dt_nascimento;
	}
	public Date getDt_nascimentoD() {
		if(dt_nascimento!= null ) {
			return dt_nascimento.getTime();
		} else
			return null;
	}
	public void setDt_nascimentoD(Date dt_nascimentoD) {
		if(this.dt_nascimento ==null) {
			this.dt_nascimento = new GregorianCalendar();
			
		} 
		this.dt_nascimento.setTime(dt_nascimentoD);
	}
}
