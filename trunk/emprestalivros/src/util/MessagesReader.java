package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class MessagesReader {
	
	private static Properties messages;
	private static InputStream is;
	
	public static Properties getMessages() {
		if(messages == null){
			
			messages = new Properties();
			is = MessagesReader.class.getResourceAsStream("messages.properties");
			try {
				messages.load(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			
		}
		return messages;
	}
	
	
	
	
}
