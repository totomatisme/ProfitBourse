package profitbourse.modele.majweb;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class RecupInternet {
	public static void getCSV(String host){
		
		InputStream input = null;
		FileOutputStream writeFile = null;

		try
		{
			//String host = "http://finance.yahoo.com/d/quotes.csv?s=XOM+BBDb.TO+JNJ+MSFT&f=sl1p2d1t1&e=.csv";
			URL url = new URL(host );
			URLConnection connection = url.openConnection();
			//int fileLength = connection.getContentLength();


			input = connection.getInputStream();
			String fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1).substring(0, url.getFile().indexOf('?')-3);
			writeFile = new FileOutputStream(fileName);
			byte[] buffer = new byte[1024];
			int read;

			while ((read = input.read(buffer)) > 0)
				writeFile.write(buffer, 0, read);
			writeFile.flush();  

		}
		catch (IOException e)
		{
			System.out.println("Error while trying to download the file.");
			e.printStackTrace();
		}
		finally
		{
			try
			{
				writeFile.close();
				input.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}

