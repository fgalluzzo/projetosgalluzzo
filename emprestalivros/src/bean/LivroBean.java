package bean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="livro")
public class LivroBean {
	
	@Id	
	@SequenceGenerator(name="livro_seq",sequenceName="livro_seq")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="livro_seq")	
	private Long id;
	
	@Column(name="nome")
	private String nome; 
	
	@Column(name="isbn")
	private String isbn;
	
	@Column(name="origem")
	private String origem;
	
	@Column(name="ano")
	private Integer ano;
	
	@Column(name="edicao")
	private String edicao;
	
	@Column(name="paginas")
	private Integer paginas;
	
	@Column(name="acabamento")
	private String acabamento;
	
	@Column(name="formato")
	private String formato;
	
	@Column(name="imagem")
	private byte[] imagem;
	
	@Column(name="descricao")
	private String descricao;
	
	@ManyToMany(targetEntity=UsuarioBean.class,fetch=FetchType.LAZY )
	@JoinTable(name="livros_usuario", 
			joinColumns=@JoinColumn(name="livro_fk"),
	        inverseJoinColumns=@JoinColumn(name="usuario_fk"))
	private Set<UsuarioBean> donos;

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

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getEdicao() {
		return edicao;
	}

	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}

	public Integer getPaginas() {
		return paginas;
	}

	public void setPaginas(Integer paginas) {
		this.paginas = paginas;
	}

	public String getAcabamento() {
		return acabamento;
	}

	public void setAcabamento(String acabamento) {
		this.acabamento = acabamento;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}		

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Set<UsuarioBean> getDonos() {
		return donos;
	}

	public void setDonos(Set<UsuarioBean> donos) {
		this.donos = donos;
	}
	
	
	
	
}
