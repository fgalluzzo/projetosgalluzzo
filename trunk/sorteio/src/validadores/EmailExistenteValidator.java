package validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.NoResultException;

import modelo.Usuario;

import util.MessagesReader;
import util.PersistenceUtil;

import dao.AbstractDao;
import dao.UsuarioDao;

public class EmailExistenteValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent uiComponent, Object object)
			throws ValidatorException {
		String enteredEmail = (String) object;
		// Set the email pattern string
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

		// Match the given string with the pattern
		Matcher m = p.matcher(enteredEmail);

		// Check whether match is found
		boolean matchFound = m.matches();

		if (!matchFound) {
			FacesMessage message = new FacesMessage();
			message.setDetail(MessagesReader.getMessages().getProperty(
					"emailInvalido"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"emailInvalido"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
		UsuarioDao usuarioDao = new UsuarioDao(PersistenceUtil.getEntityManager());
		Usuario usuario = new Usuario();
		usuario.setEmail(enteredEmail);
		try {
			usuario = usuarioDao.findByEmail(usuario);
			FacesMessage message = new FacesMessage();
			message.setDetail(MessagesReader.getMessages().getProperty(
					"emailExistente"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"emailExistente"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		} catch (NoResultException e) {
			return;
		} 
		
		
		
		
	}
	
}
