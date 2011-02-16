package util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import config.Config;

public class EnviaEmail {

	public static void enviar(String assunto, String msg, String mail,
			 String nome) throws Exception {
		try {
			Email email = new SimpleEmail();
			email.setHostName("smtp.gmail.com");
			email.setAuthenticator(new DefaultAuthenticator(Config.ADM,
					Config.SENHA_EMAIL_ADM));
			email.setSSL(true);
			email.setFrom(Config.EMAIL_ADM, "Sorteios Web");
			email.setSubject(assunto);
			email.setMsg(msg);
			email.addTo(mail,nome);
			email.send();
		} catch (Exception e) {
			throw new Exception(e.getCause());
		}

	}
}
