package mb;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import modelo.Grupo;
import modelo.Usuario;
import util.CriaHash;
import util.EnviaEmail;
import util.MessagesReader;
import util.PersistenceUtil;
import config.Config;
import dao.GrupoDao;
import dao.UsuarioDao;

@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginMB {
	private Usuario usuario;
	private UsuarioDao usuarioDao;
	private boolean logado;
	private boolean temGrupo;
	private String senhaAtual;
	private String novaSenha;
	private String confirmaNovaSenha;
	private String assunto;
	private String contato;
	private String nomeGrupo;

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
			if (usuario.getGrupo() != null) {
				temGrupo = true;
			} else {
				temGrupo = false;
			}
			return "index?faces-redirect=true";
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

	public String alterarEmail() {
		FacesMessage message = new FacesMessage();
		try {
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

	public String alterarSenha() {
		FacesMessage message = new FacesMessage();
		try {
			if (!usuario.getSenha().equals(CriaHash.SHA1(senhaAtual))) {
				message.setDetail(MessagesReader.getMessages().getProperty(
						"senhaAtualNaoConfere"));
				message.setSummary(MessagesReader.getMessages().getProperty(
						"senhaAtualNaoConfere"));
				message.setSeverity(FacesMessage.SEVERITY_ERROR);

			} else if (!novaSenha.equals(confirmaNovaSenha)) {
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
		senhaAtual = null;
		novaSenha = null;
		confirmaNovaSenha = null;
		return null;
	}

	public String preRecuperarSenha() {
		usuario = new Usuario();
		return "recuperarSenha?faces-redirect=true";
	}

	public void recuperarSenha() {
		FacesMessage message = new FacesMessage();
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			usuarioDao = new UsuarioDao(PersistenceUtil.getEntityManager());
			usuario = usuarioDao.findByUsuarioEmail(usuario);
			Random rand = new Random();

			String senhaNova = "sw" + ((Integer) rand.nextInt()).toString();
			usuario.setSenha(CriaHash.SHA1(senhaNova));
			usuarioDao.update(usuario);
			EnviaEmail.enviar(
					MessagesReader.getMessages().getProperty("recuperarSenha"),
					MessagesReader.getMessages().getProperty("novaSenhaMSG")
							+ " " + senhaNova, usuario.getEmail(),
					usuario.getNome());
			message.setDetail(MessagesReader.getMessages().getProperty(
					"emailSenhaEnviado"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"emailSenhaEnviado"));
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			context.addMessage(null, message);

		} catch (NoResultException e) {

			message.setDetail(MessagesReader.getMessages().getProperty(
					"usuarioInexistente"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"usuarioInexistente"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, message);
		} catch (Exception e) {
			message.setDetail(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, message);

		}
		usuario = new Usuario();
	}

	public String preRecuperarUsuario() {
		usuario = new Usuario();
		return "recuperarLogin?faces-redirect=true";
	}

	public void recuperaUsuario() {
		FacesMessage message = new FacesMessage();
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			usuarioDao = new UsuarioDao(PersistenceUtil.getEntityManager());
			usuario = usuarioDao.findByEmail(usuario);

			EnviaEmail.enviar(
					MessagesReader.getMessages()
							.getProperty("recuperarUsuario"), MessagesReader
							.getMessages().getProperty("seuUsuario")
							+ " "
							+ usuario.getApelido(), usuario.getEmail(), usuario
							.getNome());
			message.setDetail(MessagesReader.getMessages().getProperty(
					"emailUsuarioEnviado"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"emailUsuarioEnviado"));
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			context.addMessage(null, message);

		} catch (NoResultException e) {

			message.setDetail(MessagesReader.getMessages().getProperty(
					"usuarioInexistente"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"usuarioInexistente"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, message);
		} catch (Exception e) {
			message.setDetail(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, message);

		}
		usuario = new Usuario();
	}

	public String preContato() {
		assunto = new String();
		contato = new String();
		return "contato?faces-redirect=true";
	}

	public void contatar() {
		FacesMessage message = new FacesMessage();
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			contato += "\n Remetente: " + usuario.getNome() + "\n Email: "
					+ usuario.getEmail();
			EnviaEmail.enviar(assunto, contato, Config.EMAIL_ADM, Config.ADM);
			message.setDetail(MessagesReader.getMessages().getProperty(
					"emailContatoEnviado"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"emailContatoEnviado"));
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			context.addMessage(null, message);
		} catch (Exception e) {
			message.setDetail(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, message);
		}
	}

	public void criaGrupo() {
		GrupoDao grupoDao = new GrupoDao(PersistenceUtil.getEntityManager());
		FacesMessage message = new FacesMessage();
		String codigoGrupo;
		
		try {	
			codigoGrupo = CriaHash.SHA1(Math.random()
					+ nomeGrupo);
			usuario.setGrupo(new Grupo());
			usuario.getGrupo().setCodigo(codigoGrupo);
			grupoDao.createGrupo(usuario.getGrupo());
			temGrupo = true;
			message.setDetail(MessagesReader.getMessages().getProperty(
					"grupoCriadoSucesso") + codigoGrupo);
			message.setSummary(MessagesReader.getMessages().getProperty(
					"grupoCriadoSucesso") + codigoGrupo);
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			
		} catch (NoSuchAlgorithmException e) {
			message.setDetail(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			usuario.setGrupo(null);
		} catch (UnsupportedEncodingException e) {
			message.setDetail(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			usuario.setGrupo(null);
		} catch (Exception e) {
			message.setDetail(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSeverity(FacesMessage.SEVERITY_FATAL);
			usuario.setGrupo(null);
		}

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

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public boolean isTemGrupo() {
		return temGrupo;
	}

	public void setTemGrupo(boolean temGrupo) {
		this.temGrupo = temGrupo;
	}

	public String getNomeGrupo() {
		return nomeGrupo;
	}

	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}

}
