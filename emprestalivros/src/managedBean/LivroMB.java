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

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;

import util.MessagesReader;

import bean.LivroBean;

@ManagedBean(name = "livroMB")
@SessionScoped
public class LivroMB {

	private LivroBean livro;

	public LivroMB() {
		livro = new LivroBean();
	}

	public void cadastrar() {
		System.out.println(livro.getImagem().getName());
	}

	public LivroBean getLivro() {
		return livro;
	}

	public void setLivro(LivroBean livro) {
		this.livro = livro;
	}

	public void handleFileUpload(FileUploadEvent event) {

		try {
			this.livro.setImagem(new DefaultStreamedContent(event.getFile()
					.getInputstream(), event.getFile().getContentType(), event
					.getFile().getFileName()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		FacesMessage msg = new FacesMessage(MessagesReader.getMessages().getProperty("sucesso"), event.getFile()
				.getFileName() + " " +MessagesReader.getMessages().getProperty("carregado"));
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}
}
