package util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import controller.MessagesController;

@ManagedBean(name = "validator")
@RequestScoped
public class ValidatorUtil {

	public void validaAno(FacesContext context, UIComponent toValidate,
			Object value) {
		Date date = new Date();

		Calendar cal = new GregorianCalendar();

		cal.setTime(date);
		
		Integer ano = (Integer) value;
		((UIInput) toValidate).setValid(false);
		if (ano.compareTo(cal.get(Calendar.YEAR)) > 0) {
			MessagesController.mensagemErroCampo(toValidate.getClientId(),MessagesReader.getMessages()
					.getProperty("erro.ano.maior"));
		}
	}
}
