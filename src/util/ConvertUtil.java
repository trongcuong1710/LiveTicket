package util;

import com.google.zxing.*;
import com.google.zxing.common.*;

import java.io.*;

public class ConvertUtil {
    /**
     * convert stream to string
     * @param input : input stream
     * @return
     * @throws IOException
     */
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

    /**
     * convert barcode byte data to string
     * @param data
     * @return
     */
    public static String barcodeToString(byte[] data, int width, int height)
    {
        try
        {
            LuminanceSource source = new PlanarYUVLuminanceSource(data, width, height, 0, 0, width, height, false);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
            MultiFormatReader reader = new MultiFormatReader();
            Result result =reader.decode(binaryBitmap);
            return result.getText();
        }
        catch (Exception e)
        {
            return "";
        }
    }
}
