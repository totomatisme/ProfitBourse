package profitbourse.modele.majweb;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import profitbourse.modele.Action;
import profitbourse.modele.Portefeuille;
import au.com.bytecode.opencsv.CSVReader;

public class ObtenirNomAction {
	//génération de la chaine de caractères
	public static String toString(String code) {
		return "http://download.finance.yahoo.com/d/quotes.csv?s=" + code + "&f="+ AddressGeneratorActions.getTag();
	}

	public static String getTag(){
		String tag = "n";
		return tag;

	}




	//Telechargement du fichier
	public static void getCSV(String host){

		InputStream input = null;
		FileOutputStream writeFile = null;

		try
		{

			URL url = new URL(host );
			URLConnection connection = url.openConnection();
			//int fileLength = connection.getContentLength();


			input = connection.getInputStream();
			String fileName = "noms.csv";
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


	//Mise à jour des actions
	public static String donnerNom() throws NumberFormatException, IOException{	

		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader("noms.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String [] nextLine = null;
		// nextLine[] is an array of values from the line

		// prendre le nextLine[0] qui est le nom

		return nextLine[0];



	}

}
