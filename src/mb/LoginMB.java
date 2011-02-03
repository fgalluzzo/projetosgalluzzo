package mb;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import util.CriaHash;
import util.MessagesReader;
import util.PersistenceUtil;

import dao.UsuarioDao;

import modelo.Usuario;

@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginMB {
	private Usuario usuario;
	private UsuarioDao usuarioDao;
	private boolean logado;
	private String senhaAtual;
	private String novaSenha;
	private String confirmaNovaSenha;

	public LoginMB() {
		logado = false;
		usuario = new Usuario();
	}

	public String entrar() {
		try {
			usuarioDao = new UsuarioDao(PersistenceUtil.getEntityManager());
			usuario.setSenha(CriaHash.SHA1(usuario.getSenha()));
			usuario = usuarioDao.findByLoginSenha(usuario);
			logado = true;
			return "index";
		} catch (NoResultException e) {
			System.out.println("e");
			usuario.setApelido(null);
			usuario.setSenha(null);			
			FacesMessage message = new FacesMessage();
			message.setDetail(MessagesReader.getMessages().getProperty(
					"usuarioSenhaNaoConfere"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"usuarioSenhaNaoConfere"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, message);
			return null;
		} catch (Exception e1) {
			System.out.println("e1");
			FacesContext.getCurrentInstance().addMessage(
					"INFO",
					new FacesMessage(MessagesReader.getMessages().getProperty(
							"problemaSistema")));
			usuario.setApelido(null);
			usuario.setSenha(null);
			return null;
		}
	}
	public String alterarEmail(){
		FacesMessage message = new FacesMessage();
		try{
			usuarioDao.update(usuario);
			message.setDetail(MessagesReader.getMessages().getProperty(
			"emailAtualizado"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"emailAtualizado"));
			message.setSeverity(FacesMessage.SEVERITY_INFO);	
			
		} catch (Exception e) {
			message.setDetail(MessagesReader.getMessages().getProperty(
			"problemaSistema"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);	
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	public String alterarSenha(){
		FacesMessage message = new FacesMessage();
		try {
			if(!usuario.getSenha().equals(CriaHash.SHA1(senhaAtual))){				
				message.setDetail(MessagesReader.getMessages().getProperty(
						"senhaAtualNaoConfere"));
				message.setSummary(MessagesReader.getMessages().getProperty(
						"senhaAtualNaoConfere"));
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				
				
			}else if(!novaSenha.equals(confirmaNovaSenha)){				
				message.setDetail(MessagesReader.getMessages().getProperty(
						"senhasDevemSerIguais"));
				message.setSummary(MessagesReader.getMessages().getProperty(
						"senhasDevemSerIguais"));
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
					
			} else {
				usuario.setSenha(CriaHash.SHA1(novaSenha));
				usuarioDao.update(usuario);			
				message.setDetail(MessagesReader.getMessages().getProperty(
						"senhaAtualizada"));
				message.setSummary(MessagesReader.getMessages().getProperty(
						"senhaAtualizada"));
				message.setSeverity(FacesMessage.SEVERITY_INFO);
				
			}
			
		} catch (NoSuchAlgorithmException e) {
			message.setDetail(MessagesReader.getMessages().getProperty(
			"problemaSistema"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);			
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			message.setDetail(MessagesReader.getMessages().getProperty(
			"problemaSistema"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);	
			e.printStackTrace();
		} catch (Exception e) {
			message.setDetail(MessagesReader.getMessages().getProperty(
			"problemaSistema"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);	
			e.printStackTrace();
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	public String sair() {
		usuario = new Usuario();
		logado = false;
		return "login";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmaNovaSenha() {
		return confirmaNovaSenha;
	}

	public void setConfirmaNovaSenha(String confirmaNovaSenha) {
		this.confirmaNovaSenha = confirmaNovaSenha;
	}

}
