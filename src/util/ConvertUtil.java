package util;

import java.io.*;

public class ConvertUtil {
	// convert stream to string
	public static String convertStreamToString(InputStream input) throws IOException 
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		StringBuilder sb = new StringBuilder();
		
		String line = null;
		
		try
		{
			while((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				input.close();
			}
			catch(IOException e)
			{
				throw e;
			}
		}
		
		return sb.toString();
	}
}
