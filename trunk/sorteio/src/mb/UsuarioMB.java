package mb;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import modelo.Grupo;
import modelo.Usuario;
import util.CriaHash;
import util.MessagesReader;
import util.PersistenceUtil;
import dao.GrupoDao;
import dao.UsuarioDao;

@ManagedBean(name = "usuarioMB")
@SessionScoped
public class UsuarioMB {
	private Usuario usuario;
	private TimeZone timeZone = TimeZone.getTimeZone("America/Sao_Paulo");
	private String codigoGrupo;

	public UsuarioMB() {
		usuario = new Usuario();
		usuario.setGrupo(new Grupo());
	}

	public String preCadast() {
		usuario = new Usuario();
		usuario.setGrupo(new Grupo());
		return "cadastro?faces-redirect=true";
	}

	public void cadastrar() {
		GrupoDao grupoDao = new GrupoDao(PersistenceUtil.getEntityManager());
		UsuarioDao usuarioDao = new UsuarioDao(
				PersistenceUtil.getEntityManager());
		FacesMessage message = new FacesMessage();
		try {
			if (codigoGrupo != null && !codigoGrupo.trim().equals("")) {
				Grupo grupo = grupoDao.findByCodigo(codigoGrupo);
				usuario.setGrupo(grupo);
			} else if(usuario.getGrupo().getNome() != null && !usuario.getGrupo().getNome().trim().equals("")){
				String codigoGrupo;

				codigoGrupo = CriaHash.SHA1(Math.random()+usuario.getGrupo().getNome());
				usuario.getGrupo().setCodigo(codigoGrupo);
				grupoDao.createGrupo(usuario.getGrupo());
			} else {
				usuario.setGrupo(null);
			}
			usuario.setSenha(CriaHash.SHA1(usuario.getSenha()));
			usuarioDao.create(usuario);
			message.setDetail(MessagesReader.getMessages().getProperty(
					"usuarioCriadoSucesso"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"usuarioCriadoSucesso"));
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			usuario = new Usuario();
			codigoGrupo = new String();
		} catch (NoSuchAlgorithmException e) {
			message.setDetail(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		} catch (UnsupportedEncodingException e) {
			message.setDetail(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		} catch (Exception e) {
			message.setDetail(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"problemaSistema"));
			message.setSeverity(FacesMessage.SEVERITY_FATAL);
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
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

	public String getCodigoGrupo() {
		return codigoGrupo;
	}

	public void setCodigoGrupo(String codigoGrupo) {
		this.codigoGrupo = codigoGrupo;
	}
}
