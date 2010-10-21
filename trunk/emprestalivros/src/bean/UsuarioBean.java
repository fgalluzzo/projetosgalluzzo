package bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class UsuarioBean {
	
	@Id	
	@SequenceGenerator(name="usuario_seq",sequenceName="usuario_seq")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="usuario_seq")	
	private Long id;
	
	@Column(name="nome")
	private String nome; 
	
	
	@Column(name="apelido",unique=true)	
	private String apelido;
	
	@Column(name="senha")
	private String senha;
	
	@Column(name="data_nascimento")
	private Date dt_nascimento;
	
	@Column(name="email",unique=true)
	private String email;

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

	public Date getDt_nascimento() {
		return dt_nascimento;
	}

	public void setDt_nascimento(Date dt_nascimento) {
		this.dt_nascimento = dt_nascimento;
	}
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
