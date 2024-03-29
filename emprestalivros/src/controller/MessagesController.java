package controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessagesController {

	public static void mensagemInsercaoSucesso(String objeto) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, objeto
						+ " cadastrado(a) com sucesso!", ""));
	}

	public static void mensagemInsercaoLoginDup(String msg, String objeto) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, msg + " "
						+ objeto + "!", ""));
	}

	public static void mensagemErroInsercao(String objeto) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Houve algum erro ao tentar cadastrar um novo "
								+ objeto + "!", ""));
	}

	public static void mensagemOK(String msg, String objeto) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, objeto + " " + msg
						+ ".", ""));
	}

	public static void mensagemWarn(String msg, String objeto) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, msg + " " + objeto
						+ ".", ""));
	}

	public static void mensagemErro(String msg, String objeto) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, msg + " "
						+ objeto + ".", ""));
	}

	public static void mensagemErro(String msg) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, msg + ".", ""));
	}

	public static void mensagemErroCampo(String campo, String msg) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, campo+": Erro de validação: " + msg + ".",""));
	}
}
