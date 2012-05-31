package profitbourse.modele.majweb;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.Currency;
import java.util.Iterator;

import profitbourse.modele.Action;
import profitbourse.modele.Indice;
import profitbourse.modele.Money;
import profitbourse.modele.Portefeuille;
import profitbourse.modele.Projet;
import au.com.bytecode.opencsv.CSVReader;

public class MiseAJourIndices {
	
	//génération de la chaine de caractères
		public static String toString(Projet projet) {
			return "http://download.finance.yahoo.com/d/quotes.csv?s=" + MiseAJourIndices.getIndices(projet) + "&f="+ AddressGeneratorActions.getTag();
		}


		public static String getIndices(Projet projet){
			String list = null;
			Iterator<Indice> it = projet.getIndices().iterator();
			while (it.hasNext()) {
				list = list + "," + it.next().getCode();
			}
			return list;

		}
		public static String getTag(){
			String tag = "sl1p2d1t1";
			return tag;//s=symbol l1=Last trade d1=last trade date t1=last trade time
			// ajouter variation en 3e position
		}
		
		
		
		
		//Telechargement du fichier
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
				String fileName = "indices.csv";
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
		public static void miseAJour(Projet projet) throws NumberFormatException, IOException{	
			
			CSVReader reader = null;
			try {
				reader = new CSVReader(new FileReader("indices.csv"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String [] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				// nextLine[] is an array of values from the line
				
				//prendre le nextLine[1] en faire un float
				//prendre le nextLine[2] en faire un float
				Float cours = new Float(nextLine[1]);
				Float variation = new Float(nextLine[2]);

				
				//on fait défiler les actions 
				
				///////////////////////DOUTE SUR LE it.next(), DECALAGE ?
				Iterator<Indice> it = projet.getIndices().iterator();
				//it.next().majCoursEtVariation(cours,variation);

				
			}

		}
		
		

}
