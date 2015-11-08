package shared.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class StringUtils {

	public static String getString(InputStream input) throws UnsupportedEncodingException {
		
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(input, "UTF-8")); 
	    StringBuilder responseStrBuilder = new StringBuilder();

	    String inputStr;
	    try {
			while ((inputStr = streamReader.readLine()) != null)
			    responseStrBuilder.append(inputStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return responseStrBuilder.toString();
		
	}
	
}