package mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.NoResultException;

import util.CriaHash;
import util.PersistenceUtil;

import dao.UsuarioDao;

import modelo.Usuario;

@ManagedBean(name="loginMB")
@SessionScoped
public class LoginMB {
	private Usuario usuario;
	private UsuarioDao usuarioDao;
	private boolean logado;
	
	public LoginMB() {
		logado = false;
		usuario = new Usuario();
	}
	
	
	public String entrar(){
		try{
			usuarioDao = new UsuarioDao(PersistenceUtil.getEntityManager());
			usuario.setSenha(CriaHash.SHA1(usuario.getSenha()));
			usuario = usuarioDao.findByLoginSenha(usuario);
			logado = true;
			return "index";
		} catch(NoResultException e) {
			System.out.println("e");
			usuario.setApelido(null);
			usuario.setSenha(null);
			return null;
		} catch(Exception e1) {
			System.out.println("e1");
			usuario.setApelido(null);
			usuario.setSenha(null);
			return null;
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

}
