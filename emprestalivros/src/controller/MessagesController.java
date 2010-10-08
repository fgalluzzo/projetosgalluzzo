package controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessagesController {
	
	public static void mensagemInsercaoSucesso(String objeto){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						objeto+ " cadastrado(a) com sucesso!", ""));  
	}
	public static void mensagemInsercaoLoginDup(String msg,String objeto){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_ERROR,
						msg+" " + objeto+"!", ""));  
	}
	public static void mensagemErroInsercao(String objeto){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Houve algum erro ao tentar cadastrar um novo "+objeto+"!", ""));  
	}
	
}
