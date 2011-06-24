package util;

public class ColorHandler {

	public static int getIntRGB(String color) {
		String cores = color.substring(color.indexOf("[") + 1,
				color.indexOf("]"));
		String coresArray[] = cores.split(",");
		String corRGB = "";
		for (String cor : coresArray) {
			corRGB += cor.substring(cor.indexOf("=") + 1);
		}

		return Integer.parseInt(corRGB);
	}	

	public static int getR(String color) {
		String cores = color.substring(color.indexOf("[") + 1,
				color.indexOf("]"));
		String coresArray[] = cores.split(",");	
		String cor = coresArray[0].substring(coresArray[0].indexOf("=")+1);
		return Integer.parseInt(cor);
	}

	public static int getG(String color) {
		String cores = color.substring(color.indexOf("[") + 1,
				color.indexOf("]"));
		String coresArray[] = cores.split(",");	
		String cor = coresArray[1].substring(coresArray[1].indexOf("=")+1);
		return Integer.parseInt(cor);
	}

	public static int getB(String color) {
		String cores = color.substring(color.indexOf("[") + 1,
				color.indexOf("]"));
		String coresArray[] = cores.split(",");	
		String cor = coresArray[2].substring(coresArray[2].indexOf("=")+1);
		return Integer.parseInt(cor);
	}

}
