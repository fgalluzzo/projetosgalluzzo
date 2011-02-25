package mb;

import java.util.TimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import modelo.Usuario;

@ManagedBean(name="usuarioMB")
@SessionScoped
public class UsuarioMB {
	private Usuario usuario;
	private TimeZone timeZone = TimeZone.getTimeZone("America/Sao_Paulo");

	public UsuarioMB() {
		usuario = new Usuario();
	}
	public String preCadast() {
		usuario = new Usuario();
		return "cadastro?faces-redirect=true";
	}
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}
}
