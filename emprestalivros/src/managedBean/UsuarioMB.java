package managedBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.hibernate.exception.ConstraintViolationException;

import util.CriaHash;
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
		
	}
	
	
	
	public void cadastra(){
		try{
			usuarioDao = new UsuarioDao(PersistenceUtil.getEntityManager());
			usuarioBean.setSenha(CriaHash.SHA1(usuarioBean.getSenha()));
			usuarioDao.createUsuario(usuarioBean);
			MessagesController.mensagemInsercaoSucesso("Usu�rio "+usuarioBean.getApelido());
			usuarioBean = null;
			this.st_nascimento = null;
		}catch (ConstraintViolationException e) {
			MessagesController.mensagemInsercaoLoginDup(e.getMessage(), e.getConstraintName());
			usuarioBean.setSenha("");
		}
		catch (Exception e) {			
			MessagesController.mensagemErroInsercao("Usu�rio");
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
			e.printStackTrace();
		}
		
	}
}
