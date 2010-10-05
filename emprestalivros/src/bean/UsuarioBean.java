package bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class UsuarioBean {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="nome")
	private String nome; 
	
	@Column(name="apelido")
	private String apelido;
	
	@Column(name="senha")
	private String senha;
	
	@Column(name="data_nascimento")
	private Date dt_nascimento;
	
	private String st_nascimento;
	
	@Column(name="email")
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
	
	public String getSt_nascimento() {
		return st_nascimento;
	}
	
	public void setSt_nascimento(String st_nascimento) {
		this.st_nascimento = st_nascimento;
		SimpleDateFormat spf = new SimpleDateFormat("d/M/y");
		Date d = new Date();
		try {
			d = spf.parse(st_nascimento);
			this.setDt_nascimento(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
