package validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import util.MessagesReader;
import util.PersistenceUtil;
import dao.GrupoDao;

public class GrupoExistenteValidator implements Validator {

	public void validate(FacesContext context, UIComponent component,
			Object object) throws ValidatorException {
		String nome = (String) object;

		GrupoDao grupoDao = new GrupoDao(
				PersistenceUtil.getEntityManager());

		if (grupoDao.verificaNomeExistente(nome)) {
			FacesMessage message = new FacesMessage();
			message.setDetail(MessagesReader.getMessages().getProperty(
					"erroGrupoExistente"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"erroGrupoExistente"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
}
