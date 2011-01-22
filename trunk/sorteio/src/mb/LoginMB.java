package mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import modelo.Usuario;

@ManagedBean(name="loginMB")
@SessionScoped
public class LoginMB {
	private Usuario usuario;
	
	public LoginMB() {

		usuario = new Usuario();
	}
	
	public String entrar(){
		
		return null;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
