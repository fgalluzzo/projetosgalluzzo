package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



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
				e1.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			}
			
			
				
		
		}
		return messages;
	}
	
	
	
	
}
