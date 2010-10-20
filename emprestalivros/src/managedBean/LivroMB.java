package managedBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import controller.MessagesController;

import dao.LivroDao;

import util.CriaHash;
import util.MessagesReader;
import util.PersistenceUtil;

import bean.LivroBean;

@ManagedBean(name = "livroMB")
@SessionScoped
public class LivroMB {

	LivroDao livroDao;
	private LivroBean livro;	
	private StreamedContent imagem;
	
	
	public LivroMB() {
		livro = new LivroBean();
		livroDao = new LivroDao(PersistenceUtil.getEntityManager());
	}

	public void cadastrar() {
		try{			
			livroDao.createLivro(livro);
			MessagesController.mensagemOK(MessagesReader
					.getMessages().getProperty(
							"alerta.insercao.sucesso"),MessagesReader
					.getMessages().getProperty(
							"novolivro"));
			livro = null;
			this.imagem = null;
		}catch (ConstraintViolationException e) {
			MessagesController.mensagemInsercaoLoginDup(e.getMessage(), e.getConstraintName());			
		}
		catch (Exception e) {
			// TODO: handle exception
			MessagesController.mensagemErro(MessagesReader
					.getMessages().getProperty(
					"alerta.insercao.erro"),MessagesReader
					.getMessages().getProperty(
					"livro"));			
		}
	}
	
	public void handleFileUpload(FileUploadEvent event) {
	    
		try {
			this.imagem = new DefaultStreamedContent(event.getFile()
					.getInputstream(), event.getFile().getContentType(), event
					.getFile().getFileName());
			this.livro.setImagem(event.getFile().getContents());
		} catch (IOException e) {
			FacesMessage msg = new FacesMessage(MessagesReader.getMessages().getProperty("erro"), event.getFile()
					.getFileName() + " " +MessagesReader.getMessages().getProperty("problemaCarregarArquivo"));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		FacesMessage msg = new FacesMessage(MessagesReader.getMessages().getProperty("sucesso"), event.getFile()
				.getFileName() + " " +MessagesReader.getMessages().getProperty("carregado"));
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
}
