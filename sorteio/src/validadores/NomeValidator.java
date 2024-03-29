package validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import util.MessagesReader;

public class NomeValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object object)
			throws ValidatorException {
		String nome = (String) object;
		
		if(nome.length() < 3){
			FacesMessage message = new FacesMessage();
			message.setDetail(MessagesReader.getMessages().getProperty(
					"validaNomePequeno"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"validaNomePequeno"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
		Pattern p = Pattern.compile("^[a-zA-Z]+[\\sa-zA-Z]+$");
		// Match the given string with the pattern
		Matcher m = p.matcher(nome);

		// Check whether match is found
		boolean matchFound = m.matches();
		
		if(!matchFound){
			FacesMessage message = new FacesMessage();
			message.setDetail(MessagesReader.getMessages().getProperty(
					"validaNomeLetras"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"validaNomeLetras"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
		
	}

}
