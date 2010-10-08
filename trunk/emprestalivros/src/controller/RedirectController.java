package controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name="redirect")
@RequestScoped
public class RedirectController {
	
	public String redirectToLogin(){
		System.out.println("login");
		return "login?faces-redirect=true";
	}
	
}
