package validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import util.MessagesReader;
import util.PersistenceUtil;

import dao.UsuarioDao;

import modelo.Usuario;

public class UsuarioExistenteValidator implements Validator {
	@Override
	public void validate(FacesContext context, UIComponent component, Object object)
			throws ValidatorException {
		String apelido = (String) object;
		
		UsuarioDao usuarioDao = new UsuarioDao(PersistenceUtil.getEntityManager());
		
		if(usuarioDao.verificaLoginExistente(apelido)) {
			FacesMessage message = new FacesMessage();
			message.setDetail(MessagesReader.getMessages().getProperty(
					"erroUsuarioExistente"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"erroUsuarioExistente"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
		
		
		
	}

}
