package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;



public class MessagesReader {
	
	private static Properties messages;
	private static InputStream is;
	
	public static Properties getMessages() {
		if(messages == null){
			
			messages = new Properties();
			is = MessagesReader.class.getResourceAsStream("/message.properties");  
			try {
				
				messages.load(is);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
				
		
		}
		return messages;
	}
	
	
	
	
}
