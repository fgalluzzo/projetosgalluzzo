package modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="grupo")
public class Grupo {
	
	@Id	
	@SequenceGenerator(name="grupo_seq",sequenceName="grupo_seq")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="grupo_seq")	
	private Long id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="codigo",unique=true)
	private String codigo;
	
	@OneToMany(mappedBy="grupo")
	private List<Usuario> usuarios;
	
	@OneToMany(mappedBy="grupo")
	private List<Sorteio> sorteios;

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

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Sorteio> getSorteios() {
		return sorteios;
	}

	public void setSorteios(List<Sorteio> sorteios) {
		this.sorteios = sorteios;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	
}
