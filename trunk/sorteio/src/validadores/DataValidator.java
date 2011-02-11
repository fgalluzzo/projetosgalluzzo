package validadores;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.richfaces.component.UICalendar;
import org.richfaces.model.CalendarDataModel;

import util.MessagesReader;

public class DataValidator implements Validator {
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		UIComponent cPai = component.getParent();

		List<UIComponent> filhos = cPai.getChildren();
		for (UIComponent uiComponent : filhos) {
			if (uiComponent.getClientId().equals("dtInicio")) {
				UICalendar cal = (UICalendar) uiComponent;
				Date dtFim = (Date) value;
				Date dtInicio = (Date) cal.getValue();
				if (dtInicio.after(dtFim)) {
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
