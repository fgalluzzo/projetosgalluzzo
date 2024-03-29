package managedBean;

import java.io.IOException;
import java.util.TreeSet;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Hibernate;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import util.MessagesReader;
import util.PersistenceUtil;
import bean.LivroBean;
import bean.UsuarioBean;
import controller.MessagesController;
import dao.LivroDao;

@ManagedBean(name = "livroMB")
@SessionScoped
public class LivroMB {

	LivroDao livroDao;
	private LivroBean livro;
	private StreamedContent imagem;
	private String imagemNome;
	private Boolean temLivro;

	public LivroMB() {
		livro = new LivroBean();

	}

	public void cadastrar() {
		try {
			if (temLivro) {
				FacesContext context = FacesContext.getCurrentInstance();
				Application app = context.getApplication();

				ValueExpression expression = app.getExpressionFactory()
						.createValueExpression(context.getELContext(),
								String.format("#{%s}", "logado"), Object.class);
				UsuarioLogadoMB donoMB = (UsuarioLogadoMB) expression.getValue(context
						.getELContext());
				
				UsuarioBean dono = donoMB.getUsuario();

				Hibernate.initialize(livro.getDonos());
				if (livro.getDonos() == null) {
					livro.setDonos(new TreeSet<UsuarioBean>());
				}
				livro.getDonos().add(dono);

			}
			livroDao = new LivroDao(PersistenceUtil.getEntityManager());
			LivroBean livroExistente = livroDao.findByISBN(livro.getIsbn());
			if (livroExistente == null) {
				livroDao.createLivro(livro);
				MessagesController.mensagemOK(MessagesReader.getMessages()
						.getProperty("alerta.insercao.sucesso"), MessagesReader
						.getMessages().getProperty("novolivro"));
				this.livro = new LivroBean();
				this.imagem = null;
				this.imagemNome = null;
			}

			else {
				MessagesController.mensagemErro(MessagesReader.getMessages()
						.getProperty("alerta.isbnUnico"));
			}

		} catch (Exception e) {
			MessagesController.mensagemErro(MessagesReader.getMessages()
					.getProperty("alerta.insercao.erro"), MessagesReader
					.getMessages().getProperty("livro"));
		}
	}

	public void handleFileUpload(FileUploadEvent event) {

		try {
			this.imagem = new DefaultStreamedContent(event.getFile()
					.getInputstream(), event.getFile().getContentType(), event
					.getFile().getFileName());
			this.imagemNome = event.getFile().getFileName();
			this.livro.setImagem(event.getFile().getContents());
		} catch (IOException e) {
			FacesMessage msg = new FacesMessage(MessagesReader.getMessages()
					.getProperty("erro"), event.getFile().getFileName()
					+ " "
					+ MessagesReader.getMessages().getProperty(
							"problemaCarregarArquivo"));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		FacesMessage msg = new FacesMessage(MessagesReader.getMessages()
				.getProperty("sucesso"), event.getFile().getFileName() + " "
				+ MessagesReader.getMessages().getProperty("carregado"));
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public LivroBean getLivro() {
		return livro;
	}

	public void setLivro(LivroBean livro) {
		this.livro = livro;
	}

	public StreamedContent getImagem() {
		return imagem;
	}

	public void setImagem(StreamedContent imagem) {
		this.imagem = imagem;
	}

	public String getImagemNome() {
		return imagemNome;
	}

	public void setImagemNome(String imagemNome) {
		this.imagemNome = imagemNome;
	}

	public Boolean getTemLivro() {
		return temLivro;
	}

	public void setTemLivro(Boolean temLivro) {
		this.temLivro = temLivro;
	}
}
