package validadores;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.inputmask.InputMask;
import org.primefaces.component.inputtext.InputText;

import util.MessagesReader;

public class DataValidator implements Validator {
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		UIComponent cPai = component.getParent();

		List<UIComponent> filhos = cPai.getChildren();
		Date dtFim = (Date) value;
		Date dtInicio = null;
		String hrInicio = null;
		String hrFim = null;
		for (UIComponent uiComponent : filhos) {
			
			if (uiComponent.getClientId().equals("dtInicio")) {
				Calendar cal = (Calendar) uiComponent;				
				dtInicio = (Date) cal.getValue();				
					
			} else if(uiComponent.getClientId().equals("hrInicio")) {
				hrInicio = (String) ((InputMask)uiComponent).getValue();
			} else if(uiComponent.getClientId().equals("hrFim")) {
				hrFim = (String) ((InputMask)uiComponent).getValue();
			}
		}
		if(dtInicio!=null && !hrInicio.equals("") &&  !hrFim.equals("")) {
			
			int hrIn = Integer.parseInt(hrInicio.substring(0,2));
			int minIn = Integer.parseInt(hrInicio.substring(3));
			
			int hrF = Integer.parseInt(hrFim.substring(0,2));
			int minF = Integer.parseInt(hrFim.substring(3));
			
			if (dtInicio.after(dtFim)) {
				FacesMessage message = new FacesMessage();
				message.setDetail(MessagesReader.getMessages().getProperty(
						"erroDataMaior"));
				message.setSummary(MessagesReader.getMessages()
						.getProperty("erroDataMaior"));
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				
				throw new ValidatorException(message);
			} else if(dtInicio.equals(dtFim)) {
				if(hrIn > hrF) {
					FacesMessage message = new FacesMessage();
					message.setDetail(MessagesReader.getMessages().getProperty(
							"erroDataMaior"));
					message.setSummary(MessagesReader.getMessages()
							.getProperty("erroDataMaior"));
					message.setSeverity(FacesMessage.SEVERITY_ERROR);
					
					throw new ValidatorException(message);
				} else if(hrIn == hrF && minIn > minF) {
					FacesMessage message = new FacesMessage();
					message.setDetail(MessagesReader.getMessages().getProperty(
							"erroDataMaior"));
					message.setSummary(MessagesReader.getMessages()
							.getProperty("erroDataMaior"));
					message.setSeverity(FacesMessage.SEVERITY_ERROR);
					
					throw new ValidatorException(message);
				}
			}
		}

	}
}
