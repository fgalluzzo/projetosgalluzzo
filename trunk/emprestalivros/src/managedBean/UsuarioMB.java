package managedBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import util.PersistenceUtil;

import bean.UsuarioBean;
import controller.MessagesController;
import dao.UsuarioDao;

@ManagedBean(name="usuario")
@RequestScoped
public class UsuarioMB {
	private UsuarioBean usuarioBean; 
	UsuarioDao usuarioDao;
	public UsuarioMB() {
		usuarioBean = new UsuarioBean();
		usuarioDao = new UsuarioDao(PersistenceUtil.getEntityManager());
	}
	
	
	
	public void cadastra(){
		try{
			usuarioDao.createUsuario(usuarioBean);
			 MessagesController.mensagemInsercaoSucesso("Usuário");
		}catch (Exception e) {
			// TODO: handle exception
			MessagesController.mensagemErroInsercao("Usuário");
		}
		
				
		
	}
	
	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}
	
	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}
}
