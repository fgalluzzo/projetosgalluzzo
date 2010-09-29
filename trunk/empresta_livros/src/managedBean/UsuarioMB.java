package managedBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import bean.UsuarioBean;

@ManagedBean(name="usuario")
@RequestScoped
public class UsuarioMB {
	private UsuarioBean usuarioBean; 
	
	public UsuarioMB() {
		usuarioBean = new UsuarioBean();
	
	}
	
	
	
	public void cadastra(){
		
		System.out.println(getUsuarioBean().getNome());
	}
	
	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}
	
	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}
}
