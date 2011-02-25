package validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import util.MessagesReader;
import util.PersistenceUtil;

import dao.GrupoDao;

import modelo.Grupo;

public class CodigoGrupoValidator implements Validator {
	
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		GrupoDao grupoDao = new GrupoDao(PersistenceUtil.getEntityManager());
		String codigo = (String) value;
		if(!grupoDao.verificaCodigoExistente(codigo)) {
			FacesMessage message = new FacesMessage();
			message.setDetail(MessagesReader.getMessages().getProperty(
					"erroGrupoInexistente"));
			message.setSummary(MessagesReader.getMessages()
					.getProperty("erroGrupoInexistente"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			throw new ValidatorException(message);
		}
	}
}
