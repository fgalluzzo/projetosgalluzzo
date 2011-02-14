package validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import util.MessagesReader;

public class HoraValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		String hora = (String) value;
		
		if(!hora.matches("([1-9]|[0-1][0-9]|2[0-3]):([0-5][0-9])")) {
			FacesMessage message = new FacesMessage();
			message.setDetail(MessagesReader.getMessages().getProperty(
					"horaInvalida"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"horaInvalida"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
		
		
	}
	
}
