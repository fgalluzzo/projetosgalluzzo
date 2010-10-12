package managedBean;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.NoResultException;

import controller.MessagesController;

import util.CriaHash;
import util.MessagesReader;
import util.PersistenceUtil;

import dao.UsuarioDao;

import bean.UsuarioBean;

@ManagedBean(name="logado")
@SessionScoped
public class UsuarioLogadoMB {

	private UsuarioBean usuario;
	private UsuarioDao usuarioDao;
	
	public UsuarioLogadoMB(){
		usuario = new UsuarioBean();		
	}
	public String entrar(){
		try {
			usuarioDao = new UsuarioDao(PersistenceUtil.getEntityManager());
			UsuarioBean usuarioBD = usuarioDao.findByLoginSenha(usuario.getApelido(), CriaHash.SHA1(usuario.getSenha()));
			usuario = usuarioBD;
			return "/index.jsf?faces-redirect=true";
		} catch (NoResultException e) {
			MessagesController.mensagemWarn(MessagesReader.getMessages().getProperty("alerta.loginsenha"), usuario.getApelido());
		} catch (NoSuchAlgorithmException e) {
			MessagesController.mensagemErro(MessagesReader.getMessages().getProperty("erro.sistema"));
		} catch (UnsupportedEncodingException e) {
			MessagesController.mensagemErro(MessagesReader.getMessages().getProperty("erro.sistema"));
		}
		usuario.setApelido(null);
		usuario.setSenha(null);
		return null;
		
		
							
	}
	public UsuarioBean getUsuario() {
		return usuario;
	}
	
	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
	}
}
