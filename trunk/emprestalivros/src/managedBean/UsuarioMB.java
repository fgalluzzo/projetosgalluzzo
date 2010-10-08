package managedBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.hibernate.exception.ConstraintViolationException;

import util.PersistenceUtil;

import bean.UsuarioBean;
import controller.MessagesController;
import dao.UsuarioDao;

@ManagedBean(name="usuario")
@RequestScoped
public class UsuarioMB {
	private UsuarioBean usuarioBean; 
	UsuarioDao usuarioDao;
	private String st_nascimento;
	public UsuarioMB() {
		usuarioBean = new UsuarioBean();
		usuarioDao = new UsuarioDao(PersistenceUtil.getEntityManager());
	}
	
	
	
	public void cadastra(){
		try{
			usuarioDao.createUsuario(usuarioBean);
			MessagesController.mensagemInsercaoSucesso("Usuário "+usuarioBean.getApelido());
			usuarioBean = null;
			this.st_nascimento = null;
		}catch (ConstraintViolationException e) {
			MessagesController.mensagemInsercaoLoginDup(e.getMessage(), e.getConstraintName());
			usuarioBean.setSenha("");
		}
		catch (Exception e) {
			// TODO: handle exception
			MessagesController.mensagemErroInsercao("Usuário");
			usuarioBean.setSenha("");
		}
		
				
		
	}
	
	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}
	
	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}
	public String getSt_nascimento() {
		return st_nascimento;
	}
	
	public void setSt_nascimento(String st_nascimento) {
		this.st_nascimento = st_nascimento;
		SimpleDateFormat spf = new SimpleDateFormat("d/M/y");
		Date d = new Date();
		try {
			d = spf.parse(st_nascimento);
			getUsuarioBean().setDt_nascimento(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
